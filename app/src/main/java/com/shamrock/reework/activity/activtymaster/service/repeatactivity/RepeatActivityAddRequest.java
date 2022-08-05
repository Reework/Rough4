package com.shamrock.reework.activity.activtymaster.service.repeatactivity;

import com.shamrock.reework.model.MasterActivty.AddActivityRequest;

import java.util.ArrayList;

public class RepeatActivityAddRequest {

    ArrayList<ClsRepeatActivityRequest> addActivityRequests;

    public ArrayList<ClsRepeatActivityRequest> getAddActivityRequests() {
        return addActivityRequests;
    }

    public void setAddActivityRequests(ArrayList<ClsRepeatActivityRequest> addActivityRequests) {
        this.addActivityRequests = addActivityRequests;
    }
}
