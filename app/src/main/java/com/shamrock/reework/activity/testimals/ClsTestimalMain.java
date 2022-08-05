package com.shamrock.reework.activity.testimals;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsTestimalMain {
    private String Message;

@SerializedName("Data")
    private ArrayList<TestimalDataClass> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<TestimalDataClass> getData() {
        return Data;
    }

    public void setData(ArrayList<TestimalDataClass> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
