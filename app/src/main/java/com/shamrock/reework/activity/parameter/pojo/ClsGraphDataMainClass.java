package com.shamrock.reework.activity.parameter.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsGraphDataMainClass {
    private String Message;
    private int Code;
    @SerializedName("Data")
    private ArrayList<GraphData> Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public ArrayList<GraphData> getData() {
        return Data;
    }

    public void setData(ArrayList<GraphData> data) {
        Data = data;
    }
}
