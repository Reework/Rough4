package com.shamrock.reework.activity.HomeModule.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsProfileHeaderData {

    private String Message;

    @SerializedName("Data")
    private ArrayList<ProfileHeaderData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ProfileHeaderData> getData() {
        return Data;
    }

    public void setData(ArrayList<ProfileHeaderData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
