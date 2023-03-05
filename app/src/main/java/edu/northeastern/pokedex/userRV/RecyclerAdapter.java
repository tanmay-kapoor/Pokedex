package edu.northeastern.pokedex.userRV;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.User;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<User> userList;
    private ItemClickListener listener;

    public RecyclerAdapter(List<User> userList, Context context) {
        this.userList = userList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_item, parent, false);
        return new RecyclerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.email = user.getEmail();
        Log.i("SETTING NAME", user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

