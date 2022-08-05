package com.shamrock.reework.activity.dietplan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.MasterDetailsActivity;
import com.shamrock.reework.activity.FoodModule.adapter.AddMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.model.AddMealRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodUnitMasterPojo;
import com.shamrock.reework.activity.FoodModule.model.EditMealRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.dietplan.pojo.AddFoodPlanRequest;
import com.shamrock.reework.activity.dietplan.pojo.ClsMealtypeMain;
import com.shamrock.reework.activity.dietplan.pojo.MealTypeData;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.FoodListByType;
import com.shamrock.reework.api.response.FoodListByMealType;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetMealType;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.dialog.MealByTypeDialog;
import com.shamrock.reework.dialog.MealTypeDialog;
import com.shamrock.reework.dialog.UOMDialog;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;
import com.shamrock.reework.util.Utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class
AddRDPMealActivity extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener,View.OnClickListener,MealTypeDialog.GetMealTypeListener,MealByTypeDialog.GetMealTypeListener,AddMealIntoListAdapter.OnMealRemoveListner, UOMDialog.GetUOMListener
{
    Context context;
    Toolbar toolbar;
    private Utils utils;
    int userId;
    SessionManager sessionManager;
    FoodService foodService;
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
    TextView txt_repat;
    EditText edt_remark_rdp;
    RecyclerView recyclerview;
    ArrayList<FoodListByMealType.Datum> commonAddmealList = new ArrayList<>();
    ArrayList<FoodListByMealType.Datum> commonAddmealList_filter = new ArrayList<>();
    FoodListByMealType.Datum MealData=new FoodListByMealType.Datum() ;
    AddMealIntoListAdapter adapter = null;
    TodaysMeal mealModel;
    String commingFrom="";
    int finalCount;
    boolean isPresent=false;
    private TimePickerDialog timepickerdialog;
    TextView spinner_UOM;
    ArrayList<FoodUnitMasterData>    foodUnitMasterDataArrayList;

    double double_valueInGram;
    int strUOmID;
    private ArrayList<MealTypeData> arylst_mealtype_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_plan);
        context = AddRDPMealActivity.this;
        spinner_UOM=findViewById(R.id.spinner_UOM);
        spinner_UOM.setOnClickListener(this);
        utils = new Utils();
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Add Food Plan");
        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
        tvMealType = findViewById(R.id.tvMealTypeName);
        txt_repeat_meal = findViewById(R.id.txt_repeat_meal);
        edt_remark_rdp = findViewById(R.id.edt_remark_rdp);
        txt_repeat_meal.setVisibility(View.GONE);
        tvMeals = findViewById(R.id.tvMeals);
        ivRemove = findViewById(R.id.ivRemove);
        tvAdd = findViewById(R.id.tvAdd);
        ivAdd = findViewById(R.id.ivAdd);
        tvQty = findViewById(R.id.tvQty);
        txt_repat = findViewById(R.id.txt_repat);
        txt_repeat_meal.setPaintFlags(txt_repat.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        tvMealTime = findViewById(R.id.tvMealTime);
        recyclerview = findViewById(R.id.recyclerview);
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

        getUOMData();
        getMealTypemaster();
        
        if (Utils.isNetworkAvailable(context))
            if(!commingFrom.equalsIgnoreCase("Edit")) {
                getMealType();
            }else{
                getFoodListByType(mealModel.getMeal_type_name());
            }
        else{
            Toast.makeText(getApplicationContext(),"Check internet connection!",Toast.LENGTH_SHORT).show();

        }
          //  showRetryBar("Check internet connectio    n!");
    }

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
                timepickerdialog = new TimePickerDialog(context, AddRDPMealActivity.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);
                timepickerdialog.show();
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
                    tvQty.setText(String.valueOf(quantityCount));
                }
                break;
            case R.id.tvAdd:
                if(isValidate())



