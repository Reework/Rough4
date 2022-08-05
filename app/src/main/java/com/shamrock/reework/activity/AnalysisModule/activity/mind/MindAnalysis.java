package com.shamrock.reework.activity.AnalysisModule.activity.mind;

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
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.SleepAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalAdapter;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalPojo;
import com.shamrock.reework.activity.AnalysisModule.activity.sleep.GeneratePDF;
import com.shamrock.reework.activity.AnalysisModule.activity.sleep.SleepAnaysisData;
import com.shamrock.reework.activity.HomeModule.adapter.TipsAdapter;
import com.shamrock.reework.activity.HomeModule.fragment.HomeFragment;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.mindhistory.MindHistoryDialog;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.AddMoodRequest;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.MoodResponse;
import com.shamrock.reework.api.response.MoodUpdateResponce;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.TipsResponce;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.fragment.MasterMindFragment;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MindAnalysis extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "ACTUAL_MIND_STATUS";
    private static final String ARG_PARAM4 = "BING_QUANTITY";

    private String mParam1;
    private String mParam2;
    private  String mParam3;
    private  String mParam4;
    CommonService commonService;
    private ListView list_mind_history;
    MindHistoryDialog mindDialog;


    private MasterMindFragment.OnFragmentInteractionListener mListener;

    private Context context;
    private TextView txt_header_mind;
    Utils utils;
    HomeFragmentService service;
    SessionManager sessionManager;

    LineChart lineChart;
    int color_Orange, color_Green;
    LinearLayout linearLayout_submit, linearLayout_graph;
    Button buttonSubmit;

    RadioButton rd_button_mind_history;
    RadioButton rd_button_mind;

    TextView btn_show_mind_history;
    TextView txt_mind_date_to;
    TextView txt_mind_date_from;

    ArrayList<MoodResponse.Data> mList;
    ArrayList<String> xValuesForChart;
    ArrayList<Entry> yValueForChart;
    ArrayList<Entry>bingValueForChart;
    //    private int userId,recoachId, iHAPPY = 5, iSTRESS = -5, iCONFUSED = 0, iANXIOUS = -3;
    private int userId,recoachId, iHAPPY = 10, iSTRESS = 0, iCONFUSED = 5, iANXIOUS = -3;
    private int iYes=-5,iNo=5,iSometime=0;
    View view;
    int mindValue=2;
    String strFeeling = "Happy";
    RadioGroup RadioGroupBodyShape,RadioGroupMind;
    RadioButton radioButtonHappy,radioButtonAnxious,radioButtonStressed,radioButtonConfused,
            radioButtonMindYes,radioButtonMindNo,radioButtonMindSometime,radioButtonMind_Stressed;
    ImageView imageView_Mind_Mood;
    DatePickerDialog datepickerdialog;

    private ArrayList<TipsResponce.Datum> mDataListTips;
    private ArrayList<TipsResponce.Datum> mDataListTips_filter;
    TipsAdapter tipsAdapter,tipsAdapter1;

    RecyclerView recyclerView_tips,recyclerView_tips1;
    private HomeFragmentResponse.Data mHomeModel = null;
    private String StrDateOpen="";
    private String dummydate_from="";
    private String dummydate_to="";
    private String submitToDate="";
    private String submitFromDate="";
    private ViewFlipper vp_mind;
    NotificationService notificationService;
    Spinner spinner;

    //showdatedata
    private TextView txt_show_sleep_to,txt_show_sleep_from;
    private ImageView img_add_sleep_date;
    private TextView txt_sleep_date_from_dialog;
    private TextView txt_sleep_date_to_dialog;
    TextView txt_sleep_date_from, txt_sleep_date_to;
    private DatePickerDialog datepickerdialog_history;

    //New
    LinearLayout parent;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView imgopen,imgclose;
    LinearLayout layout_open,layout_close;

    Toolbar toolbar;
    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
