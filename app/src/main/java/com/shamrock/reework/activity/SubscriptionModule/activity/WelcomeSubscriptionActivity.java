package com.shamrock.reework.activity.SubscriptionModule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.database.SessionManager;

public class WelcomeSubscriptionActivity extends AppCompatActivity implements View.OnClickListener
{

    Context context;
    //Toolbar toolbar;
    Typeface font;
    TextView tvMsg;
    Button btnTakeMeThroughApp;
    SessionManager sessionManager;
    int planId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);
        context = WelcomeSubscriptionActivity.this;
        init();
        //setToolBar();
        findViews();
    }

    private void init()
    {
        sessionManager = new SessionManager(context);

        planId = sessionManager.getIntValue(SessionManager.KEY_USER_PLAN_ID);
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     *//*
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Membership Plan");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }
*/
    private void findViews()
    {
        tvMsg = findViewById(R.id.tvMsg);
        btnTakeMeThroughApp = findViewById(R.id.buttonTakeMeThroughApp);
        btnTakeMeThroughApp.setOnClickListener(this);

        if (planId == 1)
        {
            tvMsg.setText("You have become a paid member now!!!");
        }
        else
        {
            tvMsg.setText("You have become a subscribed member now!!!");
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonTakeMeThroughApp:

                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                /*if (planId == 1)
                    startActivity(new Intent(context, Welcome_ScheduleBloodTestActivity.class));
                else
                    startActivity(new Intent(context, HomeActivity.class));
                finish();*/
                break;

            default:
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
