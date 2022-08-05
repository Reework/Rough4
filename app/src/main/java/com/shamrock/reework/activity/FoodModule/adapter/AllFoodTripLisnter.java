package com.shamrock.reework.activity.FoodModule.adapter;

import com.shamrock.reework.api.response.FoodTripResponse;

public interface AllFoodTripLisnter {

    void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model);
    void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model);
}
