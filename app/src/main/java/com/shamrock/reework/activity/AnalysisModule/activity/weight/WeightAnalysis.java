package com.shamrock.reework.activity.AnalysisModule.activity.weight;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.WeightModule.MyWeight;
import com.shamrock.reework.activity.WeightModule.UserWeightRequest;
import com.shamrock.reework.activity.WeightModule.service.WeightService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GraphRequest;
import com.shamrock.reework.api.request.WeightMonthsRequest;
import com.shamrock.reework.api.response.GraphResponse;
import com.shamrock.reework.api.response.WeightMonthsResponse;
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
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeightAnalysis extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener, View.OnClickListener
{

   private Context context;
   private Toolbar toolbar;
   private Typeface font;
   private LineChart mChart;
   private SessionManager sessionManager;
   private EditText editTextCurrent, editTextIdeal;
   private Spinner spinner;
   private int userID;
   private WeightService weightService;
   private HealthParametersService healthParametersService;
    private List<String> WeightMonthsList;
    private Utils utils;
    int check = 0;
    private Map<String, Float> dataMain;
   private TextView labelMyProgress;
   private LinearLayout mainLayout;
   private RelativeLayout noInternetLayout;
   private Button btnRefresh;
   private Button buttonSubmitWeight;
   private EditText editText_current_user_weight;
   private NotificationService notificationService;
    private int userId;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_analysis);
        context = WeightAnalysis.this;
        showDateWiseData();

        notificationService = Client.getClient().create(NotificationService.class);
        sessionManager = new SessionManager(context);
        labelMyProgress=findViewById(R.id.labelMyProgress);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        init();
        setToolBar();
        findViews();


        LinearLayout ll_sleep_date=findViewById(R.id.ll_sleep_date);
        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetFoodhistroy();
            }
        });
    }




    private void init()
    {
        sessionManager = new SessionManager(context);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        weightService = Client.getClient().create(WeightService.class);
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Weight");
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
        editTextCurrent = findViewById(R.id.editText_WeightCurrent);
        editTextIdeal = findViewById(R.id.editText_WeightIdeal);
        editText_current_user_weight = findViewById(R.id.editText_current_user_weight);
        editText_current_user_weight.requestFocus();

        spinner = findViewById(R.id.spinner_Weight_Months);
        buttonSubmitWeight = findViewById(R.id.buttonSubmitWeight);
        buttonSubmitWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_current_user_weight.getText().toString().trim().isEmpty()){
                    hideSoftKeyboard();

                }else {
                    Toast.makeText(context, "Please enter the current weight.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        spinner.setOnItemSelectedListener(this);
        mChart = findViewById(R.id.lineChart);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);


        if (Utils.isNetworkAvailable(context))
        {
            callGraphApi(0,"");

//            callMonthsApi(userID, false);
        }
        else
            showLayouts();
    }


    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnRefresh)
        {
            if (Utils.isNetworkAvailable(context))
            {
                showLayouts();

                callGraphApi(userID,WeightMonthsList.get(0).toString());

//                callMonthsApi(userID, false);
            }
            else
                showLayouts();
        }
    }

    private void callMonthsApi(int user_ID, final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        WeightMonthsRequest request = new WeightMonthsRequest();
        request.setUserid(user_ID);
        Call<WeightMonthsResponse> call = weightService.getWeightMonthsList(request);
        call.enqueue(new Callback<WeightMonthsResponse>()
        {
            @Override
            public void onResponse(Call<WeightMonthsResponse> call, Response<WeightMonthsResponse> response)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    WeightMonthsResponse weightMonthsResponse = response.body();

                    Log.d("wt_data",weightMonthsResponse.toString());

                    if (weightMonthsResponse != null && weightMonthsResponse.getCode() == 1)
                    {
                        double currentWeight = weightMonthsResponse.getCurrentWeight();
                        double idealWeight = weightMonthsResponse.getIdealWeight();

                        editTextCurrent.setText(String.format(Locale.getDefault(), "%.2f kg", currentWeight));
                        editTextIdeal.setText(String.format(Locale.getDefault(), "%.2f kg", idealWeight));

                        // set month spinner Data
                        final List<WeightMonthsResponse.MonthDataResponse> MonthsList = response.body().getMonthData();
                        WeightMonthsList = new ArrayList<>();

                        for (WeightMonthsResponse.MonthDataResponse monthDataResponse : MonthsList)
                        {
                            WeightMonthsList.add(monthDataResponse.getHealthdate());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item_white, WeightMonthsList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        utils.hideProgressbar();
                        spinner.setVisibility(View.VISIBLE);
                        List<GraphResponse.GraphEntityData> graphEntityDataArrayList = weightMonthsResponse.getGraphData();
                        ArrayList<Entry> yAxis = new ArrayList<>();
                        ArrayList<String> xAxis = new ArrayList<>();

                        if (graphEntityDataArrayList != null)
                        {
                            for (int i = 0; i < graphEntityDataArrayList.size(); i++)
                            {
                                yAxis.add(new Entry(i, (float) graphEntityDataArrayList.get(i).getWeight()));
                                xAxis.add(graphEntityDataArrayList.get(i).getMonth().trim());
                            }


                            if (yAxis.size() > 0)
                            {


                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "" + weightMonthsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeightMonthsResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

        if (++check > 1)
        {
            if (adapterView.getItemAtPosition(i) != null)
            {
                String monthName = adapterView.getItemAtPosition(i).toString();

                if (Utils.isNetworkAvailable(context))
                {
                    callGraphApi(userID, monthName);
                }
                else
                    showLayouts();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void callGraphApi(int userID, final String monthName)
    {
//        GraphRequest request = new GraphRequest();
//        request.setUserid(userID);
//        request.setMonthName(monthName);
        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));



        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);

        }

        Call<GraphResponse> call = weightService.getWeightAnalysis(clsHistoryRequest);
        call.enqueue(new Callback<GraphResponse>()
        {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    mChart.clear();
                    mChart.setData(null);
                    mChart.highlightValue(null);
                    mChart.invalidate();
                    GraphResponse graphResponse = response.body();

                    if (graphResponse != null && graphResponse.getCode() == 1)
                    {
                        final List<GraphResponse.GraphEntityData> graphEntityList = graphResponse.getData();
                        Log.d("sunit",graphEntityList.toString());

                        final ArrayList<Entry> yAxisValues = new ArrayList<>();
                        final ArrayList<String> xAxisValues = new ArrayList<>();

                        labelMyProgress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (graphEntityList!=null&&!graphEntityList.isEmpty()){
                                    new WeightGeneratePDF(graphEntityList,context,"").execute();

                                }else {
                                    Toast.makeText(WeightAnalysis.this, "No data available", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                        if (graphEntityList != null)
                        {
                            for (int i = 0; i < graphEntityList.size(); i++)
                            {
                                yAxisValues.add(new Entry(i, (float) graphEntityList.get(i).getWeight()));
                                xAxisValues.add(graphEntityList.get(i).getMonth());
                            }




                            if (yAxisValues.size() > 0)
                            {
                                initChartData(yAxisValues, xAxisValues);

                            }
                        }
                    }
                    else
                    {
                        mChart.clear();
                        mChart.setData(null);
                        mChart.highlightValue(null);
                        mChart.invalidate();
                        Toast.makeText(WeightAnalysis.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                    Toast.makeText(WeightAnalysis.this, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t)
            {
                utils.hideProgressbar();


            }
        });
    }




    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    private void initChartData(final ArrayList<Entry> yVals, final ArrayList<String> xVals)
    {
        mChart.clear();
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.white));
        Log.d("sunit",xVals.toString());
        Spannable wordtoSpan = new SpannableString("Monthly weights(kg)");
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        LineDataSet set1 = new LineDataSet(yVals, "Monthly weights(kg)");
        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        set1.setCircleColor(getResources().getColor(R.color.colorMediumSpringGreen));
        set1.setCircleRadius(5);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);
        mChart.setNoDataText("Monthly weights(kg)");
        XAxis xAxis = mChart.getXAxis();

        if (xVals.size()==1){
            xAxis.setLabelCount(xVals.size());

        }else if (xVals.size()>10){
            xAxis.setLabelCount(10);

        }
        else {
            xAxis.setLabelCount(xVals.size(),true);

        }


        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setTextColor(Color.parseColor("#FFFFFF")); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setTextSize(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxi
        mChart.getXAxis().setTextColor(R.color.white);
        YAxis left = mChart.getAxisLeft();
        left.setTextColor(Color.WHITE); // axis line colour
        left.setDrawAxisLine(true); // no axis line
        left.setAxisLineColor(Color.WHITE); // axis line colour
        left.setDrawGridLines(true); // no grid lines
        left.setGridColor(ContextCompat.getColor(context, R.color.colorGreyLight2)); // no grid lines
        left.setGridColor(Color.WHITE); // grid li
        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount(xVals)));
        left.setTextColor(Color.WHITE); // axis line colour
        left.setDrawAxisLine(true); // no axis line
        left.setAxisLineColor(Color.WHITE); // axis line colour
        left.setDrawGridLines(true); // no grid lines
        left.setGridColor(ContextCompat.getColor(context, R.color.colorGreyLight2)); // no grid lines
        left.setGridColor(Color.WHITE); // grid line colour

        if (yVals.size() > 4)
        {
            left.setAxisLineWidth(2); // axis line colour
        }

        YAxis right = mChart.getAxisRight();
        right.setEnabled(false); // no right axis

        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
    }

    public String formatDatesWeight(String dateFromServer)
    {
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMMM");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


    public ArrayList<String> getAreaCount(ArrayList<String> clsSleepData) {

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < clsSleepData.size(); i++)

            label.add(formatDatesWeight(clsSleepData.get(i).toString()));
        return label;
    }
    private void ShowRetryBar(String msg)
    {

        String strMessage;
        if (msg.isEmpty())
        {
            strMessage = "Unable to load data";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(context))
                        {
                            showLayouts();
//                            callMonthsApi(userID, false);
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }


    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                    callGraphApi(0,"");

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
        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");


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
