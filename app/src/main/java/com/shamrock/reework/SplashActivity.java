package com.shamrock.reework;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.BeforeAfterModule.adapter.PActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.Welcome_ScheduleBloodTestActivity;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodNewActivity;
import com.shamrock.reework.activity.HealthModule.activity.NewHealthparameterGoobActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.LoginModule.ChooseLoginActivity;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.activity.MyPlansModule.activity.MyPlanDetailsActivity;
import com.shamrock.reework.activity.MyPlansModule.activity.NewMyPlansActivity;
import com.shamrock.reework.activity.NewHealthProfile.NewHealthProfileActivity;
import com.shamrock.reework.activity.WeightModule.activity.WeightActivitylatest;
import com.shamrock.reework.activity.WelcomeModule.ClsCommonWellcomaster;
import com.shamrock.reework.activity.WelcomeModule.Sunit;
import com.shamrock.reework.activity.WelcomeModule.WelcomeActivity;
import com.shamrock.reework.activity.community.CommuniyActivity;
import com.shamrock.reework.activity.health.HealthNewActivity;
import com.shamrock.reework.activity.healthmonitoring.NewHealthMonitoringActivity;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewHealthparamterActivity;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.joinNowActivity;
import com.shamrock.reework.activity.parameter.ParameterTestGraphActivity;
import com.shamrock.reework.activity.recipe.CreateRecipeActivity;
import com.shamrock.reework.activity.recipeanalytics.RecipeAnalyticsActivity;
import com.shamrock.reework.activity.reecoachquestion.ReecoachQuestionActivity;
import com.shamrock.reework.activity.reeworkerhealth.NewDesignHealthActivity;
import com.shamrock.reework.activity.unfreezeData.ClsUnfreezeDataMain;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.newdesign.StudentMeuLandingActivity;
import com.shamrock.reework.payment.CouponActivity;
import com.shamrock.reework.payment.MainActivity;
import com.shamrock.reework.payment.paymentHistoryActivity;
import com.shamrock.reework.reecoachmodule.activities.ReecoachDashBoardActivity;
import com.shamrock.reework.reecoachmodule.activities.ReeworkerListActivity;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity
{

    Context context;
    private SessionManager session;
    private Utils utils;
    private CommonService commonService;
    private ImageView img_cycle;
    private  TextView ttt;
    RelativeLayout linBottom;
    boolean isFirstTimeSpalsh;
    int miliseconds=3000;
    RelativeLayout rel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;
        session = new SessionManager(context);
        session.setStringValue("statusdate","");
        session.setStringValue("CalenderSelectedDate","");

        img_cycle = findViewById(R.id.img_cycle);


        if (false){

            startActivity(new Intent(SplashActivity.this, joinNowActivity.class));
            return;
        }

//        if (true){
//
//            Glide.with(SplashActivity.this)
//                    .asGif()
//                    .load(R.drawable.deafault_recipe)
//                    .placeholder(ResourcesCompat.getDrawable(context.getResources(),
//                            R.drawable.splash, null))
//                    .centerCrop()
//                    .into(new ImageViewTarget<GifDrawable>(img_cycle) {
//                        @Override
//                        protected void setResource(@Nullable GifDrawable resource) {
//                            img_cycle.setImageDrawable(resource);
//                        }
//                    });
//            return;
//        }
        getUnfreezeMessageAPI();



        ttt = findViewById(R.id.ttt);
        linBottom = findViewById(R.id.linBottom);
        rel = findViewById(R.id.rel);
//        CommonConfetti.rainingConfetti(rel, new int[] { Color.BLACK })
//                .infinite();


//
        if (!session.getStringValue("KEY_BEFORE_WELCOME_IMAGE").isEmpty()){
            try {
                ttt.setText(session.getStringValue("KEY_BEFORE_WELCOME_TEXT"));

                if (true){
                    ttt.setVisibility(View.GONE);
                    Glide.with(this)
                            .load(R.drawable.splash)
                            .into(img_cycle);

                                                    callSessionLogin();

                }

//
//                Glide.with(context)
//                        .load(session.getStringValue("KEY_BEFORE_WELCOME_IMAGE"))
//                        .listener(new RequestListener<Drawable>() {
//                            @Override
//                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
////                            callSessionLogin();
//
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                                linBottom.setVisibility(View.VISIBLE);
//                                callSessionLogin();
//                                return false;
//                            }
//                        })
//                        .into(img_cycle);






//                getBeforewellcomeMEssageMasterSlogan();

            }catch (Exception e){
                e.printStackTrace();
            }



        }else {

            if (true){
                ttt.setVisibility(View.GONE);
                Glide.with(this)
                        .load(R.drawable.splash)
                        .into(img_cycle);

                callSessionLogin();

            }
//            getBeforewellcomeMEssageMasterSlogan();

        }




        getwellcomeMEssageMasterSlogan();

        session.setStringValue("showDate","");
        session.setStringValue("statusdate","");
        session.setStringValue("CommanDateFormat","yyyy-MM-dd");
        session.setStringValue("backdatesubmit","");
        session.setStringValue("backdateShow","");
        String strMessage = getResources().getString(R.string.splash_message);
        int INT_START = 8;
        int INT_END = 22;
        SpannableStringBuilder str = new SpannableStringBuilder(strMessage);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                INT_START, INT_END, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ttt.setText(str);



    }



    private void getwellcomeMEssageMasterSlogan(){


        utils = new Utils();
        session = new SessionManager(this);

        commonService = Client.getClient().create(CommonService.class);

        Call<ClsCommonWellcomaster> call = commonService.getWelcomeMessage("WELCOME");
        call.enqueue(new Callback<ClsCommonWellcomaster>()
        {
            @Override
            public void onResponse(Call<ClsCommonWellcomaster> call, retrofit2.Response<ClsCommonWellcomaster> response)

            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsCommonWellcomaster moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){

                                    session.setStringValue("KEY_WELCOME_TEXT",moodResponse.getData().getMessage());
                                    session.setStringValue("KEY_WELCOME_IMAGE",moodResponse.getData().getImagePath());


                                }



                            }else {



                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<ClsCommonWellcomaster> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    private void getUnfreezeMessageAPI(){


        utils = new Utils();
        session = new SessionManager(this);

        commonService = Client.getClient().create(CommonService.class);
//        http://shamrockuat.dweb.in/Api/Master/FreezeUnfreezeMaster
        Call<ClsUnfreezeDataMain> call = commonService.getUnfreezeMessageAPI();
        call.enqueue(new Callback<ClsUnfreezeDataMain>()
        {
            @Override
            public void onResponse(Call<ClsUnfreezeDataMain> call, retrofit2.Response<ClsUnfreezeDataMain> response)
            {


                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsUnfreezeDataMain moodResponse = response.body();

                        if (moodResponse!=null){

                            if (moodResponse.getCode()==0){

                                if (moodResponse.getData()!=null){

                                    session.setStringValue("KEY_EMAIL",moodResponse.getData().getEmailinfo());
                                    session.setStringValue("KEY_MOBILE",moodResponse.getData().getMobileInfo());


                                }



                            }else {



                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<ClsUnfreezeDataMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();



        Log.w("TAG", "Started");

    }

    private void getBeforewellcomeMEssageMasterSlogan(){
        commonService = Client.getClient().create(CommonService.class);
        Call<ClsCommonWellcomaster> call = commonService.getWelcomeMessage("BEFORE WELCOME");
        call.enqueue(new Callback<ClsCommonWellcomaster>()
        {
            @Override
            public void onResponse(Call<ClsCommonWellcomaster> call, retrofit2.Response<ClsCommonWellcomaster> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        final ClsCommonWellcomaster moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    final SessionManager session=new SessionManager(getApplicationContext());
                                    if (session.getStringValue("KEY_BEFORE_WELCOME_IMAGE").isEmpty()){

                                        Glide.with(context)
                                                .load(moodResponse.getData().getImagePath())
                                                .listener(new RequestListener<Drawable>() {
                                                    @Override
                                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                           .                 callSessionLogin();

                                                        return false;

                                                    }
                                                    @Override
                                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                        linBottom.setVisibility(View.VISIBLE);
                                                        ttt.setText(moodResponse.getData().getMessage());


                                                        if (!session.getBooleanValue("Key_isFirstTimeSpalsh")){

                                                            session.setBooleanValue("Key_isFirstTimeSpalsh",true);
//                                                callSessionLogin();

                                                        }
                                                        miliseconds=3000;
                                                        callSessionLogin();


                                                        return false;
                                                    }
                                                })
                                                .into(img_cycle);
                                    }


                                    session.setStringValue("KEY_BEFORE_WELCOME_TEXT",moodResponse.getData().getMessage());
                                    session.setStringValue("KEY_BEFORE_WELCOME_IMAGE",moodResponse.getData().getImagePath());


//                                    ttt.setText(session.getStringValue("KEY_BEFORE_WELCOME_TEXT"));




                                    if (session.getBooleanValue("Key_isFirstTimeSpalsh")){


                                    }






                                }






                            }else {
                                callSessionLogin();




                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                        callSessionLogin();

                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsCommonWellcomaster> call, Throwable t)
            {
                callSessionLogin();


                Log.e("ERROR------>", t.toString());
            }
        });
    }

    private void callSessionLogin() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (session.isLoggedIn())
                {


                    if (session.getStringValue("ROLEID").equalsIgnoreCase("3")){

                        Intent intent = new Intent(context, ReecoachDashBoardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }else {

                        int planId = session.getIntValue(SessionManager.KEY_USER_PLAN_ID);
                        if (planId == 1)
                        {
                            if (session.getBooleanValue(SessionManager.KEY_USER_IS_BLOOD_TEST_SCHEDULE))
                            {
                                Intent intent = new Intent(context, WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(context, Welcome_ScheduleBloodTestActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            Intent intent = new Intent(context, WelcomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }






                    /*// start welcome page
                    startActivity(new Intent(context, WelcomeActivity.class));*/
                }
                else
                {


                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    intent.putExtra("ISReework",true);
                    startActivity(intent);


//                    startActivity(new Intent(SplashActivity.this, ChooseLoginActivity.class));
//                    startActivity(new Intent(SplashActivity.this, ReeworkerListActivity.class));
//                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                }
                finish();
            }
        }, miliseconds);
    }

}
