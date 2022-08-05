package com.shamrock.reework.activity.dietplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.MyPlansModule.activity.MyPlansActivity;
import com.shamrock.reework.activity.MyPlansModule.adapter.MyPlanMasterAdapter;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.cheatplan.MyCheatPlanActivity;
import com.shamrock.reework.activity.cheatplan.MyCheatPlanAdapter;
import com.shamrock.reework.activity.cheatplan.pojo.Plans;
import com.shamrock.reework.activity.dietplan.adapters.PathoPlanAdapter;
import com.shamrock.reework.activity.dietplan.pojo.ClsPathoReportMain;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.response.MyPlanMastersResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietPlanActivity extends AppCompatActivity  {

    private FoodService foodService;
    private Context mContext;
    private Utils utils;
    private SessionManager sessionManager;
    private int userId;
    private RecyclerView recyler_plansData;
    ClsPathoReportMain listResponse;
    TextView txt_no_diet;
    TextView txt_DietitianComment,txt_other_comment;
    LinearLayout ll_below;
    RadioButton rd_button_nonveg;
    Spinner spinnerCategory;
    private boolean iSFirstTime;
    int ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= DietPlanActivity.this;
        setContentView(R.layout.activity_diet_plan);
        txt_DietitianComment=findViewById(R.id.txt_DietitianComment);
        txt_other_comment=findViewById(R.id.txt_other_comment);
        recyler_plansData=findViewById(R.id.recyler_plansData);
        ll_below=findViewById(R.id.ll_below);
        txt_no_diet=findViewById(R.id.txt_no_diet);
        rd_button_nonveg=findViewById(R.id.rd_button_nonveg);
        final boolean ISReeplan=getIntent().getBooleanExtra("ISReeplan",false);

        rd_button_nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DietPlanActivity.this,RDPFoodPlanActivity.class);
                intent.putExtra("ISReeplan",ISReeplan);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });

        TextView tvTitle=findViewById(R.id.tvTitle);

        if (ISReeplan){
            tvTitle.setText("REEplan");

        }else {
            tvTitle.setText("My Diet Plan");

        }

        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        utils = new Utils();
        sessionManager = new SessionManager(mContext);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
        getDietPlanReport();
//        callMyPlanMasters();

    }






    private void getDietPlanReport() {
        utils.showProgressbar(this);
        Call<ClsPathoReportMain> call = foodService.getReescoreReport(userId);
            call.enqueue(new Callback<ClsPathoReportMain>()
            {
                @Override
                public void onResponse(Call<ClsPathoReportMain> call, Response<ClsPathoReportMain> response)
                {
                    utils.hideProgressbar();
                    List<String> tempList;
                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {

                         listResponse = response.body();
                        if (listResponse != null && listResponse.getCode().equals("1")){

                            if (listResponse.getData()!=null){
                                txt_no_diet.setVisibility(View.GONE);
                                recyler_plansData.setVisibility(View.VISIBLE);
                                if (listResponse.getData().getDietitianComment()!=null&&!listResponse.getData().getDietitianComment().isEmpty())
                                {
                                    txt_DietitianComment.setText(listResponse.getData().getDietitianComment());
                                }


                                if (listResponse.getData().getOtherComment()!=null&&!listResponse.getData().getOtherComment().isEmpty())
                                {
                                    txt_other_comment.setText(listResponse.getData().getOtherComment());
                                }


                                if (listResponse.getData().getOtherComment()!=null||listResponse.getData().getDietitianComment()!=null){
                                    ll_below.setVisibility(View.VISIBLE);

                                }

                                if (listResponse.getData().getPathReport()!=null&&!listResponse.getData().getPathReport().isEmpty())
                                {
                                    recyler_plansData.setAdapter(new PathoPlanAdapter(DietPlanActivity.this,listResponse.getData().getPathReport()));
                                }


                                if (listResponse.getData().getPathReport()!=null&&!listResponse.getData().getPathReport().isEmpty())
                                {
                                    recyler_plansData.setVisibility(View.VISIBLE);
                                    txt_no_diet.setVisibility(View.GONE);
                                    recyler_plansData.setAdapter(new PathoPlanAdapter(DietPlanActivity.this,listResponse.getData().getPathReport()));
                                }else {
                                    recyler_plansData.setVisibility(View.GONE);
                                    txt_no_diet.setVisibility(View.VISIBLE);
                                }

                            }else {
                                recyler_plansData.setVisibility(View.GONE);
                                txt_no_diet.setVisibility(View.VISIBLE);
                            }








                        }
                        else
                        {
                            Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsPathoReportMain> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    utils.hideProgressbar();
                }
            });
        }

    private void callMyPlanMasters()
    {
//        utils=new Utils();
//        if (!((Activity) mContext).isFinishing())
//        {
//            utils.showProgressbar(mContext);
//        }
        sessionManager=new SessionManager(mContext);
        int   userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        MyPlansService myPlansService = Client.getClient().create(MyPlansService.class);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<MyPlanMastersResponse> call = myPlansService.getMasterPlans(request);
        call.enqueue(new Callback<MyPlanMastersResponse>()
        {
            @Override
            public void onResponse(Call<MyPlanMastersResponse> call, Response<MyPlanMastersResponse> response)
            {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyPlanMastersResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        spinnerCategory = findViewById(R.id.spinner_MyPlan_Category);


                        final ArrayList<MyPlanMastersResponse.MasterData> spinnerList;

                        spinnerList = response.body().getData();

                        MyPlanMastersResponse.MasterData data = new MyPlanMastersResponse.MasterData();
                        data.setPlanName("Today's Plan");
                        data.setID(0);
                        spinnerList.add(0, data);
                        MyPlanMasterAdapter spinnerAdapter;


                        spinnerAdapter = new MyPlanMasterAdapter(mContext, spinnerList);
                        spinnerCategory.setAdapter(spinnerAdapter);

                        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                                if (!iSFirstTime){
                                    iSFirstTime=true;
                                    return;
                                }
                                if (spinnerList!= null) {
                                    String planType = spinnerList.get(i).getPlanName();
                                    int planId = spinnerList.get(i).getID();

                                    if (planType.trim().equalsIgnoreCase("Food Plan")) {
                                        startActivity(new Intent(DietPlanActivity.this, DietPlanActivity.class));
                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("Lifestyle Plan")) {
                                        Intent intent = new Intent(DietPlanActivity.this, LifeStylePlanActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);

                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("REEplace Items")) {
                                        Intent intent = new Intent(DietPlanActivity.this, MyCheatPlanActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);


                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("Today's Plan")) {
                                        Intent intent = new Intent(DietPlanActivity.this, MyPlansActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);


                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }


                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        for (int i = 0; i <spinnerList.size() ; i++) {
                            if (spinnerList.get(i).getPlanName().equalsIgnoreCase("Food Plan")){
                                spinnerCategory.setSelection(i);
                                break;
                            }

                        }

                    }
                    else
                    {
                        Toast.makeText(mContext, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyPlanMastersResponse> call, Throwable t)
            {
//                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
            }
        });
    }






}


