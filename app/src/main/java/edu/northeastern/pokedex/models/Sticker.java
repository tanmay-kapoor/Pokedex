package edu.northeastern.pokedex.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Sticker {
    private final String name;
    private final Integer count;
    private final Drawable image;

    public Sticker(String name, Integer count, Drawable image) {
        this.name = name;
        this.count = count;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public Drawable getImage() {
        return image;
    }
}
