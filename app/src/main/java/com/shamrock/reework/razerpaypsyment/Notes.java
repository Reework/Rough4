package com.shamrock.reework.razerpaypsyment;

public class Notes {
    private String Description;

    private int UserId;

    private int SubscriptionPlanId;

    private int SubPlanId;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getSubscriptionPlanId() {
        return SubscriptionPlanId;
    }

    public void setSubscriptionPlanId(int subscriptionPlanId) {
        SubscriptionPlanId = subscriptionPlanId;
    }

    public int getSubPlanId() {
        return SubPlanId;
    }

    public void setSubPlanId(int subPlanId) {
        SubPlanId = subPlanId;
    }
}
