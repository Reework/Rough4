package com.shamrock.reework.activity.AnalysisModule.activity.sleepNap;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.RadioButton;
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
import com.shamrock.reework.activity.AnalysisModule.activity.food.FoodvalueFormatter;
import com.shamrock.reework.activity.AnalysisModule.activity.sleep.SleepNapGeneratePDF;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterData;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterGeneratePDF;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
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

public class SleepNapMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener {


    private LineChart lineChart;
    private Utils utils;
    private Context context;
    private NotificationService notificationService;
    private SessionManager sessionManager;
    private int userId;
    private Spinner spinner;
    ArrayList<ClsCustomSleepNapData> arylst_ClsCustomSleepNapData =new ArrayList<ClsCustomSleepNapData>();
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
    RadioButton rd_button_sleep_nap;
    RadioButton rd_button_sleep_quta;
    LinearLayout ll_sleep_header;

    //New
    LinearLayout parent;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView imgopen,imgclose;
    LinearLayout layout_open,layout_close;

    Button btn_weekly, btn_3months, btn_6months;

    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_nap_analysis);
        context=SleepNapMainActivity.this;;
        sessionManager=new SessionManager(context);
        rd_button_sleep_quta=findViewById(R.id.rd_button_sleep_quta);
        rd_button_sleep_nap=findViewById(R.id.rd_button_sleep_nap);
        ll_sleep_header=findViewById(R.id.ll_sleep_header);

        layout_open = findViewById(R.id.layout_open);
        layout_close = findViewById(R.id.layout_close);
        imgopen = findViewById(R.id.imgopen);
        imgclose = findViewById(R.id.imgclose);

        boolean ISFromANnalysis = getIntent().getBooleanExtra("ISFromANnalysis", false);
        if (ISFromANnalysis){
            ll_sleep_header.setVisibility(View.GONE);
        }else {
            ll_sleep_header.setVisibility(View.VISIBLE);

            rd_button_sleep_nap.performClick();

            rd_button_sleep_quta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SleepNapMainActivity.this, SleepAnalysisActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            });
        }

        utils=new Utils();
        showDateWiseData();

        lineChart = findViewById(R.id.linegraph);
        lineChart.setVisibility(View.VISIBLE);

        notificationService = Client.getClient().create(NotificationService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        spinner = findViewById(R.id.spinner_Weight_Months);
        ImageView imgLeft=findViewById(R.id.imgLeft);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle=findViewById(R.id.tvTitle);
        if (ISFromANnalysis){
            tvTitle.setText("Sleep Discipline");

        }else {
            tvTitle.setText(" Sleep ");

        }
        getMeonthList();
        spinner.setOnItemSelectedListener(this);
        getMeonthList();

        //N̥ew
        parent = findViewById(R.id.parent);
//        scaleGestureDetector = new ScaleGestureDetector(this, new ColoriesAnalysisActivty.ScaleListener());

        TextView labelMyProgress = findViewById(R.id.labelMyProgress);
        ImageView img_send = findViewById(R.id.img_send);

        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                screenShot(parent, "result");
                showFullScreenDailog(arylst_ClsCustomSleepNapData);
//                if (!arylst_ClsCustomSleepNapData.isEmpty()){
//                    new SleepNapGeneratePDF(arylst_ClsCustomSleepNapData, SleepNapMainActivity.this,"").execute();
//
//                }else {
//                    Toast.makeText(SleepNapMainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
//
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


        String abc = sessionManager.getStringValue("Allsleep");

        if (abc.equals("select")) {
            btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.green_recipe));

            btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
            ll_sleep_date.setTextColor(getResources().getColor(R.color.white_recipe));


            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            dummydate_from = formatDatesSleep(submitFromDate);

            dummydate_to = formatDatesSleep(submitToDate);
            getMeonthList();


        }else  if (abc.equals("weekly")) {
            btn_weekly.setBackgroundColor(getResources().getColor(R.color.green_recipe));
            btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

            btn_weekly.setTextColor(getResources().getColor(R.color.white_recipe));
            btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
            ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));


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
            GetAllNotifications("");


        }else  if (abc.equals("3months")) {
            btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_3months.setBackgroundColor(getResources().getColor(R.color.green_recipe));
            btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

            btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_3months.setTextColor(getResources().getColor(R.color.white_recipe));
            btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
            ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));


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

            GetAllNotifications("");


        }else  if (abc.equals("6months")) {
            btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_6months.setBackgroundColor(getResources().getColor(R.color.green_recipe));
            ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

            btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_6months.setTextColor(getResources().getColor(R.color.white_recipe));
            ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));

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

            GetAllNotifications("");


        }





