package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalCondition
{
    /**
     * MedicalConditionID : 1
     * MedicalCondition : None
     */

    @SerializedName("MedicalConditionID")
    @Expose
    private Integer medicalConditionID;
    @SerializedName("MedicalCondition")
    @Expose
    private String medicalCondition;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("DeletedOn")
    @Expose
    private Object deletedOn;

    public Integer getMedicalConditionID() {
        return medicalConditionID;
    }

    public void setMedicalConditionID(Integer medicalConditionID) {
        this.medicalConditionID = medicalConditionID;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Object deletedOn) {
        this.deletedOn = deletedOn;
    }
}

