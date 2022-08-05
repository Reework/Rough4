package com.shamrock.reework.activity.dietplan.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClspathoReportData {
    private String DietitianComment;

    private String SubscriptionPlan;

    private String OtherComment;

    private String DietPref;
@SerializedName("PathReport")
    private ArrayList<PathReport> PathReport;

    public void setPathReport(ArrayList<PathReport> pathReport) {
        PathReport = pathReport;
    }

    public ArrayList<PathReport> getPathReport() {
        return PathReport;
    }

    private String Id;

    private String ReeworkerId;

    public String getDietitianComment() {
        return DietitianComment;
    }

    public void setDietitianComment(String dietitianComment) {
        DietitianComment = dietitianComment;
    }

    public String getSubscriptionPlan() {
        return SubscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        SubscriptionPlan = subscriptionPlan;
    }

    public String getOtherComment() {
        return OtherComment;
    }

    public void setOtherComment(String otherComment) {
        OtherComment = otherComment;
    }

    public String getDietPref() {
        return DietPref;
    }

    public void setDietPref(String dietPref) {
        DietPref = dietPref;
    }









    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(String reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
