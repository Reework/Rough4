package com.shamrock.reework.activity.spirituallibrary.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsSpiritaulTypeMain {

    private String Message;

    @SerializedName("Data")
    private ArrayList<SpiritualTypeData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<SpiritualTypeData> getData() {
        return Data;
    }

    public void setData(ArrayList<SpiritualTypeData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
