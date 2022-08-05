package com.shamrock.reework.activity.FoodModule.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.AllFoodTripAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.ClsHealthCategorymasterAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.FoodSnippingAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.OnHealthCatoryClick;
import com.shamrock.reework.activity.FoodModule.fragment.ClsVegNonVegFoodRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsHealthCategoryMaster;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.activity.dietplan.NewRDPFoodPlanActivity;
import com.shamrock.reework.activity.recipe.CreateRecipeActivity;
import com.shamrock.reework.activity.recipeanalytics.RecipeAnalyticsActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.FoodTripFavoriteUpdateRequest;
import com.shamrock.reework.api.response.FoodSnippingResp;
import com.shamrock.reework.api.response.FoodTripFavoriteUpdateResponse;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetFilterSubFilterResponce;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFoodActivity extends AppCompatActivity implements  View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        AllFoodTripAdapter.FoodTripListener,SearchView.OnQueryTextListener,SearchView.OnCloseListener, OnHealthCatoryClick {

    private RecyclerView rvAllRecipe;
    private LinearLayout ll_all_meal_header;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterListDialog;
    RecyclerView recylcer_healthycategory;
    boolean isProtin=false;
    String protinname="";
    boolean isFromNext=false;
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
            FILE_SELECT_REQUEST_CODE = 300;
    public static final int SECOND_ACTIVITY_REQUEST_CODE = 108;
    public boolean isCamera = false;
    public boolean isImage = false;
    private Uri mCapturedImageURI;
    private Context mContext;
    RadioButton rbFood, rbFoodSnapping;
    TextView norecipe;

    ArrayList<FoodTripResponse.FoodStripData> mFoodList;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterList;
    AllFoodTripAdapter mFoodTripAdapter;;
    LinearLayout ll_food_recipe;
    ViewFlipper vp_reciepe;
    RecyclerView rvnonvegRecipies;
    RadioButton rd_button_nonveg;
    RadioButton rd_button_veg;
    RadioButton rd_button_all;

    RadioButton rd_button_healthy;
    RadioButton rd_button_noraml;
    RadioButton rd_button_analytics;
    RadioButton rd_button_create_recipe;
    boolean isHealthRecipe=false;

    FoodSnippingAdapter mFoodSnippingAdapter;
    List<FoodSnippingResp.Datum> mSnippingList;

    private Utils utils;
    FoodService foodService;
    SessionManager sessionManager;

    ImageView imgFilter;
    FloatingActionButton fabAdd;

    int userId;
    public static int FOOD_TYPE;

    boolean isFoods = false;
    boolean isExpand = false;


    SearchView searchRecipe;
    boolean isVeg=true;
    boolean isResume;
    private String strrecipe="";


    @Override
    protected void onResume() {
        super.onResume();
        if (isResume){
            isResume=false;

//            callToGetAllRecipies(strrecipe, false);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_food);
        rvAllRecipe = findViewById(R.id.rvRecipies);

        utils = new Utils();
        sessionManager = new SessionManager(AllFoodActivity.this);
        sessionManager = new SessionManager(AllFoodActivity.this);
        foodService = Client.getClient().create(FoodService.class);
        mFoodList = new ArrayList<>();
        mSubFilterList = new ArrayList<>();
        mSnippingList = new ArrayList<>();
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Recipe Library");

        rd_button_analytics=findViewById(R.id.rd_button_analytics);
        rd_button_analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AllFoodActivity.this, RecipeAnalyticsActivity.class),233);
                overridePendingTransition(0,0);
//                finish();
            }
        });

        rd_button_create_recipe=findViewById(R.id.rd_button_create_recipe);
        rd_button_create_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();

                }else {
                    startActivityForResult(new Intent(AllFoodActivity.this, CreateRecipeActivity.class),2383);
                    overridePendingTransition(0,0);
                    finish();
                }




