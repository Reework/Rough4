package com.shamrock.reework.activity.lifestyleplanmodule;

import java.util.ArrayList;

public class ClsPlanData {

    private ArrayList<Plan> Plan;

    private Week Week;

    public ArrayList<com.shamrock.reework.activity.lifestyleplanmodule.Plan> getPlan() {
        return Plan;
    }

    public void setPlan(ArrayList<com.shamrock.reework.activity.lifestyleplanmodule.Plan> plan) {
        Plan = plan;
    }

    public com.shamrock.reework.activity.lifestyleplanmodule.Week getWeek() {
        return Week;
    }

    public void setWeek(com.shamrock.reework.activity.lifestyleplanmodule.Week week) {
        Week = week;
    }
}
