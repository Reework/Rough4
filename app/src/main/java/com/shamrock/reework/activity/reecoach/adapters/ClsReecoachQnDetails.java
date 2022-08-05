package com.shamrock.reework.activity.reecoach.adapters;

public class ClsReecoachQnDetails
{

    private int Id;
    private String Question;
    private int QuestionId;
    private String Answer;
    private int ReecoachId;
    private int ReeworkerId;

    public ClsReecoachQnDetails(int id, String question, int questionId, String answer, int reecoachId, int reeworkerId) {
        Id = id;
        Question = question;
        QuestionId = questionId;
        Answer = answer;
        ReecoachId = reecoachId;
        ReeworkerId = reeworkerId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public int getReecoachId() {
        return ReecoachId;
    }

    public void setReecoachId(int reecoachId) {
        ReecoachId = reecoachId;
    }

    public int getReeworkerId() {
        return ReeworkerId;
    }

    public void setReeworkerId(int reeworkerId) {
        ReeworkerId = reeworkerId;
    }
}
