package com.shamrock.reework.activity.healthmonitoring;

import com.google.gson.annotations.SerializedName;

public class ClsHealthMonitorMain {
    private String Message;

@SerializedName("Data")
    private healthMonitorData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public healthMonitorData getData() {
        return Data;
    }

    public void setData(healthMonitorData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
