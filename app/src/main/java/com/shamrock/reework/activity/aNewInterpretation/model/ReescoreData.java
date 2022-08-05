package com.shamrock.reework.activity.aNewInterpretation.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ReescoreData  implements Serializable {
    private ArrayList<BCAParams> bCAParams;

    private ArrayList<WellnessParams> wellnessParams;

    private ArrayList<PathoParams> pathoParams;

    private String HealthSummary;

    private String Label1;

    private String id;

    private String Label2;

    private String ReeScore;

    public ArrayList<BCAParams> getbCAParams() {
        return bCAParams;
    }

    public void setbCAParams(ArrayList<BCAParams> bCAParams) {
        this.bCAParams = bCAParams;
    }

    public ArrayList<WellnessParams> getWellnessParams() {
        return wellnessParams;
    }

    public void setWellnessParams(ArrayList<WellnessParams> wellnessParams) {
        this.wellnessParams = wellnessParams;
    }

    public ArrayList<PathoParams> getPathoParams() {
        return pathoParams;
    }

    public void setPathoParams(ArrayList<PathoParams> pathoParams) {
        this.pathoParams = pathoParams;
    }

    public String getHealthSummary() {
        return HealthSummary;
    }

    public void setHealthSummary(String healthSummary) {
        HealthSummary = healthSummary;
    }

    public String getLabel1() {
        return Label1;
    }

    public void setLabel1(String label1) {
        Label1 = label1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel2() {
        return Label2;
    }

    public void setLabel2(String label2) {
        Label2 = label2;
    }

    public String getReeScore() {
        return ReeScore;
    }

    public void setReeScore(String reeScore) {
        ReeScore = reeScore;
    }
}
