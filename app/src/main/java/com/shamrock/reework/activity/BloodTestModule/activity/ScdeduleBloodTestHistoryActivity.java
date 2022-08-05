package com.shamrock.reework.activity.BloodTestModule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.ReminderModule.dialog.RemindersEditDialoge;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScdeduleBloodTestHistoryActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int ADD_REMINDERS = 101;
    Context context;
    Toolbar toolbar;
    Typeface font;
    Utils utils;
    ReeTestService Service;
    SessionManager sessionManager;

    LinearLayout buttonSchedule;
    RelativeLayout linearNoData;

    ArrayList<GetBloodTestReportRes.DataList> reminderList;
    RecyclerView recyclerView;
    ScheduleBloodTestAdapter remindersAdapter;

    RemindersEditDialoge editDialog;
    int userId;

    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scdedule_blood_test_history);
        context = ScdeduleBloodTestHistoryActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        Service = Client.getClient().create(ReeTestService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText(R.string.title_schedule_blood_test);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews()
    {
        buttonSchedule = findViewById(R.id.buttonScheduleReminder);
        mainLayout = findViewById(R.id.mainLayout);


        recyclerView = findViewById(R.id.recyclerview);
        reminderList = new ArrayList<>();
        remindersAdapter = new ScheduleBloodTestAdapter(context, reminderList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(remindersAdapter);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        linearNoData = findViewById(R.id.relLay_MyReminder_AddClock);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);

        buttonSchedule.setOnClickListener(this);

       /* if (Utils.isNetworkAvailable(context))
        {
            callToGetAllReminders();
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
        }*/

        /*buttonSchedule = findViewById(R.id.buttonScheduleReminder);
        linearLayout_MyReminders = findViewById(R.id.linLay_MyReminder_ReminderList);
        relativeLayout_ScheduleClock = findViewById(R.id.relLay_MyReminder_AddClock);

        buttonSchedule.setOnClickListener(this);

        if (reminderItemList.isEmpty())
        {
            relativeLayout_ScheduleClock.setVisibility(View.VISIBLE);
            linearLayout_MyReminders.setVisibility(View.GONE);
        }
        else
        {
            relativeLayout_ScheduleClock.setVisibility(View.GONE);
            linearLayout_MyReminders.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (Utils.isNetworkAvailable(context))
            getAllBloodReportHistory();
        else
            showLayouts();
    }


    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
            noInternetLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
            linearNoData.setVisibility(View.GONE);
        }
        else
        {
            mainLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
            linearNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonScheduleReminder:
                startActivityForResult(new Intent(context, ScheduleBloodTestActivity.class), ADD_REMINDERS);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    getAllBloodReportHistory();
                }
                else
                    showLayouts();
                break;

            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REMINDERS && resultCode == RESULT_OK)
        {
           /* if (data != null && data.hasExtra("RESULT"))
            {
                if (data.getStringExtra("RESULT").equals("ok"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        callToGetAllReminders();
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                    }
                }
            }*/
        }
    }




    /* TO GET ALL REMINDER */
    private void getAllBloodReportHistory()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userId);

        Call<GetBloodTestReportRes> call = Service.getAllBloodReportHistory(String.valueOf(userId));
        call.enqueue(new Callback<GetBloodTestReportRes>()
        {
            @Override
            public void onResponse(Call<GetBloodTestReportRes> call, Response<GetBloodTestReportRes> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    String dataAsString  = new Gson().toJson(response);
                    GetBloodTestReportRes reminderResponse = response.body();

                    if (reminderResponse != null && reminderResponse.getCode() == 0)
                    {
                        ArrayList<GetBloodTestReportRes.DataList>  tempList = (ArrayList<GetBloodTestReportRes.DataList>) reminderResponse.getData();

                        if (tempList != null && tempList.size() > 0)

                            linearNoData.setVisibility(View.GONE);
                            noInternetLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);


                            if (tempList!=null){
                                reminderList = new ArrayList<>();
                                reminderList.clear();
                                reminderList.addAll(tempList);

                                remindersAdapter = new ScheduleBloodTestAdapter(context, reminderList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ScdeduleBloodTestHistoryActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(remindersAdapter);
                            }



                        }
                        else
                        {
                            recyclerView.setVisibility(View.GONE);
                            linearNoData.setVisibility(View.VISIBLE);
                            noInternetLayout.setVisibility(View.GONE);
                        }
                    }
                    else
                        ShowRetryBar("" );
                }


            @Override
            public void onFailure(Call<GetBloodTestReportRes> call, Throwable t)
            {
                utils.hideProgressbar();
                ShowRetryBar("" );
            }
        });
    }

    private void ShowRetryBar(String msg)
    {
        String strMessage;
        if (msg.isEmpty())
        {
            strMessage = "Unable to load data";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(context))
                        {
                            showLayouts();
                            getAllBloodReportHistory();
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }



    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



}

