package com.vivek.alisha.model;

/**
 * Created by Vivek on 20-09-2017.
 */

public class ChatModel {

    private String chat;
    private int user;

    public ChatModel(String chat, int user) {

        this.chat = chat;
        this.user = user;
    }

    public String getChat() {
        return chat;
    }

    public int getUser() {
        return user;
    }

}
