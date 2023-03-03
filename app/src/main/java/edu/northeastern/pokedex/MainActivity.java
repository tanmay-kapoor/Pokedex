package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.northeastern.pokedex.pokemonSearch.ChoosePokemonActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button aysBtn = findViewById(R.id.AtYourService);
        aysBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ChoosePokemonActivity.class)));

        Button testBtn = findViewById(R.id.testBtn);
        testBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, testActivity.class)));

        Button cmBtn = findViewById(R.id.cmBtn);
        cmBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, cloudMessaging.class)));
    }
}