//    NotificationService notificationService;


    Button btn_weekly, btn_3months, btn_6months;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mind_analysis);
        context=MindAnalysis.this;

        showDateWiseData();

        sessionManager = new SessionManager(context);
        service = Client.getClient().create(HomeFragmentService.class);
        utils = new Utils();
        layout_open = findViewById(R.id.layout_open);
        layout_close = findViewById(R.id.layout_close);
        imgopen = findViewById(R.id.imgopen);
        imgclose = findViewById(R.id.imgclose);
        sessionManager = new SessionManager(context);
        notificationService = Client.getClient().create(NotificationService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        commonService = Client.getClient().create(CommonService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);

        color_Orange = ContextCompat.getColor(context, R.color.color_graph_orange);
        color_Green = ContextCompat.getColor(context, R.color.colorBlue5);

        mList = new ArrayList<>();
        xValuesForChart = new ArrayList<>();
        yValueForChart = new ArrayList<>();
        bingValueForChart = new ArrayList<>();
        spinner = findViewById(R.id.spinner_Weight_Months);

        //N̥ew
        parent = findViewById(R.id.parent);
//        scaleGestureDetector = new ScaleGestureDetector(this, new ColoriesAnalysisActivty.ScaleListener());


        TextView labelMyProgress=findViewById(R.id.labelMyProgress);
        ImageView img_send = findViewById(R.id.img_send);
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                screenShot(parent, "result");
                showFullScreenDailog(mList);
//                if (!mList.isEmpty()){
//                    new MindGeneratePDF(mList,context,"").execute();
//
//                }else {
//                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
//                }

            }
        });


        spinner.setOnItemSelectedListener(this);
//        ImageView imgLeft=findViewById(R.id.imgLeft);
//        imgLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        TextView tvTitle=findViewById(R.id.tvTitle);


//        boolean  ISFromANnalysis=getIntent().getBooleanExtra("ISFromANnalysis",false);
//        if (!ISFromANnalysis){
//            tvTitle.setText("Stress");
//        }else {
//            tvTitle.setText("Mind");
//
//        }
        lineChart = (LineChart) findViewById(R.id.lineChart_mood);
        linearLayout_submit = findViewById(R.id.linLay_Mind_Submit);
        btn_show_mind_history = findViewById(R.id.btn_show_mind_history);
        linearLayout_graph = findViewById(R.id.linLay_Mind_Graph);
        buttonSubmit = findViewById(R.id.buttonSubmitMind);
        RadioGroupBodyShape = findViewById(R.id.RadioGroupfeeling);
        RadioGroupMind  = findViewById(R.id.RadioGroupMind);
        txt_header_mind  = findViewById(R.id.txt_header_mind);
        submitFromDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        vp_mind=findViewById(R.id.vp_mind);
        rd_button_mind=findViewById(R.id.rd_button_mind);
        list_mind_history=findViewById(R.id.list_mind_history);
        rd_button_mind_history=findViewById(R.id.rd_button_mind_history);
        rd_button_mind_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vp_mind.setDisplayedChild(1);
            }
        });

        btn_show_mind_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callMindHistoryApi(submitFromDate,submitToDate);

            }
        });

        rd_button_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_mind.setDisplayedChild(0);
            }
        });
        txt_mind_date_from=findViewById(R.id.txt_mind_date_from);
        txt_mind_date_to=findViewById(R.id.txt_mind_date_to);
//        showDatePickerDailog();
        vp_mind.setDisplayedChild(0);

        txt_mind_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="from";
                datepickerdialog.show();
            }
        });

        txt_mind_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";
                datepickerdialog.show();

            }
        });







//        dummydate_from=formatDatesSleep(submitFromDate);
        txt_mind_date_from.setText(dummydate_from);
//        dummydate_to=formatDatesSleep(submitToDate);
        txt_mind_date_to.setText(dummydate_to);
