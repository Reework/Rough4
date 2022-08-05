package com.shamrock.reework.activity.parameter.pojo;

import com.google.gson.annotations.SerializedName;

public class GraphData {
    @SerializedName("ReportDate")
    private String ReportDate;
    @SerializedName("Score")
    private String testvalue;

    @SerializedName("TestName")
    private String TestName;

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String reportDate) {
        ReportDate = reportDate;
    }

    public String getTestvalue() {
        return testvalue;
    }

    public void setTestvalue(String testvalue) {
        this.testvalue = testvalue;
    }
}
