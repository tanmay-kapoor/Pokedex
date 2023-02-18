package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.northeastern.pokedex.models.Pokemon;
import edu.northeastern.pokedex.utils.NetworkUtil;

public class PokemonDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("pokeID");
        Thread pokemonDetails = new Thread(new PokemonDetails(id));
        pokemonDetails.start();
    }

    private class PokemonDetails implements Runnable {
        private final String link;

        PokemonDetails(int id) {
            link = "https://pokeapi.co/api/v2/pokemon/"+id;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(link);
                String res = NetworkUtil.httpResponse(url);
                Log.i("data", res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}