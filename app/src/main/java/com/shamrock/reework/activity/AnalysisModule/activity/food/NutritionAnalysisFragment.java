package com.shamrock.reework.activity.AnalysisModule.activity.food;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.MicrorlNutritionActivityAnalysis;
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.activity.EditMealActivity;
import com.shamrock.reework.activity.FoodModule.adapter.WeeklyAnalysisAdapter;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragmentAllMeals;
import com.shamrock.reework.activity.FoodModule.model.FoodAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisRequest;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;
import com.shamrock.reework.util.MyValueFormatter;
import com.shamrock.reework.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NutritionAnalysisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NutritionAnalysisFragment extends Fragment  implements  View.OnClickListener,DifferentRowAdapter.OnMealClickListner, AdapterView.OnItemSelectedListener,DatePickerDialog.OnDateSetListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private static final String ARG_PARAM4 = "param4";

    private static final String ARG_PARAM3 = "MEAL_CAL_MAX";

    private String mParam4;
    TextView txt_todaysmeal_header;

    private  int mParam3=0;

    FoodService foodService;
    SessionManager sessionManager;
    List<WeeklyAnalysisResponce.Datum> list;
    private MasterFoodFragmentAllMeals.OnFragmentInteractionListener mListener;
    private Context context;
    private Utils utils;
    int userId,reecoachID;

    RecyclerView recyclerViewTodays,recyclerView_All_Meal_Weekly;
    TextView txtpercentProtin,txtpercentFat,txtpercentCarbs, txtpercentFiber;
    LinearLayout linPercent;
    LinearLayout linearLayout;
    Button btnViewMore;
    TextView tvAddMeal;
    PieChart pieChart,chart;
    private boolean FromWater;
    Spinner spinner_days;
    DifferentRowAdapter adapter;
//    public static  final int[] MY_COLORS = {
//            Color.rgb(0,215,189),  Color.rgb(15,180,210),Color.rgb(254,170,3),
//            Color.parseColor("#ffcc0000")
//    };

    public static  final int[] MY_COLORS = {
            Color.rgb(0,215,189), Color.parseColor("#ffcc0000"), Color.rgb(254,170,3),
            Color.rgb(15,180,210),
    };
    public int[] yValues = {0, 0, 0,0};

    public String[] xValues = {"", "", "",""};
    boolean ISFromANnalysis;


    /* private ArrayList<MasterWaterModel> dataSet;
    MasterWaterAdapter masterWaterAdapter;
    static String[] nameArray = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "JellyBean", "Kitkat", "Lollipop"};
    static Integer[] id_IDEAL = {8, 8, 8, 8, 8, 8, 8, 8, 8};
    static Integer[] id_YOURS = {7, 8, 9, 6, 8, 8, 8, 8, 7};*/
    // Animation
    Animation animSlideDown, animSlideUp;
    int check = 0;
    String stringJson = "{code:\"1\",status:\"Ok\",data:[{meal_name:\"Breakfast\",meal_cal_min:\"900\",meal_cal_max:\"1500\",meal_data:" +
            "[{meal_img:\"http://ww.dsda.com/image.jpg\",meal_name:\"Oats Idli\",meal_calory:\"500\",meal_quantity:\"2\"}]}," +
            "{meal_name:\"Lunch\",meal_cal_min:\"2000\",meal_cal_max:\"2500\",meal_data:[{meal_img:\"http://ww.dsda.com/image.jpg\"" +
            ",meal_name:\"Daal Tadka\",meal_calory:\"2000\",meal_quantity:\"1\"},{meal_img:\"http://ww.dsda.com/image.jpg\"," +
            "meal_name:\"Skillet Pita Bread\",meal_calory:\"500\",meal_quantity:\"2\"}]},{meal_name:\"Dinner\",meal_cal_min:\"900\"," +
            "meal_cal_max:\"2000\",meal_data:[{meal_img:\"http://ww.dsda.com/image.jpg\",meal_name:\"Dum Paneer Kali Mirch\"," +
            "meal_calory:\"300\",meal_quantity:\"1\"}]}]}";
    private String curentDate;
    private String submitHistoryDate;
    private String dummyhistoryshowdate;
    private DatePickerDialog datepickerdialog_history;
    private LinearLayout ll_edit_meal_data;
    private String strfromdatePiker="";
    private Button btn_sumit_food;







    private TextView txt_show_sleep_tonut,txt_show_sleep_fromnut;
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


    //New
    LinearLayout parent;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    TextView labelMyProgress;
    ImageView imgopennut,imgclosenut;
    LinearLayout layout_opennut,layout_closenut;

    Toolbar toolbar;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;


    Button btn_weekly, btn_3months, btn_6months;

    private SessionManager session;


    public NutritionAnalysisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NutritionAnalysisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NutritionAnalysisFragment newInstance(String param1, String param2) {
        NutritionAnalysisFragment fragment = new NutritionAnalysisFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrition_analysis, container, false);
        context = getContext();
        layout_opennut = view.findViewById(R.id.layout_opennut);
        layout_closenut = view.findViewById(R.id.layout_closenut);
        imgopennut = view.findViewById(R.id.imgopennut);
        imgclosenut = view.findViewById(R.id.imgclosenut);

        final Button ll_sleep_date=view.findViewById(R.id.btn_selectdate);
