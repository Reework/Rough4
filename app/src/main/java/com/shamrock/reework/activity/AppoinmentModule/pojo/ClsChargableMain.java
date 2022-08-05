package com.shamrock.reework.activity.AppoinmentModule.pojo;

import java.util.ArrayList;

public class ClsChargableMain {
    private String Message;

    private ArrayList<GroupData> Data;

    private String Code;
    private int PathoVisitLeft;
    private int ReecoachVisitLeft;


    public int getPathoVisitLeft() {
        return PathoVisitLeft;
    }

    public void setPathoVisitLeft(int pathoVisitLeft) {
        PathoVisitLeft = pathoVisitLeft;
    }

    public int getReecoachVisitLeft() {
        return ReecoachVisitLeft;
    }

    public void setReecoachVisitLeft(int reecoachVisitLeft) {
        ReecoachVisitLeft = reecoachVisitLeft;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<GroupData> getData() {
        return Data;
    }

    public void setData(ArrayList<GroupData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
