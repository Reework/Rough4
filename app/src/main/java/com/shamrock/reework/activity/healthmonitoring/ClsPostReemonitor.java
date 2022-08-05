package com.shamrock.reework.activity.healthmonitoring;

public class ClsPostReemonitor {
    private int UnitId;

    private int LabTestId;

    private int Id;

    private String CreatedOn;

    private double TestValue;

    private int ReeworkerId;

    private String Remark;
    private String LabTime;

    public String getLabTime() {
        return LabTime;
    }

    public void setLabTime(String labTime) {
        LabTime = labTime;
    }

    public int getUnitId() {
        return UnitId;
    }

    public void setUnitId(int unitId) {
        UnitId = unitId;
    }

    public int getLabTestId() {
        return LabTestId;
    }

    public void setLabTestId(int labTestId) {
        LabTestId = labTestId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public double getTestValue() {
        return TestValue;
    }

    public void setTestValue(double testValue) {
        TestValue = testValue;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
