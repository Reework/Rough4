package com.shamrock.reework.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.adapter.TipsAdapter;
import com.shamrock.reework.activity.HomeModule.dialog.MindDialog;
import com.shamrock.reework.activity.HomeModule.fragment.HomeFragment;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.HomeModule.service.MinMoodModel;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.mindhistory.ClsMindHistoryAdapter;
import com.shamrock.reework.activity.mindhistory.ClsMindWeeklyData;
import com.shamrock.reework.activity.mindhistory.MindHistoryDialog;
import com.shamrock.reework.activity.mindhistory.MindHistroyListAdapter;
import com.shamrock.reework.activity.mindhistory.MindVideoListAdapter;
import com.shamrock.reework.activity.mindhistory.MindWeeklyListAdapter;
import com.shamrock.reework.activity.mindhistory.OnEditMindClick;
import com.shamrock.reework.activity.mindhistory.OnMindVideoListClick;
import com.shamrock.reework.activity.mindhistory.OnSubmitMindHistoryStatus;
import com.shamrock.reework.activity.newmind.NewMindDialog;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.activity.water.WaterVideoListAdapter;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterRequest;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.AddMoodRequest;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.request.WaterRequest;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.MoodResponse;
import com.shamrock.reework.api.response.MoodUpdateResponce;
import com.shamrock.reework.api.response.TipsResponce;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.common.ClsHistoryResponse;
import com.shamrock.reework.common.Data;
import com.shamrock.reework.common.MindHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;
import com.tfb.fbtoast.FBCustomToast;
import com.vdx.designertoast.DesignerToast;

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

public class MasterMindFragment extends Fragment implements NewMindDialog.NewGetMindMoodDialogListener, OnMindVideoListClick, OnSubmitMindHistoryStatus, View.OnClickListener, DatePickerDialog.OnDateSetListener, OnEditMindClick {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "ACTUAL_MIND_STATUS";
    private static final String ARG_PARAM4 = "BING_QUANTITY";
    RelativeLayout rel_weekly_mind;
    ImageView imgprocess_mind;
    TextView historydatte;

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    CommonService commonService;
    private RecyclerView list_mind_history;
    MindHistoryDialog mindDialog;
    CardView card_avg_mind;
    RadioGroup radioGroup_sleep;


    RadioButton rd_button_sleep_video;
    private RecyclerView recylcer_spiritual_list;
    private SessionManager session;
    TextView txt_no_data_spiritual;


    private OnFragmentInteractionListener mListener;

    private Context context;
    private TextView txt_header_mind;
    Utils utils;
    HomeFragmentService service;
    SessionManager sessionManager;
    TextView txt_no_mind_history;

    LineChart lineChart;
    int color_Orange, color_Green;
    LinearLayout linearLayout_submit, linearLayout_graph;
    Button buttonSubmit;
    RadioButton rd_button_mind_video;

    Animation animSlideDown;
    Button button;
    LinearLayout linearLayout;

    RadioButton rd_button_mind_history;
    RadioButton rd_button_mind;
    RadioButton rd_button_mind_tips;

    TextView btn_show_mind_history;
    TextView txt_mind_date_to;
    TextView txt_mind_date_from;
    LinearLayout ll_mind;
    TextView txt_avg_mind_score;
    ArrayList<MoodResponse.Data> mList;
    ArrayList<String> xValuesForChart;
    ArrayList<Entry> yValueForChart;
    ArrayList<Entry> bingValueForChart;
    //    private int userId,recoachId, iHAPPY = 5, iSTRESS = -5, iCONFUSED = 0, iANXIOUS = -3;
    private int userId, recoachId, iHAPPY = 10, iSTRESS = 0, iCONFUSED = 5, iANXIOUS = -3;
    private int iYes = -5, iNo = 5, iSometime = 0;
    View view;
    int mindValue = 2;
    String strFeeling = "Happy";
    RadioGroup RadioGroupBodyShape, RadioGroupMind;
    RadioButton radioButtonHappy, radioButtonAnxious, radioButtonStressed, radioButtonConfused,
            radioButtonMindYes, radioButtonMindNo, radioButtonMindSometime, radioButtonMind_Stressed;
    ImageView imageView_Mind_Mood;
    DatePickerDialog datepickerdialog;

    private ArrayList<TipsResponce.Datum> mDataListTips;
    private ArrayList<TipsResponce.Datum> mDataListTips_filter;
    TipsAdapter tipsAdapter, tipsAdapter1;

    RecyclerView recyclerView_tips, recyclerView_tips1;
    private HomeFragmentResponse.Data mHomeModel = null;
    private String StrDateOpen = "";
    private String dummydate_from = "";
    private String dummydate_to = "";
    private String submitToDate = "";
    private String submitFromDate = "";
    private ViewFlipper vp_mind;
    private Utils util;
    RecyclerView recycler_mind_tips;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_food_tips;
    String abc;
    LinearLayout layout_profile,layout_home,layout_setting;

