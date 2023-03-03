package edu.northeastern.pokedex;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.northeastern.pokedex.assignment7.pokemonSearch.ChoosePokemonActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getDefaultSharedPreferences(getApplicationContext());
    }

    public void startPokemonChooseActivity(View view) {
        startActivity(new Intent(MainActivity.this, ChoosePokemonActivity.class));
    }

    public void startFirebaseActivity(View view) {
        if(prefs.contains("username")) {
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.clear();
//            editor.apply();
            if(prefs.getLong("expiration", 0) <= (System.currentTimeMillis()/1000)) {
                clearSharedPrefs();
                startLoginActivity();
            } else {
                Toast.makeText(MainActivity.this,
                        "Welcome " + prefs.getString("name", "") + "!",
                        Toast.LENGTH_SHORT).show();
                startFirebaseActivity();
            }
        } else {
            startLoginActivity();
        }
    }

    private void clearSharedPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    private void startLoginActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void startFirebaseActivity() {
        startActivity(new Intent(MainActivity.this, FirebaseActivity.class));
    }
}