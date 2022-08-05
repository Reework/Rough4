package com.shamrock.reework.activity.FoodModule.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.AnalyticsActivity;
import com.shamrock.reework.activity.FoodModule.adapter.AllFoodTripAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.AllFoodTripNewAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.ClsHealthCategorymasterAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.FoodSnippingAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.OnHealthCatoryClick;
import com.shamrock.reework.activity.FoodModule.fragment.ClsVegNonVegFoodRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsHealthCategoryMaster;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.recipe.CreateRecipeActivity;
import com.shamrock.reework.activity.recipe.SeeAllFoodActivity;
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

public class AllFoodNewActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, OnHealthCatoryClick, AllFoodTripNewAdapter.FoodTripListener {


    RecyclerView recycler_veg;
    RecyclerView recycler_veg_healthy;
    TextView seeAllhealthyveg;

    RecyclerView recycler_CustomisedNonVegetarian,recycler_CustomisedVegetarian;


    private RecyclerView rvAllRecipe;
    private LinearLayout ll_all_meal_header;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterListDialog;
    RecyclerView recylcer_healthycategory;
    boolean isProtin = false;
    String protinname = "";
    boolean isFromNext = false;
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
    RecyclerView recycler_nonveg_healthy;

    ArrayList<FoodTripResponse.FoodStripData> mFoodList;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterList;
    AllFoodTripAdapter mFoodTripAdapter;
    ;
    AllFoodTripNewAdapter mFoodTripAdapter_new;
    AllFoodTripNewAdapter mFoodTripAdapter_new_nonveg;
    AllFoodTripNewAdapter mFoodTripAdapter_new_veg_healthy;
    AllFoodTripNewAdapter mFoodTripAdapter_new_nonveg_healthy;

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
    boolean isHealthRecipe = false;

    FoodSnippingAdapter mFoodSnippingAdapter;
    List<FoodSnippingResp.Datum> mSnippingList;

    private Utils utils;
    private Utils utils2;
    FoodService foodService;
    SessionManager sessionManager;

    ImageView imgFilter;
    FloatingActionButton fabAdd;

    RecyclerView recycler_nonveg;
    int userId;
    public static int FOOD_TYPE;

    boolean isFoods = false;
    boolean isExpand = false;


    SearchView searchRecipe;
    boolean isVeg = true;
    boolean isResume;
    private String strrecipe = "";
    private ArrayList<FoodTripResponse.FoodStripData> arylst_healthy_veg;
    private TextView seeAllhealthynonveg;
    private LinearLayout ll_mainrecipe;
    private CountDownTimer countDownTimer;
    private boolean healthy_veg;
    private boolean healthy_nonveg;
    private ImageView img_searchrecipe;

    @Override
    protected void onResume() {
        super.onResume();
        if (isResume) {
            isResume = false;

//            callToGetAllRecipies(strrecipe, false);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_food_new);
        mContext = AllFoodNewActivity.this;
        SessionManager sessionManager=new SessionManager(AllFoodNewActivity.this);
         userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        seeAllhealthyveg = findViewById(R.id.seeAllhealthyveg);
        recycler_CustomisedVegetarian = findViewById(R.id.recycler_CustomisedVegetarian);
        recycler_CustomisedNonVegetarian = findViewById(R.id.recycler_CustomisedNonVegetarian);
        img_searchrecipe = findViewById(R.id.img_searchrecipe);
        ll_mainrecipe = findViewById(R.id.ll_mainrecipe);
        seeAllhealthynonveg = findViewById(R.id.seeAllhealthynonveg);
        utils = new Utils();
        utils2 = new Utils();
        foodService = Client.getClient().create(FoodService.class);
        newUI();
        ImageView imgLeft = findViewById(R.id.imgLeft);

        imgLeft.setImageResource(R.drawable.back_arrow);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


    }

    private void newUI() {
        try {
            TextView tvTitle=findViewById(R.id.tvTitle);
            tvTitle.setText("Recipe Library");
            seeAllhealthyveg = findViewById(R.id.seeAllhealthyveg);
        TextView    seeall_CustomisedVegetarian = findViewById(R.id.seeall_CustomisedVegetarian);
        TextView    seeall_CustomisednonVegetarian = findViewById(R.id.seeall_CustomisednonVegetarian);
            recycler_veg = findViewById(R.id.recycler_veg);
            recycler_nonveg = findViewById(R.id.recycler_nonveg);
            recycler_veg_healthy = findViewById(R.id.recycler_veg_healthy);
            recycler_nonveg_healthy = findViewById(R.id.recycler_nonveg_healthy);
            callTHealthyVegRecipies("Vegetarian", true, "");
            callTHealthyNonVegRecipies("Non-Vegetarian", true, "");
            callToGetAllRecipies("Vegetarian", false, "");
            callToGetNovvegAllRecipies("Non-Vegetarian", false, "");



            seeall_CustomisedVegetarian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                    intent.putExtra("Recipe", "CustomisedVeg");
                    startActivity(intent);
                }
            });

