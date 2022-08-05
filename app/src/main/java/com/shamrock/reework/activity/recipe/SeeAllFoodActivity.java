package com.shamrock.reework.activity.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodActivity;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodNewActivity;
import com.shamrock.reework.activity.FoodModule.activity.FoodRecipeActivity;
import com.shamrock.reework.activity.FoodModule.adapter.AllFoodTripNewAdapter;
import com.shamrock.reework.activity.FoodModule.fragment.ClsVegNonVegFoodRequest;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.recipe.adapter.SeeAllFoodAdapter;
import com.shamrock.reework.activity.recipe.cusion.CusionClickListner;
import com.shamrock.reework.activity.recipe.cusion.CusionData;
import com.shamrock.reework.activity.recipe.cusion.CusionMain;
import com.shamrock.reework.activity.recipe.cusion.CusionTypeCategoryAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllFoodActivity extends AppCompatActivity implements CusionClickListner,SearchView.OnQueryTextListener, SearchView.OnCloseListener, View.OnClickListener, SeeAllFoodAdapter.FoodTripListener {
    RecyclerView recycler_seeAll;
    private ArrayList<FoodTripResponse.FoodStripData> arylst_healthy_veg;
    private ArrayList<FoodTripResponse.FoodStripData> arylst_healthy_veg_temp;
    private SeeAllFoodAdapter mFoodTripAdapter_seesll;
    Context mContext;
    private Utils utils;
    private SessionManager sessionManager;
    private FoodService foodService;
    private String recipe;
    SearchView searchRecipe;
    RecyclerView recylcer_spiritual_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all_food);
        recylcer_spiritual_category = findViewById(R.id.recylcer_spiritual_category);
        recycler_seeAll = findViewById(R.id.recycler_seeAll);
        mContext = SeeAllFoodActivity.this;
        utils = new Utils();
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

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Recipe Library");
        sessionManager = new SessionManager(SeeAllFoodActivity.this);
        foodService = Client.getClient().create(FoodService.class);
        recipe = getIntent().getStringExtra("Recipe");


        if (recipe.equalsIgnoreCase("CustomisedVeg")) {
            tvTitle.setText("Customised Vegetarian;");
            callCustomisedVegetarianRecipies();

        } else if (recipe.equalsIgnoreCase("CustomisedNonVeg")) {
            tvTitle.setText("Customised Non-vegetarian.");
            callCustomisedNonVegetarianRecipies();

        } else if (recipe.equalsIgnoreCase("HealthyVeg")) {
            tvTitle.setText("Healthy Vegetarian");
            callTHealthyNonVegRecipies("Vegetarian", true, "");

        } else if (recipe.equalsIgnoreCase("HealthynonVeg")) {
            tvTitle.setText("Healthy Non-Vegetarian");

            callTHealthyNonVegRecipies("Non-Vegetarian", true, "");

        } else if (recipe.equalsIgnoreCase("normalveg")) {
            tvTitle.setText("Normal Vegetarian");

            callTHealthyNonVegRecipies("Vegetarian", false, "");


        } else if (recipe.equalsIgnoreCase("normalnonVeg")) {
            callTHealthyNonVegRecipies("Non-Vegetarian", false, "");
            tvTitle.setText("Normal Non-Vegetarian");


        } else if (recipe.equalsIgnoreCase("allrecipe")) {
            tvTitle.setText("Search Recipes");

            callAllRecipieslist();
        }else if (recipe.equalsIgnoreCase("allrecipenew")) {
            tvTitle.setText("All Recipes");

            callAllRecipieslist();
        }

