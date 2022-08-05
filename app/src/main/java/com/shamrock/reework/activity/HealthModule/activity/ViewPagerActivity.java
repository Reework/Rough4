package com.shamrock.reework.activity.HealthModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.KKViewPager;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.ClsCommonLoaderFragment;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.ViewPagerAdapter;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ClsBeforePaymentCommonLoaderFragment;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionList;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.RegistrationRequest;
import com.shamrock.reework.api.response.FoodListByMealType;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPagerActivity extends AppCompatActivity {

    private KKViewPager vp_health_objective;
    private Pager PagerAdapter;
    private RelativeLayout dd;
   private Context context;
    private ViewPagerAdapter viewPagerAdapter;
    ArrayList<PaidSubscriptionData> clsHealthobjectiveArrayList;
    private TextView tvTitle;
    private ImageView imgLeft;
    Utils utils;
    HealthParametersService healthParametersService;
    RegistrationRequest registrationRequest;
    private String from="";
    private String strclose="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        context = ViewPagerActivity.this;
        vp_health_objective = findViewById(R.id.vp_health_objective);
        tvTitle=findViewById(R.id.tvTitle);
        imgLeft=findViewById(R.id.imgLeft);
        from=getIntent().getStringExtra("param");
        strclose=getIntent().getStringExtra("close");
        tvTitle.setText("Health objectives Plan");
//        registrationRequest= (RegistrationRequest) getIntent().getSerializableExtra("Registration");
        utils=new Utils();
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from!=null&&from.equalsIgnoreCase("From_no_plan")){

                    finish();
                    return;

                }



                if (strclose!=null&&strclose.equalsIgnoreCase("expired")){
                    showCloseDailog();

                }else {
                    finish();

                }


            }
        });
        getPaymentDetailsApi();


    }

    private void showCloseDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Are you sure want to close the app")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        finish();

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
        alert.show();
    }

    private void getPaymentDetailsApi()
    {
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        SessionManager sessionManager=new SessionManager(context);
        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        Call<PaidSubscriptionList> call = healthParametersService.getPaymentDetails(userId);
        call.enqueue(new Callback<PaidSubscriptionList>()
        {
            @Override
            public void onResponse(Call<PaidSubscriptionList> call, Response<PaidSubscriptionList> response)
            {
                utils.hideProgressbar();
                if (response.body() != null && response.body().getCode() == 1){
                    clsHealthobjectiveArrayList=new ArrayList<>();
                    clsHealthobjectiveArrayList.addAll(response.body().getData());

                    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                    for (int i = 0; i < clsHealthobjectiveArrayList.size(); i++) {
                        adapter.addFrag(new ClsBeforePaymentCommonLoaderFragment(clsHealthobjectiveArrayList.get(i),clsHealthobjectiveArrayList,from,i,clsHealthobjectiveArrayList.size()));
                    }
                    vp_health_objective.setAdapter(adapter);
                }



            }


            @Override
            public void onFailure(Call<PaidSubscriptionList> call, Throwable t)
            {
            }
        });

    }
}