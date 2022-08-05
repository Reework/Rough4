package com.shamrock.reework.activity.dietprofile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.SplashActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyPlansModule.activity.MyPlansActivity;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.WelcomeModule.ClsCommonWellcomaster;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class DietProfileActivity extends AppCompatActivity {
    Context context;
    private SessionManager session;
    private Utils utils;
    private CommonService commonService;
    private SessionManager sessionManager;
    TextView txt_reecoach_names,txt_genger_new,txt_bmi,txt_bmr,txt_wt,txt_ht,txt_ibw,txt_food_habit,name_usewr,txt_lifestyle;
    private int sendId;
    ImageView img_user_pic;
    TextView txt_carb,txt_fat,txt_fibre,txt_protine,txt_health_goal;
    TextView txt_targetweight;
    private void setProfileImage()
    {
      String  imageUrl = "";
        imageUrl = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);



        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);

        if (isValidContextForGlide(context))
        {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(options.circleCrop())
                    .listener(new RequestListener<Drawable>()
                    {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource)
                        {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource)
                        {
                            return false;
                        }
                    })
                    .into(img_user_pic);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_profile);

        txt_health_goal=findViewById(R.id.txt_health_goal);
        txt_protine=findViewById(R.id.txt_protine);
        txt_fibre=findViewById(R.id.txt_fibre);
        txt_fat=findViewById(R.id.txt_fat);
        txt_carb=findViewById(R.id.txt_carb);
        txt_targetweight=findViewById(R.id.txt_targetweight);

        context = DietProfileActivity.this;
        session = new SessionManager(context);
        utils = new Utils();
        commonService = Client.getClient().create(CommonService.class);

        sendId= Integer.parseInt(String.valueOf(getIntent().getIntExtra("ID",0)));

        img_user_pic=findViewById(R.id.img_user_pic);
        txt_reecoach_names=findViewById(R.id.txt_reecoach_names);
        txt_genger_new=findViewById(R.id.txt_genger_new);
        txt_bmi=findViewById(R.id.txt_bmi);
        txt_bmr=findViewById(R.id.txt_bmr);
        txt_ht=findViewById(R.id.txt_ht);
        txt_wt=findViewById(R.id.txt_wt);
        txt_ibw=findViewById(R.id.txt_ibw);
        txt_food_habit=findViewById(R.id.txt_food_habit);
        name_usewr=findViewById(R.id.name_usewr);

        callDietProfileAPi();


        setProfileImage();
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Diet Profile");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();

                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void callDietProfileAPi() {
        utils = new Utils();
        sessionManager = new SessionManager(context);

      int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Call<ClsDietProfile> call = commonService.getDeitProfile(userID,sendId);
//        Call<ClsDietProfile> call = commonService.getDeitProfile(8331,47);
        call.enqueue(new Callback<ClsDietProfile>()
        {
            @Override
            public void onResponse(Call<ClsDietProfile> call, retrofit2.Response<ClsDietProfile> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsDietProfile moodResponse = response.body();

                        if (moodResponse!=null){
                            TextView txt_lifestyle=findViewById(R.id.txt_lifestyle);

                            name_usewr.setText(moodResponse.getReeworkerName());

                            if (moodResponse.getRecentWeight()!=null){
                                TextView txt_wt_recent=findViewById(R.id.txt_wt_recent);
                                txt_wt_recent.setText(""+moodResponse.getRecentWeight());
                            }else {
                                TextView txt_wt_recent=findViewById(R.id.txt_wt_recent);

                                txt_wt_recent.setText("-");

                            }

                            if (moodResponse.getTargetWeight()!=null){
                                TextView txt_targetweight=findViewById(R.id.txt_targetweight);
                                txt_targetweight.setText(""+moodResponse.getTargetWeight()+" Kgs");
                            }else {
                                TextView txt_targetweight=findViewById(R.id.txt_targetweight);

                                txt_targetweight.setText("-");

                            }


                            TextView txt_deit_prescribe=findViewById(R.id.txt_deit_prescribe);
                            if (moodResponse.getDietPriscribed()!=null){
                                txt_deit_prescribe.setText("Diet Prescribed For : "+moodResponse.getDietPriscribed());

                            }else {
                                txt_deit_prescribe.setText("Diet Prescribed For : ");

                            }
                            if (moodResponse.getHealthGoal()!=null){
                                txt_health_goal.setText("Health Goal : "+moodResponse.getHealthGoal());
                            }

                            if (moodResponse.getLifeStyle()!=null){
                                txt_lifestyle.setText(moodResponse.getLifeStyle());

                            }else {
                                txt_lifestyle.setText("-");

                            }

                            if (moodResponse.getReecoachName()!=null){
                                txt_reecoach_names.setText(""+moodResponse.getReecoachName());
                            }
                            if(moodResponse.getAge()!=null){
                                txt_genger_new.setText("Age : "+moodResponse.getAge()+" ");
                            }

                            if(moodResponse.getBMI()!=null){
                                txt_bmi.setText(""+moodResponse.getBMI());
                            }

                            if(moodResponse.getBMR()!=null){
                                txt_bmr.setText(""+moodResponse.getBMR());
                            }
                            if(moodResponse.getHeight()!=null){
                                txt_ht.setText(""+moodResponse.getHeight()+"");
                            }

                            if(moodResponse.getFirstDayWeight()!=null){
                                txt_wt.setText(""+moodResponse.getFirstDayWeight()+"");

                            } if(moodResponse.getIBW()!=null){
                                txt_ibw.setText(""+moodResponse.getIBW()+"");
                            } if(moodResponse.getFoodHabit()!=null){
                                txt_food_habit.setSelected(true);
                                txt_food_habit.setText(""+moodResponse.getFoodHabit()+"");
                            }

                            txt_carb.setText(moodResponse.getCarbs());
                            txt_fat.setText(moodResponse.getFat());
                            txt_fibre.setText(moodResponse.getFibre());
                            txt_protine.setText(moodResponse.getProtein());
                            TextView txt_total_cal=findViewById(R.id.txt_total_cal);
                            if (moodResponse.getCalories()!=null){
                                txt_total_cal.setText("Total calories : "+moodResponse.getCalories());

                            }else {
                                txt_total_cal.setText("Total calories : -");

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<ClsDietProfile> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }
}
