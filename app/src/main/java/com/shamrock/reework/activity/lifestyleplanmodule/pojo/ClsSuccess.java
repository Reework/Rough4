package com.shamrock.reework.activity.lifestyleplanmodule.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsSuccess {
    private String Message;
@SerializedName("Data")
    private ArrayList<ClsNewData> Data;
    private String Code;

    public ArrayList<ClsNewData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsNewData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