//        showDatePickerDailog();
//        callMindHistoryApi(submitFromDate,submitToDate);




        radioButtonHappy  = findViewById(R.id.radioButtonHappy);
        radioButtonAnxious  = findViewById(R.id.radioButtonAnxious);
        radioButtonStressed  = findViewById(R.id.radioButtonStressed);
        radioButtonStressed.setText("Stressed");
        radioButtonConfused  = findViewById(R.id.radioButtonConfused);
        radioButtonMindYes  = findViewById(R.id.radioButtonMindYes);
        radioButtonMindNo  = findViewById(R.id.radioButtonMindNo);
        radioButtonMindSometime  = findViewById(R.id.radioButtonMindSometime);
        radioButtonMind_Stressed = findViewById(R.id.radioButtonMind_Stressed);

        imageView_Mind_Mood = findViewById(R.id.imageView_Mind_Mood);

        recyclerView_tips = findViewById(R.id.recyclerView_tips);
        recyclerView_tips1 = findViewById(R.id.recyclerView_tips1);
        txt_header_mind.setText("Better Mind Tips");
        //for tips Data
        //Check Wheater Data is Already Loaded.
        if(HomeFragment.mCommonDataListTips.size()!=0){
            mDataListTips = HomeFragment.mCommonDataListTips;
        }
       /* mDataListTips = new ArrayList<>();
        tipsAdapter = new TipsAdapter(context, mDataListTips,"MasterMindFragment");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView_tips.setLayoutManager(layoutManager);
        recyclerView_tips.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tips.setAdapter(tipsAdapter);*/

        if (Utils.isNetworkAvailable(context)) {
//            CallToGetInitialData(false);
//            CallToFetchTipsData();

            getMeonthList();
        } else
            Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();



      /* below will be graph response
        "GraphData": [
        {
            "mood_date": "Nov-2018", // xVals
                "mood": 8,          // yVals1
                "binge": 1          // yVals2
        },
        {
            "mood_date": "Dec-2018",
                "mood": 4,
                "binge": -1
        }
            ],
      */
//        buttonSubmit.setOnClickListener(this);
//        radioButtonHappy.setOnClickListener(this);
//        radioButtonAnxious.setOnClickListener(this);
//        radioButtonStressed.setOnClickListener(this);
//        radioButtonConfused.setOnClickListener(this);
//        radioButtonMindYes.setOnClickListener(this);
//        radioButtonMindNo.setOnClickListener(this);
//        radioButtonMindSometime.setOnClickListener(this);

        //for tips Data
        mDataListTips = new ArrayList<>();



        tipsAdapter = new TipsAdapter(context, mDataListTips,"MasterMindFragment");
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context);
        recyclerView_tips.setLayoutManager(layoutManager2);
        recyclerView_tips.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tips.setAdapter(tipsAdapter);

        tipsAdapter1 = new TipsAdapter(context, mDataListTips,"MasterMindFragment");
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context);
        recyclerView_tips1.setLayoutManager(layoutManager1);
        recyclerView_tips1.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tips1.setAdapter(tipsAdapter1);



        final Button ll_sleep_date=findViewById(R.id.btn_selectdate);
//        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showNewgetFoodhistroy();
//            }
//        });


        btn_weekly = findViewById(R.id.btn_weekly);
        btn_3months = findViewById(R.id.btn_3months);
        btn_6months = findViewById(R.id.btn_6months);



        btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
        btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
        btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
        ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.green_recipe));

        btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
        btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
        btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
        ll_sleep_date.setTextColor(getResources().getColor(R.color.white_recipe));

        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.white_recipe));




                showNewgetFoodhistroy();

            }
        });


        btn_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
                calendar.add(Calendar.DAY_OF_YEAR, -7);
                Date newDate = calendar.getTime();

                submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate);
                submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                dummydate_from = formatDatesSleep(submitFromDate);
                txt_show_sleep_from.setText(dummydate_from);
                dummydate_to = formatDatesSleep(submitToDate);
                txt_show_sleep_to.setText(dummydate_to);

                btn_weekly.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));



                CallToFetchMoodData("");

            }
        });


        btn_3months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
