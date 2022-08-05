package com.shamrock.reework.razerpaypsyment;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.payment.RazerData;

public class CreatePaymentResonse {
    private String Message;

    @SerializedName("Data")
    private RazerData Data;

    private String Code;

    private boolean PaymentDone;

    public boolean isPaymentDone() {
        return PaymentDone;
    }

    public void setPaymentDone(boolean paymentDone) {
        PaymentDone = paymentDone;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public RazerData getData() {
        return Data;
    }

    public void setData(RazerData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
