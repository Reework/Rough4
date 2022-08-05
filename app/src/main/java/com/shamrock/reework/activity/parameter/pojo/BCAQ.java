package com.shamrock.reework.activity.parameter.pojo;

public class BCAQ {
    private int TestId;
    private String TestName;
    private int ReportTypeId;
    private boolean IsChecked;

    public int getTestId() {
        return TestId;
    }

    public void setTestId(int testId) {
        TestId = testId;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public int getReportTypeId() {
        return ReportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        ReportTypeId = reportTypeId;
    }

    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(boolean checked) {
        IsChecked = checked;
    }
}
