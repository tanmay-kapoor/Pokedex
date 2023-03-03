package edu.northeastern.pokedex.messaging;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
//    private final List<Message> messageList;
    private ItemClickListener listener;

//    public RecyclerAdapter(List<Pokemon> pokemonList) {
//        this.pokemonList = pokemonList;
//    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
