package com.shamrock.reework.activity.newrecipe;

import com.google.gson.annotations.SerializedName;

public class ClsAllRecipeMainClass {
    private String Message;

    @SerializedName("Data")
    private ClsAllRecipeData Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ClsAllRecipeData getData() {
        return Data;
    }

    public void setData(ClsAllRecipeData data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
