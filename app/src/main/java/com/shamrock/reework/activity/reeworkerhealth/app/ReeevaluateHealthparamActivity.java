package com.shamrock.reework.activity.reeworkerhealth.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.BeforeAfterModule.activity.BeforeAfterActivity;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ClsNewHealthParamData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthModule.service.HealthRequest;
import com.shamrock.reework.activity.HomeModule.pojo.ClsProfileHeaderData;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.profilehealth.Fragment1P;
import com.shamrock.reework.activity.profilehealth.Fragment2P;
import com.shamrock.reework.activity.profilehealth.Fragment3P;
import com.shamrock.reework.activity.profilehealth.Fragment4P;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.common.NonSwipeableViewPager;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity.healthParamData_static;

public class ReeevaluateHealthparamActivity extends AppCompatActivity implements View.OnClickListener {
    private NonSwipeableViewPager vp_health_param;
    private HealthParametersService healthParametersService;
    private SessionManager sessionManager;
    private boolean isFirstTime;
    private Utils utils;
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment1 fragment1;
//    public static ArrayList<HealthParamData> healthParamData_static= new ArrayList<>();
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private ArrayList<Data> dataArrayList;
    private  ViewPager pager;
    private Button btn_next;
    public  boolean ISFreeze=false;
    public static final String DROPDOWN ="DROPDOWN";
    public static final String RADIO_BUTTON ="RADIO";
    public static final String INPUT_BOX ="INPUTBOX";
    public static final String CHECK_BOX ="MULTISELECT";
    ArrayList<HealthParamData> healthParamDataArrayList;
    ArrayList<HealthParamData> healthParamDataArrayList_filter;
    ImageView imgProgress1, imgProgress2, imgProgress3,imgProgress4;
    int posfrag;
    ImageView imageView;
    boolean isfrompayemt;
RadioButton rd_button_profile,rd_button_health;
    private HomeFragmentService service;
    boolean ISFromReevaluate=false;

    @Override
    public void onBackPressed() {

        overridePendingTransition(0,0);

        boolean ispayemnt=getIntent().getBooleanExtra("frompayment",false);



        if (vp_health_param.getCurrentItem()==0){
            if (ispayemnt){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                return;
            }
        }

        btn_next.setVisibility(View.VISIBLE);



        switch (vp_health_param.getCurrentItem()) {
            case 3:
                if (ISFromReevaluate){
                    btn_next.setVisibility(View.VISIBLE);
                }else {
                    btn_next.setVisibility(View.VISIBLE);

                }
                imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                btn_next.setText("Next");

                posfrag=1;
                break;
            case 2:
                btn_next.setVisibility(View.VISIBLE);

                imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                btn_next.setText("Next");
                posfrag=2;
                break;
            case 1:
                btn_next.setVisibility(View.VISIBLE);

                imgProgress1.setImageResource(R.drawable.ic_prog_1);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                btn_next.setText("Next");
                posfrag=3;
                break;
            case 0:
                btn_next.setVisibility(View.VISIBLE);

                imgProgress1.setImageResource(R.drawable.ic_prog_1);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);



                btn_next.setText("Next");
                break;

        }





        if (vp_health_param.getCurrentItem()==0){
            finish();

        }else if (vp_health_param.getCurrentItem()==1){
            vp_health_param.setCurrentItem(0);


            if (!ispayemnt){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

            }else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

            }

        }else if (vp_health_param.getCurrentItem()==2){
            vp_health_param.setCurrentItem(1);

        }else if (vp_health_param.getCurrentItem()==3){
            vp_health_param.setCurrentItem(2);

        }




    }
    private int userID;
//    private SessionManager sessionManager;

    RadioGroup radioGroup_sleep;

    private Context context;
    Toolbar toolbar;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_healthparam_profile);
        context = ReeevaluateHealthparamActivity.this;
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
        vp_health_param=findViewById(R.id.vp_health_param);
        rd_button_profile=findViewById(R.id.rd_button_profile);
        rd_button_health=findViewById(R.id.rd_button_health);
        sessionManager=new SessionManager(this);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        
        Callprofileheader();
        rd_button_profile.setText(sessionManager.getStringValue("KEY_BASIC_PROFILE"));
        rd_button_health.setText(sessionManager.getStringValue("KEY_HEALTH_PROFILE"));
        rd_button_health.performClick();
        rd_button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReeevaluateHealthparamActivity.this, MyProfileActivity.class));
                overridePendingTransition(0,0);
                finish();

            }
        });

         isfrompayemt= getIntent().getBooleanExtra("frompayment",false);

        vp_health_param.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        imgProgress1 = findViewById(R.id.imgHealthProgress_1);
        imgProgress2 = findViewById(R.id.imgHealthProgress_2);
        imgProgress3 = findViewById(R.id.imgHealthProgress_3);
        imgProgress4 = findViewById(R.id.imgHealthProgress_4);

         imageView=findViewById(R.id.imgLeft);


        if (isfrompayemt){
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

        }else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

        }
