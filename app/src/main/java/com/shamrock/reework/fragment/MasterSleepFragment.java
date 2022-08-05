package com.shamrock.reework.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.adapter.RepeatSleepListAdapter;
import com.shamrock.reework.activity.HomeModule.adapter.SleepFragAdapter;
import com.shamrock.reework.activity.HomeModule.fragment.HomeFragment;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.sleepmodule.ClsRepeatSleepData;
import com.shamrock.reework.activity.sleepmodule.ClsupdateSleepRequest;
import com.shamrock.reework.activity.sleepmodule.Data;
import com.shamrock.reework.activity.sleepmodule.OnEditSleepClick;
import com.shamrock.reework.activity.sleepmodule.OnSleepVideoListClick;
import com.shamrock.reework.activity.sleepmodule.SleepActivityCustom;
import com.shamrock.reework.activity.sleepmodule.SleepVideoListAdapter;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.ClsDemoSleep;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.ClsNewEditSleepRequest;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.OnPowerNapClick;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepData;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepDurations;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepHistoryAdapter;
import com.shamrock.reework.activity.sleepmodule.repeatSleep.ClsRepeatSleepMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.activity.userhistory.MyHistoryAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.WaterRequest;
import com.shamrock.reework.api.response.SleepResponse;
import com.shamrock.reework.api.response.TipsResponce;
import com.shamrock.reework.common.ClsHistoryRequest;
//import com.shamrock.reework.common.Data;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.DecimalFormat;
import java.text.Format;
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

public class MasterSleepFragment extends Fragment implements OnSleepVideoListClick,OnPowerNapClick, OnEditSleepClick, Animation.AnimationListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    Button btn_submit_sleep_hours_daily;
    private String mParam2;
    private boolean isRepeatfound=false;

    private OnFragmentInteractionListener mListener;
    DatePickerDialog datepickerdialog;
    TextView historydatte;
    LinearLayout ll_add_history;
    TextView txt_sleep_date;
    EditText edt_actual_sleep_hours_history;
    Context context;
    TextView tvSchedule_sleep;
    LinearLayout linearLayout;
    LinearLayout ll_sleep_date;
    Button button;
    View rootLayout, cardFace, cardBack;
    TextView  tvActual;
    CircularProgressBar circularProgressBar;
    private String strFromEditDailg;
    TextView btn_show_sleep_history;
    RelativeLayout frameRoot_Sleep;
    boolean isFromcalled=true;
    RadioButton rd_button_sleep_tips;
    RecyclerView recycler_sleep_tips;
    //    Button buttonsleep_ViewMore;
    RelativeLayout rel_weekly_sleep;
    TextView txt_avg_sleep_score;
    RelativeLayout rel_no_sleep;
    TextView txt_avg_sleep_time;

    RecyclerView recyclerView, recyclerView_tips;
    private ArrayList<SleepResponse.Data> mDataList;
    private ArrayList<TipsResponce.Datum> mDataListTips;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_sleep_tips;

    ClsSleepTipsAdapter tipsAdapter;
    SleepFragAdapter sleepFragAdapter;

    Utils utils;
    HomeFragmentService service;
    SessionManager sessionManager;

    private String submitToDate="";
    private String submitFromDate="";

    TextView txt_sleep_date_from, txt_sleep_date_to;
    TextView txt_entry_date_histrory;


    CommonService commonService;
    ListView list_sleep_history;
    TextView txt_sleep_date_from_dialog_edit;
    TextView txt_sleep_date_to_dialog_edit;
    private String dummydate_to="";
    private  String dummydate_from="";

    // Animation
    Animation animSlideDown, animSlideUp;
    private int userId, mScheduledSleepForToday, mActualSleepForToday;
    private ViewFlipper vp_main;
    String abc;
    private RadioGroup radioGroup_sleep;
    private RadioButton rd_button_sleep_history,rd_button_sleep;
    TimePickerDialog timepickerdialog;
    private String StrDateOpen="";
    StringBuilder StrFromDateTime;
    StringBuilder StrToDateTime;
    StringBuilder StrToDateTime_check;
    StringBuilder StrFromDateTime_new;
    StringBuilder StrToDateTime_new;


    StringBuilder backendFromDate;
    StringBuilder backendToDate;


    private int strStatusIDSleep;
    MyHistoryAdapter myHistoryAdapter;
    int submitsleepminutes;
    TextView txt_no_sleep;
    boolean isChangeDATE=false;




    ImageView img_add_sleep_date;
    TextView txt_show_sleep_to;
    TextView txt_show_sleep_from;
    private RecyclerView recler_new_sleep_history;
    TextView txt_sleep_date_to_dialog;
    TextView txt_sleep_date_from_dialog;
    boolean isCalled=false;
    private Utils util;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_food_tips;
    private StringBuilder stringBuilder_fromDate=new StringBuilder();
    private String submitentrydatehistory="";
    private String old_entry_date="";
    private DatePickerDialog entry_datepickerdialog_history;
    CardView card_avg_sleep;

    Spinner spinnerSleepType;


    RadioButton rd_button_sleep_video;
    private RecyclerView recylcer_spiritual_list;
    private SessionManager session;
    TextView txt_no_data_spiritual;
    private String submitentrydate="";
    TextView txt_sleep_entry_date_daily;
    private StringBuilder BackendSubmitFromDate;
    private StringBuilder BackendSubmitToDate;
    private String StrTimeOpen="";
    TextView txt_sleep_date_from_daily;
    TextView txt_sleep_date_to_daily;
    CardView card_sleep;
    private boolean isDream;
    private String entry_new_show="";
    private StringBuilder entry_new_submit;
    TextView txt_sleep_entry_date;

    LinearLayout layout_profile,layout_home,layout_setting;
    private ArrayList<ClsRepeatSleepData> arylst_ClsRepeatSleeps;

    public MasterSleepFragment() {

        // Required empty public constructor
    }


    public static MasterSleepFragment newInstance(String param1, String param2) {
        MasterSleepFragment fragment = new MasterSleepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        sessionManager = new SessionManager(context);
        if(context!=null) {
            abc = sessionManager.getStringValue("Allpart");
            if (abc.equals("video")) {
//        vp_main.setDisplayedChild(3);
                getSpitualListAPiByID(3, "Sleep videos ");

                vp_main.setDisplayedChild(3);
//            session = new SessionManager(context);
//            session.setStringValue("Allpart","video");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));


                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.black));



            } else if (abc.equals("tip")) {
//        vp_main.setDisplayedChild(2);

                callToFetchSleepTipsMasterData();
                vp_main.setDisplayedChild(2);
//            session = new SessionManager(context);
//            session.setStringValue("Allpart","tip");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.black));


            } else if (abc.equals("history")) {
                vp_main.setDisplayedChild(1);
//            session = new SessionManager(context);
//            session.setStringValue("Allpart","history");
                callSleepHistoryApi(submitFromDate, submitToDate);
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.black));
            } else if (abc.equals("daily")) {
//        vp_main.setDisplayedChild(0);
                vp_main.setDisplayedChild(0);
//            session = new SessionManager(context);
//            session.setStringValue("Allpart","daily");
                if (Utils.isNetworkAvailable(context)) {
                    CallToFetchSleeprData();
                    callToFetchSleepMasterData();
                    //  }
                } else
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.white));

            }

        }
//        Toast.makeText(getContext(),"abc",Toast.LENGTH_LONG).show();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        sessionManager = new SessionManager(context);
        service = Client.getClient().create(HomeFragmentService.class);
        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }
    private void showAddSleepTimeDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before



        showDatePickerDailog();
        dialog.setContentView(R.layout.dialog_add_sleep);

        txt_sleep_date_from = dialog.findViewById(R.id.txt_sleep_date_from);
        txt_sleep_date_to = dialog.findViewById(R.id.txt_sleep_date_to);

        Button  btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {


                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }

            }
        });
        txt_sleep_date_to = dialog.findViewById(R.id.txt_sleep_date_to);
        txt_sleep_date_from = dialog.findViewById(R.id.txt_sleep_date_from);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txt_sleep_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        txt_sleep_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        dialog.show();


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_master_sleep, container, false);

        util=new Utils();
        historydatte=view.findViewById(R.id.historydatte);
        tvSchedule_sleep=view.findViewById(R.id.tvSchedule_sleep);
        txt_sleep_date=view.findViewById(R.id.txt_sleep_date);
        rd_button_sleep_tips=view.findViewById(R.id.rd_button_sleep_tips);
        frameRoot_Sleep=view.findViewById(R.id.frameRoot_Sleep);
        rel_no_sleep=view.findViewById(R.id.rel_no_sleep);
        txt_avg_sleep_time=view.findViewById(R.id.txt_avg_sleep_time);
        rel_no_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdddSleepTimeDialog();
            }
        });

        ImageView ll_add_shotcut_sleep=view.findViewById(R.id.ll_add_shotcut_sleep);
        ll_add_shotcut_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();

                    return;

                }


                showAdddSleepTimeDialog();

            }
        });



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




        recler_new_sleep_history=view.findViewById(R.id.recler_new_sleep_history);
        img_add_sleep_date=view.findViewById(R.id.img_add_sleep_date);
        txt_show_sleep_from=view.findViewById(R.id.txt_show_sleep_from);
        txt_show_sleep_to=view.findViewById(R.id.txt_show_sleep_to);
        txt_no_sleep=view.findViewById(R.id.txt_no_sleep);
        recycler_sleep_tips=view.findViewById(R.id.recycler_sleep_tips);
        txt_sleep_entry_date_daily=view.findViewById(R.id.txt_sleep_entry_date_daily);
        txt_sleep_date_to_daily=view.findViewById(R.id.txt_sleep_date_to_daily);
        txt_sleep_date_from_daily=view.findViewById(R.id.txt_sleep_date_from_daily);
        ll_sleep_date=view.findViewById(R.id.ll_sleep_date);
        txt_avg_sleep_score=view.findViewById(R.id.txt_avg_sleep_score);
        card_avg_sleep=view.findViewById(R.id.card_avg_sleep);
        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetSleephistroy();
            }
        });
        rel_weekly_sleep=view.findViewById(R.id.rel_weekly_sleep);
        card_sleep=view.findViewById(R.id.card_sleep);
        final ImageView imgprocess=view.findViewById(R.id.imgprocess);
        rel_weekly_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card_sleep.getVisibility() == View.VISIBLE) {
                    card_sleep.clearAnimation();

                    rel_weekly_sleep.setBackground(getResources().getDrawable(R.drawable.bg_black_button));

                    imgprocess.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));
                    card_sleep.setVisibility(View.GONE);
