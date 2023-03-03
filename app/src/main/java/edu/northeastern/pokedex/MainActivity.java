package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.northeastern.pokedex.assignment7.pokemonSearch.ChoosePokemonActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPokemonChooseActivity(View view) {
        startActivity(new Intent(MainActivity.this, ChoosePokemonActivity.class));
    }

    public void startFirebaseActivity(View view) {
        boolean loggedIn = false;
        if(loggedIn) {
            startActivity(new Intent(MainActivity.this, FirebaseActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}