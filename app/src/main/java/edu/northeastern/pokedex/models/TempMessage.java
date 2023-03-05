package edu.northeastern.pokedex.models;

public class TempMessage {
    private String sender;
    private int sticker;
    private String uid;

    public TempMessage(Message message) {
        this.sender = message.getSender();
        this.sticker = message.getSticker();
        this.uid = message.getUid();
    }

    public String getSender() {
        return sender;
    }

    public int getSticker() {
        return sticker;
    }

    public String getUid() {
        return uid;
    }
}
