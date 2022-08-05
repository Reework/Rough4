package com.shamrock.reework.activity.lifestyleplanmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.util.Week;

import java.util.List;

/**
 * Created by Rahul on 05,June,2020
 */
public class LifeStylePlanGet {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Plan")
    @Expose
    private List<Datum> data = null;


    @SerializedName("Week")
    @Expose
    private Week week;


    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("ReeworkerId")
        @Expose
        private Integer reeworkerId;
        @SerializedName("ActivityName")
        @Expose
        private String activityName;
        @SerializedName("ActivityTime")
        @Expose
        private String activityTime;
        @SerializedName("DurationInMinute")
        @Expose
        private Integer durationInMinute;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("DayType")
        @Expose
        private Integer dayType;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getReeworkerId() {
            return reeworkerId;
        }

        public void setReeworkerId(Integer reeworkerId) {
            this.reeworkerId = reeworkerId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getActivityTime() {
            return activityTime;
        }

        public void setActivityTime(String activityTime) {
            this.activityTime = activityTime;
        }

        public Integer getDurationInMinute() {
            return durationInMinute;
        }

        public void setDurationInMinute(Integer durationInMinute) {
            this.durationInMinute = durationInMinute;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Integer getDayType() {
            return dayType;
        }

        public void setDayType(Integer dayType) {
            this.dayType = dayType;
        }

    }
}

