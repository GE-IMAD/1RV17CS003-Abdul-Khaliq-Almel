package com.example.chatbot_dailogflow;

public class ChatMessage {
    public String message;
    public int  k;

    public ChatMessage() {
        this.message = "nothing";
        this.k=0;
    }

    public ChatMessage(String message, int k) {
        this.message = message;
        this.k = k;
    }

    public String getMessage() {
        return message;
    }

    public int getK() {
        return k;
    }
}
