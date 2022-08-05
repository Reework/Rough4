package com.shamrock.reework.payment;

public class ClsMerchantRequest {
    int  ReeworkerId;
    int PlanId;

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public int getPlanId() {
        return PlanId;
    }

    public void setPlanId(int planId) {
        PlanId = planId;
    }
}
