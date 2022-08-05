package com.shamrock.reework.activity.MyPlansModule.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.MicrorlNutritionActivityAnalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.SleepAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ColoriesAnalysisActivty;
import com.shamrock.reework.activity.AnalysisModule.activity.food.FoodNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.mind.MindAnalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.SleepNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.useractivity.ActivityNapMainActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.water.WaterAnalysisActivity;
import com.shamrock.reework.activity.AnalysisModule.activity.weight.WeightAnalysis;
import com.shamrock.reework.activity.AppoinmentModule.activity.AppoinmentScheduleActivity;
import com.shamrock.reework.activity.AppoinmentModule.adapter.MyAppointmentsAdapter;
import com.shamrock.reework.activity.AppoinmentModule.dialog.MyAppoinmentEditDialog;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.activity.BeforeAfterModule.service.BeforeAfterService;
import com.shamrock.reework.activity.BloodTestModule.activity.GetBloodTestReportRes;
import com.shamrock.reework.activity.BloodTestModule.activity.ScheduleBloodTestActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.ScheduleBloodTestAdapter;
import com.shamrock.reework.activity.BloodTestModule.pojo.OtherReportData;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.FoodModule.activity.FoodRecipeActivity;
import com.shamrock.reework.activity.FoodModule.adapter.FoodSnippingAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.FoodTripAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.UserFoodTripAdapterN;
import com.shamrock.reework.activity.FoodModule.adapter.UserFoodTripAdapterNew;
import com.shamrock.reework.activity.FoodModule.adapter.UserFoodTripMealAdapterNew;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistory;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistoryRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodData;
import com.shamrock.reework.activity.FoodModule.model.FoodHistoryData;
import com.shamrock.reework.activity.FoodModule.model.FoodMealData;
import com.shamrock.reework.activity.FoodModule.model.foodtripuser.ClsUSerFoodTripmain;
import com.shamrock.reework.activity.FoodModule.model.foodtripuser.UserFoodTripData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.FoodModule.service.OnFavoriteClick;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.activity.MyPlansModule.adapter.MyPlanMasterAdapter;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.MyRecoachModule.adapters.AdditionalQnAdapter;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.Pathologists.ChangePathoActivity;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.ReminderModule.dialog.RemindersEditDialoge;
import com.shamrock.reework.activity.RescoreModule.activity.RescoreIntroActivity;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.activity.cheatplan.MyCheatPlanActivity;
import com.shamrock.reework.activity.cheatplan.MyCheatPlanAdapter;
import com.shamrock.reework.activity.cheatplan.pojo.ClsMainPlansClass;
import com.shamrock.reework.activity.cheatplan.pojo.ClsOccastionMain;
import com.shamrock.reework.activity.cheatplan.pojo.Plans;
import com.shamrock.reework.activity.cheatplan.reeplaceitem.CheatPlanDataMain;
import com.shamrock.reework.activity.cheatplan.reeplaceitem.ReeplaceItemAdapter;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.foodguide.ClsFoodGuideMain;
import com.shamrock.reework.activity.foodguide.FoodGuidePojo;
import com.shamrock.reework.activity.foodguide.FoodGuidelistAdapter;
import com.shamrock.reework.activity.homemenu.ExpandableListAdapter;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanActivity;
import com.shamrock.reework.activity.mypathoplan.ClsMyPathoPlanActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ReeevaluateHealthparamActivity;
import com.shamrock.reework.activity.spirituallibrary.adapter.SpiritualTypeCategoryAdapter;
import com.shamrock.reework.activity.spirituallibrary.adapter.SpiritualVideoListAdapter;
import com.shamrock.reework.activity.spirituallibrary.listenres.OnSpiritualTypeCLick;
import com.shamrock.reework.activity.spirituallibrary.listenres.OnVideoCLick;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritaulTypeMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.SpiritualTypeData;
import com.shamrock.reework.activity.testimals.ClsMyTestimonialsMain;
import com.shamrock.reework.activity.testimals.ClsSuccessTest;
import com.shamrock.reework.activity.testimals.ClsTestimalMain;
import com.shamrock.reework.activity.testimals.MyTestominalsData;
import com.shamrock.reework.activity.testimals.TestimalDataClass;
import com.shamrock.reework.activity.testimals.TestimalsListAdapter;
import com.shamrock.reework.adapter.DifferentRowAdapterNew;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.FoodTripRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.request.ReecoachDetailsRequest;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.BeforeAfterResponse;
import com.shamrock.reework.api.response.FoodSnippingResp;
import com.shamrock.reework.api.response.FoodTripFavoriteUpdateResponse;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.api.response.GetFilterSubFilterResponce;
import com.shamrock.reework.api.response.GetRecoachByUserResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.MyPlanMastersResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;
import com.shamrock.reework.payment.PaymentHistoryAdapter;
import com.shamrock.reework.payment.paymentDetails;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class FoodGuideActivity extends AppCompatActivity implements View.OnClickListener, MyAppointmentsAdapter.MyAppointmentListener,
        MyAppoinmentEditDialog.ApponmentEditInterface, AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener,
        OnVideoCLick, OnSpiritualTypeCLick, OnFavoriteClick,  TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,

        FoodTripAdapter.FoodTripListener, DifferentRowAdapterNew.OnMealClickListner {


    LinearLayout layout_pathologist, layout_schedulept, layout_testimonials, layout_externaldevice,
            layout_analytics, layout_reeplaceitem, layout_paymentHistory,layout_foodguide,layout_eshoping,
            layout_wellnesslibrary,layout_foodtripe;
    Button btn_pathologist, btn_schedulepathologytest, btn_testimonials, btn_externaldevices, btn_analytics, btn_reeplaceitems,
            btn_paymenthistory, btn_foodguide,btn_greenfood,btn_orangefood,btn_redfood,btn_foodtrip;

    LinearLayout layout_greenfood,layout_orangefood,layout_redfood;
    RecyclerView recyler_food_guide;
    TextView txt_greenall,txt_greenvegetable,txt_greenfruits,txt_greenNutsseeds,
            txt_orangeall,txt_orangevegetable,txt_orangefruits,txt_orangeNutsseeds,
            txt_redall,txt_redvegetable,txt_redfruits,txt_redNutsseeds;
    LinearLayout layout_greenallfood,layout_greenvegetablefood,layout_greenfruitsfood,layout_greennutsfood,
            layout_orangeallfood,layout_orangevegetablefood,layout_orangefruitsfood,layout_orangenutsfood,
            layout_redallfood,layout_redvegetablefood,layout_redfruitsfood,layout_rednutsfood;


    Button btn_eshoping, btn_wellnesslibrary;


    //Pathlogy

    private static final String TAG = "MyReecoachProfile";
    Context context;
    Toolbar toolbar;
    Typeface font;
    FoodGuidelistAdapter lGuidelistAdapter;
    RadioButton rd_button_reecoach_appointment;
    ImageView imgReecoachBg, imgReecoach;
    TextView textView_Name, textView_MobNo, textView_Email, textView_RegAddress, textView_NewAddress;
    SessionManager sessionManager;
    ReecoachService reecoachService;
    Utils utils;
    String email;
    int userId;
    ImageView imgViewNext_roacoach;
    private ProgressBar progressBar;
    Button btn_change_reecoach;
    RecyclerView recyler_reecoach_add_info;
    RadioButton rd_button__reecoach_profile;

    //appointmnt
    LinearLayout btnScheduleAppointment;
    Button buttonSleep_ViewMore;
    RecyclerView recyclerView;
    ArrayList<GetAllAppointmentResponse.AppointmentData> appointmentDataArrayList;
    MyAppointmentsAdapter adapter;
    MyAppoinmentEditDialog editDialog;
    AppoinmentService appoinmentService;
    public static final int REQ_FOR_APPOINMENT = 1000;
    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    TextView txtNoData;
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView txt_no_weight;
    ViewFlipper vp_reecoach;


    //Schedule Pathology Test

    private static final int ADD_REMINDERS = 101;
    Context contextspt;
    Toolbar toolbarspt;
    Typeface fontspt;
    Utils utilsspt;
    ReeTestService Service;
    SessionManager sessionManagerspt;

    LinearLayout buttonSchedule;
    RelativeLayout linearNoData;

    ArrayList<GetBloodTestReportRes.DataList> reminderList;
    RecyclerView recyclerViewspt;
    ScheduleBloodTestAdapter remindersAdapter;

    RemindersEditDialoge editDialogspt;
    int userIdspt;

    LinearLayout mainLayoutspt;
    RelativeLayout noInternetLayoutspt;
    Button btnRefreshspt;


    //Testimonials

    private FloatingActionButton fab_add_testimals;
    private RecyclerView recler_testimals;
    private Utils util;
    private SessionManager sessionManagertest;
    private int userID;
    //    Context context;
    private static final int BUFFER_SIZE = 1024 * 2;
    boolean isFile = true;
    Dialog dialog;
    private ReeTestService reeTestService;
    File fileuploadimage = new File("");
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;
    private String docFilePath = "";

    ArrayList<OtherReportData> arylst_other_Data;
    private boolean isCamera;
    ArrayList<FoodGuidePojo> arylst_foodguide_selected;
    private Uri mCapturedImageURI;

    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    EditText editText;
    int testo_ID;
    private String filetype;

//Analytics

    Context mContext;
    //    Toolbar toolbar;
    RelativeLayout rlReescore, rlBcaReport, rlCalories, rlMyActivity, rlWater, rlSleep,
            rlStress, rlNutrition, rlReeplan, rlBloodReport, rlweightsanalysis, rlReeevaluate;
    private String mSubscription;
    ArrayList<String> subscription_List;

    private int userIdana;
    private int recoachId;

    SessionManager sessionManagerana;
    HomeFragmentService service;
    Utils utilsana;
    String mindStatus;
    int bingQuantity = 0;

    int recoachResonseCode = 0;
    String reaochnotAssingMSg = "";


    //Reeplace item

    private FoodService foodService;
    private Context mContextreeplace;
    private Utils utilsreeplace;
    private SessionManager sessionManagerreeplace;
    private int userIdreeplace;
    private ArrayList<String> arylst_occasations;
    private ArrayList<String> arylst_category;
    private ArrayList<Plans> ary_plan;
    private Spinner spinner_occasion;
    private Spinner spinner_category;
    private RecyclerView recyler_plansData;
    private String Str_occaasation = "";
    private String str_category = "";
    ClsMainPlansClass listResponse;
    SearchView search_chaet;
    MyCheatPlanAdapter myCheatPlanAdapter;
    TextView txt_no_cheaet;
    Spinner spinnerCategory;
    LinearLayout ll_cheat_header;
    boolean iSFirstTime = false;
    private ReeplaceItemAdapter reeplaceItemAdapter;


    //Payment History

    RecyclerView recylerview_payment;


    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;
    private ArrayList<FoodGuidePojo> food_guide_list=new ArrayList<>();



    //E-Shoping


    private TextView tvTitle;
    Button btn_medicine,btn_food,btn_restaurant;
    LinearLayout layout_medicine,layout_food,layout_restaurant;
//    private Context context;
//    Toolbar toolbar;
//    private int userID;
//    private SessionManager sessionManager;

//    TextView tvNotificationCOunt;
//    private BroadcastReceiver mBroadcastReceiver;
//    int  mNotifcationCount = 0;
//    NotificationService notificationService;


//Wellness Library

    private RecyclerView recylcer_spiritual_category;
    ArrayList<ClsSpiritualData>  arylst_spirituallist=new ArrayList<>();
    private RecyclerView recylcer_spiritual_list;
    private CommonService commonService;
    //    private Utils utils;
    private SessionManager session;
    TextView txt_no_data_spiritual;


// Food Tripe


     RadioGroup relfood;
    TextView food1,txt_rbFoodSnapping;
    //    private DatePickerDialog datepickerdialog;
//    private TimePickerDialog timepickerdialog;
    StringBuilder stringBuilder_datetime;
    TextView txt_date_time;
    String curentDateTime="";
    StringBuilder strSubmitDateTime=new StringBuilder();
    //For SubList Dialog
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterListDialog;
    boolean isVeg=true;
    TextView norecipe;


    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
//    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
//            FILE_SELECT_REQUEST_CODE = 300;
    public static final int SECOND_ACTIVITY_REQUEST_CODE = 108;
//    public boolean isCamera = false;
    public boolean isImage = false;
//    private Uri mCapturedImageURI;
//    private Context mContext;
    RadioButton rbFood, rbFoodSnapping,rbFoodreplace, rbHistory;

    RecyclerView rvAllRecipe, rvFoodSnipping;
    ArrayList<FoodTripResponse.FoodStripData> mFoodList;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterList;
    FoodTripAdapter mFoodTripAdapter;;
    UserFoodTripAdapterN user_mFoodTripAdapter;;
    LinearLayout ll_food_recipe;
    ViewFlipper vp_reciepe;
    RecyclerView rvnonvegRecipies;
    RadioButton rd_button_nonveg;
    RadioButton rd_button_veg;

    FoodSnippingAdapter mFoodSnippingAdapter;
    List<FoodSnippingResp.Datum> mSnippingList;

//    private Utils utils;
//    FoodService foodService;
//    SessionManager sessionManager;

    ImageView imgFilter;
    Button fabAdd;
    private static final String ARG_PARAM3 = "MEAL_CAL_MAX";
//    int userId;
    private  int mParam3=0;
    public static int FOOD_TYPE;

    boolean isFoods = false;
    boolean isExpand = false;
    private DatePickerDialog datepickerdialog;
    private TimePickerDialog timepickerdialog;

    List<TodaysMeal> list = new ArrayList<>();
    SearchView searchRecipe;
    String   strrecipe="Vegetarian";
    private ArrayList<UserFoodTripData> arylst_userfood_trip;



//    private String submitFromDate;
//    private String submitToDate;

    ArrayList<FoodData> listFood =new ArrayList<FoodData>();
    ArrayList<FoodHistoryData> listFood1 =new ArrayList<FoodHistoryData>();
    ArrayList<ArrayList<FoodHistoryData>> listFood2 =new ArrayList<ArrayList<FoodHistoryData>>();
    ArrayList<FoodHistoryData> listFood3 =new ArrayList<FoodHistoryData>();
    ArrayList<FoodMealData> listFood4 =new ArrayList<FoodMealData>();
    RecyclerView recyclerView_All_Meal_Weekly;

    ArrayList<FoodTripResponse.FoodStripData> mFoodList1;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterList1;
    List<FoodSnippingResp.Datum> mSnippingList1;



    RecyclerView recyclerViewTodays;
    LinearLayout layout_daily,layout_foodrec;
    TextView txt_nodata,txt_nodata1;
    //    private Context context;
    UserFoodTripAdapterNew adapter1;






    LinearLayout layot_foodhistory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodguide);


        context = FoodGuideActivity.this;


        recyler_food_guide = findViewById(R.id.recyler_food_guide);
        layout_pathologist = findViewById(R.id.layout_pathologist);
        layout_schedulept = findViewById(R.id.layout_schedulept);
        layout_testimonials = findViewById(R.id.layout_testimonials);
        layout_externaldevice = findViewById(R.id.layout_externaldevice);
        layout_analytics = findViewById(R.id.layout_analytics);
        layout_reeplaceitem = findViewById(R.id.layout_reeplaceitem);
        layout_paymentHistory = findViewById(R.id.layout_paymentHistory);
        layout_foodguide = findViewById(R.id.layout_foodguide);
        layout_eshoping = findViewById(R.id.layout_eshoping);
        layout_wellnesslibrary = findViewById(R.id.layout_wellnesslibrary);
        layout_foodtripe = findViewById(R.id.layout_foodtripe);

        layout_greenfood = findViewById(R.id.layout_greenfood);
        layout_orangefood = findViewById(R.id.layout_orangefood);
        layout_redfood = findViewById(R.id.layout_redfood);


        btn_pathologist = findViewById(R.id.btn_pathologist);
        btn_schedulepathologytest = findViewById(R.id.btn_schedulepathologytest);
        btn_testimonials = findViewById(R.id.btn_testimonials);
        btn_externaldevices = findViewById(R.id.btn_externaldevices);
        btn_analytics = findViewById(R.id.btn_analytics);
        btn_reeplaceitems = findViewById(R.id.btn_reeplaceitems);
        btn_paymenthistory = findViewById(R.id.btn_paymenthistory);
        btn_eshoping = findViewById(R.id.btn_eshoping);
        btn_wellnesslibrary = findViewById(R.id.btn_wellnesslibrary);

        btn_foodguide = findViewById(R.id.btn_foodguide);

        btn_greenfood = findViewById(R.id.btn_greenfood);
        btn_orangefood = findViewById(R.id.btn_orangefood);
        btn_redfood = findViewById(R.id.btn_redfood);
        btn_foodtrip = findViewById(R.id.btn_foodtrip);

        txt_greenall = findViewById(R.id.txt_greenall);
        txt_greenvegetable = findViewById(R.id.txt_greenvegetable);
        txt_greenfruits = findViewById(R.id.txt_greenfruits);
        txt_greenNutsseeds = findViewById(R.id.txt_greenNutsseeds);

        layout_greenallfood = findViewById(R.id.layout_greenallfood);
        layout_greenvegetablefood = findViewById(R.id.layout_greenvegetablefood);
        layout_greenfruitsfood = findViewById(R.id.layout_greenfruitsfood);
        layout_greennutsfood = findViewById(R.id.layout_greennutsfood);


        txt_orangeall = findViewById(R.id.txt_orangeall);
        txt_orangevegetable = findViewById(R.id.txt_orangevegetable);
        txt_orangefruits = findViewById(R.id.txt_orangefruits);
        txt_orangeNutsseeds = findViewById(R.id.txt_orangeNutsseeds);

        layout_orangeallfood = findViewById(R.id.layout_orangeallfood);
        layout_orangevegetablefood = findViewById(R.id.layout_orangevegetablefood);
        layout_orangefruitsfood = findViewById(R.id.layout_orangefruitsfood);
        layout_orangenutsfood = findViewById(R.id.layout_orangenutsfood);

        txt_redall = findViewById(R.id.txt_redall);
        txt_redvegetable = findViewById(R.id.txt_redvegetable);
        txt_redfruits = findViewById(R.id.txt_redfruits);
        txt_redNutsseeds = findViewById(R.id.txt_redNutsseeds);

        layout_redallfood = findViewById(R.id.layout_redallfood);
        layout_redvegetablefood = findViewById(R.id.layout_redvegetablefood);
        layout_redfruitsfood = findViewById(R.id.layout_redfruitsfood);
        layout_rednutsfood = findViewById(R.id.layout_rednutsfood);

        btn_pathologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        Intent intent = new Intent(context, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {

                        layout_pathologist.setVisibility(View.VISIBLE);
                        layout_schedulept.setVisibility(View.GONE);
                        layout_testimonials.setVisibility(View.GONE);
                        layout_externaldevice.setVisibility(View.GONE);
                        layout_analytics.setVisibility(View.GONE);
                        layout_reeplaceitem.setVisibility(View.GONE);
                        layout_paymentHistory.setVisibility(View.GONE);
                        layout_foodguide.setVisibility(View.GONE);
                        layout_wellnesslibrary.setVisibility(View.GONE);
                        layout_eshoping.setVisibility(View.GONE);
                        layout_foodtripe.setVisibility(View.GONE);

                        btn_pathologist.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                        btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                        btn_pathologist.setTextColor(getResources().getColor(R.color.white_recipe));
                        btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));


                    }
                }


            }
        });


        btn_schedulepathologytest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {
//                        if (getLockUnlockStatus("PATHOTEST")) {
//                            Toast.makeText(context, "Menu is lock", Toast.LENGTH_SHORT).show();

//                        }

                    if (false) {
                        Toast.makeText(context, "Menu is lock", Toast.LENGTH_SHORT).show();
                    } else {
//                            if (containsName(subscription_List, "Schedule Blood Test")) {

                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            Intent intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        } else if (sessionManager.getStringValue("KeyBloodTestStatus").equalsIgnoreCase("3")) {
                            Toast.makeText(context, "You are already schedule a request. You can't schedule new request meanwhile.", Toast.LENGTH_SHORT).show();

                        } else {


                            layout_pathologist.setVisibility(View.GONE);
                            layout_schedulept.setVisibility(View.VISIBLE);
                            layout_testimonials.setVisibility(View.GONE);
                            layout_externaldevice.setVisibility(View.GONE);
                            layout_analytics.setVisibility(View.GONE);
                            layout_reeplaceitem.setVisibility(View.GONE);
                            layout_paymentHistory.setVisibility(View.GONE);
                            layout_foodguide.setVisibility(View.GONE);
                            layout_wellnesslibrary.setVisibility(View.GONE);
                            layout_eshoping.setVisibility(View.GONE);
                            layout_foodtripe.setVisibility(View.GONE);

                            btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                            btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                            btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.white_recipe));
                            btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));


                        }

