package com.shamrock.reework.activity.spirituallibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.spirituallibrary.adapter.SpiritualTypeCategoryAdapter;
import com.shamrock.reework.activity.spirituallibrary.adapter.SpiritualVideoListAdapter;
import com.shamrock.reework.activity.spirituallibrary.listenres.OnSpiritualTypeCLick;
import com.shamrock.reework.activity.spirituallibrary.listenres.OnVideoCLick;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritaulTypeMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.SpiritualTypeData;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpiritualLibraryActivity extends AppCompatActivity implements OnVideoCLick, OnSpiritualTypeCLick {
    private RecyclerView recylcer_spiritual_category;
    ArrayList<ClsSpiritualData>  arylst_spirituallist=new ArrayList<>();
    private RecyclerView recylcer_spiritual_list;
    private CommonService commonService;
    private Utils utils;
    private SessionManager session;
    TextView txt_no_data_spiritual;
    private Context context;
    Toolbar toolbar;
    private int userID;
    private SessionManager sessionManager;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiritual_library);
        context = SpiritualLibraryActivity.this;
        recylcer_spiritual_category=findViewById(R.id.recylcer_spiritual_category);
        recylcer_spiritual_list=findViewById(R.id.recylcer_spiritual_list);
        txt_no_data_spiritual=findViewById(R.id.txt_no_data_spiritual);
//        TextView tvTitle=findViewById(R.id.tvTitle);
//        tvTitle.setText("Wellness Library");
//        ImageView tvTitles=findViewById(R.id.imgLeft);
//        tvTitles.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//
//            }
//        });
        sessionManager = new SessionManager(context);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        callSpiritualTypeApi();



        notificationService = Client.getClient().create(NotificationService.class);
        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
//                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
//                        PushFcmToServer();
                    } else if (intent.getAction().equals(FcmConstants.PUSH_NOTIFICATION)) // new push notification is received
                    {
                        String title = intent.getStringExtra(FcmConstants.FCM_TITLE);
                        String message = intent.getStringExtra(FcmConstants.FCM_MESSEGE);
                        mNotifcationCount = intent.getIntExtra(FcmConstants.FCM_COUNT, 0);

                        if (mNotifcationCount > 0) {
                            tvNotificationCOunt.setText(String.valueOf(mNotifcationCount));
                            invalidateOptionsMenu();
                        } else {
                            if (Utils.isNetworkAvailable(context))
                                GetAllNotificationCount();
                            else
                                Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();


    }

    private void setToolBar()
    {

        TextView tvTitle=findViewById(R.id.tvTitle);

        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Wellness Library");

        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        counterValuePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(), NotificationsActivity.class), 500);

            }
        });



        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }
    }

    private void callSpiritualTypeApi() {

        commonService = Client.getClient().create(CommonService.class);
        getSpiritaulTypeApi();
    }

    @Override
    public void getVideoLink(String videoLink, String title, String url) {



        Intent intent=new Intent(SpiritualLibraryActivity.this, ExoActivity.class);
        intent.putExtra("VideoID",extractYTId(videoLink));
        intent.putExtra("title",title);
        intent.putExtra("description",url);
        startActivity(intent);




    }
    public String extractYTId(String url) {


        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        return null;

    }
    public String youTubeLinkWithoutProtocolAndDomain(String url) {
        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
    }

    private void getSpiritaulTypeApi(){


        utils = new Utils();
        session = new SessionManager(this);
        try {
            utils.showProgressbar(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritaulTypeMain> call = commonService.getSpiritualLibraryMaster();
        call.enqueue(new Callback<ClsSpiritaulTypeMain>()
        {
            @Override
            public void onResponse(Call<ClsSpiritaulTypeMain> call, retrofit2.Response<ClsSpiritaulTypeMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsSpiritaulTypeMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){

                                    ArrayList<SpiritualTypeData> arylst_SpiritualTypeData=moodResponse.getData();

                                    recylcer_spiritual_category.setAdapter(new SpiritualTypeCategoryAdapter(SpiritualLibraryActivity.this,arylst_SpiritualTypeData));

                                    getSpitualListAPiByID(arylst_SpiritualTypeData.get(0).getId(),arylst_SpiritualTypeData.get(0).getLibraryName());

                                }else {
                                    ArrayList<SpiritualTypeData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_category.setAdapter(new SpiritualTypeCategoryAdapter(SpiritualLibraryActivity.this,arylst_SpiritualTypeData));

//                                    getSpitualListAPiByID(arylst_SpiritualTypeData.get(0).getId());
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
            public void onFailure(Call<ClsSpiritaulTypeMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }



    private void getSpitualListAPiByID(int id, final String libraryName){


        utils = new Utils();
        session = new SessionManager(this);
        try {
            utils.showProgressbar(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritualListMain> call = commonService.getSpiritualLibraryVideosById(id);
        call.enqueue(new Callback<ClsSpiritualListMain>()
        {
            @Override
            public void onResponse(Call<ClsSpiritualListMain> call, retrofit2.Response<ClsSpiritualListMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsSpiritualListMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){
                                    txt_no_data_spiritual.setVisibility(View.GONE);
                                    recylcer_spiritual_list.setVisibility(View.VISIBLE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=moodResponse.getData();

                                    recylcer_spiritual_list.setAdapter(new SpiritualVideoListAdapter(SpiritualLibraryActivity.this,arylst_SpiritualTypeData));


                                }else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName+" not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new SpiritualVideoListAdapter(SpiritualLibraryActivity.this,arylst_SpiritualTypeData));

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
            public void onFailure(Call<ClsSpiritualListMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }



    @Override
    public void getId(int id, String libraryName) {


        getSpitualListAPiByID(id, libraryName);



    }


    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

        Call<NotifCountResponse> call = notificationService.getAllNotificationCount(request);

        call.enqueue(new Callback<NotifCountResponse>() {
            @Override
            public void onResponse(Call<NotifCountResponse> call, Response<NotifCountResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    NotifCountResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mNotifcationCount = listResponse.getData();

                        invalidateOptionsMenu();
                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifCountResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }




}
