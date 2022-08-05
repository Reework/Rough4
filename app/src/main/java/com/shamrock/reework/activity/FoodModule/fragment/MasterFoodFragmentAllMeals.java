package com.shamrock.reework.activity.FoodModule.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.mobikwik.mobikwikpglib.circleprogress.CircleProgressView;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodActivity;
import com.shamrock.reework.activity.FoodModule.activity.EditMealActivity;
import com.shamrock.reework.activity.FoodModule.adapter.WeeklyAnalysisAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.WeeklyAnalysisAdapterNew;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistory;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistoryRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodAnalysisRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.model.FoodData;
import com.shamrock.reework.activity.FoodModule.model.FoodHistoryData;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisRequest;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.response.FoodListByMealType;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;
import com.shamrock.reework.reecoachmodule.activities.ReecoachDashBoardActivity;
import com.shamrock.reework.reecoachmodule.activities.ReeworkerListActivity;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsmainDashboardData;
import com.shamrock.reework.util.MyValueFormatter;
import com.shamrock.reework.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterFoodFragmentAllMeals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterFoodFragmentAllMeals extends Fragment implements View.OnClickListener, DifferentRowAdapter.OnMealClickListner, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM4 = "param4";
    ImageView imgdish;
    CircularProgressBar progress_daily_consumed;

    private static final String ARG_PARAM3 = "MEAL_CAL_MAX";
    LinearLayout ll_main_food;
    TextView txt_no_meal;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam4;
    TextView txt_todaysmeal_header;
    TextView tvAddMeal_dagte;
    private int mParam3 = 0;

    FoodService foodService;
    SessionManager sessionManager;
    List<WeeklyAnalysisResponce.Datum> list;
    ArrayList<FoodData> listFood = new ArrayList<FoodData>();
    private OnFragmentInteractionListener mListener;
    private Context context;
    private Utils utils;
    int userId, reecoachID;

    RecyclerView recyclerViewTodays, recyclerView_All_Meal_Weekly;
    TextView txtpercentProtin, txtpercentFat, txtpercentCarbs, txtpercentFiber;
    LinearLayout linPercent;
    LinearLayout linearLayout;
    TextView btnViewMore;
    RelativeLayout rel_weekly;
    ImageView tvAddMeal;
    PieChart pieChart, chart;
    private boolean FromWater;
    Spinner spinner_days;
    DifferentRowAdapter adapter;
    //    {"Protien":8.0,"Fats":15.0,"Carbohydrates":64.0,"Fibre":12.0}}
    public static final int[] MY_COLORS = {
            Color.rgb(0, 215, 189), Color.parseColor("#ffcc0000"), Color.rgb(254, 170, 3), Color.rgb(15, 180, 210),

    };
    public int[] yValues = {0, 0, 0, 0};
    ImageView imgprocess;

    public String[] xValues = {"", "", "", ""};


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
    private String strfromdatePiker = "";
    TextView txt_acual_total_cal_consumed, txt_acual_total_protein, txt_acual_total_carb, txt_acual_fat, txt_acual_total_fiber,txtallcalories;
    CardView card_cal_consumed;

    private String submitFromDate;
    private String submitToDate;
    SpannableStringBuilder spannableStringBuilder, spannableStringBuilder1;
    String strText, strText1, str,str1, sart;
