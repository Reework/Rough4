package com.shamrock.reework.activity.CheatPlanModule.activity;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.CheatPlanModule.dialog.CheatPlanDialog;
import com.shamrock.reework.activity.CheatPlanModule.dialog.OccasionDialog;
import com.shamrock.reework.activity.CheatPlanModule.dialog.WishToEatDialog;
import com.shamrock.reework.activity.CheatPlanModule.model.request.WishNCheatMealRequest;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.CheatPlanResponse;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.OccasionResponce;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.WishToEatResponse;
import com.shamrock.reework.activity.CheatPlanModule.service.CheatMealService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheatPlanActivity extends AppCompatActivity implements View.OnClickListener, OccasionDialog.GetOcaasionListener,
        WishToEatDialog.GetWishToEatListener, CheatPlanDialog.GetCheatPlanListener {

    TextView tvOccasion,tvWishToEat,tvCheatPlan;
    ImageView imgView_Back_arrow;
    private OccasionDialog occasionDialog;
    private WishToEatDialog wishToEatDialog;
    private  CheatPlanDialog cheatPlanDialog;
    Context mContext;
    Utils utils;
    SessionManager sessionManager;
    CheatMealService cheatMealService;
    ArrayList<OccasionResponce.Datum> arrayListOccassion = new ArrayList<>();
    ArrayList<WishToEatResponse.Datum> arrayListWishToEat = new ArrayList<>();
    ArrayList<CheatPlanResponse.Datum> arrayListCheatPlan = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat_plan);
        initViews();
    }

    public void initViews(){
        mContext = CheatPlanActivity.this;
        utils = new Utils();
        sessionManager = new SessionManager(mContext);

        tvOccasion = (TextView)findViewById(R.id.tvOccasion);
        tvWishToEat = (TextView)findViewById(R.id.tvWishToEat);
        tvCheatPlan = (TextView)findViewById(R.id.tvCheatPlan);
        imgView_Back_arrow  = (ImageView)findViewById(R.id.imgView_Back_arrow);
        cheatMealService = Client.getClient().create(CheatMealService.class);

        if (Utils.isNetworkAvailable(mContext))
        {
                getOccassion();
            }
        else{
            Toast.makeText(getApplicationContext(),"Check internet connection!",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.tvOccasion:
                fm = getSupportFragmentManager();
                occasionDialog = new OccasionDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arrayListOccassion);
                occasionDialog.setArguments(bundle);
                occasionDialog.show(fm, "COUNTRY_FRAGMENT");
                break;

            case R.id.tvWishToEat:
                fm = getSupportFragmentManager();
                wishToEatDialog = new WishToEatDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arrayListWishToEat);
                wishToEatDialog.setArguments(bundle);
                wishToEatDialog.show(fm, "COUNTRY_FRAGMENT");
                break;

            case R.id.tvCheatPlan:
                fm = getSupportFragmentManager();
                cheatPlanDialog = new CheatPlanDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arrayListCheatPlan);
                cheatPlanDialog.setArguments(bundle);
                cheatPlanDialog.show(fm, "COUNTRY_FRAGMENT");
                break;

            case  R.id.imgView_Back_arrow:
                finish();
                break;
        }

    }

    //Calling Occassion API
    private void getOccassion()

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }
        Call<OccasionResponce> call = cheatMealService.getOccassion();
        call.enqueue(new Callback<OccasionResponce>()
        {
            @Override
            public void onResponse(Call<OccasionResponce> call, Response<OccasionResponce> response)
            {
                utils.hideProgressbar();

                List<OccasionResponce.Datum> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    OccasionResponce listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        tempList = response.body().getData();

                        if (tempList!= null && tempList.size() > 0)
                        {
                            arrayListOccassion.clear();
                            arrayListOccassion.addAll(tempList);
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OccasionResponce> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    //calling WishToEat API
    private void getWishToEatList(int occasionId)

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        WishNCheatMealRequest request = new WishNCheatMealRequest();
        request.setOccasionId(occasionId);

        Call<WishToEatResponse> call = cheatMealService.getWishToEatList(request);
        call.enqueue(new Callback<WishToEatResponse>()
        {
            @Override
            public void onResponse(Call<WishToEatResponse> call, Response<WishToEatResponse> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    WishToEatResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<WishToEatResponse.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {
                            arrayListWishToEat.clear();
                            arrayListWishToEat.addAll(data);

                        }
                    }
                    else
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<WishToEatResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    //Calling Cheat Meal List API
    //calling WishToEat API
    private void getCheatMealList(int wishToEatID)

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        WishNCheatMealRequest request = new WishNCheatMealRequest();
        request.setWishToEatId(wishToEatID);

        Call<CheatPlanResponse> call = cheatMealService.getCheatPlanList(request);
        call.enqueue(new Callback<CheatPlanResponse>()
        {
            @Override
            public void onResponse(Call<CheatPlanResponse> call, Response<CheatPlanResponse> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    CheatPlanResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<CheatPlanResponse.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {
                            arrayListCheatPlan.clear();
                            arrayListCheatPlan.addAll(data);

                        }
                    }
                    else
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheatPlanResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }





    @Override
    public void onSubmitOccasionData(OccasionResponce.Datum model) {
        OccasionResponce.Datum data;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            OccasionDialog df = (OccasionDialog) fragment;
            df.dismiss();
        }
        data = model;
        arrayListCheatPlan.clear();
        arrayListWishToEat.clear();
        if(data!=null){
            if(data.getOccasionId()!=null) {
                tvOccasion.setText(""+model.getOccasion());
                getWishToEatList(data.getOccasionId());
            }else{
                Toast.makeText(getApplicationContext(),"Occassion Not Found.",Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onSubmitWishToEatData(WishToEatResponse.Datum model) {
        WishToEatResponse.Datum data;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            WishToEatDialog df = (WishToEatDialog) fragment;
            df.dismiss();
        }
        data = model;
        if(data!=null){
            arrayListCheatPlan.clear();
            if(data.getWishToEatId()!=null) {
                tvWishToEat.setText(""+model.getWishToEat());
                getCheatMealList(data.getWishToEatId());
            }else{
                Toast.makeText(getApplicationContext(),"Wish to eat list not found.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSubmitCheatPlanData(CheatPlanResponse.Datum model) {
        CheatPlanResponse.Datum data;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            CheatPlanDialog df = (CheatPlanDialog) fragment;
            df.dismiss();
        }
        data = model;
        if(data!=null){
            tvCheatPlan.setText(""+model.getCheatPlan());
        }
    }
}
