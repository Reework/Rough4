package com.shamrock.reework.activity.WeightModule.adapters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsWeightPojo {
    private String Message;

    @SerializedName("Data")
    private ArrayList<WeightHistory> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<WeightHistory> getData() {
        return Data;
    }

    public void setData(ArrayList<WeightHistory> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
