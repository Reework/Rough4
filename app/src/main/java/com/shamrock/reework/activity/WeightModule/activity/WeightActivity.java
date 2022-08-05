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
import com.google.android.material.textfield.TextInputLayout;
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
import android.view.Window;
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
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightPojo;
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightRequest;
import com.shamrock.reework.activity.WeightModule.adapters.WeightHistory;
import com.shamrock.reework.activity.WeightModule.service.WeightService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GraphRequest;
import com.shamrock.reework.api.request.WeightMonthsRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.GraphResponse;
import com.shamrock.reework.api.response.WeightMonthsResponse;
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


public class WeightActivity extends AppCompatActivity implements OnUpdateWeight, DatePickerDialog.OnDateSetListener,AdapterView.OnItemSelectedListener, View.OnClickListener
{
                                                                //implements OnChartGestureListener, OnChartValueSelectedListener {

    Context context;
    Toolbar toolbar;
    Typeface font;
    LineChart mChart;
    TextInputLayout txt_input_initialwt,txt_input_idealwt;
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
    private RadioButton rd_button_weight_history,rd_button_weight;
    private TextView btn_show_weight_history,txt_weight_date_to,txt_weight_date_from;
    private ListView list_weight_history;
    private ViewFlipper vp_weight;

    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;

