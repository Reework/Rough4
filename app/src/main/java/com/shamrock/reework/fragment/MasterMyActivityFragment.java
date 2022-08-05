
package com.shamrock.reework.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.mobikwik.mobikwikpglib.circleprogress.CircleProgressView;
import com.shamrock.R;
import com.shamrock.reework.activity.DailyActivityModule.Service.FitBitClient;
import com.shamrock.reework.activity.DailyActivityModule.Service.FitBitService;
import com.shamrock.reework.activity.GFitActivity.controller.GfitHomeActivity;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.adapter.ActivtyFragAdapter;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightRequest;
import com.shamrock.reework.activity.activtyhistory.Activities;
import com.shamrock.reework.activity.activtyhistory.ActivityData;
import com.shamrock.reework.activity.activtyhistory.ActivityHistoryMainAdapter;
import com.shamrock.reework.activity.activtyhistory.ActivityVideoListAdapter;
import com.shamrock.reework.activity.activtyhistory.ClsNewActivityHistoryPojo;
import com.shamrock.reework.activity.activtyhistory.OnActivtyVideoListClick;
import com.shamrock.reework.activity.activtyhistory.WeeklyActivityData;
import com.shamrock.reework.activity.activtyhistory.WeeklyActivitymainPojo;
import com.shamrock.reework.activity.activtymaster.service.DeviceListAdapter;
import com.shamrock.reework.activity.activtymaster.service.MyActivityService;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.ClsRepeatActivityRequest;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.ClsRepeatActivtyMainPojo;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.RepeatActivityAddRequest;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.RepeatActivityListAdapter;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsPostHealthData;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsgetPostData;
import com.shamrock.reework.activity.reeworkerhealth.app.ReeevaluateHealthparamActivity;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.common.DurationTimePickDialog;
import com.shamrock.reework.database.FitBitSessionManager;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.AddFitbitDataToServerRes;
import com.shamrock.reework.model.ClsGadgetData;
import com.shamrock.reework.model.ClsGadgetmainData;
import com.shamrock.reework.model.GetAllFitbitActivitiesRes;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;
import com.shamrock.reework.model.MasterActivty.ActivityResponse;
import com.shamrock.reework.model.MasterActivty.AddActivityRequest;
import com.shamrock.reework.model.MasterActivty.Data;
import com.shamrock.reework.model.MasterActivty.DeleteActivityRequest;
import com.shamrock.reework.model.MasterActivty.MyActivtyHistroy;
import com.shamrock.reework.model.MasterActivty.MyActivtyListMaster;
import com.shamrock.reework.model.TokenResponse;
import com.shamrock.reework.util.Utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobikwik.mobikwikpglib.utils.Utils.getCurrentDate;
import static com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity.healthParamData_static;

