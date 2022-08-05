package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReminderResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<ReminderData> data = null;

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

    public ArrayList<ReminderData> getData() {
        return data;
    }

    public void setData(ArrayList<ReminderData> data) {
        this.data = data;
    }

    public class ReminderData implements Serializable
    {

        @SerializedName("ActivityType")
        @Expose
        private String ActivityType;

        @SerializedName("ReminderID")
        @Expose
        private Integer reminderID;
        @SerializedName("UserID")
        @Expose
        private Integer userID;
        @SerializedName("Activity")
        @Expose
        private String activity;
        @SerializedName("Frequency")
        @Expose
        private Integer frequency;
        @SerializedName("StartDate")
        @Expose
        private String startDate;
        @SerializedName("EndDate")
        @Expose
        private String endDate;
        @SerializedName("ReminderTime")
        @Expose
        private List<String> reminderTime = null;

        @SerializedName("AcknowledgedOn")
        @Expose
        private Object acknowledgedOn;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("ModifiedOn")
        @Expose
        private Object modifiedOn;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("DeletedOn")
        @Expose
        private Object deletedOn;
        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;


        public String getActivityType() {
            return ActivityType;
        }

        public void setActivityType(String activityType) {
            ActivityType = activityType;
        }

        public Integer getReminderID() {
            return reminderID;
        }

        public void setReminderID(Integer reminderID) {
            this.reminderID = reminderID;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public Integer getFrequency() {
            return frequency;
        }

        public void setFrequency(Integer frequency) {
            this.frequency = frequency;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public List<String> getReminderTime() {
            return reminderTime;
        }

        public void setReminderTime(List<String> reminderTime) {
            this.reminderTime = reminderTime;
        }

        public Object getAcknowledgedOn() {
            return acknowledgedOn;
        }

        public void setAcknowledgedOn(Object acknowledgedOn) {
            this.acknowledgedOn = acknowledgedOn;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Object getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(Object modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Object getDeletedOn() {
            return deletedOn;
        }

        public void setDeletedOn(Object deletedOn) {
            this.deletedOn = deletedOn;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }
    }

}
