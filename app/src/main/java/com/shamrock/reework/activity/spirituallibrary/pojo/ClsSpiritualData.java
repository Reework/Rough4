package com.shamrock.reework.activity.spirituallibrary.pojo;

public class ClsSpiritualData {

//    ,[SpiritualLibraryId]
//            ,[VideoLink]
//            ,[Title]
//            ,[Description]
//            ,[ThumbnaiLink]
//            ,[CreatedOn]


    private int  SpiritualLibraryId;
    private String VideoLink;
    private String Title;
    private String Description;
    private String ThumbnailLink;
    private  String CreatedOn;

    public ClsSpiritualData(int spiritualLibraryId, String videoLink, String title, String description, String thumbnaiLink, String createdOn) {
        SpiritualLibraryId = spiritualLibraryId;
        VideoLink = videoLink;
        Title = title;
        Description = description;
        ThumbnailLink = thumbnaiLink;
        CreatedOn = createdOn;
    }

    public int getSpiritualLibraryId() {
        return SpiritualLibraryId;
    }

    public void setSpiritualLibraryId(int spiritualLibraryId) {
        SpiritualLibraryId = spiritualLibraryId;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getThumbnaiLink() {
        return ThumbnailLink;
    }

    public void setThumbnaiLink(String thumbnaiLink) {
        ThumbnailLink = thumbnaiLink;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }
}
