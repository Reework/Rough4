package com.shamrock.reework.activity.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.SingleChatModule.adapter.SingleChatAdapter;
import com.shamrock.reework.activity.SingleChatModule.service.ChatService;
import com.shamrock.reework.activity.SingleChatModule.service.SingleChatModel;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.SaveSingleChatRequest;
import com.shamrock.reework.api.request.SingleChatRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.SingleChatResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityActivityNew extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnSend,btnSend12;
    private EditText inputMsg;
    String lsMessege,lsMessege12;
    ChatService chatService;
    private ArrayList<SingleChatResponse.ChatList> chatList_admin;

    int  recoachId;
    ViewFlipper vp_reecoach;
    boolean isFalse=false;
    boolean isReecoach=true;


    // Chat messages list adapter
    private ArrayList<GetCommunityData> chatList;
    private CommunityChatAdapter chatAdapter;
    private RecyclerView recyclerView_chat;
    SingleChatModel model;
    CountDownTimer countDownTimer;
    int object_size;
    int object_size_admin;
    private SessionManager sessionManager;
    private Context context;
    private int userId;
    Utils utils;
    TextView txt_tem_msg;
    LinearLayout ll_tem_chat;
    TextView txt_tem_time;
    RecyclerView recyclerView_chat_admin;
    int reecoach_pos;
    int admin_pos;

    Button btn_cummunity,btn_family,btn_reecoach,btn_admin;
    LinearLayout layout_community,layout_family,layout_recoach,layout_admin;


    private EditText inputMsg12;
    private ArrayList<SingleChatResponse.ChatList> chatList12;
    private ArrayList<SingleChatResponse.ChatList> chatList12_admin;
    private SingleChatAdapter chatAdapter12;
    private SingleChatAdapter chatAdapter12_admin;
    private RecyclerView recyclerView_chat12;
    ImageView btnSend_admin;
    LinearLayout llMsgCompose_admin;
    EditText inputMsg_admin;
    private String lsMessege_admin="";

    Toolbar toolbar;
    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_new);
        context=CommunityActivityNew.this;
        sessionManager = new SessionManager(context);
        chatService = Client.getClient().create(ChatService.class);
        recyclerView_chat_admin=findViewById(R.id.recyclerView_chat_admin);
        btnSend_admin=findViewById(R.id.btnSend_admin);
        btnSend12 =  findViewById(R.id.btnSend12);
        inputMsg12 = (EditText) findViewById(R.id.inputMsg12);
        inputMsg_admin = (EditText) findViewById(R.id.inputMsg_admin);
        recyclerView_chat12 = findViewById(R.id.recyclerView_chat12);


        // Configure the refreshing colors

        chatList12 = new ArrayList<>();
        chatList12_admin = new ArrayList<>();
        chatAdapter12 = new SingleChatAdapter(this, chatList12,false);
        chatAdapter12_admin = new SingleChatAdapter(this, chatList12_admin,true);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_chat12.setLayoutManager(layoutManager);
        recyclerView_chat12.setItemAnimator(new DefaultItemAnimator());
        recyclerView_chat12.setAdapter(chatAdapter12);

        RecyclerView.LayoutManager layoutManage1 = new LinearLayoutManager(this);
        recyclerView_chat_admin.setLayoutManager(layoutManage1);
        recyclerView_chat_admin.setItemAnimator(new DefaultItemAnimator());
        recyclerView_chat_admin.setAdapter(chatAdapter12_admin);


        btnSend_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(context))
                {
                    if (recoachId > 0)
                    {
                        lsMessege_admin = inputMsg_admin.getText().toString();

                        if (!TextUtils.isEmpty(lsMessege_admin))
                        {

                            try {
                                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            if (Utils.isNetworkAvailable(context))
                            {
//                                showLayouts();
                                CallToSendAdminMessegeReecoach();
                                inputMsg_admin.setText("");
                            }
                            else
                                showLayouts();
                        }
                        else
                            Toast.makeText(context, "Type something to send", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    showLayouts();
            }
        });
        ll_tem_chat=findViewById(R.id.ll_tem_chat);
        txt_tem_msg=findViewById(R.id.txt_tem_msg);
        txt_tem_time=findViewById(R.id.txt_tem_time);

        btn_cummunity=findViewById(R.id.btn_cummunity);
        btn_family=findViewById(R.id.btn_family);
        btn_reecoach=findViewById(R.id.btn_reecoach);
        btn_admin=findViewById(R.id.btn_admin);

        layout_community=findViewById(R.id.layout_community);
        layout_family=findViewById(R.id.layout_family);
        layout_recoach=findViewById(R.id.layout_recoach);
        layout_admin=findViewById(R.id.layout_admin);






        utils=new Utils();
        findViewschat();
        initchat();

        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("REEchat");
        ImageView imageView=findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        countDownTimer=  new CountDownTimer(150000, 4000) {
            @Override
            public void onTick(long millisUntilFinished) {

//                GetAllRefreshChat(false);
            }

            @Override
            public void onFinish() {


            }
        }.start();



        btn_cummunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                }else {


                    layout_community.setVisibility(View.VISIBLE);
                    layout_family.setVisibility(View.GONE);
                    layout_recoach.setVisibility(View.GONE);
                    layout_admin.setVisibility(View.GONE);

//                txt_nut.setBackground(getResources().getDrawable(R.drawable.round_recipe_button));
//                txt_ing.setBackground(getResources().getDrawable(R.drawable.round_recipe_button_white));
//                txt_rec.setBackground(getResources().getDrawable(R.drawable.round_recipe_button_white));

                    btn_cummunity.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                    btn_family.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_reecoach.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_admin.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                    btn_cummunity.setTextColor(getResources().getColor(R.color.white_recipe));
                    btn_family.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_admin.setTextColor(getResources().getColor(R.color.black_recipe));

                    btn_reecoach.setTextColor(getResources().getColor(R.color.black_recipe));

                }

            }
        });

        btn_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_community.setVisibility(View.GONE);
                layout_family.setVisibility(View.VISIBLE);
                layout_recoach.setVisibility(View.GONE);
                layout_admin.setVisibility(View.GONE);

                btn_cummunity.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_family.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_reecoach.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_admin.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_cummunity.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_family.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_reecoach.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_admin.setTextColor(getResources().getColor(R.color.black_recipe));

            }
        });

        btn_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReecoach=true;
                layout_community.setVisibility(View.GONE);
                layout_family.setVisibility(View.GONE);
                layout_recoach.setVisibility(View.VISIBLE);
                layout_admin.setVisibility(View.GONE);


                btn_cummunity.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_family.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reecoach.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_admin.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_cummunity.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_family.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reecoach.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_admin.setTextColor(getResources().getColor(R.color.black_recipe));



                findReecoachId();




                GetAllChatRefreshReecoach(false);




            }
        });


        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                layout_community.setVisibility(View.GONE);
