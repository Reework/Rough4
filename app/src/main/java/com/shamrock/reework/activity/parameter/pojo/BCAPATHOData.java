package com.shamrock.reework.activity.parameter.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BCAPATHOData {
    private int ReeworkerId;
    @SerializedName("BCAQ")
    private ArrayList<BCAQ> BCAQ;

    @SerializedName("PathoQ")
    private ArrayList<BCAQ> PathoQ;

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public ArrayList<com.shamrock.reework.activity.parameter.pojo.BCAQ> getBCAQ() {
        return BCAQ;
    }

    public void setBCAQ(ArrayList<com.shamrock.reework.activity.parameter.pojo.BCAQ> BCAQ) {
        this.BCAQ = BCAQ;
    }

    public ArrayList<com.shamrock.reework.activity.parameter.pojo.BCAQ> getPathoQ() {
        return PathoQ;
    }

    public void setPathoQ(ArrayList<com.shamrock.reework.activity.parameter.pojo.BCAQ> pathoQ) {
        PathoQ = pathoQ;
    }
}
