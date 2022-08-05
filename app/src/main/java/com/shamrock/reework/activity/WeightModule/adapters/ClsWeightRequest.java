package com.shamrock.reework.activity.WeightModule.adapters;

public class ClsWeightRequest
{
    private String FromDate;
    private String ToDate;
    private int reeworkerId;

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getReeworkerId() {
        return reeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        this.reeworkerId = reeworkerId;
    }
}
