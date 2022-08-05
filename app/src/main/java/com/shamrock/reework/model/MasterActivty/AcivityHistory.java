package com.shamrock.reework.model.MasterActivty;

import java.io.Serializable;

public class AcivityHistory implements Serializable {

    private String TotalMinutes;

    private String ActivityName;
    private String ActivityTime;


    private String ActivityId;

    private String Id;

    private String SubActivityName;
    private String Date;

    private String SubActivityId;
    private String TotalBurnedCalories;

    private boolean IsFromFitbit;
    private boolean IsSelect;

    private String ImagePath;


    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public boolean isSelect() {
        return IsSelect;
    }

    public void setSelect(boolean select) {
        IsSelect = select;
    }

    public String getActivityTime() {
        return ActivityTime;
    }

    public void setActivityTime(String activityTime) {
        ActivityTime = activityTime;
    }

    public boolean isFromFitbit() {
        return IsFromFitbit;
    }

    public void setFromFitbit(boolean fromFitbit) {
        IsFromFitbit = fromFitbit;
    }

    public String getTotalBurnedCalories() {
        return TotalBurnedCalories;
    }

    public void setTotalBurnedCalories(String totalBurnedCalories) {
        TotalBurnedCalories = totalBurnedCalories;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(String totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getActivityId() {
        return ActivityId;
    }

    public void setActivityId(String activityId) {
        ActivityId = activityId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSubActivityName() {
        return SubActivityName;
    }

    public void setSubActivityName(String subActivityName) {
        SubActivityName = subActivityName;
    }

    public String getSubActivityId() {
        return SubActivityId;
    }

    public void setSubActivityId(String subActivityId) {
        SubActivityId = subActivityId;
    }
}
