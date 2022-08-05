package com.shamrock.reework.activity.unfreezeData;

import com.google.gson.annotations.SerializedName;

public class ClsUnfreezeDataMain {
    private int code;
    private String Message;
    @SerializedName("Data")
    private ClsUnfreezeData Data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ClsUnfreezeData getData() {
        return Data;
    }

    public void setData(ClsUnfreezeData data) {
        Data = data;
    }
}
