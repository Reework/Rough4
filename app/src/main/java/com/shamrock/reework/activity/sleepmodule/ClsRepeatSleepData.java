package com.shamrock.reework.activity.sleepmodule;

public class ClsRepeatSleepData {

//    "Id": 0,
//            "TodayStatusId": 0,
//            "ReeworkerId": 3040,
//            "StartTime": "2020-12-07T02:50:00",
//            "EndTime": "2020-12-07T16:20:00",
//            "EntryDate": "2020-12-07T00:00:00"

    private int Id;
    private int TodayStatusId;
    private int ReeworkerId;
    private String StartTime;
    private String EndTime;
    private String EntryDate;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTodayStatusId() {
        return TodayStatusId;
    }

    public void setTodayStatusId(int todayStatusId) {
        TodayStatusId = todayStatusId;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
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

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }
}
