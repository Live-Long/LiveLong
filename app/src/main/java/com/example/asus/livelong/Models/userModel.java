package com.example.asus.livelong.Models;

public class userModel {

    public String message;
    public boolean isSend;

    public userModel(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public userModel()
    {


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}

