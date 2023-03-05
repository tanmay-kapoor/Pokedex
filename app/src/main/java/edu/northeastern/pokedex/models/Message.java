package edu.northeastern.pokedex.models;

public class Message {
    private String sender;
    private int sticker;
    private String uid;

    public Message(String sender, int sticker, String uid) {
        this.sender = sender;
        this.sticker = sticker;
        this.uid = uid;
    }

    public String getSender() {
        return this.sender;
    }

    public int getSticker() {
        return this.sticker;
    }

    public String getUid() {
        return this.uid;
    }

//    public void setSticker(int sticker) {
//        this.sticker = sticker;
//    }
//
//    public void setSender(String sender) {
//        this.sender = sender;
//    }

}
