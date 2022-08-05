package com.shamrock.reework.activity.AnalysisModule.activity.food;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFoodGraphActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, View.OnClickListener{
    Context context;
    Toolbar toolbar;
    Utils utils;
    SessionManager sessionManager;
    NotificationService notificationService;

    RelativeLayout noInternetLayout;
    LinearLayout mainLayout;
    Button btnRefresh;
    LineChart linegraph;

    ArrayList<GetAllNotificationResponse.Notifications> mNotificationList;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    LoginService loginService;
    int userId;
    Spinner spinner;
    private boolean isFirrstime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_graph_ana);
        context= NewFoodGraphActivity.this;
        com.github.mikephil.charting.utils.Utils.init(context);

        spinner = findViewById(R.id.spinner_Weight_Months);

        spinner.setOnItemSelectedListener(this);
        linegraph=findViewById(R.id.linegraph);

        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Calories Consumption");
        utils = new Utils();
        sessionManager = new SessionManager(context);
        notificationService = Client.getClient().create(NotificationService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        loginService = Client.getClient().create(LoginService.class);

        getMeonthList();
    }

    private void getMeonthList() {


        utils.showProgressbar(context);

//        GetAllNotificationRequest request = new GetAllNotificationRequest();
//        request.setUserid(userId);

        Call<ClsCaloriesMonth> call = notificationService.getCaloriesMonth(userId);

        call.enqueue(new Callback<ClsCaloriesMonth>()
        {
            @Override
            public void onResponse(Call<ClsCaloriesMonth> call, Response<ClsCaloriesMonth> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsCaloriesMonth listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        ArrayList<String> tempList = listResponse.getData();
                        ArrayList<Data> test=new ArrayList<>();


                        if (tempList != null && tempList.size() > 0)
                        {
                            setmonthData(tempList);

                        }else {
                            Toast.makeText(NewFoodGraphActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<ClsCaloriesMonth> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });

    }

    private void setmonthData(ArrayList<String> tempList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item_white, tempList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        GetAllNotifications(tempList.get(0).toString());


    }

    private void GetAllNotifications(String monthName)
    {
        utils.showProgressbar(context);

//        GetAllNotificationRequest request = new GetAllNotificationRequest();
//        request.setUserid(userId);

        Call<ClsCalorianalysis> call = notificationService.getcaloriesHistory(new ClsHistoryRequest());

        call.enqueue(new Callback<ClsCalorianalysis>()
        {
            @Override
            public void onResponse(Call<ClsCalorianalysis> call, Response<ClsCalorianalysis> response)
            {
                utils.hideProgressbar();
                linegraph.setVisibility(View.VISIBLE);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsCalorianalysis listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        ArrayList<Data> tempList = listResponse.getData();
                        ArrayList<Data> test=new ArrayList<>();


                        if (tempList != null && tempList.size() > 0)
                        {

                            linegraph.clear();
                            linegraph.setData(null);
                            linegraph.highlightValue(null);
                            linegraph.invalidate();
                            setData(tempList);

                        }else {
                            isFirrstime=true;
                            if (isFirrstime){
                               isFirrstime=false;

                            }else {
                                linegraph.clear();
                                linegraph.setData(null);
                                linegraph.highlightValue(null);
                                linegraph.invalidate();
                            }

                            Toast.makeText(NewFoodGraphActivity.this, "No data available", Toast.LENGTH_SHORT).show();
//                            finish();
                        }
                    }
                    else
                    {
                        linegraph.clear();
                        linegraph.setData(null);
                        linegraph.highlightValue(null);
                        linegraph.invalidate();
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<ClsCalorianalysis> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }

    private void setData(final ArrayList<Data> aryCalories) {





        final ArrayList<Entry> yValues1=new ArrayList<>();
        ArrayList<Entry> yValues2=new ArrayList<>();
        ArrayList<Entry> yValues3=new ArrayList<>();
        ArrayList<Entry> yValues4=new ArrayList<>();

        for (int i = 0; i <aryCalories.size() ; i++) {
            yValues1.add(new Entry(i, Float.parseFloat(aryCalories.get(i).getBruntCalories())));
            yValues2.add(new Entry(i,Float.parseFloat(aryCalories.get(i).getConsumedCalories())));
            yValues3.add(new Entry(i,Float.parseFloat(aryCalories.get(i).getNetCalories())));
            yValues4.add(new Entry(i,Float.parseFloat(aryCalories.get(i).getScheduledCalories())));

        }


        linegraph.setVisibility(View.VISIBLE);

        LineDataSet set1,set2,set3,set4;
        set1=new LineDataSet(yValues1,"Burnt");
        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(this, R.color.mobikwik_orange));
        set1.setLineWidth(2f);

        set2=new LineDataSet(yValues2,"Consumed");
        set2.setFillAlpha(110);
        set2.setColor(ContextCompat.getColor(this,R.color.mobikwik_blue_dark));
        set2.setLineWidth(2f);
        set3=new LineDataSet(yValues3,"Net");
        set3.setFillAlpha(110);
        set3.setColor(ContextCompat.getColor(this,R.color.dark_grey_blue));
        set3.setLineWidth(2f);

        set4=new LineDataSet(yValues4,"Scheduled");
        set4.setFillAlpha(110);
        set4.setColor(ContextCompat.getColor(this,R.color.haldi));
        set4.setLineWidth(2f);
        final LineData data=new LineData(set1,set2,set3,set4);
        linegraph.setData(data);


        Description description=new Description();
        linegraph.getDescription().setText("Calories");


        XAxis xAxis = linegraph.getXAxis();
//        xAxis.setDrawLabels(false); // no axis labels
        xAxis.setTextColor(Color.parseColor("#000000")); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.BLACK); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setTextSize(2f);

        xAxis.setLabelCount(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis at bottom
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                for (int i = 0; i < aryCalories.size(); ++i)
                {
                    if (yValues1.get(i).getX() == value)
                    {

                        int index = aryCalories.get(i).getCreatedOn().indexOf("T");

                        return formatDates(aryCalories.get(i).getCreatedOn().substring(0,index));

                    }

                }


                return "";
            }
        });



new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {

        linegraph.setActivated(true);
        linegraph.performClick();

        linegraph.notifyDataSetChanged();
    }
},500);


    }
    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.getItemAtPosition(position) != null)
        {
            String monthName = parent.getItemAtPosition(position).toString();

            if (Utils.isNetworkAvailable(context))
            {
                GetAllNotifications(monthName);
//                callGraphApi(userID, monthName);
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
