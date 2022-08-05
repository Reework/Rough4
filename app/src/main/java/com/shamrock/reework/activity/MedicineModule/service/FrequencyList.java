package com.shamrock.reework.activity.MedicineModule.service;

import java.io.Serializable;

public class FrequencyList  implements Serializable {
    private String EatingStatus;

    private String EatingTime;

    private int Id;



    public FrequencyList(String eatingStatus, String eatingTime,int Id) {
        EatingStatus = eatingStatus;
        EatingTime = eatingTime;
        this.Id=Id;
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
