package com.shamrock.reework.activity.aNewInterpretation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsReescoreMain implements Serializable {
    private String Message;

    @SerializedName("Data")
    private ReescoreData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ReescoreData getData() {
        return Data;
    }

    public void setData(ReescoreData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
