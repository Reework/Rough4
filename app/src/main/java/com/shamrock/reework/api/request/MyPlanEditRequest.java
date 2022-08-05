package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyPlanEditRequest
{
    ArrayList<MyPlanData> data;

    public ArrayList<MyPlanData> getData() {
        return data;
    }

    public void setData(ArrayList<MyPlanData> data) {
        this.data = data;
    }

    public static class MyPlanData
    {
        @SerializedName("DateOfAction")
        @Expose
        private String dateOfAction;
        @SerializedName("ReeplanId")
        @Expose
        private String reeplanId;
        @SerializedName("ReeworkerId")
        @Expose
        private Integer reeworkerId;
        @SerializedName("ReecoachId")
        @Expose
        private Integer reecoachId;
        @SerializedName("PlanId")
        @Expose
        private Integer planId;
        @SerializedName("ScheduledTime")
        @Expose
        private String scheduledTime;
        @SerializedName("ActualTime")
        @Expose
        private String actualTime;
        @SerializedName("ScheduleName")
        @Expose
        private String scheduleName;
        @SerializedName("ScheduledPlan")
        @Expose
        private String scheduledPlan;
        @SerializedName("ActualPlan")
        @Expose
        private String actualPlan;
        @SerializedName("ScheduledValue")
        @Expose
        private String scheduledValue;
        @SerializedName("ActualValue")
        @Expose
        private String actualValue;
        @SerializedName("CreatedOn")
        @Expose
        private String createdOn;
        @SerializedName("ModifiedOn")
        @Expose
        private Object modifiedOn;
        @SerializedName("Action")
        @Expose
        private Boolean action;
        @SerializedName("WaterIntake")
        @Expose
        private Integer waterIntake;

        public String getDateOfAction() {
            return dateOfAction;
        }

        public void setDateOfAction(String dateOfAction) {
            this.dateOfAction = dateOfAction;
        }

        public String getReeplanId() {
            return reeplanId;
        }

        public void setReeplanId(String reeplanId) {
            this.reeplanId = reeplanId;
        }

        public Integer getReeworkerId() {
            return reeworkerId;
        }

        public void setReeworkerId(Integer reeworkerId) {
            this.reeworkerId = reeworkerId;
        }

        public Integer getReecoachId() {
            return reecoachId;
        }

        public void setReecoachId(Integer reecoachId) {
            this.reecoachId = reecoachId;
        }

        public Integer getPlanId() {
            return planId;
        }

        public void setPlanId(Integer planId) {
            this.planId = planId;
        }

        public String getScheduledTime() {
            return scheduledTime;
        }

        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        public String getActualTime() {
            return actualTime;
        }

        public void setActualTime(String actualTime) {
            this.actualTime = actualTime;
        }

        public String getScheduleName() {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public String getScheduledPlan() {
            return scheduledPlan;
        }

        public void setScheduledPlan(String scheduledPlan) {
            this.scheduledPlan = scheduledPlan;
        }

        public String getActualPlan() {
            return actualPlan;
        }

        public void setActualPlan(String actualPlan) {
            this.actualPlan = actualPlan;
        }

        public String getScheduledValue() {
            return scheduledValue;
        }

        public void setScheduledValue(String scheduledValue) {
            this.scheduledValue = scheduledValue;
        }

        public String getActualValue() {
            return actualValue;
        }

        public void setActualValue(String actualValue) {
            this.actualValue = actualValue;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public Object getModifiedOn() {
            return modifiedOn;
        }

        public void setModifiedOn(Object modifiedOn) {
            this.modifiedOn = modifiedOn;
        }

        public Boolean getAction() {
            return action;
        }

        public void setAction(Boolean action) {
            this.action = action;
        }

        public Integer getWaterIntake() {
            return waterIntake;
        }

        public void setWaterIntake(Integer waterIntake) {
            this.waterIntake = waterIntake;
        }
    }
}
