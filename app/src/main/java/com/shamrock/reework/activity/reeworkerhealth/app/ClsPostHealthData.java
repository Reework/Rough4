package com.shamrock.reework.activity.reeworkerhealth.app;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;

import java.util.ArrayList;

public class ClsPostHealthData {
    private int ReeworkedId;
    @SerializedName("Paramlist")
    private ArrayList<HealthParamData> Paramlist;

    public int getReeworkedId() {
        return ReeworkedId;
    }

    public void setReeworkedId(int reeworkedId) {
        ReeworkedId = reeworkedId;
    }

    public ArrayList<HealthParamData> getParamlist() {
        return Paramlist;
    }

    public void setParamlist(ArrayList<HealthParamData> paramlist) {
        Paramlist = paramlist;
    }
}