//                calendar.add(Calendar.DAY_OF_YEAR, -7);
                calendar.add(Calendar.MONTH, -3);
                Date newDate = calendar.getTime();
                submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate);
                submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                dummydate_from = formatDatesSleep(submitFromDate);
                txt_show_sleep_from.setText(dummydate_from);
                dummydate_to = formatDatesSleep(submitToDate);
                txt_show_sleep_to.setText(dummydate_to);

                btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));



                CallToFetchMoodData("");


            }
        });


        btn_6months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
//                calendar.add(Calendar.DAY_OF_YEAR, -7);
                calendar.add(Calendar.MONTH, -6);
                Date newDate = calendar.getTime();
                submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate);
                submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                dummydate_from = formatDatesSleep(submitFromDate);
                txt_show_sleep_from.setText(dummydate_from);
                dummydate_to = formatDatesSleep(submitToDate);
                txt_show_sleep_to.setText(dummydate_to);

                btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));



                CallToFetchMoodData("");


            }
        });



        imgopen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {
//                if(ColoriesAnalysisActivty.this.getResources().getConfiguration().orientation==1){
////                    Toast.makeText(getApplicationContext(),"potrest",Toast.LENGTH_LONG).show();
//                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                if(layout_open.getVisibility()==View.VISIBLE){
                    layout_close.setVisibility(View.VISIBLE);
                    layout_open.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//                   if(ColoriesAnalysisActivty.this.getResources().getConfiguration().orientation==0){
////                       Toast.makeText(getApplicationContext(),"landscapo",Toast.LENGTH_LONG).show();
                }
//
//
//
//                }
//                if(layout_close.getVisibility()==View.VISIBLE){
//                    layout_close.setVisibility(View.GONE);
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                    layout_open.setVisibility(View.VISIBLE);
//
//                }
//                layout_open.setVisibility(View.GONE);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//                layout_close.setVisibility(View.VISIBLE);

            }
        });
        imgclose.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                layout_close.setVisibility(View.GONE);
//                layout_open.setVisibility(View.VISIBLE);
                if(layout_close.getVisibility()==View.VISIBLE){
                    layout_close.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    layout_open.setVisibility(View.VISIBLE);

                }
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
        boolean  ISFromANnalysis=getIntent().getBooleanExtra("ISFromANnalysis",false);
        if (!ISFromANnalysis){
            tvTitle.setText("Stress");
        }else {
            tvTitle.setText("Mind");

        }

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



    private void screenShot(View view, String fileName) {

        View v1 = parent.getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bm = v1.getDrawingCache();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
//        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bm, "title", null);
//        Uri bitmapUri = Uri.parse(bitmapPath);


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bm,"IMG_" + Calendar.getInstance().getTime(),null);
        Uri bitmapUri = Uri.parse(path);