//        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showNewgetFoodhistroy();
//            }
//        });

        imgopennut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {
//                if(ColoriesAnalysisActivty.this.getResources().getConfiguration().orientation==1){
////                    Toast.makeText(getApplicationContext(),"potrest",Toast.LENGTH_LONG).show();
//                }
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                if(layout_opennut.getVisibility()==View.VISIBLE){
                    layout_closenut.setVisibility(View.VISIBLE);
                    layout_opennut.setVisibility(View.GONE);
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
        imgclosenut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onClick(View view) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                layout_close.setVisibility(View.GONE);
//                layout_open.setVisibility(View.VISIBLE);
                if(layout_closenut.getVisibility()==View.VISIBLE){
                    layout_closenut.setVisibility(View.GONE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    layout_opennut.setVisibility(View.VISIBLE);

                }
            }
        });

        try {
//            img_add_sleep_date=findViewById(R.id.img_add_sleep_date);
            txt_show_sleep_tonut=view.findViewById(R.id.txt_show_sleep_tonut);
            txt_show_sleep_fromnut=view.findViewById(R.id.txt_show_sleep_fromnut);
            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            dummydate_from = formatDatesSleep(submitFromDate);
            txt_show_sleep_fromnut.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_tonut.setText(dummydate_to);
            getFoodAnalysis(submitFromDate,submitToDate);


        }catch (Exception e){
            e.printStackTrace();
        }

        //N̥ew
        parent = view.findViewById(R.id.parent);
//        scaleGestureDetector = new ScaleGestureDetector(this, new ColoriesAnalysisActivty.ScaleListener());

        labelMyProgress = view.findViewById(R.id.labelMyProgress);

        ImageView img_sendnut = view.findViewById(R.id.img_sendnut);
        img_sendnut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenShot(parent, "result");

            }
        });

        recyclerViewTodays =view.findViewById(R.id.recyclerView_All_Meal_TodaysMeal);
        btn_sumit_food =view.findViewById(R.id.btn_sumit_food);
        recyclerView_All_Meal_Weekly =view.findViewById(R.id.recyclerView_All_Meal_Weekly);
        linearLayout =view.findViewById(R.id.linLay_All_Meal_Week_Analysis);
        btnViewMore =view.findViewById(R.id.buttonAllMeal_ViewMore);
        tvAddMeal =view.findViewById(R.id.tvAddMeal);
        pieChart =view.findViewById(R.id.pieChart_AllMeal);
        txtpercentProtin =view.findViewById(R.id.percentProtin);
        txtpercentFat =view.findViewById(R.id.percentFat);
        txtpercentCarbs =view.findViewById(R.id.percentCarbs);
        txtpercentFiber =view.findViewById(R.id.percentFibre);
        linPercent =view.findViewById(R.id.linPercent);
        spinner_days =view.findViewById(R.id.spinner_days);

        //setnewhistroy
        txt_todaysmeal_header=view.findViewById(R.id.txt_todaysmeal_header);
        ll_edit_meal_data=view.findViewById(R.id.ll_edit_meal_data);
        ll_edit_meal_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerdialog_history.show();
            }
        });



        sessionManager=new SessionManager(context);
        if (!sessionManager.getStringValue("backdateShow").isEmpty()&&!sessionManager.getStringValue("backdatesubmit").isEmpty()){
            dummyhistoryshowdate=sessionManager.getStringValue("backdateShow");
            curentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            submitHistoryDate=sessionManager.getStringValue("backdatesubmit");


//            txt_todaysmeal_header.setText( dummyhistoryshowdate);


            SimpleDateFormat  simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
            Date varDate= null;
            try {
                varDate = simpleDateFormat.parse(dummyhistoryshowdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
//            txt_todaysmeal_header.setText(simpleDateFormat.format(varDate));


        }else {
            dummyhistoryshowdate=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            curentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//            txt_todaysmeal_header.setText( new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));



        }

        showDatePickerHistoryAddDailog();
//
        // load the animation
        animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);

        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        reecoachID = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        utils = new Utils();
        foodService = Client.getClient().create(FoodService.class);

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


            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            dummydate_from = formatDatesSleep(submitFromDate);
            txt_show_sleep_fromnut.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_tonut.setText(dummydate_to);
            getFoodAnalysis(submitFromDate,submitToDate);


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
            txt_show_sleep_fromnut.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_tonut.setText(dummydate_to);
            getFoodAnalysis(submitFromDate,submitToDate);


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
            txt_show_sleep_fromnut.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_tonut.setText(dummydate_to);

            getFoodAnalysis(submitFromDate,submitToDate);


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
            txt_show_sleep_fromnut.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_show_sleep_tonut.setText(dummydate_to);

            getFoodAnalysis(submitFromDate,submitToDate);


        }


