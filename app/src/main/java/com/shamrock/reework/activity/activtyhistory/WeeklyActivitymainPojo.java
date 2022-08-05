package com.shamrock.reework.activity.activtyhistory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeeklyActivitymainPojo {
    private String Message;

    @SerializedName("Data")
    private ArrayList<WeeklyActivityData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<WeeklyActivityData> getData() {
        return Data;
    }

    public void setData(ArrayList<WeeklyActivityData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
