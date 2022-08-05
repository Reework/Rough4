package com.shamrock.reework.activity.InterpretationModule.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.Welcome_ScheduleBloodTestActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.InterpretationModule.adapter.InterpretationViewMoreAdapter;
import com.shamrock.reework.activity.InterpretationModule.service.InterpretationService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.aNewInterpretation.model.BCAParamListAdapter;
import com.shamrock.reework.activity.aNewInterpretation.model.BCAParams;
import com.shamrock.reework.activity.aNewInterpretation.model.PathoParamListAdapter;
import com.shamrock.reework.activity.aNewInterpretation.model.PathoParams;
import com.shamrock.reework.activity.aNewInterpretation.model.ReescoreData;
import com.shamrock.reework.activity.aNewInterpretation.model.WellnessListAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.InterpretationRequest;
import com.shamrock.reework.api.response.InterpretationResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.RescoreResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterpretationViewMoreActivity extends AppCompatActivity implements View.OnClickListener
{
    Context context;
    Toolbar toolbar;
    Typeface font;
    ImageView imgNext;
    TextView tvCurrentBodyWeight, tvIdealBodyWeight, tvCurrentBmi, tvIdealBmi, tvCurrentActivityLevel,tvActualActivityLevel;
    SessionManager sessionManager;
    Utils utils;
    InterpretationService service;
    Button btn_Bacsk, btn_Home_Page;
    Button btn_Back;
    RecyclerView recyclerView;
    InterpretationViewMoreAdapter adapter;
    ArrayList<RescoreResponse.RescoreData> mList;
    boolean isFirstTime;
    RecyclerView recyler_bca_param;
    private ReescoreData NEwInterpretation_data;

    ImageView img_plus,img_plus1,img_minus,img_minus1;
    RelativeLayout layout_bca,layout_patho;

    private int userID;
//    private SessionManager sessionManager;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpretation_view_more);
        context = InterpretationViewMoreActivity.this;
        NEwInterpretation_data= (ReescoreData) getIntent().getSerializableExtra("NEWINTERPRETATIONDATA");
        img_plus = findViewById(R.id.img_plus);
        img_plus1 = findViewById(R.id.img_plus1);
        img_minus = findViewById(R.id.img_minus);
        img_minus1 = findViewById(R.id.img_minus1);
        layout_bca = findViewById(R.id.layout_bca);
        layout_patho = findViewById(R.id.layout_patho);
        init();

        findViews();


        setData(NEwInterpretation_data);

//        btn_Back = findViewById(R.id.btn_back_vp);
//        btn_Home_Page = findViewById(R.id.btn_next_homepage);

//        btn_Back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(InterpretationViewMoreActivity.this, InterpretationActivity.class) );
//                finish();
//            }
//        });

        findViewById(R.id.buttonSubmit_MyProfiless).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterpretationViewMoreActivity.this, HomeActivity.class) );
                finish();
            }
        });


        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_minus.setVisibility(View.VISIBLE);
                img_plus.setVisibility(View.GONE);
                layout_bca.setVisibility(View.VISIBLE);



            }
        });

        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_plus.setVisibility(View.VISIBLE);
                img_minus.setVisibility(View.GONE);
                layout_bca.setVisibility(View.GONE);



            }
        });


        img_plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_minus1.setVisibility(View.VISIBLE);
                img_plus1.setVisibility(View.GONE);
                layout_patho.setVisibility(View.VISIBLE);



            }
        });

        img_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_plus1.setVisibility(View.VISIBLE);
                img_minus1.setVisibility(View.GONE);
                layout_patho.setVisibility(View.GONE);



            }
        });



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

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();


    }

    private void setData(ReescoreData nEwInterpretation_data) {
        recyler_bca_param=findViewById(R.id.recyler_bca_param);
        RecyclerView  recyler_patho_param=findViewById(R.id.recyler_patho_param);
        if (NEwInterpretation_data.getbCAParams()!=null&&!NEwInterpretation_data.getbCAParams().isEmpty()){
            recyler_bca_param.setAdapter(new BCAParamListAdapter(context,nEwInterpretation_data.getbCAParams()));

        }else {
//            findViewById(R.id.ll_bca_parameter).setVisibility(View.GONE);
        }
        if (NEwInterpretation_data.getPathoParams()!=null&&!NEwInterpretation_data.getPathoParams().isEmpty()){
            recyler_patho_param.setAdapter(new PathoParamListAdapter(context,nEwInterpretation_data.getPathoParams()));

        }else {
//            findViewById(R.id.ll_patho_parameter).setVisibility(View.GONE);

        }
    }

    private void setDatas(ArrayList<BCAParams> bcaParams,ArrayList<PathoParams> pathoParams) {

    }

    private void init()
    {
        service = Client.getClient().create(InterpretationService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            try
            {
                mList = new ArrayList<>();
                if ((ArrayList<RescoreResponse.RescoreData>) extras.getSerializable("RESCORE_LIST")!=null){
                    mList = (ArrayList<RescoreResponse.RescoreData>) extras.getSerializable("RESCORE_LIST");

                }

                isFirstTime = extras.getBoolean("isFirstTime");

            }
            catch (Exception e){e.printStackTrace();}
        }
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        imgLeft.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.interpretation);
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
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

    private void findViews()
    {
//        imgNext = findViewById(R.id.imgViewNext_InterpretationViewMore);
//        imgNext.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case 555:


                if (isFirstTime){

                    if (sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("false")){
                        Intent intent = new Intent(this, Welcome_ScheduleBloodTestActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("isFirstTime", isFirstTime);
                        startActivity(intent);
                    }



//                    Intent intent = new Intent(this, ScheduleBloodTestActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();


                }else {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }



                break;
            default:
        }

    }

