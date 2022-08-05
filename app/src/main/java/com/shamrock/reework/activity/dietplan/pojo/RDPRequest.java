package com.shamrock.reework.activity.dietplan.pojo;

public class RDPRequest {

    private double IdealFat;

    private double IdealCarb;

    private String FromDate;

    private String ToDate;

    private int Id;

    private double IdealFibre;

    private double IdealProtein;

    private int ReeworkerId;

    private double IdealCalories;

    public double getIdealFat() {
        return IdealFat;
    }

    public void setIdealFat(double idealFat) {
        IdealFat = idealFat;
    }

    public double getIdealCarb() {
        return IdealCarb;
    }

    public void setIdealCarb(double idealCarb) {
        IdealCarb = idealCarb;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getIdealFibre() {
        return IdealFibre;
    }

    public void setIdealFibre(double idealFibre) {
        IdealFibre = idealFibre;
    }

    public double getIdealProtein() {
        return IdealProtein;
    }

    public void setIdealProtein(double idealProtein) {
        IdealProtein = idealProtein;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }

    public double getIdealCalories() {
        return IdealCalories;
    }

    public void setIdealCalories(double idealCalories) {
        IdealCalories = idealCalories;
    }
}