//
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
                txt_show_sleep_fromnut.setText(dummydate_from);
                dummydate_to = formatDatesSleep(submitToDate);
                txt_show_sleep_tonut.setText(dummydate_to);

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


                getFoodAnalysis(submitFromDate,submitToDate);
//                GetAllNotifications("");
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
                txt_show_sleep_fromnut.setText(dummydate_from);
                dummydate_to = formatDatesSleep(submitToDate);
                txt_show_sleep_tonut.setText(dummydate_to);


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


                getFoodAnalysis(submitFromDate,submitToDate);
//                GetAllNotifications("");

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
                txt_show_sleep_fromnut.setText(dummydate_from);
                dummydate_to = formatDatesSleep(submitToDate);
                txt_show_sleep_tonut.setText(dummydate_to);

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


                getFoodAnalysis(submitFromDate,submitToDate);
//                GetAllNotifications("");

            }
        });








        btn_sumit_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFoodAnalysis(submitFromDate, submitToDate);
            }
        });

        spinner_days.setOnItemSelectedListener(this);
        tvAddMeal.setOnClickListener(this);
        btnViewMore.setOnClickListener(this);


        //set default data
        setSpinnerData();


        //------------------------Today's Meal----------------------------
        if (Utils.isNetworkAvailable(context)) {
//            getAllTodaysMeal();
            getWeeklyAnalysis();

            getFoodAnalysis(submitFromDate, submitToDate);


        }
        else{
            Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
        }




        return view;


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
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bm,"IMG_" + Calendar.getInstance().getTime(),null);
        Uri bitmapUri = Uri.parse(path);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(bitmapUri)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }




    private void showNewgetFoodhistroy() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog=dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog=dialog.findViewById(R.id.txt_sleep_date_to_dialog);


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    dialog.dismiss();

                    getFoodAnalysis(submitFromDate,submitToDate);
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
//        if (strMindate.length>1){
//            c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
//
//        }
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
    private void showDatePickerHistoryAddDailog() {
        String strMindate[]= new SessionManager(context).getStringValue("mindate").split("-");



        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_history = new DatePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, NutritionAnalysisFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        if (strMindate.length>1){
            c.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1]),Integer.parseInt(strMindate[0]));

        }
        c.setTime(today);
        c.add(Calendar.MONTH, 3);
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());
        datepickerdialog_history.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
            if (strMindate.length>1){
                c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

            }

        }
        datepickerdialog_history.getDatePicker().setMinDate(c1.getTimeInMillis());
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


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvAddMeal:
                Intent i = new Intent(context, AddMealActivity.class);
                i.putExtra("commingFrom",  "New");
                i.putExtra("HistoryDate",submitHistoryDate);

                sessionManager.setStringValue("backdatesubmit",submitHistoryDate);
                sessionManager.setStringValue("backdateShow",dummyhistoryshowdate);
                startActivity(i);
                break;

            case R.id.buttonAllMeal_ViewMore:
            {

                if (list!=null){
                    if (list.isEmpty()){

                        Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }else {
                    Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (linearLayout.getVisibility() == View.VISIBLE)
                {
                    linearLayout.clearAnimation();
                    linearLayout.setVisibility(View.GONE);
                    btnViewMore.setText("View Last 7 days");
                }
                else
                {
                    linearLayout.clearAnimation();
                    linearLayout.setVisibility(View.VISIBLE);
                    btnViewMore.setText("Less");
                    linearLayout.startAnimation(animSlideDown);
                }
            }
            break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onClickMeal(int position, TodaysMeal model) {
        Intent i = new Intent(context, EditMealActivity.class);
        i.putExtra("commingFrom",  "Edit");
        i.putExtra("mealItem", (Serializable) model);
        i.putExtra("CreatedonDate",submitHistoryDate);
        sessionManager.setStringValue("backdatesubmit",submitHistoryDate);
        sessionManager.setStringValue("backdateShow",dummyhistoryshowdate);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if (++check > 1)
        {
            if (adapterView.getItemAtPosition(position) != null)
            {
                String days = adapterView.getItemAtPosition(position).toString();
                days =  days.substring(0,1);

                if (Utils.isNetworkAvailable(context))
                {
                    getFoodAnalysis(submitFromDate, submitToDate);
                }
                else
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            txt_show_sleep_fromnut.setText(dummydate_from);
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
                    txt_show_sleep_tonut.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;
                }
            }else {
                Toast.makeText(context, "Please select Start date", Toast.LENGTH_SHORT).show();
            }
        }











        if (submitHistoryDate.equalsIgnoreCase(year + "-" + (month + 1) + "-" + dayOfMonth)){
            return;
        }


        int  monthnew=(month+1);

        strfromdatePiker="yes";

        if (monthnew<10){

            dummyhistoryshowdate = dayOfMonth + "-" + "0"+(month + 1) + "-" + year;
            sessionManager.setStringValue("backdateShow",dummyhistoryshowdate);

        }else {
            dummyhistoryshowdate = dayOfMonth + "-" +(month + 1) + "-" + year;
            sessionManager.setStringValue("backdateShow",dummyhistoryshowdate);


        }
        submitHistoryDate=year + "-" + (month + 1) + "-" + dayOfMonth;

        sessionManager.setStringValue("backdatesubmit",submitHistoryDate);
        SimpleDateFormat  simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        Date varDate= null;
        try {
            varDate = simpleDateFormat.parse(dummyhistoryshowdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");









    }
    private void setDataForPieChart() {

        ArrayList<PieEntry> entries = new ArrayList<>();
// NOTE: The order of the entries when being added to the entries array determines their position around the center of
// the chart.
        for (int i = 0; i < yValues.length ; i++) {
// if (Integer.valueOf(yValues[i]) > 0) {
            entries.add(new PieEntry(yValues[i], xValues[i]));
// }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Food Analysis (%)");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

// add a lot of colors
        dataSet.setColors(MY_COLORS);
//dataSet.setSelectionShift(0f);


        PieData data = new PieData(dataSet);
        pieChart.setUsePercentValues(true);
        data.setValueFormatter(new MyValueFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.getLegend().setEnabled(false);
        pieChart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.white));
        pieChart.getDescription().setEnabled(false);
// data.setValueTypeface(tfLight);
        pieChart.setData(data);

// undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }


    private void getFoodAnalysis(String submitFromDate, String submitToDate)

    {
        utils.showProgressbar(context);


        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setReeworkerId(String.valueOf(userId));
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);

        Call<FoodAnalysisResponce> call = foodService.get_FoodAnalysisHistory(clsHistoryRequest);
        call.enqueue(new Callback<FoodAnalysisResponce>()
        {
            @Override
            public void onResponse(Call<FoodAnalysisResponce> call, Response<FoodAnalysisResponce> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    List<WeeklyAnalysisResponce.Datum> list = new ArrayList<>();
                    FoodAnalysisResponce dataVal = response.body();
                    if (dataVal != null && dataVal.getCode() == 1)
                    {
                        FoodAnalysisResponce.Data data = response.body().getData();

                        if (data!= null) {
                            try {
                                linPercent.setVisibility(View.VISIBLE);
                                if (data.getProtien() != null && data.getFats() != null && data.getCarbohydrates() != null && data.getFibre() != null)
                                {
                                    String finalProtineValue = null, finalFatValue=null, finalCarbsValue=null, finalFiberValue=null;

                                    int finallastValueprotine, finallastValueFats, finallastValueCarbs, finallastValueFiber;

//                                float sum = data.getProtien() + data.getFats() + data.getCarbohydrates() + data.getFibre();
                                    float sum = data.getProtien() + data.getFats() + data.getCarbohydrates() ;
                                    float finalProtine = (float) ((data.getProtien() / sum) * 100);
                                    float finalfats = (float) ((data.getFats() / sum) * 100);
                                    float finalcarbs = (float) ((data.getCarbohydrates() / sum) * 100);
                                    float finalfiber = (float) ((data.getFibre() / sum) * 100);

                                    //for getting accurate percent of protine
                                    String test = String.valueOf(finalProtine);
                                    if (test.contains(".")) {


                                        finalProtineValue = String.valueOf(Math.round(Float.parseFloat(test)));


                                    }else {
                                        finalProtineValue = String.valueOf(finalProtine);

                                    }

                                    //for getting accurate percent of Fats
                                    String strFats = String.valueOf(finalfats);
                                    if (strFats.contains(".")) {

                                        finalFatValue =String.valueOf(Math.round(Float.parseFloat(strFats)));

                                    }else {
                                        finalFatValue =String.valueOf(Math.round(Float.parseFloat(strFats)));

                                    }


                                    //for getting accurate percent of Carbs
                                    String strCarbs = String.valueOf(finalcarbs);
                                    if (strCarbs.contains(".")) {
                                        finalCarbsValue=String.valueOf(Math.round(Float.parseFloat(strCarbs)));

                                    }else {
                                        finalCarbsValue=String.valueOf(Math.round(Float.parseFloat(strCarbs)));

                                    }

                                    String strFiber = String.valueOf(finalfiber);
                                    if (strFiber.contains(".")) {
                                        finalFiberValue=String.valueOf(Math.round(Float.parseFloat(strFiber)));

                                    }else {
                                        finalFiberValue=String.valueOf(Math.round(Float.parseFloat(strFiber)));

                                    }


                                    if(finalProtineValue!=null && finalFatValue!=null && finalCarbsValue!=null) {
                                        if (finalProtineValue.equalsIgnoreCase("NaN")){
                                            finalProtineValue="0";
                                        }




                                        if (finalProtineValue.equals("0")&&finalCarbsValue.equals("0")&&finalFatValue.equals("0")){
                                            pieChart.setData(null);
                                            pieChart.highlightValue(null);
                                            pieChart.invalidate();
                                        }else {
                                            yValues = new int[]{Integer.parseInt(finalProtineValue), Integer.parseInt(finalFatValue), Integer.parseInt(finalCarbsValue),Integer.parseInt(finalFiberValue)};
                                            setDataForPieChart();
                                        }

                                    }else {
                                        pieChart.setData(null);
                                        pieChart.highlightValue(null);
                                        pieChart.invalidate();
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            linPercent.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<FoodAnalysisResponce> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void setSpinnerData(){
        ArrayList DaysList = new ArrayList();
        for(int i = 0;i<7;i++){
            int days = i+1;
            if(i==0){
                DaysList.add(String.valueOf(days+"day"));
            }else{
                DaysList.add(String.valueOf(days+"days"));
            }
        }
//        Collections.reverse(DaysList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_white, DaysList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_days.setAdapter(adapter);


    }

    private void getWeeklyAnalysis()

    {
        utils.showProgressbar(context);

        WeeklyAnalysisRequest request = new WeeklyAnalysisRequest();
        request.setUserid(userId);//4186 userId
        request.setReecoachId(String.valueOf(reecoachID));
        request.setCreatedOn(submitHistoryDate);

        Call<WeeklyAnalysisResponce> call = foodService.get_WeeklyAnalysis(request);
        call.enqueue(new Callback<WeeklyAnalysisResponce>()
        {
            @Override
            public void onResponse(Call<WeeklyAnalysisResponce> call, Response<WeeklyAnalysisResponce> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    list = new ArrayList<>();
                    WeeklyAnalysisResponce dataVal = response.body();
                    if (dataVal != null && dataVal.getCode() == 1)
                    {
                        List<WeeklyAnalysisResponce.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {
                            for (int i = 0; i < data.size(); i++)
                            {
                                WeeklyAnalysisResponce.Datum datum = new WeeklyAnalysisResponce.Datum();
                                datum.setActual(data.get(i).getActual());
                                datum.setCreatedOn(data.get(i).getCreatedOn());
                                datum.setScheduled(data.get(i).getScheduled());
                                list.add(datum);

                            }
                            WeeklyAnalysisAdapter adapter = new WeeklyAnalysisAdapter(context,list);
                            recyclerView_All_Meal_Weekly.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView_All_Meal_Weekly.setAdapter(adapter);
                        }
                    }
                    else
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<WeeklyAnalysisResponce> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }
    private void getAllTodaysMeal()

    {
        utils.showProgressbar(context);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);
        request.setMeal_cal_max(mParam3);
        request.setCreatedOn(submitHistoryDate);
        //4186

        Call<TodaysMealData> call = foodService.getTodyasMeal(request);
        call.enqueue(new Callback<TodaysMealData>()
        {
            @Override
            public void onResponse(Call<TodaysMealData> call, Response<TodaysMealData> response)
            {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    List<TodaysMeal> list = new ArrayList<>();
                    TodaysMealData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<TodaysMealData.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {

                            try{


                                for (int i = 0; i < data.size(); i++)
                                {
                                    //  JSONObject mainMeal = dataArray.getJSONObject(i);

                                    // HEADER Data

                                    TodaysMeal todaysMeal = new TodaysMeal();
                                    todaysMeal.setMeal_type_name(data.get(i).getMealType());
                                    todaysMeal.setMeal_cal_min(String.valueOf(data.get(i).getMealCalMin()));
//                                    todaysMeal.setMeal_cal_max(String.valueOf(data.get(i).getMealCalMax()));
                                    todaysMeal.setMeal_type(1);
                                    todaysMeal.setIntakeTime(data.get(i).getIntakeTime());
                                    todaysMeal.setCreatedOn(data.get(i).getCreatedOn());
                                    list.add(todaysMeal);

                                    // ROW Data

                                    ;
                                    //JSONArray mealItems = mainMeal.getJSONArray("meal_data");

                                    for (int j = 0; j < data.get(i).getLstSubMealData().size(); j++) {
                                        //JSONObject rowMeal = mealItems.getJSONObject(j);
                                        TodaysMeal meal = new TodaysMeal();
                                        if (j == data.get(i).getLstSubMealData().size()) {
                                            meal.setMeal_type(3);   // last row
                                        } else {
                                            meal.setMeal_type(2);   // middle rows
                                        }
                                        meal.setMeal_type_name(data.get(i).getMealType());
                                        if(data.get(i).getLstSubMealData().get(j).getIntakeTime()!=null){
                                            data.get(i).getLstSubMealData().get(j).getIntakeTime();
                                        }
                                        meal.setIntakeTime(data.get(i).getLstSubMealData().get(j).getIntakeTime());
                                        meal.setMeal_img(data.get(i).getLstSubMealData().get(j).getMealImg());
                                        meal.setMeal_name(data.get(i).getLstSubMealData().get(j).getMealName());
                                        meal.setMeal_quantity(data.get(i).getLstSubMealData().get(j).getMealQty());
                                        meal.setFood_Id(data.get(i).getLstSubMealData().get(j).getRecipeId());
                                        meal.setUserMealId(data.get(i).getLstSubMealData().get(j).getUserMealId());
                                        meal.setMeal_calory(String.valueOf(data.get(i).getLstSubMealData().get(j).getMealCalory()));
                                        list.add(meal);
                                    }
                                }
//                                adapter = new DifferentRowAdapter(MasterFoodFragmentAllMeals.this,list,data);
//                                recyclerViewTodays.setLayoutManager(new LinearLayoutManager(context));
//                                recyclerViewTodays.setAdapter(adapter);


                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                    else
                    {

                        if (strfromdatePiker.equalsIgnoreCase("yes")){
                            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                            pieChart.setData(null);
                            pieChart.highlightValue(null);
                            pieChart.invalidate();

                        }
                        list.clear();
                        List<TodaysMealData.Datum> datas=new ArrayList<>();
//                        adapter = new DifferentRowAdapter(MasterFoodFragmentAllMeals.this,list, datas);
//                        recyclerViewTodays.setLayoutManager(new LinearLayoutManager(context));
//                        recyclerViewTodays.setAdapter(adapter);



//                        if (getUserVisibleHint()){

//                            if (!sessionManager.getStringValue("IsShowMSg").equalsIgnoreCase("false")){
//                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();

//                            }


//                        }
                    }
                }

//                sessionManager.setStringValue("IsShowMSg","true");

            }

            @Override
            public void onFailure(Call<TodaysMealData> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
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