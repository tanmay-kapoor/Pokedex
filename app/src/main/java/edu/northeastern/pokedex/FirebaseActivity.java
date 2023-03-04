package edu.northeastern.pokedex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.northeastern.pokedex.messaging.RecyclerAdapter;
import edu.northeastern.pokedex.models.Message;

public class FirebaseActivity extends AppCompatActivity {

    private DatabaseReference messageRef;
    private List<Message> messageList;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_messaging);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        messageRef = mDatabase.child("room1").child("messages");
        messageList = new ArrayList<>();

        listenForMessageUpdates();
        init(savedInstanceState);

        Button chooseStickerBtn = findViewById(R.id.chooseBtn);
        chooseStickerBtn.setOnClickListener(view -> startActivity(new Intent(FirebaseActivity.this, ChooseStickerActivity.class)));
//        chooseStickerBtn.setOnClickListener(view -> sendMessage(view));
    }

    private void init(Bundle savedInstanceState) {
        recyclerView();
    }

    private void recyclerView() {
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.messagesRv);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerAdapter(messageList, getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManager);
    }

    public void sendMessage(View view) {
        String timestamp = Long.toString(System.currentTimeMillis());

        // temp values
        String sender = user.getEmail();

        // change when adding grid view
        int sticker = R.drawable.speculate;
        Message message = new Message(sender, sticker);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + timestamp, message);
        messageRef.updateChildren(childUpdates);
        Toast.makeText(FirebaseActivity.this, "msg sent", Toast.LENGTH_LONG).show();
    }

    private void updateMessagesMap(DataSnapshot snapshot) {
        long timestamp = Long.parseLong(snapshot.getKey());
        Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
        String sender = children.next().getValue().toString();
        int sticker = Integer.parseInt(children.next().getValue().toString());

        messageList.add(new Message(sender, sticker));
    }

    private void listenForMessageUpdates() {
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // add in hashmap and update on screen
                updateMessagesMap(snapshot);
                recyclerAdapter.notifyItemInserted(messageList.size());
                recyclerView.scrollToPosition(messageList.size() - 1);
                String msgSender = snapshot.getChildren().iterator().next().getValue().toString();
                if (msgSender.equals(user.getEmail())) {
                    Toast.makeText(FirebaseActivity.this, "wassup", Toast.LENGTH_SHORT).show();
                }

                // if sender is current user then
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