//                            } else {
//                            }
                    }
                }


            }
        });

        btn_testimonials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {

                    layout_pathologist.setVisibility(View.GONE);
                    layout_schedulept.setVisibility(View.GONE);
                    layout_testimonials.setVisibility(View.VISIBLE);
                    layout_externaldevice.setVisibility(View.GONE);
                    layout_analytics.setVisibility(View.GONE);
                    layout_reeplaceitem.setVisibility(View.GONE);
                    layout_paymentHistory.setVisibility(View.GONE);
                    layout_foodguide.setVisibility(View.GONE);
                    layout_wellnesslibrary.setVisibility(View.GONE);
                    layout_eshoping.setVisibility(View.GONE);
                    layout_foodtripe.setVisibility(View.GONE);

                    btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_testimonials.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                    btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                    btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_testimonials.setTextColor(getResources().getColor(R.color.white_recipe));
                    btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));


                }


            }
        });


        btn_externaldevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pathologist.setVisibility(View.GONE);
                layout_schedulept.setVisibility(View.GONE);
                layout_testimonials.setVisibility(View.GONE);
                layout_externaldevice.setVisibility(View.VISIBLE);
                layout_analytics.setVisibility(View.GONE);
                layout_reeplaceitem.setVisibility(View.GONE);
                layout_paymentHistory.setVisibility(View.GONE);
                layout_foodguide.setVisibility(View.GONE);
                layout_wellnesslibrary.setVisibility(View.GONE);
                layout_eshoping.setVisibility(View.GONE);
                layout_foodtripe.setVisibility(View.GONE);

                btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_externaldevices.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        btn_analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {
                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        Intent intent = new Intent(context, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {


                        layout_pathologist.setVisibility(View.GONE);
                        layout_schedulept.setVisibility(View.GONE);
                        layout_testimonials.setVisibility(View.GONE);
                        layout_externaldevice.setVisibility(View.GONE);
                        layout_analytics.setVisibility(View.VISIBLE);
                        layout_reeplaceitem.setVisibility(View.GONE);
                        layout_paymentHistory.setVisibility(View.GONE);
                        layout_foodguide.setVisibility(View.GONE);
                        layout_wellnesslibrary.setVisibility(View.GONE);
                        layout_eshoping.setVisibility(View.GONE);
                        layout_foodtripe.setVisibility(View.GONE);

                        btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_analytics.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                        btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                        btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_analytics.setTextColor(getResources().getColor(R.color.white_recipe));
                        btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));

                    }
                }


            }
        });


        btn_reeplaceitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {


                    layout_pathologist.setVisibility(View.GONE);
                    layout_schedulept.setVisibility(View.GONE);
                    layout_testimonials.setVisibility(View.GONE);
                    layout_externaldevice.setVisibility(View.GONE);
                    layout_analytics.setVisibility(View.GONE);
                    layout_reeplaceitem.setVisibility(View.VISIBLE);
                    layout_paymentHistory.setVisibility(View.GONE);
                    layout_foodguide.setVisibility(View.GONE);
                    layout_wellnesslibrary.setVisibility(View.GONE);
                    layout_eshoping.setVisibility(View.GONE);
                    layout_foodtripe.setVisibility(View.GONE);

                    btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                    btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                    btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                    btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_reeplaceitems.setTextColor(getResources().getColor(R.color.white_recipe));
                    btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                    btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));

                }


            }
        });


        btn_paymenthistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pathologist.setVisibility(View.GONE);
                layout_schedulept.setVisibility(View.GONE);
                layout_testimonials.setVisibility(View.GONE);
                layout_externaldevice.setVisibility(View.GONE);
                layout_analytics.setVisibility(View.GONE);
                layout_reeplaceitem.setVisibility(View.GONE);
                layout_paymentHistory.setVisibility(View.VISIBLE);
                layout_foodguide.setVisibility(View.GONE);
                layout_wellnesslibrary.setVisibility(View.GONE);
                layout_eshoping.setVisibility(View.GONE);
                layout_foodtripe.setVisibility(View.GONE);
                btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_paymenthistory.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        btn_eshoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pathologist.setVisibility(View.GONE);
                layout_schedulept.setVisibility(View.GONE);
                layout_testimonials.setVisibility(View.GONE);
                layout_externaldevice.setVisibility(View.GONE);
                layout_analytics.setVisibility(View.GONE);
                layout_reeplaceitem.setVisibility(View.GONE);
                layout_paymentHistory.setVisibility(View.GONE);
                layout_foodguide.setVisibility(View.GONE);
                layout_eshoping.setVisibility(View.VISIBLE);
                layout_wellnesslibrary.setVisibility(View.GONE);
                layout_foodtripe.setVisibility(View.GONE);

                btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_eshoping.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_wellnesslibrary.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_eshoping.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_wellnesslibrary.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        btn_wellnesslibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pathologist.setVisibility(View.GONE);
                layout_schedulept.setVisibility(View.GONE);
                layout_testimonials.setVisibility(View.GONE);
                layout_externaldevice.setVisibility(View.GONE);
                layout_analytics.setVisibility(View.GONE);
                layout_reeplaceitem.setVisibility(View.GONE);
                layout_paymentHistory.setVisibility(View.GONE);
                layout_foodguide.setVisibility(View.GONE);
                layout_eshoping.setVisibility(View.GONE);
                layout_wellnesslibrary.setVisibility(View.VISIBLE);
                layout_foodtripe.setVisibility(View.GONE);

                callSpiritualTypeApi();

                btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_eshoping.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_wellnesslibrary.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_eshoping.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_wellnesslibrary.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });



        btn_foodtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_pathologist.setVisibility(View.GONE);
                layout_schedulept.setVisibility(View.GONE);
                layout_testimonials.setVisibility(View.GONE);
                layout_externaldevice.setVisibility(View.GONE);
                layout_analytics.setVisibility(View.GONE);
                layout_reeplaceitem.setVisibility(View.GONE);
                layout_paymentHistory.setVisibility(View.GONE);
                layout_foodguide.setVisibility(View.GONE);
                layout_eshoping.setVisibility(View.GONE);
                layout_wellnesslibrary.setVisibility(View.GONE);
                layout_foodtripe.setVisibility(View.VISIBLE);

                getAllTodaysMeal();

                btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodguide.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_eshoping.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_wellnesslibrary.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodguide.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_eshoping.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_wellnesslibrary.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodtrip.setTextColor(getResources().getColor(R.color.white_recipe));
            }
        });






        btn_foodguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Please wait it will be take more times for loading food guide data.....", Toast.LENGTH_LONG).show();

                layout_pathologist.setVisibility(View.GONE);
                layout_schedulept.setVisibility(View.GONE);
                layout_testimonials.setVisibility(View.GONE);
                layout_externaldevice.setVisibility(View.GONE);
                layout_analytics.setVisibility(View.GONE);
                layout_reeplaceitem.setVisibility(View.GONE);
                layout_paymentHistory.setVisibility(View.GONE);
                layout_foodguide.setVisibility(View.VISIBLE);
                layout_wellnesslibrary.setVisibility(View.GONE);
                layout_eshoping.setVisibility(View.GONE);
                layout_foodtripe.setVisibility(View.GONE);

                btn_pathologist.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_schedulepathologytest.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_testimonials.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_externaldevices.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_analytics.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeplaceitems.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_paymenthistory.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_foodguide.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_foodtrip.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_pathologist.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_schedulepathologytest.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_testimonials.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_externaldevices.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_analytics.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeplaceitems.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_paymenthistory.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_foodguide.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_foodtrip.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        EditText edt_food_search=findViewById(R.id.edt_food_search);
        edt_food_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (lGuidelistAdapter!=null){
                    lGuidelistAdapter.getFilter().filter(s);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btn_greenfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                layout_redfood.setVisibility(View.GONE);
                layout_orangefood.setVisibility(View.GONE);
                layout_greenfood.setVisibility(View.VISIBLE);

                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_redfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.green_recipe));


                btn_orangefood.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_redfood.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_greenfood.setTextColor(getResources().getColor(R.color.white_recipe));


                arylst_foodguide_selected=new ArrayList<>();
                for (int i = 0; i <food_guide_list.size() ; i++) {

                    if (food_guide_list.get(i).getColorCode()!=null&&food_guide_list.get(i).getColorCode().equalsIgnoreCase("3")){
                        arylst_foodguide_selected.add(food_guide_list.get(i));
                    }

                }
                recyler_food_guide.setAdapter(lGuidelistAdapter=new FoodGuidelistAdapter(FoodGuideActivity.this
                        ,arylst_foodguide_selected,"green"));



            }
        });

        btn_orangefood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_redfood.setVisibility(View.GONE);
                layout_orangefood.setVisibility(View.GONE);
                layout_greenfood.setVisibility(View.GONE);

                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_redfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));


                btn_orangefood.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_redfood.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_greenfood.setTextColor(getResources().getColor(R.color.black_recipe));
                arylst_foodguide_selected=new ArrayList<>();
                for (int i = 0; i <food_guide_list.size() ; i++) {

                    if (food_guide_list.get(i).getColorCode()!=null&&food_guide_list.get(i).getColorCode().equalsIgnoreCase("2")){
                        arylst_foodguide_selected.add(food_guide_list.get(i));
                    }

                }
                recyler_food_guide.setAdapter(lGuidelistAdapter=new FoodGuidelistAdapter(FoodGuideActivity.this
                        ,arylst_foodguide_selected,"orange"));


            }
        });


        btn_redfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_redfood.setVisibility(View.GONE);
                layout_orangefood.setVisibility(View.GONE);
                layout_greenfood.setVisibility(View.GONE);

                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_redfood.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));


                btn_orangefood.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_redfood.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_greenfood.setTextColor(getResources().getColor(R.color.black_recipe));
                arylst_foodguide_selected=new ArrayList<>();

                for (int i = 0; i <food_guide_list.size() ; i++) {

                    if (food_guide_list.get(i).getColorCode()!=null&&food_guide_list.get(i).getColorCode().equalsIgnoreCase("1")){
                        arylst_foodguide_selected.add(food_guide_list.get(i));
                    }

                }
                recyler_food_guide.setAdapter(lGuidelistAdapter=new FoodGuidelistAdapter(FoodGuideActivity.this
                        ,arylst_foodguide_selected,"red"));

            }
        });

        txt_greenall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_greenallfood.setVisibility(View.GONE);
                layout_greenvegetablefood.setVisibility(View.GONE);
                layout_greenfruitsfood.setVisibility(View.GONE);
                layout_greennutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_greenfruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenall.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_greenvegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        txt_greenvegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_greenallfood.setVisibility(View.GONE);
                layout_greenvegetablefood.setVisibility(View.GONE);
                layout_greenfruitsfood.setVisibility(View.GONE);
                layout_greennutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_greenfruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenall.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenvegetable.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_greenNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });

        txt_greenfruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_greenallfood.setVisibility(View.GONE);
                layout_greenvegetablefood.setVisibility(View.GONE);
                layout_greenfruitsfood.setVisibility(View.GONE);
                layout_greennutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_greenfruits.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_greenall.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenvegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });

        txt_greenNutsseeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_greenallfood.setVisibility(View.GONE);
                layout_greenvegetablefood.setVisibility(View.GONE);
                layout_greenfruitsfood.setVisibility(View.GONE);
                layout_greennutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_greenfruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenall.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenvegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_greenNutsseeds.setTextColor(getResources().getColor(R.color.green_recipe));
            }
        });


        txt_orangeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_orangeallfood.setVisibility(View.GONE);
                layout_orangevegetablefood.setVisibility(View.GONE);
                layout_orangefruitsfood.setVisibility(View.GONE);
                layout_orangenutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_orangefruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangevegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangeNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangeall.setTextColor(getResources().getColor(R.color.green_recipe));
            }
        });


        txt_orangevegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_orangeallfood.setVisibility(View.GONE);
                layout_orangevegetablefood.setVisibility(View.GONE);
                layout_orangefruitsfood.setVisibility(View.GONE);
                layout_orangenutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_orangefruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangevegetable.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_orangeNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangeall.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        txt_orangefruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_orangeallfood.setVisibility(View.GONE);
                layout_orangevegetablefood.setVisibility(View.GONE);
                layout_orangefruitsfood.setVisibility(View.GONE);
                layout_orangenutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_orangefruits.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_orangevegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangeNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangeall.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });

        txt_orangeNutsseeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_orangeallfood.setVisibility(View.GONE);
                layout_orangevegetablefood.setVisibility(View.GONE);
                layout_orangefruitsfood.setVisibility(View.GONE);
                layout_orangenutsfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));



                txt_orangefruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangevegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_orangeNutsseeds.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_orangeall.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });



        txt_redall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_rednutsfood.setVisibility(View.GONE);
                layout_redfruitsfood.setVisibility(View.GONE);
                layout_redvegetablefood.setVisibility(View.GONE);
                layout_redallfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));


                txt_redall.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_redvegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redfruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        txt_redvegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_rednutsfood.setVisibility(View.GONE);
                layout_redfruitsfood.setVisibility(View.GONE);
                layout_redvegetablefood.setVisibility(View.GONE);
                layout_redallfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));


                txt_redall.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redvegetable.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_redfruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        txt_redfruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_rednutsfood.setVisibility(View.GONE);
                layout_redfruitsfood.setVisibility(View.GONE);
                layout_redvegetablefood.setVisibility(View.GONE);
                layout_redallfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));


                txt_redall.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redvegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redfruits.setTextColor(getResources().getColor(R.color.green_recipe));
                txt_redNutsseeds.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });


        txt_redNutsseeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_rednutsfood.setVisibility(View.GONE);
                layout_redfruitsfood.setVisibility(View.GONE);
                layout_redvegetablefood.setVisibility(View.GONE);
                layout_redallfood.setVisibility(View.GONE);

