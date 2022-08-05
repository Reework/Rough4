package com.shamrock.reework.common;

public class ClsHistoryRequest {

    private String FromDate;
    private String ToDate;
    private String reeworkerId;
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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
