package com.shamrock.reework.activity.BloodTestModule.pojo;

import com.shamrock.reework.activity.BloodTestModule.pojo.comparereport.ClsCompareData;

import java.util.ArrayList;

public class ClsReportCompareMain {
    private String Message;

    private ArrayList<ClsCompareData> Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ClsCompareData> getData() {
        return Data;
    }

    public void setData(ArrayList<ClsCompareData> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
