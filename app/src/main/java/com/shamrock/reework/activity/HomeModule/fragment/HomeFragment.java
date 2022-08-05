package com.shamrock.reework.activity.HomeModule.fragment;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.SystemClock;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.gson.Gson;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsAddWaterRequest;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterAddSuccessData;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterUOMMain;
import com.shamrock.reework.activity.FoodModule.activity.MasterDetailsActivity;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.adapter.GlassAdapter;
import com.shamrock.reework.activity.HomeModule.adapter.RepeatSleepListAdapter;
import com.shamrock.reework.activity.HomeModule.dialog.MindDialog;
import com.shamrock.reework.activity.HomeModule.pojo.ClsProfileHeaderData;
import com.shamrock.reework.activity.HomeModule.service.ClsMessagmasterPojo;
import com.shamrock.reework.activity.HomeModule.service.GlassModel;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.HomeModule.service.MinMoodModel;
import com.shamrock.reework.activity.LoginModule.Data;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.LoginModule.UserStatus;
import com.shamrock.reework.activity.MyPlansModule.activity.NewMyPlansActivity;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ChangeReecoachActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachMasterType;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.Pathologists.SelectPathoActivity;
import com.shamrock.reework.activity.RescoreModule.activity.ReescoreActivity;
import com.shamrock.reework.activity.RescoreModule.model.ClsReescoreMianClass;
import com.shamrock.reework.activity.RescoreModule.service.RescoreService;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.WeightModule.activity.WeightActivitylatest;
import com.shamrock.reework.activity.aNewInterpretation.model.ClsReescoreMain;
import com.shamrock.reework.activity.reeworkerhealth.NewDesignHealthActivity;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.sleepmodule.ClsRepeatSleepData;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.ClsNewEditSleepRequest;
import com.shamrock.reework.activity.sleepmodule.repeatSleep.ClsRepeatSleepMain;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.AddMoodRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.GetRecoachByUserResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.MoodUpdateResponce;
import com.shamrock.reework.api.response.TipsResponce;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;
import com.tfb.fbtoast.FBCustomToast;
import com.yanzhenjie.wheel.OnWheelChangedListener;
import com.yanzhenjie.wheel.WheelView;
import com.yanzhenjie.wheel.adapters.AbstractWheelAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.activity.HomeModule.activity.HomeActivity.arylst_lock_unlockdata;
import static com.shamrock.reework.util.Utils.isValidContextForGlide;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHomeFragmentListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener, GlassAdapter.GlassInterface, DatePickerDialog.OnDateSetListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CircularProgressBar progrees_activity,progress_mind;

    List<ImageView> listGlasses;
    LoginService loginService;
    ImageView img_addFood;
    String token;
    boolean isupdateData;
    ImageView img_userimage;
    int todaysstauusId = 0;
    LinearLayout ll_profile_home,layout_setting;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
Spinner spinnerSleepType;

    private TextView txt_header_todays_Status;
    private Context mContext;
    private TextView textHome_wt_Total;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout   constrainweight_Master;
    private LinearLayout masterActivity, masterFood, masterWater, masterSleep, masterMind;
    private TextView tvWishUser, tvFoodTotal, tvWaterTotal,txtwaterttotal, tvSleepTotal, tvMindMood, tvActivityTotal, tvLargeMessage, txtCalPer;
    private int colorBlue, colorBlack;
    private SessionManager sessionManager;
    private ImageView ivAddWater, ivAddSleep, ivAddMind, ivAddActivity, ivMindMood, imgHome_AddFood, imgHome_Addweight;
    private LinearLayout cvHealth, cvReport, cvReevaluate, cvReeplan;
    //ImageView ivGlass1, ivGlass2, ivGlass3, ivGlass4, ivGlass5, ivGlass6, ivGlass7, ivGlass8;
    private ImageView ivSleepClock, ivActivityClock;
    private ProgressBar progressBarSleep, progressBarActivity, progressBarCal;

    /* GLASS ANIMATION */
    private RecyclerView rvGlassess;
    private GlassAdapter glassAdapter;
    private ArrayList<GlassModel> glassList;
    GlassModel model;
    private String intaildataMsg = "";
    private String submitHistoryDate = "";
    private String dummyhistoryshowdate = "",dummyhistoryshowdatenew = "";
    private Context context;
    private SessionManager session;
    //int sleepHour, activityHour;

    /* MIND DIALOG */
    MindDialog mindDialog;
    Button btn_submit_sleep_hours;

    HomeFragmentService service;
    private HomeFragmentResponse.Data mHomeModel = null;

    private OnHomeFragmentListener mListener;
    Utils utils;
    String lsCurrentDate;

    private int userId;
    private int recoachId;
    public static ArrayList<TipsResponce.Datum> mCommonDataListTips = new ArrayList<>();

    ArrayList<String> subscription_List;
    int scheduledWaterIntake = 0, scheduledSleepHours = 0, scheduledActivityHours = 0;
    int actualWaterIntake = 0, actualSleepHours = 0, actualActivityHours = 0;
    double actualFoodConsumed, scheduledFoodConsumed;
    DatePickerDialog datepickerdialog;
    DatePickerDialog datepickerdialog_history;
TextView txt_plan;
    SpannableStringBuilder spannableStringBuilder, spannableStringBuilder1;
    String strText, strText1, str,str1, sart;
    CircularProgressBar circular_dailydairy_cal_consumed,progress_sleep;
    private TextView txt_sleep_date_from, txt_sleep_date_to, txt_sleep_entry_date;
    private TimePickerDialog timepickerdialog;
    private String StrTimeOpen = "";
    private String StrDateOpen = "";
    private StringBuilder StrFromDateTime;
    private StringBuilder StrToDateTime;
    private StringBuilder StrToDateTime_check;
    private UnfreezeService unfreezeService;
    private RelativeLayout ll_add_history_record_date;
    private TextView txt_date_history;
    private TextView txt_show_status_date;
    private String curentDate = "";
    private String curentDateNEw = "";
    private TextView txt_total_cal_consumed;
    private TextView txt_total_cal_burned;
    private TextView txt_net_cal_burned;
    private StringBuilder BackendSubmitFromDate;
    private StringBuilder BackendSubmitToDate;
    private String submitentrydate = "";
    private CommonService commonService;
    ImageView imgHome_sun, img_main_background;
    RelativeLayout rel_home_fragment;
    private DatePickerDialog entry_datepickerdialog_history;
    private ArrayList<String> arylst_uom_water;
    private ArrayList<String> arylst_uom_water_IDs;
    private ArrayList<String> arylst_uom_water_militers;
    boolean isDream = false;
    static HomeActivity homeActivity;
    private View view;

    TextView txt_reescore;
    CircularProgressBar progress_circular_consumed1;
    ClsReescoreMianClass reescoreMianClassl1;

    SpannableStringBuilder spannableStringBuilder2;
    String strText2;

