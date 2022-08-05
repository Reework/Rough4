package com.shamrock.reework.activity.HomeModule.pojo;

public class ClsRepeatSleep {
    private String startTime;
    private String endTime;
    private String totalTime;

    public ClsRepeatSleep(String startTime, String endTime, String totalTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
