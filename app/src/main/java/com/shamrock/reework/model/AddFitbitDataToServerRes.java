package com.shamrock.reework.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddFitbitDataToServerRes {
    @SerializedName("Activities")
    @Expose
    private List<Activity> activities = null;
    @SerializedName("UserId")
    @Expose
    private Integer userId;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static class Activity {

        @SerializedName("activityId")
        @Expose
        private Integer activityId;
        @SerializedName("activityParentId")
        @Expose
        private Integer activityParentId;
        @SerializedName("calories")
        @Expose
        private Integer calories;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("distance")
        @Expose
        private Integer distance;
        @SerializedName("duration")
        @Expose
        private Integer duration;
        @SerializedName("hasStartTime")
        @Expose
        private Boolean hasStartTime;
        @SerializedName("isFavorite")
        @Expose
        private Boolean isFavorite;
        @SerializedName("logId")
        @Expose
        private Integer logId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("startTime")
        @Expose
        private String startTime;
        @SerializedName("steps")
        @Expose
        private Integer steps;

        public Integer getActivityId() {
            return activityId;
        }

        public void setActivityId(Integer activityId) {
            this.activityId = activityId;
        }

        public Integer getActivityParentId() {
            return activityParentId;
        }

        public void setActivityParentId(Integer activityParentId) {
            this.activityParentId = activityParentId;
        }

        public Integer getCalories() {
            return calories;
        }

        public void setCalories(Integer calories) {
            this.calories = calories;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Boolean getHasStartTime() {
            return hasStartTime;
        }

        public void setHasStartTime(Boolean hasStartTime) {
            this.hasStartTime = hasStartTime;
        }

        public Boolean getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(Boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

        public Integer getLogId() {
            return logId;
        }

        public void setLogId(Integer logId) {
            this.logId = logId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public Integer getSteps() {
            return steps;
        }

        public void setSteps(Integer steps) {
            this.steps = steps;
        }

    }
}
