package com.shamrock.reework.activity.services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.SnipeetActivity;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HealthModule.adapter.HealthobjplanlistAdapter;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.services.pojo.ClsExistingPlan;
import com.shamrock.reework.activity.services.pojo.ExistingPlanData;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyServiceActivity extends AppCompatActivity {
    Context context;
    String strImahePath;
    private ListView lst_objetive_plan;
    private TextView txt_health_obj_description,txt_health_obj_name;
    private TextView txt_amount_health_obj;
    Button btn_submit_payment;
    PaidSubscriptionData clsHealthobjective;
    ArrayList<PaidSubscriptionData> paidSubscriptionData;
    //    RegistrationRequest registrationRequest;
    private Utils utils;
    private SessionManager sessionManager;

    private RegistrationService regService;
    String from="";
    int size;
    int pos;
    UnfreezeService unfreezeService;
    ClsExistingPlan clsExistingPlan;
    HealthParametersService healthParametersService;
    TextView txt_subscription_header;
    TextView btn_submit_change_subscription;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyServiceActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
    public static String convertDate(String dateInMilliseconds,String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_service);
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils=new Utils();
        txt_subscription_header=findViewById(R.id.txt_subscription_header);
        btn_submit_change_subscription=findViewById(R.id.btn_submit_change_subscription);
        context=MyServiceActivity.this;
        clsExistingPlan=new ClsExistingPlan();
        clsExistingPlan.setMessage("");
        SessionManager  sessionManager = new SessionManager(context);
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            txt_subscription_header.setText("Last Membership Plan");
            btn_submit_change_subscription.setText("Renew Membership Plan");


        }else {
            txt_subscription_header.setText("Existing Membership Plan");
            btn_submit_change_subscription.setText("Change Membership Plan");


        }

        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Membership Plan");
        ImageView imageView=findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                Intent intent = new Intent(MyServiceActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


        Button btn_submit_change_subscription=findViewById(R.id.btn_submit_change_subscription);
        btn_submit_change_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent=new  Intent(MyServiceActivity.this, ViewPagerActivity.class);
                intent.putExtra("param", "change");
                startActivity(intent);

            }
        });

//        ExistingPlanData existingPlanData=new ExistingPlanData();
//        existingPlanData.setPlanName("Health & Wellness");
//        existingPlanData.setCurrency("₹");
//        existingPlanData.setPrice("1000");
//        existingPlanData.setDesription("It will provide Personal Wellness Score (PWS) which is a scientific approach. Any deficiency in this score will be provided with advises");
//
//        ArrayList<String> stringArrayList=new ArrayList<>();
//        stringArrayList.add("service1");
//        stringArrayList.add("service2");
//        stringArrayList.add("service3");
//        stringArrayList.add("service4");
//        stringArrayList.add("service5");
//        stringArrayList.add("service6");


//        existingPlanData.setServices(stringArrayList);
//        clsExistingPlan.setData(existingPlanData);


        lst_objetive_plan=findViewById(R.id.lst_plan_features);
        txt_health_obj_name=findViewById(R.id.txt_plan_name);
        txt_health_obj_description=findViewById(R.id.txt_plan_description);
        txt_amount_health_obj=findViewById(R.id.txt_plan_amount);
        btn_submit_payment=findViewById(R.id.btn_submit_payment);
        sessionManager = new SessionManager(context);
//        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);

        getMyplanApi();
//        txt_health_obj_name.setText(clsExistingPlan.getData().getPlanName());
//        txt_health_obj_description.setText(clsExistingPlan.getData().getDesription());
//        txt_amount_health_obj.setText(clsExistingPlan.getData().getCurrency()+" "+Math.round(Double.valueOf(clsExistingPlan.getData().getPrice())));
//
//        lst_objetive_plan.setAdapter(new HealthobjplanlistAdapter(this,clsExistingPlan.getData().getServices()));


    }
    private void getMyplanApi() {
        utils.showProgressbar(this);
        sessionManager=new SessionManager(context);
        Call<ClsExistingPlan> call = healthParametersService.getUserSubscriptionDetails(sessionManager.getIntValue(SessionManager.KEY_USER_ID));
        call.enqueue(new Callback<ClsExistingPlan>()
        {
            @Override
            public void onResponse(Call<ClsExistingPlan> call, Response<ClsExistingPlan> response) {
                utils.hideProgressbar();
                try{
                    if (response.code() == Client.RESPONSE_CODE_OK) {
                        ClsExistingPlan listResponse = response.body();

                        if (listResponse != null && listResponse.getCode() == 1) {

                            if (listResponse.getData().getSubPlanName()!=null&&!listResponse.getData().getSubPlanName().isEmpty()){
                                txt_health_obj_name.setText(listResponse.getData().getPlanName()+"("+listResponse.getData().getSubPlanName()+")");

                            }else {
                                txt_health_obj_name.setText(listResponse.getData().getPlanName());

                            }
                            txt_health_obj_description.setText(listResponse.getData().getDesription());
                            txt_amount_health_obj.setText(listResponse.getData().getCurrency()+" "+Math.round(Double.valueOf(listResponse.getData().getPrice())));

                            TextView txt_plan_date=findViewById(R.id.txt_plan_date);
                            String startDate=listResponse.getData().getStartDate();
                            int index = startDate.indexOf("T");
                            String strnewStartDate=listResponse.getData().getStartDate().substring(0,index);

                            String endDate=listResponse.getData().getEndDate();
                            int index1 = endDate.indexOf("T");
                            String strnewEndDate=listResponse.getData().getEndDate().substring(0,index1);

                            txt_plan_date.setText(formatDates(strnewStartDate)+" To "+formatDates(strnewEndDate));
                            lst_objetive_plan.setAdapter(new HealthobjplanlistAdapter(MyServiceActivity.this,listResponse.getData().getServices()));


                        } else {
                            //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                            Log.d("Error---->", response.message());
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ClsExistingPlan> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }


    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }



}
