package edu.northeastern.pokedex;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.pokedex.models.Message;


public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
    private static Context context;
    private List<Pair<Drawable, Integer>> stickerList;
    Map<Drawable, Integer> stickerMap;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private String senderId, receiverId;

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseUser user = mAuth.getCurrentUser();
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference messageRef;
    static String key;

    public StickerAdapter(Context context, List<Pair<Drawable, Integer>> stickerList, String receiverId) {
        StickerAdapter.context = context;
        this.stickerList = stickerList;
        assert user != null;
        this.senderId = user.getUid();
        this.receiverId = receiverId;

        key = senderId.compareTo(receiverId) <= 0 ? senderId + "+" + receiverId : receiverId + "+" + senderId;
        messageRef = mDatabase.child("messages").child(key);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sticker_popup, parent, false);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable s = stickerList.get(position).first;
        holder.mImage.setImageDrawable(s);
        holder.imageInt = stickerList.get(position).second;
    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        int imageInt;

        ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.sticker);
            mImage.setOnClickListener(view -> {
                sendMessage(imageInt);
            });
        }

    }

    public static void sendMessage(int image) {

        String timestamp = Long.toString(System.currentTimeMillis());
        Date timestampObject = new Date(System.currentTimeMillis());
        // temp values
        assert user != null;
        String sender = user.getEmail();

        // change when adding grid view
        Message message = new Message(sender, image, user.getUid(), timestampObject);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + timestamp, message);
        messageRef.updateChildren(childUpdates);
        Log.i("MSG SENDER", "should've got message");
        ((Activity)context).finish();
//        Toast.makeText(ChooseStickerActivity.this, "msg sent", Toast.LENGTH_LONG).show();
    }

}