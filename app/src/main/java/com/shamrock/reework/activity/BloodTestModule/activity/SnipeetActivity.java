package com.shamrock.reework.activity.BloodTestModule.activity;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.ApplicationSnippetModule.ApplicationSnippetActivity;
import com.shamrock.reework.activity.ApplicationSnippetModule.ClsSnippetListAdapter;
import com.shamrock.reework.activity.ApplicationSnippetModule.ClsSnippetPojo;
import com.shamrock.reework.activity.ApplicationSnippetModule.SnippetData;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnipeetActivity extends AppCompatActivity {
    Toolbar toolbar;
    UnfreezeService unfreezeService;
    Utils utils;
    private SessionManager sessionManager;
    private int userId;

    RecyclerView recler_snippet;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snipeet);
        setToolBar();

        recler_snippet=findViewById(R.id.recler_snippet);
        context = SnipeetActivity.this;
        unfreezeService = Client.getClient().create(UnfreezeService.class);
        sessionManager=new SessionManager(this);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        utils = new Utils();
        sessionManager = new SessionManager(context);
        WebView web_snipeet=findViewById(R.id.web_snipeet);
        web_snipeet.loadData("<html><head></head><body><b>1.REENGAGE:</b> Log in your details and set your profile information." +
                "<P><b>2.REETEST</b>: Pathology Test\n</p>" +
                "<p><b>3.REEASSESSMENT</b>: Know your current health, body vitals, Pathology Test results, BCA, body measurements.\n</p>" +
                "<p><b>4.REESCORE</b>: Reescore is an efficient and scientifically derived algorithm-based scoring system that assesses the quality of your lifestyle, nutrition, sleep pattern, hydration levels and mindfulness.\n" +
                "The score represents your overall health ...\n" +
                "Higher the score means you are following a holistic approach towards optimizing your overall health.\n</p>" +
                "<p><b>REEVALUATE</b>: Detailed questionnaire for your REECOACH to understand you better to help you achieve your health goals.\n</p>" +
                "<p><b>REEPLANS</b> : plan-based study to evaluate your daily, weekly and monthly goals:\n</p>" +
                "<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LIFESTYLE PLAN\n</b></p>" +
                "<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;NUTRITION PLAN\n</b></p>" +
                "<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FOOD PLAN\n</b></p>" +
                "<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SUPPLEMENT PLAN\n</b></p>" +
                "<p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CHEAT PLAN\n</b></p>" +
                "<p><b>6.REEPORTS</b>: Personal WELLNESS Reports, PWR comprises of a technical interpretation by Reework of your current health and lifestyle status.\n</p>" +
                "<p><b>7.REENGAGE</b>: Start logging your activities, nutrition, water, sleep and mood of the day\n</p>" +
                "<p><b>8.REEVITALISE</b>: Track progress of your health.\n</p>" +
                "<p><b>9.REEFOCUS</b>: et instant reminders, notifications for better adherence with constant monitoring and motivations.\n</p>" +
                "<p><b>REESTORE</b>: BUY online health foods, health monitoring gadgets, health supplements.</p></body></html>","text/html", "utf-8");

        callForUsrFreezStatus();
    }

    private void setToolBar() {

        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Snippet");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SnipeetActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();


                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }


    private void callForUsrFreezStatus()
    {
        utils.showProgressbar(context);


        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userId);

        Call<ClsSnippetPojo> call = unfreezeService.getSnippetMessage();
        call.enqueue(new Callback<ClsSnippetPojo>()
        {
            @Override
            public void onResponse(Call<ClsSnippetPojo> call, Response<ClsSnippetPojo> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    ArrayList<SnippetData> arylst_snippet=response.body().getData();


                    ClsSnippetListAdapter adapter=  new ClsSnippetListAdapter(SnipeetActivity.this, arylst_snippet);
                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(SnipeetActivity.this);
                    recler_snippet.setLayoutManager(layoutManager1);
                    recler_snippet.setItemAnimator(new DefaultItemAnimator());
                    recler_snippet.setAdapter(adapter);


                }

            }
            @Override
            public void onFailure(Call<ClsSnippetPojo> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }
}
