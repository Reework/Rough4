package com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;

import java.util.ArrayList;

public class ClsNewHealthParamData {
    private String Message;

    @SerializedName("Data")
    private ArrayList<HealthParamData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<HealthParamData> getData() {
        return Data;
    }

    public void setData(ArrayList<HealthParamData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