    Button buttonSubmitWeight;
    EditText editText_current_user_weight;
    private DatePickerDialog datepickerdialog;
    private String StrDateOpen="";
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView  txt_no_weight;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        context = WeightActivity.this;
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
        submitFromDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


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
    private void showDatePickerDailog() {
        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(WeightActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, WeightActivity.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();


        if (strMindate!=null){
            if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
                if (strMindate.length>1){
                    c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

                }

            }

        }
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


    private void findViews()
    {
        editTextCurrent = findViewById(R.id.editText_WeightCurrent);
        editTextIdeal = findViewById(R.id.editText_WeightIdeal);
        editText_current_user_weight = findViewById(R.id.editText_current_user_weight);
        rd_button_weight=findViewById(R.id.rd_button_weight);
        rd_button_weight_history=findViewById(R.id.rd_button_weight_history);
        list_weight_history=findViewById(R.id.list_weight_history);
        ll_weight_header=findViewById(R.id.ll_weight_header);
        txt_no_weight=findViewById(R.id.txt_no_weight);
        txt_input_idealwt=findViewById(R.id.txt_input_idealwt);
        txt_input_initialwt=findViewById(R.id.txt_input_initialwt);

        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
            txt_input_initialwt.setVisibility(View.GONE);
            txt_input_idealwt.setVisibility(View.GONE);
        }


        txt_weight_date_from=findViewById(R.id.txt_weight_date_from);
        txt_weight_date_to=findViewById(R.id.txt_weight_date_to);
        btn_show_weight_history=findViewById(R.id.btn_show_weight_history);
        btn_show_weight_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWeightHistoryApi(submitFromDate,submitToDate);

            }
        });
        vp_weight=findViewById(R.id.vp_weight);
        editText_current_user_weight.requestFocus();
        rd_button_weight.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            vp_weight.setDisplayedChild(0);

            callMonthsApi(userID, false);

        }
    });

        rd_button_weight_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_weight.setDisplayedChild(1);
                callWeightHistoryApi(submitFromDate,submitToDate);

            }
        });
        showDatePickerDailog();
        vp_weight.setDisplayedChild(0);

        txt_weight_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="from";
                datepickerdialog.show();
            }
        });

        txt_weight_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";
                datepickerdialog.show();

            }
        });



        dummydate_from=formatDatesSleep(submitFromDate);
        txt_weight_date_from.setText(dummydate_from);
        dummydate_to=formatDatesSleep(submitToDate);
        txt_weight_date_to.setText(dummydate_to);
        showDatePickerDailog();
        callWeightHistoryApi(submitFromDate,submitToDate);




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

    public String formatDatesSleep(String dateFromServer) {
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

    private void CallWeightSubmitApi(String trim) {
        String  todaysubmitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
       UserWeightRequest request = new UserWeightRequest();
        request.setUserid(userID);
        request.setCreatedOn(todaysubmitHistoryDate);
        utils.showProgressbar(context);
        request.setCurrentWeight(String.valueOf(editText_current_user_weight.getText().toString().trim()));
        Call<MyWeight> call = healthParametersService.sendUserHealthDetail(request);
        call.enqueue(new Callback<MyWeight>() {
            @Override
            public void onResponse(Call<MyWeight> call, Response<MyWeight> response) {

                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyWeight graphResponse = response.body();

                    if (graphResponse != null && graphResponse.getCode().equals("1"))
                    {
                        Toast.makeText(WeightActivity.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(WeightActivity.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                    Toast.makeText(WeightActivity.this, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyWeight> call, Throwable t) {

                utils.hideProgressbar();
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

//                        Collections.reverse(graphEntityDataArrayList);
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
//                                initChartData(yAxis, xAxis);

                                callGraphApi(userID,WeightMonthsList.get(0).toString());
                            }
                        }
                    }
                    else
                    {
                        if (weightMonthsResponse.getMessage().equalsIgnoreCase("Month Date list not available")){
//                            Toast.makeText(context, "" + weightMonthsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "" + weightMonthsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
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
//                        Collections.reverse(graphEntityList);

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
                        Toast.makeText(WeightActivity.this, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                    Toast.makeText(WeightActivity.this, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
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


        Spannable wordtoSpan = new SpannableString("Monthly weights(kg)");

       wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//

        String lable=wordtoSpan.toString();
        LineDataSet set1 = new LineDataSet(yVals, "Monthly weights(kg)");

        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        mChart.setData(data);
        mChart.setNoDataText("Monthly weights(kg)");

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

        YAxis right = mChart.getAxisRight();
        right.setEnabled(false); // no right axis

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

            if (dayOfMonth<10){
                dummydate_from = "0"+dayOfMonth + "-" + (month + 1) + "-" + year;

            }else {
                dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;

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
                Toast.makeText(context, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_weight_date_from.setText(dummydate_from);
            submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;
        }
        if (StrDateOpen.equalsIgnoreCase("to")) {
            if (!dummydate_from.trim().isEmpty()){
                if (dayOfMonth<10){
                    dummydate_to = "0"+dayOfMonth + "-" + (month + 1) + "-" + year;

                }else {
                    dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;

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
                    Toast.makeText(context, "To date should be greater than From date", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    txt_weight_date_to.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;

                    callWeightHistoryApi(submitFromDate,submitToDate);

                }


            }else {
                Toast.makeText(context, "Please select from date", Toast.LENGTH_SHORT).show();
            }



        }
    }


    private void callWeightHistoryApi(String submitFromDate, String submitToDate)    {

        ClsWeightRequest clsHistoryRequest=new ClsWeightRequest();
        clsHistoryRequest.setReeworkerId(userID);
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        utils.showProgressbar(WeightActivity.this);
        Call<ClsWeightPojo> call = healthParametersService.GetReeworkerWeight(clsHistoryRequest);
        call.enqueue(new Callback<ClsWeightPojo>()
        {
            @Override
            public void onResponse(Call<ClsWeightPojo> call, Response<ClsWeightPojo> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsWeightPojo listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        if (!listResponse.getData().isEmpty()){
                            txt_no_weight.setVisibility(View.GONE);
                            list_weight_history.setVisibility(View.VISIBLE);
                            ll_weight_header.setVisibility(View.VISIBLE);
                            ArrayList<WeightHistory> weightHistories=listResponse.getData();

//                            list_weight_history.setAdapter(new ClsNewWeightHistoryAdapter(WeightActivity.this,weightHistories, WeightFragment.this));

                        }else {
                            ll_weight_header.setVisibility(View.INVISIBLE);
                            txt_no_weight.setVisibility(View.VISIBLE);
                            list_weight_history.setVisibility(View.GONE);
                            txt_no_weight.setText("No records available");

                            if(vp_weight.getDisplayedChild()==1){
                                Toast.makeText(WeightActivity.this, "No records available", Toast.LENGTH_SHORT).show();

                            }

                        }








                    }
                    else
                    {
                        Toast.makeText(WeightActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(WeightActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<ClsWeightPojo> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callWeightHistorupdateyApi(WeightHistory weightHistory)    {


        utils.showProgressbar(WeightActivity.this);
        Call<AuthenticationResponse> call = healthParametersService.updateReeworkerWeight(weightHistory);
        call.enqueue(new Callback<AuthenticationResponse>()
        {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AuthenticationResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {


                        callWeightHistoryApi(submitFromDate,submitToDate);


                        Toast.makeText(WeightActivity.this, "" + listResponse.getData(), Toast.LENGTH_SHORT).show();







                    }
                    else
                    {
                        Toast.makeText(WeightActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(WeightActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

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
    public void updateweight(final WeightHistory weightHistory) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_update_sleep);
        final EditText edt_update_weight=dialog.findViewById(R.id.edt_update_weight);
        ImageView img_close=dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edt_update_weight.setText(weightHistory.getWeight());

        Button btn_submit_weight=dialog.findViewById(R.id.btn_submit_weight);
        btn_submit_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                int index = weightHistory.getHealthDate().indexOf("T");

                weightHistory.setHealthDate(weightHistory.getHealthDate().substring(0,index));



                weightHistory.setWeight(edt_update_weight.getText().toString());
                callWeightHistorupdateyApi(weightHistory);
            }
        });
        dialog.show();




    }
}
