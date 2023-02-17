package edu.northeastern.pokedex.pokemonSearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Pokemon;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<Pokemon> pokemonList;
    private ItemClickListener listener;

    private Handler imageUpdateHandler = new Handler();

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
        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonImage.setImageBitmap(pokemon.getImageBitmap());
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
