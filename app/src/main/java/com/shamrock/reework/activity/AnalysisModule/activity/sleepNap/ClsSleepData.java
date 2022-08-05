package com.shamrock.reework.activity.AnalysisModule.activity.sleepNap;

import java.util.ArrayList;

class ClsSleepData {
    private String date;
    private int nap1;
    private int nap2;
    private int nap3;

    public ClsSleepData(String date, int nap1, int nap2, int nap3) {
        this.date = date;
        this.nap1 = nap1;
        this.nap2 = nap2;
        this.nap3 = nap3;
    }

    public static ArrayList<ClsSleepData> getSleepData() {

        ArrayList<ClsSleepData> clsSleepDataArrayList=new ArrayList<>();
        clsSleepDataArrayList.add(new ClsSleepData("19-2",7,9,10));
        clsSleepDataArrayList.add(new ClsSleepData("20-2",8,11,15));
        clsSleepDataArrayList.add(new ClsSleepData("21-2",9,0,0));
        clsSleepDataArrayList.add(new ClsSleepData("22-2",10,16,19));

        return clsSleepDataArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNap1() {
        return nap1;
    }

    public void setNap1(int nap1) {
        this.nap1 = nap1;
    }

    public int getNap2() {
        return nap2;
    }

    public void setNap2(int nap) {
        this.nap2 = nap;
    }

    public int getNap3() {
        return nap3;
    }

    public void setNap3(int nap3) {
        this.nap3 = nap3;
    }
}
