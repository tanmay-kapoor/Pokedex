package edu.northeastern.pokedex.models;

import android.media.Image;

import java.net.URL;
import java.util.List;

public class Pokemon {
    private final String name;
    private final String url;
    private final Integer id;

    public Pokemon(String name, String url, Integer id) {
        this.name = name;
        this.url = url;
        this.id = id;
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

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }
}
