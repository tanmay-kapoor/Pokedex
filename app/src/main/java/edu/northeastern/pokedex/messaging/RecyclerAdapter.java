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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Message;


public class RecyclerAdapter extends RecyclerView.Adapter {
    private final List<Message> messageList;
    private final Context context;
    private final SharedPreferences prefs;
    private static final int SEND = 1;
    ImageView sticker;

    public RecyclerAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
        this.prefs = getDefaultSharedPreferences(context);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        if (viewType == 1) {
//            View view = LayoutInflater.from(context).inflate(R.layout.sender_message_item, parent, false);
//            sticker = view.findViewById(R.id.sender_image);
//            return new SenderViewHolder(view);
//        }
//        else {
//            View view = LayoutInflater.from(context).inflate(R.layout.receiver_message_item, parent, false);
//            sticker = view.findViewById(R.id.receiver_image);
//            return new ReceiverViewHolder(view);
//        }

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
//        return new RecyclerHolder(view, listener);
        View view = LayoutInflater.from(context).inflate(R.layout.sender_message_item, parent, false);
        sticker = view.findViewById(R.id.sender_image);
        return new SenderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            Drawable stickerDrawable = AppCompatResources.getDrawable(context, message.getSticker());
            sticker.setImageDrawable(stickerDrawable);
            Log.i("USERNAME and SENDER", prefs.getString("username", "not logged in") + message.getSender());
//            RelativeLayout.LayoutParams image = (RelativeLayout.LayoutParams)sticker.getLayoutParams();
//            if(prefs.getString("username", "not logged in") == message.getSender()) {
//                image.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            }
//            sticker.setLayoutParams(image);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        ImageView receiverImage;
        TextView receiverDate;
        TextView receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverImage = itemView.findViewById(R.id.receiver_image);
            receiverDate = itemView.findViewById(R.id.receiver_date);
            receiverTime = itemView.findViewById(R.id.receiver_time);
        }
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        ImageView senderImage;
        TextView senderDate;
        TextView senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderImage = itemView.findViewById(R.id.sender_image);
            senderDate = itemView.findViewById(R.id.sender_date);
            senderTime = itemView.findViewById(R.id.sender_time);
        }
    }
}
