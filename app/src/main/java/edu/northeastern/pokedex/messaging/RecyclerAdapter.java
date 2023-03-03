package edu.northeastern.pokedex.messaging;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Message;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<Message> messageList;
    private ItemClickListener listener;

    public RecyclerAdapter(List<Message> messageList) { this.messageList = messageList; }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new RecyclerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        Message message = messageList.get(position);

//        holder.sticker.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
