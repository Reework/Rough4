package com.shamrock.reework.activity.dietplan.pojo;

import com.google.gson.annotations.SerializedName;

public class RDPFoodPlanMain {
    private String Message;

    @SerializedName("Data")
    private FoodPlanData Data;

    private String Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public FoodPlanData getData() {
        return Data;
    }

    public void setData(FoodPlanData data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
