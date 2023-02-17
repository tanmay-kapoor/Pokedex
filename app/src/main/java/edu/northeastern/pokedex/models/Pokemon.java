package edu.northeastern.pokedex.models;

import android.media.Image;

import java.net.URL;
import java.util.List;

public class Pokemon {

    public String getName() {
        return name;
    }

    public Integer getID() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public List<String> getTypes() {
        return types;
    }

    public Integer getMoveCount() {
        return moveCount;
    }

    public URL getSound() {
        return sound;
    }

    private final String name;
    private final Image image;
    private final List<String> types;
    private final Integer moveCount;
    private final URL sound;
    private final Integer id;

    public Pokemon(Integer id, String name, Image image, List<String> types, Integer moveCount, URL sound) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.types = types;
        this.moveCount = moveCount;
        this.sound = sound;
    }
}
