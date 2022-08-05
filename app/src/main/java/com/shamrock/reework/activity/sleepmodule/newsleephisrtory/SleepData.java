package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

import java.util.ArrayList;

public class SleepData {
    private String SleepEntryDate;

    private ArrayList<SleepDurations> SleepDurations;

    private String TotalHours;

    public String getSleepEntryDate() {
        return SleepEntryDate;
    }

    public void setSleepEntryDate(String sleepEntryDate) {
        SleepEntryDate = sleepEntryDate;
    }

    public ArrayList<com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepDurations> getSleepDurations() {
        return SleepDurations;
    }

    public void setSleepDurations(ArrayList<com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepDurations> sleepDurations) {
        SleepDurations = sleepDurations;
    }

    public String getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(String totalHours) {
        TotalHours = totalHours;
    }
}
