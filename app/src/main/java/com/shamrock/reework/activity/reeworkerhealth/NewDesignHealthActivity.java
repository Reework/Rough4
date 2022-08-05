package com.shamrock.reework.activity.reeworkerhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ClsNewHealthParamData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthModule.service.HealthRequest;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.Reecoachpathoselection.SelectReecoachPathActivity;
import com.shamrock.reework.activity.Reemember.controller.ReememberActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsPostHealthData;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsgetPostData;
import com.shamrock.reework.activity.reeworkerhealth.app.Data;
import com.shamrock.reework.activity.reeworkerhealth.app.Fragment1;
import com.shamrock.reework.activity.reeworkerhealth.app.Fragment2;
import com.shamrock.reework.activity.reeworkerhealth.app.Fragment3;
import com.shamrock.reework.activity.reeworkerhealth.app.Fragment4;
import com.shamrock.reework.activity.reeworkerhealth.app.StepperIndicator;
import com.shamrock.reework.adapter.FoodConditionsAdapter;
import com.shamrock.reework.adapter.MedicalConditionsAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.common.MedicalConditionData;
import com.shamrock.reework.common.NonSwipeableViewPager;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.dialog.NewCountryDialog;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewDesignHealthActivity extends AppCompatActivity implements View.OnClickListener, NewCountryDialog.GetCountryListener, MedicalConditionsAdapter.MedicalConditionListener, FoodConditionsAdapter.FoodConditionListener {
    private HealthParametersService healthParametersService;
    private SessionManager sessionManager;
    EditText edt_remark_medical_condition;
    private Utils utils;
    TextView tvTitle;

    RecyclerView reclycler_foodalergy;
    private ViewFlipper vp_health_profile;
    LinearLayout ll_remark_medical_condition,ll_remark_foodallergy_condition;
    private ArrayList<HealthParamData> ary_HealthParamDataArrayList;
    TextView btn_next_slide;
    RecyclerView reclycler_medicalcondition;
    public  boolean ISFreeze=false;

    ImageView imageView;
    boolean isfrompayemt;
    RadioButton rd_button_profile,rd_button_health;
    private HomeFragmentService service;
    boolean ISFromReevaluate=false;
    TextView txt_country,txt_state,txt_city,txt_foodculture;
    private NewCountryDialog countryDialog;
    TextView txt_pound,txt_cm,txt_ftinch,txt_bloodgroup;
    private int weight_AdditionParameter=1;
    private int height_AdditionParameter=1;
    int count=1;
    int count_viewflipper=0;
    View view1,view2,view3,view4,view5,view6,view7,view8;
    EditText edt_food_allergy_remark,edt_remark_health_suppliment,edt_remark_foodallergy_condition, edt_remark_emotionalsuffering, edt_remark_anyotherspecialmentionhealth;

    EditText edt_pincode,edt_weight,edt_height;
    TextView txt_pounds,txt_kgs;
    int[] view_test= new int[]{R.id.view1,R.id.view2,R.id.view3,R.id.view4};
    View view9,view10,view11,view12,view13,view14,view15,view16;
    private String str_rel_alchol="Yes";
    private String str_alcohol_frequency="Occasionally";
    private String str_smoke="Yes";
    private String str_tabcco="Yes";
    private String str_stress="Yes";
    private String str_medical_condition="";
    private String str_food_allergy="Yes, please specify the name in remarks";
    private String str_health_suppliment="Yes, please specify the name in remarks";
    private String str_emotionalsuffering="Yes, please specify the name in remarks";
    private String str_selfmotivator="Yes";
    private String str_meal="Yes";
    private String str_distracted="Yes";
    private String str_fastfood="Daily";
    private String str_physical_activity="Sedentary";
    private String str_bodyshape="Healthy BMI";
    private String str_anyotherspecialmentionhealth="Yes, please specify the name in remarks";
    private boolean IsMedicalFoundAnyother=false;
    ImageView imgLeft;
    private boolean IsFoodFoundAnyother=false;
    private String str_food_condition="";

    @Override
    public void onBackPressed() {
        if (vp_health_profile.getDisplayedChild()==0){
            finish();
        }
        if (count!=0){
            count--;
            count_viewflipper--;
            vp_health_profile.setDisplayedChild(count_viewflipper);
            setindicator(count);
            if (vp_health_profile.getDisplayedChild()==15){
                btn_next_slide.setText("Submit");
            }else {
                btn_next_slide.setText("Next");

            }
        }


    }

    int userID;
    RadioGroup radioGroup_sleep;
    Toolbar toolbar;
    Context context;
    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_design);
        reclycler_foodalergy=findViewById(R.id.reclycler_foodalergy);
        edt_remark_foodallergy_condition=findViewById(R.id.edt_remark_foodallergy_condition);
        ll_remark_foodallergy_condition=findViewById(R.id.ll_remark_foodallergy_condition);
//        imgLeft=findViewById(R.id.imgLeft);
//        imgLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        context = NewDesignHealthActivity.this;
        initView();

        ll_remark_medical_condition=findViewById(R.id.ll_remark_medical_condition);
        edt_remark_medical_condition=findViewById(R.id.edt_remark_medical_condition);
        txt_kgs=findViewById(R.id.txt_kgs);
        txt_pounds=findViewById(R.id.txt_pounds);
        edt_pincode=findViewById(R.id.edt_pincode);
        edt_weight=findViewById(R.id.edt_weight);
        edt_height=findViewById(R.id.edt_height);
        reclycler_medicalcondition=findViewById(R.id.reclycler_medicalcondition);
        btn_next_slide=findViewById(R.id.btn_next_slide);
        vp_health_profile=findViewById(R.id.vp_health_profile);
        final TextView btn_next_slide=findViewById(R.id.btn_next_slide);
        final TextView txt_back_slide=findViewById(R.id.txt_back_slide);

        btn_next_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isAdditinal=getIntent().getBooleanExtra("IsAdditinal",false);

                if (isAdditinal){
                    callHealthParameterPostApi();
                    return;

                }


                count++;
                count_viewflipper++;

                if (count_viewflipper < 16) {
                    vp_health_profile.setDisplayedChild(count_viewflipper);

                    setindicator(count);
                    if (vp_health_profile.getDisplayedChild() == 15) {
                        btn_next_slide.setText("Submit");
                    } else {
                        btn_next_slide.setText("Next");

                    }
                }
                if (count_viewflipper == 16) {
                    btn_next_slide.setEnabled(false);
                    callHealthParameterPostApi();

                }
                if (count_viewflipper == 1) {
                    txt_back_slide.setBackgroundResource(R.drawable.bg_black_box);
                    txt_back_slide.setTextColor(getResources().getColor(R.color.black));
                }





            }
        });
        txt_back_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vp_health_profile.getDisplayedChild() == 0) {
                    txt_back_slide.setBackgroundResource(R.drawable.bg_gray_box);
                    txt_back_slide.setTextColor(getResources().getColor(R.color.gray));
                    return;
                }
                if (count_viewflipper == 1) {
                    txt_back_slide.setBackgroundResource(R.drawable.bg_gray_box);
                    txt_back_slide.setTextColor(getResources().getColor(R.color.gray));
                }
                if (count != 0) {
                    count--;
                    count_viewflipper--;
                    vp_health_profile.setDisplayedChild(count_viewflipper);
                    setindicator(count);
                    if (vp_health_profile.getDisplayedChild() == 15) {
                        btn_next_slide.setText("Submit");
                    } else {
                        btn_next_slide.setText("Next");

                    }
//                    if (count_viewflipper != 1) {
//                        txt_back_slide.setBackgroundResource(R.drawable.bg_black_box);
//                        txt_back_slide.setTextColor(getResources().getColor(R.color.black));
//                    }

                }


            }
        });


        radioGroup_sleep=findViewById(R.id.radioGroup_sleep);
        ISFromReevaluate=getIntent().getBooleanExtra("ISFromReevaluate",false);
        ISFreeze=getIntent().getBooleanExtra("ISFreeze",false);

