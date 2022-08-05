package com.shamrock.reework.activity.HomeModule.service;

import java.io.Serializable;
import java.util.ArrayList;

public class MinMoodModel implements Serializable
{
    int id, image;
    String name;

    public MinMoodModel(int id, int image, String mood)
    {
        this.id = id;
        this.image = image;
        this.name = mood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
