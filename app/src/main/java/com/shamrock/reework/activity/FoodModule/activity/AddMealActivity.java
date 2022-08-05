package com.shamrock.reework.activity.FoodModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.AddMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.RepeatMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.fragment.AddFoodDialogFragmet;
import com.shamrock.reework.activity.FoodModule.fragment.FoodRepeatMealFragmet;
import com.shamrock.reework.activity.FoodModule.model.AddMealRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodUnitMasterPojo;
import com.shamrock.reework.activity.FoodModule.model.EditMealRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.FoodModule.model.repeatdata.ClsRepeatMealData;
import com.shamrock.reework.activity.FoodModule.model.repeatdata.ClsRepeatTodaysMeal;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.FoodModule.service.OnGetFoodDailogData;
import com.shamrock.reework.activity.FoodModule.service.onSubmitRepeatMeal;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
import com.shamrock.reework.adapter.MealListByTypeAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.FoodListByType;
import com.shamrock.reework.api.response.FoodListByMealType;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetMealType;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.dialog.MealByTypeDialog;
import com.shamrock.reework.dialog.MealTypeDialog;
import com.shamrock.reework.dialog.UOMDialog;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.util.Utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
AddMealActivity extends AppCompatActivity implements OnGetFoodDailogData,onSubmitRepeatMeal,TimePickerDialog.OnTimeSetListener,View.OnClickListener,MealTypeDialog.GetMealTypeListener,MealByTypeDialog.GetMealTypeListener,AddMealIntoListAdapter.OnMealRemoveListner,RepeatMealIntoListAdapter.OnAddRpeatListner ,MealListByTypeAdapter.MealListByTypeListener,UOMDialog.GetUOMListener
{
    Context context;
    Toolbar toolbar;
    private Utils utils;
    int userId;
    Button btnSave_repeat;
    TextView txt_searcheal;
    TextView txt_viewaddedmeal,txt_viewRepeatmeal;
    SessionManager sessionManager;
    FoodService foodService;
    RelativeLayout rel_repeatmeal;
    TextView tvMealType,tvMeals,tvMealTime;
    ArrayList<String> mealType = new ArrayList<>();
    ArrayList<String> stringArrayList_uom;
    ArrayList<FoodListByMealType.Datum> arrayListByMealType = new ArrayList<>();
    private MealTypeDialog mealTypeDialog;
    private UOMDialog  uomDialog;
    private  MealByTypeDialog mealByTypeDialog;
    String strMealType="",txtcommonMealType="";
    ImageView ivRemove,ivAdd;
    double quantityCount=0.0;
    TextView txt_repeat_meal;
    TextView tvAdd;
    EditText tvQty;
    Button btnSave;
    double newQuantity;
    int newUOmID;
    double newValueInGram;
    TextView txt_repat;
    SearchView searchView;
    RecyclerView recyclerview;
    AddFoodDialogFragmet addFoodDialogFragmet;
    ArrayList<FoodListByMealType.Datum> commonAddmealList = new ArrayList<>();
    ArrayList<FoodListByMealType.Datum> commonAddmealList_filter = new ArrayList<>();
    FoodListByMealType.Datum MealData=new FoodListByMealType.Datum() ;
    AddMealIntoListAdapter adapter = null;
    TodaysMeal mealModel;
    String commingFrom;
    int finalCount;
    boolean isPresent=false;
    private TimePickerDialog timepickerdialog;
    TextView spinner_UOM;
    ArrayList<FoodUnitMasterData>    foodUnitMasterDataArrayList;

    double double_valueInGram;
    int strUOmID;

    RecyclerView recycler_repeat_meal;
    TextView txt_showaddedrepeat;
    RelativeLayout rel_parent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_add_meal);
        context = AddMealActivity.this;
        spinner_UOM=findViewById(R.id.spinner_UOM);
        rel_parent=findViewById(R.id.rel_parent);
        rel_repeatmeal=findViewById(R.id.rel_repeatmeal);
        txt_showaddedrepeat=findViewById(R.id.txt_showaddedrepeat);
        txt_showaddedrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (commonAddmealList_filter!=null&&!commonAddmealList_filter.isEmpty()){
                    if (rel_repeatmeal.getVisibility()==View.GONE){
                        rel_repeatmeal.setVisibility(View.VISIBLE);
                        txt_showaddedrepeat.setText("Hide Repeat Meal");
                        searchView.setVisibility(View.GONE);

                        btnSave_repeat.setVisibility(View.VISIBLE);
                        btnSave.setVisibility(View.GONE);





                    }else {
                        boolean isFound;
                        for (int i = 0; i <commonAddmealList_filter.size() ; i++) {
                            if (commonAddmealList_filter.get(i).isSelect()){
                                isFound=true;
                                btnSave_repeat.performClick();
                                isFound=false;


                               return;
                            }
                        }


                        rel_repeatmeal.setVisibility(View.GONE);
                        txt_showaddedrepeat.setText("Show Repeat Meal");
                        searchView.setVisibility(View.VISIBLE);
                        btnSave_repeat.setVisibility(View.GONE);
                        btnSave.setVisibility(View.VISIBLE);


                    }
                }else {
                    Toast.makeText(context, "Repeat meal not found.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        txt_searcheal=findViewById(R.id.txt_searcheal);
        spinner_UOM.setOnClickListener(this);
        utils = new Utils();
        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
        tvMealType = findViewById(R.id.tvMealTypeName);
        txt_repeat_meal = findViewById(R.id.txt_repeat_meal);
        tvMeals = findViewById(R.id.tvMeals);
        ivRemove = findViewById(R.id.ivRemove);
        recyclerview = findViewById(R.id.recyclerview);
        btnSave_repeat = findViewById(R.id.btnSave_repeat);
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Add Food");
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getStringValue("closefood").equalsIgnoreCase("true")){
                    sessionManager.setStringValue("closefood","");
                    Intent intent = new Intent(AddMealActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    finish();

                }

            }
        });

        txt_viewaddedmeal = findViewById(R.id.txt_viewaddedmeal);
        txt_viewRepeatmeal = findViewById(R.id.txt_viewRepeatmeal);
        recycler_repeat_meal = findViewById(R.id.recycler_repeat_meal);
        txt_viewaddedmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (commonAddmealList!=null&&!commonAddmealList.isEmpty()){
                    if (recyclerview.getVisibility()==View.GONE){
                        recyclerview.setVisibility(View.VISIBLE);
                        txt_viewaddedmeal.setText("Hide Added Meal");
                    }else {
                        txt_viewaddedmeal.setText("Show Added Meal");

                        recyclerview.setVisibility(View.GONE);

                    }
                }else {
                    Toast.makeText(context, "No meal added", Toast.LENGTH_SHORT).show();
                }


            }
        });
        txt_viewRepeatmeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (rel_repeatmeal.getVisibility()==View.GONE){
//                    rel_repeatmeal.setVisibility(View.VISIBLE);
//                    txt_viewRepeatmeal.setText("Hide Repeat Meal");
//                    btnSave_repeat.setVisibility(View.VISIBLE);
//                    btnSave.setVisibility(View.GONE);
//                }else {
//                    rel_repeatmeal.setVisibility(View.GONE);
//                    txt_viewRepeatmeal.setText("Show Repeat Meal");
//                    btnSave_repeat.setVisibility(View.GONE);
//                    btnSave.setVisibility(View.VISIBLE);
//
//
//                }
            }
        });

        btnSave_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSave_repeat.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);


                 List<FoodListByMealType.Datum> filtermList;


                filtermList=new ArrayList<>();
                for (int i = 0; i <commonAddmealList_filter.size() ; i++) {
                    if (commonAddmealList_filter.get(i).isSelect()){
                        filtermList.add(commonAddmealList_filter.get(i));
                    }
                }



                if (filtermList!=null&&!filtermList.isEmpty()){
                    for (int i = 0; i <filtermList.size() ; i++) {


                        double valuegramsubmit=filtermList.get(i).getValueInGram();
                        if (valuegramsubmit==0){
                            valuegramsubmit=1.0;
                        }else {
                            valuegramsubmit=filtermList.get(i).getValueInGram();
                        }
                        double qty=filtermList.get(i).getQuantity()*valuegramsubmit;
                        double singlecal=Double.parseDouble(filtermList.get(i).getCalories())/qty;




                        filtermList.get(i).setCalories(String.valueOf(singlecal));


                    }




                    commonAddmealList.addAll(filtermList);
                }


                for (int i = 0; i <commonAddmealList_filter.size() ; i++) {
                    commonAddmealList_filter.get(i).setSelect(false);
                }

                adapter = new AddMealIntoListAdapter(context, commonAddmealList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(adapter);

                txt_viewRepeatmeal.performClick();


            }
        });
        tvAdd = findViewById(R.id.tvAdd);
        ivAdd = findViewById(R.id.ivAdd);
        tvQty = findViewById(R.id.tvQty);
        tvQty.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});

        txt_repat = findViewById(R.id.txt_repat);
        txt_repeat_meal.setPaintFlags(txt_repat.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        tvMealTime = findViewById(R.id.tvMealTime);
        btnSave = findViewById(R.id.btnSave);
        tvMealType.setOnClickListener(this);
        tvMeals.setOnClickListener(this);
        tvMealTime.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivRemove.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        txt_repeat_meal.setOnClickListener(this);
        init();
        setToolBar();
        findViews();
        commingFrom  = getIntent().getStringExtra("commingFrom");
        if(commingFrom.equalsIgnoreCase("Edit")){
            mealModel = (TodaysMeal) getIntent().getSerializableExtra("mealItem");
            tvMealType.setText(mealModel.getMeal_type_name());
            if (mealModel.getIntakeTime()!=null){
                tvMealTime.setText(mealModel.getIntakeTime());

            }else {
                tvMealTime.setText("");

            }





            tvMealType.setEnabled(false);
            FoodListByMealType.Datum item = new FoodListByMealType.Datum();
            item.setRecipeId(mealModel.getFood_Id());
            item.setQuantity(mealModel.getMeal_quantity());
            item.setRecipeImage((mealModel.getMeal_img()!=null)?mealModel.getMeal_img():"");
            item.setRecipeName(mealModel.getMeal_name());
            item.setCalories(mealModel.getMeal_calory());
            item.setUserMealId(mealModel.getUserMealId());
            item.setMealType(mealModel.getMeal_type_name());
            item.setValueInGram(mealModel.getValueInGram());
            commonAddmealList.add(item);
            adapter = new AddMealIntoListAdapter(context, commonAddmealList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(adapter);
        }

        getUOMData();

        if (Utils.isNetworkAvailable(context))
            if(!commingFrom.equalsIgnoreCase("Edit")) {
                getMealType();
            }else{
                getFoodListByType(mealModel.getMeal_type_name());
            }
        else{
            Toast.makeText(getApplicationContext(),"Check internet connection!",Toast.LENGTH_SHORT).show();

        }
        //  showRetryBar("Check internet connection!");
    }

    private void getUOMData() {


        {
            if (!((Activity) context).isFinishing())
            {
                utils.showProgressbar(context);
            }
            Call<ClsFoodUnitMasterPojo> call = foodService.getFoodUnitMasterNewIDWise(3);
            call.enqueue(new Callback<ClsFoodUnitMasterPojo>()
            {
                @Override
                public void onResponse(Call<ClsFoodUnitMasterPojo> call, Response<ClsFoodUnitMasterPojo> response)
                {
                    utils.hideProgressbar();

                    List<String> tempList;
                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsFoodUnitMasterPojo listResponse = response.body();
                        if (listResponse != null && listResponse.getCode().equals("1"))
                        {


                            foodUnitMasterDataArrayList = response.body().getData();
                            if (foodUnitMasterDataArrayList!=null){
                                if (!foodUnitMasterDataArrayList.isEmpty()){

                                    if (commingFrom.equalsIgnoreCase("Edit")){

                                        for (int i = 0; i <foodUnitMasterDataArrayList.size() ; i++) {

                                            if (mealModel.getUomId()==Integer.parseInt(foodUnitMasterDataArrayList.get(i).getId())){
                                                spinner_UOM.setText(foodUnitMasterDataArrayList.get(i).getMeasurement());
                                                strUOmID= Integer.parseInt(foodUnitMasterDataArrayList.get(i).getId());
                                                break;
                                            }

                                        }
                                    }

                                }
                            }


                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsFoodUnitMasterPojo> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    utils.hideProgressbar();
                }
            });
        }
    }

    private void getUOMDataNew(Integer recipeId, final FoodListByMealType.Datum model) {


        {
            if (!((Activity) context).isFinishing())
            {
                utils.showProgressbar(context);
            }
            Call<ClsFoodUnitMasterPojo> call = foodService.getFoodUnitMasterNewIDWise(recipeId);
            call.enqueue(new Callback<ClsFoodUnitMasterPojo>()
            {
                @Override
                public void onResponse(Call<ClsFoodUnitMasterPojo> call, Response<ClsFoodUnitMasterPojo> response)
                {
                    utils.hideProgressbar();

                    List<String> tempList;
                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsFoodUnitMasterPojo listResponse = response.body();
                        if (listResponse != null && listResponse.getCode().equals("1"))
                        {


                            foodUnitMasterDataArrayList = response.body().getData();
                            if (foodUnitMasterDataArrayList!=null){
                                if (!foodUnitMasterDataArrayList.isEmpty()){

                                    Fragment fragment1 = null;
                                    if (foodUnitMasterDataArrayList!=null){
                                        MealData = model;
                                        addFoodDialogFragmet=new AddFoodDialogFragmet(AddMealActivity.this);
                                        Bundle bundle=new Bundle();
//                                        fragment1 = AddFoodDisplayFragment.newInstance("Camera", "100");
                                        bundle.putSerializable("KEY_RepeatMeal",commonAddmealList_filter);
                                        bundle.putString("imagepath",model.getRecipeImagePath());
                                        bundle.putString("meal_name",model.getRecipeName());
                                        bundle.putSerializable("nutrionData",model);
                                        bundle.putString("meal_type",tvMealType.getText().toString());
                                        bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
//                                        bundle.putString("arg1", "argValue");
//                                        bundle.putString("arg2", "argValue22");
                                        addFoodDialogFragmet.setArguments(bundle);


                                        addFoodDialogFragmet.show(getSupportFragmentManager(), addFoodDialogFragmet.getTag());
                                    }



                                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
                                    if (fragment != null)
                                    {
                                        MealByTypeDialog df = (MealByTypeDialog) fragment;
                                        df.dismiss();
                                    }

                                }else {
                                    Toast.makeText(AddMealActivity.this, "UOM is not defined.", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsFoodUnitMasterPojo> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    utils.hideProgressbar();
                }
            });
        }
    }


    private void init()
    {
    }



    @Override
    public void onClick(View view) {
        FragmentManager fm;
        Bundle bundle;

        switch (view.getId()) {
            case R.id.tvMealTypeName:
                fm = getSupportFragmentManager();
                mealTypeDialog = new MealTypeDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", mealType);
                mealTypeDialog.setArguments(bundle);
                mealTypeDialog.show(fm, "COUNTRY_FRAGMENT");
                break;


            case R.id.spinner_UOM:
                fm = getSupportFragmentManager();
                uomDialog = new UOMDialog();
                bundle = new Bundle();

                bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
                uomDialog.setArguments(bundle);
                uomDialog.show(fm, "COUNTRY_FRAGMENT");
                break;


            case R.id.tvMealTime:
                timepickerdialog = new TimePickerDialog(context, AddMealActivity.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);
                timepickerdialog.show();
                break;



            case R.id.tvMeals:


                final MealListByTypeAdapter     countryAdapter = new MealListByTypeAdapter(AddMealActivity.this, arrayListByMealType,  AddMealActivity.this);
                RecyclerView recyclerView=findViewById(R.id.recyclerview_meal);

                RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(manager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(countryAdapter);


                final ArrayList<FoodListByMealType.Datum> bundleList, countryList, countryListSearch;


                countryListSearch = new ArrayList<>();
                countryListSearch.addAll(arrayListByMealType);
//                countryList = new ArrayList<>();


             try {
                 searchView =  findViewById(R.id.searchCountry_meal);
                 searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
                 {
                     @Override
                     public boolean onQueryTextSubmit(String query) { return false; }

                     @Override
                     public boolean onQueryTextChange(String newText)
                     {
                         txt_searcheal.setVisibility(View.GONE);

                         arrayListByMealType.clear();
                         try
                         {
                             if (TextUtils.isEmpty(newText))
                             {
                                 //countryList = countryListSearch;
                                 arrayListByMealType.addAll(countryListSearch);
                             }
                             else
                             {
                                 for ( FoodListByMealType.Datum model:countryListSearch)
                                 {
                                     if (model.getRecipeName().toLowerCase().contains(newText.toLowerCase()))
                                     {
                                         txt_searcheal.setVisibility(View.VISIBLE);
                                         arrayListByMealType.add(model);
                                     }
                                 }
                             }
                         }
                         catch (Exception e)
                         {
                             Log.d("CountryDialog--->", e.toString());
                             e.printStackTrace();
                         }

                         countryAdapter.notifyDataSetChanged();
                         return false;
                     }
                 });

             }catch (Exception e){
                 e.printStackTrace();
             }


//                fm = getSupportFragmentManager();
//                mealByTypeDialog = new MealByTypeDialog();
//                bundle = new Bundle();
//                bundle.putSerializable("COUNTRY_LIST", arrayListByMealType);
//                mealByTypeDialog.setArguments(bundle);
//                mealByTypeDialog.show(fm, "COUNTRY_FRAGMENT");
                break;
            case R.id.ivAdd:
                if (quantityCount < 1000) {
                    quantityCount = quantityCount + 0.25;
                    tvQty.setText(String.valueOf(quantityCount));
                }
                break;
            case R.id.ivRemove:
                if (quantityCount > 0.25) {
                    quantityCount = quantityCount - 0.25;
                    tvQty.setText(String.valueOf(quantityCount));
                }
                break;
            case R.id.tvAdd:
                if(isValidate())

                    if (tvMealTime.getText().toString().trim().equalsIgnoreCase("Select Meal Time")){
                        Toast.makeText(context, "Select Meal Time", Toast.LENGTH_SHORT).show();
                        return ;

                    }

                if (tvMealType.getText().toString() != null && tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")) {
                    Toast.makeText(context, "Select Meal Type", Toast.LENGTH_SHORT).show();
                    return ;
                }


//                if (spinner_UOM.getText().toString().equalsIgnoreCase("Select Measurement")){
//                    Toast.makeText(context, "Please select measurement", Toast.LENGTH_SHORT).show();
//
//                    return;
//                }




//Cheack Wheather Edit if Yes Then Add Only 1 Content
                if(commingFrom.equalsIgnoreCase("Edit")){


                }else {

//                    if (tvMeals.getText().toString() != null && tvMeals.getText().toString().equalsIgnoreCase("Select Meal")) {
//                        Toast.makeText(context, "Select Meal", Toast.LENGTH_SHORT).show();
//                        return;
//                    }




//                    spinner_UOM.setText("Select Measurement");
                    tvMeals.setText("Select Meal");
                    if (commonAddmealList.size() == 0) {
                        String quantity = tvQty.getText().toString();
                        MealData.setQuantity(newQuantity);
                        MealData.setUomId(newUOmID);
                        MealData.setIntakeTime(tvMealTime.getText().toString());
                        if (newValueInGram==0){
                            MealData.setValueInGram(1.0);
                        }else {
                            MealData.setValueInGram(newValueInGram);
                        }
                        MealData.setMealType(tvMealType.getText().toString());

                        commonAddmealList.add(MealData);
                        adapter = new AddMealIntoListAdapter(context, commonAddmealList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(adapter);
                    } else {
                        for (int i = 0; i < commonAddmealList.size(); i++) {
                            if (commonAddmealList.get(i).getRecipeName().toLowerCase().equalsIgnoreCase(MealData.getRecipeName())&&commonAddmealList.get(i).getIntakeTime().equalsIgnoreCase(tvMealTime.getText().toString())) {

                                isPresent = true;
                                finalCount = i;

                            } else {
                                isPresent = false;

                            }
                        }


                        if (isPresent){
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);

                            builder.setMessage("Add Meal").setTitle("Add Meal");

                            final int finalI = finalCount;
                            builder.setMessage("Meal already added,do you want to add qauntity?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            String quantity = tvQty.getText().toString();
                                            MealData.setQuantity(MealData.getQuantity()+newQuantity);
                                            MealData.setUomId(newUOmID);
                                            MealData.setValueInGram(newValueInGram);
                                            MealData.setMealType(tvMealType.getText().toString());
                                            MealData.setIntakeTime(tvMealTime.getText().toString());

                                            commonAddmealList.get(finalI).setQuantity(MealData.getQuantity());
                                            commonAddmealList.get(finalI).setUomId(MealData.getUomId());
                                            commonAddmealList.get(finalI).setValueInGram(newValueInGram);
                                            commonAddmealList.get(finalI).setIntakeTime(MealData.getIntakeTime());

                                            if (adapter != null) {




//                                                        adapter.customArrrylsit(commonAddmealList);
                                                Collections.sort(commonAddmealList,FoodListByMealType.Datum.COMPARE_BY_PHONE);

                                                adapter.notifyDataSetChanged();
                                            }
//                                                    quantityCount = 1;
//                                                    quantityCount++;
//                                                    tvQty.setText(String.valueOf(quantityCount));

                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Add Meal");
                            alert.show();
                        }
                        else{

//                                    MealData= new FoodListByMealType.Datum();

                            /* isPresent==false*/
                            String quantity = tvQty.getText().toString();
                            MealData.setQuantity(newQuantity);
                            MealData.setUomId(newUOmID);
                            MealData.setValueInGram(newValueInGram);
                            MealData.setMealType(tvMealType.getText().toString());
                            MealData.setIntakeTime(tvMealTime.getText().toString());
                            commonAddmealList.add(MealData);
                            if(adapter!=null) {
//                                        adapter.customArrrylsit(commonAddmealList);

                                if (commonAddmealList!=null)
                                    Collections.sort(commonAddmealList,FoodListByMealType.Datum.COMPARE_BY_PHONE);

                                adapter.notifyDataSetChanged();
                            }
                            quantityCount = 1;
                            tvQty.setText(String.valueOf(1));
                        }


                    }
                }

                break;

            case R.id.txt_repeat_meal:




                if (tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")){
                    Toast.makeText(context, "Please select Meal Type", Toast.LENGTH_SHORT).show();
                    return;


                }




                getAllYestserDaysMeal();


                break;
            case R.id.btnSave:
                if (Utils.isNetworkAvailable(context)) {


//                    addDatatolistfromRepeat();





                     List<FoodListByMealType.Datum> filtermList;
                     boolean isFondRepeat=false;

                    filtermList=new ArrayList<>();
                    for (int i = 0; i <commonAddmealList_filter.size() ; i++) {
                        if (commonAddmealList_filter.get(i).isSelect()){
                            filtermList.add(commonAddmealList_filter.get(i));
                            isFondRepeat=true;
                        }
                    }
                    if (filtermList.isEmpty()&&isFondRepeat){
                        Toast.makeText(context, "Please select meal", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isFondRepeat){
                        isFondRepeat=false;

//                        getSelectedRepeatMealFood(filtermList);

                        saveRepeatMeal(filtermList);
                    }else {
                        if(commingFrom.equalsIgnoreCase("New")) {
                            if(isValidateForSave()) {

                                if (tvMealTime.getText().toString().trim().equalsIgnoreCase("Select Meal Time")){
                                    Toast.makeText(context, "Select Meal Time", Toast.LENGTH_SHORT).show();
                                    return ;

                                }

                                if (tvMealType.getText().toString() != null && tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")) {
                                    Toast.makeText(context, "Select Meal Type", Toast.LENGTH_SHORT).show();
                                    return ;
                                }

                                if (commonAddmealList!=null&&!commonAddmealList.isEmpty()){
                                    saveTodysMeal();

                                }else {
                                    Toast.makeText(context, "Please select meal", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }else{
                            if (!tvMealTime.getText().toString().trim().isEmpty()){
                                editTodysMeal();

                            }else {
                                Toast.makeText(context, "Please select meal time", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }



                }else{

                }
                break;


        }
    }

//    private void addDatatolistfromRepeat() {
//        if (filtermList!=null&&!filtermList.isEmpty()){
//            for (int i = 0; i <filtermList.size() ; i++) {
//
//
//                double valuegramsubmit=filtermList.get(i).getValueInGram();
//                if (valuegramsubmit==0){
//                    valuegramsubmit=1.0;
//                }else {
//                    valuegramsubmit=filtermList.get(i).getValueInGram();
//                }
//                double qty=filtermList.get(i).getQuantity()*valuegramsubmit;
//                double singlecal=Double.parseDouble(filtermList.get(i).getCalories())/qty;
//
//
//
//
//                filtermList.get(i).setCalories(String.valueOf(singlecal));
//
//
//            }
//
//
//
//
//            commonAddmealList.addAll(filtermList);
//        }
//
//        adapter = new AddMealIntoListAdapter(context, commonAddmealList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        recyclerview.setLayoutManager(layoutManager);
//        recyclerview.setItemAnimator(new DefaultItemAnimator());
//        recyclerview.setAdapter(adapter);
//    }


    public boolean isValidateForSave() {

        if (tvMealType.getText().toString() != null && tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")) {
//            Toast.makeText(context, "Select Meal Type", Toast.LENGTH_SHORT).show();
            return true;
        } else if (commonAddmealList.size() == 0) {
//            Toast.makeText(context, "Select Meal", Toast.LENGTH_SHORT).show();
            return true;

        }
        return true;
    }



    public boolean isValidate() {
        if (tvMealType.getText().toString() != null && tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")) {
//            Toast.makeText(context, "Select Meal Type", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (tvMeals.getText().toString() != null && tvMeals.getText().toString().equalsIgnoreCase("Select Meal")){
//            Toast.makeText(context, "Select Meal", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }
    private void getFoodListByType(String MealType)

    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        FoodListByType request = new FoodListByType();
        request.setMeal_Type(MealType);

        Call<FoodListByMealType> call = foodService.foodListByType(request);
        call.enqueue(new Callback<FoodListByMealType>()
        {
            @Override
            public void onResponse(Call<FoodListByMealType> call, Response<FoodListByMealType> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodListByMealType listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<FoodListByMealType.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {
                            arrayListByMealType.clear();
                            arrayListByMealType.addAll(data);

                            tvMeals.performClick();

                            txt_repeat_meal.performClick();

                            txt_viewaddedmeal.setVisibility(View.VISIBLE);
                            rel_parent.setVisibility(View.VISIBLE);



                        }else {
                            arrayListByMealType.clear();
                            arrayListByMealType.addAll(data);

                            tvMeals.performClick();

                            txt_repeat_meal.performClick();

                            txt_viewaddedmeal.setVisibility(View.VISIBLE);
                            rel_parent.setVisibility(View.VISIBLE);

                        }
                    }
                    else
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodListByMealType> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void getMealType()

    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        Call<GetMealType> call = foodService.getMealType();
        call.enqueue(new Callback<GetMealType>()
        {
            @Override
            public void onResponse(Call<GetMealType> call, Response<GetMealType> response)
            {
                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetMealType listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        tempList = response.body().getData();

                        if (tempList!= null && tempList.size() > 0)
                        {
                            mealType.clear();
                            mealType.addAll(tempList);
                        }
                    }
                    else
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMealType> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
                ShowRetryBar("Unable to load data");
            }
        });
    }

    private void ShowRetryBar(String msg)
    {

        String strMessage;
        if (msg.isEmpty())
        {
            strMessage = "Please try again";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(AddMealActivity.this))
                        {
                            getMealType();

                        }

                    }
                });

        snackbar.show();
    }

    private void saveTodysMeal() {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        ArrayList< AddMealRequest.LstSubMealDatum> itemDataList = new ArrayList<>();
        AddMealRequest request = new AddMealRequest();
        request.setUserId(userId);
        request.setMealType(strMealType);
        request.setIntakeTime(tvMealTime.getText().toString().trim());
        Intent intent = getIntent();
        String HistoryDate = intent.getStringExtra("HistoryDate");



        if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
//            sessionManager.setStringValue("statusdate", sessionManager.getStringValue("Entrystatusdate"));
            request.setCreatedOn(sessionManager.getStringValue("Entrystatusdate"));


        }else {
            request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        }

//        request.setCreatedOn(sessionManager.getStringValue("statusdate"));
        for(int i = 0 ; i<commonAddmealList.size(); i++){
            AddMealRequest.LstSubMealDatum  ItemData  = new AddMealRequest.LstSubMealDatum();
            ItemData.setRecipeId(commonAddmealList.get(i).getRecipeId());
            ItemData.setMealQty(String.valueOf(commonAddmealList.get(i).getQuantity()));
            ItemData.setIntakeTime(commonAddmealList.get(i).getIntakeTime());

            ItemData.setUomId(commonAddmealList.get(i).getUomId());

            ItemData.setValueInGram(commonAddmealList.get(i).getValueInGram());
            ItemData.setMeal_type(commonAddmealList.get(i).getMealType().toString());

            itemDataList.add(ItemData);


        }
        request.setLstSubMealData(itemDataList);
        String S   = new Gson().toJson(request);
        String Test = S;
        Call<GetMealType> call = foodService.saveToadysMeal(request);
        call.enqueue(new Callback<GetMealType>()
        {
            @Override
            public void onResponse(Call<GetMealType> call, Response<GetMealType> response)
            {tvMealTime.setText("Select Meal Time");

                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetMealType listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent  intent = new Intent(context, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        intent.putExtra("ISAddMeal",true);
//                        startActivity(intent);

                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else
                    {
                        Intent  intent = new Intent(context, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        intent.putExtra("ISAddMeal",true);
//                        startActivity(intent);

                        setResult(RESULT_OK,intent);
                        if (!listResponse.getMessage().isEmpty())
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMealType> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void saveRepeatMeal(List<FoodListByMealType.Datum> commonAddmealList) {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        ArrayList< AddMealRequest.LstSubMealDatum> itemDataList = new ArrayList<>();
        AddMealRequest request = new AddMealRequest();
        request.setUserId(userId);
        request.setMealType(mealType.toString());
        request.setIntakeTime(tvMealTime.getText().toString().trim());
        Intent intent = getIntent();
        String HistoryDate = intent.getStringExtra("HistoryDate");



        if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
//            sessionManager.setStringValue("statusdate", sessionManager.getStringValue("Entrystatusdate"));
            request.setCreatedOn(sessionManager.getStringValue("Entrystatusdate"));


        }else {
            request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        }

//        request.setCreatedOn(sessionManager.getStringValue("statusdate"));
        for(int i = 0 ; i<commonAddmealList.size(); i++){
            AddMealRequest.LstSubMealDatum  ItemData  = new AddMealRequest.LstSubMealDatum();
            ItemData.setRecipeId(commonAddmealList.get(i).getRecipeId());
            ItemData.setMealQty(String.valueOf(commonAddmealList.get(i).getQuantity()));
            ItemData.setIntakeTime(commonAddmealList.get(i).getIntakeTime());

            ItemData.setUomId(commonAddmealList.get(i).getUomId());

            ItemData.setValueInGram(commonAddmealList.get(i).getValueInGram());
            ItemData.setMeal_type(commonAddmealList.get(i).getMealType().toString());

            itemDataList.add(ItemData);


        }
        request.setLstSubMealData(itemDataList);
        String S   = new Gson().toJson(request);
        String Test = S;
        Call<GetMealType> call = foodService.saveToadysMeal(request);
        call.enqueue(new Callback<GetMealType>()
        {
            @Override
            public void onResponse(Call<GetMealType> call, Response<GetMealType> response)
            {tvMealTime.setText("Select Meal Time");

                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetMealType listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent  intent = new Intent(context, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        intent.putExtra("ISAddMeal",true);
//                        startActivity(intent);

                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else
                    {
                        Intent  intent = new Intent(context, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        intent.putExtra("ISAddMeal",true);
//                        startActivity(intent);

                        setResult(RESULT_OK,intent);
                        if (!listResponse.getMessage().isEmpty())
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMealType> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }



    private void editTodysMeal()

    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        EditMealRequest request = new EditMealRequest();
        request.setUserId(userId);
        request.setFoodId(commonAddmealList.get(0).getRecipeId());
        request.setMealQty(commonAddmealList.get(0).getQuantity());
        request.setCreatedOn(commonAddmealList.get(0).getCreatedOn());
        request.setIntakeTime(tvMealTime.getText().toString().trim());

        Call<GetMealType> call = foodService.editToadysMeal(request);
        call.enqueue(new Callback<GetMealType>()
        {
            @Override
            public void onResponse(Call<GetMealType> call, Response<GetMealType> response)
            {
                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetMealType listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent  intent = new Intent(context, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent  intent = new Intent(context, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        startActivity(intent);
                        finish();
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMealType> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }
  /*  private void showRetryBar(String msg)
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

        Snackbar snackbar = Snackbar.make(getApplicationContext().findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(mContext))
                        {
                            callToGetAllRecipies();
                        }
                        else
                        {
                            showRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }*/

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Add your meal");
//        imgLeft.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                finish();
//            }
//        });
    }

    private void findViews()
    {
    }

    @Override
    public void onSubmitMealTypeData(final String model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null) {
            MealTypeDialog df = (MealTypeDialog) fragment;
            df.dismiss();
        }
        try {
            if (model != null) {

                //check for 1st time entry
                if (txtcommonMealType.equalsIgnoreCase("")) {
                    if (model != null) {
                        strMealType = model;

                        if (!TextUtils.isEmpty(model)) {
                            tvMealType.setText(model);

                        } else
                            tvMealType.setText("Select Meal Type");

                        if (strMealType != null && !strMealType.equalsIgnoreCase("")) {
                            tvMeals.setText("Select Meal");
                            txtcommonMealType = strMealType;
                            getFoodListByType(model);
                        }
                    }
                } else {
                    //if previous and current meal type same
                    if (!txtcommonMealType.equalsIgnoreCase(model)) {

                        if (commonAddmealList.size() != 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);

                            builder.setMessage("Add Meal").setTitle("Add Meal");

                            //Setting message manually and performing action on button click
                            builder.setMessage("Do You Want to add more meal?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            if (model != null) {
                                                strMealType = model;
                                                if (!TextUtils.isEmpty(model)) {
                                                    tvMealType.setText(model);
                                                } else
                                                    tvMealType.setText("Select Meal Type");
                                                if (strMealType != null && !strMealType.equalsIgnoreCase("")) {
                                                    tvMeals.setText("Select Meal");
                                                    txtcommonMealType = strMealType;


//                                                commonAddmealList.clear();
//
                                                    if (adapter != null) {
//                                                    adapter.customArrrylsit(commonAddmealList);

                                                        Collections.sort(commonAddmealList,FoodListByMealType.Datum.COMPARE_BY_PHONE);
                                                        adapter.notifyDataSetChanged();
                                                    }
                                                    quantityCount = 1;
                                                    tvQty.setText(String.valueOf(1));
                                                    getFoodListByType(model);
                                                }
                                            }


                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            tvMealType.setText(txtcommonMealType);
                                            dialog.cancel();
                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Add Meal");
                            alert.show();
                        }else{
                            if (model != null) {
                                strMealType = model;

                                if (!TextUtils.isEmpty(model)) {
                                    tvMealType.setText(model);

                                } else
                                    tvMealType.setText("Select Meal Type");

                                if (strMealType != null && !strMealType.equalsIgnoreCase("")) {
                                    tvMeals.setText("Select Meal");
                                    txtcommonMealType = strMealType;
                                    quantityCount=1;
                                    tvQty.setText(String.valueOf(1));
                                    getFoodListByType(model);
                                }
                            }
                        }
                    }else{
                        //if previous and current meal type not same
                        if (model != null) {
                            strMealType = model;

                            if (!TextUtils.isEmpty(model)) {
                                tvMealType.setText(model);

                            } else
                                tvMealType.setText("Select Meal Type");

                            if (strMealType != null && !strMealType.equalsIgnoreCase("")) {
                                tvMeals.setText("Select Meal");
                                txtcommonMealType = strMealType;
                                quantityCount=1;
                                tvQty.setText(String.valueOf(1));
                                getFoodListByType(model);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubmitMealTypeData(FoodListByMealType.Datum model) {

        getUOMDataNew(model.getRecipeId(),model);

//        if (foodUnitMasterDataArrayList!=null){
//            MealData = model;
//            addFoodDialogFragmet=new AddFoodDialogFragmet(AddMealActivity.this);
//            Bundle bundle=new Bundle();
//            bundle.putSerializable("KEY_RepeatMeal",commonAddmealList_filter);
//            bundle.putString("imagepath",model.getRecipeImagePath());
//            bundle.putString("meal_name",model.getRecipeName());
//            bundle.putSerializable("nutrionData",model);
//            bundle.putString("meal_type",tvMealType.getText().toString());
//            bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
//
//            addFoodDialogFragmet.setArguments(bundle);
//
//
//            addFoodDialogFragmet.show(getSupportFragmentManager(), addFoodDialogFragmet.getTag());
//        }
//
//
//
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
//        if (fragment != null)
//      {
//            MealByTypeDialog df = (MealByTypeDialog) fragment;
//            df.dismiss();
//        }


    }

    @Override
    public void onRemove(int position, FoodListByMealType.Datum model) {
        commonAddmealList.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String lsTimeFrom = formatter.format(cal.getTime());
        tvMealTime.setText(lsTimeFrom);


    }

//    @Override
//    public void GetUOMPosition(int pos, FoodUnitMasterData model) {
//
//    }

    @Override
    public void onSubmitUOMData(FoodUnitMasterData model) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null) {
            UOMDialog df = (UOMDialog) fragment;
            df.dismiss();
        }


        spinner_UOM.setText(model.getMeasurement());
        double_valueInGram=model.getValueInGram();
        strUOmID= Integer.parseInt(model.getId());




    }


    private void getAllYestserDaysMeal()

    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }






        Call<ClsRepeatTodaysMeal> call = foodService.getRepeatsMeal(userId,tvMealType.getText().toString());
        call.enqueue(new Callback<ClsRepeatTodaysMeal>()
        {
            @Override
            public void onResponse(Call<ClsRepeatTodaysMeal> call, Response<ClsRepeatTodaysMeal> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {

                    if (commonAddmealList_filter!=null){
                        commonAddmealList_filter.clear();
                    }


                    ClsRepeatTodaysMeal listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        List<ClsRepeatMealData> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {

                            try{


                                for (int i = 0; i < data.size(); i++)
                                {


                                    for (int j = 0; j < data.get(i).getLst_SubMealData().size(); j++) {
                                        MealData=new FoodListByMealType.Datum();
                                        MealData.setQuantity(Double.valueOf(data.get(i).getLst_SubMealData().get(j).getMeal_qty()));
                                        MealData.setUomId(data.get(i).getLst_SubMealData().get(j).getUomId());
                                        MealData.setValueInGram(data.get(i).getLst_SubMealData().get(j).getValueInGram());
                                        MealData.setMealType(data.get(i).getMeal_type());
                                        MealData.setCalories(String.valueOf(data.get(i).getLst_SubMealData().get(j).getMeal_calory()));
                                        MealData.setUserMealId(data.get(i).getLst_SubMealData().get(j).getUserMealId());
                                        MealData.setRecipeId(data.get(i).getLst_SubMealData().get(j).getRecipeId());
                                        MealData.setRecipeName(data.get(i).getLst_SubMealData().get(j).getMeal_name());
                                        MealData.setUnit(data.get(i).getLst_SubMealData().get(j).getUnitText());
                                        MealData.setRecipeImagePath(data.get(i).getLst_SubMealData().get(j).getRecipeImagePath());
                                        commonAddmealList_filter.add(MealData);

                                    }
                                }

                                Collections.sort(commonAddmealList_filter,FoodListByMealType.Datum.COMPARE_BY_PHONE);


//                                AddFoodDialogFragmet fragment=new AddFoodDialogFragmet(AddMealActivity.this);
                                FoodRepeatMealFragmet fragment = new FoodRepeatMealFragmet(AddMealActivity.this);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("KEY_RepeatMeal",commonAddmealList_filter);
                                bundle.putStringArrayList("KEY_MEAL_TYPE",mealType);
                                bundle.putString("time",tvMealTime.getText().toString());
                                fragment.setArguments(bundle);

//                                fragment.show(getSupportFragmentManager(), fragment.getTag());





//                                commonAddmealList= (ArrayList<FoodListByMealType.Datum>) bundle.getSerializable("KEY_RepeatMeal");
//                                arylst_meal_list=bundle.getStringArrayList("KEY_MEAL_TYPE");
//                                time=bundle.getString("time");
                                RepeatMealIntoListAdapter   adapter = new RepeatMealIntoListAdapter(context, commonAddmealList_filter,AddMealActivity.this,mealType,tvMealTime.getText().toString());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                recycler_repeat_meal.setLayoutManager(layoutManager);
                                recycler_repeat_meal.setItemAnimator(new DefaultItemAnimator());
                                recycler_repeat_meal.setAdapter(adapter);


                                try {
                                    if (commonAddmealList_filter.size()>1){
                                        txt_showaddedrepeat.setVisibility(View.VISIBLE);

                                    }
                                }catch (Exception w){
                                    w.printStackTrace();
                                    txt_showaddedrepeat.setVisibility(View.GONE);
                                }









//                                adapter = new AddMealIntoListAdapter(context, commonAddmealList);
//                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//                                recyclerview.setLayoutManager(layoutManager);
//                                recyclerview.setItemAnimator(new DefaultItemAnimator());
//                                recyclerview.setAdapter(adapter);



                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(AddMealActivity.this, "No Meal found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(AddMealActivity.this, "No Meal found", Toast.LENGTH_SHORT).show();


                    }
                }

                sessionManager.setStringValue("IsShowMSg","true");

            }

            @Override
            public void onFailure(Call<ClsRepeatTodaysMeal> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void getSelectedRepeatMealFood(List<FoodListByMealType.Datum> filtermList) {

        if (filtermList!=null&&!filtermList.isEmpty()){
            for (int i = 0; i <filtermList.size() ; i++) {


                double valuegramsubmit=filtermList.get(i).getValueInGram();
                if (valuegramsubmit==0){
                    valuegramsubmit=1.0;
                }else {
                    valuegramsubmit=filtermList.get(i).getValueInGram();
                }
                double qty=filtermList.get(i).getQuantity()*valuegramsubmit;
                double singlecal=Double.parseDouble(filtermList.get(i).getCalories())/qty;




                filtermList.get(i).setCalories(String.valueOf(singlecal));


            }




            commonAddmealList.addAll(filtermList);
        }

        adapter = new AddMealIntoListAdapter(context, commonAddmealList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);

    }

    @Override
    public void getaddMealData(String uomID, double valueingrma, double quantity) {

        newQuantity=quantity;
        newUOmID= Integer.parseInt(uomID);
        newValueInGram=valueingrma;
        Log.d("newValueInGram", String.valueOf(newValueInGram));
        tvAdd.performClick();

    }

    @Override
    public void GetMealPosition(int pos, FoodListByMealType.Datum model) {
        getUOMDataNew(model.getRecipeId(),model);


    }

    @Override
    public void onAddRepeatMealSlected() {
//        btnSave_repeat.performClick();
    }
}