//        callAllRecipieslist

        searchRecipe = findViewById(R.id.searchRecipe);
        searchRecipe.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchRecipe.setOnSearchClickListener(this);
        searchRecipe.setOnCloseListener((SearchView.OnCloseListener) this);


    }

    private void callTHealthyVegRecipies(String vagitarian, final boolean b, String s) {
        utils.showProgressbar(SeeAllFoodActivity.this);

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
                                    arylst_healthy_veg = tempList;

                                    mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg, SeeAllFoodActivity.this);
                                    recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);
                                } catch (Exception e) {
                                    Toast.makeText(SeeAllFoodActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        utils.showProgressbar(SeeAllFoodActivity.this);


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

                                    arylst_healthy_veg = tempList;
                                    mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg, SeeAllFoodActivity.this);
                                    recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);
                                    callcusionlist();

                                } catch (Exception e) {
                                    Toast.makeText(SeeAllFoodActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String text = query;
        mFoodTripAdapter_seesll.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        mFoodTripAdapter_seesll.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    private void callAllRecipieslist() {
        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        utils.showProgressbar(SeeAllFoodActivity.this);
        Call<FoodTripResponse> call = foodService.getAllRecipeList(userId);
        call.enqueue(new Callback<FoodTripResponse>() {
            @Override

            public void onResponse(Call<FoodTripResponse> call, Response<FoodTripResponse> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FoodTripResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {
                        tempList = response.body().getData();


                        if (tempList != null && tempList.size() > 0) {
                            try {
                                arylst_healthy_veg = tempList;
                                mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg, SeeAllFoodActivity.this);
                                recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);
                                callcusionlist();

                            } catch (Exception e) {
                                Toast.makeText(SeeAllFoodActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {


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

    private void callcusionlist() {
        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        utils.showProgressbar(SeeAllFoodActivity.this);
        Call<CusionMain> call = foodService.getCuisineList();
        call.enqueue(new Callback<CusionMain>() {
            @Override

            public void onResponse(Call<CusionMain> call, Response<CusionMain> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    CusionMain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        ArrayList<CusionData> arylst_cusioncategory = response.body().getData();

                        ArrayList<CusionData> arylst_cusioncategory_new=new ArrayList<>();

                        arylst_cusioncategory_new.add(new CusionData("All","true",""));

                        for (int i = 0; i <arylst_cusioncategory.size() ; i++) {
                            arylst_cusioncategory_new.add(new CusionData(arylst_cusioncategory.get(i).getCuisine(),"true",""));
                        }
                        if (arylst_cusioncategory_new != null && arylst_cusioncategory_new.size() > 0) {
                            try {
                                CusionTypeCategoryAdapter      CusionTypeCategoryAdapters = new CusionTypeCategoryAdapter(SeeAllFoodActivity.this, arylst_cusioncategory_new);
                                recylcer_spiritual_category.setAdapter(CusionTypeCategoryAdapters);
                            } catch (Exception e) {
                                Toast.makeText(SeeAllFoodActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<CusionMain> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model) {
        if (model != null) {
            int recipeId = model.getRecipeId();
            int editId = model.getEditId();

            Intent intent = new Intent(SeeAllFoodActivity.this, FoodRecipeActivity.class);
            intent.putExtra("RECEIPE_ID", recipeId);
            intent.putExtra("EDIT_ID", editId);
            intent.putExtra("RECEIPE_Image", model.getRecipeImagePath());
            startActivity(intent);
        }

    }

    @Override
    public void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model) {

    }
    private void callCustomisedVegetarianRecipies() {
        utils.showProgressbar(SeeAllFoodActivity.this);
        sessionManager = new SessionManager(SeeAllFoodActivity.this);

//        userIDuserID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
     int   userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


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

                                    arylst_healthy_veg = tempList;
                                    mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg, SeeAllFoodActivity.this);
                                    recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);

                                    callcusionlist();

                                } catch (Exception e) {
                                    Toast.makeText(SeeAllFoodActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        utils.showProgressbar(SeeAllFoodActivity.this);
        sessionManager = new SessionManager(SeeAllFoodActivity.this);

//        userIDuserID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    int    userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


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
                                    arylst_healthy_veg = tempList;
                                    mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg, SeeAllFoodActivity.this);
                                    recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);
                                    callcusionlist();

                                } catch (Exception e) {
                                    Toast.makeText(SeeAllFoodActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onClickCusionItme(String name) {
      try {
          arylst_healthy_veg_temp=new ArrayList<>();


          if (name.equalsIgnoreCase("All")){
              for (int i = 0; i <arylst_healthy_veg.size() ; i++) {
                  if (arylst_healthy_veg.get(i).getCuisine()!=null){
                      arylst_healthy_veg_temp.add(arylst_healthy_veg.get(i));

                  }


              }

              if (!arylst_healthy_veg_temp.isEmpty()){
                  mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg_temp, SeeAllFoodActivity.this);
                  recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);

              }else {

                  Toast.makeText(mContext, "No data available", Toast.LENGTH_SHORT).show();
                  mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg_temp, SeeAllFoodActivity.this);
                  recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);
              }
          }else {

              for (int i = 0; i <arylst_healthy_veg.size() ; i++) {
                  if (arylst_healthy_veg.get(i).getCuisine()!=null){
                      if (name.equalsIgnoreCase(arylst_healthy_veg.get(i).getCuisine().toString())){
                          arylst_healthy_veg_temp.add(arylst_healthy_veg.get(i));

                      }

                  }


              }

              if (!arylst_healthy_veg_temp.isEmpty()){
                  mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg_temp, SeeAllFoodActivity.this);
                  recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);

              }else {

                  Toast.makeText(mContext, "No data available", Toast.LENGTH_SHORT).show();
                  mFoodTripAdapter_seesll = new SeeAllFoodAdapter(SeeAllFoodActivity.this, arylst_healthy_veg_temp, SeeAllFoodActivity.this);
                  recycler_seeAll.setAdapter(mFoodTripAdapter_seesll);
              }
          }






      }catch (Exception e){
          e.printStackTrace();
      }
    }
}
