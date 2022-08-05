package com.shamrock.reework.reecoachmodule.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.SingleChatModule.adapter.SingleChatAdapter;
import com.shamrock.reework.activity.SingleChatModule.service.ChatService;
import com.shamrock.reework.activity.SingleChatModule.service.SingleChatModel;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.SaveSingleChatRequest;
import com.shamrock.reework.api.request.SingleChatRequest;
import com.shamrock.reework.api.response.SingleChatResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnSend;
    private EditText inputMsg;
    String lsMessege;
    ChatService chatService;

    int  recoachId;
    ViewFlipper vp_reecoach;
    boolean isFalse=false;


    // Chat messages list adapter
    private ArrayList<SingleChatResponse.ChatList> chatList;
    private SingleChatAdapter chatAdapter;
    private RecyclerView recyclerView_chat;
    SingleChatModel model;
    CountDownTimer countDownTimer;
    int object_size;
    private SessionManager sessionManager;
    private ReecoachService reecoachService;
    private Utils utils;
    private int userId;
    private Toolbar toolbar;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        sessionManager = new SessionManager(ChatDetailsActivity.this);
        reecoachService = Client.getClient().create(ReecoachService.class);


        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);

        if (getIntent().getStringExtra("NAME")!=null){
            tvTitle.setText(getIntent().getStringExtra("NAME"));

        }
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        initchat();

        findViewschat();

        refreshChat();

    }

    private void refreshChat() {
        countDownTimer=  new CountDownTimer(150000, 4000) {
            @Override
            public void onTick(long millisUntilFinished) {

                GetAllChatRefresh();
            }

            @Override
            public void onFinish() {


            }
        }.start();


    }

    private void initchat()
    {
        chatService = Client.getClient().create(ChatService.class);
        utils = new Utils();
        sessionManager = new SessionManager(ChatDetailsActivity.this);

        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
             userId = Integer.parseInt(getIntent().getStringExtra("ID"));

    }



    private void findViewschat()
    {
        btnSend =  findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.inputMsg);
        recyclerView_chat = findViewById(R.id.recyclerView_chat);


        btnSend.setOnClickListener(this);
//        btnRefresh.setOnClickListener(this);

        chatList = new ArrayList<>();
        chatAdapter = new SingleChatAdapter(this, chatList, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_chat.setLayoutManager(layoutManager);
        recyclerView_chat.setItemAnimator(new DefaultItemAnimator());
        recyclerView_chat.setAdapter(chatAdapter);

        if (Utils.isNetworkAvailable(ChatDetailsActivity.this))
            GetAllChat(false);

        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();

        /* SWIPE TO REFRESH */
//        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//                if (Utils.isNetworkAvailable(ChatDetailsActivity.this))
//                {
//                    GetAllChat(true);
//                }
//                else
//                    Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();
//            }
//        });
    }
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btnSend) {
            if (Utils.isNetworkAvailable(ChatDetailsActivity.this)) {

                    lsMessege = inputMsg.getText().toString();

                    if (!TextUtils.isEmpty(lsMessege)) {

                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        if (Utils.isNetworkAvailable(ChatDetailsActivity.this)) {
                            CallToSendMessege();
                            inputMsg.setText("");
                        } else {

                        }
                    } else
                        Toast.makeText(ChatDetailsActivity.this, "Type something to send", Toast.LENGTH_SHORT).show();

            } else {
            }
        }
    }
    private void CallToSendMessege()
    {


        SaveSingleChatRequest request = new SaveSingleChatRequest();
        request.setFromUserID(recoachId);
        request.setToUserID(userId);
        request.setMessage(lsMessege);

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
//                            chatList.clear();
//                            chatList.addAll(tempChatList);
//                            chatAdapter.notifyDataSetChanged();
//                            recyclerView_chat.smoothScrollToPosition(chatList.size()-1);
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
    private void GetAllChat(final boolean isSwipeToRefresh)
    {
        utils.showProgressbar(ChatDetailsActivity.this);

        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);

        Call<SingleChatResponse> call = chatService.getChatHistory(recoachId,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {
                utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();
                            recyclerView_chat.smoothScrollToPosition(chatList.size()-1);


                        }
                    }
                    else
                        Toast.makeText(ChatDetailsActivity.this, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                utils.hideProgressbar();


            }
        });
    }
    private void GetAllChatRefresh()
    {


        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);

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
                                recyclerView_chat.smoothScrollToPosition(tempChatList.size()-1);

                            }

                            object_size=tempChatList.size();
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();


                        }
                    }
                    else
                        Toast.makeText(ChatDetailsActivity.this, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!false)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

}
