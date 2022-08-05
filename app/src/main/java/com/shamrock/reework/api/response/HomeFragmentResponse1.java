package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeFragmentResponse1 {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("ScheduledStatusID")
        @Expose
        private Integer scheduledStatusID;
        @SerializedName("ScheduledFoodCalConsumed")
        @Expose
        private Integer scheduledFoodCalConsumed;
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
        private Integer actualFoodCalConsumed;
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

        public Integer getScheduledFoodCalConsumed() {
            return scheduledFoodCalConsumed;
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

        public Integer getActualFoodCalConsumed() {
            return actualFoodCalConsumed;
        }

        public void setActualFoodCalConsumed(Integer actualFoodCalConsumed) {
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

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Object getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Integer getIsBingeOnLargeQuantity() {
            return isBingeOnLargeQuantity;
        }

        public void setIsBingeOnLargeQuantity(Integer isBingeOnLargeQuantity) {
            this.isBingeOnLargeQuantity = isBingeOnLargeQuantity;
        }

    }
}
