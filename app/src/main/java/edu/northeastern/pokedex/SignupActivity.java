package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.pokedex.userRV.UserListActivity;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Sticker Chat");
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void login(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    public void createAccount(View view) {
        EditText emailEditText = findViewById(R.id.email);
        String email = emailEditText.getText().toString().trim();
        String password = Utils.getDefaultPassword();

        EditText nameEditText = findViewById(R.id.name);
        String name = nameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            emailEditText.setError("Name cannot be empty");
        } else if (TextUtils.isEmpty(email)) {
            nameEditText.setError("Email cannot be empty");
        } else if (!Utils.isValidEmail(email)) {
            emailEditText.setError("Invalid email format");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Welcome " + name + "!", Toast.LENGTH_SHORT).show();
                    addNameToUserProfile(name);
                } else {
                    Toast.makeText(SignupActivity.this, "This email is in use already!", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                        DatabaseReference d = userRef.child(user.getUid());
                        d.child("email").setValue(user.getEmail());
                        d.child("name").setValue(user.getDisplayName());
//                        startActivity(new Intent(SignupActivity.this, FirebaseActivity.class));
                        startActivity(new Intent(SignupActivity.this, UserListActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}