package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SleepResponse
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
        @SerializedName("ScheduledSleepHours")
        @Expose
        private Integer scheduledSleepHours;
        @SerializedName("ActualSleepHours")
        @Expose
        private Integer actualSleepHours;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

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
