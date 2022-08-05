package com.shamrock.reework.activity.sleepmodule;

public class SleepActivityCustom {
    private String EndTime;
    private String TodayStatusId;
    private String StartTime;
    private String Id;
    private String dateSleepMain;
    private boolean IsDream;

    public boolean isDream() {
        return IsDream;
    }

    public void setDream(boolean dream) {
        IsDream = dream;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }


    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getTodayStatusId() {
        return TodayStatusId;
    }

    public void setTodayStatusId(String todayStatusId) {
        TodayStatusId = todayStatusId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDateSleepMain() {
        return dateSleepMain;
    }

    public void setDateSleepMain(String dateSleepMain) {
        this.dateSleepMain = dateSleepMain;
    }
}
