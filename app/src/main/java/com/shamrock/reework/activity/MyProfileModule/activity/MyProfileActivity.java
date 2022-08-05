package com.shamrock.reework.activity.MyProfileModule.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.shamrock.R;
import com.shamrock.reework.activity.BCAModule.adapter.MyBCAReportAdapter;
import com.shamrock.reework.activity.BCAModule.adapter.MyBloodReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.activity.AllReportActivity;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.HealthModule.activity.HealthParametersActivity;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HealthModule.adapter.HealthobjplanlistAdapter;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.HomeModule.pojo.ClsProfileHeaderData;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.MemberSubscription.ExpandableListAdapter;
import com.shamrock.reework.activity.MyPlansModule.activity.MyPlansActivity;
import com.shamrock.reework.activity.MyPlansModule.activity.NewMyPlansActivity;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.MyProfileModule.service.MyProfileService;
import com.shamrock.reework.activity.MyRecoachModule.activity.ChangeReecoachActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachMasterType;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.adapters.AdditionalQnAdapter;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.RescoreModule.activity.ReescoreActivity;
import com.shamrock.reework.activity.RescoreModule.model.ClsAddingImageDetails;
import com.shamrock.reework.activity.RescoreModule.model.ClsReescoreMianClass;
import com.shamrock.reework.activity.RescoreModule.service.RescoreService;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.changepassword.ChangePasswordActivity;
import com.shamrock.reework.activity.dietprofile.ClsDietProfile;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.ClsRegQuenPojo;
import com.shamrock.reework.activity.reeworkerhealth.NewDesignHealthActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ProfileDynamicHealthparamActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ReeevaluateHealthparamActivity;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.activity.services.pojo.ClsExistingPlan;
import com.shamrock.reework.activity.todaysplan.model.ClsTodaysPlanMain;
import com.shamrock.reework.activity.todaysplan.model.ReeworkerPlan;
import com.shamrock.reework.activity.todaysplan.model.TodaysPlanData;
import com.shamrock.reework.activity.todaysplan.model.adapters.ClsTodaysPlansAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.CityRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.ProfileDataRequest;
import com.shamrock.reework.api.request.ReecoachDetailsRequest;
import com.shamrock.reework.api.request.RescoreRequest;
import com.shamrock.reework.api.request.StateRequest;
import com.shamrock.reework.api.request.UpdateProfileRequest;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.City;
import com.shamrock.reework.api.response.CityResponse;
import com.shamrock.reework.api.response.Country;
import com.shamrock.reework.api.response.CountryResponse;
import com.shamrock.reework.api.response.Language;
import com.shamrock.reework.api.response.LanguageResponse;
import com.shamrock.reework.api.response.MyProfileResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.ProfileDataResponse;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;
import com.shamrock.reework.api.response.RescoreResponse;
import com.shamrock.reework.api.response.State;
import com.shamrock.reework.api.response.StateResponse;
import com.shamrock.reework.api.response.TestList;
import com.shamrock.reework.api.response.UpdateProfileResponse;
import com.shamrock.reework.common.TouchImageView;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.dialog.CityDialog;
import com.shamrock.reework.dialog.CountryDialog;
import com.shamrock.reework.dialog.LanguageDialog;
import com.shamrock.reework.dialog.StateDialog;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener,
        CountryDialog.GetCountryListener,
        StateDialog.GetStateListener,
        CityDialog.GetCityListener,
        LanguageDialog.GetLanguageListener,
        DatePickerDialog.OnDateSetListener, TextWatcher {
    TextView txt_select_reecoach,txt_additinal;
    RelativeLayout rel_reecoach_profile_data;
    LinearLayout ll_paid_reecoach,ll_dots_main;
    EditText edt_age;
    EditText edt_about_me;
    TextView txt_update_password, uniqueid;
    TextView user_name;
    Button btn_personal, btn_healthprofile, btn_membershipplan;
    LinearLayout layout_personl, layout_health, layout_plan;
    Button btn_healthvitels, btn_deficiency, btn_reescore;
    LinearLayout layout_healthvitels, layout_deficiency, layout_reescore;
    LinearLayout ll_home;

    private LinearLayout ll_language, ll_pincode, ll_city, ll_state, ll_country;
//    private TextInputLayout txt_input_address;

    private static final String TAG = "MyProfileActivity";
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
    Context context;
    Toolbar toolbar;
    Typeface font;
    String date = "";
    RadioButton rd_heath_profile, rd_basic_profile;
    ImageView imageViewProfile, imageViewCamera, img_blur;
    EditText editText_Email, editText_Mobile, editText_Address;
    TextView textView_DOB;
    RadioButton radioButton_Male, radioButton_Female, radioButton_Transgender;
    TextView btnSubmit;
    ArrayList<Country> countryList;
    ArrayList<State> stateList;
    ArrayList<City> cityList;
    ArrayList<Language> languageList;
    private ProgressBar progressBar;
    CommonService commonService;
    RegistrationService regService;
    int country_id = 101, state_id = 0, city_id = 0;
    String errorMsg = "";
    String firstName = "";
    String lastName = "";
    String DOB = "";
    String gender = "";
    String address = "";
    String email = "";
    String mobile = "";

    String lang_code = "";
    EditText edt_pincode;
    SessionManager sessionManager;
    DatePickerDialog datepickerdialog;
    EditText edtText_blood;

    Button btnUpload, btnPickImage;
    String mediaPath;
    ImageView imgView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};

    public boolean isCamera = false;
    public boolean isImage = false;
    //updateDrawerImage UpdateDrawerImage;

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;
    private Uri mCapturedImageURI;
    private int userID;
    String imageUrl, imageUrlNew;
    private Utils utils;

    /* Dialopgs for country, state, city and language */
    private CountryDialog countryDialog;
    private StateDialog stateDialog;
    private CityDialog cityDialog;
    private LanguageDialog languageDialog;
    private TextView tvCountry, tvState, tvCity, tvLanguage;
    private int userId;
    private ReecoachService reecoachService;
    private HomeFragmentService service;

    RadioButton rd_btn_age, rd_btn_dob;
    LinearLayout ll_date_dob;
    TextInputLayout txt_input_edt_age;
    boolean IsAge = true;

    //New
//    Context context;
    String strImahePath;
    private ListView lst_objetive_plan;
    private TextView txt_health_obj_description, txt_health_obj_name;
    private TextView txt_amount_health_obj;
    Button btn_submit_payment;
    PaidSubscriptionData clsHealthobjective;
    ArrayList<PaidSubscriptionData> paidSubscriptionData;
    //    RegistrationRequest registrationRequest;
    private Utils utils1;
    private SessionManager sessionManager1;

    private RegistrationService regService1;
    String from = "";
    int size;
    int pos;
    UnfreezeService unfreezeService;
    ClsExistingPlan clsExistingPlan;
    HealthParametersService healthParametersService;
    TextView txt_subscription_header;
    TextView btn_submit_change_subscription;


    //REEscore

    //    Context context;
//    Toolbar toolbar;
//    Typeface font;
    ImageView imgNext;
    TextView tvCurrentScore;
    //    ArcSeekBar arcSeekBar;
//    ArcSeekBar seekArc2;
//    ArcSeekBar seekArc3;
    ProgressBar pbar_first_reescore,pbar_recent_reescore,pbar_future_reescore;

    //    Utils utils;
    RescoreService serviceree;
    SessionManager sessionManagerree;

    int userIdree;
    float mCurrentScore;
    ArrayList<RescoreResponse.RescoreData> mDataList;

    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    boolean isFirstTime;
    private String currentDateandTime;
    TextView txt_select_paramters_date;
    private DatePickerDialog datepickerdialog_entry;
    private String dummydate_from;
    private String submitFromDate;


    TextView txt_cycle_text, textReescore_BigMessage;
    ImageView img_cycle;
    ClsReescoreMianClass reescoreMianClassl;
    ArrayList<ClsAddingImageDetails> arylst_adding_image = new ArrayList<>();
    ImageView img_add_1, img_add_2, img_add_3, img_add_4;
    TextView txt_add_1, txt_add_2, txt_add_3, txt_add_4;

    TextView txt_Todays_Score, txt_First_score, tvFutureScore;
    private float mTodaysREEscore, mFutureScore;
    List<BCAResponce.Datum> mbloodList_filter;
    List<BCAResponce.Datum> mBCAList_filter;
    List<BCAResponce.Datum> mBCAList;
    private ReeTestService reeTestService;
    DeficienceAdapter deficienceAdapter;
    RecyclerView recyclerView;
    List<BCAResponce.TestDetails> listOfStudentDataItems;
    LinearLayoutManager linearLayoutManager;
    ArrayList<TestList> bcaTestList = new ArrayList();
    ArrayList<BCAResponce.TestDetails> bcaTestdetailsList = new ArrayList();
    ArrayList<BCAResponce.TestDetails> bcaTestdetailsList1 = new ArrayList();

    ArrayList<TestList> pathTestList = new ArrayList();
    ArrayList<BCAResponce.TestDetails> pathTestdetailsList = new ArrayList();
    ArrayList<BCAResponce.TestDetails> pathTestdetailsList1 = new ArrayList();
    TextView txt_def_date,txt_def_date1;
    String postDate;
    private int sendId;
    //    private int sendId;
    MyPlansService myPlansService;

    TextView txt_bloodgroup, txt_height, txt_BMI, txt_recentweight, txt_targetweight;


    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyProfileActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        TextView footer_notes = findViewById(R.id.footer_notes);
        footer_notes.setSelected(true);
        txt_additinal = findViewById(R.id.txt_additinal);
        txt_additinal.setPaintFlags(txt_additinal.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        txt_additinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, NewDesignHealthActivity.class);
                intent.putExtra("IsAdditinal",true);
                startActivity(intent);
            }
        });
        ll_home = findViewById(R.id.ll_home);
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });
        edt_about_me = findViewById(R.id.edt_about_me);
        edtText_blood = findViewById(R.id.edtText_blood);
        rel_reecoach_profile_data = findViewById(R.id.rel_reecoach_profile_data);
