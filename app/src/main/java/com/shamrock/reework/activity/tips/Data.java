package com.shamrock.reework.activity.tips;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {


    @SerializedName("Id")
    @Expose
    private int Id;
    @SerializedName("Section")
    @Expose
    private String Section;
    @SerializedName("Descriptions")
    @Expose
    private String Descriptions;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String descriptions) {
        Descriptions = descriptions;
    }
}
