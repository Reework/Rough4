package com.shamrock.reework.activity.AnalysisModule.activity.water;

public class WaterData {
    private String CreatedOn;
    private double ActualWaterIntake;
    private double ScheduledWaterIntake;

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public double getActualWaterIntake() {
        return ActualWaterIntake;
    }

    public void setActualWaterIntake(double actualWaterIntake) {
        ActualWaterIntake = actualWaterIntake;
    }

    public double getScheduledWaterIntake() {
        return ScheduledWaterIntake;
    }

    public void setScheduledWaterIntake(double scheduledWaterIntake) {
        ScheduledWaterIntake = scheduledWaterIntake;
    }
}
