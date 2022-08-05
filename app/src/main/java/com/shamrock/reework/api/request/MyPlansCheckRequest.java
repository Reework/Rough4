package com.shamrock.reework.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shamrock.reework.api.response.MyPlanResponse;

import java.util.ArrayList;

public class MyPlansCheckRequest
{

    @SerializedName("todaysSchedules")
    @Expose
    ArrayList<MyPlanResponse.MyPlanData> todaysSchedules = null;

    public ArrayList<MyPlanResponse.MyPlanData> getTodaysSchedules() {
        return todaysSchedules;
    }

    public void setTodaysSchedules(ArrayList<MyPlanResponse.MyPlanData> todaysSchedules) {
        this.todaysSchedules = todaysSchedules;
    }

   /*ArrayList<MyPlanResponse.MyPlanData> list;

    public ArrayList<MyPlanResponse.MyPlanData> getList() {
        return list;
    }

    public void setList(ArrayList<MyPlanResponse.MyPlanData> list) {
        this.list = list;
    }*/
}
