package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoodResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private ArrayList<Data> data = null;

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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data
    {
        @SerializedName("ScheduledMindStatus")
        @Expose
        private String scheduledMindStatus;
        @SerializedName("ActualMindStatus")
        @Expose
        private String actualMindStatus;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

        @SerializedName("IsBingeOnLargeQuantity")
        @Expose
        private Integer isBingeOnLargeQuantity;


        public Integer getIsBingeOnLargeQuantity() {
            return isBingeOnLargeQuantity;
        }

        public void setIsBingeOnLargeQuantity(Integer isBingeOnLargeQuantity) {
            this.isBingeOnLargeQuantity = isBingeOnLargeQuantity;
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }
    }
}
