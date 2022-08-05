package com.shamrock.reework.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicineResponse
{

    /**
     * Code : 1
     * Message : Medicine list
     * Data : [{"MedicineID":5,"MedicineName":"Povidone lodine 5% w/w Ointment"},{"MedicineID":6,"MedicineName":"Povidone lodine 10% solution IP"}]
     */

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    private List<MedicineItem> Data;

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

    public List<MedicineItem> getData() {
        return Data;
    }

    public void setData(List<MedicineItem> Data) {
        this.Data = Data;
    }

}
