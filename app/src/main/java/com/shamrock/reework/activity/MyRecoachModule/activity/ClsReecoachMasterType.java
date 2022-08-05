package com.shamrock.reework.activity.MyRecoachModule.activity;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class ClsReecoachMasterType implements Serializable {

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private ArrayList<ReecoachMasterTypeData> Data;

    public void setData(ArrayList<ReecoachMasterTypeData> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ReecoachMasterTypeData> getData() {
        return Data;
    }
}