public class MasterMyActivityFragment extends Fragment implements ActivityHistoryDeleteListener, OnActivtyVideoListClick, ActivtyListListener, View.OnClickListener, Animation.AnimationListener, AddActivityDialogListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txt_repeat_activity;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    Context context;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_food_tips;
    TextView txt_no_act_histoy, historydatte;

    TextView txt_avg_activity;
    LinearLayout ll_avg_activity;

    ImageView img_add_act_histy_date;
    TextView txt_activity_history_date,txt_status;
    RecyclerView recylcler_activity_history;

    //    private ListView reclerview_activity;
    private RecyclerView reclerview_activity;
    private ImageView ll_create_Activity;
    AddActivtyDialogFragment dialogFragment;
    Toolbar toolbar;
    RelativeLayout rel_header_Activty;
    //    MyActivitiesAdapter myActivitiesAdapter;
    MyActivityRecyclerAdapter myActivitiesAdapter;
    Utils utils;
    LinearLayout linLay_sleep_week_analysis;
    MyActivityService service;
    LinearLayout ll_header;
    Data data;
    ArrayList<AcivityHistory> weeklyActivtylist;

    TextView txtNoData;
    LinearLayout ll_view_mores;
    Animation animSlideDown, animSlideUp;

    RecyclerView listView_last_sleep_List;
    private String userid;
    ActivtyFragAdapter activtyFragAdapter;
    DatePickerDialog datepickerdialog_history;

    SessionManager sessionManager;
    Button buttonSleep_ViewMore;
    LinearLayout ll_fitbit_Activity1;
    TextView ButtonFitBitConnect;
    TextView ButtonFitBitConnect2;
    FitBitSessionManager sessionFitBitSessionManager;
    FitBitService fitbitClient;
    String txtAuthorization = "Basic " + "MjJCNzZIOjc3Yjg1MGZiZjE1NjE2NzZhYWY0ZjFhYTlmYWQzYjNi";
    private String StrDateOpen = "";
    private String dummyhistoryActshowdate = "";
    private String submitActivityHistoryDate = "";


    private TextView btn_show_weight_history, txt_weight_date_to, txt_weight_date_from;
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView txt_no_weight;
    private int userID;
    private HealthParametersService healthParametersService;
    RadioButton rd_button_activity_history;
    RadioButton rd_button_activity;
    private ViewFlipper vp_activity;
    RadioButton rd_button_activity_tips;
    RecyclerView recycler_activity_tips;
    private Utils util;
    private HomeFragmentService services;
    ImageView img_clock;


    RadioButton rd_button_activity_video;
    private RecyclerView recylcer_spiritual_list;
    private SessionManager session;
    TextView txt_no_data_spiritual;
    private CommonService commonService;
    private DurationTimePickDialog timepickerdialog;

    TextView txt_add_Activity_repeat;
    ArrayList<AcivityHistory> dataResponseList_repeat;
    String abc;

    TextView txt_activity, txt_totaltime, txt_calories;
    CircularProgressBar progress_daily_consumed;

    LinearLayout layout_profile,layout_home,layout_setting;


    public MasterMyActivityFragment() {
        // Required empty public constructor
    }

    public static MasterMyActivityFragment newInstance(String param1, String param2) {
        MasterMyActivityFragment fragment = new MasterMyActivityFragment();
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
    public void onResume() {
        super.onResume();
        if (context != null) {
            sessionManager = new SessionManager(context);
            abc = sessionManager.getStringValue("Allpart");
            if (abc.equals("video")) {
                getSpitualListAPiByID(4, "Activity videos ");

                vp_activity.setDisplayedChild(3);
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));
            } else if (abc.equals("tip")) {

                callToActivityTipsMasterData();
                vp_activity.setDisplayedChild(2);

//            session = new SessionManager(context);
//            session.setStringValue("Allpart", "tip");
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));

            } else if (abc.equals("history")) {
                vp_activity.setDisplayedChild(1);

                callActivityHistoryApi(submitFromDate, submitToDate);

//            session = new SessionManager(context);
//            session.setStringValue("Allpart","history");

                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));
            } else if (abc.equals("daily")) {
                vp_activity.setDisplayedChild(0);
                callGetmyActivityListApis();
                callweeklymyActivityListApis();

//            session = new SessionManager(context);
//            session.setStringValue("Allpart","daily");
//            vp_mind.setDisplayedChild(3);
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new));
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

    private void showDatePickerHistoryAddDailog() {
        String strMindate[] = new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_history = new DatePickerDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MasterMyActivityFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        sessionManager = new SessionManager(context);
        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            if (strMindate.length > 1) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_new_my, container, false);
        context = getActivity();
        utils = new Utils();
        sessionManager = new SessionManager(context);
        userid = String.valueOf(sessionManager.getIntValue(SessionManager.KEY_USER_ID));
        ll_avg_activity = view.findViewById(R.id.ll_avg_activity);

        img_clock = view.findViewById(R.id.img_clock);
        historydatte = view.findViewById(R.id.historydatte);
        txt_avg_activity = view.findViewById(R.id.txt_avg_activity);
        txt_totaltime = view.findViewById(R.id.txt_totaltime);
        txt_activity = view.findViewById(R.id.txt_activity);
        txt_calories = view.findViewById(R.id.txt_calories);
        progress_daily_consumed=view.findViewById(R.id.progress_daily_consumed);

        txt_repeat_activity = view.findViewById(R.id.txt_repeat_activity);
        txt_repeat_activity.setPaintFlags(txt_repeat_activity.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//        callGetmyRepeatActivityListApis();
        progress_daily_consumed.setProgressMax(100);


        txt_repeat_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showChhoseActivityDailog();
                callGetmyRepeatActivityListApis();


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


        recylcler_activity_history = view.findViewById(R.id.recylcler_activity_history);
        txt_no_act_histoy = view.findViewById(R.id.txt_no_act_histoy);
        recycler_activity_tips = view.findViewById(R.id.recycler_activity_tips);
        rd_button_activity_tips = view.findViewById(R.id.rd_button_activity_tips);
        btn_show_weight_history = view.findViewById(R.id.btn_show_weight_history);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        rd_button_activity = view.findViewById(R.id.rd_button_activity);
        rd_button_activity_history = view.findViewById(R.id.rd_button_activity_history);
        vp_activity = view.findViewById(R.id.vp_activity);
        vp_activity.setDisplayedChild(0);
        rd_button_activity_video = view.findViewById(R.id.rd_button_activity_video);


        sessionManager = new SessionManager(context);


        rd_button_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vp_activity.setDisplayedChild(0);
                callGetmyActivityListApis();
                callweeklymyActivityListApis();
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart", "daily");
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new));


            }
        });

        rd_button_activity_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_activity.setDisplayedChild(1);
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart", "history");
                callActivityHistoryApi(submitFromDate, submitToDate);
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));


            }
        });

        rd_button_activity_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToActivityTipsMasterData();
                vp_activity.setDisplayedChild(2);
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart", "tip");
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));


            }
        });

        healthParametersService = Client.getClient().create(HealthParametersService.class);
        showDatePickerHistoryAddDailog();


        recylcer_spiritual_list = view.findViewById(R.id.recylcer_spiritual_list);
        txt_no_data_spiritual = view.findViewById(R.id.txt_no_data_spiritual);
        getSpitualListAPiByID(4, "Activity videos ");

        rd_button_activity_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpitualListAPiByID(4, "Activity videos ");
                sessionManager = new SessionManager(context);
                sessionManager.setStringValue("Allpart", "video");
                vp_activity.setDisplayedChild(3);
                rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new));
                rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));

            }
        });

        txt_weight_date_from = view.findViewById(R.id.txt_weight_date_from);
        txt_weight_date_to = view.findViewById(R.id.txt_weight_date_to);

        txt_weight_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from";
                datepickerdialog_history.show();
            }
        });

        txt_weight_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "to";
                datepickerdialog_history.show();

            }
        });


        dummydate_from = formatDatesSleep(submitFromDate);
        txt_weight_date_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
        txt_weight_date_to.setText(dummydate_to);
