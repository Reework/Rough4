package com.shamrock.reework.util;

public class ClsUpdateWeekDaysRequest {
//    {
//        "Id": 1,
//            "Days": "Sat,Sun",
//            "WeekType": "1",
//            "ReeworkerId": 3040,
//    }

    private int Id;
    private String Days;
    private String WeekType;
    private int ReeworkerId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getWeekType() {
        return WeekType;
    }

    public void setWeekType(String weekType) {
        WeekType = weekType;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
