package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WaterResponse
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
        @SerializedName("ScheduledWaterIntake")
        @Expose
        private Integer scheduledWaterIntake;
        @SerializedName("ActualWaterIntake")
        @Expose
        private Integer actualWaterIntake;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;

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
