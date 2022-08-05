package com.shamrock.reework.payment;

import com.google.gson.annotations.SerializedName;

public class MerhantDataResponse {
    int code;
    String Message;
    @SerializedName("Data")
    ClsMerchantData Data;

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

    public ClsMerchantData getData() {
        return Data;
    }

    public void setData(ClsMerchantData data) {
        Data = data;
    }
}
