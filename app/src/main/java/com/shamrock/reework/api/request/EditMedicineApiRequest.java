package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.activity.MedicineModule.service.FrequencyList;

import java.util.ArrayList;

public class EditMedicineApiRequest
{



    @SerializedName("ImgName")
    String ImgName;



    @SerializedName("ImgPath")
    String ImgPath;
    @SerializedName("MedicineName")
    String name;

    @SerializedName("Mymedid")
    private int myMedicineID;

    @SerializedName("MedicineId")
    private int medicineId;

    @SerializedName("Frequency")
    private String frequency;

    @SerializedName("Dosage")
    private String dosage;
    @SerializedName("IsNotification")
    private boolean IsNotification;

    public boolean getIsNotification() {
        return IsNotification;
    }

    public void setIsNotification(boolean IsNotification) {
        this.IsNotification = IsNotification;
    }

    @SerializedName("UserId")
    private int userID;

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    @SerializedName("FrequencyList")





    private ArrayList<FrequencyList> frequencyList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FrequencyList> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(ArrayList<FrequencyList> frequencyList) {
        this.frequencyList = frequencyList;
    }

    public void setMyMedicineID(int myMedicineID) {
        this.myMedicineID = myMedicineID;
    }

    public int getMyMedicineID() {
        return myMedicineID;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDosage() {
        return dosage;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }
}