//                    btnViewMore.setText("View last 7 days");
                } else {
                    card_sleep.clearAnimation();
//                    rel_weekly.setBackground(getResources().getDrawable(R.drawable.bg_green_button));
                    rel_weekly_sleep.setBackground(getResources().getDrawable(R.drawable.bg_green_button_new));

                    card_sleep.setVisibility(View.VISIBLE);
                    imgprocess.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));

//                    btnViewMore.setText("Less");
                    card_sleep.startAnimation(animSlideDown);
                }
            }
        });




        img_add_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetSleephistroy();
            }
        });







        dummyData();

        addDailyEntrySleep(view);





        circularProgressBar = view.findViewById(R.id.progress_circular);
        txt_sleep_date_to = view.findViewById(R.id.txt_sleep_date_to);
        txt_sleep_date_from = view.findViewById(R.id.txt_sleep_date_from);
        radioGroup_sleep = view.findViewById(R.id.radioGroup_sleep);
        rd_button_sleep_history = view.findViewById(R.id.rd_button_sleep_history);
        rd_button_sleep = view.findViewById(R.id.rd_button_sleep);

        try {
//        radioGroup_sleep.clearCheck();
            commonService = Client.getClient().create(CommonService.class);
            timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE),
                    false);

            rd_button_sleep_video=view.findViewById(R.id.rd_button_sleep_video);

            recylcer_spiritual_list=view.findViewById(R.id.recylcer_spiritual_list);
            txt_no_data_spiritual=view.findViewById(R.id.txt_no_data_spiritual);
//    getSpitualListAPiByID(3,"Sleep videos ");


            btn_show_sleep_history = view.findViewById(R.id.btn_show_sleep_history);

            btn_show_sleep_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callSleepHistoryApi(submitFromDate, submitToDate);

                }
            });

            txt_sleep_date_from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StrDateOpen = "from";

                    datepickerdialog.show();


                }
            });

            txt_sleep_date_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StrDateOpen = "to";

                    datepickerdialog.show();


                }
            });

            list_sleep_history = view.findViewById(R.id.list_sleep_history);
            //cardFace = view.findViewById(R.id.relative_Sleep_SetTime);
            cardBack = view.findViewById(R.id.relative_Sleep_Edit);
            linearLayout = view.findViewById(R.id.linLay_sleep_week_analysis);
            tvActual = view.findViewById(R.id.tvActual);
//    tvSchedule = view.findViewById(R.id.tvSchedule);
            button = view.findViewById(R.id.buttonSleep_ViewMore);
            recyclerView_tips = view.findViewById(R.id.recyclerView_tips);
            vp_main = view.findViewById(R.id.vp_main);
            vp_main.setDisplayedChild(0);


            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            dummydate_from = formatDatesSleep(submitFromDate);
            txt_sleep_date_from.setText(dummydate_from);
    txt_show_sleep_from.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
            txt_sleep_date_to.setText(dummydate_to);
    txt_show_sleep_to.setText(dummydate_to);
            sessionManager = new SessionManager(context);

//    txt_sleep_date.setText( formatDatesSleepStatus(sessionManager.getStringValue("statusdate"))+"'s Status");

            txt_sleep_date.setText( formatDatesSleepStatus(sessionManager.getStringValue("statusdate")));
            abc = sessionManager.getStringValue("Allpart");
            if(abc.equals("video")){
//        vp_main.setDisplayedChild(3);
                getSpitualListAPiByID(3,"Sleep videos ");

                vp_main.setDisplayedChild(3);
//        session = new SessionManager(context);
//        session.setStringValue("Allpart","video");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.black));

            }else  if(abc.equals("tip")){
//        vp_main.setDisplayedChild(2);

                callToFetchSleepTipsMasterData();
                vp_main.setDisplayedChild(2);
//        session = new SessionManager(context);
//        session.setStringValue("Allpart","tip");
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.black));

            }else  if(abc.equals("history")){
                vp_main.setDisplayedChild(1);
//        session = new SessionManager(context);
//        session.setStringValue("Allpart","history");
                callSleepHistoryApi(submitFromDate,submitToDate);
                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.white));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.black));

            }else  if(abc.equals("daily")){
//        vp_main.setDisplayedChild(0);
                vp_main.setDisplayedChild(0);

                if (Utils.isNetworkAvailable(context)) {
                    CallToFetchSleeprData();
                    callToFetchSleepMasterData();
                    //  }
                } else
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new));

                rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_sleep.setTextColor(getResources().getColor(R.color.white));
            }

//    callToFetchSleepTipsMasterData();