    RecyclerView recycleView_LastActivity_mind;
    public MasterMindFragment() {
        // Required empty public constructor
    }

    public static MasterMindFragment newInstance(String param1, String param2, String mParam3, String mParam4) {
        MasterMindFragment fragment = new MasterMindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, mParam3);
        args.putString(ARG_PARAM4, mParam4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(context!=null) {
            abc = sessionManager.getStringValue("Allpart");
            if (abc.equals("video")) {
                getSpitualListAPiByID(5, "Mind videos ");

                vp_mind.setDisplayedChild(3);
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));


                rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.white));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind.setTextColor(getResources().getColor(R.color.black));


            } else if (abc.equals("tip")) {

                callToFetchMindTipsMasterData();
                vp_mind.setDisplayedChild(2);
//            session = new SessionManager(context);
//            session.setStringValue("Allpart", "tip");
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.white));
                rd_button_mind.setTextColor(getResources().getColor(R.color.black));

            } else if (abc.equals("history")) {
                vp_mind.setDisplayedChild(1);
                callMindHistoryApi(submitFromDate, submitToDate);

//            session = new SessionManager(context);
//            session.setStringValue("Allpart","history");

                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_mind_history.setTextColor(getResources().getColor(R.color.white));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind.setTextColor(getResources().getColor(R.color.black));

            } else if (abc.equals("daily")) {
                vp_mind.setDisplayedChild(0);
                CallToGetInitialData(false);

                CallToFetchMoodData();
//            session = new SessionManager(context);
//            session.setStringValue("Allpart","daily");
//            vp_mind.setDisplayedChild(3);
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new));

                rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind.setTextColor(getResources().getColor(R.color.white));

            }
        }

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
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            if (mParam3 == null) {
                mParam3 = "";
                mParam4 = "";
            }
        }


        sessionManager = new SessionManager(context);
        service = Client.getClient().create(HomeFragmentService.class);
        utils = new Utils();

        commonService = Client.getClient().create(CommonService.class);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);

        color_Orange = ContextCompat.getColor(context, R.color.color_graph_orange);
        color_Green = ContextCompat.getColor(context, R.color.colorBlue5);

        mList = new ArrayList<>();
        xValuesForChart = new ArrayList<>();
        yValueForChart = new ArrayList<>();
        bingValueForChart = new ArrayList<>();
    }

    private void showDatePickerDailog() {
        String strMindate[] = new SessionManager(context).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MasterMindFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();

        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            if (strMindate.length > 1) {
                c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_master_mind, container, false);
        recycleView_LastActivity_mind=view.findViewById(R.id.recycleView_LastActivity_mind);
        historydatte=view.findViewById(R.id.historydatte);
        rel_weekly_mind=view.findViewById(R.id.rel_weekly_mind);
        imgprocess_mind=view.findViewById(R.id.imgprocess_mind);


        LinearLayout ll_add_feelings = view.findViewById(R.id.ll_add_feelings);
        ll_add_feelings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                NewMindDialog mindDialog = new NewMindDialog(MasterMindFragment.this);
                mindDialog.show(fm, "mind_fragment");
            }
        });


        recycler_mind_tips = view.findViewById(R.id.recycler_mind_tips);
        radioGroup_sleep = view.findViewById(R.id.radioGroup_sleep);
        txt_no_mind_history = view.findViewById(R.id.txt_no_mind_history);
        txt_avg_mind_score = view.findViewById(R.id.txt_avg_mind_score);
        ll_mind = view.findViewById(R.id.ll_mind);
        card_avg_mind = view.findViewById(R.id.card_avg_mind);
//        radioGroup_sleep.clearCheck();

        util = new Utils();


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

        lineChart = (LineChart) view.findViewById(R.id.lineChart_mood);
        linearLayout_submit = view.findViewById(R.id.linLay_Mind_Submit);
        btn_show_mind_history = view.findViewById(R.id.btn_show_mind_history);
        linearLayout_graph = view.findViewById(R.id.linLay_Mind_Graph);
