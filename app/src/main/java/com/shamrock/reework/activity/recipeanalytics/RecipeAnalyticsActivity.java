package com.shamrock.reework.activity.recipeanalytics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodActivity;
import com.shamrock.reework.activity.FoodModule.adapter.ClsHealthCategorymasterAdapter;
import com.shamrock.reework.activity.FoodModule.model.ClsHealthCategoryMaster;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeAnalyticsActivity extends AppCompatActivity implements ClsRecipeAnalyticsListAdapter.OnGetClickData,SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    RecipeAnalyticsActivity context;
    private HealthParametersService healthParametersService;
    private Utils utils;
    RecyclerView recyler_recipe_analytic_library;
    ArrayList<String> arylStrings = new ArrayList<>();
    TextView txt_select_caltegory;
    private String category = "";
    private SessionManager sessionManager;
    private FoodService foodService;
    TextView txt_select_classification;
    private ArrayList<String> aryls_string_classifcation;
    ClsRecipeAnalyticsListAdapter clsRecipeAnalyticsListAdapter;
    private ArrayList<RecipeAnalyticResult> arylst_result_old;
    private ArrayList<RecipeAnalyticResult> arylst_result;
    private ArrayList<RecipeAnalyticResult> arylst_result_classification;
    TextView txt_non_veg, txt_veg;
    boolean isVeg = true;
    SearchView searchRecipe;
    boolean isFirstTime = false;
    LinearLayout ll_veg_non_veg;
    TextView txt_no_data;


    @Override
    public boolean onQueryTextSubmit(String query) {
        String text = query;
        clsRecipeAnalyticsListAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        String text = newText;
        clsRecipeAnalyticsListAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_analytics);
        ll_veg_non_veg = findViewById(R.id.ll_veg_non_veg);
        txt_no_data = findViewById(R.id.txt_no_data);
        ll_veg_non_veg.setVisibility(View.GONE);

        ImageView img_Back = findViewById(R.id.img_Back);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        searchRecipe = findViewById(R.id.searchRecipe);
        searchRecipe.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
//        searchRecipe.setOnSearchClickListener(this);
        searchRecipe.setOnCloseListener((SearchView.OnCloseListener) this);

        txt_select_classification = findViewById(R.id.txt_select_classification);
        txt_non_veg = findViewById(R.id.txt_non_veg);
        txt_veg = findViewById(R.id.txt_veg);
        recyler_recipe_analytic_library = findViewById(R.id.recyler_recipe_analytic_library);
        txt_select_classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailogCliassification();
            }
        });
        txt_select_caltegory = findViewById(R.id.txt_select_caltegory);
        txt_select_caltegory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_veg_non_veg.setVisibility(View.VISIBLE);
                final Dialog dialog = new Dialog(RecipeAnalyticsActivity.this, R.style.CustomDialog);
                dialog.setContentView(R.layout.dialog_select_category);
                TextView txt_all = dialog.findViewById(R.id.txt_all);
                txt_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ll_veg_non_veg.setVisibility(View.GONE);

                        category = "";
                        txt_select_caltegory.setText("All");
                        txt_select_classification.setText("Select Classification");

//                        callPostRecipeLibraryAPi(category,"");

                        bytype(category);


                    }
                });
                TextView txt_recipe = dialog.findViewById(R.id.txt_recipe);
                txt_recipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ll_veg_non_veg.setVisibility(View.VISIBLE);

                        txt_select_caltegory.setText("Recipe");
                        txt_select_classification.setText("Select Classification");

//                        category="RecipeProduct";
                        category = "Recipe";
//                        callPostRecipeLibraryAPi(category,"");
                        bytype(category);

                    }
                });

                TextView txt_finish = dialog.findViewById(R.id.txt_finish);
                txt_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_veg_non_veg.setVisibility(View.GONE);

                        dialog.dismiss();
                        txt_select_caltegory.setText("Finished-Product");
                        txt_select_classification.setText("Select Classification");
