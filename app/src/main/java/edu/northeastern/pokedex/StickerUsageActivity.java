package edu.northeastern.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class StickerUsageActivity extends AppCompatActivity {

    private DatabaseReference userStickerRef;
    private Map<Integer, Integer> stickerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_usage);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        userStickerRef = mDatabase.child("room1").child("stickers").child(user.getUid());
        stickerDetails = new HashMap<>();
        getStickerDetails();
    }

    private void getStickerDetails() {
        userStickerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        int sticker = Integer.parseInt(dsp.getKey());
                        int count = Integer.parseInt(dsp.getValue().toString());
                        stickerDetails.put(sticker, count);
                    }
                    // display each sticker with count

                } else {
                    // no stickers for this user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}