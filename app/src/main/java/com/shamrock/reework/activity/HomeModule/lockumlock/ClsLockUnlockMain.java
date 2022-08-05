package com.shamrock.reework.activity.HomeModule.lockumlock;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsLockUnlockMain {
    private String Message;

    @SerializedName("Data")
    private ArrayList<ClsLockUnlockData> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsLockUnlockData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsLockUnlockData> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
