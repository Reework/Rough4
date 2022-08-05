package com.shamrock.reework.activity.WeightModule.activity;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.WeightModule.MyWeight;
import com.shamrock.reework.activity.WeightModule.UserWeightRequest;
import com.shamrock.reework.activity.WeightModule.service.WeightService;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.waterhistory.ClsWeightHistoryRequest;
import com.shamrock.reework.activity.weighthistory.OnEditWeightClick;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GraphRequest;
import com.shamrock.reework.api.request.WeightMonthsRequest;
import com.shamrock.reework.api.response.GraphResponse;
import com.shamrock.reework.api.response.WeightMonthsResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeightNewActivity extends AppCompatActivity implements OnEditWeightClick,DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener, View.OnClickListener
{
                                                                //implements OnChartGestureListener, OnChartValueSelectedListener {

    Context context;
    Toolbar toolbar;
    Typeface font;
    LineChart mChart;
    SessionManager sessionManager;
    EditText editTextCurrent, editTextIdeal;
    Spinner spinner;
    int userID;
    WeightService weightService;
    HealthParametersService healthParametersService;
    private List<String> WeightMonthsList;
    private Utils utils;
    int check = 0;
    private Map<String, Float> dataMain;
    ListView list_wt_history;

    LinearLayout ll_add_wt_history;
    RadioButton rd_button_wt,rd_button_wt_history;
LinearLayout ll_current_wt,ll_history_wt;

    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    DatePickerDialog datepickerdialog;

    Button buttonSubmitWeight;
    EditText editText_current_user_weight;
    ViewFlipper vp_wt;
    private String StrDateOpen="";
    private String dummydate_from="";
    private String dummydate_to="";
    private TextView txt_wt_date_to;
    private TextView txt_wt_date_from;
    private String submitFromDate="";
    private String submitToDate="";
    CommonService commonService;

    TextView btn_show_wt_history;
    private String submitHistoryDate="";
    private String dummyhistoryshowdate="";

    private TextView labelCalculateWeight;
    private String curentDate="";
    private int apiWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wt_new);
        context = WeightNewActivity.this;
        init();
        setToolBar();
        findViews();
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
        rd_button_wt = findViewById(R.id.rd_button_wt);
        rd_button_wt_history = findViewById(R.id.rd_button_wt_history);
        ll_history_wt = findViewById(R.id.ll_history_wt);
        ll_current_wt = findViewById(R.id.ll_current_wt);
        ll_add_wt_history = findViewById(R.id.ll_add_wt_history);
        vp_wt = findViewById(R.id.vp_wt);
        txt_wt_date_from = findViewById(R.id.txt_wt_date_from);
        txt_wt_date_to = findViewById(R.id.txt_wt_date_to);
        list_wt_history = findViewById(R.id.list_wt_history);
        labelCalculateWeight = findViewById(R.id.labelCalculateWeight);
        btn_show_wt_history = findViewById(R.id.btn_show_wt_history);
        vp_wt.setDisplayedChild(0);
        commonService = Client.getClient().create(CommonService.class);

        showDatePickerDailog();



        //mainhistriy

        curentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        labelCalculateWeight.setText("Calulate Today's Weight");
        if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)){
            labelCalculateWeight.setText("Calulate Today's Weight");
        }

        //



        ll_add_wt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="getHistory";

                datepickerdialog.show();

            }
        });

        submitFromDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        dummydate_from = formatDatesSleep(submitFromDate);
        txt_wt_date_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
        txt_wt_date_to.setText(dummydate_to);


        callMindHistoryApi(submitFromDate, submitToDate);



        btn_show_wt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMindHistoryApi(submitFromDate, submitToDate);

            }
        });
        rd_button_wt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_wt.setDisplayedChild(0);
            }
        });
        rd_button_wt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_wt.setDisplayedChild(1);
            }
        });

        txt_wt_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrDateOpen="from";

                datepickerdialog.show();
            }
        });
        txt_wt_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";

                datepickerdialog.show();
            }
        });
        editText_current_user_weight = findViewById(R.id.editText_current_user_weight);
        editText_current_user_weight.requestFocus();

        spinner = findViewById(R.id.spinner_Weight_Months);
        buttonSubmitWeight = findViewById(R.id.buttonSubmitWeight);
        buttonSubmitWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_current_user_weight.getText().toString().trim().isEmpty()){
                    hideSoftKeyboard();
                    CallWeightSubmitApi(editText_current_user_weight.getText().toString().trim());

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
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);

        /* SWIPE TO REFRESH */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (Utils.isNetworkAvailable(context))
                {
                    callMonthsApi(userID, true);
                }
                else
                    showLayouts();
            }
        });

        if (Utils.isNetworkAvailable(context))
        {
            callMonthsApi(userID, false);
        }
        else
            showLayouts();
    }

    public String formatDatesSleep(String dateFromServer)
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
    private void showDatePickerDailog() {
        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(WeightNewActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, WeightNewActivity.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog.getDatePicker().setMaxDate(maxDate);



        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
        datepickerdialog.getDatePicker().setMinDate(c1.getTimeInMillis());


        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }

    private void CallWeightSubmitApi(String trim) {


        UserWeightRequest request = new UserWeightRequest();
        request.setUserid(userID);
        request.setCreatedOn(submitHistoryDate);


        request.setCurrentWeight(String.valueOf(editText_current_user_weight.getText().toString().trim()));
        Call<MyWeight> call = healthParametersService.sendUserHealthDetail(request);
        call.enqueue(new Callback<MyWeight>() {
            @Override
            public void onResponse(Call<MyWeight> call, Response<MyWeight> response) {

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyWeight graphResponse = response.body();

                    if (graphResponse != null && graphResponse.getCode().equals("1"))
                    {
                        Toast.makeText(WeightNewActivity.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context))
                        {
                            editText_current_user_weight.setText("");
                            showLayouts();
                            callMonthsApi(userID, false);
                        }else {
                            showLayouts();
                        }


                    }
                    else
                    {
                        Toast.makeText(WeightNewActivity.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                    Toast.makeText(WeightNewActivity.this, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyWeight> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnRefresh)
        {
            if (Utils.isNetworkAvailable(context))
            {
                showLayouts();
                callMonthsApi(userID, false);
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

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

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
                        List<WeightMonthsResponse.MonthDataResponse> MonthsList = response.body().getMonthData();
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

                        // Get graph data which has yAxis list & xAxis list
                        List<GraphResponse.GraphEntityData> graphEntityDataArrayList = weightMonthsResponse.getGraphData();

                        Collections.reverse(graphEntityDataArrayList);
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
                                initChartData(yAxis, xAxis);
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

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                ShowRetryBar("" );
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
        GraphRequest request = new GraphRequest();
        request.setUserid(userID);
        request.setMonthName(monthName);

        Call<GraphResponse> call = weightService.getGraphList(request);
        call.enqueue(new Callback<GraphResponse>()
        {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GraphResponse graphResponse = response.body();

                    if (graphResponse != null && graphResponse.getCode() == 1)
                    {
                        // Get graph data which has yAxis list & xAxis list
                        List<GraphResponse.GraphEntityData> graphEntityList = graphResponse.getData();
                        Log.d("sunit",graphEntityList.toString());
                        Collections.reverse(graphEntityList);

                        final ArrayList<Entry> yAxisValues = new ArrayList<>();
                        final ArrayList<String> xAxisValues = new ArrayList<>();

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
                        Toast.makeText(WeightNewActivity.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                    Toast.makeText(WeightNewActivity.this, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t)
            {

            }
        });
    }

    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
            swipeRefreshLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    private void initChartData(final ArrayList<Entry> yVals, final ArrayList<String> xVals)
    {
        mChart.clear();
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        Log.d("sunit",xVals.toString());


        Spannable wordtoSpan = new SpannableString("Monthly weights");

       wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//

 String lable=wordtoSpan.toString();
        LineDataSet set1 = new LineDataSet(yVals, "Monthly weights");

        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        mChart.setData(data);
        mChart.setNoDataText("Monthly weights");

//        mChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPremiumBlack));

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setDrawLabels(false); // no axis labels
        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
         xAxis.setTextSize(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis at bottom
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {
                for (int i = 0; i < yVals.size(); ++i)
                {
                    if (yVals.get(i).getX() == value)
                    {
                        return xVals.get(i);

                    }
                }
                return "";
            }
        });
        if (yVals.size() > 4)
        {
            xAxis.setGranularity(1);
            xAxis.setAxisLineWidth(2); // axis line colour
            xAxis.setLabelCount(4, false);
        }

        YAxis left = mChart.getAxisLeft();
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

//        YAxis right = mChart.getAxisRight();
//        right.setEnabled(false); // no right axis

        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
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
                            callMonthsApi(userID, false);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (StrDateOpen.equalsIgnoreCase("from")) {
            dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;







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
                Toast.makeText(context, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
















            txt_wt_date_from.setText(dummydate_from);

            submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;


        }



        if (StrDateOpen.equalsIgnoreCase("getHistory")) {

            if (submitHistoryDate.equalsIgnoreCase(year + "-" + (month + 1) + "-" + dayOfMonth)){
                return;
            }


            int  monthnew=(month+1);

            if (monthnew<10){

                dummyhistoryshowdate = dayOfMonth + "-" + "0"+(month + 1) + "-" + year;

            }else {
                dummyhistoryshowdate = dayOfMonth + "-" +(month + 1) + "-" + year;

            }
            submitHistoryDate=year + "-" + (month + 1) + "-" + dayOfMonth;
            if (!curentDate.equalsIgnoreCase(dummyhistoryshowdate)){
                SimpleDateFormat  simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                Date varDate= null;
                try {
                    varDate = simpleDateFormat.parse(dummyhistoryshowdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                simpleDateFormat=new SimpleDateFormat("dd-MMM");

                labelCalculateWeight.setText("Calculate "+simpleDateFormat.format(varDate)+"'s Weight");
            }else {
                labelCalculateWeight.setText("Calulate Today's Weight");

            }

            if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)){
                labelCalculateWeight.setText("Calulate Today's Weight");

            }




//            txt_date_history.setText(dummyhistoryshowdate);
//                submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


//            CallToFetchRecoachId(false);




        }




        if (StrDateOpen.equalsIgnoreCase("to")) {


            if (!dummydate_from.trim().isEmpty()){







                dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;




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
                    Toast.makeText(context, "To date should be greater than From date", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    txt_wt_date_to.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;

                    callMindHistoryApi(submitFromDate,submitToDate);

                }






            }else {
                Toast.makeText(context, "Please select from date", Toast.LENGTH_SHORT).show();
            }



        }

    }
    private void callMindHistoryApi(String submitFromDate, String submitToDate) {


        ClsWeightHistoryRequest clsHistoryRequest=new ClsWeightHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)));

        utils.showProgressbar(context);
        Call<ClsWtHistoryResponse> call = commonService.getHealthHistoryData(clsHistoryRequest);
        call.enqueue(new Callback<ClsWtHistoryResponse>()
        {
            @Override
            public void onResponse(Call<ClsWtHistoryResponse> call, Response<ClsWtHistoryResponse> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    Toast.makeText(WeightNewActivity.this, "success", Toast.LENGTH_SHORT).show();
                    try {
                        ClsWtHistoryResponse moodResponse = response.body();
                        if (moodResponse.getCode()!=null){

                            if (moodResponse.getCode().equals("1")){

                                ArrayList<Data> dataArrayList=moodResponse.getData();

                                if (!dataArrayList.isEmpty()){

//                                    list_wt_history.setAdapter(new ClsWeightHistoryAdapter(WeightNewActivity.this,dataArrayList));

                                }else {
                                    ArrayList<Data> dataempty=new ArrayList<>();
                                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
//                                    list_wt_history.setAdapter(new ClsWeightHistoryAdapter(WeightNewActivity.this,dataempty));

                                }





                            }else {


                            }



                        }else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsWtHistoryResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });








    }

    @Override
    public void getEditWtPosition(final Data data) {


        apiWeight=data.getWeight();
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.activity_weight_dialog);
        final EditText edt_new_wt=dialog.findViewById(R.id.edt_new_wt);
         EditText edt_schedule_wt=dialog.findViewById(R.id.edt_schedule_wt);
        ImageView img_minus_water=dialog.findViewById(R.id.img_minus_water);
        ImageView img_plus_water=dialog.findViewById(R.id.img_plus_water);
        ImageView close_slepp_histoy_dialog=dialog.findViewById(R.id.close_slepp_histoy_dialog);
        close_slepp_histoy_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_update_water_hitostory=dialog.findViewById(R.id.btn_update_water_hitostory);
        btn_update_water_hitostory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClWtUpdateRequest clsEditWaterRequest=new ClWtUpdateRequest();
                clsEditWaterRequest.setWeight(Integer.parseInt(edt_new_wt.getText().toString()));
                clsEditWaterRequest.setHealthParamID(data.getHealthParamID());
                clsEditWaterRequest.setCreatedOn(formatDatesSunmit(data.getCreatedOn()));
                callWeightUpdateApi(clsEditWaterRequest);
                dialog.dismiss();




            }
        });


        dialog.show();













    }


    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    public String formatDatesSunmit(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    private void callWeightUpdateApi(ClWtUpdateRequest clsEditSleepRequest){




        utils.showProgressbar(context);
        Call<ClsEditSleepResonse> call = commonService.setUpdateWtHistoy(clsEditSleepRequest);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, retrofit2.Response<ClsEditSleepResonse> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsEditSleepResonse moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){


                                callMindHistoryApi(submitFromDate, submitToDate);






                            }else {

                                Toast.makeText(context, ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

}
