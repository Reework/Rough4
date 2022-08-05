package com.shamrock.reework.activity.reeworkerhealth.app;

import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;

import java.util.ArrayList;

public class GetAllDataRepo {
    private static GetAllDataRepo getAllDataRepo;
    private ArrayList<HealthParamData> healthParamData;
    public static GetAllDataRepo getInstance(){
        if(getAllDataRepo==null){
            getAllDataRepo=new GetAllDataRepo();
        }
        return getAllDataRepo;


    }

    public ArrayList<HealthParamData> getHealthParamData() {
        return healthParamData;
    }

    public void setHealthParamData(ArrayList<HealthParamData> healthParamData) {
        this.healthParamData = healthParamData;
    }
}
