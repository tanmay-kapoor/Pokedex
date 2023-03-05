package edu.northeastern.pokedex.messaging;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Objects;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.Message;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private final List<Message> messageList;
    private ItemClickListener listener;
    private final Context context;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();

    public RecyclerAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
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
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.sticker.getLayoutParams();
        assert user != null;
        if(Objects.equals(user.getEmail(), message.getSender())) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        holder.sticker.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