//                finish();
            }
        });

        ImageView tvTitles=findViewById(R.id.imgLeft);
        tvTitles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        norecipe=findViewById(R.id.norecipe);
        rd_button_veg=findViewById(R.id.rd_button_veg);
        rd_button_nonveg=findViewById(R.id.rd_button_nonveg);
        rd_button_noraml=findViewById(R.id.rd_button_noraml);
        rd_button_healthy=findViewById(R.id.rd_button_healthy);
        ll_all_meal_header=findViewById(R.id.ll_all_meal_header);
        recylcer_healthycategory=findViewById(R.id.recylcer_healthycategory);
        rd_button_all=findViewById(R.id.rd_button_all);
        callAllRecipieslist();

        rd_button_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_all_meal_header.setVisibility(View.GONE);
                callAllRecipieslist();
            }
        });





        searchRecipe=findViewById(R.id.searchRecipe);
        searchRecipe.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchRecipe.setOnSearchClickListener(this);
        searchRecipe.setOnCloseListener((SearchView.OnCloseListener) this);


        mFoodTripAdapter = new AllFoodTripAdapter(AllFoodActivity.this, mFoodList, AllFoodActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvAllRecipe.setLayoutManager(layoutManager);
        rvAllRecipe.setItemAnimator(new DefaultItemAnimator());
        rvAllRecipe.setAdapter(mFoodTripAdapter);
//        getHealthCategoryMasterAPI();


//        if (Utils.isNetworkAvailable(AllFoodActivity.this)){
//            callToGetAllRecipies("Vegetarian", isHealthRecipe,"");
//            strrecipe="Vegetarian";
//
//        }else {
//            showRetryBar("Check internet connection!");
//
//        }


        rd_button_noraml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isFromNext){
                    isHealthRecipe=false;
                    isProtin=false;
                    recylcer_healthycategory.setVisibility(View.GONE);

                    ll_all_meal_header.setVisibility(View.VISIBLE);

                    rd_button_veg.performClick();

//                callToGetAllRecipies("Vegetarian", isHealthRecipe);
                    strrecipe="Vegetarian";
                }
                isFromNext=false;


            }
        });

        rd_button_healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHealthRecipe=true;
                rvAllRecipe.setVisibility(View.INVISIBLE);
                recylcer_healthycategory.setVisibility(View.VISIBLE);

                ll_all_meal_header.setVisibility(View.GONE);
//                rd_button_veg.performClick();
                getHealthCategoryMasterAPI();
