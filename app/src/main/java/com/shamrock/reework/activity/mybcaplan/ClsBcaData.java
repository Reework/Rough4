package com.shamrock.reework.activity.mybcaplan;

import java.io.Serializable;
import java.util.ArrayList;

public class ClsBcaData implements Serializable {
    private ArrayList<PlanwiseListData> PlanwiseListData;

    private String planname;
    private int PlanId;

    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }

    public ArrayList<PlanwiseListData> getPlanwiseListData() {
        return PlanwiseListData;
    }

    public void setPlanwiseListData(ArrayList<PlanwiseListData> planwiseListData) {
        PlanwiseListData = planwiseListData;
    }

    public String getPlanname() {
        return planname;
    }

    public void setPlanname(String planname) {
        this.planname = planname;
    }
}
