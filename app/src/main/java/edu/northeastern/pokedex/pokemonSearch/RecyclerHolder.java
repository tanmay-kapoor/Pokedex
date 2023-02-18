package edu.northeastern.pokedex.pokemonSearch;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

import edu.northeastern.pokedex.MainActivity;
import edu.northeastern.pokedex.PokemonDetailsActivity;
import edu.northeastern.pokedex.R;

public class RecyclerHolder extends RecyclerView.ViewHolder{
    private  Context context;
    private Intent intent;
    ImageView pokemonImage;
    TextView pokemonName;
    private CardView pokemonItem;
    int pokeID;

    public RecyclerHolder(View itemView, final ItemClickListener listener) {
        super(itemView);


        pokemonName = itemView.findViewById(R.id.pokemonNameTV);
        pokemonImage = itemView.findViewById(R.id.pokemonImageIV);
        pokemonItem = itemView.findViewById(R.id.pokemonItemCV);

        pokemonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = itemView.getContext();
                intent = new Intent(context, PokemonDetailsActivity.class);
                intent.putExtra("pokeID", pokeID);
                context.startActivity(intent);
            }
        });

    }



}