//        txt_input_edt_age = findViewById(R.id.txt_input_edt_age);
        ll_date_dob = findViewById(R.id.ll_date_dob);
        rd_btn_age = findViewById(R.id.rd_btn_age);
        rd_btn_dob = findViewById(R.id.rd_btn_dob);
        txt_update_password = findViewById(R.id.txt_update_password);


//        txt_update_password.setPaintFlags(txt_update_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        reeTestService = Client.getClient().create(ReeTestService.class);

        btn_personal = findViewById(R.id.btn_personal);
        btn_healthprofile = findViewById(R.id.btn_healthprofile);
        btn_membershipplan = findViewById(R.id.btn_membershipplan);
        layout_personl = findViewById(R.id.layout_personl);
        layout_health = findViewById(R.id.layout_health);
        layout_plan = findViewById(R.id.layout_plan);


        btn_healthvitels = findViewById(R.id.btn_healthvitels);
        btn_deficiency = findViewById(R.id.btn_deficiency);
        btn_reescore = findViewById(R.id.btn_reescore);
        layout_healthvitels = findViewById(R.id.layout_healthvitels);
        layout_deficiency = findViewById(R.id.layout_deficiency);
        layout_reescore = findViewById(R.id.layout_reescore);


        //REEscore

        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
//        arcSeekBar = findViewById(R.id.seekArc1);
//        seekArc2 = findViewById(R.id.seekArc2);
//        seekArc3 = findViewById(R.id.seekArc3);
//        arcSeekBar.setMaxProgress(100);
//        seekArc2.setMaxProgress(100);
//        seekArc3.setMaxProgress(100);

        pbar_first_reescore = findViewById(R.id.pbar_first_reescore);
        pbar_recent_reescore = findViewById(R.id.pbar_recent_reescore);
        pbar_future_reescore = findViewById(R.id.pbar_future_reescore);
//        pbar_first_reescore.getProgressDrawable().setColorFilter(
//                R.color.p1, android.graphics.PorterDuff.Mode.SRC_IN);
        pbar_first_reescore.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.p1)));
        pbar_recent_reescore.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.p2)));
        pbar_future_reescore.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.p3)));



        txt_First_score = findViewById(R.id.tvFirstScore);
        txt_Todays_Score = findViewById(R.id.txt_todays_score);
        tvFutureScore = findViewById(R.id.tv_future_score);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        submitFromDate = sdf.format(new Date());

        SimpleDateFormat sdfss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTimess = sdfss.format(new Date());
        txt_select_paramters_date = findViewById(R.id.txt_select_paramters_date);
        txt_select_paramters_date.setText(currentDateandTimess);
        txt_select_paramters_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailogentry();
                datepickerdialog_entry.show();

            }
        });


        recyclerView = findViewById(R.id.rvMyBCAReport12);
        txt_def_date = findViewById(R.id.txt_def_date);
        txt_def_date1 = findViewById(R.id.txt_def_date1);

        postDate = getCurrentDates();
        myPlansService = Client.getClient().create(MyPlansService.class);

        txt_bloodgroup = findViewById(R.id.txt_bloodgroup);
        txt_height = findViewById(R.id.txt_height);
        txt_BMI = findViewById(R.id.txt_BMI);
        txt_recentweight = findViewById(R.id.txt_recentweight);
        txt_targetweight = findViewById(R.id.txt_targetweight);

        btn_healthvitels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                layout_healthvitels.setVisibility(View.VISIBLE);
                layout_deficiency.setVisibility(View.GONE);
                layout_reescore.setVisibility(View.GONE);

                btn_healthvitels.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_deficiency.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reescore.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_healthvitels.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_deficiency.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reescore.setTextColor(getResources().getColor(R.color.black_recipe));

//                callTodaysPlanApi();
                callDietProfileAPi();
            }
        });


        btn_deficiency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                layout_healthvitels.setVisibility(View.GONE);
                layout_deficiency.setVisibility(View.VISIBLE);
                layout_reescore.setVisibility(View.GONE);

                btn_deficiency.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_healthvitels.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reescore.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_deficiency.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_healthvitels.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reescore.setTextColor(getResources().getColor(R.color.black_recipe));

                callToGetBCAReport();


            }
        });


        btn_reescore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                layout_healthvitels.setVisibility(View.GONE);
                layout_deficiency.setVisibility(View.GONE);
                layout_reescore.setVisibility(View.VISIBLE);

                btn_deficiency.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_healthvitels.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reescore.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_deficiency.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_healthvitels.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reescore.setTextColor(getResources().getColor(R.color.white_recipe));

                if (Utils.isNetworkAvailable(context))
                    CallToGetRescoreData();

                else
                    showLayouts();

            }
        });


        btn_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                layout_personl.setVisibility(View.VISIBLE);
                layout_health.setVisibility(View.GONE);
                layout_plan.setVisibility(View.GONE);

                btn_personal.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_healthprofile.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_membershipplan.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_personal.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_healthprofile.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_membershipplan.setTextColor(getResources().getColor(R.color.black_recipe));

            }
        });

        btn_healthprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_personl.setVisibility(View.GONE);
                layout_health.setVisibility(View.VISIBLE);
                layout_plan.setVisibility(View.GONE);
                layout_healthvitels.setVisibility(View.VISIBLE);
                btn_personal.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_healthprofile.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                btn_membershipplan.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                btn_personal.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_healthprofile.setTextColor(getResources().getColor(R.color.white_recipe));
                btn_membershipplan.setTextColor(getResources().getColor(R.color.black_recipe));

//                callToGetBCAReport();

                callDietProfileAPi();

            }
        });


        btn_membershipplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_plan.setVisibility(View.VISIBLE);
                layout_health.setVisibility(View.GONE);
                layout_personl.setVisibility(View.GONE);

                btn_personal.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_healthprofile.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_membershipplan.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_personal.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_healthprofile.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_membershipplan.setTextColor(getResources().getColor(R.color.white_recipe));

            }
        });


        txt_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, ChangePasswordActivity.class));


            }
        });
        rd_btn_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_age.setVisibility(View.VISIBLE);
                ll_date_dob.setVisibility(View.VISIBLE);
                IsAge = true;

            }
        });

        rd_btn_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_age.setVisibility(View.VISIBLE);
                ll_date_dob.setVisibility(View.VISIBLE);
                IsAge = false;

            }
        });
        txt_select_reecoach = findViewById(R.id.txt_select_reecoach);
        edt_age = findViewById(R.id.edt_age);

        ll_paid_reecoach = findViewById(R.id.ll_paid_reecoach);

        txt_select_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReecaochTypeData();
            }
        });
        context = MyProfileActivity.this;
        sessionManager = new SessionManager(context);

        rd_basic_profile = findViewById(R.id.rd_basic_profile);
        rd_heath_profile = findViewById(R.id.rd_heath_profile);
        rd_basic_profile.setText(sessionManager.getStringValue("KEY_BASIC_PROFILE"));
        rd_heath_profile.setText(sessionManager.getStringValue("KEY_HEALTH_PROFILE"));
        Callprofileheader();
        rd_heath_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, ProfileDynamicHealthparamActivity.class);
                intent.putExtra("ISFreeze", true);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        init();


        findViews();
        getRegQuestionStepsApi();

        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            rd_heath_profile.setVisibility(View.VISIBLE);


            if (sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true")) {
                ll_paid_reecoach.setVisibility(View.GONE);

                callProfileDetailsAPI();

            } else {
                ll_paid_reecoach.setVisibility(View.GONE);
                rel_reecoach_profile_data.setVisibility(View.GONE);
                txt_select_reecoach.setVisibility(View.GONE);
            }

        } else {
            RadioGroup radioGroup_sleep = findViewById(R.id.radioGroup_sleep);
            radioGroup_sleep.setVisibility(View.GONE);

            rd_heath_profile.setVisibility(View.GONE);
            ll_paid_reecoach.setVisibility(View.GONE);

            rel_reecoach_profile_data.setVisibility(View.GONE);
            txt_select_reecoach.setVisibility(View.GONE);
        }


        //New

        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils1 = new Utils();
        txt_subscription_header = findViewById(R.id.txt_subscription_header);
        btn_submit_change_subscription = findViewById(R.id.btn_submit_change_subscription);
        context = MyProfileActivity.this;
        clsExistingPlan = new ClsExistingPlan();
        clsExistingPlan.setMessage("");


        sessionManager1 = new SessionManager(context);
        if (sessionManager1.getStringValue("notallowed").equalsIgnoreCase("true")) {
            txt_subscription_header.setText("Last Membership Plan");
            btn_submit_change_subscription.setText("Renew Membership Plan");


        } else {
            txt_subscription_header.setText("Existing Membership Plan");
            btn_submit_change_subscription.setText("Change Membership Plan");


        }


        Button btn_submit_change_subscription = findViewById(R.id.btn_submit_change_subscription);
        btn_submit_change_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyProfileActivity.this, ViewPagerActivity.class);
                intent.putExtra("param", "change");
                startActivity(intent);

            }
        });

        lst_objetive_plan = findViewById(R.id.lst_plan_features);
        txt_health_obj_name = findViewById(R.id.txt_plan_name);
        txt_health_obj_description = findViewById(R.id.txt_plan_description);
        txt_amount_health_obj = findViewById(R.id.txt_plan_amount);
        btn_submit_payment = findViewById(R.id.btn_submit_payment);
        sessionManager1 = new SessionManager(context);
