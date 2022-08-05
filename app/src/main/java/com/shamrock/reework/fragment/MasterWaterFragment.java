package com.shamrock.reework.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsAddWaterRequest;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterAddSuccessData;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterHistoryPojoMain;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterUOMMain;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterDurations;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterHistoryData;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.adapter.GlassAdapter;
import com.shamrock.reework.activity.HomeModule.fragment.HomeFragment;
import com.shamrock.reework.activity.HomeModule.service.GlassModel;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.sleepmodule.WaterHistoryAdapter;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.OnWaterPowerNapClick;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.activity.water.OnwaterVideoListClick;
import com.shamrock.reework.activity.water.WaterVideoListAdapter;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterRequest;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
import com.shamrock.reework.activity.waterhistory.OnEditWaterClick;
import com.shamrock.reework.adapter.MasterWaterAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.WaterRequest;
import com.shamrock.reework.api.response.WaterResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.common.Data;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;
import com.yanzhenjie.wheel.OnWheelChangedListener;
import com.yanzhenjie.wheel.WheelView;
import com.yanzhenjie.wheel.adapters.AbstractWheelAdapter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterWaterFragment extends Fragment implements OnwaterVideoListClick,OnWaterPowerNapClick,OnEditWaterClick,GlassAdapter.GlassInterface, TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ImageView imgprocess_water;
    TextView historydatte;

    RecyclerView recyclerView, rvGlassess;
    private ArrayList<WaterResponse.Data> mDataList;
    MasterWaterAdapter masterWaterAdapter;
    CommonService commonService;
    int countwater;
    RelativeLayout rel_weekly_water;

    /* GLASS ANIMATION */
    GlassAdapter glassAdapter;
    ArrayList<GlassModel> glassList;
    GlassModel model;
    TextView btn_show_water_history;
    TextView txt_water_date_to;
    TextView txt_water_date_from;
    RecyclerView list_water_history;

    TextView tvGoal,txt_take_glass;
    private int userId, mScheduledWaterIntakeForToday, mActualWaterIntakeForToday;
    Utils utils;
    HomeFragmentService service;
    SessionManager sessionManager;
    private SessionManager session;
    private String StrDateOpen="";
    DatePickerDialog datepickerdialog;
    CardView card_weekly_water;




    private RadioGroup radioGroup_water;
    private RadioButton rd_button_sleep_history,rd_button_water_daily,rd_button_water_tips;

    private String dummydate_to="";
    private  String dummydate_from="";

    private String submitToDate="";
    private String submitFromDate="";
    private OnFragmentInteractionListener mListener;
    private Context context;
    ViewFlipper vp_main_water;
    private Utils util;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_food_tips;
    RecyclerView recycler_water_tips;
    private ArrayList<String> arylst_uom_water;
    private ArrayList<String> arylst_uom_water_Millilitre;

    LinearLayout ll_avg_water;
    TextView txt_avg_water;

    String abc;
    //video
    RadioButton rd_button_water_video;
    private RecyclerView recylcer_spiritual_list;
    //    private SessionManager session;
    TextView txt_no_data_spiritual;
    private ArrayList<String> arylst_uom_water_IDs;
    private ArrayList<String> arylst_uom_water_militers;


    TextView txt_sleep_date_to_dialog;
    TextView txt_sleep_date_from_dialog;
    TextView txt_show_sleep_from;
    TextView txt_show_sleep_to;
    TextView txt_no_water_history;
    private int waterUnitPos;
    LinearLayout layout_profile,layout_home,layout_setting;

    public MasterWaterFragment()
    {
        // Required empty public constructor
    }


    private void showDatePickerDailog() {

        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MasterWaterFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (strMindate!=null){
            if (strMindate.length>1){
                c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

            }
        }
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


    public static MasterWaterFragment newInstance(String param1, String param2)
    {
        MasterWaterFragment fragment = new MasterWaterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

//        sessionManager = new SessionManager(getActivity());

        if(context!=null) {

            abc = sessionManager.getStringValue("Allpart");
            if (abc.equals("video")) {
                vp_main_water.setDisplayedChild(3);
                getSpitualListAPiByID(1, "Water videos ");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));


            } else if (abc.equals("tip")) {
                vp_main_water.setDisplayedChild(2);
//            rd_button_water_tips.performClick();
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.white));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));

                callToFetchFoodTipsMasterData();
            } else if (abc.equals("history")) {
                vp_main_water.setDisplayedChild(1);
                callWaterHistoryApi(submitFromDate, submitToDate);
//            rd_button_sleep_history.performClick();
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.white));
            } else if (abc.equals("daily")) {
                vp_main_water.setDisplayedChild(0);
                CallToFetchWaterData();
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new));

                rd_button_water_daily.setTextColor(getResources().getColor(R.color.white));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
            }


        }


//        Toast.makeText(getContext(),"abc",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    public void onStart(){
        super.onStart();
//        Toast.makeText(getContext(),"abc123",Toast.LENGTH_LONG).show();


    }





    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sessionManager = new SessionManager(context);
        service = Client.getClient().create(HomeFragmentService.class);
        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        abc = sessionManager.getStringValue("Allpart");
