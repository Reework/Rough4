package com.shamrock.reework.activity.BCAModule.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.BCAModule.adapter.MyBCAReportAdapter;
import com.shamrock.reework.activity.BCAModule.service.BCAReportItem;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.AnalysisModule.service.MyAnalysisService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.BcaResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBCAReportActivity extends AppCompatActivity
{
    Context context;
    Toolbar toolbar;
    MyAnalysisService service;
    Utils utils;
    SessionManager sessionManager;

    RecyclerView recyclerView;
    MyBCAReportAdapter bcaReportAdapter;
    ArrayList<BCAReportItem> bcaList;
    ArrayList<BcaResponse.BcaReport> mList;
    List<BCAResponce.Datum> mBCAList;
    List<BCAResponce.Datum> mBCAList_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bcareport);
        context = MyBCAReportActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        utils = new Utils();
        sessionManager = new SessionManager(context);
        service = Client.getClient().create(MyAnalysisService.class);

        bcaList = new ArrayList<>();
    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("My BCA Report");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void findViews()
    {
        recyclerView = findViewById(R.id.rvMyBCAReport);

        if (Utils.isNetworkAvailable(context))
           // callToGetBcaReport();
            callToGetBCAReport();
        else
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
    }


    private void callToGetBCAReport()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);////4186


        Call<BCAResponce> call = service.getMyBCAHistory(String.valueOf(userId));
        call.enqueue(new Callback<BCAResponce>()
        {
            @Override
            public void onResponse(Call<BCAResponce> call, Response<BCAResponce> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    BCAResponce bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode() == 1)
                    {
                        mBCAList = bcaResponse.getData();
                        mBCAList_filter=new ArrayList<>();
                        if (mBCAList != null && mBCAList.size() > 0)
                        {

                            for (int i = 0; i <mBCAList.size() ; i++) {
                                if (mBCAList.get(i).getReportType().equalsIgnoreCase("BCA Test")){
                                    mBCAList_filter.add(mBCAList.get(i));
                                }

                            }


                            bcaReportAdapter = new MyBCAReportAdapter(context, mBCAList_filter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyBCAReportActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(bcaReportAdapter);
                        }
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BCAResponce> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }



    private void callToGetBcaReport()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);//4186

        Call<BcaResponse> call = service.getBcaReport(request);
        call.enqueue(new Callback<BcaResponse>()
        {
            @Override
            public void onResponse(Call<BcaResponse> call, Response<BcaResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    BcaResponse bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode() == 1)
                    {
                        mList = bcaResponse.getData();

                        if (mList != null && mList.size() > 0)
                        {
                            int bodyFat = mList.get(0).getBodyFat();
                            int visceralBodyFat = mList.get(0).getVisceralBodyFat();
                            int skeletalMuscle = mList.get(0).getSkeletalMuscle();
                            int totalBodyWater = mList.get(0).getTotalBodyWater();
                            int BMI = mList.get(0).getBMI();
                            int BMR = mList.get(0).getBMR();

                            bcaList.clear();
                            bcaList.add(new BCAReportItem("Body Fat", bodyFat));
                            bcaList.add(new BCAReportItem("Visceral Body Fat", visceralBodyFat));
                            bcaList.add(new BCAReportItem("Skeletal Muscle Fat", skeletalMuscle));
                            bcaList.add(new BCAReportItem("Total Body Water", totalBodyWater));
                            bcaList.add(new BCAReportItem("BMI", BMI));
                            bcaList.add(new BCAReportItem("BMR", BMR));

                        /*    bcaReportAdapter = new MyBCAReportAdapter(context, bcaList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyBCAReportActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(bcaReportAdapter);*/
                        }
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BcaResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