//        buttonSubmit = view.findViewById(R.id.buttonSubmitMind);
        RadioGroupBodyShape = view.findViewById(R.id.RadioGroupfeeling);
        RadioGroupMind = view.findViewById(R.id.RadioGroupMind);
        txt_header_mind = view.findViewById(R.id.txt_header_mind);
        txt_header_mind = view.findViewById(R.id.txt_header_mind);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        vp_mind = view.findViewById(R.id.vp_mind);
        rd_button_mind = view.findViewById(R.id.rd_button_mind);
        rd_button_mind_tips = view.findViewById(R.id.rd_button_mind_tips);
        list_mind_history = view.findViewById(R.id.list_mind_history);
        rd_button_mind_history = view.findViewById(R.id.rd_button_mind_history);
        rd_button_mind_video = view.findViewById(R.id.rd_button_mind_video);

        linearLayout = view.findViewById(R.id.linLay_activity_week_analysis);
        button = view.findViewById(R.id.buttonActivity_ViewMore);

        abc = sessionManager.getStringValue("Allpart");
        if (abc.equals("video")) {
            getSpitualListAPiByID(5, "Mind videos ");

            vp_mind.setDisplayedChild(3);
            rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

            rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind_video.setTextColor(getResources().getColor(R.color.white));
            rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind.setTextColor(getResources().getColor(R.color.black));
        } else if (abc.equals("tip")) {

            callToFetchMindTipsMasterData();
            vp_mind.setDisplayedChild(2);
//            session = new SessionManager(context);
//            session.setStringValue("Allpart", "tip");
            rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

            rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind_tips.setTextColor(getResources().getColor(R.color.white));
            rd_button_mind.setTextColor(getResources().getColor(R.color.black));


        } else if (abc.equals("history")) {
            vp_mind.setDisplayedChild(1);
            callMindHistoryApi(submitFromDate, submitToDate);

//            session = new SessionManager(context);
//            session.setStringValue("Allpart","history");

            rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

            rd_button_mind_history.setTextColor(getResources().getColor(R.color.white));
            rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind.setTextColor(getResources().getColor(R.color.black));

        } else if (abc.equals("daily")) {
            vp_mind.setDisplayedChild(0);
            CallToGetInitialData(false);

            CallToFetchMoodData();
//            session = new SessionManager(context);
//            session.setStringValue("Allpart","daily");
//            vp_mind.setDisplayedChild(3);
            rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new));

            rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
            rd_button_mind.setTextColor(getResources().getColor(R.color.white));
        }

        callMindWeeklyApi();

        rd_button_mind_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart","history");
                vp_mind.setDisplayedChild(1);
                callMindHistoryApi(submitFromDate, submitToDate);
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_mind_history.setTextColor(getResources().getColor(R.color.white));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind.setTextColor(getResources().getColor(R.color.black));


            }
        });
        animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

        rel_weekly_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.clearAnimation();
//                    linearLayout.startAnimation(animSlideUp);
                    linearLayout.setVisibility(View.GONE);
                    recycleView_LastActivity_mind.setVisibility(View.GONE);
                    rel_weekly_mind.setBackground(getResources().getDrawable(R.drawable.bg_black_button));

                    imgprocess_mind.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));

//                    flipCard();
                } else {
                    recycleView_LastActivity_mind.setVisibility(View.VISIBLE);
                    linearLayout.clearAnimation();
                    linearLayout.setVisibility(View.VISIBLE);
                    rel_weekly_mind.setBackground(getResources().getDrawable(R.drawable.bg_green_button_new));

                    imgprocess_mind.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
                    linearLayout.startAnimation(animSlideDown);
//                    flipCard();
                }
            }
        });


        btn_show_mind_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMindHistoryApi(submitFromDate, submitToDate);

            }
        });

        rd_button_mind_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToFetchMindTipsMasterData();
                vp_mind.setDisplayedChild(2);
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart","tip");
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.white));
                rd_button_mind.setTextColor(getResources().getColor(R.color.black));


            }
        });

        rd_button_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_mind.setDisplayedChild(0);
                callMindWeeklyApi();
                CallToGetInitialData(false);
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart","daily");
                CallToFetchMoodData();
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new));


                rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind.setTextColor(getResources().getColor(R.color.white));

            }
        });


        rd_button_mind_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpitualListAPiByID(5, "Mind videos ");
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart","video");
                vp_mind.setDisplayedChild(3);
                rd_button_mind_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_mind_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_mind.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button_mind_history.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind_video.setTextColor(getResources().getColor(R.color.white));
                rd_button_mind_tips.setTextColor(getResources().getColor(R.color.black));
                rd_button_mind.setTextColor(getResources().getColor(R.color.black));

            }
        });
        txt_mind_date_from = view.findViewById(R.id.txt_mind_date_from);
        txt_mind_date_to = view.findViewById(R.id.txt_mind_date_to);
        showDatePickerDailog();
        vp_mind.setDisplayedChild(0);

        txt_mind_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from";
                datepickerdialog.show();
            }
        });

        txt_mind_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "to";
                datepickerdialog.show();

            }
        });


        recylcer_spiritual_list = view.findViewById(R.id.recylcer_spiritual_list);
        txt_no_data_spiritual = view.findViewById(R.id.txt_no_data_spiritual);
//        getSpitualListAPiByID(5,"Mind videos ");


        dummydate_from = formatDatesSleep(submitFromDate);
//        txt_mind_date_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
//        txt_mind_date_to.setText(dummydate_to);
        showDatePickerDailog();
