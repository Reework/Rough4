package com.shamrock.reework.activity.SubscriptionModule.dialog;

public class PlanModel
{
    Integer planId;

    String planName;

    double dPrice;

    public PlanModel(Integer planId, String planName, double dPrice)
    {
        this.planId = planId;
        this.planName = planName;
        this.dPrice = dPrice;
    }

    public double getdPrice() {
        return dPrice;
    }

    public void setdPrice(double dPrice) {
        this.dPrice = dPrice;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
