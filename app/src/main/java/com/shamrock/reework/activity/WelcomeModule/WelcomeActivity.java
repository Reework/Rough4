package com.shamrock.reework.activity.WelcomeModule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.SnipeetActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.Welcome_ScheduleBloodTestActivity;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.service.ClsMessagmasterPojo;
import com.shamrock.reework.activity.LoginModule.Data;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.LoginModule.UserStatus;
import com.shamrock.reework.activity.Pathologists.ChangePathoActivity;
import com.shamrock.reework.activity.Reecoachpathoselection.SelectReecoachPathActivity;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.activity.dietplan.RDPFoodPlanActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener
{

    Context context;
    Button imgNext;
    UnfreezeService unfreezeService;
    Utils utils;
    int userId, planId;
    String token;
    SessionManager sessionManager;
    LoginService loginService;
    boolean IsTrail=true;
    ImageView img_smiley;
    TextView textWelcomeMessage;
    private CommonService commonService;
    RelativeLayout rel_welcome;
    int pathoID=0;
    boolean isRequiredPAtho=false;
    LinearLayout ll_video;


     private static final String PATIENTAWERNESSEXTRA = "patientvideos";
        private SimpleExoPlayer player;
        private SimpleExoPlayerView simpleExoPlayerView;
        private ProgressBar pbIndicatorLoading;
        private ImageView ivFullScreen;
        private ImageView mFullScreenIcon;
        private FrameLayout mFullScreenButton;
        private Dialog mFullScreenDialog;
        private boolean mExoPlayerFullscreen = false;



    private void initVideo() {


        pbIndicatorLoading = findViewById(R.id.pb_indicator_loading);
        simpleExoPlayerView = findViewById(R.id.player_view);
        player = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(new DefaultBandwidthMeter.Builder().build()));

        simpleExoPlayerView.hideController();

//        extractYoutubeUrl();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "vidapp"), null);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(new String(sessionManager.getStringValue("KEY_WELCOME_IMAGE")).replaceAll(" ", "%20")));

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                Log.d("exo", "timeLine Changed");
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                pbIndicatorLoading.setVisibility(View.GONE);
                simpleExoPlayerView.hideController();
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d("exo", "loding changed= " + isLoading);
                if (isLoading) {
                    pbIndicatorLoading.setVisibility(View.VISIBLE);
                } else {
                    pbIndicatorLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d("exo", "state changed");
                pbIndicatorLoading.setVisibility(View.GONE);
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e("exo", "exoplayer error", error);
                pbIndicatorLoading.setVisibility(View.GONE);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {
                Log.d("exo", "seek processed");
                pbIndicatorLoading.setVisibility(View.VISIBLE);

            }
        });
        player.prepare(mediaSource);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(true);
        //end
        //   }


    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVideoPlayer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseVideoPlayer();
    }
    public void releaseVideoPlayer() {
        if (player != null) {
            player.release();
        }
        player = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ll_video=findViewById(R.id.ll_video);
        img_smiley=findViewById(R.id.img_smiley);
        textWelcomeMessage=findViewById(R.id.textWelcomeMessage);
        rel_welcome=findViewById(R.id.rel_welcome);
        context = WelcomeActivity.this;
        imgNext = findViewById(R.id.buttonNext_Welcome);
        imgNext.setOnClickListener(this);
        utils = new Utils();
        sessionManager = new SessionManager(context);
        sessionManager.setStringValue("Entrystatusdate","");
        unfreezeService = Client.getClient().create(UnfreezeService.class);
        loginService = Client.getClient().create(LoginService.class);
//        selectReecoachPathDialog(listResponse.getData());

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        planId = sessionManager.getIntValue(SessionManager.KEY_USER_PLAN_ID);
        token = sessionManager.getStringValue(SessionManager.KEY_USER_TOKEN);




        textWelcomeMessage.setText(sessionManager.getStringValue("KEY_WELCOME_TEXT"));


//        if (!sessionManager.getStringValue("KEY_WELCOME_IMAGE").endsWith(".mp4")){
//            ll_video.setVisibility(View.GONE);
//
//            Glide.with(WelcomeActivity.this)
//                    .load(sessionManager.getStringValue("KEY_WELCOME_IMAGE"))
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            rel_welcome.setVisibility(View.VISIBLE);
//                            rel_welcome.setBackgroundColor(getResources().getColor(R.color.black));
//                            return false;
//                        }
//                    })
//                    .into(img_smiley);
//        }else {
//            ll_video.setVisibility(View.VISIBLE);
//            img_smiley.setVisibility(View.GONE);
//            initVideo();
//        }






        if (sessionManager.getStringValue("KEY_WELCOME_TEXT").isEmpty()){
//            getMessageMasterSlogan();

        }
//        getHomeMessageMasterSlogan();
        callPathoReecoachStatus();
        callForUsrFreezStatus();

        Intent homeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
        homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeActivity);
        finish();
