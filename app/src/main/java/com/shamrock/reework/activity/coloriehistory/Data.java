package com.shamrock.reework.activity.coloriehistory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {

    private String Message;

    private ArrayList<String> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<String> getData() {
        return Data;
    }

    public void setData(ArrayList<String> data) {
        Data = data;
    }
}
