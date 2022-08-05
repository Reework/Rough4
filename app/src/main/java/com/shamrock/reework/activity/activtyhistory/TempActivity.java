package com.shamrock.reework.activity.activtyhistory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.SplashActivity;
import com.shamrock.reework.activity.WelcomeModule.ClsCommonWellcomaster;
import com.shamrock.reework.activity.cheatplan.MyCheatPlanActivity;
import com.shamrock.reework.activity.community.CommuniyActivity;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.database.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;

public class TempActivity extends AppCompatActivity {

    private CommonService commonService;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        sessionManager=new SessionManager(TempActivity.this);



        if (sessionManager.getStringValue("KEY_BEFORE_WELCOME_IMAGE").isEmpty()){
            getBeforewellcomeMEssageMasterSlogan();

        }else {
            startActivity(new Intent(TempActivity.this, SplashActivity.class));
            overridePendingTransition(0, 0);
            finish();
        }

    }
    private void getBeforewellcomeMEssageMasterSlogan(){



        commonService = Client.getClient().create(CommonService.class);

        Call<ClsCommonWellcomaster> call = commonService.getWelcomeMessage("BEFORE WELCOME");
        call.enqueue(new Callback<ClsCommonWellcomaster>()
        {
            @Override
            public void onResponse(Call<ClsCommonWellcomaster> call, retrofit2.Response<ClsCommonWellcomaster> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        final ClsCommonWellcomaster moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    final SessionManager session=new SessionManager(TempActivity.this);


                                    session.setStringValue("KEY_BEFORE_WELCOME_TEXT",moodResponse.getData().getMessage());
                                    session.setStringValue("KEY_BEFORE_WELCOME_IMAGE",moodResponse.getData().getImagePath());


//                                    ttt.setText(session.getStringValue("KEY_BEFORE_WELCOME_TEXT"));


                                    startActivity(new Intent(TempActivity.this, SplashActivity.class));
                                    overridePendingTransition(0, 0);
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
            public void onFailure(Call<ClsCommonWellcomaster> call, Throwable t)
            {


                Log.e("ERROR------>", t.toString());
            }
        });
    }
}