//        btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//        btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//        btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//        ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//
//        btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
//        btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
//        btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
//        ll_sleep_date.setTextColor(getResources().getColor(R.color.white_recipe));

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

                session = new SessionManager(context);
                session.setStringValue("Allsleep","select");

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
                GetAllNotifications("");



                btn_weekly.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));


                session = new SessionManager(context);
                session.setStringValue("Allsleep","weekly");

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

                GetAllNotifications("");

                btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));
                session = new SessionManager(context);
                session.setStringValue("Allsleep","3months");

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

                GetAllNotifications("");

                btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_6months.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_6months.setTextColor(getResources().getColor(R.color.white_recipe));
                ll_sleep_date.setTextColor(getResources().getColor(R.color.black_recipe));

                session = new SessionManager(context);
                session.setStringValue("Allsleep","6months");

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
    private void showFullScreenDailog(final ArrayList<ClsCustomSleepNapData> tempList1) {
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
                    if (!arylst_ClsCustomSleepNapData.isEmpty()){

                        Dexter.withActivity(SleepNapMainActivity.this)
                                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        new SleepNapGeneratePDF(arylst_ClsCustomSleepNapData, SleepNapMainActivity.this, "").execute();


                                    }    @Override
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
                        Toast.makeText(SleepNapMainActivity.this, "No data available", Toast.LENGTH_SHORT).show();

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


    private void setGraphData(ArrayList<ClsCustomSleepNapData> arylst_ClsCustomSleepNapData){
        lineChart.setData(null);
        lineChart.invalidate();
        lineChart.clear();
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.getLegend().setEnabled(true);
        lineChart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.white));
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();
        ArrayList<Entry> y3 = new ArrayList<>();
        ArrayList<Entry> y4 = new ArrayList<>();
        ArrayList<Entry> y5 = new ArrayList<>();

        for (int i = 0; i < arylst_ClsCustomSleepNapData.size(); i++) {
            try {
                if (arylst_ClsCustomSleepNapData.get(i).getNap1() != 0) {
                    y1.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomSleepNapData.get(i).getNap1()))));
                }
                if (arylst_ClsCustomSleepNapData.get(i).getNap2() != 0) {
                    y2.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomSleepNapData.get(i).getNap2()))));
                }
                if (arylst_ClsCustomSleepNapData.get(i).getNap3() != 0) {
                    y3.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomSleepNapData.get(i).getNap3()))));
                }

                if (arylst_ClsCustomSleepNapData.get(i).getNap4() != 0) {
                    y4.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomSleepNapData.get(i).getNap4()))));
                }

                if (arylst_ClsCustomSleepNapData.get(i).getNap5() != 0) {
                    y5.add(new Entry(Float.parseFloat(String.valueOf(i)), Float.parseFloat(String.valueOf(arylst_ClsCustomSleepNapData.get(i).getNap5()))));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();


        if (y1.size()>0){

            LineDataSet setComp1 = new LineDataSet(y1, "Nap1");
            setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp1.setFillAlpha(110);
            setComp1.setColor(ContextCompat.getColor(this,R.color.color_5));
            setComp1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp1.setCircleColors(getResources().getColor(R.color.color_5));
            setComp1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp1.setValueFormatter(new FoodvalueFormatter());

            setComp1.setLineWidth(2f);
            dataSets.add(setComp1);

        }


        if (y2.size()>0){
            LineDataSet setComp2 = new LineDataSet(y2, "Nap2");
            setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp2.setColor(ContextCompat.getColor(this,R.color.color_4));
            setComp2.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp2.setValueFormatter(new FoodvalueFormatter());

            setComp2.setCircleColors(getResources().getColor(R.color.color_4));

            setComp2.setLineWidth(2f);
            dataSets.add(setComp2);

        }




        if (y3.size()>0){
            LineDataSet setComp3 = new LineDataSet(y3, "Nap3");
            setComp3.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp3.setColor(ContextCompat.getColor(this,R.color.color_3));
            setComp3.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp3.setCircleColors(getResources().getColor(R.color.color_3));
            setComp3.setValueFormatter(new FoodvalueFormatter());

            setComp3.setLineWidth(2f);
            dataSets.add(setComp3);

        }




        if (y4.size()>0){
            LineDataSet setComp4 = new LineDataSet(y4, "Nap4");
            setComp4.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp4.setColor(ContextCompat.getColor(this,R.color.color_2));
            setComp4.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp4.setCircleColors(getResources().getColor(R.color.color_2));
            setComp4.setValueFormatter(new FoodvalueFormatter());

            setComp4.setLineWidth(2f);
            dataSets.add(setComp4);

        }

        if (y5.size()>0){

            LineDataSet setComp5 = new LineDataSet(y5, "Nap5");
            setComp5.setAxisDependency(YAxis.AxisDependency.LEFT);
            setComp5.setColor(ContextCompat.getColor(this,R.color.color_1));
            setComp5.setValueTextColor(ContextCompat.getColor(context, R.color.white));
            setComp5.setCircleColors(getResources().getColor(R.color.color_1));
            setComp5.setValueFormatter(new FoodvalueFormatter());

            setComp5.setLineWidth(2f);
            dataSets.add(setComp5);

        }

        if (dataSets.isEmpty()){
            lineChart.setData(null);
            lineChart.invalidate();
            lineChart.clear();
        }




        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxis.setDrawGridLines(false);
        //xAxis.setAvoidFirstLastClipping(true);

        xAxis.setTextColor(Color.parseColor("#000000")); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setTextSize(2f);

        //YAxis right = lineChart.getAxisRight();
        // right.setEnabled(false); // no right axis

        //xAxis.setLabelCount(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxi

        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount(arylst_ClsCustomSleepNapData)));




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
//        lineChart.setVisibleXRangeMaximum(5);
        if (arylst_ClsCustomSleepNapData.size()==1){
            lineChart.getXAxis().setLabelCount(arylst_ClsCustomSleepNapData.size());

        }else if (arylst_ClsCustomSleepNapData.size()>10){
            lineChart.getXAxis().setLabelCount(10);

        }

        else {
            lineChart.getXAxis().setLabelCount(arylst_ClsCustomSleepNapData.size(),true);

        }
        lineChart.setData(data);
        lineChart.invalidate(); // refresh

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
                            Toast.makeText(SleepNapMainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
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
        Call<ClsSleepNap> call = notificationService.getSleepNapAnalysis(clsHistoryRequest);

        call.enqueue(new Callback<ClsSleepNap>()
        {
            @Override
            public void onResponse(Call<ClsSleepNap> call, Response<ClsSleepNap> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsSleepNap listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        ArrayList<SleepNapData>       tempList = listResponse.getData();
//                        ArrayList<ClsCustomActivityData> clsCustomActivityData=new ArrayList<>();
                        arylst_ClsCustomSleepNapData=new ArrayList<>();
                        for (int i = 0; i <tempList.size() ; i++) {

                            arylst_ClsCustomSleepNapData.add(new ClsCustomSleepNapData(tempList.get(i).getCreatedOn(),tempList.get(i).getNaps().getNap1(),
                                    tempList.get(i).getNaps().getNap2(),tempList.get(i).getNaps().getNap3(),
                                    tempList.get(i).getNaps().getNap4(),tempList.get(i).getNaps().getNap5(),
                                    tempList.get(i).getNaps().getNap6(),tempList.get(i).getNaps().getNap7(),
                                    tempList.get(i).getNaps().getNap8()
                                    ,tempList.get(i).getNaps().getNap9(),tempList.get(i).getNaps().getNap10()
                            ));

                        }


                        if (!arylst_ClsCustomSleepNapData.isEmpty()){
                            setGraphData(arylst_ClsCustomSleepNapData);

                        }else {
                            lineChart.setData(null);
                            lineChart.invalidate();
                            lineChart.clear();
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
            public void onFailure(Call<ClsSleepNap> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }


    public ArrayList<String> getAreaCount(ArrayList<ClsCustomSleepNapData> clsSleepData) {

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
                dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1)+ "-" + year;

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
}