//        Toast.makeText(getContext(),"abc",Toast.LENGTH_LONG).show();

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
    TextView txt_date,txt_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_water_master, container, false);

        historydatte = view.findViewById(R.id.historydatte);
        imgprocess_water = view.findViewById(R.id.imgprocess_water);
        rel_weekly_water = view.findViewById(R.id.rel_weekly_water);
        txt_date = view.findViewById(R.id.txt_date);
        txt_status = view.findViewById(R.id.txt_status);
        rel_weekly_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (card_weekly_water.getVisibility() == View.VISIBLE) {

                    rel_weekly_water.setBackground(getResources().getDrawable(R.drawable.bg_black_button));

                    imgprocess_water.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
                    card_weekly_water.setVisibility(View.GONE);
//                    btnViewMore.setText("View last 7 days");
                } else {
//                    rel_weekly.setBackground(getResources().getDrawable(R.drawable.bg_green_button));
                    rel_weekly_water.setBackground(getResources().getDrawable(R.drawable.bg_green_button_new));

                    card_weekly_water.setVisibility(View.VISIBLE);
                    imgprocess_water.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));

//                    btnViewMore.setText("Less");
                }
            }
        });
        tvGoal = view.findViewById(R.id.tvTodaysGoal);
        txt_take_glass = view.findViewById(R.id.txt_take_glass);
        vp_main_water = view.findViewById(R.id.vp_main_water);
        rd_button_water_tips = view.findViewById(R.id.rd_button_water_tips);
        rd_button_water_video = view.findViewById(R.id.rd_button_water_video);
        btn_show_water_history = view.findViewById(R.id.btn_show_water_history);
        recycler_water_tips = view.findViewById(R.id.recycler_water_tips);
        txt_no_water_history = view.findViewById(R.id.txt_no_water_history);
        card_weekly_water = view.findViewById(R.id.card_weekly_water);
        txt_avg_water=view.findViewById(R.id.txt_avg_water);
        ll_avg_water=view.findViewById(R.id.ll_avg_water);
//        card_weekly_water


         layout_profile = view.findViewById(R.id.layout_profile);
        layout_home = view.findViewById(R.id.layout_home);
        layout_setting = view.findViewById(R.id.layout_setting);


        layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyProfileActivity.class);
                startActivity(intent);
            }
        });

        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Coming Soon",Toast.LENGTH_LONG).show();
            }
        });




        util=new Utils();


        txt_water_date_from=view.findViewById(R.id.txt_water_date_from);
        txt_water_date_to=view.findViewById(R.id.txt_water_date_to);
        list_water_history=view.findViewById(R.id.list_water_history);
        commonService = Client.getClient().create(CommonService.class);

        radioGroup_water = view.findViewById(R.id.radioGroup_water);
        rd_button_sleep_history = view.findViewById(R.id.rd_button_water_history);
        rd_button_water_daily = view.findViewById(R.id.rd_button_water_daily);


        recylcer_spiritual_list=view.findViewById(R.id.recylcer_spiritual_list);
        txt_no_data_spiritual=view.findViewById(R.id.txt_no_data_spiritual);
        getSpitualListAPiByID(1,"Water videos ");
        sessionManager = new SessionManager(context);
        abc = sessionManager.getStringValue("Allpart");
        if(abc.equals("video")){
            vp_main_water.setDisplayedChild(3);
            getSpitualListAPiByID(1,"Water videos ");
            rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));


            rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
            rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
            rd_button_water_video.setTextColor(getResources().getColor(R.color.white));
            rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));

        }else  if(abc.equals("tip")){
            vp_main_water.setDisplayedChild(2);
//            rd_button_water_tips.performClick();
            rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));

            rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
            rd_button_water_tips.setTextColor(getResources().getColor(R.color.white));
            rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
            rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
            callToFetchFoodTipsMasterData();
        }else  if(abc.equals("history")){
            vp_main_water.setDisplayedChild(1);
            callWaterHistoryApi(submitFromDate,submitToDate);
//            rd_button_sleep_history.performClick();
            rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
            rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
            rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
            rd_button_sleep_history.setTextColor(getResources().getColor(R.color.white));
        }else  if(abc.equals("daily")){
            vp_main_water.setDisplayedChild(0);
            CallToFetchWaterData();
            rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_water_daily.setTextColor(getResources().getColor(R.color.white));
            rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
            rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
            rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
        }

        CallWaterUOMApis();


        String strText1="";
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


                if (strText1.equals(formattedDate)) {
                    txt_date.setText(strText1);
                    txt_status.setText("Today's Goal");
                } else {
                    txt_date.setText(strText1);
                    txt_status.setText("Earlier Goal");
                }


//
            } else {


                strText1 = sessionManager.getStringValue("showDate");
                txt_date.setText(strText1);
                txt_status.setText("Today's Goal");
//                String abc[]=strText.split(" ");
//

//                txt_todaysmeal_headernew.setVisibility(View.GONE);


            }
        }





        rd_button_water_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main_water.setDisplayedChild(0);
                CallToFetchWaterData();
                session = new SessionManager(context);
                session.setStringValue("Allpart","daily");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new));

                rd_button_water_daily.setTextColor(getResources().getColor(R.color.white));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));


            }
        });
        rd_button_sleep_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main_water.setDisplayedChild(1);
                callWaterHistoryApi(submitFromDate,submitToDate);
                session = new SessionManager(context);
                session.setStringValue("Allpart","history");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));


                rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.white));


            }
        });


        rd_button_water_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callToFetchFoodTipsMasterData();
                vp_main_water.setDisplayedChild(2);
                session = new SessionManager(context);
                session.setStringValue("Allpart","tip");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));


                rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.white));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
            }
        });


        rd_button_water_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpitualListAPiByID(1,"Water videos ");
                session = new SessionManager(context);
                session.setStringValue("Allpart","video");
                vp_main_water.setDisplayedChild(3);
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_water_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_water_daily.setBackgroundResource((R.drawable.custom_white_radio_new1));


                rd_button_water_daily.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_water_video.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));

            }
        });

        submitFromDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        dummydate_from=formatDatesSleep(submitFromDate);
        txt_water_date_from.setText(dummydate_from);
        dummydate_to=formatDatesSleep(submitToDate);
        txt_water_date_to.setText(dummydate_to);





        showDatePickerDailog();
        CallWaterUOMApi(view);