//    callSleepHistoryApi(submitFromDate, submitToDate);


            rd_button_sleep_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSpitualListAPiByID(3,"Sleep videos ");

                    vp_main.setDisplayedChild(3);
                    session = new SessionManager(context);
                    session.setStringValue("Allpart","video");
                    rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                    rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                    rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep_video.setTextColor(getResources().getColor(R.color.white));
                    rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep.setTextColor(getResources().getColor(R.color.black));

                }
            });
            rd_button_sleep_tips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callToFetchSleepTipsMasterData();
                    vp_main.setDisplayedChild(2);
                    session = new SessionManager(context);
                    session.setStringValue("Allpart","tip");

                    rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                    rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                    rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.white));
                    rd_button_sleep.setTextColor(getResources().getColor(R.color.black));

                }
            });

            rd_button_sleep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vp_main.setDisplayedChild(0);
                    session = new SessionManager(context);
                    session.setStringValue("Allpart","daily");
                    if (Utils.isNetworkAvailable(context)) {
                        CallToFetchSleeprData();
                        callToFetchSleepMasterData();
                        //  }
                    } else {
                        Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
                    }

                    rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new));


                    rd_button_sleep_history.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep.setTextColor(getResources().getColor(R.color.white));

                }
            });


            rd_button_sleep_history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vp_main.setDisplayedChild(1);
                    session = new SessionManager(context);
                    session.setStringValue("Allpart","history");
                    callSleepHistoryApi(submitFromDate,submitToDate);

                    rd_button_sleep_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                    rd_button_sleep_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                    rd_button_sleep.setBackgroundResource((R.drawable.custom_white_radio_new1));

                    rd_button_sleep_history.setTextColor(getResources().getColor(R.color.white));
                    rd_button_sleep_video.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep_tips.setTextColor(getResources().getColor(R.color.black));
                    rd_button_sleep.setTextColor(getResources().getColor(R.color.black));

                }
            });
            showDatePickerDailog();
            //SET WEEKLY ANALYSIS
            recyclerView = view.findViewById(R.id.listView_last_sleep_List);
            mDataList = new ArrayList<>();
            sleepFragAdapter = new SleepFragAdapter(context, mDataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(sleepFragAdapter);

            //for tips Data
            //Check Wheater Data is Already Loaded.

            if (HomeFragment.mCommonDataListTips.size() != 0) {
                mDataListTips = HomeFragment.mCommonDataListTips;
            }
            mDataListTips = new ArrayList<>();
            arylst_sleep_tips=new ArrayList<>();

//    tipsAdapter = new ClsSleepTipsAdapter(context, arylst_sleep_tips, "MasterSleepFragment");
//    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context);
//    recyclerView_tips.setLayoutManager(layoutManager1);
//    recyclerView_tips.setItemAnimator(new DefaultItemAnimator());
//    recyclerView_tips.setAdapter(tipsAdapter);


            // load the animation
            animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
            animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            //animSlideUp.setAnimationListener(this);

            button.setOnClickListener(new View.OnClickListener() {
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


                    if (linearLayout.getVisibility() == View.VISIBLE) {
                        linearLayout.clearAnimation();
                        //linearLayout.startAnimation(animSlideUp);
                        linearLayout.setVisibility(View.GONE);
                        button.setText("View Last 7 days");
                        //flipCard();
                    } else {
                        linearLayout.clearAnimation();
                        linearLayout.setVisibility(View.VISIBLE);
                        button.setText("Less");
                        linearLayout.startAnimation(animSlideDown);
                        //flipCard();
                    }
                }
            });

            if (Utils.isNetworkAvailable(context)) {
                CallToFetchSleeprData();
                callToFetchSleepMasterData();
                //  }
            } else
                Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void dummyData() {

//        ClsDemoSleep clsDemoSleep=new ClsDemoSleep();
//        clsDemoSleep.setTotalhours(10);
//        clsDemoSleep.setDate("10-1-11");
//        ArrayList<ClSPowenappDemo> arrayList=new ArrayList<>();
//        arrayList.add(new ClSPowenappDemo(10,100));
//        arrayList.add(new ClSPowenappDemo(10,100));
//        clsDemoSleep.setClSPowenappDemoArrayList(arrayList);
//
//
//        ArrayList<ClsDemoSleep> arrayList1=new ArrayList<>();
//        arrayList1.add(clsDemoSleep);
//        arrayList1.add(clsDemoSleep);
//        arrayList1.add(clsDemoSleep);
//        arrayList1.add(clsDemoSleep);
//        arrayList1.add(clsDemoSleep);
//        arrayList1.add(clsDemoSleep);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//        recler_new_sleep_history.setLayoutManager(layoutManager);
//        recler_new_sleep_history.setAdapter(new SleepHistoryAdapter(context,arrayList1));



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

                    callSleepHistoryApi(submitFromDate,submitToDate);
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

    private void callToFetchSleepMasterData() {

        utils.showProgressbar(context);
        Call<ClsSleepTips> call = service.getMasterSleepTipsData();
        call.enqueue(new Callback<ClsSleepTips>() {
            @Override
            public void onResponse(Call<ClsSleepTips> call, retrofit2.Response<ClsSleepTips> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsSleepTips tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {

                            if (!tipsResponse.getData().isEmpty()){
                                arylst_sleep_tips=new ArrayList<>();
                                arylst_sleep_tips.clear();
                                arylst_sleep_tips.addAll(tipsResponse.getData());
//                            HomeFragment.mCommateataListTips = mDataListTips;
//                                tipsAdapter.notifyDataSetChanged();



                                tipsAdapter = new ClsSleepTipsAdapter(context, arylst_sleep_tips, "MasterSleepFragment");
                                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context);
                                recyclerView_tips.setLayoutManager(layoutManager1);
                                recyclerView_tips.setItemAnimator(new DefaultItemAnimator());
                                recyclerView_tips.setAdapter(tipsAdapter);

                            }else {
//                                Toast.makeText(context, " ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else{

                }
//                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ClsSleepTips> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void callSleepTimeUpdateApi(SleepDurations clsEditSleepRequest, String sleepEntryDate, EditText edt_sleep_count){

        ClsNewEditSleepRequest clsNewEditSleepRequest=new ClsNewEditSleepRequest();
        clsNewEditSleepRequest.setEndSleepTime(clsEditSleepRequest.getEndTime());
        clsNewEditSleepRequest.setId(clsEditSleepRequest.getId());

        String ary_entrydate[]=clsEditSleepRequest.getStartTime().toString().split(" ");


        clsNewEditSleepRequest.setSleepEntryTime(ary_entrydate[0]);
        clsNewEditSleepRequest.setChanged(isChangeDATE);
        clsNewEditSleepRequest.setTodayStatusId(clsEditSleepRequest.getTodayStatusId());
        clsNewEditSleepRequest.setStartSleepTime(clsEditSleepRequest.getStartTime());
        clsNewEditSleepRequest.setDream(isDream);
        if (edt_sleep_count.getText().toString().trim().isEmpty()){
            clsNewEditSleepRequest.setSLeepCount(0);

        }else {
            clsNewEditSleepRequest.setSLeepCount(Integer.parseInt(edt_sleep_count.getText().toString()));

        }
        clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));
        utils.showProgressbar(context);
        Call<ClsEditSleepResonse> call = commonService.SetSleepActivityHistory(clsNewEditSleepRequest);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, retrofit2.Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsEditSleepResonse moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){
                                Toast.makeText(context, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();


                                callSleepHistoryApi(submitFromDate, submitToDate);






                            }else {

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
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void callSleepHistoryApi(String submitFromDate, String submitToDate) {
        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)));


        txt_avg_sleep_time.setText("");
        txt_avg_sleep_score.setText("");
        utils.showProgressbar(context);
        Call<ClsDemoSleep> call = commonService.getSleepHistoryData(clsHistoryRequest);
        historydatte.setText(formatDatesHistoy(dummydate_from)+" to "+formatDatesHistoy(dummydate_to));

        call.enqueue(new Callback<ClsDemoSleep>()
        {
            @Override
            public void onResponse(Call<ClsDemoSleep> call, retrofit2.Response<ClsDemoSleep> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsDemoSleep moodResponse = response.body();
                        if (moodResponse.getCode()!=null){

                            if (moodResponse.getCode().equals("1")){

                                ArrayList<SleepData> dataArrayList=moodResponse.getData();


                                if (dataArrayList!=null){
                                    if (!dataArrayList.isEmpty()){
                                        CallToFetchSleeprData();
                                        card_avg_sleep.setVisibility(View.VISIBLE);
                                        txt_avg_sleep_time.setVisibility(View.VISIBLE);
                                        txt_avg_sleep_score.setVisibility(View.VISIBLE);

                                        int min= (int) Math.round(Double.parseDouble(String.valueOf(moodResponse.getAvgSleep())));
                                        txt_avg_sleep_time.setText(formatHoursAndMinutesNew(min));

                                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                        String showschedulestr = decimalFormat.format(moodResponse.getAvgSleepScore());
                                        String strqty= String.valueOf(showschedulestr);
                                        int startindex=strqty.length()-2;
                                        int endindex=strqty.length()-1;
                                        String[] strNewQty=strqty.split("\\.");
                                        boolean isFound=false;


                                        if (strNewQty[1].equals("00")){
                                            isFound=true;

                                        }

                                        if (isFound){
                                            isFound=false;




                                            txt_avg_sleep_score.setText(strNewQty[0].toString());

                                        }else {
                                            txt_avg_sleep_score.setText(showschedulestr);

                                        }





                                        txt_no_sleep.setVisibility(View.GONE);
                                        recler_new_sleep_history.setVisibility(View.VISIBLE);

                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                        recler_new_sleep_history.setLayoutManager(layoutManager);
                                        recler_new_sleep_history.setAdapter(new SleepHistoryAdapter(context,dataArrayList,MasterSleepFragment.this));


                                    }else {
                                        if (vp_main.getDisplayedChild()==1){
//                                          Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();

                                        }
                                        txt_no_sleep.setVisibility(View.VISIBLE);
                                        recler_new_sleep_history.setVisibility(View.GONE);
                                        ArrayList<Data> fg=new ArrayList<>();
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                        recler_new_sleep_history.setLayoutManager(layoutManager);
                                        ArrayList<SleepData> arrayList1=new ArrayList<>();
                                        recler_new_sleep_history.setAdapter(new SleepHistoryAdapter(context,arrayList1, MasterSleepFragment.this));
                                    }

                                }



                            }else {
                                if (vp_main.getDisplayedChild()==1){
                                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();

                                }




                            }



                        }else {
                            if (vp_main.getDisplayedChild()==1){
                                Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsDemoSleep> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
                txt_no_sleep.setVisibility(View.VISIBLE);
                recler_new_sleep_history.setVisibility(View.GONE);
                ArrayList<Data> fg=new ArrayList<>();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recler_new_sleep_history.setLayoutManager(layoutManager);
                ArrayList<SleepData> arrayList1=new ArrayList<>();
                recler_new_sleep_history.setAdapter(new SleepHistoryAdapter(context,arrayList1, MasterSleepFragment.this));
                txt_avg_sleep_score.setVisibility(View.GONE);
                txt_avg_sleep_time.setVisibility(View.GONE);
            }
        });






    }




    private void CallToFetchTipsData() {
        utils.showProgressbar(context);
        Call<TipsResponce> call = service.getTipsData();
        call.enqueue(new Callback<TipsResponce>() {
            @Override
            public void onResponse(Call<TipsResponce> call, retrofit2.Response<TipsResponce> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    TipsResponce tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {
                            mDataListTips.clear();
                            mDataListTips.addAll(tipsResponse.getData());
                            HomeFragment.mCommonDataListTips = mDataListTips;
                            tipsAdapter.notifyDataSetChanged();
                        }
                    } else
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TipsResponce> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    private void CallToFetchSleeprData() {
        utils.showProgressbar(context);

        WaterRequest request = new WaterRequest();
        request.setUserID(userId);

        Call<SleepResponse> call = service.getSleepData(request);
        call.enqueue(new Callback<SleepResponse>() {
            @Override
            public void onResponse(Call<SleepResponse> call, retrofit2.Response<SleepResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    SleepResponse sleepResponse = response.body();

                    if (sleepResponse != null && sleepResponse.getCode() == 1) {
                        if (sleepResponse.getData() != null) {
                            setTodaysGoal(sleepResponse.getData());

                            mDataList.clear();
                            mDataList.addAll(sleepResponse.getData());
                            sleepFragAdapter.notifyDataSetChanged();

                            if (mDataList.get(0).getScheduledSleepHours() > 0) {
                                String text = formatHoursAndMinutesMin(mDataList.get(0).getScheduledSleepHours()) ;
//                                tvSchedule.setText(text);
//                                tvSchedule.setText(0 + "sunit");

                            }
                            else{
//                                tvSchedule.setText("0");

                            }

//                            if (mDataList.get(0).getActualSleepHours() > 0) {
//                                String text = "<font color=#00e1b1>" + formatHoursAndMinutesMin(mDataList.get(0).getActualSleepHours()) + "</font> <font color=#00e1b1></font>";
//                                tvActual.setText(Html.fromHtml(text + ""));
//                            }
//                            else{
//                                tvActual.setText("0");
//
//                            }




                        }
                    } else{

                    }
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SleepResponse> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }
    public String getCurrentDate()
    {

        String date = "";
        try
        {
//            yymmdd
            String datearray[]=sessionManager.getStringValue("SleepDate").split("-");
            String year=datearray[0];
            String month=datearray[1];
            String dates=datearray[2];
            if (month.length()==1){
                month="0"+month;
            }
//            date=sessionManager.getStringValue("SleepDate");
            date=year+"-"+month+"-"+dates;
        }catch (Exception e){e.printStackTrace();}
        return date;
    }
    public  String formatHoursAndMinutesMinnew(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" h ";
        }else {
            strhours=" h ";

        }


        String submittime="";
        submittime= (totalMinutes / 60) + strhours + minutes+" m";




//        if (hoursstr>0){
//
//        }else {
//
//            submittime=  minutes+" Mins";
//
//        }


        String newSubmit="";
        if (totalMinutes<60){
            newSubmit=submittime.replace("0 h ","");
        }else {
            newSubmit=submittime;
        }
        return newSubmit;


    }


    public void setTodaysGoal(ArrayList<SleepResponse.Data> list) {
        Date tDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//        String todayDate = sessionManager.getStringValue("statusdate");
        String todayDate = getCurrentDate();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {


                if (todayDate.equalsIgnoreCase(formatDates(list.get(i).getCreatedOn()))) {

                    mScheduledSleepForToday = list.get(i).getScheduledSleepHours();
                    mActualSleepForToday = list.get(i).getActualSleepHours();


                    if (mScheduledSleepForToday > 0) {
                        tvSchedule_sleep.setText(""+formatHoursAndMinutesMinnew(mScheduledSleepForToday));
//                        tvSchedule.setText(formatHoursAndMinutesMin(mScheduledSleepForToday) + "");


                        circularProgressBar.setProgressMax(mScheduledSleepForToday);
                    } else {
//                        tvSchedule.setText(0 + "");


                    }

                    if (mActualSleepForToday > 0) {

                        rel_no_sleep.setVisibility(View.GONE);
                        frameRoot_Sleep.setVisibility(View.VISIBLE);
                        tvActual.setText(formatHoursAndMinutesMin(mActualSleepForToday) + "");
                        circularProgressBar.setProgress(mActualSleepForToday);
                    } else {
                        frameRoot_Sleep.setVisibility(View.VISIBLE);
                        rel_no_sleep.setVisibility(View.GONE);

                        tvActual.setText(0 + "");
                    }
                    break;
                }
            }
        }
    }

    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }

    public String formatEntryDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }

    public String updateformatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return dateFromServer;
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
        return "";
    }
    public String BackendformatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String BackendformatDatesfirsttime(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyy hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFromServer;
    }

    /*private void flipCardd()
    {

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }*/

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
// Take any action after completing the animation
        // check for fade in animation
        if (animation == animSlideUp) {
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

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

    private void callToFetchSleepTipsMasterData() {


        util.showProgressbar(getActivity());

        Call<ClsSleepTips> call = service.getMasterFoodTipsData(1);
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
                                recycler_sleep_tips.setLayoutManager(layoutManager1);
                                recycler_sleep_tips.setItemAnimator(new DefaultItemAnimator());
                                recycler_sleep_tips.setAdapter(adapter);

                            }else {
                                Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ClsSleepTips> call, Throwable t) {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }


    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {

        try {
            if (StrTimeOpen.equalsIgnoreCase("from1")) {


                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, i);
                cal.set(Calendar.MINUTE, i1);
                Format formatter;
                formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                String lsTimeFrom = formatter.format(cal.getTime());
                String abc[]=lsTimeFrom.split(" ");

                if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                    lsTimeFrom=abc[0]+" AM";
                }else{
                    lsTimeFrom=abc[0]+" PM";
                }

                StrFromDateTime.append(" ").append(lsTimeFrom);
                BackendSubmitFromDate.append(" ").append(lsTimeFrom);

                txt_sleep_date_from.setText(StrFromDateTime.toString());


            }


            if (StrTimeOpen.equalsIgnoreCase("to1")) {

                if (!txt_sleep_date_from.getText().toString().trim().isEmpty()) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, i1);
                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    String lsTimeFrom = formatter.format(cal.getTime());
                    String abc[]=lsTimeFrom.split(" ");

                    if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                        lsTimeFrom=abc[0]+" AM";
                    }else{
                        lsTimeFrom=abc[0]+" PM";
                    }

                    StrToDateTime_check.append(" ").append(lsTimeFrom);
                    BackendSubmitToDate.append(" ").append(lsTimeFrom);


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = simpleDateFormat.parse(StrFromDateTime.toString().trim());
                        date2 = simpleDateFormat.parse(StrToDateTime_check.toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (StrFromDateTime.toString().trim().equalsIgnoreCase(StrToDateTime_check.toString().trim())) {
                        Toast.makeText(context, "Start Time and End Time should be different", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (date1.after(date2)) {
                        Toast.makeText(context, "End Time should be greater than Start Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // else if (printDifference(date1, date2) > 23) {
                    //      Toast.makeText(mContext, "Sleeping time not be greater than 24 hours", Toast.LENGTH_SHORT).show();
                    //    return;

                    //   }
                    else {
                        StrToDateTime_check.setLength(0);


                        StrToDateTime.append(" ").append(lsTimeFrom);
                        txt_sleep_date_to.setText(StrToDateTime.toString());
                    }

                } else {
                    Toast.makeText(context, "Please select Start Time ", Toast.LENGTH_SHORT).show();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }















        try {
            if (StrTimeOpen.equalsIgnoreCase("from")) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, i);
                cal.set(Calendar.MINUTE, i1);
                Format formatter;
                formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                String lsTimeFrom = formatter.format(cal.getTime());
                String abc[]=lsTimeFrom.split(" ");

                if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                    lsTimeFrom=abc[0]+" AM";
                }else{
                    lsTimeFrom=abc[0]+" PM";
                }
                StrFromDateTime.append(" ").append(lsTimeFrom);
                BackendSubmitFromDate.append(" ").append(lsTimeFrom);

                txt_sleep_date_from_daily.setText(StrFromDateTime.toString());


            }


            if (StrTimeOpen.equalsIgnoreCase("to")) {

                if (!txt_sleep_date_from_daily.getText().toString().trim().isEmpty()) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, i1);
                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    String lsTimeFrom = formatter.format(cal.getTime());
                    String abc[]=lsTimeFrom.split(" ");

                    if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                        lsTimeFrom=abc[0]+" AM";
                    }else{
                        lsTimeFrom=abc[0]+" PM";
                    }
                    StrToDateTime_check.append(" ").append(lsTimeFrom);
                    BackendSubmitToDate.append(" ").append(lsTimeFrom);



                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = simpleDateFormat.parse(StrFromDateTime.toString().trim());
                        date2 = simpleDateFormat.parse(StrToDateTime_check.toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    if (StrFromDateTime.toString().trim().equalsIgnoreCase(StrToDateTime_check.toString().trim())) {
                        Toast.makeText(context, "Start Time and End Time should be different", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (date1.after(date2)) {
                        Toast.makeText(context, "End Time should be greater than Start Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // else if (printDifference(date1, date2) > 23) {
                    //      Toast.makeText(mContext, "Sleeping time not be greater than 24 hours", Toast.LENGTH_SHORT).show();
                    //    return;

                    //   }
                    else {
                        StrToDateTime_check.setLength(0);


                        StrToDateTime.append(" ").append(lsTimeFrom);
                        txt_sleep_date_to_daily.setText(StrToDateTime.toString());
                    }

                } else {
                    Toast.makeText(context, "Please select Start Time ", Toast.LENGTH_SHORT).show();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (StrDateOpen.equalsIgnoreCase("fromEditSleep")) {
                isFromcalled=true;
                isCalled=false;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, i);
                cal.set(Calendar.MINUTE, i1);
                Format formatter;
                formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                String lsTimeFrom = formatter.format(cal.getTime());
                String abc[]=lsTimeFrom.split(" ");

                if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                    lsTimeFrom=abc[0]+" AM";
                }else{
                    lsTimeFrom=abc[0]+" PM";
                }

                backendFromDate.append(" ").append(lsTimeFrom);
                StrFromDateTime.append(" ").append(lsTimeFrom);
                StrFromDateTime_new.append(" ").append(lsTimeFrom);
                txt_sleep_date_from_dialog_edit.setText(StrFromDateTime.toString());

                stringBuilder_fromDate.append(" "+lsTimeFrom);

            }


            if (StrDateOpen.equalsIgnoreCase("ToEditSleep")) {
                if (isFromcalled){
                    isCalled=true;

                }
                if (!txt_sleep_date_from_dialog_edit.getText().toString().trim().isEmpty()) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, i1);
                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    String lsTimeFrom = formatter.format(cal.getTime());

                    String abc[]=lsTimeFrom.split(" ");

                    if(abc[1].equals("am")||abc[1].equals("Am")||abc[1].equals("AM")){
                        lsTimeFrom=abc[0]+" AM";
                    }else{
                        lsTimeFrom=abc[0]+" PM";
                    }

                    StrToDateTime_check.append(" ").append(lsTimeFrom);

                    StrToDateTime_new.append(" ").append(lsTimeFrom);
                    backendToDate.append(" ").append(lsTimeFrom);


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");


                    Date date1 = null;

                    Date date2 = null;
                    try {
//                        if (!isCalled){
//
//                        }
//                        date1 = simpleDateFormat.parse(stringBuilder_fromDate.toString().trim());
//                        date2 = simpleDateFormat.parse(StrToDateTime_new.toString().trim());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (stringBuilder_fromDate.toString().trim().equalsIgnoreCase(StrToDateTime_check.toString().trim())) {
                        Toast.makeText(context, "Start Time and End Time should be different", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
//                        if (!isCalled)
//                        {
//                        if (date1.after(date2)){
//                            isCalled=false;
//                            Toast.makeText(context, "End Time should be greater than Start Time", Toast.LENGTH_SHORT).show();
//                            return;
//                        }

                        StrToDateTime_check.setLength(0);


                    StrToDateTime.append(" ").append(lsTimeFrom);


                    txt_sleep_date_to_dialog_edit.setText(StrToDateTime.toString());


//                    }
                    // else if (printDifference(date1, date2) > 23) {
                    //      Toast.makeText(mContext, "Sleeping time not be greater than 24 hours", Toast.LENGTH_SHORT).show();
                    //    return;

                    //   }
//                    else {
//
//                    }

                } else {
                    Toast.makeText(context, "Please select Start Time ", Toast.LENGTH_SHORT).show();

                }

                if (Utils.isNetworkAvailable(context)) {

                    try {
//


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                        try {


                            if (txt_sleep_date_from_dialog_edit.getText().toString().trim().isEmpty()) {
                                Toast.makeText(context, "Please select start time", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (txt_sleep_date_to_dialog_edit.getText().toString().trim().isEmpty()) {
                                Toast.makeText(context, "Please select end time", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (txt_sleep_date_from_dialog_edit.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                Toast.makeText(context, "Please select correct time", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                                Date date1 = simpleDateFormat.parse(StrFromDateTime_new.toString());
//                                Date date2 = simpleDateFormat.parse(StrToDateTime_new.toString());
//                                if (date1.after(date2)) {
//                                    Toast.makeText(context, "To Time should be greater than from time", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }



                            try {

//                                    int diffmin=printDifference(date1,date2);
//                                 String strsetsleep=   formatHoursAndMinutes(diffmin);

//                                    edt_actual_sleep_hours_history.setText(strsetsleep);
//                                    ClsEditSleepRequest clsEditSleepRequest=new ClsEditSleepRequest();
//
//                                    clsEditSleepRequest.setActualValue(String.valueOf(newhours));
//                                    clsEditSleepRequest.setReportType(2);
//                                    clsEditSleepRequest.setStatusId(strStatusIDSleep);
//
//                                    callSleepTimeUpdateApi(clsEditSleepRequest);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        int inum = (int) elapsedHours;
        int dayshous = (int) elapsedDays;
        int totalhousdays = dayshous * 24;
        int inum_new = totalhousdays + inum;

        int finalmin= (int) ((totalhousdays*60)+(inum*60)+elapsedMinutes);

        return finalmin;
    }
    public String getFromattedStringDate(int s){


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }



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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        try {
            String  selectedDate=year+ "-"+ (month + 1)+"-"+dayOfMonth;




            if (StrDateOpen.equalsIgnoreCase("entry_new")) {
                entry_new_show = dayOfMonth + "-" + (month + 1) + "-" + year;
                txt_sleep_entry_date.setText(entry_new_show);
                entry_new_submit = new StringBuilder();
                entry_new_submit.append(year + "-" + (month + 1) + "-" + dayOfMonth);


            }





            if (StrDateOpen.equalsIgnoreCase("from1")) {
                BackendSubmitFromDate = new StringBuilder();
                String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
                StrTimeOpen = "from1";
                StrFromDateTime = new StringBuilder();
                StrFromDateTime.append(dummydate.toString());
                BackendSubmitFromDate.append(year + "-" + (month + 1) + "-" + dayOfMonth);

                timepickerdialog.show();

            }


            if (StrDateOpen.equalsIgnoreCase("to1")) {
                String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
//                String dummydate = year+ "-"+ (month + 1)+"-"+dayOfMonth;
                StrTimeOpen = "to1";
                StrToDateTime = new StringBuilder();
                StrToDateTime_check = new StringBuilder();
                StrToDateTime.append(dummydate.toString());
                StrToDateTime_check.append(dummydate.toString());

                BackendSubmitToDate = new StringBuilder();
                BackendSubmitToDate.append(year + "-" + (month + 1) + "-" + dayOfMonth);

                timepickerdialog.show();
            }







            if (StrDateOpen.equalsIgnoreCase("from_daily")) {
                BackendSubmitFromDate=new StringBuilder();
                String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
                StrTimeOpen = "from";
                StrFromDateTime = new StringBuilder();
                StrFromDateTime.append(dummydate.toString());
                BackendSubmitFromDate.append(year+ "-"+ (month + 1)+"-"+dayOfMonth);

                timepickerdialog.show();

            }

            if (StrDateOpen.equalsIgnoreCase("to_daily")) {
                String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
//                String dummydate = year+ "-"+ (month + 1)+"-"+dayOfMonth;
                StrTimeOpen = "to";
                StrToDateTime = new StringBuilder();
                StrToDateTime_check = new StringBuilder();
                StrToDateTime.append(dummydate.toString());
                StrToDateTime_check.append(dummydate.toString());

                BackendSubmitToDate=new StringBuilder();
                BackendSubmitToDate.append(year+ "-"+ (month + 1)+"-"+dayOfMonth);

                timepickerdialog.show();
            }


            if (StrDateOpen.equalsIgnoreCase("entry")){
                String dummydateentry = dayOfMonth + "-" + (month + 1) + "-" + year;
                submitentrydate=year+ "-"+ (month + 1)+"-"+dayOfMonth;
                txt_sleep_entry_date_daily.setText(dummydateentry);
//                showEntryDateAddDailog(dummydateentry);

            }


            if (StrDateOpen.equalsIgnoreCase("entrySleepHistroyDate")){
                isChangeDATE=false;

                String dummydateentry = dayOfMonth + "-" + (month + 1) + "-" + year;
                submitentrydatehistory=year+ "-"+ getFromattedStringDate(month + 1)+"-"+getFromattedStringDate(dayOfMonth);

//                showEntryDateAddDailog(dummydateentry);

                txt_entry_date_histrory.setText(dummydateentry);

                int index=old_entry_date.indexOf("T");
                String str_old_history_date=old_entry_date.substring(0,index);

                if (str_old_history_date.equalsIgnoreCase(submitentrydatehistory)){
                    isChangeDATE=false;
                }else {
//                    txt_sleep_date_from_dialog_edit.setText("");
//                    txt_sleep_date_to_dialog_edit.setText("");
//                    backendFromDate.setLength(0);
//                    backendToDate.setLength(0);
                    isChangeDATE=true;

                }




            }





            if (StrDateOpen.equalsIgnoreCase("from")) {

                if (month<10){
                    dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1)+ "-" + year;

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
















                txt_sleep_date_from.setText(dummydate_from);
                txt_show_sleep_from.setText(dummydate_from);
                txt_sleep_date_from_dialog.setText(dummydate_from);




                submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;


            }


            if (StrDateOpen.equalsIgnoreCase("to")) {


                if (!dummydate_from.trim().isEmpty()){



                    if (month<10){
                        dummydate_to = dayOfMonth + "-" + getFromattedStringDate(month+1)+ "-" + year;

                    }else  {
                        dummydate_to = dayOfMonth + "-" +getFromattedStringDate(month+1) + "-" + year;

                    }




//                    dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;




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
                        txt_sleep_date_to.setText(dummydate_to);
                        txt_show_sleep_to.setText(dummydate_to);
                        txt_sleep_date_to_dialog.setText(dummydate_to);

                        submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;

                        callSleepHistoryApi(submitFromDate,submitToDate);

                    }






                }else {
                    Toast.makeText(context, "Please select Start date", Toast.LENGTH_SHORT).show();
                }



            }







            try {
                if (StrDateOpen.equalsIgnoreCase("fromEditSleep")) {

                    stringBuilder_fromDate=new StringBuilder();
                    String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
                    String dummydate_new =  (month + 1) + "-" + dayOfMonth+"-"+year;

                    stringBuilder_fromDate.append(dummydate);
                    backendFromDate=new StringBuilder();
                    backendFromDate.append(year+"-"+ (month + 1)+"-"+dayOfMonth);

                    strFromEditDailg = "from";
                    StrFromDateTime = new StringBuilder();
                    StrFromDateTime_new=new StringBuilder();
                    StrFromDateTime.append(dummydate.toString());
                    StrFromDateTime_new.append(dummydate);
                    timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            false);
                    timepickerdialog.show();

                }


                if (StrDateOpen.equalsIgnoreCase("ToEditSleep")) {
                    String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
                    String dummydate_new =  (month + 1) + "-" + dayOfMonth+"-"+year;

                    strFromEditDailg = "to";
                    backendToDate=new StringBuilder();

                    backendToDate.append(year+"-"+ (month + 1)+"-"+dayOfMonth);


                    StrToDateTime = new StringBuilder();
                    StrToDateTime_check = new StringBuilder();
                    StrToDateTime_new=new StringBuilder();
                    StrToDateTime.append(dummydate.toString());
                    StrToDateTime_new.append(dummydate.toString());
                    StrToDateTime_check.append(dummydate.toString());
                    timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            false);

                    timepickerdialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }












        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getEditSleepPosition(final SleepActivityCustom data) {
//        strStatusIDSleep= data.getId();
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.row_wiki);


        final RadioButton rd_btn_dreamless_sleep=dialog.findViewById(R.id.rd_btn_dreamless_sleep);
        final RadioButton rd_btn_dreamwith_sleep=dialog.findViewById(R.id.rd_btn_dreamwith_sleep);

        if (data.isDream()){
            isDream=true;
            rd_btn_dreamwith_sleep.setChecked(true);
            rd_btn_dreamless_sleep.setChecked(false);
            Toast.makeText(context, ""+data.isDream(), Toast.LENGTH_SHORT).show();
        }else {
            isDream=false;
            rd_btn_dreamless_sleep.setChecked(true);
            rd_btn_dreamwith_sleep.setChecked(false);
            Toast.makeText(context, ""+data.isDream(), Toast.LENGTH_SHORT).show();

        }

        rd_btn_dreamless_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamless_sleep.setChecked(true);
                rd_btn_dreamwith_sleep.setChecked(false);
                isDream=false;

            }
        });
        rd_btn_dreamwith_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamwith_sleep.setChecked(true);
                rd_btn_dreamless_sleep.setChecked(false);
                isDream=true;


            }
        });





        EditText edt_actual_sleep_hours=dialog.findViewById(R.id.edt_actual_sleep_hours);
        EditText edt_schedule_sleep_hours=dialog.findViewById(R.id.edt_schedule_sleep_hours);
        edt_actual_sleep_hours_history=dialog.findViewById(R.id.edt_actual_sleep_hours_history);
        ImageView close_slepp_histoy_dialog=dialog.findViewById(R.id.close_slepp_histoy_dialog);
        close_slepp_histoy_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txt_sleep_date_to_dialog_edit=dialog.findViewById(R.id.txt_sleep_date_to_dialog_edit);
        txt_sleep_date_from_dialog_edit =dialog.findViewById(R.id.txt_sleep_date_from_dialog_edit);

        txt_sleep_date_from_dialog_edit.setText(data.getStartTime());
        txt_sleep_date_to_dialog_edit.setText(data.getEndTime());



        backendFromDate=new StringBuilder();
        backendFromDate.append(BackendformatDates(data.getStartTime()));

        backendToDate=new StringBuilder();
        backendToDate.append(BackendformatDates(data.getEndTime()));

        StrFromDateTime_new=new StringBuilder();
        StrFromDateTime_new.append(data.getStartTime());
        StrToDateTime_new=new StringBuilder();
        StrToDateTime_new.append(data.getEndTime());

        StrFromDateTime=new StringBuilder();
        StrFromDateTime.append(data.getStartTime());
        StrToDateTime_check=new StringBuilder();
        StrToDateTime_check.append(data.getEndTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        try {
//            StrToDateTime_check=new StringBuilder();
//            StrToDateTime=new StringBuilder();
//            Date datedfrom=simpleDateFormat.parse(data.getStartTime());
//            StrFromDateTime.append(datedfrom.toString());
//
//            StrToDateTime_check.append(simpleDateFormat.parse(data.getStartTime().toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }







//        edt_schedule_sleep_hours.setText(data.getScheduledSleepHours());
        Button btn_update_sleep_history_time=dialog.findViewById(R.id.btn_update_sleep_history_time);


        btn_update_sleep_history_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {










//
//
//

















                ClsupdateSleepRequest clsEditSleepRequest=new ClsupdateSleepRequest();
                clsEditSleepRequest.setId(data.getId());
                clsEditSleepRequest.setTodayStatusId(data.getTodayStatusId());
                clsEditSleepRequest.setDream(isDream);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

                try {
                    clsEditSleepRequest.setStartTime(backendFromDate.toString());
                    clsEditSleepRequest.setEndTime(backendToDate.toString());


//                    callSleepTimeUpdateApi(clsEditSleepRequest);
//                    dialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });


        txt_sleep_date_from_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StrDateOpen = "fromEditSleep";

                datepickerdialog.show();
            }
        });

        txt_sleep_date_to_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StrDateOpen = "ToEditSleep";
                datepickerdialog.show();

            }
        });



        dialog.show();

    }

    @Override
    public void onDeleteSleep(final SleepActivityCustom data) {


//        deleteSleepHistroy



        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Sleep Time").setTitle("Sleep Time");

        builder.setMessage("Are you sure want to delete this record?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        utils.showProgressbar(context);
                        Call<ClsEditSleepResonse> call = commonService.deleteSleepHistroy(data.getId());
                        call.enqueue(new Callback<ClsEditSleepResonse>()
                        {
                            @Override
                            public void onResponse(Call<ClsEditSleepResonse> call, retrofit2.Response<ClsEditSleepResonse> response)
                            {


                                utils.hideProgressbar();



                                if (response.code() == Client.RESPONSE_CODE_OK){


                                    try {
                                        ClsEditSleepResonse moodResponse = response.body();

                                        if (moodResponse!=null){
                                            if (moodResponse.getCode().equals("1")){


                                                callSleepHistoryApi(submitFromDate, submitToDate);






                                            }else {
                                                callSleepHistoryApi(submitFromDate, submitToDate);

                                                Toast.makeText(getContext(), ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        }




                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }



                                }


                            }

                            @Override
                            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
                            {
//                utils.hideProgressbar();
                                Log.e("ERROR------>", t.toString());
                            }
                        });


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
        alert.setTitle("Sleep Time");
        alert.show();








    }

    @Override
    public void updatePowerNap(final SleepDurations sleepDurations, final String sleepEntryDate) {

        SessionManager  sessionManager = new SessionManager(context);
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }

        int index2 = sleepEntryDate.indexOf("T");
        String str_sleepEntryDate=sleepEntryDate.substring(0,index2);

        submitentrydatehistory=str_sleepEntryDate;

        old_entry_date=sleepEntryDate;
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.row_wiki);
        final RadioButton rd_btn_dreamless_sleep=dialog.findViewById(R.id.rd_btn_dreamless_sleep);
        final RadioButton rd_btn_dreamwith_sleep=dialog.findViewById(R.id.rd_btn_dreamwith_sleep);
        final EditText edt_sleep_count=dialog.findViewById(R.id.edt_sleep_count);


        edt_sleep_count.setText(String.valueOf(sleepDurations.getSLeepCount()));

        if (sleepDurations.isDream()){
            isDream=true;
            rd_btn_dreamwith_sleep.setChecked(true);
            rd_btn_dreamless_sleep.setChecked(false);
        }else {
            isDream=false;
            rd_btn_dreamless_sleep.setChecked(true);
            rd_btn_dreamwith_sleep.setChecked(false);

        }

        rd_btn_dreamless_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamless_sleep.setChecked(true);
                rd_btn_dreamwith_sleep.setChecked(false);
                isDream=false;

            }
        });
        rd_btn_dreamwith_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamwith_sleep.setChecked(true);
                rd_btn_dreamless_sleep.setChecked(false);
                isDream=true;


            }
        });


        EditText edt_actual_sleep_hours=dialog.findViewById(R.id.edt_actual_sleep_hours);
        EditText edt_schedule_sleep_hours=dialog.findViewById(R.id.edt_schedule_sleep_hours);
        edt_actual_sleep_hours_history=dialog.findViewById(R.id.edt_actual_sleep_hours_history);
        ImageView close_slepp_histoy_dialog=dialog.findViewById(R.id.close_slepp_histoy_dialog);
        close_slepp_histoy_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        txt_sleep_date_to_dialog_edit=dialog.findViewById(R.id.txt_sleep_date_to_dialog_edit);
        txt_sleep_date_from_dialog_edit =dialog.findViewById(R.id.txt_sleep_date_from_dialog_edit);
        txt_entry_date_histrory =dialog.findViewById(R.id.txt_entry_date_histrory);

        String  curentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        showEntryDateAddDailog(curentDate);
        txt_entry_date_histrory.setText(formatEntryDatesNew(str_sleepEntryDate));
        txt_entry_date_histrory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="entrySleepHistroyDate";

                datepickerdialog.show();
            }
        });


        if (sleepDurations.getStartTime().contains("T")){
            int index = sleepDurations.getStartTime().indexOf("T");
            String start=sleepDurations.getStartTime();

            int index1 = sleepDurations.getEndTime().indexOf("T");
            String end=sleepDurations.getEndTime();
            txt_sleep_date_from_dialog_edit.setText(BackendformatDatesfirsttime(sleepDurations.getStartTime()));
            txt_sleep_date_to_dialog_edit.setText(BackendformatDatesfirsttime(sleepDurations.getEndTime()));
        }else {
            txt_sleep_date_from_dialog_edit.setText(sleepDurations.getStartTime());
            txt_sleep_date_to_dialog_edit.setText(sleepDurations.getEndTime());
        }





        backendFromDate=new StringBuilder();
        backendFromDate.append(BackendformatDates(sleepDurations.getStartTime()));

        backendToDate=new StringBuilder();
        backendToDate.append(BackendformatDates(sleepDurations.getEndTime()));

        StrFromDateTime_new=new StringBuilder();
        StrFromDateTime_new.append(sleepDurations.getStartTime());
        StrToDateTime_new=new StringBuilder();
        StrToDateTime_new.append(sleepDurations.getEndTime());

        StrFromDateTime=new StringBuilder();
        StrFromDateTime.append(sleepDurations.getStartTime());
        StrToDateTime_check=new StringBuilder();
        StrToDateTime_check.append(sleepDurations.getEndTime());







//        edt_schedule_sleep_hours.setText(data.getScheduledSleepHours());
        Button btn_update_sleep_history_time=dialog.findViewById(R.id.btn_update_sleep_history_time);


        btn_update_sleep_history_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (txt_sleep_date_from_dialog_edit.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please select Start Time", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (txt_sleep_date_to_dialog_edit.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please select End Time", Toast.LENGTH_SHORT).show();
                    return;

                }

                SimpleDateFormat simpleDateFormats = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                Date date1 = null;
                Date date2 = null;
                try {

                    date1 = simpleDateFormats.parse(txt_sleep_date_from_dialog_edit.getText().toString().trim());
                    date2 = simpleDateFormats.parse(txt_sleep_date_to_dialog_edit.getText().toString().trim());
                    if (date1.after(date2)) {
                        Toast.makeText(context, "End Time should be greater than Start time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ClsupdateSleepRequest clsEditSleepRequest=new ClsupdateSleepRequest();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

                try {
                    sleepDurations.setStartTime(backendFromDate.toString());
                    sleepDurations.setEndTime(backendToDate.toString());


                    callSleepTimeUpdateApi(sleepDurations,sleepEntryDate,edt_sleep_count);
                    dialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });


        txt_sleep_date_from_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StrDateOpen = "fromEditSleep";

                entry_datepickerdialog_history.show();
            }
        });

        txt_sleep_date_to_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StrDateOpen = "ToEditSleep";
                entry_datepickerdialog_history.show();

            }
        });



        dialog.show();





    }

    @Override
    public void deletePowerNap(String id) {

        deleteSleep(id);

    }

    private void deleteSleep(String id) {
        utils.showProgressbar(context);
        Call<ClsEditSleepResonse> call = commonService.deleteSleepPowernapHistroy(id);
        call.enqueue(new Callback<ClsEditSleepResonse>() {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response) {
                utils.hideProgressbar();
                RDPSuccess rdpSuccess;
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    if (response.body().getCode().equals("1")) {



                        if (response.body().getData() != null) {
                            Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                            callSleepHistoryApi(submitFromDate,submitToDate);

                        }

                    } else {
                        callSleepHistoryApi(submitFromDate, submitToDate);

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


    private void showEntryDateAddDailog(String strMindate) {
        String dateArray[]=strMindate.split("-");

        try {
            Calendar calendar = Calendar.getInstance();
            int year = Integer.parseInt(dateArray[2]);
            int month = Integer.parseInt(dateArray[1])-1;
            int day = Integer.parseInt(dateArray[0]);


            entry_datepickerdialog_history = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MasterSleepFragment.this, year, month, day);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);//Year,Mounth -1,Day
            entry_datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());


//            Calendar c1 = Calendar.getInstance();
//            c.set(year, month, day-1);//Year,Mounth -1,Day
//            entry_datepickerdialog_history.getDatePicker().setMinDate(c.getTimeInMillis());


            entry_datepickerdialog_history.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button buttonNeg = entry_datepickerdialog_history.getButton(DialogInterface.BUTTON_NEGATIVE);
                    buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    Button buttonPos = entry_datepickerdialog_history.getButton(DialogInterface.BUTTON_POSITIVE);
                    buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    private void showDatePickerDailog() {
        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MasterSleepFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog.getDatePicker().setMaxDate(maxDate);



        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
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
        return "";
    }
    public  String formatHoursAndMinutesNew(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=".";
        }else {
            strhours=".";

        }



        String submittime="";
        if (hoursstr>1){
            submittime= (totalMinutes / 60) + strhours + minutes+" hrs";

        }else {
            submittime= (totalMinutes / 60) + strhours + minutes+" hr";

        }

        submitsleepminutes=hoursstr*60+Integer.parseInt(minutes);




//        String newSubmit=submittime.replace("0 Hours","");

        return submittime;


    }


    public  String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hour ";
        }else {
            strhours=" Hours ";

        }



        String submittime="";
        submittime= (totalMinutes / 60) + strhours + minutes+" Mins";

        submitsleepminutes=hoursstr*60+Integer.parseInt(minutes);




//        String newSubmit=submittime.replace("0 Hours","");

        return submittime;


    }


    public  String formatHoursAndMinutesMin(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" h ";
        }else {
            strhours=" h ";

        }






        String submittime="";

        if (minutes.equalsIgnoreCase("00")){
            submittime= (totalMinutes / 60) + strhours + minutes+" m";

        }else {
            submittime= (totalMinutes / 60) + strhours + minutes+" m";

        }


        submitsleepminutes=hoursstr*60+Integer.parseInt(minutes);

        String newSubmit="";

        if (totalMinutes<60){
            newSubmit=submittime.replace("0 h ","");

        }else {
            newSubmit=submittime;
        }

        return newSubmit;


    }
    public String avgformatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours = "";
        int hoursstr = (totalMinutes / 60);
        if (hoursstr == 1) {
            strhours = " h ";
        } else {
            strhours = " h ";

        }


        String submittime = "";
        if (minutes.equalsIgnoreCase("00")){
            submittime = hoursstr + strhours ;

        }else {
            submittime = hoursstr + strhours + minutes + " m";

        }


        return submittime;


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

                                    recylcer_spiritual_list.setAdapter(new SleepVideoListAdapter(MasterSleepFragment.this,arylst_SpiritualTypeData));


                                }else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName+" not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new SleepVideoListAdapter(MasterSleepFragment.this,arylst_SpiritualTypeData));

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
    private void addDailyEntrySleep(View dialog) {
        final TextView txt_sleep_date_from_daily;
        TextView txt_sleep_entry_date_daily;
        final TextView txt_sleep_date_to_daily;
        Button btn_submit_sleep_hours_daily;

        submitentrydate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String        curentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        showEntryDateAddDailog(curentDate);



        showDatePickerDailog();

        txt_sleep_date_from_daily = dialog.findViewById(R.id.txt_sleep_date_from_daily);
        txt_sleep_date_to_daily = dialog.findViewById(R.id.txt_sleep_date_to_daily);
        txt_sleep_entry_date_daily = dialog.findViewById(R.id.txt_sleep_entry_date_daily);
        final EditText edt_sleep_count=dialog.findViewById(R.id.edt_sleep_count);

        txt_sleep_entry_date_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "entry";
                timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE) ,
                        false);

                datepickerdialog.show();
            }
        });
        txt_sleep_entry_date_daily.setText( new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));


        btn_submit_sleep_hours_daily = dialog.findViewById(R.id.btn_submit_sleep_hours_daily);

        btn_submit_sleep_hours_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){

                        try {
//


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                            try {


                                if (txt_sleep_date_from_daily.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_to_daily.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select end time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_from_daily.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                    Toast.makeText(context, "Please select correct time", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Date date1 = simpleDateFormat.parse(txt_sleep_date_from_daily.getText().toString().trim());
                                Date date2 = simpleDateFormat.parse(txt_sleep_date_to_daily.getText().toString().trim());
                                if (date1.after(date2)) {
                                    Toast.makeText(context, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }



                                try {


//                                    if (submitentrydate.isEmpty()){
//                                        Toast.makeText(mContext, "Please enter entry date", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
                                    ClsNewEditSleepRequest  clsNewEditSleepRequest=new ClsNewEditSleepRequest();

                                    String ary_entrydate[]=backendFromDate.toString().split(" ");

                                    clsNewEditSleepRequest.setSleepEntryTime(ary_entrydate[0]);
                                    clsNewEditSleepRequest.setStartSleepTime(backendFromDate.toString());
                                    clsNewEditSleepRequest.setEndSleepTime(submitFromDate);
                                    clsNewEditSleepRequest.setTodayStatusId("0");
                                    clsNewEditSleepRequest.setId("0");
                                    clsNewEditSleepRequest.setSLeepCount(Integer.parseInt(edt_sleep_count.getText().toString()));

                                    clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));
                                    callSleepTimeUpdateApiDaily(clsNewEditSleepRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }else {
//                        if (mHomeModel != null) {
                        try {
//


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                            try {


                                if (txt_sleep_date_from_daily.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_to_daily.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select end time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_from_daily.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                    Toast.makeText(context, "Please select correct time", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Date date1 = simpleDateFormat.parse(txt_sleep_date_from_daily.getText().toString().trim());
                                Date date2 = simpleDateFormat.parse(txt_sleep_date_to_daily.getText().toString().trim());
                                if (date1.after(date2)) {
                                    Toast.makeText(context, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }




                                try {



                                    if (submitentrydate.isEmpty()){
                                        Toast.makeText(context, "Please enter entry date", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    ClsNewEditSleepRequest  clsNewEditSleepRequest=new ClsNewEditSleepRequest();
                                    String ary_entrydate[]=BackendSubmitFromDate.toString().split(" ");

                                    clsNewEditSleepRequest.setSleepEntryTime(ary_entrydate[0]);
                                    clsNewEditSleepRequest.setStartSleepTime(BackendSubmitFromDate.toString());
                                    clsNewEditSleepRequest.setEndSleepTime(BackendSubmitToDate.toString());
                                    clsNewEditSleepRequest.setTodayStatusId("0");
                                    clsNewEditSleepRequest.setId("0");
                                    clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));
                                    callSleepTimeUpdateApiDaily(clsNewEditSleepRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        } else
//                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                    }


                }
                else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }

            }
        });


        txt_sleep_date_from_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from_daily";

                timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE) ,
                        false);
                entry_datepickerdialog_history.show();


            }
        });
        txt_sleep_date_to_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "to_daily";
                timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE) ,
                        false);

                entry_datepickerdialog_history.show();

            }
        });



    }
    private void callSleepTimeUpdateApiDaily(ClsNewEditSleepRequest clsNewEditSleepRequest){


        utils.showProgressbar(context);
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsEditSleepResonse> call = commonService.SetSleepActivityHistory(clsNewEditSleepRequest);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, retrofit2.Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsEditSleepResonse moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){
                                txt_sleep_date_from_daily.setText("Select start time here");
                                txt_sleep_date_to_daily.setText("Select end time here");
                                BackendSubmitFromDate.setLength(0);
                                BackendSubmitToDate.setLength(0);
                                Toast.makeText(context, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();
                                CallToFetchSleeprData();

                            }else {

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
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
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


    public void showAdddSleepTimeDialog() {
//        if (true){
//            return;
//        }else {
//            Toast.makeText(context, "Coming soon.....", Toast.LENGTH_SHORT).show();
//        }

        String curentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        showEntryDateAddDailog(curentDate);

//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before


        final Dialog dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before




        showDatePickerDailog();
        dialog.setContentView(R.layout.dialog_add_sleep);
        TextView txt_repeat_sleep_data = dialog.findViewById(R.id.txt_repeat_sleep_data);
        final TextView txt_add_repeat_sleep = dialog.findViewById(R.id.txt_add_repeat_sleep);
//        btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        final Button   btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

//        btn_submit_sleep_hours_daily = dialog.findViewById(R.id.btn_submit_sleep_hours_daily);

        callRepeatSleepDataAPI(submitentrydate, dialog, txt_add_repeat_sleep,btn_submit_sleep_hours);

//        TextView txt_repeat_sleep_data=dialog.findViewById(R.id.txt_repeat_sleep_data);
        txt_repeat_sleep_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRepeatSleepDataAPI(submitentrydate,dialog, txt_add_repeat_sleep, btn_submit_sleep_hours);
            }
        });


        txt_sleep_date_from = dialog.findViewById(R.id.txt_sleep_date_from);
        txt_sleep_date_to = dialog.findViewById(R.id.txt_sleep_date_to);
        txt_sleep_entry_date = dialog.findViewById(R.id.txt_sleep_entry_date);


        spinnerSleepType = dialog.findViewById(R.id.spinnerSleepType);
        ArrayAdapter<String> adapters_activty;

        final String[] frequencyArray = getResources().getStringArray(R.array.sleep_type);

        adapters_activty = new ArrayAdapter<>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, frequencyArray);
        spinnerSleepType.setAdapter(adapters_activty);

        spinnerSleepType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0){
                    isDream=false;
                }else{
                    isDream=true;
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        RadioGroup radioGroup = (RadioGroup)dialog. findViewById(R.id.rd_group_sleep);

        final RadioButton rd_btn_dreamless_sleep=dialog.findViewById(R.id.rd_btn_dreamless_sleep);
        final RadioButton rd_btn_dreamwith_sleep=dialog.findViewById(R.id.rd_btn_dreamwith_sleep);
        final EditText edt_sleep_count=dialog.findViewById(R.id.edt_sleep_count);
        LinearLayout ll_entry=dialog.findViewById(R.id.ll_entry);
        ll_entry.setVisibility(View.VISIBLE);


        rd_btn_dreamless_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamless_sleep.setChecked(true);
                rd_btn_dreamwith_sleep.setChecked(false);
                isDream=false;

            }
        });
        rd_btn_dreamwith_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamwith_sleep.setChecked(true);
                rd_btn_dreamless_sleep.setChecked(false);
                isDream=true;


            }
        });





        txt_sleep_entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "entry";
                StrDateOpen = "entry_new";
                timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);

                datepickerdialog.show();
            }
        });
        txt_sleep_entry_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        entry_new_submit=new StringBuilder();
        entry_new_submit.append(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()).toString());