//
//                        category="FinishedProduct";
                        category = "Finished Product";
//                        callFinishPostRecipeLibraryAPi(category,"");
                        bytype(category);


                    }
                });
                dialog.show();

            }
        });
        context = RecipeAnalyticsActivity.this;
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();

        arylst_result = new ArrayList<>();
        clsRecipeAnalyticsListAdapter = new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this, arylst_result);
        recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);
        callPostRecipeLibraryAPi("", "");
        getHealthCategoryMasterAPI();

        txt_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (arylst_result_old != null) {
                    txt_select_classification.setText("Select Classification");


                    txt_veg.setBackgroundColor(context.getResources().getColor(R.color.dark_grey_blue));
                    txt_non_veg.setBackgroundColor(context.getResources().getColor(R.color.colorGreen3));

                    isVeg = true;
                    if (arylst_result != null && !arylst_result.isEmpty()) {
                        arylst_result.clear();
                    }
                    for (int i = 0; i < arylst_result_old.size(); i++) {
                        if (arylst_result_old.get(i).getIsVeg() != null) {
                            if (arylst_result_old.get(i).getIsVeg().equalsIgnoreCase("true")) {

                                arylst_result.add(arylst_result_old.get(i));

                            }
                        }


                    }
                    clsRecipeAnalyticsListAdapter = new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this, arylst_result);
                    recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);
                }

            }
        });
        txt_non_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                txt_select_caltegory.setText("Select Category");

                if (arylst_result_old != null) {
                    txt_select_classification.setText("Select Classification");
                    txt_non_veg.setBackgroundColor(context.getResources().getColor(R.color.dark_grey_blue));
                    txt_veg.setBackgroundColor(context.getResources().getColor(R.color.colorGreen3));

                    if (arylst_result != null && !arylst_result.isEmpty()) {
                        arylst_result.clear();
                    }
                    isVeg = false;
                    for (int i = 0; i < arylst_result_old.size(); i++) {

                        if (arylst_result_old.get(i).getIsVeg() != null) {
                            if (!arylst_result_old.get(i).getIsVeg().equalsIgnoreCase("true")) {
                                arylst_result.add(arylst_result_old.get(i));
                            }
                        }


                    }
                    clsRecipeAnalyticsListAdapter = new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this, arylst_result);
                    recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);
                }


            }
        });
    }


    public void bytype(String type) {
        if (arylst_result != null && !arylst_result.isEmpty()) {
            arylst_result.clear();

        }

        if (!type.isEmpty()){
            if (type.equalsIgnoreCase("recipe")){
                for (int i = 0; i < arylst_result_old.size(); i++) {
                    if (isVeg){
                        if (arylst_result_old.get(i).getCategory().equalsIgnoreCase(type)&&arylst_result_old.get(i).getIsVeg().equalsIgnoreCase("true")){
                            {

                                arylst_result.add(arylst_result_old.get(i));

                            }
                        }
                    }else {
                        if (arylst_result_old.get(i).getCategory().equalsIgnoreCase(type)&&!arylst_result_old.get(i).getIsVeg().equalsIgnoreCase("true")){
                            {

                                arylst_result.add(arylst_result_old.get(i));

                            }
                        }

                    }





                }
            }else {
                for (int i = 0; i < arylst_result_old.size(); i++) {
                    if (isVeg){
                        if (arylst_result_old.get(i).getCategory().equalsIgnoreCase(type)){
                            {

                                arylst_result.add(arylst_result_old.get(i));

                            }
                        }
                    }else {
                        if (arylst_result_old.get(i).getCategory().equalsIgnoreCase(type)){
                            {

                                arylst_result.add(arylst_result_old.get(i));

                            }
                        }

                    }





                }
            }

        }else {
            for (int i = 0; i < arylst_result_old.size(); i++) {
                arylst_result.add(arylst_result_old.get(i));



            }
        }

        txt_select_classification.setText("Select Classification");


//        if (!type.isEmpty()){
//            if (isVeg){
//                txt_veg.performClick();
//            }else {
//                txt_non_veg.performClick();
//            }
//        }

        clsRecipeAnalyticsListAdapter = new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this, arylst_result);
        recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);




    }
    private void callPostRecipeLibraryAPi(String category,String searchtext) {
        ClsRecipeLibraryAnalyticRequest clsRecipeLibraryAnalyticRequest=new ClsRecipeLibraryAnalyticRequest();
        clsRecipeLibraryAnalyticRequest.setCategory(category);
        clsRecipeLibraryAnalyticRequest.setSearchText("");

        utils.showProgressbar(this);

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please wait ................", Snackbar.LENGTH_LONG);
        snackbar.show();
        Call<ClsRecipeAnalyticDataMain> call = healthParametersService.getRecipeLibraryAnalytics(clsRecipeLibraryAnalyticRequest);
        call.enqueue(new Callback<ClsRecipeAnalyticDataMain>() {
            @Override
            public void onResponse(Call<ClsRecipeAnalyticDataMain> call, Response<ClsRecipeAnalyticDataMain> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsRecipeAnalyticDataMain listResponse = response.body();
                    recyler_recipe_analytic_library.setVisibility(View.VISIBLE);
                    txt_no_data.setVisibility(View.GONE);

                    if (listResponse != null && listResponse.getCode().equals("1")) {

//                        arylst_result_old=listResponse.getData().getResult();


                        arylst_result=new ArrayList<>();
                        arylst_result_old=listResponse.getData().getResult();
                        for (int i = 0; i < arylst_result_old.size(); i++) {
                            arylst_result.add(arylst_result_old.get(i));



                        }
                        clsRecipeAnalyticsListAdapter = new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this, arylst_result);
                        recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);



//                        if (isVeg){
//                            txt_veg.performClick();
//                        }else {
//                            txt_non_veg.performClick();
//                        }



                    } else {
                        try {
                            if (listResponse.getMessage() != null){
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please try again", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Retry", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
//                                                callCountryApi();
                                                callPostRecipeLibraryAPi("","");

                                            }
                                        });
                                snackbar.show();
                            }
//                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("Error---->", response.message());
                        }catch (Exception e){

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsRecipeAnalyticDataMain> call, Throwable t) {
                utils.hideProgressbar();
                Toast.makeText(RecipeAnalyticsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callFinishPostRecipeLibraryAPi(String category,String searchtext) {
        ClsRecipeLibraryAnalyticRequest clsRecipeLibraryAnalyticRequest=new ClsRecipeLibraryAnalyticRequest();
        clsRecipeLibraryAnalyticRequest.setCategory(category);
        clsRecipeLibraryAnalyticRequest.setSearchText("");

        utils.showProgressbar(this);
        Call<ClsRecipeAnalyticDataMain> call = healthParametersService.getRecipeLibraryAnalytics(clsRecipeLibraryAnalyticRequest);
        call.enqueue(new Callback<ClsRecipeAnalyticDataMain>() {
            @Override
            public void onResponse(Call<ClsRecipeAnalyticDataMain> call, Response<ClsRecipeAnalyticDataMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsRecipeAnalyticDataMain listResponse = response.body();

                    recyler_recipe_analytic_library.setVisibility(View.VISIBLE);
                    txt_no_data.setVisibility(View.GONE);

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        arylst_result=new ArrayList<>();
                        arylst_result_old=listResponse.getData().getResult();

                        for (int i = 0; i <arylst_result_old.size() ; i++) {
                            arylst_result.add(arylst_result_old.get(i));


                        }
                        clsRecipeAnalyticsListAdapter=new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this,arylst_result);
                        recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);


//                        clsRecipeAnalyticsListAdapter=new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this,arylst_result_old);
//                        recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);

                    } else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsRecipeAnalyticDataMain> call, Throwable t) {
                utils.hideProgressbar();
                Toast.makeText(RecipeAnalyticsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getHealthCategoryMasterAPI() {
        foodService = Client.getClient().create(FoodService.class);
//        utils.showProgressbar(RecipeAnalyticsActivity.this);




        Call<ClsHealthCategoryMaster> call = foodService.getHealthCategoryMaster();
        call.enqueue(new Callback<ClsHealthCategoryMaster>()
        {
            @Override

            public void onResponse(Call<ClsHealthCategoryMaster> call, Response<ClsHealthCategoryMaster> response)
            {
//                utils.hideProgressbar();

                aryls_string_classifcation=new ArrayList<>();
                ArrayList<HealthCatogoryData> arylst_HealthCatogoryData;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsHealthCategoryMaster listResponse = response.body();

                    if (listResponse!=null){
                        if (listResponse.getData()!=null){
                            if (!listResponse.getData().isEmpty()){
                                arylst_HealthCatogoryData =listResponse.getData();
                                aryls_string_classifcation.add(0,"All");
                                for (int i = 0; i <arylst_HealthCatogoryData.size() ; i++) {
                                    aryls_string_classifcation.add(arylst_HealthCatogoryData.get(i).getClassification().toString());

                                }

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

    public void showDailogCliassification(){
        final Dialog dialog = new Dialog(RecipeAnalyticsActivity.this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_select_classification);
        ListView lst_classification=dialog.findViewById(R.id.lst_classification);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item, aryls_string_classifcation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lst_classification.setAdapter(adapter);
        lst_classification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                txt_select_classification.setText(aryls_string_classifcation.get(position).toString());

                dialog.dismiss();
                filterclassificationWiseData(aryls_string_classifcation.get(position).toString());




            }
        });
        dialog.show();
    }

    private void filterclassificationWiseData(String toString) {
        arylst_result_classification=new ArrayList<>();



        if (toString.equalsIgnoreCase("All")){

            for (int i = 0; i < arylst_result.size(); i++) {
                arylst_result_classification.add(arylst_result.get(i));

            }

        }

        if (!toString.equalsIgnoreCase("All")){
            for (int i = 0; i <arylst_result.size() ; i++) {

                StringBuilder stringBuilder=new StringBuilder();

                if (arylst_result.get(i).getClassification()!=null){
                    for (int j = 0; j <arylst_result.get(i).getClassification().size() ; j++) {
                        stringBuilder.append(arylst_result.get(i).getClassification().get(j).toString());
                    }
                    if(stringBuilder.toString().contains(toString)){
                        arylst_result_classification.add(arylst_result.get(i));
                    }
                }










            }
        }


        if (arylst_result_classification!=null&&!arylst_result_classification.isEmpty()){
            recyler_recipe_analytic_library.setVisibility(View.VISIBLE);
            txt_no_data.setVisibility(View.GONE);
            clsRecipeAnalyticsListAdapter=new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this,arylst_result_classification);
            recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);

        }else {
            txt_no_data.setVisibility(View.VISIBLE);
            recyler_recipe_analytic_library.setVisibility(View.GONE);
            arylst_result_classification=new ArrayList<>();
            clsRecipeAnalyticsListAdapter=new ClsRecipeAnalyticsListAdapter(RecipeAnalyticsActivity.this,arylst_result_classification);
            recyler_recipe_analytic_library.setAdapter(clsRecipeAnalyticsListAdapter);

//            Toast.makeText(context, "No Data available", Toast.LENGTH_SHORT).show();
        }






    }


    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public void getData(RecipeAnalyticResult adapterPosition) {

        Intent intent=new Intent(this,RecipeAnalyticsDetails.class);
        intent.putExtra("Data",adapterPosition);
        startActivity(intent);

    }
}
