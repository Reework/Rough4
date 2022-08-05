package com.shamrock.reework.activity.MedicineModule.service;

public class EatingRequest {

    private String EatingStatus;
    private String EatingTime;
    private int Id;

    public EatingRequest(String eatingStatus, String eatingTime, int id) {
        EatingStatus = eatingStatus;
        EatingTime = eatingTime;
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEatingStatus() {
        return EatingStatus;
    }

    public void setEatingStatus(String eatingStatus) {
        EatingStatus = eatingStatus;
    }

    public String getEatingTime() {
        return EatingTime;
    }

    public void setEatingTime(String eatingTime) {
        EatingTime = eatingTime;
    }
}
