package com.shamrock.reework.activity.healthmonitoring;

public class TestList {
    private String TestName;

    private String UnitId;

    private String RangeUnit;

    private String Id;

    private String CreatedOn;

    private String TestValue;

    private Range Range;

    private String Remark;

    private String LabTime;

    public String getLabTime() {
        return LabTime;
    }

    public void setLabTime(String labTime) {
        LabTime = labTime;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getUnitId() {
        return UnitId;
    }

    public void setUnitId(String unitId) {
        UnitId = unitId;
    }

    public String getRangeUnit() {
        return RangeUnit;
    }

    public void setRangeUnit(String rangeUnit) {
        RangeUnit = rangeUnit;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getTestValue() {
        return TestValue;
    }

    public void setTestValue(String testValue) {
        TestValue = testValue;
    }

    public com.shamrock.reework.activity.healthmonitoring.Range getRange() {
        return Range;
    }

    public void setRange(com.shamrock.reework.activity.healthmonitoring.Range range) {
        Range = range;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