//        imgNext.performClick();




    }
    public String getCurrentDate()
    {
        String date = "";
        try
        {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        }catch (Exception e){e.printStackTrace();}
        return date;
    }
    private void getHomeMessageMasterSlogan(){


        utils.showProgressbar(this);
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsMessagmasterPojo> call = commonService.getMessageMaster();
        call.enqueue(new Callback<ClsMessagmasterPojo>()
        {
            @Override
            public void onResponse(Call<ClsMessagmasterPojo> call, retrofit2.Response<ClsMessagmasterPojo> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsMessagmasterPojo moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){



                                if (moodResponse.getData()!=null){
                                    sessionManager.setStringValue("KEY_HOME_IMAGE",moodResponse.getData().getImgPath());
                                    sessionManager.setStringValue("KEY_HOME_MESSAGE",moodResponse.getData().getHeader());

// message & Img comes from Api for App Dashboard

                                }






                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsMessagmasterPojo> call, Throwable t)
            {

            }
        });
    }


    private void getMessageMasterSlogan(){


        utils.showProgressbar(this);
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
                                    sessionManager.setStringValue("KEY_WELCOME_TEXT",moodResponse.getData().getMessage());
                                    sessionManager.setStringValue("KEY_WELCOME_IMAGE",moodResponse.getData().getImagePath());

                                    if (textWelcomeMessage!=null){
                                        textWelcomeMessage.setText(moodResponse.getData().getMessage());

                                    }


                                    if (img_smiley!=null){
                                        Glide.with(WelcomeActivity.this)
                                                .load(moodResponse.getData().getImagePath())
                                                .listener(new RequestListener<Drawable>() {
                                                    @Override
                                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                        return false;
                                                    }
                                                    @Override
                                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                        rel_welcome.setVisibility(View.VISIBLE);
                                                        rel_welcome.setBackgroundColor(getResources().getColor(R.color.black));

                                                        return false;
                                                    }
                                                })
                                                .into(img_smiley);
                                    }




                                }




