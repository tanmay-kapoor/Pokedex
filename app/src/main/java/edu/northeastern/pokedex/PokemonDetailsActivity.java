package edu.northeastern.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.northeastern.pokedex.models.Pokemon;
import edu.northeastern.pokedex.utils.NetworkUtil;
public class PokemonDetailsActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private TextView pokemonNameText;
    private TextView moveCountValue;
    private TextView pokemonTypeList;
    private ImageView pokemonImageView;

    private List<String> pokemonType;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        pokemonNameText = findViewById(R.id.PokemonName);
        moveCountValue = findViewById(R.id.MoveCountValue);
        pokemonTypeList = findViewById(R.id.TypeValues);
        pokemonImageView = findViewById(R.id.ImageView);

        if(savedInstanceState != null) {
            pokemonNameText.setText(savedInstanceState.getString("pokemonName"));
            moveCountValue.setText(savedInstanceState.getString("moveCount"));

            pokemonType = savedInstanceState.getStringArrayList("typeList");
            pokemonTypeList.setText(String.join(", ", pokemonType));

            imageBitmap = savedInstanceState.getParcelable("imageBitmap");
            pokemonImageView.setImageBitmap(imageBitmap);
        } else {
            Bundle extras = getIntent().getExtras();
            int id = extras.getInt("pokeID");
            Thread pokemonDetails = new Thread(new PokemonDetails(id));
            pokemonDetails.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("pokemonName", pokemonNameText.getText().toString());
        outState.putString("moveCount", moveCountValue.getText().toString());
        outState.putStringArrayList("typeList", (ArrayList<String>) pokemonType);
        outState.putParcelable("imageBitmap", imageBitmap);
        super.onSaveInstanceState(outState);
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
                JSONObject data = new JSONObject(res);

                // get move count
                int moveCount = data.getJSONArray("moves").length();

                // get list of pokemon types
                pokemonType = new ArrayList<>();
                JSONArray types = data.getJSONArray("types");
                for(int i = 0; i<types.length(); i++) {
                    String name = types.getJSONObject(i).getJSONObject("type").getString("name");
                    String ch = name.charAt(0)+"";
                    pokemonType.add(name.replace(ch, ch.toUpperCase(Locale.ROOT)));
                }

                // get pokemon name
                String name = data.getString("name");
                String ch = name.charAt(0)+"";
                final String pokemonName = name.replace(ch, ch.toUpperCase(Locale.ROOT));

                // get image bitmap
                String imageLink = data.getJSONObject("sprites").getString("front_default");
                URL urlConnection = new URL(imageLink);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                imageBitmap = BitmapFactory.decodeStream(input);

                // update on UI thread
                handler.post(() -> {
                    pokemonNameText.setText(pokemonName);
                    moveCountValue.setText(Integer.toString(moveCount));
                    pokemonTypeList.setText(String.join(", ", pokemonType));
                    pokemonImageView.setImageBitmap(imageBitmap);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
}