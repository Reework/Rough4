package com.shamrock.reework.activity.lifestyleplanmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 05,June,2020
 */
public class LifeStylePlanPostsucces {

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

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("ActivityName")
        @Expose
        private String activityName;
        @SerializedName("ActivityTime")
        @Expose
        private String activityTime;
        @SerializedName("DurationInMinute")
        @Expose
        private Integer durationInMinute;
        @SerializedName("ReeworkerId")
        @Expose
        private Integer reeworkerId;
        @SerializedName("DayType")
        @Expose
        private Integer dayType;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("UserMaster")
        @Expose
        private Object userMaster;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Integer getReeworkerId() {
            return reeworkerId;
        }

        public void setReeworkerId(Integer reeworkerId) {
            this.reeworkerId = reeworkerId;
        }

        public Integer getDayType() {
            return dayType;
        }

        public void setDayType(Integer dayType) {
            this.dayType = dayType;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Object getUserMaster() {
            return userMaster;
        }

        public void setUserMaster(Object userMaster) {
            this.userMaster = userMaster;
        }

    }
}