//                                Toast.makeText(mContext, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();


                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


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


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonNext_Welcome:


                if (Utils.isNetworkAvailable(context)) {
                    callForUsrFreezStatus();
                }
                else{
                    Toast.makeText(WelcomeActivity.this, "No internet connection !", Toast.LENGTH_SHORT).show();

        }



                break;

            default:
        }
    }

    private void callUserStatusApi() {
//        utils.showProgressbar(context);
        Call<UserStatus> call = loginService.getUserStatusHistroy(userId);
        Log.d("req",call.request().toString());
        call.enqueue(new Callback<UserStatus>()
        {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    UserStatus listResponse = response.body();
                    Log.d("userresponce",listResponse.toString());

                    if (listResponse != null && listResponse.getCode().equals("0"))
                    {


                        Data data=listResponse.getData();

                        if (data!=null){

                            try {

//                                sessionManager.setStringValue("IsAllowUser",data.getIsAppliedBloodTest());
                                sessionManager.setStringValue("IsAllowUser",data.getIsAppliedBloodTest());
                                sessionManager.setStringValue("KeyAssingDailyTassk",data.getIsScheduledTask());
                                sessionManager.setStringValue("KeyAssingReecoach",data.getIsReecoachAssigned());
                                sessionManager.setStringValue("KeyIsFreezed",data.getIsFreezed());

                                if (sessionManager.getStringValue("FromWeb").equalsIgnoreCase("true")){
                                    sessionManager.setStringValue("IsAllowUser","true");

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            if (sessionManager.getBooleanValue(SessionManager.KEY_USER_HEALTH_IS_FOUND))
                            {


                                if (planId == 1)
                                {
                                    if (sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true"))
                                    {

//
//                                        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
                                    }
                                    else
                                    {

//                                        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
                                    }
                                }
                                else
                                {
                                    if (sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("false"))
                                    {
                                        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
//                                            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                            finish();

                                        }else {
//                                            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
//                                            finish();


//                                            Intent intent = new Intent(WelcomeActivity.this, Welcome_ScheduleBloodTestActivity.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            startActivity(intent);
                                        }

                                    }else {
//                                        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                        finish();
                                    }



                                }
                            }
                            else
                            {

                                if (sessionManager.getStringValue("showSnippetFristime").equalsIgnoreCase("true")){
                                    Intent homeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
                                    homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    homeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(homeActivity);
                                    finish();
                                }else {

                                    if (getIntent().getStringExtra("fromWeb")!=null&&getIntent().getStringExtra("fromWeb").equalsIgnoreCase("true")){
                                        Intent homeActivity = new Intent(WelcomeActivity.this, HomeActivity.class);
                                        homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        homeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(homeActivity);
                                        finish();
                                    }else {
                                        Intent intent=new Intent(context, SnipeetActivity.class);
                                        intent .putExtra("isFirstTime", true);
                                        intent.putExtra("isFromFirstHealth",true);

                                        startActivity(intent);
                                        sessionManager.setStringValue("showSnippetFristime","true");
                                        finish();
                                    }


                                }





                            }













                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });

    }



    private void callForUsrFreezStatus()
    {
        if (!((Activity) context).isFinishing())
        {
            try {
                utils.showProgressbar(context);


            }catch (Exception e){

            }
        }

        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userId);

        Call<UserStatusResponse> call = unfreezeService.getUserStatus(token, request);
        call.enqueue(new Callback<UserStatusResponse>()
        {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response)
            {



                utils.hideProgressbar();




                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    UserStatusResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        int status = listResponse.getData().getStatusId();
                        int planid = listResponse.getData().getPlanID();



                        sessionManager.setIntValue(SessionManager.KEY_USER_PLAN_ID, planid);

                        sessionManager.setStringValue("Trial",listResponse.getData().getIsTrail());

                        Log.d("Trial",listResponse.getData().getIsTrail());


                        if (listResponse.getData().getIsTrail().equalsIgnoreCase("expire")){
                            sessionManager.setStringValue("notallowed","true");


                            showDailogExpire();
                            return;




                        }else if (listResponse.getData().getIsTrail().equalsIgnoreCase("noSubscription")){
                            sessionManager.setStringValue("notallowed","true");

                            Intent  intent = new Intent(context, ViewPagerActivity.class);
                            intent.putExtra("param", "From_Home");
                            intent.putExtra("close","expired");
                            startActivity(intent);

                            finish();
                            return;
                        }else {
                            sessionManager.setStringValue("notallowed","false");

                        }

                        if (status == 1)
                        {
                            clearTask();
                            return;
                        }





                        callUserStatusApi();
                    }else {


                    }
                }
                else
                {
                    //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    Log.d("Error---->", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });
    }

    private void callPathoReecoachStatus()
    {
       try {
           if (!((Activity) context).isFinishing())
           {
               utils.showProgressbar(context);
           }
       }catch (Exception e){

       }

        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userId);

        Call<UserStatusResponse> call = unfreezeService.getUserStatus(token, request);
        call.enqueue(new Callback<UserStatusResponse>()
        {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {



                    UserStatusResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        pathoID=listResponse.getData().getPathologistId();
                        sessionManager.setStringValue("KEY_PATHO_ID", String.valueOf(pathoID));
                        sessionManager.setIntValue(SessionManager.KEY_USER_REECOACH_ID, listResponse.getData().getReecoachId());
                        sessionManager.setIntValue(SessionManager.KEY_USER_PATHO_ID, listResponse.getData().getPathologistId());

                        if (listResponse.getData().isReecoachRequire()){

                            sessionManager.setStringValue("KEY_ISSHOW_REECOACH","true");

                        }else {
                            sessionManager.setStringValue("KEY_ISSHOW_REECOACH","false");

                        }

                        if (listResponse.getData().isPathoRequire()){

                            isRequiredPAtho=true;
                            sessionManager.setStringValue("KEY_ISSHOW_PATHO","true");



                        }else {
                            isRequiredPAtho=false;
                            sessionManager.setStringValue("KEY_ISSHOW_PATHO","false");

                        }






                        if ((listResponse.getData().isPathoRequire())&&listResponse.getData().getPathologistId()==0||(listResponse.getData().isReecoachRequire())&&listResponse.getData().getReecoachId()==0){
//                            selectReecoachPathDialog(listResponse.getData());


                        }




                    }
                }
                else
                {
                    //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    Log.d("Error---->", response.message());


                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }

    private void showDailogExpire() {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_expired);
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
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent  intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_Home");
                intent.putExtra("close","expired");
                startActivity(intent);

                finish();
            }
        });

        dialog.show();

    }

    public void clearTask()
    {
        Intent intent = new Intent(this, UnfreezeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    public void selectReecoachPathDialog(UserStatusResponse.UserData data){
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_select_reecoach);
        dialog.setCancelable(false);
        ImageView close_dialg=dialog.findViewById(R.id.close_dialg);
        TextView txt_later=dialog.findViewById(R.id.txt_later);
        TextView txt_header_reeacoch_patho=dialog.findViewById(R.id.txt_header_reeacoch_patho);
        txt_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        close_dialg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_select_now=dialog.findViewById(R.id.btn_select_now);

        if (data.isReecoachRequire()&&data.getReecoachId()==0){
            btn_select_now.setVisibility(View.VISIBLE);
        }
        Button btn_select_patho=dialog.findViewById(R.id.btn_select_patho);

        if (data.isPathoRequire()&&data.getPathologistId()==0){
            btn_select_patho.setVisibility(View.VISIBLE);
        }




        if(data.isReecoachRequire()&&data.isPathoRequire()){
            txt_header_reeacoch_patho.setText("Select Reecoach and Pathologist");
        }else  if ((data.isReecoachRequire()&&data.getReecoachId()==0)&&(data.isPathoRequire()&&data.getPathologistId()==0)){
            txt_header_reeacoch_patho.setText("Select Pathologist");

        }else  if (data.isReecoachRequire()&&!data.isPathoRequire()){
            txt_header_reeacoch_patho.setText("Select Reecoach");

        }



        btn_select_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WelcomeActivity.this, SelectReecoachPathActivity.class));

            }
        });

        btn_select_patho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WelcomeActivity.this, ChangePathoActivity.class));

            }
        });
        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);


        dialog.show();

    }
}
