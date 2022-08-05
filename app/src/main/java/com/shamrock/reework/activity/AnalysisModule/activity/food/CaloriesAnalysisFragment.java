package com.shamrock.reework.activity.AnalysisModule.activity.food;

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

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.CaloryGeneratePDF;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ColoriesAnalysisActivty;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Data;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalAdapter;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ShareCalPojo;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.adapter.NotificationAdapter;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaloriesAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaloriesAnalysisFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    Context context;
    Toolbar toolbar;
    Utils utils;
    SessionManager sessionManager;
    NotificationService notificationService;
    ImageView img1;
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
    TextView labelMyProgress;
    Button ll_sleep_date;

    int view = R.layout.activity_colories_analysis_activty;

    //showdatedata
    private TextView txt_show_sleep_to, txt_show_sleep_from;
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

    //New
    LinearLayout parent;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView imgopen, imgclose, img_send;
    LinearLayout layout_open, layout_close;
    final ArrayList<Data> tempList1 = new ArrayList<Data>();

//    Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
//    final int orientation = display.getOrientation();

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int mNotifcationCount = 0;
//    NotificationService notificationService;


    Button btn_weekly, btn_3months, btn_6months;
    private SessionManager session;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CaloriesAnalysisFragment() {
        // Required empty public constructor
    }

    public static CaloriesAnalysisFragment newInstance(String param1, String param2) {
        CaloriesAnalysisFragment fragment = new CaloriesAnalysisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
//            scaleGestureDetector = new ScaleGestureDetector(getActivity(), new CaloriesAnalysisFragment.ScaleListener());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_calories_analysis, container, false);


        context = getContext();
        com.github.mikephil.charting.utils.Utils.init(context);
        layout_open = view.findViewById(R.id.layout_open);
        layout_close = view.findViewById(R.id.layout_close);
        imgopen = view.findViewById(R.id.imgopen);
        imgclose = view.findViewById(R.id.imgclose);
        img_send = view.findViewById(R.id.img_send);
//        spinner = view.findViewById(R.id.spinner_Weight_Months);
//        spinner.setOnItemSelectedListener(CaloriesAnalysisFragment.this);
        linegraph = view.findViewById(R.id.linegraph);
        ll_sleep_date = view.findViewById(R.id.btn_selectdate);
//        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showNewgetFoodhistroy();
//
//            }
//        });
        labelMyProgress = view.findViewById(R.id.labelMyProgress);
//        ImageView imgLeft = findViewById(R.id.imgLeft);
//        imgLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        TextView tvTitle = findViewById(R.id.tvTitle);
//        tvTitle.setText("Calories Consumption");
        utils = new Utils();
        sessionManager = new SessionManager(context);
        notificationService = Client.getClient().create(NotificationService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        loginService = Client.getClient().create(LoginService.class);

        //N̥ew
        parent = view.findViewById(R.id.parent);
//        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new CaloriesAnalysisFragment.ScaleListener());

//        layout_open.setVisibility(View.VISIBLE);

        imgopen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {
//                if(ColoriesAnalysisActivty.this.getResources().getConfiguration().orientation==1){
////                    Toast.makeText(getApplicationContext(),"potrest",Toast.LENGTH_LONG).show();
//                }
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                if (layout_open.getVisibility() == View.VISIBLE) {
                    layout_close.setVisibility(View.VISIBLE);
                    layout_open.setVisibility(View.GONE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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
                if (layout_close.getVisibility() == View.VISIBLE) {
                    layout_close.setVisibility(View.GONE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    layout_open.setVisibility(View.VISIBLE);

                }
            }
        });

        txt_show_sleep_to = view.findViewById(R.id.txt_show_sleep_to);
        txt_show_sleep_from = view.findViewById(R.id.txt_show_sleep_from);


        btn_weekly = view.findViewById(R.id.btn_weekly);
        btn_3months = view.findViewById(R.id.btn_3months);
        btn_6months = view.findViewById(R.id.btn_6months);


        String abc = sessionManager.getStringValue("Allanay");

        if (abc.equals("select")) {
            btn_weekly.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_3months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            btn_6months.setBackgroundColor(getResources().getColor(R.color.white_recipe));
            ll_sleep_date.setBackgroundColor(getResources().getColor(R.color.green_recipe));

            btn_weekly.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_3months.setTextColor(getResources().getColor(R.color.black_recipe));
            btn_6months.setTextColor(getResources().getColor(R.color.black_recipe));
            ll_sleep_date.setTextColor(getResources().getColor(R.color.white_recipe));

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

                session = new SessionManager(context);
                session.setStringValue("Allanay","select");


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

                session = new SessionManager(context);
                session.setStringValue("Allanay","weekly");


                GetAllNotifications("");
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

                session = new SessionManager(context);
                session.setStringValue("Allanay","3months");

                GetAllNotifications("");

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

                session = new SessionManager(context);
                session.setStringValue("Allanay","6months");

                GetAllNotifications("");

            }
        });





        showDateWiseData();
        getMeonthList();


        return view;


    }


