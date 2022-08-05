package com.shamrock.reework.activity.newmyplan;

import com.google.gson.annotations.SerializedName;

public class ClsTodaysPlanNewMainPojo {
    private String Message;

    @SerializedName("Data")
    private MyPlanNewDataqClass Data;

    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public MyPlanNewDataqClass getData() {
        return Data;
    }

    public void setData(MyPlanNewDataqClass data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