            seeall_CustomisednonVegetarian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                    intent.putExtra("Recipe", "CustomisedNonVeg");
                    startActivity(intent);
                }
            });





            seeAllhealthyveg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (arylst_healthy_veg != null) {
                        try {
                            Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                            intent.putExtra("Recipe", "HealthyVeg");
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            seeAllhealthynonveg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (arylst_healthy_veg != null) {
                        try {
                            Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                            intent.putExtra("Recipe", "HealthynonVeg");
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            });


            TextView seeall_veg_normal = findViewById(R.id.seeall_veg_normal);
            seeall_veg_normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arylst_healthy_veg != null) {
                        try {
                            Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                            intent.putExtra("Recipe", "normalVeg");
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            });


            TextView seeall_normalnonveg = findViewById(R.id.seeall_normalnonveg);
            seeall_normalnonveg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                        intent.putExtra("Recipe", "normalnonVeg");
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });

            CardView card_searchrecipe = findViewById(R.id.card_searchrecipe);
            img_searchrecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                        intent.putExtra("Recipe", "allrecipe");
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            CardView card_allrecipe=findViewById(R.id.card_allrecipe);
            card_allrecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(AllFoodNewActivity.this, SeeAllFoodActivity.class);
                        intent.putExtra("Recipe", "allrecipenew");
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });


            Button card_createrecipe = findViewById(R.id.card_createrecipe);
            card_createrecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(AllFoodNewActivity.this, CreateRecipeActivity.class);
                    startActivity(intent);

                }
            });

            CardView card_analytics=findViewById(R.id.card_analytics);
            card_analytics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AllFoodNewActivity.this, RecipeAnalyticsActivity.class);
                    startActivity(intent);

                }
            });

        } catch (Exception e) {
            Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void shownoplan() {

        final Dialog dialog = new Dialog(AllFoodNewActivity.this, R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired = dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe = dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no = dialog.findViewById(R.id.btn_subscribe_no);
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

                Intent intent = new Intent(AllFoodNewActivity.this, ViewPagerActivity.class);
                intent.putExtra("param", "From_Home");
                startActivity(intent);


            }
        });

        dialog.show();

    }


    private void showRetryBar(String msg) {
        String strMessage;
        if (!msg.isEmpty()) {
            strMessage = msg;
        } else {
            strMessage = "Unable to load data.";
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (Utils.isNetworkAvailable(AllFoodNewActivity.this)) {

                            if (isVeg) {
                                callToGetAllRecipies("Vegetarian", isHealthRecipe, "");

                            } else {
                                callToGetAllRecipies("Non-Vegetarian", isHealthRecipe, "");

                            }
                        } else {
                            showRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }

    private void callToGetAllRecipies(String vagitarian, final boolean b, String s) {
        Toast.makeText(mContext, "Please wait it will be take more times for loading data.....", Toast.LENGTH_LONG).show();

        utils2.showProgressbar(AllFoodNewActivity.this);

        ClsVegNonVegFoodRequest clsVegNonVegFoodRequest = new ClsVegNonVegFoodRequest();
        clsVegNonVegFoodRequest.setUserId(userId);
        if (s.isEmpty()) {
            clsVegNonVegFoodRequest.setDietary_preference(vagitarian);

        }
        clsVegNonVegFoodRequest.setHealthy(b);
        clsVegNonVegFoodRequest.setClassification(s);
        Call<FoodTripResponse> call = foodService.getAllRecipies(clsVegNonVegFoodRequest);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils2.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (true) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {
                                try {
                                    ll_mainrecipe.setVisibility(View.VISIBLE);


                                    mFoodTripAdapter_new_veg_healthy = new AllFoodTripNewAdapter(AllFoodNewActivity.this, tempList, AllFoodNewActivity.this,50);
                                    recycler_veg.setAdapter(mFoodTripAdapter_new_veg_healthy);

                                } catch (Exception e) {
                                    Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                        //                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils2.hideProgressbar();
            }
        });
    }

    private void callToGetNovvegAllRecipies(String vagitarian, final boolean b, String s) {
        utils.showProgressbar(AllFoodNewActivity.this);


        ClsVegNonVegFoodRequest clsVegNonVegFoodRequest = new ClsVegNonVegFoodRequest();
        clsVegNonVegFoodRequest.setUserId(userId);
        if (s.isEmpty()) {
            clsVegNonVegFoodRequest.setDietary_preference(vagitarian);

        }
        clsVegNonVegFoodRequest.setHealthy(b);
        clsVegNonVegFoodRequest.setClassification("");
        Call<FoodTripResponse> call = foodService.getAllRecipies(clsVegNonVegFoodRequest);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (true) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {
                                try {
                                    ll_mainrecipe.setVisibility(View.VISIBLE);


                                    mFoodTripAdapter_new_nonveg = new AllFoodTripNewAdapter(AllFoodNewActivity.this, tempList, AllFoodNewActivity.this,50);
                                    recycler_nonveg.setAdapter(mFoodTripAdapter_new_nonveg);
                                    callCustomisedVegetarianRecipies();
                                    callCustomisedNonVegetarianRecipies();
                                } catch (Exception e) {
                                    Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                        //                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void callTHealthyVegRecipies(String vagitarian, final boolean b, String s) {
        utils.showProgressbar(AllFoodNewActivity.this);


        SessionManager sessionManager=new SessionManager(AllFoodNewActivity.this);
        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        ClsVegNonVegFoodRequest clsVegNonVegFoodRequest = new ClsVegNonVegFoodRequest();
        clsVegNonVegFoodRequest.setUserId(userId);
        if (s.isEmpty()) {
            clsVegNonVegFoodRequest.setDietary_preference(vagitarian);

        }
        clsVegNonVegFoodRequest.setHealthy(b);
        clsVegNonVegFoodRequest.setClassification(s);
        Call<FoodTripResponse> call = foodService.getAllRecipies(clsVegNonVegFoodRequest);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (true) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {
                                try {
                                    healthy_veg=true;
                                    arylst_healthy_veg = tempList;
                                    mFoodTripAdapter_new_veg_healthy = new AllFoodTripNewAdapter(AllFoodNewActivity.this, tempList, AllFoodNewActivity.this,50);
                                    recycler_veg_healthy.setAdapter(mFoodTripAdapter_new_veg_healthy);
                                } catch (Exception e) {
                                    Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                        //                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void callTHealthyNonVegRecipies(String vagitarian, final boolean b, String s) {
        utils.showProgressbar(AllFoodNewActivity.this);


        ClsVegNonVegFoodRequest clsVegNonVegFoodRequest = new ClsVegNonVegFoodRequest();
        clsVegNonVegFoodRequest.setUserId(userId);
        if (s.isEmpty()) {
            clsVegNonVegFoodRequest.setDietary_preference(vagitarian);

        }
        clsVegNonVegFoodRequest.setHealthy(b);
        clsVegNonVegFoodRequest.setClassification(s);
        Call<FoodTripResponse> call = foodService.getAllRecipies(clsVegNonVegFoodRequest);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (true) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {
                                try {

                                    healthy_nonveg=true;

                                    mFoodTripAdapter_new_nonveg_healthy = new AllFoodTripNewAdapter(AllFoodNewActivity.this, tempList, AllFoodNewActivity.this,50);
                                    recycler_nonveg_healthy.setAdapter(mFoodTripAdapter_new_nonveg_healthy);





                                } catch (Exception e) {
                                    Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                        //                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
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


    private void callToUpdateFavoriteStatus(FoodTripResponse.FoodStripData model) {
        utils.showProgressbar(AllFoodNewActivity.this);


        FoodTripFavoriteUpdateRequest request = new FoodTripFavoriteUpdateRequest();
        request.setUserId(userId);

        if (model.getIsfavourite() != null) {
            if (model.getIsfavourite() == true) {
                request.setIsfavourite(1);
            } else {
                request.setIsfavourite(0);
            }
        }
        request.setEditId(model.getEditId());
        request.setRecipeId(model.getRecipeId());
        Gson gson = new Gson();
        String json = gson.toJson(request);
        String test = json;

        Call<FoodTripFavoriteUpdateResponse> call = foodService.setIsFavoriteStatus(request);
        call.enqueue(new Callback<FoodTripFavoriteUpdateResponse>() {
            @Override
            public void onResponse(Call<FoodTripFavoriteUpdateResponse> call, Response<FoodTripFavoriteUpdateResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripFavoriteUpdateResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {


                        mFoodTripAdapter.notifyDataSetChanged();

                        if (Utils.isNetworkAvailable(AllFoodNewActivity.this)) {

                            if (isProtin) {
                                callToGetAllRecipies("Vegetarian", isHealthRecipe, protinname);


                            } else if (isVeg) {

                                if (Utils.isNetworkAvailable(AllFoodNewActivity.this)) {
                                    callToGetAllRecipies("" +
                                            "Vegetarian", isHealthRecipe, "");

                                } else {
                                    showRetryBar("Check internet connection!");

                                }

                            } else {
                                if (Utils.isNetworkAvailable(AllFoodNewActivity.this)) {
                                    callToGetAllRecipies("Non-Vegetarian", isHealthRecipe, "");

                                } else {
                                    showRetryBar("Check internet connection!");

                                }


                            }
//                            callToGetAllRecipies("intial", 0, "");

                        } else {
                            Toast.makeText(AllFoodNewActivity.this, R.string.internet_connection_unavailable, Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(AllFoodNewActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodTripFavoriteUpdateResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void getHathCatogoryPosName(String name, boolean isProtin) {

        protinname = name;
        this.isProtin = isProtin;
        callToGetAllRecipies("Vegetarian", isHealthRecipe, name);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void callAllHealtyRecipe(String vagitarian, final boolean b, String s) {

        utils.showProgressbar(AllFoodNewActivity.this);


        ClsVegNonVegFoodRequest clsVegNonVegFoodRequest = new ClsVegNonVegFoodRequest();
        clsVegNonVegFoodRequest.setUserId(userId);
        if (s.isEmpty()) {
            clsVegNonVegFoodRequest.setDietary_preference(vagitarian);

        }
        clsVegNonVegFoodRequest.setHealthy(b);
        clsVegNonVegFoodRequest.setClassification(s);
        Call<FoodTripResponse> call = foodService.getAllRecipies(clsVegNonVegFoodRequest);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                rvAllRecipe.setVisibility(View.VISIBLE);
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (b) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {

                                rvAllRecipe.setVisibility(View.VISIBLE);
                                norecipe.setVisibility(View.GONE);

                                mFoodList.clear();
                                mFoodList.addAll(tempList);
                                mFoodTripAdapter.notifyDataSetChanged();
                            }
                        } else {
                            mFoodList.clear();
//                        mFoodList.addAll(tempList);
                            mFoodTripAdapter.notifyDataSetChanged();
                            rvAllRecipe.setVisibility(View.GONE);
                            norecipe.setVisibility(View.VISIBLE);
                            norecipe.setText(listResponse.getMessage());


//                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {

                                rvAllRecipe.setVisibility(View.VISIBLE);
                                norecipe.setVisibility(View.GONE);

                                mFoodList.clear();
                                mFoodList.addAll(tempList);
                                mFoodTripAdapter.notifyDataSetChanged();
                            }
                        } else {
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
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model) {
        if (model != null)
        {
            int recipeId = model.getRecipeId();
            int editId = model.getEditId();

            isResume=true;
            Intent intent = new Intent(AllFoodNewActivity.this, FoodRecipeActivity.class);
            intent.putExtra("RECEIPE_ID", recipeId);
            intent.putExtra("EDIT_ID",editId);
            intent.putExtra("RECEIPE_Image",model.getRecipeImagePath());
            startActivity(intent);
        }

    }

    @Override
    public void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model) {

    }
    private void callCustomisedVegetarianRecipies() {
        utils.showProgressbar(AllFoodNewActivity.this);
        sessionManager = new SessionManager(AllFoodNewActivity.this);

//        userIDuserID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        Call<FoodTripResponse> call = foodService.getCustomizedVegeterianRecipies(userId);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (true) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {
                                try {
                                    ll_mainrecipe.setVisibility(View.VISIBLE);


                                    AllFoodTripNewAdapter allFoodTripNewAdapter_cust_veg;


                                    allFoodTripNewAdapter_cust_veg = new AllFoodTripNewAdapter(AllFoodNewActivity.this, tempList, AllFoodNewActivity.this,50);
                                    recycler_CustomisedVegetarian.setAdapter(allFoodTripNewAdapter_cust_veg);
                                } catch (Exception e) {
                                    Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                        //                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }
    private void callCustomisedNonVegetarianRecipies() {
        utils.showProgressbar(AllFoodNewActivity.this);
        sessionManager = new SessionManager(AllFoodNewActivity.this);

//        userIDuserID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        Call<FoodTripResponse> call = foodService.getCustomizedNonVegeterianRecipies(userId);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (true) {
                        if (listResponse != null && listResponse.getCode() == 1) {
                            tempList = response.body().getData();


                            if (tempList != null && tempList.size() > 0) {
                                try {
                                    ll_mainrecipe.setVisibility(View.VISIBLE);


                                    AllFoodTripNewAdapter allFoodTripNewAdapter_cust_nonveg;


                                    allFoodTripNewAdapter_cust_nonveg = new AllFoodTripNewAdapter(AllFoodNewActivity.this, tempList, AllFoodNewActivity.this,50);
                                    recycler_CustomisedNonVegetarian.setAdapter(allFoodTripNewAdapter_cust_nonveg);
                                } catch (Exception e) {
                                    Toast.makeText(AllFoodNewActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                        //                        Toast.makeText(AllFoodActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            }

            @Override
            public void onFailure(Call<FoodTripResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


}
