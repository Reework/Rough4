package com.shamrock.reework.activity.recipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.FaqActivity.ReeworkFAQActivity;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodActivity;
import com.shamrock.reework.activity.FoodModule.model.ClsHealthCategoryMaster;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthSupportModule.HealthAndSupportActivity;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.activity.MyPlansModule.activity.GlosaryListActivity;
import com.shamrock.reework.activity.newrecipe.EditRecipeData;
import com.shamrock.reework.activity.newrecipe.Ingredients;
import com.shamrock.reework.activity.recipe.adapter.ClassificationListAdapter;
import com.shamrock.reework.activity.recipe.adapter.SelectedIngradientListAdapter;
import com.shamrock.reework.activity.recipe.model.ClsIngradientMain;
import com.shamrock.reework.activity.recipe.model.ClsNutritionDataMain;
import com.shamrock.reework.activity.recipe.model.ClsPostIngradeints;
import com.shamrock.reework.activity.recipe.model.ClsRecipeMasterMain;
import com.shamrock.reework.activity.recipe.model.ClsSelectedIngradientDetails;
import com.shamrock.reework.activity.recipe.model.CusineList;
import com.shamrock.reework.activity.recipe.model.IngradientData;
import com.shamrock.reework.activity.recipe.model.ItemUomMaster;
import com.shamrock.reework.activity.recipe.model.MealTypeList;
import com.shamrock.reework.activity.recipe.model.ServingUnitMaster;
import com.shamrock.reework.activity.recipe.model.daillogs.cuision.CusionListDialog;
import com.shamrock.reework.activity.recipe.model.daillogs.cuision.ingradient.IngradientListDialog;
import com.shamrock.reework.activity.recipe.model.daillogs.cuision.ingradientunit.IngradientUnitListDialog;
import com.shamrock.reework.activity.recipe.model.daillogs.cuision.mealtype.MealTypeListDialog;
import com.shamrock.reework.activity.recipe.model.daillogs.cuision.serving.ServingUnitTypeListDialog;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRecipeActivity extends AppCompatActivity implements View.OnClickListener, IngradientUnitListDialog.GetIngradientUnitListener, IngradientListDialog.GetIngradientListener, ServingUnitTypeListDialog.GetServingUnitListener, CusionListDialog.GetCustionListener, MealTypeListDialog.GetMealtypeListener, SelectedIngradientListAdapter.OnEditIngradeitn {
    private Button btn_next_vp, btn_back_vp;
    private ImageView img_recipe_image;
    private ArrayList<ClsSelectedIngradientDetails> arylst_selected_ingrad_list;
    private ArrayList<ClsPostIngradeints> arylst_ClsPostIngradeints;
    private ArrayList<Ingredients> arylst_ClsPostIngradeints_data;
    ViewFlipper vp_recipe;
    private HealthParametersService healthParametersService;
    private Utils utils;
    private TextView txt_select_cusion;
    CusionListDialog cusionlistdailg;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
            FILE_SELECT_REQUEST_CODE = 300;
    private Uri mCapturedImageURI;
    TextView txt_upload_recipe;
    private ArrayList<CusineList> arylst_CusineLists;
    private EditText edt_approximate_value, edt_reciepe_name;
    private EditText edt_cooking_time, edt_preparation_time, edt_serving_qty;
    TextView tvMealTypeName;
    TextView txt_select_serving_unit, txt_select_ingradient, txt_select_ingrad_unit, txt_add_ingradient;
    boolean isVisibleProximate = false;
    private Context context;

    //send data
    int cusionID;
    int mealtypeID;
    String mealtype_name;
    int serving_unit_id;
    int ingradeintID;
    int ingradient_unit_id;
    EditText edt_remark;


    private MealTypeListDialog mealtypelistdailog;
    private ArrayList<MealTypeList> arylst_mealtype_list;
    private ServingUnitTypeListDialog servingunitlistdailog;
    private ArrayList<ServingUnitMaster> arylst_servingunit_list;
    private ArrayList<IngradientData> arylst_ingradientlist;
    private ArrayList<ItemUomMaster> arylst_ingradient_unit;
    private IngradientListDialog ingradientlistdailog;
    private IngradientUnitListDialog ingradient_unit_listdailog;
    File fileuploadimage = new File("");

    RecyclerView recler_ingradient_list;
    private EditText edt_recipe_method;
    private SessionManager sessionManager;
    private int userId;
    RadioGroup rg_recipe;
    RadioButton rb_veg;
    RadioButton rb_non_veg;
    boolean isVeg = true;


    //
    RadioGroup rg_heathy_recipe;
    RadioButton rb_healthy_no, rb_healthy_yes;
    private boolean isHealthyRecipe = true;
    private String str_cusion = "";
    private FoodService foodService;
    TextView txt_select_classification;
    private ArrayList<HealthCatogoryData> arylst_HealthCatogoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        context = CreateRecipeActivity.this;
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();
        initView();
        callMasterApi();
        callIngradientMasterApi();

         txt_select_classification=findViewById(R.id.txt_select_classification);
        txt_select_classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHealthCategoryMasterAPI();
            }
        });


        rb_veg = findViewById(R.id.rb_veg);
        rb_non_veg = findViewById(R.id.rb_non_veg);
        rg_recipe = findViewById(R.id.rg_recipe);


        rb_veg.setChecked(true);
        rg_recipe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_veg:
                        isVeg = true;
                        break;
                    case R.id.rb_non_veg:
                        isVeg = false;

                        break;

                }
            }
        });


        rb_healthy_no = findViewById(R.id.rb_healthy_no);
        rb_healthy_yes = findViewById(R.id.rb_healthy_yes);
        rg_heathy_recipe = findViewById(R.id.rg_heathy_recipe);
        rb_healthy_yes.setChecked(true);

        rg_heathy_recipe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_healthy_yes:
                        isHealthyRecipe = true;
                        txt_select_classification.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_healthy_no:
                        isHealthyRecipe = false;
                        txt_select_classification.setVisibility(View.GONE);


                        break;

                }
            }
        });
    }

    private void callPostIngradeintApi(ArrayList<ClsPostIngradeints> clsPostIngradeints) {


        utils.showProgressbar(this);
        Call<ClsNutritionDataMain> call = healthParametersService.GetNutrition(clsPostIngradeints);
        call.enqueue(new Callback<ClsNutritionDataMain>() {
            @Override
            public void onResponse(Call<ClsNutritionDataMain> call, Response<ClsNutritionDataMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsNutritionDataMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        TextView txt_fib = findViewById(R.id.txt_fib);
                        txt_fib.setText(listResponse.getData().getFibre());
                        TextView txt_fat = findViewById(R.id.txt_fat);
                        txt_fat.setText(listResponse.getData().getFat());
                        TextView txt_car = findViewById(R.id.txt_car);
                        txt_car.setText(listResponse.getData().getCarbs());

                        TextView txt_prot = findViewById(R.id.txt_prot);
                        txt_prot.setText(listResponse.getData().getProtein());

                        TextView txt_cal = findViewById(R.id.txt_cal);
                        txt_cal.setText(listResponse.getData().getCalories());


                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsNutritionDataMain> call, Throwable t) {
                utils.hideProgressbar();
            }
        });
    }

    private void initView() {

        edt_preparation_time = findViewById(R.id.edt_preparation_time);
        edt_remark = findViewById(R.id.edt_remark);
        edt_recipe_method = findViewById(R.id.edt_recipe_method);
        txt_upload_recipe = findViewById(R.id.txt_upload_recipe);
        img_recipe_image = findViewById(R.id.img_recipe_image);
        txt_upload_recipe.setOnClickListener(this);
        edt_serving_qty = findViewById(R.id.edt_serving_qty);
        edt_cooking_time = findViewById(R.id.edt_cooking_time);
        vp_recipe = findViewById(R.id.vp_recipe);
        edt_reciepe_name = findViewById(R.id.edt_reciepe_name
        );
        edt_approximate_value = findViewById(R.id.edt_approximate_value);
        txt_add_ingradient = findViewById(R.id.txt_add_ingradient);
        btn_next_vp = findViewById(R.id.btn_next_vp);
        btn_back_vp = findViewById(R.id.btn_back_vp);
        txt_select_ingradient = findViewById(R.id.txt_select_ingradient);
        txt_select_ingrad_unit = findViewById(R.id.txt_select_ingrad_unit);
        final EditText edt_ing_qty = findViewById(R.id.edt_ing_qty);

        recler_ingradient_list = findViewById(R.id.recler_ingradient_list);

        arylst_selected_ingrad_list = new ArrayList<>();

        txt_add_ingradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_select_ingradient.getText().toString().equalsIgnoreCase("Select Ingredient")) {
                    Toast.makeText(CreateRecipeActivity.this, "Please Select Ingradient", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txt_select_ingrad_unit.getText().toString().equalsIgnoreCase("Select Ingredient Unit")) {
                    Toast.makeText(CreateRecipeActivity.this, "Please Select Ingredient Unit", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edt_ing_qty.getText().toString().trim().isEmpty()) {
                    Toast.makeText(CreateRecipeActivity.this, "Please Enter Ingradient Quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isVisibleProximate) {
                    Toast.makeText(CreateRecipeActivity.this, "Please Enter Approximate value", Toast.LENGTH_SHORT).show();
                    return;
                }


                String ingradeintName = txt_select_ingradient.getText().toString().trim();
                int ingradeintIDs = ingradeintID;
                int UOMID = ingradient_unit_id;
                String uomName = txt_select_ingrad_unit.getText().toString();
                double dbl_qty = Double.parseDouble(edt_ing_qty.getText().toString());
                double app_value = 0;
                if (isVisibleProximate) {


                    app_value = Double.parseDouble(edt_approximate_value.getText().toString());

                }


                String remark = edt_remark.getText().toString();
                arylst_selected_ingrad_list.add(new ClsSelectedIngradientDetails(ingradeintName, ingradeintIDs, UOMID, uomName, dbl_qty, remark, app_value));
                recler_ingradient_list.setAdapter(new SelectedIngradientListAdapter(CreateRecipeActivity.this, arylst_selected_ingrad_list));

                setDefaultvalues(edt_ing_qty, edt_approximate_value);
                updateIngradeintAPI(arylst_selected_ingrad_list);

            }
        });
        btn_next_vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vp_recipe.getDisplayedChild() == 0) {
                    btn_next_vp.setText("Next");


                    if (edt_reciepe_name.getText().toString().trim().isEmpty()) {
                        Toast.makeText(CreateRecipeActivity.this, "Please enter recipe name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (txt_select_cusion.getText().toString().equalsIgnoreCase("Select Cuision")) {
                        Toast.makeText(CreateRecipeActivity.this, "Please Select Cuision", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    if (tvMealTypeName.getText().toString().equalsIgnoreCase("Select Meal Type")) {
                        Toast.makeText(CreateRecipeActivity.this, "Please Select Meal Type", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    if (edt_preparation_time.getText().toString().trim().isEmpty()) {
                        Toast.makeText(CreateRecipeActivity.this, "Please enter Preparation Time", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (edt_cooking_time.getText().toString().trim().isEmpty()) {
                        Toast.makeText(CreateRecipeActivity.this, "Please enter Cooking Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (edt_serving_qty.getText().toString().trim().isEmpty()) {
                        Toast.makeText(CreateRecipeActivity.this, "Please Enter Serving Quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (txt_select_serving_unit.getText().toString().equalsIgnoreCase("Select Serving Unit")) {
                        Toast.makeText(CreateRecipeActivity.this, "Please Select Serving Unit", Toast.LENGTH_SHORT).show();
//                        return;
                    }


                    vp_recipe.setDisplayedChild(1);

                } else if (vp_recipe.getDisplayedChild() == 1) {
                    vp_recipe.setDisplayedChild(2);
                    btn_next_vp.setText("Submit");

                } else if (vp_recipe.getDisplayedChild() == 2) {

                    if (edt_recipe_method.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Please enter recipe method", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    callADDRecipeApi();
                }


            }
        });

        btn_back_vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vp_recipe.getDisplayedChild() == 2) {
                    vp_recipe.setDisplayedChild(1);
                    btn_next_vp.setText("Next");

                } else if (vp_recipe.getDisplayedChild() == 1) {
                    vp_recipe.setDisplayedChild(0);
                    btn_next_vp.setText("Next");

                } else if (vp_recipe.getDisplayedChild() == 0) {
//                    startActivity(new Intent(CreateRecipeActivity.this, AllFoodActivity.class));
                    finish();
                }
                 /*   startActivity(new Intent(CreateRecipeActivity.this, AllFoodActivity.class));
                    finish();*/

            }
        });


        vp_recipe.setDisplayedChild(0);
        txt_select_cusion = findViewById(R.id.txt_select_cusion);
        tvMealTypeName = findViewById(R.id.tvMealTypeName);
        txt_select_serving_unit = findViewById(R.id.txt_select_serving_unit);
        txt_select_cusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arylst_CusineLists != null) {
                    FragmentManager fm = getSupportFragmentManager();
                    cusionlistdailg = new CusionListDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CUSION_LIST", arylst_CusineLists);
                    cusionlistdailg.setArguments(bundle);
                    cusionlistdailg.show(fm, "COUNTRY_FRAGMENT");
                }

            }
        });


        tvMealTypeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arylst_mealtype_list != null) {
                    FragmentManager fm = getSupportFragmentManager();
                    mealtypelistdailog = new MealTypeListDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CUSION_LIST", arylst_mealtype_list);
                    mealtypelistdailog.setArguments(bundle);
                    mealtypelistdailog.show(fm, "COUNTRY_FRAGMENT");
                }

            }
        });


        txt_select_serving_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arylst_servingunit_list != null) {
                    FragmentManager fm = getSupportFragmentManager();
                    servingunitlistdailog = new ServingUnitTypeListDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CUSION_LIST", arylst_servingunit_list);
                    servingunitlistdailog.setArguments(bundle);
                    servingunitlistdailog.show(fm, "COUNTRY_FRAGMENT");
                }

            }
        });

        txt_select_ingradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                ingradientlistdailog = new IngradientListDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("CUSION_LIST", arylst_ingradientlist);
                ingradientlistdailog.setArguments(bundle);
                ingradientlistdailog.show(fm, "COUNTRY_FRAGMENT");
            }
        });

        txt_select_ingrad_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();
                ingradient_unit_listdailog = new IngradientUnitListDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable("CUSION_LIST", arylst_ingradient_unit);
                bundle.putBoolean("INGRAD", true);
                ingradient_unit_listdailog.setArguments(bundle);
                ingradient_unit_listdailog.show(fm, "COUNTRY_FRAGMENT");
            }
        });
    }

    private void setDefaultvalues(EditText edt_ing_qty, EditText edt_approximate_value) {
        txt_select_cusion.setText("Select Ingredient");
        txt_select_ingrad_unit.setText("Please Select Ingradient Unit");
        txt_select_ingradient.setText("Select Ingredient");

        edt_remark.setText("");

        edt_ing_qty.setText("");
        edt_approximate_value.setText("");

    }

    private void callMasterApi() {

        utils.showProgressbar(this);
        Call<ClsRecipeMasterMain> call = healthParametersService.getMasterList();
        call.enqueue(new Callback<ClsRecipeMasterMain>() {
            @Override
            public void onResponse(Call<ClsRecipeMasterMain> call, Response<ClsRecipeMasterMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsRecipeMasterMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        if (listResponse.getData() != null) {
                            arylst_CusineLists = listResponse.getData().getCusineList();
                            arylst_mealtype_list = listResponse.getData().getMealTypeList();
                            arylst_servingunit_list = listResponse.getData().getServingUnitMaster();
                            arylst_ingradient_unit = listResponse.getData().getItemUomMaster();
                        }


                    } else {
                        //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsRecipeMasterMain> call, Throwable t) {
                utils.hideProgressbar();
            }
        });
    }

    private void callIngradientMasterApi() {

        utils.showProgressbar(this);
//        http://shamrockuat.dweb.in/api/Recipe/IngredientList

        Call<ClsIngradientMain> call = healthParametersService.getIngradientMasterList();
        call.enqueue(new Callback<ClsIngradientMain>() {
            @Override
            public void onResponse(Call<ClsIngradientMain> call, Response<ClsIngradientMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsIngradientMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        arylst_ingradientlist = listResponse.getData();


                    } else {
                        //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsIngradientMain> call, Throwable t) {
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void onSubmitCusionData(CusineList model) {

        txt_select_cusion.setText(model.getCuisine());
        str_cusion = model.getCuisine();
        cusionID = Integer.parseInt(model.getId());

    }

    @Override
    public void onSubmitMealtypeData(MealTypeList model) {
        tvMealTypeName.setText(model.getMealType());
        mealtypeID = Integer.parseInt(model.getId());
        mealtype_name = model.getMealType();

    }

    @Override
    public void onSubmitServinguniteData(ServingUnitMaster model) {
        serving_unit_id = Integer.parseInt(model.getId());
        txt_select_serving_unit.setText(model.getUnit());

    }

    @Override
    public void onSubmitIIngradientData(IngradientData model) {
        ingradeintID = Integer.parseInt(model.getId());
        txt_select_ingradient.setText(model.getIngredientName());

    }

    @Override
    public void onSubmitIngradeintUnitData(ItemUomMaster model) {
        ingradient_unit_id = Integer.parseInt(model.getId());
        txt_select_ingrad_unit.setText(model.getMeasurement());
        if (model.getIsApproximate().equalsIgnoreCase("true")) {
            edt_approximate_value.setVisibility(View.VISIBLE);
            isVisibleProximate = true;

        } else {
            edt_approximate_value.setVisibility(View.GONE);
            isVisibleProximate = false;

        }

    }

    @Override
    public void geteditingradData(ClsSelectedIngradientDetails cusineList, final int adapterPosition) {

        final Dialog dialog = new Dialog(CreateRecipeActivity.this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_edit_ingradeint);
        final EditText edt_ingrad_remark = dialog.findViewById(R.id.edt_ingrad_remark);
        final EditText edt_ingrad_qty = dialog.findViewById(R.id.edt_ingrad_qty);
        EditText edt_ingrad_unit = dialog.findViewById(R.id.edt_ingrad_unit);
        EditText edt_edit_ingrad = dialog.findViewById(R.id.edt_edit_ingrad);
        Button btn_delete_ingrad = dialog.findViewById(R.id.btn_delete_ingrad);
        btn_delete_ingrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                arylst_selected_ingrad_list.remove(adapterPosition);
                recler_ingradient_list.setAdapter(new SelectedIngradientListAdapter(CreateRecipeActivity.this, arylst_selected_ingrad_list));

                updateIngradeintAPI(arylst_selected_ingrad_list);

            }
        });
        Button btn_update_ingrad = dialog.findViewById(R.id.btn_update_ingrad);
        btn_update_ingrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                arylst_selected_ingrad_list.get(adapterPosition).setQuantity(Double.parseDouble(edt_ingrad_qty.getText().toString()));
                arylst_selected_ingrad_list.get(adapterPosition).setRemarks(edt_ingrad_remark.getText().toString());
                recler_ingradient_list.setAdapter(new SelectedIngradientListAdapter(CreateRecipeActivity.this, arylst_selected_ingrad_list));

                updateIngradeintAPI(arylst_selected_ingrad_list);


            }
        });

        edt_edit_ingrad.setText(cusineList.getIngradientName());
        edt_ingrad_unit.setText(cusineList.getUomName());
        edt_ingrad_qty.setText(String.valueOf(cusineList.getQuantity()));
        edt_ingrad_remark.setText(cusineList.getRemarks());


        dialog.show();

    }

    private void updateIngradeintAPI(ArrayList<ClsSelectedIngradientDetails> arylst_selected_ingrad_list) {

        arylst_ClsPostIngradeints = new ArrayList<>();
        arylst_ClsPostIngradeints_data = new ArrayList<>();
        for (int i = 0; i < arylst_selected_ingrad_list.size(); i++) {

            arylst_ClsPostIngradeints.add(new ClsPostIngradeints(arylst_selected_ingrad_list.get(i).getIngradientID(), arylst_selected_ingrad_list.get(i).getUomID(), arylst_selected_ingrad_list.get(i).getApproximateValue(), arylst_selected_ingrad_list.get(i).getQuantity()));


            arylst_ClsPostIngradeints_data.add(new Ingredients(arylst_selected_ingrad_list.get(i).getIngradientName()
                    , String.valueOf(arylst_selected_ingrad_list.get(i).getIngradientID()), String.valueOf(arylst_selected_ingrad_list.get(i).getQuantity()), String.valueOf(arylst_selected_ingrad_list.get(i).getUomID()), arylst_selected_ingrad_list.get(i).getRemarks(), "0"));
        }

        callPostIngradeintApi(arylst_ClsPostIngradeints);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_upload_recipe) {

            AddMedicineImageDailog();

        }

    }

    private void AddMedicineImageDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage("Upload Recipe Image ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialog.dismiss();

                        Dexter.withActivity(CreateRecipeActivity.this)
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        CallCameraIntent();
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();


                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        dialog.cancel();

                        Dexter.withActivity(CreateRecipeActivity.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        fileChooser();
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();


                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCancelable(true);
        //Setting the title manually
        alert.show();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void CallCameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void fileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                img_recipe_image.setImageBitmap(null);
                img_recipe_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                fileuploadimage = new File(picturePath);
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                } catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString());
                    t.printStackTrace();
                }