//        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);

        getMyplanApi();

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


    }


    private void callToGetBCAReport() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);////4186


        Call<BCAResponce> call = reeTestService.getAllBloodReportNewHistory(String.valueOf(userId));
        call.enqueue(new Callback<BCAResponce>() {
            @Override
            public void onResponse(Call<BCAResponce> call, Response<BCAResponce> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    BCAResponce bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode() == 1) {
                        mBCAList = bcaResponse.getData();

                        if (mBCAList != null && mBCAList.size() > 0) {
                            mBCAList_filter = new ArrayList<>();
                            mbloodList_filter = new ArrayList<>();
                            pathTestdetailsList1.clear();
                            pathTestdetailsList.clear();
                            pathTestList.clear();
                            bcaTestdetailsList1.clear();
                            bcaTestdetailsList.clear();

                            bcaTestList.clear();
                            pathTestList.clear();
                            List<TestList> TestList = null;
                            List<TestList> pathtestList = null;
                            for (int i = 0; i < mBCAList.size(); i++) {
                                if (mBCAList.get(i).getReportType().equalsIgnoreCase("BCA Test")) {
                                    mBCAList_filter.add(mBCAList.get(i));
                                } else {
                                    mbloodList_filter.add(mBCAList.get(i));

                                }


                            }

                            if (mBCAList_filter.size() != 0) {


                                if (mBCAList_filter.get(0).getCollectedDate().contains("T")) {
                                    try {
                                        int index = mBCAList_filter.get(0).getCollectedDate().indexOf("T");

                                        txt_def_date.setText(" As on : " + formatDates(mBCAList_filter.get(0).getCollectedDate().substring(0, index)));
                                    } catch (Exception e) {
                                        int index = mBCAList_filter.get(0).getCollectedDate().indexOf("T");

                                        txt_def_date.setText((mBCAList_filter.get(0).getCollectedDate().substring(0, index)));

                                        e.printStackTrace();
                                    }
                                }


//                            for (int i = 0; i < mBCAList_filter.size(); i++) {

//                                TestList mBCAList_filter.get(i).getTestList();
                                TestList = (mBCAList_filter.get(0).getTestList());
//                                listOfStudentDataItems =mBCAList_filter.get(i).getTestList().get(0).getTestDetails();
                                bcaTestList.addAll(TestList);
//                                testdetailsList.addAll(listOfStudentDataItems);
//                            }

                            }
                            for (int i = 0; i < bcaTestList.size(); i++) {

                                bcaTestdetailsList.addAll(bcaTestList.get(i).getTestDetails());
                            }

                            for (int i = 0; i < bcaTestdetailsList.size(); i++) {
                                if (bcaTestdetailsList.get(i).getRemark() != null) {

                                    if (bcaTestdetailsList.get(i).getRemark().equals("Above average.") || bcaTestdetailsList.get(i).getRemark().equals("Required attention.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Needs attention.") || bcaTestdetailsList.get(i).getRemark().equals("Needs intervention.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Below Normal.") || bcaTestdetailsList.get(i).getRemark().equals("Need to contact your REEcoach.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Talk to your REEcoach") || bcaTestdetailsList.get(i).getRemark().equals("Improvement required.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Intervention required.") || bcaTestdetailsList.get(i).getRemark().equals("Slightly above normal.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Talk to REEcoach.") || bcaTestdetailsList.get(i).getRemark().equals("Urgent attention required.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Pre-obese, need to take precautions.") || bcaTestdetailsList.get(i).getRemark().equals("Overweight.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Need to talk to REEcoach.") || bcaTestdetailsList.get(i).getRemark().equals("Hypertension stage 1, need to discuss with REEcoach.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Just below normal.") || bcaTestdetailsList.get(i).getRemark().equals("Attention required.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Reactive, Needs Attention.") || bcaTestdetailsList.get(i).getRemark().equals("Need to contact REEcoach.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Need to be careful.") || bcaTestdetailsList.get(i).getRemark().equals("Obese Type I, talk to your REEcoach.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Moderate risk.") || bcaTestdetailsList.get(i).getRemark().equals("Hypertension.")
                                            || bcaTestdetailsList.get(i).getRemark().equals("Pre-hypertension, need to take precautions.")) {


                                        bcaTestdetailsList1.add(bcaTestdetailsList.get(i));

                                    }


                                }
                            }
                            if (mbloodList_filter.size() != 0) {

//                            for (int i = 0; i < mbloodList_filter.size(); i++) {

//                                TestList mBCAList_filter.get(i).getTestList();
                                pathtestList = (mbloodList_filter.get(0).getTestList());
//                                listOfStudentDataItems =mBCAList_filter.get(i).getTestList().get(0).getTestDetails();
                                pathTestList.addAll(pathtestList);
//                                testdetailsList.addAll(listOfStudentDataItems);
//                            }

                            }
                            for (int i = 0; i < pathTestList.size(); i++) {

                                pathTestdetailsList.addAll(pathTestList.get(i).getTestDetails());
                            }

                            for (int i = 0; i < pathTestdetailsList.size(); i++) {
                                if (pathTestdetailsList.get(i).getRemark() != null) {


                                    if (pathTestdetailsList.get(i).getRemark().equals("Above average.") || pathTestdetailsList.get(i).getRemark().equals("Required attention.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Needs attention.") || pathTestdetailsList.get(i).getRemark().equals("Needs intervention.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Below Normal.") || pathTestdetailsList.get(i).getRemark().equals("Need to contact your REEcoach.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Talk to your REEcoach") || pathTestdetailsList.get(i).getRemark().equals("Improvement required.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Intervention required.") || pathTestdetailsList.get(i).getRemark().equals("Slightly above normal.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Talk to REEcoach.") || pathTestdetailsList.get(i).getRemark().equals("Urgent attention required.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Pre-obese, need to take precautions.") || pathTestdetailsList.get(i).getRemark().equals("Overweight.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Need to talk to REEcoach.") || pathTestdetailsList.get(i).getRemark().equals("Hypertension stage 1, need to discuss with REEcoach.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Just below normal.") || pathTestdetailsList.get(i).getRemark().equals("Attention required.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Reactive, Needs Attention.") || pathTestdetailsList.get(i).getRemark().equals("Need to contact REEcoach.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Need to be careful.") || pathTestdetailsList.get(i).getRemark().equals("Obese Type I, talk to your REEcoach.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Moderate risk.") || pathTestdetailsList.get(i).getRemark().equals("Hypertension.")
                                            || pathTestdetailsList.get(i).getRemark().equals("Pre-hypertension, need to take precautions.")) {


                                        pathTestdetailsList1.add(pathTestdetailsList.get(i));


                                    }


                                }
                            }


                            pathTestdetailsList1.addAll(bcaTestdetailsList1);
//                            listOfStudentDataItems = list.get(i).getTestList().get(0).getTestDetails();

                            if (!pathTestdetailsList1.isEmpty()) {
                                recyclerView.setVisibility(View.VISIBLE);
//                                txt_noreportbca.setVisibility(View.GONE);
                                deficienceAdapter = new DeficienceAdapter(context, pathTestdetailsList1);
//                                LinearLayoutManager layoutManager = new LinearLayoutManager(MyProfileActivity.this);
                                linearLayoutManager = new LinearLayoutManager(MyProfileActivity.this, LinearLayoutManager.VERTICAL, false);
//                                layoutManager.set(true);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(deficienceAdapter);
//                                recyclerView.setNestedScrollingEnabled(false);
                            } else {
//                                recyclerView.setVisibility(View.GONE);
//                                txt_noreportbca.setVisibility(View.VISIBLE);
//                                txt_noreportbca.setText("No Reports available");
                            }


                        } else {


//                            Toast.makeText(BloodReportActivity.this, "No Reports available", Toast.LENGTH_SHORT).show();
//                            finish();

                        }
                    } else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BCAResponce> call, Throwable t) {
                utils.hideProgressbar();
            }
        });
    }


    private void callToGetBCAReport1() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);////4186


        Call<BCAResponce> call = reeTestService.getAllBloodReportNewHistory(String.valueOf(userId));
        call.enqueue(new Callback<BCAResponce>() {
            @Override
            public void onResponse(Call<BCAResponce> call, Response<BCAResponce> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    BCAResponce bcaResponse = response.body();
                    utils.hideProgressbar();
                    if (bcaResponse != null && bcaResponse.getCode() == 1) {
                        mBCAList = bcaResponse.getData();

                        if (mBCAList != null && mBCAList.size() > 0) {
                            mBCAList_filter = new ArrayList<>();
                            mbloodList_filter = new ArrayList<>();
                            pathTestdetailsList1.clear();
                            pathTestdetailsList.clear();
                            pathTestList.clear();
                            bcaTestdetailsList1.clear();
                            bcaTestdetailsList.clear();

                            bcaTestList.clear();
                            pathTestList.clear();
                            List<TestList> TestList = null;
                            List<TestList> pathtestList = null;
                            for (int i = 0; i < mBCAList.size(); i++) {
                                if (mBCAList.get(i).getReportType().equalsIgnoreCase("BCA Test")) {
                                    mBCAList_filter.add(mBCAList.get(i));
                                } else {
                                    mbloodList_filter.add(mBCAList.get(i));

                                }


                            }


                            if (mBCAList_filter.get(0).getCollectedDate().contains("T")) {
                                try {
                                    int index = mBCAList_filter.get(0).getCollectedDate().indexOf("T");

                                    txt_def_date1.setText(" As on : " + formatDates(mBCAList_filter.get(0).getCollectedDate().substring(0, index)));
                                } catch (Exception e) {
                                    int index = mBCAList_filter.get(0).getCollectedDate().indexOf("T");

                                    txt_def_date1.setText((mBCAList_filter.get(0).getCollectedDate().substring(0, index)));

                                    e.printStackTrace();
                                }
                            }







                            if (mBCAList_filter.size() != 0) {


//                            for (int i = 0; i < mBCAList_filter.size(); i++) {

//                                TestList mBCAList_filter.get(i).getTestList();
                                TestList = (mBCAList_filter.get(0).getTestList());
//                                listOfStudentDataItems =mBCAList_filter.get(i).getTestList().get(0).getTestDetails();
                                bcaTestList.addAll(TestList);
//                                testdetailsList.addAll(listOfStudentDataItems);
//                            }Blood Group

                            }
                            for (int i = 0; i < bcaTestList.size(); i++) {

                                bcaTestdetailsList.addAll(bcaTestList.get(i).getTestDetails());
                            }

                            for (int i = 0; i < bcaTestdetailsList.size(); i++) {


//                                bcaTestdetailsList1.add(bcaTestdetailsList.get(i));
                                if (bcaTestdetailsList.get(i).getTestName().equals("Blood Group")) {
                                    txt_bloodgroup.setText("Blood group : " + bcaTestdetailsList.get(i).getTestValue());
                                }


                            }


                        } else {


//                            Toast.makeText(BloodReportActivity.this, "No Reports available", Toast.LENGTH_SHORT).show();
//                            finish();

                        }
                    } else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BCAResponce> call, Throwable t) {
                utils.hideProgressbar();
            }
        });
    }


    private void callTodaysPlanApi() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<ClsTodaysPlanMain> call = myPlansService.getTodaysPlan(userID, 0, postDate);
        call.enqueue(new Callback<ClsTodaysPlanMain>() {
            @Override
            public void onResponse(Call<ClsTodaysPlanMain> call, Response<ClsTodaysPlanMain> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsTodaysPlanMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

                        sendId = Integer.parseInt(listResponse.getData().getId());


                        callDietProfileAPi();

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ClsTodaysPlanMain> call, Throwable t) {
                utils.hideProgressbar();

            }
        });
    }


    private void callDietProfileAPi() {
        utils = new Utils();
        sessionManager = new SessionManager(context);

        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Call<ClsDietProfile> call = commonService.getDeitProfile(userID, sendId);
//        Call<ClsDietProfile> call = commonService.getDeitProfile(8331,47);
        call.enqueue(new Callback<ClsDietProfile>() {
            @Override
            public void onResponse(Call<ClsDietProfile> call, retrofit2.Response<ClsDietProfile> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsDietProfile moodResponse = response.body();

                        if (moodResponse != null) {


                            if (moodResponse.getRecentWeight() != null) {
//                                TextView txt_wt_recent=findViewById(R.id.txt_wt_recent);
                                txt_recentweight.setText("Recent weight : " + moodResponse.getRecentWeight()+ " Kgs");
                            } else {
//                                TextView txt_wt_recent=findViewById(R.id.txt_wt_recent);

                                txt_recentweight.setText("-");

                            }

                            if (moodResponse.getTargetWeight() != null) {
//                                TextView txt_targetweight=findViewById(R.id.txt_targetweight);
                                txt_targetweight.setText("Target weight : " + moodResponse.getTargetWeight() + " Kgs");
                            } else {
//                                TextView txt_targetweight=findViewById(R.id.txt_targetweight);
//
                                txt_targetweight.setText("-");

                            }

                            if (moodResponse.getBMI() != null) {
                                txt_BMI.setText("BMI : " + moodResponse.getBMI());
                            }

                            if (moodResponse.getHeight() != null) {
                                txt_height.setText("Height : " + moodResponse.getHeight() + "");
                            }

//                            if(moodResponse.getWeight()!=null){
////                                txt_wt.setText(""+moodResponse.getWeight()+"");
//
//                            }

                            callToGetBCAReport1();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<ClsDietProfile> call, Throwable t) {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }


    @SuppressLint("NewApi")
    private void showDatePickerDailogentry() {
        String strMindate[] = new SessionManager(MyProfileActivity.this).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_entry = new DatePickerDialog(MyProfileActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MyProfileActivity.this, year, month, day);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog_entry.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (strMindate!=null){
            if (strMindate.length>1){
                c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

            }
        }

        datepickerdialog_entry.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog_entry.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog_entry.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        datepickerdialog_entry.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                currentDateandTime = formatDatessubmit(year + "-" + (month + 1) + "-" + dayOfMonth);
                submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;

                txt_select_paramters_date.setText(dummydate_from);

                CallToGetRescoreData();


            }
        });


    }


    public String formatDatessubmit(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String getCurrentDates() {
        String date = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    private void CallToGetRescoreData() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        RescoreRequest request = new RescoreRequest();
        request.setUserid(userId);
        request.setHealthdate(submitFromDate);
        serviceree = Client.getClient().create(RescoreService.class);
        Call<RescoreResponse> call = serviceree.GetRescoreData(request);
        call.enqueue(new Callback<RescoreResponse>() {
            @Override
            public void onResponse(Call<RescoreResponse> call, Response<RescoreResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    RescoreResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
//                        imgNext.setVisibility(View.VISIBLE);
                        mDataList = listResponse.getData();
                        Log.d("RanbowData", mDataList.toString());

                        if (mDataList != null && mDataList.size() > 0)
                            mCurrentScore = mDataList.get(0).getFirstREEscoreInPercent();
                        mTodaysREEscore = mDataList.get(0).getTodaysREEscoreInPercent();
                        mFutureScore = mDataList.get(0).getFutureREEscoreInPercent();

                        if (mCurrentScore > 0) {
                            try {
                                txt_First_score.setText(String.valueOf(Math.round((mDataList.get(0).getFirstREEscoreInPercent()))));
//                                arcSeekBar.setProgress(Math.round(mCurrentScore));
                                pbar_first_reescore.setProgress(Math.round(mCurrentScore));
                            } catch (Exception e) {

                            }


                        }
                        if (mTodaysREEscore > 0) {
                            try {
                                txt_Todays_Score.setText(String.valueOf(Math.round((mDataList.get(0).getTodaysREEscoreInPercent()))));
                                //arcSeekBar.setProgress(Math.round(mTodaysREEscore));
//                                seekArc2.setProgress(Math.round(mTodaysREEscore));
                                pbar_recent_reescore.setProgress(Math.round(mTodaysREEscore));

                            } catch (Exception e) {

                            }


                        }
// Ajit added FutureScore

                        if (mFutureScore > 0) {
                            try {
                                tvFutureScore.setText(String.valueOf(Math.round((mDataList.get(0).getFutureREEscoreInPercent()))));
                                //arcSeekBar.setProgress(Math.round(mFutureScore));
//                                seekArc3.setProgress(Math.round(mFutureScore));
                                pbar_future_reescore.setProgress(Math.round(mFutureScore));

                            } catch (Exception e) {

                            }


                        }







                    /*    if (mDataList != null && mDataList.size() > 0)
                            mCurrentScore = mDataList.get(0).getTodaysREEscore();
                            mFirstCurrentScore = mDataList.get(0).getFirstREEscore();
                            mFutureScore = mDataList.get(0).getFutureREEscore();

                        if (mCurrentScore > 0) {
                            try {
                                txt_daily_score.setText(String.valueOf(Math.round((mDataList.get(0).getTodaysREEscore()))));
                                seekArc2.setProgress(Math.round(mCurrentScore));

                            } catch (Exception e) {

                            }


                        }
                        if (mFirstCurrentScore > 0) {
                            try {
                                tvCurrentScore.setText(String.valueOf(Math.round((mDataList.get(0).getFirstREEscore()))));
                                arcSeekBar.setProgress(Math.round(mFirstCurrentScore));

                            } catch (Exception e) {

                            }


                        }
// Ajit added FutureScore

                        if (mFutureScore > 0) {
                            try {
                                tvFutureScore.setText(String.valueOf(Math.round((mDataList.get(0).getFutureREEscore()))));
                                arcSeekBar.setProgress(Math.round(mFutureScore));

                            } catch (Exception e) {

                            }


                        }
*/

                    } else
                        Toast.makeText(MyProfileActivity.this, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                } else
                    ShowRetryBar("");
            }

            @Override
            public void onFailure(Call<RescoreResponse> call, Throwable t) {
                Log.e("ReescoreActivity", t.toString());
                utils.hideProgressbar();
                ShowRetryBar("");
            }
        });
    }


    private void ShowRetryBar(String msg) {

        imgNext.setVisibility(View.INVISIBLE);
        String strMessage;
        if (msg.isEmpty()) {
            strMessage = "No data available";
        } else {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Utils.isNetworkAvailable(context)) {
                            showLayouts();
                            CallToGetRescoreData();
                        } else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    public void showLayouts() {
        if (!Utils.isNetworkAvailable(context)) {
            mainLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        } else {
            mainLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }


    private void getMyplanApi() {
        utils1.showProgressbar(this);
        sessionManager1 = new SessionManager(context);
        Call<ClsExistingPlan> call = healthParametersService.getUserSubscriptionDetails(sessionManager1.getIntValue(SessionManager.KEY_USER_ID));
        call.enqueue(new Callback<ClsExistingPlan>() {
            @Override
            public void onResponse(Call<ClsExistingPlan> call, Response<ClsExistingPlan> response) {
                utils1.hideProgressbar();
                try {
                    if (response.code() == Client.RESPONSE_CODE_OK) {
                        ClsExistingPlan listResponse = response.body();

                        if (listResponse != null && listResponse.getCode() == 1) {

                            if (listResponse.getData().getSubPlanName() != null && !listResponse.getData().getSubPlanName().isEmpty()) {
                                txt_health_obj_name.setText(listResponse.getData().getPlanName() + "(" + listResponse.getData().getSubPlanName() + ")");

                            } else {
                                txt_health_obj_name.setText(listResponse.getData().getPlanName());

                            }
                            txt_health_obj_description.setText(listResponse.getData().getDesription());
                            txt_amount_health_obj.setText(listResponse.getData().getCurrency() + " " + Math.round(Double.valueOf(listResponse.getData().getPrice())));

                            TextView txt_plan_date = findViewById(R.id.txt_plan_date);
                            String startDate = listResponse.getData().getStartDate();
                            int index = startDate.indexOf("T");
                            String strnewStartDate = listResponse.getData().getStartDate().substring(0, index);

                            String endDate = listResponse.getData().getEndDate();
                            int index1 = endDate.indexOf("T");
                            String strnewEndDate = listResponse.getData().getEndDate().substring(0, index1);

                            txt_plan_date.setText(formatDates(strnewStartDate) + " To " + formatDates(strnewEndDate));
                            lst_objetive_plan.setAdapter(new HealthobjplanlistAdapter(MyProfileActivity.this, listResponse.getData().getServices()));


                        } else {
                            //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                            Log.d("Error---->", response.message());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ClsExistingPlan> call, Throwable t) {
                utils1.hideProgressbar();
            }
        });
    }


    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    private void getRegQuestionStepsApi() {
        regService = Client.getClient().create(RegistrationService.class);
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        Call<ClsRegQuenPojo> call = regService.getUserQ1Master();
        call.enqueue(new Callback<ClsRegQuenPojo>() {
            @Override
            public void onResponse(Call<ClsRegQuenPojo> call, Response<ClsRegQuenPojo> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsRegQuenPojo listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        if (listResponse.getData() != null) {
                            if (!listResponse.getData().isEmpty()) {

                                for (int i = 0; i < listResponse.getData().size(); i++) {

                                    if (listResponse.getData().get(i).getId() == 1) {
//                                        TextInputLayout txt_input_edtText_Reg_FirstName = findViewById(R.id.txt_input_edtText_Reg_FirstName);

//                                        txt_input_edtText_Reg_FirstName.setHint(listResponse.getData().get(i).getQuestion());
                                    }
                                    if (listResponse.getData().get(i).getId() == 2) {
//                                        TextInputLayout txt_input_edtText_Reg_LastName = findViewById(R.id.txt_input_edtText_Reg_LastName);

//                                        txt_input_edtText_Reg_LastName.setHint(listResponse.getData().get(i).getQuestion());
                                    }

                                    if (listResponse.getData().get(i).getId() == 3) {
//                                        TextInputLayout txt_input_edtText_Reg_Email = findViewById(R.id.txt_input_edtText_Reg_Email);

//                                        txt_input_edtText_Reg_Email.setHint(listResponse.getData().get(i).getQuestion());
                                    }

                                    if (listResponse.getData().get(i).getId() == 4) {

//                                        TextInputLayout txt_inputedtText_Reg_Mobile = findViewById(R.id.txt_inputedtText_Reg_Mobile);

//                                        txt_inputedtText_Reg_Mobile.setHint(listResponse.getData().get(i).getQuestion());
                                    }

                                    if (listResponse.getData().get(i).getId() == 5) {
//                                        TextView txt_gender = findViewById(R.id.txt_gender);

//                                        txt_gender.setText(listResponse.getData().get(i).getQuestion());
                                    }

                                    if (listResponse.getData().get(i).getId() == 6) {
                                        TextView txt_date_of_birth = findViewById(R.id.txt_date_of_birth);

                                        txt_date_of_birth.setText(listResponse.getData().get(i).getQuestion());
                                    }

                                }
                            }
                        }

                    } else {
//                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                    Log.e(TAG, response.message());
                }

            }

            @Override
            public void onFailure(Call<ClsRegQuenPojo> call, Throwable t) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rd_heath_profile != null) {
            rd_basic_profile.performClick();

        }

    }

    private void init() {
        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);
        sessionManager = new SessionManager(context);
        utils = new Utils();

        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            ll_paid_reecoach.setVisibility(View.GONE);
        } else {
            ll_paid_reecoach.setVisibility(View.VISIBLE);

        }
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        imageUrlNew = "";


        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");
        final Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
        final int year = calendar.get(Calendar.YEAR) ;
//        int month = calendar.get(Calendar.MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.set(calendar.get(Calendar.YEAR)+18,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
//                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 0);

        datepickerdialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);


        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        Calendar c1 = Calendar.getInstance();
//        if (strMindate!=null){
//            if (strMindate.length>1){
//                datepickerdialog.updateDate(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
//
//            }
//        }

        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);




//                calendar.set(year,month,day);

            }
        });

