package com.shamrock.reework.activity.MyBloodReportModule.service;

public class BloodReportItemModel
{
    String name, percent;

    public BloodReportItemModel(String name, String percent)
    {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
