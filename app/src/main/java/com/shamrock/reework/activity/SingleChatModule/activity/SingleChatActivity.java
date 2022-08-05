package com.shamrock.reework.activity.SingleChatModule.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
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

public class SingleChatActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnSend;
    private EditText inputMsg;
    String lsMessege;
    private Toolbar toolbar;
    Context context;
    ChatService chatService;
    Utils utils;
    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;

    int userId, recoachId;

    View noInternetLayout;
    Button btnRefresh;

    // Chat messages list adapter
    private ArrayList<SingleChatResponse.ChatList> chatList;
    private SingleChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    SingleChatModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        context = SingleChatActivity.this;
        init();
        setToolBar();
        findViews();





    }

    private void init()
    {
        chatService = Client.getClient().create(ChatService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Chat with your Reecoach");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void findViews()
    {
        btnSend = findViewById(R.id.btnSend);
        inputMsg = findViewById(R.id.inputMsg);
        recyclerView = findViewById(R.id.recyclerView);

        noInternetLayout =  findViewById(R.id.no_internet);
        btnRefresh = findViewById(R.id.btnRefresh);

        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);

        btnSend.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);

        chatList = new ArrayList<>();
        chatAdapter = new SingleChatAdapter(this, chatList, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);

        if (Utils.isNetworkAvailable(context))

            GetAllChat(false);
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();

        /* SWIPE TO REFRESH */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
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

    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
            swipeRefreshLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSend:

                if (Utils.isNetworkAvailable(context))
                {
                    if (recoachId > 0)
                    {
                        lsMessege = inputMsg.getText().toString();

                        if (!TextUtils.isEmpty(lsMessege))
                        {
                            if (Utils.isNetworkAvailable(context))
                            {
                                showLayouts();
                                CallToSendMessege();
                                inputMsg.setText("");
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

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    GetAllChat(false);
                }
                else
                    showLayouts();
                break;
        }
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

        Call<SingleChatResponse> call = chatService.getAllChat(recoachId,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {

                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

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
                            recyclerView.smoothScrollToPosition(chatList.size()-1);
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

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                ShowRetryBar("" );
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
                            GetAllChat(false);
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    private void CallToSendMessege()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        SaveSingleChatRequest request = new SaveSingleChatRequest();
        request.setFromUserID(userId);
        request.setToUserID(recoachId);
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
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(chatList.size()-1);
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
}
