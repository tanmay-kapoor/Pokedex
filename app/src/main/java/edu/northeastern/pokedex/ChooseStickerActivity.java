package edu.northeastern.pokedex;

import android.annotation.SuppressLint;
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
    private List<Pair<String,String>> stickerList;
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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey().toString();
                    String val = dataSnapshot.getValue().toString();
                    stickerList.add(new Pair<>(key, val));
                    System.out.println(stickerList);
                }
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

    public void sendMessage(int image) {
        String timestamp = Long.toString(System.currentTimeMillis());

        // temp values
        assert user != null;
        String sender = user.getEmail();

        // change when adding grid view
        Message message = new Message(sender, image);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + timestamp, message);
        messageRef.updateChildren(childUpdates);
        Log.i("MSGSENDER", "shoul've got message");
        finish();
//                Toast.makeText(FirebaseActivity.this, "msg sent", Toast.LENGTH_LONG).show();
    }

}
