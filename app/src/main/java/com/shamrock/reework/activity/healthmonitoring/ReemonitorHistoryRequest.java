package com.shamrock.reework.activity.healthmonitoring;

public class ReemonitorHistoryRequest {
    private String FromDate;
    private String ToDate;
    private String reeworkerId;

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

    public String getReeworkerId() {
        return reeworkerId;
    }

    public void setReeworkerId(String reeworkerId) {
        this.reeworkerId = reeworkerId;
    }
}
