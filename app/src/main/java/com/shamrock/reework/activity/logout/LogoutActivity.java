package com.shamrock.reework.activity.logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.database.SessionManager;

public class LogoutActivity extends AppCompatActivity {
    SessionManager sessionManager;
//    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logout);
        sessionManager=new SessionManager(LogoutActivity.this);
        showFullScrenenStayHeladyDailog();
    }
    private void showFullScrenenStayHeladyDailog() {


//         dialog = new Dialog(LogoutActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

//        dialog.setContentView(R.layout.dialg_stay_healthy);
        AppCompatTextView txt_headthy = findViewById(R.id.txt_headthy);

//        if (sessionManager.getStringValue(SessionManager.KEY_USER_DOB))


        Log.d("dob", sessionManager.getStringValue(SessionManager.KEY_USER_DOB));
        if (sessionManager.getStringValue(SessionManager.KEY_USER_DOB) != null) {
            if (!sessionManager.getStringValue(SessionManager.KEY_USER_DOB).isEmpty()) {

                int age = 0;
                try {
//                    age = getAge(sessionManager.getStringValue(SessionManager.KEY_USER_DOB));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String msg=sessionManager.getStringValue("Logoutmsg");
                if (!msg.isEmpty()){
                    txt_headthy.setText(msg);

                }else {
                    txt_headthy.setText("Always a Rockstar");

                }

//                if (age < 18) {
//                    txt_headthy.setText("You are a Star");
//                } else if (age > 18 && age < 30) {
//                    txt_headthy.setText("Miss You");
//
//                } else if (age > 31 && age < 45) {
//                    txt_headthy.setText("Stay Young & Fit");
//
//                } else if (age > 46 && age < 60) {
//                    txt_headthy.setText("Always a Rockstar");
//
//                } else {
//                    txt_headthy.setText("Stay Healthy");
//
//                }

            }
        }

        TextView btn_close =findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences("AndroidHivePref", Context.MODE_PRIVATE);
                settings.edit().clear().apply();



                sessionManager.logoutUser();
                finish();




            }
        });
//        setTvZoomInOutAnimation(txt_headthy);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(550); //You can manage the blinking time with this parameter
        anim.setStartOffset(80);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_headthy.startAnimation(anim);


    }

}
