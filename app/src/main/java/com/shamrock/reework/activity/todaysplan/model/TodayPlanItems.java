package com.shamrock.reework.activity.todaysplan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TodayPlanItems implements Serializable {
    public String Measurement;
    @SerializedName("VideoLink")
    private String videolink;

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    private String ItemName;

    private String Quantity;

    public String Id;

    private String ItemId;

    private String UomId;
    private String Remark;
    private boolean isAlternate;
    private String msgcount;
    private String planMasterID;

    private String mealtype;

    private String plantime;

    private boolean IsAdded;

    private boolean isNotify;

    private String PostPlanID;

    private int alternatePostItemID;
    private boolean isCheckedFlag;
    private int submitID;

    private int dialyPlanMasterID;
    private int LinkedRecipeId;

    private String finalAlternate;
    private int reeworkerPlanID;
    private int DuplicateItemId;

    public int getDuplicateItemId() {
        return DuplicateItemId;
    }

    public void setDuplicateItemId(int duplicateItemId) {
        DuplicateItemId = duplicateItemId;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public boolean isAlternate() {
        return isAlternate;
    }

    public void setAlternate(boolean alternate) {
        isAlternate = alternate;
    }

    public String getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(String msgcount) {
        this.msgcount = msgcount;
    }

    public String getPlanMasterID() {
        return planMasterID;
    }

    public void setPlanMasterID(String planMasterID) {
        this.planMasterID = planMasterID;
    }

    public String getMealtype() {
        return mealtype;
    }

    public void setMealtype(String mealtype) {
        this.mealtype = mealtype;
    }

    public String getPlantime() {
        return plantime;
    }

    public void setPlantime(String plantime) {
        this.plantime = plantime;
    }

    public boolean isAdded() {
        return IsAdded;
    }

    public void setAdded(boolean added) {
        IsAdded = added;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public String getPostPlanID() {
        return PostPlanID;
    }

    public void setPostPlanID(String postPlanID) {
        PostPlanID = postPlanID;
    }

    public int getAlternatePostItemID() {
        return alternatePostItemID;
    }

    public void setAlternatePostItemID(int alternatePostItemID) {
        this.alternatePostItemID = alternatePostItemID;
    }

    public boolean isCheckedFlag() {
        return isCheckedFlag;
    }

    public void setCheckedFlag(boolean checkedFlag) {
        isCheckedFlag = checkedFlag;
    }

    public int getSubmitID() {
        return submitID;
    }

    public void setSubmitID(int submitID) {
        this.submitID = submitID;
    }

    public int getDialyPlanMasterID() {
        return dialyPlanMasterID;
    }

    public void setDialyPlanMasterID(int dialyPlanMasterID) {
        this.dialyPlanMasterID = dialyPlanMasterID;
    }

    public int getLinkedRecipeId() {
        return LinkedRecipeId;
    }

    public void setLinkedRecipeId(int linkedRecipeId) {
        LinkedRecipeId = linkedRecipeId;
    }

    public String getFinalAlternate() {
        return finalAlternate;
    }

    public void setFinalAlternate(String finalAlternate) {
        this.finalAlternate = finalAlternate;
    }

    public int getReeworkerPlanID() {
        return reeworkerPlanID;
    }

    public void setReeworkerPlanID(int reeworkerPlanID) {
        this.reeworkerPlanID = reeworkerPlanID;
    }
}
