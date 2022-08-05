package com.shamrock.reework.activity.AnalysisModule.activity.useractivity;


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
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
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
import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.ActivityData;
import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.ClsActivityHistoryPojo;
import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.ClsCustomActivityData;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.CaloryGeneratePDF;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ColoriesAnalysisActivty;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalAdapter;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalPojo;
import com.shamrock.reework.activity.AnalysisModule.activity.food.ActivityvalueFormatter;
import com.shamrock.reework.activity.AnalysisModule.activity.food.ClsCustomFoodNapData;
import com.shamrock.reework.activity.AnalysisModule.activity.food.ActivityvalueFormatter;
import com.shamrock.reework.activity.AnalysisModule.activity.sleep.SleepAnaysisData;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.ClsCustomSleepNapData;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.io.ByteArrayOutputStream;
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

public class ActivityNapMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {


    private ArrayList<Entry> listData = new ArrayList<>();
    private LineChart lineChart;
    private Utils utils;
    private Context context;
    private NotificationService notificationService;
    private SessionManager sessionManager;
    private int userId;
    private Spinner spinner;
    ClsCustomSleepNapData clsCustomSleepNapData;
    ArrayList<ClsCustomFoodNapData> arylst_ClsCustomFoodNapData;
    private  ArrayList<ClsCustomActivityData> clsCustomActivityData=new ArrayList<ClsCustomActivityData>();
    private LinearLayout y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11,y12,y13,y14,y15,y16,y17,y18,y19,y20,y21,y22,y23,y24,y25;
    private LinearLayout y26,y27,y28,y29,y30;
    TextView txt_graph1;
    TextView txt_graph2;
    TextView txt_graph3;
    TextView txt_graph4;
    TextView txt_graph5;
    TextView txt_graph6;
    TextView txt_graph7;
    TextView txt_graph8;
    TextView txt_graph9;
    TextView txt_graph10;
    TextView txt_graph11;
    TextView txt_graph12;
    TextView txt_graph13;
    TextView txt_graph14;
    TextView txt_graph15;
    TextView txt_graph16;
    TextView txt_graph17;
    TextView txt_graph18;
    TextView txt_graph19;
    TextView txt_graph20;
    TextView txt_graph21;
    TextView txt_graph22;
    TextView txt_graph23;
    TextView txt_graph24;
    TextView txt_graph25;
    TextView txt_graph26;
    TextView txt_graph27;
    TextView txt_graph28;
    TextView txt_graph29;
    TextView txt_graph30;



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
    private ArrayList<String> arylst_activity_name_list;

    //New
    LinearLayout parent;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView imgopen,imgclose,img_send;
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
        setContentView(R.layout.activity__nap_analysis);

