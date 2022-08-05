package com.shamrock.reework.activity.reecoach.postrate;

import java.util.ArrayList;
import java.util.Date;

public class ClsSucessRateMain {
    private String Message;

    private ArrayList<ClsSuccessDatarate> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsSuccessDatarate> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsSuccessDatarate> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
