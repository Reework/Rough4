package com.shamrock.reework.activity.AnalysisModule.activity.sleep;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsSleepAnalysis {
    private String Message;

    @SerializedName("Data")
    private ArrayList<SleepAnaysisData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<SleepAnaysisData> getData() {
        return Data;
    }

    public void setData(ArrayList<SleepAnaysisData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