        initView();
        initgraphtext();
        layout_open = findViewById(R.id.layout_open);
        layout_close = findViewById(R.id.layout_close);
        imgopen = findViewById(R.id.imgopen);
        imgclose = findViewById(R.id.imgclose);
        context= ActivityNapMainActivity.this;;
        sessionManager=new SessionManager(context);
        utils=new Utils();
        lineChart = findViewById(R.id.linegraph);
        lineChart.setVisibility(View.VISIBLE);
//        ImageView imgLeft=findViewById(R.id.imgLeft);
//        imgLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        TextView tvTitle=findViewById(R.id.tvTitle);
//        tvTitle.setText("Activity Discipline");
//
//        boolean  ISFromANnalysis=getIntent().getBooleanExtra("ISFromANnalysis",false);
//        if (!ISFromANnalysis){
//            tvTitle.setText("My Activity");
//        }else {
//            tvTitle.setText("Activity Discipline");
//
//        }
        notificationService = Client.getClient().create(NotificationService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        spinner = findViewById(R.id.spinner_Weight_Months);
        spinner.setOnItemSelectedListener(this);
        getMeonthList();
        showDateWiseData();


        //N̥ew
        parent = findViewById(R.id.parent);
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

//        scaleGestureDetector = new ScaleGestureDetector(this, new ColoriesAnalysisActivty.ScaleListener());

        TextView labelMyProgress = findViewById(R.id.labelMyProgress);
        ImageView img_send = findViewById(R.id.img_send);
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                screenShot(parent, "result");
                showFullScreenDailog(clsCustomActivityData);

//                if (!clsCustomActivityData.isEmpty()){
//                    new UserActivityGeneratePDF(clsCustomActivityData, ActivityNapMainActivity.this,"",arylst_activity_name_list).execute();
//
//                }else {
//                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
//                }
            }
        });


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


                GetAllNotifications();
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



                GetAllNotifications();

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


                GetAllNotifications();

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
            tvTitle.setText("My Activity");
        }else {
            tvTitle.setText("Activity Discipline");

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
    private void showFullScreenDailog(final ArrayList<ClsCustomActivityData> tempList1) {
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
                    if (!tempList1.isEmpty()){


                        Dexter.withActivity(ActivityNapMainActivity.this)
                                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        new UserActivityGeneratePDF(tempList1, ActivityNapMainActivity.this, "", arylst_activity_name_list).execute();


                                    }   @Override
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

    private void initView() {
        y1=findViewById(R.id.y1);
        y2=findViewById(R.id.y2);
        y3=findViewById(R.id.y3);
        y4=findViewById(R.id.y4);
        y5=findViewById(R.id.y5);
        y6=findViewById(R.id.y6);
        y7=findViewById(R.id.y7);
        y8=findViewById(R.id.y8);
        y9=findViewById(R.id.y9);
        y10=findViewById(R.id.y10);
        y11=findViewById(R.id.y11);
        y12=findViewById(R.id.y12);
        y13=findViewById(R.id.y13);
        y14=findViewById(R.id.y14);
        y15=findViewById(R.id.y15);
        y16=findViewById(R.id.y16);
        y17=findViewById(R.id.y17);
        y18=findViewById(R.id.y18);
        y19=findViewById(R.id.y19);
        y20=findViewById(R.id.y20);
        y21=findViewById(R.id.y21);
        y22=findViewById(R.id.y22);
        y23=findViewById(R.id.y23);
        y24=findViewById(R.id.y24);
        y25=findViewById(R.id.y25);
        y26=findViewById(R.id.y26);
        y27=findViewById(R.id.y27);
        y28=findViewById(R.id.y28);
        y29=findViewById(R.id.y29);
        y30=findViewById(R.id.y30);
    }


    private void setGraphData(ArrayList<ClsCustomActivityData> clsCustomActivityData){
        lineChart.setData(null);
        lineChart.invalidate();
        lineChart.clear();
//        final ArrayList<ClsSleepData> clsSleepData = ClsSleepData.getSleepData();
        final ArrayList<Entry> yValues1=new ArrayList<>();
        final ArrayList<Entry> yValues2=new ArrayList<>();
        final ArrayList<Entry> yValues3=new ArrayList<>();
        final ArrayList<Entry> yValues4=new ArrayList<>();
        final ArrayList<Entry> yValues5=new ArrayList<>();
        final ArrayList<Entry> yValues6=new ArrayList<>();
        final ArrayList<Entry> yValues7=new ArrayList<>();
        final ArrayList<Entry> yValues8=new ArrayList<>();
        final ArrayList<Entry> yValues9=new ArrayList<>();
        final ArrayList<Entry> yValues10=new ArrayList<>();
        final ArrayList<Entry> yValues11=new ArrayList<>();
        final ArrayList<Entry> yValues12=new ArrayList<>();
        final ArrayList<Entry> yValues13=new ArrayList<>();
        final ArrayList<Entry> yValues14=new ArrayList<>();
        final ArrayList<Entry> yValues15=new ArrayList<>();
        final ArrayList<Entry> yValues16=new ArrayList<>();
        final ArrayList<Entry> yValues17=new ArrayList<>();
        final ArrayList<Entry> yValues18=new ArrayList<>();
        final ArrayList<Entry> yValues19=new ArrayList<>();
        final ArrayList<Entry> yValues20=new ArrayList<>();
        final ArrayList<Entry> yValues21=new ArrayList<>();
        final ArrayList<Entry> yValues22=new ArrayList<>();
        final ArrayList<Entry> yValues23=new ArrayList<>();
        final ArrayList<Entry> yValues24=new ArrayList<>();
        final ArrayList<Entry> yValues25=new ArrayList<>();
        final ArrayList<Entry> yValues26=new ArrayList<>();
        final ArrayList<Entry> yValues27=new ArrayList<>();
        final ArrayList<Entry> yValues28=new ArrayList<>();
        final ArrayList<Entry> yValues29=new ArrayList<>();
        final ArrayList<Entry> yValues30=new ArrayList<>();
        for (int i = 0; i <clsCustomActivityData.size() ; i++) {

            try {
                if (clsCustomActivityData.get(i).getBICYCLING() != 0) {
                    yValues1.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getBICYCLING()))));
                }


                if (clsCustomActivityData.get(i).getCONDITIONINGEXERCISE() != 0) {
                    yValues2.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getCONDITIONINGEXERCISE()))));
                }

                if (clsCustomActivityData.get(i).getDANCING() != 0) {
                    yValues3.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getDANCING()))));
                }


                if (clsCustomActivityData.get(i).getFISHINGANDHUNTING() != 0) {
                    yValues4.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getFISHINGANDHUNTING()))));
                }

                if (clsCustomActivityData.get(i).getHOMEACTIVITIES() != 0) {
                    yValues5.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getHOMEACTIVITIES()))));
                }

                if (clsCustomActivityData.get(i).getHOMEREPAIR() != 0) {
                    yValues6.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getHOMEREPAIR()))));
                }



                if (clsCustomActivityData.get(i).getINACTIVITYQUIETLIGHT() != 0) {
                    yValues7.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getINACTIVITYQUIETLIGHT()))));
                }

                if (clsCustomActivityData.get(i).getLAWNANDGARDEN() != 0) {
                    yValues8.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getLAWNANDGARDEN()))));
                }

                if (clsCustomActivityData.get(i).getMISCELLANEOUS() != 0) {
                    yValues9.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getMISCELLANEOUS()))));
                }

                if (clsCustomActivityData.get(i).getMUSICPLAYING() != 0) {
                    yValues10.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getMUSICPLAYING()))));
                }

                if (clsCustomActivityData.get(i).getOCCUPATION() != 0) {
                    yValues11.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getOCCUPATION()))));
                }
                if (clsCustomActivityData.get(i).getRELIGIOUSACTIVITIES() != 0) {
                    yValues12.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getRELIGIOUSACTIVITIES()))));
                }

                if (clsCustomActivityData.get(i).getRUNNING() != 0) {
                    yValues13.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getRUNNING()))));
                }


                if (clsCustomActivityData.get(i).getSELFCARE() != 0) {
                    yValues14.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getSELFCARE()))));
                }

                if (clsCustomActivityData.get(i).getSEXUALACTIVITY() != 0) {
                    yValues15.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getSEXUALACTIVITY()))));
                }

                if (clsCustomActivityData.get(i).getSPORTS() != 0) {
                    yValues16.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getSPORTS()))));
                }

                if (clsCustomActivityData.get(i).getTRANSPORTATION() != 0) {
                    yValues17.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getTRANSPORTATION()))));
                }


                if (clsCustomActivityData.get(i).getVOLUNTEERACTIVITIES() != 0) {
                    yValues18.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getVOLUNTEERACTIVITIES()))));
                }


                if (clsCustomActivityData.get(i).getWALKING() != 0) {
                    yValues19.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getWALKING()))));
                }

                if (clsCustomActivityData.get(i).getWATERACTIVITIES() != 0) {
                    yValues20.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getWATERACTIVITIES()))));
                }

                if (clsCustomActivityData.get(i).getActivity21() != 0) {
                    yValues21.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity21()))));
                }

                if (clsCustomActivityData.get(i).getActivity22() != 0) {
                    yValues22.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity22()))));
                }

                if (clsCustomActivityData.get(i).getActivity23() != 0) {
                    yValues23.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity23()))));
                }

                if (clsCustomActivityData.get(i).getActivity24() != 0) {
                    yValues24.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity24()))));
                }
                if (clsCustomActivityData.get(i).getActivity25() != 0) {
                    yValues25.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity25()))));
                }

                if (clsCustomActivityData.get(i).getActivity26() != 0) {
                    yValues26.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity26()))));
                }
                if (clsCustomActivityData.get(i).getActivity27() != 0) {
                    yValues27.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity27()))));
                }

                if (clsCustomActivityData.get(i).getActivity28() != 0) {
                    yValues28.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity28()))));
                }

                if (clsCustomActivityData.get(i).getActivity29() != 0) {
                    yValues29.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity29()))));
                }

                if (clsCustomActivityData.get(i).getActivity30() != 0) {
                    yValues30.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(clsCustomActivityData.get(i).getActivity30()))));
                }






            }catch (Exception e){
                e.printStackTrace();
            }


        }

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();



        lineChart.getLegend().setEnabled(false);
        if (yValues1.size()>0){
            y1.setVisibility(View.VISIBLE);


            LineDataSet setComp1 = new LineDataSet(yValues1, "");
            setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp1.setFillAlpha(110);
            setComp1.setValueFormatter(new ActivityvalueFormatter());

            setComp1.setColor(ContextCompat.getColor
                    (this,R.color.blue_btn_bg_pressed_color));
            setComp1.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp1.setCircleColors(getResources().getColor(R.color.blue_btn_bg_pressed_color));
            setComp1.setCircleRadius(5);

            setComp1.setLineWidth(2f);
            dataSets.add(setComp1);

        }


        if (yValues2.size()>0){
            y2.setVisibility(View.VISIBLE);

            LineDataSet setComp2 = new LineDataSet(yValues2, " Breakfast(hh.mm)");
            setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp2.setValueFormatter(new ActivityvalueFormatter());
            setComp2.setCircleRadius(5);
            setComp2.setColor(ContextCompat.getColor(this,R.color.color_2));
            setComp2.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp2.setCircleColors(getResources().getColor(R.color.color_2));

            setComp2.setLineWidth(2f);
            dataSets.add(setComp2);

        }




        if (yValues3.size()>0){
            y3.setVisibility(View.VISIBLE);

            LineDataSet setComp3 = new LineDataSet(yValues3, "Lunch(hh.mm)");
            setComp3.setValueFormatter(new ActivityvalueFormatter());
            setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp3.setColor(ContextCompat.getColor(this,R.color.color_3));
            setComp3.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp3.setCircleColors(getResources().getColor(R.color.color_3));
            setComp3.setCircleRadius(5);
            setComp3.setLineWidth(2f);
            dataSets.add(setComp3);

        }




        if (yValues4.size()>0){
            y4.setVisibility(View.VISIBLE);

            LineDataSet setComp4 = new LineDataSet(yValues4, "Evening snacks(hh.mm)");
            setComp4.setValueFormatter(new ActivityvalueFormatter());

            setComp4.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp4.setCircleColors(getResources().getColor(R.color.color_4));
            setComp4.setCircleRadius(5);
            setComp4.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp4.setColor(ContextCompat.getColor(this,R.color.color_4));
            setComp4.setLineWidth(2f);
            dataSets.add(setComp4);

        }

        if (yValues5.size()>0){
            y5.setVisibility(View.VISIBLE);

            LineDataSet setComp5 = new LineDataSet(yValues5, "Dinner(hh.mm)");
            setComp5.setValueFormatter(new ActivityvalueFormatter());
            setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp5.setColor(ContextCompat.getColor(this,R.color.color_5));
            setComp5.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp5.setCircleColors(getResources().getColor(R.color.color_5));
            setComp5.setCircleRadius(5);
            setComp5.setLineWidth(2f);
            dataSets.add(setComp5);

        }


        if (yValues6.size()>0){
            y6.setVisibility(View.VISIBLE);

            LineDataSet setComp5 = new LineDataSet(yValues6, "Dessert(hh.mm)");
            setComp5.setValueFormatter(new ActivityvalueFormatter());
            setComp5.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp5.setCircleColors(getResources().getColor(R.color.color_6));
            setComp5.setCircleRadius(5);
            setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp5.setColor(ContextCompat.getColor(this,R.color.color_6));
            setComp5.setLineWidth(2f);
            dataSets.add(setComp5);

        }


        if (yValues7.size()>0){
            y7.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues7, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_7));
            setComp6.setCircleRadius(5);
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_7));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues8.size()>0){
            y8.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues8, "Dessert(hh.mm)");
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_8));
            setComp6.setCircleRadius(5);

            setComp6.setColor(ContextCompat.getColor(this,R.color.color_8));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues9.size()>0){
            y9.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues9, "Dessert(hh.mm)");
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_9));
            setComp6.setCircleRadius(5);

            setComp6.setColor(ContextCompat.getColor(this,R.color.color_9));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues10.size()>0){
            y10.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues10, "Dessert(hh.mm)");
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_10));
            setComp6.setCircleRadius(5);

            setComp6.setColor(ContextCompat.getColor(this,R.color.color_10));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues11.size()>0){
            y11.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues11, "Dessert(hh.mm)");
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_11));
            setComp6.setCircleRadius(5);

            setComp6.setColor(ContextCompat.getColor(this,R.color.color_11));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues12.size()>0){
            y12.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues12, "Dessert(hh.mm)");
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setCircleColors(getResources().getColor(R.color.color_12));
            setComp6.setCircleRadius(5);

            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setColor(ContextCompat.getColor(this,R.color.color_12));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues13.size()>0){
            y13.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues13, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_13));
            setComp6.setCircleRadius(5);

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_13));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }


        if (yValues14.size()>0){
            y14.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues14, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_14));
            setComp6.setCircleRadius(5);

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_14));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues15.size()>0){
            y15.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues15, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleColors(getResources().getColor(R.color.color_15));
            setComp6.setCircleRadius(5);

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_15));
            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues16.size()>0){
            y16.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues16, "Dessert(hh.mm)");
            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