//        TextView tvTitle=findViewById(R.id.tvTitle);
//        if (ISFromReevaluate){
//            radioGroup_sleep.setVisibility(View.GONE);
//            tvTitle.setText("REEvaluate");
//
//        }else {
//            tvTitle.setText("REEvaluate");
//
//            radioGroup_sleep.setVisibility(View.VISIBLE);
//        }
        txt_bloodgroup=findViewById(R.id.txt_bloodgroup);

        txt_cm=findViewById(R.id.txt_cm);
        txt_ftinch=findViewById(R.id.txt_ftinch);
        txt_city=findViewById(R.id.txt_city);
        txt_country=findViewById(R.id.txt_country);
        txt_foodculture=findViewById(R.id.txt_foodculture);

        txt_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_cm.setBackground(getResources().getDrawable(R.drawable.bg_newdesign_btn));
                txt_ftinch.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                height_AdditionParameter=1;

            }
        });
        txt_ftinch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_ftinch.setBackground(getResources().getDrawable(R.drawable.bg_newdesign_btn));
                txt_cm.setBackground(getResources().getDrawable(R.drawable.bg_black_box));

                height_AdditionParameter=2;
            }
        });

        txt_kgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weight_AdditionParameter=1;

                txt_kgs.setBackground(getResources().getDrawable(R.drawable.bg_newdesign_btn));
                txt_pounds.setBackground(getResources().getDrawable(R.drawable.bg_black_box));

            }
        });

        txt_pounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight_AdditionParameter=2;

                txt_pounds.setBackground(getResources().getDrawable(R.drawable.bg_newdesign_btn));
                txt_kgs.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
            }
        });

        txt_state=findViewById(R.id.txt_state);
        txt_city=findViewById(R.id.txt_city);
        rd_button_profile=findViewById(R.id.rd_button_profile);
        rd_button_health=findViewById(R.id.rd_button_health);
        sessionManager=new SessionManager(this);
        rd_button_profile.setText(sessionManager.getStringValue("KEY_BASIC_PROFILE"));
        rd_button_health.setText(sessionManager.getStringValue("KEY_HEALTH_PROFILE"));
        rd_button_health.performClick();
        rd_button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewDesignHealthActivity.this, MyProfileActivity.class));
                overridePendingTransition(0,0);
                finish();

            }
        });
        utils=new Utils();
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        sessionManager=new SessionManager(NewDesignHealthActivity.this);
        callHealthParametergetApi();
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

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


        boolean isAdditinal=getIntent().getBooleanExtra("IsAdditinal",false);

        LinearLayout   ll_dots_main = findViewById(R.id.ll_dots_main);
        tvTitle = findViewById(R.id.tvTitle);

        if (isAdditinal){
            btn_next_slide.setText("Submit");
            tvTitle.setText("Additonal Information");
            ll_dots_main.setVisibility(View.GONE);

        }

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();

        if (isAdditinal){
            tvTitle.setText("Additonal Information");

        }




    }



    private void setToolBar() {
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        if (ISFromReevaluate){
            radioGroup_sleep.setVisibility(View.GONE);
            tvTitle.setText("REEvaluate");

        }else {
            tvTitle.setText("REEvaluate");

            radioGroup_sleep.setVisibility(View.VISIBLE);
        }
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



    private void setindicator(int count) {


        view1.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view2.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view3.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view4.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view5.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view6.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view7.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view8.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view9.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view10.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view11.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view12.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view13.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view14.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view15.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view16.setBackgroundColor(Color.parseColor("#EAEAEC"));


        if (count==1){ view1.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==2){ view2.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==3){ view3.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==4){ view4.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==5){ view5.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==6){ view6.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==7){ view7.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==8){ view8.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==9){ view9.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==10){ view10.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==11){ view11.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==12){ view12.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==13){ view13.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==14){ view14.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==15){ view15.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==16){ view16.setBackgroundColor(Color.parseColor("#00D5BC")); }






    }

    private void initView() {
        view1=findViewById(R.id.view1);
        view2=findViewById(R.id.view2);
        view3=findViewById(R.id.view3);
        view4=findViewById(R.id.view4);
        view5=findViewById(R.id.view5);
        view6=findViewById(R.id.view6);
        view7=findViewById(R.id.view7);
        view8=findViewById(R.id.view8);
        view9=findViewById(R.id.view9);
        view10=findViewById(R.id.view10);
        view11=findViewById(R.id.view11);
        view12=findViewById(R.id.view12);
        view13=findViewById(R.id.view13);
        view14=findViewById(R.id.view14);
        view15=findViewById(R.id.view15);
        view16=findViewById(R.id.view16);
    }


    private void callHealthParametergetApi() {

        HealthRequest healthRequest=new HealthRequest();
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Log.e("idd", String.valueOf(userID));

        healthRequest.setUserId(userID);


        utils.showProgressbar(NewDesignHealthActivity.this);

        Call<ClsNewHealthParamData> call = healthParametersService.getReeowrkerHealthData(userID);
        call.enqueue(new Callback<ClsNewHealthParamData>()
        {
            @Override
            public void onResponse(Call<ClsNewHealthParamData> call, Response<ClsNewHealthParamData> response)
            {

                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        final ClsNewHealthParamData clsNewHealthParamData = response.body();
                        if (clsNewHealthParamData.getCode()!=null){

                            ary_HealthParamDataArrayList=clsNewHealthParamData.getData();
//                            setMedicalConditionData(clsNewHealthParamData);


                            setFirstPageData(clsNewHealthParamData);
                            setAlcholData(clsNewHealthParamData);
                            setSmokeData(clsNewHealthParamData);
                            setChabaccoData(clsNewHealthParamData);
                            setStressData(clsNewHealthParamData);
                            setMedicalConditionData(clsNewHealthParamData);
//                            setFoodAlergyData(clsNewHealthParamData);
                            setNewFoodAllergyData(clsNewHealthParamData);
                            setHealthSupplimentData(clsNewHealthParamData);
                            setEmotionalSufferingData(clsNewHealthParamData);
                            setSelfMotivatorData(clsNewHealthParamData);
                            setMealData(clsNewHealthParamData);
                            setDistractedData(clsNewHealthParamData);
                            setFastFoodData(clsNewHealthParamData);
                            setPhysicalActivityData(clsNewHealthParamData);
                            setBodyshapeData(clsNewHealthParamData);
                            setSpecialmentionHealthData(clsNewHealthParamData);





                        }else {
                            Toast.makeText(NewDesignHealthActivity.this, ""+clsNewHealthParamData.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsNewHealthParamData> call, Throwable t)
            {
                utils.hideProgressbar();
                ShowRetryBar("");
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void setFirstPageData(final ClsNewHealthParamData clsNewHealthParamData) {

        try {
            for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
                if (ary_HealthParamDataArrayList.get(i).getQuestionId()==1){
                    if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                        txt_country.setText(clsNewHealthParamData.getData().get(i).getAnswer());

                    }
                }
            }
            for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
                if (ary_HealthParamDataArrayList.get(i).getQuestionId()==2){
                    if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                        txt_state.setText(clsNewHealthParamData.getData().get(i).getAnswer());

                    }

                }
            }

            for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
                if (ary_HealthParamDataArrayList.get(i).getQuestionId()==3){

                    if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                        txt_city.setText(clsNewHealthParamData.getData().get(i).getAnswer());

                    }


                }
            }

            for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
                if (ary_HealthParamDataArrayList.get(i).getQuestionId()==4){
                    if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                        edt_pincode.setText(clsNewHealthParamData.getData().get(i).getAnswer());

                    }

                }
            }



            for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
                if (ary_HealthParamDataArrayList.get(i).getQuestionId()==28){

                    if (ary_HealthParamDataArrayList.get(i).getAnswer()!=null){
                        edt_weight.setText(String.valueOf(ary_HealthParamDataArrayList.get(i).getAnswer()));

                    }
                    if (clsNewHealthParamData.getData().get(i).getAdditonalParameter()!=null&&clsNewHealthParamData.getData().get(i).getAdditonalParameter().equalsIgnoreCase("1")){
                        txt_kgs.performClick();
                    }else {
                        txt_pounds.performClick();
                    }
                }
            }


            for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
                if (ary_HealthParamDataArrayList.get(i).getQuestionId()==27){
                    if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                        edt_height.setText(clsNewHealthParamData.getData().get(i).getAnswer());

                    }
                    if (clsNewHealthParamData.getData().get(i).getAdditonalParameter()!=null&&   clsNewHealthParamData.getData().get(i).getAdditonalParameter().equalsIgnoreCase("1")){
                        txt_cm.performClick();
                    }else {
                        txt_ftinch.performClick();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }





        txt_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                countryDialog = new NewCountryDialog();
                Bundle   bundle = new Bundle();
                ArrayList<String>  stringArrayList=new ArrayList<>();
                stringArrayList.add(clsNewHealthParamData.getData().get(0).getItemList());
                String[] cccc = clsNewHealthParamData.getData().get(0).getItemList().split("\\$");
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                bundle.putStringArrayList("COUNTRY_LIST",list );
                bundle.putString("from","Country");
                countryDialog.setArguments(bundle);
                countryDialog.show(fm, "COUNTRY_FRAGMENT");
            }
        });

        txt_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                countryDialog = new NewCountryDialog();
                Bundle   bundle = new Bundle();
                ArrayList<String>  stringArrayList=new ArrayList<>();
                String[] cccc = clsNewHealthParamData.getData().get(1).getItemList().split("\\$");
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                bundle.putStringArrayList("COUNTRY_LIST",list );
                bundle.putString("from","state");

                countryDialog.setArguments(bundle);
                countryDialog.show(fm, "COUNTRY_FRAGMENT");
            }
        });



        txt_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                countryDialog = new NewCountryDialog();
                Bundle   bundle = new Bundle();
                ArrayList<String>  stringArrayList=new ArrayList<>();
                String[] cccc = clsNewHealthParamData.getData().get(2).getItemList().split("\\$");
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                bundle.putStringArrayList("COUNTRY_LIST",list );
                bundle.putString("from","city");

                countryDialog.setArguments(bundle);
                countryDialog.show(fm, "COUNTRY_FRAGMENT");
            }
        });




        for (int i = 0; i <clsNewHealthParamData.getData().size() ; i++) {
            if (clsNewHealthParamData.getData().get(i).getQuestionId()==5){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    String bloodgroup = clsNewHealthParamData.getData().get(i).getAnswer();
                    txt_bloodgroup.setText(bloodgroup);
                }

                break;
            }

        }

        txt_bloodgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                countryDialog = new NewCountryDialog();
                Bundle   bundle = new Bundle();
                ArrayList<String>  stringArrayList=new ArrayList<>();
                for (int i = 0; i <clsNewHealthParamData.getData().size() ; i++) {
                    if (clsNewHealthParamData.getData().get(i).getQuestionId()==5){
                        String[] cccc = clsNewHealthParamData.getData().get(i).getItemList().split("\\$");
                        ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                        bundle.putStringArrayList("COUNTRY_LIST",list );
                        bundle.putString("from","blood");
                        countryDialog.setArguments(bundle);
                        countryDialog.show(fm, "COUNTRY_FRAGMENT");
                        break;
                    }

                }






            }
        });


        for (int i = 0; i <clsNewHealthParamData.getData().size() ; i++) {
            if (clsNewHealthParamData.getData().get(i).getQuestionId()==7){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    String foodcluture = clsNewHealthParamData.getData().get(i).getAnswer();
                    txt_foodculture.setText(foodcluture);
                }

                break;
            }

        }

        txt_foodculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                countryDialog = new NewCountryDialog();
                Bundle   bundle = new Bundle();
                ArrayList<String>  stringArrayList=new ArrayList<>();
                for (int i = 0; i <clsNewHealthParamData.getData().size() ; i++) {
                    if (clsNewHealthParamData.getData().get(i).getQuestionId()==7){
                        String[] cccc = clsNewHealthParamData.getData().get(i).getItemList().split("\\$");
                        ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                        bundle.putStringArrayList("COUNTRY_LIST",list );
                        bundle.putString("from","foodculture");
                        countryDialog.setArguments(bundle);
                        countryDialog.show(fm, "COUNTRY_FRAGMENT");
                        break;
                    }

                }

            }
        });


    }




    private void callHealthParameterPostApi()
    {

        utils.showProgressbar(NewDesignHealthActivity.this);

        setAllData();

        ClsPostHealthData clsPostHealthData_new=new ClsPostHealthData();
        ArrayList<HealthParamData> arylst_Data=new ArrayList<>();
        ClsPostHealthData clsPostHealthData=new ClsPostHealthData();
        clsPostHealthData.setReeworkedId(sessionManager.getIntValue(SessionManager.KEY_USER_ID));
        clsPostHealthData.setParamlist(ary_HealthParamDataArrayList);




        Call<ClsgetPostData> call = healthParametersService.saveHealthParameter(clsPostHealthData);

        call.enqueue(new Callback<ClsgetPostData>()
        {
            @Override
            public void onResponse(Call<ClsgetPostData> call, Response<ClsgetPostData> response)
            {
                btn_next_slide.setEnabled(true);

                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsgetPostData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        if (listResponse.getData()!=null){
                            Toast.makeText(NewDesignHealthActivity.this, "" + listResponse.getData(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(NewDesignHealthActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                        }

                        boolean isAdditinal=getIntent().getBooleanExtra("IsAdditinal",false);

                        if (isAdditinal){

                            finish();

                        }else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(NewDesignHealthActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            },1000);

                        }








//                        }
                        /* finish();*/
                    }
                    else
                    {
//                        Toast.makeText(ProfileDynamicHealthparamActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(NewDesignHealthActivity.this, "You have successfully Reeevaluated" , Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
                else
                {
                    Toast.makeText(NewDesignHealthActivity.this, "You have successfully Reeevaluated", Toast.LENGTH_SHORT).show();

                    finish();

//                    Toast.makeText(ProfileDynamicHealthparamActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<ClsgetPostData> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
                btn_next_slide.setEnabled(true);

                Toast.makeText(NewDesignHealthActivity.this, "You have successfully Reeevaluated", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }


    @Override
    public void onSubmitCountryData(String model,String from) {

        if (from.equalsIgnoreCase("country")){
            txt_country.setText(model);
        }
        if (from.equalsIgnoreCase("state")){
            txt_state.setText(model);
        }
        if (from.equalsIgnoreCase("city")){
            txt_city.setText(model);
        }
        if (from.equalsIgnoreCase("foodculture")){
            txt_foodculture.setText(model);
        }
        if (from.equalsIgnoreCase("blood")){
            txt_bloodgroup.setText(model);
        }

    }

    @Override
    public void GetmedicalCondition(ArrayList<MedicalConditionData> medicalcondition) {
        StringBuilder stringBuilder_medicalCondition=new StringBuilder();
        IsMedicalFoundAnyother=false;

        for (int i = 0; i <medicalcondition.size() ; i++) {
            if (medicalcondition.get(i).isSelect()){

                if (medicalcondition.get(i).getMedicalconditionName().equalsIgnoreCase("Any other, please specify in remarks")){
                    IsMedicalFoundAnyother=true;
                }

                stringBuilder_medicalCondition.append(medicalcondition.get(i).getMedicalconditionName()+"$");

            }
        }

        if (IsMedicalFoundAnyother){
            ll_remark_medical_condition.setVisibility(View.VISIBLE);
        }else {
            ll_remark_medical_condition.setVisibility(View.GONE);

        }


        if (!stringBuilder_medicalCondition.toString().isEmpty()) {
            str_medical_condition = "";
            if (stringBuilder_medicalCondition.toString().endsWith("$")) {
                str_medical_condition = stringBuilder_medicalCondition.toString().substring(0, stringBuilder_medicalCondition.toString().length() - 1);
            } else {
                str_medical_condition = stringBuilder_medicalCondition.toString();
            }



        }



    }





    @Override
    public void onClick(View v) {
    }

    private void ShowRetryBar(String msg)
    {

        String strMessage;
        if (msg.isEmpty())
        {
            strMessage = "Please try again";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(NewDesignHealthActivity.this))
                        {
                            callHealthParametergetApi();
                        }

                    }
                });

        snackbar.show();
    }
    public void saveDataofpage1(){

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==1){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(txt_country.getText().toString());

                break;

            }

        }

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==2){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(txt_state.getText().toString());
                break;
            }

        }


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==3){

                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(txt_city.getText().toString());
                break;
            }

        }

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==4){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(edt_pincode.getText().toString());
                break;

            }

        }



        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==28){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(edt_weight.getText().toString());
                ary_HealthParamDataArrayList.get(i).setAdditonalParameter(String.valueOf(weight_AdditionParameter));
                break;
            }

        }



        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==27){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(edt_height.getText().toString());
                ary_HealthParamDataArrayList.get(i).setAdditonalParameter(String.valueOf(height_AdditionParameter));
                break;
            }

        }





        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==5){

                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(txt_bloodgroup.getText().toString());
                break;

            }

        }



        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==7){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(txt_foodculture.getText().toString());
                break;
            }

        }












    }

    public  void setAlcholData(final ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_alchol_no=findViewById(R.id.rel_alchol_no);
        final RelativeLayout rel_alchol_yes=findViewById(R.id.rel_alchol_yes);
        final ImageView img_alchohol_no=findViewById(R.id.img_alchohol_no);
        final ImageView img_alchohol_yes=findViewById(R.id.img_alchohol_yes);
        final ImageView img_alcohol_regularly=findViewById(R.id.img_alcohol_regularly);
        final ImageView img_alcohol_occasionaly=findViewById(R.id.img_alcohol_occasionaly);
        final RelativeLayout rel_alcohol_regularly=findViewById(R.id.rel_alcohol_regularly);
        final RelativeLayout rel_alcohol_occasionally=findViewById(R.id.rel_alcohol_occasionally);
        final LinearLayout ll_frequency_alcohol=findViewById(R.id.ll_frequency_alcohol);

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==8){
                if (ary_HealthParamDataArrayList.get(i).getAnswer()!=null){
                    if (ary_HealthParamDataArrayList.get(i).getAnswer().equalsIgnoreCase("Yes")){

                        str_rel_alchol="Yes";

                        ll_frequency_alcohol.setVisibility(View.VISIBLE);
                        img_alchohol_yes.setVisibility(View.VISIBLE);
                        img_alchohol_no.setVisibility(View.INVISIBLE);
                        rel_alchol_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_alchol_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_alchol_yes.setPadding(20,20,20,20);
                        rel_alchol_no.setPadding(20,20,20,20);

                        for (int j = 0; j <ary_HealthParamDataArrayList.size() ; j++) {
                            if (ary_HealthParamDataArrayList.get(j).getQuestionId()==9){
                                if (ary_HealthParamDataArrayList.get(j).getAnswer().equalsIgnoreCase("Occasionally")){
                                    img_alcohol_occasionaly.setVisibility(View.VISIBLE);
                                    img_alcohol_regularly.setVisibility(View.INVISIBLE);
                                    rel_alcohol_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_done));
                                    rel_alcohol_regularly.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                                    rel_alcohol_occasionally.setPadding(20,20,20,20);
                                    rel_alcohol_regularly.setPadding(20,20,20,20);
                                }
                                if (ary_HealthParamDataArrayList.get(j).getAnswer().equalsIgnoreCase("Regularly")){

                                    img_alcohol_regularly.setVisibility(View.VISIBLE);
                                    img_alcohol_occasionaly.setVisibility(View.INVISIBLE);
                                    rel_alcohol_regularly.setBackground(getResources().getDrawable(R.drawable.bg_done));
                                    rel_alcohol_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                                    rel_alcohol_occasionally.setPadding(20,20,20,20);
                                    rel_alcohol_regularly.setPadding(20,20,20,20);
                                }
                            }

                        }

                        ll_frequency_alcohol.setVisibility(View.VISIBLE);

                    }
                    if (ary_HealthParamDataArrayList.get(i).getAnswer().equalsIgnoreCase("No")){
                        str_rel_alchol="No";

                        ll_frequency_alcohol.setVisibility(View.INVISIBLE);

                        img_alchohol_no.setVisibility(View.VISIBLE);
                        img_alchohol_yes.setVisibility(View.INVISIBLE);
                        rel_alchol_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_alchol_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_alchol_yes.setPadding(20,20,20,20);
                        rel_alchol_no.setPadding(20,20,20,20);
//            ll_frequency_alcohol.setVisibility(View.INVISIBLE);
                    }
                }


            }
        }



        rel_alchol_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_rel_alchol="Yes";

                ll_frequency_alcohol.setVisibility(View.VISIBLE);
                img_alchohol_yes.setVisibility(View.VISIBLE);
                img_alchohol_no.setVisibility(View.INVISIBLE);
                rel_alchol_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_alchol_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_alchol_yes.setPadding(20,20,20,20);
                rel_alchol_no.setPadding(20,20,20,20);


            }
        });

        rel_alchol_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_rel_alchol="No";

                ll_frequency_alcohol.setVisibility(View.GONE);

                img_alchohol_no.setVisibility(View.VISIBLE);
                img_alchohol_yes.setVisibility(View.INVISIBLE);
                rel_alchol_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_alchol_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_alchol_yes.setPadding(20,20,20,20);
                rel_alchol_no.setPadding(20,20,20,20);


            }
        });

        rel_alcohol_occasionally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_alcohol_frequency="Occasionally";

                img_alcohol_occasionaly.setVisibility(View.VISIBLE);
                img_alcohol_regularly.setVisibility(View.INVISIBLE);
                rel_alcohol_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_alcohol_regularly.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_alcohol_occasionally.setPadding(20,20,20,20);
                rel_alcohol_regularly.setPadding(20,20,20,20);
            }
        });
        rel_alcohol_regularly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_alcohol_frequency="Regularly";


                img_alcohol_regularly.setVisibility(View.VISIBLE);
                img_alcohol_occasionaly.setVisibility(View.INVISIBLE);
                rel_alcohol_regularly.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_alcohol_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_alcohol_occasionally.setPadding(20,20,20,20);
                rel_alcohol_regularly.setPadding(20,20,20,20);
            }
        });




