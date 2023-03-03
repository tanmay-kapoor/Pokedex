package edu.northeastern.pokedex;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private SharedPreferences prefs;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getDefaultSharedPreferences(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createAccount(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }

    public void authenticate(View view) {
        EditText usernameText = findViewById(R.id.username);
        String username = usernameText.getText().toString();
        final String[] name = {null};

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean usernameExists = false;
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    if (dsp.child("username").getValue().toString().equals(username)) {
                        usernameExists = true;
                        name[0] = dsp.child("name").getValue().toString();
                        break;
                    }
                }

                if (usernameExists) {
                    SharedPreferences.Editor editor = prefs.edit();;
                    editor.putString("username", username);
                    editor.putString("name", name[0]);
                    // expiration time 30 minutes;
                    editor.putLong("expiration", (System.currentTimeMillis() + 1800000)/1000);
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, FirebaseActivity.class));
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