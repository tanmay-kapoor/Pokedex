package edu.northeastern.pokedex.pokemonSearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
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
    private Button previousButton;
    private Button nextButton;
    private String currPokeListLink;
    private String prevPokeListLink;
    private String nextPokeListLink;
    private ProgressBar progressBar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currPokeListLink = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20";
        prevPokeListLink = null;
        nextPokeListLink = null;
        setContentView(R.layout.choose_pokemon);
        searchView = findViewById(R.id.pokemonSV);
        previousButton = findViewById(R.id.prevBtn);
        nextButton = findViewById(R.id.nextBtn);
        progressBar = findViewById(R.id.progressBar);
        init(savedInstanceState);
    }

    public void getNextPage(View view) {
        if(nextPokeListLink == null) {
            return;
        }
        currPokeListLink = nextPokeListLink;
        recycleRecyclerView();
    }

    public void getPreviousPage(View view) {
        if(prevPokeListLink == null) {
            return;
        }
        currPokeListLink = prevPokeListLink;
        recycleRecyclerView();
    }

    private void init(Bundle savedInstanceState) {
        recycleRecyclerView();
    }

    private void recycleRecyclerView() {
        //Delete all items from the recyclerview
        if(pokemonList.size() > 0) {
            pokemonList.removeIf(pokemon -> true == true);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        progressBar.setVisibility(View.VISIBLE);
        //Get new data
        Thread getPokemonList = new Thread(new PokemonList(currPokeListLink));
        getPokemonList.start();
        //Create recycler with new data
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
        String pokeListLink;

        PokemonList(String link) {
            this.pokeListLink = link;
        }

        @Override
        public void run() {
            try {
                String pokeListResult = NetworkUtil.httpResponse(new URL(pokeListLink));
                JSONObject pokeListJSON = new JSONObject(pokeListResult);
                if(pokeListJSON.has("next")) {
                    nextPokeListLink = String.valueOf(pokeListJSON.get("next"));
                }
                if(pokeListJSON.has("previous")) {
                    prevPokeListLink = String.valueOf(pokeListJSON.get("previous"));
                }
                JSONArray pokemonOverview = pokeListJSON.getJSONArray("results");
                for (int i = 0; i < pokemonOverview.length(); i++) {
                    String pokemonDataResult = NetworkUtil.httpResponse(new URL(String.valueOf(pokemonOverview.getJSONObject(i).get("url"))));
                    JSONObject pokemonDataJSON = new JSONObject(pokemonDataResult);

                    URL urlConnection = new URL(String.valueOf(pokemonDataJSON.getJSONObject("sprites").get("front_default")));
                    HttpURLConnection connection = (HttpURLConnection) urlConnection
                            .openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);

                    pokemonList.add(new Pokemon(
                            String.valueOf(pokemonOverview.getJSONObject(i).get("name")),
                            String.valueOf(pokemonOverview.getJSONObject(i).get("url")),
                            Integer.valueOf(String.valueOf(pokemonDataJSON.get("id"))),
                            myBitmap
                    ));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }
}
