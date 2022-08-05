package com.shamrock.reework.activity.sleepmodule.repeatSleep;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.sleepmodule.ClsRepeatSleepData;

import java.util.ArrayList;

public class ClsRepeatSleepMain {
    private int Code;
    private String Message;

    @SerializedName("Data")
    private ArrayList<ClsRepeatSleepData> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsRepeatSleepData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsRepeatSleepData> data) {
        Data = data;
    }
}
