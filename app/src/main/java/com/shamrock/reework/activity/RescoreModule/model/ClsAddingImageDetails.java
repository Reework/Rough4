package com.shamrock.reework.activity.RescoreModule.model;

public class ClsAddingImageDetails {
    private String addingImageName;
    private String addingImageMessage;

    public ClsAddingImageDetails(String addingImageName, String addingImageMessage) {
        this.addingImageName = addingImageName;
        this.addingImageMessage = addingImageMessage;
    }

    public String getAddingImageName() {
        return addingImageName;
    }

    public void setAddingImageName(String addingImageName) {
        this.addingImageName = addingImageName;
    }

    public String getAddingImageMessage() {
        return addingImageMessage;
    }

    public void setAddingImageMessage(String addingImageMessage) {
        this.addingImageMessage = addingImageMessage;
    }
}
