package edu.northeastern.pokedex.models;

import android.graphics.Bitmap;
import android.media.Image;

import java.net.URL;
import java.util.List;

public class Pokemon {
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
}
