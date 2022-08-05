package com.shamrock.reework.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MedicalConditionsResponse
{
    /**
     * Code : 1
     * Message : Ok
     * Data : [{"MedicalConditionID":1,"MedicalCondition":"None"},{"MedicalConditionID":2,"MedicalCondition":"Diabetes Type II"},{"MedicalConditionID":3,"MedicalCondition":"High Blood Pressure"},{"MedicalConditionID":4,"MedicalCondition":"Low Blood Pressure"},{"MedicalConditionID":5,"MedicalCondition":"Acidity"},{"MedicalConditionID":6,"MedicalCondition":"PCOD"},{"MedicalConditionID":7,"MedicalCondition":"High Cholesterol"},{"MedicalConditionID":8,"MedicalCondition":"Water Retention"},{"MedicalConditionID":9,"MedicalCondition":"High Uric Acid"},{"MedicalConditionID":10,"MedicalCondition":"Heart Disease"},{"MedicalConditionID":11,"MedicalCondition":"Thyroid Hypo"},{"MedicalConditionID":12,"MedicalCondition":"Others"}]
     */

   /* @SerializedName("Code")
    private int Code;
    @SerializedName("Message")
    private String Message;
    private List<MedicalCondition> Data;

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

    public List<MedicalCondition> getData() {
        return Data;
    }

    public void setData(List<MedicalCondition> Data) {
        this.Data = Data;
    }*/
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<MedicalCondition> data = null;

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

    public List<MedicalCondition> getData() {
        return data;
    }

    public void setData(List<MedicalCondition> data) {
        this.data = data;
    }

}
