package com.shamrock.reework.reecoachmodule.activities.pojo;

import java.io.Serializable;

public class DashboardData implements Serializable {
    private int FreezedReeworker;

    private int TotalReeworker;

    private int ActiveReeworker;

    private int IsActivReeworker;

    public int getFreezedReeworker() {
        return FreezedReeworker;
    }

    public void setFreezedReeworker(int freezedReeworker) {
        FreezedReeworker = freezedReeworker;
    }

    public int getTotalReeworker() {
        return TotalReeworker;
    }

    public void setTotalReeworker(int totalReeworker) {
        TotalReeworker = totalReeworker;
    }

    public int getActiveReeworker() {
        return ActiveReeworker;
    }

    public void setActiveReeworker(int activeReeworker) {
        ActiveReeworker = activeReeworker;
    }

    public int getIsActivReeworker() {
        return IsActivReeworker;
    }

    public void setIsActivReeworker(int isActivReeworker) {
        IsActivReeworker = isActivReeworker;
    }
}