//        callWaterHistoryApi(submitFromDate,submitToDate);

        btn_show_water_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWaterHistoryApi(submitFromDate,submitToDate);

            }
        });

        txt_water_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from";
                datepickerdialog.show();


            }
        });

        txt_water_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "to";
                datepickerdialog.show();


            }
        });
        final Animation animSlideDown, animSlideUp;
        animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        final Button buttonAllMeal_ViewMore=view.findViewById(R.id.buttonAllMeal_ViewMore);
        buttonAllMeal_ViewMore.performClick();
        buttonAllMeal_ViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDataList != null) {
                    if (mDataList.isEmpty()) {

                        Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (card_weekly_water.getVisibility() == View.VISIBLE) {
                    card_weekly_water.clearAnimation();
                    card_weekly_water.setVisibility(View.VISIBLE);
                    buttonAllMeal_ViewMore.setText("View last 7 days");
                } else {
                    card_weekly_water.clearAnimation();
                    card_weekly_water.setVisibility(View.VISIBLE);
                    buttonAllMeal_ViewMore.setText("Less");
                    card_weekly_water.startAnimation(animSlideDown);
                }








            }
        });





        //SET WEEKLY ANALYSIS
        recyclerView = view.findViewById(R.id.recycleView_Water);
        mDataList = new ArrayList<>();
        masterWaterAdapter = new MasterWaterAdapter(context, mDataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(masterWaterAdapter);

        rvGlassess = view.findViewById(R.id.rvGlassess);
        glassList = new ArrayList<>();
        glassAdapter = new GlassAdapter(context, glassList, this);
        RecyclerView.LayoutManager layoutManagerGlass = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvGlassess.setLayoutManager(layoutManagerGlass);
        rvGlassess.setItemAnimator(new DefaultItemAnimator());
        rvGlassess.setAdapter(glassAdapter);

        ImageView ll_add_water=view.findViewById(R.id.ll_add_water);
        ll_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();

                    return;

                }

                showDynamicAddWaterDailog(6);
            }
        });

//        rd_button_water_daily.performClick();

        if (Utils.isNetworkAvailable(context))
        {
            CallToFetchWaterData();
//            callToFetchFoodTipsMasterData();
        }
        else
            Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

        return view;
    }
    private void shownoplan() {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe=dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no=dialog.findViewById(R.id.btn_subscribe_no);
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

                Intent  intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }


    public class TestAdapter extends AbstractWheelAdapter {
        ArrayList<String> str_qnty;

        public TestAdapter(ArrayList<String> str_qnty) {
            this.str_qnty=str_qnty;
        }

        @Override
        public int getItemsCount() {
            return str_qnty.size();
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            View view=LayoutInflater.from(getContext()).inflate(R.layout.row_test,parent,false);
            TextView txt_unit_name=view.findViewById(R.id.txt_unit_name);
            txt_unit_name.setText(str_qnty.get(index).toString());




            return view;
        }
    }

    private void showDynamicAddWaterDailogol(final int actualWaterIntake) {


        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_add_water);
        final Spinner spinner_add_uom_water = dialog.findViewById(R.id.spinner_add_uom_water);

        if (arylst_uom_water != null && !arylst_uom_water.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_white, arylst_uom_water);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_add_uom_water.setAdapter(adapter);


        }

        ImageView close_add_water = dialog.findViewById(R.id.close_add_water);
        close_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText edt_add_actual_water = dialog.findViewById(R.id.edt_add_actual_water);
        edt_add_actual_water.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});
        Button btn_add_water = dialog.findViewById(R.id.btn_add_water);

        btn_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (edt_add_actual_water.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double water = Double.parseDouble(edt_add_actual_water.getText().toString());
                    int poswater = spinner_add_uom_water.getSelectedItemPosition();
                    int uomid = Integer.parseInt(arylst_uom_water_IDs.get(poswater));
                    dialog.dismiss();
                    ClsAddWaterRequest clsAddWaterRequest = new ClsAddWaterRequest();
                    clsAddWaterRequest.setWaterIntake(water);
                    clsAddWaterRequest.setChanged(false);
                    clsAddWaterRequest.setUoMId(uomid);
                    clsAddWaterRequest.setTodayStatusId(Integer.parseInt(sessionManager.getStringValue("key_todaysstauusId")));
                    clsAddWaterRequest.setEntryDate(sessionManager.getStringValue("statusdate"));
                    clsAddWaterRequest.setReeworkerId(userId);
                    clsAddWaterRequest.setId(0);
                    addWaterApi(clsAddWaterRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        dialog.show();

    }

    private void showDynamicAddWaterDailog(final int actualWaterIntake) {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme1);
        dialog.setContentView(R.layout.dialog_add_water);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);


        final Spinner spinner_add_uom_water = dialog.findViewById(R.id.spinner_add_uom_water);

        if (arylst_uom_water != null && !arylst_uom_water.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_white, arylst_uom_water);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_add_uom_water.setAdapter(adapter);


        }



        final ArrayList<String> str_qnty=new ArrayList<>();

        for (int i = 0; i <arylst_uom_water.size() ; i++) {
            str_qnty.add(arylst_uom_water.get(i).toString());
        }
        WheelView wheal_list_uom=dialog.findViewById(R.id.wheal_list_uom);
        TextView waterheader=dialog.findViewById(R.id.waterheader);
        TextView waterheader1=dialog.findViewById(R.id.waterheader1);
        waterheader.setVisibility(View.VISIBLE);
        wheal_list_uom.setAdapter(new TestAdapter(arylst_uom_water));
        Drawable centerFilter = context.getResources().getDrawable(R.drawable.quantity_selector);
        wheal_list_uom.setCenterFilter(centerFilter);
        wheal_list_uom.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                waterUnitPos=newValue;

            }
        });

        ImageView close_add_water = dialog.findViewById(R.id.close_add_water);
        close_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText edt_add_actual_water = dialog.findViewById(R.id.edt_add_actual_water);
        edt_add_actual_water.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});
        Button btn_add_water = dialog.findViewById(R.id.btn_add_water);

        btn_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (edt_add_actual_water.getText().toString().isEmpty()) {
                        Toast.makeText(context, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double water = Double.parseDouble(edt_add_actual_water.getText().toString());
                    int poswater = waterUnitPos;
                    int uomid = Integer.parseInt(arylst_uom_water_IDs.get(poswater));
                    dialog.dismiss();
                    ClsAddWaterRequest clsAddWaterRequest = new ClsAddWaterRequest();
                    clsAddWaterRequest.setWaterIntake(water);
                    clsAddWaterRequest.setChanged(false);
                    clsAddWaterRequest.setUoMId(uomid);
                    clsAddWaterRequest.setTodayStatusId(Integer.parseInt(sessionManager.getStringValue("key_todaysstauusId")));
                    if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
                        clsAddWaterRequest.setEntryDate(sessionManager.getStringValue("Entrystatusdate"));

                    }else {
                        clsAddWaterRequest.setEntryDate(sessionManager.getStringValue("statusdate"));

                    }
                    clsAddWaterRequest.setReeworkerId(userId);
                    clsAddWaterRequest.setId(0);
                    addWaterApi(clsAddWaterRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        DecimalFormat decimalFormat = new DecimalFormat("0.00");


        double scheduleintake = Double.valueOf(mDataList.get(0).getScheduledWaterIntake()) / 100;

        String showschedule = decimalFormat.format(scheduleintake);


        double actualintake = Double.valueOf(mDataList.get(0).getActualWaterIntake()) / 100;
        String showActual = decimalFormat.format(actualintake);


        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1 = new SpannableString(showActual);
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lightblack)), 0, str1.length(), 0);
        str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(str1 +" Litres");
