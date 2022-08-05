package com.shamrock.reework.activity.AnalysisModule.activity.food;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

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

public class FoodNapMainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {


    private LineChart lineChart;
    private Utils utils;
    private Context context;
    private NotificationService notificationService;
    private SessionManager sessionManager;
    private int userId;
    private Spinner spinner;
    ArrayList<ClsCustomFoodNapData> arylst_ClsCustomFoodNapData;

    //showdatedata
    private TextView txt_show_sleep_to,txt_show_sleep_from;
    private ImageView img_add_sleep_date;
    private TextView txt_sleep_date_from_dialog;
    private TextView txt_sleep_date_to_dialog;
    private String submitFromDate;
    private String submitToDate;
    private String dummydate_from;
    TextView txt_sleep_date_from, txt_sleep_date_to;
    private String dummydate_to;
    private String StrDateOpen;
    private DatePickerDialog datepickerdialog;
    private CommonService commonService;
    private DatePickerDialog datepickerdialog_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_nap_analysis);
        context= FoodNapMainActivity.this;
        showDateWiseData();

        sessionManager=new SessionManager(context);
        utils=new Utils();
        lineChart = findViewById(R.id.linegraph);
        lineChart.setVisibility(View.VISIBLE);
        Description description = new Description();
        description.setText("Time(hh.mm)");
        lineChart.setDescription(description);
        notificationService = Client.getClient().create(NotificationService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        spinner = findViewById(R.id.spinner_Weight_Months);
        spinner.setOnItemSelectedListener(this);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Food Discipline");
        getMeonthList();

       TextView labelMyProgress = findViewById(R.id.labelMyProgress);

        labelMyProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!arylst_ClsCustomFoodNapData.isEmpty()){
                    new FoodNapPDF(arylst_ClsCustomFoodNapData, FoodNapMainActivity.this, "").execute();

                }else {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                }
            }
        });


        LinearLayout ll_sleep_date=findViewById(R.id.ll_sleep_date);
        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetFoodhistroy();
            }
        });


    }


    private void setGraphData(ArrayList<ClsCustomFoodNapData> arylst_ClsCustomFoodNapData){
        lineChart.setData(null);
        lineChart.invalidate();
        lineChart.clear();

        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();
        ArrayList<Entry> y3 = new ArrayList<>();
        ArrayList<Entry> y4 = new ArrayList<>();
        ArrayList<Entry> y5 = new ArrayList<>();
        ArrayList<Entry> y6 = new ArrayList<>();

        for (int i = 0; i < arylst_ClsCustomFoodNapData.size(); i++) {
            try {
                if (arylst_ClsCustomFoodNapData.get(i).getBeforeBreakfast() != 0) {
                    y1.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomFoodNapData.get(i).getBeforeBreakfast()))));
                }
                if (arylst_ClsCustomFoodNapData.get(i).getBreakfast() != 0) {
                    y2.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomFoodNapData.get(i).getBreakfast()))));
                }
                if (arylst_ClsCustomFoodNapData.get(i).getLunch() != 0) {
                    y3.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomFoodNapData.get(i).getLunch()))));
                }

                if (arylst_ClsCustomFoodNapData.get(i).getEveningSnacks() != 0) {
                    y4.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomFoodNapData.get(i).getEveningSnacks()))));
                }

                if (arylst_ClsCustomFoodNapData.get(i).getDinner() != 0) {
                    y5.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomFoodNapData.get(i).getDinner()))));
                }

                if (arylst_ClsCustomFoodNapData.get(i).getDessert() != 0) {
                    y6.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomFoodNapData.get(i).getDessert()))));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        lineChart.getLegend().setEnabled(false);


        if (y1.size()>0){
            LineDataSet setComp1 = new LineDataSet(y1, "Before Breakfast(hh.mm)");
            setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp1.setFillAlpha(110);
            setComp1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp1.setCircleColors(getResources().getColor(R.color.colorAccent));
            setComp1.setCircleRadius(5);


            setComp1.setColor(ContextCompat.getColor
                    (this,R.color.colorAccent));
            setComp1.setLineWidth(2f);
            setComp1.setValueFormatter(new FoodvalueFormatter());
            dataSets.add(setComp1);


        }


        if (y2.size()>0){
            LineDataSet setComp2 = new LineDataSet(y2, " Breakfast(hh.mm)");
            setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp2.setValueFormatter(new FoodvalueFormatter());
            setComp2.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp2.setCircleColors(getResources().getColor(R.color.colorGreen1));
            setComp2.setCircleRadius(5);


            setComp2.setColor(ContextCompat.getColor(this,R.color.colorGreen1));
            setComp2.setLineWidth(2f);
            dataSets.add(setComp2);

        }




        if (y3.size()>0){
                    LineDataSet setComp3 = new LineDataSet(y3, "Lunch(hh.mm)");
        setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp3.setValueFormatter(new FoodvalueFormatter());
            setComp3.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp3.setCircleColors(getResources().getColor(R.color.dark_grey_blue));
            setComp3.setCircleRadius(5);

            setComp3.setColor(ContextCompat.getColor(this,R.color.dark_grey_blue));
        setComp3.setLineWidth(2f);
            dataSets.add(setComp3);

        }




        if (y4.size()>0){
            LineDataSet setComp4 = new LineDataSet(y4, "Evening snacks(hh.mm)");
            setComp4.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp4.setValueFormatter(new FoodvalueFormatter());
            setComp4.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp4.setCircleColors(getResources().getColor(R.color.color_graph_orange));
            setComp4.setCircleRadius(5);

            setComp4.setColor(ContextCompat.getColor(this,R.color.color_graph_orange));
            setComp4.setLineWidth(2f);
            dataSets.add(setComp4);

        }

        if (y5.size()>0){

            LineDataSet setComp5 = new LineDataSet(y5, "Dinner(hh.mm)");
            setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp5.setValueFormatter(new FoodvalueFormatter());
            setComp5.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp5.setCircleColors(getResources().getColor(R.color.colorReescore_BlueYellow));
            setComp5.setCircleRadius(5);

            setComp5.setColor(ContextCompat.getColor(this,R.color.colorReescore_BlueYellow));
            setComp5.setLineWidth(2f);
            dataSets.add(setComp5);

        }


        if (y6.size()>0){

            LineDataSet setComp5 = new LineDataSet(y6, "Dessert(hh.mm)");
            setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp5.setValueFormatter(new FoodvalueFormatter());
            setComp5.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp5.setCircleColors(getResources().getColor(R.color.title_gray));
            setComp5.setCircleRadius(5);

            setComp5.setColor(ContextCompat.getColor(this,R.color.title_gray));
            setComp5.setLineWidth(2f);
            dataSets.add(setComp5);

        }




        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(2f);

        if (arylst_ClsCustomFoodNapData.size()==1){
            xAxis.setLabelCount(arylst_ClsCustomFoodNapData.size());

        }else if (arylst_ClsCustomFoodNapData.size()>10){
            xAxis.setLabelCount(10);

        }
        else {
            xAxis.setLabelCount(arylst_ClsCustomFoodNapData.size(),true);

        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount(arylst_ClsCustomFoodNapData)));


        lineChart.getXAxis().setTextColor(R.color.white);


        YAxis left = lineChart.getAxisLeft();
        left.setTextColor(Color.WHITE); // axis line colour
        left.setDrawAxisLine(true); // no axis line
        left.setAxisLineColor(Color.WHITE); // axis line colour
        left.setDrawGridLines(true); // no grid lines
        left.setGridColor(ContextCompat.getColor(context, R.color.colorGreyLight2)); // no grid lines
        left.setGridColor(Color.WHITE); // grid li



