package com.shamrock.reework.activity.FoodModule.model.repeatdata;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsRepeatTodaysMeal {

    private String Message;

    @SerializedName("data")
    private ArrayList<ClsRepeatMealData> data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public ArrayList<ClsRepeatMealData> getData() {
        return data;
    }

    public void setData(ArrayList<ClsRepeatMealData> data) {
        this.data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
