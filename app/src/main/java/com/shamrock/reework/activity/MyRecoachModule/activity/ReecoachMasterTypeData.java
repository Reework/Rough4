package com.shamrock.reework.activity.MyRecoachModule.activity;

import java.io.Serializable;

public class ReecoachMasterTypeData implements Serializable {

    private int Id;
      private String ReecoachType;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReecoachType() {
        return ReecoachType;
    }

    public void setReecoachType(String reecoachType) {
        ReecoachType = reecoachType;
    }
}
