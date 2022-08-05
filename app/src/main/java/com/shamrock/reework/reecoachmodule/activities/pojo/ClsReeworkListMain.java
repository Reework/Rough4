package com.shamrock.reework.reecoachmodule.activities.pojo;

import java.util.ArrayList;

public class ClsReeworkListMain {
    private int Code;
    private String Message;
    private ArrayList<ClsReeeworklistData> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        this.Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsReeeworklistData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsReeeworklistData> data) {
        Data = data;
    }
}