TextView txt_todaysmeal_headernew;
    public MasterFoodFragmentAllMeals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1    Parameter 1.
     * @param param2    Parameter 2.
     * @param fromWater
     * @return A new instance of fragment MasterFoodFragmentAllMeals.
     */
    // TODO: Rename and change types and number of parameters
    public static MasterFoodFragmentAllMeals newInstance(String param1, String param2, int param3, boolean fromWater) {
        MasterFoodFragmentAllMeals fragment = new MasterFoodFragmentAllMeals();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        args.putBoolean("FromWater", fromWater);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            FromWater = getArguments().getBoolean("FromWater");
            // mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_food_fragment_all_meals, container, false);
        progress_daily_consumed = view.findViewById(R.id.progress_daily_consumed);
        txt_todaysmeal_headernew = view.findViewById(R.id.txt_todaysmeal_headernew);
        imgdish = view.findViewById(R.id.imgdish);
        txtallcalories = view.findViewById(R.id.txtallcalories);
        imgprocess = view.findViewById(R.id.imgprocess);
        recyclerViewTodays = view.findViewById(R.id.recyclerView_All_Meal_TodaysMeal);
        card_cal_consumed = view.findViewById(R.id.card_cal_consumed);
        txt_acual_total_cal_consumed = view.findViewById(R.id.txt_acual_total_cal_consumed);
        txt_acual_total_protein = view.findViewById(R.id.txt_acual_total_protein);
        txt_acual_total_carb = view.findViewById(R.id.txt_acual_total_carb);
        txt_acual_fat = view.findViewById(R.id.txt_acual_fat);
        txt_acual_total_fiber = view.findViewById(R.id.txt_acual_total_fiber);
        recyclerView_All_Meal_Weekly = view.findViewById(R.id.recyclerView_All_Meal_Weekly);
        linearLayout = view.findViewById(R.id.linLay_All_Meal_Week_Analysis);
        btnViewMore = view.findViewById(R.id.buttonAllMeal_ViewMore);
        rel_weekly = view.findViewById(R.id.rel_weekly);
        rel_weekly.setOnClickListener(this);
//        txt_no_meal = view.findViewById(R.id.txt_no_meal);
//        ll_main_food = view.findViewById(R.id.ll_main_food);
        sessionManager = new SessionManager(context);




        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            btnViewMore.setVisibility(View.GONE);
        }
        tvAddMeal = view.findViewById(R.id.tvAddMeal);
        pieChart = view.findViewById(R.id.pieChart_AllMeal);
        txtpercentProtin = view.findViewById(R.id.percentProtin);
        txtpercentFat = view.findViewById(R.id.percentFat);
        txtpercentCarbs = view.findViewById(R.id.percentCarbs);
        txtpercentFiber = view.findViewById(R.id.percentFibre);
        linPercent = view.findViewById(R.id.linPercent);
        spinner_days = view.findViewById(R.id.spinner_days);


        if (sessionManager.getStringValue("AddFood").equalsIgnoreCase("true")){
            sessionManager.setStringValue("AddFood","");
            if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                shownoplan();


            } else {
                if (Utils.isNetworkAvailable(context)) {
                    Intent i = new Intent(context, AddMealActivity.class);
                    i.putExtra("commingFrom", "New");
                    i.putExtra("HistoryDate", sessionManager.getStringValue("statusdate"));

                    sessionManager.setStringValue("backdatesubmit", sessionManager.getStringValue("statusdate"));
                    sessionManager.setStringValue("backdateShow", dummyhistoryshowdate);
                    sessionManager.setStringValue("closefood", "true");
                    startActivityForResult(i, 502);
                } else {
                    Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
                }

            }
        }


            //setnewhistroy
        txt_todaysmeal_header = view.findViewById(R.id.txt_todaysmeal_header);
        tvAddMeal_dagte = view.findViewById(R.id.tvAddMeal_dagte);
