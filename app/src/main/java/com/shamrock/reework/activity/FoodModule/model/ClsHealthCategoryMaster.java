package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsHealthCategoryMaster {
    private int Code;
    private String Message;
    @SerializedName("Data")
    private ArrayList<HealthCatogoryData> Data;

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

    public ArrayList<HealthCatogoryData> getData() {
        return Data;
    }

    public void setData(ArrayList<HealthCatogoryData> data) {
        Data = data;
    }
}
