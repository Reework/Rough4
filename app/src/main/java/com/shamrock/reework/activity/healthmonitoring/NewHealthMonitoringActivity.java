package com.shamrock.reework.activity.healthmonitoring;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
import android.widget.TextView;
import android.widget.TimePicker;
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
import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterAddSuccessData;
import com.shamrock.reework.activity.BloodTestModule.activity.AllReportActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.ReportCompareActivity;
import com.shamrock.reework.activity.BloodTestModule.adapter.PlannerAdapter;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.WeightModule.activity.WeightActivitylatest;
import com.shamrock.reework.activity.parameter.ParameterTestGraphActivity;
import com.shamrock.reework.activity.parameter.adapters.TestNameAdapters;
import com.shamrock.reework.activity.parameter.pojo.BCAQ;
import com.shamrock.reework.activity.parameter.pojo.ClsBcaPathoGraphMain;
import com.shamrock.reework.activity.parameter.pojo.ClsGraphDataMainClass;
import com.shamrock.reework.activity.parameter.pojo.GraphData;
import com.shamrock.reework.activity.reemonitor.OnparameterClick;
import com.shamrock.reework.activity.reemonitor.ParameterlistAdapter;
import com.shamrock.reework.activity.reemonitor.ReemonitorCompareActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHealthMonitoringActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, OnparameterClick, HealthmonitorHistoryListAdapter.OnEditTestLinstenr, TimePickerDialog.OnTimeSetListener {
    TextView txt_select_range, txt_select_measurement, txt_select_paramters;
    private Utils util;
    private HomeFragmentService services;
    //    private ArrayList<LabTestList> arylst_LabTestLists;
    private ArrayList<LabTestList> mFilterlist;

    ArrayList<String> arylst_select_parameters = new ArrayList<>();
    ArrayList<String> arylst_select_parametersID = new ArrayList<>();
    ArrayList<String> arlst_lab_testID = new ArrayList<>();
    ArrayList<String> arlst_unit = new ArrayList<>();
    ArrayList<String> arlst_unitID = new ArrayList<>();
    ArrayList<String> arylst_select_parametersUnit_Name = new ArrayList<>();
    ArrayList<String> arylst_select_parametersUnit_ID = new ArrayList<>();
    String str_range;
    Dialog dialog_parameter;
    String str_rangeID;
    TextView txt_select_unit;
    private String strUnitID;
    LinearLayout ll_range;
    private String Str_ParameterID;
    private EditText edt_remark;
    EditText txt_lab_test_value;
    private Button btn_submit;
    private SessionManager sessionManager;
    RadioButton rd_btn_reemonitor;
    private int userID;
    TextView btn_show_water_history;
    TextView txt_water_date_to;
    TextView txt_water_date_from;
    RecyclerView list_reemonitor_history;
    TextView txt_sleep_date_to_dialog;
    TextView txt_sleep_date_from_dialog;
    TextView txt_show_sleep_from;
    TextView txt_show_sleep_to;
    TextView txt_no_water_history;
    private String StrDateOpen = "", StrDateOpen1 = "", StrDateOpen2 = "";
    private String dummydate_from;
    private String dummydate_to;
    private String submitToDate;
    private String submitFromDate, submitFromHistoryDate, submitToHistoryDate;
    //    LinearLayout ll_choosedate;
    private DatePickerDialog datepickerdialog;
    //    private ViewFlipper vp_reemonitor;
//    RadioButton rd_button_history, rd_button_cureent,rd_button_compare;
    TextView txt_current, txt_history, txt_comparison, txt_graph;
    LinearLayout layout_current, layout_history, layout_comparison, layout_graph;
    LinearLayout lay_current, lay_history, lay_comparison, lay_graph;

    TextView txt_weight_date_from, txt_weight_date_to;
    RadioButton rd_btn_weight;
    TextView txt_select_paramters_date;
    private DatePickerDialog datepickerdialog_entry;
    TextView txt_select_paramters_time;

    String currentDateandTime;
    private TimePickerDialog timepickerdialog;
    private String lsTime = "";
    Button btn_history_submit;

    TextView txt_comp_from, txt_comp_to;
    Button btn_comp_submit;
    TextView txt_bca, txt_patho;

    int testType = 2;
    private ReeTestService reeTestService;
    RecyclerView recycler_report_compare;
    TextView txt_nofoundhist, txt_nofoundcom;
    PlannerAdapter clsReportCompareAdapter;
    ArrayList<String> arylst_result = new ArrayList<>();

    TextView txt_graph_bca, txt_graph_patho, txt_graph_from, txt_graph_to;
    Button btn_graph;
    LineChart linegraph;
    private int reporttypeID = 2;
    private ArrayList<BCAQ> arylst_bcaq;

    private ArrayList<GraphData> arylst_graph_data;
    private int testIDSubmit;
    TextView testname, testparameter;
    private HealthParametersService service;
    private ListView lst_testname;

    String com_from_date, com_to_date, history_from_date, history_to_date;
    TextView txt_Allhistory, txt_allcomp;

    private Context context;
    Toolbar toolbar;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    ArrayList<ClsCustomTestList> arylst_ClsCustomTestLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_health_monitoring);
        context = NewHealthMonitoringActivity.this;
        txt_select_paramters_time = findViewById(R.id.txt_select_paramters_time);
        txt_select_paramters_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                timepickerdialog = new TimePickerDialog(NewHealthMonitoringActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, NewHealthMonitoringActivity.this, hour, minute, false);
                timepickerdialog.show();
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());

        SimpleDateFormat sdfss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        final String currentDateandTimess = sdfss.format(new Date());
        rd_btn_weight = findViewById(R.id.rd_btn_weight);
        rd_btn_weight = findViewById(R.id.rd_btn_weight);