//                btn_orangefood.setBackgroundColor(getResources().getColor(R.color.white_recipe));
//                txt_greenall.setBackgroundColor(getResources().getColor(R.color.green_recipe));
//                btn_greenfood.setBackgroundColor(getResources().getColor(R.color.white_recipe));


                txt_redall.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redvegetable.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redfruits.setTextColor(getResources().getColor(R.color.black_recipe));
                txt_redNutsseeds.setTextColor(getResources().getColor(R.color.green_recipe));
            }
        });






        //Payment History

        recylerview_payment = findViewById(R.id.recylerview_payment);
        createPayment();


        //Reeplace item

        ll_cheat_header = findViewById(R.id.ll_cheat_header);
        spinner_occasion = findViewById(R.id.spinner_occasion);
        spinner_category = findViewById(R.id.spinner_category);
        recyler_plansData = findViewById(R.id.recyler_plansData);
        search_chaet = findViewById(R.id.search_chaet);

        AutoCompleteTextView search_text = (AutoCompleteTextView) search_chaet.findViewById(search_chaet.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        search_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_small));

        txt_no_cheaet = findViewById(R.id.txt_no_cheaet);

        search_chaet.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        search_chaet.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        search_chaet.setOnCloseListener((SearchView.OnCloseListener) this);
        utilsreeplace = new Utils();
        mContext = FoodGuideActivity.this;
        sessionManagerreeplace = new SessionManager(mContext);

        spinner_occasion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Str_occaasation = parent.getItemAtPosition(position).toString().trim();

                ary_plan = new ArrayList<>();
                if (listResponse != null) {
                    for (int i = 0; i < listResponse.getData().size(); i++) {

                        if (Str_occaasation.equalsIgnoreCase(listResponse.getData().get(i).getOccasion())) {


                            for (int j = 0; j < listResponse.getData().get(i).getCheatPlans().size(); j++) {

                                if (str_category.trim().equalsIgnoreCase(listResponse.getData().get(i).getCheatPlans().get(j).getCategory())) {
                                    ary_plan.addAll(listResponse.getData().get(i).getCheatPlans().get(j).getPlans());
                                }

                            }


                        }
                    }


                    if (!ary_plan.isEmpty()) {
                        txt_no_cheaet.setVisibility(View.GONE);
                        recyler_plansData.setVisibility(View.VISIBLE);
                        myCheatPlanAdapter = new MyCheatPlanAdapter(FoodGuideActivity.this, ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString());
                        recyler_plansData.setAdapter(myCheatPlanAdapter);

                    } else {
                        txt_no_cheaet.setVisibility(View.VISIBLE);
                        recyler_plansData.setVisibility(View.GONE);
//                         Toast.makeText(mContext, "No data available", Toast.LENGTH_SHORT).show();
                        recyler_plansData.setAdapter(new MyCheatPlanAdapter(FoodGuideActivity.this, ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString()));


                    }


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_category = parent.getItemAtPosition(position).toString();


                ary_plan = new ArrayList<>();

                if (listResponse != null) {
                    for (int i = 0; i < listResponse.getData().size(); i++) {

                        if (Str_occaasation.equalsIgnoreCase(listResponse.getData().get(i).getOccasion())) {


                            for (int j = 0; j < listResponse.getData().get(i).getCheatPlans().size(); j++) {

                                if (str_category.trim().equalsIgnoreCase(listResponse.getData().get(i).getCheatPlans().get(j).getCategory())) {
                                    ary_plan.addAll(listResponse.getData().get(i).getCheatPlans().get(j).getPlans());
                                }

                            }


                        }
                    }

                    if (!ary_plan.isEmpty()) {
                        txt_no_cheaet.setVisibility(View.GONE);
                        recyler_plansData.setVisibility(View.VISIBLE);
                        myCheatPlanAdapter = new MyCheatPlanAdapter(FoodGuideActivity.this, ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString());
                        recyler_plansData.setAdapter(myCheatPlanAdapter);
                    } else {
                        txt_no_cheaet.setVisibility(View.VISIBLE);
                        recyler_plansData.setVisibility(View.GONE);
                        recyler_plansData.setAdapter(new MyCheatPlanAdapter(FoodGuideActivity.this, ary_plan, spinner_occasion.getSelectedItem().toString(), spinner_category.getSelectedItem().toString()));


                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userId = sessionManagerreeplace.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
//        getOccastionMaster();
        getNewCheatMaster();


        //Analytics

        mContext = FoodGuideActivity.this;
        rlReeevaluate = findViewById(R.id.rlReeevaluate);
        sessionManagerana = new SessionManager(mContext);
        service = Client.getClient().create(HomeFragmentService.class);
        utilsana = new Utils();
        TextView txt_CaloriesConsumption = findViewById(R.id.txt_CaloriesConsumption);
        txt_CaloriesConsumption.setSelected(true);
        TextView txt_trend_water = findViewById(R.id.txt_trend_water);
        txt_trend_water.setSelected(true);
        mSubscription = sessionManagerana.getStringValue(SessionManager.KEY_USER_SUBSCRIPTION_LIST);
        userIdana = sessionManagerana.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManagerana.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        TextView txtrescore = findViewById(R.id.txtrescore);
        txtrescore.setSelected(true);

        try {
            JSONArray obj = new JSONArray(mSubscription);
            subscription_List = new ArrayList<>();
            if (obj != null)
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject object = obj.getJSONObject(i);
                    String name = object.getString("FeatureName");
                    subscription_List.add(name);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Analytics

//        initana();
        findViewsana();
        if (Utils.isNetworkAvailable(mContext)) {
            if (recoachId == 0) {
                CallToFetchRecoachId(false);
            } else {
                CallToGetInitialData(false);
            }
        } else
            Toast.makeText(mContext, "No internet !", Toast.LENGTH_LONG).show();


        //Testimonials
        util = new Utils();
//        context= TestimalActivity.this;
        reeTestService = Client.getClient().create(ReeTestService.class);


        sessionManagertest = new SessionManager(FoodGuideActivity.this);

        userID = sessionManagertest.getIntValue(SessionManager.KEY_USER_ID);
        addTestimals();

        fab_add_testimals = findViewById(R.id.fab_add_testimals);
        fab_add_testimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getMytestominals();

                if (sessionManagertest.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                    return;
                }

                addTestominal();

            }
        });

        recler_testimals = findViewById(R.id.recler_testimals);


//Schedule Pathology Test
        initspt();
//        setToolBarspt();
        findViewsspt();


//Pathology
        context = FoodGuideActivity.this;
        recyler_reecoach_add_info = findViewById(R.id.recyler_reecoach_add_info);
        init();
        setToolBar();
        findViews();
        initAppointment();
        findViewsAppoimtent();
        TextView txt_path_plam_my = findViewById(R.id.txt_path_plam_my);
        txt_path_plam_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodGuideActivity.this, ClsMyPathoPlanActivity.class));

            }
        });
        TextView rd_button_patho_plan = findViewById(R.id.rd_button_patho_plan);
        rd_button_patho_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodGuideActivity.this, ClsMyPathoPlanActivity.class));

            }
        });

        btn_medicine=findViewById(R.id.btn_medicine);
        btn_food=findViewById(R.id.btn_food);
        btn_restaurant=findViewById(R.id.btn_restaurant);

        layout_medicine=findViewById(R.id.layout_medicine);
        layout_food=findViewById(R.id.layout_food);
        layout_restaurant=findViewById(R.id.layout_restaurant);


        btn_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.VISIBLE);
                layout_food.setVisibility(View.GONE);
                layout_restaurant.setVisibility(View.GONE);