ImageView img_activity;
    RescoreService service12 ;
    private boolean isRepeatfound=false;
    private int waterUnitPos=0;
    private ArrayList<ClsRepeatSleepData> arylst_ClsRepeatSleeps;

    private Dialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2, HomeActivity homeActivitys) {
        homeActivity = homeActivitys;
        HomeFragment fragment = new HomeFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentListener) {
            mListener = (OnHomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentListener");
        }
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initServices();

        getSubscriptionData();

        getCurrentdate();

        callRescoreDataAPI();

    }

    private void getCurrentdate() {
        Date tDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        lsCurrentDate = dateFormatter.format(tDate);
    }

    private void getSubscriptionData() {
        String mSubscription = sessionManager.getStringValue(SessionManager.KEY_USER_SUBSCRIPTION_LIST);
        try {
            JSONArray obj = new JSONArray(mSubscription);
            subscription_List = new ArrayList<>();
            for (int i = 0; i < obj.length(); i++) {
                JSONObject object = obj.getJSONObject(i);
                String name = object.getString("FeatureName");
                subscription_List.add(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initServices() {
        sessionManager = new SessionManager(mContext);

        service = Client.getClient().create(HomeFragmentService.class);
        service12 = Client.getClient().create(RescoreService.class);
        utils = new Utils();
        loginService = Client.getClient().create(LoginService.class);
        token = sessionManager.getStringValue(SessionManager.KEY_USER_TOKEN);
        unfreezeService = Client.getClient().create(UnfreezeService.class);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
//        txt_header_todays_Status.setText("(" + data.getScheduledFrom() + " - " + data.getScheduledTo() + ")");



        showNewCalData();

    }
    private void showNewCalData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mHomeModel==null){
                    showNewCalData();


                    return;
                }
                Calendar startDate = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                try {
                    startDate.setTime(sdf.parse(mHomeModel.getScheduledFrom()));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdfff = new SimpleDateFormat("dd-MM-yyyy");
                String sdfffff = sdfff.format(c.getTime());
                Calendar selecteddate = Calendar.getInstance();
                if (!sessionManager.getStringValue("statusdate").isEmpty()) {
//                    String string = RegformatDates(sessionManager.getStringValue("statusdate"));
//                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//                    Date date = null;
//                    try {
//                        date = format.parse(string);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    selecteddate.setTime(date);

                    SimpleDateFormat dddddd = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    try {
                        selecteddate.setTime(dddddd.parse(sdfffff));// all done
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else {
                    SimpleDateFormat dddddd = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    try {
                        selecteddate.setTime(dddddd.parse(sdfffff));// all done
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }



                Calendar endDate = Calendar.getInstance();
                try {
                    endDate.setTime(sdf.parse(mHomeModel.getScheduledTo()));// all done
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                HorizontalCalendar horizontalCalendar;

                if (!sessionManager.getStringValue("CalenderSelectedDate").isEmpty()) {
                    String testdate=sessionManager.getStringValue("CalenderSelectedDate");
//                    String testdate="2022-03-04";
//                    String testdate="04-03-2022";
//                    Toast.makeText(mContext, ""+sessionManager.getStringValue("statusdate"), Toast.LENGTH_SHORT).show();

                    Calendar cal_test = Calendar.getInstance();
                    SimpleDateFormat sdfddd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {
                        cal_test.setTime(sdfddd.parse(testdate));// all done
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    View calView = view.findViewById(R.id.calendarView);
                    horizontalCalendar =  new HorizontalCalendar.Builder(getActivity(),calView.getId()).range(startDate,selecteddate)
                            .datesNumberOnScreen(5)
                            .configure()
                            .formatTopText("MMM")
                            .formatMiddleText("dd")
                            .formatBottomText("EEE")
                            .textSize(12, 12, 12)
                            .showTopText(true)
                            .showBottomText(true)
                            .textColor(Color.LTGRAY, Color.WHITE)
                            .end()
//                        .defaultSelectedDate(selecteddate)
                            .defaultSelectedDate(cal_test)

                            .build();

                }else {
                    View calView = view.findViewById(R.id.calendarView);
                    horizontalCalendar =  new HorizontalCalendar.Builder(getActivity(),calView.getId()).range(startDate,selecteddate)
                            .datesNumberOnScreen(5)
                            .configure()
                            .formatTopText("MMM")
                            .formatMiddleText("dd")
                            .formatBottomText("EEE")
                            .textSize(12, 12, 12)
                            .showTopText(true)
                            .showBottomText(true)
                            .textColor(Color.LTGRAY, Color.WHITE)
                            .end()
//                            .configure().showTopText(false).textSize(13,13,13).end()
                            .defaultSelectedDate(selecteddate)

                            .build();

                }



                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                    @Override
                    public void onDateSelected(Calendar date, int position) {

                        SimpleDateFormat formatter = new SimpleDateFormat("dd,MM,yyyy");
                        String dateString = formatter.format(new Date(date.getTimeInMillis()));
//                       Toast.makeText(mContext, ""+dateString, Toast.LENGTH_SHORT).show();
                        String arr[]=dateString.split(",");


                        try {
                            setnewCalederData(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        },500);

    }


//    private void showNewCalData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mHomeModel==null){
//                    showNewCalData();
//
//
//                    return;
//                }
//                Calendar startDate = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//                try {
//                    startDate.setTime(sdf.parse(mHomeModel.getScheduledFrom()));// all done
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat sdfff = new SimpleDateFormat("dd-MM-yyyy");
//                String sdfffff = sdfff.format(c.getTime());
//                Calendar selecteddate = Calendar.getInstance();
//                if (!sessionManager.getStringValue("statusdate").isEmpty()) {
//
//                    SimpleDateFormat dddddd = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//                    try {
//                        selecteddate.setTime(dddddd.parse(sdfffff));// all done
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }else {
//                    SimpleDateFormat dddddd = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//                    try {
//                        selecteddate.setTime(dddddd.parse(sdfffff));// all done
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//
//                Calendar endDate = Calendar.getInstance();
//                try {
//                    endDate.setTime(sdf.parse(mHomeModel.getScheduledTo()));// all done
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//
//                HorizontalCalendar horizontalCalendar = null;
//                View calView = view.findViewById(R.id.calendarView);
//
//                if (!sessionManager.getStringValue("CalenderSelectedDate").isEmpty()) {
//                    String testdate=sessionManager.getStringValue("CalenderSelectedDate");
//
//
//
//                    try {
//                        Calendar cal_test = Calendar.getInstance();
//                        SimpleDateFormat sdfddd = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//                        cal_test.setTime(sdfddd.parse(testdate));
//                        horizontalCalendar =  new HorizontalCalendar.Builder(getActivity(),calView.getId()).range(startDate,selecteddate)
//                                .datesNumberOnScreen(3)
//                                .configure().showTopText(false).textSize(13,13,13).end()
//                                .defaultSelectedDate(cal_test)
//                                .build();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                }else {
//                     horizontalCalendar =  new HorizontalCalendar.Builder(getActivity(),calView.getId()).
//                             range(startDate,selecteddate)
//                            .datesNumberOnScreen(3)
//                            .configure().showTopText(false).textSize(13,13,13).end()
//                        .defaultSelectedDate(selecteddate)
//                            .build();
//
//
//
//
//                    horizontalCalendar.selectDate(selecteddate,true);
//
//                }
//
//
//
//                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
//                    @Override
//                    public void onDateSelected(Calendar date, int position) {
//
//
//
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd,MM,yyyy");
//                        String dateString = formatter.format(new Date(date.getTimeInMillis()));
////                       Toast.makeText(mContext, ""+dateString, Toast.LENGTH_SHORT).show();
//                        String arr[]=dateString.split(",");
//
//
//                        try {
//                            setnewCalederData(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//            }
//        },1000);
//
//    }

    private void setnewCalederData(int dayOfMonth, int month, int year) throws ParseException {
        StrDateOpen="getHistory";
        String exta="";
        String exta2="";

        if (StrDateOpen.equalsIgnoreCase("getHistory")) {

            if (false) {
                sessionManager.setStringValue("Cal_consumed_date", year + "-0" + month + "-" + dayOfMonth);

            } else {
                if (month<10){
                    exta="0";
                }
                if (dayOfMonth<10){
                    exta2="0";
                }
                sessionManager.setStringValue("Cal_consumed_date", year + "-"+exta + month + "-"+exta2 + dayOfMonth);

            }

            if (submitHistoryDate.equalsIgnoreCase(year + "-" + month  + "-" + dayOfMonth)) {
                return;
            }


            if (false) {

                dummyhistoryshowdate = dayOfMonth + "-" + "0" + month  + "-" + year;

            } else {
                dummyhistoryshowdate = dayOfMonth + "-" + month  + "-" + year;

            }
            dummyhistoryshowdatenew = dayOfMonth + "-" + month  + "-" + year;
            submitHistoryDate = year + "-" + month  + "-" + dayOfMonth;
            submitHistoryDate = year + "-" + month  + "-" + dayOfMonth;






            sessionManager.setStringValue("statusdate", submitHistoryDate);
            sessionManager.setStringValue("Entrystatusdate", submitHistoryDate);
            sessionManager.setStringValue("CalenderSelectedDate", dummyhistoryshowdate);

            if (!curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat simpleDateFormat12 = new SimpleDateFormat("dd-MM-yyyy");
                Date varDate = simpleDateFormat.parse(dummyhistoryshowdate);
                simpleDateFormat = new SimpleDateFormat("dd-MMM");
                simpleDateFormat12 = new SimpleDateFormat("dd-MMM");

                txt_show_status_date.setText(simpleDateFormat.format(varDate) + "'s Status");
                sessionManager.setStringValue("showDate", simpleDateFormat12.format(varDate) + "'s Status");


                txt_show_status_date.setText(sessionManager.getStringValue("showDate"));


            String abc =    new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(varDate);


                strText1 = formatDates1(simpleDateFormat.format(varDate));
                spannableStringBuilder1 = new SpannableStringBuilder(strText1 + " plan :");
                SuperscriptSpan superscriptSpan1 = new SuperscriptSpan();

//                spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("th"),
//                        strText1.indexOf("th") + ("th").length(),
//                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                showSmallSizeText1("th");

                if (str.equals("th")) {
                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("th"),
                            strText1.indexOf("th") + ("th").length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    showSmallSizeText1("th");
                } else if (str.equals("nd")) {
                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("nd"),
                            strText1.indexOf("nd") + ("nd").length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    showSmallSizeText1("nd");
                } else if (str.equals("rd")) {
                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("rd"),
                            strText1.indexOf("rd") + ("rd").length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    showSmallSizeText1("rd");
                } else if (str.equals("st")) {
                    spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("st"),
                            strText1.indexOf("st") + ("st").length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    showSmallSizeText1("st");
                }

                if (curentDate.equals(abc)) {

                    txt_plan.setText("Today's plan :");
                }else {
                    txt_plan.setText(spannableStringBuilder1);
                }







            } else {
                txt_show_status_date.setText("Today's Status" + " (" + curentDateNEw + ")");
                sessionManager.setStringValue("showDate", "Today's status");
                txt_show_status_date.setText(sessionManager.getStringValue("showDate"));


            }

            if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
                txt_show_status_date.setText("Today's status" + " (" + curentDateNEw + ")");
                sessionManager.setStringValue("showDate", "Today's status" + " (" + curentDateNEw + ")");
                txt_show_status_date.setText(sessionManager.getStringValue("showDate"));

//                     txt_date_history.setText(dummyhistoryshowdate);


            } else {
                txt_date_history.setText(dummyhistoryshowdate);

            }


            CallToFetchRecoachId(false);

        }

    }

    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    public String formatDates1(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MMM");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd,MMM");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return getFormattedDate1(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getFormattedDate1(Date dates) {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_home, container, false);


        initView(view);
        datesData();
        if (Utils.isNetworkAvailable(mContext)) {
            setHomePageWellcomeMessage();
            CallToFetchRecoachId(false);
            CallWaterUOMApi();

            callPathoReecoachStatus();
            showDatesData();
            addClickListeners();
            setHomepagePictureMessageApi();
            onRefreshData();
            getMessageMasterSlogan();

            session = new SessionManager(mContext);
            session.setStringValue("Allpart","daily");
            if (!sessionManager.getStringValue("Createdby").equalsIgnoreCase("1")) {
                showpathoAndReecoachSelectDailog();

            }
            Callprofileheader();


        } else {
            Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();

        }

//        callLockUnlockApi();

     LinearLayout ll_first=view.findViewById(R.id.ll_first);








        return view;
    }


    private void showpathoAndReecoachSelectDailog() {

        if ((sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true") && sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID) == 0) ) {
            selectReecoachPathDialog();
        }

    }

    private void onRefreshData() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkAvailable(mContext)) {
                    if (Utils.isNetworkAvailable(mContext)) {
                        CallToFetchRecoachId(false);
                    } else {
                        Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
                    }
                    CallWaterUOMApi();
                    Callprofileheader();
                    getMessageMasterSlogan();
                    callPathoReecoachStatus();
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
                    Log.d("swipeRefreshLayout", "called");
                }
            }
        });
    }

    private void setHomePageWellcomeMessage() {
        String userName = sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME);

        //for wishes as per time
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String strMessage = "";
        if (timeOfDay >= 0 && timeOfDay < 12) {
            strMessage = getString(R.string.home_good_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            strMessage = getString(R.string.home_good_afternoon);
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            strMessage = getString(R.string.home_good_evening);
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            strMessage = getString(R.string.home_good_evening);
        }
        String wishMessage = strMessage + " " + userName + "!";

        SpannableStringBuilder str = new SpannableStringBuilder(wishMessage);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), strMessage.length(),
                wishMessage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvWishUser.setText(str);

        String largeMessage = getString(R.string.home_large_message);
        SpannableStringBuilder strLarge = new SpannableStringBuilder(largeMessage);
        strLarge.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                12, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        strLarge.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                58, 63, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLargeMessage.setText(strLarge);

        tvLargeMessage.setText(sessionManager.getStringValue("KEY_HOME_MESSAGE"));

    }


    private void showDatesData() {
        txt_date_history.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        if (!sessionManager.getStringValue("showDate").isEmpty()) {
            txt_show_status_date.setText(sessionManager.getStringValue("showDate"));
        } else {
            if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
                sessionManager.setStringValue("showDate", "Today's status" + " (" + curentDateNEw + ")");
                txt_show_status_date.setText(sessionManager.getStringValue("showDate"));
            }
        }
    }
    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    public String getFormattedDate(Date dates) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dates);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("d");






        String date = format.format(dates);

        if(date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("d'st' MMM");
        else if(date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("d'nd' MMM");
        else if(date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("d'rd' MMM");
        else
            format = new SimpleDateFormat("d'th' MMM");

        return format.format(dates);


    }
    private void showSmallSizeText2(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.6f);
        spannableStringBuilder2.setSpan(relativeSizeSpan, strText2.indexOf(s), strText2.indexOf(s) + (s).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    private void datesData() {
        curentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        showEntryDateAddDailog(curentDate);
//        String abc =formatDates(new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(new Date()));
//        strText2 =abc;
//        spannableStringBuilder2 = new SpannableStringBuilder(strText2);
//        SuperscriptSpan superscriptSpan2 = new SuperscriptSpan();
//
//        spannableStringBuilder2.setSpan(superscriptSpan2, strText2.indexOf("th"),
//                strText2.indexOf("th") + ("th").length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        showSmallSizeText2("th");

//        String abc=formatDates(new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(new Date()));

        curentDateNEw = formatDates(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

//        curentDateNEw =formatDates(new SimpleDateFormat("dd-MMM", Locale.getDefault()).format(new Date()));


        submitHistoryDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        sessionManager.setStringValue("Cal_consumed_date", submitHistoryDate);

        if (!sessionManager.getStringValue("statusdate").isEmpty()) {
//            sessionManager.setStringValue("statusdate", sessionManager.getStringValue("statusdate"));
            if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
                sessionManager.setStringValue("statusdate", sessionManager.getStringValue("Entrystatusdate"));

            }else {
                sessionManager.setStringValue("statusdate", sessionManager.getStringValue("statusdate"));

            }




        } else {

            sessionManager.setStringValue("showDate",  curentDateNEw );

            sessionManager.setStringValue("statusdate", submitHistoryDate);

        }
        submitentrydate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }


    private void setHomepagePictureMessageApi() {
        if (sessionManager.getStringValue("KEY_HOME_IMAGE").contains(".gif")) {
            Glide.with(mContext).asGif().load(sessionManager.getStringValue("KEY_HOME_IMAGE"))
                    .listener(new RequestListener<GifDrawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                            rel_home_fragment.setBackgroundColor(Color.parseColor("#000000"));
                            return false;
                        }
                    })
                    .into(img_main_background);
        } else {
            Glide.with(mContext)
                    .load(sessionManager.getStringValue("KEY_HOME_IMAGE"))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                            rel_home_fragment.setBackgroundColor(Color.parseColor("#000000"));
                            return false;
                        }
                    })
                    .into(img_main_background);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean callLockFunction(String menuname) {
        boolean isLock = false;
        if (arylst_lock_unlockdata != null) {
            for (int i = 0; i < arylst_lock_unlockdata.size(); i++) {

                if (arylst_lock_unlockdata.get(i).getStaticName().equalsIgnoreCase(menuname)) {
                    String isLocked = arylst_lock_unlockdata.get(i).getIsLocked();
                    if (isLocked.equalsIgnoreCase("true")) {
                        isLock = true;
                    } else {
                        isLock = false;

                    }

                    break;

                }
            }

        }
        return isLock;
    }

    private void callProcedureText(String wellnesslibrary) {
        if (arylst_lock_unlockdata != null) {
            Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
            dialog.setContentView(R.layout.lay_procudure_text);
            TextView txt_procedure_text = dialog.findViewById(R.id.txt_procedure_text);
            TextView txt_pro_header = dialog.findViewById(R.id.txt_pro_header);

            for (int i = 0; i < arylst_lock_unlockdata.size(); i++) {
                if (wellnesslibrary.equalsIgnoreCase(arylst_lock_unlockdata.get(i).getStaticName())) {
                    if (arylst_lock_unlockdata.get(i).getProcedureText() != null) {
                        txt_procedure_text.setText(arylst_lock_unlockdata.get(i).getProcedureText());
                        txt_pro_header.setText(arylst_lock_unlockdata.get(i).getServiceName());
                        dialog.show();

                    }
                    break;
                }


            }
        }


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {


            case R.id.txt_sleep_date_from:


                StrDateOpen = "from";


                timepickerdialog = new TimePickerDialog(getContext(), HomeFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);

                entry_datepickerdialog_history.show();


                break;


            case R.id.txt_sleep_date_to:
                StrDateOpen = "to";
                timepickerdialog = new TimePickerDialog(getContext(), HomeFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);

                entry_datepickerdialog_history.show();


                break;





            case R.id.constrainFood_Master:


                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }


                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                    intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 0);
                    intent.putExtra("MEAL_CAL_MAX", 0);
                    startActivity(intent);
                    break;

                } else {


                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {
                        intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        intent.putExtra("MEAL_CAL_MAX", (int) mHomeModel.getScheduledFoodCalConsumed());
                        startActivityForResult(intent, 501);
                    } else {
                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();

                        }
                    }
                }
                break;


            case R.id.constrainWater_Master:


                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }

                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                    sessionManager.setStringValue("IsShowMSg", "false");
                    intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 1);
                    intent.putExtra("FromWater", true);
                    startActivity(intent);
                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {

                        sessionManager.setStringValue("IsShowMSg", "false");
                        intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 1);
                        intent.putExtra("FromWater", true);
                        startActivity(intent);
                    } else {
                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                }


                break;

            case R.id.constrainSleep_Master:
                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }


                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                    intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 2);
                    startActivity(intent);
                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//
                    else if (mHomeModel != null) {
                        intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 2);
                        startActivity(intent);
                    } else {
                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                }


                break;

            case R.id.constrainMind_Master:


                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }
                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                    intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 4);
                    if (mHomeModel != null) {
                        intent.putExtra("ACTUAL_MIND_STATUS", mHomeModel.getActualMindStatus());
                        intent.putExtra("BING_QUANTITY", String.valueOf(mHomeModel.getIsBingeOnLargeQuantity()));
                    } else {
                        intent.putExtra("ACTUAL_MIND_STATUS", "");
                        intent.putExtra("BING_QUANTITY", "");
                    }
                    startActivity(intent);


                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {
                        intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 4);
                        if (mHomeModel != null) {
                            intent.putExtra("ACTUAL_MIND_STATUS", mHomeModel.getActualMindStatus());
                            intent.putExtra("BING_QUANTITY", String.valueOf(mHomeModel.getIsBingeOnLargeQuantity()));
                        } else {
                            intent.putExtra("ACTUAL_MIND_STATUS", "");
                            intent.putExtra("BING_QUANTITY", "");
                        }
                        startActivity(intent);
                    } else {

                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                }


                break;

            case R.id.constrainActivity_Master:

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }
                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                    intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 3);

                    startActivity(intent);


                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {
                        intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 3);

                        startActivity(intent);

                    } else {

                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }

                    }
                }

                break;


            case R.id.ll_add_history_record_date:
                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;
                }

                StrDateOpen = "getHistory";


                datepickerdialog_history.show();

                break;


            case R.id.imgHome_Addweight:

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