//        callActivityHistoryApi(submitFromDate,submitToDate);

        callToActivityTipsMasterData();


        btn_show_weight_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivityHistoryApi(submitFromDate, submitToDate);

            }
        });


        reclerview_activity = view.findViewById(R.id.reclerview_activity);
        txtNoData = view.findViewById(R.id.txtNoData);
        ll_create_Activity = view.findViewById(R.id.ll_create_Activity);
        buttonSleep_ViewMore = view.findViewById(R.id.buttonSleep_ViewMore);
        linLay_sleep_week_analysis = view.findViewById(R.id.linLay_sleep_week_analysis);
        listView_last_sleep_List = view.findViewById(R.id.listView_last_sleep_List);
        rel_header_Activty = view.findViewById(R.id.rel_header_Activty);
        ll_fitbit_Activity1 = view.findViewById(R.id.ll_fitbit_Activity1);
        ButtonFitBitConnect = view.findViewById(R.id.ButtonFitBitConnect);
        ButtonFitBitConnect2 = view.findViewById(R.id.ButtonFitBitConnect2);
        ButtonFitBitConnect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonFitBitConnect.performClick();
            }
        });
        txt_activity_history_date = view.findViewById(R.id.txt_activity_history_date);
        txt_status = view.findViewById(R.id.txt_status);
        img_add_act_histy_date = view.findViewById(R.id.img_add_act_histy_date);

        String strText1="";
        if (!sessionManager.getStringValue("showDate").isEmpty()) {

            if (!sessionManager.getStringValue("CalenderSelectedDate").isEmpty()) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                SimpleDateFormat simpleDateFormat12 = new SimpleDateFormat("dd-MMM-yyyy");
                Date varDate = null, varDate1 = null;
                try {
                    varDate = simpleDateFormat.parse(sessionManager.getStringValue("CalenderSelectedDate"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                simpleDateFormat = new SimpleDateFormat("dd-MMM");
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
//                simpleDateFormat12.format(varDate1);

                strText1 = (simpleDateFormat12.format(varDate1));


                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);


                if (strText1.equals(formattedDate)) {
                    txt_activity_history_date.setText(strText1);
                    txt_status.setText("Today’s Activities");
                } else {
                    txt_activity_history_date.setText(strText1);
                    txt_status.setText("Earlier Activities");
                }



//                txt_activity_history_date.setText(simpleDateFormat.format(varDate) + "'s status");
//                txt_todaysmeal_header.setText(sessionManager.getStringValue("CalenderSelectedDate"));

            } else {
                txt_activity_history_date.setText(sessionManager.getStringValue("showDate"));

                txt_status.setText("Today’s Activities");


            }


        } else {
//            txt_activity_history_date.setText("Today's status");

        }

//        txt_activity_history_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        submitActivityHistoryDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        img_add_act_histy_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                datepickerdialog_history.show();
                StrDateOpen = "fromactHistory";

            }
        });


//        ll_header = view.findViewById(R.id.ll_header);
        ll_create_Activity.setOnClickListener(this);
        buttonSleep_ViewMore.setOnClickListener(this);
        ll_fitbit_Activity1.setOnClickListener(this);
        ButtonFitBitConnect.setOnClickListener(this);
        img_clock.setOnClickListener(this);

        service = Client.getClient().create(MyActivityService.class);
        fitbitClient = FitBitClient.getClient().create(FitBitService.class);
        sessionManager = new SessionManager(context);
        sessionFitBitSessionManager = new FitBitSessionManager(context);
        userid = String.valueOf(sessionManager.getIntValue(SessionManager.KEY_USER_ID));
        animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        animSlideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        callActivityAndSubActivityApi();
        boolean isLoggedIn = sessionFitBitSessionManager.getBooleanValue(FitBitSessionManager.IS_FITBIT_LOGIN);
        if (isLoggedIn) {

            String ConnectOrDisconnect = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_CONNECT_OR_DISSCONNECT);
            ButtonFitBitConnect.setText(sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_CONNECT_OR_DISSCONNECT));
            if (Utils.isNetworkAvailable(getContext())) {
                String Token = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN);
                String RefreshToken = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN);
                String TokenType = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE);
                String UserId = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_USER_ID);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                try {
                    if (ConnectOrDisconnect.equalsIgnoreCase("Add My Device")) {
                        callGetmyActivityListApis();
                    } else {
                        callToGetAllActivitiesApi(TokenType + " " + Token, UserId, dateFormat.format(date));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            callGetmyActivityListApis();
            ButtonFitBitConnect.setText("Add My Device");
        }

        callweeklymyActivityListApis();

        callGetmyActivityListApis();


        abc = sessionManager.getStringValue("Allpart");
        if (abc.equals("video")) {
            getSpitualListAPiByID(4, "Activity videos ");

            vp_activity.setDisplayedChild(3);
            rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));
        } else if (abc.equals("tip")) {

            callToActivityTipsMasterData();
            vp_activity.setDisplayedChild(2);

//            session = new SessionManager(context);
//            session.setStringValue("Allpart", "tip");
            rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));

        } else if (abc.equals("history")) {
            vp_activity.setDisplayedChild(1);

            callActivityHistoryApi(submitFromDate, submitToDate);

//            session = new SessionManager(context);
//            session.setStringValue("Allpart","history");

            rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new));
            rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new1));
        } else if (abc.equals("daily")) {
            vp_activity.setDisplayedChild(0);
            callGetmyActivityListApis();
            callweeklymyActivityListApis();

//            session = new SessionManager(context);
//            session.setStringValue("Allpart","daily");
//            vp_mind.setDisplayedChild(3);
            rd_button_activity_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity_tips.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rd_button_activity.setBackgroundResource((R.drawable.custom_white_radio_new));
        }
        final RelativeLayout rel_weekly_mind = view.findViewById(R.id.rel_weekly_mind);
        final LinearLayout linearLayout = view.findViewById(R.id.linLay_sleep_week_analysis);
        final ImageView imgprocess_mind = view.findViewById(R.id.imgprocess_mind);


        rel_weekly_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.clearAnimation();
//                    linearLayout.startAnimation(animSlideUp);
                    linearLayout.setVisibility(View.GONE);
                    rel_weekly_mind.setBackground(getResources().getDrawable(R.drawable.bg_black_button));

                    imgprocess_mind.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus));

//                    flipCard();
                } else {
                    linearLayout.clearAnimation();
                    linearLayout.setVisibility(View.VISIBLE);
                    rel_weekly_mind.setBackground(getResources().getDrawable(R.drawable.bg_green_button_new));

                    imgprocess_mind.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
                    linearLayout.startAnimation(animSlideDown);
//                    flipCard();
                }
            }
        });


