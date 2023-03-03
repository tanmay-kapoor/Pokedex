package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void createAccount(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void authenticate(View view) {
        // check this username exists or not in db
        // add username to shared pref
        // go to messaging activity
    }
}