//        ary_HealthParamDataArrayList.get(9).setItemList("");
//        ary_HealthParamDataArrayList.get(9).setQuestion("");
//        ary_HealthParamDataArrayList.get(9).setAnswer(str_rel_alchol);
//        healthParamData_static.add(ary_HealthParamDataArrayList.get(9));
//
//        ary_HealthParamDataArrayList.get(10).setItemList("");
//        ary_HealthParamDataArrayList.get(10).setQuestion("");
//        ary_HealthParamDataArrayList.get(10).setAnswer(str_alcohol_frequency);
//        healthParamData_static.add(ary_HealthParamDataArrayList.get(10));



    }

    public void setSmokeData(final ClsNewHealthParamData clsNewHealthParamData){
        final ImageView img_smoke_no=findViewById(R.id.img_smoke_no);
        final ImageView img_smoke_yes=findViewById(R.id.img_smoke_yes);
        final RelativeLayout rel_smoke_no=findViewById(R.id.rel_smoke_no);
        final RelativeLayout rel_smoke_yes=findViewById(R.id.rel_smoke_yes);

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {


            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==10){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes")){
                        str_smoke="Yes";

                        img_smoke_yes.setVisibility(View.VISIBLE);
                        img_smoke_no.setVisibility(View.INVISIBLE);
                        rel_smoke_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_smoke_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_smoke_yes.setPadding(20,20,20,20);
                        rel_smoke_no.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_smoke="No";


                        img_smoke_no.setVisibility(View.VISIBLE);
                        img_smoke_yes.setVisibility(View.INVISIBLE);
                        rel_smoke_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_smoke_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_smoke_yes.setPadding(20,20,20,20);
                        rel_smoke_no.setPadding(20,20,20,20);
                    }
                }

            }
        }



        rel_smoke_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_smoke="Yes";

                img_smoke_yes.setVisibility(View.VISIBLE);
                img_smoke_no.setVisibility(View.INVISIBLE);
                rel_smoke_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_smoke_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_smoke_yes.setPadding(20,20,20,20);
                rel_smoke_no.setPadding(20,20,20,20);

            }
        });
        rel_smoke_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_smoke="No";


                img_smoke_no.setVisibility(View.VISIBLE);
                img_smoke_yes.setVisibility(View.INVISIBLE);
                rel_smoke_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_smoke_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_smoke_yes.setPadding(20,20,20,20);
                rel_smoke_no.setPadding(20,20,20,20);
            }
        });