//                txt_nut.setBackground(getResources().getDrawable(R.drawable.round_recipe_button));
//                txt_ing.setBackground(getResources().getDrawable(R.drawable.round_recipe_button_white));
//                txt_rec.setBackground(getResources().getDrawable(R.drawable.round_recipe_button_white));

                btn_medicine.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_food.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_restaurant.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_food.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_restaurant.setTextColor(getResources().getColor(R.color.black_recipe));
            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.GONE);
                layout_food.setVisibility(View.VISIBLE);
                layout_restaurant.setVisibility(View.GONE);

                btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_food.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_restaurant.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_food.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_restaurant.setTextColor(getResources().getColor(R.color.black_recipe));

            }
        });

        btn_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.GONE);
                layout_food.setVisibility(View.GONE);
                layout_restaurant.setVisibility(View.VISIBLE);


                btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_food.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_restaurant.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_food.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_restaurant.setTextColor(getResources().getColor(R.color.white_recipe));

            }
        });



        recylcer_spiritual_category=findViewById(R.id.recylcer_spiritual_category);
        recylcer_spiritual_list=findViewById(R.id.recylcer_spiritual_list);
        txt_no_data_spiritual=findViewById(R.id.txt_no_data_spiritual);


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


        callFoodGuideApi();


        //Food Trip

        mFoodList1 = new ArrayList<>();
        mSubFilterList1 = new ArrayList<>();
        mSnippingList1 = new ArrayList<>();

         relfood=findViewById(R.id.relfood);
        layot_foodhistory=findViewById(R.id.layot_foodhistory);
        txt_rbFoodSnapping=findViewById(R.id.txt_rbFoodSnapping);
        food1=findViewById(R.id.food1);
        txt_rbFoodSnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relfood.setVisibility(View.GONE);
//                txt_rbFoodSnapping.setBackgroundColor(getResources().getColor(R.color.black));
                txt_rbFoodSnapping.setBackground(getResources().getDrawable(R.drawable.border_new_rightblack));
                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.white));
//                food1.setBackground(getResources().getDrawable(R.color.white));
//                food1.setTextColor(getResources().getColor(R.color.black));
                food1.setBackground(getResources().getDrawable(R.drawable.border_new_rightline));
                food1.setTextColor(getResources().getColor(R.color.shadegray));
                fabAdd.setVisibility(View.VISIBLE);
                rvFoodSnipping.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_daily.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));

                callToGetAllSnippingImages();
            }
        });

        recyclerView_All_Meal_Weekly = findViewById(R.id.recyclerView_All_Meal_Weekly);

        curentDateTime= new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault()).format(new Date());
        strSubmitDateTime.append(new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault()).format(new Date()).toString());
        rbFood = findViewById(R.id.rbFood);
        fabAdd = findViewById(R.id.fab);
        rvAllRecipe = findViewById(R.id.rvRecipies);
        rvFoodSnipping = findViewById(R.id.rvFoodSnipping);
        recyclerViewTodays = findViewById(R.id.recyclerView_All_Meal_TodaysMeal);
        layout_daily = findViewById(R.id.layout_daily);
        layout_foodrec = findViewById(R.id.layout_foodrec);
        txt_nodata = findViewById(R.id.txt_nodata);
        txt_nodata1 = findViewById(R.id.txt_nodata1);
        imgFilter = findViewById(R.id.imgFilter);
        searchRecipe = findViewById(R.id.searchRecipe);
        searchRecipe.setOnClickListener(this);
        searchRecipe.setOnQueryTextListener(this);
        showDatePickerDailog();
        showTimePickerDialog();
//        this.context = context;

        searchRecipe.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchRecipe.setOnSearchClickListener(this);
        searchRecipe.setOnCloseListener((SearchView.OnCloseListener) this);
        searchRecipe.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand) {
                    visibleView();
                    isExpand = true;
                }
                else{
                    hideView();
                    isExpand = false;
                }
            }
        });
        rbFoodSnapping = findViewById(R.id.rbFoodSnapping);
        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                relfood.setVisibility(View.VISIBLE);
//                txt_rbFoodSnapping.setBackgroundColor(getResources().getColor(R.color.white));
                txt_rbFoodSnapping.setBackground(getResources().getDrawable(R.drawable.border_new_line));
                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.shadegray));
//                food1.setBackgroundColor(getResources().getColor(R.color.black));
                food1.setBackground(getResources().getDrawable(R.drawable.border_new_black));
                food1.setTextColor(getResources().getColor(R.color.white));
                fabAdd.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                rvFoodSnipping.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.VISIBLE);
                layout_daily.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);

                rbFood.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));


//                callToGetFoodTripAllRecipies();
                getAllTodaysMeal();

                if(list.size()==0){
                    recyclerViewTodays.setVisibility(View.GONE);
                    txt_nodata.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewTodays.setVisibility(View.VISIBLE);
                    txt_nodata.setVisibility(View.GONE);
                }

            }
        });
        rbFood.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {


                relfood.setVisibility(View.VISIBLE);
//                txt_rbFoodSnapping.setBackgroundColor(getResources().getColor(R.color.white));
//                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.black));
//                food1.setBackgroundColor(getResources().getColor(R.color.black));
//                food1.setTextColor(getResources().getColor(R.color.white));

                txt_rbFoodSnapping.setBackground(getResources().getDrawable(R.drawable.border_new_line));
                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.shadegray));
//                food1.setBackgroundColor(getResources().getColor(R.color.black));
                food1.setBackground(getResources().getDrawable(R.drawable.border_new_black));
                food1.setTextColor(getResources().getColor(R.color.white));
                fabAdd.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                rvFoodSnipping.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.VISIBLE);
                layout_daily.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);

                rbFood.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));


//                callToGetFoodTripAllRecipies();
//                getAllTodaysMeal();

                if(list.size()==0){
                    recyclerViewTodays.setVisibility(View.GONE);
                    txt_nodata.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewTodays.setVisibility(View.VISIBLE);
                    txt_nodata.setVisibility(View.GONE);
                }


            }
        });


        rbFoodSnapping.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.VISIBLE);
                rvFoodSnipping.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_daily.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));

                callToGetAllSnippingImages();
            }
        });

        rbFoodreplace = findViewById(R.id.rbFoodreplace);
        rbHistory = findViewById(R.id.rbHistory);


        rbFoodreplace.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.GONE);
                rbFoodreplace.setVisibility(View.VISIBLE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);
                rvFoodSnipping.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_foodrec.setVisibility(View.VISIBLE);
                layout_daily.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));

//                callToGetAllSnippingImages();
            }
        });

        rbHistory.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.GONE);
                rbHistory.setVisibility(View.VISIBLE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_foodrec.setVisibility(View.GONE);
                layout_daily.setVisibility(View.GONE);
                rvFoodSnipping.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.VISIBLE);
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));


//                callFoodHistoryData();
//                callToGetAllSnippingImages();
            }
        });





        rd_button_veg=findViewById(R.id.rd_button_veg);
        rd_button_nonveg=findViewById(R.id.rd_button_nonveg);
        norecipe=findViewById(R.id.norecipe);

//        callToGetFoodTripAllRecipies();





        imgFilter.setOnClickListener(this);
        fabAdd.setOnClickListener(this);



        mFoodSnippingAdapter = new FoodSnippingAdapter(mContext, mSnippingList1);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvFoodSnipping.setLayoutManager(gridLayoutManager);
        rvFoodSnipping.setItemAnimator(new DefaultItemAnimator());
        rvFoodSnipping.setAdapter(mFoodSnippingAdapter);



















        btn_foodguide.performClick();


    }




    //Food Trip



    private void showTimePickerDialog() {

        timepickerdialog = new TimePickerDialog(mContext, this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE) ,
                false);
//        datepickerdialog.show();
    }
    private void showDatePickerDailog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(mContext, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);





        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());


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



     public void callFoodHistoryData() {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date newDate = calendar.getTime();
        Date date1=null;
        String sDate1=sessionManager.getStringValue("statusdate");
        try {
            date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date weekAgoDate = getDateWithOffset(-7, date1);
//        getCalculatedDate("01-01-2015", "dd-MM-yyyy", -10);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(weekAgoDate);
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ClsFoodHistoryRequest clsFoodHistoryRequest=new ClsFoodHistoryRequest();

        clsFoodHistoryRequest.setFromDate(submitFromDate);
        clsFoodHistoryRequest.setToDate(sessionManager.getStringValue("statusdate"));
        clsFoodHistoryRequest.setReeworkerId(String.valueOf(userId));
        FoodService   commonService = Client.getClient().create(FoodService.class);


        Call<ClsFoodHistory> call = commonService.FoodHistory(clsFoodHistoryRequest);
        call.enqueue(new Callback<ClsFoodHistory>()
        {
            @Override
            public void onResponse(Call<ClsFoodHistory> call, Response<ClsFoodHistory> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){
                    try {
                        ClsFoodHistory moodResponse = response.body();
                        if (moodResponse != null ) {
                            moodResponse.getData();
                            listFood = new ArrayList<FoodData>();
                            ArrayList<FoodData> data = response.body().getData();
//                         ArrayList<FoodHistoryData> data1=new ArrayList<FoodHistoryData>();
                            for (int i = 0; i < data.size(); i++)
                            {
                                FoodData datum = new FoodData();
                                datum.setCreatedOn(data.get(i).getCreatedOn());
                                datum.setTotalCaloriesIntake(data.get(i).getTotalCaloriesIntake());
                                datum.setTotalCarbsIntake(data.get(i).getTotalCarbsIntake());
                                datum.setTotalFatIntake(data.get(i).getTotalFatIntake());
                                datum.setTotalFibreIntake(data.get(i).getTotalFibreIntake());
                                datum.setTotalProteinIntake(data.get(i).getTotalProteinIntake());
                                datum.setMealIntakeByMealType(data.get(i).getMealIntakeByMealType());
//                                FoodHistoryData datum1 = new FoodHistoryData();
//                                datum.setData(data.get(i).getData());
//                                datum.set(data.get(i).getTotalCaloriesIntake());
//                                data1 = data.get(i).getData();
//                                listFood1.add(data.get(i).getData());
//                                listFood2.add(data.get(i).getData()));
                                listFood.add(datum);

                            }
                            listFood1.clear();
                            listFood2.clear();
                            listFood3.clear();
                            listFood4.clear();
                            for (int i = 0; i < listFood.size(); i++)
                            {
//                                FoodData datum = new FoodData();
//                                datum.setData(listFood.get(i).getData());
//                                datum.set(data.get(i).getTotalCaloriesIntake());

                                listFood1.addAll(listFood.get(i).getMealIntakeByMealType());

                            }

                            for (int i = 0; i < listFood1.size(); i++)
                            {

                                FoodHistoryData datum = new FoodHistoryData();
                                datum.setMeals(listFood1.get(i).getMeals());

                                listFood3.add(datum);



                            }


                            for (int i = 0; i < listFood3.size(); i++)
                            {
//                                FoodData datum = new FoodData();
//                                datum.setData(listFood.get(i).getData());
//                                datum.set(data.get(i).getTotalCaloriesIntake());

                                listFood4.addAll(listFood3.get(i).getMeals());

                            }



                            UserFoodTripMealAdapterNew adapter = new UserFoodTripMealAdapterNew(mContext,listFood4);
                            recyclerView_All_Meal_Weekly.setLayoutManager(new LinearLayoutManager(mContext));
                            recyclerView_All_Meal_Weekly.setAdapter(adapter);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsFoodHistory> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });






    }


    public static Date getDateWithOffset(int offset, Date date){
        Calendar calendar = calendar = Calendar.getInstance();;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar.getTime();
    }



    private void getAllTodaysMeal()

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }
        BcaRequest request = new BcaRequest();
        request.setUserid(userId);
        request.setMeal_cal_max(mParam3);
        request.setCreatedOn(sessionManager.getStringValue("statusdate"));
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
                    list = new ArrayList<>();
                    TodaysMealData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<TodaysMealData.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {

                            try{

                                double totalAllFoodCal=0.0;


                                for (int i = 0; i < data.size(); i++)
                                {
                                    //  JSONObject mainMeal = dataArray.getJSONObject(i);

                                    // HEADER Data

                                    totalAllFoodCal=totalAllFoodCal+data.get(i).getMealCalMin();
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





                                    Collections.sort( data.get(i).getLstSubMealData(), new Comparator<TodaysMealData.LstSubMealDatum>() {

                                        @Override
                                        public int compare(TodaysMealData.LstSubMealDatum o1, TodaysMealData.LstSubMealDatum o2) {
                                            try {
                                                if (o1.getIntakeTime()!=null&&o2.getIntakeTime()!=null){
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
                                        if(data.get(i).getLstSubMealData().get(j).getIntakeTime()!=null){
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

                                        double singlecal=data.get(i).getLstSubMealData().get(j).getMealCalory();


                                        DecimalFormat decimalFormat=new DecimalFormat("0.00");
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


//                                txt_acual_total_cal_consumed.setText(" Total consumed for the day : "+showschedulestr+" kcal");

                                adapter1 = new UserFoodTripAdapterNew(mContext,list);
                                recyclerViewTodays.setLayoutManager(new LinearLayoutManager(mContext));
                                recyclerViewTodays.setAdapter(adapter1);

                                if(list.size()==0){
                                    recyclerViewTodays.setVisibility(View.GONE);
                                    txt_nodata.setVisibility(View.VISIBLE);
                                }else{
                                    recyclerViewTodays.setVisibility(View.VISIBLE);
                                    txt_nodata.setVisibility(View.GONE);
                                }



                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                    else
                    {


                        list.clear();
                        List<TodaysMealData.Datum> datas=new ArrayList<>();
                        adapter1 = new UserFoodTripAdapterNew(mContext,list);
                        recyclerViewTodays.setLayoutManager(new LinearLayoutManager(mContext));
                        recyclerViewTodays.setAdapter(adapter1);



//                        if (getUserVisibleHint()){
//
//                            if (!sessionManager.getStringValue("IsShowMSg").equalsIgnoreCase("false")){
////                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//
//
//                        }
                    }
                }

                sessionManager.setStringValue("IsShowMSg","true");

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




    private void newDailog() {

        if (Utils.isNetworkAvailable(mContext))
        {

            final Dialog dialog=new Dialog(mContext,R.style.CustomDialog);

            dialog.setContentView(R.layout.dialog_datetime_image);

            Button btn_camera=dialog.findViewById(R.id.btn_camera);
            Button btn_gallery=dialog.findViewById(R.id.btn_gallery);
            txt_date_time=dialog.findViewById(R.id.txt_date_time);
            txt_date_time.setText(curentDateTime);
            ImageView img_close_upload=dialog.findViewById(R.id.img_close_upload);
            img_close_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            LinearLayout ll_upload_photo_date=dialog.findViewById(R.id.ll_upload_photo_date);
            btn_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dexter.withActivity(FoodGuideActivity.this)
                            .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera

                                    dialog.dismiss();
                                    fileChooser();                                    }

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

                }
            });
            btn_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    Dexter.withActivity(FoodGuideActivity.this)
                            .withPermission(Manifest.permission.CAMERA)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera
                                    dialog.dismiss();
                                    CallCameraIntent();
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


                }
            });


            ll_upload_photo_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datepickerdialog.show();
                }
            });


            dialog.show();



//                    openContextMenu(ivBefore);
        }
        else
        {
            showRetryBar(getString(R.string.internet_connection_unavailable));
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Camera", "Gallery"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Image Picker");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {

                    // Do some stuff

                    Dexter.withActivity(FoodGuideActivity.this)
                            .withPermission(Manifest.permission.CAMERA)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera

                                    CallCameraIntent();
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

                } else if (items[item].equals("Gallery")) {
                    Dexter.withActivity(FoodGuideActivity.this)
                            .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera

                                    dialog.dismiss();
                                    fileChooser();                                    }

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

                }
            }
        });
        builder.show();
    }



