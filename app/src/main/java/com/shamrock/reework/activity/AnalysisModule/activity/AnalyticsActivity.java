package com.shamrock.reework.activity.AnalysisModule.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ColoriesAnalysisActivty;
import com.shamrock.reework.activity.AnalysisModule.activity.food.FoodAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.food.FoodNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.mind.MindAnalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.SleepNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.useractivity.ActivityNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.weight.WeightAnalysis;
import com.shamrock.reework.activity.BloodTestModule.activity.BCAReportActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.PathoReportActivity;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MyPlansModule.activity.NewMyPlansActivity;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.RescoreModule.activity.ReescoreActivity;
import com.shamrock.reework.activity.RescoreModule.activity.RescoreIntroActivity;
import com.shamrock.reework.activity.RescoreModule.model.ClsReescoreMianClass;
import com.shamrock.reework.activity.RescoreModule.service.RescoreService;
import com.shamrock.reework.activity.aNewInterpretation.model.ClsReescoreMain;
import com.shamrock.reework.activity.reeworkerhealth.app.ProfileDynamicHealthparamActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ReeevaluateHealthparamActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.response.GetRecoachByUserResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class AnalyticsActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    Toolbar toolbar;
    RelativeLayout rlReescore, rlBcaReport, rlFood, rlMyActivity, rlWater, rlSleep,
            rlStress, rlNutrition, rlReeplan, rlBloodReport,rlweightsanalysis,rlReeevaluate,rlmy_bca_report;
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

    TextView txt_reescore;
    CircularProgressBar progress_circular_consumed1;
    ClsReescoreMianClass reescoreMianClassl1;
    RescoreService service12 ;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_analytics);
        mContext = AnalyticsActivity.this;
        rlReeevaluate=findViewById(R.id.rlReeevaluate);
        rlmy_bca_report=findViewById(R.id.rlmy_bca_report);
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
        txt_reescore = findViewById(R.id.txt_reescore);
        progress_circular_consumed1 = findViewById(R.id.progress_circular_consumed1);
        service12 = Client.getClient().create(RescoreService.class);
//        txtrescore.setSelected(true);
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

        findViews();

        String userName = "Hi "+sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME) + " " +
                sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME)+"!";
        TextView userfullname=findViewById(R.id.userfullname);
        userfullname.setText(userName);
        String   userPhoto = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);

        ShapeableImageView imageView = findViewById(R.id.image_view);
//        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, (float) 4.0)
                .build());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MyProfileActivity.class);
                startActivity(intent);

            }
        });
        if (isValidContextForGlide(mContext)) {

            Glide
                    .with(mContext)
                    .load(userPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);


        }


        callRescoreDataAPI();
        callInterpretationApi();

        if (Utils.isNetworkAvailable(mContext)) {
            if (recoachId == 0) {
                CallToFetchRecoachId(false);
            } else {
                CallToGetInitialData(false);
            }
        } else
            Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();




        notificationService = Client.getClient().create(NotificationService.class);
        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
