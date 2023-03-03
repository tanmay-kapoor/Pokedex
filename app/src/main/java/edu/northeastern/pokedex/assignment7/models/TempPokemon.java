package edu.northeastern.pokedex.assignment7.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class TempPokemon implements Parcelable {
    String name;
    String url;
    int id;
    String bitmapStr;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getBitmapStr() {
        return bitmapStr;
    }

    public TempPokemon(Pokemon p) {
        this.name = p.getName();
        this.url = p.getUrl();
        this.id = p.getId();

        Bitmap bitmap = p.getImageBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        this.bitmapStr = Base64.encodeToString(b, Base64.DEFAULT);
    }

    protected TempPokemon(Parcel in) {
        name = in.readString();
        url = in.readString();
        id = in.readInt();
        bitmapStr = in.readString();
    }

    public static final Creator<TempPokemon> CREATOR = new Creator<TempPokemon>() {
        @Override
        public TempPokemon createFromParcel(Parcel in) {
            return new TempPokemon(in);
        }

        @Override
        public TempPokemon[] newArray(int size) {
            return new TempPokemon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt(id);
        dest.writeString(bitmapStr);
    }
}
