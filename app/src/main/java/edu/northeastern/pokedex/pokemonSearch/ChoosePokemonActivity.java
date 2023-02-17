package edu.northeastern.pokedex.pokemonSearch;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.pokedex.R;

public class ChoosePokemonActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerLayoutManger;

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

    }
}