//    private void showSettingsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("Need Permissions");
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
//        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                openSettings();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//
//
//
//
//
//    }

//    private void openSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 101);
//    }





//    public void CallCameraIntent()
//    {
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
//        mCapturedImageURI = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//    }

//    public void fileChooser()
//    {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
//    }





    //Declared in FoodtripAdapter
    @Override
    public void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model)
    {
        if (model != null)
        {
            int recipeId = model.getRecipeId();
            int editId = model.getEditId();

            Intent intent = new Intent(mContext, FoodRecipeActivity.class);
            intent.putExtra("RECEIPE_ID", recipeId);
            intent.putExtra("EDIT_ID",editId);
            intent.putExtra("RECEIPE_Image",model.getRecipeImagePath());
            startActivity(intent);
        }
    }

    //Declared in FoodtripAdapter
    @Override
    public void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model)
    {
//        if (Utils.isNetworkAvailable(mContext))
//            callToUpdateFavoriteStatus(model);
//        else
//            showRetryBar("Check internet connection!");
    }



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        int dataSize=0;
//
//        if (requestCode == FILE_SELECT_REQUEST_CODE)
//        {
//            if (resultCode == RESULT_OK)
//            {
//
//
//                Uri selectedImage = data.getData();
//
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = mContext.getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String picturePath = cursor.getString(columnIndex);
//                cursor.close();
//
//                uploadFile(new File(picturePath), userId);
//
//                isImage = true;
//            }
//        }
//        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
//        {
//            if (resultCode == RESULT_OK)
//            {
//                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userId);
//                isImage = true;
//            }
//        }
//        else if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
//            if (resultCode == RESULT_OK) {
//                boolean res = data.getBooleanExtra("result",false);
//                if (res) {
//                    if (Utils.isNetworkAvailable(mContext)) {
////                        callToGetAllRecipies("intial", 0, "");
//
//                    } else
//                        Toast.makeText(mContext.getApplicationContext(), "R.string.internet_connection_unavailable", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }



