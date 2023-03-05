package edu.northeastern.pokedex.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message {
    private String sender;
    private int sticker;
    private String uid;

    private Date timestamp;



    public Message(String sender, int sticker, String uid, Date timestamp) {
        this.sender = sender;
        this.sticker = sticker;
        this.uid = uid;
        this.timestamp = timestamp;
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

    public String getDate() {
        return new SimpleDateFormat("MM-dd-yyyy").format(timestamp);
    }

    public String getTime() {
        return new SimpleDateFormat("K:mm a, z").format(timestamp);
    }

    public Date getTimestamp() {
        return timestamp;
    }

//    public void setSticker(int sticker) {
//        this.sticker = sticker;
//    }
//
//    public void setSender(String sender) {
//        this.sender = sender;
//    }

}
