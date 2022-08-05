package com.shamrock.reework.activity.community;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsGetCommunityChatMain {

    @SerializedName("Data")
    private ArrayList<GetCommunityData> Data;

    private int Code;

    private String Mesage;

    public ArrayList<GetCommunityData> getData() {
        return Data;
    }

    public void setData(ArrayList<GetCommunityData> data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMesage() {
        return Mesage;
    }

    public void setMesage(String mesage) {
        Mesage = mesage;
    }
}
