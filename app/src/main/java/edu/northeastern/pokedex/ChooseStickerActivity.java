package edu.northeastern.pokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.northeastern.pokedex.models.Message;

public class ChooseStickerActivity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuth.getCurrentUser();
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private final DatabaseReference messageRef = mDatabase.child("room1").child("messages");


    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.sticker_popup);

        LinearLayout ll = findViewById(R.id.LL);
        ImageView iv0 = findViewById(R.id.iv0);
        iv0.setOnClickListener(view -> sendMessage(R.drawable.smile));
        ImageView iv1 = findViewById(R.id.iv1);
        iv1.setOnClickListener(view -> sendMessage(R.drawable.laugh));
        ImageView iv2 = findViewById(R.id.iv2);
        iv2.setOnClickListener(view -> sendMessage(R.drawable.dead_laugh));
        ImageView iv3 = findViewById(R.id.iv3);
        iv3.setOnClickListener(view -> sendMessage(R.drawable.battered));
        ImageView iv4 = findViewById(R.id.iv4);
        iv4.setOnClickListener(view -> sendMessage(R.drawable.speculate));
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