//    public String getRealPathFromURI(Uri contentUri)
//    {
//        try
//        {
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//        catch (Exception e) {return contentUri.getPath();}
//    }

    private void uploadFile(File file, int userId)
    {

        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        File fileTemp = null;
        try {
            fileTemp = new Compressor(mContext).compressToFile(file);
          /*  fileTemp  = new Compressor(getActivity())
                    .setMaxWidth(2000)
                    .setMaxHeight(1400)
                    .setQuality(90)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file, file.getName());*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileTemp);

        MultipartBody.Part photo = MultipartBody.Part.createFormData("image", fileTemp.getName(), photoContent);

        BeforeAfterService uploadService = Client.getClientMultiPart().create(BeforeAfterService.class);
        RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), ""+userId);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), fileTemp.getName());

        FoodService foodService = Client.getClientMultiPart().create(FoodService.class);
        RequestBody uplaodtime = RequestBody.create(MediaType.parse("multipart/form-MainModel"), strSubmitDateTime.toString());

        Call<BeforeAfterResponse> call = foodService.Upload(photo, user_Id, filename,uplaodtime);
        call.enqueue(new Callback<BeforeAfterResponse>()
        {

            @Override
            public void onResponse(Call<BeforeAfterResponse> call, Response<BeforeAfterResponse> response)
            {
                //ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> data = null;

                utils.hideProgressbar();

                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        BeforeAfterResponse dataResponse = response.body();

                        if (dataResponse.getCode() == 1)
                        {
                            callToGetAllSnippingImages();
                        }
                        Toast.makeText(mContext.getApplicationContext(), "Uploaded successfully !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(mContext.getApplicationContext(), "Server : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext.getApplicationContext(), "Server : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BeforeAfterResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Toast.makeText(mContext.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void callToGetAllSnippingImages()

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        FoodTripRequest request = new FoodTripRequest();
        request.setUserId(userId);//userId
        Call<FoodSnippingResp> call = foodService.getAllImages(request);
        call.enqueue(new Callback<FoodSnippingResp>()
        {
            @Override
            public void onResponse(Call<FoodSnippingResp> call, Response<FoodSnippingResp> response)
            {
                utils.hideProgressbar();

                List<FoodSnippingResp.Datum> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodSnippingResp listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        tempList = response.body().getData();

                        if (tempList!= null && tempList.size() > 0)
                        {
                            rvAllRecipe.setVisibility(View.GONE);
                            rvFoodSnipping.setVisibility(View.VISIBLE);
                            mSnippingList1.clear();
                            mSnippingList1.addAll(tempList);
                            mFoodSnippingAdapter.notifyDataSetChanged();
                        }else{
                            rvAllRecipe.setVisibility(View.GONE);
                            rvFoodSnipping.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, "Data Not Found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        rvAllRecipe.setVisibility(View.GONE);
                        rvFoodSnipping.setVisibility(View.VISIBLE);
                        if (!listResponse.getMessage().isEmpty()){
                            Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(mContext, "Data Not Found.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodSnippingResp> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }





    private void callToGetFoodTripAllRecipies() {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }


        Call<ClsUSerFoodTripmain> call = foodService.getUserFoodTrip(userId);
        call.enqueue(new Callback<ClsUSerFoodTripmain>()
        {
            @Override

            public void onResponse(Call<ClsUSerFoodTripmain> call, Response<ClsUSerFoodTripmain> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsUSerFoodTripmain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        arylst_userfood_trip = response.body().getData();

                        if (arylst_userfood_trip!= null && arylst_userfood_trip.size() > 0)
                        {
                            rvAllRecipe.setVisibility(View.VISIBLE);
//
//                            user_mFoodTripAdapter = new UserFoodTripAdapterN(mContext, arylst_userfood_trip, FoodGuideActivity.this);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
//                            rvAllRecipe.setLayoutManager(layoutManager);
//                            rvAllRecipe.setItemAnimator(new DefaultItemAnimator());
//                            rvAllRecipe.setAdapter(user_mFoodTripAdapter);
//                            mFoodTripAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        mFoodList1.clear();
//                        mFoodList.addAll(tempList);
                        mFoodTripAdapter.notifyDataSetChanged();
                        rvAllRecipe.setVisibility(View.GONE);
//                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsUSerFoodTripmain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }



    private void callToUpdateFavoriteStatus(int id,boolean b)
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

//        FoodTripFavoriteUpdateRequest request = new FoodTripFavoriteUpdateRequest();
//        request.setUserId(userId);
//
//        if(model.getIsfavourite()!=null){
//            if(model.getIsfavourite()==true){
//                request.setIsfavourite(1);
//            }else{
//                request.setIsfavourite(0);
//            }
//        }
//        request.setEditId(model.getEditId());
//        request.setRecipeId(model.getRecipeId());
//        Gson gson = new Gson();
//        String json = gson.toJson(request);
//        String test = json;

        Call<FoodTripFavoriteUpdateResponse> call = foodService.setuserTripFavoriete(id,b);
        call.enqueue(new Callback<FoodTripFavoriteUpdateResponse>()
        {
            @Override
            public void onResponse(Call<FoodTripFavoriteUpdateResponse> call, Response<FoodTripFavoriteUpdateResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodTripFavoriteUpdateResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


//                        mFoodTripAdapter.notifyDataSetChanged();

                        if (Utils.isNetworkAvailable(mContext)) {



                            callToGetFoodTripAllRecipies();
                        } else{
                            Toast.makeText(mContext.getApplicationContext(), R.string.internet_connection_unavailable, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodTripFavoriteUpdateResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void showRetryBar(String msg)
    {
        String strMessage;
        if (!msg.isEmpty())
        {
            strMessage = msg;
        }
        else
        {
            strMessage = "Unable to load data.";
        }

        Snackbar snackbar = Snackbar.make(this.findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(mContext))
                        {
//                            callToGetAllRecipies("Vegetarian");
                        }
                        else
                        {
                            showRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }

    public void hideView(){
        rbFoodSnapping.setVisibility(View.GONE);
        rbFood.setVisibility(View.GONE);
        rbFoodreplace.setVisibility(View.GONE);
        rbHistory.setVisibility(View.GONE);
    }

    public void visibleView(){
        rbFoodSnapping.setVisibility(View.GONE);
        rbFood.setVisibility(View.VISIBLE);
        rbFoodreplace.setVisibility(View.VISIBLE);
        rbHistory.setVisibility(View.VISIBLE);
    }




//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        hideView();
//        String text = query;
//        if (user_mFoodTripAdapter!=null){
//            user_mFoodTripAdapter.getFilter().filter(query);
//
//        }
//        return false;
//    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//
//
//        hideView();
//        String text = newText;
//        try {
//            user_mFoodTripAdapter.getFilter().filter(newText);
//
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }
//        return false;
//    }

    @Override
    public boolean onClose() {
        visibleView();
        return false;
    }

    @Override
    public void getFavorite(int mealid, boolean status) {

//        http://shamrockuat.dweb.in/api/Recipe/SaveFavoriteMeal?MealId=413&IsFav=true
        if (Utils.isNetworkAvailable(mContext))
            callToUpdateFavoriteStatus(mealid,status);
        else
            showRetryBar("Check internet connection!");

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(layot_foodhistory.getVisibility()==View.GONE) {
            strSubmitDateTime = new StringBuilder();
            stringBuilder_datetime = new StringBuilder();
            String dummydateentry = dayOfMonth + "-" + (month + 1) + "-" + year;

            strSubmitDateTime.append(year + "-" + (month + 1) + "-" + dayOfMonth);
            stringBuilder_datetime.append(dummydateentry);
            timepickerdialog.show();

        }










    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String lsTimeFrom = formatter.format(cal.getTime());
        stringBuilder_datetime.append(" ").append(lsTimeFrom);
        strSubmitDateTime.append(" ").append(lsTimeFrom);
        txt_date_time.setText(stringBuilder_datetime.toString());

    }

    @Override
    public void onClickMeal(int position, TodaysMeal model) {

    }






















    //Wellness Library


    private void callSpiritualTypeApi() {

        commonService = Client.getClient().create(CommonService.class);
        getSpiritaulTypeApi();
    }


    @Override
    public void getVideoLink(String videoLink, String title, String url) {



        Intent intent=new Intent(FoodGuideActivity.this, ExoActivity.class);
        intent.putExtra("VideoID",extractYTId(videoLink));
        intent.putExtra("title",title);
        intent.putExtra("description",url);
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

    private void getSpiritaulTypeApi(){


        utils = new Utils();
        session = new SessionManager(this);
        try {
            utils.showProgressbar(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritaulTypeMain> call = commonService.getSpiritualLibraryMaster();
        call.enqueue(new Callback<ClsSpiritaulTypeMain>()
        {
            @Override
            public void onResponse(Call<ClsSpiritaulTypeMain> call, Response<ClsSpiritaulTypeMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsSpiritaulTypeMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){

                                    ArrayList<SpiritualTypeData> arylst_SpiritualTypeData=moodResponse.getData();

                                    recylcer_spiritual_category.setAdapter(new SpiritualTypeCategoryAdapter(FoodGuideActivity.this,arylst_SpiritualTypeData));

                                    getSpitualListAPiByID(arylst_SpiritualTypeData.get(0).getId(),arylst_SpiritualTypeData.get(0).getLibraryName());

                                }else {
                                    ArrayList<SpiritualTypeData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_category.setAdapter(new SpiritualTypeCategoryAdapter(FoodGuideActivity.this,arylst_SpiritualTypeData));

//                                    getSpitualListAPiByID(arylst_SpiritualTypeData.get(0).getId());
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
            public void onFailure(Call<ClsSpiritaulTypeMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }



    private void getSpitualListAPiByID(int id, final String libraryName){


        utils = new Utils();
        session = new SessionManager(this);
        try {
            utils.showProgressbar(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritualListMain> call = commonService.getSpiritualLibraryVideosById(id);
        call.enqueue(new Callback<ClsSpiritualListMain>()
        {
            @Override
            public void onResponse(Call<ClsSpiritualListMain> call, Response<ClsSpiritualListMain> response)
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

                                    recylcer_spiritual_list.setAdapter(new SpiritualVideoListAdapter(FoodGuideActivity.this,arylst_SpiritualTypeData));


                                }else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName+" not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new SpiritualVideoListAdapter(FoodGuideActivity.this,arylst_SpiritualTypeData));

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
    public void getId(int id, String libraryName) {


        getSpitualListAPiByID(id, libraryName);



    }




    //Payment History

    private void createPayment() {

        final Utils utils = new Utils();
        RegistrationService regService = Client.getClient().create(RegistrationService.class);

        final SessionManager sessionManager = new SessionManager(FoodGuideActivity.this);
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils.showProgressbar(FoodGuideActivity.this);
//        Call<Ar> call = regService.getPaymentHistory(userID);
        Call<List<paymentDetails>> call = regService.getPaymentHistory(userID);

        call.enqueue(new Callback<List<paymentDetails>>() {
            @Override
            public void onResponse(Call<List<paymentDetails>> call, Response<List<paymentDetails>> response) {
                utils.hideProgressbar();
                List<paymentDetails> rs = response.body();
                if (rs != null && !rs.isEmpty()) {
                    recylerview_payment.setAdapter(new PaymentHistoryAdapter(FoodGuideActivity.this, rs));

                }


            }

            @Override
            public void onFailure(Call<List<paymentDetails>> call, Throwable t) {
                utils.hideProgressbar();
                Toast.makeText(FoodGuideActivity.this, "" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();


            }
        });


    }


//Reeplace Item

    private void getUOMData() {
        utilsreeplace.showProgressbar(FoodGuideActivity.this);
        Call<ClsMainPlansClass> call = foodService.getCheatPlan(userId);
        call.enqueue(new Callback<ClsMainPlansClass>() {
            @Override
            public void onResponse(Call<ClsMainPlansClass> call, Response<ClsMainPlansClass> response) {
                utilsreeplace.hideProgressbar();
                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ll_cheat_header.setVisibility(View.VISIBLE);
                    listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        arylst_occasations = new ArrayList<>();
                        arylst_category = new ArrayList<>();

                        for (int i = 0; i < listResponse.getData().size(); i++) {

                            for (int j = 0; j < listResponse.getData().get(i).getCheatPlans().size(); j++) {
                                arylst_category.add(listResponse.getData().get(i).getCheatPlans().get(j).getCategory() + "    ");

                            }
                        }
                        if (!arylst_category.isEmpty()) {
                            str_category = arylst_category.get(0).toString();
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, arylst_category);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_category.setAdapter(adapter1);
                        } else {
                            ArrayList<String> stringArrayList = new ArrayList<>();
                            stringArrayList.add("No category   ");

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, stringArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_category.setAdapter(adapter);
                        }


                    } else {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsMainPlansClass> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utilsreeplace.hideProgressbar();
            }
        });
    }

    private void getOccastionMaster() {
        Call<ClsOccastionMain> call = foodService.getOccasionMaster();
        call.enqueue(new Callback<ClsOccastionMain>() {
            @Override
            public void onResponse(Call<ClsOccastionMain> call, Response<ClsOccastionMain> response) {
                utilsreeplace.hideProgressbar();
                List<String> tempList;
                ClsOccastionMain clsOccastionMain;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    clsOccastionMain = response.body();

                    if (clsOccastionMain != null && clsOccastionMain.getCode().equals("1")) {
                        arylst_occasations = new ArrayList<>();
                        for (int i = 0; i < clsOccastionMain.getData().size(); i++) {
                            arylst_occasations.add(clsOccastionMain.getData().get(i).getOccasion() + "  ");

                        }

                        if (!arylst_occasations.isEmpty()) {
                            Str_occaasation = arylst_occasations.get(0).toString();

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, arylst_occasations);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_occasion.setAdapter(adapter);
                        } else {
                            ArrayList<String> stringArrayList = new ArrayList<>();
                            stringArrayList.add("No Occasion ");

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item_white, stringArrayList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_occasion.setAdapter(adapter);
                        }


                        getUOMData();


                    } else {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsOccastionMain> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utilsreeplace.hideProgressbar();
            }
        });
    }

    private void getNewCheatMaster() {
        Call<CheatPlanDataMain> call = foodService.getNewCheatMaster();
        call.enqueue(new Callback<CheatPlanDataMain>() {
            @Override
            public void onResponse(Call<CheatPlanDataMain> call, Response<CheatPlanDataMain> response) {
                utilsreeplace.hideProgressbar();
                List<String> tempList;
                CheatPlanDataMain clsOccastionMain;
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    clsOccastionMain = response.body();

                    if (clsOccastionMain != null && clsOccastionMain.getCode().equals("1")) {

                        reeplaceItemAdapter = new ReeplaceItemAdapter(FoodGuideActivity.this, clsOccastionMain.getData());

                        RecyclerView recyler_newcheat = findViewById(R.id.recyler_newcheat);
                        recyler_newcheat.setAdapter(reeplaceItemAdapter);

                        getUOMData();


                    } else {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheatPlanDataMain> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utilsreeplace.hideProgressbar();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String occaasation = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

//    @Override
//    public boolean onClose() {
//
//        visibleView();
//
//        return false;
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        reeplaceItemAdapter.getFilter().filter(query);


        hideView();
        String text = query;
        if (user_mFoodTripAdapter!=null){
            user_mFoodTripAdapter.getFilter().filter(query);

        }


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        reeplaceItemAdapter.getFilter().filter(newText);


        hideView();
        String text = newText;
        try {
            user_mFoodTripAdapter.getFilter().filter(newText);

        }catch (Exception e){
            e.printStackTrace();

        }


        return false;
    }

    private void callMyPlanMasters() {
//        utils=new Utils();
//        if (!((Activity) mContext).isFinishing())
//        {
//            utils.showProgressbar(mContext);
//        }
        sessionManagerreeplace = new SessionManager(mContext);
        int userID = sessionManagerreeplace.getIntValue(SessionManager.KEY_USER_ID);

        MyPlansService myPlansService = Client.getClient().create(MyPlansService.class);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<MyPlanMastersResponse> call = myPlansService.getMasterPlans(request);
        call.enqueue(new Callback<MyPlanMastersResponse>() {
            @Override
            public void onResponse(Call<MyPlanMastersResponse> call, Response<MyPlanMastersResponse> response) {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MyPlanMastersResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

                        spinnerCategory = findViewById(R.id.spinner_MyPlan_Category);


                        final ArrayList<MyPlanMastersResponse.MasterData> spinnerList;

                        spinnerList = response.body().getData();

                        MyPlanMastersResponse.MasterData data = new MyPlanMastersResponse.MasterData();
                        data.setPlanName("Today's Plan");
                        data.setID(0);
                        spinnerList.add(0, data);
                        MyPlanMasterAdapter spinnerAdapter;


                        spinnerAdapter = new MyPlanMasterAdapter(mContext, spinnerList);
                        spinnerCategory.setAdapter(spinnerAdapter);


                        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                                if (!iSFirstTime) {
                                    iSFirstTime = true;
                                    return;
                                }
                                if (spinnerList != null) {
                                    String planType = spinnerList.get(i).getPlanName();
                                    int planId = spinnerList.get(i).getID();

                                    if (planType.trim().equalsIgnoreCase("Food Plan")) {
                                        startActivity(new Intent(FoodGuideActivity.this, DietPlanActivity.class));
                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("Lifestyle Plan")) {
                                        Intent intent = new Intent(FoodGuideActivity.this, LifeStylePlanActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);

                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }

                                    if (planType.trim().equalsIgnoreCase("REEplace Items")) {
                                        Intent intent = new Intent(FoodGuideActivity.this, MyCheatPlanActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);


                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }
                                    if (planType.trim().equalsIgnoreCase("Today's Plan")) {
                                        Intent intent = new Intent(FoodGuideActivity.this, MyPlansActivity.class);
                                        intent.putExtra("isFrommyplan", true);

                                        startActivity(intent);


                                        overridePendingTransition(0, 0);
                                        finish();
                                        return;
                                    }


                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        for (int i = 0; i < spinnerList.size(); i++) {
                            if (spinnerList.get(i).getPlanName().equalsIgnoreCase("REEplace Items")) {
                                spinnerCategory.setSelection(i);
                                break;
                            }

                        }

                    } else {
                        Toast.makeText(mContext, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyPlanMastersResponse> call, Throwable t) {
//                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
            }
        });
    }


//Analytics


    private void CallToFetchRecoachId(final boolean isSwipeToRefresh) {
        if (!isSwipeToRefresh)
            utilsana.showProgressbar(mContext);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userIdana);

        Call<GetRecoachByUserResponse> call = service.GetReecoachId(request);
        call.enqueue(new Callback<GetRecoachByUserResponse>() {
            @Override
            public void onResponse(Call<GetRecoachByUserResponse> call, Response<GetRecoachByUserResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    GetRecoachByUserResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (listResponse.getData() != null) {
                            recoachId = listResponse.getData().getReecoachId();
                            sessionManagerana.setIntValue(SessionManager.KEY_USER_REECOACH_ID, recoachId);

                            /*CALL TO GET ALL BASIC DAILY INFO */
                            CallToGetInitialData(isSwipeToRefresh);
                        }
                    } else {
                        //Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        if (!isSwipeToRefresh)
                            utilsana.hideProgressbar();

                    }
                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();

                    if (!isSwipeToRefresh)
                        utilsana.hideProgressbar();


                }
            }

            @Override
            public void onFailure(Call<GetRecoachByUserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utilsana.hideProgressbar();

            }
        });
    }


    /* CALL TO GET INITIAL DATA FROM SERVER */
    private void CallToGetInitialData(final boolean isSwipeToRefresh) {
        HomeFragmentRequest request = new HomeFragmentRequest();
        /*request.setCreatedOn(lsCurrentDate);*/
        request.setReeworkerID(userIdana);
        request.setReecoachID(recoachId);

        Call<HomeFragmentResponse> call = service.getInitialData(request);
        call.enqueue(new Callback<HomeFragmentResponse>() {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, Response<HomeFragmentResponse> response) {
                if (!isSwipeToRefresh)
                    utilsana.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    recoachResonseCode = response.body().getCode();


//                    rlBcaReport.setEnabled(true);
//                    rlCalories.setEnabled(true);
//                    rlMyActivity.setEnabled(true);
//                    rlWater.setEnabled(true);
//                    rlSleep.setEnabled(true);
//                    rlStress.setEnabled(true);
//                    rlNutrition.setEnabled(true);
//                    rlReeplan.setEnabled(true);
//                    rlBloodReport.setEnabled(true);

                    HomeFragmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        if (listResponse.getData() != null) {
                            mindStatus = listResponse.getData().getActualMindStatus();
                            bingQuantity = listResponse.getData().getIsBingeOnLargeQuantity();
                            if (mindStatus == null) {
                                mindStatus = "";
                            }
                        }
                    } else {
                        if (!isSwipeToRefresh)
                            utilsana.hideProgressbar();

                        recoachResonseCode = response.body().getCode();
                        reaochnotAssingMSg = response.body().getMessage();
//                        rlBcaReport.setEnabled(false);
//                        rlCalories.setEnabled(false);
//                        rlMyActivity.setEnabled(false);
//                        rlWater.setEnabled(false);
//                        rlSleep.setEnabled(false);
//                        rlStress.setEnabled(false);
//                        rlNutrition.setEnabled(false);
//                        rlReeplan.setEnabled(false);
//                        rlBloodReport.setEnabled(false);

//                        Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    if (!isSwipeToRefresh)
                        utilsana.hideProgressbar();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
                if (!isSwipeToRefresh)
                    utilsana.hideProgressbar();
            }
        });
    }


    private void findViewsana() {
        rlReescore = findViewById(R.id.rlReescore);
//        rlBcaReport = findViewById(R.id.rlBcaReport);
        rlCalories = findViewById(R.id.rlCaloriesConsumption);
        rlMyActivity = findViewById(R.id.rlMyActivity);
        rlWater = findViewById(R.id.rlWater);
        rlSleep = findViewById(R.id.rlSleep);
        rlStress = findViewById(R.id.rlStress);
        rlNutrition = findViewById(R.id.rlNutrition);
        rlReeplan = findViewById(R.id.rlReeplan);
        rlBloodReport = findViewById(R.id.rlBloodReport);
        rlweightsanalysis = findViewById(R.id.rlweightsanalysis);

        rlReescore.setOnClickListener(this);
//        rlBcaReport.setOnClickListener(this);
        rlCalories.setOnClickListener(this);
        rlMyActivity.setOnClickListener(this);
        rlWater.setOnClickListener(this);
        rlSleep.setOnClickListener(this);
        rlStress.setOnClickListener(this);
        rlNutrition.setOnClickListener(this);
        rlReeplan.setOnClickListener(this);
        rlBloodReport.setOnClickListener(this);
        rlweightsanalysis.setOnClickListener(this);
        rlReeevaluate.setOnClickListener(this);
    }


    //
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


    private void addTestominal() {
        dialog = new Dialog(context, R.style.CustomDialog);
        dialog.setContentView(R.layout.dailog_lay_add_testimal);
        TextView testimonal_header = dialog.findViewById(R.id.testimonal_header);
        editText = dialog.findViewById(R.id.edt_testominals);
        testimonal_header.setText("Add Testimonials");

        Button btn_submit_testomials = dialog.findViewById(R.id.btn_submit_testomials);

        btn_submit_testomials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(FoodGuideActivity.this, "Please enter the Testominals", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();

                postTestimals(editText.getText().toString().trim(), fileuploadimage);
            }
        });
        ImageView img_upload_image = dialog.findViewById(R.id.img_upload_image);
        ImageView img_upload_file = dialog.findViewById(R.id.img_upload_file);
        img_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicineImageDailog();
            }
        });
        img_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();

            }
        });


        dialog.show();
    }

    private void getMytestominals() {
        util.showProgressbar(context);
        {
            if (!((Activity) context).isFinishing()) {
                util.showProgressbar(context);
            }
            Call<ClsMyTestimonialsMain> call = reeTestService.getMyTestominals(userID);
            call.enqueue(new Callback<ClsMyTestimonialsMain>() {
                @Override
                public void onResponse(Call<ClsMyTestimonialsMain> call, Response<ClsMyTestimonialsMain> response) {
                    util.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK) {
                        ClsMyTestimonialsMain listResponse = response.body();
                        if (listResponse != null && listResponse.getCode() == 1) {
                            try {
                                final MyTestominalsData arylst_testimal_data = listResponse.getData();

                                testo_ID = Integer.parseInt(arylst_testimal_data.getTestimonialId());


                                if (arylst_testimal_data != null) {

//                                   dialog=new Dialog(context,R.style.CustomDialog);
//                                    dialog.setContentView(R.layout.dailog_lay_add_testimal);
//                                    TextView testimonal_header=dialog.findViewById(R.id.testimonal_header);
//                                    editText=dialog.findViewById(R.id.edt_testominals);
//                                    if (arylst_testimal_data.getTestimonial()!=null&&!arylst_testimal_data.getTestimonial().isEmpty()){
//                                        editText.setText(arylst_testimal_data.getTestimonial());
//                                        testimonal_header.setText("Edit Testimonials");
//
//                                    }else {
//                                        testimonal_header.setText("Add Testimonials");
//
//                                    }
//                                    Button btn_submit_testomials=dialog.findViewById(R.id.btn_submit_testomials);
//
//                                    btn_submit_testomials.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//                                            if (editText.getText().toString().trim().isEmpty()){
//                                                Toast.makeText(TestimalActivity.this, "Please enter the Testominals", Toast.LENGTH_SHORT).show();
//                                           return;
//                                            }
//                                            dialog.dismiss();
//
//                                            postTestimals(editText.getText().toString().trim(), fileuploadimage);
//                                        }
//                                    });
//                                    Button btn_add_png=dialog.findViewById(R.id.btn_add_png);
//                                    Button btn_add_image=dialog.findViewById(R.id.btn_add_image);
//                                    btn_add_image.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            AddMedicineImageDailog();
//                                        }
//                                    });
//                                    btn_add_png.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            fileChooser();
//
//                                        }
//                                    });
//
//
//                                    dialog.show();
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsMyTestimonialsMain> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    util.hideProgressbar();
                }
            });
        }

    }


    private void addTestimals() {
        util.showProgressbar(context);
        {
            if (!((Activity) context).isFinishing()) {
                util.showProgressbar(context);
            }
            Call<ClsTestimalMain> call = reeTestService.getTestimalList();
            call.enqueue(new Callback<ClsTestimalMain>() {
                @Override
                public void onResponse(Call<ClsTestimalMain> call, Response<ClsTestimalMain> response) {
                    util.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK) {
                        ClsTestimalMain listResponse = response.body();
                        if (listResponse != null && listResponse.getCode() == 1) {
                            ArrayList<TestimalDataClass> arylst_testimal_data = listResponse.getData();
//                            arylst_testimal_data.addAll(arylst_testimal_data);
//                            arylst_testimal_data.addAll(arylst_testimal_data);
                            if (arylst_testimal_data != null) {
                                TestimalsListAdapter testimalsListAdapter = new TestimalsListAdapter(FoodGuideActivity.this, arylst_testimal_data);
                                recler_testimals.setAdapter(testimalsListAdapter);
                            }


                        } else {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsTestimalMain> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    util.hideProgressbar();
                }
            });
        }
    }

    private void postTestimals(String str_data, File file) {
        util.showProgressbar(context);
        {
            if (!((Activity) context).isFinishing()) {
                util.showProgressbar(context);
            }

            filetype = "0";


            RequestBody userIdbody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(testo_ID));
            RequestBody TestominalIdbody = RequestBody.create(MediaType.parse("text/plain"), "" + str_data);
            RequestBody ReeworkIDbody = RequestBody.create(MediaType.parse("text/plain"), "" + userID);


            MultipartBody.Part photo = null;
            if (!file.getPath().isEmpty()) {


                if (!isFile) {

                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    String imagename = file.getName();
                    photo = MultipartBody.Part.createFormData("ReportName", imagename, requestFile);
                    filetype = "1";

                } else {

                    RequestBody photoContent;

                    photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    photo = MultipartBody.Part.createFormData("ReportName", file.getName(), photoContent);
                    filetype = "2";


                }


//                RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), this.fileuploadimage);
//                photo = MultipartBody.Part.createFormData("ReportName", this.fileuploadimage.getName(), photoContent);
            } else {
                RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                photo = MultipartBody.Part.createFormData("ReportName", "", photoContent);
                filetype = "0";

            }
            MedicineService medService = Client.getClientMultiPart().create(MedicineService.class);

            RequestBody fileTypebody = RequestBody.create(MediaType.parse("text/plain"), "" + filetype);

//            Call<ClsSuccessTest> call = reeTestService.posttestominals(clsPostTestominals);
            Call<ClsSuccessTest> call = medService.newPosttestominalss(userIdbody, TestominalIdbody, ReeworkIDbody, fileTypebody, photo);
            call.enqueue(new Callback<ClsSuccessTest>() {
                @Override
                public void onResponse(Call<ClsSuccessTest> call, Response<ClsSuccessTest> response) {
                    util.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK) {
                        ClsSuccessTest listResponse = response.body();
                        if (listResponse != null && listResponse.getCode() == 1) {
                            Toast.makeText(FoodGuideActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            fileuploadimage = new File("");

                            dialog.dismiss();
                            addTestimals();


                        } else {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsSuccessTest> call, Throwable t) {
                    dialog.dismiss();

                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    util.hideProgressbar();
                }
            });
        }
    }

    private void AddMedicineImageDailog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);


        builder.setMessage("Upload Image ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(FoodGuideActivity.this)
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        CallCameraIntent();
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


                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        dialog.cancel();

                        Dexter.withActivity(FoodGuideActivity.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        Imagechooser();
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


                    }
                });
        //Creating dialog box
        android.app.AlertDialog alert = builder.create();
        alert.setCancelable(true);
        //Setting the title manually
        alert.show();
    }

    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
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

//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 101);

    }

    public void CallCameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, 500);


