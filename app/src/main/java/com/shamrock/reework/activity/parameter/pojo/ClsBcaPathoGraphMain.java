package com.shamrock.reework.activity.parameter.pojo;

import com.google.gson.annotations.SerializedName;

public class ClsBcaPathoGraphMain {
    private String Message;
    private int Code;
    @SerializedName("Data")
    private BCAPATHOData Data;
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public BCAPATHOData getData() {
        return Data;
    }

    public void setData(BCAPATHOData data) {
        Data = data;
    }
}
