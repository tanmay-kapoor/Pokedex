package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.northeastern.pokedex.assignment7.pokemonSearch.ChoosePokemonActivity;
import edu.northeastern.pokedex.userRV.UserListActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void startPokemonChooseActivity(View view) {
        startActivity(new Intent(MainActivity.this, ChoosePokemonActivity.class));
    }

    public void startFirebaseActivity(View view) {
        FirebaseAuth.getInstance().signOut();
        user = mAuth.getCurrentUser();
        if(user == null) {
            startLoginActivity();
        } else {
            startFirebaseActivity();
        }
    }

    private void startLoginActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void startFirebaseActivity() {
//        startActivity(new Intent(MainActivity.this, FirebaseActivity.class));
        startActivity(new Intent(MainActivity.this, UserListActivity.class));
    }
}