package edu.northeastern.pokedex;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.northeastern.pokedex.messaging.RecyclerAdapter;
import edu.northeastern.pokedex.models.Message;

public class FirebaseActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference messageRef;
    private List<Message> messageList;
    private RecyclerView recyclerView;
    private SharedPreferences prefs;
    private String username;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_messaging);

        prefs = getDefaultSharedPreferences(getApplicationContext());
        username = prefs.getString("username", "not logged in");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messageRef = mDatabase.child("room1").child("messages");
        messageList = new ArrayList<>();

        populateMessagesMap(savedInstanceState);

    }

    private void init (Bundle savedInstanceState) {
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
        String sender = username;

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

    private void populateMessagesMap(Bundle savedInstanceState) {
        Query query = messageRef.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp : snapshot.getChildren()) {
                    updateMessagesMap(dsp);
                }
                init(savedInstanceState);
                listenForMessageUpdates();
                recyclerView.scrollToPosition(messageList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void listenForMessageUpdates() {
        messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // add in hashmap and update on screen
                updateMessagesMap(snapshot);
                recyclerAdapter.notifyItemInserted(messageList.size());
                recyclerView.scrollToPosition(messageList.size() - 1);

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