//        callMindHistoryApi(submitFromDate,submitToDate);


        radioButtonHappy = view.findViewById(R.id.radioButtonHappy);
        radioButtonAnxious = view.findViewById(R.id.radioButtonAnxious);
        radioButtonStressed = view.findViewById(R.id.radioButtonStressed);
        radioButtonStressed.setText("Stressed");
        radioButtonConfused = view.findViewById(R.id.radioButtonConfused);
        radioButtonMindYes = view.findViewById(R.id.radioButtonMindYes);
        radioButtonMindNo = view.findViewById(R.id.radioButtonMindNo);
        radioButtonMindSometime = view.findViewById(R.id.radioButtonMindSometime);
        radioButtonMind_Stressed = view.findViewById(R.id.radioButtonMind_Stressed);

        imageView_Mind_Mood = view.findViewById(R.id.imageView_Mind_Mood);

        recyclerView_tips = view.findViewById(R.id.recyclerView_tips);
        recyclerView_tips1 = view.findViewById(R.id.recyclerView_tips1);
        txt_header_mind.setText("Better Mind Tips");
        //for tips Data
        //Check Wheater Data is Already Loaded.
        if (HomeFragment.mCommonDataListTips.size() != 0) {
            mDataListTips = HomeFragment.mCommonDataListTips;
        }
       /* mDataListTips = new ArrayList<>();
        tipsAdapter = new TipsAdapter(context, mDataListTips,"MasterMindFragment");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView_tips.setLayoutManager(layoutManager);
        recyclerView_tips.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tips.setAdapter(tipsAdapter);*/

        if (Utils.isNetworkAvailable(context)) {
            CallToGetInitialData(false);
//            CallToFetchTipsData();
//            callToFetchMindTipsMasterData();

            CallToFetchMoodData();
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
        radioButtonHappy.setOnClickListener(this);
        radioButtonAnxious.setOnClickListener(this);
        radioButtonStressed.setOnClickListener(this);
        radioButtonConfused.setOnClickListener(this);
        radioButtonMindYes.setOnClickListener(this);
        radioButtonMindNo.setOnClickListener(this);
        radioButtonMindSometime.setOnClickListener(this);

        //for tips Data
        mDataListTips = new ArrayList<>();


        tipsAdapter = new TipsAdapter(context, mDataListTips, "MasterMindFragment");
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(context);
        recyclerView_tips.setLayoutManager(layoutManager2);
        recyclerView_tips.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tips.setAdapter(tipsAdapter);

        tipsAdapter1 = new TipsAdapter(context, mDataListTips, "MasterMindFragment");
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context);
        recyclerView_tips1.setLayoutManager(layoutManager1);
        recyclerView_tips1.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tips1.setAdapter(tipsAdapter1);

        return view;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.buttonSubmitMind:
