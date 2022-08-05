package com.shamrock.reework.activity.todaysplan.model.listners;

import com.shamrock.reework.activity.todaysplan.model.PlanItems;

public interface onAddFoodPlan {

    public void onAddFoodPlanClick(PlanItems id);
    public void onAddnewFoodPlanClick(PlanItems id);
    public void onEditFoodPlanClick(PlanItems id);
    public void getFoodPlanData(PlanItems reeworkerPlan, String mealType, String planTime, String itemId);
    public void getLifeStyleplan(PlanItems reeworkerPlan, String mealType, String planTime);
    public void addActivityLifeStyleplantoDailyDaily(PlanItems reeworkerPlan);
    public void onNotificationClick(int id, boolean notifyflag, boolean isAddedFlag, boolean alternate, int alternatePostItemID, int submitID, String s);

    void onViewRecipe(int linkedRecipeId);
}
