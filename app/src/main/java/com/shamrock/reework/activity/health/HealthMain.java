package com.shamrock.reework.activity.health;

import java.util.ArrayList;

public class HealthMain {
    private String Message;

    private ArrayList<Data> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<com.shamrock.reework.activity.health.Data> getData() {
        return Data;
    }

    public void setData(ArrayList<com.shamrock.reework.activity.health.Data> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
