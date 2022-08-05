package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsDemoSleep {

    private String Message;
@SerializedName("Data")
    private ArrayList<SleepData> Data;

    private String Code;
    private double AvgSleepScore;

    private double AvgSleep;



    public double getAvgSleepScore() {
        return AvgSleepScore;
    }

    public void setAvgSleepScore(double avgSleepScore) {
        AvgSleepScore = avgSleepScore;
    }

    public double getAvgSleep() {
        return AvgSleep;
    }

    public void setAvgSleep(double avgSleep) {
        AvgSleep = avgSleep;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<SleepData> getData() {
        return Data;
    }

    public void setData(ArrayList<SleepData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
