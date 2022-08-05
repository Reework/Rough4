package com.shamrock.reework.activity.HealthModule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.database.SessionManager;

public class HealthParameterGoodJobActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Toolbar toolbar;
    Typeface font;
    ImageView imgNext;
    SessionManager session;
    private boolean isFirstTime;
    int planId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_parameter_thank_you);
        context = HealthParameterGoodJobActivity.this;
        init();
        setToolBar();
        findViews();

    }

    private void init() {
        session = new SessionManager(context);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFirstTime = bundle.getBoolean("isFirstTime");
        }

        planId = session.getIntValue(SessionManager.KEY_USER_PLAN_ID);
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText(R.string.title_health_parameters);
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        imgNext = findViewById(R.id.imgViewNext_HPThankYou);
        imgNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewNext_HPThankYou:

                //   startActivity(new Intent(context, ReescoreActivity.class));
//                startActivity(new Intent(context, HomeActivity.class));

                if (isFirstTime) {
//                       startActivity(new Intent(context, ReescoreActivity.class).putExtra("isFirstTime", isFirstTime));


//                       tempvhange by amey
                    Intent intent=new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();






                } else {


                    Intent intent=new Intent(context, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }


                break;
            default:
        }
    }

    /* if (planId == 1)
                {
                    if (session.getBooleanValue(SessionManager.KEY_USER_REETEST_IS_DONE))
                    {
                    *//*if (session.getBooleanValue(SessionManager.KEY_USER_IS_FREEZE)) {
                        startActivity(new Intent(context, UnfreezeActivity.class));
                    } else {
                        startActivity(new Intent(context, HomeActivity.class));
                    }*//*

                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        //startActivity(new Intent(context, HomeActivity.class));
                       *//* if (isFirstTime)
                        {
                            startActivity(new Intent(context, HomeActivity.class));
                        }
                        else
                        {
                            finish();
                        }*//*
                    }
                    else
                    {
                        Intent intent = new Intent(this, Welcome_ScheduleBloodTestActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        // for first time Use
                        //startActivity(new Intent(context, Welcome_ScheduleBloodTestActivity.class));
                    }
                }
                else
                {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }*/
}