//        ary_HealthParamDataArrayList.get(11).setItemList("");
//        ary_HealthParamDataArrayList.get(11).setQuestion("");
//        ary_HealthParamDataArrayList.get(11).setAnswer(str_smoke);
//        healthParamData_static.add(ary_HealthParamDataArrayList.get(11));


    }

    public void setChabaccoData(final ClsNewHealthParamData clsNewHealthParamData){
        final ImageView img_tabco_no=findViewById(R.id.img_tabco_no);
        final ImageView img_tabco_yes=findViewById(R.id.img_tabco_yes);
        final RelativeLayout rel_tabco_no=findViewById(R.id.rel_tabco_no);
        final RelativeLayout rel_tabco_yes=findViewById(R.id.rel_tabco_yes);
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==12){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes")){
                        str_tabcco="Yes";

                        img_tabco_yes.setVisibility(View.VISIBLE);
                        img_tabco_no.setVisibility(View.INVISIBLE);
                        rel_tabco_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_tabco_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_tabco_yes.setPadding(20,20,20,20);
                        rel_tabco_no.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_tabcco="No";


                        img_tabco_no.setVisibility(View.VISIBLE);
                        img_tabco_yes.setVisibility(View.INVISIBLE);
                        rel_tabco_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_tabco_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_tabco_yes.setPadding(20,20,20,20);
                        rel_tabco_no.setPadding(20,20,20,20);
                    }
                }

            }
        }



        rel_tabco_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_tabcco="Yes";

                img_tabco_yes.setVisibility(View.VISIBLE);
                img_tabco_no.setVisibility(View.INVISIBLE);
                rel_tabco_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_tabco_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_tabco_yes.setPadding(20,20,20,20);
                rel_tabco_no.setPadding(20,20,20,20);

            }
        });
        rel_tabco_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_tabcco="No";


                img_tabco_no.setVisibility(View.VISIBLE);
                img_tabco_yes.setVisibility(View.INVISIBLE);
                rel_tabco_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_tabco_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_tabco_yes.setPadding(20,20,20,20);
                rel_tabco_no.setPadding(20,20,20,20);
            }
        });

//        ary_HealthParamDataArrayList.get(12).setItemList("");
//        ary_HealthParamDataArrayList.get(12).setQuestion("");
//        ary_HealthParamDataArrayList.get(12).setAnswer(str_tabcco);
//        healthParamData_static.add(ary_HealthParamDataArrayList.get(12));


    }

    public void setStressData(final ClsNewHealthParamData clsNewHealthParamData){
        final ImageView img_stress_no=findViewById(R.id.img_stress_no);
        final ImageView img_stress_yes=findViewById(R.id.img_stress_yes);
        final RelativeLayout rel_stress_no=findViewById(R.id.rel_stress_no);
        final RelativeLayout rel_stress_yes=findViewById(R.id.rel_stress_yes);

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==13){

                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes")){
                        str_stress="Yes";

                        img_stress_yes.setVisibility(View.VISIBLE);
                        img_stress_no.setVisibility(View.INVISIBLE);
                        rel_stress_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_stress_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_stress_yes.setPadding(20,20,20,20);
                        rel_stress_no.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_stress="No";


                        img_stress_no.setVisibility(View.VISIBLE);
                        img_stress_yes.setVisibility(View.INVISIBLE);
                        rel_stress_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_stress_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_stress_yes.setPadding(20,20,20,20);
                        rel_stress_no.setPadding(20,20,20,20);
                    }
                }


            }
        }


        rel_stress_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_stress="Yes";

                img_stress_yes.setVisibility(View.VISIBLE);
                img_stress_no.setVisibility(View.INVISIBLE);
                rel_stress_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_stress_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_stress_yes.setPadding(20,20,20,20);
                rel_stress_no.setPadding(20,20,20,20);

            }
        });
        rel_stress_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_stress="No";


                img_stress_no.setVisibility(View.VISIBLE);
                img_stress_yes.setVisibility(View.INVISIBLE);
                rel_stress_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_stress_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_stress_yes.setPadding(20,20,20,20);
                rel_stress_no.setPadding(20,20,20,20);
            }
        });

//        ary_HealthParamDataArrayList.get(13).setItemList("");
//        ary_HealthParamDataArrayList.get(13).setQuestion("");
//        ary_HealthParamDataArrayList.get(13).setAnswer(str_stress);
//        healthParamData_static.add(ary_HealthParamDataArrayList.get(13));


    }

    public void setMedicalConditionData(ClsNewHealthParamData clsNewHealthParamData){
        for (int i = 0; i <clsNewHealthParamData.getData().size() ; i++) {
            if (clsNewHealthParamData.getData().get(i).getQuestionId()==14){
                ArrayList<String> list_answer = null;
                String[] cccc = clsNewHealthParamData.getData().get(i).getItemList().split("\\$");
                str_medical_condition=clsNewHealthParamData.getData().get(i).getAnswer();
                String[] answer_medicalcondition = new String[0];
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){

                    answer_medicalcondition = clsNewHealthParamData.getData().get(i).getAnswer().split("\\$");

                }
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                if (answer_medicalcondition!=null){
                    list_answer = new ArrayList<String>(Arrays.asList(answer_medicalcondition));

                }

                ArrayList<MedicalConditionData> ary_MedicalConditionData=new ArrayList<>();

                for (int j = 0; j <list.size() ; j++) {
                    boolean isFound=true;

                    if (list_answer!=null&&!list_answer.isEmpty()){
                        for (int k = 0; k <list_answer.size() ; k++) {


                            if (list.get(j).toString().trim().equalsIgnoreCase(list_answer.get(k).toString().trim())){
                                isFound=false;
                                ary_MedicalConditionData.add(new MedicalConditionData(list.get(j).toString(),true));


                            }

                        }

                    }
                    if (isFound){
                        ary_MedicalConditionData.add(new MedicalConditionData(list.get(j).toString().trim(),false));
                        isFound=false;

                    }






                }

                boolean isFoundInGetMedical=false;

                if (list_answer!=null&&!list_answer.isEmpty()){
                    for (int j = 0; j <list_answer.size() ; j++) {
                        if (list_answer.get(j).toString().trim().equalsIgnoreCase("Any other, please specify in remarks")){
                            isFoundInGetMedical=true;

                        }
                    }
                }


                if (isFoundInGetMedical){
                    ll_remark_medical_condition.setVisibility(View.VISIBLE);

                    for (int j = 0; j <ary_HealthParamDataArrayList.size() ; j++) {
                        if (ary_HealthParamDataArrayList.get(i).getQuestionId()==14){
                            if (ary_HealthParamDataArrayList.get(i).getRemark()!=null){
                                edt_remark_medical_condition.setText(ary_HealthParamDataArrayList.get(i).getRemark());

                            }
                        }
                    }
                }else {
                    ll_remark_medical_condition.setVisibility(View.INVISIBLE);
                }


                reclycler_medicalcondition.setAdapter(new MedicalConditionsAdapter(NewDesignHealthActivity.this,ary_MedicalConditionData,NewDesignHealthActivity.this));
                break;
            }
        }


    }


    public void setNewFoodAllergyData(ClsNewHealthParamData clsNewHealthParamData){
        for (int i = 0; i <clsNewHealthParamData.getData().size() ; i++) {
            if (clsNewHealthParamData.getData().get(i).getQuestionId()==15){
                String[] answer_medicalcondition=null;
                ArrayList<String> list_answer = null;
                String foodallergy="Dairy/milk/lactose$Eggs$Fish$Gluten$Peanuts$Sesame$Shellfish$Soy$Tree nuts$Wheat$My allergy is not listed here$I don't have any food allergies";
                String[] cccc = foodallergy.split("\\$");
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    answer_medicalcondition = clsNewHealthParamData.getData().get(i).getAnswer().split("\\$");

                }
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(cccc));
                if (answer_medicalcondition!=null){
                    list_answer = new ArrayList<String>(Arrays.asList(answer_medicalcondition));

                }
                ArrayList<MedicalConditionData> ary_MedicalConditionData=new ArrayList<>();
//                I don't have any food allergies
                for (int j = 0; j <list.size() ; j++) {
                    boolean isFound=true;

                    if (list_answer!=null){
                        for (int k = 0; k <list_answer.size() ; k++) {


                            if (list.get(j).toString().trim().equalsIgnoreCase(list_answer.get(k).toString().trim())){
                                isFound=false;
                                ary_MedicalConditionData.add(new MedicalConditionData(list.get(j).toString(),true));


                            }

                        }

                    }
                    if (isFound){
                        ary_MedicalConditionData.add(new MedicalConditionData(list.get(j).toString().trim(),false));
                        isFound=false;

                    }






                }

                boolean isFoundInGetMedical=false;

                if (list_answer!=null){
                    for (int j = 0; j <list_answer.size() ; j++) {
                        if (list_answer.get(j).toString().trim().equalsIgnoreCase("Any other, please specify in remarks")){
                            isFoundInGetMedical=true;

                        }
                    }
                }


                if (isFoundInGetMedical){
                    ll_remark_foodallergy_condition.setVisibility(View.VISIBLE);

                    for (int j = 0; j <ary_HealthParamDataArrayList.size() ; j++) {
                        if (ary_HealthParamDataArrayList.get(i).getQuestionId()==15){
                            edt_remark_foodallergy_condition.setText(ary_HealthParamDataArrayList.get(i).getRemark());
                        }
                    }
                }else {
                    ll_remark_foodallergy_condition.setVisibility(View.INVISIBLE);
                }


                reclycler_foodalergy.setAdapter(new FoodConditionsAdapter(NewDesignHealthActivity.this,ary_MedicalConditionData,NewDesignHealthActivity.this));
                break;
            }
        }


    }


    public void setFoodAlergyData(ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_foodallegy_yes=findViewById(R.id.rel_foodallegy_yes);
        final RelativeLayout rel_foodallegy_no=findViewById(R.id.rel_foodallegy_no);
        final ImageView img_foodallergy_no=findViewById(R.id.img_foodallergy_no);
        final ImageView img_foodallergy_yes=findViewById(R.id.img_foodallergy_yes);
        final LinearLayout ll_food_allergy=findViewById(R.id.ll_food_allergy);
        edt_food_allergy_remark=findViewById(R.id.edt_food_allergy_remark);


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==15){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes, please specify the name in remarks")){
                        str_food_allergy="Yes, please specify the name in remarks";

                        ll_food_allergy.setVisibility(View.VISIBLE);
                        edt_food_allergy_remark.setText(clsNewHealthParamData.getData().get(15).getRemark());

                        img_foodallergy_yes.setVisibility(View.VISIBLE);
                        img_foodallergy_no.setVisibility(View.INVISIBLE);
                        rel_foodallegy_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_foodallegy_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_foodallegy_yes.setPadding(20,20,20,20);
                        rel_foodallegy_no.setPadding(20,20,20,20);
                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_food_allergy="No";
                        ll_food_allergy.setVisibility(View.GONE);

                        img_foodallergy_no.setVisibility(View.VISIBLE);
                        img_foodallergy_yes.setVisibility(View.INVISIBLE);
                        rel_foodallegy_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_foodallegy_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_foodallegy_yes.setPadding(20,20,20,20);
                        rel_foodallegy_no.setPadding(20,20,20,20);
                    }
                }


            }
        }


        rel_foodallegy_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_food_allergy="Yes, please specify the name in remarks";
                ll_food_allergy.setVisibility(View.VISIBLE);

                img_foodallergy_yes.setVisibility(View.VISIBLE);
                img_foodallergy_no.setVisibility(View.INVISIBLE);
                rel_foodallegy_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_foodallegy_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_foodallegy_yes.setPadding(20,20,20,20);
                rel_foodallegy_no.setPadding(20,20,20,20);

            }
        });
        rel_foodallegy_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_food_allergy.setVisibility(View.GONE);


                str_food_allergy="No";


                img_foodallergy_no.setVisibility(View.VISIBLE);
                img_foodallergy_yes.setVisibility(View.INVISIBLE);
                rel_foodallegy_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_foodallegy_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_foodallegy_yes.setPadding(20,20,20,20);
                rel_foodallegy_no.setPadding(20,20,20,20);
            }
        });

