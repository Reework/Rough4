package com.shamrock.reework.activity.todaysplan.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanItems implements Serializable {
    private String ImagePath;
    @SerializedName("VideoLink")
    private String videolink;
    private String Measurement;

    private String ItemName;

    private String Quantity;

    private String Id;

    private String ItemId;

    private String UomId;
    private String Remark;
    private boolean isAlternate;
    private String msgcount;
    private String planMasterID;

    private String mealtype;

    private String plantime;

    private boolean isEditItmeAdd;

    public boolean isEditItmeAdd() {
        return isEditItmeAdd;
    }

    public void setEditItmeAdd(boolean editItmeAdd) {
        isEditItmeAdd = editItmeAdd;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    private boolean IsAdded;

    private boolean isNotify;

    private String PostPlanID;
    private String ActivationTime;

    public String getActivationTime() {
        return ActivationTime;
    }

    public void setActivationTime(String activationTime) {
        ActivationTime = activationTime;
    }

    public int getSubActivityId() {
        return SubActivityId;
    }

    public void setSubActivityId(int subActivityId) {
        SubActivityId = subActivityId;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    private int SubActivityId;

    private int alternatePostItemID;
    private boolean isCheckedFlag;
    private int submitID;

    private int dialyPlanMasterID;
    private int LinkedRecipeId;

    private String finalAlternate;
    private int reeworkerPlanID;

    private int DuplicateItemId;
    private boolean IsTodayPlan;
    private int ReeworkerplanId;


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

    public String getFinalAlternate() {
        return finalAlternate;
    }

    public void setFinalAlternate(String finalAlternate) {
        this.finalAlternate = finalAlternate;
    }

    public int getLinkedRecipeId() {
        return LinkedRecipeId;
    }

    public void setLinkedRecipeId(int linkedRecipeId) {
        LinkedRecipeId = linkedRecipeId;
    }

    public int getDialyPlanMasterID() {
        return dialyPlanMasterID;
    }

    public void setDialyPlanMasterID(int dialyPlanMasterID) {
        this.dialyPlanMasterID = dialyPlanMasterID;
    }

    public int getReeworkerPlanID() {
        return reeworkerPlanID;
    }

    public void setReeworkerPlanID(int reeworkerPlanID) {
        this.reeworkerPlanID = reeworkerPlanID;
    }



    public int getSubmitID() {
        return submitID;
    }

    public void setSubmitID(int submitID) {
        this.submitID = submitID;
    }

    public boolean isCheckedFlag() {
        return isCheckedFlag;
    }

    public void setCheckedFlag(boolean checkedFlag) {
        isCheckedFlag = checkedFlag;
    }

    public int getAlternatePostItemID() {
        return alternatePostItemID;
    }

    public void setAlternatePostItemID(int alternatePostItemID) {
        this.alternatePostItemID = alternatePostItemID;
    }

    public String getPostPlanID() {
        return PostPlanID;
    }

    public void setPostPlanID(String postPlanID) {
        PostPlanID = postPlanID;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean notify) {
        isNotify = notify;
    }

    public boolean isAdded() {
        return IsAdded;
    }

    public void setAdded(boolean added) {
        IsAdded = added;
    }

    public String getPlantime() {
        return plantime;
    }

    public void setPlantime(String plantime) {
        this.plantime = plantime;
    }

    public String getMealtype() {
        return mealtype;
    }

    public void setMealtype(String mealtype) {
        this.mealtype = mealtype;
    }

    public String getPlanMasterID() {
        return planMasterID;
    }

    public void setPlanMasterID(String planMasterID) {
        this.planMasterID = planMasterID;
    }

    public String getMsgcount() {
        return msgcount;
    }

    public void setMsgcount(String msgcount) {
        this.msgcount = msgcount;
    }

    public PlanItems(int SubActivityId,String ActivationTime,String measurement, String itemName, String quantity, String id, String itemId, String uomId, String remark, boolean isAlternate, String msgcount, String planMasterID, String mealtype,String plantime,boolean IsAdded,boolean isNotify,String PostPlanID,int alternatePostItemID,boolean isCheckedFlag,int submitID,int LinkedRecipeId,String finalAlternate,int reeworkerPlanID,int dialyPlanMasterID,boolean IsTodayPlan,int ReeworkerplanId,String ImagePath,String videolink) {
        this.SubActivityId=SubActivityId;
        this.ActivationTime=ActivationTime;
        Measurement = measurement;
        ItemName = itemName;
        Quantity = quantity;
        Id = id;
        ItemId = itemId;
        UomId = uomId;
        Remark = remark;
        this.isAlternate=isAlternate;
        this.msgcount=msgcount;
        this.planMasterID=planMasterID;
        this.mealtype=mealtype;
        this.plantime=plantime;
        this.IsAdded=IsAdded;
        this.isNotify=isNotify;
        this.PostPlanID=PostPlanID;
        this.alternatePostItemID=alternatePostItemID;
        this.isCheckedFlag=isCheckedFlag;
        this.submitID=submitID;
        this.LinkedRecipeId=LinkedRecipeId;
        this.finalAlternate=finalAlternate;
        this.reeworkerPlanID=reeworkerPlanID;
        this.dialyPlanMasterID=dialyPlanMasterID;
        this.IsTodayPlan=IsTodayPlan;
        this.ReeworkerplanId=ReeworkerplanId;
        this.ImagePath=ImagePath;
        this.videolink=videolink;
    }
    public PlanItems(int SubActivityId,String ActivationTime,String measurement, String itemName, String quantity, String id, String itemId, String uomId, String remark, boolean isAlternate, String msgcount, String planMasterID, String mealtype,String plantime,boolean IsAdded,boolean isNotify,String PostPlanID,int alternatePostItemID,boolean isCheckedFlag,int submitID,int LinkedRecipeId,String finalAlternate,int reeworkerPlanID,int dialyPlanMasterID,boolean IsTodayPlan,int ReeworkerplanId,String ImagePath) {
        this.SubActivityId=SubActivityId;
        this.ActivationTime=ActivationTime;
        Measurement = measurement;
        ItemName = itemName;
        Quantity = quantity;
        Id = id;
        ItemId = itemId;
        UomId = uomId;
        Remark = remark;
        this.isAlternate=isAlternate;
        this.msgcount=msgcount;
        this.planMasterID=planMasterID;
        this.mealtype=mealtype;
        this.plantime=plantime;
        this.IsAdded=IsAdded;
        this.isNotify=isNotify;
        this.PostPlanID=PostPlanID;
        this.alternatePostItemID=alternatePostItemID;
        this.isCheckedFlag=isCheckedFlag;
        this.submitID=submitID;
        this.LinkedRecipeId=LinkedRecipeId;
        this.finalAlternate=finalAlternate;
        this.reeworkerPlanID=reeworkerPlanID;
        this.dialyPlanMasterID=dialyPlanMasterID;
        this.IsTodayPlan=IsTodayPlan;
        this.ReeworkerplanId=ReeworkerplanId;
        this.ImagePath=ImagePath;
    }


    private ArrayList<AlternateItems> AlternateItems;

    public ArrayList<AlternateItems> getAlternateItems() {
        return AlternateItems;
    }

    public boolean isAlternate() {
        return isAlternate;
    }

    public void setAlternate(boolean alternate) {
        isAlternate = alternate;
    }

    public void setAlternateItems(ArrayList<AlternateItems> alternateItems) {
        AlternateItems = alternateItems;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
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
