package com.shamrock.reework.activity.MyPlansModule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodActivity;
import com.shamrock.reework.activity.FoodModule.adapter.ClsHealthCategorymasterAdapter;
import com.shamrock.reework.activity.FoodModule.model.ClsHealthCategoryMaster;
import com.shamrock.reework.activity.FoodModule.model.HealthCatogoryData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.MyPlansModule.adapter.GlosaryListAdapter;
import com.shamrock.reework.activity.MyPlansModule.model.GlosaryData;
import com.shamrock.reework.activity.MyPlansModule.model.GlosaryMainClass;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlosaryListActivity extends AppCompatActivity {

    private Utils utils;
    private SessionManager sessionManager;
    private FoodService foodService;
    private int userId;
    private ArrayList<GlosaryData> glosary_data_List;
    private RecyclerView recyler_glosary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glosary_list);
        recyler_glosary=findViewById(R.id.recyler_glosary);
        TextView tvTitle= findViewById(R.id.tvTitle);
        tvTitle.setText("Grocery List");
        ImageView imgLeft= findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        utils = new Utils();
        sessionManager = new SessionManager(GlosaryListActivity.this);
        foodService = Client.getClient().create(FoodService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        callAllRecipieslist();
    }
    private void callAllRecipieslist() {

        utils.showProgressbar(GlosaryListActivity.this);
        Call<GlosaryMainClass> call = foodService.getGroceryList(getIntent().getIntExtra("DataID",0));
//        Call<GlosaryMainClass> call = foodService.getGroceryList(4346);
        call.enqueue(new Callback<GlosaryMainClass>()
        {
            @Override

            public void onResponse(Call<GlosaryMainClass> call, Response<GlosaryMainClass> response)
            {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GlosaryMainClass listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        glosary_data_List = response.body().getData();
                        if (glosary_data_List!= null && glosary_data_List.size() > 0)
                        {
                            recyler_glosary.setAdapter(new GlosaryListAdapter(GlosaryListActivity.this,glosary_data_List));

                        }
                    }
                    else
                    {









                    }

                }
            }

            @Override
            public void onFailure(Call<GlosaryMainClass> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }



}
