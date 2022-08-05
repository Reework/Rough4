package com.shamrock.reework.activity.reecoach.adapters;

import com.google.gson.annotations.SerializedName;

public class ClsReecoachQnMain {
    private String Message;

    @SerializedName("Data")
    private ClsReecoachQnData Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ClsReecoachQnData getData() {
        return Data;
    }

    public void setData(ClsReecoachQnData data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
