package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

public class ClsNewEditSleepRequest {
    private boolean IsDream;
    private String TotalMinutes;

    private String EndSleepTime;
    private String SleepEntryTime;

    private String TodayStatusId;

    private String StartSleepTime;

    private String Id;

    private String ReeworkerId;

    private boolean IsChanged;

    private int SLeepCount;

    public int getSLeepCount() {
        return SLeepCount;
    }

    public void setSLeepCount(int SLeepCount) {
        this.SLeepCount = SLeepCount;
    }

    public boolean isDream() {
        return IsDream;
    }

    public void setDream(boolean dream) {
        IsDream = dream;
    }

    public boolean isChanged() {
        return IsChanged;
    }

    public void setChanged(boolean changed) {
        IsChanged = changed;
    }

    public void setTotalMinutes(String totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public void setEndSleepTime(String endSleepTime) {
        EndSleepTime = endSleepTime;
    }

    public void setSleepEntryTime(String sleepEntryTime) {
        SleepEntryTime = sleepEntryTime;
    }

    public void setTodayStatusId(String todayStatusId) {
        TodayStatusId = todayStatusId;
    }

    public void setStartSleepTime(String startSleepTime) {
        StartSleepTime = startSleepTime;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setReeworkerId(String reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public String getTotalMinutes() {
        return TotalMinutes;
    }

    public String getEndSleepTime() {
        return EndSleepTime;
    }

    public String getSleepEntryTime() {
        return SleepEntryTime;
    }

    public String getTodayStatusId() {
        return TodayStatusId;
    }

    public String getStartSleepTime() {
        return StartSleepTime;
    }

    public String getId() {
        return Id;
    }

    public String getReeworkerId() {
        return ReeworkerId;
    }
}
