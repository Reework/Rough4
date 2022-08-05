package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.MedicineModule.service.FrequencyList;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddMedicineRequest implements Serializable {

    /**
     * UserID : 7
     * MedicineId : 2
     * Frequency : 3
     * Dosage : 2,5,6
     */

    @SerializedName("UserID")
    private int UserID;

    @SerializedName("Mymedid")
    private int MedicineId;

    @SerializedName("Frequency")
    private String Frequency;

    @SerializedName("Dosage")
    private String Dosage;

    @SerializedName("MedicineName")
    private String MedicineName;

    @SerializedName("IsNotification")
    private boolean IsNotification;

    public boolean IsNotification() {
        return IsNotification;
    }

    public void setChekdedNoti(boolean chekdedNoti) {
        IsNotification = chekdedNoti;
    }

    @SerializedName("FrequencyList")
    private ArrayList<FrequencyList>FrequencyList;


    public ArrayList<FrequencyList> getFrequencyList() {
        return FrequencyList;
    }

    public void setFrequencyList(ArrayList<FrequencyList> frequencyList) {
        FrequencyList = frequencyList;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getMedicineId() {
        return MedicineId;
    }

    public void setMedicineId(int MedicineId) {
        this.MedicineId = MedicineId;
    }


    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String Dosage) {
        this.Dosage = Dosage;
    }
}
