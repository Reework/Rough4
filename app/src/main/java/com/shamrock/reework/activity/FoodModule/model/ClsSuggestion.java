package com.shamrock.reework.activity.FoodModule.model;

import java.util.ArrayList;

public class ClsSuggestion {

    private String Message;

    private ArrayList<Data> Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<com.shamrock.reework.activity.FoodModule.model.Data> getData() {
        return Data;
    }

    public void setData(ArrayList<com.shamrock.reework.activity.FoodModule.model.Data> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
