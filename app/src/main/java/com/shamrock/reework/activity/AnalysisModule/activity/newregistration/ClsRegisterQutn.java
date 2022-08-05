package com.shamrock.reework.activity.AnalysisModule.activity.newregistration;

public class ClsRegisterQutn {
    private String question;
    private String ids;

    public ClsRegisterQutn(String question, String ids) {
        this.question = question;
        this.ids = ids;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
