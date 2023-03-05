package edu.northeastern.pokedex.models;

public class User {
    private final String email;
    private final String name;
    private final String uid;

    public User(String email, String name, String uid) {
        this.email = email;
        this.name = name;
        this.uid = uid;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getUid() {
        return this.uid;
    }
}
