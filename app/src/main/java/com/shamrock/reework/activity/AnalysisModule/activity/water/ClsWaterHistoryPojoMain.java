package com.shamrock.reework.activity.AnalysisModule.activity.water;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsWaterHistoryPojoMain {

    private String Message;
@SerializedName("Data")
    private ArrayList<WaterHistoryData> Data;

    private String Code;

    private String AvgWaterIntake;


    public String getAvgWaterIntake() {
        return AvgWaterIntake;
    }

    public void setAvgWaterIntake(String avgWaterIntake) {
        AvgWaterIntake = avgWaterIntake;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<WaterHistoryData> getData() {
        return Data;
    }

    public void setData(ArrayList<WaterHistoryData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
