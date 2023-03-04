package edu.northeastern.pokedex;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseStickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.sticker_popup);

        Log.i("Showing", "showing sticker popu menu");
    }

}
