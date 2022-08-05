package com.shamrock.reework.activity.FoodModule.activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.EditMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.fragment.UpdateFoodDialogFragmet;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodUnitMasterPojo;
import com.shamrock.reework.activity.FoodModule.model.EditMealRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.FoodModule.model.RemoveMealItem;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.FoodModule.service.OnGetUpdateFoodDailogData;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMealActivity extends AppCompatActivity implements OnGetUpdateFoodDailogData, UOMDialog.GetUOMListener,TimePickerDialog.OnTimeSetListener,View.OnClickListener, MealTypeDialog.GetMealTypeListener, MealByTypeDialog.GetMealTypeListener, EditMealIntoListAdapter.OnMealRemoveListner
{
    Context context;
    Toolbar toolbar;
    private Utils utils;
    int userId;
    boolean isSameadd=false;
    SessionManager sessionManager;
    FoodService foodService;
    TextView tvMealType,tvMeals,tvMealTime;
    ArrayList<String> mealType = new ArrayList<>();
    ArrayList<FoodListByMealType.Datum> arrayListByMealType = new ArrayList<>();
    private MealTypeDialog mealTypeDialog;
    private  MealByTypeDialog mealByTypeDialog;
    String strMealType;
    ImageView ivRemove,ivAdd;
    double quantityCount=0.0;
    TextView tvAdd;
    EditText tvQty;
    Button btnSave;
    RecyclerView recyclerview;
    ArrayList<FoodListByMealType.Datum> commonAddmealList = new ArrayList<>();
    ArrayList<FoodListByMealType.Datum> commonAddmealFinalList = new ArrayList<>();
    FoodListByMealType.Datum MealData;
    EditMealIntoListAdapter adapter = null;
    TodaysMeal mealModel;
    String commingFrom;
    int userMealId;
    int lastUserMealID;
    boolean isRemoveMealItem;
    String strCreatedOnDate;
    double newquantity;
    double sinlecalnew;
    private TimePickerDialog timepickerdialog;
    String tempRecipe="";
    TextView txt_uom_measurement;
    ArrayList<FoodUnitMasterData>    foodUnitMasterDataArrayList;
    private UOMDialog uomDialog;
    private double double_valueInGram;
    private int strUOmID;

    private void getUOMData() {


        {
            if (!((Activity) context).isFinishing())
            {
                utils.showProgressbar(context);
            }
            Call<ClsFoodUnitMasterPojo> call = foodService.getFoodUnitMaster();
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
                                        mealModel = (TodaysMeal) getIntent().getSerializableExtra("mealItem");


                                        for (int i = 0; i <foodUnitMasterDataArrayList.size() ; i++) {

                                            if (mealModel.getUomId()==Integer.parseInt(foodUnitMasterDataArrayList.get(i).getId())){
                                                txt_uom_measurement.setText(foodUnitMasterDataArrayList.get(i).getMeasurement());
                                                strUOmID= Integer.parseInt(foodUnitMasterDataArrayList.get(i).getId());
                                                double_valueInGram=foodUnitMasterDataArrayList.get(i).getValueInGram();
                                                break;
                                            }

                                        }
                                    }


                                    if(commingFrom.equalsIgnoreCase("Edit")){
//                                        mealModel = (TodaysMeal) getIntent().getSerializableExtra("mealItem");
                                        tvMealType.setText(mealModel.getMeal_type_name());


                                        if (mealModel.getIntakeTime()!=null){
                                            tvMealTime.setText(mealModel.getIntakeTime());

                                        }


                                        tvMealType.setEnabled(true);
                                        FoodListByMealType.Datum item = new FoodListByMealType.Datum();
                                        item.setUserMealId(mealModel.getUserMealId());
                                        lastUserMealID = mealModel.getUserMealId();
                                        item.setRecipeId(mealModel.getFood_Id());
                                        item.setQuantity(mealModel.getMeal_quantity());
                                        item.setRecipeImage((mealModel.getMeal_img()!=null)?mealModel.getMeal_img():"");
                                        item.setRecipeName(mealModel.getMeal_name());
                                        item.setCalories(mealModel.getMeal_calory());
                                        item.setMealType(mealModel.getMeal_type_name());
                                        item.setIntakeTime(mealModel.getIntakeTime());
                                        item.setProtin(mealModel.getProtin());
                                        item.setFibre(mealModel.getFibre());
                                        item.setFat(mealModel.getFat());
                                        item.setCarb(mealModel.getCarbs());


                                        strUOmID=mealModel.getUomId();
                                        item.setUomId(mealModel.getUomId());
//                                        double_valueInGram=mealModel.getValueInGram();
//                                        item.setValueInGram(mealModel.getValueInGram());
                                        item.setValueInGram(double_valueInGram);


                                        double single=Double.parseDouble(mealModel.getMeal_calory())/mealModel.getMeal_quantity();

                                        item.setSingleCal(String.valueOf(single));
                                        item.setRecipeImagePath(mealModel.getRecipeImagePath());


                                        commonAddmealList.add(item);
                                        adapter = new EditMealIntoListAdapter(context, commonAddmealList,"None", EditMealActivity.this,foodUnitMasterDataArrayList);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditMealActivity.this);
                                        recyclerview.setLayoutManager(layoutManager);
                                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                                        recyclerview.setAdapter(adapter);
                                    }
                                    if (Utils.isNetworkAvailable(context))
                                        if(!commingFrom.equalsIgnoreCase("Edit")) {
                                            getMealType();
                                        }else{
                                            getMealType();

                                            getFoodListByType(mealModel.getMeal_type_name());
                                        }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Check internet connection!",Toast.LENGTH_SHORT).show();

                                    }





