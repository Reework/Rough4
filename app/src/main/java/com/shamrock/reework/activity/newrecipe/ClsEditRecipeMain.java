package com.shamrock.reework.activity.newrecipe;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClsEditRecipeMain implements Serializable {
    private String Message;

    @SerializedName("Data")
    private EditRecipeData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public EditRecipeData getData() {
        return Data;
    }

    public void setData(EditRecipeData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