//        builder.append(" of " + showschedule + " Litres");


        waterheader.setText(builder);
        if(actualintake==0.0){
            waterheader1.setText(scheduleintake
                    + " Litres Pending");
        }else {

            waterheader1.setText(decimalFormat.format(scheduleintake - actualintake)
                    + " Litres Pending");

        }
        dialog.show();

    }



    private void adddailyEntryDialg(View dialog) {

        final Spinner spinner_add_uom_water=dialog.findViewById(R.id.spinner_add_uom_water);

        if (arylst_uom_water!=null&&!arylst_uom_water.isEmpty()){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_uom_water);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_add_uom_water.setAdapter(adapter);


        }

        ImageView close_add_water=dialog.findViewById(R.id.close_add_water);

        final EditText edt_add_actual_water=dialog.findViewById(R.id.edt_add_actual_water);
        edt_add_actual_water.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});

        Button btn_add_water=dialog.findViewById(R.id.btn_add_water);

        btn_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (edt_add_actual_water.getText().toString().isEmpty()){
                        Toast.makeText(context, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double water = Double.parseDouble(edt_add_actual_water.getText().toString());
                    int poswater = spinner_add_uom_water.getSelectedItemPosition();
                    int uomid = Integer.parseInt(arylst_uom_water_IDs.get(poswater));
                    ClsAddWaterRequest clsAddWaterRequest=new ClsAddWaterRequest();
                    clsAddWaterRequest.setWaterIntake(water);
                    clsAddWaterRequest.setChanged(false);
                    clsAddWaterRequest.setUoMId(uomid);
                    clsAddWaterRequest.setTodayStatusId(Integer.parseInt(sessionManager.getStringValue("key_todaysstauusId")));
                    clsAddWaterRequest.setEntryDate(sessionManager.getStringValue("statusdate"));
                    clsAddWaterRequest.setReeworkerId(userId);
                    clsAddWaterRequest.setId(0);

                    edt_add_actual_water.setText("");
                    addWaterApi(clsAddWaterRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    private void showNewgetSleephistroy() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog=dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog=dialog.findViewById(R.id.txt_sleep_date_to_dialog);


        Button  btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    dialog.dismiss();

                    callWaterHistoryApi(submitFromDate,submitToDate);
                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

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

    private void CallWaterUOMApi(final View view) {


        Call<ClsWaterUOMMain> call = service.GetWaterUoMApi();

        call.enqueue(new Callback<ClsWaterUOMMain>() {
            @Override
            public void onResponse(Call<ClsWaterUOMMain> call, Response<ClsWaterUOMMain> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterUOMMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() .equals("1") ) {

                        if (listResponse.getData()!=null&&!listResponse.getData().isEmpty()){
                            arylst_uom_water=new ArrayList<String>();
                            arylst_uom_water_IDs =new ArrayList<String>();
                            arylst_uom_water_militers =new ArrayList<String>();
                            for (int i = 0; i <listResponse.getData().size() ; i++) {
                                arylst_uom_water.add(listResponse.getData().get(i).getUnit());
                                arylst_uom_water_IDs.add(String.valueOf(listResponse.getData().get(i).getId()));
                                arylst_uom_water_militers.add(String.valueOf(listResponse.getData().get(i).getMillilitre()));
                            }

                        }

                        adddailyEntryDialg(view);



                        Log.d("FCM REGISTER--->", "FCM REGISTER SUCCESSFULLY");


                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ClsWaterUOMMain> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }

    private void CallToFetchWaterData()
    {
        utils.showProgressbar(context);

        WaterRequest request = new WaterRequest();
        request.setUserID(userId);

        Call<WaterResponse> call = service.getWaterData(request);
        call.enqueue(new Callback<WaterResponse>()
        {
            @Override
            public void onResponse(Call<WaterResponse> call, retrofit2.Response<WaterResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    WaterResponse waterResponse = response.body();

                    if (waterResponse != null && waterResponse.getCode() == 1)
                    {
                        if (waterResponse.getData() != null&&!waterResponse.getData().isEmpty())
                        {
//                            card_weekly_water.setVisibility(View.VISIBLE);
                            setTodaysGoal(waterResponse.getData());

                            mDataList.clear();
                            mDataList.addAll(waterResponse.getData());
                            masterWaterAdapter.notifyDataSetChanged();



                        }else {
//                            card_weekly_water.setVisibility(View.GONE);
                        }
                    }
                    else{
//                        card_weekly_water.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<WaterResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    public void setTodaysGoal(ArrayList<WaterResponse.Data> list)
    {
        Date tDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);

        if (list!= null)
        {
            for (int i=0; i<list.size(); i++)
            {
                if (todayDate.equalsIgnoreCase(formatDates(list.get(i).getCreatedOn())))
                {
                    mScheduledWaterIntakeForToday = list.get(i).getScheduledWaterIntake();
                    mActualWaterIntakeForToday = list.get(i).getActualWaterIntake();


                    String strmActualWaterIntakeForToday="0";
                    if (mScheduledWaterIntakeForToday <10000000){
                        try {

                            int scheduleintake;
                            String strmScheduledWaterIntakeForToday="";
                            DecimalFormat decimalFormat = new DecimalFormat("0.00");
                            if (mScheduledWaterIntakeForToday>0){
                                scheduleintake =  mScheduledWaterIntakeForToday / 25;
                                strmScheduledWaterIntakeForToday = decimalFormat.format(scheduleintake);
                            }else {
                                strmScheduledWaterIntakeForToday = decimalFormat.format(0.00);

                            }


                            int scheduleintake1;

                            if (mActualWaterIntakeForToday>0){
                                scheduleintake1 = mActualWaterIntakeForToday/ 25;
                                strmActualWaterIntakeForToday = decimalFormat.format(scheduleintake1);
                            }

                            int    colorBlue = ContextCompat.getColor(context, R.color.colorRobinEggBlue);


                            SpannableStringBuilder builder = new SpannableStringBuilder();
                            builder.append("Today's goal is ");

                            SpannableString str1 = new SpannableString(String.valueOf(Math.round(Float.parseFloat(strmScheduledWaterIntakeForToday))));
                            str1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str1.length(), 0);
                            str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            str1.setSpan(new RelativeSizeSpan(1.1f),0, str1.length(),0 );
                            builder.append(str1);

                            builder.append(" glass of water ");

                            SpannableString str2 = new SpannableString("");
                            str2.setSpan(new ForegroundColorSpan(colorBlue), 0, str2.length(), 0);
                            str2.setSpan(new StyleSpan(Typeface.BOLD), 0, str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            str2.setSpan(new RelativeSizeSpan(1.1f),0, str2.length(),0 );

                            builder.append(str2);
                            builder.append("");








//                            tvGoal.setText("Today's goal is " + strmScheduledWaterIntakeForToday + " glass of water, " +
//                                    "you took " + strmActualWaterIntakeForToday + " glass of water.");
                            tvGoal.setText(builder);
                            txt_take_glass.setText(String.valueOf(Math.round(Float.parseFloat(strmScheduledWaterIntakeForToday)))+" Glass");
                        }catch (Exception e){
                            e.printStackTrace();
                            tvGoal.setText("Today's goal is " + strmActualWaterIntakeForToday + " Litres of water");
                        }
                        break;
                    }








                }
            }
        }

        if (mActualWaterIntakeForToday>mScheduledWaterIntakeForToday){
            setGlassData(mScheduledWaterIntakeForToday, mActualWaterIntakeForToday);

        }else {
            setGlassData(mScheduledWaterIntakeForToday, mActualWaterIntakeForToday);

        }
    }

    public void setGlassData(int scheduleWaterIntakes, int actualWaterIntakes)
    {
        ArrayList<GlassModel> tempGlassList = new ArrayList<>();
        int scheduleWaterIntake =0;
        if (scheduleWaterIntakes>=25){
            scheduleWaterIntake=scheduleWaterIntakes/25;

        }
        int actualWaterIntake=0;

        if (actualWaterIntakes>=25){
            actualWaterIntake=actualWaterIntakes/25;

        }

        if (scheduleWaterIntake == 0) {

            scheduleWaterIntake=actualWaterIntake;
        }



        for (int i=0; i < scheduleWaterIntake; i++)
        {
            model = new GlassModel();
            model.setScheduleWaterIntake(i+1);
            tempGlassList.add(model);
        }
        if (actualWaterIntake <= scheduleWaterIntake)
        {
            for (int i=0; i < actualWaterIntake; i++)
            {
                tempGlassList.get(i).setActualWaterIntake(i+1);
            }
        }
        glassList.clear();
        glassList.addAll(tempGlassList);
        glassAdapter.notifyDataSetChanged();
    }

    public String formatDatesHistoy(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return dateFromServer;



    }



    public String formatDates(String dateFromServer)
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

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
        if (radioGroup_water!=null){
            rd_button_water_daily.setChecked(true);
        }

    }

    @Override
    public void glassPosition(int pos){}

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

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
//            dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;
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
                Toast.makeText(context, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_water_date_from.setText(dummydate_from);

            submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;
            callWaterHistoryApi(submitFromDate,submitToDate);



        }


        if (StrDateOpen.equalsIgnoreCase("to")) {


            if (!dummydate_from.trim().isEmpty()){




                if (month<10){
                    dummydate_to = dayOfMonth + "-" + "0"+(month + 1) + "-" + year;

                }else {
                    dummydate_to = dayOfMonth + "-" +(month + 1) + "-" + year;

                }



//                dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;




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
                    txt_water_date_to.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;

                    callWaterHistoryApi(submitFromDate,submitToDate);

                }






            }else {
                Toast.makeText(context, "Please select from date", Toast.LENGTH_SHORT).show();
            }



        }

    }


    private void callWaterHistoryApi(String submitFromDate, String submitToDate) {


        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)));

        historydatte.setText(formatDatesHistoy(dummydate_from)+" to "+formatDatesHistoy(dummydate_to));

//        historydatte

//            utils.showProgressbar(context);
        Call<ClsWaterHistoryPojoMain> call = commonService.getWaterActivityHistory(clsHistoryRequest);
        call.enqueue(new Callback<ClsWaterHistoryPojoMain>()
        {
            @Override
            public void onResponse(Call<ClsWaterHistoryPojoMain> call, retrofit2.Response<ClsWaterHistoryPojoMain> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){



                    try {
                        ClsWaterHistoryPojoMain moodResponse = response.body();
                        if (moodResponse.getCode()!=null){

                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    if (!moodResponse.getData().isEmpty()){
                                        list_water_history.setVisibility(View.VISIBLE);
                                        txt_no_water_history.setVisibility(View.GONE);

                                        ll_avg_water.setVisibility(View.GONE);


                                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                        double scheduleintake = Double.parseDouble(moodResponse.getAvgWaterIntake());
                                        String showschedulestr = decimalFormat.format(scheduleintake);

                                        txt_avg_water.setText(" "+showschedulestr+" Litre");
                                        ArrayList<WaterHistoryData> dataArrayList=moodResponse.getData();
                                        list_water_history.setAdapter(new WaterHistoryAdapter(context,dataArrayList,MasterWaterFragment.this));
                                        CallToFetchWaterData();
                                    }else {
                                        ll_avg_water.setVisibility(View.GONE);

                                        list_water_history.setVisibility(View.GONE);
                                        txt_no_water_history.setVisibility(View.VISIBLE);

//




                                    }

                                }else {
                                    ll_avg_water.setVisibility(View.GONE);


                                    list_water_history.setVisibility(View.GONE);
                                    txt_no_water_history.setVisibility(View.VISIBLE);
                                    if (vp_main_water.getDisplayedChild()==1){
//                                            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();

                                    }
                                }





                            }else {
                                if (vp_main_water.getDisplayedChild()==1){
                                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();

                                }

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
            public void onFailure(Call<ClsWaterHistoryPojoMain> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });








    }


    private void callSleepTimeUpdateApi(ClsEditWaterRequest clsEditSleepRequest){

        utils.showProgressbar(context);



        Call<ClsEditWaterResonse> call = commonService.setUpdateWaterHistoy(clsEditSleepRequest);
        call.enqueue(new Callback<ClsEditWaterResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditWaterResonse> call, retrofit2.Response<ClsEditWaterResonse> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsEditWaterResonse moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){


                                Toast.makeText(getContext(), ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                callWaterHistoryApi(submitFromDate,submitToDate);





                            }else {

                                Toast.makeText(getContext(), ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsEditWaterResonse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void callToFetchFoodTipsMasterData() {

        util.showProgressbar(getActivity());

        Call<ClsSleepTips> call = service.getMasterFoodTipsData(5);
        call.enqueue(new Callback<ClsSleepTips>() {
            @Override
            public void onResponse(Call<ClsSleepTips> call, retrofit2.Response<ClsSleepTips> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsSleepTips tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {

                            if (!tipsResponse.getData().isEmpty()){
                                arylst_food_tips=new ArrayList<>();
                                arylst_food_tips.clear();
                                arylst_food_tips.addAll(tipsResponse.getData());



                                ClsSleepTipsAdapter adapter=  new ClsSleepTipsAdapter(getContext(), arylst_food_tips, "MasterSleepFragment");
                                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                                recycler_water_tips.setLayoutManager(layoutManager1);
                                recycler_water_tips.setItemAnimator(new DefaultItemAnimator());
                                recycler_water_tips.setAdapter(adapter);

                            }else {
//                                Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else{

                    }
//                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else{

                }
//                    Toast.makeText(getActivity(), "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ClsSleepTips> call, Throwable t) {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }




    @Override
    public void getEditWaterPosition(final Data data) {


        try {
            if (data.getActualWaterIntake() != null) {
                countwater = Integer.parseInt(data.getActualWaterIntake());

            }

            final Dialog dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setContentView(R.layout.dialog_water_edit);
            EditText edt_schedule_water_intake = dialog.findViewById(R.id.edt_schedule_water_intake);
            final EditText edt_actual_water_hours_history = dialog.findViewById(R.id.edt_actual_water_hours_history);
            edt_actual_water_hours_history.setFilters(new InputFilter[]{new DigitsInputFilter(2, 2, 100)});

            ImageView img_minus_water = dialog.findViewById(R.id.img_minus_water);
            ImageView img_plus_water = dialog.findViewById(R.id.img_plus_water);
            ImageView close_slepp_histoy_dialog = dialog.findViewById(R.id.close_slepp_histoy_dialog);
            close_slepp_histoy_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Button btn_update_water_hitostory = dialog.findViewById(R.id.btn_update_water_hitostory);
            btn_update_water_hitostory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ClsEditWaterRequest clsEditWaterRequest = new ClsEditWaterRequest();
                    clsEditWaterRequest.setActualValue(String.valueOf(countwater));
                    clsEditWaterRequest.setReportType(1);
                    clsEditWaterRequest.setStatusId(Integer.parseInt(data.getId()));
                    callSleepTimeUpdateApi(clsEditWaterRequest);
                    dialog.dismiss();


                }
            });
            img_plus_water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countwater++;

                    edt_actual_water_hours_history.setText(String.valueOf(countwater) + " glasses");

                }
            });
            img_minus_water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (countwater == 0) {
                        return;
                    }
                    countwater--;
                    if (countwater > 1) {
                        edt_actual_water_hours_history.setText(String.valueOf(countwater) + " glasses");

                    } else {
                        edt_actual_water_hours_history.setText(String.valueOf(countwater) + " glass");

                    }
                }
            });
            if (Integer.parseInt(data.getActualWaterIntake()) > 1) {
                edt_actual_water_hours_history.setText(data.getActualWaterIntake() + " glasses");

            } else {
                edt_actual_water_hours_history.setText(data.getActualWaterIntake() + " glass");

            }
            edt_schedule_water_intake.setText(data.getScheduledWaterIntake() + " glasses");
            dialog.show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateWaterPowerNap(WaterDurations waterDurations, String waterEntryDate) {

        SessionManager  sessionManager = new SessionManager(context);
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
        }else {
            showDynamicAddWaterDailog(waterDurations,waterEntryDate);

        }


    }

    @Override
    public void deleteWaterPowerNap(int id) {

        deletewater(String.valueOf(id));


    }

    private void deletewater(String id) {
        utils.showProgressbar(context);
        Call<ClsEditSleepResonse> call = commonService.deletewaterPowernapHistroy(id);
        call.enqueue(new Callback<ClsEditSleepResonse>() {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response) {
                utils.hideProgressbar();
                RDPSuccess rdpSuccess;
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    if (response.body().getCode().equals("1")) {



                        if (response.body().getData() != null) {
                            Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                            callWaterHistoryApi(submitFromDate,submitToDate);

                        }

                    } else {

                        Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void showDynamicAddWaterDailog(final WaterDurations waterDurations, final String entrydate) {


        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme1);
        dialog.setContentView(R.layout.dialog_update_water);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);


        final Spinner spinner_add_uom_water = dialog.findViewById(R.id.spinner_add_uom_water);

        if (arylst_uom_water != null && !arylst_uom_water.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_white, arylst_uom_water);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_add_uom_water.setAdapter(adapter);


        }



        final ArrayList<String> str_qnty=new ArrayList<>();

        for (int i = 0; i <arylst_uom_water.size() ; i++) {
            str_qnty.add(arylst_uom_water.get(i).toString());
        }
        WheelView wheal_list_uom=dialog.findViewById(R.id.wheal_list_uom);
        TextView waterheader=dialog.findViewById(R.id.waterheader);
        waterheader.setVisibility(View.GONE);
        wheal_list_uom.setAdapter(new TestAdapter(arylst_uom_water));
        Drawable centerFilter = context.getResources().getDrawable(R.drawable.quantity_selector);
        wheal_list_uom.setCenterFilter(centerFilter);
        wheal_list_uom.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                waterUnitPos=newValue;

            }
        });

        ImageView close_add_water = dialog.findViewById(R.id.close_add_water);
        close_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText edt_add_actual_water = dialog.findViewById(R.id.edt_add_actual_water);
        edt_add_actual_water.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});
        Button btn_add_water = dialog.findViewById(R.id.btn_add_water);


//        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
//        dialog.setContentView(R.layout.dialog_add_water);
//        final Spinner spinner_add_uom_water=dialog.findViewById(R.id.spinner_add_uom_water);
//
//
//
//
//
//        if (arylst_uom_water!=null&&!arylst_uom_water.isEmpty()){
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item_white, arylst_uom_water);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner_add_uom_water.setAdapter(adapter);
//            for (int i = 0; i <arylst_uom_water.size() ; i++) {
//                if (waterDurations.getUnit().equalsIgnoreCase(arylst_uom_water.get(i))){
//                    spinner_add_uom_water.setSelection(i);
//                    break;
//                }
//            }
//        }
//
//        ImageView close_add_water=dialog.findViewById(R.id.close_add_water);
//        close_add_water.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        final EditText edt_add_actual_water=dialog.findViewById(R.id.edt_add_actual_water);
//        edt_add_actual_water.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 1000)});
//
//
//        try {
//            String strqty = String.valueOf(waterDurations.getWaterInTake());
//            String[] strNewQty = strqty.split("\\.");
//            boolean isFound = false;
//
//
//            if (strNewQty[1].equals("0")) {
//                isFound = true;
//
//            }
//            if (isFound) {
//                isFound = false;
////            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
//                edt_add_actual_water.setText("" + strNewQty[0].toString());
////                edt_add_actual_water.setText(String.valueOf(waterDurations.getWaterInTake()));
//
//
//            } else {
//                edt_add_actual_water.setText(String.valueOf(waterDurations.getWaterInTake()));
//
//            }
//
//        }catch (Exception e){
//            edt_add_actual_water.setText(String.valueOf(waterDurations.getWaterInTake()));
//
//        }
//
//
//
//        Button btn_add_water=dialog.findViewById(R.id.btn_add_water);

        btn_add_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (edt_add_actual_water.getText().toString().isEmpty()){
                        Toast.makeText(context, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int poswater = spinner_add_uom_water.getSelectedItemPosition();
                    int uomid = Integer.parseInt(arylst_uom_water_Millilitre.get(poswater));

//                    int waterQuantity = totalwaterActual * perunit;
//                    mHomeModel.setActualWaterIntake(waterQuantity);
//                    CallForUpdatingData(mHomeModel, false);
                    dialog.dismiss();

                    ClsAddWaterRequest clsAddWaterRequest=new ClsAddWaterRequest();
                    clsAddWaterRequest.setWaterIntake(Double.parseDouble(edt_add_actual_water.getText().toString()));
                    clsAddWaterRequest.setChanged(false);
                    clsAddWaterRequest.setUoMId(uomid);
                    clsAddWaterRequest.setTodayStatusId(waterDurations.getTodayStatusId());

                    int index=entrydate.indexOf("T");
                    String str_old_history_date=entrydate.substring(0,index);
                    clsAddWaterRequest.setEntryDate(str_old_history_date);
                    clsAddWaterRequest.setReeworkerId(userId);
                    clsAddWaterRequest.setId(waterDurations.getId());

                    addWaterApi(clsAddWaterRequest);





                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



        dialog.show();

    }



    private void addWaterApi(ClsAddWaterRequest clsAddWaterRequest) {


        utils.showProgressbar(context);
        Call<ClsWaterAddSuccessData> call = service.SetWaterActivityHistory(clsAddWaterRequest);

        call.enqueue(new Callback<ClsWaterAddSuccessData>() {
            @Override
            public void onResponse(Call<ClsWaterAddSuccessData> call, Response<ClsWaterAddSuccessData> response) {

                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterAddSuccessData listResponse = response.body();

                    if (listResponse != null && listResponse.getCode()==1 ) {


                        callWaterHistoryApi(submitFromDate,submitToDate);
                        CallToFetchWaterData();


                        Toast.makeText(context, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("FCM REGISTER--->", "FCM REGISTER SUCCESSFULLY");


                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ClsWaterAddSuccessData> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());

                utils.hideProgressbar();

            }
        });
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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void CallWaterUOMApis()
    { Call<ClsWaterUOMMain> call = service.GetWaterUoMApi();

        call.enqueue(new Callback<ClsWaterUOMMain>() {
            @Override
            public void onResponse(Call<ClsWaterUOMMain> call, Response<ClsWaterUOMMain> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterUOMMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() .equals("1") ) {

                        if (listResponse.getData()!=null&&!listResponse.getData().isEmpty()){
                            arylst_uom_water=new ArrayList<String>();
                            arylst_uom_water_Millilitre=new ArrayList<String>();
                            for (int i = 0; i <listResponse.getData().size() ; i++) {
                                arylst_uom_water.add(listResponse.getData().get(i).getUnit());
                                arylst_uom_water_Millilitre.add(String.valueOf(listResponse.getData().get(i).getId()));
                            }

                        }



                        Log.d("FCM REGISTER--->", "FCM REGISTER SUCCESSFULLY");


                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ClsWaterUOMMain> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }

    private void getSpitualListAPiByID(int id, final String libraryName){


        utils = new Utils();
        session = new SessionManager(getActivity());
        try {
            utils.showProgressbar(getActivity());
        }catch (Exception e){
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritualListMain> call = commonService.getDailyDiaryVideoByCategoryId(id);
        call.enqueue(new Callback<ClsSpiritualListMain>()
        {
            @Override
            public void onResponse(Call<ClsSpiritualListMain> call, retrofit2.Response<ClsSpiritualListMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsSpiritualListMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){
                                    txt_no_data_spiritual.setVisibility(View.GONE);
                                    recylcer_spiritual_list.setVisibility(View.VISIBLE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=moodResponse.getData();

                                    recylcer_spiritual_list.setAdapter(new WaterVideoListAdapter(MasterWaterFragment.this,arylst_SpiritualTypeData));


                                }else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName+" not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new WaterVideoListAdapter(MasterWaterFragment.this,arylst_SpiritualTypeData));

                                }






                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ClsSpiritualListMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    public void getFoodVideoData(String videoLink, String title, String description) {

        Intent intent=new Intent(getActivity(), ExoActivity.class);
        intent.putExtra("VideoID",extractYTId(videoLink));
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        startActivity(intent);




    }
    public String extractYTId(String url) {


        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        return null;

    }
    public String youTubeLinkWithoutProtocolAndDomain(String url) {
        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
    }


}
