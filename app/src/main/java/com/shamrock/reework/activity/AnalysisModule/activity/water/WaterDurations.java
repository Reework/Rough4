package com.shamrock.reework.activity.AnalysisModule.activity.water;

public class WaterDurations {
    private double WaterInTake;

    private int TodayStatusId;

    private int Id;

    private String CreatedOn;

    private int UoM_Id;

    private String Unit;

    private int ReeworkerId;

    public double getWaterInTake() {
        return WaterInTake;
    }

    public void setWaterInTake(double waterInTake) {
        WaterInTake = waterInTake;
    }

    public int getTodayStatusId() {
        return TodayStatusId;
    }

    public void setTodayStatusId(int todayStatusId) {
        TodayStatusId = todayStatusId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getUoM_Id() {
        return UoM_Id;
    }

    public void setUoM_Id(int uoM_Id) {
        UoM_Id = uoM_Id;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
