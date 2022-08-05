package com.shamrock.reework.activity.reecoach;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.reecoach.adapters.ClsReecoachQnDetails;
import com.shamrock.reework.activity.reecoach.adapters.ClsReecoachQnMain;
import com.shamrock.reework.activity.reecoach.postrate.ClsSucessRateMain;
import com.shamrock.reework.activity.reevaluate.ReevaluateActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.ProfileDataRequest;
import com.shamrock.reework.api.response.ProfileDataResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReecoachRatingActivity extends AppCompatActivity  {
    private ListView lst_question;

    private LinearLayout ll_q1;
    private LinearLayout ll_q2;
    private LinearLayout ll_q3;
    private LinearLayout ll_q4;
    private LinearLayout ll_q5;
    private RatingBar edt_ans_1;
    private RatingBar edt_ans_2;
    private RatingBar edt_ans_3;
    private RatingBar edt_ans_4;
    private RatingBar edt_ans_5;
    private TextView q1;
    private TextView q2;
    private TextView q3;
    private TextView q4;
    private TextView q5;



    private CommonService commonService;
    private SessionManager sessionManager;
    private Utils utils;
    private int userID;
    RatingBar ratingBar;
    private ArrayList<ClsReecoachQnDetails> arylst_reecoach_qn;
    private int mainsize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reecoach_rating);
        initData();
        initView();
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Rating of Reecoach");
        ImageView imageView=findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView btn_post_rate=findViewById(R.id.btn_post_rate);
        btn_post_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postApi();
            }
        });
        ImageView img_close_rate=findViewById(R.id.img_close_rate);
        img_close_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initData() {

        commonService = Client.getClient().create(CommonService.class);
        sessionManager = new SessionManager(ReecoachRatingActivity.this);
        utils = new Utils();
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    private void initView() {
        ratingBar=findViewById(R.id.rating_reecoach);
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        ratingBar.setFocusable(false);
        ll_q1=findViewById(R.id.ll_q1);
        ll_q2=findViewById(R.id.ll_q2);
        ll_q3=findViewById(R.id.ll_q3);
        ll_q4=findViewById(R.id.ll_q4);
        ll_q5=findViewById(R.id.ll_q5);

        q1=findViewById(R.id.q1);
        q2=findViewById(R.id.q2);
        q3=findViewById(R.id.q3);
        q4=findViewById(R.id.q4);
        q5=findViewById(R.id.q5);

        edt_ans_1=findViewById(R.id.edt_ans_1);
        edt_ans_2=findViewById(R.id.edt_ans_2);
        edt_ans_3=findViewById(R.id.edt_ans_3);
        edt_ans_4=findViewById(R.id.edt_ans_4);
        edt_ans_5=findViewById(R.id.edt_ans_5);



        callApi();

    }

    private void callApi()
    {

        sessionManager=new SessionManager(ReecoachRatingActivity.this);
       int reecoachID = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);

        Call<ClsReecoachQnMain> call = commonService.getReecoachRatingQn(reecoachID,userID);
        call.enqueue(new Callback<ClsReecoachQnMain>()
        {
            @Override
            public void onResponse(Call<ClsReecoachQnMain> call, Response<ClsReecoachQnMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsReecoachQnMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        try {
                            arylst_reecoach_qn=listResponse.getData().getQuestions();

                                 mainsize=listResponse.getData().getQuestions().size();
                                 if (listResponse.getData().getRating()!=null){
                                     ratingBar.setRating(Float.parseFloat(listResponse.getData().getRating()));

                                 }
                                 if (mainsize==0){
                                     return;
                                 }

                                if (mainsize==1){

                                    oneVisible(listResponse);


                                }else if(mainsize==2){

                                    oneVisible(listResponse);
                                    twoVisible(listResponse);



                            }else if (mainsize==3){
                                    oneVisible(listResponse);
                                    twoVisible(listResponse);
                                    threeVisible(listResponse);



                                } else if (mainsize==4) {
                                    oneVisible(listResponse);
                                    twoVisible(listResponse);
                                    threeVisible(listResponse);
                                    fourVisible(listResponse);

                                }else if (mainsize==5){
                                     oneVisible(listResponse);
                                     twoVisible(listResponse);
                                     threeVisible(listResponse);
                                     fourVisible(listResponse);
                                     fiveVisble(listResponse);

                                }else {
                                    oneVisible(listResponse);
                                    twoVisible(listResponse);
                                    threeVisible(listResponse);
                                    fourVisible(listResponse);
                                    fiveVisble(listResponse);
                                }





                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ClsReecoachQnMain> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }

    private void fiveVisble(ClsReecoachQnMain listResponse) {
        ll_q5.setVisibility(View.VISIBLE);
        q5.setVisibility(View.VISIBLE);
        edt_ans_5.setVisibility(View.VISIBLE);
        q5.setText(listResponse.getData().getQuestions().get(4).getQuestion());
        edt_ans_5.setRating(Float.parseFloat(listResponse.getData().getQuestions().get(4).getAnswer()));
    }

    private void fourVisible(ClsReecoachQnMain listResponse) {
        ll_q4.setVisibility(View.VISIBLE);
        q4.setVisibility(View.VISIBLE);
        edt_ans_4.setVisibility(View.VISIBLE);

        q4.setText(listResponse.getData().getQuestions().get(3).getQuestion());
        edt_ans_4.setRating(Float.parseFloat(listResponse.getData().getQuestions().get(3).getAnswer()));
    }


    private void postApi()
    {
        utils.showProgressbar(ReecoachRatingActivity.this);

        sessionManager=new SessionManager(ReecoachRatingActivity.this);
        int reecoachID = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        ArrayList<ClsReecoachQnDetails> clsReecoachQnDetailsArrayList=new ArrayList<>();
        float avg;

        if (mainsize==1){
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(0).getId(),arylst_reecoach_qn.get(0).getQuestion(),arylst_reecoach_qn.get(0).getQuestionId(),String.valueOf(edt_ans_1.getRating()),reecoachID,userID));

        }else if (mainsize==2){
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(0).getId(),arylst_reecoach_qn.get(0).getQuestion(),arylst_reecoach_qn.get(0).getQuestionId(),String.valueOf(edt_ans_1.getRating()).toString(),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(1).getId(),arylst_reecoach_qn.get(1).getQuestion(),arylst_reecoach_qn.get(1).getQuestionId(),String.valueOf(edt_ans_2.getRating()),reecoachID,userID));

        }else if (mainsize==3){
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(0).getId(),arylst_reecoach_qn.get(0).getQuestion(),arylst_reecoach_qn.get(0).getQuestionId(),String.valueOf(edt_ans_1.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(1).getId(),arylst_reecoach_qn.get(1).getQuestion(),arylst_reecoach_qn.get(1).getQuestionId(),String.valueOf(edt_ans_2.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(2).getId(),arylst_reecoach_qn.get(2).getQuestion(),arylst_reecoach_qn.get(2).getQuestionId(),String.valueOf(edt_ans_3.getRating()),reecoachID,userID));

        }else if (mainsize==4){
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(0).getId(),arylst_reecoach_qn.get(0).getQuestion(),arylst_reecoach_qn.get(0).getQuestionId(),String.valueOf(edt_ans_1.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(1).getId(),arylst_reecoach_qn.get(1).getQuestion(),arylst_reecoach_qn.get(1).getQuestionId(),String.valueOf(edt_ans_2.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(2).getId(),arylst_reecoach_qn.get(2).getQuestion(),arylst_reecoach_qn.get(2).getQuestionId(),String.valueOf(edt_ans_3.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(3).getId(),arylst_reecoach_qn.get(3).getQuestion(),arylst_reecoach_qn.get(3).getQuestionId(),String.valueOf(edt_ans_4.getRating()),reecoachID,userID));

        }else if (mainsize==5){
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(0).getId(),arylst_reecoach_qn.get(0).getQuestion(),arylst_reecoach_qn.get(0).getQuestionId(),String.valueOf(edt_ans_1.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(1).getId(),arylst_reecoach_qn.get(1).getQuestion(),arylst_reecoach_qn.get(1).getQuestionId(),String.valueOf(edt_ans_2.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(2).getId(),arylst_reecoach_qn.get(2).getQuestion(),arylst_reecoach_qn.get(2).getQuestionId(),String.valueOf(edt_ans_3.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(3).getId(),arylst_reecoach_qn.get(3).getQuestion(),arylst_reecoach_qn.get(3).getQuestionId(),String.valueOf(edt_ans_4.getRating()),reecoachID,userID));
            clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(4).getId(),arylst_reecoach_qn.get(4).getQuestion(),arylst_reecoach_qn.get(4).getQuestionId(),String.valueOf(edt_ans_5.getRating()),reecoachID,userID));

        }else {
            if (mainsize!=0){
                clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(0).getId(),arylst_reecoach_qn.get(0).getQuestion(),arylst_reecoach_qn.get(0).getQuestionId(),String.valueOf(edt_ans_1.getRating()),reecoachID,userID));
                clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(1).getId(),arylst_reecoach_qn.get(1).getQuestion(),arylst_reecoach_qn.get(1).getQuestionId(),String.valueOf(edt_ans_2.getRating()),reecoachID,userID));
                clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(2).getId(),arylst_reecoach_qn.get(2).getQuestion(),arylst_reecoach_qn.get(2).getQuestionId(),String.valueOf(edt_ans_3.getRating()),reecoachID,userID));
                clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(3).getId(),arylst_reecoach_qn.get(3).getQuestion(),arylst_reecoach_qn.get(3).getQuestionId(),String.valueOf(edt_ans_4.getRating()),reecoachID,userID));
                clsReecoachQnDetailsArrayList.add(new ClsReecoachQnDetails(arylst_reecoach_qn.get(4).getId(),arylst_reecoach_qn.get(4).getQuestion(),arylst_reecoach_qn.get(4).getQuestionId(),String.valueOf(edt_ans_5.getRating()),reecoachID,userID));

            }

        }

