package com.shamrock.reework.fragment;

import com.shamrock.reework.model.MasterActivty.AcivityHistory;

public interface ActivtyListListener {
    void updateActivty(int position, AcivityHistory myMedicine);
    void deleteActivty(int position, AcivityHistory myMedicine);
}
