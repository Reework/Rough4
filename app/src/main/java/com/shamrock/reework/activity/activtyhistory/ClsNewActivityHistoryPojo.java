package com.shamrock.reework.activity.activtyhistory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClsNewActivityHistoryPojo {
    private String Message;

    @SerializedName("Data")
    private ArrayList<ActivityData> ActivityData;

    private String Code;
    private String AvgActivityTime;

    public String getAvgActivityTime() {
        return AvgActivityTime;
    }

    public void setAvgActivityTime(String avgActivityTime) {
        AvgActivityTime = avgActivityTime;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<com.shamrock.reework.activity.activtyhistory.ActivityData> getActivityData() {
        return ActivityData;
    }

    public void setActivityData(ArrayList<com.shamrock.reework.activity.activtyhistory.ActivityData> activityData) {
        ActivityData = activityData;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
