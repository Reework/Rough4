package com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsActivityHistoryPojo {

    private String Message;

    @SerializedName("ActivityList")
    private ArrayList<String> ActivityList;

    @SerializedName("Data")
    private ArrayList<ActivityData> Data;

    private String Code;

    public ArrayList<String> getActivityList() {
        return ActivityList;
    }

    public void setActivityList(ArrayList<String> activityList) {
        ActivityList = activityList;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<ActivityData> getData() {
        return Data;
    }

    public void setData(ArrayList<ActivityData> data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
