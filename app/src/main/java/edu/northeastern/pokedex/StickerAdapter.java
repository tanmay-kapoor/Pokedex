package edu.northeastern.pokedex;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import edu.northeastern.pokedex.models.Message;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    private Context context;
    private List<Pair<String, Drawable>> mData;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
//    private String senderRoom, receiverRoom;
    private String senderId, receiverId;

    public StickerAdapter(Context context, List<Pair<String, Drawable>> mData, String receiverId) {
        this.context = context;
        this.mData = mData;
        this.receiverId = receiverId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sticker_popup, parent, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        return new StickerAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable s = mData.get(position).second;
        String emojiName = mData.get(position).first;
        holder.mImage.setImageDrawable(s);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.sticker);
        }
    }
}