package com.shamrock.reework.activity.FoodModule;

import com.shamrock.reework.api.response.FoodListByMealType;

import java.util.List;

public interface OnRepeatMealSelect {

    public void getSelectedRepeatMealFood(List<FoodListByMealType.Datum> filtermList);
}