//    private void callToGetInterpretationViewMoreData()
//    {
//        if (!((Activity) context).isFinishing())
//        {
//            utils.showProgressbar(context);
//        }
//
//        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
//
//        InterpretationRequest request = new InterpretationRequest();
//        request.setUserID(userId);
//
//        Call<InterpretationResponse> call = service.getInterpretationData(request);
//        call.enqueue(new Callback<InterpretationResponse>()
//        {
//            @Override
//            public void onResponse(Call<InterpretationResponse> call, Response<InterpretationResponse> response)
//            {
//                utils.hideProgressbar();
//
//                if (response.code() == Client.RESPONSE_CODE_OK)
//                {
//                    InterpretationResponse interpretationResponse = response.body();
//
//                    if (interpretationResponse != null && interpretationResponse.getCode() == 1)
//                    {
//                        ArrayList<InterpretationResponse.Data>  tempList = interpretationResponse.getData();
//
//                        if (tempList != null && tempList.size() > 0)
//                        {
//                            int bodyWeightCurrentState = tempList.get(0).getBodyWeightCurrentState();
//                            int bodyWeightIdealState = tempList.get(0).getBodyWeightIdealState();
//                            double bMICurrentState = tempList.get(0).getBMICurrentState();
//                            double bMIIdealState = tempList.get(0).getBMIIdealState();
//                            String activityLevelCurrentState = tempList.get(0).getActivityLevelCurrentState();
//                            String activityLevelIdealState = tempList.get(0).getActivityLevelIdealState();
//
//                            if (bodyWeightCurrentState > 0){
//                                tvCurrentBodyWeight.setText(bodyWeightCurrentState+"");
//
//                                try {
//                                    if (bodyWeightCurrentState>bodyWeightIdealState){
//                                        tvCurrentBodyWeight.setTextColor(getResources().getColor(R.color.color_smooth_red));
//                                    }
//
//                                    if (bodyWeightCurrentState==bodyWeightIdealState){
//                                        tvCurrentBodyWeight.setTextColor(getResources().getColor(R.color.holo_green_darks));
//
//                                    }
//
//
//                                    if (bodyWeightCurrentState<bodyWeightIdealState){
//                                        tvCurrentBodyWeight.setTextColor(getResources().getColor(R.color.colorPrimary));
//
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//
//
//
//
//
//                            if (bodyWeightIdealState > 0)
//                                tvIdealBodyWeight.setText(bodyWeightIdealState+"");
//
//                            if (bMICurrentState > 0)
//                                tvCurrentBmi.setText(bMICurrentState+"");
//
//                            if (bMIIdealState > 0)
//                                tvIdealBmi.setText(bMIIdealState+"");
//
//                            if (!TextUtils.isEmpty(activityLevelCurrentState))
//                                tvCurrentActivityLevel.setText(activityLevelCurrentState);
//
//                            if (!TextUtils.isEmpty(activityLevelIdealState))
//                                tvActualActivityLevel.setText(activityLevelIdealState);
//                        }
//                    }
//                    else
//                    {
//                        Snackbar.make(findViewById(android.R.id.content), interpretationResponse.getMessage(), Snackbar.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InterpretationResponse> call, Throwable t)
//            {
//                utils.hideProgressbar();
//            }
//        });
//    }

    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

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
