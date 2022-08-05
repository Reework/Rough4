package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleReminderRequest
{
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("Activity")
    @Expose
    private String activity;
    @SerializedName("Frequency")
    @Expose
    private Integer frequency;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("ReminderTime")
    @Expose
    private List<String> reminderTime = null;

    public List<String> getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(List<String> reminderTime) {
        this.reminderTime = reminderTime;
    }

    @SerializedName("ActivityType")
    @Expose
    private String ActivityType;

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
