package com.shamrock.reework.activity.reeworkerhealth.app;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.NewHealthparameterGoobActivity;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ClsNewHealthParamData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthModule.service.HealthRequest;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.common.NonSwipeableViewPager;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DynamicHealthparamActivity extends AppCompatActivity implements View.OnClickListener {
    private NonSwipeableViewPager vp_health_param;
    private HealthParametersService healthParametersService;
    private SessionManager sessionManager;
    private boolean isFirstTime;
    private Utils utils;
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment1 fragment1;
    public static ArrayList<HealthParamData> healthParamData_static= new ArrayList<>();
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private ArrayList<Data> dataArrayList;
    private  ViewPager pager;
    private Button btn_next;
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


    @Override
    public void onBackPressed() {

        boolean ispayemnt=getIntent().getBooleanExtra("frompayment",false);
        if (vp_health_param.getCurrentItem()==0){
            if (ispayemnt){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.back_arrow));

                return;
            }
        }




        switch (vp_health_param.getCurrentItem()) {
            case 3:
                imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                btn_next.setText("Submit");
                posfrag=1;
                break;
            case 2:
                imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                btn_next.setText("Next");
                posfrag=2;
                break;
            case 1:
                imgProgress1.setImageResource(R.drawable.ic_prog_1);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);
                btn_next.setText("Next");
                posfrag=3;
                break;
            case 0:
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_healthparam);
        vp_health_param=findViewById(R.id.vp_health_param);

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
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Health Profile");
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
        sessionManager=new SessionManager(DynamicHealthparamActivity.this);
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
    }


    private void callHealthParametergetApi() {

        HealthRequest healthRequest=new HealthRequest();
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Log.e("idd", String.valueOf(userID));

        healthRequest.setUserId(userID);


        utils.showProgressbar(DynamicHealthparamActivity.this);

        Call<ClsNewHealthParamData> call = healthParametersService.getReeowrkerHealthData(userID);
        call.enqueue(new Callback<ClsNewHealthParamData>()
        {
            @Override
            public void onResponse(Call<ClsNewHealthParamData> call, retrofit2.Response<ClsNewHealthParamData> response)
            {

                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsNewHealthParamData clsNewHealthParamData = response.body();
                        if (clsNewHealthParamData.getCode()!=null){

                            healthParamDataArrayList=clsNewHealthParamData.getData();

                            if (!healthParamDataArrayList.isEmpty()){
                                setToFragments(clsNewHealthParamData.getData());
                            }else {
                                Toast.makeText(DynamicHealthparamActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                                finish();

                            }

                        }else {
                            Toast.makeText(DynamicHealthparamActivity.this, ""+clsNewHealthParamData.getMessage(), Toast.LENGTH_SHORT).show();
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
            vp_health_param.setAdapter(new PagerAdapter(getSupportFragmentManager()));
            vp_health_param.setOffscreenPageLimit(4);
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

        utils.showProgressbar(DynamicHealthparamActivity.this);

         //   {"ActivityStatus":2,"Avgsleep":8,"Drink":1,"Easy_Monitoring_of_Body_Parameters":0,"FoodcultureID":1,"FoodtypeID":3,"Health_And_Wellness":0,"Height":195,"MedicalConditionID":"1,2,3,4,5,6,7,8,9,10,11,12","Smoke":1,"UserID":3040,"Waterintake":5,"Weight":64.0,"Weight_Control":0,"body_Type":1,"body_shape":2,"weight_Gain":1}
        Call<ClsgetPostData> call = healthParametersService.saveHealthParameter(clsPostHealthData);
        call.enqueue(new Callback<ClsgetPostData>()
        {
            @Override
            public void onResponse(Call<ClsgetPostData> call, Response<ClsgetPostData> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    healthParamData_static.clear();
                    ClsgetPostData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        Toast.makeText(DynamicHealthparamActivity.this, "" + listResponse.getData(), Toast.LENGTH_SHORT).show();
                        if (getIntent().getStringExtra("param")!=null){
                            if (getIntent().getStringExtra("param").equalsIgnoreCase("From_Home")){
                                finish();
                            }else
                            if (getIntent().getStringExtra("param").equalsIgnoreCase("Trial")) {
                                Intent intent = new Intent(DynamicHealthparamActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();


                            }else {
                                Intent intent = new Intent(DynamicHealthparamActivity.this, NewHealthparameterGoobActivity.class);

                                if (getIntent().getStringExtra("FromWeb")!=null&&getIntent().getStringExtra("FromWeb").equalsIgnoreCase("true")){
                                    intent.putExtra("FromWeb","true");
                                }
                                startActivity(intent);

                            }

                        }else {
                            Intent intent = new Intent(DynamicHealthparamActivity.this, NewHealthparameterGoobActivity.class);

                            if (getIntent().getStringExtra("FromWeb")!=null&&getIntent().getStringExtra("FromWeb").equalsIgnoreCase("true")){
                                intent.putExtra("FromWeb","true");
                            }
                            startActivity(intent);

                        }



//                        }
                        /* finish();*/
                    }
                    else
                    {
                        Toast.makeText(DynamicHealthparamActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    healthParamData_static.clear();

                    Toast.makeText(DynamicHealthparamActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<ClsgetPostData> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
                healthParamData_static.clear();


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
                      vp_health_param.setCurrentItem(3);
                      btn_next.setText("Submit");
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
                        if (Utils.isNetworkAvailable(DynamicHealthparamActivity.this))
                        {
                            callHealthParametergetApi();
                        }

                    }
                });

        snackbar.show();
    }



}
