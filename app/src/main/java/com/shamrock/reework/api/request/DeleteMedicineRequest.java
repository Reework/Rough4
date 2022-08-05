package com.shamrock.reework.api.request;

import com.google.gson.annotations.SerializedName;

public class DeleteMedicineRequest
{

    @SerializedName("Mymedid")
    private int myMedicineId;

    @SerializedName("UserId")
    private int userID;

    public void setMyMedicineId(int myMedicineId) {
        this.myMedicineId = myMedicineId;
    }

    public int getMyMedicineId() {
        return myMedicineId;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }
}
