package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

public class SleepDurations {
    private String TotalMinutes;

    private String EndTime;

    private String TodayStatusId;

    private String StartTime;

    private String Id;

    private String ReeworkerId;

    private boolean IsDream;

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

    public String getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(String totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getTodayStatusId() {
        return TodayStatusId;
    }

    public void setTodayStatusId(String todayStatusId) {
        TodayStatusId = todayStatusId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(String reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
