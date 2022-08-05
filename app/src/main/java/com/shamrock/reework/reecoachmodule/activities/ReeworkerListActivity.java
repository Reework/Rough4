package com.shamrock.reework.reecoachmodule.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsReeeworklistData;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsReeworkListMain;
import com.shamrock.reework.reecoachmodule.adapters.ReeworkerlistListAdapter;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ReeworkerListActivity extends AppCompatActivity {

    private Utils utils;
    private Context context;
    private SessionManager sessionManager;
    private int userId;
    private CommonService commonService;
    private int recoachId;
    ReeworkerlistListAdapter listAdapter;
    ListView lst_reeworker_list;
    ArrayList<ClsReeeworklistData> arylst_reeworker;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reeworker_list);
//        startActivity(new Intent(ReeworkerListActivity.this,ReecoachDashBoardActivity.class));
        context=ReeworkerListActivity.this;
        utils = new Utils();
        sessionManager = new SessionManager(context);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("My Reeworkers ");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

//        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
//        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        lst_reeworker_list=findViewById(R.id.lst_reeworker_list);
        getReeworkerList();

        lst_reeworker_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent=new Intent(ReeworkerListActivity.this,ChatDetailsActivity.class);
//                intent.putExtra("ID",String.valueOf(arylst_reeworker.get(position).getUserid()));
//                intent.putExtra("NAME",arylst_reeworker.get(position).getFullName());
//                startActivity(intent);




            }
        });

    }

    private void getReeworkerList(){


        utils.showProgressbar(this);
        commonService = Client.getClient().create(CommonService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

//        recoachId=3037;
        Call<ClsReeworkListMain> call = commonService.getAllReeworker(userId);
        call.enqueue(new Callback<ClsReeworkListMain>()
        {
            @Override
            public void onResponse(Call<ClsReeworkListMain> call, retrofit2.Response<ClsReeworkListMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsReeworkListMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode()==1){

                                if (moodResponse.getData()!=null){



                                    if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){
                                        arylst_reeworker=moodResponse.getData();
                                        listAdapter=new ReeworkerlistListAdapter(ReeworkerListActivity.this,moodResponse.getData());
                                        lst_reeworker_list.setAdapter(listAdapter);
                                    }
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
            public void onFailure(Call<ClsReeworkListMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

}
