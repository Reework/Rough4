package com.shamrock.reework.activity.healthmonitoring;

public class ClsCustomTestList implements Comparable<ClsCustomTestList> {
    private String TestName;
    private String TestValue;
    private int id;
    private int unitID;
    private String date;
    private String unit;
    private String LabTime;

    public String getLabTime() {
        return LabTime;
    }

    public void setLabTime(String labTime) {
        LabTime = labTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    private String Remark;

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTestValue() {
        return TestValue;
    }

    public void setTestValue(String testValue) {
        TestValue = testValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public int compareTo(ClsCustomTestList o) {
        return TestName.compareTo(o.TestName);
    }
}
