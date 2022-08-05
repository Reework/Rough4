package com.shamrock.reework.activity.HealthModule.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 06,June,2020
 */
public class PaidSubscriptionData {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("PlanName")
    @Expose
    private String planName;
    @SerializedName("Desription")
    @Expose
    private String desription;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("Services")
    @Expose
    private List<String> services = null;

    private ArrayList<SubPlans> SubPlans;


    @SerializedName("SubPlanName")
    @Expose
    private String SubPlanName;


    public String getSubPlanName() {
        return SubPlanName;
    }

    public void setSubPlanName(String subPlanName) {
        SubPlanName = subPlanName;
    }

    public ArrayList<SubPlans> getSubPlans() {
        return SubPlans;
    }

    public void setSubPlans(ArrayList<com.shamrock.reework.activity.HealthModule.entity.SubPlans> subPlans) {
        SubPlans = subPlans;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
