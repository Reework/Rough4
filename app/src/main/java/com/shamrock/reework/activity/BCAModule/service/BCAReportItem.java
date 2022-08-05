package com.shamrock.reework.activity.BCAModule.service;

public class BCAReportItem
{

    String name;
    int percent;

    public BCAReportItem(String name, int percent)
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

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
