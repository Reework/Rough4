package com.shamrock.reework.activity.recipe.model;

public class ItemUomMaster {
    private String Measurement;

    private String Id;

    private String IsApproximate;

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsApproximate() {
        return IsApproximate;
    }

    public void setIsApproximate(String isApproximate) {
        IsApproximate = isApproximate;
    }
}