//                layout_family.setVisibility(View.GONE);
//                layout_recoach.setVisibility(View.GONE);
//                layout_admin.setVisibility(View.VISIBLE);
//
//
//                btn_cummunity.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                btn_family.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                btn_reecoach.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                btn_admin.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//
//                btn_cummunity.setTextColor(getResources().getColor(R.color.black_recipe));
//                btn_family.setTextColor(getResources().getColor(R.color.black_recipe));
//                btn_reecoach.setTextColor(getResources().getColor(R.color.black_recipe));
//                btn_admin.setTextColor(getResources().getColor(R.color.white_recipe));
//
//
//
//                findReecoachId();
//
//
//
//
////                GetAllChatRefreshReecoach(false);



                layout_community.setVisibility(View.GONE);
                layout_family.setVisibility(View.GONE);
                layout_recoach.setVisibility(View.GONE);
                layout_admin.setVisibility(View.VISIBLE);


                btn_cummunity.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_family.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_admin.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_reecoach.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_cummunity.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_family.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reecoach.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_admin.setTextColor(getResources().getColor(R.color.white));




                GetAllChatAdmin(false);



            }
        });







        countDownTimer=  new CountDownTimer(150000, 4000) {
            @Override
            public void onTick(long millisUntilFinished) {

//                GetAllRefreshChat(false);
//                GetAllChatRefreshReecoach(false);
//                GetAllChatRefreshAdmin(false);
            }

            @Override
            public void onFinish() {


            }
        }.start();


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
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEchat");

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
    private  void findReecoachId(){


        btnSend12.setOnClickListener(this);

//         GetAllChatAdmin(false);
        if (Utils.isNetworkAvailable(context))
            GetAllChatReecoach(false);

        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();



        /* SWIPE TO REFRESH */









    }


    private void GetAllChatReecoach(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);


        Call<SingleChatResponse> call = chatService.getAllChat(recoachId,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {

                if (!isSwipeToRefresh)
                    utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList12.clear();
                            chatList12.addAll(tempChatList);
                            chatAdapter12.notifyDataSetChanged();
                            recyclerView_chat12.smoothScrollToPosition(chatList12.size()-1);

                            sessionManager.setStringValue("reecoach",appointmentResponse.getSenderProfileImg());

                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    private void GetAllChatAdmin(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);
        recoachId=1;

        Call<SingleChatResponse> call = chatService.getAllChat(recoachId,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {

                if (!isSwipeToRefresh)
                    utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            if (chatList12_admin!=null){
                                chatList12_admin.clear();

                            }
                            sessionManager.setStringValue("admin",appointmentResponse.getSenderProfileImg());

                            chatList12_admin.addAll(tempChatList);
                            chatAdapter12_admin.notifyDataSetChanged();


                            recyclerView_chat_admin.smoothScrollToPosition(chatList12_admin.size()-1);


                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    private void GetAllChatRefreshReecoach(final boolean isSwipeToRefresh) {


        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);
        if (!isReecoach){
            recoachId=1;
        }else {
            recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        }

        Call<SingleChatResponse> call = chatService.getAllChat(recoachId,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {




                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            if (object_size<tempChatList.size()){
                                recyclerView_chat12.smoothScrollToPosition(tempChatList.size()-1);

                            }

                            object_size=tempChatList.size();
                            chatList12.clear();
                            chatList12.addAll(tempChatList);
                            chatAdapter12.notifyDataSetChanged();


                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    private void GetAllChatRefreshAdmin(final boolean isSwipeToRefresh) {


        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);
        recoachId=1;


        Call<SingleChatResponse> call = chatService.getAllChat(recoachId,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {




                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            if (object_size_admin<tempChatList.size()){
                                recyclerView_chat_admin.smoothScrollToPosition(tempChatList.size()-1);

                            }

                            object_size_admin=tempChatList.size();
                            chatList12_admin.clear();
                            chatList12_admin.addAll(tempChatList);
                            chatAdapter12_admin.notifyDataSetChanged();


                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }


    private void CallToSendMessegeReecoach()
    {
        if (!((Activity) context).isFinishing())
        {
//            utils.showProgressbar(context);
        }

        SaveSingleChatRequest request = new SaveSingleChatRequest();
        request.setFromUserID(userId);
        if (isReecoach){
            request.setToUserID(recoachId);

        }else {
            request.setToUserID(0);

        }
        request.setMessage(lsMessege12);

        Call<SingleChatResponse> call = chatService.saveChat(request);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse body = response.body();

                    if (body != null && body.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = body.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList12.clear();
                            chatList12.addAll(tempChatList);
                            chatAdapter12.notifyDataSetChanged();
                            recyclerView_chat12.smoothScrollToPosition(chatList12.size()-1);
                        }
                        else
                            Snackbar.make(findViewById(android.R.id.content), "No chat history found !",
                                    Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), body.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }

    private void CallToSendAdminMessegeReecoach()
    {
        if (!((Activity) context).isFinishing())
        {
//            utils.showProgressbar(context);
        }

        SaveSingleChatRequest request = new SaveSingleChatRequest();
        request.setFromUserID(userId);
        request.setToUserID(1);

        request.setMessage(lsMessege_admin);

        Call<SingleChatResponse> call = chatService.saveChat(request);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse body = response.body();

                    if (body != null && body.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = body.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList12_admin.clear();
                            chatList12_admin.addAll(tempChatList);
                            chatAdapter12_admin.notifyDataSetChanged();
                            recyclerView_chat_admin.smoothScrollToPosition(chatList12_admin.size()-1);
                        }
                        else
                            Snackbar.make(findViewById(android.R.id.content), "No chat history found !",
                                    Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), body.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                utils.hideProgressbar();
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
//                            callToGetAllAppoinments(false);
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
//            swipeRefreshLayout.setVisibility(View.GONE);
//            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
//            noInternetLayout.setVisibility(View.GONE);
        }
    }


    //Reecoach Chat
    private void initchat()
    {
//        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
    }



    private void findViewschat()
    {
        btnSend =  findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.inputMsg);
        recyclerView_chat = findViewById(R.id.recyclerView_chat);


        btnSend.setOnClickListener(this);
//        btnRefresh.setOnClickListener(this);

        chatList = new ArrayList<>();
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        chatAdapter = new CommunityChatAdapter(this, chatList,userId);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_chat.setLayoutManager(layoutManager);
        recyclerView_chat.setItemAnimator(new DefaultItemAnimator());
        recyclerView_chat.setAdapter(chatAdapter);

        if (Utils.isNetworkAvailable(context))
            GetAllChat(false);

        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();

        /* SWIPE TO REFRESH */

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

                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSend:


                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }

                if (Utils.isNetworkAvailable(context))
                {
                    lsMessege = inputMsg.getText().toString();

                    if (!TextUtils.isEmpty(lsMessege))
                    {

                        try {
                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        if (Utils.isNetworkAvailable(context))
                        {
                            ll_tem_chat.setVisibility(View.VISIBLE);
                            String currentDate = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                            txt_tem_time.setText(currentDate);

                            txt_tem_msg.setText(lsMessege);
                            CallToSendMessege();
                            inputMsg.setText("");
                        }
                        else{

                        }
                    }
                    else
                        Toast.makeText(context, "Type something to send", Toast.LENGTH_SHORT).show();
                }
                else
                {

                }
                break;



            case R.id.btnSend12:

                if (Utils.isNetworkAvailable(context))
                {
                    if (recoachId > 0)
                    {
                        lsMessege12 = inputMsg12.getText().toString();

                        if (!TextUtils.isEmpty(lsMessege12))
                        {

                            try {
                                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            if (Utils.isNetworkAvailable(context))
                            {
                                showLayouts();
                                CallToSendMessegeReecoach();
                                inputMsg12.setText("");
                            }
                            else
                                showLayouts();
                        }
                        else
                            Toast.makeText(context, "Type something to send", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    showLayouts();
                break;



        }

    }

    private void CallToSendMessege()
    {
        if (!((Activity) context).isFinishing())
        {
//            utils.showProgressbar(context);
        }
        SaveSingleChatRequest request = new SaveSingleChatRequest();
        request.setFromUserID(userId);
        request.setToUserID(recoachId);
        request.setMessage(lsMessege);

        Call<ChatData> call = chatService.setchatApi(userId,lsMessege);
        call.enqueue(new Callback<ChatData>()
        {
            @Override
            public void onResponse(Call<ChatData> call, Response<ChatData> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ChatData body = response.body();

                    if (body != null && body.getCode() == 1)
                    {


                        if (body != null )
                        {
//                            Toast.makeText(CommuniyActivity.this, ""+body.getData(), Toast.LENGTH_SHORT).show();
                        }
                        else
                            Snackbar.make(findViewById(android.R.id.content), "No chat history found !",
                                    Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
//                        Snackbar.make(findViewById(android.R.id.content), body.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatData> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }
    private void GetAllChat(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);

        Call<ClsGetCommunityChatMain> call = chatService.getChatCommunityMesage();
        call.enqueue(new Callback<ClsGetCommunityChatMain>()
        {
            @Override
            public void onResponse(Call<ClsGetCommunityChatMain> call, Response<ClsGetCommunityChatMain> response)
            {

                ll_tem_chat.setVisibility(View.GONE);
                txt_tem_msg.setText("");

                if (!isSwipeToRefresh)
                    utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsGetCommunityChatMain appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 0)
                    {
                        ArrayList<GetCommunityData>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();
                            recyclerView_chat.smoothScrollToPosition(chatList.size()-1);


                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMesage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ClsGetCommunityChatMain> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }


    private void GetAllRefreshChat(final boolean isSwipeToRefresh)
    {


        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);

        Call<ClsGetCommunityChatMain> call = chatService.getChatCommunityMesage();
        call.enqueue(new Callback<ClsGetCommunityChatMain>()
        {
            @Override
            public void onResponse(Call<ClsGetCommunityChatMain> call, Response<ClsGetCommunityChatMain> response)
            {
                ll_tem_chat.setVisibility(View.GONE);
                txt_tem_msg.setText("");
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsGetCommunityChatMain appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 0)
                    {
                        ll_tem_chat.setVisibility(View.GONE);
                        txt_tem_msg.setText("");
                        ArrayList<GetCommunityData>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            if (object_size<tempChatList.size()){
                                recyclerView_chat.smoothScrollToPosition(tempChatList.size()-1);

                            }

                            object_size=tempChatList.size();
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();

                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMesage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ClsGetCommunityChatMain> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }


    //admin


    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userId);

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
