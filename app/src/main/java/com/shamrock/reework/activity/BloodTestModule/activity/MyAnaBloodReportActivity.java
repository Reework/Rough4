package com.shamrock.reework.activity.BloodTestModule.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.BCAModule.adapter.MyBCAReportAdapter;
import com.shamrock.reework.activity.BCAModule.service.BCAReportItem;
import com.shamrock.reework.activity.BloodTestModule.adapter.BloodReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.BloodTestReportRequest;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.BcaResponse;
import com.shamrock.reework.api.response.BloodReportItem;
import com.shamrock.reework.api.response.BloodTestReportResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAnaBloodReportActivity extends AppCompatActivity
{

    Context context;
    Toolbar toolbar;
    Typeface font;
    ListView listView;
    List<BloodReportItem> BloodReportItemArrayList;
    private ReeTestService reeTestService;
    private SessionManager sessionManager;
    private Utils utils;
    private int userID;
    BloodReportAdapter bloodReportAdapter;


    RecyclerView recyclerView;
    MyBCAReportAdapter bcaReportAdapter;
    ArrayList<BCAReportItem> bcaList;
    ArrayList<BcaResponse.BcaReport> mList;
    List<BCAResponce.Datum> mBCAList;
  private   List<BCAResponce.Datum> mBCAList_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_report);

        context = MyAnaBloodReportActivity.this;
        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        BloodReportItemArrayList = new ArrayList<>();

        sessionManager = new SessionManager(context);
        reeTestService = Client.getClient().create(ReeTestService.class);
        utils = new Utils();
        
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Blood Reports");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
     //   listView = findViewById(R.id.listViewBloodReport);

        recyclerView = findViewById(R.id.rvMyBCAReport);
        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

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


        Call<BCAResponce> call = reeTestService.getAllBloodReportNewHistory(String.valueOf(userId));
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

                        if (mBCAList != null && mBCAList.size() > 0)
                        {

                            mBCAList_filter=new ArrayList<>();


                            for (int i = 0; i <mBCAList.size() ; i++) {
                                if (!mBCAList.get(i).getReportType().equalsIgnoreCase("BCA Test")){
                                    mBCAList_filter.add(mBCAList.get(i));
                                }

                            }

                            if (!mBCAList_filter.isEmpty()){
                                bcaReportAdapter = new MyBCAReportAdapter(context, mBCAList_filter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyAnaBloodReportActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(bcaReportAdapter);
                            }else {
                                Toast.makeText(MyAnaBloodReportActivity.this, "No Blood Reports available", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }else {

                            Toast.makeText(MyAnaBloodReportActivity.this, "No Blood Reports available", Toast.LENGTH_SHORT).show();
                            finish();
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



    private void CallToGetAllBloodTestReports(int userId)
    {
        utils.showProgressbar(context);

        BloodTestReportRequest request = new BloodTestReportRequest();
        request.setUserID(String.valueOf(userId));

        Call<BloodTestReportResponse> call = reeTestService.getBloodTestReports(request);
        call.enqueue(new Callback<BloodTestReportResponse>()
        {
            @Override
            public void onResponse(Call<BloodTestReportResponse> call, Response<BloodTestReportResponse> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    BloodTestReportResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        BloodReportItemArrayList = listResponse.getData();

                        if (BloodReportItemArrayList != null && !BloodReportItemArrayList.isEmpty())
                        {
                            bloodReportAdapter = new BloodReportAdapter(context, BloodReportItemArrayList);
                           // listView.setAdapter(bloodReportAdapter);
                        }
                        else
                            Snackbar.make(findViewById(android.R.id.content), "No reports available !", Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<BloodTestReportResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void ShowRetryBar(String msg)
    {

        String strMessage;

        if (TextUtils.isEmpty(msg))
        {
            strMessage = "Unable to load data";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        CallToGetAllBloodTestReports(userID);
                    }
                });

        snackbar.show();
    }

}