// food trip

        ContentValues values1 = new ContentValues();
        values1.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values1);

        Intent intentPicture1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture1.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);





    }

    public void fileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(intent, FILE_SELECT_REQUEST_CODE);


        //food trip

        Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent1, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);






    }

    public void Imagechooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName + ".pdf");
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {

            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);

//            try
//            {
//                String[] proj = {MediaStore.Images.Media.DATA};
//                Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                return cursor.getString(column_index);
//            }

        } catch (Exception e) {

            return contentUri.getPath();
        }
    }


    //Schedule Pathology Test

    private void initspt() {
        Service = Client.getClient().create(ReeTestService.class);
        utilsspt = new Utils();
        sessionManagerspt = new SessionManager(context);

        userIdspt = sessionManagerspt.getIntValue(SessionManager.KEY_USER_ID);
    }


    private void findViewsspt() {
        buttonSchedule = findViewById(R.id.buttonScheduleReminder);
        mainLayoutspt = findViewById(R.id.mainLayout);


        recyclerViewspt = findViewById(R.id.recyclerview);
        reminderList = new ArrayList<>();
        remindersAdapter = new ScheduleBloodTestAdapter(context, reminderList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewspt.setLayoutManager(layoutManager);
        recyclerViewspt.setItemAnimator(new DefaultItemAnimator());
        recyclerViewspt.setAdapter(remindersAdapter);

        mainLayoutspt = findViewById(R.id.mainLayout);
        noInternetLayoutspt = findViewById(R.id.no_internet);
        linearNoData = findViewById(R.id.relLay_MyReminder_AddClock);
        btnRefreshspt = findViewById(R.id.btnRefresh);
        btnRefreshspt.setOnClickListener(this);

        buttonSchedule.setOnClickListener(this);

       /* if (Utils.isNetworkAvailable(context))
        {
            callToGetAllReminders();
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
        }*/

        /*buttonSchedule = findViewById(R.id.buttonScheduleReminder);
        linearLayout_MyReminders = findViewById(R.id.linLay_MyReminder_ReminderList);
        relativeLayout_ScheduleClock = findViewById(R.id.relLay_MyReminder_AddClock);

        buttonSchedule.setOnClickListener(this);

        if (reminderItemList.isEmpty())
        {
            relativeLayout_ScheduleClock.setVisibility(View.VISIBLE);
            linearLayout_MyReminders.setVisibility(View.GONE);
        }
        else
        {
            relativeLayout_ScheduleClock.setVisibility(View.GONE);
            linearLayout_MyReminders.setVisibility(View.VISIBLE);
        }*/
    }


    public void showLayouts() {
        if (!Utils.isNetworkAvailable(context)) {
            noInternetLayoutspt.setVisibility(View.VISIBLE);
            mainLayoutspt.setVisibility(View.GONE);
            linearNoData.setVisibility(View.GONE);
        } else {
            mainLayoutspt.setVisibility(View.VISIBLE);
            noInternetLayoutspt.setVisibility(View.GONE);
            linearNoData.setVisibility(View.GONE);
        }
    }


    /* TO GET ALL REMINDER */
    private void getAllBloodReportHistory() {
        if (!((Activity) context).isFinishing()) {
            utilsspt.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userIdspt);

        Call<GetBloodTestReportRes> call = Service.getAllBloodReportHistory(String.valueOf(userIdspt));
        call.enqueue(new Callback<GetBloodTestReportRes>() {
            @Override
            public void onResponse(Call<GetBloodTestReportRes> call, Response<GetBloodTestReportRes> response) {
                utilsspt.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    String dataAsString = new Gson().toJson(response);
                    GetBloodTestReportRes reminderResponse = response.body();

                    if (reminderResponse != null && reminderResponse.getCode() == 0) {
                        ArrayList<GetBloodTestReportRes.DataList> tempList = (ArrayList<GetBloodTestReportRes.DataList>) reminderResponse.getData();

                        if (tempList != null && tempList.size() > 0)

                            linearNoData.setVisibility(View.GONE);
                        noInternetLayoutspt.setVisibility(View.GONE);
                        recyclerViewspt.setVisibility(View.VISIBLE);


                        if (tempList != null) {
                            reminderList = new ArrayList<>();
                            reminderList.clear();
                            reminderList.addAll(tempList);

                            remindersAdapter = new ScheduleBloodTestAdapter(context, reminderList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FoodGuideActivity.this);
                            recyclerViewspt.setLayoutManager(layoutManager);
                            recyclerViewspt.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewspt.setAdapter(remindersAdapter);
                        }


                    } else {
                        recyclerViewspt.setVisibility(View.GONE);
                        linearNoData.setVisibility(View.VISIBLE);
                        noInternetLayoutspt.setVisibility(View.GONE);
                    }
                } else
                    ShowRetryBar("");
            }


            @Override
            public void onFailure(Call<GetBloodTestReportRes> call, Throwable t) {
                utils.hideProgressbar();
                ShowRetryBar("");
            }
        });
    }

    private void ShowRetryBar(String msg) {
        String strMessage;
        if (msg.isEmpty()) {
            strMessage = "Unable to load data";
        } else {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Utils.isNetworkAvailable(context)) {
                            showLayouts();
                            getAllBloodReportHistory();
                        } else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    //Pathology

    private void init() {
        sessionManager = new SessionManager(context);
        reecoachService = Client.getClient().create(ReecoachService.class);
        utils = new Utils();
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Food Guide");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vp_reecoach.getDisplayedChild() == 1) {
                    rd_button__reecoach_profile.performClick();
                    return;
                }
                startActivity(new Intent(FoodGuideActivity.this, HomeActivity.class));
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
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

    private void findViews() {
        imgReecoachBg = findViewById(R.id.imageView_ReecoachProfile_Photo_bg);
        btn_change_reecoach = findViewById(R.id.btn_change_reecoach);
        btn_change_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FoodGuideActivity.this, ChangePathoActivity.class);
                startActivityForResult(intent, 205);

            }
        });


        imgViewNext_roacoach = findViewById(R.id.imgViewNext_roacoach);
        imgReecoach = findViewById(R.id.imageView_ReecoachProfile_Photo);
        textView_Name = findViewById(R.id.text_ReecoachProfile_Name);
        textView_MobNo = findViewById(R.id.text_ReecoachProfile_Mobile);
        textView_Email = findViewById(R.id.text_ReecoachProfile_Email);
        textView_RegAddress = findViewById(R.id.text_ReecoachProfile_RegAddress);
        textView_NewAddress = findViewById(R.id.text_ReecoachProfile_NewAddress);
        progressBar = findViewById(R.id.progress);
        rd_button_reecoach_appointment = findViewById(R.id.rd_button_reecoach_appointment);
        vp_reecoach = findViewById(R.id.vp_reecoach);
        vp_reecoach.setDisplayedChild(0);
        email = sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        if (Utils.isNetworkAvailable(context)) {
            callProfileDetailsAPI(email, userId);
        }

    }

    private void callProfileDetailsAPI(String email, int userid) {

        ReecoachDetailsRequest request = new ReecoachDetailsRequest();
//        request.setEmail(email);
        request.setUserid(userid);

        Call<ReecoachDetailsResponse> call = reecoachService.getPathologiesProfileDetails(request);
        call.enqueue(new Callback<ReecoachDetailsResponse>() {
            @Override
            public void onResponse(Call<ReecoachDetailsResponse> call, Response<ReecoachDetailsResponse> response) {
//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ReecoachDetailsResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        ReecoachDetailsResponse.DataResponse dataResponse = listResponse.getData();

                        if (dataResponse != null) {
                            btn_change_reecoach.setText("Change Pathologist");

                            String reecoachName = dataResponse.getFirstName() + " " + dataResponse.getLastName();
                            String reecoachMobile = dataResponse.getMobileNo();
                            String reecoachEmail = dataResponse.getEmail();
                            String reecoachAddress = dataResponse.getAddress();
                            String imgUrl = dataResponse.getImageUrl();


                            if (!TextUtils.isEmpty(reecoachName))
                                if (dataResponse.getReecoachType() != null && !dataResponse.getReecoachType().trim().isEmpty()) {


                                    String type = dataResponse.getReecoachType().substring(0, dataResponse.getReecoachType().length() - 2);

                                    String strReecoachType = "(" + type + ")";

                                    textView_Name.setText(reecoachName + strReecoachType);
                                } else {
                                    textView_Name.setText(reecoachName);
                                }


                            if (!TextUtils.isEmpty(reecoachMobile))
                                textView_MobNo.setText(reecoachMobile);

                            if (!TextUtils.isEmpty(reecoachEmail))
                                textView_Email.setText(reecoachEmail);

                            if (!TextUtils.isEmpty(reecoachAddress))
                                textView_RegAddress.setText(reecoachAddress);

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_profile_pic_bg)
                                    .error(R.drawable.ic_profile_pic_bg)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .priority(Priority.HIGH);


                            if (listResponse.getData().getAdditionalDetails() != null && !listResponse.getData().getAdditionalDetails().isEmpty()) {

                                recyler_reecoach_add_info.setAdapter(new AdditionalQnAdapter(FoodGuideActivity.this, listResponse.getData().getAdditionalDetails()));

                            }


                            if (isValidContextForGlide(context)) {
                                // for background Image
                                Glide.with(context)
                                        .load(imgUrl)
                                        .apply(options)
                                        .into(imgReecoachBg);

                                // for profile Image
                                Glide.with(context)
                                        .load(imgUrl)
                                        .apply(options)
                                        .apply(RequestOptions.circleCropTransform())
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                                        Target<Drawable> target, boolean isFirstResource) {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                                           DataSource dataSource, boolean isFirstResource) {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }
                                        })
                                        .into(imgReecoach);
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    btn_change_reecoach.setText("Select Pathologist");
                    Toast.makeText(context, "You have not selected any pathologist ,Please select Pathologist first", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ReecoachDetailsResponse> call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void initAppointment() {
        appoinmentService = Client.getClient().create(AppoinmentService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);
    }


    private void findViewsAppoimtent() {
        recyclerView = findViewById(R.id.rvAppoinments);
        btnScheduleAppointment = findViewById(R.id.buttonScheduleAppointment);
        buttonSleep_ViewMore = findViewById(R.id.buttonSleep_ViewMore);
        txtNoData = findViewById(R.id.txtNoData);

        appointmentDataArrayList = new ArrayList<>();
        adapter = new MyAppointmentsAdapter(context, appointmentDataArrayList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnScheduleAppointment.setOnClickListener(this);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);


        final ViewFlipper vp_reecoach = findViewById(R.id.vp_reecoach);
        vp_reecoach.setDisplayedChild(0);
        rd_button__reecoach_profile = findViewById(R.id.rd_button__reecoach_profile);
        rd_button__reecoach_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_change_reecoach.setVisibility(View.VISIBLE);
                vp_reecoach.setDisplayedChild(0);
                if (Utils.isNetworkAvailable(context)) {
                    callProfileDetailsAPI(email, userId);
                }

            }
        });

        RadioButton rd_button_chat = findViewById(R.id.rd_button_chat);
        rd_button_chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btn_change_reecoach.setVisibility(View.GONE);

                vp_reecoach.setDisplayedChild(2);

            }
        });


        rd_button_reecoach_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_change_reecoach.setVisibility(View.GONE);
                vp_reecoach.setDisplayedChild(1);

            }
        });


        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Schedule Pathology Test
        if (Utils.isNetworkAvailable(context))
            getAllBloodReportHistory();
        else
            showLayouts();

        //Pathology
        if (sessionManager.getStringValue("Back").equalsIgnoreCase("true")) {
            callToGetAllAppoinments(false);
            sessionManager.setStringValue("Back", "");

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//Testimonial
        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                isFile = false;

                fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
//


//                    Bitmap bitmapImage = BitmapFactory.decodeFile(fileuploadimage.getPath());
//                    int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

                    if (fileuploadimage.exists()) {
                        postTestimals(editText.getText().toString().trim(), fileuploadimage);

                    } else {
                        Toast.makeText(context, "file not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString());
                    t.printStackTrace();
                }

            }
        }


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                isFile = false;

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


