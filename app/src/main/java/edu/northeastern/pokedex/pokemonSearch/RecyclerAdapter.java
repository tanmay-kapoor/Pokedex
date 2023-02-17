package edu.northeastern.pokedex.pokemonSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Pokemon;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<Pokemon> pokemonList;
    private ItemClickListener listener;

    public RecyclerAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        return new RecyclerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);

        holder.pokemonName.setText(pokemon.getName());
//        holder.pokemonImage;
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
