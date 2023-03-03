package edu.northeastern.pokedex.assignment7.pokemonSearch;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.pokedex.assignment7.PokemonDetailsActivity;
import edu.northeastern.pokedex.R;

public class RecyclerHolder extends RecyclerView.ViewHolder{
    private  Context context;
    private Intent intent;
    ImageView pokemonImage;
    TextView pokemonName;
    TextView pokemonID;
    private CardView pokemonItem;
    int pokeID;

    public RecyclerHolder(View itemView, final ItemClickListener listener) {
        super(itemView);


        pokemonName = itemView.findViewById(R.id.pokemonNameTV);
        pokemonID = itemView.findViewById(R.id.pokeID);
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
