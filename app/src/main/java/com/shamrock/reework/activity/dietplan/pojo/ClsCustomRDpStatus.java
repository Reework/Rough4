package com.shamrock.reework.activity.dietplan.pojo;

public class ClsCustomRDpStatus {

    private String name;
    private String existing;
    private String ideal;
    private String plan;


    public ClsCustomRDpStatus(String name, String existing, String ideal, String plan) {
        this.name = name;
        this.existing = existing;
        this.ideal = ideal;
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExisting() {
        return existing;
    }

    public void setExisting(String existing) {
        this.existing = existing;
    }

    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
