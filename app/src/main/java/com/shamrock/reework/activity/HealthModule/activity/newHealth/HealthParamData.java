package com.shamrock.reework.activity.HealthModule.activity.newHealth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HealthParamData implements Serializable {
    private String Answer;

    private String InputType;



    private int QuestionId;

    private String ItemList;

    private int AnswerId;
    private String AdditonalParameter;
    private String Question;

    private String Remark;
    @SerializedName("IsRemark")
    private String IsRemarkShow;

    public String getAdditonalParameter() {
        return AdditonalParameter;
    }

    public void setAdditonalParameter(String additonalParameter) {
        AdditonalParameter = additonalParameter;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getIsRemarkShow() {
        return IsRemarkShow;
    }

    public void setIsRemarkShow(String isRemarkShow) {
        IsRemarkShow = isRemarkShow;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public int getAnswerId() {
        return AnswerId;
    }

    public void setAnswerId(int answerId) {
        AnswerId = answerId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getInputType() {
        return InputType;
    }

    public void setInputType(String inputType) {
        InputType = inputType;
    }



    public String getItemList() {
        return ItemList;
    }

    public void setItemList(String itemList) {
        ItemList = itemList;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }
}