//                if (Utils.isNetworkAvailable(context)) {
//                    if (strFeeling != null) {
//                        CallToAddMoodData();
//                    }
//                } else
//                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
//
//                break;

            case R.id.radioButtonHappy:
                strFeeling = "happy";
                if (Utils.isNetworkAvailable(context)) {
                    if (strFeeling != null) {
                        CallToAddMoodData();
                    }
                } else
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                break;

            case R.id.radioButtonAnxious:
                strFeeling = "Anxious";
                break;

            case R.id.radioButtonStressed:
                strFeeling = "stress";
                if (Utils.isNetworkAvailable(context)) {
                    if (strFeeling != null) {
                        CallToAddMoodData();
                    }
                } else
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                break;

            case R.id.radioButtonConfused:
                strFeeling = "neutral";
                if (Utils.isNetworkAvailable(context)) {
                    if (strFeeling != null) {
                        CallToAddMoodData();
                    }
                } else
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                break;


            case R.id.radioButtonMindYes:
                mindValue = 2;
                break;

            case R.id.radioButtonMindNo:
                mindValue = 1;
                break;

            case R.id.radioButtonMindSometime:
                mindValue = 3;
                break;

        }
    }


    private void toggleLayouts() {
        if (linearLayout_submit.getVisibility() == View.VISIBLE) {
            linearLayout_submit.setVisibility(View.GONE);
            linearLayout_graph.setVisibility(View.GONE);
            CallToFetchMoodData();
        } else {
            linearLayout_submit.setVisibility(View.VISIBLE);
            linearLayout_graph.setVisibility(View.GONE);
            CallToFetchMoodData();


        }
    }


    /* CALL TO GET INITIAL DATA FROM SERVER */
    private void CallToGetInitialData(final boolean isSwipeToRefresh) {
        HomeFragmentRequest request = new HomeFragmentRequest();
        /*request.setCreatedOn(lsCurrentDate);*/
        request.setReeworkerID(userId);
        request.setReecoachID(recoachId);

        String submitHistoryDate_mind = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        request.setCreatedOn(submitHistoryDate_mind);
        Call<HomeFragmentResponse> call = service.getInitialData(request);
        call.enqueue(new Callback<HomeFragmentResponse>() {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    HomeFragmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mHomeModel = listResponse.getData();

                        if (mHomeModel.getIsBingeOnLargeQuantity() != null) {
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


                                if (mHomeModel.getActualMindStatus() != null) {
                                    if (!mHomeModel.getActualMindStatus().isEmpty()) {
                                        switch (mHomeModel.getActualMindStatus()) {

                                            case "Happy":
                                                radioButtonHappy.setChecked(true);
                                                break;

                                            case "stress":
                                                radioButtonStressed.setChecked(true);
                                                break;
                                            case "Stressed":
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
                                } else if (mindStatus.equalsIgnoreCase("Anxious")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.anxious_hover);
                                } else if (mindStatus.equalsIgnoreCase("stress")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.stressed_hover);
                                } else if (mindStatus.equalsIgnoreCase("Stressed")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.stressed_hover);
                                } else if (mindStatus.equalsIgnoreCase("neutral")) {
                                    imageView_Mind_Mood.setBackgroundResource(R.drawable.confused_hover);

                                }

                                if (Utils.isNetworkAvailable(context)) {
                                    CallToFetchMoodData();

                                    //  CallToFetchMoodData();
                                } else
                                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
                            }

                        }
                    } else {
                        utils.hideProgressbar();
                        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                        } else {
                            Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();
            }
        });
    }


    private void CallToAddMoodData() {
        utils.showProgressbar(context);

        AddMoodRequest request = new AddMoodRequest();
        request.setUserId(userId);
        request.setIsBingeOnLargeQuantity(mindValue);
        request.setActualMindStatus(strFeeling);
        request.setId("0");

        Call<MoodUpdateResponce> call = service.addMoodData(request);
        call.enqueue(new Callback<MoodUpdateResponce>() {
            @Override
            public void onResponse(Call<MoodUpdateResponce> call, retrofit2.Response<MoodUpdateResponce> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MoodUpdateResponce moodResponse = response.body();

                    if (moodResponse != null && moodResponse.getCode() == 1) {
                        if (moodResponse.getMessage() != null) {
                            String bingOnLarge = null, feelingStatus;
//                            Toast.makeText(context, moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

//                            DesignerToast.defaultToast(context, "Success Toast", Gravity.END, Toast.LENGTH_LONG);

                            FBCustomToast fbCustomToast = new FBCustomToast(context);
                            fbCustomToast.setMsg("   Saved   ");
//                            fbCustomToast.setIcon(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_android_white_24dp));
                            fbCustomToast.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.bg_rounded_corner_green));
//                            fbCustomToast.setTypeface(Typeface.createFromAsset(getAssets(),"font/PoppinsBold.ttf"));
//                            fbCustomToast.set
                            fbCustomToast.show();


                            callMindHistoryApi(submitFromDate, submitToDate);
                            if (moodResponse.getData() != null) {


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
                            } else {
                                bingOnLarge = "";
                                feelingStatus = "";
                            }


                            if (bingOnLarge.equalsIgnoreCase("2")) {
                                radioButtonMind_Stressed.setText("Yes");
                            } else if (bingOnLarge.equalsIgnoreCase("1")) {
                                radioButtonMind_Stressed.setText("No");
                            } else if (bingOnLarge.equalsIgnoreCase("3")) {
                                radioButtonMind_Stressed.setText("Sometime");
                            }

                            //set Feeling Status
                            if (feelingStatus != null && feelingStatus.equalsIgnoreCase("Happy")) {
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.happy_hover);
                            } else if (feelingStatus != null && feelingStatus.equalsIgnoreCase("Anxious")) {
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.anxious_hover);
                            } else if (feelingStatus != null && feelingStatus.equalsIgnoreCase("stress")) {
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.stressed_hover);
                            } else if (feelingStatus != null && feelingStatus.equalsIgnoreCase("neutral")) {
                                imageView_Mind_Mood.setBackgroundResource(R.drawable.confused_hover);
                            }

                            toggleLayouts();
                        }

                    } else
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MoodUpdateResponce> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
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
                            mDataListTips_filter = new ArrayList<>();
                            mDataListTips_filter.clear();
                            mDataListTips_filter.addAll(tipsResponse.getData());

                            for (int i = 0; i < mDataListTips_filter.size(); i++) {

                                if (!mDataListTips_filter.get(i).getMindTips().trim().isEmpty()) {
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

    private void CallToFetchMoodData() {
        utils.showProgressbar(context);

        WaterRequest request = new WaterRequest();
        request.setUserID(userId);

        Call<MoodResponse> call = service.getMoodData(request);
        call.enqueue(new Callback<MoodResponse>() {
            @Override
            public void onResponse(Call<MoodResponse> call, retrofit2.Response<MoodResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MoodResponse moodResponse = response.body();

                    if (moodResponse != null && moodResponse.getCode() == 1) {
                        if (moodResponse.getData() != null) {
                            mList.clear();
                            yValueForChart.clear();
                            bingValueForChart.clear();
                            xValuesForChart.clear();
                            mList.addAll(moodResponse.getData());

                            if (mList != null && mList.size() > 0) {
                                for (int i = 0; i < mList.size(); i++) {

                                    try {
                                        if (mList.get(i).getIsBingeOnLargeQuantity() == 1) {
                                            bingValueForChart.add(new Entry(i, iNo));
                                        } else if (mList.get(i).getIsBingeOnLargeQuantity() == 2) {
                                            bingValueForChart.add(new Entry(i, iYes));
                                        } else {
                                            bingValueForChart.add(new Entry(i, iSometime));
                                        }

                                        if (mList.get(i).getActualMindStatus() != null) {
                                            if (mList.get(i).getActualMindStatus().equalsIgnoreCase("Happy"))
                                                yValueForChart.add(new Entry(i, iHAPPY));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("stress"))
                                                yValueForChart.add(new Entry(i, iSTRESS));

                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("Stressed"))
                                                yValueForChart.add(new Entry(i, iSTRESS));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("sad"))
                                                yValueForChart.add(new Entry(i, iSTRESS));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("Anxious"))
                                                yValueForChart.add(new Entry(i, iANXIOUS));
                                            else if (mList.get(i).getActualMindStatus().equalsIgnoreCase("neutral")) {
                                                yValueForChart.add(new Entry(i, iCONFUSED));

                                            }


                                        }
                                        String date = formatDates(mList.get(i).getCreatedOn());
                                        xValuesForChart.add(date);


                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }

                                if (!yValueForChart.isEmpty() && !xValuesForChart.isEmpty()) {
                                    initChartData(yValueForChart, xValuesForChart);

                                }

//                                temsunit
                                linearLayout_submit.setVisibility(View.VISIBLE);
                                linearLayout_graph.setVisibility(View.GONE);

                            }
                        }
                    } else {

                    }
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MoodResponse> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void initChartData(final ArrayList<Entry> yData, final ArrayList<String> xData) {
        lineChart.clear();
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.fitScreen();


       /* LineDataSet set1 = new LineDataSet(yVals1, "Monthly weights");
        //set1.setCircleColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.colorTransparent));*/

        LineDataSet set1 = new LineDataSet(yData, "mood");
        set1.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
        set1.setCircleRadius(5);
        set1.setCircleColor(getResources().getColor(R.color.colorPrimaryDark));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        //for display bing values
        ArrayList<Entry> dataset2 = new ArrayList<Entry>();
        if (!bingValueForChart.isEmpty()) {
            for (int j = 0; j < bingValueForChart.size(); j++) {

                dataset2.add(bingValueForChart.get(j));
            }
        }


//
//        LineDataSet set2 = new LineDataSet(dataset2, "Bing");
//
//        set2.setColor(ContextCompat.getColor(context, R.color.color_graph_orange));
//        set2.setLineWidth(2f);
//        set2.setValueTextColor(ContextCompat.getColor(context, R.color.colorTransparent));
//        set2.setCircleRadius(5);
//        set2.setCircleColor(getResources().getColor(R.color.color_graph_orange));
//
//
//        dataSets.add(set2);


        //set center white line
        LimitLine upper_limit = new LimitLine(0f, "");
        upper_limit.setLineWidth(2f);
        upper_limit.setLineColor(context.getResources().getColor(R.color.white));
        upper_limit.enableDashedLine(0f, 0f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);


        LineData data = new LineData(dataSets);

        lineChart.setData(data);
        lineChart.setNoDataText("Weekly mood");
        lineChart.getLegend().setEnabled(false);
//        lineChart.setVisibleXRange(5,5);
        //lineChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPremiumBlack));

        XAxis xAxis = lineChart.getXAxis();
        if (xData.size() == 1) {
            xAxis.setLabelCount(xData.size());

        } else {
            xAxis.setLabelCount(xData.size(), true);

        }
        //xAxis.setDrawLabels(false); // no axis labels
        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis at bottom


        xAxis.setValueFormatter(new IndexAxisValueFormatter(getAreaCount(xData)));


        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
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

        if (yData.size() > 5) {
            left.setAxisLineWidth(2); // axis line colour
        }

        YAxis right = lineChart.getAxisRight();
        right.setEnabled(false); // no right axis

        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
    }

    public ArrayList<String> getAreaCount(ArrayList<String> clsSleepData) {

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < clsSleepData.size(); i++)
            label.add(clsSleepData.get(i).toString());
        return label;
    }

    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("MMM dd");
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (StrDateOpen.equalsIgnoreCase("from")) {
            dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;
            txt_mind_date_from.setText(dummydate_from);
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
//            txt_mind_date_from.setText(dummydate_from);
            submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;

            callMindHistoryApi(submitFromDate, submitToDate);

        }
        if (StrDateOpen.equalsIgnoreCase("to")) {
            if (!dummydate_from.trim().isEmpty()) {
                dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;
                txt_mind_date_to.setText(dummydate_to);

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
                } else {
//                    txt_mind_date_to.setText(dummydate_to);

                    submitToDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                    callMindHistoryApi(submitFromDate, submitToDate);

                }


            } else {
                Toast.makeText(context, "Please select from date", Toast.LENGTH_SHORT).show();
            }



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

    private void callMindHistoryApi(String submitFromDate, String submitToDate) {


        MindHistoryRequest clsHistoryRequest = new MindHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)));


        try {
            historydatte.setText(formatDatesHistoy(dummydate_from)+" to "+formatDatesHistoy(dummydate_to));
            txt_mind_date_from.setText(dummydate_from);
            txt_mind_date_to.setText(dummydate_to);

        }catch (Exception e){
            e.printStackTrace();
        }

