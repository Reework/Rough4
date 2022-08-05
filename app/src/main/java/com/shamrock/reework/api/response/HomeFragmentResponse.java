package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentResponse {
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public  class Data {
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("ScheduledFoodCalConsumed")
        @Expose
        private double scheduledFoodCalConsumed;
        @SerializedName("ActualFoodCalConsumed")
        @Expose
        private double actualFoodCalConsumed;
        @SerializedName("ScheduledWaterIntake")
        @Expose
        private Integer scheduledWaterIntake;
        @SerializedName("ActualWaterIntake")
        @Expose
        private Integer actualWaterIntake;
        @SerializedName("ScheduledSleepHours")
        @Expose
        private Integer scheduledSleepHours;
        @SerializedName("ActualSleepHours")
        @Expose
        private Integer actualSleepHours;
        @SerializedName("ScheduledMindStatus")
        @Expose
        private String scheduledMindStatus;
        @SerializedName("ActualMindStatus")
        @Expose
        private String actualMindStatus;
        @SerializedName("ScheduledActivityHours")
        @Expose
        private Integer scheduledActivityHours;
        @SerializedName("ActualActivityHours")
        @Expose
        private Integer actualActivityHours;
        @SerializedName("IsBingeOnLargeQuantity")
        @Expose
        private Integer isBingeOnLargeQuantity;
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

        @SerializedName("ScheduledStatusID")
        @Expose
        private Integer scheduledStatusID;



        @SerializedName("CanTestSchedule")
        @Expose
        private String CanTestSchedule;



        @SerializedName("TotalBurnedCalories")
        @Expose
        private float TotalBurnedCalories;


        @SerializedName("ScheduledTo")
        @Expose
        private String ScheduledTo;
        @SerializedName("ScheduledFrom")
        @Expose
        private String ScheduledFrom;

        @SerializedName("NetCalories")
        @Expose
        private float NetCalories;

        @SerializedName("ScheduledWeight")
        @Expose
        private float ScheduledWeight;


        @SerializedName("ScheduledWeightText")
        @Expose
        private String ScheduledWeightText;

        @SerializedName("RegisterationFrom")
        @Expose
        private String RegistrationFrom;

        public String getRegistrationFrom() {
            return RegistrationFrom;
        }

        public void setRegistrationFrom(String registrationFrom) {
            RegistrationFrom = registrationFrom;
        }

        public float getScheduledWeight() {
            return ScheduledWeight;
        }


        public String getScheduledWeightText() {
            return ScheduledWeightText;
        }

        public void setScheduledWeightText(String scheduledWeightText) {
            ScheduledWeightText = scheduledWeightText;
        }

        public void setScheduledWeight(int initialWeight) {
            ScheduledWeight = ScheduledWeight;
        }

        public float getNetCalories() {
            return NetCalories;
        }

        public void setNetCalories(float netCalories) {
            NetCalories = netCalories;
        }

        public Integer getiD() {
            return iD;
        }

        public void setiD(Integer iD) {
            this.iD = iD;
        }

        public String getScheduledTo() {
            return ScheduledTo;
        }

        public void setScheduledTo(String scheduledTo) {
            ScheduledTo = scheduledTo;
        }

        public String getScheduledFrom() {
            return ScheduledFrom;
        }

        public void setScheduledFrom(String scheduledFrom) {
            ScheduledFrom = scheduledFrom;
        }


        public float getTotalBurnedCalories() {
            return TotalBurnedCalories;
        }

        public void setTotalBurnedCalories(int totalBurnedCalories) {
            TotalBurnedCalories = totalBurnedCalories;
        }

        public Integer getScheduledStatusID() {
            return scheduledStatusID;
        }

        public void setScheduledStatusID(Integer scheduledStatusID) {
            this.scheduledStatusID = scheduledStatusID;
        }


        public String getCanTestSchedule() {
            return CanTestSchedule;
        }

        public void setCanTestSchedule(String canTestSchedule) {
            CanTestSchedule = canTestSchedule;
        }

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public double getScheduledFoodCalConsumed() {
            return scheduledFoodCalConsumed;
        }

        public void setScheduledFoodCalConsumed(float scheduledFoodCalConsumed) {
            this.scheduledFoodCalConsumed = scheduledFoodCalConsumed;
        }

        //        public Integer getScheduledFoodCalConsumed() {
//            return scheduledFoodCalConsumed;
//        }

        public void setScheduledFoodCalConsumed(Integer scheduledFoodCalConsumed) {
            this.scheduledFoodCalConsumed = scheduledFoodCalConsumed;
        }

        public double getActualFoodCalConsumed() {
            return actualFoodCalConsumed;
        }

        public void setActualFoodCalConsumed(double actualFoodCalConsumed) {
            this.actualFoodCalConsumed = actualFoodCalConsumed;
        }

        public Integer getScheduledWaterIntake() {
            return scheduledWaterIntake;
        }

        public void setScheduledWaterIntake(Integer scheduledWaterIntake) {
            this.scheduledWaterIntake = scheduledWaterIntake;
        }

        public Integer getActualWaterIntake() {
            return actualWaterIntake;
        }

        public void setActualWaterIntake(Integer actualWaterIntake) {
            this.actualWaterIntake = actualWaterIntake;
        }

        public Integer getScheduledSleepHours() {
            return scheduledSleepHours;
        }

        public void setScheduledSleepHours(Integer scheduledSleepHours) {
            this.scheduledSleepHours = scheduledSleepHours;
        }

        public Integer getActualSleepHours() {
            return actualSleepHours;
        }

        public void setActualSleepHours(Integer actualSleepHours) {
            this.actualSleepHours = actualSleepHours;
        }

        public String getScheduledMindStatus() {
            return scheduledMindStatus;
        }

        public void setScheduledMindStatus(String scheduledMindStatus) {
            this.scheduledMindStatus = scheduledMindStatus;
        }

        public String getActualMindStatus() {
            return actualMindStatus;
        }

        public void setActualMindStatus(String actualMindStatus) {
            this.actualMindStatus = actualMindStatus;
        }

        public Integer getScheduledActivityHours() {
            return scheduledActivityHours;
        }

        public void setScheduledActivityHours(Integer scheduledActivityHours) {
            this.scheduledActivityHours = scheduledActivityHours;
        }

        public Integer getActualActivityHours() {
            return actualActivityHours;
        }

        public void setActualActivityHours(Integer actualActivityHours) {
            this.actualActivityHours = actualActivityHours;
        }

        public Integer getIsBingeOnLargeQuantity() {
            return isBingeOnLargeQuantity;
        }

        public void setIsBingeOnLargeQuantity(Integer isBingeOnLargeQuantity) {
            this.isBingeOnLargeQuantity = isBingeOnLargeQuantity;
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

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }
    }
}