//        xAxis.setDrawLabels(false); // no axis labels
        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate(); // refresh

    }

    private void getMeonthList() {


        utils.showProgressbar(context);


        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));
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
                            Toast.makeText(FoodNapMainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
//                            finish();
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
        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));
        Call<ClsMainFoodNap> call = notificationService.getFoodAnalysis(clsHistoryRequest);

        call.enqueue(new Callback<ClsMainFoodNap>()
        {
            @Override
            public void onResponse(Call<ClsMainFoodNap> call, Response<ClsMainFoodNap> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsMainFoodNap listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                   ArrayList<FoodNapData>  tempList = listResponse.getData();
                        arylst_ClsCustomFoodNapData=new ArrayList<>();
                                if (tempList!=null){
                                    if (!tempList.isEmpty()){
                                        for (int i = 0; i <tempList.size() ; i++) {

                                            arylst_ClsCustomFoodNapData.add(new ClsCustomFoodNapData(tempList.get(i).getCreatedOn(),
                                                    tempList.get(i).getMealIntakes().getBeforeBreakfast(),
                                                    tempList.get(i).getMealIntakes().getBreakfast(),
                                                    tempList.get(i).getMealIntakes().getLunch(),
                                                    tempList.get(i).getMealIntakes().getEveningSnacks(),
                                                    tempList.get(i).getMealIntakes().getDinner(),
                                                    tempList.get(i).getMealIntakes().getDessert()

                                            ));

                                        }

                                    }
                                }

                            if (!arylst_ClsCustomFoodNapData.isEmpty()){
                                setGraphData(arylst_ClsCustomFoodNapData);

                            }else {
                                lineChart.setData(null);
                                lineChart.clear();
                                lineChart.invalidate();


                            }



                    }
                    else
                    {

//                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<ClsMainFoodNap> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }

    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMMM");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    public ArrayList<String> getAreaCount(ArrayList<ClsCustomFoodNapData> clsSleepData) {

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < clsSleepData.size(); i++)

            label.add(formatDatesNew(clsSleepData.get(i).getCreatedOn()));
        return label;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getItemAtPosition(position) != null)
        {
            String monthName = parent.getItemAtPosition(position).toString();
            if (Utils.isNetworkAvailable(context))
            {
                GetAllNotifications(monthName);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void showNewgetFoodhistroy() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog=dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog=dialog.findViewById(R.id.txt_sleep_date_to_dialog);
        TextView txt_dialog_header=dialog.findViewById(R.id.txt_dialog_header);
        txt_dialog_header.setText("Set Date");


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);
        btn_submit_sleep_hours.setText("Submit");

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    dialog.dismiss();

                    GetAllNotifications("");

//                    getFoodAnalysis(submitFromDate,submitToDate);
                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }

            }
        });