//        rd_button_graph=findViewById(R.id.rd_button_graph);

        txt_select_paramters_date = findViewById(R.id.txt_select_paramters_date);
        txt_select_paramters_date.setText(currentDateandTimess);
        txt_select_paramters_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "";
                StrDateOpen1 = "";
                showDatePickerDailogentry();
                datepickerdialog_entry.show();

            }
        });
        rd_btn_reemonitor = findViewById(R.id.rd_btn_reemonitor);
      /*  rd_btn_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewHealthMonitoringActivity.this, WeightActivitylatest.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });*/
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEmonitor");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        ll_choosedate = findViewById(R.id.ll_choosedate);
//        vp_reemonitor = findViewById(R.id.vp_reemonitor);
//        vp_reemonitor.setDisplayedChild(0);
//        rd_button_compare = findViewById(R.id.rd_button_compare);
//        rd_button_cureent = findViewById(R.id.rd_button_cureent);
//        rd_button_history = findViewById(R.id.rd_button_history);

        service = Client.getClient().create(HealthParametersService.class);
        submitFromHistoryDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToHistoryDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        com_from_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        com_to_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        dummydate_from = formatDatesSleep(submitFromDate);

        txt_current = findViewById(R.id.txt_current);
        txt_history = findViewById(R.id.txt_history);
        txt_comparison = findViewById(R.id.txt_comparison);
        txt_graph = findViewById(R.id.txt_graph);

        layout_current = findViewById(R.id.layout_current);
        layout_history = findViewById(R.id.layout_history);
        layout_comparison = findViewById(R.id.layout_comparison);
        layout_graph = findViewById(R.id.layout_graph);

        lay_current = findViewById(R.id.lay_current);
        lay_history = findViewById(R.id.lay_history);
        lay_comparison = findViewById(R.id.lay_comparison);
        lay_graph = findViewById(R.id.lay_graph);

        txt_show_sleep_from = findViewById(R.id.txt_show_sleep_from);
        txt_show_sleep_to = findViewById(R.id.txt_show_sleep_to);

        btn_history_submit = findViewById(R.id.btn_history_submit);
        txt_comp_from = findViewById(R.id.txt_comp_from);
        txt_comp_to = findViewById(R.id.txt_comp_to);
        btn_comp_submit = findViewById(R.id.btn_comp_submit);

        reeTestService = Client.getClient().create(ReeTestService.class);
        recycler_report_compare = findViewById(R.id.recycler_report_compare);

        txt_nofoundhist = findViewById(R.id.txt_nofoundhist);
        txt_nofoundcom = findViewById(R.id.txt_nofoundcom);


        txt_bca = findViewById(R.id.txt_bca);
        txt_patho = findViewById(R.id.txt_patho);

        txt_Allhistory = findViewById(R.id.txt_Allhistory);
        txt_allcomp = findViewById(R.id.txt_allcomp);


        txt_graph_bca = findViewById(R.id.txt_graph_bca);
        txt_graph_patho = findViewById(R.id.txt_graph_patho);
        txt_graph_from = findViewById(R.id.txt_graph_from);
        txt_graph_to = findViewById(R.id.txt_graph_to);
        btn_graph = findViewById(R.id.btn_graph);


        linegraph = findViewById(R.id.linegraph);
        testname = findViewById(R.id.testname);
        testparameter = findViewById(R.id.testparameter);
        lst_testname = findViewById(R.id.lst_testname);

//        linegraph.getLegend().setEnabled(true);
//        linegraph.getLegend().setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        txt_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vp_reemonitor.setDisplayedChild(0);
                layout_current.setVisibility(View.VISIBLE);
                layout_history.setVisibility(View.GONE);
                layout_comparison.setVisibility(View.GONE);
                layout_graph.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                lay_current.setBackground(getResources().getDrawable(R.drawable.bg_green_button));
                lay_history.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_comparison.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_graph.setBackground(getResources().getDrawable(R.drawable.bg_white_button));

            }
        });
        txt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vp_reemonitor.setDisplayedChild(1);
                layout_current.setVisibility(View.GONE);
                layout_history.setVisibility(View.VISIBLE);
                layout_comparison.setVisibility(View.GONE);
                layout_graph.setVisibility(View.GONE);
                btn_submit.setVisibility(View.GONE);
                submitFromHistoryDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                submitToHistoryDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//                txt_show_sleep_from.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
//                txt_show_sleep_to.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));


                txt_show_sleep_from.setText("Start Date");
                txt_show_sleep_to.setText("End Date");


                getHistoryParameterData();
                lay_current.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_history.setBackground(getResources().getDrawable(R.drawable.bg_green_button));
                lay_comparison.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_graph.setBackground(getResources().getDrawable(R.drawable.bg_white_button));

            }
        });

        txt_comparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NewHealthMonitoringActivity.this, ReemonitorCompareActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                overridePendingTransition(0,0);
                btn_submit.setVisibility(View.GONE);
                com_from_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                com_to_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

//                txt_comp_from.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
//        txt_sleep_date_from_dialog.setText(dummydate_from);
//                txt_comp_to.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

                txt_comp_from.setText("Start Date");
//        txt_sleep_date_from_dialog.setText(dummydate_from);
                txt_comp_to.setText("End Date");

                testType = 2;
                CallOtherTestReports(com_from_date, com_to_date);
                layout_current.setVisibility(View.GONE);
                layout_history.setVisibility(View.GONE);
                layout_comparison.setVisibility(View.VISIBLE);
                layout_graph.setVisibility(View.GONE);
                lay_current.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_history.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_comparison.setBackground(getResources().getDrawable(R.drawable.bg_green_button));
                lay_graph.setBackground(getResources().getDrawable(R.drawable.bg_white_button));

            }
        });

        txt_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rd_button_cureent.setChecked(true);
                btn_submit.setVisibility(View.GONE);
                lay_current.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_history.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_comparison.setBackground(getResources().getDrawable(R.drawable.bg_white_button));
                lay_graph.setBackground(getResources().getDrawable(R.drawable.bg_green_button));

                layout_current.setVisibility(View.GONE);
                layout_history.setVisibility(View.GONE);
                layout_comparison.setVisibility(View.GONE);
                layout_graph.setVisibility(View.VISIBLE);

                txt_graph_from.setText("Start Date");
//        txt_sleep_date_from_dialog.setText(dummydate_from);
                txt_graph_to.setText("End Date");

                getParameterDataQn(2);

//                startActivity(new Intent(NewHealthMonitoringActivity.this, ParameterTestGraphActivity.class));
            }
        });

