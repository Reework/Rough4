package com.shamrock.reework.activity.parameter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.parameter.adapters.TestNameAdapters;
import com.shamrock.reework.activity.parameter.pojo.BCAQ;
import com.shamrock.reework.activity.parameter.pojo.ClsBcaPathoGraphMain;
import com.shamrock.reework.activity.parameter.pojo.ClsGraphDataMainClass;
import com.shamrock.reework.activity.parameter.pojo.GraphData;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParameterTestGraphActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private HealthParametersService service;
    private SessionManager sessionManager;
    private Utils utils;
    private int userID;
    private RadioButton rd_bca_graph,rd_path_graph;
    private LinearLayout ll_select_test;
    private ListView lst_testname;
    private ArrayList<BCAQ> arylst_bcaq;

    private ArrayList<GraphData> arylst_graph_data;
    Context context;
    Toolbar toolbar;
    NotificationService notificationService;
    TextView testname;

    RelativeLayout noInternetLayout;
    LinearLayout mainLayout;
    Button btnRefresh;
    LineChart linegraph;
    TextView labelMyProgress;

    ArrayList<GetAllNotificationResponse.Notifications> mNotificationList;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    LoginService loginService;
    int userId;
    Spinner spinner;
    private boolean isFirrstime;
    ArrayList<WaterData> tempList;

    TextView testparameter;

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
    boolean ISFromANnalysis;
    private int reporttypeID=1;
    private int testIDSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_test_graph);
        context=ParameterTestGraphActivity.this;
        utils = new Utils();
        linegraph=findViewById(R.id.linegraph);
        showDateWiseData();
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Graph");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rd_path_graph=findViewById(R.id.rd_path_graph);
        testparameter=findViewById(R.id.testparameter);
        testname=findViewById(R.id.testname);
        rd_bca_graph=findViewById(R.id.rd_bca_graph);
        lst_testname=findViewById(R.id.lst_testname);
        ll_select_test=findViewById(R.id.ll_select_test);
        service = Client.getClient().create(HealthParametersService.class);
        sessionManager = new SessionManager(ParameterTestGraphActivity.this);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        getParameterDataQn(1);
        rd_bca_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reporttypeID=2;
                getParameterDataQn(2);


            }
        });
        rd_path_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reporttypeID=1;
                getParameterDataQn(1);
            }
        });
        ll_select_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arylst_bcaq!=null)
                showDialog();
            }
        });

        LinearLayout ll_sleep_date=findViewById(R.id.ll_sleep_date);
        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDataDialog();
            }
        });

    }

    private void showDialog() {
        final Dialog dialog=new Dialog(this,R.style.CustomDialog);
        dialog.setContentView(R.layout.dailog_select_parameter);
        lst_testname=dialog.findViewById(R.id.lst_testname);
        lst_testname.setAdapter(new TestNameAdapters(this,arylst_bcaq));
        lst_testname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getGraphDatalist(arylst_bcaq.get(position).getTestId());
                testIDSubmit=arylst_bcaq.get(position).getTestId();
                testname.setText("Score("+arylst_bcaq.get(position).getTestName()+")");
                testparameter.setText(arylst_bcaq.get(position).getTestName());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getParameterDataQn(int type) {
        utils.showProgressbar(this);

        Call<ClsBcaPathoGraphMain> call = service.BCAPathoTestQuestion(userID,reporttypeID);
        call.enqueue(new Callback<ClsBcaPathoGraphMain>()
        {
            @Override
            public void onResponse(Call<ClsBcaPathoGraphMain> call, Response<ClsBcaPathoGraphMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsBcaPathoGraphMain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (arylst_bcaq!=null){
                            arylst_bcaq.clear();
                        }
                        if (reporttypeID==1){
                            if (listResponse.getData().getPathoQ()!=null){
                                arylst_bcaq=listResponse.getData().getPathoQ();
                                testIDSubmit=arylst_bcaq.get(0).getTestId();
                                testname.setText("Score("+arylst_bcaq.get(0).getTestName()+")");

                            }
                        }


                        if (reporttypeID==2){
                            if (listResponse.getData().getBCAQ()!=null){
                                arylst_bcaq=listResponse.getData().getBCAQ();
                                testIDSubmit=arylst_bcaq.get(0).getTestId();
                                testname.setText("Score("+arylst_bcaq.get(0).getTestName()+")");

                            }
                        }

                        testparameter.setText(arylst_bcaq.get(0).getTestName());

                        getGraphDatalist(testIDSubmit);






                    } else {
                        //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsBcaPathoGraphMain> call, Throwable t)
            {
                Toast.makeText(ParameterTestGraphActivity.this, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                utils.hideProgressbar();
            }
        });
    }
    private void getGraphDatalist(int testID) {
        utils.showProgressbar(this);
        Call<ClsGraphDataMainClass> call = service.getGraphData(userID,reporttypeID,submitFromDate,submitToDate,testID);
        call.enqueue(new Callback<ClsGraphDataMainClass>()
        {
            @Override
            public void onResponse(Call<ClsGraphDataMainClass> call, Response<ClsGraphDataMainClass> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsGraphDataMainClass listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {

                        linegraph.clear();
                        linegraph.setData(null);
                        linegraph.highlightValue(null);
                        linegraph.invalidate();
                        arylst_graph_data=listResponse.getData();
                        if (arylst_graph_data!=null){
                            if (!arylst_graph_data.isEmpty()){

                                setGraphData(arylst_graph_data);
                            }else {
                                Toast.makeText(ParameterTestGraphActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(ParameterTestGraphActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsGraphDataMainClass> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }


    private void setGraphData(final ArrayList<GraphData> aryCalories) {

        final ArrayList<Entry> yValues1=new ArrayList<>();
        ArrayList<Entry> yValues2=new ArrayList<>();



        for (int i = 0; i <aryCalories.size() ; i++) {
            yValues1.add(new Entry(i, Float.parseFloat(String.valueOf(aryCalories.get(i).getTestvalue()))));

        }


        linegraph.setVisibility(View.VISIBLE);

        LineDataSet set1,set2,set3,set4;
        set1=new LineDataSet(yValues1,"Score");
        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(this, R.color.transparent1));
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        set1.setCircleColor(getResources().getColor(R.color.colorGreen1));


        set1.setLineWidth(2f);


//
        final LineData data=new LineData(set1);
        linegraph.setData(data);



        Description description=new Description();
        linegraph.getDescription().setText("Score");


        XAxis xAxis = linegraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        if (aryCalories.size()==1){
            xAxis.setLabelCount(aryCalories.size());

        }else if (aryCalories.size()>10){
            xAxis.setLabelCount(10);

        }

        else {
            xAxis.setLabelCount(aryCalories.size(),true);

        }
        // xAxis.setDrawGridLines(false);
        //xAxis.setAvoidFirstLastClipping(true);

        xAxis.setTextColor(Color.parseColor("#FFFFFF")); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setTextSize(2f);

        //YAxis right = lineChart.getAxisRight();
        // right.setEnabled(false); // no right axis

        //xAxis.setLabelCount(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxi
        linegraph.getXAxis().setTextColor(R.color.white);


        YAxis left = linegraph.getAxisLeft();
        left.setTextColor(Color.WHITE); // axis line colour
        left.setDrawAxisLine(true); // no axis line
        left.setAxisLineColor(Color.WHITE); // axis line colour
        left.setDrawGridLines(true); // no grid lines
        left.setGridColor(ContextCompat.getColor(context, R.color.colorGreyLight2)); // no grid lines
        left.setGridColor(Color.WHITE); // grid li


        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {

                if (aryCalories!=null){
                    for (int i = 0; i < aryCalories.size(); ++i)
                    {
                        if (yValues1.get(i).getX() == value)
                        {
                            return WaterDatesNew(aryCalories.get(i).getReportDate());
                        }
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


    public String WaterDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
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
    private void selectDataDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog=dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog=dialog.findViewById(R.id.txt_sleep_date_to_dialog);
        TextView txt_dialog_header=dialog.findViewById(R.id.txt_dialog_header);
        txt_dialog_header.setText("Set Date");


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);
        btn_submit_sleep_hours.setText("Ok");

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    dialog.dismiss();
                    getGraphDatalist(testIDSubmit);


                } else {

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


    }
    private void showDateWiseData() {
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
                    selectDataDialog();
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

try {
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

}catch (Exception e){
    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

}


    }
}
//http://shamrockuat.dweb.in/api/Report/GetGraphData?UserId=3040&ReportTypeId=2&From=2021-03-03&To=2021-06-10&TestId=20063
//        -------------------------Response ---------------------
//        {
//        "Code": 1,
//        "Message": "Success",
//        "Data": [
//        {
//        "ReportDate": "2021-05-04T00:00:00",
//        "Blood Pressure-Systolic (Top Number)": 0.6
//        },
//        {
//        "ReportDate": "2021-05-15T00:00:00",
//        "Blood Pressure-Systolic (Top Number)": 0.8
//        }
//        ]
//        }