package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeFragmentRequest
{
    @SerializedName("ID")
    @Expose
    private Integer iD;

    @SerializedName("ScheduledStatusID")
    @Expose
    private Integer scheduledStatusID;

    @SerializedName("ScheduledFoodCalConsumed")
    @Expose
    private double scheduledFoodCalConsumed;

    @SerializedName("ScheduledWaterIntake")
    @Expose
    private Integer scheduledWaterIntake;


    @SerializedName("ScheduledSleepHours")
    @Expose
    private Integer scheduledSleepHours;
    @SerializedName("ScheduledMindStatus")
    @Expose
    private String scheduledMindStatus;
    @SerializedName("ScheduledActivityHours")
    @Expose
    private Integer scheduledActivityHours;
    @SerializedName("ActualFoodCalConsumed")
    @Expose
    private double actualFoodCalConsumed;
    @SerializedName("ActualWaterIntake")
    @Expose
    private Integer actualWaterIntake;
    @SerializedName("ActualSleepHours")
    @Expose
    private Integer actualSleepHours;
    @SerializedName("ActualMindStatus")
    @Expose
    private String actualMindStatus;
    @SerializedName("ActualActivityHours")
    @Expose
    private Integer actualActivityHours;

    @SerializedName("ReeworkerID")
    @Expose
    private Integer reeworkerID;


    @SerializedName("ReecoachID")
    @Expose
    private Integer reecoachID;

    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

    @SerializedName("ModifiedOn")
    @Expose
    private String modifiedOn;
    @SerializedName("IsBingeOnLargeQuantity")
    @Expose
    private Integer isBingeOnLargeQuantity;


    @SerializedName("NetCalories")
    @Expose
    private float NetCalories;



    @SerializedName("TotalBurnedCalories")
    @Expose
    private float TotalBurnedCalories;



//    @SerializedName("ScheduledFrom")
//    @Expose
//    private String  ScheduledFrom;
//
//
//    @SerializedName("ScheduledTo")
//    @Expose
//    private String  ScheduledTo;


    @SerializedName("StartSleepTime")
    @Expose
    private String  StartSleepTime;

    @SerializedName("EndSleepTime")
    @Expose
    private String  EndSleepTime;

    public String getStartSleepTime() {
        return StartSleepTime;
    }

    public void setStartSleepTime(String startSleepTime) {
        StartSleepTime = startSleepTime;
    }

    public String getEndSleepTime() {
        return EndSleepTime;
    }

    public void setEndSleepTime(String endSleepTime) {
        EndSleepTime = endSleepTime;
    }

    public Integer getiD() {
        return iD;
    }

    public void setiD(Integer iD) {
        this.iD = iD;
    }

//    public String getScheduledFrom() {
//        return ScheduledFrom;
//    }

//    public void setScheduledFrom(String scheduledFrom) {
//        ScheduledFrom = scheduledFrom;
//    }
//
//    public String getScheduledTo() {
//        return ScheduledTo;
//    }
//
//    public void setScheduledTo(String scheduledTo) {
//        ScheduledTo = scheduledTo;
//    }

    public float getNetCalories() {
        return NetCalories;
    }

    public void setNetCalories(float netCalories) {
        NetCalories = netCalories;
    }

    public float getTotalBurnedCalories() {
        return TotalBurnedCalories;
    }

    public void setTotalBurnedCalories(float totalBurnedCalories) {
        TotalBurnedCalories = totalBurnedCalories;
    }

    public Integer getIsBingeOnLargeQuantity() {
        return isBingeOnLargeQuantity;
    }

    public void setIsBingeOnLargeQuantity(Integer isBingeOnLargeQuantity) {
        this.isBingeOnLargeQuantity = isBingeOnLargeQuantity;
    }


    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }


    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getScheduledStatusID() {
        return scheduledStatusID;
    }

    public void setScheduledStatusID(Integer scheduledStatusID) {
        this.scheduledStatusID = scheduledStatusID;
    }


    public double getScheduledFoodCalConsumed() {
        return scheduledFoodCalConsumed;
    }

    public void setScheduledFoodCalConsumed(double scheduledFoodCalConsumed) {
        this.scheduledFoodCalConsumed = scheduledFoodCalConsumed;
    }

    public void setScheduledFoodCalConsumed(Integer scheduledFoodCalConsumed) {
        this.scheduledFoodCalConsumed = scheduledFoodCalConsumed;
    }

    public Integer getScheduledWaterIntake() {
        return scheduledWaterIntake;
    }

    public void setScheduledWaterIntake(Integer scheduledWaterIntake) {
        this.scheduledWaterIntake = scheduledWaterIntake;
    }

    public Integer getScheduledSleepHours() {
        return scheduledSleepHours;
    }

    public void setScheduledSleepHours(Integer scheduledSleepHours) {
        this.scheduledSleepHours = scheduledSleepHours;
    }

    public String getScheduledMindStatus() {
        return scheduledMindStatus;
    }

    public void setScheduledMindStatus(String scheduledMindStatus) {
        this.scheduledMindStatus = scheduledMindStatus;
    }

    public Integer getScheduledActivityHours() {
        return scheduledActivityHours;
    }

    public void setScheduledActivityHours(Integer scheduledActivityHours) {
        this.scheduledActivityHours = scheduledActivityHours;
    }

    public double getActualFoodCalConsumed() {
        return actualFoodCalConsumed;
    }

    public void setActualFoodCalConsumed(double actualFoodCalConsumed) {
        this.actualFoodCalConsumed = actualFoodCalConsumed;
    }

    public Integer getActualWaterIntake() {
        return actualWaterIntake;
    }

    public void setActualWaterIntake(Integer actualWaterIntake) {
        this.actualWaterIntake = actualWaterIntake;
    }

    public Integer getActualSleepHours() {
        return actualSleepHours;
    }

    public void setActualSleepHours(Integer actualSleepHours) {
        this.actualSleepHours = actualSleepHours;
    }

    public String getActualMindStatus() {
        return actualMindStatus;
    }

    public void setActualMindStatus(String actualMindStatus) {
        this.actualMindStatus = actualMindStatus;
    }

    public Integer getActualActivityHours() {
        return actualActivityHours;
    }

    public void setActualActivityHours(Integer actualActivityHours) {
        this.actualActivityHours = actualActivityHours;
    }

    public Integer getReeworkerID() {
        return reeworkerID;
    }

    public void setReeworkerID(Integer reeworkerID) {
        this.reeworkerID = reeworkerID;
    }

    public Integer getReecoachID() {
        return reecoachID;
    }

    public void setReecoachID(Integer reecoachID) {
        this.reecoachID = reecoachID;
    }

}
