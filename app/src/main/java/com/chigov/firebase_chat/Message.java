package com.chigov.firebase_chat;

public class Message {
    private String author;
    private String messageText;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    private long date;

    //constructor with parameters
    public Message(String author, String messageText, long date) {
        this.author = author;
        this.messageText = messageText;
        this.date = date;
    }
    //constructor with no parameters
    public Message() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
