package edu.northeastern.pokedex.userRV;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.models.User;

public class UserListActivity extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = mDatabase.child("users");
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<User> userList;

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.user_list);

        userList = new ArrayList<>();

        getAllUsers();
        init();
    }

    private void init() {
        recyclerView();
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
                    Iterator<DataSnapshot> children = dsp.getChildren().iterator();
                    String email = children.next().getValue().toString();
                    Log.i("User", email);
                    String name = children.next().getValue().toString();
                    userList.add(new User(email, name));
                    recyclerAdapter.notifyItemInserted(userList.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
