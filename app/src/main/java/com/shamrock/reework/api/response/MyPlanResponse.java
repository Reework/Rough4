package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MyPlanResponse
{
    @SerializedName("Code")
    @Expose
    private Integer code;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Data")
    @Expose
    private ArrayList<MyPlanData> data = null;

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

    public ArrayList<MyPlanData> getData() {
        return data;
    }

    public void setData(ArrayList<MyPlanData> data) {
        this.data = data;
    }

    public class MyPlanData implements Serializable
    {
        @SerializedName("Id")
        @Expose
        private String id;

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
        private Object actualTime;

        @SerializedName("ScheduleName")
        @Expose
        private String scheduleName;

        @SerializedName("ScheduledPlan")
        @Expose
        private String scheduledPlan;

        @SerializedName("ActualPlan")
        @Expose
        private Object actualPlan;
        @SerializedName("ScheduledValue")
        @Expose
        private String scheduledValue;
        @SerializedName("ActualValue")
        @Expose
        private Object actualValue;
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
        private Object waterIntake;







        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public Object getActualTime() {
            return actualTime;
        }

        public void setActualTime(Object actualTime) {
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

        public Object getActualPlan() {
            return actualPlan;
        }

        public void setActualPlan(Object actualPlan) {
            this.actualPlan = actualPlan;
        }

        public String getScheduledValue() {
            return scheduledValue;
        }

        public void setScheduledValue(String scheduledValue) {
            this.scheduledValue = scheduledValue;
        }

        public Object getActualValue() {
            return actualValue;
        }

        public void setActualValue(Object actualValue) {
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

        public Object getWaterIntake() {
            return waterIntake;
        }

        public void setWaterIntake(Object waterIntake) {
            this.waterIntake = waterIntake;
        }
    }

}
