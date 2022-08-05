package com.shamrock.reework.activity.waterhistory;

public class ClsEditWaterRequest {
    private int StatusId;
    private String ActualValue;
    private int ReportType;

    public ClsEditWaterRequest(int statusId, String actualValue, int reportType) {
        StatusId = statusId;
        ActualValue = actualValue;
        ReportType = reportType;
    }

    public ClsEditWaterRequest() {
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
