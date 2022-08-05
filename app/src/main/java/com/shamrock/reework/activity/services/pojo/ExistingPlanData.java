package com.shamrock.reework.activity.services.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExistingPlanData {

    @SerializedName("EndDate")
    @Expose
    private String EndDate;
    @SerializedName("StartDate")
    @Expose
    private String StartDate;
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

    @SerializedName("SubPlanName")
    @Expose
    private String SubPlanName;

    @SerializedName("SubscriptioPlanId")
    @Expose
    private Integer SubscriptioPlanId;
    public String getSubPlanName() {
        return SubPlanName;
    }

    public void setSubPlanName(String subPlanName) {
        SubPlanName = subPlanName;
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

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
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

