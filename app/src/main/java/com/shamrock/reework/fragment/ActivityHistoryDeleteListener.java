package com.shamrock.reework.fragment;

import com.shamrock.reework.activity.activtyhistory.Activities;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;

public interface ActivityHistoryDeleteListener {
    void updateHistoryActivty(int position, Activities myMedicine);
    void deleteHistoryActivty(int position, Activities myMedicine);
}
