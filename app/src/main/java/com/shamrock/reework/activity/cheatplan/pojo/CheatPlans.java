package com.shamrock.reework.activity.cheatplan.pojo;

import com.shamrock.reework.activity.lifestyleplanmodule.Plan;

import java.util.ArrayList;

public class CheatPlans {

    private String Category;

    private ArrayList<Plans> Plans;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public ArrayList<com.shamrock.reework.activity.cheatplan.pojo.Plans> getPlans() {
        return Plans;
    }

    public void setPlans(ArrayList<com.shamrock.reework.activity.cheatplan.pojo.Plans> plans) {
        Plans = plans;
    }
}
