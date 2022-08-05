package com.shamrock.reework.activity.REEspot.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReespotActivity extends AppCompatActivity {


    LinearLayout layout_yogatrainers, layout_gym, layout_gardens, layout_healthyrestaurants;
    Button btn_yogatrainers, btn_gym, btn_gardens, btn_healthyrestaurants;
    Toolbar toolbar;

    private Context context;
    private int userID;
    private SessionManager sessionManager;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reespot);
        context = ReespotActivity.this;

        sessionManager = new SessionManager(context);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        layout_yogatrainers = findViewById(R.id.layout_yogatrainers);
        layout_gym = findViewById(R.id.layout_gym);
        layout_gardens = findViewById(R.id.layout_gardens);
        layout_healthyrestaurants = findViewById(R.id.layout_healthyrestaurants);

        btn_yogatrainers = findViewById(R.id.btn_yogatrainers);
        btn_gym = findViewById(R.id.btn_gym);
        btn_gardens = findViewById(R.id.btn_gardens);
        btn_healthyrestaurants = findViewById(R.id.btn_healthyrestaurants);


        btn_yogatrainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_yogatrainers.setVisibility(View.VISIBLE);
                layout_gym.setVisibility(View.GONE);
                layout_gardens.setVisibility(View.GONE);
                layout_healthyrestaurants.setVisibility(View.GONE);

                btn_yogatrainers.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_gym.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_gardens.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_healthyrestaurants.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_yogatrainers.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_gym.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_gardens.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_healthyrestaurants.setTextColor(getResources().getColor(R.color.black_recipe));


            }
        });

        btn_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_yogatrainers.setVisibility(View.VISIBLE);
                layout_gym.setVisibility(View.GONE);
                layout_gardens.setVisibility(View.GONE);
                layout_healthyrestaurants.setVisibility(View.GONE);

                btn_yogatrainers.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_gym.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_gardens.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_healthyrestaurants.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_yogatrainers.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_gym.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_gardens.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_healthyrestaurants.setTextColor(getResources().getColor(R.color.black_recipe));


            }
        });


        btn_gardens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_yogatrainers.setVisibility(View.GONE);
                layout_gym.setVisibility(View.GONE);
                layout_gardens.setVisibility(View.VISIBLE);
                layout_healthyrestaurants.setVisibility(View.GONE);

                btn_yogatrainers.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_gym.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_gardens.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_healthyrestaurants.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_yogatrainers.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_gym.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_gardens.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_healthyrestaurants.setTextColor(getResources().getColor(R.color.black_recipe));


            }
        });


        btn_healthyrestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_yogatrainers.setVisibility(View.GONE);
                layout_gym.setVisibility(View.GONE);
                layout_gardens.setVisibility(View.GONE);
                layout_healthyrestaurants.setVisibility(View.VISIBLE);

                btn_yogatrainers.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_gym.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_gardens.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_healthyrestaurants.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_yogatrainers.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_gym.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_gardens.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_healthyrestaurants.setTextColor(getResources().getColor(R.color.white_recipe));


            }
        });


        notificationService = Client.getClient().create(NotificationService.class);
        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
//                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
//                        PushFcmToServer();
                    } else if (intent.getAction().equals(FcmConstants.PUSH_NOTIFICATION)) // new push notification is received
                    {
                        String title = intent.getStringExtra(FcmConstants.FCM_TITLE);
                        String message = intent.getStringExtra(FcmConstants.FCM_MESSEGE);
                        mNotifcationCount = intent.getIntExtra(FcmConstants.FCM_COUNT, 0);

                        if (mNotifcationCount > 0) {
                            tvNotificationCOunt.setText(String.valueOf(mNotifcationCount));
                            invalidateOptionsMenu();
                        } else {
                            if (Utils.isNetworkAvailable(context))
                                GetAllNotificationCount();
                            else
                                Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();



    }

    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEspot");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });


        counterValuePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), NotificationsActivity.class), 500);

            }
        });


        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }

    }

    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

        Call<NotifCountResponse> call = notificationService.getAllNotificationCount(request);

        call.enqueue(new Callback<NotifCountResponse>() {
            @Override
            public void onResponse(Call<NotifCountResponse> call, Response<NotifCountResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    NotifCountResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mNotifcationCount = listResponse.getData();

                        invalidateOptionsMenu();
                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifCountResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }


    }
