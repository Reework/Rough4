package com.shamrock.reework.activity.MyPlansModule.model;

import java.util.ArrayList;

public class GlosaryMainClass
{
    private String Message;

    private ArrayList<GlosaryData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<GlosaryData> getData() {
        return Data;
    }

    public void setData(ArrayList<GlosaryData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