//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(bitmapUri)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void showFullScreenDailog(final ArrayList<MoodResponse.Data> tempList1) {
//        final ArrayList<ShareCalPojo> data =new ArrayList<ShareCalPojo>();
        ShareCalPojo data=new ShareCalPojo();
        ShareCalPojo data0=new ShareCalPojo();

        data.setId(1);
        data.setName("Share As PDF");
        data0.setId(2);
        data0.setName("Share As Image");
        final ArrayList<ShareCalPojo> data1 =new ArrayList<ShareCalPojo>();
        data1.add(data);
        data1.add(data0);
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.lay_show_device_list);
        ListView lst_device_list=dialog.findViewById(R.id.lst_device_list);
        TextView txt_header =dialog.findViewById(R.id.txt_header);
        ImageView img_close=dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txt_header.setText("Share Option");
        ShareCalAdapter shareCalAdapter=new ShareCalAdapter(context,data1);
        lst_device_list.setAdapter(shareCalAdapter);

        lst_device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (data1.get(position).getId()==1){
//                    openFitBit();
                    dialog.dismiss();
                    if (!mList.isEmpty()){


                        Dexter.withActivity(MindAnalysis.this)
                                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        new MindGeneratePDF(mList, context, "").execute();
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();



                    }else {
                        Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (data1.get(position).getId()==2){
                    dialog.dismiss();
                    screenShot(parent, "result");
//                    Intent intent =new Intent(getActivity(), GfitHomeActivity.class);
//                    startActivity(intent);
                }else {
                    Toast.makeText(context, "This device is not linked with app", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }


    private void CallToGetInitialData(final boolean isSwipeToRefresh)
    {
        HomeFragmentRequest request = new HomeFragmentRequest();
        /*request.setCreatedOn(lsCurrentDate);*/
        request.setReeworkerID(userId);
        request.setReecoachID(recoachId);

        String  submitHistoryDate_mind= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        request.setCreatedOn(submitHistoryDate_mind);
        Call<HomeFragmentResponse> call = service.getInitialData(request);
        call.enqueue(new Callback<HomeFragmentResponse>()
        {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    HomeFragmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mHomeModel = listResponse.getData();

                        if (mHomeModel.getIsBingeOnLargeQuantity() != null){
                            int binge = mHomeModel.getIsBingeOnLargeQuantity();
                            String bingeQuantity = String.valueOf(binge);
                            String mindStatus = mHomeModel.getActualMindStatus();
                            if (mindStatus == null) {
                                mindStatus = "";
                            }
                            if (mindStatus.equalsIgnoreCase("")) {

                                linearLayout_submit.setVisibility(View.VISIBLE);
                                linearLayout_graph.setVisibility(View.GONE);

                                if (Utils.isNetworkAvailable(context)) {
                                    CallToFetchTipsData();

                                } else
                                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();


                            } else {

//                                linearLayout_submit.setVisibility(View.GONE);
//                                linearLayout_graph.setVisibility(View.VISIBLE);
                                linearLayout_submit.setVisibility(View.VISIBLE);
                                linearLayout_graph.setVisibility(View.GONE);


                                if (mHomeModel.getActualMindStatus()!=null){
                                    if (!mHomeModel.getActualMindStatus().isEmpty()){
                                        switch (mHomeModel.getActualMindStatus()){

                                            case "Happy":
                                                radioButtonHappy.setChecked(true);
                                                break;

                                            case "stress":
                                                radioButtonStressed.setChecked(true);
                                                break;

                                            case "neutral":
                                                radioButtonConfused.setChecked(true);
                                                break;
                                        }
                                    }
                                }




                                //Set Bing As per Value
                                if (bingeQuantity.equalsIgnoreCase("2")) {
                                    radioButtonMind_Stressed.setText("Yes");
                                } else if (bingeQuantity.equalsIgnoreCase("1")) {
                                    radioButtonMind_Stressed.setText("No");
                                } else if (bingeQuantity.equalsIgnoreCase("3")) {
                                    radioButtonMind_Stressed.setText("Sometime");
                                }

                                //set Feeling Status
                                if (mindStatus.equalsIgnoreCase("Happy")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.happy_hover);
                                }

                                else if (mindStatus.equalsIgnoreCase("Anxious")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.anxious_hover);
                                } else if (mindStatus.equalsIgnoreCase("stress")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.stressed_hover);
                                }
                                else if (mindStatus.equalsIgnoreCase("Stressed")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.stressed_hover);
                                }

                                else if (mindStatus.equalsIgnoreCase("neutral")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.confused_hover);

                                }

                                if (Utils.isNetworkAvailable(context)) {
                                    getMeonthList();

                                    //  CallToFetchMoodData();
                                } else
                                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                    else
                    {
                        utils.hideProgressbar();
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();
            }
        });
    }


    private void CallToAddMoodData()
    {
        utils.showProgressbar(context);

        AddMoodRequest request = new AddMoodRequest();
        request.setUserId(userId);
        request.setIsBingeOnLargeQuantity(mindValue);
        request.setActualMindStatus(strFeeling);

        Call<MoodUpdateResponce> call = service.addMoodData(request);
        call.enqueue(new Callback<MoodUpdateResponce>()
        {
            @Override
            public void onResponse(Call<MoodUpdateResponce> call, retrofit2.Response<MoodUpdateResponce> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MoodUpdateResponce moodResponse = response.body();

                    if (moodResponse != null && moodResponse.getCode() == 1)
                    {
                        if (moodResponse.getMessage() != null){
                            String bingOnLarge = null,feelingStatus;
                            Toast.makeText(context,moodResponse.getMessage(),Toast.LENGTH_SHORT).show();

//                            callMindHistoryApi(submitFromDate,submitToDate);
                            if(moodResponse.getData()!=null) {


                                //check null values.
                                if (moodResponse.getData().getIsBingeOnLargeQuantity() != null) {
                                    bingOnLarge = String.valueOf(moodResponse.getData().getIsBingeOnLargeQuantity());
                                } else {
                                    bingOnLarge = "";
                                }
                                if (moodResponse.getData().getActualMindStatus() != null) {
                                    feelingStatus = moodResponse.getData().getActualMindStatus();
                                } else {
                                    feelingStatus = "";
                                }
                            }else{
                                bingOnLarge = "";
                                feelingStatus = "";
                            }


                            if(bingOnLarge.equalsIgnoreCase("2")){
                                radioButtonMind_Stressed.setText("Yes");
                            }else if(bingOnLarge.equalsIgnoreCase("1")){
                                radioButtonMind_Stressed.setText("No");
                            }else if(bingOnLarge.equalsIgnoreCase("3")){
                                radioButtonMind_Stressed.setText("Sometime");
                            }

                            //set Feeling Status
                            if(feelingStatus!=null  && feelingStatus.equalsIgnoreCase("Happy")){
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.happy_hover);
                            }else if(feelingStatus!=null  && feelingStatus.equalsIgnoreCase("Anxious")){
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.anxious_hover);
                            }else if(feelingStatus!=null  && feelingStatus.equalsIgnoreCase("stress")){
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.stressed_hover);
                            } else if(feelingStatus!=null  && feelingStatus.equalsIgnoreCase("neutral")){
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.confused_hover);
                            }

//                            toggleLayouts();
                        }

                    }
                    else
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MoodUpdateResponce> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }



    private void getMeonthList() {


        utils.showProgressbar(context);

//        GetAllNotificationRequest request = new GetAllNotificationRequest();
//        request.setUserid(userId);

        Call<ClsCaloriesMonth> call = notificationService.getMoodAnaMonth(userId);

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


                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item_white, tempList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
                            CallToFetchMoodData(tempList.get(0).toString());

                        }else {
                            Toast.makeText(MindAnalysis.this, "No data available", Toast.LENGTH_SHORT).show();
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


    }


    private void CallToFetchTipsData()
    {
        utils.showProgressbar(context);
        Call<TipsResponce> call = service.getTipsData();
        call.enqueue(new Callback<TipsResponce>()
        {
            @Override
            public void onResponse(Call<TipsResponce> call, retrofit2.Response<TipsResponce> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    TipsResponce tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1)
                    {
                        if (tipsResponse.getData() != null)
                        {
                            mDataListTips.clear();
                            mDataListTips_filter=new ArrayList<>();
                            mDataListTips_filter.clear();
                            mDataListTips_filter.addAll(tipsResponse.getData());

                            for (int i = 0; i <mDataListTips_filter.size() ; i++) {

                                if (!mDataListTips_filter.get(i).getMindTips().trim().isEmpty()){
                                    mDataListTips.add(mDataListTips_filter.get(i));
                                }
                            }
//                            if (mDataListTips == null) {
//                                for (int i = 0; i <mDataListTips.size() ; i++) {
//                                    if (!mDataListTips.get(i).getMindTips().trim().isEmpty()){
//
//                                    }
//                                }
//
//                            }


                            HomeFragment.mCommonDataListTips = mDataListTips;
                            tipsAdapter.notifyDataSetChanged();
                            tipsAdapter1.notifyDataSetChanged();
                        }
                    }
                    else
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TipsResponce> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void CallToFetchMoodData(String month)
    {
        utils.showProgressbar(context);

//        WaterRequest request = new WaterRequest();
//        request.setUserID(userId);

        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));

        Call<MoodResponse> call = service.getMoodHistoryData(clsHistoryRequest);
        call.enqueue(new Callback<MoodResponse>()
        {
            @Override
            public void onResponse(Call<MoodResponse> call, retrofit2.Response<MoodResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MoodResponse moodResponse = response.body();

                    if (moodResponse != null && moodResponse.getCode() == 1)
                    {
                        if (moodResponse.getData() != null)
                        {
                            lineChart.clear();
                            lineChart.setData(null);
                            mList.clear();
                            yValueForChart.clear();
                            bingValueForChart.clear();
                            xValuesForChart.clear();
                            mList.addAll(moodResponse.getData());

                            if (mList !=null && mList.size()>0)
                            {
                                for (int i=0; i<mList.size(); i++)
                                {

                                    try {
                                        if (mList.get(i).getIsBingeOnLargeQuantity() == 1) {
                                            bingValueForChart.add(new Entry(i, iNo));
                                        } else if (mList.get(i).getIsBingeOnLargeQuantity() == 2) {
                                            bingValueForChart.add(new Entry(i, iYes));
                                        } else {
                                            bingValueForChart.add(new Entry(i, iSometime));
                                        }

                                        if (mList.get(i).getActualMindStatus() != null){
                                            if (mList.get(i).getActualMindStatus().equalsIgnoreCase("Happy"))
                                                yValueForChart.add(new Entry(i, iHAPPY));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("stress"))
                                                yValueForChart.add(new Entry(i, iSTRESS));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("sad"))
                                                yValueForChart.add(new Entry(i, iSTRESS));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("Anxious"))
                                                yValueForChart.add(new Entry(i, iANXIOUS));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("neutral")){
                                                yValueForChart.add(new Entry(i, iCONFUSED));

                                            }


                                        }
                                        String date = formatDates(mList.get(i).getCreatedOn());
                                        xValuesForChart.add(date);

                                    }catch (Exception e){
                                        e.printStackTrace();

                                    }
                                }

                                if (!yValueForChart.isEmpty()&&!xValuesForChart.isEmpty()){
                                    try {
                                        initChartData(yValueForChart, xValuesForChart);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }

//                                temsunit
                                linearLayout_submit.setVisibility(View.VISIBLE);
                                linearLayout_graph.setVisibility(View.GONE);

                            }
                        }
                    }
                    else
//                        mList.clear();
//                    yValueForChart.clear();
//                    bingValueForChart.clear();
//                    xValuesForChart.clear();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
//                    mList.clear();
//                yValueForChart.clear();
//                bingValueForChart.clear();
//                xValuesForChart.clear();

                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MoodResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("MMM dd");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }



    private void initChartData(final ArrayList<Entry> yData, final ArrayList<String> xData)
    {

        try {
            lineChart.clear();
            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(true);
            lineChart.fitScreen();


       /* LineDataSet set1 = new LineDataSet(yVals1, "Monthly weights");
        //set1.setCircleColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.colorTransparent));*/

            LineDataSet set1 = new LineDataSet(yData, "Feelings");
            set1.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            set1.setLineWidth(2f);
            set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            set1.setCircleColor(getResources().getColor(R.color.colorPrimaryDark));
            set1.setCircleRadius(5);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            //for display bing values
//            ArrayList<Entry> dataset2 = new ArrayList<Entry>();
//            if (!bingValueForChart.isEmpty()) {
//                for (int j = 0; j < bingValueForChart.size(); j++) {
//                    dataset2.add(bingValueForChart.get(j));
//                }
//            }
//
//
//
//            LineDataSet set2 = new LineDataSet(dataset2, "Bing");
//            set2.setColor(ContextCompat.getColor(context, R.color.color_graph_orange));
//            set2.setLineWidth(2f);
//            set2.setValueTextColor(ContextCompat.getColor(context, R.color.white));
//            set2.setCircleColor(getResources().getColor(R.color.color_graph_orange));
//            set2.setCircleRadius(5);
//            dataSets.add(set2);
            // dataSets.add(set2);


            //set center white line
            LimitLine upper_limit = new LimitLine(0f, "");
            upper_limit.setLineWidth(2f);
            upper_limit.setLineColor(context.getResources().getColor(R.color.white));
            upper_limit.enableDashedLine(0f, 0f, 0f);
            upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);


            LineData data = new LineData(dataSets);

            lineChart.setData(data);
            lineChart.setNoDataText("No chart data available");
            lineChart.getLegend().setEnabled(true);
            lineChart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.white));

