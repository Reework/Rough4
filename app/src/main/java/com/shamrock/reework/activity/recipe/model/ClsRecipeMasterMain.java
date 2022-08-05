package com.shamrock.reework.activity.recipe.model;

import com.google.gson.annotations.SerializedName;

public class ClsRecipeMasterMain {
    private String Message;

    @SerializedName("Data")
    private RecipeMasterData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public RecipeMasterData getData() {
        return Data;
    }

    public void setData(RecipeMasterData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
