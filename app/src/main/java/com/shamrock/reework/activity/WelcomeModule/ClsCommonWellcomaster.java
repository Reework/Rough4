package com.shamrock.reework.activity.WelcomeModule;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HomeModule.service.MeassageMasterData;

public class ClsCommonWellcomaster {
    private String Message;

    @SerializedName("Data")
    private WellcomeMasterData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public WellcomeMasterData getData() {
        return Data;
    }

    public void setData(WellcomeMasterData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