//        ll_choosedate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showNewgetSleephistroy();
//            }
//        });


        btn_history_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_show_sleep_from.getText().toString().equals("Start Date") ||
                        txt_show_sleep_to.getText().toString().equals("End Date")) {
                    Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                } else {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(txt_show_sleep_from.getText().toString());
                        date2 = format.parse(txt_show_sleep_to.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date1.compareTo(date2) > 0) {

                        Toast.makeText(getApplicationContext(), "please select End Date greater than From Date", Toast.LENGTH_LONG).show();
//                        if (arylst_ClsCustomTestLists.size() == 0) {
                        arylst_ClsCustomTestLists.clear();
                        list_reemonitor_history.setVisibility(View.GONE);
                        txt_Allhistory.setVisibility(View.GONE);
                        txt_nofoundhist.setVisibility(View.VISIBLE);
                        list_reemonitor_history.setAdapter(new HealthmonitorHistoryListAdapter(NewHealthMonitoringActivity.this, arylst_ClsCustomTestLists));

//                        }

                    }else {
                        getHistoryParameterData();
                    }


                }


            }
        });


//        txt_show_sleep_from.setText(currentDateandTimess);
//        txt_sleep_date_from_dialog.setText(currentDateandTimess);
//        txt_show_sleep_to.setText(currentDateandTimess);
//        txt_sleep_date_to_dialog.setText(dummydate_to);

//        txt_comp_from.setText(currentDateandTimess);
//        txt_sleep_date_from_dialog.setText(dummydate_from);
//        txt_comp_to.setText(currentDateandTimess);

        txt_show_sleep_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrDateOpen = "from";
                StrDateOpen1 = "";
//                datepickerdialog.show();
                showDatePickerDailogentry();
                datepickerdialog_entry.show();
            }
        });


        txt_show_sleep_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrDateOpen = "to";
                StrDateOpen1 = "";
//                datepickerdialog.show();
                showDatePickerDailogentry();
                datepickerdialog_entry.show();
            }
        });
        txt_comp_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrDateOpen = "";
                StrDateOpen1 = "from";
//                datepickerdialog.show();
                showDatePickerDailogentry();
                datepickerdialog_entry.show();
            }
        });

        txt_comp_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrDateOpen = "";
                StrDateOpen1 = "to";
//                datepickerdialog.show();
                showDatePickerDailogentry();
                datepickerdialog_entry.show();
            }
        });


        btn_comp_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_comp_from.getText().toString().equals("Start Date") ||
                        txt_comp_to.getText().toString().equals("End Date")) {
                    Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                } else {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(txt_comp_from.getText().toString());
                        date2 = format.parse(txt_comp_to.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date1.compareTo(date2) > 0) {

                        Toast.makeText(getApplicationContext(), "please select End Date greater than From Date", Toast.LENGTH_LONG).show();
//
                        recycler_report_compare.setVisibility(View.GONE);
                        txt_allcomp.setVisibility(View.GONE);
                        txt_nofoundcom.setVisibility(View.VISIBLE);
                        arylst_result.clear();
                        clsReportCompareAdapter.notifyDataSetChanged();

                    }else {


                        CallOtherTestReports(com_from_date, com_to_date);

                    }
                }
            }
        });

        txt_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testType = 2;
                CallOtherTestReports(com_from_date, com_to_date);
                txt_bca.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_patho.setTextColor(getResources().getColor(R.color.ree_gray));
            }
        });

        txt_patho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testType = 1;
                CallOtherTestReports(com_from_date, com_to_date);
                txt_bca.setTextColor(getResources().getColor(R.color.ree_gray));
                txt_patho.setTextColor(getResources().getColor(R.color.green_recipe));

            }
        });


        txt_graph_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reporttypeID = 2;
                getParameterDataQn(2);
                txt_graph_bca.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_graph_patho.setTextColor(getResources().getColor(R.color.ree_gray));
            }
        });

        txt_graph_patho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reporttypeID = 1;
                getParameterDataQn(1);
                txt_graph_bca.setTextColor(getResources().getColor(R.color.ree_gray));
                txt_graph_patho.setTextColor(getResources().getColor(R.color.green_recipe));

            }
        });

//        txt_graph_from.setText(currentDateandTimess);
//        txt_sleep_date_from_dialog.setText(dummydate_from);
//        txt_graph_to.setText(currentDateandTimess);


        txt_graph_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrDateOpen = "";
                StrDateOpen1 = "fromgraph";
//                datepickerdialog.show();
                showDatePickerDailogentry();
                datepickerdialog_entry.show();
            }
        });

        txt_graph_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrDateOpen = "";
                StrDateOpen1 = "tograph";
//                datepickerdialog.show();
                showDatePickerDailogentry();
                datepickerdialog_entry.show();
            }
        });


        testparameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arylst_bcaq != null)
                    showDialog();
            }
        });

        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_graph_from.getText().toString().equals("Start Date") ||
                        txt_graph_to.getText().toString().equals("End Date")) {
                    Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                } else {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(txt_graph_from.getText().toString());
                        date2 = format.parse(txt_graph_to.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date1.compareTo(date2) > 0) {

                        Toast.makeText(getApplicationContext(), "please select End Date greater than From Date", Toast.LENGTH_LONG).show();
//

                    }else {

                        getParameterDataQn(2);

                    }
                }

            }
        });


        showDatePickerDailog();

        util = new Utils();
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        sessionManager = new SessionManager(NewHealthMonitoringActivity.this);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        ll_range = findViewById(R.id.ll_range);
        list_reemonitor_history = findViewById(R.id.list_reemonitor_history);
        edt_remark = findViewById(R.id.edt_remark);
        btn_submit = findViewById(R.id.btn_submit);
        txt_lab_test_value = findViewById(R.id.txt_lab_test_value);
        txt_lab_test_value.setRawInputType(Configuration.KEYBOARD_12KEY);


        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 8;
            final int maxDigitsAfterDecimalPoint = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };
        txt_lab_test_value.setFilters(new InputFilter[]{filter});

        txt_select_paramters = findViewById(R.id.txt_select_paramters);
        txt_select_unit = findViewById(R.id.txt_select_unit);
        txt_select_unit.setSelected(true);
        txt_select_measurement = findViewById(R.id.txt_select_measurement);
        txt_select_range = findViewById(R.id.txt_select_range);

        txt_weight_date_from = findViewById(R.id.txt_weight_date_from);
        txt_weight_date_to = findViewById(R.id.txt_weight_date_to);