//        ary_HealthParamDataArrayList.get(15).setItemList("");
//        ary_HealthParamDataArrayList.get(15).setQuestion("");
//        ary_HealthParamDataArrayList.get(15).setAnswer(str_food_allergy);
//        ary_HealthParamDataArrayList.get(15).setRemark(edt_food_allergy_remark.getText().toString());
//        healthParamData_static.add(ary_HealthParamDataArrayList.get(15));

    }

    public void setHealthSupplimentData(ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_healthsuppliment_yes=findViewById(R.id.rel_healthsuppliment_yes);
        final RelativeLayout rel_healthsuppliment_no=findViewById(R.id.rel_healthsuppliment_no);
        final ImageView img_healthsuppliment_no=findViewById(R.id.img_healthsuppliment_no);
        final ImageView img_healthsuppliment_yes=findViewById(R.id.img_healthsuppliment_yes);
        final LinearLayout ll_health_suppliment=findViewById(R.id.ll_health_suppliment);
        edt_remark_health_suppliment=findViewById(R.id.edt_remark_health_suppliment);


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==16){
                if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes, please specify the name in remarks")){
                    str_health_suppliment="Yes, please specify the name in remarks";

                    ll_health_suppliment.setVisibility(View.VISIBLE);
                    edt_remark_health_suppliment.setText(clsNewHealthParamData.getData().get(16).getRemark());

                    img_healthsuppliment_yes.setVisibility(View.VISIBLE);
                    img_healthsuppliment_no.setVisibility(View.INVISIBLE);
                    rel_healthsuppliment_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                    rel_healthsuppliment_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                    rel_healthsuppliment_yes.setPadding(20,20,20,20);
                    rel_healthsuppliment_no.setPadding(20,20,20,20);
                }
                if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                    str_health_suppliment="No";
                    ll_health_suppliment.setVisibility(View.GONE);

                    img_healthsuppliment_no.setVisibility(View.VISIBLE);
                    img_healthsuppliment_yes.setVisibility(View.INVISIBLE);
                    rel_healthsuppliment_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                    rel_healthsuppliment_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                    rel_healthsuppliment_yes.setPadding(20,20,20,20);
                    rel_healthsuppliment_no.setPadding(20,20,20,20);
                }
            }
        }


        rel_healthsuppliment_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_health_suppliment="Yes, please specify the name in remarks";
                ll_health_suppliment.setVisibility(View.VISIBLE);

                img_healthsuppliment_yes.setVisibility(View.VISIBLE);
                img_healthsuppliment_no.setVisibility(View.INVISIBLE);
                rel_healthsuppliment_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_healthsuppliment_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_healthsuppliment_yes.setPadding(20,20,20,20);
                rel_healthsuppliment_no.setPadding(20,20,20,20);

            }
        });
        rel_healthsuppliment_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_health_suppliment.setVisibility(View.GONE);


                str_health_suppliment="No";


                img_healthsuppliment_no.setVisibility(View.VISIBLE);
                img_healthsuppliment_yes.setVisibility(View.INVISIBLE);
                rel_healthsuppliment_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_healthsuppliment_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_healthsuppliment_yes.setPadding(20,20,20,20);
                rel_healthsuppliment_no.setPadding(20,20,20,20);
            }
        });



    }

    public void setEmotionalSufferingData(final ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_emotionalsuffering_none=findViewById(R.id.rel_emotionalsuffering_none);
        final RelativeLayout rel_relationshipproblem=findViewById(R.id.rel_relationshipproblem);
        final RelativeLayout rel_financialproblem=findViewById(R.id.rel_financialproblem);
        final RelativeLayout rel_emotionalsuffering_anyother=findViewById(R.id.rel_emotionalsuffering_anyother);
        final ImageView img_emotionalsuffering_none=findViewById(R.id.img_emotionalsuffering_none);
        final ImageView img_emotionalsuffering_relationship=findViewById(R.id.img_emotionalsuffering_relationship);
        final ImageView img_emotionalsuffering_financial=findViewById(R.id.img_emotionalsuffering_financial);
        final ImageView img_emotionalsuffering_anyother=findViewById(R.id.img_emotionalsuffering_anyother);
        final LinearLayout ll_emotionalsuffering=findViewById(R.id.ll_emotionalsuffering);
        edt_remark_emotionalsuffering=findViewById(R.id.edt_remark_emotionalsuffering);


        for (int i = 0; i < ary_HealthParamDataArrayList.size(); i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==17){

                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("None")){
                        str_emotionalsuffering="None";
                        ll_emotionalsuffering.setVisibility(View.GONE);
                        edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(i).getRemark());
                        img_emotionalsuffering_none.setVisibility(View.VISIBLE);
                        img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                        rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_emotionalsuffering_none.setPadding(20,20,20,20);
                        rel_relationshipproblem.setPadding(20,20,20,20);
                        rel_financialproblem.setPadding(20,20,20,20);
                        rel_emotionalsuffering_anyother.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Relationship problem")){
                        str_emotionalsuffering="Relationship problem";
                        ll_emotionalsuffering.setVisibility(View.GONE);
                        edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(i).getRemark());

                        img_emotionalsuffering_none.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_relationship.setVisibility(View.VISIBLE);
                        img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                        rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_emotionalsuffering_none.setPadding(20,20,20,20);
                        rel_relationshipproblem.setPadding(20,20,20,20);
                        rel_financialproblem.setPadding(20,20,20,20);
                        rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
                    }

                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Financial problem")){
                        str_emotionalsuffering="Financial problem";
                        ll_emotionalsuffering.setVisibility(View.GONE);
                        edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(i).getRemark());

                        img_emotionalsuffering_none.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_financial.setVisibility(View.VISIBLE);
                        img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                        rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_emotionalsuffering_none.setPadding(20,20,20,20);
                        rel_relationshipproblem.setPadding(20,20,20,20);
                        rel_financialproblem.setPadding(20,20,20,20);
                        rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
                    }

                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Any other, please specify in remarks")){
                        str_emotionalsuffering="Any other, please specify in remarks";
                        ll_emotionalsuffering.setVisibility(View.VISIBLE);
                        edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(i).getRemark());

                        img_emotionalsuffering_none.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                        img_emotionalsuffering_anyother.setVisibility(View.VISIBLE);
                        rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_emotionalsuffering_none.setPadding(20,20,20,20);
                        rel_relationshipproblem.setPadding(20,20,20,20);
                        rel_financialproblem.setPadding(20,20,20,20);
                        rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
                    }

                }else {
                    str_emotionalsuffering="None";
                    ll_emotionalsuffering.setVisibility(View.GONE);
                    edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(i).getRemark());
                    img_emotionalsuffering_none.setVisibility(View.VISIBLE);
                    img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                    img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                    img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                    rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_done));
                    rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                    rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                    rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                    rel_emotionalsuffering_none.setPadding(20,20,20,20);
                    rel_relationshipproblem.setPadding(20,20,20,20);
                    rel_financialproblem.setPadding(20,20,20,20);
                    rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
                }

            }
        }

        rel_emotionalsuffering_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_emotionalsuffering="None";

                ll_emotionalsuffering.setVisibility(View.GONE);
//                edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(17).getRemark());

                img_emotionalsuffering_none.setVisibility(View.VISIBLE);
                img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_emotionalsuffering_none.setPadding(20,20,20,20);
                rel_relationshipproblem.setPadding(20,20,20,20);
                rel_financialproblem.setPadding(20,20,20,20);
                rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
            }
        });

        rel_relationshipproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_emotionalsuffering="Relationship problem";
                ll_emotionalsuffering.setVisibility(View.GONE);
