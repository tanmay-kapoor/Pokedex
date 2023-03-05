package edu.northeastern.pokedex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

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

public class ChooseStickerActivity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
//    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//    private final DatabaseReference messageRef = mDatabase.child("room1").child("messages");

    private FirebaseDatabase database;
    private List<Pair<Drawable, Integer>> stickerList;
    private StickerAdapter stickerAdapter;
    private RecyclerView stickerRecycler;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.sticker_recycler);

        getSupportActionBar().setTitle("Stickers");
        String recID = getIntent().getStringExtra("recID");

        stickerList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("stickers");

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Resources res = getResources();
//                Drawable cool = ResourcesCompat.getDrawable(res, R.drawable.cool, null);
//                Drawable annoyed = ResourcesCompat.getDrawable(res, R.drawable.annoyed, null);
//                Drawable cry = ResourcesCompat.getDrawable(res, R.drawable.cry, null);
//                Drawable smile = ResourcesCompat.getDrawable(res, R.drawable.smile, null);
//                Drawable irritated = ResourcesCompat.getDrawable(res, R.drawable.irritated, null);
//                Drawable think = ResourcesCompat.getDrawable(res, R.drawable.think, null);
//                Drawable wink = ResourcesCompat.getDrawable(res, R.drawable.wink, null);
//                Drawable surprised = ResourcesCompat.getDrawable(res, R.drawable.surprised, null);
//                Drawable sobbing = ResourcesCompat.getDrawable(res, R.drawable.sobbing, null);
//                Drawable sick = ResourcesCompat.getDrawable(res, R.drawable.sick, null);
//                Drawable money = ResourcesCompat.getDrawable(res, R.drawable.money, null);
//                Drawable monkey = ResourcesCompat.getDrawable(res, R.drawable.monkey, null);
//                Drawable liar = ResourcesCompat.getDrawable(res, R.drawable.liar, null);
//                Drawable cold = ResourcesCompat.getDrawable(res, R.drawable.cold, null);
//                Drawable eyes = ResourcesCompat.getDrawable(res, R.drawable.eyes, null);
//                Drawable king = ResourcesCompat.getDrawable(res, R.drawable.king, null);
//                Drawable queen = ResourcesCompat.getDrawable(res, R.drawable.queen, null);
//                Drawable panda = ResourcesCompat.getDrawable(res, R.drawable.panda, null);
//                Drawable flower = ResourcesCompat.getDrawable(res, R.drawable.flower, null);
//                Drawable poop = ResourcesCompat.getDrawable(res, R.drawable.poop, null);
                stickerList.add(new Pair<>(getDrawable(R.drawable.cool), R.drawable.cool));
                stickerList.add(new Pair<>(getDrawable(R.drawable.annoyed), R.drawable.annoyed));
                stickerList.add(new Pair<>(getDrawable(R.drawable.cry), R.drawable.cry));
                stickerList.add(new Pair<>(getDrawable(R.drawable.smile), R.drawable.smile));
                stickerList.add(new Pair<>(getDrawable(R.drawable.irritated), R.drawable.irritated));
                stickerList.add(new Pair<>(getDrawable(R.drawable.think), R.drawable.think));
                stickerList.add(new Pair<>(getDrawable(R.drawable.wink), R.drawable.wink));
                stickerList.add(new Pair<>(getDrawable(R.drawable.surprised), R.drawable.surprised));
                stickerList.add(new Pair<>(getDrawable(R.drawable.sobbing), R.drawable.sobbing));
                stickerList.add(new Pair<>(getDrawable(R.drawable.sick), R.drawable.sick));
                stickerList.add(new Pair<>(getDrawable(R.drawable.money), R.drawable.money));
                stickerList.add(new Pair<>(getDrawable(R.drawable.monkey), R.drawable.monkey));
                stickerList.add(new Pair<>(getDrawable(R.drawable.liar), R.drawable.liar));
                stickerList.add(new Pair<>(getDrawable(R.drawable.cold), R.drawable.cold));
                stickerList.add(new Pair<>(getDrawable(R.drawable.eyes), R.drawable.eyes));
                stickerList.add(new Pair<>(getDrawable(R.drawable.king), R.drawable.king));
                stickerList.add(new Pair<>(getDrawable(R.drawable.queen), R.drawable.queen));
                stickerList.add(new Pair<>(getDrawable(R.drawable.panda), R.drawable.panda));
                stickerList.add(new Pair<>(getDrawable(R.drawable.flower), R.drawable.flower));
                stickerList.add(new Pair<>(getDrawable(R.drawable.poop), R.drawable.poop));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChooseStickerActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
