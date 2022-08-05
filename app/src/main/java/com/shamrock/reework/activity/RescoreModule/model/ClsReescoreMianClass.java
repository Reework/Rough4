package com.shamrock.reework.activity.RescoreModule.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClsReescoreMianClass implements Serializable {
    private String Message;

    private ArrayList<RescoreData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<RescoreData> getData() {
        return Data;
    }

    public void setData(ArrayList<RescoreData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