//Cheack Wheather Edit if Yes Then Add Only 1 Content
                if(commingFrom.equalsIgnoreCase("Edit")){


                }else {

                    if (tvMeals.getText().toString() != null && tvMeals.getText().toString().equalsIgnoreCase("Select Meal")) {
                        Toast.makeText(context, "Select Meal", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (spinner_UOM.getText().toString().equalsIgnoreCase("Select Measurement")){
                        Toast.makeText(context, "Please select measurement", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    spinner_UOM.setText("Select Measurement");
                    tvMeals.setText("Select Meal");
                    if (commonAddmealList.size() == 0) {
                        String quantity = tvQty.getText().toString();
                        if (quantity != null && !quantity.equalsIgnoreCase("")) {
                            MealData.setQuantity(Double.valueOf(tvQty.getText().toString()));
                            MealData.setUomId(strUOmID);
                            if (double_valueInGram==0){
                                MealData.setValueInGram(1.0);

                            }else {
                                MealData.setValueInGram(double_valueInGram);

                            }
                            MealData.setMealType(tvMealType.getText().toString());

                        }

                        commonAddmealList.add(MealData);
                        adapter = new AddMealIntoListAdapter(context, commonAddmealList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                        recyclerview.setLayoutManager(layoutManager);
                        recyclerview.setItemAnimator(new DefaultItemAnimator());
                        recyclerview.setAdapter(adapter);
                    } else {
                        for (int i = 0; i < commonAddmealList.size(); i++) {
                            if (commonAddmealList.get(i).getRecipeName().toLowerCase().equalsIgnoreCase(MealData.getRecipeName())) {

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
                                                    if (quantity != null && !quantity.equalsIgnoreCase("")) {
                                                        MealData.setQuantity(MealData.getQuantity()+Double.valueOf(tvQty.getText().toString()));
                                                        MealData.setUomId(strUOmID);
                                                        MealData.setValueInGram(double_valueInGram);
                                                        MealData.setMealType(tvMealType.getText().toString());

                                                    }
                                                    commonAddmealList.get(finalI).setQuantity(MealData.getQuantity());
                                                    commonAddmealList.get(finalI).setUomId(MealData.getUomId());
                                                    commonAddmealList.get(finalI).setValueInGram(double_valueInGram);
                                                    if (adapter != null) {




//                                                        adapter.customArrrylsit(commonAddmealList);
                                                        Collections.sort(commonAddmealList,FoodListByMealType.Datum.COMPARE_BY_PHONE);

                                                        adapter.notifyDataSetChanged();
                                                    }
                                                    quantityCount = 1;
//                                                    quantityCount++;
                                                    tvQty.setText(String.valueOf(1));

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
                                   /* isPresent==false*/
                                    String quantity = tvQty.getText().toString();
                                    if (quantity != null && !quantity.equalsIgnoreCase("")) {
                                        MealData.setQuantity(Double.valueOf(tvQty.getText().toString()));
                                        MealData.setUomId(strUOmID);
                                        MealData.setValueInGram(double_valueInGram);
                                        MealData.setMealType(tvMealType.getText().toString());
                                    }

                                    commonAddmealList.add(MealData);
                                    if(adapter!=null) {
//                                        adapter.customArrrylsit(commonAddmealList);

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

                if (tvMealTime.getText().toString().equalsIgnoreCase("Select Meal Time")){
                    Toast.makeText(context, "Please select Meal Time", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")){
                    Toast.makeText(context, "Please select Meal Type", Toast.LENGTH_SHORT).show();
                    return;


                }

                getAllYestserDaysMeal();


                break;
            case R.id.btnSave:
                if (Utils.isNetworkAvailable(context)) {
                    saveTodysMeal();


                }else{

                }
                break;


        }
    }


    public boolean isValidateForSave() {

        if (tvMealType.getText().toString() != null && tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")) {
            Toast.makeText(context, "Select Meal Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (commonAddmealList.size() == 0) {
            Toast.makeText(context, "Select Meal", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }



    public boolean isValidate() {
        if (tvMealType.getText().toString() != null && tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")) {
            Toast.makeText(context, "Select Meal Type", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tvMeals.getText().toString() != null && tvMeals.getText().toString().equalsIgnoreCase("Select Meal")){
//            Toast.makeText(context, "Select Meal", Toast.LENGTH_SHORT).show();
        return false;
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


    private void getMealTypemaster()

    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        Call<ClsMealtypeMain> call = foodService.getMealTypeMaster();
        call.enqueue(new Callback<ClsMealtypeMain>()
        {
            @Override
            public void onResponse(Call<ClsMealtypeMain> call, Response<ClsMealtypeMain> response)
            {
                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsMealtypeMain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        arylst_mealtype_id = response.body().getData();

                        if (arylst_mealtype_id!= null && arylst_mealtype_id.size() > 0)
                        {


                        }
                    }
                    else
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsMealtypeMain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void saveTodysMeal()

    {

        ArrayList< AddMealRequest.LstSubMealDatum> itemDataList = new ArrayList<>();
        AddMealRequest request = new AddMealRequest();
        request.setUserId(userId);
        request.setMealType(strMealType);










        Intent intent = getIntent();


        request.setLstSubMealData(itemDataList);
        int mealtypeID=0;


        if (arylst_mealtype_id!=null){
            for (int i = 0; i <arylst_mealtype_id.size() ; i++) {

                if (tvMealType.getText().toString().trim().equalsIgnoreCase(arylst_mealtype_id.get(i).getMealType().trim())){
                    mealtypeID= Integer.parseInt(arylst_mealtype_id.get(i).getId());
                }

            }
        }

        if (tvMealTime.getText().toString().equalsIgnoreCase("Select Meal Time")){
            Toast.makeText(context, "Please select meal time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tvMealType.getText().toString().equalsIgnoreCase("Select Meal Type")){
            Toast.makeText(context, "Please select meal type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (spinner_UOM.getText().toString().equalsIgnoreCase("Select Measurement")){
            Toast.makeText(context, "Please select measurement", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tvMeals.getText().toString().equalsIgnoreCase("Select Meal")){
            Toast.makeText(context, "Please select Meal", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tvQty.getText().toString().isEmpty()){
            Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }



        AddFoodPlanRequest addFoodPlanRequest=new AddFoodPlanRequest();
        addFoodPlanRequest.setMealTypeId(mealtypeID);
        addFoodPlanRequest.setFoodPlanId(0);
        addFoodPlanRequest.setMealId(MealData.getRecipeId());

        addFoodPlanRequest.setRdpId(Integer.parseInt(sessionManager.getStringValue("RDP_ID")));




        String time=tvMealTime.getText().toString();
//        if (time.contains("am")){
//            addFoodPlanRequest.setMealTime(time.replace("am","AM"));
//        }else if (time.contains("pm")){
//            addFoodPlanRequest.setMealTime(time.replace("pm","PM"));
//        }else {
//            addFoodPlanRequest.setMealTime(time);
//
//        }

        if (time.contains("am")||time.contains("AM")){
            addFoodPlanRequest.setMealTime(time.replace("am","AM"));
        }else if (time.contains("pm")||time.contains("PM")){
            addFoodPlanRequest.setMealTime(time.replace("pm","PM"));
        }


        addFoodPlanRequest.setQuantity(Double.valueOf(tvQty.getText().toString()));
        addFoodPlanRequest.setRemark(edt_remark_rdp.getText().toString());
        addFoodPlanRequest.setUomId(strUOmID);


        Call<RDPSuccess> call = foodService.getSaveFoodPlan(addFoodPlanRequest);
        call.enqueue(new Callback<RDPSuccess>()
        {
            @Override
            public void onResponse(Call<RDPSuccess> call, Response<RDPSuccess> response)
            {

                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    RDPSuccess listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();



                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result","done");
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();


                    }
                    else
                    {



                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPSuccess> call, Throwable t)
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
                                                quantityCount = 0;
                                                quantityCount++;
                                                tvQty.setText(String.valueOf(quantityCount));
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
                                    quantityCount=0;
                                    quantityCount++;
                                    tvQty.setText(String.valueOf(quantityCount));
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
                                quantityCount=0;
                                quantityCount++;
                                tvQty.setText(String.valueOf(quantityCount));
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
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            MealByTypeDialog df = (MealByTypeDialog) fragment;
            df.dismiss();
        }
        quantityCount=1;
        tvQty.setText(String.valueOf(1));
        tvMeals.setText(model.getRecipeName());
        MealData = model;

      /*  Toast
    .makeText(getApplicationContext(),model.getRecipeName(),Toast.LENGTH_SHORT).show();*/
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


        String putDate="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sessionManager.getStringValue("statusdate"));

            System.out.println("Current Date " + dateFormat.format(date1));

            // Convert Date to Calendar
            Calendar c = Calendar.getInstance();
                c.setTime(date1);
            c.add(Calendar.DATE, -1);

            Date currentDatePlusOne = c.getTime();

            putDate=   dateFormat.format(currentDatePlusOne);


        } catch (Exception e) {
            e.printStackTrace();
        }


        BcaRequest request = new BcaRequest();
        request.setUserid(userId);
        request.setMeal_cal_max(0);
        request.setMeal_type(tvMealType.getText().toString());
        request.setCreatedOn(putDate);
        //4186

        Call<TodaysMealData> call = foodService.getTodyasMeal(request);
        call.enqueue(new Callback<TodaysMealData>()
        {
            @Override
            public void onResponse(Call<TodaysMealData> call, Response<TodaysMealData> response)
            {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    List<TodaysMeal> list = new ArrayList<>();
                    TodaysMealData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<TodaysMealData.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {

                            try{


                                for (int i = 0; i < data.size(); i++)
                                {

                                    MealData=new FoodListByMealType.Datum();
                                    for (int j = 0; j < data.get(i).getLstSubMealData().size(); j++) {

                                            MealData.setQuantity(Double.valueOf(data.get(i).getLstSubMealData().get(j).getMealQty()));
                                            MealData.setUomId(data.get(i).getLstSubMealData().get(j).getUomId());
                                            MealData.setValueInGram(data.get(i).getLstSubMealData().get(j).getValueInGram());
                                            MealData.setMealType(data.get(i).getMealType());
                                            MealData.setCalories(String.valueOf(data.get(i).getLstSubMealData().get(j).getMealCalory()));
                                            MealData.setUserMealId(data.get(i).getLstSubMealData().get(j).getUserMealId());
                                            MealData.setRecipeId(data.get(i).getLstSubMealData().get(j).getRecipeId());
                                            MealData.setRecipeName(data.get(i).getLstSubMealData().get(j).getMealName());

                                        commonAddmealList.add(MealData);

                                    }
                                }

                                Collections.sort(commonAddmealList,FoodListByMealType.Datum.COMPARE_BY_PHONE);

                                adapter = new AddMealIntoListAdapter(context, commonAddmealList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                recyclerview.setLayoutManager(layoutManager);
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                recyclerview.setAdapter(adapter);



                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(AddRDPMealActivity.this, "No records found", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {


                    }
                }

                sessionManager.setStringValue("IsShowMSg","true");

            }

            @Override
            public void onFailure(Call<TodaysMealData> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

}
