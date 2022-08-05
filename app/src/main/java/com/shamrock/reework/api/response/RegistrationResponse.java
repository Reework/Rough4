package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
    @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;

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
