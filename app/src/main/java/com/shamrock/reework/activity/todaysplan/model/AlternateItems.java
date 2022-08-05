package com.shamrock.reework.activity.todaysplan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlternateItems implements Serializable {
    private String ImagePath;
    @SerializedName("VideoLink")
    private String videolink;

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    private String Measurement;


    private String ItemName;

    private String Quantity;

    private String Id;

    private String ItemId;

    private String UomId;
    private boolean IsAdded;

    private String Remark;

    private int LinkedRecipeId;

    private int DuplicateItemId;
    private boolean IsTodayPlan;
    private int ReeworkerplanId;
    private int SubActivityId;
    private String ActivationTime;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getSubActivityId() {
        return SubActivityId;
    }

    public void setSubActivityId(int subActivityId) {
        SubActivityId = subActivityId;
    }

    public String getActivationTime() {
        return ActivationTime;
    }

    public void setActivationTime(String activationTime) {
        ActivationTime = activationTime;
    }

    public int getReeworkerplanId() {
        return ReeworkerplanId;
    }

    public void setReeworkerplanId(int reeworkerplanId) {
        ReeworkerplanId = reeworkerplanId;
    }

    public boolean isTodayPlan() {
        return IsTodayPlan;
    }

    public void setTodayPlan(boolean todayPlan) {
        IsTodayPlan = todayPlan;
    }

    public int getDuplicateItemId() {
        return DuplicateItemId;
    }

    public void setDuplicateItemId(int duplicateItemId) {
        DuplicateItemId = duplicateItemId;
    }

    public int getLinkedRecipeId() {
        return LinkedRecipeId;
    }

    public void setLinkedRecipeId(int linkedRecipeId) {
        LinkedRecipeId = linkedRecipeId;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public boolean isAdded() {
        return IsAdded;
    }

    public void setAdded(boolean added) {
        IsAdded = added;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }


    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getUomId() {
        return UomId;
    }

    public void setUomId(String uomId) {
        UomId = uomId;
    }
}
