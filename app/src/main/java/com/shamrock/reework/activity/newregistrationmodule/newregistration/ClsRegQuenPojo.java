package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsRegQuenPojo {

    private String Message;
@SerializedName("Data")
    private ArrayList<RegistrationQuestion> Data;

    private String Code;
    private String TcUrl;

    public String getTcUrl() {
        return TcUrl;
    }

    public void setTcUrl(String tcUrl) {
        TcUrl = tcUrl;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<RegistrationQuestion> getData() {
        return Data;
    }

    public void setData(ArrayList<RegistrationQuestion> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
