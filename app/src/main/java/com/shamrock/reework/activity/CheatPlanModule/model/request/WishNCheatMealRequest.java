package com.shamrock.reework.activity.CheatPlanModule.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishNCheatMealRequest {
    @SerializedName("OccasionId")
    @Expose
    private Integer occasionId;
    @SerializedName("WishToEatId")
    @Expose
    private Integer wishToEatId;

    public Integer getOccasionId() {
        return occasionId;
    }

    public void setOccasionId(Integer occasionId) {
        this.occasionId = occasionId;
    }

    public Integer getWishToEatId() {
        return wishToEatId;
    }

    public void setWishToEatId(Integer wishToEatId) {
        this.wishToEatId = wishToEatId;
    }
}
