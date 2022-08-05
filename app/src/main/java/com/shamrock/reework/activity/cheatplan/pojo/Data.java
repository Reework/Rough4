package com.shamrock.reework.activity.cheatplan.pojo;

import java.util.ArrayList;

public class Data {

    private ArrayList<CheatPlans> CheatPlans;

    private String Occasion;

    public ArrayList<com.shamrock.reework.activity.cheatplan.pojo.CheatPlans> getCheatPlans() {
        return CheatPlans;
    }

    public void setCheatPlans(ArrayList<com.shamrock.reework.activity.cheatplan.pojo.CheatPlans> cheatPlans) {
        CheatPlans = cheatPlans;
    }

    public String getOccasion() {
        return Occasion;
    }

    public void setOccasion(String occasion) {
        Occasion = occasion;
    }
}