//        long time = calendar.getTimeInMillis();
//        datepickerdialog.getDatePicker().setMaxDate(time);
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
        tvTitle.setText("My Profile");
        //tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        rd_heath_profile = findViewById(R.id.rd_heath_profile);

        ll_country = findViewById(R.id.ll_country);
        ll_state = findViewById(R.id.ll_state);
        ll_city = findViewById(R.id.ll_city);
        ll_pincode = findViewById(R.id.ll_pincode);
        ll_language = findViewById(R.id.ll_language);
        editText_Address = findViewById(R.id.edtText_MyProfile_Address);

        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            editText_Address.setVisibility(View.GONE);
            ll_state.setVisibility(View.GONE);
            ll_city.setVisibility(View.GONE);
            ll_pincode.setVisibility(View.GONE);
            ll_language.setVisibility(View.GONE);
            ll_country.setVisibility(View.GONE);
        } else {

            editText_Address.setVisibility(View.VISIBLE);
            ll_state.setVisibility(View.GONE);
            ll_city.setVisibility(View.GONE);
            ll_pincode.setVisibility(View.GONE);
            ll_language.setVisibility(View.GONE);
            ll_country.setVisibility(View.GONE);
        }


        imageViewProfile = findViewById(R.id.imageView_UpdateProfile_Image);
        imageViewCamera = findViewById(R.id.imageView_UpdateProfile_Camera);