//        utils.showProgressbar(context);
        Call<ClsHistoryResponse> call = commonService.getHistoryData(clsHistoryRequest);
        call.enqueue(new Callback<ClsHistoryResponse>() {
            @Override
            public void onResponse(Call<ClsHistoryResponse> call, retrofit2.Response<ClsHistoryResponse> response) {


//                utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsHistoryResponse moodResponse = response.body();
                        if (moodResponse.getCode() != null) {

                            if (moodResponse.getCode().equals("1")) {

                                ArrayList<Data> dataArrayList = moodResponse.getData();

                                if (!dataArrayList.isEmpty()) {
                                    ArrayList<Data> dataArrayListfilter = new ArrayList<>();
                                    for (int i = 0; i < dataArrayList.size(); i++) {

                                        if (dataArrayList.get(i).getActualMindStatus() != null)
                                            dataArrayListfilter.add(dataArrayList.get(i));

                                    }


                                    if (dataArrayListfilter != null && !dataArrayListfilter.isEmpty()) {
                                        txt_no_mind_history.setVisibility(View.GONE);

                                        list_mind_history.setVisibility(View.VISIBLE);
                                        card_avg_mind.setVisibility(View.VISIBLE);
                                        callAvaerage(dataArrayListfilter);
                                        list_mind_history.setAdapter(new MindHistroyListAdapter(MasterMindFragment.this, dataArrayListfilter));

                                    } else {
                                        card_avg_mind.setVisibility(View.GONE);

                                        list_mind_history.setVisibility(View.GONE);
                                        txt_no_mind_history.setVisibility(View.VISIBLE);
                                        txt_no_mind_history.setText("No data available");
                                    }

                                } else {
                                    ArrayList<Data> dataempty = new ArrayList<>();
//                                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                                    list_mind_history.setAdapter(new MindHistroyListAdapter(MasterMindFragment.this, dataempty));


                                }


                            } else {


                            }


                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsHistoryResponse> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });


    }


    private void callMindWeeklyApi() {



        utils.showProgressbar(context);
        Call<ClsMindWeeklyData> call = commonService.getMindWeeklyHistoryData(userId);
        call.enqueue(new Callback<ClsMindWeeklyData>() {
            @Override
            public void onResponse(Call<ClsMindWeeklyData> call, retrofit2.Response<ClsMindWeeklyData> response) {



                utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsMindWeeklyData moodResponse = response.body();
                        if (moodResponse.getCode() != null) {

                            if (moodResponse.getCode().equals("1")) {

                                ArrayList<com.shamrock.reework.activity.mindhistory.Data> dataArrayList = moodResponse.getData();

                                if (!dataArrayList.isEmpty()) {
                                    try {
                                        recycleView_LastActivity_mind.setAdapter(new MindWeeklyListAdapter(MasterMindFragment.this, dataArrayList));

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }



                                } else {
//                                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();


                                }


                            } else {


                            }


                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsMindWeeklyData> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });


    }


    private void callAvaerage(ArrayList<Data> dataArrayListfilter) {
        ll_mind.setVisibility(View.VISIBLE);


        try {
            int count = 0;
            for (int i = 0; i < dataArrayListfilter.size(); i++) {

                if (dataArrayListfilter.get(i).getActualMindStatus().equalsIgnoreCase("Happy")) {
                    count = count + 10;

                }
                if (dataArrayListfilter.get(i).getActualMindStatus().equalsIgnoreCase("Neutral")) {
                    count = count + 5;

                }

            }

            double avg = Double.parseDouble(String.valueOf(count)) / dataArrayListfilter.size();

            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String showschedulestr = decimalFormat.format(avg);
            txt_avg_mind_score.setText(String.valueOf(showschedulestr));
//        Toast.makeText(context, "total "+count, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            ll_mind.setVisibility(View.GONE);
        }

    }


    private void callMindUpdateApi(ClsEditWaterRequest clsEditSleepRequest) {

        utils.showProgressbar(context);


        Call<ClsEditWaterResonse> call = commonService.setUpdateWaterHistoy(clsEditSleepRequest);
        call.enqueue(new Callback<ClsEditWaterResonse>() {
            @Override
            public void onResponse(Call<ClsEditWaterResonse> call, retrofit2.Response<ClsEditWaterResonse> response) {


                utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsEditWaterResonse moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getCode().equals("1")) {


                                callMindHistoryApi(submitFromDate, submitToDate);

                            } else {

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
            public void onFailure(Call<ClsEditWaterResonse> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    @Override
    public void getEditMindPosition(Data data) {

        SessionManager sessionManager = new SessionManager(context);
        if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
            shownoplan();
            return;
        } else {
            FragmentManager fm = getFragmentManager();
            mindDialog = new MindHistoryDialog(MasterMindFragment.this, data.getId());
            mindDialog.show(fm, "mind_fragment");
        }

    }

    private void deleteMind(int id) {
        utils.showProgressbar(context);
        FoodService   foodService = Client.getClient().create(FoodService.class);

        Call<RDPSuccess> call = foodService.getDeleteMind(id);
        call.enqueue(new Callback<RDPSuccess>() {
            @Override
            public void onResponse(Call<RDPSuccess> call, Response<RDPSuccess> response) {
                utils.hideProgressbar();
                RDPSuccess rdpSuccess;
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    if (response.body().getCode() == 1) {


                        if (response.body().getData() != null) {
                            Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();

                            callMindHistoryApi(submitFromDate,submitToDate);
                        }

                    } else {

                        Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPSuccess> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void deleteMindPosition(Data data) {

        deleteMind(Integer.parseInt(data.getId()));
    }

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
    public void getMindStatus(MinMoodModel status, String statusid) {

        ClsEditWaterRequest clsEditWaterRequest = new ClsEditWaterRequest();
        clsEditWaterRequest.setActualValue(String.valueOf(status.getName()));
        clsEditWaterRequest.setReportType(4);
        clsEditWaterRequest.setStatusId(Integer.parseInt(statusid));
        callMindUpdateApi(clsEditWaterRequest);

    }

    @Override
    public void onSubmitMindMood(MinMoodModel model) {
        strFeeling = model.getName();
        switch (model.getName()) {
            case "Happy":
                radioButtonHappy.setChecked(true);
                break;

            case "happy":
                radioButtonHappy.setChecked(true);
                break;

            case "stress":
                radioButtonStressed.setChecked(true);
                break;
            case "Stressed":
                radioButtonStressed.setChecked(true);
                break;

            case "neutral":
                radioButtonConfused.setChecked(true);
                break;

        }


        CallToAddMoodData();


    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void callToFetchMindTipsMasterData() {

        util.showProgressbar(getActivity());

        Call<ClsSleepTips> call = service.getMasterFoodTipsData(2);
        call.enqueue(new Callback<ClsSleepTips>() {
            @Override
            public void onResponse(Call<ClsSleepTips> call, retrofit2.Response<ClsSleepTips> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsSleepTips tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {

                            if (!tipsResponse.getData().isEmpty()) {
                                arylst_food_tips = new ArrayList<>();
                                arylst_food_tips.clear();
                                arylst_food_tips.addAll(tipsResponse.getData());


                                ClsSleepTipsAdapter adapter = new ClsSleepTipsAdapter(getContext(), arylst_food_tips, "MasterSleepFragment");
                                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                                recycler_mind_tips.setLayoutManager(layoutManager1);
                                recycler_mind_tips.setItemAnimator(new DefaultItemAnimator());
                                recycler_mind_tips.setAdapter(adapter);

                            } else {
//                                Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else {

                    }
//                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

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

    private void getSpitualListAPiByID(int id, final String libraryName) {


        utils = new Utils();
        session = new SessionManager(getActivity());
        try {
            utils.showProgressbar(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritualListMain> call = commonService.getDailyDiaryVideoByCategoryId(id);
        call.enqueue(new Callback<ClsSpiritualListMain>() {
            @Override
            public void onResponse(Call<ClsSpiritualListMain> call, retrofit2.Response<ClsSpiritualListMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsSpiritualListMain moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getCode().equals("1")) {

                                if (moodResponse.getData() != null && !moodResponse.getData().isEmpty()) {
                                    txt_no_data_spiritual.setVisibility(View.GONE);
                                    recylcer_spiritual_list.setVisibility(View.VISIBLE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData = moodResponse.getData();

                                    recylcer_spiritual_list.setAdapter(new MindVideoListAdapter(MasterMindFragment.this, arylst_SpiritualTypeData));


                                } else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName + " not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData = new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new MindVideoListAdapter(MasterMindFragment.this, arylst_SpiritualTypeData));

                                }


                            } else {


                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ClsSpiritualListMain> call, Throwable t) {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    public void getFoodVideoData(String videoLink, String title, String description) {

        Intent intent = new Intent(getActivity(), ExoActivity.class);
        intent.putExtra("VideoID", extractYTId(videoLink));
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);


    }

    public String extractYTId(String url) {


        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        final String[] videoIdRegex = {"\\?vi?=([^&]*)", "watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        for (String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;

    }

    public String youTubeLinkWithoutProtocolAndDomain(String url) {
        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return url.replace(matcher.group(), "");
        }
        return url;
    }

}
