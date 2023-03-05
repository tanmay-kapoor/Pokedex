package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Sticker Chat");
        mAuth = FirebaseAuth.getInstance();
    }

    public void createAccount(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }

    public void authenticate(View view) {
        EditText emailEditText = findViewById(R.id.email);
        String email = emailEditText.getText().toString().trim();
        String password = Utils.getDefaultPassword();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email cannot be empty");
        } else if (!Utils.isValidEmail(email)) {
            emailEditText.setError("Invalid email format");
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, FirebaseActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}