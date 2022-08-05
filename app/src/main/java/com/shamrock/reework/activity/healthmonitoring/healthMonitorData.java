package com.shamrock.reework.activity.healthmonitoring;

import java.util.ArrayList;

public class healthMonitorData {
    private ArrayList<UnitList> UnitList;

    private ArrayList<LabTestList> LabTestList;

    public ArrayList<UnitList> getUnitList() {
        return UnitList;
    }

    public void setUnitList(ArrayList<UnitList> unitList) {
        UnitList = unitList;
    }

    public ArrayList<LabTestList> getLabTestList() {
        return LabTestList;
    }

    public void setLabTestList(ArrayList<LabTestList> labTestList) {
        LabTestList = labTestList;
    }
}