//            lineChart.setVisibleXRange(5, 5);

            //lineChart.setBackgroundColor(initChartDataContextCompat.getColor(context, R.color.colorPremiumBlack));

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setTextSize(2f);

            if (xData.size()==1){
                xAxis.setLabelCount(xData.size());

            }else if (xData.size()>10){
                xAxis.setLabelCount(10);

            }
            else {
                xAxis.setLabelCount(xData.size(),true);

            }
            //xAxis.setDrawLabels(false); // no axis labels
            xAxis.setTextColor(Color.WHITE); // axis line colour
            xAxis.setDrawAxisLine(true); // no axis line
            xAxis.setAxisLineColor(Color.WHITE); // axis line colour
            xAxis.setDrawGridLines(false); // no grid lines
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis at bottom

            xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount(xData)));

            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//            xAxis.setValueFormatter(new IAxisValueFormatter() {
//                @Override
//                public String getFormattedValue(float value, AxisBase axis) {
//                    if (yData != null) {
//                        if (!yData.isEmpty()) {
//                            for (int i = 0; i < yData.size(); ++i) {

//                                if (yData.get(i).getX() == value) {
//                                    return xData.get(i);
//                                }
//                            }
//                        }
//                    }

//                    return "";
//                }
//            });

      /*  if (yData.size() > 5)
        {
            xAxis.setGranularity(1);
            xAxis.setAxisLineWidth(2); // axis line colour
            xAxis.setLabelCount(5, false);
        }
*/
            YAxis left = lineChart.getAxisLeft();
            left.setTextColor(Color.WHITE); // axis line colour
            left.setDrawAxisLine(true); // no axis line
            left.setAxisLineColor(Color.WHITE); // axis line colour
            left.setDrawGridLines(true); // no grid lines
            //for default 0 position line
            left.addLimitLine(upper_limit);
            left.setDrawLimitLinesBehindData(true);
            left.setDrawZeroLine(false);
            left.setGridColor(ContextCompat.getColor(context, R.color.colorGreyLight2)); // no grid lines
            left.setGridColor(Color.WHITE); // grid line colour

