package com.shamrock.reework.activity.ApplicationSnippetModule;


import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;

import java.util.ArrayList;

public class ClsSnippetPojo {

    private String Message;
    @SerializedName("Data")
    private ArrayList<SnippetData> Data;
    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<SnippetData> getData() {
        return Data;
    }

    public void setData(ArrayList<SnippetData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
