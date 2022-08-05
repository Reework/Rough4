package com.shamrock.reework.activity.AnalysisModule.activity.newregistration;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
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
import com.shamrock.reework.activity.AnalysisModule.activity.userQuestiondata.QuestionData;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

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


    ArrayList<GetAllNotificationResponse.Notifications> mNotificationList;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    LoginService loginService;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lst_qn=findViewById(R.id.lst_qn);
        stringBuilder=new StringBuilder();
        arylstiDs=new ArrayList<>();
        tvTitle=findViewById(R.id.tvTitle);
        ll_remain_data=findViewById(R.id.ll_remain_data);
        context=RegisterQuestionActivity.this;

        TextView qn_header_trial=findViewById(R.id.qn_header_trial);
        qn_header_trial.setSelected(true);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("Objectives of 7 days Trial");


        utils = new Utils();
        sessionManager = new SessionManager(context);
        notificationService = Client.getClient().create(NotificationService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        getAllQuestiontrail();




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
                        ArrayList<Data> test=new ArrayList<>();


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

//        Intent intent = new Intent(RegisterQuestionActivity.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();

    }
}
