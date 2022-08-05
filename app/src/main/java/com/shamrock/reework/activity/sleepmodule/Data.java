package com.shamrock.reework.activity.sleepmodule;

import java.util.ArrayList;

public class Data {
      private String StatusDate;;

      private ArrayList<SleepActivities> SleepActivities;

    public String getStatusDate() {
        return StatusDate;
    }

    public void setStatusDate(String statusDate) {
        StatusDate = statusDate;
    }

    public ArrayList<com.shamrock.reework.activity.sleepmodule.SleepActivities> getSleepActivities() {
        return SleepActivities;
    }

    public void setSleepActivities(ArrayList<com.shamrock.reework.activity.sleepmodule.SleepActivities> sleepActivities) {
        SleepActivities = sleepActivities;
    }
}
