package edu.northeastern.pokedex.messaging;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Message;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<Message> messageList;
    private ItemClickListener listener;
    private final Context context;
    private SharedPreferences prefs;

    public RecyclerAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
        this.prefs = getDefaultSharedPreferences(context);
    }

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
        Drawable stickerDrawable = AppCompatResources.getDrawable(context, message.getSticker());
        holder.sticker.setImageDrawable(stickerDrawable);
        Log.i("USERNAME and SENDER", prefs.getString("username", "not logged in") + message.getSender());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.sticker.getLayoutParams();
        if(prefs.getString("username", "not logged in") == message.getSender()) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        holder.sticker.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
