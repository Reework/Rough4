package com.shamrock.reework.activity.health;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.FaqActivity.ClsFAQPojo;
import com.shamrock.reework.activity.FaqActivity.FAQListAdapter;
import com.shamrock.reework.activity.HealthSupportModule.HealthAndSupportActivity;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.payment.paymentHistoryActivity;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;

public class HealthNewActivity extends AppCompatActivity {
    Utils utils=new Utils();
    private ReecoachService reecoachService;
    RadioButton rd_btn_Faq,rd_btn_NeedMoreHelp;
    RecyclerView recler_health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        TextView tvTitle=findViewById(R.id.tvTitle);

       // tvTitle.setText("FAQ");
        tvTitle.setText("Health");
        ImageView imageView=findViewById(R.id.imgLeft);
        recler_health=findViewById(R.id.recler_health);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getKnowlegebankData();

    }

    private void getKnowlegebankData(){



        utils.showProgressbar(this);
        SessionManager sessionManager = new SessionManager(this);
        reecoachService = Client.getClient().create(ReecoachService.class);
        int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Call<HealthMain> call = reecoachService.getKnowledgeBank(userID);
        call.enqueue(new Callback<HealthMain>()
        {
            @Override
            public void onResponse(Call<HealthMain> call, retrofit2.Response<HealthMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        HealthMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){

                                    if (!moodResponse.getData().isEmpty()){
                                        recler_health.setAdapter(new HealthListAdapter(HealthNewActivity.this,moodResponse.getData()));
                                    }else {
                                        Toast.makeText(HealthNewActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                }else {
                                    Toast.makeText(HealthNewActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<HealthMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

}
