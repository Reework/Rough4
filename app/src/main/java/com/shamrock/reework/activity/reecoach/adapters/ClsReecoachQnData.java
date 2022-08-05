package com.shamrock.reework.activity.reecoach.adapters;

import java.util.ArrayList;

public class ClsReecoachQnData {
    private String ReecoachId;

    private String FullName;

    private String Rating;

    private ArrayList<ClsReecoachQnDetails> Questions;

    public String getReecoachId() {
        return ReecoachId;
    }

    public void setReecoachId(String reecoachId) {
        ReecoachId = reecoachId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public ArrayList<ClsReecoachQnDetails> getQuestions() {
        return Questions;
    }

    public void setQuestions(ArrayList<ClsReecoachQnDetails> questions) {
        Questions = questions;
    }
}
