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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.northeastern.pokedex.models.Message;

public class FirebaseActivity extends AppCompatActivity {

//    private DatabaseReference messageRef;
    private DatabaseReference mRef;

//    private DatabaseReference stickerRef;
    private DatabaseReference sRef;

    private DatabaseReference userRef;

    private List<Message> messageList;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private FirebaseUser user;
    private String currUid;
    private String otherUid;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_messaging);
        getSupportActionBar().setTitle("Chat");
        createNotificationChannel();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

//        messageRef = mDatabase.child("room1").child("messages");
        currUid = user.getUid();
        otherUid = getIntent().getStringExtra("uid");
        key = currUid.compareTo(otherUid) <= 0 ? currUid + "+" + otherUid : otherUid + "+" + currUid;
        mRef = mDatabase.child("messages").child(key);

//        stickerRef = mDatabase.child("room1").child("stickers");
        sRef = mDatabase.child("stickers").child(currUid);

        userRef = mDatabase.child("users");
        messageList = new ArrayList<>();

        listenForMessageUpdates();
        init(savedInstanceState);

        Button chooseStickerBtn = findViewById(R.id.chooseBtn);
        chooseStickerBtn.setOnClickListener(view -> startChooseStickerActivity());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(FirebaseActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.stats:
                intent = new Intent(getApplicationContext(), StickerStatsActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startChooseStickerActivity() {
        Intent intent = new Intent(FirebaseActivity.this, ChooseStickerActivity.class);
        intent.putExtra("recID", otherUid);
        startActivity(intent);
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

    private void sendNotification(String uid, int sticker) {
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
                children.next();
                String name = children.next().getValue().toString();
                sendNotificationToPerson(uid, name, sticker);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotificationToPerson(String uid, String name, int sticker) {
        Intent intent = new Intent(this, FirebaseActivity.class);
        intent.putExtra("uid", uid);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT);
        }

        Intent intentForReply = new Intent(this, FirebaseActivity.class);
        intentForReply.putExtra("uid", uid);
        intentForReply.putExtra("fromNotification", true);
        intentForReply.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent checkIntent = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentForReply, PendingIntent.FLAG_MUTABLE);
        } else {
            PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intentForReply, PendingIntent.FLAG_ONE_SHOT);
        }


        String channelId = "id";
        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.team_47_icon_foreground)
                .setContentTitle(name)
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

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
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
        Date timestamp = new Date(Long.parseLong(snapshot.getKey()));
        Log.i("idk", timestamp.toString());
        Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
        String sender = children.next().getValue().toString();
        int sticker = Integer.parseInt(children.next().getValue().toString());
        String uid = children.next().getValue().toString();

        messageList.add(new Message(sender, sticker, uid, timestamp));
    }

    private void listenForMessageUpdates() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long existingChildrenCount = snapshot.getChildrenCount();
                final long[] cnt = {0};

                mRef.orderByKey().addChildEventListener(new ChildEventListener() {
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
                            String senderUid = children.next().getValue().toString();

                            if (!TextUtils.equals(msgSender, user.getEmail())) {
                                sendNotification(senderUid, sticker);
                            }

                            DatabaseReference ref = sRef.child(Integer.toString(sticker));
                            ref.setValue(ServerValue.increment(1));
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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

//        messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                long existingChildrenCount = snapshot.getChildrenCount();
//                final long[] cnt = {0};
//
//                messageRef.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        cnt[0]++;
//
//                        updateMessagesMap(snapshot);
//                        recyclerAdapter.notifyItemInserted(messageList.size());
//                        recyclerView.scrollToPosition(messageList.size() - 1);
//
//                        if (cnt[0] == existingChildrenCount && getIntent().hasExtra("fromNotification")) {
//                            startChooseStickerActivity();
//                        } else if (cnt[0] > existingChildrenCount) {
//                            Iterator<DataSnapshot> children = snapshot.getChildren().iterator();
//                            String msgSender = children.next().getValue().toString();
//                            int sticker = Integer.parseInt(children.next().getValue().toString());
//
//                            if (!TextUtils.equals(msgSender, user.getEmail())) {
//                                sendNotification(sticker);
//                            }
//
//                            DatabaseReference ref = stickerRef.child(user.getUid()).child(Integer.toString(sticker));
//                            ref.setValue(ServerValue.increment(1));
//                        }
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        Log.d("onChildChanged", "called");
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                        // delete from messages map and update screen
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}