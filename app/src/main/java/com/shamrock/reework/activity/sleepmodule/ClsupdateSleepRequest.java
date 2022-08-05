package com.shamrock.reework.activity.sleepmodule;

public class ClsupdateSleepRequest {
    private String Id;
    private String TodayStatusId;
    private String StartTime;
    private String EndTime;
    private boolean IsDream;

    public boolean isDream() {
        return IsDream;
    }

    public void setDream(boolean dream) {
        IsDream = dream;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
