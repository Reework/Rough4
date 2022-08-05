package com.shamrock.reework.activity.MyRecoachModule.activity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsSetReecoach {
    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private String Data;


    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
