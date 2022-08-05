package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

public class ReetestResponse
{

    /**
     * Code : 1
     * Message : Blood Test Schedule
     * Data : null
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private Object Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }
}
