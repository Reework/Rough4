package com.shamrock.reework.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsGadgetmainData {
    @SerializedName("Code")
    private int code;
    private String Message;
    @SerializedName("Data")
    private ArrayList<ClsGadgetData> Data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsGadgetData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsGadgetData> data) {
        Data = data;
    }
}
