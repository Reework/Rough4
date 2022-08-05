package com.shamrock.reework.model.MasterActivty;

import com.shamrock.reework.model.Data;

public class MyActivtyHistroy {
    private String Message;

    private com.shamrock.reework.model.Data Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public com.shamrock.reework.model.Data getData() {
        return Data;
    }

    public void setData(com.shamrock.reework.model.Data data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
