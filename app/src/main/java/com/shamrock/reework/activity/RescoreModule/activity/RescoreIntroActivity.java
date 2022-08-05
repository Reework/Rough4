package com.shamrock.reework.activity.RescoreModule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.RescoreModule.model.ClsReescoreMianClass;
import com.shamrock.reework.activity.RescoreModule.service.RescoreService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.RescoreRequest;
import com.shamrock.reework.api.response.RescoreResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RescoreIntroActivity extends AppCompatActivity implements View.OnClickListener
{
    Context context;
    Toolbar toolbar;
    TextView tvMsg;
    ImageView imgNext;
    private RescoreService service;
    private SessionManager sessionManager;
    private Utils utils;
    private int userId;
    ClsReescoreMianClass reescoreMianClassl1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescore_intro);
        context = RescoreIntroActivity.this;

        setToolBar();
        findViews();
        callRescoreDataAPI();

    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("My Reescore");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void findViews()
    {
        imgNext = findViewById(R.id.ivNext);
        imgNext.setOnClickListener(this);

        tvMsg = findViewById(R.id.tvMsg);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivNext:

//                callRescoreDataAPI();

                if (reescoreMianClassl1!=null){
                    Intent intent=new Intent(RescoreIntroActivity.this, ReescoreActivity.class);
                    intent.putExtra("REESCORE_DATA",reescoreMianClassl1);
                    startActivity(intent);

                }



                break;
        }
    }


    private void callRescoreDataAPI()
    {

        service = Client.getClient().create(RescoreService.class);
        sessionManager = new SessionManager(context);
        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        Call<ClsReescoreMianClass> call = service.GetReeScoreMessage();
        call.enqueue(new Callback<ClsReescoreMianClass>()
        {
            @Override
            public void onResponse(Call<ClsReescoreMianClass> call, Response<ClsReescoreMianClass> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsReescoreMianClass listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        if (listResponse.getData()!=null){

                            reescoreMianClassl1=listResponse;
                            for (int i = 0; i < listResponse.getData().size(); i++) {

                                if (listResponse.getData().get(i).getMessageType().equalsIgnoreCase("What is Reescore")){
                                    tvMsg.setText(listResponse.getData().get(i).getMessage());
                                    break;

                                }
                            }
                        }




                    }
                    else
                        Toast.makeText(RescoreIntroActivity.this, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ClsReescoreMianClass> call, Throwable t)
            {
                Log.e("ReescoreActivity", t.toString());
                utils.hideProgressbar();
            }
        });
    }
}
