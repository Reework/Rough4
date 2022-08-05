package com.shamrock.reework.activity.HealthModule.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;

public class HealthObjectivePAgerAdapter extends PagerAdapter{

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return false;
    }
}