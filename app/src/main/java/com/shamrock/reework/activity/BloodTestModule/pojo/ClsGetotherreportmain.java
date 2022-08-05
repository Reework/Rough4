package com.shamrock.reework.activity.BloodTestModule.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsGetotherreportmain {
    private String Message;

    @SerializedName("Data")
    private ArrayList<OtherReportData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<OtherReportData> getData() {
        return Data;
    }

    public void setData(ArrayList<OtherReportData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
