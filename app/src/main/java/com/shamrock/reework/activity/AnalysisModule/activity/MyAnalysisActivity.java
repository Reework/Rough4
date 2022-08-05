package com.shamrock.reework.activity.AnalysisModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ColoriesAnalysisActivty;
import com.shamrock.reework.activity.AnalysisModule.activity.food.FoodNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.mind.MindAnalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.SleepNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.useractivity.ActivityNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.weight.WeightAnalysis;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.RescoreModule.activity.RescoreIntroActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ProfileDynamicHealthparamActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ReeevaluateHealthparamActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.response.GetRecoachByUserResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MyAnalysisActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Toolbar toolbar;
    RelativeLayout rlReescore, rlBcaReport, rlCalories, rlMyActivity, rlWater, rlSleep,
            rlStress, rlNutrition, rlReeplan, rlBloodReport,rlweightsanalysis,rlReeevaluate;
    private String mSubscription;
    ArrayList<String> subscription_List;

    private int userId;
    private int recoachId;

    SessionManager sessionManager;
    HomeFragmentService service;
    Utils utils;
    String mindStatus;
    int bingQuantity = 0;

    int recoachResonseCode = 0;
    String reaochnotAssingMSg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_analysis);
        mContext = MyAnalysisActivity.this;
        rlReeevaluate=findViewById(R.id.rlReeevaluate);
        sessionManager = new SessionManager(mContext);
        service = Client.getClient().create(HomeFragmentService.class);
        utils = new Utils();
        TextView txt_CaloriesConsumption=findViewById(R.id.txt_CaloriesConsumption);
        txt_CaloriesConsumption.setSelected(true);
        TextView txt_trend_water=findViewById(R.id.txt_trend_water);
        txt_trend_water.setSelected(true);
        mSubscription = sessionManager.getStringValue(SessionManager.KEY_USER_SUBSCRIPTION_LIST);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        TextView txtrescore=findViewById(R.id.txtrescore);
        txtrescore.setSelected(true);
        try {
            JSONArray obj = new JSONArray(mSubscription);
            subscription_List = new ArrayList<>();
            if (obj != null)
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject object = obj.getJSONObject(i);
                    String name = object.getString("FeatureName");
                    subscription_List.add(name);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        setToolBar();
        findViews();
        if (Utils.isNetworkAvailable(mContext)) {
            if (recoachId == 0) {
                CallToFetchRecoachId(false);
            } else {
                CallToGetInitialData(false);
            }
        } else
            Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
    }


    /* CALL TO FETCH RECOACH ID */
    private void CallToFetchRecoachId(final boolean isSwipeToRefresh) {
        if (!isSwipeToRefresh)
            utils.showProgressbar(mContext);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userId);

        Call<GetRecoachByUserResponse> call = service.GetReecoachId(request);
        call.enqueue(new Callback<GetRecoachByUserResponse>() {
            @Override
            public void onResponse(Call<GetRecoachByUserResponse> call, retrofit2.Response<GetRecoachByUserResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    GetRecoachByUserResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (listResponse.getData() != null) {
                            recoachId = listResponse.getData().getReecoachId();
                            sessionManager.setIntValue(SessionManager.KEY_USER_REECOACH_ID, recoachId);

                            /*CALL TO GET ALL BASIC DAILY INFO */
                            CallToGetInitialData(isSwipeToRefresh);
                        }
                    } else {
                        //Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (!isSwipeToRefresh)
                            utils.hideProgressbar();

                    }
                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();

                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();


                }
            }

            @Override
            public void onFailure(Call<GetRecoachByUserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

            }
        });
    }


    /* CALL TO GET INITIAL DATA FROM SERVER */
    private void CallToGetInitialData(final boolean isSwipeToRefresh) {
        HomeFragmentRequest request = new HomeFragmentRequest();
        /*request.setCreatedOn(lsCurrentDate);*/
        request.setReeworkerID(userId);
        request.setReecoachID(recoachId);

        Call<HomeFragmentResponse> call = service.getInitialData(request);
        call.enqueue(new Callback<HomeFragmentResponse>() {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response) {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    recoachResonseCode = response.body().getCode();


//                    rlBcaReport.setEnabled(true);
//                    rlCalories.setEnabled(true);
//                    rlMyActivity.setEnabled(true);
//                    rlWater.setEnabled(true);
//                    rlSleep.setEnabled(true);
//                    rlStress.setEnabled(true);
//                    rlNutrition.setEnabled(true);
//                    rlReeplan.setEnabled(true);
//                    rlBloodReport.setEnabled(true);

                    HomeFragmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (listResponse.getData() != null) {
                            mindStatus = listResponse.getData().getActualMindStatus();
                            bingQuantity = listResponse.getData().getIsBingeOnLargeQuantity();
                            if (mindStatus == null) {
                                mindStatus = "";
                            }
                        }
                    } else {
                        if (!isSwipeToRefresh)
                            utils.hideProgressbar();

                        recoachResonseCode = response.body().getCode();
                        reaochnotAssingMSg = response.body().getMessage();
//                        rlBcaReport.setEnabled(false);
//                        rlCalories.setEnabled(false);
//                        rlMyActivity.setEnabled(false);
//                        rlWater.setEnabled(false);
//                        rlSleep.setEnabled(false);
//                        rlStress.setEnabled(false);
//                        rlNutrition.setEnabled(false);
//                        rlReeplan.setEnabled(false);
//                        rlBloodReport.setEnabled(false);

//                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();
            }
        });
    }


    private void init() {

    }

    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Analytics");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        rlReescore = findViewById(R.id.rlReescore);
