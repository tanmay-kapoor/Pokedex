package edu.northeastern.pokedex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.northeastern.pokedex.models.Message;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Message> messageList;
    private ItemClickListener listener;
    private final Context context;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private static final int SEND = 1;
    private static final int RECEIVE = 2;
    ImageView sticker;

    public RecyclerAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
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
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSender())) {
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
