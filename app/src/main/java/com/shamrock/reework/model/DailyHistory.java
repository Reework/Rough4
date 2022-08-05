package com.shamrock.reework.model;

import com.shamrock.reework.model.MasterActivty.AcivityHistory;

import java.util.ArrayList;

public class DailyHistory {
    private String WeeklyBurnedCalories;

    private ArrayList<AcivityHistory> AcivityHistory;

    public String getWeeklyBurnedCalories() {
        return WeeklyBurnedCalories;
    }

    public void setWeeklyBurnedCalories(String weeklyBurnedCalories) {
        WeeklyBurnedCalories = weeklyBurnedCalories;
    }

    public ArrayList<com.shamrock.reework.model.MasterActivty.AcivityHistory> getAcivityHistory() {
        return AcivityHistory;
    }

    public void setAcivityHistory(ArrayList<com.shamrock.reework.model.MasterActivty.AcivityHistory> acivityHistory) {
        AcivityHistory = acivityHistory;
    }
}
