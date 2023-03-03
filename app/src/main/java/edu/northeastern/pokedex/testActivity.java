package edu.northeastern.pokedex;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class testActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        System.out.println();
        DatabaseReference myRef = db.getReference("message");
        myRef.setValue("At test Activity class!");

        User user = new User("Sam", "Wilson");
        db.getReference("users").setValue(user);

        db.getReference("users").child("username").setValue("Supra");


    }





    public class User {

        public String username;
        public String email;

        public User() {
            username = "";
            email = "";
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

    }
}
