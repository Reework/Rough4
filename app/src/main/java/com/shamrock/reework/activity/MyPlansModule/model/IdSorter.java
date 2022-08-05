package com.shamrock.reework.activity.MyPlansModule.model;

import com.shamrock.reework.activity.todaysplan.model.PlanItems;

import java.util.Comparator;

public class IdSorter implements Comparator<PlanItems>
{
    @Override
    public int compare(PlanItems o1, PlanItems  o2) {
        return (o1.getReeworkerPlanID() < o2.getReeworkerPlanID() ) ? -1: (o1.getReeworkerPlanID() > o2.getReeworkerPlanID()) ? 1:0 ;

    }
}
