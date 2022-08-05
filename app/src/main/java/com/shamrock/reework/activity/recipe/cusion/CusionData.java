package com.shamrock.reework.activity.recipe.cusion;

public class CusionData {
    private String Cuisine;

    private String IsActive;

    private String Id;

    public CusionData(String cuisine, String isActive, String id) {
        Cuisine = cuisine;
        IsActive = isActive;
        Id = id;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
