package com.shamrock.reework.activity.FoodModule.model.foodtripuser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsUSerFoodTripmain {
    private String Message;

    @SerializedName("Data")
    private ArrayList<UserFoodTripData> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<UserFoodTripData> getData() {
        return Data;
    }

    public void setData(ArrayList<UserFoodTripData> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
