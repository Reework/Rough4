package com.shamrock.reework.activity.BloodTestModule.activity;

public class ReportRequest {

//    Report: {"Id":"0","ReeworkerId":"3040","ReportName":"logo report","ReportFilePath":""}

    String Id;
    String ReeworkerId;
    String ReportName;
    String ReportFilePath;

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

    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String reportName) {
        ReportName = reportName;
    }

    public String getReportFilePath() {
        return ReportFilePath;
    }

    public void setReportFilePath(String reportFilePath) {
        ReportFilePath = reportFilePath;
    }
}
