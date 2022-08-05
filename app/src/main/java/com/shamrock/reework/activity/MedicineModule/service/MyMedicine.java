package com.shamrock.reework.activity.MedicineModule.service;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyMedicine implements Serializable
{

    int medId;
    int myMedId;
    String medName;
    boolean IsNotification;
    String medFreq;
    String medDays;
    String path;
    String imageName;
    private ArrayList<FrequencyList> FrequencyList;




    public MyMedicine(int medId, int myMedId, String medName, String medFreq, String medDays, ArrayList<FrequencyList> FrequencyList, String path,String imageName,boolean IsNotification)
    {
        this.medId = medId;
        this.myMedId = myMedId;
        this.medName = medName;
        this.medFreq = medFreq;
        this.medDays = medDays;
        this.FrequencyList=FrequencyList;
        this.path=path;
        this.imageName=imageName;
        this.IsNotification=IsNotification;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public ArrayList<FrequencyList> getFrequencyList() {
        return FrequencyList;
    }

    public void setFrequencyList(ArrayList<FrequencyList> frequencyList) {
        FrequencyList = frequencyList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean IsNotification() {
        return IsNotification;
    }

    public void setChekdedNoti(boolean chekdedNoti) {
        IsNotification = chekdedNoti;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public int getMyMedId() {
        return myMedId;
    }

    public void setMyMedId(int myMedId) {
        this.myMedId = myMedId;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedFreq() {
        return medFreq;
    }

    public void setMedFreq(String medFreq) {
        this.medFreq = medFreq;
    }

    public String getMedDays() {
        return medDays;
    }

    public void setMedDays(String medDays) {
        this.medDays = medDays;
    }
}
