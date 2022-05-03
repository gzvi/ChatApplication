package com.example.chatapplication;

public class ChatMessage {
    private long dateInMillis;

    private String senderId;
    private String message;
    private String senderName;
    private String senderImageUrl;


    public ChatMessage(String message, String senderId, String senderName, String senderImageUrl) {
        this.dateInMillis = System.currentTimeMillis();
        this.senderId = senderId;
        this.message = message;
        this.senderName = senderName;
        this.senderImageUrl = senderImageUrl;
    }

    public ChatMessage() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(long dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }
}
