package com.shamrock.reework.activity.AnalysisModule.activity.sleepNap;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.Activities;

import java.util.ArrayList;

public class SleepNapData {

    @SerializedName("Naps")
    private Naps Naps;

    private String CreatedOn;

    public Naps getNaps() {
        return Naps;
    }

    public void setNaps(Naps naps) {
        Naps = naps;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