//                                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item, stringArrayList_uom);
////                                adapter.setDropDownViewResource(android.R.layout.simpl/e_spinner_dropdown_item);
//                                spinner_UOM.setAdapter(adapter);
//                                spinner_UOM.setSelection(0);

//                                spinner_UOM
                                }
                            }


                        }
                        else
                        {
//                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);

        context = EditMealActivity.this;

        utils = new Utils();

        txt_uom_measurement=findViewById(R.id.txt_uom_measurement);
        txt_uom_measurement.setOnClickListener(this);
        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
        tvMealType = findViewById(R.id.tvMealTypeName);
        tvMealTime = findViewById(R.id.tvMealTime);
        tvMeals = findViewById(R.id.tvMeals);
        ivRemove = findViewById(R.id.ivRemove);
        tvAdd = findViewById(R.id.tvAdd);
        ivAdd = findViewById(R.id.ivAdd);
        tvQty = findViewById(R.id.tvQty);
        tvQty.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});

        recyclerview = findViewById(R.id.recyclerview);
        btnSave = findViewById(R.id.btnSave);
        tvMealType.setOnClickListener(this);
        tvMeals.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        ivRemove.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        tvMealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtimeclock();
            }
        });
        init();
        setToolBar();
        findViews();
        commingFrom  = getIntent().getStringExtra("commingFrom");
        strCreatedOnDate=getIntent().getStringExtra("CreatedonDate");
        getUOMData();




//        getFoodListByType(tvMealType.getText().toString());



        //  showRe

    }
    private void init()
    {
    }


    public void showtimeclock(){
        timepickerdialog = new TimePickerDialog(context, EditMealActivity.this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false);
        timepickerdialog.show();

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


            case R.id.txt_uom_measurement:

                fm = getSupportFragmentManager();
                uomDialog = new UOMDialog();
                bundle = new Bundle();

                bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
                uomDialog.setArguments(bundle);
                uomDialog.show(fm, "COUNTRY_FRAGMENT");





                break;




            case R.id.tvMeals:

                fm = getSupportFragmentManager();
                mealByTypeDialog = new MealByTypeDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arrayListByMealType);
                mealByTypeDialog.setArguments(bundle);
                mealByTypeDialog.show(fm, "COUNTRY_FRAGMENT");
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
                    tvQty.setText(String.valueOf(1));
                }
                break;
            case R.id.tvAdd:
