package com.shamrock.reework.activity.activtyhistory;

import java.util.ArrayList;

public class ActivityData {
    private ArrayList<Activities> Activities;

    private String CreatedOn;

    public ArrayList<com.shamrock.reework.activity.activtyhistory.Activities> getActivities() {
        return Activities;
    }

    public void setActivities(ArrayList<com.shamrock.reework.activity.activtyhistory.Activities> activities) {
        Activities = activities;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
