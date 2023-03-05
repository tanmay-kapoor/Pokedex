package edu.northeastern.pokedex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import edu.northeastern.pokedex.models.Message;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Message> messageList;
    private final Context context;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private static final int SEND = 1;
    private static final int RECEIVE = 2;
    ImageView sticker;

    public RecyclerAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sticker = parent.findViewById(R.id.sticker);
        if (viewType == SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_message_item, parent, false);
            return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_message_item, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(holder.getClass() == ReceiverViewHolder.class) {
            ReceiverViewHolder receiveHolder =  (ReceiverViewHolder) holder;
            Drawable image = AppCompatResources.getDrawable(context, message.getSticker());
            Log.i("RECEIVED", String.valueOf(message.getSticker()));
            receiveHolder.sticker.setImageDrawable(image);
            setSenderName(receiveHolder, message);
            receiveHolder.receiverDate.setText(message.getDate());
            receiveHolder.receiverTime.setText(message.getTime());
        } else {
            SenderViewHolder senderHolder = (SenderViewHolder) holder;
            Drawable image = AppCompatResources.getDrawable(context, message.getSticker());
            Log.i("SENT", String.valueOf(message.getSticker()));
            senderHolder.sticker.setImageDrawable(image);
            senderHolder.senderName.setText(user.getDisplayName());
            senderHolder.senderDate.setText(message.getDate());
            senderHolder.senderTime.setText(message.getTime());
        }
    }

    private void setSenderName(ReceiverViewHolder holder, Message message) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child("users").child(message.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
                children.next();
                String name = children.next().getValue().toString();
                holder.receiverName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(message.getSender())) {
            return SEND;
        } else {
            return RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        ImageView sticker;
        TextView receiverDate;
        TextView receiverTime;
        TextView receiverName;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverName = itemView.findViewById(R.id.receiver_name);
            sticker = itemView.findViewById(R.id.sticker_image);
            receiverDate = itemView.findViewById(R.id.sticker_count);
            receiverTime = itemView.findViewById(R.id.sticker_name);
        }
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        ImageView sticker;
        TextView senderName;
        TextView senderDate;
        TextView senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sticker = itemView.findViewById(R.id.sender_image);
            senderName = itemView.findViewById(R.id.sender_name);
            senderDate = itemView.findViewById(R.id.sender_date);
            senderTime = itemView.findViewById(R.id.sender_time);
        }
    }
}
