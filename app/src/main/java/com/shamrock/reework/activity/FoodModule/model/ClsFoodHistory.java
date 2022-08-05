package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsFoodHistory {

    private String Message;
//    private String Data;

    @SerializedName("Data")
    private ArrayList<FoodData> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<FoodData> getData() {
        return Data;
    }

    public void setData(ArrayList<FoodData> data) {
        Data = data;
    }


//    public List<FoodData> getData() {
//        return Data;
//    }
//
//    public void setData(List<FoodData> data) {
//        Data = data;
//    }




    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
