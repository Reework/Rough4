package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
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

import com.github.mikephil.charting.charts.LineChart;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.newregistration.ClsregQn;
import com.shamrock.reework.activity.AnalysisModule.activity.userQuestiondata.QuestionData;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.RegistrationRequest;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.api.response.RegistrationResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterQuestionActivity extends AppCompatActivity implements OnRadioBtnYesClick{
    ArrayList<ClsRegisterQutn> clsRegisterQutnArrayList=new ArrayList<>();
    RecyclerView lst_qn;
    StringBuilder stringBuilder;
    ArrayList<String> arylstiDs;
    TextView tvTitle;
    Button btn_new_trail;
    Context context;
    Toolbar toolbar;
    Utils utils;
    LinearLayout ll_remain_data;
    SessionManager sessionManager;
    NotificationService notificationService;
    RelativeLayout noInternetLayout;
    LinearLayout mainLayout;
    Button btnRefresh;
    LineChart linegraph;
    TextView txt_startdate;
    TextView txt_enddate;

    ArrayList<GetAllNotificationResponse.Notifications> mNotificationList;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    LoginService loginService;
    int userId;
    private String strSubmitStartDate;
    private String strSubmitEndDate;
    CommonService commonService;
    RegistrationService regService;
    String Key_sucess_msg_text;

    @Override
    public void onBackPressed() {
        return;
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lst_qn=findViewById(R.id.lst_qn);
        stringBuilder=new StringBuilder();
        arylstiDs=new ArrayList<>();
        tvTitle=findViewById(R.id.tvTitle);
        txt_enddate=findViewById(R.id.txt_enddate);
        txt_startdate=findViewById(R.id.txt_startdate);
        ll_remain_data=findViewById(R.id.ll_remain_data);
        context=RegisterQuestionActivity.this;
        TextView qn_header_trial=findViewById(R.id.qn_header_trial);
        qn_header_trial.setSelected(true);
        utils = new Utils();
        sessionManager = new SessionManager(context);
        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);
        showStartEndDate();
        Key_sucess_msg_text=getIntent().getStringExtra("Key_sucess_msg");

        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText("Objectives of 7 days Trial");

        sessionManager = new SessionManager(context);
        notificationService = Client.getClient().create(NotificationService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        getAllQuestiontrail();




    }



    public void clearTask()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }


    private void showStartEndDate() {
        strSubmitStartDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        txt_startdate.setText(formatDates(strSubmitStartDate));
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,7);
        strSubmitEndDate=new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        txt_enddate.setText(formatDates(strSubmitEndDate));
    }

    private void getAllQuestiontrail()
    {
        utils.showProgressbar(context);

        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userId);

        Call<ClsregQn> call = notificationService.getTrailQuestion(userId);

        call.enqueue(new Callback<ClsregQn>()
        {
            @Override
            public void onResponse(Call<ClsregQn> call, Response<ClsregQn> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsregQn listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        ArrayList<QuestionData> tempList = listResponse.getData();


                        if (tempList != null && tempList.size() > 0)
                        {
                            ll_remain_data.setVisibility(View.VISIBLE);
                            setData(tempList);

                        }else {
                            Toast.makeText(RegisterQuestionActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            finish();
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
            public void onFailure(Call<ClsregQn> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }

    private void setData(ArrayList<QuestionData> arylst_question) {
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("Whether you are consuming balance diet.","1"));
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("Whether you are taking sufficient water.","2"));
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("Whether you have adequatee sleep.","3"));
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("Whether you have adequatee physical activities.","4"));
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("Net Calories consumed.","4"));
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("It will give you a Diet Plan,if required.","4"));
        clsRegisterQutnArrayList.add(new ClsRegisterQutn("It will also suggest Spiritual videos etc.","4"));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lst_qn.setLayoutManager(layoutManager);
        lst_qn.setAdapter(new NewQnlistRecylerAdapter(RegisterQuestionActivity.this,arylst_question));

    }


    @Override
    public void getSelectedID(int id) {

        if (!arylstiDs.contains(id)){
            arylstiDs.add(String.valueOf(id));

        }
    }

    @Override
    public void notselected(int id) {
        if (arylstiDs.contains(id)){
            arylstiDs.remove(id);
        }

    }
    public void clickonTrail(View view) {



        utils.showProgressbar(context);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                final Dialog dialog=new Dialog(RegisterQuestionActivity.this);
                dialog.setContentView(R.layout.dialog_sucess);
                dialog.setCancelable(false);
                Button btn_ok=dialog.findViewById(R.id.btn_ok);
                TextView txt_msg=dialog.findViewById(R.id.txt_msg);
                txt_msg.setText(Key_sucess_msg_text);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        clearTask();

                    }
                });
                dialog.show();

            }
        },2000);


//        callRegApi();

    }
}