//                uploadFile(new File(picturePath), userID);

//                isImage = true;
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                } catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString());
                    t.printStackTrace();
                }

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mCapturedImageURI);
                    img_recipe_image.setImageBitmap(null);

                    img_recipe_image.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
//                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
//                isImage = true;
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    private void callADDRecipeApi() {
        utils.showProgressbar(context);
        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        EditRecipeData editRecipeData = new EditRecipeData();
        editRecipeData.setServingQuantitiy(edt_serving_qty.getText().toString());
        editRecipeData.setServingUnitId(String.valueOf(serving_unit_id));
        editRecipeData.setRecipeName(edt_reciepe_name.getText().toString());
        editRecipeData.setIsVeg(String.valueOf(isVeg));
        editRecipeData.setIsHealthy(String.valueOf(isHealthyRecipe));
        editRecipeData.setPrepTime(edt_preparation_time.getText().toString());
        editRecipeData.setCookTime(edt_cooking_time.getText().toString());

        editRecipeData.setIngredients(arylst_ClsPostIngradeints_data);

        editRecipeData.setCreatedBy(String.valueOf(userId));
        editRecipeData.setRecipeId(String.valueOf(0));
        editRecipeData.setCuisine(str_cusion);
        editRecipeData.setDescription(edt_recipe_method.getText().toString());
        EditText edt_recipe_link = findViewById(R.id.edt_recipe_link);
        editRecipeData.setVideoLink(edt_recipe_link.getText().toString());
        StringBuilder stringBuilder=new StringBuilder();

        if (isHealthyRecipe){
            for (int i = 0; i <arylst_HealthCatogoryData.size() ; i++) {
                if (arylst_HealthCatogoryData.get(i).isChecked()){
                    stringBuilder.append(arylst_HealthCatogoryData.get(i).getId()).append(",");
                }
            }
            try{
                if (stringBuilder.length()>=1){
                    String classifcationids=stringBuilder.substring(0,stringBuilder.length()-1);
                    editRecipeData.setClassificationIds(classifcationids);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            editRecipeData.setClassificationIds("");

        }




















        String requestedit = new Gson().toJson(editRecipeData);


//        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), requestedit);
//        MultipartBody.Part photo = null;
//        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
//        photo = MultipartBody.Part.createFormData("RecipeImage","", photoContent);


        //
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), requestedit);
        MultipartBody.Part photo = null;
        if (!fileuploadimage.getPath().isEmpty()) {
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileuploadimage);
            photo = MultipartBody.Part.createFormData("RecipeImage", fileuploadimage.getName(), photoContent);
        } else {
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            photo = MultipartBody.Part.createFormData("RecipeImage", "", photoContent);

        }
        MedicineService medService = Client.getClientMultiPart().create(MedicineService.class);
        Call<ClsEditWaterResonse> call = medService.saveEditRecipe(body, photo);
        call.enqueue(new Callback<ClsEditWaterResonse>() {
            @Override
            public void onResponse(Call<ClsEditWaterResonse> call, Response<ClsEditWaterResonse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsEditWaterResonse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();

                            }
                        }, 1500);


                    } else
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsEditWaterResonse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();

            }
        });

    }


    private void getHealthCategoryMasterAPI() {
        foodService = Client.getClient().create(FoodService.class);
        sessionManager = new SessionManager(context);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        utils.showProgressbar(CreateRecipeActivity.this);

        Call<ClsHealthCategoryMaster> call = foodService.getHealthCategoryMaster();
        call.enqueue(new Callback<ClsHealthCategoryMaster>()
        {
            @Override

            public void onResponse(Call<ClsHealthCategoryMaster> call, Response<ClsHealthCategoryMaster> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsHealthCategoryMaster listResponse = response.body();

                    if (listResponse!=null){
                        if (listResponse.getData()!=null){
                            if (!listResponse.getData().isEmpty()){
                                arylst_HealthCatogoryData =listResponse.getData();
                                final ArrayList<String> arylst=new ArrayList<>();
                                for (int i = 0; i <arylst_HealthCatogoryData.size() ; i++) {
                                    arylst.add(arylst_HealthCatogoryData.get(i).getClassification());
                                }

                                final Dialog dialog=new Dialog(context,R.style.CustomDialog);
                                dialog.setContentView(R.layout.lay_select_classification);
                                final ListView lst_classificationlist=dialog.findViewById(R.id.lst_classificationlist);
//                                lst_classificationlist.setAdapter(new ClassificationListAdapter(CreateRecipeActivity.this,arylst_HealthCatogoryData));

                                lst_classificationlist.setAdapter(new ArrayAdapter<String>(CreateRecipeActivity.this, android.R.layout.simple_list_item_multiple_choice, arylst));
                                lst_classificationlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//                                lst_classificationlist.setItemChecked(2, true);
                                lst_classificationlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        SparseBooleanArray sp=lst_classificationlist.getCheckedItemPositions();

                                        String str=arylst_HealthCatogoryData.get(position).getClassification();
                                        for (int i = 0; i < arylst_HealthCatogoryData.size(); i++) {
                                            if (str.equals(arylst_HealthCatogoryData.get(i).getClassification())){
                                                if (arylst_HealthCatogoryData.get(i).isChecked()){
                                                    arylst_HealthCatogoryData.get(i).setChecked(false);
                                                }else {
                                                    arylst_HealthCatogoryData.get(i).setChecked(true);

                                                }
                                                break;

                                            }



                                        }

                                        StringBuilder stringBuilder_classfication=new StringBuilder();

                                        for (int i = 0; i <arylst_HealthCatogoryData.size() ; i++) {
                                            if (arylst_HealthCatogoryData.get(i).isChecked()){
                                                stringBuilder_classfication.append(arylst_HealthCatogoryData.get(i).getClassification()).append(",");
                                            }
                                        }
                                        try{
                                            if (stringBuilder_classfication.length()>=1){
                                                String classifcationids=stringBuilder_classfication.substring(0,stringBuilder_classfication.length()-1);
                                                 txt_select_classification.setText(classifcationids);
                                            }
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }








                                    }
                                });
                                Button btn_classifcation=dialog.findViewById(R.id.btn_classifcation);
                                btn_classifcation.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
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

}
