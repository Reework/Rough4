package com.shamrock.reework.activity.cheatplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.MyPlansModule.activity.MyPlansActivity;
import com.shamrock.reework.activity.MyPlansModule.adapter.MyPlanMasterAdapter;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.cheatplan.pojo.ClsMainPlansClass;
import com.shamrock.reework.activity.cheatplan.pojo.ClsOccastionMain;
import com.shamrock.reework.activity.cheatplan.pojo.Plans;
import com.shamrock.reework.activity.cheatplan.reeplaceitem.CheatPlanDataMain;
import com.shamrock.reework.activity.cheatplan.reeplaceitem.ReeplaceItemAdapter;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.response.MyPlanMastersResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCheatPlanActivity extends AppCompatActivity  implements  AdapterView.OnItemSelectedListener,SearchView.OnQueryTextListener,SearchView.OnCloseListener{

    private FoodService foodService;
    private Context mContext;
    private Utils utils;
    private SessionManager sessionManager;
    private int userId;
    private ArrayList<String> arylst_occasations;
    private ArrayList<String> arylst_category;
    private ArrayList<Plans> ary_plan;
    private Spinner spinner_occasion;
    private Spinner spinner_category;
    private RecyclerView recyler_plansData;
    private String Str_occaasation="";
    private String str_category="";
    ClsMainPlansClass listResponse;
    SearchView search_chaet;
    MyCheatPlanAdapter myCheatPlanAdapter;
    TextView txt_no_cheaet;
    Spinner spinnerCategory;
    LinearLayout ll_cheat_header;
    boolean iSFirstTime=false;
    private ReeplaceItemAdapter reeplaceItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=MyCheatPlanActivity.this;
        setContentView(R.layout.activity_my_cheat_plan);
        TextView tvTitle=findViewById(R.id.tvTitle);
        ll_cheat_header=findViewById(R.id.ll_cheat_header);
        tvTitle.setText("REEplace Items");
//        callMyPlanMasters();
        spinner_occasion=findViewById(R.id.spinner_occasion);
        spinner_category=findViewById(R.id.spinner_category);
        recyler_plansData=findViewById(R.id.recyler_plansData);
        search_chaet=findViewById(R.id.search_chaet);

        AutoCompleteTextView search_text = (AutoCompleteTextView) search_chaet.findViewById(search_chaet.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));

        txt_no_cheaet=findViewById(R.id.txt_no_cheaet);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                search_chaet.clearFocus();
//
//            }
//        },200);

        search_chaet.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        search_chaet.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        search_chaet.setOnCloseListener((SearchView.OnCloseListener) this);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        utils = new Utils();
        sessionManager = new SessionManager(mContext);

