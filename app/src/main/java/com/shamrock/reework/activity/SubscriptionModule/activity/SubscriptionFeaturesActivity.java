package com.shamrock.reework.activity.SubscriptionModule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.SubscriptionModule.adapter.SubscribeFeatureAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.SubscriptionModule.service.SubscriptionService;
import com.shamrock.reework.api.response.SubscriptionFeaturesResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionFeaturesActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "SubsFeatureActivity";
    Context context;
    Toolbar toolbar;
    Typeface font;
    Button btnSubmitFeature;
    public static TextView tvFree, tvMember;

    ArrayList<HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList>> hashPlanPriceList;
    RecyclerView recyclerView;
    ArrayList<SubscriptionFeaturesResponse.SubData> subscriptionFeatureArrayList;
    ArrayList<SubscriptionFeaturesResponse.PlanHeadingList> planHeadingList;
    ArrayList<SubscriptionFeaturesResponse.FeatureList> featureList;
    SubscribeFeatureAdapter subscribeFeatureAdapter;

    SubscriptionService subscriptionService;
    private SessionManager session;
    Utils utils;

    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_features);
        context = SubscriptionFeaturesActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        subscriptionService = Client.getClient().create(SubscriptionService.class);
        session = new SessionManager(context);
        utils =new Utils();
        subscriptionFeatureArrayList = new ArrayList<>();
        hashPlanPriceList = new ArrayList<>();
        planHeadingList = new ArrayList<>();
        featureList = new ArrayList<>();
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Membership Plan");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        recyclerView = findViewById(R.id.recyclerview);
        tvFree = findViewById(R.id.tvFree);
        tvMember = findViewById(R.id.tvMember);
        btnSubmitFeature = findViewById(R.id.buttonSubmitSubscribe);
        btnSubmitFeature.setOnClickListener(this);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);

        /* SWIPE TO REFRESH */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (Utils.isNetworkAvailable(context))
                    callFeaturesApi(true);
                else
                    showLayouts();
            }
        });

        if (Utils.isNetworkAvailable(context))
            callFeaturesApi(false);
        else
            showLayouts();
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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonSubmitSubscribe:
                Intent intent = new Intent(context, SubscriptionPaymentActivity.class);
                intent.putExtra("HASH_PRICE_LIST", hashPlanPriceList);
                startActivity(intent);
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    callFeaturesApi(true);
                }
                else
                    showLayouts();

            default:
        }
    }

    private void callFeaturesApi(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        Call<SubscriptionFeaturesResponse> call = subscriptionService.getSubscriptiobFeaturesList();

        call.enqueue(new Callback<SubscriptionFeaturesResponse>()
        {
            @Override
            public void onResponse(Call<SubscriptionFeaturesResponse> call, Response<SubscriptionFeaturesResponse> response)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SubscriptionFeaturesResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        subscriptionFeatureArrayList = response.body().getData();

                        if (subscriptionFeatureArrayList!=null && subscriptionFeatureArrayList.size() > 0)
                        {
                            planHeadingList = subscriptionFeatureArrayList.get(0).getPlanHeadingList();
                            featureList = subscriptionFeatureArrayList.get(0).getFeatureList();

                            /* Saving list in Sharedprefrence */
                            session.saveSubscriptionFeaturesInPrefrence(context, featureList);

                            if (planHeadingList != null && planHeadingList.size() > 0)
                            {
                                HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList> hashMap = new HashMap<>();

                                for (int i=0; i<planHeadingList.size(); i++)
                                {
                                    SubscriptionFeaturesResponse.PlanHeadingList heading = planHeadingList.get(i);
                                    if (heading != null)
                                    {
                                        if (heading.getPlanID() == 1)//MEMBER USER
                                        {
                                            if (!TextUtils.isEmpty(heading.getPlanName()))
                                                SubscriptionFeaturesActivity.tvFree.setText(heading.getPlanName());
                                        }
                                        else if (heading.getPlanID() == 2)//FREE USER
                                        {
                                            if (!TextUtils.isEmpty(heading.getPlanName()))
                                                SubscriptionFeaturesActivity.tvMember.setText(heading.getPlanName());
                                        }

                                        hashMap.put(heading.getPlanID(), heading);
                                        hashPlanPriceList.add(hashMap);
                                    }
                                }
                            }

                            subscribeFeatureAdapter = new SubscribeFeatureAdapter(context, featureList);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(subscribeFeatureAdapter);
                        }
                    }
                    else
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SubscriptionFeaturesResponse> call, Throwable t)
            {
                Log.e("ERROR---->", t.toString());
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
                            callFeaturesApi(true);
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }


}
