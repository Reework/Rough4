package com.shamrock.reework.activity.healthmonitoring;

import java.util.ArrayList;

public class ClsReemonitorHistory {
    private String Message;

    private ArrayList<HistoryReemonitorData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<HistoryReemonitorData> getData() {
        return Data;
    }

    public void setData(ArrayList<HistoryReemonitorData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