//                Uri uri =/ data/.getData();

//                String path = getFilePathFr//omURI(BloodReportActivity.this,selectedImage);
                File file = new File(picturePath);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                Bitmap bitmapImage = BitmapFactory.decodeFile(file.getPath());
//                int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

                if (file.exists()) {
                    postTestimals(editText.getText().toString().trim(), file);

                } else {
                    Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
                }

            }
        }


        if (resultCode == RESULT_OK) {
            if (requestCode == FILE_SELECT_REQUEST_CODE) {
                isFile = true;
                Uri uri = data.getData();

                String path = getFilePathFromURI(FoodGuideActivity.this, uri);
                File file = new File(path);

                if (file.exists()) {
                    postTestimals(editText.getText().toString().trim(), file);


                } else {
                    Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
                }

            }
        }


        if (requestCode == REQ_FOR_APPOINMENT && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("RESULT")) {
                if (data.getStringExtra("RESULT").equals("ok")) {
                    if (Utils.isNetworkAvailable(context)) {
                        callToGetAllAppoinments(false);
                    } else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == 205 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("Reeacoach")) {
                if (data.getStringExtra("Reeacoach").equals("yes")) {
                    if (Utils.isNetworkAvailable(context)) {
                        callProfileDetailsAPI(email, userId);

                    } else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }


//food trip

        if (requestCode == FILE_SELECT_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {


                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = mContext.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                uploadFile(new File(picturePath), userId);

                isImage = true;
            }
        }
        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userId);
                isImage = true;
            }
        }
        else if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                boolean res = data.getBooleanExtra("result",false);
                if (res) {
                    if (Utils.isNetworkAvailable(mContext)) {
//                        callToGetAllRecipies("intial", 0, "");

                    } else
                        Toast.makeText(mContext.getApplicationContext(), "R.string.internet_connection_unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        }






    }

    /* Get clicked position here */
    @Override
    public void GetClickedAppointment(String type, int position, GetAllAppointmentResponse.AppointmentData model) {
        if (!TextUtils.isEmpty(type)) {
            if (type.equals("edit")) {
                FragmentManager fm = getSupportFragmentManager();
                editDialog = new MyAppoinmentEditDialog();

                Bundle bundle = new Bundle();
                bundle.putSerializable("MODEL", model);
                editDialog.setArguments(bundle);
                editDialog.show(fm, "edit_fragment");
            } else {
                deleteAppoinment(position, model);
            }
        }
    }

    @Override
    public void onEdit(GetAllAppointmentResponse.AppointmentData model) {
        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);
        else
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
    }

    public void deleteAppoinment(final int pos, final GetAllAppointmentResponse.AppointmentData model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete appointment!")
                .setMessage("Do you really want to delete this appointment?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context)) {
                            utils.showProgressbar(context);

                            callDeleteAppoinment(pos, model);
                        } else {
                            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
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

    private void callDeleteAppoinment(final int pos, GetAllAppointmentResponse.AppointmentData model) {
        AppoinmentEditRequest request = new AppoinmentEditRequest();
        request.setUserID(model.getUserID());
        request.setApptID(model.getApptID());

        Call<AppoinmentResponse> call = appoinmentService.deleteAppoinment(request);
        call.enqueue(new Callback<AppoinmentResponse>() {
            @Override
            public void onResponse(Call<AppoinmentResponse> call, Response<AppoinmentResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    AppoinmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        // call List API to reload list
                        appointmentDataArrayList.remove(pos);
                        adapter.notifyDataSetChanged();
                        callToGetAllAppoinments(false);
                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppoinmentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void callToGetAllAppoinments(final boolean isSwipeToRefresh) {
        if (!((Activity) context).isFinishing()) {
            if (!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setRoleId(4);
        request.setUserID(userId);


        Call<GetAllAppointmentResponse> call = appoinmentService.getAllAppoinments(request);
        call.enqueue(new Callback<GetAllAppointmentResponse>() {
            @Override
            public void onResponse(Call<GetAllAppointmentResponse> call, Response<GetAllAppointmentResponse> response) {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    GetAllAppointmentResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1) {
                        ArrayList<GetAllAppointmentResponse.AppointmentData> tempList = appointmentResponse.getData();

                        if (tempList != null && tempList.size() > 0) {
                            appointmentDataArrayList.clear();
                            appointmentDataArrayList.addAll(tempList);
                            adapter.notifyDataSetChanged();
                            txtNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            txtNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else if (vp_reecoach.getDisplayedChild() == 1) {

                        txtNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<GetAllAppointmentResponse> call, Throwable t) {
                Log.e("ERROR---->", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //Analytics

            case R.id.rlReescore:

                startActivity(new Intent(mContext, RescoreIntroActivity.class));

                break;

            case R.id.rlReeevaluate:

                Intent intent2 = new Intent(this, ReeevaluateHealthparamActivity.class);
                intent2.putExtra("ISFromReevaluate", true);
                startActivity(intent2);
                break;


            case R.id.rlCaloriesConsumption:


                Intent intent = new Intent(mContext, ColoriesAnalysisActivty.class);
                startActivity(intent);


                break;

            case R.id.rlMyActivity:
//                Intent intent1 = new Intent(mContext, ActivityNapMainActivity.class);
//                startActivity(intent1);
                Intent intent7 = new Intent(mContext, ActivityNapMainActivity.class);
                intent7.putExtra("ISFromANnalysis", true);
                startActivity(intent7);

                break;


            case R.id.rlweightsanalysis:


                startActivity(new Intent(mContext, WeightAnalysis.class));
                break;

            case R.id.rlWater:

                Intent intent5 = new Intent(mContext, WaterAnalysisActivity.class);
                intent5.putExtra("ISFromANnalysis", true);
                startActivity(intent5);


                break;

            case R.id.rlSleep:

                Intent intent3 = new Intent(mContext, SleepAnalysisActivity.class);
                intent3.putExtra("ISFromANnalysis", true);
                startActivity(intent3);


                break;

            case R.id.rlStress:

                Intent intent8 = new Intent(mContext, MindAnalysis.class);
                intent8.putExtra("ISFromANnalysis", true);
                startActivity(intent8);

                break;

            case R.id.rlNutrition:


                Intent intent6 = new Intent(mContext, MicrorlNutritionActivityAnalysis.class);
                intent6.putExtra("ISFromANnalysis", true);
                startActivity(intent6);

                break;

            case R.id.rlReeplan:


                Intent intent4 = new Intent(mContext, SleepNapMainActivity.class);
                intent4.putExtra("ISFromANnalysis", true);
                startActivity(intent4);


                break;

            case R.id.rlBloodReport:

                startActivity(new Intent(mContext, FoodNapMainActivity.class));


                break;


            //Schedule Pathology Test
            case R.id.buttonScheduleReminder:
                startActivityForResult(new Intent(context, ScheduleBloodTestActivity.class), ADD_REMINDERS);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context)) {
                    showLayouts();
                    getAllBloodReportHistory();
                } else
                    showLayouts();
                break;

            //Pathology

            case R.id.buttonScheduleAppointment:

                Intent intent1 = new Intent(context, AppoinmentScheduleActivity.class);
                intent1.putExtra("IsFromPatho", true);

                startActivityForResult(intent1, REQ_FOR_APPOINMENT);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            case R.id.buttonSleep_ViewMore:

                break;


            case R.id.fab:

                if (Utils.isNetworkAvailable(mContext))
                {
                    /* registerForContextMenu(imgFilter);*/
//                    selectImage();

                    newDailog();
                }
                break;

            case R.id.imgFilter:
                /*Intent i = new Intent(mContext, FoodFilter_SubFilterActivity.class);
                i.putExtra("data",mSubFilterList);
                startActivity(i);*/
//                showFilterDialog(v);
                break;

            case R.id.searchRecipe:
                hideView();
                break;



        }

    }

    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

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


    private void callFoodGuideApi() {


        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);



        Call<ClsFoodGuideMain> call = appoinmentService.getFoodGuide(userId);
        call.enqueue(new Callback<ClsFoodGuideMain>() {
            @Override
            public void onResponse(Call<ClsFoodGuideMain> call, Response<ClsFoodGuideMain> response) {


//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsFoodGuideMain appointmentResponse = response.body();

                    if (appointmentResponse != null ) {
                        food_guide_list = appointmentResponse.getData();

                        btn_greenfood.performClick();



                    }

                }

            }

            @Override
            public void onFailure(Call<ClsFoodGuideMain> call, Throwable t) {
                Log.e("ERROR---->", t.toString());
                utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }


}