//                callToGetAllRecipies("Vegetarian", isHealthRecipe);

            }
        });

        rd_button_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(AllFoodActivity.this)){
                    callToGetAllRecipies("Vegetarian", isHealthRecipe, "");
                    strrecipe="Vegetarian";


                }else {
                    showRetryBar("Check internet connection!");

                }                isVeg=true;

            }
        });

        rd_button_nonveg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(AllFoodActivity.this)){
                    callToGetAllRecipies("Non-Vegetarian", isHealthRecipe, "");
                    strrecipe="Non-Vegetarian";


                }else {
                    showRetryBar("Check internet connection!");

                }
                isVeg=false;
            }
        });


    }

    private void shownoplan() {

        final Dialog dialog=new Dialog(AllFoodActivity.this,R.style.CustomDialog);

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

                Intent  intent = new Intent(AllFoodActivity.this, ViewPagerActivity.class);
                intent.putExtra("param", "From_Home");
                startActivity(intent);


            }
        });

        dialog.show();

    }


    private void showRetryBar(String msg)
    {
        String strMessage;
        if (!msg.isEmpty())
        {
            strMessage = msg;
        }
        else
        {
            strMessage = "Unable to load data.";
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {


                        if (Utils.isNetworkAvailable(AllFoodActivity.this))
                        {

                            if (isVeg){
                                callToGetAllRecipies("Vegetarian", isHealthRecipe, "");

                            }else {
                                callToGetAllRecipies("Non-Vegetarian", isHealthRecipe, "");

                            }
                        }
                        else
                        {
                            showRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }
    private void callToGetAllRecipies(String vagitarian, final boolean b, String s) {

        utils.showProgressbar(AllFoodActivity.this);



        ClsVegNonVegFoodRequest clsVegNonVegFoodRequest=new ClsVegNonVegFoodRequest();
        clsVegNonVegFoodRequest.setUserId(userId);
        if (s.isEmpty()){
            clsVegNonVegFoodRequest.setDietary_preference(vagitarian);

        }
        clsVegNonVegFoodRequest.setHealthy(b);
        clsVegNonVegFoodRequest.setClassification(s);
        Call<FoodTripResponse> call = foodService.getAllRecipies(clsVegNonVegFoodRequest);
        call.enqueue(new Callback<FoodTripResponse>()
        {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response)
            {
                utils.hideProgressbar();
                rvAllRecipe.setVisibility(View.VISIBLE);
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodTripResponse listResponse = response.body();
                   if (b){
                       if (listResponse != null && listResponse.getCode() == 1)
                       {
                           tempList = response.body().getData();



                           if (tempList!= null && tempList.size() > 0)
                           {

                               rvAllRecipe.setVisibility(View.VISIBLE);
                               norecipe.setVisibility(View.GONE);

                               mFoodList.clear();
                               mFoodList.addAll(tempList);
                               mFoodTripAdapter.notifyDataSetChanged();
                           }
                       }
                       else
                       {
                           mFoodList.clear();
//                        mFoodList.addAll(tempList);
                           mFoodTripAdapter.notifyDataSetChanged();
                           rvAllRecipe.setVisibility(View.GONE);
                           norecipe.setVisibility(View.VISIBLE);
                           norecipe.setText(listResponse.getMessage());


//                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                       }

                   }else {
                       if (listResponse != null && listResponse.getCode() == 1)
                       {
                           tempList = response.body().getData();



                           if (tempList!= null && tempList.size() > 0)
                           {

                               rvAllRecipe.setVisibility(View.VISIBLE);
                               norecipe.setVisibility(View.GONE);

                               mFoodList.clear();
                               mFoodList.addAll(tempList);
                               mFoodTripAdapter.notifyDataSetChanged();
                           }
                       }
                       else
                       {
                           mFoodList.clear();
//                        mFoodList.addAll(tempList);
                           mFoodTripAdapter.notifyDataSetChanged();
                           rvAllRecipe.setVisibility(View.GONE);
                           norecipe.setVisibility(View.VISIBLE);
                           norecipe.setText(listResponse.getMessage());


//                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void callAllRecipieslist() {

        utils.showProgressbar(AllFoodActivity.this);
        Call<FoodTripResponse> call = foodService.getAllRecipeList(userId);
        call.enqueue(new Callback<FoodTripResponse>()
        {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response)
            {
                utils.hideProgressbar();
                rvAllRecipe.setVisibility(View.VISIBLE);
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodTripResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        tempList = response.body().getData();



                        if (tempList!= null && tempList.size() > 0)
                        {

                            rvAllRecipe.setVisibility(View.VISIBLE);
                            norecipe.setVisibility(View.GONE);

                            mFoodList.clear();
                            mFoodList.addAll(tempList);
                            mFoodTripAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        mFoodList.clear();
//                        mFoodList.addAll(tempList);
                        mFoodTripAdapter.notifyDataSetChanged();
                        rvAllRecipe.setVisibility(View.GONE);
                        norecipe.setVisibility(View.VISIBLE);
                        norecipe.setText(listResponse.getMessage());


//                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }




    private void getHealthCategoryMasterAPI() {

        utils.showProgressbar(AllFoodActivity.this);

        Call<ClsHealthCategoryMaster> call = foodService.getHealthCategoryMaster();
        call.enqueue(new Callback<ClsHealthCategoryMaster>()
        {
            @Override

            public void onResponse(Call<ClsHealthCategoryMaster> call, Response<ClsHealthCategoryMaster> response)
            {
                utils.hideProgressbar();

                ArrayList<HealthCatogoryData> arylst_HealthCatogoryData;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsHealthCategoryMaster listResponse = response.body();

                    if (listResponse!=null){
                        if (listResponse.getData()!=null){
                            if (!listResponse.getData().isEmpty()){
                                arylst_HealthCatogoryData =listResponse.getData();
                                recylcer_healthycategory.setAdapter(new ClsHealthCategorymasterAdapter(AllFoodActivity.this,arylst_HealthCatogoryData));
                                callToGetAllRecipies("Vegetarian", isHealthRecipe,arylst_HealthCatogoryData.get(0).getClassification().toString());

                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ClsHealthCategoryMaster> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String text = query;
        mFoodTripAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        String text = newText;
        mFoodTripAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model) {

        if (model != null)
        {
            int recipeId = model.getRecipeId();
            int editId = model.getEditId();

            isResume=true;
            Intent intent = new Intent(AllFoodActivity.this, FoodRecipeActivity.class);
            intent.putExtra("RECEIPE_ID", recipeId);
            intent.putExtra("EDIT_ID",editId);
            intent.putExtra("RECEIPE_Image",model.getRecipeImagePath());
            startActivity(intent);
        }

    }

    @Override
    public void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model) {

        callToUpdateFavoriteStatus(model);


    }
    private void callToUpdateFavoriteStatus(FoodTripResponse.FoodStripData model)
    {
        utils.showProgressbar(AllFoodActivity.this);


        FoodTripFavoriteUpdateRequest request = new FoodTripFavoriteUpdateRequest();
        request.setUserId(userId);

        if(model.getIsfavourite()!=null){
            if(model.getIsfavourite()==true){
                request.setIsfavourite(1);
            }else{
                request.setIsfavourite(0);
            }
        }
        request.setEditId(model.getEditId());
        request.setRecipeId(model.getRecipeId());
        Gson gson = new Gson();
        String json = gson.toJson(request);
        String test = json;

        Call<FoodTripFavoriteUpdateResponse> call = foodService.setIsFavoriteStatus(request);
        call.enqueue(new Callback<FoodTripFavoriteUpdateResponse>()
        {
            @Override
            public void onResponse(Call<FoodTripFavoriteUpdateResponse> call, Response<FoodTripFavoriteUpdateResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodTripFavoriteUpdateResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {





                        mFoodTripAdapter.notifyDataSetChanged();

                        if (Utils.isNetworkAvailable(AllFoodActivity.this)) {

                            if (isProtin){
                                callToGetAllRecipies("Vegetarian", isHealthRecipe,protinname);




                            }else
                                  if (isVeg){

                                if (Utils.isNetworkAvailable(AllFoodActivity.this)){
                                    callToGetAllRecipies("" +
                                            "Vegetarian", isHealthRecipe, "");

                                }else {
                                    showRetryBar("Check internet connection!");

                                }

                            }else {
                                if (Utils.isNetworkAvailable(AllFoodActivity.this)){
                                    callToGetAllRecipies("Non-Vegetarian", isHealthRecipe, "");

                                }else {
                                    showRetryBar("Check internet connection!");

                                }


                            }
//                            callToGetAllRecipies("intial", 0, "");

                        } else{
                            Toast.makeText(AllFoodActivity.this, R.string.internet_connection_unavailable, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodTripFavoriteUpdateResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void getHathCatogoryPosName(String name, boolean isProtin) {

        protinname=name;
       this. isProtin=isProtin;
        callToGetAllRecipies("Vegetarian", isHealthRecipe, name);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==233){

            isFromNext=true;
            RadioGroup radioGroup_sl=findViewById(R.id.radioGroup_sl);
            radioGroup_sl.clearCheck();
//            rd_button_noraml.setChecked(true);
            rd_button_all.performClick();

        }
    }
}
