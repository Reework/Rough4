package com.shamrock.reework.activity.ReminderModule.model;

import java.util.ArrayList;

public class ClsReminderMaster {
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
