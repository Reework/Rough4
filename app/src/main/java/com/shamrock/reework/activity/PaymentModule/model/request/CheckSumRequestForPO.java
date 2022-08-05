package com.shamrock.reework.activity.PaymentModule.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckSumRequestForPO {

    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("orderId")
    @Expose
    private String orderId;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
