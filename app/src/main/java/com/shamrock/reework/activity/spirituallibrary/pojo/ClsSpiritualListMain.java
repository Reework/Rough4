package com.shamrock.reework.activity.spirituallibrary.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsSpiritualListMain {
    private String Message;

    @SerializedName("Data")
    private ArrayList<ClsSpiritualData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsSpiritualData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsSpiritualData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
