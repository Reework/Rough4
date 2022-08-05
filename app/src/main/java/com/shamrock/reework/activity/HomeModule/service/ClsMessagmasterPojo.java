package com.shamrock.reework.activity.HomeModule.service;

import com.google.gson.annotations.SerializedName;

public class ClsMessagmasterPojo {

    private String Message;

    @SerializedName("Data")
    private MeassageMasterData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public MeassageMasterData getData() {
        return Data;
    }

    public void setData(MeassageMasterData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