//        txt_repeat_activity.performClick();

        return view;
    }

    private void showChhoseActivityDailog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);


        builder.setMessage("Select Add Activity option")
                .setCancelable(true)
                .setPositiveButton(" NEW ACTIVITY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();


                    }
                })
                .setNegativeButton(" REPEAT ACTIVITY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        android.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();
    }

    public void addRepeatActivityAPi(ArrayList<AcivityHistory> dataResponseList_repeat_new) {
        int userid_new = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        ArrayList<ClsRepeatActivityRequest> aryClsRepeatActivityRequests = new ArrayList<>();
        for (int i = 0; i < dataResponseList_repeat_new.size(); i++) {

            aryClsRepeatActivityRequests.add(new ClsRepeatActivityRequest(Integer.parseInt(dataResponseList_repeat_new.get(i).getTotalMinutes())
                    , userid_new, Integer.parseInt(dataResponseList_repeat_new.get(i).getActivityId()),
                    0, sessionManager.getStringValue("statusdate"), dataResponseList_repeat_new.get(i).getActivityTime(), Integer.parseInt(dataResponseList_repeat_new.get(i).getSubActivityId())));
        }


        utils.showProgressbar(context);
        RepeatActivityAddRequest new_RepeatActivityAddRequest = new RepeatActivityAddRequest();
        new_RepeatActivityAddRequest.setAddActivityRequests(aryClsRepeatActivityRequests);

        Call<ActivityResponse> call = service.addrepeatActivityData(aryClsRepeatActivityRequests);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        callGetmyActivityListApis();
                        callweeklymyActivityListApis();


                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void calltimepikerrepeat() {
        timepickerdialog = new DurationTimePickDialog(context, MasterMyActivityFragment.this,
                0,
                1,
                true);
        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
        timepickerdialog.setTitle("Select Time Spend in Activity");

    }

    private void callRepeatActivitylistApis() {

//        http://shamrockuat.dweb.in/api/Report/RepeatActivity?UserId=3040
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


    private void callActivityAndSubActivityApi() {


        utils.showProgressbar(context);
        Call<MyActivtyListMaster> call = service.getAllActivityListMaster();
        call.enqueue(new Callback<MyActivtyListMaster>() {
            @Override
            public void onResponse(Call<MyActivtyListMaster> call, Response<MyActivtyListMaster> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MyActivtyListMaster tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode().equals("1")) {
                        if (tipsResponse.getData() != null) {
                            data = tipsResponse.getData();

                        }
                    } else
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyActivtyListMaster> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void setWeeklyData(ArrayList<WeeklyActivityData> dataResponseList) {
        activtyFragAdapter = new ActivtyFragAdapter(context, dataResponseList);
        listView_last_sleep_List.setLayoutManager(new LinearLayoutManager(context));
        listView_last_sleep_List.setItemAnimator(new DefaultItemAnimator());
        listView_last_sleep_List.setAdapter(activtyFragAdapter);

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animSlideUp) {
            ll_view_mores.setVisibility(View.GONE);
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

    @Override
    public void onActivityAdd(String ActivityID, String SubActivtyID, String min, String ID, String activityclocktime) {


        callAddActivtyAPI(ActivityID, SubActivtyID, min, ID, activityclocktime);
    }

    private void callAddActivtyAPI(String activityID, String subActivtyID, String min, String ID, String activityclocktime) {


        AddActivityRequest addActivityRequest = new AddActivityRequest();
        addActivityRequest.setActivityId(activityID);
        addActivityRequest.setSubActivityId(subActivtyID);
        addActivityRequest.setTotalMinutes(min);
        addActivityRequest.setId("0");
        addActivityRequest.setActivityTime(activityclocktime);
        addActivityRequest.setCreatedOn(sessionManager.getStringValue("statusdate"));
        addActivityRequest.setUserId(userid);
        utils.showProgressbar(context);

        Call<ActivityResponse> call = service.setActivityData(addActivityRequest);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        callGetmyActivityListApis();
                        callweeklymyActivityListApis();


                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });


    }


    private void callToAddFitbitActivities(List<AddFitbitDataToServerRes.Activity> model) {

        AddFitbitDataToServerRes request = new AddFitbitDataToServerRes();
        request.setActivities(model);
        request.setUserId(Integer.parseInt(userid));
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }
        String gsonString = new Gson().toJson(request);
        String gsonStriTest = gsonString;
        Call<ActivityResponse> call = service.addFitibitActivities(request);
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();
                    if (listResponse.getCode().equalsIgnoreCase("1")) {
                        Utils.shortToast(context, "fitbit data Added Succesfully");
                        callGetmyActivityListApis();
                    } else {

                    }
                } else {
//
                }
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });
    }


    private void callGetmyActivityListApis() {

        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        services = Client.getClient().create(HomeFragmentService.class);
        Call<MyActivtyHistroy> call = service.getUserActivityHistroy(Integer.parseInt(userid), sessionManager.getStringValue("statusdate"));
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<MyActivtyHistroy>() {
            @Override
            public void onResponse(Call<MyActivtyHistroy> call, Response<MyActivtyHistroy> response) {
                utils.hideProgressbar();
                callGetmyRepeatActivityListApis();


                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MyActivtyHistroy listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        ArrayList<AcivityHistory> dataResponseList = listResponse.getData().getResult().getDailyHistory().getAcivityHistory();
                        weeklyActivtylist = listResponse.getData().getResult().getWeeklyHistory().getAcivityHistory();
                        if (dataResponseList != null) {
//                            setWeeklyData(d ataResponseList);

                            if (!dataResponseList.isEmpty()) {
                                int abc = 0;
                                String strduraton = "";

                                float cal = 0;

                                for (int i = 0; i < dataResponseList.size(); i++) {
                                    abc = Integer.parseInt(dataResponseList.get(i).getTotalMinutes()) + abc;
                                    cal = Float.parseFloat(dataResponseList.get(i).getTotalBurnedCalories()) + cal;
                                }

                                if ((abc) >= 60) {

                                    strduraton = formatHoursAndMinutes1((abc));
                                    if (strduraton.contains("Hour")) {
                                        txt_totaltime.setText(strduraton);

                                    }
                                    if (strduraton.contains("Hours")) {
                                        txt_totaltime.setText(strduraton);

                                    }

                                } else {

                                    txt_totaltime.setText(abc + " Mins");

                                }


                                txt_calories.setText(Math.round(cal) + " Cal");
                                txt_activity.setText(dataResponseList.size()+"");


                                progress_daily_consumed.setProgress((float) cal);


//                                txtNoData.setVisibility(View.GONE);
//                                rel_header_Activty.setVisibility(View.VISIBLE);
//
//
//                                reclerview_activity.setVisibility(View.VISIBLE);
//
//                                myActivitiesAdapter = new MyActivityRecyclerAdapter(context, dataResponseList, MasterMyActivityFragment.this);
//
//                                reclerview_activity.setLayoutManager(new LinearLayoutManager(context));
//                                reclerview_activity.setItemAnimator(new DefaultItemAnimator());
//                                reclerview_activity.setAdapter(myActivitiesAdapter);
                            } else {
//                                rel_header_Activty.setVisibility(View.INVISIBLE);
//                                txtNoData.setVisibility(View.VISIBLE);
//                                reclerview_activity.setVisibility(View.GONE);
                            }


                        } else {
//                            rel_header_Activty.setVisibility(View.INVISIBLE);
//
//                            txtNoData.setVisibility(View.VISIBLE);
//                            reclerview_activity.setVisibility(View.GONE);
                        }
                    } else {
//                        rel_header_Activty.setVisibility(View.INVISIBLE);
//
//                        txtNoData.setVisibility(View.VISIBLE);
//                        reclerview_activity.setVisibility(View.GONE);
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<MyActivtyHistroy> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
                ShowRetryBar();
                callGetmyRepeatActivityListApis();

            }
        });
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


    private void callGetmyRepeatActivityListApis() {

//        if (!((Activity) context).isFinishing())
//        {
//            utils.showProgressbar(context);
//        }

        service = Client.getClient().create(MyActivityService.class);
        ;
        services = Client.getClient().create(HomeFragmentService.class);
        Call<ClsRepeatActivtyMainPojo> call = service.RepeatActivity(Integer.parseInt(userid));
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<ClsRepeatActivtyMainPojo>() {
            @Override
            public void onResponse(Call<ClsRepeatActivtyMainPojo> call, Response<ClsRepeatActivtyMainPojo> response) {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsRepeatActivtyMainPojo listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        try {
                            dataResponseList_repeat = listResponse.getData();
                            if (dataResponseList_repeat != null) {
//                            setWeeklyData(dataResponseList);

                                if (!dataResponseList_repeat.isEmpty()) {

                                    if (sessionManager.getStringValue("AddActivity").equalsIgnoreCase("true")){
                                        ll_create_Activity.performClick();
                                        sessionManager.setStringValue("DialogClose","true");

                                        sessionManager.setStringValue("AddActivity","");


                                    }else {
                                        sessionManager.setStringValue("AddActivity","");

                                    }


                                    final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                                    dialog.setContentView(R.layout.dialog_add_repat_activity);

                                    ImageView close_repeat_activity_dialog = dialog.findViewById(R.id.close_repeat_activity_dialog);
                                    close_repeat_activity_dialog.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    RecyclerView recylr_repeat_activity_list = dialog.findViewById(R.id.recylr_repeat_activity_list);
                                    recylr_repeat_activity_list.setHasFixedSize(true);
//                                    recylr_repeat_activity_list.setAdapter(new RepeatActivityListAdapter(MasterMyActivityFragment.this, dataResponseList_repeat));
                                    txt_add_Activity_repeat = dialog.findViewById(R.id.txt_add_Activity_repeat);

                                    txt_add_Activity_repeat.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            ArrayList<AcivityHistory> dataResponseList_repeat_new;

                                            dataResponseList_repeat_new = new ArrayList<>();
                                            for (int i = 0; i < dataResponseList_repeat.size(); i++) {
                                                if (dataResponseList_repeat.get(i).isSelect()) {
                                                    dataResponseList_repeat_new.add(dataResponseList_repeat.get(i));
                                                }
                                            }

                                            if (dataResponseList_repeat_new.isEmpty()) {
                                                Toast.makeText(context, "Please select activity", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
//                                            dialog.dismiss();


                                            addRepeatActivityAPi(dataResponseList_repeat_new);


                                        }
                                    });




//                                    dialog.show();
                                } else {
                                    Toast.makeText(context, "No activity found", Toast.LENGTH_SHORT).show();


                                }


                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<ClsRepeatActivtyMainPojo> call, Throwable t) {
                // Log error here since request failed
//                utils.hideProgressbar();
                ShowRetryBar();
            }
        });
    }


    private void callweeklymyActivityListApis() {

        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        services = Client.getClient().create(HomeFragmentService.class);
        Call<WeeklyActivitymainPojo> call = service.GetWeeklyActivityAnalysis(Integer.parseInt(userid));
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<WeeklyActivitymainPojo>() {
            @Override
            public void onResponse(Call<WeeklyActivitymainPojo> call, Response<WeeklyActivitymainPojo> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    WeeklyActivitymainPojo listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        ArrayList<WeeklyActivityData> dataResponseList = listResponse.getData();
                        if (dataResponseList != null) {
                            setWeeklyData(dataResponseList);


                        } else {

                        }
                    } else {

                    }
                } else {
//                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<WeeklyActivitymainPojo> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
                ShowRetryBar();
            }
        });
    }


    @Override
    public void onActivityEdit(String ActivityID, String SubActivtyID, String min, String ID, String activityclocktime) {


        callMyActivityEditApi(ActivityID, SubActivtyID, min, ID, activityclocktime);

    }

    private void callMyActivityEditApi(String activityID, String subActivtyID, String min, String id, String activityclocktime) {

        AddActivityRequest addActivityRequest = new AddActivityRequest();
        addActivityRequest.setActivityId(activityID);
        addActivityRequest.setSubActivityId(subActivtyID);
        addActivityRequest.setTotalMinutes(min);
        addActivityRequest.setId(id);
        addActivityRequest.setActivityTime(activityclocktime);
        addActivityRequest.setCreatedOn(sessionManager.getStringValue("statusdate"));
        addActivityRequest.setUserId(userid);
        utils.showProgressbar(context);


        Call<ActivityResponse> call = service.editUserActivity(addActivityRequest);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();
                    Log.d("ListResponce", listResponse.getData());

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        dialogFragment.dismiss();

                        callGetmyActivityListApis();
                        callweeklymyActivityListApis();
                        callActivityHistoryApi(submitFromDate, submitToDate);

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });

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
    public void onClick(View view) {

        if (view.getId() == R.id.ll_create_Activity) {

            if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                shownoplan();

                return;

            }


            if (data != null) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                dialogFragment = new AddActivtyDialogFragment(MasterMyActivityFragment.this);
                Bundle bundle = new Bundle();
                bundle.putSerializable("activtylist", data);
                bundle.putSerializable("Repeatactivtylist", dataResponseList_repeat);

                dialogFragment.setArguments(bundle);


                dialogFragment.show(fm, "add_fragment");
            }

        }

        if (view.getId() == R.id.buttonSleep_ViewMore) {

            if (weeklyActivtylist != null) {
                if (weeklyActivtylist.isEmpty()) {
                    Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
                return;

            }


            if (linLay_sleep_week_analysis.getVisibility() == View.VISIBLE) {
                linLay_sleep_week_analysis.clearAnimation();
                //linearLayout.startAnimation(animSlideUp);
                linLay_sleep_week_analysis.setVisibility(View.GONE);
                buttonSleep_ViewMore.setText("View last 7 days");
                //flipCard();
            } else {
                linLay_sleep_week_analysis.clearAnimation();
                linLay_sleep_week_analysis.setVisibility(View.VISIBLE);
                buttonSleep_ViewMore.setText("Less");
                linLay_sleep_week_analysis.startAnimation(animSlideDown);
                //flipCard();
            }
        }
        if (view.getId() == R.id.ButtonFitBitConnect) {

            callGetDeviceListApi();


//              openFitBit();

        }

        if (view.getId() == R.id.img_clock) {

            callGetDeviceListApi();


//              openFitBit();

        }

    }

    private void openFitBit() {

        boolean isLoggedIn = sessionFitBitSessionManager.getBooleanValue(FitBitSessionManager.IS_FITBIT_LOGIN);
        if (isLoggedIn) {
            final String ConnectOrDisconnect = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_CONNECT_OR_DISSCONNECT);
//            Toast.makeText(context, ""+ConnectOrDisconnect, Toast.LENGTH_SHORT).show();

            if (ConnectOrDisconnect.equalsIgnoreCase("Add My Device")) {
                utils.showProgressbar(context);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        utils.hideProgressbar();
                        Toast.makeText(context, "fitbit successfully connected.", Toast.LENGTH_SHORT).show();
                        String Token = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN);
                        String TokenType = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE);
                        String UserId = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_USER_ID);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        System.out.println(dateFormat.format(date));
                        try {
                            callToGetAllActivitiesApi(TokenType + " " + Token, UserId, dateFormat.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ButtonFitBitConnect.setText("Remove My Device");
                        sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_CONNECT_OR_DISSCONNECT, "Remove My Device");
                    }
                }, 2000);

            } else {
                utils.showProgressbar(context);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        utils.hideProgressbar();
                        Toast.makeText(context, "fitbit successfully disconnected.", Toast.LENGTH_SHORT).show();
                        sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_CONNECT_OR_DISSCONNECT, "Add My Device");
                        ButtonFitBitConnect.setText("Add My Device");
                    }
                }, 2000);

            }
        } else {
            Intent intent = new Intent(context, FitBitActivity.class);
            intent.putExtra(getResources().getString(R.string.coming_from), "MasterActivityFragment");
            startActivityForResult(intent, 1);
        }


    }


    private void callGetDeviceListApi() {
        utils.showProgressbar(context);
        Call<ClsGadgetmainData> call = healthParametersService.getGadgetData();
        call.enqueue(new Callback<ClsGadgetmainData>() {
            @Override
            public void onResponse(Call<ClsGadgetmainData> call, Response<ClsGadgetmainData> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsGadgetmainData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {

                        if (listResponse.getData() != null) {

                            showFullScreenDailog(listResponse.getData());


                        } else {

                        }


                    } else {
                    }
                } else {


                }
            }


            @Override
            public void onFailure(Call<ClsGadgetmainData> call, Throwable t) {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
                Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void showFullScreenDailog(final ArrayList<ClsGadgetData> data) {

        try {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setContentView(R.layout.lay_show_device_list);
            ListView lst_device_list = dialog.findViewById(R.id.lst_device_list);
            ImageView img_close = dialog.findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            DeviceListAdapter deviceListAdapter = new DeviceListAdapter(context, data);
            lst_device_list.setAdapter(deviceListAdapter);

            lst_device_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (data.get(position).getId() == 2) {
                        openFitBit();
                        dialog.dismiss();


                    } else if (data.get(position).getId() == 3) {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), GfitHomeActivity.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();

//                      Toast.makeText(context, "This device is not linked with app", Toast.LENGTH_SHORT).show();


                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {
                String action = data.getStringExtra("action");
                if (action.equalsIgnoreCase("fetch")) {
                    if (Utils.isNetworkAvailable(getContext())) {
                        String Token = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN);
                        String RefreshToken = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN);
                        String TokenType = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE);
                        String UserId = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_USER_ID);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        System.out.println(dateFormat.format(date));
                        try {
                            callToGetAllActivitiesApi(TokenType + " " + Token, UserId, dateFormat.format(date));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

    }


    private void callToGetAllActivitiesApi(String beare, String id, String date) {
        if (!((Activity) context).isFinishing()) {

        }
        //response_type
        Call<GetAllFitbitActivitiesRes> call = fitbitClient.getAllActivities(beare, id, date);
        //Call<ResponseBody> call = client.getAllActivities(beare,id,date);

        Log.d("rohit", beare.toString());
        Log.d("rohitid", id.toString());

        //String finalURL  = client.getAllActivities(beare,id,date).request().url().toString();
        String finalURL = fitbitClient.getAllActivities(beare, id, date).request().url().toString();
        String test = finalURL;
        call.enqueue(new Callback<GetAllFitbitActivitiesRes>() {
            @Override
            public void onResponse(Call<GetAllFitbitActivitiesRes> call, Response<GetAllFitbitActivitiesRes> response) {
                //Check Wheater token is Expire if yes the refresh token.
                if (response.code() == 401) {
                    callingForRefreshToken();
                } else if (response.code() == 200) {
                    if (response.body() != null) {
                        List<GetAllFitbitActivitiesRes.Activity> allActivities = response.body().getActivities();
                        if (allActivities != null) {
                            if (allActivities.size() > 0) {
                                List<GetAllFitbitActivitiesRes.Activity> allActivitiestest = allActivities;
                                if (allActivitiestest.size() > 0) {
                                    List<AddFitbitDataToServerRes.Activity> listActivities = new ArrayList<>();
                                    for (int i = 0; i < allActivitiestest.size(); i++) {
                                        AddFitbitDataToServerRes request = new AddFitbitDataToServerRes();
                                        AddFitbitDataToServerRes.Activity activity = new AddFitbitDataToServerRes.Activity();
                                        activity.setActivityId(allActivitiestest.get(i).getActivityId());
                                        activity.setActivityParentId(allActivitiestest.get(i).getActivityParentId());
                                        activity.setCalories(allActivitiestest.get(i).getCalories());
                                        activity.setDistance(0);
                                        activity.setDescription(allActivitiestest.get(i).getDescription());
                                        activity.setDuration(allActivitiestest.get(i).getDuration());
                                        activity.setHasStartTime(allActivitiestest.get(i).getHasStartTime());
                                        activity.setIsFavorite(allActivitiestest.get(i).getIsFavorite());
                                        activity.setName(allActivitiestest.get(i).getName());
                                        //activity.setLogId(allActivitiestest.get(i).getLogId());
                                        activity.setSteps(allActivitiestest.get(i).getSteps());
                                        activity.setStartTime(allActivitiestest.get(i).getStartTime());
                                        listActivities.add(activity);

                                    }
                                    callToAddFitbitActivities(listActivities);
                                }

                            } else {

                                callGetmyActivityListApis();
                            }
                        }

                    } else {
                        //  Toast.makeText(context,"fitbit data not found.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllFitbitActivitiesRes> call, Throwable t) {
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(context, strError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    //Calling Token Refresh API
    private void callingForRefreshToken() {
        if (!((Activity) context).isFinishing()) {

        }

        String AuthCode = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_AUTH_CODE);//getApplicationContext().getSharedPreferences(SharedPre,0).getString("AuthCode","");
        String RefreshToken = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN);//getApplicationContext().getSharedPreferences(SharedPre,0).getString("RefreshToken","");
        Call<TokenResponse> call = fitbitClient.getRefreshToken(txtAuthorization, "refresh_token", RefreshToken);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    //  JsonObject object = new JsonObject()(response.body());
                   /* editor.putString("AccessToken",response.body().getAccessToken());
                    editor.putString("RefreshToken",response.body().getRefreshToken());
                    editor.putString("TokenType",response.body().getTokenType());
                    editor.putString("UserId",response.body().getUserId());
                    editor.commit();*/
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN, response.body().getAccessToken());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN, response.body().getRefreshToken());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE, response.body().getTokenType());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_USER_ID, response.body().getUserId());
                    //callToGetAllActivitiesApi(response.body().getTokenType()+" "+response.body().getAccessToken(),response.body().getUserId(),"2019-08-08.json");
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(context, strError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void updateActivty(int position, AcivityHistory AcivityHistory) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialogFragment = new AddActivtyDialogFragment(MasterMyActivityFragment.this);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", true);
        bundle.putSerializable("activtylist", data);
        bundle.putSerializable("myActivity", AcivityHistory);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fm, "edit_fragment");


    }

    @Override
    public void deleteActivty(final int position, final AcivityHistory acivityHistory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Activity!")
                .setMessage("Do you really want to delete this activity?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(getContext())) {
                            callDeletActivityApi(position, acivityHistory.getId());
                        } else {
                            Utils.shortToast(getContext(), getString(R.string.internet_connection_unavailable));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void callDeletActivityApi(final int pos, String id) {

        DeleteActivityRequest request = new DeleteActivityRequest();
        request.setId(id);

        Call<ActivityResponse> call = service.DeleteUserActivity(id);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        // call List API to reload list
                        myActivitiesAdapter.removeItem(pos);
                        myActivitiesAdapter.notifyDataSetChanged();
                        callGetmyActivityListApis();
                        callweeklymyActivityListApis();

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void callDeletHistoryActivityApi(final int pos, String id) {

        DeleteActivityRequest request = new DeleteActivityRequest();
        request.setId(id);

        Call<ActivityResponse> call = service.DeleteUserActivity(id);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        // call List API to reload list
                        callActivityHistoryApi(submitFromDate, submitToDate);

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


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
                Toast.makeText(context, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_weight_date_from.setText(dummydate_from);
            submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            callActivityHistoryApi(submitFromDate, submitToDate);

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
                    Toast.makeText(context, "To date should be greater than From date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    txt_weight_date_to.setText(dummydate_to);

                    submitToDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                    callActivityHistoryApi(submitFromDate, submitToDate);

                }


            } else {
                Toast.makeText(context, "Please select from date", Toast.LENGTH_SHORT).show();
            }


        }


        if (submitActivityHistoryDate.equalsIgnoreCase(year + "-" + (month + 1) + "-" + dayOfMonth)) {
            return;
        }

        if (StrDateOpen.equalsIgnoreCase("fromactHistory")) {
            dummyhistoryActshowdate = dayOfMonth + "-" + (month + 1) + "-" + year;

//            txt_activity_history_date.setText(dummyhistoryActshowdate);
            submitActivityHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;
//                submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            callGetmyActivityListApis();

        }
    }

    private void callActivitffyHistoryApi(String submitFromDate, String submitToDate) {
    }

    public String formatDatesHistoy(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFromServer;


    }

    private void callActivityHistoryApi(String submitFromDate, String submitToDate) {

        sessionManager = new SessionManager(context);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        ClsWeightRequest clsHistoryRequest = new ClsWeightRequest();
        clsHistoryRequest.setReeworkerId(userID);
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        historydatte.setText(formatDatesHistoy(dummydate_from) + " to " + formatDatesHistoy(dummydate_to));

//        utils.showProgressbar(context);
        Call<ClsNewActivityHistoryPojo> call = healthParametersService.GetActivityHistory(clsHistoryRequest);
        call.enqueue(new Callback<ClsNewActivityHistoryPojo>() {
            @Override
            public void onResponse(Call<ClsNewActivityHistoryPojo> call, Response<ClsNewActivityHistoryPojo> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsNewActivityHistoryPojo listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        if (!listResponse.getActivityData().isEmpty()) {
                            recylcler_activity_history.setVisibility(View.VISIBLE);

                            ll_avg_activity.setVisibility(View.GONE);


                            int min = (int) Math.round(Double.parseDouble(listResponse.getAvgActivityTime()));
                            txt_avg_activity.setText(formatHoursAndMinutes(min));
                            txt_no_act_histoy.setVisibility(View.GONE);
                            ArrayList<ActivityData> weightHistories = listResponse.getActivityData();
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
                            recylcler_activity_history.setLayoutManager(manager);
                            recylcler_activity_history.setAdapter(new ActivityHistoryMainAdapter(context, weightHistories, MasterMyActivityFragment.this));

                        } else {
                            ll_avg_activity.setVisibility(View.GONE);
                            txt_no_act_histoy.setVisibility(View.VISIBLE);
                            txt_no_act_histoy.setText("No data available");
                            recylcler_activity_history.setVisibility(View.GONE);


                        }


                    } else {
                        txt_no_act_histoy.setVisibility(View.VISIBLE);
                        txt_no_act_histoy.setText("No data available");
                        recylcler_activity_history.setVisibility(View.GONE);
                    }
                } else {
                }
            }


            @Override
            public void onFailure(Call<ClsNewActivityHistoryPojo> call, Throwable t) {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }


    public String formatHoursAndMinutes1(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours = "";
        int hoursstr = (totalMinutes / 60);
        if (hoursstr == 1) {
            strhours = " Hour ";
        } else {
            strhours = " Hours ";

        }
        return (totalMinutes / 60) + strhours + minutes + " Mins";
    }

    public String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours = "";
        int hoursstr = (totalMinutes / 60);
        if (hoursstr == 1) {
            strhours = " Hour ";
        } else {
            strhours = " Hours ";

        }
        boolean isFound = false;
        String test = hoursstr + strhours;
        if (test.trim().equalsIgnoreCase("0 Hours")) {
            isFound = true;
        }


        String submittime = "";
        if (isFound) {
            submittime = minutes + " Mins";

        } else {
            submittime = hoursstr + strhours + minutes + " Mins";

        }


        return submittime;


    }


    private void callToActivityTipsMasterData() {
        services = Client.getClient().create(HomeFragmentService.class);
        util = new Utils();
        util.showProgressbar(getActivity());

        Call<ClsSleepTips> call = services.getMasterFoodTipsData(4);
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
                                recycler_activity_tips.setLayoutManager(layoutManager1);
                                recycler_activity_tips.setItemAnimator(new DefaultItemAnimator());
                                recycler_activity_tips.setAdapter(adapter);

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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String lsTimeFrom = formatter.format(cal.getTime());


    }

    @Override
    public void updateHistoryActivty(int position, Activities myMedicine) {
        AcivityHistory AcivityHistory = new AcivityHistory();
        AcivityHistory.setActivityId(myMedicine.getActivityId());
        AcivityHistory.setSubActivityId(myMedicine.getSubActivityId());
        AcivityHistory.setTotalMinutes(myMedicine.getTotalMinutes());
        AcivityHistory.setTotalBurnedCalories(myMedicine.getTotalBurnedCalories());
        AcivityHistory.setDate(myMedicine.getCreateDate());
        AcivityHistory.setActivityName(myMedicine.getActivityName());
        AcivityHistory.setActivityTime(myMedicine.getActivityTime());
        AcivityHistory.setId(myMedicine.getId());
        AcivityHistory.setSubActivityName(myMedicine.getSubActivityName());

        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialogFragment = new AddActivtyDialogFragment(MasterMyActivityFragment.this);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEdit", true);
        bundle.putSerializable("activtylist", data);
        bundle.putSerializable("myActivity", AcivityHistory);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fm, "edit_fragment");


    }

    @Override
    public void deleteHistoryActivty(final int position, Activities myMedicine) {
        final AcivityHistory AcivityHistory = new AcivityHistory();
        AcivityHistory.setActivityId(myMedicine.getActivityId());
        AcivityHistory.setSubActivityId(myMedicine.getSubActivityId());
        AcivityHistory.setTotalMinutes(myMedicine.getTotalMinutes());
        AcivityHistory.setTotalBurnedCalories(myMedicine.getTotalBurnedCalories());
        AcivityHistory.setDate(myMedicine.getCreateDate());
        AcivityHistory.setActivityName(myMedicine.getActivityName());
        AcivityHistory.setActivityTime(myMedicine.getActivityTime());
        AcivityHistory.setId(myMedicine.getId());
        AcivityHistory.setSubActivityName(myMedicine.getSubActivityName());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Activity!")
                .setMessage("Do you really want to delete this activity?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(getContext())) {
                            callDeletHistoryActivityApi(position, AcivityHistory.getId());
                        } else {
                            Utils.shortToast(getContext(), getString(R.string.internet_connection_unavailable));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void ShowRetryBar() {
//        String strMessage = "Unable to load data";
//        Snackbar snackbar = Snackbar.make(getView().findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        callGetmyActivityListApis();
//                    }
//                });
//        snackbar.show();
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

                                    recylcer_spiritual_list.setAdapter(new ActivityVideoListAdapter(MasterMyActivityFragment.this, arylst_SpiritualTypeData));


                                } else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName + " not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData = new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new ActivityVideoListAdapter(MasterMyActivityFragment.this, arylst_SpiritualTypeData));

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
