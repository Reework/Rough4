package com.shamrock.reework.activity.HealthSupportModule.modal;

import com.google.gson.annotations.SerializedName;

public class HealthSupportClassMain {

    private String Message;

    @SerializedName("Data")
    private HealthSupportData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public HealthSupportData getData() {
        return Data;
    }

    public void setData(HealthSupportData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
