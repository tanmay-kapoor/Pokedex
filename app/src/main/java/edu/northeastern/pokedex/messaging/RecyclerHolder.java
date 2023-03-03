package edu.northeastern.pokedex.messaging;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import edu.northeastern.pokedex.FirebaseActivity;
import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Message;

public class RecyclerHolder extends RecyclerView.ViewHolder{
    private Map<Long, Message> messages;
    ImageView sticker;
    CardView cardView;

    public RecyclerHolder(View itemView, final ItemClickListener listener) {
        super(itemView);

        sticker = itemView.findViewById(R.id.sticker);
        cardView = itemView.findViewById(R.id.messageCV);

        cardView.setOnClickListener(view -> {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, FirebaseActivity.class);
            context.startActivity(intent);
        });
    }

}
