package edu.northeastern.pokedex.userRV;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.pokedex.FirebaseActivity;
import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.User;

public class RecyclerHolder extends RecyclerView.ViewHolder{
//    public String email;
    public User user;
    private Context context;
    private Intent intent;
    TextView name;
    TextView email;

    ImageView dpImage;

    public RecyclerHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.userNameTV);
        email = itemView.findViewById(R.id.emailID);
        dpImage = itemView.findViewById(R.id.userDP);
        CardView userItem = itemView.findViewById(R.id.userCV);

        userItem.setOnClickListener(view -> {
            context = itemView.getContext();
            intent = new Intent(context, FirebaseActivity.class);
//            intent.putExtra("email", email);
            intent.putExtra("uid", user.getUid());
            context.startActivity(intent);
        });
    }

}
