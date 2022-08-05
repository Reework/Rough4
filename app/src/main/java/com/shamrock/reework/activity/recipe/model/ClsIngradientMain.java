package com.shamrock.reework.activity.recipe.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsIngradientMain {

    private String Message;

    @SerializedName("Data")
    private ArrayList<IngradientData> Data;

    private String Code;



    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<IngradientData> getData() {
        return Data;
    }

    public void setData(ArrayList<IngradientData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