//                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
//                        PushFcmToServer();
                    } else if (intent.getAction().equals(FcmConstants.PUSH_NOTIFICATION)) // new push notification is received
                    {
                        String title = intent.getStringExtra(FcmConstants.FCM_TITLE);
                        String message = intent.getStringExtra(FcmConstants.FCM_MESSEGE);
                        mNotifcationCount = intent.getIntExtra(FcmConstants.FCM_COUNT, 0);

                        if (mNotifcationCount > 0) {
                            tvNotificationCOunt.setText(String.valueOf(mNotifcationCount));
                            invalidateOptionsMenu();
                        } else {
                            if (Utils.isNetworkAvailable(context))
                                GetAllNotificationCount();
                            else
                                Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if (Utils.isNetworkAvailable(mContext))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();






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

    private void callInterpretationApi() {

        if (!((Activity) mContext).isFinishing()) {
            utils.showProgressbar(mContext);
        }

       String submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        Call<ClsReescoreMain> call = service12.GetInterpretationByReeWorkerIDAPI(userId,submitHistoryDate);
        call.enqueue(new Callback<ClsReescoreMain>() {
            @Override
            public void onResponse(Call<ClsReescoreMain> call, Response<ClsReescoreMain> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsReescoreMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

//                        Intent intent = new Intent(context, InterpretationActivity.class);
//                        intent.putExtra("RESCORE_LIST", mDataList);
//                        intent.putExtra("isFirstTime", isFirstTime);
//                        intent.putExtra("NEwInterpretation", listResponse.getData());
//                        startActivity(intent);
                        txt_reescore.setText(Math.round(Float.parseFloat(listResponse.getData().getReeScore()))+"");

                        progress_circular_consumed1.setProgress((float) Float.parseFloat(listResponse.getData().getReeScore()));
                        progress_circular_consumed1.setProgressMax(100);

                    } else
                        Toast.makeText(mContext, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                }
//                    ShowRetryBar("");
            }

            @Override
            public void onFailure(Call<ClsReescoreMain> call, Throwable t) {
                Log.e("ReescoreActivity", t.toString());
                Toast.makeText(mContext, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                utils.hideProgressbar();
//                ShowRetryBar("");
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

        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("My Analysis");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        counterValuePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(), NotificationsActivity.class), 500);

            }
        });



        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }
    }

    private void findViews() {
        rlReescore = findViewById(R.id.rlReescore);
//        rlBcaReport = findViewById(R.id.rlBcaReport);
        rlFood = findViewById(R.id.rlFood);
        rlMyActivity = findViewById(R.id.rlMyActivity);
        rlWater = findViewById(R.id.rlWater);
        rlSleep = findViewById(R.id.rlSleep);
        rlStress = findViewById(R.id.rlStress);
//        rlNutrition = findViewById(R.id.rlNutrition);
        rlReeplan = findViewById(R.id.rel_patho_report);
        rlBloodReport = findViewById(R.id.rlBloodReport);
        rlweightsanalysis = findViewById(R.id.rlweightsanalysis);

        rlReescore.setOnClickListener(this);
//        rlBcaReport.setOnClickListener(this);
        rlFood.setOnClickListener(this);
        rlMyActivity.setOnClickListener(this);
        rlWater.setOnClickListener(this);
        rlSleep.setOnClickListener(this);
        rlStress.setOnClickListener(this);
//        rlNutrition.setOnClickListener(this);
        rlReeplan.setOnClickListener(this);
        rlBloodReport.setOnClickListener(this);
        rlweightsanalysis.setOnClickListener(this);
        rlReeevaluate.setOnClickListener(this);
        rlmy_bca_report.setOnClickListener(this);
    }



    private void callRescoreDataAPI()
    {

        RescoreService service = Client.getClient().create(RescoreService.class);
        sessionManager = new SessionManager(mContext);
        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        Call<ClsReescoreMianClass> call = service.GetReeScoreMessage();
        call.enqueue(new Callback<ClsReescoreMianClass>()
        {
            @Override
            public void onResponse(Call<ClsReescoreMianClass> call, Response<ClsReescoreMianClass> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsReescoreMianClass listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        if (listResponse.getData()!=null){

                            reescoreMianClassl1=listResponse;
                            for (int i = 0; i < listResponse.getData().size(); i++) {

                                if (listResponse.getData().get(i).getMessageType().equalsIgnoreCase("What is Reescore")){
//                                    txt_reescore.setText(listResponse.getData().get(i).getMessage());

                                    break;

                                }
                            }
//                            callInterpretationApi();
                        }




                    }
                    else
                        Toast.makeText(mContext, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ClsReescoreMianClass> call, Throwable t)
            {
                Log.e("ReescoreActivity", t.toString());
                utils.hideProgressbar();
            }
        });
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

//                startActivity(new Intent(mContext, RescoreIntroActivity.class));
//
//                ReescoreActivity
                Intent intent12 = new Intent(mContext, ReescoreActivity.class);
                intent12.putExtra("REESCORE_DATA",reescoreMianClassl1);
                startActivity(intent12);
                break;

            case R.id.rlReeevaluate:

                Intent intent2=new Intent(this, ReeevaluateHealthparamActivity.class);
                intent2.putExtra("ISFromReevaluate",true);
                startActivity(intent2);
                break;





            case R.id.rlFood:


                Intent intent = new Intent(mContext, FoodAnalysisActivity.class);
                startActivity(intent);


                break;

            case R.id.rlMyActivity:
                Intent intent1 = new Intent(mContext, ActivityNapMainActivity.class);
                startActivity(intent1);
                break;


            case R.id.rlweightsanalysis:


                startActivity(new Intent(mContext, NewMyPlansActivity.class));
                break;

            case R.id.rlWater:


                startActivity(new Intent(mContext, WaterAnalysisActivity.class));



                break;

            case R.id.rlSleep:

                startActivity(new Intent(mContext, SleepAnalysisActivity.class));



                break;

            case R.id.rlStress:

                startActivity(new Intent(mContext, MindAnalysis.class));

                break;

            case R.id.rlNutrition:


                startActivity(new Intent(mContext,MicrorlNutritionActivityAnalysis.class));
                break;

            case R.id.rel_patho_report:

//                startActivity(new Intent(mContext, SleepNapMainActivity.class));
                startActivity(new Intent(mContext, PathoReportActivity .class));


                break;

            case R.id.rlBloodReport:

                startActivity(new Intent(mContext, FoodNapMainActivity.class));


                break;


            case R.id.rlmy_bca_report:
                startActivity(new Intent(mContext, BCAReportActivity.class));
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


    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userId);

        Call<NotifCountResponse> call = notificationService.getAllNotificationCount(request);

        call.enqueue(new Callback<NotifCountResponse>() {
            @Override
            public void onResponse(Call<NotifCountResponse> call, Response<NotifCountResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    NotifCountResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mNotifcationCount = listResponse.getData();

                        invalidateOptionsMenu();
                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifCountResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }



}
