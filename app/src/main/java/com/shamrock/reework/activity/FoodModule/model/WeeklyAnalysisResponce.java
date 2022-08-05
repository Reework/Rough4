package com.shamrock.reework.activity.FoodModule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeeklyAnalysisResponce {
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

    public static class Datum {

        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("Scheduled")
        @Expose
        private double scheduled;
        @SerializedName("Actual")
        @Expose
        private double actual;

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public double getScheduled() {
            return scheduled;
        }

        public void setScheduled(double scheduled) {
            this.scheduled = scheduled;
        }

        public double getActual() {
            return actual;
        }

        public void setActual(double actual) {
            this.actual = actual;
        }

        public void setActual(Integer actual) {
            this.actual = actual;
        }

    }
}
