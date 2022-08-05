package com.shamrock.reework.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetActivitiesResponse {
    @SerializedName("activityId")
    @Expose
    private Integer activityId;
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
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
