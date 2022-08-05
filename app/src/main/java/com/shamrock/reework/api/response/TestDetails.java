package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestDetails {

    @SerializedName("ReportType")
    @Expose
    private String reportType;
    @SerializedName("TestId")
    @Expose
    private Integer testId;
    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("TestValue")
    @Expose
    private String testValue;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("NormalRange")
    @Expose
    private String normalRange;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("FilePath")
    @Expose
    private String filePath;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
