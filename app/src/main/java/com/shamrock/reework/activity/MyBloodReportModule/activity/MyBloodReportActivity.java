package com.shamrock.reework.activity.MyBloodReportModule.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import com.shamrock.reework.activity.MyBloodReportModule.adapter.MyBloodReportAdapter;
import com.shamrock.reework.activity.MyBloodReportModule.service.BloodReportItemModel;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.AnalysisModule.service.MyAnalysisService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.response.BloodReportResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBloodReportActivity extends AppCompatActivity
{
    Context context;
    Toolbar toolbar;
    MyAnalysisService service;
    Utils utils;
    SessionManager sessionManager;

    RecyclerView recyclerView;
    MyBloodReportAdapter bloodReportAdapter;
    ArrayList<BloodReportItemModel> bloodList;
    ArrayList<BloodReportResponse.BloodReportData> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_blood_report);
        context = MyBloodReportActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        utils = new Utils();
        service = Client.getClient().create(MyAnalysisService.class);
        sessionManager = new SessionManager(context);

        bloodList = new ArrayList<>();
    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("My Blood Report");
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
        recyclerView = findViewById(R.id.rvBloodReport);

        if (Utils.isNetworkAvailable(context))
            callToGetBloodReport();
        else
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
    }

    private void callToGetBloodReport()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(4173);

        Call<BloodReportResponse> call = service.getBloodReport(request);
        call.enqueue(new Callback<BloodReportResponse>()
        {
            @Override
            public void onResponse(Call<BloodReportResponse> call, Response<BloodReportResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    BloodReportResponse bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode() == 1)
                    {
                        mList = bcaResponse.getData();

                        if (mList != null && mList.size() > 0)
                        {
                            String FastingSugar = mList.get(0).getFastingSugar();
                            String BP = mList.get(0).getBP();
                            String PostPrandialSugar = mList.get(0).getPostPrandialSugar();
                            String HBA1c = mList.get(0).getHBA1c();
                            String Creatinine = mList.get(0).getCreatinine();
                            String urea = mList.get(0).getUrea();
                            String T3 = mList.get(0).getT3();
                            String Haemoglobin = mList.get(0).getHaemoglobin();
                            String Potassium = mList.get(0).getPotassium();
                            String Sodium = mList.get(0).getSodium();
                            String Calcium = mList.get(0).getCalcium();
                            String Magnesium = mList.get(0).getMagnesium();
                            String Phosphorous = mList.get(0).getPhosphorous();
                            String VitB12 = mList.get(0).getVitB12();
                            String FolicAcid = mList.get(0).getFolicAcid();
                            String Homocysteine = mList.get(0).getHomocysteine();
                            String VitD = mList.get(0).getVitD();
                            String TotalCholesterol = mList.get(0).getTotalCholesterol();
                            String Triglycerides = mList.get(0).getTriglycerides();
                            String HDLCholesterol = mList.get(0).getHDLCholesterol();
                            String LDLCholesterol = mList.get(0).getLDLCholesterol();
                            String Uricacid = mList.get(0).getUricacid();
                            String T4 = mList.get(0).getT4();
                            String TSH = mList.get(0).getTSH();
                            String SHBG = mList.get(0).getSHBG();
                            String ProteinTotal = mList.get(0).getProteinTotal();
                            String Albumin = mList.get(0).getAlbumin();
                            String BilirubinTotal = mList.get(0).getBilirubinTotal();
                            String SGOT = mList.get(0).getSGOT();
                            String SGPT = mList.get(0).getSGPT();
                            String CreactiveProtein = mList.get(0).getCreactiveProtein();

                            bloodList.clear();
                            bloodList.add(new BloodReportItemModel("Fasting Sugar", FastingSugar));
                            bloodList.add(new BloodReportItemModel("BP", BP));
                            bloodList.add(new BloodReportItemModel("Post Prandial Sugar", PostPrandialSugar));
                            bloodList.add(new BloodReportItemModel("HBA1c", HBA1c));
                            bloodList.add(new BloodReportItemModel("Creatinine", Creatinine));
                            bloodList.add(new BloodReportItemModel("urea", urea));
                            bloodList.add(new BloodReportItemModel("T3", T3));
                            bloodList.add(new BloodReportItemModel("Haemoglobin", Haemoglobin));
                            bloodList.add(new BloodReportItemModel("Potassium", Potassium));
                            bloodList.add(new BloodReportItemModel("Sodium", Sodium));
                            bloodList.add(new BloodReportItemModel("Calcium", Calcium));
                            bloodList.add(new BloodReportItemModel("Magnesium", Magnesium));
                            bloodList.add(new BloodReportItemModel("Phosphorous", Phosphorous));
                            bloodList.add(new BloodReportItemModel("VitB12", VitB12));
                            bloodList.add(new BloodReportItemModel("FolicAcid", FolicAcid));
                            bloodList.add(new BloodReportItemModel("Homocysteine", Homocysteine));
                            bloodList.add(new BloodReportItemModel("VitD", VitD));
                            bloodList.add(new BloodReportItemModel("Total Cholesterol", TotalCholesterol));
                            bloodList.add(new BloodReportItemModel("Triglycerides", Triglycerides));
                            bloodList.add(new BloodReportItemModel("HDL Cholesterol", HDLCholesterol));
                            bloodList.add(new BloodReportItemModel("LDL Cholesterol", LDLCholesterol));
                            bloodList.add(new BloodReportItemModel("Uricacid", Uricacid));
                            bloodList.add(new BloodReportItemModel("T4", T4));
                            bloodList.add(new BloodReportItemModel("TSH", TSH));
                            bloodList.add(new BloodReportItemModel("SHBG", SHBG));
                            bloodList.add(new BloodReportItemModel("Protein Total", ProteinTotal));
                            bloodList.add(new BloodReportItemModel("Albumin", Albumin));
                            bloodList.add(new BloodReportItemModel("Bilirubin Total", BilirubinTotal));
                            bloodList.add(new BloodReportItemModel("SGOT", SGOT));
                            bloodList.add(new BloodReportItemModel("SGPT", SGPT));
                            bloodList.add(new BloodReportItemModel("Creactive Protein", CreactiveProtein));

                            bloodReportAdapter = new MyBloodReportAdapter(context, bloodList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyBloodReportActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(bloodReportAdapter);
                        }
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BloodReportResponse> call, Throwable t)
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
