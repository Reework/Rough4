package com.shamrock.reework.activity.testimals;

import com.google.gson.annotations.SerializedName;

public class ClsMyTestimonialsMain {

    private String Message;

    @SerializedName("Data")
    private MyTestominalsData Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public MyTestominalsData getData() {
        return Data;
    }

    public void setData(MyTestominalsData data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
