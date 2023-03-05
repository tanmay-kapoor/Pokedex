package edu.northeastern.pokedex.userRV;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.pokedex.FirebaseActivity;
import edu.northeastern.pokedex.R;

public class RecyclerHolder extends RecyclerView.ViewHolder{
    public String email;
    private Context context;
    private Intent intent;
    TextView name;

    public RecyclerHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.userNameTV);
        CardView userItem = itemView.findViewById(R.id.userCV);

        userItem.setOnClickListener(view -> {
            context = itemView.getContext();
            intent = new Intent(context, FirebaseActivity.class);
            intent.putExtra("email", email);
            context.startActivity(intent);
        });
    }

}
