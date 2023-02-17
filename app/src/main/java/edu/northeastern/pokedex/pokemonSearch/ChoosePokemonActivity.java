package edu.northeastern.pokedex.pokemonSearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Pokemon;

public class ChoosePokemonActivity extends AppCompatActivity {
    private final List<Pokemon> pokemonList = new ArrayList<>();

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

                } catch(Exception e) {
                    Snackbar snackbar = Snackbar.make(recyclerView,"Pokedata not found!", Snackbar.LENGTH_SHORT);
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
}
