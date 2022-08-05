package com.shamrock.reework.activity.WeightModule.activity;


import java.util.ArrayList;

public class ClsWtHistoryResponse {

    private String Message;

    private ArrayList<Data> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<Data> getData() {
        return Data;
    }

    public void setData(ArrayList<Data> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
