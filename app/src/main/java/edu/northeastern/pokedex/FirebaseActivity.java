package edu.northeastern.pokedex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.northeastern.pokedex.messaging.RecyclerAdapter;
import edu.northeastern.pokedex.models.Message;

public class FirebaseActivity extends AppCompatActivity {

    private DatabaseReference messageRef;
    private DatabaseReference stickerRef;
    private List<Message> messageList;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_messaging);
        createNotificationChannel();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        messageRef = mDatabase.child("room1").child("messages");
        stickerRef = mDatabase.child("room1").child("stickers");
        messageList = new ArrayList<>();

        listenForMessageUpdates();
        init(savedInstanceState);

        Button chooseStickerBtn = findViewById(R.id.chooseBtn);
        chooseStickerBtn.setOnClickListener(view -> startChooseStickerActivity());
    }

    private void startChooseStickerActivity() {
        startActivity(new Intent(FirebaseActivity.this, ChooseStickerActivity.class));
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(int sticker) {
        Intent intent = new Intent(this, FirebaseActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Intent intentForReply = new Intent(this, FirebaseActivity.class);
        intentForReply.putExtra("fromNotification", true);
        intentForReply.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent checkIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentForReply, 0);

        String channelId = "id";
        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.team_47_icon_foreground)
                .setContentTitle(user.getDisplayName())
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(getBitmap(sticker)).bigLargeIcon(null))
                .setLargeIcon(getBitmap(sticker))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.team_47_icon_foreground, "Reply", checkIntent)
                .setContentIntent(pIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notifyBuild.build());
    }

    private Bitmap getBitmap(int sticker) {
        return BitmapFactory.decodeResource(getResources(), sticker);
    }

    private void init(Bundle savedInstanceState) {
        recyclerView();
    }

    public void getStickerDetails(View view) {
        startActivity(new Intent(FirebaseActivity.this, StickerUsageActivity.class));
    }

    private void recyclerView() {
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.messagesRv);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter = new RecyclerAdapter(messageList, getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManager);
    }

    private void updateMessagesMap(DataSnapshot snapshot) {
        long timestamp = Long.parseLong(snapshot.getKey());
        Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
        String sender = children.next().getValue().toString();
        int sticker = Integer.parseInt(children.next().getValue().toString());

        messageList.add(new Message(sender, sticker));
    }

    private void listenForMessageUpdates() {
        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long existingChildrenCount = snapshot.getChildrenCount();
                final long[] cnt = {0};

                messageRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        cnt[0]++;

                        updateMessagesMap(snapshot);
                        recyclerAdapter.notifyItemInserted(messageList.size());
                        recyclerView.scrollToPosition(messageList.size() - 1);

                        if (cnt[0] == existingChildrenCount && getIntent().hasExtra("fromNotification")) {
                            startChooseStickerActivity();
                        } else if (cnt[0] > existingChildrenCount) {
                            Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
                            String msgSender = children.next().getValue().toString();
                            int sticker = Integer.parseInt(children.next().getValue().toString());

                            if (!TextUtils.equals(msgSender, user.getEmail())) {
                                sendNotification(sticker);
                            }

                            DatabaseReference ref = stickerRef.child(user.getUid()).child(Integer.toString(sticker));
                            ref.setValue(ServerValue.increment(1));
                        }
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}