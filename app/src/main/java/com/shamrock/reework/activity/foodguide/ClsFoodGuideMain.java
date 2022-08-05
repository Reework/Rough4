package com.shamrock.reework.activity.foodguide;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterHistoryData;

import java.util.ArrayList;

public class ClsFoodGuideMain {
    private String Message;
    private ArrayList<FoodGuidePojo> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<FoodGuidePojo> getData() {
        return Data;
    }

    public void setData(ArrayList<FoodGuidePojo> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
