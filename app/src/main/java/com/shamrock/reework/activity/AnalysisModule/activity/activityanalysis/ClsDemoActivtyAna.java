package com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis;

public class ClsDemoActivtyAna {
    private String date;
    private int bycycicletime;
    private int jogging;
    private int swimming;
    private int jumptinng;
    private int driving;

    public ClsDemoActivtyAna(String date, int bycycicletime, int jogging, int swimming, int jumptinng, int driving) {
        this.date = date;
        this.bycycicletime = bycycicletime;
        this.jogging = jogging;
        this.swimming = swimming;
        this.jumptinng = jumptinng;
        this.driving = driving;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public int getBycycicletime() {
        return bycycicletime;
    }

    public void setBycycicletime(int bycycicletime) {
        this.bycycicletime = bycycicletime;
    }

    public int getJogging() {
        return jogging;
    }

    public void setJogging(int jogging) {
        this.jogging = jogging;
    }

    public int getSwimming() {
        return swimming;
    }

    public void setSwimming(int swimming) {
        this.swimming = swimming;
    }

    public int getJumptinng() {
        return jumptinng;
    }

    public void setJumptinng(int jumptinng) {
        this.jumptinng = jumptinng;
    }
}
