package com.shamrock.reework.activity.mybcaplan;

import java.io.Serializable;

public class PlanwiseListData implements Serializable {

    private String GroupName;

    private boolean Checked;

    private String TestId;

    private String TestName;

    private String ReportTypeId;

    private String PlanId;

    private String Id;

    private String PlanName;

    private String IsChecked;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
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

    public String getReportTypeId() {
        return ReportTypeId;
    }

    public void setReportTypeId(String reportTypeId) {
        ReportTypeId = reportTypeId;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String planId) {
        PlanId = planId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPlanName() {
        return PlanName;
    }

    public void setPlanName(String planName) {
        PlanName = planName;
    }

    public String getIsChecked() {
        return IsChecked;
    }

    public void setIsChecked(String isChecked) {
        IsChecked = isChecked;
    }
}
