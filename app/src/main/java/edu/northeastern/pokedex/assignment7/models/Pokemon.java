package edu.northeastern.pokedex.assignment7.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Pokemon implements Parcelable {
    private final String name;
    private final String url;
    private final Integer id;
    private final Bitmap imageBitmap;

    public Pokemon(String name, String url, Integer id, Bitmap imageBitmap) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.imageBitmap = imageBitmap;
    }

    protected Pokemon(Parcel in) {
        name = in.readString();
        url = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        imageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getId() {
        return id;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt(id);
        dest.writeValue(imageBitmap);
    }
}
