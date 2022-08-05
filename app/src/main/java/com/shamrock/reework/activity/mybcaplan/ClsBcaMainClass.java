package com.shamrock.reework.activity.mybcaplan;

import java.io.Serializable;
import java.util.ArrayList;

public class ClsBcaMainClass implements Serializable {
    private String Message;


    private ArrayList<ClsBcaData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsBcaData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsBcaData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