//            setComp6.setCircleColor(R.color.color_16);
//            setComp6.setCircleColorHole(R.color.red_btn_bg_color);
//            setComp6.setCircleHoleRadius(55);
//            setComp6.setCircleRadius(55);
//            setComp6.setDrawCircles(true);
            setComp6.setCircleRadius(5);

            setComp6.setColor(ContextCompat.getColor(this,R.color.color_16));
            setComp6.setCircleColors(getResources().getColor(R.color.color_16));

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues17.size()>0){
            y17.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues17, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleRadius(5);

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_17));
            setComp6.setCircleColors(getResources().getColor(R.color.color_17));

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues18.size()>0){
            y18.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues18, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleRadius(5);

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_18));
            setComp6.setCircleColors(getResources().getColor(R.color.color_18));

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues19.size()>0){
            y19.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues19, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setCircleRadius(5);

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_19));
            setComp6.setCircleColors(getResources().getColor(R.color.color_19));

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues20.size()>0){
            y20.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues20, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_20));
            setComp6.setCircleColors(getResources().getColor(R.color.color_20));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues21.size()>0){
            y21.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues21, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_21));
            setComp6.setCircleColors(getResources().getColor(R.color.color_21));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }
        if (yValues22.size()>0){
            y22.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues22, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_22));
            setComp6.setCircleColors(getResources().getColor(R.color.color_22));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues23.size()>0){
            y23.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues23, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_23));
            setComp6.setCircleColors(getResources().getColor(R.color.color_23));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }


        if (yValues24.size()>0){
            y24.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues24, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_24));
            setComp6.setCircleColors(getResources().getColor(R.color.color_24));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues25.size()>0){
            y25.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues25, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_25));
            setComp6.setCircleColors(getResources().getColor(R.color.color_25));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }


        if (yValues26.size()>0){
            y26.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues26, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_26));
            setComp6.setCircleColors(getResources().getColor(R.color.color_26));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues27.size()>0){
            y27.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues27, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_27));
            setComp6.setCircleColors(getResources().getColor(R.color.color_27));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }

        if (yValues28.size()>0){
            y28.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues28, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_28));
            setComp6.setCircleColors(getResources().getColor(R.color.color_28));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);


        }


        if (yValues29.size()>0){
            y29.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues29, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_29));
            setComp6.setCircleColors(getResources().getColor(R.color.color_29));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }


        if (yValues30.size()>0){
            y30.setVisibility(View.VISIBLE);

            LineDataSet setComp6 = new LineDataSet(yValues30, "Dessert(hh.mm)");
            setComp6.setValueFormatter(new ActivityvalueFormatter());
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp6.setValueTextColor(ContextCompat.getColor(context, R.color.white));

            setComp6.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp6.setColor(ContextCompat.getColor(this,R.color.color_30));
            setComp6.setCircleColors(getResources().getColor(R.color.color_30));
            setComp6.setCircleRadius(5);

            setComp6.setLineWidth(2f);
            dataSets.add(setComp6);

        }







        XAxis xAxis = lineChart.getXAxis();
        if (clsCustomActivityData.size()==1){
            xAxis.setLabelCount(clsCustomActivityData.size());

        }else {
            xAxis.setLabelCount(clsCustomActivityData.size(),true);

        }


        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);


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



        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount(clsCustomActivityData)));


