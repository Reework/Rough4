package com.shamrock.reework.activity.sleepmodule;

public class ClsEditSleepRequest {
    private int StatusId;
    private String ActualValue;
    private int ReportType;

    public ClsEditSleepRequest(int statusId, String actualValue, int reportType) {
        StatusId = statusId;
        ActualValue = actualValue;
        ReportType = reportType;
    }

    public ClsEditSleepRequest() {
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getActualValue() {
        return ActualValue;
    }

    public void setActualValue(String actualValue) {
        ActualValue = actualValue;
    }

    public int getReportType() {
        return ReportType;
    }

    public void setReportType(int reportType) {
        ReportType = reportType;
    }
}