//        txt_show_sleep_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
//        txt_show_sleep_to.setText(dummydate_to);
        txt_select_unit.setClickable(false);
        txt_select_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txt_select_paramters.getText().toString().equalsIgnoreCase("Select parameter")) {
                    Toast.makeText(NewHealthMonitoringActivity.this, "Please select parameter", Toast.LENGTH_SHORT).show();
                }
//                final Dialog dialog = new Dialog(NewHealthMonitoringActivity.this, R.style.CustomDialog);
//                dialog.setContentView(R.layout.dialog_select_parameters);
//                TextView txt_header = dialog.findViewById(R.id.txt_header);
//                txt_header.setText("Select Unit");
//                EditText edt_search_parameter = dialog.findViewById(R.id.edt_search_parameter);
//                ImageView img_close = dialog.findViewById(R.id.img_close);
//                img_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//
//                ListView lst_parameter = dialog.findViewById(R.id.lst_parameter);
//                final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(NewHealthMonitoringActivity.this, R.layout.simple_spinner_item, arlst_unit);
//                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                lst_parameter.setAdapter(adapter1);
//                edt_search_parameter.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        adapter1.getFilter().filter(s);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                    }
//                });
//                lst_parameter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//                        dialog.dismiss();
//
//                        txt_select_unit.setText(arlst_unit.get(i).toString());
//                        strUnitID = arlst_unitID.get(i).toString();
//
//
//                    }
//                });
//
//                dialog.show();
            }
        });
        txt_select_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(NewHealthMonitoringActivity.this, R.style.CustomDialog);
                dialog.setContentView(R.layout.dialog_select_parameters);
                ListView lst_parameter = dialog.findViewById(R.id.lst_parameter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewHealthMonitoringActivity.this, R.layout.simple_spinner_item, arylst_select_parameters);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lst_parameter.setAdapter(adapter);

                lst_parameter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    }
                });

                dialog.show();

            }
        });


        txt_select_paramters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_parameter = new Dialog(NewHealthMonitoringActivity.this, R.style.CustomDialog);
                dialog_parameter.setContentView(R.layout.dialog_select_parameters);
                RecyclerView lst_parameter = dialog_parameter.findViewById(R.id.lst_parameter);
                EditText edt_search_parameter = dialog_parameter.findViewById(R.id.edt_search_parameter);
                TextView txt_header = dialog_parameter.findViewById(R.id.txt_header);
                ImageView img_close = dialog_parameter.findViewById(R.id.img_close);
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_parameter.dismiss();
                    }
                });
                txt_header.setText("Select Parameter");
//                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewHealthMonitoringActivity.this, R.layout.simple_spinner_item, arylst_select_parameters);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                lst_parameter.setAdapter(adapter);


                final ParameterlistAdapter parameterlistAdapter = new ParameterlistAdapter(NewHealthMonitoringActivity.this, mFilterlist);

                lst_parameter.setAdapter(parameterlistAdapter);
                edt_search_parameter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        parameterlistAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
