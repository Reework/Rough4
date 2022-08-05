package com.shamrock.reework.activity.sleepmodule.newsleephisrtory;

import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterDurations;

public interface OnWaterPowerNapClick {
    public void updateWaterPowerNap(WaterDurations img_edit_sleep_power, String sleepEntryDate);

    void deleteWaterPowerNap(int id);
}
