package com.shamrock.reework.activity.AppoinmentModule.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.AppoinmentModule.adapter.MyAppointmentsAdapter;
import com.shamrock.reework.activity.AppoinmentModule.adapter.ServiceAppointmentsAdapter;
import com.shamrock.reework.activity.AppoinmentModule.dialog.MyAppoinmentEditDialog;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceAppointmentActivity extends AppCompatActivity implements View.OnClickListener, ServiceAppointmentsAdapter.MyAppointmentListener,
        MyAppoinmentEditDialog.ApponmentEditInterface{
//appointemnt

    LinearLayout btnScheduleAppointment;
    Button buttonSleep_ViewMore;
    RecyclerView recyclerView;
    ArrayList<GetAllAppointmentResponse.AppointmentData> appointmentDataArrayList;
    ServiceAppointmentsAdapter adapter;
    MyAppoinmentEditDialog editDialog;
    AppoinmentService appoinmentService;
    public static final int REQ_FOR_APPOINMENT = 1000;
    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    TextView txtNoData;
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView  txt_no_weight;
    private Utils utils;
    Context context;
    private SessionManager sessionManager;
    RadioButton rd_button_path,rd_button_reecoach;
    int rollid=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_appointment);
        context=ServiceAppointmentActivity.this;


        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Appointments");
        ImageView imageView=findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inits();
        findViewss();
    }
    private void inits()
    {
        appoinmentService = Client.getClient().create(AppoinmentService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.getStringValue("Back").equalsIgnoreCase("true")){
            callToGetAllAppoinments(false);
            sessionManager.setStringValue("Back","");

        }

    }

    private void findViewss()
    {
        recyclerView = findViewById(R.id.rvAppoinments);
        btnScheduleAppointment = findViewById(R.id.buttonScheduleAppointment);
        buttonSleep_ViewMore = findViewById(R.id.buttonSleep_ViewMore);
        txtNoData = findViewById(R.id.txtNoData);


        rd_button_reecoach = findViewById(R.id.rd_button_reecoach);
        rd_button_path = findViewById(R.id.rd_button_path);

        if (!sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true")){
            rd_button_reecoach.setVisibility(View.GONE);
        }
        if (!sessionManager.getStringValue("KEY_ISSHOW_PATHO").equalsIgnoreCase("true")){
            rd_button_path.setVisibility(View.GONE);
        }
        rd_button_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rollid=4;
                callToGetAllAppoinments(false);
            }
        });

        rd_button_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollid=3;
                callToGetAllAppoinments(false);

            }
        });
        appointmentDataArrayList = new ArrayList<>();
        adapter = new ServiceAppointmentsAdapter(context, appointmentDataArrayList, ServiceAppointmentActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnScheduleAppointment.setOnClickListener(this);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
//        swipeRefreshLayout = findViewById(R.id.swipeContainer);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
//                android.R.color.holo_green_light,
//                android.R.color.holo_purple,
//                android.R.color.holo_red_light);

        /* SWIPE TO REFRESH */
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//                if (Utils.isNetworkAvailable(context))
//                    callToGetAllAppoinments(true);
//                else
//                    showLayouts();
//            }
//        });

        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);
        else
            showLayouts();
    }

    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
//            swipeRefreshLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {



            case R.id.buttonScheduleAppointment:

                Intent  intent=new Intent(context, AppoinmentScheduleActivity.class);
                intent.putExtra("KEY_HIDE_ADD",true);
                if (rollid==4){
                    intent.putExtra("IsFromPatho",true);

                }else {
                    intent.putExtra("IsFromPatho",false);

                }

                startActivityForResult(intent, REQ_FOR_APPOINMENT);
//                rel_appointment
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnSend:


                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    callToGetAllAppoinments(false);
                }
                else
                    showLayouts();
                break;


            case R.id.buttonSleep_ViewMore:

                break;
        }

    }

    @Override
    public void GetClickedAppointment(String type, int position, GetAllAppointmentResponse.AppointmentData model)
    {

        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;

        }



        if (!TextUtils.isEmpty(type))
        {
            if (type.equals("edit"))
            {
                FragmentManager fm = getSupportFragmentManager();
                editDialog = new MyAppoinmentEditDialog();

                Bundle bundle = new Bundle();
                bundle.putSerializable("MODEL", model);
                bundle.putBoolean("PATHO",true);
                bundle.putBoolean("FROM_SERVICE",true);
                bundle.putString("rollID",String.valueOf(rollid));
                editDialog.setArguments(bundle);
                editDialog.show(fm, "edit_fragment");
            }
            else
            {
                deleteAppoinment(position, model);
            }
        }
    }

    @Override
    public void onEdit(GetAllAppointmentResponse.AppointmentData model)
    {
        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);
        else
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
    }

    public void deleteAppoinment(final int pos , final GetAllAppointmentResponse.AppointmentData model)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete appointment!")
                .setMessage("Do you really want to delete this appointment?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context))
                        {
                            utils.showProgressbar(context);

                            callDeleteAppoinment(pos, model);
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

    private void callDeleteAppoinment(final int pos , GetAllAppointmentResponse.AppointmentData model)
    {
        AppoinmentEditRequest request = new AppoinmentEditRequest();
        request.setUserID(model.getUserID());
        request.setApptID(model.getApptID());

        Call<AppoinmentResponse> call = appoinmentService.deleteAppoinment(request);
        call.enqueue(new Callback<AppoinmentResponse>()
        {
            @Override
            public void onResponse(Call<AppoinmentResponse> call, Response<AppoinmentResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AppoinmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        // call List API to reload list
                        appointmentDataArrayList.remove(pos);
                        adapter.notifyDataSetChanged();
                        callToGetAllAppoinments(false);
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
            public void onFailure(Call<AppoinmentResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }
    private void callToGetAllAppoinments(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setRoleId(rollid);
        request.setUserID(userId);


        Call<GetAllAppointmentResponse> call = appoinmentService.getAllAppoinments(request);
        call.enqueue(new Callback<GetAllAppointmentResponse>()
        {
            @Override
            public void onResponse(Call<GetAllAppointmentResponse> call, Response<GetAllAppointmentResponse> response)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetAllAppointmentResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<GetAllAppointmentResponse.AppointmentData>  tempList = appointmentResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
                            appointmentDataArrayList.clear();
                            appointmentDataArrayList.addAll(tempList);
                            adapter.notifyDataSetChanged();
                            txtNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            txtNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    else{
                        txtNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

//                        Snackbar.make(findViewById(android.R.id.content), appointmentResponse.getMessage(), Snackbar.LENGTH_LONG).show();

                    }


                }

            }

            @Override
            public void onFailure(Call<GetAllAppointmentResponse> call, Throwable t)
            {
                Log.e("ERROR---->", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_FOR_APPOINMENT && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("RESULT"))
            {
                if (data.getStringExtra("RESULT").equals("ok"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        callToGetAllAppoinments(false);
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == 205 && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("Reeacoach"))
            {
                if (data.getStringExtra("Reeacoach").equals("yes"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {

                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }


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
