package edu.northeastern.pokedex.messaging;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;

import edu.northeastern.pokedex.ChooseStickerActivity;
import edu.northeastern.pokedex.MainActivity;
import edu.northeastern.pokedex.R;
import edu.northeastern.pokedex.assignment7.pokemonSearch.ChoosePokemonActivity;
import edu.northeastern.pokedex.models.Message;

public class MessageActivity extends AppCompatActivity {
    SharedPreferences prefs;
    private DatabaseReference mDatabase;

    Map<Long, Message> messages;

    @Override
    protected void onCreate(Bundle saveInstances) {
        super.onCreate(saveInstances);
        setContentView(R.layout.actvity_messaging);

        prefs = getDefaultSharedPreferences(getApplicationContext());
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }



}