//        rlBcaReport = findViewById(R.id.rlBcaReport);
        rlCalories = findViewById(R.id.rlCaloriesConsumption);
        rlMyActivity = findViewById(R.id.rlMyActivity);
        rlWater = findViewById(R.id.rlWater);
        rlSleep = findViewById(R.id.rlSleep);
        rlStress = findViewById(R.id.rlStress);
        rlNutrition = findViewById(R.id.rlNutrition);
        rlReeplan = findViewById(R.id.rlReeplan);
        rlBloodReport = findViewById(R.id.rlBloodReport);
        rlweightsanalysis = findViewById(R.id.rlweightsanalysis);

        rlReescore.setOnClickListener(this);
//        rlBcaReport.setOnClickListener(this);
        rlCalories.setOnClickListener(this);
        rlMyActivity.setOnClickListener(this);
        rlWater.setOnClickListener(this);
        rlSleep.setOnClickListener(this);
        rlStress.setOnClickListener(this);
        rlNutrition.setOnClickListener(this);
        rlReeplan.setOnClickListener(this);
        rlBloodReport.setOnClickListener(this);
        rlweightsanalysis.setOnClickListener(this);
        rlReeevaluate.setOnClickListener(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlReescore:

                startActivity(new Intent(mContext, RescoreIntroActivity.class));

                break;

            case R.id.rlReeevaluate:

                Intent intent2=new Intent(this, ReeevaluateHealthparamActivity.class);
                intent2.putExtra("ISFromReevaluate",true);
                startActivity(intent2);
                break;





            case R.id.rlCaloriesConsumption:


                Intent intent = new Intent(mContext, ColoriesAnalysisActivty.class);
                startActivity(intent);


                break;

            case R.id.rlMyActivity:
//                Intent intent1 = new Intent(mContext, ActivityNapMainActivity.class);
//                startActivity(intent1);
                Intent intent7=new Intent(mContext, ActivityNapMainActivity.class);
                intent7.putExtra("ISFromANnalysis",true);
                startActivity(intent7);

                break;


            case R.id.rlweightsanalysis:


                startActivity(new Intent(mContext, WeightAnalysis.class));
                break;

            case R.id.rlWater:

                Intent intent5=new Intent(mContext, WaterAnalysisActivity.class);
                intent5.putExtra("ISFromANnalysis",true);
                startActivity(intent5);





                break;

            case R.id.rlSleep:

                Intent intent3=new Intent(mContext, SleepAnalysisActivity.class);
                intent3.putExtra("ISFromANnalysis",true);
                startActivity(intent3);



                break;

            case R.id.rlStress:

                Intent intent8=new Intent(mContext, MindAnalysis.class);
                intent8.putExtra("ISFromANnalysis",true);
                startActivity(intent8);

                break;

            case R.id.rlNutrition:



                Intent intent6=new Intent(mContext, MicrorlNutritionActivityAnalysis.class);
                intent6.putExtra("ISFromANnalysis",true);
                startActivity(intent6);

                break;

            case R.id.rlReeplan:


                Intent intent4=new Intent(mContext, SleepNapMainActivity.class);
                intent4.putExtra("ISFromANnalysis",true);
                startActivity(intent4);


                break;

            case R.id.rlBloodReport:

                startActivity(new Intent(mContext, FoodNapMainActivity.class));


                break;

            default:
        }
    }

    private static boolean containsName(final List<String> transaction, final String search) {
        for (final String transactionLine : transaction) {
            if (transactionLine.equals(search)) {
                return true;
            }
        }

        return false;
    }


}
