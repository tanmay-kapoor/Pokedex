package edu.northeastern.pokedex.models;

public class Message {
    private String sender;
    private int sticker;

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

//    public void setSticker(int sticker) {
//        this.sticker = sticker;
//    }
//
//    public void setSender(String sender) {
//        this.sender = sender;
//    }

}
