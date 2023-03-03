package edu.northeastern.pokedex;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.pokedex.models.User;

public class SignupActivity extends AppCompatActivity {

    SharedPreferences prefs;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        prefs = getDefaultSharedPreferences(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void login(View view){
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    public void createAccount(View view) {
        EditText usernameText = findViewById(R.id.username);
        String newUsername = usernameText.getText().toString();

        EditText nameText = findViewById(R.id.name);
        String newName = nameText.getText().toString();

        DatabaseReference userRef = mDatabase.child("users");
        userRef.child(newUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Toast.makeText(SignupActivity.this, newUsername + " username already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    userRef.child(newUsername).child("name").setValue(newName);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", newUsername);
                    editor.putString("name", newName);
                    // expiration time 30 minutes;
                    editor.putLong("expiration", (System.currentTimeMillis() + 1800000)/1000);
                    editor.apply();

                    Toast.makeText(SignupActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, FirebaseActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                boolean isUniqueUsername = true;
//                for (DataSnapshot dsp : snapshot.getChildren()) {
//                    String username = dsp.child("username").getValue().toString();
//                    if (username.equals(newUsername)) {
//                        isUniqueUsername = false;
//                        break;
//                    }
//                }
//                if (isUniqueUsername) {
//                    DatabaseReference newEntry = userRef.push();
//                    newEntry.child("username").setValue(newUsername);
//                    newEntry.child("name").setValue(newName);
//
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.putString("username", newUsername);
//                    editor.putString("name", newName);
//                    // expiration time 30 minutes;
//                    editor.putLong("expiration", (System.currentTimeMillis() + 1800000)/1000);
//                    editor.apply();
//
//                    Toast.makeText(SignupActivity.this, "Account created!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SignupActivity.this, FirebaseActivity.class));
//                    finish();
//                } else {
//                    Toast.makeText(SignupActivity.this, newUsername + " username already exists!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        // check username already exists or not
        // add username to firebase and shared pref
    }
}