//    private void setToolBar()
//    {
//        TextView tvTitle = view.findViewById(R.id.tvTitle);
//        ImageView imgLeft = view.findViewById(R.id.imgLeft);
//        tvNotificationCOunt = (TextView) findViewById(R.id.count);
//        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
//        setSupportActionBar(toolbar);
//        imgLeft.setImageResource(R.drawable.back_arrow);
//        tvTitle.setText("Calories Consumption");
//
//        imgLeft.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                getActivity().finish();
//            }
//        });
//        counterValuePanel.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                startActivityForResult(new Intent(context, NotificationsActivity.class), 500);
//
//            }
//        });
//
//
//
//        if (tvNotificationCOunt != null) {
//            if (mNotifcationCount == 0)
//                tvNotificationCOunt.setVisibility(View.GONE);
//            else {
//                tvNotificationCOunt.setVisibility(View.VISIBLE);
//                tvNotificationCOunt.setText("" + mNotifcationCount);
//            }
//        }
//    }


//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//
//        savedInstanceState.putBoolean("MyBoolean", true);
//        savedInstanceState.putDouble("myDouble", 1.9);
//        savedInstanceState.putInt("MyInt", 1);
//        savedInstanceState.putString("MyString", "Welcome back to Android");
//        Bundle args = new Bundle();
//        args.putSerializable("ARRAYLIST",(Serializable)tempList1);
//        savedInstanceState.putBundle("Mylist",args);
//        // etc.
//
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        super.onRestoreInstanceState(savedInstanceState);
//
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//
//        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
//        double myDouble = savedInstanceState.getDouble("myDouble");
//        int myInt = savedInstanceState.getInt("MyInt");
//        String myString = savedInstanceState.getString("MyString");
//        Bundle args = savedInstanceState.getBundle("Mylist");
//        ArrayList<Data> object = (ArrayList<Data>) args.getSerializable("ARRAYLIST");
//
//        Toast.makeText(getApplicationContext(),""+myBoolean+myDouble+myInt+myString,Toast.LENGTH_LONG).show();
//        setData(object);
//    }


//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        scaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//
//            parent.setScaleX(mScaleFactor);
//            parent.setScaleY(mScaleFactor);
//            return true;
//        }
//    }

    private void showDatePickerHistoryAddDailog() {
        String strMindate[] = new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_history = new DatePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, CaloriesAnalysisFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
//        Calendar c = Calendar.getInstance();
//        if (strMindate.length>1){
//            c.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1]),Integer.parseInt(strMindate[0]));
//
//        }
//        c.setTime(today);
//        c.add(Calendar.MONTH, 3);
//        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());
        datepickerdialog_history.getDatePicker().setMaxDate(System.currentTimeMillis());
//        Calendar c1 = Calendar.getInstance();
//        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
//            if (strMindate.length>1){
//                c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
//
//            }
//
//        }
//        datepickerdialog_history.getDatePicker().setMinDate(c1.getTimeInMillis());
        datepickerdialog_history.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog_history.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog_history.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }


    private void getMeonthList() {


        utils.showProgressbar(context);

//        GetAllNotificationRequest request = new GetAllNotificationRequest();
//        request.setUserid(userId);

        Call<ClsCaloriesMonth> call = notificationService.getCaloriesMonth(userId);

        call.enqueue(new Callback<ClsCaloriesMonth>() {
            @Override
            public void onResponse(Call<ClsCaloriesMonth> call, Response<ClsCaloriesMonth> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsCaloriesMonth listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        ArrayList<String> tempList = listResponse.getData();
                        ArrayList<Data> test = new ArrayList<>();


                        if (tempList != null && tempList.size() > 0) {
                            setmonthData(tempList);

                        } else {
                            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    } else {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }

            @Override
            public void onFailure(Call<ClsCaloriesMonth> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });

    }

    private void setmonthData(ArrayList<String> tempList) {

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item_white, tempList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);


        GetAllNotifications(tempList.get(0).toString());


    }

    private void GetAllNotifications(String monthName) {
        utils.showProgressbar(context);

        ClsHistoryRequest clsHistoryRequest = new ClsHistoryRequest();
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));


        Call<ClsCalorianalysis> call = notificationService.getcaloriesHistory(clsHistoryRequest);

        call.enqueue(new Callback<ClsCalorianalysis>() {
            @Override
            public void onResponse(Call<ClsCalorianalysis> call, Response<ClsCalorianalysis> response) {
                utils.hideProgressbar();
                linegraph.setVisibility(View.VISIBLE);
                linegraph.clear();
                linegraph.setData(null);
                linegraph.highlightValue(null);
                linegraph.invalidate();

                if (response.code() == Client.RESPONSE_CODE_OK) {

                    ClsCalorianalysis listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        final ArrayList<Data> tempList = listResponse.getData();
                        ArrayList<Data> test = new ArrayList<>();

                        for (int i = 0; i < tempList.size(); i++) {
                            if (!tempList.get(i).getBruntCalories().equals("0.0")) {

                                tempList1.add(tempList.get(i));

                            }

                        }


                        img_send.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("SourceLockedOrientationActivity")
                            @Override
                            public void onClick(View v) {

//                                screenShot(parent, "result");
                                showFullScreenDailog(tempList1);
//                                if (!tempList1.isEmpty()) {
//
//                                    new CaloryGeneratePDF(tempList1, ColoriesAnalysisActivty.this).execute();
//
////                                    screenShot(parent, "result");
//
//                                } else {
//                                    Toast.makeText(ColoriesAnalysisActivty.this, "No data available", Toast.LENGTH_SHORT).show();
//                                }

                            }
                        });

                        if (tempList1 != null && tempList1.size() > 0) {

                            linegraph.clear();
                            linegraph.setData(null);
                            linegraph.highlightValue(null);
                            linegraph.invalidate();
                            if (tempList1.size() != 0)
                                setData(tempList1);

                        } else {
                            isFirrstime = true;
                            if (isFirrstime) {
                                isFirrstime = false;

                            } else {
                                linegraph.clear();
                                linegraph.setData(null);
                                linegraph.highlightValue(null);
                                linegraph.invalidate();
                            }

                            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
//                            finish();
                        }
                    } else {
                        linegraph.clear();
                        linegraph.setData(null);
                        linegraph.highlightValue(null);
                        linegraph.invalidate();
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
                }
            }

            @Override
            public void onFailure(Call<ClsCalorianalysis> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("NotificationsActivity", t.toString());
            }
        });
    }


    //New

    private void screenShot(View view, String fileName) {

        View v1 = parent.getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bm = v1.getDrawingCache();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);
