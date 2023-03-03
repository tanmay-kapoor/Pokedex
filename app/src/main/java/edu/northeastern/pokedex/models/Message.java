package edu.northeastern.pokedex.models;

public class Message {
    private final String sender;
    private final int sticker;

    public Message(String sender, int sticker) {
        this.sender = sender;
        this.sticker = sticker;
    }

    public String getSender() {
        return this.sender;
    }

    public int getSticker() {
        return this.sticker;
    }
}
