package com.shamrock.reework.activity.AnalysisModule.activity.food;

public class ClsCustomFoodNapData {
    private String createdOn;
   private double BeforeBreakfast;
   private double Breakfast;
   private double Lunch;
   private double EveningSnacks;
   private double Dinner;
   private double Dessert;


    public ClsCustomFoodNapData(String createdOn, double beforeBreakfast, double breakfast, double lunch, double eveningSnacks, double dinner, double dessert) {
        this.createdOn = createdOn;
        BeforeBreakfast = beforeBreakfast;
        Breakfast = breakfast;
        Lunch = lunch;
        EveningSnacks = eveningSnacks;
        Dinner = dinner;
        Dessert = dessert;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }


    public double getBeforeBreakfast() {
        return BeforeBreakfast;
    }

    public void setBeforeBreakfast(double beforeBreakfast) {
        BeforeBreakfast = beforeBreakfast;
    }

    public double getBreakfast() {
        return Breakfast;
    }

    public void setBreakfast(double breakfast) {
        Breakfast = breakfast;
    }

    public double getLunch() {
        return Lunch;
    }

    public void setLunch(double lunch) {
        Lunch = lunch;
    }

    public double getEveningSnacks() {
        return EveningSnacks;
    }

    public void setEveningSnacks(double eveningSnacks) {
        EveningSnacks = eveningSnacks;
    }

    public double getDinner() {
        return Dinner;
    }

    public void setDinner(double dinner) {
        Dinner = dinner;
    }

    public double getDessert() {
        return Dessert;
    }

    public void setDessert(double dessert) {
        Dessert = dessert;
    }
}
