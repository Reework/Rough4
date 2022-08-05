package com.shamrock.reework.activity.ReminderModule.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.ReminderModule.adapter.MyRemindersAdapter;
import com.shamrock.reework.activity.ReminderModule.model.ClsReminderMaster;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.ReminderModule.service.RemindersService;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.ReminderDeleteRequest;
import com.shamrock.reework.api.response.ReminderDeleteResponse;
import com.shamrock.reework.api.response.ReminderResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.activity.ReminderModule.dialog.RemindersEditDialoge;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemindersActivity extends AppCompatActivity implements View.OnClickListener,
                                                                MyRemindersAdapter.MyReminderListener,
        RemindersEditDialoge.ReminderEditDialogeInterface
{

    private static final int ADD_REMINDERS = 101;
    Context context;
    Toolbar toolbar;
    Typeface font;
    Utils utils;
    RemindersService remindersService;
    SessionManager sessionManager;

    LinearLayout buttonSchedule;
    RelativeLayout linearNoData;
    RelativeLayout relativeLayout_ScheduleClock;

    ArrayList<ReminderResponse.ReminderData> reminderList;
    RecyclerView recyclerView;
    MyRemindersAdapter remindersAdapter;

    RemindersEditDialoge editDialog;;
    RelativeLayout txt_no_reminder;
    int userId;

    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    private ReeTestService reeTestService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reminders);
        context = RemindersActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        remindersService = Client.getClient().create(RemindersService.class);
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
        tvTitle.setText("Reminders");
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
        txt_no_reminder = findViewById(R.id.txt_no_reminder);
        mainLayout = findViewById(R.id.mainLayout);
        relativeLayout_ScheduleClock = findViewById(R.id.relLay_MyReminder_AddClock);
        recyclerView = findViewById(R.id.recyclerview);
        reminderList = new ArrayList<>();
        remindersAdapter = new MyRemindersAdapter(context, reminderList, this);
        recyclerView.setAdapter(remindersAdapter);
        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        linearNoData = findViewById(R.id.relLay_MyReminder_AddClock);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        buttonSchedule.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (Utils.isNetworkAvailable(context))
            callToGetAllReminders();
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

                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }

                startActivityForResult(new Intent(context, ReminderSheduleActivity.class), ADD_REMINDERS);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    callToGetAllReminders();
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
    @Override
    public void onDelete(int pos, ReminderResponse.ReminderData model)
    {
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }

        if (model != null)
        {
            deleteReminder(pos, model);
        }
    }

    private void callActvityTypeMasterAPI(int pos, final ReminderResponse.ReminderData model)
    {
            utils.showProgressbar(context);

        sessionManager = new SessionManager(context);
        reeTestService = Client.getClient().create(ReeTestService.class);

        int   userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        Call<ClsReminderMaster> call = reeTestService.getAllRemiderActivityMaster();
        call.enqueue(new Callback<ClsReminderMaster>()
        {
            @Override
            public void onResponse(Call<ClsReminderMaster> call, Response<ClsReminderMaster> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsReminderMaster listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        ArrayList<String> arylst_Activities_list=listResponse.getData();
                        if (arylst_Activities_list!=null){
                            arylst_Activities_list.set(0,"Select Activity");

                            FragmentManager fm = getSupportFragmentManager();
                            editDialog = new RemindersEditDialoge();


                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MODEL", model);
                            bundle.putSerializable("Actvitiylist",arylst_Activities_list);
                            editDialog.setArguments(bundle);
                            editDialog.show(fm, "edit_fragment");
                        }


                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ClsReminderMaster> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }

    @Override
    public void onEdit(int pos, ReminderResponse.ReminderData model)
    {
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }

        if (model != null)
        {
            callActvityTypeMasterAPI(pos,model);


        }
    }

    /* TO GET ALL REMINDER */
    private void callToGetAllReminders()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userId);

        Call<ReminderResponse> call = remindersService.getAllReminder(request);
        call.enqueue(new Callback<ReminderResponse>()
        {
            @Override
            public void onResponse(Call<ReminderResponse> call, Response<ReminderResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    String dataAsString  = new Gson().toJson(response);
                    ReminderResponse reminderResponse = response.body();

                    if (reminderResponse != null && reminderResponse.getCode() == 1)
                    {
                        ArrayList<ReminderResponse.ReminderData>  tempList = reminderResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
//                            reminderList.clear();
//                            reminderList.addAll(tempList);
//                            remindersAdapter.notifyDataSetChanged();

                            linearNoData.setVisibility(View.GONE);
                            noInternetLayout.setVisibility(View.GONE);
                            txt_no_reminder.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);


                            reminderList = new ArrayList<>();
                            reminderList.clear();
                            reminderList.addAll(tempList);

                            remindersAdapter = new MyRemindersAdapter(context, reminderList,RemindersActivity.this);

//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RemindersActivity.this);
//                            recyclerView.setLayoutManager(layoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(remindersAdapter);


                        }
                        else
                        {
                            txt_no_reminder.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            linearNoData.setVisibility(View.VISIBLE);
                            noInternetLayout.setVisibility(View.GONE);
                        }
                    }
                    else
                        ShowRetryBar("" );
                }
            }

            @Override
            public void onFailure(Call<ReminderResponse> call, Throwable t)
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
                            callToGetAllReminders();
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    public void deleteReminder(final int pos , final ReminderResponse.ReminderData model)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete Reminder!")
                .setMessage("Do you really want to delete this Reminder?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        if (Utils.isNetworkAvailable(context))
                        {
                            utils.showProgressbar(context);

                            callDeleteReminder(pos, model);
                        }
                        else
                        {
                            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void callDeleteReminder(final int pos , ReminderResponse.ReminderData model)
    {
        ReminderDeleteRequest request = new ReminderDeleteRequest();
        request.setReminderID(model.getReminderID());

        Call<ReminderDeleteResponse> call = remindersService.deleteReminder(request);
        call.enqueue(new Callback<ReminderDeleteResponse>()
        {
            @Override
            public void onResponse(Call<ReminderDeleteResponse> call, Response<ReminderDeleteResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ReminderDeleteResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        // call List API to reload list
                        reminderList.remove(pos);
                        remindersAdapter.notifyDataSetChanged();
                        callToGetAllReminders();
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReminderDeleteResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void onEdit(ReminderResponse.ReminderData model)
    {
        if (Utils.isNetworkAvailable(context))
            callToGetAllReminders();
        else
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
    }
    private void shownoplan() {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe=dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no=dialog.findViewById(R.id.btn_subscribe_no);
        btn_subscribe_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent  intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }

}
