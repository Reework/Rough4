package com.shamrock.reework.activity.cheatplan.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsOccastionMain {
    private String Message;
@SerializedName("Data")
    private ArrayList<OccasionData> Data;

    private String Code;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<OccasionData> getData() {
        return Data;
    }

    public void setData(ArrayList<OccasionData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