//                startActivity(new Intent(mContext,WeightActivity.class));

                intent = new Intent(mContext, MasterDetailsActivity.class);
                intent.putExtra("FRAGMENT_POSITION", 5);
                startActivity(intent);

                break;


            case R.id.imgHome_AddFood:
                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (Utils.isNetworkAvailable(mContext)) {
                    if (mHomeModel != null) {
                        try {

                            double actualCalConsumed = mHomeModel.getActualFoodCalConsumed();
                            double sheduleCalConsumed = mHomeModel.getScheduledFoodCalConsumed();

                            if (actualCalConsumed == sheduleCalConsumed) {
                                Toast.makeText(mContext, "You completed this task !", Toast.LENGTH_LONG).show();
                                break;
                            } else if (actualCalConsumed < sheduleCalConsumed) {

                                double value = (sheduleCalConsumed * 10) / 100;

                                actualCalConsumed += value;

                                if (actualCalConsumed > sheduleCalConsumed) {
                                    actualCalConsumed = actualCalConsumed - sheduleCalConsumed;
                                }
                            } else {
                                actualCalConsumed = sheduleCalConsumed;
                            }
                            mHomeModel.setActualFoodCalConsumed(actualCalConsumed);
                            CallForUpdatingData(mHomeModel, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }
                    }


                } else
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
                break;


            case R.id.imgHome_AddWater:

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                    return;

                }


                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (Utils.isNetworkAvailable(mContext)) {
                    if (sessionManager.getStringValue("Trial").equalsIgnoreCase("ignore" +
                            "")) {

                        try {
                            int actualWaterIntake = 0;
                            int sheduleIntake = 0;

                            if (actualWaterIntake < sheduleIntake)
                                actualWaterIntake += 1;
                            else
                                actualWaterIntake += 1;

                            HomeFragmentRequest request = new HomeFragmentRequest();
                            request.setActualWaterIntake(actualWaterIntake);
                            String jsonRquest = new Gson().toJson(request);
                            String test = jsonRquest;
                            Call<HomeFragmentResponse> call = service.UpdateHomeData(request);
                            call.enqueue(new Callback<HomeFragmentResponse>() {
                                @Override
                                public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response) {
                                    utils.hideProgressbar();

                                    if (response.code() == Client.RESPONSE_CODE_OK) {
                                        HomeFragmentResponse listResponse = response.body();

                                        if (listResponse != null && listResponse.getCode() == 1) {
                                            Toast.makeText(mContext, "Data added successfully", Toast.LENGTH_SHORT).show();

                                            mHomeModel = listResponse.getData();
                                            setHomeData(mHomeModel);
                                        }  //Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                                        utils.hideProgressbar();
                                    }
                                }

                                @Override
                                public void onFailure(Call<HomeFragmentResponse> call, Throwable t) {
                                    // Log error here since request failed
                                    Log.e("ACTV::", t.toString());
                                    utils.hideProgressbar();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                            try {
                                int actualWaterIntake = mHomeModel.getActualWaterIntake();
                                int sheduleIntake = mHomeModel.getScheduledWaterIntake();


                                showDynamicAddWaterDailog(actualWaterIntake);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                intent = new Intent(mContext, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                            }
//                            else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                                Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                            }
                            else if (mHomeModel != null) {
                                try {
                                    int actualWaterIntake = mHomeModel.getActualWaterIntake();
                                    int sheduleIntake = mHomeModel.getScheduledWaterIntake();

//                                    if (actualWaterIntake < sheduleIntake)
//                                        actualWaterIntake += 1;
//                                    else
//                                        actualWaterIntake += 1;
                                    showDynamicAddWaterDailog(actualWaterIntake);

//                                    mHomeModel.setActualWaterIntake(actualWaterIntake);
//                                    CallForUpdatingData(mHomeModel, false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (!intaildataMsg.isEmpty()) {
                                    Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                                }

                            }

                        }


                    }


                } else
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
                break;

            case R.id.imgHome_AddSleep:

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();

                    return;

                }

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                    timepickerdialog = new TimePickerDialog(getContext(), HomeFragment.this,
                            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                            Calendar.getInstance().get(Calendar.MINUTE),
                            false);


                    timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                            Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                            buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


                        }
                    });


                    showAddSleepTimeDialog();

                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {

                        timepickerdialog = new TimePickerDialog(getContext(), HomeFragment.this,
                                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                                Calendar.getInstance().get(Calendar.MINUTE),
                                false);


                        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


                            }
                        });

                        showAddSleepTimeDialog();

                    } else {


                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }
                    }
                }


                break;

            case R.id.imgHome_AddMind:

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();

                    return;

                }

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//                    FragmentManager fm = getFragmentManager();
//                    mindDialog = new MindDialog();
//                    mindDialog.show(fm, "mind_fragment");
//

                    dialog=new Dialog(mContext,R.style.CustomDialog);
                    dialog.setContentView(R.layout.dialog_feelings);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    LinearLayout lay_happy=dialog.findViewById(R.id.lay_happy);
                    LinearLayout lay_stressed=dialog.findViewById(R.id.lay_stressed);
                    LinearLayout lay_neutral=dialog.findViewById(R.id.lay_neutral);

                    TextView txt_happy=dialog.findViewById(R.id.txt_happy);
                    TextView txt_stressed=dialog.findViewById(R.id.txt_stressed);
                    TextView txt_neutral=dialog.findViewById(R.id.txt_neutral);

                    TextView txt_close=dialog.findViewById(R.id.txt_close);

                    txt_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    if(mHomeModel!=null){

                        if(mHomeModel.getActualMindStatus().equals("happy")){
                            txt_happy.setTextColor(getResources().getColor(R.color.black));
                            txt_stressed.setTextColor(getResources().getColor(R.color.feelingunselect));
                            txt_neutral.setTextColor(getResources().getColor(R.color.feelingunselect));

                        }else if(mHomeModel.getActualMindStatus().equals("Stressed")){
                            txt_happy.setTextColor(getResources().getColor(R.color.feelingunselect));
                            txt_stressed.setTextColor(getResources().getColor(R.color.black));
                            txt_neutral.setTextColor(getResources().getColor(R.color.feelingunselect));

                        }else if(mHomeModel.getActualMindStatus().equals("neutral")){
                            txt_happy.setTextColor(getResources().getColor(R.color.feelingunselect));
                            txt_stressed.setTextColor(getResources().getColor(R.color.feelingunselect));
                            txt_neutral.setTextColor(getResources().getColor(R.color.black));
                        }


                    }



//final String abc1;
                    lay_happy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//                            abc1="happy";

                            setMindDataFromActivityNew("happy");
                            dialog.dismiss();

                        }
                    });


                    lay_stressed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//                            abc="Stressed";

                            setMindDataFromActivityNew("Stressed");
                            dialog.dismiss();
                        }
                    });


                    lay_neutral.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

//                            abc="neutral";
                            setMindDataFromActivityNew("neutral");
                            dialog.dismiss();
                        }
                    });








                    dialog.show();











                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {
//                        FragmentManager fm = getFragmentManager();
//                        mindDialog = new MindDialog();
//                        mindDialog.show(fm, "mind_fragment");

                        dialog=new Dialog(mContext,R.style.CustomDialog);
                        dialog.setContentView(R.layout.dialog_feelings);
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        LinearLayout lay_happy=dialog.findViewById(R.id.lay_happy);
                        LinearLayout lay_stressed=dialog.findViewById(R.id.lay_stressed);
                        LinearLayout lay_neutral=dialog.findViewById(R.id.lay_neutral);

                        TextView txt_happy=dialog.findViewById(R.id.txt_happy);
                        TextView txt_stressed=dialog.findViewById(R.id.txt_stressed);
                        TextView txt_neutral=dialog.findViewById(R.id.txt_neutral);
                        TextView txt_close=dialog.findViewById(R.id.txt_close);

                        txt_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });


                        if(mHomeModel!=null) {

                            if(mHomeModel.getActualMindStatus()!=null){
                                if (mHomeModel.getActualMindStatus().equals("happy")) {
                                    txt_happy.setTextColor(getResources().getColor(R.color.black));
                                    txt_stressed.setTextColor(getResources().getColor(R.color.feelingunselect));
                                    txt_neutral.setTextColor(getResources().getColor(R.color.feelingunselect));

                                } else if (mHomeModel.getActualMindStatus().equals("Stressed")) {
                                    txt_happy.setTextColor(getResources().getColor(R.color.feelingunselect));
                                    txt_stressed.setTextColor(getResources().getColor(R.color.black));
                                    txt_neutral.setTextColor(getResources().getColor(R.color.feelingunselect));

                                } else if (mHomeModel.getActualMindStatus().equals("neutral")) {
                                    txt_happy.setTextColor(getResources().getColor(R.color.feelingunselect));
                                    txt_stressed.setTextColor(getResources().getColor(R.color.feelingunselect));
                                    txt_neutral.setTextColor(getResources().getColor(R.color.black));
                                }

                            }

                        }



