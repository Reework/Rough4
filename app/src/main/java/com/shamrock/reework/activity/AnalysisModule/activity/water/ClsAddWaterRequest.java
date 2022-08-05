package com.shamrock.reework.activity.AnalysisModule.activity.water;

public class ClsAddWaterRequest {
    private double WaterIntake;

    private boolean IsChanged;

    private int TodayStatusId;

    private String EntryDate;

    private int Id;

    private int ReeworkerId;

    private int UoMId;


    public double getWaterIntake() {
        return WaterIntake;
    }

    public void setWaterIntake(double waterIntake) {
        WaterIntake = waterIntake;
    }

    public boolean isChanged() {
        return IsChanged;
    }

    public void setChanged(boolean changed) {
        IsChanged = changed;
    }

    public int getTodayStatusId() {
        return TodayStatusId;
    }

    public void setTodayStatusId(int todayStatusId) {
        TodayStatusId = todayStatusId;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public int getUoMId() {
        return UoMId;
    }

    public void setUoMId(int uoMId) {
        UoMId = uoMId;
    }
}
