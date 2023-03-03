package edu.northeastern.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createAccount(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }

    public void authenticate(View view) {
        EditText usernameText = findViewById(R.id.username);
        String username = usernameText.getText().toString();

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean usernameExists = false;
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("username").getValue().toString().equals(username)) {
                        usernameExists = true;
                        break;
                    }
                }

                if (usernameExists) {
                    startActivity(new Intent(LoginActivity.this, FirebaseActivity.class));
                    // store in shred pref
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "This account does not exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}