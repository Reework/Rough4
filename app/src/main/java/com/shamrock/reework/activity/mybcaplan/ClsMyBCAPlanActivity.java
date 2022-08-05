package com.shamrock.reework.activity.mybcaplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.WeightModule.activity.WeightActivitylatest;
import com.shamrock.reework.activity.healthmonitoring.NewHealthMonitoringActivity;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ClsMyBCAPlanActivity extends AppCompatActivity implements  OnListBcaClick{

    private SessionManager sessionManager;
    private Context context;
    private ReecoachService reecoachService;
    private Utils utils;
    RecyclerView recler_main_bca;
    TextView txt_plan_names;
    private ArrayList<ClsBcaData> arylst_bcasingledata;
    private Dialog dialog;
    private int userID;
    private int existingPlanID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_my_b_c_a_plan);
        context=ClsMyBCAPlanActivity.this;
        recler_main_bca=findViewById(R.id.recler_main_bca);
        txt_plan_names=findViewById(R.id.txt_plan_names);
        sessionManager = new SessionManager(context);
        reecoachService = Client.getClient().create(ReecoachService.class);
        utils = new Utils();
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        TextView btn_chnage_plan=findViewById(R.id.btn_chnage_plan);
        btn_chnage_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callPlanListApi();


            }
        });
        getMyExistingBCAPlanData();
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("My BCA Plan");
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClsMyBCAPlanActivity.this, MyReecoachProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();

            }
        });
        TextView btn_view_more=findViewById(R.id.btn_view_more);
        btn_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll_bca=findViewById(R.id.ll_bca);
                ll_bca.setVisibility(View.VISIBLE);
            }
        });
    }


    private void getMyExistingBCAPlanData(){


        utils.showProgressbar(this);

        Call<ClsBcaMainClass> call = reecoachService.getExitingplan(2,userID);
        call.enqueue(new Callback<ClsBcaMainClass>()
        {
            @Override
            public void onResponse(Call<ClsBcaMainClass> call, retrofit2.Response<ClsBcaMainClass> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){



                    try {
                        ClsBcaMainClass moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){

                                    existingPlanID=moodResponse.getData().get(0).getPlanId();

                                    if (moodResponse.getData().get(0).getPlanname()!=null){
                                        txt_plan_names.setText("Existing BCA Plan : "+moodResponse.getData().get(0).getPlanname());

                                    }else {
                                        txt_plan_names.setText("Existing BCA Plan");

                                    }
                                    MyBcaPlanAdapter myBcaPlanAdapter=new MyBcaPlanAdapter(ClsMyBCAPlanActivity.this,moodResponse.getData().get(0).getPlanwiseListData());
                                    recler_main_bca.setAdapter(myBcaPlanAdapter);


                                }

                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsBcaMainClass> call, Throwable t)
            {
                Toast.makeText(ClsMyBCAPlanActivity.this, "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }
    private void callPlanListApi(){


        utils.showProgressbar(this);

        Call<ClsBcaMainClass> call = reecoachService.getBCALabTestPlanList(2);
        call.enqueue(new Callback<ClsBcaMainClass>()
        {
            @Override
            public void onResponse(Call<ClsBcaMainClass> call, retrofit2.Response<ClsBcaMainClass> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){



                    try {
                        final ClsBcaMainClass moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    dialog=new Dialog(context,R.style.CustomDialog);
                                    dialog.setContentView(R.layout.dailog_lay_bca_plan_list);
                                    ListView lst_bca_plan_list=dialog.findViewById(R.id.lst_bca_plan_list);

                                    BcaPlanListAdapter bcaPlanListAdapter=new BcaPlanListAdapter(ClsMyBCAPlanActivity.this,moodResponse.getData(),existingPlanID);

                                    lst_bca_plan_list.setAdapter(bcaPlanListAdapter);

                                    arylst_bcasingledata=moodResponse.getData();




                                    dialog.show();

                                }

                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsBcaMainClass> call, Throwable t)
            {
                Toast.makeText(ClsMyBCAPlanActivity.this, "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    public void getClickBCaPos(int pos) {

        Intent intent=new Intent(ClsMyBCAPlanActivity.this,ClsSingleBCAPlanActivity.class);
        intent.putExtra("DatasingleBca",arylst_bcasingledata.get(pos).getPlanwiseListData());
        intent.putExtra("name",arylst_bcasingledata.get(pos).getPlanname());
        startActivity(intent);;
    }

    @Override
    public void selectPlanItem(int planID, int i) {

        dialog.dismiss();

        callEditPlanApi(arylst_bcasingledata.get(i).getPlanId(),2,userID);

    }
    private void callEditPlanApi(int planis,int type,int reeworderid){


        utils.showProgressbar(this);

        Call<ClsEditSleepResonse> call = reecoachService.getChangePlan(planis,type,reeworderid);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, retrofit2.Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){

                    Toast.makeText(ClsMyBCAPlanActivity.this, "success", Toast.LENGTH_SHORT).show();


                    try {
                        final ClsEditSleepResonse moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    Toast.makeText(ClsMyBCAPlanActivity.this, ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    getMyExistingBCAPlanData();



                                }

                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
                Toast.makeText(ClsMyBCAPlanActivity.this, "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ClsMyBCAPlanActivity.this, MyReecoachProfileActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }
}
