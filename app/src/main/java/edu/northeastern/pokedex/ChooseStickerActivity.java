package edu.northeastern.pokedex;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.pokedex.models.Message;

public class ChooseStickerActivity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference messageRef = mDatabase.child("room1").child("messages");

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private List<Pair<String, Drawable>> stickerList;
    private StickerAdapter stickerAdapter;
    private RecyclerView stickerRecycler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.sticker_recycler);
        String recID = getIntent().getStringExtra("recID");
        stickerList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("stickers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Resources res = getResources();
                Drawable cool = ResourcesCompat.getDrawable(res, R.drawable.cool, null);
                Drawable annoyed = ResourcesCompat.getDrawable(res, R.drawable.annoyed, null);
                Drawable cry = ResourcesCompat.getDrawable(res, R.drawable.cry, null);
                Drawable smile = ResourcesCompat.getDrawable(res, R.drawable.smile, null);
                Drawable irritated = ResourcesCompat.getDrawable(res, R.drawable.irritated, null);
                Drawable think = ResourcesCompat.getDrawable(res, R.drawable.think, null);
                Drawable wink = ResourcesCompat.getDrawable(res, R.drawable.wink, null);
                Drawable surprised = ResourcesCompat.getDrawable(res, R.drawable.surprised, null);
                Drawable sobbing = ResourcesCompat.getDrawable(res, R.drawable.sobbing, null);
                Drawable sick = ResourcesCompat.getDrawable(res, R.drawable.sick, null);
                Drawable money = ResourcesCompat.getDrawable(res, R.drawable.money, null);
                Drawable monkey = ResourcesCompat.getDrawable(res, R.drawable.monkey, null);
                Drawable liar = ResourcesCompat.getDrawable(res, R.drawable.liar, null);
                Drawable cold = ResourcesCompat.getDrawable(res, R.drawable.cold, null);
                Drawable eyes = ResourcesCompat.getDrawable(res, R.drawable.eyes, null);
                Drawable king = ResourcesCompat.getDrawable(res, R.drawable.king, null);
                Drawable queen = ResourcesCompat.getDrawable(res, R.drawable.queen, null);
                Drawable panda = ResourcesCompat.getDrawable(res, R.drawable.panda, null);
                Drawable flower = ResourcesCompat.getDrawable(res, R.drawable.flower, null);
                Drawable poop = ResourcesCompat.getDrawable(res, R.drawable.poop, null);
                stickerList.add(new Pair<>("cool", cool));
                stickerList.add(new Pair<>("annoyed", annoyed));
                stickerList.add(new Pair<>("cry", cry));
                stickerList.add(new Pair<>("smile", smile));
                stickerList.add(new Pair<>("irritated", irritated));
                stickerList.add(new Pair<>("think", think));
                stickerList.add(new Pair<>("wink", wink));
                stickerList.add(new Pair<>("surprised", surprised));
                stickerList.add(new Pair<>("sobbing", sobbing));
                stickerList.add(new Pair<>("sick", sick));
                stickerList.add(new Pair<>("money", money));
                stickerList.add(new Pair<>("monkey", monkey));
                stickerList.add(new Pair<>("liar", liar));
                stickerList.add(new Pair<>("cold", cold));
                stickerList.add(new Pair<>("eyes", eyes));
                stickerList.add(new Pair<>("king", king));
                stickerList.add(new Pair<>("queen", queen));
                stickerList.add(new Pair<>("panda", panda));
                stickerList.add(new Pair<>("flower", flower));
                stickerList.add(new Pair<>("poop", poop));
                System.out.println(stickerList);
                stickerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        stickerRecycler = findViewById(R.id.sticker_recycler);
        stickerRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        stickerAdapter = new StickerAdapter(this, stickerList, recID);
        stickerRecycler.setAdapter(stickerAdapter);
    };

//    public void sendMessage(int image) {
//        String timestamp = Long.toString(System.currentTimeMillis());
//
//        // temp values
//        assert user != null;
//        String sender = user.getEmail();
//
//        // change when adding grid view
//        Message message = new Message(sender, image);
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/" + timestamp, message);
//        messageRef.updateChildren(childUpdates);
//        Log.i("MSGSENDER", "shoul've got message");
//        finish();
////                Toast.makeText(FirebaseActivity.this, "msg sent", Toast.LENGTH_LONG).show();
//    }

}
