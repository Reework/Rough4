package com.shamrock.reework.activity.AnalysisModule.activity.food;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.SleepNapData;

import java.util.ArrayList;

public class ClsMainFoodNap {

    private String Message;
    @SerializedName("Data")
    private ArrayList<FoodNapData> Data;
    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<FoodNapData> getData() {
        return Data;
    }

    public void setData(ArrayList<FoodNapData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