//Cheack Wheather Edit if Yes Then Add Only 1 Content
                /* if(commingFrom.equalsIgnoreCase("Edit")){*/
                if (tvMeals.getText().toString().equalsIgnoreCase("Select Meal")){
                    isRemoveMealItem = false;
                tvMeals.setText("Select Meal");
                if (commonAddmealList.size() == 1) {
                    commonAddmealList.clear();
                    commonAddmealFinalList.clear();
                    boolean isFoundMealtype=false;
                    String strtvMealTypeName=tvMealType.getText().toString();
                    String quantity = String.valueOf(quantityCount);
                    double dbl_qntitiy=Double.valueOf(quantity);
                    if (!quantity.equalsIgnoreCase("")) {

                        double singlecal;
                        MealData.setQuantity(Double.parseDouble(quantity));
                        if (isSameadd){



                             singlecal=sinlecalnew;
                            isSameadd=false;

                        }else {
                             singlecal=Double.parseDouble(MealData.getCalories())/4;

                        }

                        double counts=Double.parseDouble(quantity)/0.25;
                        double finalcal=singlecal*counts;

//                        MealData.setCalories(String.valueOf(finalcal));
                        MealData.setUomId(strUOmID);
                        MealData.setMealType(strtvMealTypeName);
                        MealData.setValueInGram(double_valueInGram);

                    }

                    for (int i = 0; i <commonAddmealList.size() ; i++) {

                        if ((strtvMealTypeName.equalsIgnoreCase(commonAddmealList.get(i).getMealType().toString())&&MealData.getRecipeName().equalsIgnoreCase(commonAddmealList.get(i).getRecipeName().toString())))
                        {
                            isFoundMealtype=true;

                            double qun= commonAddmealList.get(i).getQuantity();
                            commonAddmealList.get(i).setQuantity(qun+dbl_qntitiy);
                            break;
                        }

                    }
                    if (!isFoundMealtype){
                        commonAddmealList.add(MealData);

                    }





                    commonAddmealFinalList.add(MealData);
                    adapter = new EditMealIntoListAdapter(context, commonAddmealList, "AddAction",this, foodUnitMasterDataArrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                    recyclerview.setLayoutManager(layoutManager);
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(adapter);
                }else{
                    boolean isFoundMealtype=false;
                    String quantity = String.valueOf(quantityCount);
                    double dbl_qntitiy=Double.valueOf(quantity);
                    String strtvMealTypeName=tvMealType.getText().toString();

                    MealData.setQuantity(quantityCount);
                    MealData.setUomId(strUOmID);
                    MealData.setValueInGram(double_valueInGram);


                    for (int i = 0; i <commonAddmealFinalList.size() ; i++) {

                        if ((strtvMealTypeName.equalsIgnoreCase(commonAddmealFinalList.get(i).getMealType().toString())&&MealData.getRecipeName().equalsIgnoreCase(commonAddmealFinalList.get(i).getRecipeName().toString())))
                        {
                            isFoundMealtype=true;

                            double qun= commonAddmealFinalList.get(i).getQuantity();
                            commonAddmealFinalList.get(i).setQuantity(qun+dbl_qntitiy);
                            break;
                        }

                    }



                    if (!isFoundMealtype){
                        commonAddmealList.add(MealData);

                    }
                    isFoundMealtype=false;

                    commonAddmealFinalList.add(MealData);
                    adapter = new EditMealIntoListAdapter(context, commonAddmealList, "AddAction",EditMealActivity.this, foodUnitMasterDataArrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                    recyclerview.setLayoutManager(layoutManager);
                    recyclerview.setItemAnimator(new DefaultItemAnimator());
                    recyclerview.setAdapter(adapter);
                }
                quantityCount = 1;
//                quantityCount++;
                tvQty.setText(String.valueOf(1));


                /* }*//*else {
                    tvMeals.setText("Select Meal");
                    if (commonAddmealList.size() == 0) {
                        String quantity = tvQty.getText().toString();
                        if (quantity != null && !quantity.equalsIgnoreCase("")) {
                            MealData.setQuantity(Integer.valueOf(quantity));
                        }
                        commonAddmealList.add(MealData);
                        commonAddmealFinalList.add(MealData);
                        adapter = new EditMealIntoListAdapter(context, commonAddmealList,"AddAction");
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(adapter);
                    } else {
                        String quantity = tvQty.getText().toString();
                        if (quantity != null && !quantity.equalsIgnoreCase("")) {
                            MealData.setQuantity(Integer.valueOf(quantity));
                        }
                        commonAddmealList.add(MealData);
                        adapter.notifyDataSetChanged();
                    }
                    quantityCount = 0;
                    quantityCount++;
                    tvQty.setText(String.valueOf(quantityCount));
                }*/
        }else
                    Toast.makeText(context, "Please Select Meal", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnSave:
                if (Utils.isNetworkAvailable(context)) {

                    if (!tvMealTime.getText().toString().isEmpty()){
                        editTodysMeal();


                    }else {
                        Toast.makeText(context, "Please Select Meal Time", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(context, "Please Check Intenet Connnection..", Toast.LENGTH_SHORT).show();
                }
                break;


        }
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

                tvMeals.setText("Select Meal");
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
            }
        });
    }




    private void editTodysMeal()

    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        Call<GetMealType> call;
        if(isRemoveMealItem==true){

            RemoveMealItem removeMealItemRequest = new RemoveMealItem();
            removeMealItemRequest.setUserMealId(lastUserMealID);
            removeMealItemRequest.setCreatedOn(strCreatedOnDate);
            call = foodService.removeToadysMealItem(removeMealItemRequest);
        }else {
            EditMealRequest request = new EditMealRequest();
            request.setUserId(lastUserMealID);
//            request.setUserId(commonAddmealList.get(0).getUserMealId());
            request.setFoodId(commonAddmealList.get(0).getRecipeId());
            request.setMealQty(commonAddmealList.get(0).getQuantity());
            request.setIntakeTime(tvMealTime.getText().toString().trim());
//            request.setUomId(commonAddmealList.get(0).getUomId());
            request.setUomId(commonAddmealList.get(0).getUomId());


//            request.setValueInGram(commonAddmealList.get(0).getValueInGram());

            if (commonAddmealList.get(0).getValueInGram()==0){
                request.setValueInGram(1.0);

            }else {
                request.setValueInGram(commonAddmealList.get(0).getValueInGram());

            }
            request.setCreatedOn(strCreatedOnDate);
            request.setMealType(tvMealType.getText().toString());

//            for (int i = 0; i < commonAddmealList.size(); i++) {
//
//                EditMealRequest request1 = new EditMealRequest();
//                request.setUserId(commonAddmealList.get(i).getUserMealId());
//                request.setFoodId(commonAddmealList.get(i).getRecipeId());
//                request.setMealQty(commonAddmealList.get(i).getQuantity());
//                request.setIntakeTime(tvMealTime.getText().toString().trim());
//                request.setUomId(commonAddmealList.get(i).getUomId());
//                request.setValueInGram(commonAddmealList.get(i).getValueInGram());
//                request.setCreatedOn(commonAddmealList.get(i).getCreatedOn());
//                request.setMealType(commonAddmealList.get(i).getMealType().toString());
//
//
//
//            }

            call = foodService.editToadysMeal(request);
        }
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
                        intent.putExtra("ISAddMeal",true);
