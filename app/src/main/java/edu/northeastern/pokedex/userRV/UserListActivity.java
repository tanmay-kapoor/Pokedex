package edu.northeastern.pokedex.userRV;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.northeastern.pokedex.FirebaseActivity;
import edu.northeastern.pokedex.MainActivity;
import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.StickerStatsActivity;
import edu.northeastern.pokedex.assignment7.models.TempPokemon;
import edu.northeastern.pokedex.models.ParcelableUser;
import edu.northeastern.pokedex.models.User;

public class UserListActivity extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = mDatabase.child("users");
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<User> userList;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.user_list);
        init(savedInstances);
    }

    private void init(Bundle savedInstances) {
        userList = new ArrayList<>();
        if(savedInstances != null) {
            if(savedInstances.containsKey("current_user")
            && savedInstances.containsKey("size")) {
                user = savedInstances.getParcelable("current_user");
                int size = savedInstances.getInt("size");
                for (int i = 0; i < size; i++) {
                    ParcelableUser temp = savedInstances.getParcelable("user_"+ i);
                    User user = new User(temp.getEmail(), temp.getName(), temp.getUid());
                    userList.add(user);
                }
            }
        }
        else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            getAllUsers();
        }
        recyclerView();

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
                intent = new Intent(getApplicationContext(), MainActivity.class);
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

    private void recyclerView() {
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.userListRV);
        recyclerView.setHasFixedSize(true);

        recyclerAdapter = new RecyclerAdapter(userList, getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(recyclerLayoutManager);
    }

    private void getAllUsers() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp : snapshot.getChildren()) {
                    String uid = dsp.getKey();
                    if(user.getUid().equals(uid))
                        continue;
                    Iterator<DataSnapshot> children = dsp.getChildren().iterator();
                    String email = children.next().getValue().toString();
                    Log.i("User", email);
                    String name = children.next().getValue().toString();
                    userList.add(new User(email, name, uid));
                    recyclerAdapter.notifyItemInserted(userList.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("current_user", user);
        outState.putInt("size", userList.size());
        for (int i = 0; i < userList.size(); i++) {
            ParcelableUser user = new ParcelableUser(userList.get(i));
            outState.putParcelable("user_" + i, user);
        }
        super.onSaveInstanceState(outState);
    }
}
