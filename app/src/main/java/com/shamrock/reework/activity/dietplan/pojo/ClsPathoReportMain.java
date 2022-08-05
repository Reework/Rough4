package com.shamrock.reework.activity.dietplan.pojo;

import com.google.gson.annotations.SerializedName;

public class ClsPathoReportMain {
    private String Message;

    @SerializedName("Data")
    private ClspathoReportData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ClspathoReportData getData() {
        return Data;
    }

    public void setData(ClspathoReportData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
