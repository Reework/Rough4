package com.shamrock.reework.activity.eshopping;

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
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EShoppingActivityNew extends AppCompatActivity {

    private TextView tvTitle;
    Button btn_medicine,btn_food,btn_restaurant;
    LinearLayout layout_medicine,layout_food,layout_restaurant;
    private Context context;
    Toolbar toolbar;
    private int userID;
    private SessionManager sessionManager;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_shopping_new);
        context = EShoppingActivityNew.this;
        sessionManager = new SessionManager(context);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
//        ImageView imgLeft=findViewById(R.id.imgLeft);
//        imgLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        tvTitle=findViewById(R.id.tvTitle);
//        tvTitle.setText("E-Shopping");

        btn_medicine=findViewById(R.id.btn_medicine);
        btn_food=findViewById(R.id.btn_food);
        btn_restaurant=findViewById(R.id.btn_restaurant);

        layout_medicine=findViewById(R.id.layout_medicine);
        layout_food=findViewById(R.id.layout_food);
        layout_restaurant=findViewById(R.id.layout_restaurant);




        btn_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.VISIBLE);
                layout_food.setVisibility(View.GONE);
                layout_restaurant.setVisibility(View.GONE);

//                txt_nut.setBackground(getResources().getDrawable(R.drawable.round_recipe_button));
//                txt_ing.setBackground(getResources().getDrawable(R.drawable.round_recipe_button_white));
//                txt_rec.setBackground(getResources().getDrawable(R.drawable.round_recipe_button_white));

                btn_medicine.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_food.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_restaurant.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_food.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_restaurant.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.GONE);
                layout_food.setVisibility(View.VISIBLE);
                layout_restaurant.setVisibility(View.GONE);

                btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_food.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_restaurant.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_food.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_restaurant.setTextColor(getResources().getColor(R.color.black_recipe));

            }
        });

        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.GONE);
                layout_food.setVisibility(View.GONE);
                layout_restaurant.setVisibility(View.VISIBLE);


                btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_food.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_restaurant.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_food.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_restaurant.setTextColor(getResources().getColor(R.color.white_recipe));

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

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("E-Shopping");

        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        counterValuePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
