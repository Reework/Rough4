package com.shamrock.reework.activity.HomeModule.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsLogoutMsg {

    int Code;
    String Message;

    @SerializedName("Data")
    Logoutdata Data;

    public Logoutdata getData() {
        return Data;
    }

    public void setData(Logoutdata data) {
        Data = data;
    }

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












}
