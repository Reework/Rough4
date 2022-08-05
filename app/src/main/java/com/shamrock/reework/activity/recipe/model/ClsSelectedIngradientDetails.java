package com.shamrock.reework.activity.recipe.model;

public class ClsSelectedIngradientDetails {

    private String ingradientName;
    private int IngradientID;
    private int uomID;
    private String uomName;
    private double quantity;
    private double approximateValue;
    private String remarks;

    public ClsSelectedIngradientDetails(String ingradientName, int ingradientID, int uomID, String uomName, double quantity,String remarks,double approximateValue) {
        this.ingradientName = ingradientName;
        IngradientID = ingradientID;
        this.uomID = uomID;
        this.uomName = uomName;
        this.quantity = quantity;
        this.remarks=remarks;
        this.approximateValue=approximateValue;
    }

    public double getApproximateValue() {
        return approximateValue;
    }

    public void setApproximateValue(double approximateValue) {
        this.approximateValue = approximateValue;
    }

    public String getIngradientName() {
        return ingradientName;
    }

    public int getUomID() {
        return uomID;
    }

    public void setUomID(int uomID) {
        this.uomID = uomID;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setIngradientName(String ingradientName) {
        this.ingradientName = ingradientName;
    }

    public int getIngradientID() {
        return IngradientID;
    }

    public void setIngradientID(int ingradientID) {
        IngradientID = ingradientID;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
