package com.shamrock.reework.activity.AnalysisModule.activity.water;

import java.util.ArrayList;

public class WaterHistoryData {

    private ArrayList<WaterDurations> WaterDurations;

    private double ScheduledWaterIntake;

    private double TotalWaterIntake;

    private String EntryDate;

    public ArrayList<com.shamrock.reework.activity.AnalysisModule.activity.water.WaterDurations> getWaterDurations() {
        return WaterDurations;
    }

    public void setWaterDurations(ArrayList<com.shamrock.reework.activity.AnalysisModule.activity.water.WaterDurations> waterDurations) {
        WaterDurations = waterDurations;
    }

    public double getScheduledWaterIntake() {
        return ScheduledWaterIntake;
    }

    public void setScheduledWaterIntake(double scheduledWaterIntake) {
        ScheduledWaterIntake = scheduledWaterIntake;
    }

    public double getTotalWaterIntake() {
        return TotalWaterIntake;
    }

    public void setTotalWaterIntake(double totalWaterIntake) {
        TotalWaterIntake = totalWaterIntake;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }
}
