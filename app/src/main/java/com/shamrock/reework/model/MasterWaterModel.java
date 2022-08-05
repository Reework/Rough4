package com.shamrock.reework.model;

public class MasterWaterModel {
    String date;
    int idealHrs, yourHrs;

    public MasterWaterModel()
    {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdealHrs() {
        return idealHrs;
    }

    public void setIdealHrs(int idealHrs) {
        this.idealHrs = idealHrs;
    }

    public int getYourHrs() {
        return yourHrs;
    }

    public void setYourHrs(int yourHrs) {
        this.yourHrs = yourHrs;
    }
}
