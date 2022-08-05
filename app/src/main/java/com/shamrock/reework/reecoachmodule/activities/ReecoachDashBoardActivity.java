package com.shamrock.reework.reecoachmodule.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.fragment.FoodHistoryFragment;
import com.shamrock.reework.activity.FoodModule.history.ClsFoodHistoryPojo;
import com.shamrock.reework.activity.FoodModule.history.Data;
import com.shamrock.reework.activity.FoodModule.history.FoodHistoryMainAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsmainDashboardData;
import com.shamrock.reework.util.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ReecoachDashBoardActivity extends AppCompatActivity {

    private CommonService commonService;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reecoach_dash_board);
        callDashboradData();
    }
    public void callDashboradData() {

     SessionManager   sessionManager = new SessionManager(ReecoachDashBoardActivity.this);

       int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        commonService = Client.getClient().create(CommonService.class);
        utils=new Utils();
        utils.showProgressbar(ReecoachDashBoardActivity.this);
        Call<ClsmainDashboardData> call = commonService.getDashboardDeails(userId);
        call.enqueue(new Callback<ClsmainDashboardData>()
        {
            @Override
            public void onResponse(Call<ClsmainDashboardData> call, retrofit2.Response<ClsmainDashboardData> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsmainDashboardData moodResponse = response.body();

                        if (moodResponse.getCode()==1){
                         SessionManager   sessionManager = new SessionManager(ReecoachDashBoardActivity.this);
                         String   imageUrl = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);

                            ImageView img_pic=findViewById(R.id.img_pic);
                            TextView reecoachanem=findViewById(R.id.reecoachanem);
                            TextView txt_myreeworker=findViewById(R.id.txt_myreeworker);
                            txt_myreeworker.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(ReecoachDashBoardActivity.this,ReeworkerListActivity.class));
                                }
                            });
                            String name=sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME)+" "+sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME);
                            reecoachanem.setText(name);
                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_profile_pic_bg)
                                    .error(R.drawable.ic_profile_pic_bg)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .priority(Priority.HIGH);
                            Glide.with(ReecoachDashBoardActivity.this)
                                    .load(imageUrl)
                                    .apply(options)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(img_pic);


                            TextView txt_InActive=findViewById(R.id.txt_InActive);
                            txt_InActive.setText(""+moodResponse.getData().getIsActivReeworker());

                            TextView txt_Freezed=findViewById(R.id.txt_Freezed);
                            txt_Freezed.setText(""+moodResponse.getData().getFreezedReeworker());

                            TextView txt_Active=findViewById(R.id.txt_Active);
                            txt_Active.setText(""+moodResponse.getData().getActiveReeworker());

                            TextView txt_total_user=findViewById(R.id.txt_total_user);
                            txt_total_user.setText(""+moodResponse.getData().getTotalReeworker());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsmainDashboardData> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });






    }

}
