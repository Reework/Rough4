package com.shamrock.reework.activity.MyPlansModule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.MyPlansModule.score.ClsActionScore;
import com.shamrock.reework.activity.MyPlansModule.score.Score;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.todaysplan.model.ClsTodaysPlanMain;
import com.shamrock.reework.activity.todaysplan.model.ReeworkerPlan;
import com.shamrock.reework.activity.todaysplan.model.TodaysPlanData;
import com.shamrock.reework.activity.todaysplan.model.adapters.ClsTodaysPlansAdapter;
import com.shamrock.reework.activity.todaysplan.model.adapters.NewClsTodaysPlansAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActionScoreActivity extends AppCompatActivity {
    CircularProgressBar progress_circular_food,progress_circular_nutrition,
            progress_circular_supliment,progress_circular_lifestyle,progress_circular_cheat,progress_circular_overall;
    private ClsActionScore clsActionScore;
    Utils utils;
    private MyPlansService myPlansService;
    private SessionManager sessionManager;
    private int userID;
    private String total1,total2,total3,total4,total5;
    private TextView txt_score_food,txt_score_suppliment,txt_score_cheat,txt_score_lifestyle,txt_score_nutrition,txt_score_overall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_score);

        txt_score_food=findViewById(R.id.txt_score_food);
        txt_score_suppliment=findViewById(R.id.txt_score_suppliment);
        txt_score_cheat=findViewById(R.id.txt_score_cheat);
        txt_score_lifestyle=findViewById(R.id.txt_score_lifestyle);
        txt_score_nutrition=findViewById(R.id.txt_score_nutrition);
        txt_score_overall=findViewById(R.id.txt_score_overall);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progress_circular_overall=findViewById(R.id.progress_circular_overall);
        progress_circular_nutrition=findViewById(R.id.progress_circular_nutrition);
        progress_circular_food=findViewById(R.id.progress_circular_food);
        progress_circular_supliment=findViewById(R.id.progress_circular_supliment);
        progress_circular_lifestyle=findViewById(R.id.progress_circular_lifestyle);
        progress_circular_cheat=findViewById(R.id.progress_circular_cheat);

        utils = new Utils();
        myPlansService = Client.getClient().create(MyPlansService.class);
        sessionManager = new SessionManager(ActionScoreActivity.this);

//        userIDuserID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        callTodaysPlanApi(0);

    }

    private void callTodaysPlanApi(int planID)
    {

          utils=new Utils();

        utils.showProgressbar(ActionScoreActivity.this);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);
        String date=sessionManager.getStringValue("ActionScore");


        Call<ClsTodaysPlanMain> call = myPlansService.getTodaysPlan(userID,planID,date);
        call.enqueue(new Callback<ClsTodaysPlanMain>()
        {
            @Override
            public void onResponse(Call<ClsTodaysPlanMain> call, Response<ClsTodaysPlanMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsTodaysPlanMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {



                        TodaysPlanData aryltTodaysPlanData = response.body().getData();


                        setActionScoreData(listResponse.getScore());












                    }
                    else
                    {

                    }
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ClsTodaysPlanMain> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });
    }

    private void setActionScoreData(ArrayList<Score> score) {
        clsActionScore=new ClsActionScore();


        for (int i = 0; i <score.size() ; i++) {
            if (score.get(i).getPlanTypeId()==1){
                clsActionScore.setNutritionplan(String.valueOf(score.get(i).TotalScore));
                progress_circular_nutrition.setProgressMax(100);
                progress_circular_nutrition.setProgress(Float.parseFloat(clsActionScore.getNutritionplan()));
                total1=clsActionScore.getNutritionplan();

                txt_score_nutrition.setText("Score : "+clsActionScore.getNutritionplan()+"%");



            }

            if (score.get(i).getPlanTypeId()==2){
                clsActionScore.setSupplimentplan(String.valueOf(score.get(i).TotalScore));
                progress_circular_supliment.setProgressMax(100);
                progress_circular_supliment.setProgress(Float.parseFloat(clsActionScore.getSupplimentplan()));
                total2=clsActionScore.getSupplimentplan();
                txt_score_suppliment.setText("Score : "+clsActionScore.getSupplimentplan()+"%");



            }

            if (score.get(i).getPlanTypeId()==4){
                clsActionScore.setReeplaceitem(String.valueOf(score.get(i).TotalScore));
                progress_circular_cheat.setProgressMax(100);
                progress_circular_cheat.setProgress(Float.parseFloat(clsActionScore.getReeplaceitem()));
                total3=clsActionScore.getReeplaceitem();
                txt_score_cheat.setText("Score : "+clsActionScore.getReeplaceitem()+"%");


            }

            if (score.get(i).getPlanTypeId()==5){
                clsActionScore.setLifestyleplan(String.valueOf(score.get(i).TotalScore));
                progress_circular_lifestyle.setProgressMax(100);

                progress_circular_lifestyle.setProgress(Float.parseFloat(clsActionScore.getLifestyleplan()));
                total4=clsActionScore.getLifestyleplan();
                txt_score_lifestyle.setText("Score : "+clsActionScore.getLifestyleplan()+"%");


            }

            if (score.get(i).getPlanTypeId()==6){
                clsActionScore.setFoodplan(String.valueOf(score.get(i).TotalScore));
                progress_circular_food.setProgressMax(100);
                progress_circular_food.setProgress(Float.parseFloat(clsActionScore.getFoodplan()));
                total5=clsActionScore.getFoodplan();
                txt_score_food.setText("Score : "+clsActionScore.getFoodplan()+"%");


            }
            if (total1==null){
                total1="0";
            }
            if (total2==null){
                total2="0";
            }
            if (total3==null){
                total3="0";
            }
            if (total4==null){
                total4="0";
            }
            if (total5==null){
                total5="0";
            }


            float totalall=Float.parseFloat(total1)+Float.parseFloat(total2)+Float.parseFloat(total3)+Float.parseFloat(total4)+Float.parseFloat(total5);

            double  percetage = (float)((totalall /500) * 100);
            progress_circular_overall.setProgress((float) percetage);

            txt_score_overall.setText("Score : "+(float) percetage+"%");





        }
    }
}
