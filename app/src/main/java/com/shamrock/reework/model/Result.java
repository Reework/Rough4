package com.shamrock.reework.model;

public class Result {
    private String UserId;

    private DailyHistory DailyHistory;

    private WeeklyHistory WeeklyHistory;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public com.shamrock.reework.model.DailyHistory getDailyHistory() {
        return DailyHistory;
    }

    public void setDailyHistory(com.shamrock.reework.model.DailyHistory dailyHistory) {
        DailyHistory = dailyHistory;
    }

    public com.shamrock.reework.model.WeeklyHistory getWeeklyHistory() {
        return WeeklyHistory;
    }

    public void setWeeklyHistory(com.shamrock.reework.model.WeeklyHistory weeklyHistory) {
        WeeklyHistory = weeklyHistory;
    }
}
