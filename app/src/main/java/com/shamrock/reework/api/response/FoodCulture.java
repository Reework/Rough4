package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodCulture {
    /**
     * FoodcultureID : 1
     * Foodculture : Punjabi
     */

    /*@SerializedName("FoodcultureID")
    private int FoodcultureID;
    @SerializedName("Foodculture")
    private String Foodculture;

    public int getFoodcultureID() {
        return FoodcultureID;
    }

    public void setFoodcultureID(int FoodcultureID) {
        this.FoodcultureID = FoodcultureID;
    }

    public String getFoodculture() {
        return Foodculture;
    }

    public void setFoodculture(String Foodculture) {
        this.Foodculture = Foodculture;

    }*/

    @SerializedName("FoodcultureID")
    @Expose
    private Integer foodcultureID;
    @SerializedName("Foodculture")
    @Expose
    private String foodculture;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("DeletedOn")
    @Expose
    private Object deletedOn;


    public FoodCulture(Integer foodcultureID, String foodculture, Boolean isDeleted, Object deletedOn) {
        this.foodcultureID = foodcultureID;
        this.foodculture = foodculture;
        this.isDeleted = isDeleted;
        this.deletedOn = deletedOn;
    }

    public Integer getFoodcultureID() {
        return foodcultureID;
    }

    public void setFoodcultureID(Integer foodcultureID) {
        this.foodcultureID = foodcultureID;
    }

    public String getFoodculture() {
        return foodculture;
    }

    public void setFoodculture(String foodculture) {
        this.foodculture = foodculture;
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
}