//                edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(17).getRemark());

                img_emotionalsuffering_none.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_relationship.setVisibility(View.VISIBLE);
                img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_emotionalsuffering_none.setPadding(20,20,20,20);
                rel_relationshipproblem.setPadding(20,20,20,20);
                rel_financialproblem.setPadding(20,20,20,20);
                rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
            }
        });
        rel_financialproblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_emotionalsuffering="Financial problem";
                ll_emotionalsuffering.setVisibility(View.GONE);
//                edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(17).getRemark());

                img_emotionalsuffering_none.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_financial.setVisibility(View.VISIBLE);
                img_emotionalsuffering_anyother.setVisibility(View.INVISIBLE);
                rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_emotionalsuffering_none.setPadding(20,20,20,20);
                rel_relationshipproblem.setPadding(20,20,20,20);
                rel_financialproblem.setPadding(20,20,20,20);
                rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
            }
        });

        rel_emotionalsuffering_anyother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_emotionalsuffering="Any other, please specify in remarks";
                ll_emotionalsuffering.setVisibility(View.VISIBLE);
                for (int i = 0; i < ary_HealthParamDataArrayList.size(); i++) {

                    if (ary_HealthParamDataArrayList.get(i).getQuestionId()==17){
                        edt_remark_emotionalsuffering.setText(clsNewHealthParamData.getData().get(i).getRemark());

                    }
                }

                img_emotionalsuffering_none.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_relationship.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_financial.setVisibility(View.INVISIBLE);
                img_emotionalsuffering_anyother.setVisibility(View.VISIBLE);
                rel_emotionalsuffering_none.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_relationshipproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_financialproblem.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_emotionalsuffering_anyother.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_emotionalsuffering_none.setPadding(20,20,20,20);
                rel_relationshipproblem.setPadding(20,20,20,20);
                rel_financialproblem.setPadding(20,20,20,20);
                rel_emotionalsuffering_anyother.setPadding(20,20,20,20);
            }
        });





    }

    public void setSelfMotivatorData(final ClsNewHealthParamData clsNewHealthParamData){
        final ImageView img_selfmotivator_no=findViewById(R.id.img_selfmotivator_no);
        final ImageView img_selfmotivator_yes=findViewById(R.id.img_selfmotivator_yes);
        final RelativeLayout rel_selfmotivator_no=findViewById(R.id.rel_selfmotivator_no);
        final RelativeLayout rel_selfmotivator_yes=findViewById(R.id.rel_selfmotivator_yes);
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==18){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes")){
                        str_selfmotivator="Yes";

                        img_selfmotivator_yes.setVisibility(View.VISIBLE);
                        img_selfmotivator_no.setVisibility(View.INVISIBLE);
                        rel_selfmotivator_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_selfmotivator_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_selfmotivator_yes.setPadding(20,20,20,20);
                        rel_selfmotivator_no.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_selfmotivator="No";


                        img_selfmotivator_no.setVisibility(View.VISIBLE);
                        img_selfmotivator_yes.setVisibility(View.INVISIBLE);
                        rel_selfmotivator_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_selfmotivator_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_selfmotivator_yes.setPadding(20,20,20,20);
                        rel_selfmotivator_no.setPadding(20,20,20,20);
                    }

                }

            }
        }


        rel_selfmotivator_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_selfmotivator="Yes";

                img_selfmotivator_yes.setVisibility(View.VISIBLE);
                img_selfmotivator_no.setVisibility(View.INVISIBLE);
                rel_selfmotivator_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_selfmotivator_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_selfmotivator_yes.setPadding(20,20,20,20);
                rel_selfmotivator_no.setPadding(20,20,20,20);

            }
        });
        rel_selfmotivator_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_selfmotivator="No";


                img_selfmotivator_no.setVisibility(View.VISIBLE);
                img_selfmotivator_yes.setVisibility(View.INVISIBLE);
                rel_selfmotivator_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_selfmotivator_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_selfmotivator_yes.setPadding(20,20,20,20);
                rel_selfmotivator_no.setPadding(20,20,20,20);
            }
        });




    }

    public void setMealData(final ClsNewHealthParamData clsNewHealthParamData){
        final ImageView img_meal_no=findViewById(R.id.img_meal_no);
        final ImageView img_meal_yes=findViewById(R.id.img_meal_yes);
        final RelativeLayout rel_meal_no=findViewById(R.id.rel_meal_no);
        final RelativeLayout rel_meal_yes=findViewById(R.id.rel_meal_yes);
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==19){

                if (clsNewHealthParamData.getData().get(19).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(19).getAnswer().equalsIgnoreCase("Yes")){
                        str_meal="Yes";

                        img_meal_yes.setVisibility(View.VISIBLE);
                        img_meal_no.setVisibility(View.INVISIBLE);
                        rel_meal_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_meal_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_meal_yes.setPadding(20,20,20,20);
                        rel_meal_no.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(19).getAnswer().equalsIgnoreCase("No")){
                        str_meal="No";


                        img_meal_no.setVisibility(View.VISIBLE);
                        img_meal_yes.setVisibility(View.INVISIBLE);
                        rel_meal_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_meal_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_meal_yes.setPadding(20,20,20,20);
                        rel_meal_no.setPadding(20,20,20,20);
                    }
                }



            }
        }




        rel_meal_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_meal="Yes";

                img_meal_yes.setVisibility(View.VISIBLE);
                img_meal_no.setVisibility(View.INVISIBLE);
                rel_meal_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_meal_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_meal_yes.setPadding(20,20,20,20);
                rel_meal_no.setPadding(20,20,20,20);

            }
        });
        rel_meal_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_meal="No";


                img_meal_no.setVisibility(View.VISIBLE);
                img_meal_yes.setVisibility(View.INVISIBLE);
                rel_meal_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_meal_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_meal_yes.setPadding(20,20,20,20);
                rel_meal_no.setPadding(20,20,20,20);
            }
        });



    }

    public void setDistractedData(final ClsNewHealthParamData clsNewHealthParamData){
        final ImageView img_distracted_no=findViewById(R.id.img_distracted_no);
        final ImageView img_distracted_yes=findViewById(R.id.img_distracted_yes);
        final RelativeLayout rel_distracted_no=findViewById(R.id.rel_distracted_no);
        final RelativeLayout rel_distracted_yes=findViewById(R.id.rel_distracted_yes);

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==20){


                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes")){
                        str_distracted="Yes";

                        img_distracted_yes.setVisibility(View.VISIBLE);
                        img_distracted_no.setVisibility(View.INVISIBLE);
                        rel_distracted_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_distracted_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_distracted_yes.setPadding(20,20,20,20);
                        rel_distracted_no.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_distracted="No";


                        img_distracted_no.setVisibility(View.VISIBLE);
                        img_distracted_yes.setVisibility(View.INVISIBLE);
                        rel_distracted_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_distracted_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_distracted_yes.setPadding(20,20,20,20);
                        rel_distracted_no.setPadding(20,20,20,20);
                    }

                }


            }
        }



        rel_distracted_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_distracted="Yes";

                img_distracted_yes.setVisibility(View.VISIBLE);
                img_distracted_no.setVisibility(View.INVISIBLE);
                rel_distracted_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_distracted_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_distracted_yes.setPadding(20,20,20,20);
                rel_distracted_no.setPadding(20,20,20,20);

            }
        });
        rel_distracted_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_distracted="No";


                img_distracted_no.setVisibility(View.VISIBLE);
                img_distracted_yes.setVisibility(View.INVISIBLE);
                rel_distracted_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_distracted_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_distracted_yes.setPadding(20,20,20,20);
                rel_distracted_no.setPadding(20,20,20,20);
            }
        });



    }


    public void setFastFoodData(final ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_fastfood_daily=findViewById(R.id.rel_fastfood_daily);
        final RelativeLayout rel_fastfood_occasionally=findViewById(R.id.rel_fastfood_occasionally);
        final RelativeLayout rel_fastfood_never=findViewById(R.id.rel_fastfood_never);
        final ImageView img_fastfood_daily=findViewById(R.id.img_fastfood_daily);
        final ImageView img_fastfood_occasionally=findViewById(R.id.img_fastfood_occasionally);
        final ImageView img_fastfood_never=findViewById(R.id.img_fastfood_never);


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==21){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Daily")){
                        str_fastfood="Daily";

                        img_fastfood_daily.setVisibility(View.VISIBLE);
                        img_fastfood_occasionally.setVisibility(View.INVISIBLE);
                        img_fastfood_never.setVisibility(View.INVISIBLE);
                        rel_fastfood_daily.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_fastfood_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_fastfood_never.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_fastfood_daily.setPadding(20,20,20,20);
                        rel_fastfood_occasionally.setPadding(20,20,20,20);
                        rel_fastfood_never.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Occasionally/Rarely")){
                        str_fastfood="Occasionally/Rarely";
                        img_fastfood_daily.setVisibility(View.INVISIBLE);
                        img_fastfood_occasionally.setVisibility(View.VISIBLE);
                        img_fastfood_never.setVisibility(View.INVISIBLE);
                        rel_fastfood_daily.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_fastfood_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_fastfood_never.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_fastfood_daily.setPadding(20,20,20,20);
                        rel_fastfood_occasionally.setPadding(20,20,20,20);
                        rel_fastfood_never.setPadding(20,20,20,20);
                    }

                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Never")){
                        str_fastfood="Never";
                        img_fastfood_daily.setVisibility(View.INVISIBLE);
                        img_fastfood_occasionally.setVisibility(View.INVISIBLE);
                        img_fastfood_never.setVisibility(View.VISIBLE);
                        rel_fastfood_daily.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_fastfood_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_fastfood_never.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_fastfood_daily.setPadding(20,20,20,20);
                        rel_fastfood_occasionally.setPadding(20,20,20,20);
                        rel_fastfood_never.setPadding(20,20,20,20);
                    }
                }


            }
        }




        rel_fastfood_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_fastfood="Daily";

                img_fastfood_daily.setVisibility(View.VISIBLE);
                img_fastfood_occasionally.setVisibility(View.INVISIBLE);
                img_fastfood_never.setVisibility(View.INVISIBLE);
                rel_fastfood_daily.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_fastfood_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_fastfood_never.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_fastfood_daily.setPadding(20,20,20,20);
                rel_fastfood_occasionally.setPadding(20,20,20,20);
                rel_fastfood_never.setPadding(20,20,20,20);

            }
        });

        rel_fastfood_occasionally.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_fastfood="Occasionally/Rarely";
                img_fastfood_daily.setVisibility(View.INVISIBLE);
                img_fastfood_occasionally.setVisibility(View.VISIBLE);
                img_fastfood_never.setVisibility(View.INVISIBLE);
                rel_fastfood_daily.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_fastfood_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_fastfood_never.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_fastfood_daily.setPadding(20,20,20,20);
                rel_fastfood_occasionally.setPadding(20,20,20,20);
                rel_fastfood_never.setPadding(20,20,20,20);
            }
        });
        rel_fastfood_never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_fastfood="Never";
                img_fastfood_daily.setVisibility(View.INVISIBLE);
                img_fastfood_occasionally.setVisibility(View.INVISIBLE);
                img_fastfood_never.setVisibility(View.VISIBLE);
                rel_fastfood_daily.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_fastfood_occasionally.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_fastfood_never.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_fastfood_daily.setPadding(20,20,20,20);
                rel_fastfood_occasionally.setPadding(20,20,20,20);
                rel_fastfood_never.setPadding(20,20,20,20);
            }
        });








    }

    public void setPhysicalActivityData(final ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_physical_sedentary=findViewById(R.id.rel_physical_sedentary);
        final RelativeLayout rel_physical_moderate=findViewById(R.id.rel_physical_moderate);
        final RelativeLayout rel_physical_heavy=findViewById(R.id.rel_physical_heavy);
        final ImageView img_activity_sedentary=findViewById(R.id.img_activity_sedentary);
        final ImageView img_activity_moderate=findViewById(R.id.img_activity_moderate);
        final ImageView img_activity_heavy=findViewById(R.id.img_activity_heavy);

//        Sedentary (e.g: Office goers etc.)$Moderate (Daily 45 min dedicated activity)$Heavy (e.g: athlete etc.)

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==22){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().contains("Sedentary (e.g: Office goers etc.)")){
                        str_physical_activity="Sedentary (e.g: Office goers etc.)";

                        img_activity_sedentary.setVisibility(View.VISIBLE);
                        img_activity_moderate.setVisibility(View.INVISIBLE);
                        img_activity_heavy.setVisibility(View.INVISIBLE);
                        rel_physical_sedentary.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_physical_moderate.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_physical_heavy.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_physical_sedentary.setPadding(20,20,20,20);
                        rel_physical_moderate.setPadding(20,20,20,20);
                        rel_physical_heavy.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().contains("Moderate (Daily 45 min dedicated activity)")){
                        str_physical_activity="Moderate (Daily 45 min dedicated activity)";
                        img_activity_sedentary.setVisibility(View.INVISIBLE);
                        img_activity_moderate.setVisibility(View.VISIBLE);
                        img_activity_heavy.setVisibility(View.INVISIBLE);
                        rel_physical_sedentary.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_physical_moderate.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_physical_heavy.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_physical_sedentary.setPadding(20,20,20,20);
                        rel_physical_moderate.setPadding(20,20,20,20);
                        rel_physical_heavy.setPadding(20,20,20,20);
                    }

                    if (clsNewHealthParamData.getData().get(i).getAnswer().contains("Heavy (e.g: athlete etc.)")){
                        str_physical_activity="Heavy (e.g: athlete etc.)";
                        img_activity_sedentary.setVisibility(View.INVISIBLE);
                        img_activity_moderate.setVisibility(View.INVISIBLE);
                        img_activity_heavy.setVisibility(View.VISIBLE);
                        rel_physical_sedentary.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_physical_moderate.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_physical_heavy.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_physical_sedentary.setPadding(20,20,20,20);
                        rel_physical_moderate.setPadding(20,20,20,20);
                        rel_physical_heavy.setPadding(20,20,20,20);
                    }
                }



            }
        }


        rel_physical_sedentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_physical_activity="Sedentary (e.g: Office goers etc.)";

                img_activity_sedentary.setVisibility(View.VISIBLE);
                img_activity_moderate.setVisibility(View.INVISIBLE);
                img_activity_heavy.setVisibility(View.INVISIBLE);
                rel_physical_sedentary.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_physical_moderate.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_physical_heavy.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_physical_sedentary.setPadding(20,20,20,20);
                rel_physical_moderate.setPadding(20,20,20,20);
                rel_physical_heavy.setPadding(20,20,20,20);

            }
        });

        rel_physical_moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_physical_activity="Moderate (Daily 45 min dedicated activity)";
                img_activity_sedentary.setVisibility(View.INVISIBLE);
                img_activity_moderate.setVisibility(View.VISIBLE);
                img_activity_heavy.setVisibility(View.INVISIBLE);
                rel_physical_sedentary.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_physical_moderate.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_physical_heavy.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_physical_sedentary.setPadding(20,20,20,20);
                rel_physical_moderate.setPadding(20,20,20,20);
                rel_physical_heavy.setPadding(20,20,20,20);



            }
        });
        rel_physical_heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_physical_activity="Heavy (e.g: athlete etc.)";
                img_activity_sedentary.setVisibility(View.INVISIBLE);
                img_activity_moderate.setVisibility(View.INVISIBLE);
                img_activity_heavy.setVisibility(View.VISIBLE);
                rel_physical_sedentary.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_physical_moderate.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_physical_heavy.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_physical_sedentary.setPadding(20,20,20,20);
                rel_physical_moderate.setPadding(20,20,20,20);
                rel_physical_heavy.setPadding(20,20,20,20);
            }
        });








    }
    public void setBodyshapeData(final ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_healthybmi=findViewById(R.id.rel_healthybmi);
        final RelativeLayout rel_bananashape=findViewById(R.id.rel_bananashape);
        final RelativeLayout rel_pearshape=findViewById(R.id.rel_pearshape);
        final RelativeLayout rel_appleshape=findViewById(R.id.rel_appleshape);
        final ImageView img_healthybmi=findViewById(R.id.img_healthybmi);
        final ImageView img_bananashape=findViewById(R.id.img_bananashape);
        final ImageView img_pearshape=findViewById(R.id.img_pearshape);
        final ImageView img_appleshape=findViewById(R.id.img_appleshape);


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==23){

                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Healthy BMI")){
                        str_bodyshape="Healthy BMI";


                        img_healthybmi.setVisibility(View.VISIBLE);
                        img_bananashape.setVisibility(View.INVISIBLE);
                        img_pearshape.setVisibility(View.INVISIBLE);
                        img_appleshape.setVisibility(View.INVISIBLE);
                        rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_healthybmi.setPadding(20,20,20,20);
                        rel_bananashape.setPadding(20,20,20,20);
                        rel_pearshape.setPadding(20,20,20,20);
                        rel_appleshape.setPadding(20,20,20,20);

                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Banana shape")){
                        str_bodyshape="Banana shape";
                        img_healthybmi.setVisibility(View.INVISIBLE);
                        img_bananashape.setVisibility(View.VISIBLE);
                        img_pearshape.setVisibility(View.INVISIBLE);
                        img_appleshape.setVisibility(View.INVISIBLE);
                        rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_healthybmi.setPadding(20,20,20,20);
                        rel_bananashape.setPadding(20,20,20,20);
                        rel_pearshape.setPadding(20,20,20,20);
                        rel_appleshape.setPadding(20,20,20,20);
                    }

                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Pear shape")){
                        str_bodyshape="Pear shape";
                        img_healthybmi.setVisibility(View.INVISIBLE);
                        img_bananashape.setVisibility(View.INVISIBLE);
                        img_pearshape.setVisibility(View.VISIBLE);
                        img_appleshape.setVisibility(View.INVISIBLE);
                        rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_healthybmi.setPadding(20,20,20,20);
                        rel_bananashape.setPadding(20,20,20,20);
                        rel_pearshape.setPadding(20,20,20,20);
                        rel_appleshape.setPadding(20,20,20,20);
                    }

                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Apple shape")){
                        str_bodyshape="Apple shape";
                        img_healthybmi.setVisibility(View.INVISIBLE);
                        img_bananashape.setVisibility(View.INVISIBLE);
                        img_pearshape.setVisibility(View.INVISIBLE);
                        img_appleshape.setVisibility(View.VISIBLE);
                        rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_healthybmi.setPadding(20,20,20,20);
                        rel_bananashape.setPadding(20,20,20,20);
                        rel_pearshape.setPadding(20,20,20,20);
                        rel_appleshape.setPadding(20,20,20,20);
                    }
                }


            }
        }

        rel_healthybmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_bodyshape="Healthy BMI";


                img_healthybmi.setVisibility(View.VISIBLE);
                img_bananashape.setVisibility(View.INVISIBLE);
                img_pearshape.setVisibility(View.INVISIBLE);
                img_appleshape.setVisibility(View.INVISIBLE);
                rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_healthybmi.setPadding(20,20,20,20);
                rel_bananashape.setPadding(20,20,20,20);
                rel_pearshape.setPadding(20,20,20,20);
                rel_appleshape.setPadding(20,20,20,20);
            }
        });

        rel_bananashape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_bodyshape="Banana shape";
                img_healthybmi.setVisibility(View.INVISIBLE);
                img_bananashape.setVisibility(View.VISIBLE);
                img_pearshape.setVisibility(View.INVISIBLE);
                img_appleshape.setVisibility(View.INVISIBLE);
                rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_healthybmi.setPadding(20,20,20,20);
                rel_bananashape.setPadding(20,20,20,20);
                rel_pearshape.setPadding(20,20,20,20);
                rel_appleshape.setPadding(20,20,20,20);
            }
        });
        rel_pearshape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_bodyshape="Pear shape";
                img_healthybmi.setVisibility(View.INVISIBLE);
                img_bananashape.setVisibility(View.INVISIBLE);
                img_pearshape.setVisibility(View.VISIBLE);
                img_appleshape.setVisibility(View.INVISIBLE);
                rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_healthybmi.setPadding(20,20,20,20);
                rel_bananashape.setPadding(20,20,20,20);
                rel_pearshape.setPadding(20,20,20,20);
                rel_appleshape.setPadding(20,20,20,20);
            }
        });

        rel_appleshape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_bodyshape="Apple shape";
                img_healthybmi.setVisibility(View.INVISIBLE);
                img_bananashape.setVisibility(View.INVISIBLE);
                img_pearshape.setVisibility(View.INVISIBLE);
                img_appleshape.setVisibility(View.VISIBLE);
                rel_healthybmi.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_bananashape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_pearshape.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_appleshape.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_healthybmi.setPadding(20,20,20,20);
                rel_bananashape.setPadding(20,20,20,20);
                rel_pearshape.setPadding(20,20,20,20);
                rel_appleshape.setPadding(20,20,20,20);
            }
        });






    }

    public void setSpecialmentionHealthData(ClsNewHealthParamData clsNewHealthParamData){
        final RelativeLayout rel_anyotherspecialmentionhealth_yes=findViewById(R.id.rel_anyotherspecialmentionhealth_yes);
        final RelativeLayout rel_anyotherspecialmentionhealth_no=findViewById(R.id.rel_anyotherspecialmentionhealth_no);
        final ImageView img_anyotherspecialmentionhealth_no=findViewById(R.id.img_anyotherspecialmentionhealth_no);
        final ImageView img_anyotherspecialmentionhealth_yes=findViewById(R.id.img_anyotherspecialmentionhealth_yes);
        final LinearLayout ll_anyotherspecialmentionhealth=findViewById(R.id.ll_anyotherspecialmentionhealth);
        edt_remark_anyotherspecialmentionhealth=findViewById(R.id.edt_remark_anyotherspecialmentionhealth);


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==24){
                if (clsNewHealthParamData.getData().get(i).getAnswer()!=null){
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("Yes, please specify the name in remarks")){
                        str_anyotherspecialmentionhealth="Yes, please specify the name in remarks";

                        ll_anyotherspecialmentionhealth.setVisibility(View.VISIBLE);

                        for (int a = 0; a <ary_HealthParamDataArrayList.size() ; a++) {
                            if (ary_HealthParamDataArrayList.get(a).getQuestionId()==24){
                                edt_remark_anyotherspecialmentionhealth.setText(clsNewHealthParamData.getData().get(i).getRemark());

                            }
                        }

                        img_anyotherspecialmentionhealth_yes.setVisibility(View.VISIBLE);
                        img_anyotherspecialmentionhealth_no.setVisibility(View.INVISIBLE);
                        rel_anyotherspecialmentionhealth_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_anyotherspecialmentionhealth_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_anyotherspecialmentionhealth_yes.setPadding(20,20,20,20);
                        rel_anyotherspecialmentionhealth_no.setPadding(20,20,20,20);
                    }
                    if (clsNewHealthParamData.getData().get(i).getAnswer().equalsIgnoreCase("No")){
                        str_anyotherspecialmentionhealth="No";
                        ll_anyotherspecialmentionhealth.setVisibility(View.GONE);

                        img_anyotherspecialmentionhealth_no.setVisibility(View.VISIBLE);
                        img_anyotherspecialmentionhealth_yes.setVisibility(View.INVISIBLE);
                        rel_anyotherspecialmentionhealth_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                        rel_anyotherspecialmentionhealth_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                        rel_anyotherspecialmentionhealth_yes.setPadding(20,20,20,20);
                        rel_anyotherspecialmentionhealth_no.setPadding(20,20,20,20);
                    }
                }

            }
        }


        rel_anyotherspecialmentionhealth_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_anyotherspecialmentionhealth="Yes, please specify the name in remarks";
                ll_anyotherspecialmentionhealth.setVisibility(View.VISIBLE);

                img_anyotherspecialmentionhealth_yes.setVisibility(View.VISIBLE);
                img_anyotherspecialmentionhealth_no.setVisibility(View.INVISIBLE);
                rel_anyotherspecialmentionhealth_yes.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_anyotherspecialmentionhealth_no.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_anyotherspecialmentionhealth_yes.setPadding(20,20,20,20);
                rel_anyotherspecialmentionhealth_no.setPadding(20,20,20,20);

            }
        });
        rel_anyotherspecialmentionhealth_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_anyotherspecialmentionhealth.setVisibility(View.GONE);


                str_anyotherspecialmentionhealth="No";


                img_anyotherspecialmentionhealth_no.setVisibility(View.VISIBLE);
                img_anyotherspecialmentionhealth_yes.setVisibility(View.INVISIBLE);
                rel_anyotherspecialmentionhealth_no.setBackground(getResources().getDrawable(R.drawable.bg_done));
                rel_anyotherspecialmentionhealth_yes.setBackground(getResources().getDrawable(R.drawable.bg_black_box));
                rel_anyotherspecialmentionhealth_yes.setPadding(20,20,20,20);
                rel_anyotherspecialmentionhealth_no.setPadding(20,20,20,20);
            }
        });



    }


    public void setAllData(){

        try {
            saveDataofpage1();
            saveAlcoholData();
            saveSmokeData();
            setTabaccoData();
            saveStressData();
            savemedicalConditionData();
//        saveFoodAlergyData();
            saveFoodAllergyConditionData();
            saveHealthSupplimentData();
            saveEmotionalSufferingData();
            saveSelfMotivatorData();
            saveMealData();
            saveDistractedData();
            saveFastFoodData();
            savePhysicalActivityData();
            saveBodyShapeData();
            saveAnyotherhealth();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void savemedicalConditionData() {
        for (int i = 0; i < ary_HealthParamDataArrayList.size(); i++) {

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==14){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_medical_condition);
                if (IsMedicalFoundAnyother){
                    ary_HealthParamDataArrayList.get(i).setRemark(edt_remark_medical_condition.getText().toString().trim());

                }else {
                    ary_HealthParamDataArrayList.get(i).setRemark("");

                }

            }
        }
    }

    private void saveFoodAllergyConditionData() {
        for (int i = 0; i < ary_HealthParamDataArrayList.size(); i++) {

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==15){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_food_condition);
                if (IsFoodFoundAnyother){
                    ary_HealthParamDataArrayList.get(i).setRemark(edt_remark_foodallergy_condition.getText().toString().trim());

                }else {
                    ary_HealthParamDataArrayList.get(i).setRemark("");

                }

            }
        }
    }



    private void saveAnyotherhealth() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==24){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_anyotherspecialmentionhealth);
                if (str_anyotherspecialmentionhealth.equalsIgnoreCase("No")){
                    ary_HealthParamDataArrayList.get(i).setRemark("");

                }else {
                    ary_HealthParamDataArrayList.get(i).setRemark(edt_remark_anyotherspecialmentionhealth.getText().toString());

                }
            }
        }
    }

    private void saveBodyShapeData() {

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==23){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_bodyshape);
                ary_HealthParamDataArrayList.get(i).setRemark("");
            }
        }

    }

    private void savePhysicalActivityData() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==22){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(1).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_physical_activity);
                ary_HealthParamDataArrayList.get(i).setRemark("");
            }
        }
    }

    private void saveFastFoodData() {

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==21){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_fastfood);
                ary_HealthParamDataArrayList.get(i).setRemark("");
            }
        }
    }

    private void saveDistractedData() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==20){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_distracted);

            }
        }
    }

    private void saveMealData() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==19){

                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_meal);


            }
        }
    }

    private void saveSelfMotivatorData() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==18){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_selfmotivator);
            }
        }
    }

    private void saveEmotionalSufferingData() {

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==17){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_emotionalsuffering);

                if (str_emotionalsuffering.equalsIgnoreCase("Any other, please specify in remarks")){
                    ary_HealthParamDataArrayList.get(i).setRemark(edt_remark_emotionalsuffering.getText().toString());


                }else {
                    ary_HealthParamDataArrayList.get(i).setRemark("");

                }

            }
        }

    }

    private void saveHealthSupplimentData() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==16){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_health_suppliment);
                if (str_health_suppliment.equalsIgnoreCase("No")){
                    ary_HealthParamDataArrayList.get(i).setRemark("");

                }else {
                    ary_HealthParamDataArrayList.get(i).setRemark(edt_remark_health_suppliment.getText().toString());

                }