//            if (yData.size() > 5) {
//                left.setAxisLineWidth(2); // axis line colour
//            }

            YAxis right = lineChart.getAxisRight();
            right.setEnabled(true); // no right axis

            Description description = new Description();
            description.setText("");
            lineChart.setDescription(description);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> getAreaCount(ArrayList<String> clsSleepData) {

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < clsSleepData.size(); i++)
            label.add(clsSleepData.get(i).toString());
        return label;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getItemAtPosition(position) != null)
        {
            String monthName = parent.getItemAtPosition(position).toString();

            if (Utils.isNetworkAvailable(context))
            {
//                mList.clear();
//                yValueForChart.clear();
//                bingValueForChart.clear();
//                xValuesForChart.clear();
//
                CallToFetchMoodData(monthName);
//                callGraphApi(userID, monthName);
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
                    CallToFetchMoodData("");

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
        Calendar c1 = Calendar.getInstance();
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
//            img_add_sleep_date=findViewById(R.id.img_add_sleep_date);
            txt_show_sleep_to=findViewById(R.id.txt_show_sleep_to);
            txt_show_sleep_from=findViewById(R.id.txt_show_sleep_from);
            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            dummydate_from = formatDatesSleep(submitFromDate);
            txt_show_sleep_from.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_to.setText(dummydate_to);
//            img_add_sleep_date.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showNewgetFoodhistroy();
//                }
//            });


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
