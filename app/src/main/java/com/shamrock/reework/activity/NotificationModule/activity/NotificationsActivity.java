package com.shamrock.reework.activity.NotificationModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.LoginModule.Data;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.LoginModule.UserStatus;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.SetNotificationStatusRequest;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.api.response.SetNotificationStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity implements NotificationAdapter.NotificationListener, View.OnClickListener
{
    Context context;
    Toolbar toolbar;
    Utils utils;
    SessionManager sessionManager;
    NotificationService notificationService;

    RelativeLayout noInternetLayout;
    LinearLayout mainLayout;
    Button btnRefresh;

    ArrayList<GetAllNotificationResponse.Notifications> mNotificationList;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    LoginService loginService;
//    GET-http://localhost:62096/api/Notification/ClearNotification?UserId=1
    int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        context = NotificationsActivity.this;

        //this.getClass().getSimpleName();

        init();
        setToolBar();
        findViews();
        Intent intent=getIntent();
        if (intent!=null){
            try {

                boolean isFromnoti = intent.getBooleanExtra("FromNoitification", false);

                if (isFromnoti) {
                    callUserStatusApi();

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void init()
    {
        //remindersService = FitBitClient.getClient().create(RemindersService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);
        notificationService = Client.getClient().create(NotificationService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        loginService = Client.getClient().create(LoginService.class);
        TextView txtclearnoti=findViewById(R.id.txtclearnoti);
        txtclearnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);


                builder.setMessage("Are you sure you want to clear all notifications ? ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                clearAllNotifications();




                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();

            }
        });

    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Notifications");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        counterValuePanel.setVisibility(View.GONE);
    }

    private void findViews()
    {
        mainLayout = findViewById(R.id.linLay_MyReminder_ReminderList);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerview);
        mNotificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(context, mNotificationList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationAdapter);

        if (Utils.isNetworkAvailable(context))
            GetAllNotifications();
        else
            showLayouts();
    }

    private void GetAllNotifications()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userId);

        Call<GetAllNotificationResponse> call = notificationService.getAllNotifications(request);

        call.enqueue(new Callback<GetAllNotificationResponse>()
        {
            @Override
            public void onResponse(Call<GetAllNotificationResponse> call, Response<GetAllNotificationResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetAllNotificationResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        ArrayList<GetAllNotificationResponse.Notifications> tempList = listResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
                            recyclerView.setVisibility(View.VISIBLE);
                            mNotificationList.clear();
                            mNotificationList.addAll(tempList);
                            notificationAdapter.notifyDataSetChanged();
                            for (int i = 0; i <mNotificationList.size() ; i++) {
                                if (!mNotificationList.get(i).getIsRead())
                                SetNotificationStatus(i, mNotificationList.get(i));

                            }


                        }else {
                            Toast.makeText(NotificationsActivity.this, "No notification available", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<GetAllNotificationResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }

    private void clearAllNotifications()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userId);

        Call<ClsEditSleepResonse> call = notificationService.getClearNotification(userId);

        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsEditSleepResonse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() .equals("1"))
                    {
                        Toast.makeText(NotificationsActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        recyclerView.setVisibility(View.INVISIBLE);
                        mNotificationList = new ArrayList<>();
                        notificationAdapter = new NotificationAdapter(context, mNotificationList, NotificationsActivity.this);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotificationsActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(notificationAdapter);


                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }

    public void showLayouts()
    {
        mainLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    //Get notification click here
    @Override
    public void GetNotificationListener(int pos, GetAllNotificationResponse.Notifications notifModel)
    {
        if (notifModel != null)
        {
            SetNotificationStatus(pos, notifModel);
        }
    }

    private void SetNotificationStatus(final int pos, final GetAllNotificationResponse.Notifications model)
    {

        SetNotificationStatusRequest request = new SetNotificationStatusRequest();
        request.setId(model.getID());
        request.setIsRead(true);

        Call<SetNotificationStatusResponse> call = notificationService.setNotificationStatus(request);

        call.enqueue(new Callback<SetNotificationStatusResponse>()
        {
            @Override
            public void onResponse(Call<SetNotificationStatusResponse> call, Response<SetNotificationStatusResponse> response)
            {

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SetNotificationStatusResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Log.e("NOTIF_ACTIVITY_MSG", ""+model.getNotificationMessage());
                        mNotificationList.get(pos).setIsRead(true);
                        notificationAdapter.notifyDataSetChanged();

                        if (pos==0&&model.getNotificationSubject().toString().equalsIgnoreCase("Chat Notification")){

                            Intent intent=new Intent(NotificationsActivity.this, MyReecoachProfileActivity.class);
                            intent.putExtra("FromNotification","true");
                            startActivity(intent);

                        }


                    }
                    else
                    {
                        Log.e("NOTIF_ACTIVITY", ""+listResponse.getMessage());
                       // Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Log.e("NOTIF_ACTIVITY", ""+response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<SetNotificationStatusResponse> call, Throwable t)
            {
                Log.e("NOTIF_ACTIVITY", t.toString());
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnRefresh)
        {
            if (Utils.isNetworkAvailable(context))
                GetAllNotifications();
            else
                showLayouts();
        }
    }


    private void callUserStatusApi() {
        Call<UserStatus> call = loginService.getUserStatusHistroy(userId);
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<UserStatus>() {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    UserStatus listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("0")) {


                        Data data = listResponse.getData();

                        if (data != null) {

                            try {
                                sessionManager.setStringValue("IsAllowUser", data.getIsAppliedBloodTest());
                                sessionManager.setStringValue("KeyAssingDailyTassk", data.getIsScheduledTask());
                                sessionManager.setStringValue("KeyAssingReecoach", data.getIsReecoachAssigned());
                                sessionManager.setStringValue("KeyBloodTestStatus", data.getBloodTestStatus());
                                sessionManager.setStringValue("KeyIsFreezed", data.getIsFreezed());
                                if (sessionManager.getStringValue("FromWeb").equalsIgnoreCase("true")){
                                    sessionManager.setStringValue("IsAllowUser","true");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                        }

                    }
                }
                //                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
//                callForUsrFreezStatus();

            }
        });

    }

}
