package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class MedicineRequest {
    @SerializedName("MedicineName")
    String medicineName;


    @SerializedName("UserId")
    int UserId;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}
