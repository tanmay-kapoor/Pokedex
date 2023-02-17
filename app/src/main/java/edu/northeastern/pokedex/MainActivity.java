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

        Button aysBtn = findViewById(R.id.AtYourService);
        aysBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, Pokedex.class)));
    }
}