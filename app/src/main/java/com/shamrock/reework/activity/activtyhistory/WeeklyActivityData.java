package com.shamrock.reework.activity.activtyhistory;

public class WeeklyActivityData {

    private String TotalActivityMinutes;

    private String CreatedOn;

    private String TotalBurnedCalories;

    private String scheduleMinutes;

    public String getScheduleMinutes() {
        return scheduleMinutes;
    }

    public void setScheduleMinutes(String scheduleMinutes) {
        this.scheduleMinutes = scheduleMinutes;
    }

    public String getTotalActivityMinutes() {
        return TotalActivityMinutes;
    }

    public void setTotalActivityMinutes(String totalActivityMinutes) {
        TotalActivityMinutes = totalActivityMinutes;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getTotalBurnedCalories() {
        return TotalBurnedCalories;
    }

    public void setTotalBurnedCalories(String totalBurnedCalories) {
        TotalBurnedCalories = totalBurnedCalories;
    }
}