//        lineChart.getXAxis().setLabelCount(5);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate(); // refresh

    }

    private void getMeonthList() {


        utils.showProgressbar(context);


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
                            Toast.makeText(ActivityNapMainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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


        GetAllNotifications();


    }

    private void GetAllNotifications()
    {
        utils.showProgressbar(context);
        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));
        Call<ClsActivityHistoryPojo> call = notificationService.getActivityAnalysis(clsHistoryRequest);

        call.enqueue(new Callback<ClsActivityHistoryPojo>()
        {
            @Override
            public void onResponse(Call<ClsActivityHistoryPojo> call, Response<ClsActivityHistoryPojo> response)
            {
                utils.hideProgressbar();
                lineChart.setVisibility(View.VISIBLE);
                lineChart.setData(null);
                lineChart.invalidate();
                lineChart.clear();

                y1.setVisibility(View.GONE);
                y2.setVisibility(View.GONE);
                y3.setVisibility(View.GONE);
                y4.setVisibility(View.GONE);
                y5.setVisibility(View.GONE);
                y6.setVisibility(View.GONE);
                y7.setVisibility(View.GONE);
                y8.setVisibility(View.GONE);
                y9.setVisibility(View.GONE);
                y10.setVisibility(View.GONE);
                y11.setVisibility(View.GONE);
                y12.setVisibility(View.GONE);
                y13.setVisibility(View.GONE);
                y14.setVisibility(View.GONE);
                y15.setVisibility(View.GONE);
                y16.setVisibility(View.GONE);
                y17.setVisibility(View.GONE);
                y18.setVisibility(View.GONE);
                y19.setVisibility(View.GONE);
                y20.setVisibility(View.GONE);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsActivityHistoryPojo listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        arylst_activity_name_list=listResponse.getActivityList();

                        setDataGraph(arylst_activity_name_list);
                        ArrayList<ActivityData>     tempList = listResponse.getData();
                        clsCustomActivityData=new ArrayList<>();

                        for (int i = 0; i <tempList.size() ; i++) {

                            clsCustomActivityData.add(new ClsCustomActivityData(tempList.get(i).getCreatedOn(),
                                    tempList.get(i).getActivities().getBICYCLING(),
                                    tempList.get(i).getActivities().getCONDITIONINGEXERCISE(),
                                    tempList.get(i).getActivities().getDANCING(),
                                    tempList.get(i).getActivities().getFISHINGANDHUNTING(),
                                    tempList.get(i).getActivities().getHOMEACTIVITIES(),
                                    tempList.get(i).getActivities().getHOMEREPAIR(),
                                    tempList.get(i).getActivities().getINACTIVITYQUIETLIGHT(),
                                    tempList.get(i).getActivities().getLAWNANDGARDEN(),
                                    tempList.get(i).getActivities().getMISCELLANEOUS(),
                                    tempList.get(i).getActivities().getMUSICPLAYING(),
                                    tempList.get(i).getActivities().getOCCUPATION(),
                                    tempList.get(i).getActivities().getRELIGIOUSACTIVITIES(),
                                    tempList.get(i).getActivities().getRUNNING(),
                                    tempList.get(i).getActivities().getSELFCARE(),
                                    tempList.get(i).getActivities().getSEXUALACTIVITY(),
                                    tempList.get(i).getActivities().getSPORTS(),
                                    tempList.get(i).getActivities().getTRANSPORTATION(),
                                    tempList.get(i).getActivities().getVOLUNTEERACTIVITIES(),
                                    tempList.get(i).getActivities().getWALKING(),
                                    tempList.get(i).getActivities().getWATERACTIVITIES(),
                                    tempList.get(i).getActivities().getActivity21(),
                                    tempList.get(i).getActivities().getActivity22(),
                                    tempList.get(i).getActivities().getActivity23(),
                                    tempList.get(i).getActivities().getActivity24(),
                                    tempList.get(i).getActivities().getActivity25(),
                                    tempList.get(i).getActivities().getActivity26(),
                                    tempList.get(i).getActivities().getActivity27(),
                                    tempList.get(i).getActivities().getActivity28(),
                                    tempList.get(i).getActivities().getActivity29(),
                                    tempList.get(i).getActivities().getActivity30()


                            ));

                        }
                        ArrayList<SleepAnaysisData> test=new ArrayList<>();
                        if (tempList != null && tempList.size() > 0)
                        {
                            lineChart.clear();
                            lineChart.setData(null);
                            lineChart.highlightValue(null);
                            lineChart.invalidate();
                            setGraphData(clsCustomActivityData);

                        }else {
                            lineChart.setData(null);
                            lineChart.invalidate();
                            lineChart.clear();


                            Toast.makeText(ActivityNapMainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        lineChart.clear();
                        lineChart.setData(null);
                        lineChart.highlightValue(null);
                        lineChart.invalidate();
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }
            @Override
            public void onFailure(Call<ClsActivityHistoryPojo> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }

    private void setDataGraph(ArrayList<String> arylst_activity_name_list) {

        try{

            txt_graph1.setText(arylst_activity_name_list.get(0).toString());
            txt_graph2.setText(arylst_activity_name_list.get(1).toString());
            txt_graph3.setText(arylst_activity_name_list.get(2).toString());
            txt_graph4.setText(arylst_activity_name_list.get(3).toString());
            txt_graph5.setText(arylst_activity_name_list.get(4).toString());
            txt_graph6.setText(arylst_activity_name_list.get(5).toString());
            txt_graph7.setText(arylst_activity_name_list.get(6).toString());
            txt_graph8.setText(arylst_activity_name_list.get(7).toString());
            txt_graph9.setText(arylst_activity_name_list.get(8).toString());
            txt_graph10.setText(arylst_activity_name_list.get(9).toString());
            txt_graph11.setText(arylst_activity_name_list.get(10).toString());
            txt_graph12.setText(arylst_activity_name_list.get(11).toString());
            txt_graph13.setText(arylst_activity_name_list.get(12).toString());
            txt_graph14.setText(arylst_activity_name_list.get(13).toString());
            txt_graph15.setText(arylst_activity_name_list.get(14).toString());
            txt_graph16.setText(arylst_activity_name_list.get(15).toString());
            txt_graph17.setText(arylst_activity_name_list.get(16).toString());
            txt_graph18.setText(arylst_activity_name_list.get(17).toString());
            txt_graph19.setText(arylst_activity_name_list.get(18).toString());
            txt_graph20.setText(arylst_activity_name_list.get(19).toString());
            txt_graph21.setText(arylst_activity_name_list.get(20).toString());
            txt_graph22.setText(arylst_activity_name_list.get(21).toString());
            txt_graph23.setText(arylst_activity_name_list.get(22).toString());
            txt_graph24.setText(arylst_activity_name_list.get(23).toString());
            txt_graph25.setText(arylst_activity_name_list.get(24).toString());
            txt_graph26.setText(arylst_activity_name_list.get(25).toString());
            txt_graph27.setText(arylst_activity_name_list.get(26).toString());
            txt_graph28.setText(arylst_activity_name_list.get(27).toString());
            txt_graph29.setText(arylst_activity_name_list.get(28).toString());
            txt_graph30.setText(arylst_activity_name_list.get(29).toString());

        }catch (Exception e){

        }

    }


    public ArrayList<String> getAreaCount(ArrayList<ClsCustomActivityData> clsSleepData) {

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
                GetAllNotifications();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    GetAllNotifications();

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


    }
    private void showDateWiseData() {
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
                dummydate_from = dayOfMonth + "-" +(month + 1) + "-" + year;

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
    public void initgraphtext(){
        txt_graph1=findViewById(R.id.txt_graph1);
        txt_graph2=findViewById(R.id.txt_graph2);
        txt_graph3=findViewById(R.id.txt_graph3);
        txt_graph4=findViewById(R.id.txt_graph4);
        txt_graph5=findViewById(R.id.txt_graph5);
        txt_graph6=findViewById(R.id.txt_graph6);
        txt_graph7=findViewById(R.id.txt_graph7);
        txt_graph8=findViewById(R.id.txt_graph8);
        txt_graph9=findViewById(R.id.txt_graph9);
        txt_graph10=findViewById(R.id.txt_graph10);
        txt_graph11=findViewById(R.id.txt_graph11);
        txt_graph12=findViewById(R.id.txt_graph12);
        txt_graph13=findViewById(R.id.txt_graph13);
        txt_graph14=findViewById(R.id.txt_graph14);
        txt_graph15=findViewById(R.id.txt_graph15);
        txt_graph16=findViewById(R.id.txt_graph16);
        txt_graph17=findViewById(R.id.txt_graph17);
        txt_graph18=findViewById(R.id.txt_graph18);
        txt_graph19=findViewById(R.id.txt_graph19);
        txt_graph20=findViewById(R.id.txt_graph20);
        txt_graph21=findViewById(R.id.txt_graph21);
        txt_graph22=findViewById(R.id.txt_graph22);
        txt_graph23=findViewById(R.id.txt_graph23);
        txt_graph24=findViewById(R.id.txt_graph24);
        txt_graph25=findViewById(R.id.txt_graph25);
        txt_graph26=findViewById(R.id.txt_graph26);
        txt_graph27=findViewById(R.id.txt_graph27);
        txt_graph28=findViewById(R.id.txt_graph28);
        txt_graph29=findViewById(R.id.txt_graph29);
        txt_graph30=findViewById(R.id.txt_graph30);
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