float total=0;
        for (int i = 0; i <clsReecoachQnDetailsArrayList.size() ; i++) {

            total+=Float.parseFloat(clsReecoachQnDetailsArrayList.get(i).getAnswer());
        }
        avg=total/clsReecoachQnDetailsArrayList.size();
        Call<ClsSucessRateMain> call = commonService.postRatingQn(reecoachID,userID,avg,clsReecoachQnDetailsArrayList);
        call.enqueue(new Callback<ClsSucessRateMain>()
        {
            @Override
            public void onResponse(Call<ClsSucessRateMain> call, Response<ClsSucessRateMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsSucessRateMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        try {

                            Toast.makeText(ReecoachRatingActivity.this, "Thank you for giving your valuable feedback.", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();

                                }
                            },1000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ClsSucessRateMain> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }
    private void threeVisible(ClsReecoachQnMain listResponse) {
        ll_q3.setVisibility(View.VISIBLE);
        q3.setVisibility(View.VISIBLE);
        edt_ans_3.setVisibility(View.VISIBLE);
        q3.setText(listResponse.getData().getQuestions().get(2).getQuestion());
        if (listResponse.getData().getQuestions().get(2).getAnswer()!=null){
            edt_ans_3.setRating(Float.parseFloat(listResponse.getData().getQuestions().get(2).getAnswer()));

        }
    }

    private void twoVisible(ClsReecoachQnMain listResponse) {
        ll_q2.setVisibility(View.VISIBLE);
        q2.setVisibility(View.VISIBLE);
        edt_ans_2.setVisibility(View.VISIBLE);
        q2.setText(listResponse.getData().getQuestions().get(1).getQuestion());
        if (listResponse.getData().getQuestions().get(1).getAnswer()!=null){
            edt_ans_2.setRating(Float.parseFloat(listResponse.getData().getQuestions().get(1).getAnswer()));

        }
    }

    private void oneVisible(ClsReecoachQnMain listResponse) {
        ll_q1.setVisibility(View.VISIBLE);
        q1.setVisibility(View.VISIBLE);
        edt_ans_1.setVisibility(View.VISIBLE);
        q1.setText(listResponse.getData().getQuestions().get(0).getQuestion());
        if (listResponse.getData().getQuestions().get(0).getAnswer()!=null){
            edt_ans_1.setRating(Float.parseFloat(listResponse.getData().getQuestions().get(0).getAnswer()));

        }
    }
}
