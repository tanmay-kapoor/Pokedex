package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.northeastern.pokedex.pokemonSearch.ChoosePokemonActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button atYourServiceBtn = findViewById(R.id.AtYourService);
        atYourServiceBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChoosePokemonActivity.class);
            startActivity(intent);
        });


    }
}