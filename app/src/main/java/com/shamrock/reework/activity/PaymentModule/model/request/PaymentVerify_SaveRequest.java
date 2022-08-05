package com.shamrock.reework.activity.PaymentModule.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentVerify_SaveRequest {
    @SerializedName("ResponseStr")
    @Expose
    private String responseStr;
    @SerializedName("ReceivedChecksum")
    @Expose
    private String receivedChecksum;

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public String getReceivedChecksum() {
        return receivedChecksum;
    }

    public void setReceivedChecksum(String receivedChecksum) {
        this.receivedChecksum = receivedChecksum;
    }

}
