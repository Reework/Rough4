package com.shamrock.reework.activity.MyPlansModule.addfood;

public class UpdatePlanItem {
    public int Id ;
    public int GroupId ;
    public int UomId ;
    public int ItemId ;
    public double Quantity ;
    public String Remark ;
    public String ItemType ;
    public String CreatedOn;
    public boolean IsTodayPlan;
    public int DuplicateItemId;
    private  int ReeworkerplanId;
    private  int SubActivityId;
    private  String ActivationTime;

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

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

//    public boolean isEdit() {
//        return IsEdit;
//    }
//
//    public void setEdit(boolean edit) {
//        IsEdit = edit;
//    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
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
}
