package com.shamrock.reework.activity.FaqActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthSupportModule.HealthAndSupportActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachListByType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListAdapter;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.Pathologists.ChangePathoActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;
import com.squareup.okhttp.internal.Util;

import retrofit2.Call;
import retrofit2.Callback;

public class ReeworkFAQActivity extends AppCompatActivity {
    Utils utils=new Utils();
    private ReecoachService reecoachService;
    RadioButton rd_btn_Faq,rd_btn_NeedMoreHelp;

    //    http://shamrockuat.dweb.in/api/Master/FAQ_Master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reework_f_a_q);
        TextView tvTitle=findViewById(R.id.tvTitle);
        rd_btn_Faq=findViewById(R.id.rd_btn_faq);
        rd_btn_NeedMoreHelp=findViewById(R.id.rd_btn_needMoreHelp);
       // tvTitle.setText("FAQ");
        tvTitle.setText("Help & Support");
        ImageView imageView=findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getReecaochTypeData();
        rd_btn_NeedMoreHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReeworkFAQActivity.this, HealthAndSupportActivity.class));
                finish();
            }
        });
    }

    private void getReecaochTypeData(){



        utils.showProgressbar(this);
        SessionManager sessionManager = new SessionManager(this);
        reecoachService = Client.getClient().create(ReecoachService.class);

        Call<ClsFAQPojo> call = reecoachService.getFAQ();
        call.enqueue(new Callback<ClsFAQPojo>()
        {
            @Override
            public void onResponse(Call<ClsFAQPojo> call, retrofit2.Response<ClsFAQPojo> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsFAQPojo moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){

                                    if (!moodResponse.getData().isEmpty()){
                                        RecyclerView recyler_faq=findViewById(R.id.recyler_faq);
                                        recyler_faq.setAdapter(new FAQListAdapter(ReeworkFAQActivity.this,moodResponse.getData()));
                                    }else {
                                        Toast.makeText(ReeworkFAQActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                }else {
                                    Toast.makeText(ReeworkFAQActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                                    finish();


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
            public void onFailure(Call<ClsFAQPojo> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

}
