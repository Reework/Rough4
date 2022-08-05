package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionFeaturesResponse
{

    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private ArrayList<SubData> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SubData> getData() {
        return data;
    }

    public void setData(ArrayList<SubData> data)
    {
        this.data = data;
    }

    /* -----------------------------------------------------------------------------*/
    public static class SubData
    {
        @SerializedName("PlanHeading_List")
        @Expose
        private ArrayList<PlanHeadingList> planHeadingList = null;

        @SerializedName("Feature_List")
        @Expose
        private ArrayList<FeatureList> featureList = null;

        public ArrayList<PlanHeadingList> getPlanHeadingList()
        {
            return planHeadingList;
        }

        public void setPlanHeadingList(ArrayList<PlanHeadingList> planHeadingList) {
            this.planHeadingList = planHeadingList;
        }

        public ArrayList<FeatureList> getFeatureList() {
            return featureList;
        }

        public void setFeatureList(ArrayList<FeatureList> featureList) {
            this.featureList = featureList;
        }
    }

    public static class FeatureList implements Serializable
    {
        @SerializedName("FeatureID")
        @Expose
        private Integer featureID;

        @SerializedName("FeatureName")
        @Expose
        private String featureName;

        @SerializedName("PlanIDs")
        @Expose
        private String planIDs;

        public Integer getFeatureID() {
            return featureID;
        }

        public void setFeatureID(Integer featureID) {
            this.featureID = featureID;
        }

        public String getFeatureName() {
            return featureName;
        }

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        public String getPlanIDs() {
            return planIDs;
        }

        public void setPlanIDs(String planIDs) {
            this.planIDs = planIDs;
        }
    }

    public static class PlanHeadingList implements Serializable
    {
        @SerializedName("planID")
        @Expose
        private Integer planID;

        @SerializedName("planName")
        @Expose
        private String planName;

        @SerializedName("planFees")
        @Expose
        private Integer planFees;

        @SerializedName("features")
        @Expose
        private String features;

        public Integer getPlanID() {
            return planID;
        }

        public void setPlanID(Integer planID) {
            this.planID = planID;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public Integer getPlanFees() {
            return planFees;
        }

        public void setPlanFees(Integer planFees) {
            this.planFees = planFees;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

    }



   /* *//**
     * Code : 1
     * Message : Subscription Feature
     * Data : [{"FeatureID":1,"FeatureName":"Assessment Result","IsFree":false,"Description":""},{"FeatureID":2,"FeatureName":"Reescore","IsFree":true,"Description":""}]
     *//*

    @SerializedName("Code")
    private int Code;

    @SerializedName("Message")
    private String Message;

    @SerializedName("Data")
    private List<SubscriptionFeature> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<SubscriptionFeature> getData() {
        return Data;
    }

    public void setData(List<SubscriptionFeature> Data) {
        this.Data = Data;
    }

    public static class SubscriptionFeature
    {
        *//**
         * FeatureID : 1
         * FeatureName : Assessment Result
         * IsFree : false
         * Description :
         *//*

      *//*  @SerializedName("FeatureID")
        private int FeatureID;

        @SerializedName("FeatureName")
        private String FeatureName;

        @SerializedName("IsFree")
        private boolean IsFree;

        @SerializedName("Description")
        private String Description;

        public int getFeatureID() {
            return FeatureID;
        }

        public void setFeatureID(int FeatureID) {
            this.FeatureID = FeatureID;
        }

        public String getFeatureName() {
            return FeatureName;
        }

        public void setFeatureName(String FeatureName) {
            this.FeatureName = FeatureName;
        }

        public boolean isIsFree() {
            return IsFree;
        }

        public void setIsFree(boolean IsFree) {
            this.IsFree = IsFree;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }*//*

        @SerializedName("FeatureID")
        @Expose
        private Integer featureID;

        @SerializedName("FeatureName")
        @Expose
        private String featureName;

        @SerializedName("Description")
        @Expose
        private String description;

        @SerializedName("IsFree")
        @Expose
        private Boolean isFree;

        @SerializedName("IsDeleted")
        @Expose
        private Boolean isDeleted;

        @SerializedName("DeletedOn")
        @Expose
        private Object deletedOn;

        public Integer getFeatureID() {
            return featureID;
        }

        public void setFeatureID(Integer featureID) {
            this.featureID = featureID;
        }

        public String getFeatureName() {
            return featureName;
        }

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getIsFree() {
            return isFree;
        }

        public void setIsFree(Boolean isFree) {
            this.isFree = isFree;
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
    }*/
}
