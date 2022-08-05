package com.shamrock.reework.activity.SubscriptionModule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamrock.R;

public class SubscriptionActivity extends AppCompatActivity implements View.OnClickListener
{

    Context context;
    Toolbar toolbar;
    Typeface font;
    LinearLayout linearLayoutAbout, linearLayoutSkip;
    TextView txtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        context = SubscriptionActivity.this;
        init();
        setToolBar();
        findViews();
    }

    private void init(){}

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Membership Plan");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        linearLayoutAbout = findViewById(R.id.linSubscription_About);
        //linearLayoutSkip = findViewById(R.id.linSubscription_Skip);
        txtSkip = findViewById(R.id.textSubscription_Skip);

        linearLayoutAbout.setOnClickListener(this);
        //linearLayoutSkip.setOnClickListener(this);
        txtSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.linSubscription_About:
                startActivity(new Intent(context, SubscriptionFeaturesActivity.class));
                break;

            case R.id.textSubscription_Skip:
                //startActivity(new Intent(context, SubscriptionFeaturesActivity.class));
                finish();
                //Toast.makeText(context, "next will be Home Page??", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}
