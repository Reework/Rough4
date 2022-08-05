package com.shamrock.reework.activity.AnalysisModule.activity.water;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;

import java.util.ArrayList;

public class ClsWaterAnalyisPojo {

    private String Message;

    @SerializedName("Data")
    private ArrayList<WaterData> Data;

    private int Code;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<WaterData> getData() {
        return Data;
    }

    public void setData(ArrayList<WaterData> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
