package com.shamrock.reework.activity.reecoachquestion;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.ReecoachHealthParamData;

import java.util.ArrayList;

public class ClsReecoachpostData {
    private int ReeworkedId;
    @SerializedName("Paramlist")
    private ArrayList<ReecoachHealthParamData> Paramlist;
    public int getReeworkedId() {
        return ReeworkedId;
    }
    public void setReeworkedId(int reeworkedId) {
        ReeworkedId = reeworkedId;
    }
    public ArrayList<ReecoachHealthParamData> getParamlist() {
        return Paramlist;
    }
    public void setParamlist(ArrayList<ReecoachHealthParamData> paramlist) {
        Paramlist = paramlist;
    }
}
