package com.shamrock.reework.common;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.Toast;

import com.shamrock.reework.activity.LoginModule.LoginActivity;

public class Toaster {
    private static final int SHORT_TOAST_DURATION = 2000;
    public Context context;

    public Toaster() {}

    public Toaster(LoginActivity loginActivity) {
        context=loginActivity;
    }

    public  void makeLongToast(String text, long durationInMillis) {
        final Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);

        new CountDownTimer(Math.max(durationInMillis - SHORT_TOAST_DURATION, 1000), 1000) {
            @Override
            public void onFinish() {
                t.show();
            }

            @Override
            public void onTick(long millisUntilFinished) {
                t.show();
            }
        }.start();
    }
}