//        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bm, "title", null);
//        Uri bitmapUri = Uri.parse(bitmapPath);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bm, "IMG_" + Calendar.getInstance().getTime(), null);
        Uri bitmapUri = Uri.parse(path);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(bitmapUri)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }


    private void showFullScreenDailog(final ArrayList<Data> tempList1) {
//        final ArrayList<ShareCalPojo> data =new ArrayList<ShareCalPojo>();
        ShareCalPojo data = new ShareCalPojo();
        ShareCalPojo data0 = new ShareCalPojo();

        data.setId(1);
        data.setName("Share As PDF");
        data0.setId(2);
        data0.setName("Share As Image");
        final ArrayList<ShareCalPojo> data1 = new ArrayList<ShareCalPojo>();
        data1.add(data);
        data1.add(data0);
        final Dialog dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.lay_show_device_list);
        ListView lst_device_list = dialog.findViewById(R.id.lst_device_list);
        TextView txt_header = dialog.findViewById(R.id.txt_header);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txt_header.setText("Share Option");
        ShareCalAdapter shareCalAdapter = new ShareCalAdapter(context, data1);
        lst_device_list.setAdapter(shareCalAdapter);

        lst_device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                switch(orientation) {
//                    case Configuration.ORIENTATION_PORTRAIT:
//                        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//                        break;
//                    case Configuration.ORIENTATION_LANDSCAPE:
//                        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
//                        break;
//                }

//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                if (data1.get(position).getId() == 1) {
//                    openFitBit();
                    dialog.dismiss();
                    if (!tempList1.isEmpty()) {


                        Dexter.withActivity(getActivity())
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        new CaloryGeneratePDF(tempList1, context).execute();

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


                    } else {
                        Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else if (data1.get(position).getId() == 2) {
                    dialog.dismiss();
                    screenShot(parent, "result");
//                    Intent intent =new Intent(getActivity(), GfitHomeActivity.class);
//                    startActivity(intent);
                } else {
                    Toast.makeText(context, "This device is not linked with app", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void setData(final ArrayList<Data> aryCalories) {


        final ArrayList<Entry> yValues1 = new ArrayList<>();
        ArrayList<Entry> yValues2 = new ArrayList<>();
        ArrayList<Entry> yValues3 = new ArrayList<>();
        ArrayList<Entry> yValues4 = new ArrayList<>();

        for (int i = 0; i < aryCalories.size(); i++) {
            yValues1.add(new Entry(i, Float.parseFloat(aryCalories.get(i).getBruntCalories())));
//            yValues2.add(new Entry(i,Float.parseFloat(aryCalories.get(i).getConsumedCalories())));
//            yValues3.add(new Entry(i,Float.parseFloat(aryCalories.get(i).getNetCalories())));
            yValues4.add(new Entry(i, Float.parseFloat(aryCalories.get(i).getScheduledCalories())));

        }

        linegraph.setVisibility(View.VISIBLE);
        linegraph.getLegend().setEnabled(true);
        linegraph.getLegend().setTextColor(ContextCompat.getColor(context, R.color.white));
        LineDataSet set1, set2, set3, set4;
        set1 = new LineDataSet(yValues1, "Burnt(kcal)");
        set1.setFillAlpha(110);
        set1.setColor(ContextCompat.getColor(context, R.color.mobikwik_orange));
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        set1.setCircleColor(getResources().getColor(R.color.mobikwik_orange));
//        set1.setCircleRadius(5);


        set1.setLineWidth(2f);

//        set2=new LineDataSet(yValues2,"Consumed");
//        set2.setFillAlpha(110);
//        set2.setColor(ContextCompat.getColor(this,R.color.mobikwik_blue_dark));
//        set2.setValueTextColor(ContextCompat.getColor(context, R.color.white));
//        set2.setCircleColor(getResources().getColor(R.color.mobikwik_blue_dark));
//        set2.setCircleRadius(5);
//
//
//
//        set2.setLineWidth(2f);
//        set3=new LineDataSet(yValues3,"Net");
//        set3.setFillAlpha(110);
//        set3.setColor(ContextCompat.getColor(this,R.color.dark_grey_blue));
//        set3.setValueTextColor(ContextCompat.getColor(context, R.color.white));
//        set3.setCircleColor(getResources().getColor(R.color.dark_grey_blue));
//        set3.setCircleRadius(5);
//
//
//
//        set3.setLineWidth(2f);

        set4 = new LineDataSet(yValues4, "Scheduled(kcal)");
        set4.setFillAlpha(110);
        set4.setColor(ContextCompat.getColor(context, R.color.haldi));
        set4.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        set4.setCircleColor(getResources().getColor(R.color.haldi));
        set4.setCircleRadius(5);


        set4.setLineWidth(2f);
        final LineData data = new LineData(set1, set4);
        linegraph.setData(data);

//        linegraph.getLegend().setEnabled(false);

        Description description = new Description();
        linegraph.getDescription().setText("Calories");


        XAxis xAxis = linegraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        linegraph.setDragEnabled(true);

        if (aryCalories.size() == 1) {
            xAxis.setLabelCount(aryCalories.size());

        } else {
            xAxis.setLabelCount(aryCalories.size(), true);

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
        linegraph.getXAxis().setTextColor(R.color.white);


        YAxis left = linegraph.getAxisLeft();
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

//        linegraph.getAxisLeft().setEnabled(true); //show y-axis at left
//        linegraph.getAxisRight().setEnabled(false); //hide y-axis at right
//
//
//        linegraph.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return "string_" + (int) value; // yVal is a string array
//            }
//        });


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (!aryCalories.isEmpty()) {
                    try {
                        for (int i = 0; i < aryCalories.size(); ++i) {
                            if (yValues1.get(i).getX() == value) {

                                int index = aryCalories.get(i).getCreatedOn().indexOf("T");

                                return formatDates(aryCalories.get(i).getCreatedOn().substring(0, index));

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                return "";
            }
        });


    }

    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("MMM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if (parent.getItemAtPosition(position) != null) {
            String monthName = parent.getItemAtPosition(position).toString();

            if (Utils.isNetworkAvailable(context)) {
                GetAllNotifications(monthName);
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
        txt_sleep_date_from_dialog = dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog = dialog.findViewById(R.id.txt_sleep_date_to_dialog);
        TextView txt_dialog_header = dialog.findViewById(R.id.txt_dialog_header);
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
        String strMindate[] = new SessionManager(context).getStringValue("mindate").split("-");


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
//

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
//            img_add_sleep_date = findViewById(R.id.img_add_sleep_date);

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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String formatDatesSleep(String dateFromServer) {

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

    public String getFromattedStringDate(int s) {


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

            if (month < 10) {
                dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month + 1) + "-" + year;

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
                Toast.makeText(context, "From date should be greater than End date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_show_sleep_from.setText(dummydate_from);
            txt_sleep_date_from_dialog.setText(dummydate_from);
            submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;


        }


        if (StrDateOpen.equalsIgnoreCase("to")) {


            if (!dummydate_from.trim().isEmpty()) {


                if (month < 10) {
                    dummydate_to = dayOfMonth + "-" + "0" + (month + 1) + "-" + year;

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
                    Toast.makeText(context, "End date should be greater than Start date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    txt_show_sleep_to.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                }
            } else {
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

                        getActivity().invalidateOptionsMenu();
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