//        editText_FirstName = findViewById(R.id.edtText_MyProfile_FirstName);
//        editText_LastName = findViewById(R.id.edtText_MyProfile_LastName);
        editText_Email = findViewById(R.id.edtText_MyProfile_Email);
        editText_Mobile = findViewById(R.id.edtText_MyProfile_Mobile);
        editText_Mobile.addTextChangedListener(this);
        textView_DOB = findViewById(R.id.textView_MyProfile_DOB);
        radioButton_Male = findViewById(R.id.radioButton_MyProfile_Male);
        radioButton_Female = findViewById(R.id.radioButton_MyProfile_Female);
        radioButton_Transgender = findViewById(R.id.radioButton_MyProfile_Transgender);
        edt_pincode = findViewById(R.id.edt_pincode);
        progressBar = findViewById(R.id.progress);

        tvCountry = findViewById(R.id.tvCountry);
        tvState = findViewById(R.id.tvState);
        tvCity = findViewById(R.id.tvCity);
        tvLanguage = findViewById(R.id.tvLanguage);

        btnSubmit = findViewById(R.id.buttonSubmit_MyProfile);

        btnSubmit.setOnClickListener(this);
        imageViewProfile.setOnClickListener(this);
        imageViewCamera.setOnClickListener(this);

        tvCountry.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);
        textView_DOB.setOnClickListener(this);

//        user_name.setText(sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME)+" "+sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME));

        if (!TextUtils.isEmpty(sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME)))
//            editText_FirstName.setText(sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME));

            if (!TextUtils.isEmpty(sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME)))
//            editText_LastName.setText(sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME));

                if (!TextUtils.isEmpty(sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL)))
                    editText_Email.setText(sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL));

        if (!TextUtils.isEmpty(sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO)))
            editText_Mobile.setText("+91" + sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO));


        if (!TextUtils.isEmpty(sessionManager.getStringValue(SessionManager.KEY_USER_ADDRESS)))
            editText_Address.setText(sessionManager.getStringValue(SessionManager.KEY_USER_ADDRESS));

        if (!TextUtils.isEmpty(sessionManager.getStringValue(SessionManager.KEY_USER_DOB)))

            if (!sessionManager.getStringValue(SessionManager.KEY_USER_DOB).equalsIgnoreCase("01/01/0001")) {
//                textView_DOB.setText(sessionManager.getStringValue(SessionManager.KEY_USER_DOB));

            }


        Log.d("dob", sessionManager.getStringValue(SessionManager.KEY_USER_DOB));

        int gender = sessionManager.getIntValue(SessionManager.KEY_USER_GENDER);

        switch (gender) {
            case 1:
                radioButton_Male.setChecked(true);
                break;
            case 2:
                radioButton_Female.setChecked(true);
                break;
            case 3:
                radioButton_Transgender.setChecked(true);
                break;
        }

//        country_id = sessionManager.getIntValue(SessionManager.KEY_USER_COUNTRY_ID);
//        state_id = sessionManager.getIntValue(SessionManager.KEY_USER_STATE_ID);
//        city_id = sessionManager.getIntValue(SessionManager.KEY_USER_CITY_ID);
//        lang_code = sessionManager.getStringValue(SessionManager.KEY_USER_LANGUAGE_CODE);

        // call your webApi
        if (Utils.isNetworkAvailable(context)) {
            callGetProfileDataApi(userID);
            callCountryApi();

        } else {
            ShowRetryBar();
        }

        setProfileImage();

        registerForContextMenu(imageViewCamera);

//        editText_FirstName.setFocusable(false);
//        editText_LastName.setFocusable(false);
        editText_Email.setFocusable(false);