//final String abc1;
                        lay_happy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                            abc1="happy";

                                setMindDataFromActivityNew("happy");
                                dialog.dismiss();

                            }
                        });


                        lay_stressed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                            abc="Stressed";

                                setMindDataFromActivityNew("Stressed");
                                dialog.dismiss();
                            }
                        });


                        lay_neutral.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                            abc="neutral";
                                setMindDataFromActivityNew("neutral");
                                dialog.dismiss();
                            }
                        });




                        dialog.show();












                    } else {

                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }


                    }
                }


                break;

            case R.id.imgHome_AddActivity:
                Toast.makeText(mContext, "Coming soon", Toast.LENGTH_SHORT).show();

                break;

            case R.id.cvHealth:

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                    intent = new Intent(mContext, WeightActivitylatest.class);
                    intent.putExtra("FRAGMENT_POSITION", 5);
                    startActivity(intent);

                } else {
                    if (containsName(subscription_List, "My Health")) {

                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(mContext, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                            Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                        } else if (mHomeModel != null) {
                            intent = new Intent(mContext, WeightActivitylatest.class);
                            intent.putExtra("FRAGMENT_POSITION", 5);
                            startActivity(intent);

                        } else {


                            if (!intaildataMsg.isEmpty()) {
                                Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                            }
                        }


                    } else {
                        Toast.makeText(mContext, "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                    }
                }

                break;


            case R.id.constrainweight_Master:
                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

//

                    intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 5);
                    startActivity(intent);
                } else {
                    if (containsName(subscription_List, "My Health")) {

                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(mContext, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                            Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                        }
//            else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//            }
                        else if (mHomeModel != null) {


                            intent = new Intent(mContext, MasterDetailsActivity.class);
                            intent.putExtra("FRAGMENT_POSITION", 4);
                            startActivity(intent);
                        } else {

                            if (!intaildataMsg.isEmpty()) {
                                Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                            }


                        }


                    } else {
                        Toast.makeText(mContext, "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                    }
                }

                break;


            case R.id.cvReport:

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//                    startActivity(new Intent(mContext, AllReportActivity.class));

                    Intent intent1 = new Intent(mContext, NewMyPlansActivity.class);
                    intent1.putExtra("param", "");
                    startActivity(intent1);
                } else {
                    if (containsName(subscription_List, "Blood Report")) {
                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(mContext, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                            Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                        }
//
                        else {

//                            startActivity(new Intent(mContext, AllReportActivity.class));

                            Intent intent1 = new Intent(mContext, NewMyPlansActivity.class);
                            intent1.putExtra("param", "");
                            startActivity(intent1);

                        }


                    } else {
                        Toast.makeText(mContext, "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            case R.id.cvReevaluate:
                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();

                    return;

                }

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//                    startActivity(new Intent(mContext, ReevaluateActivity.class));
                    showSubscriptionSuccessDailog();

//                    Intent intent1=new Intent(mContext,ReeevaluateHealthparamActivity.class);
//                    intent1.putExtra("ISFromReevaluate",true);
//                    startActivity(intent1);


                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {

//                        showSubscriptionSuccessDailog();

                        Intent intent1 = new Intent(mContext, NewDesignHealthActivity.class);
                        intent1.putExtra("ISFromReevaluate", true);
                        startActivity(intent1);
//                        dialog.dismiss();

                    }
                }


                break;

            case R.id.cvReeplan:


                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//                    startActivity(new Intent(mContext, MyPlansActivity.class));

                } else {
                    if (containsName(subscription_List, "My Plans")) {


                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(mContext, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                            Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                        }
//                        else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                            Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                        }
                        else if (mHomeModel != null) {
//                            Intent intent1=new Intent(mContext, DietPlanActivity.class);
//                            Intent intent1 = new Intent(mContext, RescoreIntroActivity.class);
//                            intent1.putExtra("ISReeplan", true);
//                            startActivity(intent1);
                            Intent intent1 = new Intent(mContext, ReescoreActivity.class);
                            intent1.putExtra("REESCORE_DATA",reescoreMianClassl1);
                            startActivity(intent1);


                        } else {
                            Toast.makeText(mContext, "Recoach is not assigned any plan.", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(mContext, "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            default:
        }
    }




    private void callRescoreDataAPI()
    {

        RescoreService service = Client.getClient().create(RescoreService.class);
        sessionManager = new SessionManager(mContext);
        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        Call<ClsReescoreMianClass> call = service.GetReeScoreMessage();
        call.enqueue(new Callback<ClsReescoreMianClass>()
        {
            @Override
            public void onResponse(Call<ClsReescoreMianClass> call, Response<ClsReescoreMianClass> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsReescoreMianClass listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        if (listResponse.getData()!=null){

                            reescoreMianClassl1=listResponse;
                            for (int i = 0; i < listResponse.getData().size(); i++) {

                                if (listResponse.getData().get(i).getMessageType().equalsIgnoreCase("What is Reescore")){
//                                    txt_reescore.setText(listResponse.getData().get(i).getMessage());

                                    break;

                                }
                            }
                            callInterpretationApi();
                        }




                    }
                    else
                        Toast.makeText(mContext, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ClsReescoreMianClass> call, Throwable t)
            {
                Log.e("ReescoreActivity", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void callInterpretationApi() {

        if (!((Activity) mContext).isFinishing()) {
            utils.showProgressbar(mContext);
        }
      String  submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        Call<ClsReescoreMain> call = service12.GetInterpretationByReeWorkerIDAPI(userId,submitHistoryDate);
        call.enqueue(new Callback<ClsReescoreMain>() {
            @Override
            public void onResponse(Call<ClsReescoreMain> call, Response<ClsReescoreMain> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsReescoreMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

//                        Intent intent = new Intent(context, InterpretationActivity.class);
//                        intent.putExtra("RESCORE_LIST", mDataList);
//                        intent.putExtra("isFirstTime", isFirstTime);
//                        intent.putExtra("NEwInterpretation", listResponse.getData());
//                        startActivity(intent);
                        txt_reescore.setText(Math.round(Float.parseFloat(listResponse.getData().getReeScore()))+"");

                        progress_circular_consumed1.setProgress((float) Float.parseFloat(listResponse.getData().getReeScore()));
                        progress_circular_consumed1.setProgressMax(100);

                    } else
                        txt_reescore.setText("0");

                    Toast.makeText(mContext, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                }
//                    ShowRetryBar("");
            }

            @Override
            public void onFailure(Call<ClsReescoreMain> call, Throwable t) {
                Log.e("ReescoreActivity", t.toString());
//                Toast.makeText(mContext, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                txt_reescore.setText("0");

                utils.hideProgressbar();
//                ShowRetryBar("");
            }
        });
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, arylst_uom_water);
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
        waterheader.setText(txtwaterttotal.getText().toString());
        wheal_list_uom.setAdapter(new TestAdapter(arylst_uom_water));
        Drawable centerFilter = mContext.getResources().getDrawable(R.drawable.quantity_selector);
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
                        Toast.makeText(mContext, "Please enter the quantity", Toast.LENGTH_SHORT).show();
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
                    clsAddWaterRequest.setTodayStatusId(todaysstauusId);
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


        double scheduleintake = Double.valueOf(scheduledWaterIntake) / 100;

        String showschedule = decimalFormat.format(scheduleintake);


        double actualintake = Double.valueOf(actualWaterIntake) / 100;
        String showActual = decimalFormat.format(actualintake);


        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1 = new SpannableString(showActual);
        str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
        str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(str1+" Litres");
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

    private void showAddSleepTimeDialog() {


        showEntryDateAddDailog(curentDate);

      try {
            final Dialog dialog = new Dialog(mContext,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before


        showDatePickerDailog();
        dialog.setContentView(R.layout.dialog_add_sleep);
        TextView txt_repeat_sleep_data = dialog.findViewById(R.id.txt_repeat_sleep_data);
        final TextView txt_add_repeat_sleep = dialog.findViewById(R.id.txt_add_repeat_sleep);
        btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        callRepeatSleepDataAPI(submitentrydate, dialog, txt_add_repeat_sleep,btn_submit_sleep_hours);


        txt_repeat_sleep_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callRepeatSleepDataAPI(submitentrydate, dialog,txt_add_repeat_sleep, btn_submit_sleep_hours);


            }
        });

        txt_sleep_date_from = dialog.findViewById(R.id.txt_sleep_date_from);
        txt_sleep_date_to = dialog.findViewById(R.id.txt_sleep_date_to);
        txt_sleep_entry_date = dialog.findViewById(R.id.txt_sleep_entry_date);
        final EditText edt_sleep_count = dialog.findViewById(R.id.edt_sleep_count);



          spinnerSleepType = dialog.findViewById(R.id.spinnerSleepType);
          ArrayAdapter<String> adapters_activty;

          final String[] frequencyArray = getResources().getStringArray(R.array.sleep_type);

          adapters_activty = new ArrayAdapter<>(mContext,R.layout.custom_simple_spinner_item,R.id.txt_when, frequencyArray);
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







          radioGroup = (RadioGroup) dialog.findViewById(R.id.rd_group_sleep);

        final RadioButton rd_btn_dreamless_sleep = dialog.findViewById(R.id.rd_btn_dreamless_sleep);
        final RadioButton rd_btn_dreamwith_sleep = dialog.findViewById(R.id.rd_btn_dreamwith_sleep);


        rd_btn_dreamless_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamless_sleep.setChecked(true);
                rd_btn_dreamwith_sleep.setChecked(false);
                isDream = false;

            }
        });
        rd_btn_dreamwith_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_btn_dreamwith_sleep.setChecked(true);
                rd_btn_dreamless_sleep.setChecked(false);
                isDream = true;


            }
        });


        txt_sleep_entry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "entry";
                timepickerdialog = new TimePickerDialog(getContext(), HomeFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);

                datepickerdialog.show();
            }
        });
        txt_sleep_entry_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));



        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {







                if (Utils.isNetworkAvailable(mContext)) {

                    if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                        try {
//


                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                            try {


                                if (txt_sleep_date_from.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(mContext, "Please select start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_to.getText().toString().trim().isEmpty()) {
                                    Toast.makeText(mContext, "Please select end time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (txt_sleep_date_from.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                    Toast.makeText(mContext, "Please select correct time", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Date date1 = simpleDateFormat.parse(txt_sleep_date_from.getText().toString().trim());
                                Date date2 = simpleDateFormat.parse(txt_sleep_date_to.getText().toString().trim());
                                if (date1.after(date2)) {
                                    Toast.makeText(mContext, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                dialog.dismiss();


                                try {


                                    if (submitentrydate.isEmpty()) {
                                        Toast.makeText(mContext, "Please enter entry date", Toast.LENGTH_SHORT).show();
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
                                    callSleepTimeUpdateApi(clsNewEditSleepRequest);
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
                        if (mHomeModel != null) {
                            try {
//


                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm a");

                                try {


                                    if (txt_sleep_date_from.getText().toString().trim().isEmpty()) {
                                        Toast.makeText(mContext, "Please select start time", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (txt_sleep_date_to.getText().toString().trim().isEmpty()) {
                                        Toast.makeText(mContext, "Please select end time", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (txt_sleep_date_from.getText().toString().trim().equalsIgnoreCase(txt_sleep_date_to.getText().toString().trim())) {
                                        Toast.makeText(mContext, "Please select correct time", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Date date1 = simpleDateFormat.parse(txt_sleep_date_from.getText().toString().trim());
                                    Date date2 = simpleDateFormat.parse(txt_sleep_date_to.getText().toString().trim());
                                    if (date1.after(date2)) {
                                        Toast.makeText(mContext, "End time should be greater than start time", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    dialog.dismiss();


                                    try {
                                        int newhours = printDifferencenew(date1, date2);


                                        if (submitentrydate.isEmpty()) {
                                            Toast.makeText(mContext, "Please enter entry date", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        ClsNewEditSleepRequest clsNewEditSleepRequest = new ClsNewEditSleepRequest();

                                        String ary_entrydate[]=BackendSubmitFromDate.toString().split(" ");
                                        clsNewEditSleepRequest.setSleepEntryTime(ary_entrydate[0]);
                                        clsNewEditSleepRequest.setStartSleepTime(BackendSubmitFromDate.toString());
                                        clsNewEditSleepRequest.setEndSleepTime(BackendSubmitToDate.toString());

                                        if (edt_sleep_count.getText().toString().trim().isEmpty()) {
                                            clsNewEditSleepRequest.setSLeepCount(0);

                                        } else {
                                            clsNewEditSleepRequest.setSLeepCount(Integer.parseInt(edt_sleep_count.getText().toString()));

                                        }
                                        clsNewEditSleepRequest.setTodayStatusId("0");
                                        clsNewEditSleepRequest.setId("0");
                                        clsNewEditSleepRequest.setDream(isDream);
                                        clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));
                                        callSleepTimeUpdateApi(clsNewEditSleepRequest);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();

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

        txt_sleep_date_to.setOnClickListener(this);
        txt_sleep_date_from.setOnClickListener(this);

        dialog.show();
      }catch (Exception e){
          e.printStackTrace();
      }


    }

    private void callRepeatSleepDataAPI(String c, final Dialog dialogfirst, final TextView txt_add_repeat_sleep, final Button btn_submit_sleep_hours) {

        commonService = Client.getClient().create(CommonService.class);
        utils.showProgressbar(mContext);

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
//                                Toast.makeText(mContext, "No records found", Toast.LENGTH_SHORT).show();
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
                                                Toast.makeText(mContext, "Please select only one time slot", Toast.LENGTH_SHORT).show();
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
                if (Utils.isNetworkAvailable(mContext)) {
                    CallToFetchRecoachId(false);
                } else {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();

                }
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    private void callLockUnlockApi() {

        commonService = Client.getClient().create(CommonService.class);
        utils.showProgressbar(mContext);

//        Call<ClsLockUnlockMain> call = commonService.getLockUnlockDataAPI(userId);
//        call.enqueue(new Callback<ClsLockUnlockMain>() {
//            @Override
//            public void onResponse(Call<ClsLockUnlockMain> call, retrofit2.Response<ClsLockUnlockMain> response) {
//                utils.hideProgressbar();
//                if (response.code() == Client.RESPONSE_CODE_OK) {
//
//
//                    try {
//                        ClsLockUnlockMain moodResponse = response.body();
//
//                        if (moodResponse != null) {
//                            if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){
//
//
//
//
//                            }else {
//                                Toast.makeText(mContext, "No Data found", Toast.LENGTH_SHORT).show();
//                            }
//
//
//
//
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ClsLockUnlockMain> call, Throwable t) {
//                if (Utils.isNetworkAvailable(mContext)) {
//                    CallToFetchRecoachId(false);
//                } else {
//                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
//
//                }
//                utils.hideProgressbar();
//                Log.e("ERROR------>", t.toString());
//            }
//        });
    }


    private void showReepeatSleep(ClsRepeatSleepMain moodResponse, final Dialog dialogfirst, TextView txt_add_repeat_sleep, final Button btn_submit_sleep_hours) {
//        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog.setContentView(R.layout.repeat_sleep);
        RecyclerView recylr_repeat_sleep_list = dialogfirst.findViewById(R.id.recylr_repeat_sleep_list);
         arylst_ClsRepeatSleeps = moodResponse.getData();


        if (arylst_ClsRepeatSleeps!=null&&!arylst_ClsRepeatSleeps.isEmpty()){
            recylr_repeat_sleep_list.setAdapter(new RepeatSleepListAdapter(getContext(), arylst_ClsRepeatSleeps));

        }



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
                        Toast.makeText(mContext, "Please select only one time slot", Toast.LENGTH_SHORT).show();
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


    public String formatRepeatSleepDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    public String formatRepeatSleepDatesnew(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    public String formatSubmitRepeatSleep(String dateFromServer) {
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
        return "N/A";
    }

    private void callRepeatSleepDataAPIfun(ClsRepeatSleepData clsRepeatSleepData) {
        ClsNewEditSleepRequest clsNewEditSleepRequest = new ClsNewEditSleepRequest();
        clsNewEditSleepRequest.setSleepEntryTime(submitentrydate);
        clsNewEditSleepRequest.setStartSleepTime(clsRepeatSleepData.getStartTime().toString());
        clsNewEditSleepRequest.setEndSleepTime(clsRepeatSleepData.getEndTime().toString());
        clsNewEditSleepRequest.setTodayStatusId("0");
        clsNewEditSleepRequest.setId("0");
        clsNewEditSleepRequest.setReeworkerId(String.valueOf(userId));
        callRepeatSleepTimeUpdateApi(clsNewEditSleepRequest);
    }


    private void getMessageMasterSlogan() {


//        utils.showProgressbar(mContext);
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsMessagmasterPojo> call = commonService.getMessageMaster();
        call.enqueue(new Callback<ClsMessagmasterPojo>() {
            @Override
            public void onResponse(Call<ClsMessagmasterPojo> call, retrofit2.Response<ClsMessagmasterPojo> response) {
//                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsMessagmasterPojo moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getCode().equals("1")) {

                                if (moodResponse.getData() != null) {

                                    sessionManager.setStringValue("KEY_HOME_IMAGE", moodResponse.getData().getImgPath());
                                    sessionManager.setStringValue("KEY_HOME_MESSAGE", moodResponse.getData().getHeader());
                                    tvLargeMessage.setText(moodResponse.getData().getHeader());


                                    if (moodResponse.getData().getImgPath().contains(".gif")) {
                                        Glide.with(mContext).asGif().load(moodResponse.getData().getImgPath())
                                                .listener(new RequestListener<GifDrawable>() {
                                                    @Override
                                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {


                                                        return false;
                                                    }

                                                    @Override
                                                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                                                        rel_home_fragment.setBackgroundColor(Color.parseColor("#000000"));


                                                        return false;
                                                    }
                                                })


                                                .into(img_main_background);


                                    } else {
                                        if (moodResponse.getData().getImgPath() != null && !moodResponse.getData().getImgPath().isEmpty()) {
                                            Glide.with(mContext)

                                                    .load(moodResponse.getData().getImgPath())
                                                    .listener(new RequestListener<Drawable>() {
                                                        @Override
                                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

//                                                            rel_home_fragment.setBackgroundColor(Color.parseColor("#000000"));
                                                            return false;
                                                        }
                                                    })
//                                            .apply(options)
                                                    .into(img_main_background);
                                        }


                                    }


//                                    if (Utils.isNetworkAvailable(mContext)) {
//                                        CallToFetchRecoachId(false);
//                                    } else {
//                                        Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();
//
//                                    }
                                }


//                                Toast.makeText(mContext, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();


                            } else {

                                if (Utils.isNetworkAvailable(mContext)) {
                                    CallToFetchRecoachId(false);
                                } else {
                                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();

                                }

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
            public void onFailure(Call<ClsMessagmasterPojo> call, Throwable t) {
                if (Utils.isNetworkAvailable(mContext)) {
                    CallToFetchRecoachId(false);
                } else {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();

                }
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void callSleepTimeUpdateApi(ClsNewEditSleepRequest clsNewEditSleepRequest) {


        utils.showProgressbar(mContext);
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
                                CallToFetchRecoachId(false);


                                Toast.makeText(mContext, "" + moodResponse.getData(), Toast.LENGTH_SHORT).show();


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
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t) {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    private void callRepeatSleepTimeUpdateApi(final ClsNewEditSleepRequest clsNewEditSleepRequest) {


        utils.showProgressbar(mContext);
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
                                CallToFetchRecoachId(false);


                                Toast.makeText(mContext, "" + moodResponse.getData(), Toast.LENGTH_SHORT).show();


                            } else {
                                String from_time = formatRepeatSleepDates(clsNewEditSleepRequest.getStartSleepTime());
                                String to_time = formatRepeatSleepDates(clsNewEditSleepRequest.getEndSleepTime());
                                String msg = "" + from_time + " - " + to_time;


                                Toast.makeText(getContext(), msg + " : " + moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void showDatePickerDailog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, HomeFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog.getDatePicker().setMaxDate(maxDate);

        String strMindate[] = new SessionManager(mContext).getStringValue("mindate").split("-");


        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (strMindate != null) {
            if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                try {
                    if (strMindate.length > 1) {
//                        c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    if (strMindate.length > 1) {
//                        c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void showDatePickerHistoryAddDailog(String[] strMindate) {

        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            datepickerdialog_history = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, HomeFragment.this, year, month, day);


            Date today = new Date();
            Calendar c = Calendar.getInstance();
            if (strMindate != null) {
                if (strMindate.length > 1) {
                    c.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]), Integer.parseInt(strMindate[0]));

                }
            }
            c.setTime(today);
//            c.add(Calendar.MONTH, 3);
            datepickerdialog_history.getDatePicker().setMaxDate(System.currentTimeMillis());
            Calendar c1 = Calendar.getInstance();
            if (strMindate != null) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showEntryDateAddDailog(String strMindate) {
        String dateArray[] = strMindate.split("-");

        try {
            Calendar calendar = Calendar.getInstance();
            int year = Integer.parseInt(dateArray[2]);
            int month = Integer.parseInt(dateArray[1]) - 1;
            int day = Integer.parseInt(dateArray[0]);


            entry_datepickerdialog_history = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, HomeFragment.this, year, month, day);
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);//Year,Mounth -1,Day
            entry_datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());


//            Calendar c1 = Calendar.getInstance();
//            c.set(year, month, day - 1);//Year,Mounth -1,Day
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static boolean containsName(final List<String> transaction, final String search) {
//        for (final String transactionLine : transaction) {
//            if (transactionLine.equals(search)) {
//                return true;
//            }
//        }

        return true;
    }


    private void CallToFetchRecoachId(final boolean isSwipeToRefresh) {

        try {
            if (!isSwipeToRefresh) {
                utils.showProgressbar(mContext);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userId);

        Call<GetRecoachByUserResponse> call = service.GetReecoachId(request);
        call.enqueue(new Callback<GetRecoachByUserResponse>() {
            @Override
            public void onResponse(Call<GetRecoachByUserResponse> call, retrofit2.Response<GetRecoachByUserResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    GetRecoachByUserResponse listResponse = response.body();


                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (listResponse.getData() != null) {
                            recoachId = listResponse.getData().getReecoachId();
                            sessionManager.setIntValue(SessionManager.KEY_USER_REECOACH_ID, recoachId);

                            /*CALL TO GET ALL BASIC DAILY INFO */
                            CallToGetInitialData(isSwipeToRefresh);
                        } else {
                            if (!isSwipeToRefresh)
                                utils.hideProgressbar();
                            mHomeModel = null;
                            if (swipeRefreshLayout.isRefreshing())
                                swipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(mContext, "Recoach is not assigned", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (!isSwipeToRefresh)
                            utils.hideProgressbar();

                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();

                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();

                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<GetRecoachByUserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    /* CALL TO GET INITIAL DATA FROM SERVER */
    public void CallToGetInitialData(final boolean isSwipeToRefresh) {
        HomeFragmentRequest request = new HomeFragmentRequest();
        request.setReeworkerID(userId);
        request.setReecoachID(recoachId);

        if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
            request.setCreatedOn(sessionManager.getStringValue("Entrystatusdate"));

        }else {
            request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        }

        session.setStringValue("SleepDate",request.getCreatedOn());

//        Toast.makeText(mContext, "Date "+request.getCreatedOn(), Toast.LENGTH_SHORT).show();


//        request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        isupdateData = false;
        utils.showProgressbar(mContext);
        Call<HomeFragmentResponse> call = service.getInitialData(request);
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<HomeFragmentResponse>() {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response) {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    HomeFragmentResponse listResponse = response.body();


                    if (listResponse != null && listResponse.getCode() == 1) {
                        mHomeModel = listResponse.getData();
                        setHomeData(mHomeModel);
                        callUserStatusApi();
                    } else {
                        if (!isSwipeToRefresh)
                            utils.hideProgressbar();
                        intaildataMsg = response.body().getMessage();

                        sessionManager.setStringValue("KeynotAssingReacoachMsg", intaildataMsg);


                        callUserStatusApi();


                    }
                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
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

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /* CALL FOR UPDATING DATA */
    private void CallForUpdatingData(HomeFragmentResponse.Data data, boolean ISUpdateSleep) {
        utils.showProgressbar(mContext);

        HomeFragmentRequest request = new HomeFragmentRequest();
        request.setID(data.getID());

        if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
            request.setCreatedOn(sessionManager.getStringValue("Entrystatusdate"));

        }else {
            request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        }


        request.setScheduledStatusID(data.getScheduledStatusID());
        request.setScheduledFoodCalConsumed(data.getScheduledFoodCalConsumed());
        request.setScheduledWaterIntake(data.getScheduledWaterIntake());
        request.setScheduledSleepHours(data.getScheduledSleepHours());
        request.setScheduledMindStatus(data.getScheduledMindStatus());
        request.setScheduledActivityHours(data.getScheduledActivityHours());
        request.setActualFoodCalConsumed(data.getActualFoodCalConsumed());
        request.setActualWaterIntake(data.getActualWaterIntake());
        request.setScheduledFoodCalConsumed(data.getScheduledFoodCalConsumed());
        request.setTotalBurnedCalories(data.getTotalBurnedCalories());
        request.setNetCalories(data.getNetCalories());


        if (ISUpdateSleep) {
            request.setActualSleepHours(data.getActualSleepHours());
            request.setStartSleepTime(BackendSubmitFromDate.toString());
            request.setEndSleepTime(BackendSubmitToDate.toString());


        } else {
            request.setActualSleepHours(0);


        }

        if (!TextUtils.isEmpty(data.getActualMindStatus()))
            request.setActualMindStatus(data.getActualMindStatus());
        else
            request.setActualMindStatus(data.getScheduledMindStatus());

        request.setActualActivityHours(data.getActualActivityHours());
        request.setReeworkerID(userId);
        request.setReecoachID(recoachId);
        String jsonRquest = new Gson().toJson(request);
        String test = jsonRquest;
        isupdateData = true;

        Call<HomeFragmentResponse> call = service.UpdateHomeData(request);
        call.enqueue(new Callback<HomeFragmentResponse>() {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    HomeFragmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(mContext, "Data added successfully", Toast.LENGTH_SHORT).show();

                        mHomeModel = listResponse.getData();
                        setHomeData(mHomeModel);
                    }  //Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    utils.hideProgressbar();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    public void setHomeData(HomeFragmentResponse.Data data) {

        if (data != null) {
            utils.showProgressbar(mContext);
            if (!isupdateData) {

                String arr[] = data.getScheduledWeightText().split(" ");
                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(arr[0]);
                str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(str1);

                for (int i = 1; i < arr.length; i++) {
                    builder.append(" " + arr[i]);
                }


//                textHome_wt_Total.setText(""+data.getScheduledWeightText());
                textHome_wt_Total.setText(builder);

            }
            txt_total_cal_burned.setText("" + Math.round(data.getTotalBurnedCalories())+" kcal");
            txt_total_cal_consumed.setText("" +Math.round(data.getActualFoodCalConsumed())+" kcal");
            txt_net_cal_burned.setText("" + Math.round(data.getNetCalories())+" kcal");



          double  percentage = (float)((data.getActualFoodCalConsumed() / data.getScheduledFoodCalConsumed()) * 100);



            CircularProgressBar progress_circular_consumed,progress_circular_burnt,progress_circular_net;
            progress_circular_consumed = view.findViewById(R.id.progress_circular_consumed);
            progress_circular_consumed.setProgress((float) percentage);
            progress_circular_consumed.setProgressMax(100);
            TextView txt_percentage_consumed=view.findViewById(R.id.txt_percentage_consumed);
            if (data.getScheduledFoodCalConsumed()<1){
                txt_percentage_consumed.setText("0"+"%");

            }else {

                txt_percentage_consumed.setText(""+Math.round(percentage)+"%");

            }

            double  progress_circular_burnt1 = (float)((data.getTotalBurnedCalories() / data.getScheduledFoodCalConsumed()) * 100);

            progress_circular_burnt = view.findViewById(R.id.progress_circular_burnt);
//            progress_circular_burnt.setProgress((float) progress_circular_burnt1);
            progress_circular_burnt.setProgressMax(100);
            TextView txt_percentage_burnt=view.findViewById(R.id.txt_percentage_burnt);




            double  progress_circular_burnt2 = (float)((data.getNetCalories() / data.getScheduledFoodCalConsumed()) * 100);

            progress_circular_net = view.findViewById(R.id.progress_circular_net);
            progress_circular_net.setProgress((float) progress_circular_burnt2);
            progress_circular_net.setProgressMax(100);
            TextView txt_percentage_net=view.findViewById(R.id.txt_percentage_net);

            if (data.getScheduledFoodCalConsumed()<1){
                txt_percentage_net.setText("0"+"%");

            }else {
                txt_percentage_net.setText(""+Math.round(progress_circular_burnt2)+"%");

            }








            if (data.getRegistrationFrom() != null && !data.getRegistrationFrom().isEmpty()) {


                int index = data.getRegistrationFrom().indexOf("T");

                String regdate = RegformatDates(data.getRegistrationFrom().substring(0, index));

                String strMindate[] = regdate.split("-");
                sessionManager.setStringValue("mindate", regdate);
                if (strMindate != null) {
                    showDatePickerHistoryAddDailog(strMindate);

                }
            } else {
                if (data.getScheduledFrom() != null && !data.getScheduledFrom().isEmpty()) {
                    String strMindate[] = data.getScheduledFrom().split("-");
                    sessionManager.setStringValue("mindate", data.getScheduledFrom());
                    if (strMindate != null) {
                        showDatePickerHistoryAddDailog(strMindate);

                    }

                }

            }
            if (data.getScheduledFrom() != null && data.getScheduledTo() != null) {


                if (data.getScheduledFrom() != null) {
                    if (!data.getScheduledFrom().isEmpty()) {
                        ll_add_history_record_date.setEnabled(true);
                    }
                }

                TextView txtplanvalidity=view.findViewById(R.id.txtplanvalidity);
                txtplanvalidity.setText("Plan valid till "  + " " + data.getScheduledTo() + "");

            }
            int id = data.getID();
            todaysstauusId = data.getID();
            sessionManager.setStringValue("key_todaysstauusId", String.valueOf(todaysstauusId));

            String scheduledMindStatus, actualMindStatus;
            scheduledWaterIntake = data.getScheduledWaterIntake();
            scheduledSleepHours = data.getScheduledSleepHours();
            scheduledMindStatus = data.getScheduledMindStatus();
            scheduledActivityHours = data.getScheduledActivityHours();
            if (data.getActualWaterIntake() != null) {
                actualWaterIntake = data.getActualWaterIntake();

            }
            actualSleepHours = data.getActualSleepHours();



            progress_sleep=view.findViewById(R.id.progress_sleep);
            progrees_activity=view.findViewById(R.id.progrees_activity);
            progress_mind=view.findViewById(R.id.progress_mind);




            double  dbl_sleep = (float)((Float.parseFloat(String.valueOf(data.getActualSleepHours())) / Float.parseFloat(String.valueOf(data.getScheduledSleepHours()))) * 100);
            progress_sleep.setProgress((float) dbl_sleep);
            progress_sleep.setProgressMax(100);




            double  dbl_activity = (float)((Float.parseFloat(String.valueOf(data.getTotalBurnedCalories())) / Float.parseFloat(String.valueOf(data.getScheduledActivityHours()))) * 100);
            progrees_activity.setProgressMax(100);

//            Toast.makeText(mContext, "activity actual "+data.getActualActivityHours()+" shedule"+data.getScheduledActivityHours(), Toast.LENGTH_SHORT).show();


            progrees_activity.setProgress((float) dbl_activity);

            progress_circular_burnt.setProgress((float) dbl_activity);
            if (data.getScheduledActivityHours()>0){
                txt_percentage_burnt.setText(""+Math.round(dbl_activity)+"%");

            }else {
                txt_percentage_burnt.setText("0"+"%");

            }

            progress_mind.setProgressMax(100);

            if (data.getActualMindStatus()!=null){
                if (data.getActualMindStatus().equalsIgnoreCase("happy")){
                    progress_mind.setProgress((100));

                }
                if (data.getActualMindStatus().equalsIgnoreCase("neutral")){
                    progress_mind.setProgress((50));

                }
                if (data.getActualMindStatus().equalsIgnoreCase("stress")||data.getActualMindStatus().equalsIgnoreCase("stressed")){
                    progress_mind.setProgress((0));

                }
            }




//            progrees_activity.setProgress(50);





            actualActivityHours = data.getActualActivityHours();
            actualMindStatus = data.getActualMindStatus();
            actualFoodConsumed = data.getActualFoodCalConsumed();
            scheduledFoodConsumed = data.getScheduledFoodCalConsumed();
            if (data.getCanTestSchedule() != null) {
                String str_CanTestSchedule = data.getCanTestSchedule();
                sessionManager.setStringValue("Key_str_CanTestSchedule", str_CanTestSchedule);

            }

            if (scheduledActivityHours > 0)
                progressBarActivity.setMax(scheduledActivityHours);

            if (scheduledSleepHours > 0)
                progressBarSleep.setMax(scheduledSleepHours);


            if (scheduledFoodConsumed > 0)
                progressBarCal.setMax((int) scheduledFoodConsumed);



            /*Food Calories*/
            int len = String.valueOf(actualFoodConsumed).length();
            double dbl_scheduledFoodConsumed = 0;
            double dbl_actualFoodConsumed = 0;

            if (actualFoodConsumed > 0) {
                dbl_actualFoodConsumed = actualFoodConsumed;

            }
            if (scheduledFoodConsumed > 0) {
                dbl_scheduledFoodConsumed = scheduledFoodConsumed;

            }
//            DecimalFormat decimalFormats = new DecimalFormat("0.00");

            String showActual_cal = String.valueOf(dbl_actualFoodConsumed);
            String showScheduled_cal = String.valueOf(dbl_scheduledFoodConsumed);


            String FoodConsume = String.valueOf(showActual_cal) + " " + "of " + String.valueOf(showScheduled_cal) + " " + "kcal consumed";


            TextView txtfoodcalconsumed=view.findViewById(R.id.txtfoodcalconsumed);
            txtfoodcalconsumed.setText("Eat upto "+Math.round(Double.parseDouble(showScheduled_cal))+" kcal");
            sessionManager.setStringValue("shedulecalories", String.valueOf(data.getScheduledFoodCalConsumed()));

            double  dbl_circular_dailydairy_cal_consumed = (float)((data.getActualFoodCalConsumed() / data.getScheduledFoodCalConsumed()) * 100);


            circular_dailydairy_cal_consumed.setProgress((float) dbl_circular_dailydairy_cal_consumed);
            circular_dailydairy_cal_consumed.setProgressMax(100);


//            circular_dailydairy_cal_consumed

            tvFoodTotal.setVisibility(View.VISIBLE);
            if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

                if (actualFoodConsumed > 0) {
                    dbl_actualFoodConsumed = actualFoodConsumed;

                }
                String showActual_cals = String.valueOf(dbl_actualFoodConsumed);

                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(showActual_cals);
                str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.append(str1);
                builder.append(" kcal consumed");

                tvFoodTotal.setText(builder);

            } else {
                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(showActual_cal);
                str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                str1.setSpan(new RelativeSizeSpan(1.2f),0, str1.length(),0 );
                builder.append(str1);

                SpannableString str2 = new SpannableString(showScheduled_cal);
                str2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str2.length(), 0);
                str2.setSpan(new StyleSpan(Typeface.BOLD), 0, str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.append(" of " + str2 + " kcal consumed");

//                tvFoodTotal.setText(String.valueOf(str1) + " " + "of " + String.valueOf(str2) + " " + "Kcal consumed");
                tvFoodTotal.setText(builder);

            }

            double factual = actualFoodConsumed;
            double fconsume = scheduledFoodConsumed;

//            try {
//                int value = (actualFoodConsumed / scheduledFoodConsumed) * 100;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            float finalProtine = (float) ((factual / fconsume) * 100);


            //Calculate Percentage of Calories.
            String Test = String.valueOf(finalProtine);


            float finalPercent = 0;
            try {
                if (scheduledFoodConsumed != 0) {
                    finalPercent = (float) ((actualFoodConsumed / scheduledFoodConsumed) * 100);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String caloriesPercent = finalPercent + "";
            if (Test.contains(".")) {
                int index = Test.indexOf(".");
                String intialValue = Test.substring(0, index);
                txtCalPer.setText(String.valueOf(intialValue) + "%");
            }

            foodProgress();

            /* WATER INTAKE */
            if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                if (actualWaterIntake > 0) {
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    double scheduleintake = Double.valueOf(actualWaterIntake) / 100;
                    String showschedulestr = decimalFormat.format(scheduleintake);


                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    SpannableString str1 = new SpannableString(showschedulestr);
                    str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                    str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(str1);
                    builder.append(" of Litres");



                    CircularProgressBar progress_water=view.findViewById(R.id.progress_water);
                    double  percentage_progress_water = (float)((20.0 / 100.0) * 100);

                    progress_water.setProgress((float) percentage_progress_water);
                    progress_water.setProgressMax(100);


                    txtwaterttotal.setText(builder);
                } else {

                    SpannableStringBuilder builder = new SpannableStringBuilder();
                    SpannableString str1 = new SpannableString(String.valueOf(actualWaterIntake));
                    str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                    str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append(str1);
                    builder.append(" Litres");
                    txtwaterttotal.setText(builder);
                    CircularProgressBar progress_water=view.findViewById(R.id.progress_water);
                    double  percentage_progress_water = (float)((20.0 / 100.0) * 100);

                    progress_water.setProgress((float) percentage_progress_water);
                    progress_water.setProgressMax(100);

                }


                if (actualWaterIntake > 0) {
                    setGlassForTrailData(actualWaterIntake);

                } else {
                    glassList = new ArrayList<>();

                    glassAdapter = new GlassAdapter(mContext, glassList, this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                    rvGlassess.setLayoutManager(layoutManager);
                    rvGlassess.setItemAnimator(new DefaultItemAnimator());
                    rvGlassess.setAdapter(glassAdapter);

                }

            } else {

                DecimalFormat decimalFormat = new DecimalFormat("0.00");


                double scheduleintake = Double.valueOf(scheduledWaterIntake) / 100;

                String showschedule = decimalFormat.format(scheduleintake);


                double actualintake = Double.valueOf(actualWaterIntake) / 100;
                String showActual = decimalFormat.format(actualintake);


                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(showActual);
                str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(str1);
                builder.append(" of " + showschedule + " Litres");




                txtwaterttotal.setText(builder);

                CircularProgressBar progress_water=view.findViewById(R.id.progress_water);

                double  percentage_progress_water = (((Double.parseDouble(String.valueOf(actualWaterIntake))) /(Double.parseDouble(String.valueOf(scheduledWaterIntake)))) * 100);

                progress_water.setProgress((float) percentage_progress_water);
                progress_water.setProgressMax(100);

            }

            if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                if (actualWaterIntake > scheduledWaterIntake && scheduledWaterIntake != 0) {
                    setGlassData(scheduledWaterIntake, scheduledWaterIntake);
                } else {
                    setGlassData(scheduledWaterIntake, actualWaterIntake);
                }

            }

            if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                String strsleepse = formatHoursAndMinutes(actualSleepHours);


                String showTodaysSleeptext = strsleepse;
                String actual_trail_sleep = "";

                if (showTodaysSleeptext.contains("0 Hours") && actualSleepHours < 60) {
                    actual_trail_sleep = showTodaysSleeptext.replace("0 Hours", "");

                } else if (showTodaysSleeptext.contains("00 Mins")) {
                    actual_trail_sleep = showTodaysSleeptext.replace("00 Mins", "");

                } else {
                    actual_trail_sleep = showTodaysSleeptext;

                }


                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(actual_trail_sleep);
                str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(str1);
                builder.append("");
                tvSleepTotal.setText(builder);

            } else {
                String strsleepse = "";
                strsleepse = formatHoursAndMinutes(actualSleepHours);
                float floatscheduledSleepHours = (scheduledSleepHours / 60.0f);
                String s = String.format("%.2f", floatscheduledSleepHours);

                String stractualsleepse = "";

                stractualsleepse = formatHoursAndMinutes(scheduledSleepHours);
                if (scheduledSleepHours < 60) {

                    stractualsleepse = stractualsleepse.replace("0 H", "");
                }
                if (actualSleepHours < 60) {
                    strsleepse = strsleepse.replace("0 H", "");
                }


                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString str1 = new SpannableString(strsleepse);
                str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(str1);
                builder.append(" of " + stractualsleepse + "");


                String newsleeptext=builder.toString().replace("H",":");

                tvSleepTotal.setText(newsleeptext.replace("M","Hr"));


            }


            Log.d("actualSleepHours", String.valueOf(actualSleepHours));
            sleepProgress();

            /* MIND STATUS */
            if (!TextUtils.isEmpty(actualMindStatus)) {
                if (actualMindStatus.contains("happy")) {
                    ivMindMood.setImageResource(R.drawable.ic_happy);


//                    tvMindMood.setText("Seems to be happy");
                    tvMindMood.setText(SpanableMindStatus("Happy"));
                } else if (actualMindStatus.contains("Happy")) {
                    ivMindMood.setImageResource(R.drawable.ic_happy);
                    tvMindMood.setText(SpanableMindStatus("Happy"));
                } else if (actualMindStatus.contains("stress")) {
                    ivMindMood.setImageResource(R.drawable.icc_stressed);
//                    tvMindMood.setText("Seems to be stressed");
                    tvMindMood.setText(SpanableMindStatus("Stressed"));

                } else if (actualMindStatus.contains("Stressed")) {
                    ivMindMood.setImageResource(R.drawable.icc_stressed);
                    tvMindMood.setText(SpanableMindStatus("Stressed"));
                } else if (actualMindStatus.contains("neutral")) {
                    ivMindMood.setImageResource(R.drawable.ic_neutral);
                    tvMindMood.setText(SpanableMindStatus("Neutral"));

                }

            } else {
                ivMindMood.setImageResource(R.drawable.ic_happy);
                tvMindMood.setText(SpanableMindStatus("Happy"));

            }


            if (!isupdateData) {
                if (data.getTotalBurnedCalories() != 0) {

                    SpannableStringBuilder builder = new SpannableStringBuilder();

                    SpannableString str1 = new SpannableString(String.valueOf(Math.round(((data.getTotalBurnedCalories())))));
                    str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
                    str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.append("Calories burned : ");
                    builder.append(str1);
                    tvActivityTotal.setText(builder);

                } else
                    tvActivityTotal.setText("No activity for the day");
            }

            activityProgress();


            utils.hideProgressbar();
        }

    }

    public String RegformatDates(String dateFromServer) {
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

    public SpannableStringBuilder SpanableMindStatus(String text) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1 = new SpannableString(text);
        str1.setSpan(new ForegroundColorSpan(colorBlue), 0, str1.length(), 0);
        str1.setSpan(new StyleSpan(Typeface.BOLD), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("Seems to be ");
        builder.append(str1);

        return builder;
    }


    public void setGlassData(int scheduleWaterIntakes, int actualWaterIntakes) {
        boolean halfglass = false;


        int scheduleWaterIntake = 0;
        if (scheduleWaterIntakes >= 25) {
            scheduleWaterIntake = scheduleWaterIntakes / 25;

        }
        int actualWaterIntake = 0;

        if (actualWaterIntakes >= 25) {
            actualWaterIntake = actualWaterIntakes / 25;

        }

        if (actualWaterIntake>1){
            txtwaterttotal.setText(""+actualWaterIntake+" Glasses out of "+scheduleWaterIntake+" Glasses");

        }else {
            txtwaterttotal.setText(""+actualWaterIntake+" Glass out of "+scheduleWaterIntake+" Glasses");

        }



        int totalglassdata = actualWaterIntakes - (actualWaterIntake * 25);
//        if (totalglassdata==12){
//            halfglass=true;
//            actualWaterIntake=actualWaterIntake+1;
//
//        }


        if (scheduleWaterIntake == 0) {

            scheduleWaterIntake = actualWaterIntake;
        }


        glassList = new ArrayList<>();
        ArrayList<GlassModel> tempGlassList = new ArrayList<>();
        for (int i = 0; i < scheduleWaterIntake; i++) {
            model = new GlassModel();
            model.setScheduleWaterIntake(i + 1);
            tempGlassList.add(model);
        }

        if (actualWaterIntake <= scheduleWaterIntake) {
            for (int i = 0; i < actualWaterIntake; i++) {
                tempGlassList.get(i).setActualWaterIntake(i + 1);

            }
//            if (halfglass){
//                tempGlassList.get(actualWaterIntake+1).setActualWaterIntake(actualWaterIntake+2);
//
//            }

        }

        glassList.addAll(tempGlassList);
        glassAdapter = new GlassAdapter(mContext, glassList, this, halfglass);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rvGlassess.setLayoutManager(layoutManager);
        rvGlassess.setItemAnimator(new DefaultItemAnimator());
        rvGlassess.setAdapter(glassAdapter);
    }


    public void setGlassForTrailData(int actualWaterIntakess) {
        glassList = new ArrayList<>();
        int actualWaterIntake = actualWaterIntakess / 25;

        ArrayList<GlassModel> tempGlassList = new ArrayList<>();
        for (int i = 0; i < actualWaterIntake; i++) {
            model = new GlassModel();
            model.setScheduleWaterIntake(i + 1);
            tempGlassList.add(model);
        }

        for (int i = 0; i < actualWaterIntake; i++) {
            tempGlassList.get(i).setActualWaterIntake(i + 1);
        }

        glassList.addAll(tempGlassList);
        glassAdapter = new GlassAdapter(mContext, glassList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rvGlassess.setLayoutManager(layoutManager);
        rvGlassess.setItemAnimator(new DefaultItemAnimator());
        rvGlassess.setAdapter(glassAdapter);
    }


    /* SET MIND DATA FROM ACTIVITY */
    public void setMindDataFromActivity(MinMoodModel model) {
        try {
            if (mHomeModel != null) {
                if (model != null) {
                    ivMindMood.setImageResource(model.getImage());
                    if (!TextUtils.isEmpty(model.getName()))
                        tvMindMood.setText(model.getName());

                    if (Utils.isNetworkAvailable(mContext)) {
                        if (!TextUtils.isEmpty(model.getName())) {
                            String mood = model.getName();


//                            CallToAddMoodData(mHomeModel.getActualMindStatus());

                            if (mood.contains("happy")) {
                                mHomeModel.setActualMindStatus("happy");
                            } else if (mood.contains("stress")) {
                                mHomeModel.setActualMindStatus("stress");
                            } else if (mood.contains("Stressed")) {
                                mHomeModel.setActualMindStatus("Stressed");
                            } else if (mood.contains("neutral")) {
                                mHomeModel.setActualMindStatus("neutral");
                            }
                            CallToAddMoodData(mHomeModel.getActualMindStatus());


//                            CallForUpdatingData(mHomeModel, false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void CallToAddMoodData(String strFeeling) {
        utils.showProgressbar(context);

        AddMoodRequest request = new AddMoodRequest();
        request.setUserId(userId);
        request.setIsBingeOnLargeQuantity(0);
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
                            CallToFetchRecoachId(false);
//
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



    /* SET MIND DATA FROM ACTIVITY */
    public void setMindDataFromActivityNew(String abc) {
        try {
            if (mHomeModel != null) {

//                    ivMindMood.setImageResource(model.getImage());
                if (!TextUtils.isEmpty(abc))
                    tvMindMood.setText(abc);

                if (Utils.isNetworkAvailable(mContext)) {
                    if (!TextUtils.isEmpty(abc)) {
                        String mood = abc;

                        if (mood.contains("happy")) {
                            mHomeModel.setActualMindStatus("happy");
                        } else if (mood.contains("stress")) {
                            mHomeModel.setActualMindStatus("stress");
                        } else if (mood.contains("Stressed")) {
                            mHomeModel.setActualMindStatus("Stressed");
                        } else if (mood.contains("neutral")) {
                            mHomeModel.setActualMindStatus("neutral");
                        }

                        CallForUpdatingData(mHomeModel, false);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /* SET PROGRESS BAR FOR ACTIVITY */
    public void activityProgress() {
        try {
            int sHour = mHomeModel.getScheduledActivityHours();
            int actualAcHour = mHomeModel.getActualActivityHours();
            if (actualAcHour <= sHour) {
                if (sHour > 0) {
                    float uvAct = 360 / sHour;
                    float rotationAct = actualAcHour * uvAct;
                    ivActivityClock.setRotation(rotationAct);
                    progressBarActivity.setProgress(actualAcHour);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* SET PROGRESS BAR FOR FOOD */
    public void foodProgress() {
        try {
            int sHour = (int) mHomeModel.getScheduledFoodCalConsumed();
            int actualAcHour = (int) mHomeModel.getActualFoodCalConsumed();
            if (actualAcHour <= sHour) {
                if (sHour > 0) {
                    float uvAct = 360 / sHour;
                    float rotationAct = actualAcHour * uvAct;
                    //ivActivityClock.setRotation(rotationAct);
                    progressBarCal.setProgress(actualAcHour);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* SET SLEEP HOURS */
    public void sleepProgress() {
        try {
            int sSleep = mHomeModel.getScheduledSleepHours();
            int actualSleep = mHomeModel.getActualSleepHours();
            if (actualSleep <= sSleep) {
                if (sSleep > 0) {
                    float uv = 360 / sSleep;
                    float rotation = actualSleep * uv;
                    ivSleepClock.setRotation(rotation);
                    progressBarSleep.setProgress(actualSleep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void glassPosition(int pos) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {


        if (StrDateOpen.equalsIgnoreCase("entry")) {
            String dummydateentry = dayOfMonth + "-" + (month + 1) + "-" + year;
            submitentrydate = year + "-" + (month + 1) + "-" + dayOfMonth;
            txt_sleep_entry_date.setText(dummydateentry);
            showEntryDateAddDailog(curentDate);

        }


        try {
            if (StrDateOpen.equalsIgnoreCase("from")) {
                BackendSubmitFromDate = new StringBuilder();
                String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
                StrTimeOpen = "from";
                StrFromDateTime = new StringBuilder();
                StrFromDateTime.append(dummydate.toString());
                BackendSubmitFromDate.append(year + "-" + (month + 1) + "-" + dayOfMonth);



                timepickerdialog.show();

            }


            if (StrDateOpen.equalsIgnoreCase("to")) {
                String dummydate = dayOfMonth + "-" + (month + 1) + "-" + year;
//                String dummydate = year+ "-"+ (month + 1)+"-"+dayOfMonth;
                StrTimeOpen = "to";
                StrToDateTime = new StringBuilder();
                StrToDateTime_check = new StringBuilder();
                StrToDateTime.append(dummydate.toString());
                StrToDateTime_check.append(dummydate.toString());

                BackendSubmitToDate = new StringBuilder();
                BackendSubmitToDate.append(year + "-" + (month + 1) + "-" + dayOfMonth);

                timepickerdialog.show();
            }
            int monthnew = (month + 1);

            if (StrDateOpen.equalsIgnoreCase("getHistory")) {

                if (monthnew < 10) {
                    sessionManager.setStringValue("Cal_consumed_date", year + "-0" + (month + 1) + "-" + dayOfMonth);

                } else {
                    sessionManager.setStringValue("Cal_consumed_date", year + "-" + (month + 1) + "-" + dayOfMonth);

                }

                if (submitHistoryDate.equalsIgnoreCase(year + "-" + (month + 1) + "-" + dayOfMonth)) {
                    return;
                }


                if (monthnew < 10) {

                    dummyhistoryshowdate = dayOfMonth + "-" + "0" + (month + 1) + "-" + year;

                } else {
                    dummyhistoryshowdate = dayOfMonth + "-" + (month + 1) + "-" + year;

                }
                submitHistoryDate = year + "-" + (month + 1) + "-" + dayOfMonth;


                if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()){
                    sessionManager.setStringValue("statusdate", sessionManager.getStringValue("Entrystatusdate"));

                }else {
                    sessionManager.setStringValue("statusdate", submitHistoryDate);

                }

                sessionManager.setStringValue("statusdate", submitHistoryDate);

                if (!curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date varDate = simpleDateFormat.parse(dummyhistoryshowdate);
                    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                    txt_show_status_date.setText(simpleDateFormat.format(varDate) + "'s Status");
                    sessionManager.setStringValue("showDate", simpleDateFormat.format(varDate));


                    txt_show_status_date.setText(sessionManager.getStringValue("showDate"));









                } else {
                    txt_show_status_date.setText("Today's Status" + " (" + curentDateNEw + ")");
                    sessionManager.setStringValue("showDate", "Today's status");
                    txt_show_status_date.setText(sessionManager.getStringValue("showDate"));


                }

                if (curentDate.equalsIgnoreCase(dummyhistoryshowdate)) {
                    txt_show_status_date.setText("Today's Status" + " (" + curentDateNEw + ")");
                    sessionManager.setStringValue("showDate",  curentDateNEw );
                    txt_show_status_date.setText(sessionManager.getStringValue("showDate"));

//                     txt_date_history.setText(dummyhistoryshowdate);


                } else {
                    txt_date_history.setText(dummyhistoryshowdate);

                }


                CallToFetchRecoachId(false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public int printDifferencenew(Date startDate, Date endDate) {
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

        int finalmin = (int) ((totalhousdays * 60) + (inum * 60) + elapsedMinutes);

        return finalmin;
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
        long strsubmitMin = inum_new * 60 + elapsedMinutes;

        return (int) strsubmitMin;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {


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

                txt_sleep_date_from.setText(StrFromDateTime.toString());


            }


            if (StrTimeOpen.equalsIgnoreCase("to")) {

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
                        Toast.makeText(mContext, "Start Time and End Time should be different", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (date1.after(date2)) {
                        Toast.makeText(mContext, "End Time should be greater than Start Time", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(mContext, "Please select Start Time ", Toast.LENGTH_SHORT).show();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void callUserStatusApi() {
        Call<UserStatus> call = loginService.getUserStatusHistroy(userId);

        Log.d("req", call.request().toString());
        call.enqueue(new Callback<UserStatus>() {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response) {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    UserStatus listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("0")) {


                        Data data = listResponse.getData();

                        if (data != null) {

                            try {
                                sessionManager.setStringValue("IsAllowUser", data.getIsAppliedBloodTest());
                                sessionManager.setStringValue("KeyAssingDailyTassk", data.getIsScheduledTask());
                                sessionManager.setStringValue("KeyAssingReecoach", data.getIsReecoachAssigned());
                                sessionManager.setStringValue("KeyBloodTestStatus", data.getBloodTestStatus());
                                sessionManager.setStringValue("KeyIsFreezed", data.getIsFreezed());
                                if (sessionManager.getStringValue("FromWeb").equalsIgnoreCase("true")) {
                                    sessionManager.setStringValue("IsAllowUser", "true");
                                }

                                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                    Intent intent = new Intent(mContext, UnfreezeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                utils.hideProgressbar();

            }
        });

    }


    public interface OnHomeFragmentListener {
        void OnHomeFragment(Uri uri);
    }


    public String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours = "";
        int hoursstr = (totalMinutes / 60);
        if (hoursstr == 1) {
            strhours = " H ";
        } else {
            strhours = " H ";

        }


        String submittime = "";
        submittime = hoursstr + strhours + minutes + " M";


        return submittime;


    }


    private void CallWaterUOMApi() {


        Call<ClsWaterUOMMain> call = service.GetWaterUoMApi();

        call.enqueue(new Callback<ClsWaterUOMMain>() {
            @Override
            public void onResponse(Call<ClsWaterUOMMain> call, Response<ClsWaterUOMMain> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterUOMMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        if (listResponse.getData() != null && !listResponse.getData().isEmpty()) {
                            arylst_uom_water = new ArrayList<String>();
                            arylst_uom_water_IDs = new ArrayList<String>();
                            arylst_uom_water_militers = new ArrayList<String>();
                            for (int i = 0; i < listResponse.getData().size(); i++) {
                                arylst_uom_water.add(listResponse.getData().get(i).getUnit());
                                arylst_uom_water_IDs.add(String.valueOf(listResponse.getData().get(i).getId()));
                                arylst_uom_water_militers.add(String.valueOf(listResponse.getData().get(i).getMillilitre()));
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

    private void Callprofileheader() {


        Call<ClsProfileHeaderData> call = service.getProfileHeaderApi();

        call.enqueue(new Callback<ClsProfileHeaderData>() {
            @Override
            public void onResponse(Call<ClsProfileHeaderData> call, Response<ClsProfileHeaderData> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsProfileHeaderData listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        if (listResponse.getData() != null && !listResponse.getData().isEmpty()) {
                            sessionManager = new SessionManager(mContext);
                            sessionManager.setStringValue("KEY_BASIC_PROFILE", listResponse.getData().get(0).getHeaderName());
                            sessionManager.setStringValue("KEY_HEALTH_PROFILE", listResponse.getData().get(1).getHeaderName());
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
            public void onFailure(Call<ClsProfileHeaderData> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }


    private void addWaterApi(ClsAddWaterRequest clsAddWaterRequest) {

        utils.showProgressbar(mContext);

        Call<ClsWaterAddSuccessData> call = service.SetWaterActivityHistory(clsAddWaterRequest);

        call.enqueue(new Callback<ClsWaterAddSuccessData>() {
            @Override
            public void onResponse(Call<ClsWaterAddSuccessData> call, Response<ClsWaterAddSuccessData> response) {

                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsWaterAddSuccessData listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

                        Toast.makeText(mContext, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        CallToFetchRecoachId(false);

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
                utils.hideProgressbar();
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }
    public void expiredDialog() {
        try {
            final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
            dialog.setContentView(R.layout.dialog_email_verified);
            dialog.setCancelable(false);

//            Please confirm your 'Activation Email' to proceed further.
            TextView txtmessage = dialog.findViewById(R.id.txtmessage);

            String normaltext1 = "Please confirm your";
            String boldText = "Activation Email";
            String normalText = "to proceed further";
            SpannableString str = new SpannableString(normaltext1+boldText + normalText);
            str.setSpan(new StyleSpan(Typeface.BOLD), 20, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            txtmessage.setText(str);
            Button btn_ok_close = dialog.findViewById(R.id.btn_ok_close);
            btn_ok_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.exit(0);
                }
            });


            if (dialog != null) {

                dialog.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void callPathoReecoachStatus() {


        UnfreezeRequest request = new UnfreezeRequest();
        request.setUserid(userId);

        Call<UserStatusResponse> call = unfreezeService.getUserStatus(token, request);
        call.enqueue(new Callback<UserStatusResponse>() {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    UserStatusResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

//                        if (!listResponse.isEmailVerified()){
//
////                            expiredDialog();
//                        }

//                        expiredDialog();


                        if (listResponse.getData().isReecoachRequire()) {

                            sessionManager.setStringValue("KEY_ISSHOW_REECOACH", "true");


                        } else {
                            sessionManager.setStringValue("KEY_ISSHOW_REECOACH", "false");

                        }

                        if (listResponse.getData().isPathoRequire()) {

                            sessionManager.setStringValue("KEY_ISSHOW_PATHO", "true");


                        } else {
                            sessionManager.setStringValue("KEY_ISSHOW_PATHO", "false");

                        }

                        homeActivity.addExpandalbe();


                    }
                } else {
                    //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    Log.d("Error---->", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                utils.hideProgressbar();
            }
        });
    }

    public void selectReecoachPathDialog() {
        try {
            final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
            dialog.setContentView(R.layout.dialog_select_reecoach);
            dialog.setCancelable(false);
            ImageView close_dialg = dialog.findViewById(R.id.close_dialg);
            TextView txt_later = dialog.findViewById(R.id.txt_later);
            TextView txt_header_reeacoch_patho = dialog.findViewById(R.id.txt_header_reeacoch_patho);
            txt_later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            close_dialg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Button btn_select_now = dialog.findViewById(R.id.btn_select_now);

            if (sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true") && sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID) == 0) {
                btn_select_now.setVisibility(View.VISIBLE);
            }
            Button btn_select_patho = dialog.findViewById(R.id.btn_select_patho);

            if (sessionManager.getStringValue("KEY_ISSHOW_PATHO").equalsIgnoreCase("true") && sessionManager.getIntValue(SessionManager.KEY_USER_PATHO_ID) == 0) {
                btn_select_patho.setVisibility(View.GONE);
            }
            btn_select_patho.setVisibility(View.GONE);


            btn_select_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getReecaochTypeData();

//                    callReeocoachData();

//                    startActivity(new Intent(mContext, ChangeReecoachActivity.class));

                }
            });

            btn_select_patho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(mContext, SelectPathoActivity.class));

                }
            });
            TextView txt_lable_expired = dialog.findViewById(R.id.txt_lable_expired);

            if (dialog != null) {

                dialog.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView(View view) {
        colorBlue = ContextCompat.getColor(mContext, R.color.colorRobinEggBlue);
        colorBlack = ContextCompat.getColor(mContext, R.color.colorPremiumBlack);
        imgHome_sun = view.findViewById(R.id.imgHome_sun);
        tvWishUser = view.findViewById(R.id.labelHome_WishMessage);
        constrainweight_Master = view.findViewById(R.id.constrainweight_Master);

        img_addFood = view.findViewById(R.id.img_addFood);
        txt_reescore = view.findViewById(R.id.txt_reescore);

        progress_circular_consumed1 = view.findViewById(R.id.progress_circular_consumed1);

//        img_userimage = view.findViewById(R.id.img_userimage);
        txtwaterttotal = view.findViewById(R.id.txtwaterttotal);
        circular_dailydairy_cal_consumed = view.findViewById(R.id.circular_dailydairy_cal_consumed);
        ll_profile_home = view.findViewById(R.id.ll_profile_home);
        ll_profile_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyProfileActivity.class);
                startActivity(intent);

            }
        });

        layout_setting = view.findViewById(R.id.layout_setting);

        layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"Coming Soon",Toast.LENGTH_LONG).show();

            }
        });

        img_activity  = view.findViewById(R.id.img_activity);

        img_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }


                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }


                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                  Intent  intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 0);
                    intent.putExtra("MEAL_CAL_MAX", 0);
                    sessionManager.setStringValue("AddFood","true");

                    startActivity(intent);

                } else {


                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                      Intent  intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sessionManager.setStringValue("AddFood","true");

                        startActivity(intent);
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {
                      Intent  intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 0);
                        sessionManager.setStringValue("AddFood","true");

                        intent.putExtra("MEAL_CAL_MAX", (int) mHomeModel.getScheduledFoodCalConsumed());
                        startActivityForResult(intent, 501);
                    } else {
                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
        });

        img_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Utils.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "No internet !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (callLockFunction("DAILYDIARY")) {

                    callProcedureText("DAILYDIARY");
                    return;


                }
                if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                  Intent  intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 3);
                    sessionManager.setStringValue("AddActivity","true");

                    startActivity(intent);


                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                       Intent intent = new Intent(mContext, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        sessionManager.setStringValue("AddActivity","true");
                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(mContext, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    }
//                    else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(mContext, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    }
                    else if (mHomeModel != null) {
                      Intent  intent = new Intent(mContext, MasterDetailsActivity.class);
                        intent.putExtra("FRAGMENT_POSITION", 3);

                        startActivity(intent);
                        sessionManager.setStringValue("AddActivity","true");


                    } else {

                        if (!intaildataMsg.isEmpty()) {
                            Toast.makeText(mContext, intaildataMsg, Toast.LENGTH_LONG).show();
                        }

                    }
                }

            }
        });

     String   userPhoto = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);


        callLockUnlockApi();

        ShapeableImageView imageView = view.findViewById(R.id.image_view);
//        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, (float) 4.0)
                .build());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, MyProfileActivity.class);
                startActivity(intent);


            }
        });
        if (isValidContextForGlide(mContext)) {
//            Glide.with(mContext)
//                    .load(userPhoto)
//                    .apply(
//                            RequestOptions.circleCropTransform()
//                                    .placeholder(R.drawable.ic_profile_pic_bg)
//                                    .error(R.drawable.ic_profile_pic_bg)
//                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    )
//                    .into(imageView);


            Glide
                    .with(mContext)
                    .load(userPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);


        }




        String userName = "Hi "+sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME) + " " +
                sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME)+"!";
        TextView userfullname=view.findViewById(R.id.userfullname);
        userfullname.setText(userName);



        constrainweight_Master.setOnClickListener(this);
        textHome_wt_Total = view.findViewById(R.id.textHome_wt_Total);
        rel_home_fragment = view.findViewById(R.id.rel_home_fragment);
        tvLargeMessage = view.findViewById(R.id.labelHome_LargeMessage);
        ll_add_history_record_date = view.findViewById(R.id.ll_add_history_record_date);
        ll_add_history_record_date.setEnabled(false);
        txt_date_history = view.findViewById(R.id.txt_date_history);
        txt_show_status_date = view.findViewById(R.id.heaser);
        txt_total_cal_burned = view.findViewById(R.id.txt_total_cal_burned);
        txt_total_cal_consumed = view.findViewById(R.id.txt_total_cal_consumed);
        txt_net_cal_burned = view.findViewById(R.id.txt_net_cal_burned);
        txt_plan = view.findViewById(R.id.txt_plan);
        imgHome_Addweight = view.findViewById(R.id.imgHome_Addweight);
        cvHealth = view.findViewById(R.id.cvHealth);
        cvReport = view.findViewById(R.id.cvReport);
        cvReevaluate = view.findViewById(R.id.cvReevaluate);
        cvReeplan = view.findViewById(R.id.cvReeplan);
        txt_header_todays_Status = view.findViewById(R.id.txt_header_todays_Status);
        img_main_background = view.findViewById(R.id.img_main_background);
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);
        /* FOOD VARIABLES */
        masterFood = view.findViewById(R.id.constrainFood_Master);
        tvFoodTotal = view.findViewById(R.id.textHome_Food_Total);
        /* WATER VARIABLES */
        rvGlassess = view.findViewById(R.id.rvGlassess);
        masterWater = view.findViewById(R.id.constrainWater_Master);
        progressBarCal = view.findViewById(R.id.progressBarCal);
        txtCalPer = view.findViewById(R.id.txtCalPer);
        tvWaterTotal = view.findViewById(R.id.textHome_Water_Total);
        ivAddWater = view.findViewById(R.id.imgHome_AddWater);
        listGlasses = new ArrayList<>();
        /* SLEEP VARIABLES */
        masterSleep = view.findViewById(R.id.constrainSleep_Master);
        tvSleepTotal = view.findViewById(R.id.textHome_Sleep_Total);
        ivAddSleep = view.findViewById(R.id.imgHome_AddSleep);
        ivSleepClock = view.findViewById(R.id.imgHome_Sleep_ClockHand);
        progressBarSleep = view.findViewById(R.id.progressBar_Home_Sleep);
        /* MIND VARIABLES */
        masterMind = view.findViewById(R.id.constrainMind_Master);
        tvMindMood = view.findViewById(R.id.textHome_Mind_Total);
        ivAddMind = view.findViewById(R.id.imgHome_AddMind);
        ivMindMood = view.findViewById(R.id.ivMood);
        imgHome_AddFood = view.findViewById(R.id.imgHome_AddFood);
        /* ACTIVITY VARIABLES */
        masterActivity = view.findViewById(R.id.constrainActivity_Master);
        tvActivityTotal = view.findViewById(R.id.textHome_Activity_Total);
        ivAddActivity = view.findViewById(R.id.imgHome_AddActivity);
        ivActivityClock = view.findViewById(R.id.imgHome_Activity_ClockHand);
        progressBarActivity = view.findViewById(R.id.progressBar_Home_Activity);
    }

    private void addClickListeners() {

        masterFood.setOnClickListener(this);
        masterWater.setOnClickListener(this);
        masterSleep.setOnClickListener(this);
        masterMind.setOnClickListener(this);
        masterActivity.setOnClickListener(this);
        ivAddWater.setOnClickListener(this);
        ivAddSleep.setOnClickListener(this);
        ivAddMind.setOnClickListener(this);
        ivAddActivity.setOnClickListener(this);
        cvHealth.setOnClickListener(this);
        cvReport.setOnClickListener(this);
        cvReeplan.setOnClickListener(this);
        cvReevaluate.setOnClickListener(this);
        imgHome_AddFood.setOnClickListener(this);
        imgHome_Addweight.setOnClickListener(this);
        ll_add_history_record_date.setOnClickListener(this);
    }


    public void showSubscriptionSuccessDailog() {

        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.reevaluate_lay);
        Button btn_reevaluate_later = dialog.findViewById(R.id.btn_reevaluate_later);
        Button btn_reevaluate = dialog.findViewById(R.id.btn_reevaluate);


        btn_reevaluate_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });

        btn_reevaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(mContext, ReeevaluateHealthparamActivity.class);
                Intent intent1 = new Intent(mContext, NewDesignHealthActivity.class);
                intent1.putExtra("ISFromReevaluate", true);
                startActivity(intent1);
                dialog.dismiss();

            }
        });

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("REEvaluate");
        ImageView imageView = dialog.findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void shownoplan() {

        final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);

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

                Intent intent = new Intent(mContext, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }

    private void getReecaochTypeData() {

        ReecoachService reecoachService = Client.getClient().create(ReecoachService.class);

        utils.showProgressbar(mContext);

        Call<ClsReecoachMasterType> call = reecoachService.GetReecoachTypeMaster();
        call.enqueue(new Callback<ClsReecoachMasterType>() {
            @Override
            public void onResponse(Call<ClsReecoachMasterType> call, retrofit2.Response<ClsReecoachMasterType> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsReecoachMasterType moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getCode() == 1) {

                                if (moodResponse.getData() != null) {
                                    Intent intent = new Intent(mContext, ChangeReecoachActivity.class);
                                    intent.putExtra("KEY_ReecoachMasterType", moodResponse);
                                    startActivityForResult(intent, 205);
                                }

//                                Toast.makeText(mContext, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();
                            } else {


                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                new UserHealthResponse().Data=moodResponse.getData();

                }


            }

            @Override
            public void onFailure(Call<ClsReecoachMasterType> call, Throwable t) {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


}
