package com.shamrock.reework.activity.RescoreModule.model;

import java.io.Serializable;

public class RescoreData  implements Serializable {

    private String IsDefault;

    private String ImageName;

    private String IsChanged;

    private String Message;

    private String IsActive;

    private String ImagePath;

    private String FromDate;

    private String ToDate;

    private String Id;

    private String ImgFile;

    private String MessageType;

    public String getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(String isDefault) {
        IsDefault = isDefault;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getIsChanged() {
        return IsChanged;
    }

    public void setIsChanged(String isChanged) {
        IsChanged = isChanged;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImgFile() {
        return ImgFile;
    }

    public void setImgFile(String imgFile) {
        ImgFile = imgFile;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }
}
