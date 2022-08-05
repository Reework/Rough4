package com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.ReecoachHealthParamData;

import java.util.ArrayList;

public class ReecoachClsNewHealthParamData {
    private String Message;

    @SerializedName("Data")
    private ArrayList<ReecoachHealthParamData> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ReecoachHealthParamData> getData() {
        return Data;
    }

    public void setData(ArrayList<ReecoachHealthParamData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