//        imageView.setVisibility(View.INVISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();
                if (vp_health_param.getCurrentItem()==0){
                    boolean isfrompayemt= getIntent().getBooleanExtra("frompayment",false);

                    if (!isfrompayemt){
//                        finish();
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                    }
                }else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                }


            }
        });


       if (!isfrompayemt){
           imageView.setEnabled(true);

           if (vp_health_param.getCurrentItem()==0){

               imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));


           }else {
               imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

           }
       }else {
           imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_hamburger_menu));
           imageView.setEnabled(true);

       }




        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        utils=new Utils();
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        sessionManager=new SessionManager(ReeevaluateHealthparamActivity.this);
//        vp_health_param.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        callHealthParametergetApi();


        vp_health_param.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {






            }

            @Override
            public void onPageScrollStateChanged(int i) {

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



    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
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

        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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


    private void callHealthParametergetApi() {

        HealthRequest healthRequest=new HealthRequest();
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Log.e("idd", String.valueOf(userID));

        healthRequest.setUserId(userID);


        utils.showProgressbar(ReeevaluateHealthparamActivity.this);

        Call<ClsNewHealthParamData> call = healthParametersService.getReeowrkerHealthData(userID);
        call.enqueue(new Callback<ClsNewHealthParamData>()
        {
            @Override
            public void onResponse(Call<ClsNewHealthParamData> call, Response<ClsNewHealthParamData> response)
            {

                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsNewHealthParamData clsNewHealthParamData = response.body();
                        if (clsNewHealthParamData.getCode()!=null){

                            healthParamDataArrayList=clsNewHealthParamData.getData();
                            healthParamDataArrayList_filter=new ArrayList<>();
                            for (int i = 0; i <healthParamDataArrayList.size() ; i++) {




                            }

                            if (!healthParamDataArrayList.isEmpty()){
                                setToFragments(clsNewHealthParamData.getData());
                            }else {
                                Toast.makeText(ReeevaluateHealthparamActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }else {
                            Toast.makeText(ReeevaluateHealthparamActivity.this, ""+clsNewHealthParamData.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void setToFragments(ArrayList<HealthParamData> data) {

        fragment1 = new Fragment1(getPageData(1));
        fragment2 = new Fragment2(getPageData(2));
        fragment3 = new Fragment3(getPageData(3));
        fragment4 = new Fragment4(getPageData(4));

        fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(fragment1);
        fragmentArrayList.add(fragment2);
        fragmentArrayList.add(fragment3);
        fragmentArrayList.add(fragment4);
        assert vp_health_param != null;
        try {
            vp_health_param.setOffscreenPageLimit(4);


            vp_health_param.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        }catch (Exception e){
            e.printStackTrace();
        }

        final StepperIndicator indicator = findViewById(R.id.stepper_indicator);
        // We keep last page for a "finishing" page
        indicator.setViewPager(vp_health_param, false);

        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                vp_health_param.setCurrentItem(step, true);
            }
        });

    }


    private void callHealthParameterPostApi(ClsPostHealthData clsPostHealthData)
    {

        utils.showProgressbar(ReeevaluateHealthparamActivity.this);


        ClsPostHealthData clsPostHealthData_new=new ClsPostHealthData();
        ArrayList<HealthParamData> arylst_Data=new ArrayList<>();





        Call<ClsgetPostData> call = healthParametersService.saveHealthParameter(clsPostHealthData);

        call.enqueue(new Callback<ClsgetPostData>()
        {
            @Override
            public void onResponse(Call<ClsgetPostData> call, Response<ClsgetPostData> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsgetPostData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        healthParamData_static.clear();

                        if (listResponse.getData()!=null){
                            Toast.makeText(ReeevaluateHealthparamActivity.this, "" + listResponse.getData(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(ReeevaluateHealthparamActivity.this, "Saved successfully", Toast.LENGTH_SHORT).show();

                        }
                        if (getIntent().getStringExtra("param")!=null){
                            if (getIntent().getStringExtra("param").equalsIgnoreCase("From_Home")){
                                finish();
                            }else {
//                                startActivity(new Intent(ProfileDynamicHealthparamActivity.this, NewHealthparameterGoobActivity.class));



                                vp_health_param.setCurrentItem(0);
                                imgProgress1.setImageResource(R.drawable.ic_prog_1);
                                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                                btn_next.setText("Next");
                                imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));
//                                callHealthParametergetApi();
                                finish();
                            }

                        }else {
//                            startActivity(new Intent(ProfileDynamicHealthparamActivity.this, NewHealthparameterGoobActivity.class));

                            vp_health_param.setCurrentItem(0);
                            imgProgress1.setImageResource(R.drawable.ic_prog_1);
                            imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                            imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                            imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                            btn_next.setText("Next");
                            imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));
//                            callHealthParametergetApi();
                            finish();

                        }



//                        }
                        /* finish();*/
                    }
                    else
                    {
//                        Toast.makeText(ProfileDynamicHealthparamActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ReeevaluateHealthparamActivity.this, "You have successfully Reeevaluated" , Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
                else
                {
                    Toast.makeText(ReeevaluateHealthparamActivity.this, "You have successfully Reeevaluated", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(ReeevaluateHealthparamActivity.this, "You have successfully Reeevaluated", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    private ArrayList<HealthParamData> getPageData(int pos) {



        int count=healthParamDataArrayList.size()/4;
        int secondCount=count+count+1;
        int thridCount=count+count+count;
        int foutcount=healthParamDataArrayList.size()+1;

        ArrayList<HealthParamData> data1 = null;
        if(pos==1){
            data1= new ArrayList<>();
            for (int i = 0; i < count; i++) {
                data1.add(healthParamDataArrayList.get(i));
            }
        }else if(pos==2){
            data1= new ArrayList<>();

            for (int i = count; i <secondCount; i++) {
                data1.add(healthParamDataArrayList.get(i));
            }
        }else if(pos==3){
            data1= new ArrayList<>();

            for (int i = secondCount; i < thridCount; i++) {
                data1.add(healthParamDataArrayList.get(i));
            }
        }else if(pos==4){
            data1= new ArrayList<>();

            for (int i = thridCount; i < foutcount-1; i++) {
                data1.add(healthParamDataArrayList.get(i));
            }
        }
        return data1;

    }



    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "Page " + position;
//        }
    }



    @Override
    public void onClick(View v) {
      if(v.getId()==R.id.btn_next){
          btn_next.setVisibility(View.VISIBLE);

          if (fragment1.edt_weight.getText().toString().isEmpty()){
              Toast.makeText(this, "Please enter the weight", Toast.LENGTH_SHORT).show();
              return;
          }
          if (fragment1.edt_hight.getText().toString().isEmpty()&&fragment1.edt_feet.getText().toString().isEmpty()){
              Toast.makeText(this, "Please enter the height", Toast.LENGTH_SHORT).show();
              return;
          }


          switch (vp_health_param.getCurrentItem()) {
              case 0:
                  imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                  imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                  imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                  imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));
                  posfrag=1;
                  break;
              case 1:
                  imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                  imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                  posfrag=2;
                  imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                  break;
              case 2:
                  imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress3.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                  posfrag=3;
                  imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                  break;
              case 3:
                  imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress3.setImageResource(R.drawable.ic_health_progress_done);
                  imgProgress4.setImageResource(R.drawable.ic_health_progress_done);
                  imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                  break;

          }






          if(vp_health_param.getCurrentItem()==0){
            if(fragment1!=null){


                fragment1.getFirstPageData();
                vp_health_param.setCurrentItem(1);
            }
          }else if(vp_health_param.getCurrentItem()==1){
              if(fragment2!=null){
                  if (fragment2.getSecondPageData()){
                      vp_health_param.setCurrentItem(2);

                  }else {
                      return;
                  }

              }
          }else if(vp_health_param.getCurrentItem()==2){
              if(fragment3!=null){
                  if (fragment3.getThirdPageData()){
                      if (ISFromReevaluate){

                      }
                      vp_health_param.setCurrentItem(3);
                      btn_next.setText("Submit");

                      if (ISFromReevaluate){
                          btn_next.setVisibility(View.VISIBLE);
                      }else {
                          btn_next.setVisibility(View.GONE);
                      }

                  }else {
                      return;
                  }


              }
          }
          else if(vp_health_param.getCurrentItem()==3){
              if(fragment4!=null){

              if (fragment4.getFourthPageData()){
                  ClsPostHealthData clsPostHealthData=new ClsPostHealthData();
                  clsPostHealthData.setReeworkedId(sessionManager.getIntValue(SessionManager.KEY_USER_ID));
                  clsPostHealthData.setParamlist(healthParamData_static);


                  callHealthParameterPostApi(clsPostHealthData);
                  Log.d("dff",clsPostHealthData.toString());
              }else {
                  return;
              }





                 //Api call
              }
          }



      }
    }
    private void Callprofileheader() {

        service = Client.getClient().create(HomeFragmentService.class);

        service = Client.getClient().create(HomeFragmentService.class);

        Call<ClsProfileHeaderData> call = service.getProfileHeaderApi();

        call.enqueue(new Callback<ClsProfileHeaderData>() {
            @Override
            public void onResponse(Call<ClsProfileHeaderData> call, Response<ClsProfileHeaderData> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsProfileHeaderData listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() .equals("1") ) {

                        if (listResponse.getData()!=null&&!listResponse.getData().isEmpty()){
                            sessionManager=new SessionManager(ReeevaluateHealthparamActivity.this);
                            sessionManager.setStringValue("KEY_BASIC_PROFILE",listResponse.getData().get(0).getHeaderName());
                            sessionManager.setStringValue("KEY_HEALTH_PROFILE",listResponse.getData().get(1).getHeaderName());
                            rd_button_profile.setText(sessionManager.getStringValue("KEY_BASIC_PROFILE"));
                            rd_button_health.setText(sessionManager.getStringValue("KEY_HEALTH_PROFILE"));
                        }

                        Log.d("FCM REGISTER--->", "FCM REGISTER SUCCESSFULLY");


                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ClsProfileHeaderData> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
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
                        if (Utils.isNetworkAvailable(ReeevaluateHealthparamActivity.this))
                        {
                            callHealthParametergetApi();
                        }

                    }
                });

        snackbar.show();
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
