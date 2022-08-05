package com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActivityData {

    @SerializedName("Activities")
    private Activities Activities;

    private String CreatedOn;


    public Activities getActivities() {
        return Activities;
    }

    public void setActivities(Activities activities) {
        Activities = activities;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
