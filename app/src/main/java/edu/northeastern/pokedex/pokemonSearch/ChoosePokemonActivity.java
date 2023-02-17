package edu.northeastern.pokedex.pokemonSearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Pokemon;
import edu.northeastern.pokedex.utils.NetworkUtil;

public class ChoosePokemonActivity extends AppCompatActivity {
    private List<Pokemon> pokemonList = new ArrayList<>();

    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerLayoutManger;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_pokemon);

        searchView = findViewById(R.id.pokemonSV);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        // get initial item data
        Thread getPokemonList = new Thread(new PokemonList());
        getPokemonList.start();
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        createRecyclerView();
    }

    private void createRecyclerView() {
        recyclerLayoutManger = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.pokemonsRV);
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(pokemonList);

        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    startActivity(intent);
                    recyclerAdapter.notifyItemChanged(position);

                } catch (Exception e) {
                    Snackbar snackbar = Snackbar.make(recyclerView, "Pokedata not found!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

            @Override
            public void onEditBtnClick(int position) {

            }
        };

        recyclerAdapter.setOnItemClickListener(itemClickListener);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManger);
    }

    private class PokemonList implements Runnable {
        String link = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20";

        @Override
        public void run() {
            try {
                URL url = new URL(link);
                String res = NetworkUtil.httpResponse(url);
                JSONObject pokemonData = new JSONObject(res);
                JSONArray pokemons = pokemonData.getJSONArray("results");
                for (int i = 0; i < pokemons.length(); i++) {
                    pokemonList.add(new Pokemon(
                            String.valueOf(pokemons.getJSONObject(i).get("name")),
                            String.valueOf(pokemons.getJSONObject(i).get("url")),
                            i
                    ));
                }

                for(Pokemon p: pokemonList) {
                    System.out.println(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
