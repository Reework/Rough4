package com.shamrock.reework.activity.HealthModule.activity.newHealth;

import java.util.ArrayList;

public class ClsHealthobjective {
    private String objectivename;
    private String cost;

    public ClsHealthobjective(String objectivename, String cost) {
        this.objectivename = objectivename;
        this.cost = cost;
    }

    public String getObjectivename() {
        return objectivename;
    }

    public void setObjectivename(String objectivename) {
        this.objectivename = objectivename;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
