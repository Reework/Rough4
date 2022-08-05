package com.shamrock.reework.activity.aNewInterpretation.model;

import java.io.Serializable;

public class WellnessParams implements Serializable {
    private String SequenceNo;

    private String CurrentScore;

    private String Answer;

    private String MaxScore;

    private String Score;

    private String Question;

    private String Id;

    private String IsScore;

    private String IsInterpretation;

     private double FutureScore;
     private  String Remark;

    public double getFutureScore() {
        return FutureScore;
    }

    public void setFutureScore(double futureScore) {
        FutureScore = futureScore;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getCurrentScore() {
        return CurrentScore;
    }

    public void setCurrentScore(String currentScore) {
        CurrentScore = currentScore;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getMaxScore() {
        return MaxScore;
    }

    public void setMaxScore(String maxScore) {
        MaxScore = maxScore;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIsScore() {
        return IsScore;
    }

    public void setIsScore(String isScore) {
        IsScore = isScore;
    }

    public String getIsInterpretation() {
        return IsInterpretation;
    }

    public void setIsInterpretation(String isInterpretation) {
        IsInterpretation = isInterpretation;
    }
}