//                        startActivity(intent);
                        setResult(RESULT_OK,intent);
                        finish();


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
            }
        });
    }


    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Update your meal");
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
    }

    @Override
    public void onSubmitMealTypeData(String model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            MealTypeDialog df = (MealTypeDialog) fragment;
            df.dismiss();
        }

        if (model != null)
        {
            strMealType = model;

            if (!TextUtils.isEmpty(model)) {
                tvMealType.setText(model);


            }
            else



                tvMealType.setText("Select Meal Type");

            if (strMealType!=null && !strMealType.equalsIgnoreCase(""))
            {
                getFoodListByType(model);
            }
        }
    }

    @Override
    public void onSubmitMealTypeData(FoodListByMealType.Datum model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            MealByTypeDialog df = (MealByTypeDialog) fragment;
            df.dismiss();
        }
        if (tempRecipe.equalsIgnoreCase(model.getRecipeName())){
            isSameadd=true;
            newquantity=model.getQuantity()/0.25;
            tempRecipe="";
            sinlecalnew=Double.parseDouble(model.getCalories())/newquantity;



        }else {
            isSameadd=false;
        }

//        lastUserMealID=model.getRecipeId();
        quantityCount=1;
//        quantityCount++;
        tvQty.setText(String.valueOf(1));
//        tvMeals.setText(model.getRecipeName());
        tempRecipe=model.getRecipeName();
        MealData = model;

        //new sunit
        UpdateFoodDialogFragmet   addFoodDialogFragmet=new UpdateFoodDialogFragmet(EditMealActivity.this);
        Bundle bundle=new Bundle();
        bundle.putString("imagepath",model.getRecipeImagePath());
        bundle.putString("meal_name",model.getRecipeName());
        bundle.putString("meal_type",tvMealType.getText().toString());
        bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
        bundle.putSerializable("nutrionData",model);

        addFoodDialogFragmet.setArguments(bundle);


        addFoodDialogFragmet.show(getSupportFragmentManager(), addFoodDialogFragmet.getTag());



      /*  Toast
    .makeText(getApplicationContext(),model.getRecipeName(),Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onRemove(int position, FoodListByMealType.Datum model,boolean isRemove) {
       userMealId = model.getUserMealId();
        commonAddmealList.remove(position);
        isRemoveMealItem = isRemove;
        quantityCount=0.0;
        tvQty.setText("");
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

//        adapter.updateData(lsTimeFrom);
    }

    @Override
    public void onSubmitUOMData(FoodUnitMasterData model) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null) {
            UOMDialog df = (UOMDialog) fragment;
            df.dismiss();
        }
        double_valueInGram=model.getValueInGram();
        strUOmID= Integer.parseInt(model.getId());
        txt_uom_measurement.setText(model.getMeasurement());


    }

    @Override
    public void getaddMealData(String uomID, double valueingrma, double quantity) {

        strUOmID= Integer.parseInt(uomID);
        double_valueInGram=valueingrma;
        quantityCount=quantity;
        tvAdd.performClick();

    }
}