//        tvAddMeal_dagte.setText(sessionManager.getStringValue("CalenderSelectedDate"));
        tvAddMeal_dagte.setVisibility(View.VISIBLE);

        if (!sessionManager.getStringValue("showDate").isEmpty()) {

            if (!sessionManager.getStringValue("CalenderSelectedDate").isEmpty()) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date varDate = null, varDate1 = null;
                try {
                    varDate = simpleDateFormat.parse(sessionManager.getStringValue("CalenderSelectedDate"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                simpleDateFormat = new SimpleDateFormat("dd-MMM");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDateFormat12 = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    varDate1 = simpleDateFormat1.parse(sessionManager.getStringValue("CalenderSelectedDate"));
                    simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                simpleDateFormat12.format(varDate1);


//                formatDates(new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(new Date()));
                strText1 = (simpleDateFormat12.format(varDate1));


                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);


                if(strText1.equals(formattedDate)){
                    tvAddMeal_dagte.setText(strText1);
                    txt_todaysmeal_header.setText("Today's status");
                }else{
                    tvAddMeal_dagte.setText(strText1);
                    txt_todaysmeal_header.setText("Earlier status");
                }




//                spannableStringBuilder1 = new SpannableStringBuilder(strText1 + " status");
//                SuperscriptSpan superscriptSpan1 = new SuperscriptSpan();

//                spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("th"),
//                        strText1.indexOf("th") + ("th").length(),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                showSmallSizeText1("th");

//                if (str.equals("th")) {
//                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("th"),
//                            strText1.indexOf("th") + ("th").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText1("th");
//                } else if (str.equals("nd")) {
//                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("nd"),
//                            strText1.indexOf("nd") + ("nd").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText1("nd");
//                } else if (str.equals("rd")) {
//                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("rd"),
//                            strText1.indexOf("rd") + ("rd").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText1("rd");
//                } else if (str.equals("st")) {
//                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("st"),
//                            strText1.indexOf("st") + ("st").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText1("st");
//                }


//                txt_todaysmeal_header.setText(spannableStringBuilder1);
//                txt_todaysmeal_header.setText(sessionManager.getStringValue("CalenderSelectedDate"));

            } else {


                strText = sessionManager.getStringValue("showDate");
                tvAddMeal_dagte.setText(strText);
                txt_todaysmeal_header.setText("Today's status");
//                String abc[]=strText.split(" ");
//
//                String abc1=abc[2];
//                str1=abc1.substring(abc1.lastIndexOf("(")+3);
//
//                sart=abc[2]+abc[3];

//                spannableStringBuilder = new SpannableStringBuilder(sart);
//                SuperscriptSpan superscriptSpan = new SuperscriptSpan();

//                spannableStringBuilder.setSpan(superscriptSpan, strText.indexOf("th"),
//                        strText.indexOf("th") + ("th").length(),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                showSmallSizeText("th");


//                String abc[]=strText.split(" ");
//
//                String abc1=abc[2];
//                str1=abc1.substring(abc1.lastIndexOf("(")+3);
//
//                 sart=abc[2]+abc[3];
                txt_todaysmeal_headernew.setVisibility(View.GONE);
//                if (str1.equals("th")||str1.equals("h")) {
//                    spannableStringBuilder.setSpan(superscriptSpan, sart.indexOf("th"),
//                            sart.indexOf("th") + ("th").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText("th");
//                } else if (str1.equals("nd")) {
//                    spannableStringBuilder.setSpan(superscriptSpan, sart.indexOf("nd"),
//                            sart.indexOf("nd") + ("nd").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText("nd");
//                } else if (str1.equals("rd")) {
//                    spannableStringBuilder.setSpan(superscriptSpan, sart.indexOf("rd"),
//                            sart.indexOf("rd") + ("rd").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText("rd");
//                } else if (str1.equals("st")) {
//                    spannableStringBuilder.setSpan(superscriptSpan, sart.indexOf("st"),
//                            sart.indexOf("st") + ("st").length(),
//                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    showSmallSizeText("st");
//                }





//                txt_todaysmeal_header.setText(spannableStringBuilder);

            }


        } else {
            txt_todaysmeal_header.setText("Today's status");

        }
        ll_edit_meal_data = view.findViewById(R.id.ll_edit_meal_data);
        ll_edit_meal_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                datepickerdialog_history.show();
            }
        });


        if (!sessionManager.getStringValue("backdateShow").isEmpty() && !sessionManager.getStringValue("backdatesubmit").isEmpty()) {
            dummyhistoryshowdate = sessionManager.getStringValue("backdateShow");
            curentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            submitHistoryDate = sessionManager.getStringValue("statusdate");


//            txt_todaysmeal_header.setText( dummyhistoryshowdate);


            if (!curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date varDate = null;
                try {
                    varDate = simpleDateFormat.parse(dummyhistoryshowdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                simpleDateFormat = new SimpleDateFormat("dd-MMM");

//                txt_todaysmeal_header.setText(simpleDateFormat.format(varDate)+"'s Meal");
            } else {
//                txt_todaysmeal_header.setText("Today's Meal");

            }


        } else {
            dummyhistoryshowdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            curentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            submitHistoryDate = sessionManager.getStringValue("statusdate");
//            txt_todaysmeal_header.txt_todaysmeal_headersetText( new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
            if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
//                txt_todaysmeal_header.setText("Today's Meal");
            }
        }

        showDatePickerHistoryAddDailog();


        spinner_days.setOnItemSelectedListener(this);
        tvAddMeal.setOnClickListener(this);
        btnViewMore.setOnClickListener(this);

        // load the animation
        animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);

        sessionManager = new SessionManager(context);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        reecoachID = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        utils = new Utils();
        foodService = Client.getClient().create(FoodService.class);
        //set default data
        setSpinnerData();


        //------------------------Today's Meal----------------------------
        if (Utils.isNetworkAvailable(context)) {
            callFoodHistoryData();
            getAllTodaysMeal();
            getWeeklyAnalysis();
            getFoodAnalysis(0);

        } else {
            Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
        }


        return view;
    }
    public String formatCalDate(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
/*
        2019-07-30T17:07:00*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }
    @Override
    public void onResume() {
        super.onResume();
        MasterFoodFragment.rbAllMeal.setChecked(true);


    }

    private void showSmallSizeText(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder.setSpan(relativeSizeSpan,  sart.indexOf(s),  sart.indexOf(s) + (s).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void setDataForPieChart() {

        ArrayList<PieEntry> entries = new ArrayList<>();
// NOTE: The order of the entries when being added to the entries array determines their position around the center of
// the chart.
        for (int i = 0; i < yValues.length; i++) {
// if (Integer.valueOf(yValues[i]) > 0) {
            entries.add(new PieEntry(yValues[i], xValues[i]));
// }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Food Analysis");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);


// add a lot of colors
        dataSet.setColors(MY_COLORS);
//dataSet.setSelectionShift(0f);


        PieData data = new PieData(dataSet);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(50);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setHoleColor(Color.WHITE);
        data.setValueFormatter(new MyValueFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
// data.setValueTypeface(tfLight);
        pieChart.setData(data);

// undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }


    private void getFoodAnalysis(int days) {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        FoodAnalysisRequest request = new FoodAnalysisRequest();
        request.setUserid(userId);//4186 userIddays
        request.setReecoachId(reecoachID);
        if (days == 0) {
            request.setDays(days);
        } else {
            int newday = days;
            request.setDays(-newday);
        }


//        request.setCreatedOn(submitHistoryDate);
        String ana_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        request.setCreatedOn(ana_date);

        Call<FoodAnalysisResponce> call = foodService.get_FoodAnalysis(request);
        call.enqueue(new Callback<FoodAnalysisResponce>() {
            @Override
            public void onResponse(Call<FoodAnalysisResponce> call, Response<FoodAnalysisResponce> response) {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    List<WeeklyAnalysisResponce.Datum> list = new ArrayList<>();
                    FoodAnalysisResponce dataVal = response.body();
                    if (dataVal != null && dataVal.getCode() == 1) {
                        FoodAnalysisResponce.Data data = response.body().getData();

                        if (data != null) {
                            try {
                                linPercent.setVisibility(View.VISIBLE);
                                if (data.getProtien() != null && data.getFats() != null && data.getCarbohydrates() != null && data.getFibre() != null) {
                                    String finalProtineValue = null, finalFatValue = null, finalCarbsValue = null, finalFiberValue = null;


                                    float sum = data.getProtien() + data.getFats() + data.getCarbohydrates() + data.getFibre();
                                    float finalProtine = (float) ((data.getProtien() / sum) * 100);
                                    float finalfats = (float) ((data.getFats() / sum) * 100);
                                    float finalcarbs = (float) ((data.getCarbohydrates() / sum) * 100);
                                    float finalfiber = (float) ((data.getFibre() / sum) * 100);

                                    //for getting accurate percent of protine
                                    String test = String.valueOf(finalProtine);
                                    if (test.contains(".")) {


                                        finalProtineValue = String.valueOf(Math.round(Float.parseFloat(test)));


                                    } else {
                                        finalProtineValue = String.valueOf(finalProtine);

                                    }

                                    //for getting accurate percent of Fats
                                    String strFats = String.valueOf(finalfats);
                                    if (strFats.contains(".")) {

                                        finalFatValue = String.valueOf(Math.round(Float.parseFloat(strFats)));

                                    } else {
                                        finalFatValue = String.valueOf(Math.round(Float.parseFloat(strFats)));

                                    }


                                    //for getting accurate percent of Carbs
                                    String strCarbs = String.valueOf(finalcarbs);
                                    if (strCarbs.contains(".")) {
                                        finalCarbsValue = String.valueOf(Math.round(Float.parseFloat(strCarbs)));

                                    } else {
                                        finalCarbsValue = String.valueOf(Math.round(Float.parseFloat(strCarbs)));

                                    }


                                    String strFiber = String.valueOf(finalfiber);
                                    if (strFiber.contains(".")) {
                                        finalFiberValue = String.valueOf(Math.round(Float.parseFloat(strFiber)));

                                    } else {
                                        finalFiberValue = String.valueOf(Math.round(Float.parseFloat(strFiber)));
                                    }


                                    if (finalProtineValue != null && finalFatValue != null && finalCarbsValue != null) {
                                        if (finalProtineValue.equalsIgnoreCase("NaN")) {
                                            finalProtineValue = "0";
                                        }
                                        txtpercentFiber.setText(finalFiberValue + "%");
                                        txtpercentProtin.setText(finalProtineValue + "%");
                                        txtpercentCarbs.setText(finalCarbsValue + "%");
                                        txtpercentFat.setText(finalFatValue + "%");
//                                    yValues = new int[]{Integer.parseInt(finalProtineValue), Integer.parseInt(finalFatValue), Integer.parseInt(finalCarbsValue), Integer.parseInt(finalFiberValue)};


                                        if (finalProtineValue.equals("0") && finalCarbsValue.equals("0") && finalFatValue.equals("0") && finalFiberValue.equals("0")) {
                                            pieChart.setData(null);
                                            pieChart.highlightValue(null);
                                            pieChart.invalidate();
                                        } else {
                                            yValues = new int[]{Integer.parseInt(finalProtineValue), Integer.parseInt(finalFatValue), Integer.parseInt(finalCarbsValue), Integer.parseInt(finalFiberValue)};
                                            setDataForPieChart();
                                        }

                                    } else {
                                        pieChart.setData(null);
                                        pieChart.highlightValue(null);
                                        pieChart.invalidate();
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            linPercent.setVisibility(View.GONE);
                            if (getUserVisibleHint()) {
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        if (getUserVisibleHint()) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodAnalysisResponce> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void setSpinnerData() {
        ArrayList DaysList = new ArrayList();
        for (int i = 0; i < 7; i++) {
            int days = i + 1;
            if (i == 0) {
                DaysList.add(String.valueOf("Today"));
            } else {
                DaysList.add(String.valueOf(days + " Days"));
            }
        }
//        Collections.reverse(DaysList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_black, DaysList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_days.setAdapter(adapter);


    }


    public void callFoodHistoryData() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date newDate = calendar.getTime();
        Date date1 = null;
        String sDate1 = sessionManager.getStringValue("statusdate");
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date weekAgoDate = getDateWithOffset(-7, date1);
//        getCalculatedDate("01-01-2015", "dd-MM-yyyy", -10);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(weekAgoDate);
//        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ClsFoodHistoryRequest clsFoodHistoryRequest = new ClsFoodHistoryRequest();

        clsFoodHistoryRequest.setFromDate(submitFromDate);
        clsFoodHistoryRequest.setToDate(sessionManager.getStringValue("statusdate"));
        clsFoodHistoryRequest.setReeworkerId(String.valueOf(userId));
        FoodService commonService = Client.getClient().create(FoodService.class);


        Call<ClsFoodHistory> call = commonService.FoodHistory(clsFoodHistoryRequest);
        call.enqueue(new Callback<ClsFoodHistory>() {
            @Override
            public void onResponse(Call<ClsFoodHistory> call, retrofit2.Response<ClsFoodHistory> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    try {
                        ClsFoodHistory moodResponse = response.body();
                        if (moodResponse != null) {
                            moodResponse.getData();
                            listFood = new ArrayList<FoodData>();
                            ArrayList<FoodData> data = response.body().getData();
                            for (int i = 0; i < data.size(); i++) {
                                FoodData datum = new FoodData();
                                datum.setCreatedOn(data.get(i).getCreatedOn());
                                datum.setTotalCaloriesIntake(data.get(i).getTotalCaloriesIntake());
                                datum.setTotalCarbsIntake(data.get(i).getTotalCarbsIntake());
                                datum.setTotalFatIntake(data.get(i).getTotalFatIntake());
                                datum.setTotalFibreIntake(data.get(i).getTotalFibreIntake());
                                datum.setTotalProteinIntake(data.get(i).getTotalProteinIntake());
                                listFood.add(datum);

                            }
                            for (int i = 0; i < listFood.size(); i++) {
                                if (sessionManager.getStringValue("Cal_consumed_date").equalsIgnoreCase(formatDates(listFood.get(i).getCreatedOn()))) {
                                    card_cal_consumed.setVisibility(View.GONE);
                                    txt_acual_total_cal_consumed.setText(new DecimalFormat("##.#").format(listFood.get(i).getTotalCaloriesIntake()) + "");

                                    txt_acual_total_protein.setText(new DecimalFormat("##.#").format(listFood.get(i).getTotalProteinIntake()) + "");
                                    txt_acual_total_carb.setText(new DecimalFormat("##.#").format(listFood.get(i).getTotalCarbsIntake()) + "");
                                    txt_acual_fat.setText(new DecimalFormat("##.#").format(listFood.get(i).getTotalFatIntake()) + "");
                                    txt_acual_total_fiber.setText(new DecimalFormat("##.#").format(listFood.get(i).getTotalFibreIntake()) + "");

                                    txtallcalories.setText(new DecimalFormat("##.#").format(listFood.get(i).getTotalCaloriesIntake())+" kcal");

                                    double  dbl_circular_dailydairy_cal_consumed = (float)((listFood.get(i).getTotalCaloriesIntake() /Double.parseDouble( sessionManager.getStringValue("shedulecalories"))) * 100);


                                    progress_daily_consumed.setProgress((float) dbl_circular_dailydairy_cal_consumed);
                                    progress_daily_consumed.setProgressMax(100);
                                }
                            }

                            WeeklyAnalysisAdapterNew adapter = new WeeklyAnalysisAdapterNew(context, listFood);
                            recyclerView_All_Meal_Weekly.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView_All_Meal_Weekly.setAdapter(adapter);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsFoodHistory> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });


    }


    public static Date getDateWithOffset(int offset, Date date) {
        Calendar calendar = calendar = Calendar.getInstance();
        ;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar.getTime();
    }


    private void getWeeklyAnalysis() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        WeeklyAnalysisRequest request = new WeeklyAnalysisRequest();
        request.setUserid(userId);//4186 userId
        request.setReecoachId(String.valueOf(reecoachID));
        request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        Call<WeeklyAnalysisResponce> call = foodService.get_WeeklyAnalysis(request);
        call.enqueue(new Callback<WeeklyAnalysisResponce>() {
            @Override
            public void onResponse(Call<WeeklyAnalysisResponce> call, Response<WeeklyAnalysisResponce> response) {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    list = new ArrayList<>();
                    WeeklyAnalysisResponce dataVal = response.body();
                    if (dataVal != null && dataVal.getCode() == 1) {
                        String textconsumedCal = "";
                        List<WeeklyAnalysisResponce.Datum> data = response.body().getData();

                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                WeeklyAnalysisResponce.Datum datum = new WeeklyAnalysisResponce.Datum();
                                datum.setActual(data.get(i).getActual());
                                datum.setCreatedOn(data.get(i).getCreatedOn());
                                datum.setScheduled(data.get(i).getScheduled());
                                list.add(datum);

                                if (sessionManager.getStringValue("Cal_consumed_date").equalsIgnoreCase(formatDates(data.get(i).getCreatedOn()))) {
                                    textconsumedCal = String.valueOf(data.get(i).getActual());
//                                    card_cal_consumed.setVisibility(View.VISIBLE);
//                                    txt_acual_total_cal_consumed.setText("" + textconsumedCal + "");


                                }

                            }


//                            WeeklyAnalysisAdapter adapter = new WeeklyAnalysisAdapter(context,list);
//                            recyclerView_All_Meal_Weekly.setLayoutManager(new LinearLayoutManager(context));
//                            recyclerView_All_Meal_Weekly.setAdapter(adapter);
                        }
                    } else {
                        if (getUserVisibleHint()) {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<WeeklyAnalysisResponce> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public boolean getUserVisibleHint() {
        //  Toast.makeText(context,"VisibleHint",Toast.LENGTH_SHORT).show();
        return super.getUserVisibleHint();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    private void getAllTodaysMeal() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);
        request.setMeal_cal_max(mParam3);
        request.setCreatedOn(sessionManager.getStringValue("statusdate"));
        //4186

        Call<TodaysMealData> call = foodService.getTodyasMeal(request);
        call.enqueue(new Callback<TodaysMealData>() {
            @Override
            public void onResponse(Call<TodaysMealData> call, Response<TodaysMealData> response) {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    List<TodaysMeal> list = new ArrayList<>();
                    TodaysMealData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {
                        List<TodaysMealData.Datum> data = response.body().getData();

                        if (data != null && data.size() > 0) {

                            try {

                                double totalAllFoodCal = 0.0;


                                for (int i = 0; i < data.size(); i++) {
                                    //  JSONObject mainMeal = dataArray.getJSONObject(i);

                                    // HEADER Data

                                    totalAllFoodCal = totalAllFoodCal + data.get(i).getMealCalMin();
                                    TodaysMeal todaysMeal = new TodaysMeal();
                                    todaysMeal.setMeal_type_name(data.get(i).getMealType());
                                    todaysMeal.setMeal_cal_min(String.valueOf(data.get(i).getMealCalMin()));
                                    todaysMeal.setMeal_cal_max(Double.parseDouble(String.valueOf(data.get(i).getMealCalMax())));
                                    todaysMeal.setMeal_type(1);
                                    todaysMeal.setIntakeTime(data.get(i).getIntakeTime());
                                    todaysMeal.setCreatedOn(data.get(i).getCreatedOn());
                                    list.add(todaysMeal);

                                    // ROW Data

                                    ;
                                    //JSONArray mealItems = mainMeal.getJSONArray("meal_data");


                                    Collections.sort(data.get(i).getLstSubMealData(), new Comparator<TodaysMealData.LstSubMealDatum>() {

                                        @Override
                                        public int compare(TodaysMealData.LstSubMealDatum o1, TodaysMealData.LstSubMealDatum o2) {
                                            try {
                                                if (o1.getIntakeTime() != null && o2.getIntakeTime() != null) {
                                                    return new SimpleDateFormat("hh:mm aa").parse(o1.getIntakeTime()).compareTo(new SimpleDateFormat("hh:mm aa").parse(o2.getIntakeTime()));

                                                }
                                                return 0;

                                            } catch (Exception e) {
                                                return 0;
                                            }
                                        }
                                    });

                                    for (int j = 0; j < data.get(i).getLstSubMealData().size(); j++) {
                                        //JSONObject rowMeal = mealItems.getJSONObject(j);
                                        TodaysMeal meal = new TodaysMeal();
                                        if (j == data.get(i).getLstSubMealData().size()) {
                                            meal.setMeal_type(3);   // last row
                                        } else {
                                            meal.setMeal_type(2);   // middle rows
                                        }
                                        meal.setMeal_type_name(data.get(i).getMealType());
                                        if (data.get(i).getLstSubMealData().get(j).getIntakeTime() != null) {
                                            data.get(i).getLstSubMealData().get(j).getIntakeTime();
                                        }
                                        meal.setIntakeTime(data.get(i).getLstSubMealData().get(j).getIntakeTime());
                                        meal.setMeal_img(data.get(i).getLstSubMealData().get(j).getMealImg());
                                        meal.setMeal_name(data.get(i).getLstSubMealData().get(j).getMealName());
                                        meal.setMeal_quantity(data.get(i).getLstSubMealData().get(j).getMealQty());
                                        meal.setFood_Id(data.get(i).getLstSubMealData().get(j).getRecipeId());
                                        meal.setUserMealId(data.get(i).getLstSubMealData().get(j).getUserMealId());
                                        meal.setUomId(data.get(i).getLstSubMealData().get(j).getUomId());
                                        meal.setUnitText(data.get(i).getLstSubMealData().get(j).getUnitText());
                                        meal.setProtin(data.get(i).getLstSubMealData().get(j).getProtin());
                                        meal.setCarbs(data.get(i).getLstSubMealData().get(j).getCarb());
                                        meal.setFibre(data.get(i).getLstSubMealData().get(j).getFibre());
                                        meal.setFat(data.get(i).getLstSubMealData().get(j).getFat());
                                        meal.setItemTypeId(data.get(i).getLstSubMealData().get(j).getItemTypeId());

                                        double singlecal = data.get(i).getLstSubMealData().get(j).getMealCalory();


                                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                        meal.setRecipeImagePath(data.get(i).getLstSubMealData().get(j).getRecipeImagePath());

                                        meal.setMeal_calory(String.valueOf(decimalFormat.format(singlecal)));
                                        list.add(meal);
                                    }
                                }


//                                Collections.sort(list, new Comparator<TodaysMeal>() {
//
//                                    @Override
//                                    public int compare(TodaysMeal o1, TodaysMeal o2) {
//                                        try {
//                                            return new SimpleDateFormat("hh:mm aa").parse(o1.getIntakeTime()).compareTo(new SimpleDateFormat("hh:mm aa").parse(o2.getIntakeTime()));
//                                        } catch (ParseException e) {
//                                            return 0;
//                                        }
//                                    }
//                                });


                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                String showschedulestr = decimalFormat.format(totalAllFoodCal);


                                if (list!=null&&!list.isEmpty()){
                                    imgdish.setVisibility(View.VISIBLE);
                                }else {
                                    imgdish.setVisibility(View.GONE);
                                }
//                                txt_acual_total_cal_consumed.setText(" Total consumed for the day : "+showschedulestr+" kcal");

                                adapter = new DifferentRowAdapter(MasterFoodFragmentAllMeals.this, list, data);
                                recyclerViewTodays.setLayoutManager(new LinearLayoutManager(context));
                                recyclerViewTodays.setAdapter(adapter);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {

                        if (strfromdatePiker.equalsIgnoreCase("yes")) {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        list.clear();
                        List<TodaysMealData.Datum> datas = new ArrayList<>();
                        adapter = new DifferentRowAdapter(MasterFoodFragmentAllMeals.this, list, datas);
                        recyclerViewTodays.setLayoutManager(new LinearLayoutManager(context));
                        recyclerViewTodays.setAdapter(adapter);


                        if (getUserVisibleHint()) {

                            if (!sessionManager.getStringValue("IsShowMSg").equalsIgnoreCase("false")) {
//                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    }
                }

                sessionManager.setStringValue("IsShowMSg", "true");

            }

            @Override
            public void onFailure(Call<TodaysMealData> call, Throwable t) {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


   /* private void setPiChartData()
    {
        //------------------------Pie chart Data----------------------------
        pieChart.setUsePercentValues(true);
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        ArrayList yvalues = new ArrayList();
        yvalues.add(new PieEntry(12, 0));
        yvalues.add(new PieEntry(32, 1));
        yvalues.add(new PieEntry(16, 2));
        yvalues.add(new PieEntry(40, 3));



        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
        dataSet.setSliceSpace(3);
        ArrayList xVals = new ArrayList();

        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");


        PieData data = new PieData(dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);

        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        pieChart.setDrawHoleEnabled(true);
//        pieChart.setTransparentCircleColor(ContextCompat.getColor(context,  R.color.color_graph_bg));
        pieChart.setTransparentCircleRadius(56f);
        pieChart.setHoleColor(ContextCompat.getColor(context, R.color.color_graph_bg));
        pieChart.setHoleRadius(56f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
//        pieChart.setOnChartValueSelectedListener(this);
//        pieChart.animateXY(1400, 1400);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }*/

    private void shownoplan() {

        final Dialog dialog = new Dialog(context, R.style.CustomDialog);

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

                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvAddMeal:

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();


                } else {
                    if (Utils.isNetworkAvailable(context)) {
                        Intent i = new Intent(context, AddMealActivity.class);
                        i.putExtra("commingFrom", "New");
                        i.putExtra("HistoryDate", sessionManager.getStringValue("statusdate"));

                        sessionManager.setStringValue("backdatesubmit", sessionManager.getStringValue("statusdate"));
                        sessionManager.setStringValue("backdateShow", dummyhistoryshowdate);
                        startActivityForResult(i, 502);
                    } else {
                        Toast.makeText(context, "Check internet connection!", Toast.LENGTH_SHORT).show();
                    }

                }


                break;

            case R.id.rel_weekly: {


                if (listFood != null) {
                    if (listFood.isEmpty()) {

                        Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.clearAnimation();

                    rel_weekly.setBackground(getResources().getDrawable(R.drawable.bg_black_button));

                    imgprocess.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
                    linearLayout.setVisibility(View.GONE);
//                    btnViewMore.setText("View last 7 days");
                } else {
                    linearLayout.clearAnimation();
//                    rel_weekly.setBackground(getResources().getDrawable(R.drawable.bg_green_button));
                    rel_weekly.setBackground(getResources().getDrawable(R.drawable.bg_green_button_new));

                    linearLayout.setVisibility(View.VISIBLE);
                    imgprocess.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));

//                    btnViewMore.setText("Less");
                    linearLayout.startAnimation(animSlideDown);
                }
            }
            break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClickMeal(int position, TodaysMeal model) {
        if (model.getItemTypeId() == 1 || model.getItemTypeId() == 2) {
            return;
        }

        Intent i = new Intent(context, EditMealActivity.class);
        i.putExtra("commingFrom", "Edit");
        i.putExtra("mealItem", (Serializable) model);
        i.putExtra("CreatedonDate", sessionManager.getStringValue("statusdate"));
        sessionManager.setStringValue("backdatesubmit", sessionManager.getStringValue("statusdate"));
        sessionManager.setStringValue("backdateShow", dummyhistoryshowdate);
        startActivityForResult(i, 503);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if (Utils.isNetworkAvailable(context)) {
            if (position == 0) {
                getFoodAnalysis(0);


            } else {
                getFoodAnalysis(position);

            }
        } else {
            Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

        }


//        if (++check > 1)
//        {
//            if (adapterView.getItemAtPosition(position) != null)
//            {
//                String days = adapterView.getItemAtPosition(position).toString();
//                days =  days.substring(0,1);
//
//
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        if (submitHistoryDate.equalsIgnoreCase(year + "-" + (month + 1) + "-" + dayOfMonth)) {
            return;
        }


//        if (StrDateOpen.equalsIgnoreCase("getHistory")) {
        int monthnew = (month + 1);

        strfromdatePiker = "yes";

        if (monthnew < 10) {

            dummyhistoryshowdate = dayOfMonth + "-" + "0" + (month + 1) + "-" + year;
            sessionManager.setStringValue("backdateShow", dummyhistoryshowdate);

        } else {
            dummyhistoryshowdate = dayOfMonth + "-" + (month + 1) + "-" + year;
            sessionManager.setStringValue("backdateShow", dummyhistoryshowdate);


        }
        submitHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;

        sessionManager.setStringValue("backdatesubmit", sessionManager.getStringValue("statusdate"));
        if (!curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date varDate = null;
            try {
                varDate = simpleDateFormat.parse(dummyhistoryshowdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            simpleDateFormat = new SimpleDateFormat("dd-MMM");

//                txt_todaysmeal_header.setText(simpleDateFormat.format(varDate)+"'s Meal");
        } else {
//                txt_todaysmeal_header.setText("Today's Meal");

        }

        if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
//                txt_todaysmeal_header.setText("Today's Meal");

        }

        getAllTodaysMeal();
        getWeeklyAnalysis();
        callFoodHistoryData();
        // getFoodAnalysis(7);
        //getFoodAnalysis(7);
        //  setPiChartData();
        getFoodAnalysis(0);


//                submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


//            CallToFetchRecoachId(true);


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void showDatePickerHistoryAddDailog() {
        String strMindate[] = new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_history = new DatePickerDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MasterFoodFragmentAllMeals.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        if (strMindate.length > 1) {
            if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                c.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]), Integer.parseInt(strMindate[0]));

            }


        }
        c.setTime(today);
        c.add(Calendar.MONTH, 3);
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());
        datepickerdialog_history.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            if (strMindate.length > 1) {
                c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {


            boolean isFromAddMeal = data.getBooleanExtra("ISAddMeal", false);
            if (isFromAddMeal) {
                getAllTodaysMeal();
                getWeeklyAnalysis();
                callFoodHistoryData();
                getFoodAnalysis(1);


            }
        } catch (Exception e) {

        }
    }

    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String formatDates1(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MMM");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd,MMM");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return getFormattedDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getFormattedDate(Date dates) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("d");


        String date = format.format(dates);

        if (date.endsWith("1") && !date.endsWith("11")) {
            str = "st";
            format = new SimpleDateFormat("d'st' MMM");
        } else if (date.endsWith("2") && !date.endsWith("12")) {
            str = "nd";
            format = new SimpleDateFormat("d'nd' MMM");
        } else if (date.endsWith("3") && !date.endsWith("13")) {
            str = "rd";
            format = new SimpleDateFormat("d'rd' MMM");
        } else {
            str = "th";
            format = new SimpleDateFormat("d'th' MMM");
        }

        return format.format(dates);


    }
}