//                lst_parameter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        boolean found = false;
//                        dialog.dismiss();
//                        txt_select_paramters.setText(arylst_select_parameters.get(position).toString());
//                        Str_ParameterID = arylst_select_parametersID.get(position).toString();
//                        txt_select_unit.setText(arylst_select_parametersUnit_Name.get(position).toString());
//                        strUnitID=arylst_select_parametersUnit_ID.get(position).toString();
//
//
//
//
//
//
//
//                        for (int i = 0; i < arylst_LabTestLists.size(); i++) {
//
//                            if (arylst_select_parameters.get(position).toString().equalsIgnoreCase(arylst_LabTestLists.get(i).getTestName())) {
//
//
//
//
//
//                                if (arylst_LabTestLists.get(i).getRange() != null) {
//                                    txt_select_range.setVisibility(View.VISIBLE);
//
//
//
//
//
//
//
//                                    str_range = arylst_LabTestLists.get(i).getRange().getRange();
//                                    str_rangeID = arylst_LabTestLists.get(i).getRange().getId();
//                                    txt_select_range.setText("Range :" + str_range + " " + arylst_LabTestLists.get(i).getUnit());
//                                    found = true;
//                                    ll_range.setVisibility(View.VISIBLE);
//
//                                } else {
//                                    txt_select_range.setVisibility(View.GONE);
//                                    ll_range.setVisibility(View.VISIBLE);
//
//                                }
//
//
//                                break;
//                            }
//
//                        }
//
//
//                    }
//                });

                dialog_parameter.show();


            }
        });
        txt_select_measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txt_select_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        callParameterData();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();

                    return;

                }

                if (txt_select_paramters.getText().toString().equalsIgnoreCase("Select parameter")) {
                    Toast.makeText(NewHealthMonitoringActivity.this, "Please select parameter", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txt_lab_test_value.getText().toString().isEmpty()) {
                    Toast.makeText(NewHealthMonitoringActivity.this, "Please enter lab-test value", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (txt_select_unit.getText().toString().equalsIgnoreCase("Select Unit")) {
                    Toast.makeText(NewHealthMonitoringActivity.this, "Please enter unit", Toast.LENGTH_SHORT).show();

                    return;
                }
                callPostReemonitorParameterData();
            }
        });

    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dailog_select_parameter);
        lst_testname = dialog.findViewById(R.id.lst_testname);
        lst_testname.setAdapter(new TestNameAdapters(this, arylst_bcaq));
        lst_testname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getGraphDatalist(arylst_bcaq.get(position).getTestId());
                testIDSubmit = arylst_bcaq.get(position).getTestId();
                testname.setText("Score(" + arylst_bcaq.get(position).getTestName() + ")");
                testparameter.setText(arylst_bcaq.get(position).getTestName());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getParameterDataQn(int type) {
        util.showProgressbar(this);

        Call<ClsBcaPathoGraphMain> call = service.BCAPathoTestQuestion(userID, reporttypeID);
        call.enqueue(new Callback<ClsBcaPathoGraphMain>() {
            @Override
            public void onResponse(Call<ClsBcaPathoGraphMain> call, Response<ClsBcaPathoGraphMain> response) {
                util.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsBcaPathoGraphMain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (arylst_bcaq != null) {
                            arylst_bcaq.clear();
                        }
                        if (reporttypeID == 1) {
                            if (listResponse.getData().getPathoQ() != null) {
                                arylst_bcaq = listResponse.getData().getPathoQ();
                                testIDSubmit = arylst_bcaq.get(0).getTestId();
                                testname.setText("Score(" + arylst_bcaq.get(0).getTestName() + ")");

                            }
                        }


                        if (reporttypeID == 2) {
                            if (listResponse.getData().getBCAQ() != null) {
                                arylst_bcaq = listResponse.getData().getBCAQ();
                                testIDSubmit = arylst_bcaq.get(0).getTestId();
                                testname.setText("Score(" + arylst_bcaq.get(0).getTestName() + ")");

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
            public void onFailure(Call<ClsBcaPathoGraphMain> call, Throwable t) {
                Toast.makeText(NewHealthMonitoringActivity.this, "" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                util.hideProgressbar();
            }
        });
    }


    private void getGraphDatalist(int testID) {
        util.showProgressbar(this);
        Call<ClsGraphDataMainClass> call = service.getGraphData(userID, reporttypeID, submitFromHistoryDate, submitToHistoryDate, testID);
        call.enqueue(new Callback<ClsGraphDataMainClass>() {
            @Override
            public void onResponse(Call<ClsGraphDataMainClass> call, Response<ClsGraphDataMainClass> response) {
                util.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsGraphDataMainClass listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {

                        linegraph.clear();
                        linegraph.setData(null);
                        linegraph.highlightValue(null);
                        linegraph.invalidate();
                        arylst_graph_data = listResponse.getData();
                        if (arylst_graph_data != null) {
                            if (!arylst_graph_data.isEmpty()) {

                                setGraphData(arylst_graph_data);
                            } else {
                                Toast.makeText(NewHealthMonitoringActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(NewHealthMonitoringActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        Log.d("Error---->", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsGraphDataMainClass> call, Throwable t) {
                util.hideProgressbar();
            }
        });



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
                            tvNotificationCOunt.setVisibility(View.GONE);
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

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();


    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEmonitor");

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



        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }
    }


    private void setGraphData(final ArrayList<GraphData> aryCalories) {

        final ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();


        for (int i = 0; i < aryCalories.size(); i++) {
            yValues1.add(new Entry(i, Float.parseFloat(String.valueOf(aryCalories.get(i).getTestvalue()))));

        }


        linegraph.setVisibility(View.VISIBLE);

        LineDataSet set1, set2, set3, set4;
        set1 = new LineDataSet(yValues1, "");
        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(this, R.color.colorGreen1));
        set1.setValueTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        set1.setCircleColor(getResources().getColor(R.color.colorGreen1));

        set1.setLineWidth(2f);

//        linegraph.setDescription("");    // Hide the description
//        linegraph.getAxisLeft().setDrawLabels(false);
//        linegraph.getAxisRight().setDrawLabels(false);
//        linegraph.getXAxis().setDrawLabels(false);

        linegraph.getLegend().setEnabled(false);
//
        final LineData data = new LineData(set1);
        linegraph.setData(data);


        Description description = new Description();
//        linegraph.getDescription().setText("Score");


        XAxis xAxis = linegraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        if (aryCalories.size() == 1) {
            xAxis.setLabelCount(aryCalories.size());

        } else if (aryCalories.size() > 10) {
            xAxis.setLabelCount(10);

        } else {
            xAxis.setLabelCount(aryCalories.size(), true);

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
        left.setGridColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreyLight2)); // no grid lines
        left.setGridColor(Color.WHITE); // grid li


        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                if (aryCalories != null) {
                    for (int i = 0; i < aryCalories.size(); ++i) {
                        if (yValues1.get(i).getX() == value) {
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
        }, 500);


    }


    public String WaterDatesNew(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    private void CallOtherTestReports(String fromdate, String todate) {
        util.showProgressbar(NewHealthMonitoringActivity.this);


//        Call<ResponseBody> call = reeTestService.getCompareReportList(userID,2,"2019-01-01","2021-01-01");

        Call<ResponseBody> call = reeTestService.getReemonitorCompareReportList(userID, testType, fromdate, todate);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    util.hideProgressbar();
                    int count = 0;
                    ResponseBody listResponse = response.body();
                    ArrayList<String> arylst_key = null;
                     arylst_result = new ArrayList<>();
                    try {
                        String res = response.body().string();
                        JSONObject obj = new JSONObject(res);
                        JSONArray jsonArray = null;
                        if (obj.get("Data").toString().length() != 2) {
                            jsonArray = obj.getJSONArray("Data");


                            JSONObject object = jsonArray.getJSONObject(0);
                            StringBuilder stringBuilder = new StringBuilder();
                            arylst_key = new ArrayList<>();
                            for (String key : iterate(object.keys())) {
                                stringBuilder.append(key).append(",");
                                arylst_key.add(key);
                                // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.

                            }
                            arylst_result.add(0, stringBuilder.substring(0, stringBuilder.length() - 1));

                            for (int i = 0; i < jsonArray.length(); i++) {
                                count = 0;

                                JSONObject object1 = jsonArray.getJSONObject(i);
                                StringBuilder stringBuilder1 = new StringBuilder();

                                for (int j = 0; j < arylst_key.size(); j++) {
                                    String data = object1.getString(arylst_key.get(j).toString());
                                    if (!data.equalsIgnoreCase("null")) {
                                        stringBuilder1.append(data).append(",");
                                    } else {
                                        stringBuilder1.append("-").append(",");
                                    }

                                    count++;

                                }

                                arylst_result.add(stringBuilder1.toString().substring(0, stringBuilder1.length() - 1));


                            }

                            recycler_report_compare.setVisibility(View.VISIBLE);
                            txt_allcomp.setVisibility(View.VISIBLE);
                            txt_nofoundcom.setVisibility(View.GONE);
                             clsReportCompareAdapter = new PlannerAdapter(NewHealthMonitoringActivity.this, arylst_result, count);
                            recycler_report_compare.setAdapter(clsReportCompareAdapter);
                            clsReportCompareAdapter.notifyDataSetChanged();

                        } else {
                            recycler_report_compare.setVisibility(View.GONE);
                            txt_allcomp.setVisibility(View.GONE);
                            txt_nofoundcom.setVisibility(View.GONE);
                            arylst_result.clear();
                             clsReportCompareAdapter = new PlannerAdapter(NewHealthMonitoringActivity.this, arylst_result, count);
                            recycler_report_compare.setAdapter(clsReportCompareAdapter);
                            clsReportCompareAdapter.notifyDataSetChanged();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Log error here since request failed
                util.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }


    private <T> Iterable<T> iterate(final Iterator<T> i) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }


    public String formatDatesSleep(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private void callParameterData() {

        util.showProgressbar(NewHealthMonitoringActivity.this);
        services = Client.getClient().create(HomeFragmentService.class);

        sessionManager = new SessionManager(NewHealthMonitoringActivity.this);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        Call<ClsHealthMonitorMain> call = services.getParametersListApi(userID);
        call.enqueue(new Callback<ClsHealthMonitorMain>() {
            @Override
            public void onResponse(Call<ClsHealthMonitorMain> call, retrofit2.Response<ClsHealthMonitorMain> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsHealthMonitorMain tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode().equals("1")) {
                        if (tipsResponse.getData() != null) {

                            for (int i = 0; i < tipsResponse.getData().getUnitList().size(); i++) {
                                arlst_unit.add(tipsResponse.getData().getUnitList().get(i).getUnit());
                                arlst_unitID.add(tipsResponse.getData().getUnitList().get(i).getId());
                            }

                            mFilterlist = tipsResponse.getData().getLabTestList();
                            for (int i = 0; i < mFilterlist.size(); i++) {
                                arylst_select_parameters.add(mFilterlist.get(i).getTestName());
                                arylst_select_parametersID.add(String.valueOf(mFilterlist.get(i).getTestId()));
                                arylst_select_parametersUnit_Name.add(String.valueOf(mFilterlist.get(i).getUnit()));
                                arylst_select_parametersUnit_ID.add(String.valueOf(mFilterlist.get(i).getUnitId()));

                            }
                        }
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ClsHealthMonitorMain> call, Throwable t) {
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void callPostReemonitorParameterData() {

        util.showProgressbar(NewHealthMonitoringActivity.this);
        services = Client.getClient().create(HomeFragmentService.class);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
//        String currentDateandTime = sdf.format(new Date());

        ClsPostReemonitor clsPostReemonitor = new ClsPostReemonitor();
        clsPostReemonitor.setId(0);
        clsPostReemonitor.setLabTestId(Integer.parseInt(Str_ParameterID));
        clsPostReemonitor.setReeworkerId(userID);
        clsPostReemonitor.setCreatedOn(currentDateandTime);

        clsPostReemonitor.setTestValue(Double.parseDouble(txt_lab_test_value.getText().toString()));
        clsPostReemonitor.setRemark(edt_remark.getText().toString());
        clsPostReemonitor.setUnitId(Integer.parseInt(strUnitID));
        clsPostReemonitor.setLabTime(lsTime);


        Call<ClsWaterAddSuccessData> call = services.posthealthmonitirData(clsPostReemonitor);
        call.enqueue(new Callback<ClsWaterAddSuccessData>() {
            @Override
            public void onResponse(Call<ClsWaterAddSuccessData> call, retrofit2.Response<ClsWaterAddSuccessData> response) {

                util.hideProgressbar();


                txt_select_paramters.setText("Select parameter");
                txt_lab_test_value.setText("");
                txt_select_unit.setText("Select Unit");
                edt_remark.setText("");
                txt_select_range.setText("");
                txt_select_range.setVisibility(View.GONE);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterAddSuccessData tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {

                            Toast.makeText(NewHealthMonitoringActivity.this, "" + tipsResponse.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ClsWaterAddSuccessData> call, Throwable t) {
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void getHistoryParameterData() {


        util.showProgressbar(NewHealthMonitoringActivity.this);
        services = Client.getClient().create(HomeFragmentService.class);

        ReemonitorHistoryRequest reemonitorHistoryRequest = new ReemonitorHistoryRequest();
        reemonitorHistoryRequest.setFromDate(submitFromHistoryDate);
        reemonitorHistoryRequest.setToDate(submitToHistoryDate);
        reemonitorHistoryRequest.setReeworkerId(String.valueOf(userID));


        Call<ClsReemonitorHistory> call = services.getHealthMonitorHistory(reemonitorHistoryRequest);
        call.enqueue(new Callback<ClsReemonitorHistory>() {
            @Override
            public void onResponse(Call<ClsReemonitorHistory> call, retrofit2.Response<ClsReemonitorHistory> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsReemonitorHistory tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode().equals("1")) {
                        if (tipsResponse.getData() != null) {
//                            ArrayList<ClsCustomTestList> arylst_ClsCustomTestLists = new ArrayList<>();
                            arylst_ClsCustomTestLists = new ArrayList<>();
                            for (int i = 0; i < tipsResponse.getData().size(); i++) {

                                for (int j = 0; j < tipsResponse.getData().get(i).getTestList().size(); j++) {
                                    ClsCustomTestList clsCustomTestList = new ClsCustomTestList();

                                    for (int k = 0; k < arlst_unitID.size(); k++) {
                                        if (arlst_unitID.get(k).toString().equalsIgnoreCase(tipsResponse.getData().get(i).getTestList().get(j).getUnitId())) {

                                            clsCustomTestList.setUnit(arlst_unit.get(k).toString());
                                            break;
                                        }

                                    }

                                    clsCustomTestList.setDate(tipsResponse.getData().get(i).getTestList().get(j).getCreatedOn());
                                    clsCustomTestList.setId(Integer.parseInt(tipsResponse.getData().get(i).getTestList().get(j).getId()));
                                    clsCustomTestList.setRemark(tipsResponse.getData().get(i).getTestList().get(j).getRemark());
                                    clsCustomTestList.setTestName(tipsResponse.getData().get(i).getTestList().get(j).getTestName());
                                    clsCustomTestList.setTestValue(tipsResponse.getData().get(i).getTestList().get(j).getTestValue());
                                    clsCustomTestList.setUnitID(Integer.parseInt(tipsResponse.getData().get(i).getTestList().get(j).getUnitId()));
                                    clsCustomTestList.setLabTime(tipsResponse.getData().get(i).getTestList().get(j).getLabTime());
                                    arylst_ClsCustomTestLists.add(clsCustomTestList);

                                }

                            }

                            Collections.sort(arylst_ClsCustomTestLists);

                            if (arylst_ClsCustomTestLists.size() == 0) {
                                list_reemonitor_history.setVisibility(View.GONE);
                                txt_Allhistory.setVisibility(View.GONE);
                                txt_nofoundhist.setVisibility(View.VISIBLE);
                                list_reemonitor_history.setAdapter(new HealthmonitorHistoryListAdapter(NewHealthMonitoringActivity.this, arylst_ClsCustomTestLists));

                            } else {
                                list_reemonitor_history.setVisibility(View.VISIBLE);
                                txt_Allhistory.setVisibility(View.VISIBLE);
                                txt_nofoundhist.setVisibility(View.GONE);
                                list_reemonitor_history.setAdapter(new HealthmonitorHistoryListAdapter(NewHealthMonitoringActivity.this, arylst_ClsCustomTestLists));

                            }


                        }


                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ClsReemonitorHistory> call, Throwable t) {
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        if (StrDateOpen.equalsIgnoreCase("weightEntry")) {
            dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;
//            todaysubmitHistoryDate=year+"-"+(month+1)+"-"+dayOfMonth;

//            yyyy-MM-dd

//            txt_weight_date_select.setText(dummydate_from);
        }


        if (StrDateOpen.equalsIgnoreCase("from")) {

            if (dayOfMonth < 10) {
                dummydate_from = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;

            } else {
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
                Toast.makeText(NewHealthMonitoringActivity.this, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_weight_date_from.setText(dummydate_from);
            txt_sleep_date_from_dialog.setText(dummydate_from);
            submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        }
        if (StrDateOpen.equalsIgnoreCase("to")) {
            if (!dummydate_from.trim().isEmpty()) {
                if (dayOfMonth < 10) {
                    dummydate_to = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;

                } else {
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
                    Toast.makeText(NewHealthMonitoringActivity.this, "To date should be greater than From date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    txt_weight_date_to.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate = year + "-" + (month + 1) + "-" + dayOfMonth;

//                    callWeightHistoryApi(submitFromDate,submitToDate);
                    getHistoryParameterData();

                }


            } else {
                Toast.makeText(NewHealthMonitoringActivity.this, "Please select from date", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public String getFromattedStringDate(int s) {


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }


    }

    private void showCloseDailog(final ClsCustomTestList adapterPosition) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Are you sure want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        getDeleteData(adapterPosition.getId());


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


    @Override
    public void geteditinTest(final ClsCustomTestList data) {
        final Dialog dialog = new Dialog(NewHealthMonitoringActivity.this, R.style.CustomDialog);
        dialog.setContentView(R.layout.row_edit_reemonitor);
        final EditText edt_remark_update = dialog.findViewById(R.id.edt_remark_update);
        if (data.getRemark() != null) {
            edt_remark_update.setText(data.getRemark());

        }
        ImageView img_quantity_close = dialog.findViewById(R.id.img_quantity_close);

        img_quantity_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText edt_updated_value = dialog.findViewById(R.id.edt_updated_value);


        edt_updated_value.setRawInputType(Configuration.KEYBOARD_12KEY);


        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 8;
            final int maxDigitsAfterDecimalPoint = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };
        edt_updated_value.setFilters(new InputFilter[]{filter});
        final TextView edt_updated_unit = dialog.findViewById(R.id.edt_updated_unit);
        edt_updated_unit.setText(data.getUnit());
//        edt_updated_unit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateunit(data, edt_updated_unit);
//            }
//        });
        edt_updated_value.setText(data.getTestValue());
        Button btn_submit_value = dialog.findViewById(R.id.btn_submit_value);
        btn_submit_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateReemonitorParameterData(data, edt_updated_value, edt_remark_update);
            }
        });
        dialog.show();
    }

    @Override
    public void getDeleteTest(ClsCustomTestList adapterPosition) {
        showCloseDailog(adapterPosition);
    }

    private void updateReemonitorParameterData(ClsCustomTestList clsCustomTestList, EditText edt_updated_value, EditText edt_updated_remark) {

        String int_testID = "";
        for (int i = 0; i < arylst_select_parameters.size(); i++) {

            if (clsCustomTestList.getTestName().equalsIgnoreCase(arylst_select_parameters.get(i).toString())) {
                int_testID = arylst_select_parametersID.get(i).toString();
                break;
            }
        }

        util.showProgressbar(NewHealthMonitoringActivity.this);
        services = Client.getClient().create(HomeFragmentService.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        ClsPostReemonitor clsPostReemonitor = new ClsPostReemonitor();
        clsPostReemonitor.setId(clsCustomTestList.getId());
        clsPostReemonitor.setLabTestId(Integer.parseInt(int_testID));
        clsPostReemonitor.setReeworkerId(userID);
        clsPostReemonitor.setCreatedOn(clsCustomTestList.getDate());
        clsPostReemonitor.setLabTime(clsCustomTestList.getLabTime());

        clsPostReemonitor.setTestValue(Double.parseDouble(edt_updated_value.getText().toString()));
        clsPostReemonitor.setRemark(edt_updated_remark.getText().toString());
        clsPostReemonitor.setUnitId(clsCustomTestList.getUnitID());


        Call<ClsWaterAddSuccessData> call = services.posthealthmonitirData(clsPostReemonitor);
        call.enqueue(new Callback<ClsWaterAddSuccessData>() {
            @Override
            public void onResponse(Call<ClsWaterAddSuccessData> call, retrofit2.Response<ClsWaterAddSuccessData> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterAddSuccessData tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {
                            getHistoryParameterData();

                            Toast.makeText(NewHealthMonitoringActivity.this, "" + tipsResponse.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ClsWaterAddSuccessData> call, Throwable t) {
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void showNewgetSleephistroy() {

        final Dialog dialog = new Dialog(NewHealthMonitoringActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog = dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog = dialog.findViewById(R.id.txt_sleep_date_to_dialog);


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(NewHealthMonitoringActivity.this)) {

                    dialog.dismiss();

                    getHistoryParameterData();
                } else {

                }

            }
        });


        txt_show_sleep_from.setText(dummydate_from);
        txt_sleep_date_from_dialog.setText(dummydate_from);
        txt_show_sleep_to.setText(dummydate_to);
        txt_sleep_date_to_dialog.setText(dummydate_to);


        txt_sleep_date_from_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from";
                datepickerdialog.show();

            }
        });

        txt_sleep_date_to_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "to";
                datepickerdialog.show();
            }
        });

        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }

    private void showDatePickerDailog() {
        String strMindate[] = new SessionManager(NewHealthMonitoringActivity.this).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(NewHealthMonitoringActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, NewHealthMonitoringActivity.this, year, month, day);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();


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

    @SuppressLint("NewApi")
    private void showDatePickerDailogentry() {
        String strMindate[] = new SessionManager(NewHealthMonitoringActivity.this).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_entry = new DatePickerDialog(NewHealthMonitoringActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, NewHealthMonitoringActivity.this, year, month, day);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog_entry.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();


        datepickerdialog_entry.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog_entry.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog_entry.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        datepickerdialog_entry.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentDateandTime = formatDatessubmit(year + "-" + (month + 1) + "-" + dayOfMonth);

                if (month + 1 < 10) {
                    dummydate_from = dayOfMonth + "-" + "0" + (month + 1) + "-" + year;
                } else {
                    dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;
                }

                if (StrDateOpen.equals("from")) {
                    txt_show_sleep_from.setText(dummydate_from);
                    submitFromHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    ;
                } else if (StrDateOpen.equals("to")) {
                    txt_show_sleep_to.setText(dummydate_from);
                    submitToHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    ;
                } else if (StrDateOpen1.equals("from")) {
                    txt_comp_from.setText(dummydate_from);
                    com_from_date = year + "-" + (month + 1) + "-" + dayOfMonth;
                } else if (StrDateOpen1.equals("to")) {
                    txt_comp_to.setText(dummydate_from);
                    com_to_date = year + "-" + (month + 1) + "-" + dayOfMonth;
                } else if (StrDateOpen1.equals("fromgraph")) {
                    txt_graph_from.setText(dummydate_from);
                    submitFromHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                } else if (StrDateOpen1.equals("tograph")) {
                    txt_graph_to.setText(dummydate_from);
                    submitToHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                } else {
                    txt_select_paramters_date.setText(dummydate_from);


                }


//                 else if (StrDateOpen1.equals("historyfrom")) {
//                    txt_comp_from.setText(dummydate_from);
//                    com_from_date =  year + "-" +(month + 1)  + "-"+dayOfMonth;
//                } else if (StrDateOpen1.equals("historyto")) {
//                    txt_comp_to.setText(dummydate_from);
//                    com_to_date =  year + "-" +(month + 1) + "-" +dayOfMonth;
//                }


            }
        });


    }

    public String formatDatessubmit(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private void getDeleteData(int id) {


        util.showProgressbar(NewHealthMonitoringActivity.this);
        services = Client.getClient().create(HomeFragmentService.class);


        Call<ClsWaterAddSuccessData> call = services.deleteHealthMonitor(id);
        call.enqueue(new Callback<ClsWaterAddSuccessData>() {
            @Override
            public void onResponse(Call<ClsWaterAddSuccessData> call, retrofit2.Response<ClsWaterAddSuccessData> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterAddSuccessData tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {

                        Toast.makeText(NewHealthMonitoringActivity.this, "" + tipsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getHistoryParameterData();

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ClsWaterAddSuccessData> call, Throwable t) {
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    public void updateunit(final ClsCustomTestList data, final TextView edt_updated_unit) {
        final Dialog dialog = new Dialog(NewHealthMonitoringActivity.this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_select_parameters);
        TextView txt_header = dialog.findViewById(R.id.txt_header);
        txt_header.setText("Select Unit");
        EditText edt_search_parameter = dialog.findViewById(R.id.edt_search_parameter);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        ListView lst_parameter = dialog.findViewById(R.id.lst_parameter);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(NewHealthMonitoringActivity.this, R.layout.simple_spinner_item, arlst_unit);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lst_parameter.setAdapter(adapter1);
        edt_search_parameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter1.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        lst_parameter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                dialog.dismiss();

                edt_updated_unit.setText(arlst_unit.get(i).toString());
                strUnitID = arlst_unitID.get(i).toString();
                data.setUnitID(Integer.parseInt(strUnitID));


            }
        });

        dialog.show();
    }

    private void shownoplan() {

        final Dialog dialog = new Dialog(NewHealthMonitoringActivity.this, R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired = dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe = dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no = dialog.findViewById(R.id.btn_subscribe_no);
        btn_subscribe_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent intent = new Intent(NewHealthMonitoringActivity.this, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Date tDate = new Date();
        //SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);


        Calendar current = Calendar.getInstance();
        Calendar datetime = Calendar.getInstance();

        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);

//                if (datetime.getTimeInMillis() > current.getTimeInMillis())
//                {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);

        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        lsTime = "";
        lsTime = formatter.format(cal.getTime());
        txt_select_paramters_time.setText(lsTime);

    }

    @Override
    public void getParmaeterPosition(int position, LabTestList object) {
        boolean found = false;
        dialog_parameter.dismiss();

        txt_select_paramters.setText(object.getTestName().toString());
        Str_ParameterID = object.getTestId().toString();
        txt_select_unit.setText(object.getUnit().toString());
        strUnitID = String.valueOf(object.getUnitId());


        for (int i = 0; i < mFilterlist.size(); i++) {

            if (object.getTestName().toString().equalsIgnoreCase(mFilterlist.get(i).getTestName())) {


                if (mFilterlist.get(i).getRange() != null) {
                    txt_select_range.setVisibility(View.VISIBLE);


                    str_range = mFilterlist.get(i).getRange().getRange();
                    str_rangeID = mFilterlist.get(i).getRange().getId();
                    txt_select_range.setText("Range :" + str_range + " " + mFilterlist.get(i).getUnit());
                    found = true;
                    ll_range.setVisibility(View.VISIBLE);

                } else {
                    txt_select_range.setVisibility(View.GONE);
                    ll_range.setVisibility(View.VISIBLE);

                }


                break;
            }

        }


    }


    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

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
