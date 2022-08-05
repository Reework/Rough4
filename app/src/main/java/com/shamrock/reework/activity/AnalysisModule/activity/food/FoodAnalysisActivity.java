package com.shamrock.reework.activity.AnalysisModule.activity.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.MicrorlNutritionActivityAnalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.CaloryGeneratePDF;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ColoriesAnalysisActivty;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalAdapter;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalPojo;
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.activity.EditMealActivity;
import com.shamrock.reework.activity.FoodModule.activity.MasterDetailsActivity;
import com.shamrock.reework.activity.FoodModule.adapter.WeeklyAnalysisAdapter;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragment;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragmentAllMeals;
import com.shamrock.reework.activity.FoodModule.model.FoodAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisRequest;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.fragment.MasterMindFragment;
import com.shamrock.reework.fragment.MasterMyActivityFragment;
import com.shamrock.reework.fragment.MasterSleepFragment;
import com.shamrock.reework.fragment.MasterWaterFragment;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.MyValueFormatter;
import com.shamrock.reework.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodAnalysisActivity extends AppCompatActivity {


    private ViewPager mViewPager;
    TabLayout tabLayout;
    private int FRAGMENT_POSITION = 0;

    Toolbar toolbar;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;
    private Context context;
    int userId,reecoachID;
    SessionManager sessionManager;


    LinearLayout parent;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_analysis);
        context= FoodAnalysisActivity.this;
        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        final TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);

        tvTitle.setText("Calories");



        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        counterValuePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(), NotificationsActivity.class), 500);

            }
        });

        final Button btn_calories = findViewById(R.id.btn_calories);

        final Button btn_nutrition = findViewById(R.id.btn_nutrition);
        SessionManager  session = new SessionManager(context);
        session.setStringValue("Allanay","select");

        final Fragment fragment=new CaloriesAnalysisFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, fragment, null)
                .commit();
        btn_calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, fragment, null)
                        .commit();
                tvTitle.setText("Calories");
                btn_calories.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_calories.setTextColor(getResources().getColor(R.color.white));
                btn_nutrition.setBackgroundColor(getResources().getColor(R.color.white));
                btn_nutrition.setTextColor(getResources().getColor(R.color.black));
//


            }
        });

        btn_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_nutrition.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_nutrition.setTextColor(getResources().getColor(R.color.white));
                btn_calories.setBackgroundColor(getResources().getColor(R.color.white));
                btn_calories.setTextColor(getResources().getColor(R.color.black));

                tvTitle.setText("Nutrition");
                Fragment fragment=new NutritionAnalysisFragment();
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, fragment, null)
                        .commit();
//                Intent i =new Intent(getApplicationContext(),ColoriesAnalysisActivty.class);
//                startActivity(i);

            }
        });

        parent = findViewById(R.id.parent);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());



        notificationService = Client.getClient().create(NotificationService.class);
        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
//                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
//                        PushFcmToServer();
                    } else if (intent.getAction().equals(FcmConstants.PUSH_NOTIFICATION)) // new push notification is received
                    {
                        String title = intent.getStringExtra(FcmConstants.FCM_TITLE);
                        String message = intent.getStringExtra(FcmConstants.FCM_MESSEGE);
                        mNotifcationCount = intent.getIntExtra(FcmConstants.FCM_COUNT, 0);

                        if (mNotifcationCount > 0) {
                            tvNotificationCOunt.setText(String.valueOf(mNotifcationCount));
                            invalidateOptionsMenu();
                        } else {
                            if (Utils.isNetworkAvailable(context))
                                GetAllNotificationCount();
                            else
                                Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if (Utils.isNetworkAvailable(getApplicationContext()))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();





    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            parent.setScaleX(mScaleFactor);
            parent.setScaleY(mScaleFactor);
            return true;
        }
    }

    private void setToolBar()
    {




        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }
    }


    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userId);

        Call<NotifCountResponse> call = notificationService.getAllNotificationCount(request);

        call.enqueue(new Callback<NotifCountResponse>() {
            @Override
            public void onResponse(Call<NotifCountResponse> call, Response<NotifCountResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    NotifCountResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mNotifcationCount = listResponse.getData();

                        invalidateOptionsMenu();
                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifCountResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }



}