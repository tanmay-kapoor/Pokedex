package edu.northeastern.pokedex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    public void createAccount(View view) {
        EditText emailEditText = findViewById(R.id.username);
        String email = emailEditText.getText().toString();
        String password = "password";

        EditText nameEditText = findViewById(R.id.name);
        String name = nameEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Welcome " + name + "!", Toast.LENGTH_SHORT).show();
                    addNameToUserProfile(name);
                } else {
                    Toast.makeText(SignupActivity.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addNameToUserProfile(String name) {
        FirebaseUser user = mAuth.getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        assert user != null;
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(SignupActivity.this, FirebaseActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}