package com.shamrock.reework.common;

import java.util.ArrayList;

public class ClsHistoryResponse {

    private String Message;

    private ArrayList<Data> Data;

    private String Code;

    private double AvgMoodScroe;

    public double getAvgMoodScroe() {
        return AvgMoodScroe;
    }

    public void setAvgMoodScroe(double avgMoodScroe) {
        AvgMoodScroe = avgMoodScroe;
    }

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
