package com.shamrock.reework.activity.aNewInterpretation.model;

import java.io.Serializable;

public class BCAParams implements Serializable {
    private String TestId;

    private String TestName;

    private String CurrentScore;

    private String IsDeleted;

    private String ReportTypeId;

    private String Weightage;

    private String IsInterpretation;
    private String FutureScore;
    private  String Remark;

    public String getFutureScore() {
        return FutureScore;
    }

    public void setFutureScore(String futureScore) {
        FutureScore = futureScore;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getCurrentScore() {
        return CurrentScore;
    }

    public void setCurrentScore(String currentScore) {
        CurrentScore = currentScore;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getReportTypeId() {
        return ReportTypeId;
    }

    public void setReportTypeId(String reportTypeId) {
        ReportTypeId = reportTypeId;
    }

    public String getWeightage() {
        return Weightage;
    }

    public void setWeightage(String weightage) {
        Weightage = weightage;
    }

    public String getIsInterpretation() {
        return IsInterpretation;
    }

    public void setIsInterpretation(String isInterpretation) {
        IsInterpretation = isInterpretation;
    }
}