//        editText_Mobile.setFocusable(false);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm;
        Bundle bundle;

        switch (view.getId()) {
            case R.id.tvCountry:

                fm = getSupportFragmentManager();
                countryDialog = new CountryDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", countryList);
                countryDialog.setArguments(bundle);
                countryDialog.show(fm, "COUNTRY_FRAGMENT");
                break;

            case R.id.tvState:

                if (country_id <= 0)
                    Snackbar.make(findViewById(android.R.id.content), "Select country first", Snackbar.LENGTH_SHORT).show();
                else {
                    fm = getSupportFragmentManager();
                    stateDialog = new StateDialog();
                    bundle = new Bundle();
                    bundle.putSerializable("STATE_LIST", stateList);
                    stateDialog.setArguments(bundle);
                    stateDialog.show(fm, "STATE_FRAGMENT");
                }
                break;

            case R.id.tvCity:

                if (state_id <= 0)
                    Snackbar.make(findViewById(android.R.id.content), "Select state first", Snackbar.LENGTH_SHORT).show();
                else {
                    fm = getSupportFragmentManager();
                    cityDialog = new CityDialog();
                    bundle = new Bundle();
                    bundle.putSerializable("CITY_LIST", cityList);
                    cityDialog.setArguments(bundle);
                    cityDialog.show(fm, "CITY_FRAGMENT");
                }
                break;

            case R.id.tvLanguage:

                fm = getSupportFragmentManager();
                languageDialog = new LanguageDialog();
                bundle = new Bundle();
                bundle.putSerializable("LANGUAGE_LIST", languageList);
                languageDialog.setArguments(bundle);
                languageDialog.show(fm, "LANGUAGE_FRAGMENT");
                break;

            case R.id.buttonSubmit_MyProfile:

//                if (true){
//                    startActivity(new Intent(this, ChangePasswordActivity.class));
//                    return;
//                }


//                firstName = editText_FirstName.getText().toString();
//                lastName = editText_LastName.getText().toString();
                email = editText_Email.getText().toString();
                mobile = editText_Mobile.getText().toString();
                address = editText_Address.getText().toString().trim();
//                DOB = textView_DOB.getText().toString().trim();

                boolean dataIsValid = validateAllData(firstName, lastName, email, mobile, address, DOB);

                if (dataIsValid) {
                    callUpdateProfileApi(address, country_id, state_id, city_id, lang_code, DOB);
                } else {
                    Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                    errorMsg = "";
                }
                break;

            case R.id.imageView_UpdateProfile_Image:


                opens(imageUrl);


                break;


            case R.id.imageView_UpdateProfile_Camera:
                openContextMenu(imageViewCamera);
                break;

            case R.id.textView_MyProfile_DOB:
                datepickerdialog.show();
















                break;

            case R.id.textView_Reg_DOB:
                datepickerdialog.show();
                break;


            default:
        }
    }

    private void opens(String finalUrl) {
        if (!finalUrl.isEmpty()) {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);

            dialog.setContentView(R.layout.dailg_fill_before_after);
            dialog.setCancelable(true);
            TouchImageView img_full_screen = dialog.findViewById(R.id.img_full_screen);
            TextView txt_medicine_name = dialog.findViewById(R.id.txt_medicine_name);
            txt_medicine_name.setSelected(true);
            ImageView img_close_dailog = dialog.findViewById(R.id.img_close_dailog);
            final RelativeLayout rel_med_header = dialog.findViewById(R.id.rel_med_header);
            img_close_dailog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Glide.with(context)

                    .load(finalUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            rel_med_header.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
//                                            .apply(options)
                    .into(img_full_screen);

            dialog.show();
        }
    }

    /* Get Country click here */
    @Override
    public void onSubmitCountryData(Country model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null) {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null) {
            country_id = model.getCountryId();

            if (!TextUtils.isEmpty(model.getCountryName()))
                tvCountry.setText(model.getCountryName());
            else
                tvCountry.setText("Select country");

            if (country_id > 0) {
                callStateApi(country_id);
            }
        }
    }

    /* Get state click here */
    @Override
    public void onSubmitStateData(State model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("STATE_FRAGMENT");
        if (fragment != null) {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null) {
            state_id = model.getStateId();

            if (!TextUtils.isEmpty(model.getStateName()))
                tvState.setText(model.getStateName());
            else
                tvCountry.setText("Select state");

            if (state_id > 0) {
                callCityApi(state_id);
            }
        }
    }

    /* Get city click here */
    @Override
    public void onSubmitCityeData(City model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("CITY_FRAGMENT");
        if (fragment != null) {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null) {
            city_id = model.getCityId();

            if (!TextUtils.isEmpty(model.getCityName()))
                tvCity.setText(model.getCityName());
            else
                tvCity.setText("Select city");
        } else
            city_id = 0;
    }

    /* Get Language click here */
    @Override
    public void onSubmitLanguageeData(Language model) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("LANGUAGE_FRAGMENT");
        if (fragment != null) {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null) {
            lang_code = model.getLangCode();

            if (!TextUtils.isEmpty(model.getLangName()))
                tvLanguage.setText(model.getLangName());
            else
                tvLanguage.setText("Select language");
        } else
            lang_code = "";
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.title_image_picker);
        menu.add(0, v.getId(), 0, getString(R.string.camera));
        menu.add(0, v.getId(), 0, getString(R.string.gallery));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.camera))) {
            if (!checkIfAlreadyHavePermission()) {
                isCamera = true;

                callExterpermssion();


            } else {
                isCamera = true;

                Dexter.withActivity(MyProfileActivity.this)
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
        } else if (item.getTitle().equals(getString(R.string.gallery))) {

            Dexter.withActivity(MyProfileActivity.this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            // permission is granted, open the camera

                            fileChooser();
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
            return false;
        }
        return true;
    }

    private void callExterpermssion() {
        Dexter.withActivity(MyProfileActivity.this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera

                        Dexter.withActivity(MyProfileActivity.this)
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

    /**
     * Check if app having permissions for External storage
     *
     * @return
     */

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

    private boolean checkIfAlreadyHavePermission() {

        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(MyProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_READ_PERMISSION_GRANT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_READ_PERMISSION_GRANT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (isCamera)
                        CallCameraIntent();
                    else
                        fileChooser();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied !", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void CallCameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.jpg");
        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void fileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int dataSize = 0;

        if (requestCode == 206 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("Reeacoach")) {
                if (data.getStringExtra("Reeacoach").equals("yes")) {
                    if (Utils.isNetworkAvailable(context)) {
                        callProfileDetailsAPI();

                    } else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == FILE_SELECT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();


                try {
                    String scheme = selectedImage.getScheme();
                    System.out.println("Scheme type " + scheme);
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                        try {
                            InputStream fileInputStream = getContentResolver().openInputStream(selectedImage);
                            dataSize = fileInputStream.available();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        System.out.println("File size in bytes"+dataSize);
                        //                    bytesToMeg(dataSize);
//                        Toast.makeText(context, ""+bytesToMeg(dataSize), Toast.LENGTH_SHORT).show();


                        if (bytesToMeg(dataSize) > 5) {
                            Toast.makeText(context, "Please select image of less than 5 mb", Toast.LENGTH_SHORT).show();
                            return;

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    int rotate = Utils.getCameraPhotoOrientation(context, selectedImage, picturePath);

                    uploadFile(new File(picturePath), userID);
                    isImage = true;
                }


                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                int rotate = Utils.getCameraPhotoOrientation(context, selectedImage, picturePath);

                if (picturePath != null) {
                    try {
                        uploadFile(new File(picturePath), userID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                isImage = true;
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {


                String path = null;
                try {
                    path = getRealPathFromURI(mCapturedImageURI);

                    String scheme = mCapturedImageURI.getScheme();
                    System.out.println("Scheme type " + scheme);
                    if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                        try {
                            InputStream fileInputStream = getContentResolver().openInputStream(mCapturedImageURI);
                            dataSize = fileInputStream.available();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (bytesToMeg(dataSize) > 5) {
                            Toast.makeText(context, "Please select image of less than 5 mb", Toast.LENGTH_SHORT).show();
                            return;

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    int rotate = Utils.getCameraPhotoOrientation(context, mCapturedImageURI, path);

                    uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
                    isImage = true;
                }


                int rotate = Utils.getCameraPhotoOrientation(context, mCapturedImageURI, path);

                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
                isImage = true;
            }
        }
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMeg(long bytes) {
        return bytes / MEGABYTE;
    }


    // Uploading Image/Video
    private void uploadFile(File file, int userId) {
        utils.showProgressbar(context);

        try {
            File fileTemp = new Compressor(this).compressToFile(file);


           /* RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileTemp);

            MultipartBody.Part photo = MultipartBody.Part.createForlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllmData("image", fileTemp.getName(), photoContent);

            BeforeAfterService uploadService = FitBitClient.getClientMultiPart().create(BeforeAfterService.class);
            RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), ""+userId);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), fileTemp.getName());

            FoodService foodService = FitBitClient.getClientMultiPart().create(FoodService.class);

            Call<BeforeAfterResponse> call = foodService.Upload(photo, user_Id, filename);*/


            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileTemp);

            MultipartBody.Part photo = MultipartBody.Part.createFormData("Image", fileTemp.getName(), photoContent);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), fileTemp.getName());
            RequestBody userIdbody = RequestBody.create(MediaType.parse("text/plain"), "" + userId);

            MyProfileService uploadService = Client.getClientMultiPart().create(MyProfileService.class);

            uploadService.Upload(userIdbody, photo, filename).enqueue(new Callback<MyProfileResponse>() {
                @Override
                public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getCode() == 1) {
                                imageUrl = response.body().getImageUrl();
                                imageUrlNew = response.body().getImageUrl();


                                img_blur = findViewById(R.id.img_blur);
                                Glide.with(context).load(imageUrl)
                                        .apply(bitmapTransform(new BlurTransformation(22)))
                                        .into(img_blur);


                                sessionManager.setStringValue(SessionManager.KEY_USER_PROFILE_IMAGE, imageUrl);

                                if (isValidContextForGlide(context)) {
                                    Glide.with(context)
                                            .load(imageUrl)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .apply(RequestOptions.circleCropTransform()
                                                    .skipMemoryCache(true)
                                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                    .error(R.drawable.ic_profile_pic_bg))
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
                                            .into(imageViewProfile);
                                }
                                utils.hideProgressbar();
                                //Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Server : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                utils.hideProgressbar();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Server : " + response.message(), Toast.LENGTH_SHORT).show();
                            utils.hideProgressbar();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Server : " + response.code(), Toast.LENGTH_SHORT).show();
                        utils.hideProgressbar();
                    }
                }

                @Override
                public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                    utils.hideProgressbar();
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateAllData(String firstName, String lastName, String email, String mobile,
                                    String address, String dob) {
        boolean valid = true;
//        String errorMsg = "";

//        if (firstName.isEmpty()) {
//            valid = false;
//            errorMsg = "Please enter first name";
//        }
//
//        if (valid) {
//            if (lastName.isEmpty()) {
//                valid = false;
//                errorMsg = "Please enter last name";
//            }
//        }


        buttonCheckChanged();
        if (valid) {
            if (email.isEmpty()) {
                valid = false;
                errorMsg = "Email ID can not be blank";
            } else if (email.length() > 255) {
                valid = false;
                errorMsg = "Email ID Max 255 characters";
            } else if (!Utils.isValidEmail(email)) {
                valid = false;
                errorMsg = "Please enter valid email ID";
            }
        }

        if (valid) {
            if (mobile.isEmpty()) {
                valid = false;
                errorMsg = "Mobile Number can not be blank";
            } else if (mobile.length() < 13) {
                valid = false;
                errorMsg = "Please enter 10 digit Mobile Number";
            } else if (!Utils.isValidMobileNo(mobile)) {
                valid = false;
                errorMsg = "Enter valid mobile number";
            }
        }

//        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")||sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
//            if (valid)
//            {
//                if (address.isEmpty())
//                {
//                    valid = false;
//                    errorMsg = "Enter the address";
//                }
//            }
//        }


//        if (valid)
//        {
//            if (dob.isEmpty())
//            {
//                valid = false;
//                errorMsg = "Select Date of birth";
//            }
//        }

//        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")||sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
//            if (valid)
//            {
//                if (country_id <= 0)
//                {
//                    valid = false;
//                    errorMsg = "Please select Country";
//                }
//            }
//
//            if (valid)
//            {
//                if (state_id <= 0)
//                {
//                    valid = false;
//                    errorMsg = "Please select state";
//                }
//            }
//
//            if (valid)
//            {
//                if (city_id <= 0)
//                {
//                    valid = false;
//                    errorMsg = "Please select City";
//                }
//            }
//
//            if (valid)
//            {
//                if (lang_code!=null&&lang_code.equalsIgnoreCase("-1"))
//                {
//                    valid = false;
//                    errorMsg = "Please select Language";
//                }
//            }
//
//            if (valid){
//                if (edt_pincode.getText().toString().trim().isEmpty()){
//                    valid = false;
//                    errorMsg = "Please enter pincode";
//                }
//
//                if (!edt_pincode.getText().toString().trim().isEmpty()){
//                    if (edt_pincode.getText().toString().trim().length()<6){
//                        valid = false;
//                        errorMsg = "Please enter correct pincode";
//                    }
//                }
//            }
//        }


        if (!errorMsg.isEmpty()) {
            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void buttonCheckChanged() {
        if (radioButton_Male.isChecked()) {
            gender = "1";
        } else if (radioButton_Female.isChecked()) {
            gender = "2";
        } else if (radioButton_Transgender.isChecked()) {
            gender = "3";
        }
    }


    private void callUpdateProfileApi(final String address, int country_id, int state_id, int city_id,
                                      String lang_code, String dob) {


        if (textView_DOB.getText().toString().trim().isEmpty() && edt_age.getText().toString().isEmpty()) {
            Toast.makeText(context, "Select either Date of birth  or enter the Age ", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setUserID(String.valueOf(userID));
        request.setCountry_id(String.valueOf(country_id));
        request.setState_id(String.valueOf(state_id));
        request.setCity_id(String.valueOf(city_id));
        request.setLang_code(lang_code);
        request.setAddress(address);

        if (IsAge) {
            if (!edt_age.getText().toString().isEmpty()) {
                request.setAge(Integer.parseInt(edt_age.getText().toString()));

            } else {
                request.setAge(0);

            }

            if (Integer.parseInt(edt_age.getText().toString()) < 18) {
                Toast.makeText(context, "You must be 18 years of age or older to use this app.", Toast.LENGTH_LONG).show();
                return;
            }

            request.setdOB("");

        } else {

            request.setdOB(DOB);
            request.setAge(0);

        }

        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }
        request.setImageUrl(imageUrl);
        request.setPincode(edt_pincode.getText().toString().trim());
        request.setAboutme(edt_about_me.getText().toString());

        Call<UpdateProfileResponse> call = regService.setUpdateProfileData(request);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    UpdateProfileResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        saveAddress();  // to get updated address for ScheduleBloodTest
                        finish();
                    } else {
                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callGetProfileDataApi(int userId) {
        try {
            if (!((Activity) context).isFinishing()) {
                utils.showProgressbar(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
            utils.hideProgressbar();
        }

        ProfileDataRequest request = new ProfileDataRequest();
        request.setUserid(userId);

        Call<ProfileDataResponse> call = regService.getProfileData(request);
        call.enqueue(new Callback<ProfileDataResponse>() {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response) {
                utils.hideProgressbar();
                Log.w(TAG, response.toString());

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ProfileDataResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        ProfileDataResponse.DataResponse data = listResponse.getData();
                        if (data.getAboutme() != null) {
                            edt_about_me.setText(data.getAboutme());

                        }

                        if (data.getBloodGroup() != null) {
                            edtText_blood.setText(data.getBloodGroup());

                        }

                        String USER_F_NAME = data.getFirstName();
                        String USER_L_NAME = data.getLastName();
                        String USER_EMAIL = data.getEmail();
                        String USER_MOBILE_NO = data.getMobileNo();
                        String USER_DOB = data.getDOB();
                        Log.d("My Profile_Data", USER_F_NAME);
                        TextView nametxt = findViewById(R.id.nametxt);
                        nametxt.setText(USER_F_NAME + " " + USER_L_NAME);

                        if (USER_DOB != null && !USER_DOB.isEmpty()) {
                            if (!USER_DOB.equalsIgnoreCase("01/01/0001")) {
                                textView_DOB.setText(formatDatesSleep(USER_DOB));
                                String strMindate[]=USER_DOB.split("/");

                                datepickerdialog.updateDate(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));

                            }
                        }
                        TextView uniqueid = findViewById(R.id.uniqueid);
                        uniqueid.setText("Unique Id : " + data.getUniqueId());


                        int USER_GENDER = data.getGender();
                        String USER_ADDRESS = data.getAddress();
                        int USER_COUNTRY_ID = 0;
                        int USER_STATE_ID = 0;
                        int USER_CITY_ID = 0;
                        String USER_LANGUAGE_CODE = data.getLangCode();
                        String USER_TOKEN = data.getToken();
                        String USER_Image = data.getImageUrl();
                        String COUNTRY_NAME = data.getCountryName();
                        String STATE_NAME = data.getStateName();
                        String CITY_NAME = data.getCityName();
                        int AGE = 0;
                        try {
                            AGE = data.getAge();
                            if (AGE != 0) {
                                edt_age.setText(String.valueOf(AGE));

                            }

                            if (AGE > 0) {
                                IsAge = true;
                                rd_btn_age.performClick();
                            } else {
                                IsAge = false;

                                rd_btn_dob.performClick();
                            }

                        } catch (Exception e) {

                        }

                        String PINCODE = "";
                        if (data.getPincode() != null) {
                            PINCODE = data.getPincode();

                        }


                        sessionManager.saveProfileData(USER_F_NAME, USER_L_NAME, USER_EMAIL, USER_MOBILE_NO, USER_DOB,
                                USER_GENDER, USER_ADDRESS, USER_COUNTRY_ID, USER_STATE_ID, USER_CITY_ID, USER_LANGUAGE_CODE,
                                USER_TOKEN, USER_Image, COUNTRY_NAME, STATE_NAME, CITY_NAME);
                        country_id = USER_COUNTRY_ID;
                        state_id = USER_STATE_ID;
                        city_id = USER_CITY_ID;
                        lang_code = USER_LANGUAGE_CODE;
                        imageUrl = USER_Image;
                        img_blur = findViewById(R.id.img_blur);
                        Glide.with(context).load(imageUrl)
                                .apply(bitmapTransform(new BlurTransformation(22)))
                                .into(img_blur);


                        if (!TextUtils.isEmpty(PINCODE)) {
                            edt_pincode.setText(PINCODE);

                        }

                        if (!TextUtils.isEmpty(USER_F_NAME))
//                            editText_FirstName.setText(USER_F_NAME);

                            if (!TextUtils.isEmpty(USER_L_NAME))
//                            editText_LastName.setText(USER_L_NAME);

                                if (!TextUtils.isEmpty(USER_EMAIL))
                                    editText_Email.setText(USER_EMAIL);

                        if (!TextUtils.isEmpty(USER_MOBILE_NO))
                            editText_Mobile.setText(USER_MOBILE_NO);

                        if (!TextUtils.isEmpty(USER_ADDRESS))
                            editText_Address.setText(USER_ADDRESS);

                        if (!TextUtils.isEmpty(COUNTRY_NAME)) {
                            tvCountry.setText(COUNTRY_NAME);

                        } else {
//                            tvCountry.setText("India");

                        }

                        if (!TextUtils.isEmpty(STATE_NAME))
                            tvState.setText(STATE_NAME);

                        if (!TextUtils.isEmpty(CITY_NAME))
                            tvCity.setText(CITY_NAME);


                        if (!TextUtils.isEmpty(STATE_NAME)) {
                            tvState.setText(STATE_NAME);

                        }


                    } else {
                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileDataResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callCountryApi() {

        Call<CountryResponse> call = commonService.getCountryList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    CountryResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {
                        countryList = response.body().getData();

                        if (country_id > 0)
                            callStateApi(country_id);

                        if (state_id > 0)
                            callCityApi(state_id);

                        callLanguageApi();
                    } else {
                        ShowRetryBar(listResponse);
                    }
                } else {
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                utils.hideProgressbar();
                Log.e(TAG, t.toString());
            }

            private void ShowRetryBar(CountryResponse listResponse) {
                String strMessage;
                if (listResponse.getMessage() != null) {
                    strMessage = listResponse.getMessage();
                } else {
                    strMessage = "Unable to load data";
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callCountryApi();
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void callStateApi(int countryId) {
        StateRequest request = new StateRequest();
        request.setCountryId(countryId);

        Call<StateResponse> call = commonService.getStateList(request);
        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                stateList = response.body().getData();

                if (stateList == null) {
                    state_id = 0;
                    city_id = 0;
                    Toast.makeText(context, "States not available for this country", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    private void callCityApi(int stateid) {
        CityRequest request = new CityRequest();
        request.setStateId(stateid);

        Call<CityResponse> call = commonService.getCityList(request);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                cityList = response.body().getData();

                if (cityList == null) {
                    city_id = 0;
                    Toast.makeText(context, "City not available for this state", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    private void callLanguageApi() {
        Call<LanguageResponse> call = commonService.getLanguageList();
        call.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                utils.hideProgressbar();

                String language = "";
                String langCode = sessionManager.getStringValue(SessionManager.KEY_USER_LANGUAGE_CODE);

                if (response.body() != null) {
                    languageList = response.body().getData();

                    if (languageList != null && languageList.size() > 0) {
                        for (int i = 0; i < languageList.size(); i++) {
                            if (languageList.get(i).getLangCode().trim().equals(langCode.trim())) {
                                language = languageList.get(i).getLangName();
                                break;
                            }
                        }
                        if (!TextUtils.isEmpty(language)) {
                            tvLanguage.setText(language);
                            sessionManager.setStringValue(SessionManager.KEY_USER_LANGUAGE, language);
                        }
                    }
                } else {
                    Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t) {
                utils.hideProgressbar();
                Log.e(TAG, t.toString());
            }
        });
    }


    private void ShowRetryBar() {
        String strMessage = "Unable to load data";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callGetProfileDataApi(userID);
                    }
                });
        snackbar.show();
    }

    private void setProfileImage() {
        imageUrl = "";
        imageUrl = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);


        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);

        if (isValidContextForGlide(context)) {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(options.circleCrop())
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
                    .into(imageViewProfile);
        }
    }

    /**
     * This method is used to get real path of file from from uri
     *
     * @param contentUri
     * @return String
     */
    //----------------------------------------
    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    private int getAge(String dobString) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dayOfMonth + "/" + (month + 1) + "/" + year;
        if (getAge(dateString) < 18) {

            Toast.makeText(context, "You must be 18 years of age or older to use this app.", Toast.LENGTH_LONG).show();
            return;


        } else {


//            textView_DOB.setText(dateString);

        }


        date = String.format(Locale.getDefault(), "%02d | %02d | %04d", dayOfMonth, month + 1, year);
        textView_DOB.setText(date);
        datepickerdialog.updateDate(year,month+1,dayOfMonth);
        // change date format for webAPI
        date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
        DOB = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text_value = editText_Mobile.getText().toString().trim();
        if (text_value.equalsIgnoreCase("+91")) {
            editText_Mobile.setText("");
        } else {
            if (!text_value.startsWith("+91") && text_value.length() > 0) {
                editText_Mobile.setText("+91" + charSequence.toString());
                Selection.setSelection(editText_Mobile.getText(), editText_Mobile.getText().length());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public class ServerResponse {
        // variable name should be same as in the json response from php    @SerializedName("success")
        @SerializedName("success")
        String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public void saveAddress() {
        sessionManager.setStringValue(SessionManager.KEY_USER_ADDRESS, editText_Address.getText().toString());
    }


    private void callProfileDetailsAPI() {

        email = sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        ReecoachDetailsRequest request = new ReecoachDetailsRequest();
        request.setEmail(email);
        request.setUserid(userId);


        ReecoachService reecoachService;
        reecoachService = Client.getClient().create(ReecoachService.class);

        Call<ReecoachDetailsResponse> call = reecoachService.getProfileDetails(request);
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
                            if (dataResponse.getFirstName() != null && !dataResponse.getFirstName().isEmpty()) {
                                rel_reecoach_profile_data.setVisibility(View.VISIBLE);
                                txt_select_reecoach.setVisibility(View.GONE);

                                String reecoachName = dataResponse.getFirstName() + " " + dataResponse.getLastName();
                                String reecoachMobile = dataResponse.getMobileNo();
                                String reecoachEmail = dataResponse.getEmail();
                                String reecoachAddress = dataResponse.getAddress();
                                String imgUrl = dataResponse.getImageUrl();


                                TextView txt_reecoach_name = findViewById(R.id.txt_reecoach_name);
                                TextView txt_reecoach_address = findViewById(R.id.txt_reecoach_address);
                                txt_reecoach_name.setText(reecoachName);
                                txt_reecoach_address.setText(reecoachAddress);
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_profile_pic_bg)
                                        .error(R.drawable.ic_profile_pic_bg)
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .priority(Priority.HIGH);


                                ImageView img_reecoach_image = findViewById(R.id.img_reecoach_image);


                                if (isValidContextForGlide(context)) {
                                    // for background Image
                                    Glide.with(context)
                                            .load(imgUrl)
                                            .apply(options)
                                            .into(img_reecoach_image);

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
                                            .into(img_reecoach_image);
                                }
                            } else {
                                rel_reecoach_profile_data.setVisibility(View.GONE);

                            }


                        } else {

                            rel_reecoach_profile_data.setVisibility(View.GONE);
                            txt_select_reecoach.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        rel_reecoach_profile_data.setVisibility(View.GONE);
                        txt_select_reecoach.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    rel_reecoach_profile_data.setVisibility(View.GONE);
                    txt_select_reecoach.setVisibility(View.VISIBLE);
//                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<ReecoachDetailsResponse> call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
                ShowRetryBar();
            }
        });
    }


    @Override
    public void finish() {
        if (!imageUrlNew.isEmpty()) {
            Intent intent = new Intent();

            Bundle bundle = new Bundle();
            bundle.putString("image", imageUrlNew);
            intent.putExtras(bundle);

            setResult(RESULT_OK, intent);
        } else {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
        }
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void getReecaochTypeData() {


        utils.showProgressbar(this);
        reecoachService = Client.getClient().create(ReecoachService.class);

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
                                    Intent intent = new Intent(MyProfileActivity.this, ChangeReecoachActivity.class);
                                    intent.putExtra("KEY_ReecoachMasterType", moodResponse);
                                    startActivityForResult(intent, 206);
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

    /* ----------------EXTRAAA------------------- */

    //        else if (requestCode == IMAGE_CROP) {
//            // crop activity result
//            try {
//                if (requestCode == IMAGE_CROP) {
//                    String image_name = data.getStringExtra("image");
//                    File bitmap = new ImageSaver(context).
//                            setFileName(image_name).
//                            getFilepath();
//
//                    imageViewProfile.setImageBitmap(new ImageSaver(context).
//                            setFileName(image_name).
//                            load());
//                    uploadAlbum(bitmap, false);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                /*String errorMessage = "Whoops - your device doesn't support the crop action!";
//                Contants2.showToastMessage(context, errorMessage, true);*/
//                // toast.show();
//            }
//
//        }

    //imageViewProfile.setImageBitmap(rotateImage(rotate, compressBitmap(BitmapFactory.decodeFile(path))));

//                Intent intent = new Intent(context, BasicCropActivity.class);
//                File imageFile = new File(getRealPathFromURI(mCapturedImageURI));
//                intent.setData(Uri.fromFile(imageFile));
//                startActivityForResult(intent, IMAGE_CROP);

//                Glide.with(context).clear(imageViewProfile);
//                Glide.with(context).load(path).into(imageViewProfile);
     /*  spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        spinnerCity = findViewById(R.id.spinnerCity);*/
    /*  private void uploadFileNew(File file, int userId)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.33/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Map is used to multipart the file using okhttp3.RequestBody
//        File file = new File(strMediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("video_keyword", file.getName(), requestBody);

//        RequestBody param_filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody param_userId = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(userId));

        CommonService commonService = retrofit.create(CommonService.class);
        Call<ServerResponse> call = commonService.uploadImageNew(fileToUpload, param_userId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (!serverResponse.getMessage().isEmpty()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }*/

    //        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        MediaType mediaType = MediaType.parse("multipart/form-data");
//        CommonService commonServiceAA = retrofit.create(CommonService.class);
//        RequestBody requestBody = RequestBody.create(mediaType, file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("Image", file.getName(), requestBody);
//        RequestBody param_userId = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(userId));
//        Call<MyProfileResponse> call = commonServiceAA.uploadImage("multipart/form-data", fileToUpload, String.valueOf(userId)); //, param_userId);
//        call.enqueue(new Callback<MyProfileResponse>() {
//            @Override
//            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
//                progressDialog.dismiss();
//                MyProfileResponse myProfileResponse = response.body();
//                if (myProfileResponse != null) {
//                    if (!myProfileResponse.getMessage().isEmpty()) {
//                        Toast.makeText(getApplicationContext(), myProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), myProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    if (response != null) {
////                        Log.v("Response", "Server : " + response.raw().code() + " - " + response.raw().message());
//                        Toast.makeText(context,
//                                "Server : " + response.raw().code() + " - " + response.raw().message(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context,
//                                "Server : " + response.raw().code() + " - " + response.raw().message(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
//                Log.v("Response", "Server onFailure: " + t.getMessage());
//            }
//        });

     /*      spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (countryAdapter != null)
                {
                   *//* Country country = countryAdapter.getItem(i);
                    country_id = country.getCountryId();
                    callStateApi(country_id);*//*
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (stateAdapter != null)
                {
                    State state = stateAdapter.getItem(i);
                    state_id = state.getStateId();
                    callCityApi(state_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
*/

/*
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (cityAdapter != null)
                {
                    City country = cityAdapter.getItem(i);
                    city_id = country.getCityId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
*/

       /* spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (languageAdapter != null)
                {
                    Language country = languageAdapter.getItem(i);
                    lang_code = country.getLangCode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });*/
        /*CountryAdapter countryAdapter;
    StateAdapter stateAdapter;
    CityAdapter cityAdapter;
    LanguageAdapter languageAdapter;*/


    private void Callprofileheader() {

        service = Client.getClient().create(HomeFragmentService.class);

        Call<ClsProfileHeaderData> call = service.getProfileHeaderApi();

        call.enqueue(new Callback<ClsProfileHeaderData>() {
            @Override
            public void onResponse(Call<ClsProfileHeaderData> call, Response<ClsProfileHeaderData> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsProfileHeaderData listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        if (listResponse.getData() != null && !listResponse.getData().isEmpty()) {
                            sessionManager = new SessionManager(MyProfileActivity.this);
                            sessionManager.setStringValue("KEY_BASIC_PROFILE", listResponse.getData().get(0).getHeaderName());
                            sessionManager.setStringValue("KEY_HEALTH_PROFILE", listResponse.getData().get(1).getHeaderName());
                            rd_basic_profile.setText(sessionManager.getStringValue("KEY_BASIC_PROFILE"));
                            rd_heath_profile.setText(sessionManager.getStringValue("KEY_HEALTH_PROFILE"));
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

    public String formatDatesSleep(String dateFromServer) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
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





}
