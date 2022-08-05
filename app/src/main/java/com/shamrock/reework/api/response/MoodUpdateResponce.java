package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoodUpdateResponce {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

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

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public Integer getiD() {
            return iD;
        }

        public void setiD(Integer iD) {
            this.iD = iD;
        }

        public double getScheduledFoodCalConsumed() {
            return scheduledFoodCalConsumed;
        }

        public void setScheduledFoodCalConsumed(double scheduledFoodCalConsumed) {
            this.scheduledFoodCalConsumed = scheduledFoodCalConsumed;
        }

        public double getActualFoodCalConsumed() {
            return actualFoodCalConsumed;
        }

        public void setActualFoodCalConsumed(double actualFoodCalConsumed) {
            this.actualFoodCalConsumed = actualFoodCalConsumed;
        }

        public void setActualFoodCalConsumed(Integer actualFoodCalConsumed) {
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
