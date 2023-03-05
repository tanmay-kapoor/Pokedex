package edu.northeastern.pokedex.assignment7.pokemonSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.assignment7.models.Pokemon;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<Pokemon> pokemonList;
    private ItemClickListener listener;

    public RecyclerAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
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
        holder.pokemonName.setText(pokemon.getName().toUpperCase(Locale.ROOT));
        holder.pokemonImage.setImageBitmap(pokemon.getImageBitmap());
        holder.pokemonID.setText(String.valueOf(pokemon.getId()));
        holder.pokeID = pokemon.getId();
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
