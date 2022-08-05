package com.shamrock.reework.activity.reeworkerhealth.app;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;

import java.util.ArrayList;

public class ClsgetPostData {
    private String Message;

    @SerializedName("Data")
    private String Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
