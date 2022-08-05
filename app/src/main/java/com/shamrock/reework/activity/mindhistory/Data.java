package com.shamrock.reework.activity.mindhistory;

public class Data {
    String ActualMindStatus;
    String Score;
    String CreatedOn;
    String ScheduledMindStatus;
    String StatusTime;

    public String getStatusTime() {
        return StatusTime;
    }

    public void setStatusTime(String statusTime) {
        StatusTime = statusTime;
    }

    public String getActualMindStatus() {
        return ActualMindStatus;
    }

    public void setActualMindStatus(String actualMindStatus) {
        ActualMindStatus = actualMindStatus;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getScheduledMindStatus() {
        return ScheduledMindStatus;
    }

    public void setScheduledMindStatus(String scheduledMindStatus) {
        ScheduledMindStatus = scheduledMindStatus;
    }
}