//                healthParamData_static.add(ary_HealthParamDataArrayList.get(16));
            }
        }
    }

    private void saveFoodAlergyData() {


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==15){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_food_allergy);
                if (str_food_allergy.equalsIgnoreCase("No")){
                    ary_HealthParamDataArrayList.get(i).setRemark("");

                }else {
                    ary_HealthParamDataArrayList.get(i).setRemark(edt_food_allergy_remark.getText().toString());

                }
            }
        }
    }

    private void saveStressData() {


        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==13){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_stress);
//                healthParamData_static.add(ary_HealthParamDataArrayList.get(1));
            }
        }


    }

    private void setTabaccoData() {
        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==12){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_tabcco);
            }
        }



//        healthParamData_static.add(ary_HealthParamDataArrayList.get(12));
    }

    private void saveSmokeData() {

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==10){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_smoke);
            }

        }

//        healthParamData_static.add(ary_HealthParamDataArrayList.get(10));

    }

    private void saveAlcoholData() {

        for (int i = 0; i <ary_HealthParamDataArrayList.size() ; i++) {
            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==8){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_rel_alchol);
            }

            if (ary_HealthParamDataArrayList.get(i).getQuestionId()==9){
                ary_HealthParamDataArrayList.get(i).setItemList("");
                ary_HealthParamDataArrayList.get(i).setQuestion("");
                ary_HealthParamDataArrayList.get(i).setAnswer(str_alcohol_frequency);

            }



        }



    }


    @Override
    public void GetfoodCondition(ArrayList<MedicalConditionData> medicalcondition) {


        StringBuilder stringBuilder_medicalCondition=new StringBuilder();
        IsFoodFoundAnyother=false;

        for (int i = 0; i <medicalcondition.size() ; i++) {
            if (medicalcondition.get(i).isSelect()){

                if (medicalcondition.get(i).getMedicalconditionName().equalsIgnoreCase("Any other, please specify in remarks")){
                    IsFoodFoundAnyother=true;
                }

                stringBuilder_medicalCondition.append(medicalcondition.get(i).getMedicalconditionName()+"$");

            }
        }

        if (IsFoodFoundAnyother){
            ll_remark_foodallergy_condition.setVisibility(View.VISIBLE);
        }else {
            ll_remark_foodallergy_condition.setVisibility(View.GONE);

        }


        if (!stringBuilder_medicalCondition.toString().isEmpty()) {
            str_food_condition = "";
            if (stringBuilder_medicalCondition.toString().endsWith("$")) {
                str_food_condition = stringBuilder_medicalCondition.toString().substring(0, stringBuilder_medicalCondition.toString().length() - 1);
            } else {
                str_food_condition = stringBuilder_medicalCondition.toString();
            }



        }

    }

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
