package com.shamrock.reework.activity.mybcaplan;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ClsSingleBCAPlanActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Context context;
    private ReecoachService reecoachService;
    private Utils utils;
    RecyclerView recler_main_bca;
    TextView txt_plan_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_my_b_c_a_plan_single);
        context= ClsSingleBCAPlanActivity.this;
        recler_main_bca=findViewById(R.id.recler_main_bca);
        txt_plan_names=findViewById(R.id.txt_plan_names);
        txt_plan_names.setVisibility(View.GONE);
        txt_plan_names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callPlanListApi();


            }
        });
//        getMyExistingBCAPlanData();
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText(getIntent().getStringExtra("name"));
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ArrayList<PlanwiseListData> countryList= (ArrayList<PlanwiseListData>) getIntent().getSerializableExtra("DatasingleBca");



        MyBcaPlanAdapter myBcaPlanAdapter=new MyBcaPlanAdapter(ClsSingleBCAPlanActivity.this,countryList);
        recler_main_bca.setAdapter(myBcaPlanAdapter);

    }


    private void callPlanListApi(){
        sessionManager = new SessionManager(context);
        reecoachService = Client.getClient().create(ReecoachService.class);
        utils = new Utils();

        utils.showProgressbar(this);

        Call<ClsBcaMainClass> call = reecoachService.getBCALabTestPlanList(1);
        call.enqueue(new Callback<ClsBcaMainClass>()
        {
            @Override
            public void onResponse(Call<ClsBcaMainClass> call, retrofit2.Response<ClsBcaMainClass> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){

                    Toast.makeText(ClsSingleBCAPlanActivity.this, "success", Toast.LENGTH_SHORT).show();


                    try {
                        ClsBcaMainClass moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    Toast.makeText(ClsSingleBCAPlanActivity.this, ""+moodResponse.getData().size(), Toast.LENGTH_SHORT).show();
                                    Dialog  dialog=new Dialog(context,R.style.CustomDialog);
                                    dialog.setContentView(R.layout.dailog_lay_bca_plan_list);
                                    ListView lst_bca_plan_list=dialog.findViewById(R.id.lst_bca_plan_list);

                                    BcaPlanListAdapter bcaPlanListAdapter=new BcaPlanListAdapter(ClsSingleBCAPlanActivity.this,moodResponse.getData(), 0);

                                    lst_bca_plan_list.setAdapter(bcaPlanListAdapter);




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
                Toast.makeText(ClsSingleBCAPlanActivity.this, "error" +t.getMessage(), Toast.LENGTH_SHORT).show();

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

}