//        spinner_occasion.setOnItemSelectedListener(this);
//        spinner_category.setOnItemSelectedListener(this);

        spinner_occasion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 Str_occaasation = parent.getItemAtPosition(position).toString().trim();

                 ary_plan=new ArrayList<>();
                 if (listResponse!=null){
                     for (int i = 0; i <  listResponse.getData().size(); i++) {

                         if (Str_occaasation.equalsIgnoreCase(listResponse.getData().get(i).getOccasion())){


                             for (int j = 0; j <listResponse.getData().get(i).getCheatPlans().size() ; j++) {

                                 if (str_category.trim().equalsIgnoreCase(listResponse.getData().get(i).getCheatPlans().get(j).getCategory())){
                                     ary_plan.addAll(listResponse.getData().get(i).getCheatPlans().get(j).getPlans());
                                 }

                             }



                         }
                     }


                     if (!ary_plan.isEmpty()){
                         txt_no_cheaet.setVisibility(View.GONE);
                         recyler_plansData.setVisibility(View.VISIBLE);
                         myCheatPlanAdapter=new MyCheatPlanAdapter(MyCheatPlanActivity.this,ary_plan,spinner_occasion.getSelectedItem().toString(),spinner_category.getSelectedItem().toString());
                         recyler_plansData.setAdapter(myCheatPlanAdapter);

                     }else {
                         txt_no_cheaet.setVisibility(View.VISIBLE);
                         recyler_plansData.setVisibility(View.GONE);
//                         Toast.makeText(mContext, "No data available", Toast.LENGTH_SHORT).show();
                         recyler_plansData.setAdapter(new MyCheatPlanAdapter(MyCheatPlanActivity.this,ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString()));


                     }


                 }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_category = parent.getItemAtPosition(position).toString();


                ary_plan=new ArrayList<>();

            if (listResponse!=null){
                for (int i = 0; i <  listResponse.getData().size(); i++) {

                    if (Str_occaasation.equalsIgnoreCase(listResponse.getData().get(i).getOccasion())){


                        for (int j = 0; j <listResponse.getData().get(i).getCheatPlans().size() ; j++) {

                            if (str_category.trim().equalsIgnoreCase(listResponse.getData().get(i).getCheatPlans().get(j).getCategory())){
                                ary_plan.addAll(listResponse.getData().get(i).getCheatPlans().get(j).getPlans());
                            }

                        }



                    }
                }

                if (!ary_plan.isEmpty()){
                    txt_no_cheaet.setVisibility(View.GONE);
                    recyler_plansData.setVisibility(View.VISIBLE);
                    myCheatPlanAdapter=new MyCheatPlanAdapter(MyCheatPlanActivity.this,ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString());
                    recyler_plansData.setAdapter(myCheatPlanAdapter);
                }else {
                    txt_no_cheaet.setVisibility(View.VISIBLE);
                    recyler_plansData.setVisibility(View.GONE);
                    recyler_plansData.setAdapter(new MyCheatPlanAdapter(MyCheatPlanActivity.this,ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString()));


                }
            }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
