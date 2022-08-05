package com.shamrock.reework.model.MasterActivty;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private ArrayList<Activities> Activities;

    private ArrayList<SubActivities> SubActivities;

    public ArrayList<com.shamrock.reework.model.MasterActivty.Activities> getActivities() {
        return Activities;
    }

    public void setActivities(ArrayList<com.shamrock.reework.model.MasterActivty.Activities> activities) {
        Activities = activities;
    }

    public ArrayList<com.shamrock.reework.model.MasterActivty.SubActivities> getSubActivities() {
        return SubActivities;
    }

    public void setSubActivities(ArrayList<com.shamrock.reework.model.MasterActivty.SubActivities> subActivities) {
        SubActivities = subActivities;
    }
}
