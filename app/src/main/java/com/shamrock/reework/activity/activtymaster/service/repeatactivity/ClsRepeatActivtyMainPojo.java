package com.shamrock.reework.activity.activtymaster.service.repeatactivity;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import java.util.ArrayList;

public class ClsRepeatActivtyMainPojo {
    private String Message;

    @SerializedName("Data")
    private ArrayList<com.shamrock.reework.model.MasterActivty.AcivityHistory> Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<AcivityHistory> getData() {
        return Data;
    }

    public void setData(ArrayList<AcivityHistory> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private String Code;
}
