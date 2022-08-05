package com.shamrock.reework.activity.FaqActivity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsFAQPojo {

    private String Message;

    @SerializedName("Data")
    private ArrayList<ClsFAQData> Data;

    private String Code;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsFAQData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsFAQData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
