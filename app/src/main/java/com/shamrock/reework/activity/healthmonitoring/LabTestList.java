package com.shamrock.reework.activity.healthmonitoring;

import java.util.ArrayList;

public class LabTestList {
    private String TestId;

    private String TestName;

    private String Unit;

    private Range Range;
    private int UnitId;

    public int getUnitId() {
        return UnitId;
    }

    public void setUnitId(int unitId) {
        UnitId = unitId;
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

    public Range getRange() {
        return Range;
    }

    public void setRange(Range range) {
        Range = range;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}
