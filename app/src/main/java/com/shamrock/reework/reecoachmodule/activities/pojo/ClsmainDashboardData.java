package com.shamrock.reework.reecoachmodule.activities.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsmainDashboardData implements Serializable {
    private String Message;

    @SerializedName("Data")
    private DashboardData Data;

    private int Code;

    public DashboardData getData() {
        return Data;
    }

    public void setData(DashboardData data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
