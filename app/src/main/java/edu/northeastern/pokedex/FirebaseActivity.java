package edu.northeastern.pokedex;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.northeastern.pokedex.models.Message;

public class FirebaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference messageRef;
    private Map<Long, Message> messages;
    private SharedPreferences prefs;
    private String username;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        prefs = getDefaultSharedPreferences(getApplicationContext());
        username = prefs.getString("username", "not logged in");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messageRef = mDatabase.child("room1").child("messages");
        messages = new HashMap<>();
        listenForMessageUpdates();
        displayMessages();
        logout = findViewById(R.id.logOutBtn);
        logout.setOnClickListener((it -> {
            logout();
        }));
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(FirebaseActivity.this, LoginActivity.class));
        Toast.makeText(FirebaseActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
    }

    public void displayMessages() {
        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp : snapshot.getChildren()) {
                    long timestamp = Long.parseLong(dsp.getKey());
                    Iterator<DataSnapshot> children = dsp.getChildren().iterator();
                    String sender = children.next().getValue().toString();
                    String sticker = children.next().getValue().toString();
                    Log.i("SENDER", sender);
                    Log.i("STICKER", sticker);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendMessage(View view) {
        String timestamp = Long.toString(System.currentTimeMillis());

        // temp values
        String sender = username;

        // change when adding grid view
        int sticker = R.drawable.laugh;
        Message message = new Message(sender, sticker);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + timestamp, message);
        messageRef.updateChildren(childUpdates);
    }

    private void updateMessagesMap(DataSnapshot snapshot) {
        long timestamp = Long.parseLong(snapshot.getKey());
        Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
        String sender = children.next().getValue().toString();
        int sticker = Integer.parseInt(children.next().getValue().toString());

        Message message = new Message(sender, sticker);
        messages.put(timestamp, message);
    }

    private void listenForMessageUpdates() {
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // add in hashmap and update on screen
                updateMessagesMap(snapshot);

                // if sender matches user name in shared pref then
                //      display on right (sent by this user)
                // else
                //      display on left (received by this user)
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("onChildChanged", "called");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // delete from messages map and update screen
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}