//        entry_new_submit= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        txt_sleep_entry_date.setVisibility(View.VISIBLE);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (Utils.isNetworkAvailable(context)) {

                    if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                        try {
//


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                            try {


                                if (txt_sleep_date_from.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_to.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select end time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_from.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                    Toast.makeText(context, "Please select correct time", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Date date1 = simpleDateFormat.parse(txt_sleep_date_from.getText().toString().trim());
                                Date date2 = simpleDateFormat.parse(txt_sleep_date_to.getText().toString().trim());
                                if (date1.after(date2)) {
                                    Toast.makeText(context, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                dialog.dismiss();


                                try {


                                    if (submitentrydate.isEmpty()) {
                                        Toast.makeText(context, "Please enter entry date", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    ClsNewEditSleepRequest clsNewEditSleepRequest = new ClsNewEditSleepRequest();
                                    String ary_entrydate[]=BackendSubmitFromDate.toString().split(" ");

                                    clsNewEditSleepRequest.setSleepEntryTime(ary_entrydate[0]);
                                    clsNewEditSleepRequest.setStartSleepTime(BackendSubmitFromDate.toString());
                                    clsNewEditSleepRequest.setEndSleepTime(BackendSubmitToDate.toString());
                                    clsNewEditSleepRequest.setTodayStatusId("0");
                                    clsNewEditSleepRequest.setId("0");
                                    clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));

                                    if (!edt_sleep_count.getText().toString().isEmpty()){
                                        clsNewEditSleepRequest.setSLeepCount(Integer.parseInt(edt_sleep_count.getText().toString()));


                                    }else {

                                        clsNewEditSleepRequest.setSLeepCount(0);

                                    }

                                    callSleepTimeUpdateApifortoday(clsNewEditSleepRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        try {
//


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                            try {


                                if (txt_sleep_date_from.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_to.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(context, "Please select end time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_from.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                    Toast.makeText(context, "Please select correct time", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Date date1 = simpleDateFormat.parse(txt_sleep_date_from.getText().toString().trim());
                                Date date2 = simpleDateFormat.parse(txt_sleep_date_to.getText().toString().trim());
                                if (date1.after(date2)) {
                                    Toast.makeText(context, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                dialog.dismiss();


                                try {


                                    if (sessionManager.getStringValue("statusdate").isEmpty()) {
                                        Toast.makeText(context, "Please enter entry date", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    ClsNewEditSleepRequest clsNewEditSleepRequest = new ClsNewEditSleepRequest();
                                    String entry=    sessionManager.getStringValue("statusdate");
                                    clsNewEditSleepRequest.setSleepEntryTime(entry_new_submit.toString());
                                    clsNewEditSleepRequest.setStartSleepTime(BackendSubmitFromDate.toString());
                                    clsNewEditSleepRequest.setEndSleepTime(BackendSubmitToDate.toString());
                                    clsNewEditSleepRequest.setTodayStatusId("0");
                                    clsNewEditSleepRequest.setId("0");
                                    clsNewEditSleepRequest.setDream(isDream);
                                    clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));
                                    if (!edt_sleep_count.getText().toString().isEmpty()){
                                        clsNewEditSleepRequest.setSLeepCount(Integer.parseInt(edt_sleep_count.getText().toString()));


                                    }else {

                                        clsNewEditSleepRequest.setSLeepCount(0);

                                    }
                                    callSleepTimeUpdateApifortoday(clsNewEditSleepRequest);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }

            }
        });
        txt_sleep_date_to = dialog.findViewById(R.id.txt_sleep_date_to);
        txt_sleep_date_from = dialog.findViewById(R.id.txt_sleep_date_from);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txt_sleep_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrDateOpen = "to1";
                timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);

                entry_datepickerdialog_history.show();

            }
        });
        txt_sleep_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from1";

                timepickerdialog = new TimePickerDialog(getContext(), MasterSleepFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);
                entry_datepickerdialog_history.show();
            }
        });




        dialog.show();


    }

    private void callSleepTikmeUpdateApifortoday(ClsNewEditSleepRequest clsNewEditSleepRequest) {
    }
    private void callSleepTimeUpdateApifortoday(ClsNewEditSleepRequest clsNewEditSleepRequest) {


        utils.showProgressbar(context);
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsEditSleepResonse> call = commonService.SetSleepActivityHistory(clsNewEditSleepRequest);
        call.enqueue(new Callback<ClsEditSleepResonse>() {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, retrofit2.Response<ClsEditSleepResonse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsEditSleepResonse moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getCode().equals("1")) {

                                CallToFetchSleeprData();
                                callToFetchSleepMasterData();

                                Toast.makeText(context, "" + moodResponse.getData(), Toast.LENGTH_SHORT).show();


                            } else {
                                CallToFetchSleeprData();
                                callToFetchSleepMasterData();

                                Toast.makeText(getContext(), "" + moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t) {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    private void callRepeatSleepDataAPI(String c, final Dialog dialogfirst, final TextView txt_add_repeat_sleep, final Button btn_submit_sleep_hours) {

        commonService = Client.getClient().create(CommonService.class);
        utils.showProgressbar(context);

        Call<ClsRepeatSleepMain> call = commonService.GetRepeatSleep(userId, submitentrydate);
        call.enqueue(new Callback<ClsRepeatSleepMain>() {
            @Override
            public void onResponse(Call<ClsRepeatSleepMain> call, retrofit2.Response<ClsRepeatSleepMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsRepeatSleepMain moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getData() != null && !moodResponse.getData().isEmpty()) {
                                TextView txtheader=dialogfirst.findViewById(R.id.txtheader);
                                txtheader.setVisibility(View.VISIBLE);

                                showReepeatSleep(moodResponse, dialogfirst,txt_add_repeat_sleep,btn_submit_sleep_hours);

                            } else {
                                TextView txtheader=dialogfirst.findViewById(R.id.txtheader);
                                txtheader.setVisibility(View.GONE);

                                txt_add_repeat_sleep.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int count = 0;
                                        int countRepeatSleep = 0;
                                        if (arylst_ClsRepeatSleeps!=null){
                                            for (int i = 0; i < arylst_ClsRepeatSleeps.size(); i++) {
                                                if (arylst_ClsRepeatSleeps.get(i).isChecked()) {
                                                    count++;
                                                }
                                            }
                                        }



                                        if (count>0){
                                            if (count > 1) {
                                                Toast.makeText(context, "Please select only one time slot", Toast.LENGTH_SHORT).show();
                                                return;
                                            }


                                            for (int i = 0; i < arylst_ClsRepeatSleeps.size(); i++) {
                                                if (arylst_ClsRepeatSleeps.get(i).isChecked()) {

                                                    isRepeatfound=true;

                                                    BackendSubmitFromDate = new StringBuilder();
                                                    BackendSubmitToDate = new StringBuilder();
//                        callRepeatSleepDataAPIfun(arylst_ClsRepeatSleeps.get(i));
                                                    txt_sleep_date_from.setText(formatRepeatSleepDatesnew(arylst_ClsRepeatSleeps.get(i).getStartTime()));
                                                    txt_sleep_date_to.setText(formatRepeatSleepDatesnew(arylst_ClsRepeatSleeps.get(i).getEndTime()));
                                                    BackendSubmitFromDate.append(arylst_ClsRepeatSleeps.get(i).getStartTime());
                                                    BackendSubmitToDate.append(arylst_ClsRepeatSleeps.get(i).getEndTime());
                                                    btn_submit_sleep_hours.performClick();

                                                    return;

                                                }

                                            }
                                        }else {
                                            btn_submit_sleep_hours.performClick();
                                        }



                                    }
                                });
                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ClsRepeatSleepMain> call, Throwable t) {
                if (Utils.isNetworkAvailable(context)) {
                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }
    private void showReepeatSleep(ClsRepeatSleepMain moodResponse, final Dialog dialogfirst, TextView txt_add_repeat_sleep, final Button btn_submit_sleep_hours) {
//        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.setContentView(R.layout.repeat_sleep);
        RecyclerView recylr_repeat_sleep_list = dialogfirst.findViewById(R.id.recylr_repeat_sleep_list);
         arylst_ClsRepeatSleeps = moodResponse.getData();



        recylr_repeat_sleep_list.setAdapter(new RepeatSleepListAdapter(getContext(), arylst_ClsRepeatSleeps));


//        ImageView close_repeat_sleep_dialog = dialog.findViewById(R.id.close_repeat_sleep_dialog);
//        close_repeat_sleep_dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        TextView txt_add_repeat_sleep = dialog.findViewById(R.id.txt_add_repeat_sleep);
        txt_add_repeat_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogfirst.dismiss();



                int count = 0;
                int countRepeatSleep = 0;
                for (int i = 0; i < arylst_ClsRepeatSleeps.size(); i++) {
                    if (arylst_ClsRepeatSleeps.get(i).isChecked()) {
                        count++;
                    }
                }


                if (count>0){
                    if (count > 1) {
                        Toast.makeText(context, "Please select only one time slot", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    for (int i = 0; i < arylst_ClsRepeatSleeps.size(); i++) {
                        if (arylst_ClsRepeatSleeps.get(i).isChecked()) {

                            isRepeatfound=true;

                            BackendSubmitFromDate = new StringBuilder();
                            BackendSubmitToDate = new StringBuilder();
//                        callRepeatSleepDataAPIfun(arylst_ClsRepeatSleeps.get(i));
                            txt_sleep_date_from.setText(formatRepeatSleepDatesnew(arylst_ClsRepeatSleeps.get(i).getStartTime()));
                            txt_sleep_date_to.setText(formatRepeatSleepDatesnew(arylst_ClsRepeatSleeps.get(i).getEndTime()));
                            BackendSubmitFromDate.append(arylst_ClsRepeatSleeps.get(i).getStartTime());
                            BackendSubmitToDate.append(arylst_ClsRepeatSleeps.get(i).getEndTime());
                            btn_submit_sleep_hours.performClick();

                            return;

                        }

                    }
                }else {
                    btn_submit_sleep_hours.performClick();
                }



            }
        });

//        dialog.show();
    }


    //    private void showReepeatSleep(ClsRepeatSleepMain moodResponse, final Dialog dialogfirst) {
//        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.setContentView(R.layout.repeat_sleep);
//        RecyclerView recylr_repeat_sleep_list=dialog.findViewById(R.id.recylr_repeat_sleep_list);
//        final ArrayList<ClsRepeatSleepData> arylst_ClsRepeatSleeps=moodResponse.getData();
//
//
//        recylr_repeat_sleep_list.setAdapter(new RepeatSleepListAdapter(getContext(),arylst_ClsRepeatSleeps));
//
//
//        ImageView close_repeat_sleep_dialog=dialog.findViewById(R.id.close_repeat_sleep_dialog);
//        close_repeat_sleep_dialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        TextView txt_add_repeat_sleep=dialog.findViewById(R.id.txt_add_repeat_sleep);
//        txt_add_repeat_sleep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dialogfirst.dismiss();
//
//                int count=0;
//                for (int i = 0; i < arylst_ClsRepeatSleeps.size(); i++) {
//                    if (arylst_ClsRepeatSleeps.get(i).isChecked()){
//                        count++;
//                    }
//                }
//                if (count>1){
//                    Toast.makeText(context, "Please select only one time slot", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                dialog.dismiss();
//
//
//                for (int i = 0; i <arylst_ClsRepeatSleeps.size() ; i++) {
//                    if(arylst_ClsRepeatSleeps.get(i).isChecked()){
//                        BackendSubmitFromDate=new StringBuilder();
//                        BackendSubmitToDate=new StringBuilder();
////                        callRepeatSleepDataAPIfun(arylst_ClsRepeatSleeps.get(i));
//                        txt_sleep_date_from.setText(formatRepeatSleepDatesnew(arylst_ClsRepeatSleeps.get(i).getStartTime()));
//                        txt_sleep_date_to.setText(formatRepeatSleepDatesnew(arylst_ClsRepeatSleeps.get(i).getEndTime()));
//                        BackendSubmitFromDate.append(arylst_ClsRepeatSleeps.get(i).getStartTime());
//                        BackendSubmitToDate.append(arylst_ClsRepeatSleeps.get(i).getEndTime());
//                        return;
//
//                    }
//
//                }
//
//            }
//        });
//
//        dialog.show();
//    }
    public String  formatRepeatSleepDatesnew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    public String getFormattedDate(Date dates) {
        String strText="";
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(dates);

        if(date.endsWith("1") && !date.endsWith("11")) {
            strText = "st";
            format = new SimpleDateFormat("d'st' MMM");
        }
        else if(date.endsWith("2") && !date.endsWith("12")) {
            strText = "nd";
            format = new SimpleDateFormat("d'nd' MMM");


        } else if(date.endsWith("3") && !date.endsWith("13")) {
            strText = "rd";
            format = new SimpleDateFormat("d'rd' MMM");
        }  else {
            strText = "th";
            format = new SimpleDateFormat("d'th' MMM");
        }

        return format.format(dates);


    }


    public String formatDatesSleepStatus(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


}