//        txt_show_sleep_from.setText(dummydate_from);
        txt_sleep_date_from_dialog.setText(dummydate_from);
//        txt_show_sleep_to.setText(dummydate_to);
        txt_sleep_date_to_dialog.setText(dummydate_to);

        showDatePickerDailog();

        txt_sleep_date_from_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="from";
                datepickerdialog.show();

            }
        });

        txt_sleep_date_to_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";
                datepickerdialog.show();
            }
        });

        ImageView img_close=dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }
    private void showDatePickerDailog() {
//        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog.getDatePicker().setMaxDate(maxDate);



        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//        Calendar c1 = Calendar.getInstance();
//        c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
//        datepickerdialog.getDatePicker().setMinDate(c1.getTimeInMillis());


        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }private void showDateWiseData() {
        try {
            img_add_sleep_date=findViewById(R.id.img_add_sleep_date);
            txt_show_sleep_to=findViewById(R.id.txt_show_sleep_to);
            txt_show_sleep_from=findViewById(R.id.txt_show_sleep_from);
            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            dummydate_from = formatDatesSleep(submitFromDate);
            txt_show_sleep_from.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_to.setText(dummydate_to);
            img_add_sleep_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNewgetFoodhistroy();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String formatDatesSleep(String dateFromServer) {

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
    public String getFromattedStringDate(int s){


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (StrDateOpen.equalsIgnoreCase("from")) {

            if (month<10){
                dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1) + "-" + year;

            }else {
                dummydate_from = dayOfMonth + "-" +getFromattedStringDate(month+1) + "-" + year;

            }







            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");

            Date date1 = null;
            Date date2 = null;
            try {
                date1 = simpleDateFormat.parse(dummydate_from);
                date2 = simpleDateFormat.parse(dummydate_to);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (date1.after(date2)) {
                Toast.makeText(context, "From date should be greater than End date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_show_sleep_from.setText(dummydate_from);
            txt_sleep_date_from_dialog.setText(dummydate_from);
            submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;


        }


        if (StrDateOpen.equalsIgnoreCase("to")) {


            if (!dummydate_from.trim().isEmpty()){



                if (month<10){
                    dummydate_to = dayOfMonth + "-" + "0"+(month + 1) + "-" + year;

                }else  {
                    dummydate_to = dayOfMonth + "-" +(month + 1) + "-" + year;

                }

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = simpleDateFormat.parse(dummydate_from.trim());
                    date2 = simpleDateFormat.parse(dummydate_to.toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if (date1.after(date2)) {
                    Toast.makeText(context, "End date should be greater than Start date", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    txt_show_sleep_to.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;
                }
            }else {
                Toast.makeText(context, "Please select Start date", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
