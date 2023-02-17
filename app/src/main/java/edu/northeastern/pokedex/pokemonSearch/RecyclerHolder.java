package edu.northeastern.pokedex.pokemonSearch;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

import edu.northeastern.pokedex.R;

public class RecyclerHolder extends RecyclerView.ViewHolder{
    ImageView pokemonImage;
    TextView pokemonName;
    private CardView pokemonItem;

    public RecyclerHolder(View itemView, final ItemClickListener listener) {
        super(itemView);
        pokemonName = itemView.findViewById(R.id.pokemonNameTV);
        pokemonImage = itemView.findViewById(R.id.pokemonImageIV);
        pokemonItem = itemView.findViewById(R.id.pokemonItemCV);
        pokemonItem.setOnClickListener(view -> {

        });
    }



}
