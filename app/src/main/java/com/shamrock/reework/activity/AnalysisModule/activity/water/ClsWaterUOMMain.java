package com.shamrock.reework.activity.AnalysisModule.activity.water;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsWaterUOMMain {

    private String Message;

    @SerializedName("Data")
    private ArrayList<WaterUOmData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<WaterUOmData> getData() {
        return Data;
    }

    public void setData(ArrayList<WaterUOmData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
