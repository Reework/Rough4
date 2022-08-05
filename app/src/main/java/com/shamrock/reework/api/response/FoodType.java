package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodType
{

    @SerializedName("FoodtypeID")
    @Expose
    private Integer foodtypeID;
    @SerializedName("Foodtype")
    @Expose
    private String foodtype;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("DeletedOn")
    @Expose
    private Object deletedOn;

    public FoodType(Integer foodtypeID, String foodtype, Boolean isDeleted, Object deletedOn) {
        this.foodtypeID = foodtypeID;
        this.foodtype = foodtype;
        this.isDeleted = isDeleted;
        this.deletedOn = deletedOn;
    }

    public Integer getFoodtypeID() {
        return foodtypeID;
    }

    public void setFoodtypeID(Integer foodtypeID) {
        this.foodtypeID = foodtypeID;
    }

    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Object deletedOn) {
        this.deletedOn = deletedOn;
    }


/*    @SerializedName("FoodtypeID")
    private int id;
    @SerializedName("Foodtype")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/
}
