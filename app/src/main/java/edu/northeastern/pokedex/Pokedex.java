package edu.northeastern.pokedex;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.northeastern.pokedex.utils.NetworkUtil;

public class Pokedex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Thread pokemons = new Thread(new Pokemons());
        pokemons.start();
    }

    private static class Pokemons implements Runnable {
        String link = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20";

        @Override
        public void run() {
            try {
                URL url = new URL(link);
                String res = NetworkUtil.httpResponse(url);
                Log.i("res", res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
