package com.shamrock.reework.activity.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.SingleChatModule.adapter.SingleChatAdapter;
import com.shamrock.reework.activity.SingleChatModule.service.ChatService;
import com.shamrock.reework.activity.SingleChatModule.service.SingleChatModel;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.SaveSingleChatRequest;
import com.shamrock.reework.api.request.SingleChatRequest;
import com.shamrock.reework.api.response.SingleChatResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommuniyActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnSend;
    private EditText inputMsg;
    String lsMessege;
    ChatService chatService;
    SwipeRefreshLayout swipeRefreshLayout1;

    int  recoachId;
    ViewFlipper vp_reecoach;
    boolean isFalse=false;


    // Chat messages list adapter
    private ArrayList<GetCommunityData> chatList;
    private CommunityChatAdapter chatAdapter;
    private RecyclerView recyclerView_chat;
    SingleChatModel model;
    CountDownTimer countDownTimer;
    int object_size;
    private SessionManager sessionManager;
    private Context context;
    private int userId;
    Utils utils;
    TextView txt_tem_msg;
    LinearLayout ll_tem_chat;
    TextView txt_tem_time;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        countDownTimer=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communiy);
        context=CommuniyActivity.this;
        sessionManager = new SessionManager(context);
        chatService = Client.getClient().create(ChatService.class);
        ll_tem_chat=findViewById(R.id.ll_tem_chat);
        txt_tem_msg=findViewById(R.id.txt_tem_msg);
        txt_tem_time=findViewById(R.id.txt_tem_time);


        utils=new Utils();
        findViewschat();
        initchat();

        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Community");
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

                GetAllRefreshChat(false);
            }

            @Override
            public void onFinish() {


            }
        }.start();
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

        swipeRefreshLayout1 = findViewById(R.id.swipeContainer1);
        // Configure the refreshing colors
        swipeRefreshLayout1.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);

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
        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (Utils.isNetworkAvailable(context))
                {
                    GetAllChat(true);
                }
                else
                    Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();
            }
        });
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

                if (swipeRefreshLayout1.isRefreshing())
                    swipeRefreshLayout1.setRefreshing(false);

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

                if (swipeRefreshLayout1.isRefreshing())
                    swipeRefreshLayout1.setRefreshing(false);

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


}
