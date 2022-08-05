package com.shamrock.reework.model;

import android.app.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllFitbitActivitiesRes {


    @SerializedName("activities")
    @Expose
    private List<Activity> activities = null;
   /* @SerializedName("goals")
    @Expose
    private Goals goals;*/
/*    @SerializedName("summary")
    @Expose
    private Summary summary;*/

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

 /*   public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }*/

    public class Activity {

        @SerializedName("activityId")
        @Expose
        private Integer activityId;
        @SerializedName("activityParentId")
        @Expose
        private Integer activityParentId;
        @SerializedName("activityParentName")
        @Expose
        private String activityParentName;
        @SerializedName("calories")
        @Expose
        private Integer calories;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("distance")
        @Expose
        private Float distance;
        @SerializedName("duration")
        @Expose
        private Integer duration;
        @SerializedName("hasStartTime")
        @Expose
        private Boolean hasStartTime;
        @SerializedName("isFavorite")
        @Expose
        private Boolean isFavorite;
        @SerializedName("lastModified")
        @Expose
        private String lastModified;
        @SerializedName("logId")
        @Expose
        private long logId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("startDate")
        @Expose
        private String startDate;
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

        public String getActivityParentName() {
            return activityParentName;
        }

        public void setActivityParentName(String activityParentName) {
            this.activityParentName = activityParentName;
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

        public Float getDistance() {
            return distance;
        }

        public void setDistance(Float distance) {
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

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public long getLogId() {
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

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
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
    public class Distance {

        @SerializedName("activity")
        @Expose
        private String activity;
        @SerializedName("distance")
        @Expose
        private Float distance;

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public Float getDistance() {
            return distance;
        }

        public void setDistance(Float distance) {
            this.distance = distance;
        }

    }

    public class Goals {

        @SerializedName("activeMinutes")
        @Expose
        private Integer activeMinutes;
        @SerializedName("caloriesOut")
        @Expose
        private Integer caloriesOut;
        @SerializedName("distance")
        @Expose
        private Float distance;
        @SerializedName("floors")
        @Expose
        private Integer floors;
        @SerializedName("steps")
        @Expose
        private Integer steps;

        public Integer getActiveMinutes() {
            return activeMinutes;
        }

        public void setActiveMinutes(Integer activeMinutes) {
            this.activeMinutes = activeMinutes;
        }

        public Integer getCaloriesOut() {
            return caloriesOut;
        }

        public void setCaloriesOut(Integer caloriesOut) {
            this.caloriesOut = caloriesOut;
        }

        public Float getDistance() {
            return distance;
        }

        public void setDistance(Float distance) {
            this.distance = distance;
        }

        public Integer getFloors() {
            return floors;
        }

        public void setFloors(Integer floors) {
            this.floors = floors;
        }

        public Integer getSteps() {
            return steps;
        }

        public void setSteps(Integer steps) {
            this.steps = steps;
        }

    }


    public class HeartRateZone {

        @SerializedName("caloriesOut")
        @Expose
        private Integer caloriesOut;
        @SerializedName("max")
        @Expose
        private Integer max;
        @SerializedName("min")
        @Expose
        private Integer min;
        @SerializedName("minutes")
        @Expose
        private Integer minutes;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getCaloriesOut() {
            return caloriesOut;
        }

        public void setCaloriesOut(Integer caloriesOut) {
            this.caloriesOut = caloriesOut;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMinutes() {
            return minutes;
        }

        public void setMinutes(Integer minutes) {
            this.minutes = minutes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


    public class Summary {

        @SerializedName("activeScore")
        @Expose
        private Integer activeScore;
        @SerializedName("activityCalories")
        @Expose
        private Integer activityCalories;
        @SerializedName("caloriesBMR")
        @Expose
        private Integer caloriesBMR;
        @SerializedName("caloriesOut")
        @Expose
        private Integer caloriesOut;
        @SerializedName("distances")
        @Expose
        private List<Distance> distances = null;
        @SerializedName("elevation")
        @Expose
        private Float elevation;
        @SerializedName("fairlyActiveMinutes")
        @Expose
        private Integer fairlyActiveMinutes;
        @SerializedName("floors")
        @Expose
        private Integer floors;
        @SerializedName("heartRateZones")
        @Expose
        private List<HeartRateZone> heartRateZones = null;
        @SerializedName("lightlyActiveMinutes")
        @Expose
        private Integer lightlyActiveMinutes;
        @SerializedName("marginalCalories")
        @Expose
        private Integer marginalCalories;
        @SerializedName("sedentaryMinutes")
        @Expose
        private Integer sedentaryMinutes;
        @SerializedName("steps")
        @Expose
        private Integer steps;
        @SerializedName("veryActiveMinutes")
        @Expose
        private Integer veryActiveMinutes;

        public Integer getActiveScore() {
            return activeScore;
        }

        public void setActiveScore(Integer activeScore) {
            this.activeScore = activeScore;
        }

        public Integer getActivityCalories() {
            return activityCalories;
        }

        public void setActivityCalories(Integer activityCalories) {
            this.activityCalories = activityCalories;
        }

        public Integer getCaloriesBMR() {
            return caloriesBMR;
        }

        public void setCaloriesBMR(Integer caloriesBMR) {
            this.caloriesBMR = caloriesBMR;
        }

        public Integer getCaloriesOut() {
            return caloriesOut;
        }

        public void setCaloriesOut(Integer caloriesOut) {
            this.caloriesOut = caloriesOut;
        }

        public List<Distance> getDistances() {
            return distances;
        }

        public void setDistances(List<Distance> distances) {
            this.distances = distances;
        }

        public Float getElevation() {
            return elevation;
        }

        public void setElevation(Float elevation) {
            this.elevation = elevation;
        }

        public Integer getFairlyActiveMinutes() {
            return fairlyActiveMinutes;
        }

        public void setFairlyActiveMinutes(Integer fairlyActiveMinutes) {
            this.fairlyActiveMinutes = fairlyActiveMinutes;
        }

        public Integer getFloors() {
            return floors;
        }

        public void setFloors(Integer floors) {
            this.floors = floors;
        }

        public List<HeartRateZone> getHeartRateZones() {
            return heartRateZones;
        }

        public void setHeartRateZones(List<HeartRateZone> heartRateZones) {
            this.heartRateZones = heartRateZones;
        }

        public Integer getLightlyActiveMinutes() {
            return lightlyActiveMinutes;
        }

        public void setLightlyActiveMinutes(Integer lightlyActiveMinutes) {
            this.lightlyActiveMinutes = lightlyActiveMinutes;
        }

        public Integer getMarginalCalories() {
            return marginalCalories;
        }

        public void setMarginalCalories(Integer marginalCalories) {
            this.marginalCalories = marginalCalories;
        }

        public Integer getSedentaryMinutes() {
            return sedentaryMinutes;
        }

        public void setSedentaryMinutes(Integer sedentaryMinutes) {
            this.sedentaryMinutes = sedentaryMinutes;
        }

        public Integer getSteps() {
            return steps;
        }

        public void setSteps(Integer steps) {
            this.steps = steps;
        }

        public Integer getVeryActiveMinutes() {
            return veryActiveMinutes;
        }

        public void setVeryActiveMinutes(Integer veryActiveMinutes) {
            this.veryActiveMinutes = veryActiveMinutes;
        }

    }

}
