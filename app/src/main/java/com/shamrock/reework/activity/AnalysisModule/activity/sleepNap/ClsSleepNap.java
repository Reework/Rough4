package com.shamrock.reework.activity.AnalysisModule.activity.sleepNap;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.ActivityData;

import java.util.ArrayList;

public class ClsSleepNap {

    private String Message;
    @SerializedName("Data")
    private ArrayList<SleepNapData> Data;
    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<SleepNapData> getData() {
        return Data;
    }

    public void setData(ArrayList<SleepNapData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