//        getOccastionMaster();
        getNewCheatMaster();

    }



    private void getUOMData() {
        utils.showProgressbar(MyCheatPlanActivity.this);
        Call<ClsMainPlansClass> call = foodService.getCheatPlan(userId);
            call.enqueue(new Callback<ClsMainPlansClass>()
            {
                @Override
                public void onResponse(Call<ClsMainPlansClass> call, Response<ClsMainPlansClass> response)
                {
                    utils.hideProgressbar();
                    List<String> tempList;
                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ll_cheat_header.setVisibility(View.VISIBLE);
                         listResponse = response.body();
                        if (listResponse != null && listResponse.getCode().equals("1"))
                        {
                            arylst_occasations=new ArrayList<>();
                            arylst_category=new ArrayList<>();

                            for (int i = 0; i <listResponse.getData().size() ; i++) {

                                for (int j = 0; j < listResponse.getData().get(i).getCheatPlans().size(); j++) {
                                    arylst_category.add(listResponse.getData().get(i).getCheatPlans().get(j).getCategory()+"    ");

                                }
                            }
                            if (!arylst_category.isEmpty()){
                                str_category=arylst_category.get(0).toString();
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, arylst_category);
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_category.setAdapter(adapter1);
                            }else {
                                ArrayList<String> stringArrayList=new ArrayList<>();
                                stringArrayList.add("No category   ");

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, stringArrayList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_category.setAdapter(adapter);
                            }









                        }
                        else
                        {
                            Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsMainPlansClass> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    utils.hideProgressbar();
                }
            });
        }


    private void getOccastionMaster() {
        Call<ClsOccastionMain> call = foodService.getOccasionMaster();
        call.enqueue(new Callback<ClsOccastionMain>()
        {
            @Override
            public void onResponse(Call<ClsOccastionMain> call, Response<ClsOccastionMain> response)
            {
                utils.hideProgressbar();
                List<String> tempList;
                ClsOccastionMain clsOccastionMain;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    clsOccastionMain = response.body();

                    if (clsOccastionMain != null && clsOccastionMain.getCode().equals("1"))

                    {
                        arylst_occasations=new ArrayList<>();
                        for (int i = 0; i <clsOccastionMain.getData().size() ; i++) {
                            arylst_occasations.add(clsOccastionMain.getData().get(i).getOccasion()+"  ");

                        }

                        if (!arylst_occasations.isEmpty()){
                            Str_occaasation=arylst_occasations.get(0).toString();

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, arylst_occasations);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_occasion.setAdapter(adapter);
                        }else {
                            ArrayList<String> stringArrayList=new ArrayList<>();
                            stringArrayList.add("No Occasion ");

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, stringArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_occasion.setAdapter(adapter);
                        }


                        getUOMData();


                    }
                    else
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsOccastionMain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void getNewCheatMaster() {
        Call<CheatPlanDataMain> call = foodService.getNewCheatMaster();
        call.enqueue(new Callback<CheatPlanDataMain>()
        {
            @Override
            public void onResponse(Call<CheatPlanDataMain> call, Response<CheatPlanDataMain> response)
            {
                utils.hideProgressbar();
                List<String> tempList;
                CheatPlanDataMain clsOccastionMain;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    clsOccastionMain = response.body();

                    if (clsOccastionMain != null && clsOccastionMain.getCode().equals("1"))

                    {

                         reeplaceItemAdapter=new ReeplaceItemAdapter(MyCheatPlanActivity.this,clsOccastionMain.getData());

                        RecyclerView recyler_newcheat=findViewById(R.id.recyler_newcheat);
                        recyler_newcheat.setAdapter(reeplaceItemAdapter);

                        getUOMData();


                    }
                    else
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheatPlanDataMain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String occaasation = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {



    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        reeplaceItemAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        reeplaceItemAdapter.getFilter().filter(newText);
        return false;
    }
    private void callMyPlanMasters()
    {
//        utils=new Utils();
//        if (!((Activity) mContext).isFinishing())
//        {
//            utils.showProgressbar(mContext);
//        }
        sessionManager=new SessionManager(mContext);
        int   userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        MyPlansService myPlansService = Client.getClient().create(MyPlansService.class);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<MyPlanMastersResponse> call = myPlansService.getMasterPlans(request);
        call.enqueue(new Callback<MyPlanMastersResponse>()
        {
            @Override
            public void onResponse(Call<MyPlanMastersResponse> call, Response<MyPlanMastersResponse> response)
            {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyPlanMastersResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        spinnerCategory = findViewById(R.id.spinner_MyPlan_Category);


                        final ArrayList<MyPlanMastersResponse.MasterData> spinnerList;

                        spinnerList = response.body().getData();

                        MyPlanMastersResponse.MasterData data = new MyPlanMastersResponse.MasterData();
                        data.setPlanName("Today's Plan");
                        data.setID(0);
                        spinnerList.add(0, data);
                        MyPlanMasterAdapter spinnerAdapter;


                        spinnerAdapter = new MyPlanMasterAdapter(mContext, spinnerList);
                        spinnerCategory.setAdapter(spinnerAdapter);


                        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                if (!iSFirstTime){
                                    iSFirstTime=true;
                                    return;
                                }
                                if (spinnerList!= null) {
                                    String planType = spinnerList.get(i).getPlanName();
                                    int planId = spinnerList.get(i).getID();

                                    if (planType.trim().equalsIgnoreCase("Food Plan")) {
                                        startActivity(new Intent(MyCheatPlanActivity.this, DietPlanActivity.class));
                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("Lifestyle Plan")) {
                                        Intent intent = new Intent(MyCheatPlanActivity.this, LifeStylePlanActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);

                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("REEplace Items")) {
                                        Intent intent = new Intent(MyCheatPlanActivity.this, MyCheatPlanActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);


                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }
                                    if (planType.trim().equalsIgnoreCase("Today's Plan")) {
                                        Intent intent = new Intent(MyCheatPlanActivity.this, MyPlansActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);


                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }



                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        for (int i = 0; i <spinnerList.size() ; i++) {
                            if (spinnerList.get(i).getPlanName().equalsIgnoreCase("REEplace Items")){
                                spinnerCategory.setSelection(i);
                                break;
                            }

                        }

                    }
                    else
                    {
                        Toast.makeText(mContext, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyPlanMastersResponse> call, Throwable t)
            {
//                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
            }
        });
    }
}


