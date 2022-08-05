package com.shamrock.reework.activity.Reemember.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
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
import com.shamrock.reework.activity.BCAModule.adapter.MyBCAReportAdapter;
import com.shamrock.reework.activity.BCAModule.adapter.MyBloodReportAdapter;
import com.shamrock.reework.activity.BCAModule.service.BCAReportItem;
import com.shamrock.reework.activity.BeforeAfterModule.service.BeforeAfterService;
import com.shamrock.reework.activity.BloodTestModule.activity.ReportCompareActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.ReportRequest;
import com.shamrock.reework.activity.BloodTestModule.adapter.BloodReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.adapter.OtherReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsGetotherreportmain;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsUplaodReportSucess;
import com.shamrock.reework.activity.BloodTestModule.pojo.OtherReportData;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.MedicineModule.activity.MyMedicinesActivity;
import com.shamrock.reework.activity.MedicineModule.adapter.MyMedicinesAdapter;
import com.shamrock.reework.activity.MedicineModule.service.EatingRequest;
import com.shamrock.reework.activity.MedicineModule.service.FrequencyList;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.ReminderModule.activity.ReminderSheduleActivity;
import com.shamrock.reework.activity.ReminderModule.adapter.MyRemindersAdapter;
import com.shamrock.reework.activity.ReminderModule.dialog.RemindersEditDialoge;
import com.shamrock.reework.activity.ReminderModule.model.ClsReminderMaster;
import com.shamrock.reework.activity.ReminderModule.service.RemindersService;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AddMedicineRequest;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.BloodTestReportRequest;
import com.shamrock.reework.api.request.DeleteMedicineRequest;
import com.shamrock.reework.api.request.EditMedicineApiRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.MedicineListRequest;
import com.shamrock.reework.api.request.ReminderDeleteRequest;
import com.shamrock.reework.api.request.ReminderEditRequest;
import com.shamrock.reework.api.request.ScheduleReminderRequest;
import com.shamrock.reework.api.response.AddMedicineResponse;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.BcaResponse;
import com.shamrock.reework.api.response.BloodReportItem;
import com.shamrock.reework.api.response.BloodTestReportResponse;
import com.shamrock.reework.api.response.DeleteMedicineResponse;
import com.shamrock.reework.api.response.MedicineListResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.ReminderDeleteResponse;
import com.shamrock.reework.api.response.ReminderEditResponse;
import com.shamrock.reework.api.response.ReminderResponse;
import com.shamrock.reework.api.response.ReminderScheduleResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.DaysUtils;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.MultiSelectionSpinner;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReememberActivity extends AppCompatActivity implements View.OnClickListener,
        AddMedicineDialogNewFragment.AddMedicineDialogListener,
        MyMedicinesAdapter.MedicineListListener, MyRemindersAdapter.MyReminderListener,
        RemindersEditDialoge.ReminderEditDialogeInterface,
        MultiSelectionSpinner.OnMultipleItemsSelectedListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{


    private static final String SEPARATOR = ",";
    Button btn_medicine, btn_reeminders, btn_reeports;
    LinearLayout layout_medicine, layout_reeminders, layout_reeports;
    String days = "";
    List<Integer> indices;
    int medicineId = 0;
    AutoCompleteTextView edt_Medicine;
    TextView txt_Days, imgAdd;
    Spinner spinnerFreq;
    ImageView img_add_medicine;
    Spinner spinner_time, spinner_time2, spinner_time3;
    MultiSelectionSpinner spinnerDays;
    Switch switch_noti;
    String frequency = "Select frequency";
    private String fromEatingStatus = "daily";
    private String fromFrequency = "daily";
    TimePickerDialog timepickerdialog;
    private String errorMsg;
    TextView txt_time1, txt_time2, txt_time3;
    private String strSubmitWhen = "";
    private String strSubmitTime = "";
    private boolean IsNotification;

    private LinearLayout ll_1, lay1, lay2, lay3;
    LinearLayout l1,l2;
    private LinearLayout ll_2;
    private LinearLayout ll_3;
    private String time;

    Button buttonAddMedicine;
    ArrayAdapter<String> adapter_whens;

    String[] frequency_whenArray;
    String[] frequency_whenArray_api;
    private String strEatingStatus1="";
    private String strEatingStatus2="";
    private String strEatingStatus3="";
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
    int when1 = 0;
    int when2 = 0;
    int when3 = 0;

    boolean isAdd ;
    private static final String TAG = "MyMedicinesActivity";
    Context context;
    Toolbar toolbar;
    TextView txt_no_medicine;
    Typeface font;
    LinearLayout textAddMedicine;
    ListView listView;
    ArrayList<MyMedicine> myMedicinesList;
    AddMedicineDialogNewFragment dialogFragment;
    SessionManager sessionManager;
    private MedicineService medService;
    Utils utils;
    MyMedicinesAdapter subscribeFeatureAdapter;
    int userID;
    private int userId;

    //Reminders

    private static final int ADD_REMINDERS = 101;
    //    Context context;
//    Toolbar toolbar;
//    Typeface font;
//    Utils utils;
    RemindersService remindersService;
    SessionManager sessionManagerreem;

    LinearLayout buttonSchedule;
    RelativeLayout linearNoData;
    RelativeLayout relativeLayout_ScheduleClock;

    ArrayList<ReminderResponse.ReminderData> reminderList;
    RecyclerView recyclerView;
    MyRemindersAdapter remindersAdapter;

    RemindersEditDialoge editDialog;
    ;
    RelativeLayout txt_no_reminder;
    int userIdreem;

    RelativeLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    private ReeTestService reeTestService;


    //Reports

    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    private final int GALLERY = 1;
    private ArrayList<HashMap<String, String>> arraylist;
    boolean isFile = true;


    //    Context context;
//    Toolbar toolbar;
//    Typeface font;
    ListView rv_other_report;
    ListView listViewreport;
    List<BloodReportItem> BloodReportItemArrayList;
    private ReeTestService reeTestServicereport;
    private SessionManager sessionManagerreport;
    private Utils utilsreport;
    private int userIDreport;
    BloodReportAdapter bloodReportAdapter;
    TextView txt_noreportbca, txt_noreport;

    ViewFlipper vp_reports;


    RecyclerView recyclerViewreport;
    RecyclerView rvMyBloodReport;
    MyBCAReportAdapter bcaReportAdapter;
    MyBloodReportAdapter newbloodReportAdapter;
    ArrayList<BCAReportItem> bcaList;
    ArrayList<BcaResponse.BcaReport> mList;
    List<BCAResponce.Datum> mBCAList;
    List<BCAResponce.Datum> mBCAList_filter;
    List<BCAResponce.Datum> mbloodList_filter;
    CardView rd_button_blood;
    CardView rd_button_bca;
    CardView rd_button_anyother;
    CardView rd_button_compare;
    FloatingActionButton fab_upload_data;
    TextView txt_pdf_upload;
    boolean isShowmenu = false;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;
    private String docFilePath = "";

    ArrayList<OtherReportData> arylst_other_Data;
    private boolean isCamera;
    private Uri mCapturedImageURI;
    private File fileuploadimage = new File("");

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int mNotifcationCount = 0;
    NotificationService notificationService;
    Dialog dialog;



    LinearLayout linTime2, linTime3, linTime4, linTime5, linTime6, linTime7, linTime8, linTime9, linTime10;
    TextView tvTime,tvTime2, tvTime3, tvTime4, tvTime5, tvTime6, tvTime7, tvTime8, tvTime9, tvTime10,txt_close;
    EditText etActivityName;
    Button buttonAddReemider,buttonEditReemider;
    Spinner spinner_activity_type,spinnerReminder;
    TextView tvStartDate,tvEndDate;
    DatePickerDialog startDatePicker;
    //    TimePickerDialog timepickerdialog;
    private String lsStartDate = "", lsEndDate = "", lsTime = "", lsActivity = "";
    String lsTime2 = "", lsTime3 = "", lsTime4 = "", lsTime5 = "", lsTime6 = "", lsTime7 = "", lsTime8 = "", lsTime9 = "", lsTime10 = "";
    private boolean isEndDate = false;
    String isWhichTimeSlected = "";
    int frequency1 = 0, liUserId = 0;

    boolean isReeminder=false;
    boolean editReeminder=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reemember);


        context = ReememberActivity.this;

        indices = new ArrayList<>();
        btn_medicine = findViewById(R.id.btn_medicine);
        btn_reeminders = findViewById(R.id.btn_reeminders);
        btn_reeports = findViewById(R.id.btn_reeports);
        layout_medicine = findViewById(R.id.layout_medicine);
        layout_reeminders = findViewById(R.id.layout_reeminders);
        layout_reeports = findViewById(R.id.layout_reeports);

        btn_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {


                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        Intent intent = new Intent(context, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                        Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                    } else {


                        layout_medicine.setVisibility(View.VISIBLE);
                        layout_reeminders.setVisibility(View.GONE);
                        layout_reeports.setVisibility(View.GONE);


                        btn_medicine.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                        btn_reeminders.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_reeports.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                        btn_medicine.setTextColor(getResources().getColor(R.color.white_recipe));
                        btn_reeminders.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_reeports.setTextColor(getResources().getColor(R.color.black_recipe));


                    }

                }


            }
        });

        btn_reeminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                } else {


                    if (sessionManager.getStringValue("Trial").equalsIgnoreCase("True")) {

                        layout_medicine.setVisibility(View.GONE);
                        layout_reeminders.setVisibility(View.VISIBLE);
                        layout_reeports.setVisibility(View.GONE);


                        btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                        btn_reeminders.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                        btn_reeports.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                        btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                        btn_reeminders.setTextColor(getResources().getColor(R.color.white_recipe));
                        btn_reeports.setTextColor(getResources().getColor(R.color.black_recipe));


                    } else {
                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            Intent intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                            Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                        } else {
                            layout_medicine.setVisibility(View.GONE);
                            layout_reeminders.setVisibility(View.VISIBLE);
                            layout_reeports.setVisibility(View.GONE);


                            btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                            btn_reeminders.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                            btn_reeports.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                            btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                            btn_reeminders.setTextColor(getResources().getColor(R.color.white_recipe));
                            btn_reeports.setTextColor(getResources().getColor(R.color.black_recipe));
                        }

                    }

                }


            }
        });

        btn_reeports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_medicine.setVisibility(View.GONE);
                layout_reeminders.setVisibility(View.GONE);
                layout_reeports.setVisibility(View.VISIBLE);


                btn_medicine.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeminders.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                btn_reeports.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                btn_medicine.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeminders.setTextColor(getResources().getColor(R.color.black_recipe));
                btn_reeports.setTextColor(getResources().getColor(R.color.white_recipe));
            }
        });


//Report̥


        rd_button_compare = findViewById(R.id.rd_button_compare);
        fab_upload_data = findViewById(R.id.fab_upload_data);
        txt_pdf_upload = findViewById(R.id.txt_pdf_upload);
        rv_other_report = findViewById(R.id.rv_other_report);
        fab_upload_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                    shownoplan();
//                    return;
//                }


                final Dialog dialog = new Dialog(ReememberActivity.this, R.style.CustomDialog);
                dialog.setContentView(R.layout.dialog_add_report);
                ImageView close_add_report = dialog.findViewById(R.id.close_add_report);
                close_add_report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btn_add_png = dialog.findViewById(R.id.btn_add_png);
                Button btn_add_image = dialog.findViewById(R.id.btn_add_image);
                btn_add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AddMedicineImageDailog();
                    }
                });
                btn_add_png.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Dexter.withActivity(ReememberActivity.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
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


                    }
                });
                dialog.show();

            }
        });


        initreport();
//        setToolBar();
        findViewsreport();

        CallOtherTestReports();


        //Reminders


        initreem();
//        setToolBarreem();
        findViewsreem();


        //medicines

        init();

        findViews();

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


    //Report


    public void fileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(intent, FILE_SELECT_REQUEST_CODE);

    }


    private void closeSubMenusFab() {
        txt_pdf_upload.setVisibility(View.INVISIBLE);
        isShowmenu = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        txt_pdf_upload.setVisibility(View.VISIBLE);
        isShowmenu = true;
    }

    private void initreport() {
        BloodReportItemArrayList = new ArrayList<>();

        sessionManagerreport = new SessionManager(context);
        reeTestServicereport = Client.getClient().create(ReeTestService.class);
        utilsreport = new Utils();

        userIDreport = sessionManagerreport.getIntValue(SessionManager.KEY_USER_ID);
    }

    private void findViewsreport() {
        //   listView = findViewById(R.id.listViewBloodReport);

        recyclerViewreport = findViewById(R.id.rvMyBCAReport);
        rvMyBloodReport = findViewById(R.id.rvMyBloodReport);
        txt_noreport = findViewById(R.id.txt_noreport);
        txt_noreportbca = findViewById(R.id.txt_noreportbca);
        vp_reports = findViewById(R.id.vp_reports);
        rd_button_bca = findViewById(R.id.rd_button_bca);
        rd_button_blood = findViewById(R.id.rd_button_blood);
        rd_button_anyother = findViewById(R.id.rd_button_anyother);
        int userId = sessionManagerreport.getIntValue(SessionManager.KEY_USER_ID);
        vp_reports.setDisplayedChild(0);
        final TextView txt_reportheader = findViewById(R.id.txt_reportheader);
        txt_reportheader.setText("BCA Report");

        rd_button_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ReememberActivity.this, ReportCompareActivity.class));
                overridePendingTransition(0, 0);
                finish();


            }
        });

        rd_button_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_reports.setDisplayedChild(0);
                txt_reportheader.setText("  BCA Report");

            }
        });

        rd_button_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_reports.setDisplayedChild(1);
                txt_reportheader.setText("  Pathology Report");

            }
        });

        rd_button_anyother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_reports.setDisplayedChild(2);
                CallOtherTestReports();
                txt_reportheader.setText("  Other Report");


            }
        });
        if (Utils.isNetworkAvailable(context))
            // callToGetBcaReport();
            callToGetBCAReport();
        else
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
    }


    private void callToGetBCAReport() {
        if (!((Activity) context).isFinishing()) {
            utilsreport.showProgressbar(context);
        }

        int userId = sessionManagerreport.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);////4186


        Call<BCAResponce> call = reeTestServicereport.getAllBloodReportNewHistory(String.valueOf(userId));
        call.enqueue(new Callback<BCAResponce>() {
            @Override
            public void onResponse(Call<BCAResponce> call, Response<BCAResponce> response) {
                utilsreport.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    BCAResponce bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode() == 1) {
                        mBCAList = bcaResponse.getData();

                        if (mBCAList != null && mBCAList.size() > 0) {
                            mBCAList_filter = new ArrayList<>();
                            mbloodList_filter = new ArrayList<>();

                            for (int i = 0; i < mBCAList.size(); i++) {
                                if (mBCAList.get(i).getReportType().equalsIgnoreCase("BCA Test")) {
                                    mBCAList_filter.add(mBCAList.get(i));
                                } else {
                                    mbloodList_filter.add(mBCAList.get(i));

                                }


                            }

                            if (!mBCAList_filter.isEmpty()) {
                                recyclerViewreport.setVisibility(View.VISIBLE);
                                txt_noreportbca.setVisibility(View.GONE);
                                bcaReportAdapter = new MyBCAReportAdapter(context, mBCAList_filter);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ReememberActivity.this);
//                                layoutManager.setReverseLayout(true);
                                recyclerViewreport.setLayoutManager(layoutManager);
                                recyclerViewreport.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewreport.setAdapter(bcaReportAdapter);
                            } else {
                                recyclerViewreport.setVisibility(View.GONE);
                                txt_noreportbca.setVisibility(View.VISIBLE);
                                txt_noreportbca.setText("No Reports available");
                            }


                            if (!mbloodList_filter.isEmpty()) {
                                rvMyBloodReport.setVisibility(View.VISIBLE);
                                txt_noreport.setVisibility(View.GONE);
                                newbloodReportAdapter = new MyBloodReportAdapter(context, mbloodList_filter);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(ReememberActivity.this);
//                                layoutManager2.setReverseLayout(true);
                                rvMyBloodReport.setLayoutManager(layoutManager2);
                                rvMyBloodReport.setItemAnimator(new DefaultItemAnimator());
                                rvMyBloodReport.setAdapter(newbloodReportAdapter);
                            } else {
                                rvMyBloodReport.setVisibility(View.GONE);
                                txt_noreport.setVisibility(View.VISIBLE);
                                txt_noreport.setText("No Reports available");

                            }


                        } else {
                            recyclerViewreport.setVisibility(View.GONE);
                            rvMyBloodReport.setVisibility(View.GONE);
                            txt_noreport.setVisibility(View.VISIBLE);
                            txt_noreportbca.setVisibility(View.VISIBLE);

                            txt_noreport.setText("No Reports available");
                            txt_noreportbca.setText("No Reports available");


//                            Toast.makeText(BloodReportActivity.this, "No Reports available", Toast.LENGTH_SHORT).show();
//                            finish();

                        }
                    } else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BCAResponce> call, Throwable t) {
                utilsreport.hideProgressbar();
            }
        });
    }

    private void CallToGetAllBloodTestReports(int userId) {
        utilsreport.showProgressbar(context);

        BloodTestReportRequest request = new BloodTestReportRequest();
        request.setUserID(String.valueOf(userId));

        Call<BloodTestReportResponse> call = reeTestService.getBloodTestReports(request);
        call.enqueue(new Callback<BloodTestReportResponse>() {
            @Override
            public void onResponse(Call<BloodTestReportResponse> call, Response<BloodTestReportResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    BloodTestReportResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        BloodReportItemArrayList = listResponse.getData();

                        if (BloodReportItemArrayList != null && !BloodReportItemArrayList.isEmpty()) {

                            bloodReportAdapter = new BloodReportAdapter(context, BloodReportItemArrayList);
                            // listView.setAdapter(bloodReportAdapter);
                        } else
                            Snackbar.make(findViewById(android.R.id.content), "No reports available !", Snackbar.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utilsreport.hideProgressbar();
            }

            @Override
            public void onFailure(Call<BloodTestReportResponse> call, Throwable t) {
                // Log error here since request failed
                utilsreport.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }


    private void CallOtherTestReports() {
        utilsreport.showProgressbar(context);


        Call<ClsGetotherreportmain> call = reeTestServicereport.getOtherReportList(userIDreport);
        call.enqueue(new Callback<ClsGetotherreportmain>() {
            @Override
            public void onResponse(Call<ClsGetotherreportmain> call, Response<ClsGetotherreportmain> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsGetotherreportmain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        arylst_other_Data = listResponse.getData();

                        if (arylst_other_Data != null && !arylst_other_Data.isEmpty()) {

                            OtherReportAdapter bloodReportAdapter = new OtherReportAdapter(context, arylst_other_Data);
                            rv_other_report.setAdapter(bloodReportAdapter);
                        } else {
                            if (vp_reports.getDisplayedChild() == 2) {
                                Snackbar.make(findViewById(android.R.id.content), "No reports available !", Snackbar.LENGTH_LONG).show();

                            }

                        }
                    } else {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utilsreport.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ClsGetotherreportmain> call, Throwable t) {
                // Log error here since request failed
                utilsreport.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }


    private String getFileNameByUri(Context context, Uri uri) {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file = null;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else if (uri.getScheme().compareTo("file") == 0) {
            try {
                try {
                    file = new File(new URI(uri.toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (file.exists())
                    filepath = file.getAbsolutePath();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            filepath = uri.getPath();
        }
        return filepath;
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 500) {

            if (isAdd == true) {


                if (requestCode == FILE_SELECT_REQUEST_CODE) {


                    if (resultCode == RESULT_OK) {

                        Uri selectedImage = data.getData();

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = ReememberActivity.this.getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        img_add_medicine.setImageBitmap(null);
                        img_add_medicine.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                        fileuploadimage = new File(picturePath);
                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                        } catch (Throwable t) {
                            Log.e("ERROR", "Error compressing file." + t.toString());
                            t.printStackTrace();
                        }
//                uploadFile(new File(picturePath), userID);

//                isImage = true;
                    }


                } else if (requestCode == 500) {


                    if (resultCode == RESULT_OK) {
                        fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                        try {
                            Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                        } catch (Throwable t) {
                            Log.e("ERROR", "Error compressing file." + t.toString());
                            t.printStackTrace();
                        }

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(ReememberActivity.this.getContentResolver(), mCapturedImageURI);
                            img_add_medicine.setImageBitmap(null);

                            img_add_medicine.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
//                isImage = true;
                    }


                }

                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);


            } else if(isAdd==false) {


//                if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                    } catch (Throwable t) {
                        Log.e("ERROR", "Error compressing file." + t.toString());
                        t.printStackTrace();
                    }

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(ReememberActivity.this.getContentResolver(), mCapturedImageURI);
                        img_add_medicine.setImageBitmap(null);

                        img_add_medicine.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
//                isImage = true;
//                    }
                }


            }else{




                if (resultCode == RESULT_OK) {
                    fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
//


//                    Bitmap bitmapImage = BitmapFactory.decodeFile(fileuploadimage.getPath());
//                    int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

                        if (fileuploadimage.exists()) {
                            callUploadApi(fileuploadimage);

                        } else {
                            Toast.makeText(context, "file not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Throwable t) {
                        Log.e("ERROR", "Error compressing file." + t.toString());
                        t.printStackTrace();
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
                            callUploadApi(file);

                        } else {
                            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


                if (resultCode == RESULT_OK) {
                    if (requestCode == FILE_SELECT_REQUEST_CODE) {
                        isFile = true;
                        Uri uri = data.getData();

                        String path = getFilePathFromURI(ReememberActivity.this, uri);
                        File file = new File(path);

                        if (file.exists()) {
                            callUploadApi(file);

                        } else {
                            Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            }

        }else{
            if (requestCode == 100) {
                if (resultCode == RESULT_OK) {

//                    Uri selectedImage = data.getData();
//
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    Cursor cursor = ReememberActivity.this.getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    cursor.close();


                    Uri selectedImageUri = data.getData();
                    String selectedImagePath = getPath(selectedImageUri);

                    img_add_medicine.setImageBitmap(null);
//                    img_add_medicine.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
                    img_add_medicine.setImageURI(selectedImageUri);

                    fileuploadimage = new File(selectedImagePath);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                    } catch (Throwable t) {
                        Log.e("ERROR", "Error compressing file." + t.toString());
                        t.printStackTrace();
                    }
//                uploadFile(new File(picturePath), userID);

//                isImage = true;
                }
            }

            if(l1!=null) {
                if (isAdd == false) {
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                }else{
                    l1.setVisibility(View.VISIBLE);
                    l2.setVisibility(View.GONE);
                }
            }


        }
    }

    public String getPath(Uri uri) {
        String selectedImagePath;
        //1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            selectedImagePath = cursor.getString(column_index);
        }else{
            selectedImagePath = null;
        }

        if(selectedImagePath == null){
            //2:OI FILE Manager --- call method: uri.getPath()
            selectedImagePath = uri.getPath();
        }
        return selectedImagePath;
    }

    private void callUploadApi(File file) {


        MultipartBody.Part photo;


        if (isFile) {

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            String imagename = file.getName();
            photo = MultipartBody.Part.createFormData("ReportFile", imagename, requestFile);


        } else {

            RequestBody photoContent;

            photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            photo = MultipartBody.Part.createFormData("ReportFile", file.getName(), photoContent);


        }


        BeforeAfterService uploadService = Client.getClientMultiPart().create(BeforeAfterService.class);


        utilsreport.showProgressbar(this);
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setId("0");
        reportRequest.setReeworkerId(String.valueOf(userID));
        reportRequest.setReportFilePath(file.getPath());
        reportRequest.setReportName(file.getName());


        String request2 = new Gson().toJson(reportRequest);

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), request2);


//        RequestBody user_Id = RequestBody.create(MediaType.parse("multipart/form-MainModel"), ""+reportRequest);


        Call<ClsUplaodReportSucess> call = uploadService.UploadReportFile(photo, body);
        call.enqueue(new Callback<ClsUplaodReportSucess>() {

            @Override
            public void onResponse(Call<ClsUplaodReportSucess> call, Response<ClsUplaodReportSucess> response) {
                //ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> data = null;

                utilsreport.hideProgressbar();

                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        ClsUplaodReportSucess dataResponse = response.body();

                        if (dataResponse.getCode().equals("1")) {

                            Toast.makeText(getApplicationContext(), "Uploaded successfully !", Toast.LENGTH_SHORT).show();
                            CallOtherTestReports();


//                            fetchData();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Server : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Server : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsUplaodReportSucess> call, Throwable t) {

                utilsreport.hideProgressbar();
                Toast.makeText(ReememberActivity.this, "" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                fetchData();
//                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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

    private void browseDocuments() {

        String[] mimeTypes =
                { // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "image/*"
                };

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 1);

    }

    private boolean checkIfAlreadyHavePermission() {
        int res = ContextCompat.checkSelfPermission(ReememberActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(ReememberActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_READ_PERMISSION_GRANT);
    }


    private void AddMedicineImageDailogNew() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);


        builder.setMessage("Upload Medicine Image ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(ReememberActivity.this)
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        CallCameraIntentNew();
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

                        Dexter.withActivity(ReememberActivity.this)
                                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
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


                        if (!checkIfAlreadyHavePermission()) {
                            isCamera = false;
                            requestForSpecificPermission();

                        } else {

                        }
                    }
                });
        //Creating dialog box
        android.app.AlertDialog alert = builder.create();
        alert.setCancelable(true);
        //Setting the title manually
        alert.show();
    }


    private void AddMedicineImageDailog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);


        builder.setMessage("Upload Photo ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(ReememberActivity.this)
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

                        Dexter.withActivity(ReememberActivity.this)
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
    }

    public void CallCameraIntent() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, 500);
    }

    public void CallCameraIntentNew() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

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

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }


    //Remimders

    private void initreem() {
        remindersService = Client.getClient().create(RemindersService.class);
        utils = new Utils();
        sessionManagerreem = new SessionManager(context);

        userIdreem = sessionManagerreem.getIntValue(SessionManager.KEY_USER_ID);
    }


    private void findViewsreem() {
        buttonSchedule = findViewById(R.id.buttonScheduleReminder);
        txt_no_reminder = findViewById(R.id.txt_no_reminder);
        mainLayout = findViewById(R.id.mainLayout);
        relativeLayout_ScheduleClock = findViewById(R.id.relLay_MyReminder_AddClock);
        recyclerView = findViewById(R.id.recyclerview);
        reminderList = new ArrayList<>();
        remindersAdapter = new MyRemindersAdapter(context, reminderList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReememberActivity.this);
//                                layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(remindersAdapter);
        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        linearNoData = findViewById(R.id.relLay_MyReminder_AddClock);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
        buttonSchedule.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.isNetworkAvailable(context))
            callToGetAllReminders();
        else
            showLayouts();
    }


    public void showLayouts() {
        if (!Utils.isNetworkAvailable(context)) {
            noInternetLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
            linearNoData.setVisibility(View.GONE);
        } else {
            mainLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
            linearNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDelete(int pos, ReminderResponse.ReminderData model) {
        if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
            shownoplan();
            return;
        }

        if (model != null) {
            deleteReminder(pos, model);
        }
    }

    private void callActvityTypeMasterAPI(int pos, final ReminderResponse.ReminderData model) {
        utils.showProgressbar(context);

        sessionManagerreem = new SessionManager(context);
        reeTestService = Client.getClient().create(ReeTestService.class);

        int userID = sessionManagerreem.getIntValue(SessionManager.KEY_USER_ID);

        Call<ClsReminderMaster> call = reeTestService.getAllRemiderActivityMaster();
        call.enqueue(new Callback<ClsReminderMaster>() {
            @Override
            public void onResponse(Call<ClsReminderMaster> call, Response<ClsReminderMaster> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsReminderMaster listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        ArrayList<String> arylst_Activities_list = listResponse.getData();
                        if (arylst_Activities_list != null) {
                            arylst_Activities_list.set(0, "Select Activity");

//                            FragmentManager fm = getSupportFragmentManager();
//                            editDialog = new RemindersEditDialoge();
//
//
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("MODEL", model);
//                            bundle.putSerializable("Actvitiylist", arylst_Activities_list);
//                            editDialog.setArguments(bundle);
//                            editDialog.show(fm, "edit_fragment");


                            showDialogReeminderEdit(model,arylst_Activities_list);


                        }


                    } else {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ClsReminderMaster> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }

    @Override
    public void onEdit(int pos, ReminderResponse.ReminderData model) {
        if (sessionManagerreem.getStringValue("notallowed").equalsIgnoreCase("true")) {
            shownoplan();
            return;
        }

        if (model != null) {
            callActvityTypeMasterAPI(pos, model);


        }
    }


    /* TO GET ALL REMINDER */
    private void callToGetAllReminders() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userIdreem);

        Call<ReminderResponse> call = remindersService.getAllReminder(request);
        call.enqueue(new Callback<ReminderResponse>() {
            @Override
            public void onResponse(Call<ReminderResponse> call, Response<ReminderResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    String dataAsString = new Gson().toJson(response);
                    ReminderResponse reminderResponse = response.body();

                    if (reminderResponse != null && reminderResponse.getCode() == 1) {
                        ArrayList<ReminderResponse.ReminderData> tempList = reminderResponse.getData();

                        if (tempList != null && tempList.size() > 0) {
//                            reminderList.clear();
//                            reminderList.addAll(tempList);
//                            remindersAdapter.notifyDataSetChanged();

                            linearNoData.setVisibility(View.GONE);
                            noInternetLayout.setVisibility(View.GONE);
                            txt_no_reminder.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);


                            reminderList = new ArrayList<>();
                            reminderList.clear();
                            reminderList.addAll(tempList);

                            remindersAdapter = new MyRemindersAdapter(context, reminderList, ReememberActivity.this);

//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RemindersActivity.this);
//                            recyclerView.setLayoutManager(layoutManager);
//                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(remindersAdapter);


                        } else {
                            txt_no_reminder.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            linearNoData.setVisibility(View.VISIBLE);
                            noInternetLayout.setVisibility(View.GONE);
                        }
                    } else
                        ShowRetryBar("");
                }
            }

            @Override
            public void onFailure(Call<ReminderResponse> call, Throwable t) {
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
                            callToGetAllReminders();
                        } else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    public void deleteReminder(final int pos, final ReminderResponse.ReminderData model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete Reminder!")
                .setMessage("Do you really want to delete this Reminder?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Utils.isNetworkAvailable(context)) {
                            utils.showProgressbar(context);

                            callDeleteReminder(pos, model);
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

    private void callDeleteReminder(final int pos, ReminderResponse.ReminderData model) {
        ReminderDeleteRequest request = new ReminderDeleteRequest();
        request.setReminderID(model.getReminderID());

        Call<ReminderDeleteResponse> call = remindersService.deleteReminder(request);
        call.enqueue(new Callback<ReminderDeleteResponse>() {
            @Override
            public void onResponse(Call<ReminderDeleteResponse> call, Response<ReminderDeleteResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ReminderDeleteResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        // call List API to reload list
                        reminderList.remove(pos);
                        remindersAdapter.notifyDataSetChanged();
                        callToGetAllReminders();
                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReminderDeleteResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void onEdit(ReminderResponse.ReminderData model) {
        if (Utils.isNetworkAvailable(context))
            callToGetAllReminders();
        else
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
    }

//    private void shownoplan() {
//
//        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
//
//        dialog.setContentView(R.layout.dialog_no_plan);
//        dialog.setCancelable(false);
//        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);
//
//        Animation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(500); //You can manage the blinking time with this parameter
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        txt_lable_expired.startAnimation(anim);
//
//        Button btn_subscribe=dialog.findViewById(R.id.btn_subscribe);
//        Button btn_subscribe_no=dialog.findViewById(R.id.btn_subscribe_no);
//        btn_subscribe_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        btn_subscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.dismiss();
//
//                Intent  intent = new Intent(context, ViewPagerActivity.class);
//                intent.putExtra("param", "From_no_plan");
//                startActivity(intent);
//
//            }
//        });
//
//        dialog.show();
//
//    }


    //medicines
    private void init() {
        sessionManager = new SessionManager(context);
        medService = Client.getClient().create(MedicineService.class);
        utils = new Utils();

        myMedicinesList = new ArrayList<>();
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
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
        tvTitle.setText("REEmember");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });


        counterValuePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        textAddMedicine = findViewById(R.id.textMedicines_Add);
        listView = findViewById(R.id.listViewMyMedicines);
        txt_no_medicine = findViewById(R.id.txt_no_medicine);

        textAddMedicine.setOnClickListener(this);

//        subscribeFeatureAdapter = new MyMedicinesAdapter(context, myMedicinesList);
//        listView.setAdapter(subscribeFeatureAdapter);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        if (Utils.isNetworkAvailable(context)) {
            callMedicineListAPI(userId);
        } else {
            ShowRetryBar();
        }
    }

    private void ShowRetryBar() {
        String strMessage = "Unable to load data";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callMedicineListAPI(userId);
                    }
                });
        snackbar.show();
    }

    private void callMedicineListAPI(int userId) {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        MedicineListRequest request = new MedicineListRequest();
        request.setUserid(userId);

        Call<MedicineListResponse> call = medService.getMedicinesList(request);
        call.enqueue(new Callback<MedicineListResponse>() {
            @Override
            public void onResponse(Call<MedicineListResponse> call, Response<MedicineListResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MedicineListResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        List<MedicineListResponse.DataResponse> dataResponseList = listResponse.getData();

                        if (dataResponseList != null) {
                            myMedicinesList.clear();
                            for (MedicineListResponse.DataResponse data : dataResponseList) {
                                ArrayList<FrequencyList> FrequencyList = new ArrayList<>();

                                int medID = 0;
                                int myMedID = data.getMymedid();
                                String name = data.getMedicineName();
                                String fre = data.getFrequency();
                                String daysInComma = data.getDosage();
                                FrequencyList.addAll(data.getFrequencyList());
                                String path = data.getImgPath();
                                String imageName = data.getImgName();

                                MyMedicine myMedicine = new MyMedicine(medID, myMedID, name, fre, daysInComma, FrequencyList, path, imageName, data.IsNotification());
                                myMedicinesList.add(myMedicine);
                            }

                            if (!myMedicinesList.isEmpty()) {
                                listView.setVisibility(View.VISIBLE);
                                txt_no_medicine.setVisibility(View.GONE);
                                subscribeFeatureAdapter = new MyMedicinesAdapter(context, myMedicinesList);
                                listView.setAdapter(subscribeFeatureAdapter);
                            } else {
                                listView.setVisibility(View.GONE);
                                txt_no_medicine.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        listView.setVisibility(View.GONE);
                        txt_no_medicine.setVisibility(View.VISIBLE);
                        txt_no_medicine.setText(listResponse.getMessage());

//                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<MedicineListResponse> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
                ShowRetryBar();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    public String formatDatesNew(String dateFromServer, boolean b)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat=null;
        if (b){
            dateFormat = new SimpleDateFormat("yyyy-dd-MM");

        }else {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        }
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {

            e.printStackTrace(); }
        return "N/A";
    }

    public String formatDatesServer(String dateFromServer)
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

    private void showDialogReeminderEdit(final ReminderResponse.ReminderData model,ArrayList<String> arylst_Activities_list ) {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reminderbottomsheetlayoutedit);

        isReeminder = true;
//        editReeminder=true;

        buttonEditReemider = dialog.findViewById(R.id.buttonEditReemider);
        etActivityName = dialog.findViewById(R.id.etActivityName);
        spinner_activity_type = dialog.findViewById(R.id.spinner_activity_type);
        spinnerReminder = dialog.findViewById(R.id.spinnerReminder);

        tvStartDate = dialog.findViewById(R.id.tvStartDate);
        tvEndDate = dialog.findViewById(R.id.tvEndDate);


        linTime2 = dialog.findViewById(R.id.linTime2);
        linTime3 = dialog.findViewById(R.id.linTime3);
        linTime4 = dialog.findViewById(R.id.linTime4);
        linTime5 = dialog.findViewById(R.id.linTime5);
        linTime6 = dialog.findViewById(R.id.linTime6);
        linTime7 = dialog.findViewById(R.id.linTime7);
        linTime8 = dialog.findViewById(R.id.linTime8);
        linTime9 = dialog.findViewById(R.id.linTime9);
        linTime10 = dialog.findViewById(R.id.linTime10);

        tvTime = dialog.findViewById(R.id.tvTime);
        tvTime2 = dialog.findViewById(R.id.tvTime2);
        tvTime3 = dialog.findViewById(R.id.tvTime3);
        tvTime4 = dialog.findViewById(R.id.tvTime4);
        tvTime5 = dialog.findViewById(R.id.tvTime5);
        tvTime6 = dialog.findViewById(R.id.tvTime6);
        tvTime7 = dialog.findViewById(R.id.tvTime7);
        tvTime8 = dialog.findViewById(R.id.tvTime8);
        tvTime9 = dialog.findViewById(R.id.tvTime9);
        tvTime10 = dialog.findViewById(R.id.tvTime10);
        txt_close = dialog.findViewById(R.id.txt_close);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);


        startDatePicker = new DatePickerDialog(ReememberActivity.this, this, year, month, day);

        startDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        startDatePicker.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = startDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = startDatePicker.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


        ArrayAdapter<String> adapter_when = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_Activities_list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_activity_type.setAdapter(adapter_when);
        spinner_activity_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
//                when = i;




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        String[] frequencyArray = getResources().getStringArray(R.array.reminder_frequency);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequencyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReminder.setAdapter(adapter);

        lsActivity= "";
        lsStartDate="";
        lsEndDate="";
        lsTime="";
        frequency1 = 0;



        if (model != null)
        {
            if(model.getActivityType()!=null){

                if (arylst_Activities_list!=null){
                    if (!model.getActivityType().isEmpty()){

                        for (int i = 0; i <arylst_Activities_list.size() ; i++) {

                            if (model.getActivityType().trim().equalsIgnoreCase(arylst_Activities_list.get(i).toString().trim())){
                                spinner_activity_type.setSelection(i);
                                break;
                            }

                        }
                    }
                }
            }




            if (!TextUtils.isEmpty(model.getActivity()))
            {
                lsActivity = model.getActivity();

                if (!TextUtils.isEmpty(lsActivity))
                    etActivityName.setText(lsActivity);
            }
            if (!TextUtils.isEmpty(model.getStartDate()))
            {
                String tempStartDate = model.getStartDate();

                if (!TextUtils.isEmpty(tempStartDate))
                {
                    lsStartDate = formatDatesServer(tempStartDate);
                    tvStartDate.setText(formatDatesNew(tempStartDate,false));
                }
            }
            if (!TextUtils.isEmpty(model.getEndDate()))
            {
                String tempEndDate = model.getEndDate();

                if (!TextUtils.isEmpty(tempEndDate))
                {
                    lsEndDate = formatDatesServer(tempEndDate);


                    tvEndDate.setText(formatDatesNew(tempEndDate,false));
                }
            }
          /*  if (!TextUtils.isEmpty(mModel.getStartTime()))
            {
                String time = mModel.getStartTime();

                if (!TextUtils.isEmpty(time))
                {
                    lsTime = formatTime(time);
                    tvTime.setText(lsTime);
                }
            }*/
            if (model.getFrequency() > 0)
            {
                frequency1 = model.getFrequency();
                spinnerReminder.setSelection(model.getFrequency());
            }
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");



            Date _24HourDt =null;
            List<String> reminderList  = model.getReminderTime();
            if(reminderList!=null) {
                for (int i = 0; i < reminderList.size(); i++) {
                    if (i == 0) {

                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                        tvTime.setText(formatter.format(reminderList.get(i)));
                    } else if (i == 1) {
                        linTime2.setVisibility(View.VISIBLE);

                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime2.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime2.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime2.setText((reminderList.get(i)));
                    } else if (i == 2) {
                        linTime3.setVisibility(View.VISIBLE);

                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime3.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime3.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                        tvTime3.setText(reminderList.get(i));
                    } else if (i == 3) {
                        linTime4.setVisibility(View.VISIBLE);
                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime4.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime4.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime4.setText(reminderList.get(i));
                    } else if (i == 4) {
                        linTime5.setVisibility(View.VISIBLE);

                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime5.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime5.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime5.setText(reminderList.get(i));
                    } else if (i == 5) {
                        linTime6.setVisibility(View.VISIBLE);

                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime6.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime6.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime6.setText(reminderList.get(i));
                    } else if (i == 6) {
                        linTime7.setVisibility(View.VISIBLE);

                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime7.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime7.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime7.setText(reminderList.get(i));
                    } else if (i == 7) {
                        linTime8.setVisibility(View.VISIBLE);
                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime8.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime8.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime8.setText(reminderList.get(i));
                    } else if (i == 8) {
                        linTime9.setVisibility(View.VISIBLE);
                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime9.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime9.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime9.setText(reminderList.get(i));
                    } else if (i == 9) {
                        linTime10.setVisibility(View.VISIBLE);
                        try {
                            _24HourDt = _24HourSDF.parse(reminderList.get(i).toString());


                            String abc[]=_12HourSDF.format(_24HourDt).split(" ");

                            if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                                tvTime10.setText(abc[0]+" AM");
                            }else{
//                            sb.append(abc[0]+" PM");
                                tvTime10.setText(abc[0]+" PM");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                        tvTime10.setText(reminderList.get(i));
                    }

                }
            }
        }








        spinnerReminder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequency1 = i;
                hideStartTimeView();
                hideNShow(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });




        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });








//        callActvityTypeMasterAPI();



        tvTime.setOnClickListener(this);
        tvTime2.setOnClickListener(this);
        tvTime3.setOnClickListener(this);
        tvTime4.setOnClickListener(this);
        tvTime5.setOnClickListener(this);
        tvTime6.setOnClickListener(this);
        tvTime7.setOnClickListener(this);
        tvTime8.setOnClickListener(this);
        tvTime9.setOnClickListener(this);
        tvTime10.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
//        buttonAddReemider.setOnClickListener(this);
        buttonEditReemider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> reminderArrayList = new ArrayList<>();
                lsActivity = "";
                lsActivity = etActivityName.getText().toString().trim();

                boolean isDataValid = validateAllData1(lsActivity, lsStartDate, lsEndDate, lsTime, frequency1);



                if (isDataValid) {
                    if (Utils.isNetworkAvailable(context)) {

                        if (frequency1 == 1) {
                            reminderArrayList.add(tvTime.getText().toString());
                        } else if (frequency1 == 2) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                        } else if (frequency1 == 3) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                        } else if (frequency1 == 4) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                        } else if (frequency1 == 5) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                        } else if (frequency1 == 6) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                        } else if (frequency1 == 7) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                        } else if (frequency1 == 8) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                        } else if (frequency1 == 9) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                            reminderArrayList.add(tvTime9.getText().toString());
                        } else if (frequency1 == 10) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                            reminderArrayList.add(tvTime9.getText().toString());
                            reminderArrayList.add(tvTime10.getText().toString());
                        }


                        /*JSONArray jsArray = new JSONArray(reminderArrayList);
                        String remindertest = jsArray.toString();*/
                        if(isSameDateSelected(reminderArrayList)==true){
                            Utils.shortToast(context,"time should not be same.");
                        }else {

                            callSheduleReminderEdit(model.getReminderID(),reminderArrayList);

                        }
                    } else {
                        Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
                    }
                } else {
                    Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }



            }
        });





        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }








    private void showDialogReeminder() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reminderbottomsheetlayout);

        isReeminder = true;

        buttonAddReemider = dialog.findViewById(R.id.buttonAddReemider);
        etActivityName = dialog.findViewById(R.id.etActivityName);
        spinner_activity_type = dialog.findViewById(R.id.spinner_activity_type);
        spinnerReminder = dialog.findViewById(R.id.spinnerReminder);

        tvStartDate = dialog.findViewById(R.id.tvStartDate);
        tvEndDate = dialog.findViewById(R.id.tvEndDate);


        linTime2 = dialog.findViewById(R.id.linTime2);
        linTime3 = dialog.findViewById(R.id.linTime3);
        linTime4 = dialog.findViewById(R.id.linTime4);
        linTime5 = dialog.findViewById(R.id.linTime5);
        linTime6 = dialog.findViewById(R.id.linTime6);
        linTime7 = dialog.findViewById(R.id.linTime7);
        linTime8 = dialog.findViewById(R.id.linTime8);
        linTime9 = dialog.findViewById(R.id.linTime9);
        linTime10 = dialog.findViewById(R.id.linTime10);

        tvTime = dialog.findViewById(R.id.tvTime);
        tvTime2 = dialog.findViewById(R.id.tvTime2);
        tvTime3 = dialog.findViewById(R.id.tvTime3);
        tvTime4 = dialog.findViewById(R.id.tvTime4);
        tvTime5 = dialog.findViewById(R.id.tvTime5);
        tvTime6 = dialog.findViewById(R.id.tvTime6);
        tvTime7 = dialog.findViewById(R.id.tvTime7);
        tvTime8 = dialog.findViewById(R.id.tvTime8);
        tvTime9 = dialog.findViewById(R.id.tvTime9);
        tvTime10 = dialog.findViewById(R.id.tvTime10);
        txt_close = dialog.findViewById(R.id.txt_close);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);


        startDatePicker = new DatePickerDialog(ReememberActivity.this, this, year, month, day);

        startDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        startDatePicker.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = startDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = startDatePicker.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });




        String[] frequencyArray = getResources().getStringArray(R.array.reminder_frequency);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequencyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReminder.setAdapter(adapter);

        spinnerReminder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequency1 = i;
                hideStartTimeView();
                hideNShow(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });




        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });








        callActvityTypeMasterAPI();



        tvTime.setOnClickListener(this);
        tvTime2.setOnClickListener(this);
        tvTime3.setOnClickListener(this);
        tvTime4.setOnClickListener(this);
        tvTime5.setOnClickListener(this);
        tvTime6.setOnClickListener(this);
        tvTime7.setOnClickListener(this);
        tvTime8.setOnClickListener(this);
        tvTime9.setOnClickListener(this);
        tvTime10.setOnClickListener(this);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        buttonAddReemider.setOnClickListener(this);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }

    private void hideNShow(int i) {
        switch (i) {

            case 2:
                linTime2.setVisibility(View.VISIBLE);
                break;

            case 3:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                break;

            case 4:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                break;
            case 5:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                linTime5.setVisibility(View.VISIBLE);
                break;
            case 6:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                linTime5.setVisibility(View.VISIBLE);
                linTime6.setVisibility(View.VISIBLE);
                break;
            case 7:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                linTime5.setVisibility(View.VISIBLE);
                linTime6.setVisibility(View.VISIBLE);
                linTime7.setVisibility(View.VISIBLE);
                break;
            case 8:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                linTime5.setVisibility(View.VISIBLE);
                linTime6.setVisibility(View.VISIBLE);
                linTime7.setVisibility(View.VISIBLE);
                linTime8.setVisibility(View.VISIBLE);
                break;
            case 9:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                linTime5.setVisibility(View.VISIBLE);
                linTime6.setVisibility(View.VISIBLE);
                linTime7.setVisibility(View.VISIBLE);
                linTime8.setVisibility(View.VISIBLE);
                linTime9.setVisibility(View.VISIBLE);
                break;
            case 10:
                linTime2.setVisibility(View.VISIBLE);
                linTime3.setVisibility(View.VISIBLE);
                linTime4.setVisibility(View.VISIBLE);
                linTime5.setVisibility(View.VISIBLE);
                linTime6.setVisibility(View.VISIBLE);
                linTime7.setVisibility(View.VISIBLE);
                linTime8.setVisibility(View.VISIBLE);
                linTime9.setVisibility(View.VISIBLE);
                linTime10.setVisibility(View.VISIBLE);
                break;


        }
    }

    private void hideStartTimeView() {
        linTime2.setVisibility(View.GONE);
        linTime3.setVisibility(View.GONE);
        linTime4.setVisibility(View.GONE);
        linTime5.setVisibility(View.GONE);
        linTime6.setVisibility(View.GONE);
        linTime7.setVisibility(View.GONE);
        linTime8.setVisibility(View.GONE);
        linTime9.setVisibility(View.GONE);
        linTime10.setVisibility(View.GONE);
    }



    private boolean validateAllData1(String activity, String startDate, String endDate, String time,int frequency)
    {
        boolean valid = true;

        if (activity != null)
        {
            if (activity.isEmpty())
            {
                valid = false;
                errorMsg = "Enter activity";
            }
        }
        else
        {
            valid = false;
        }

        if (valid && startDate != null)
        {
            if (startDate.isEmpty())
            {
                valid = false;
                errorMsg = "Select start date";
            }
        }
        else
        {
            valid = false;
        }

        if (valid && endDate != null)
        {
            if (endDate.isEmpty())
            {
                valid = false;
                errorMsg = "Select end date";
            }
        }
        else
        {
            valid = false;
        }

        if (valid && time != null)
        {
            /*if (time.isEmpty())
            {
                valid = false;
                errorMsg = "Select time";
            }*/
            if(frequency==1){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
            }else if(frequency==2){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString()==null || tvTime2.getText().toString().equalsIgnoreCase("")){
                    valid = false;
                    errorMsg = "Select time";
                }

            }
            else if(frequency==3){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
            }
            else if(frequency==4){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
            }
            else if(frequency==5){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime5.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
            }
            else if(frequency==6){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime5.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime6.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }

            }
            else if(frequency==7){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime5.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime6.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime7.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }


            }
            else if(frequency==8){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime5.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime6.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime7.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime8.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }

            }
            else if(frequency==9){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime5.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime6.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime7.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime8.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }  else if(tvTime9.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }

            }
            else if(frequency==10){
                if(tvTime.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime3.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime4.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime5.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime6.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime7.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime8.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }  else if(tvTime9.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
                else if(tvTime10.getText().toString().equalsIgnoreCase("Select Time")){
                    valid = false;
                    errorMsg = "Select time";
                }
            }
        }
        else
        {
            valid = false;
        }

        if (valid && frequency <= 0)
        {
            valid = false;
            errorMsg = "Select frequency";
        }

        if (valid &&spinner_activity_type.getSelectedItem().toString().equalsIgnoreCase("Select Activity"))
        {
            valid = false;
            errorMsg = "Select Activity";
        }

        return valid;
    }


    private boolean isSameDateSelected( ArrayList<String> reminderArrayList) {
        boolean isPresent = false;
        Set<String> set = new HashSet<>();
        for (String s : reminderArrayList) {
            if (!set.add(s)) {
                return true;
            }
        }

        return  isPresent;
    }



    private void callActvityTypeMasterAPI()
    {
//            utils.showProgressbar(context);

        sessionManager = new SessionManager(context);
        reeTestService = Client.getClient().create(ReeTestService.class);
        utils = new Utils();

        int   userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        Call<ClsReminderMaster> call = reeTestService.getAllRemiderActivityMaster();
        call.enqueue(new Callback<ClsReminderMaster>()
        {
            @Override
            public void onResponse(Call<ClsReminderMaster> call, Response<ClsReminderMaster> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsReminderMaster listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        ArrayList<String> arylst_Activities_list=listResponse.getData();
                        if (arylst_Activities_list!=null){
                            arylst_Activities_list.set(0,"Select Activity");
                            ArrayAdapter<String> adapter_when = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, arylst_Activities_list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_activity_type.setAdapter(adapter_when);
                            spinner_activity_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                when = i;


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }


                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ClsReminderMaster> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }




    @Override
    public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (view.getId()) {

            //remimnders



            case R.id.buttonScheduleReminder:

                if (sessionManagerreem.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                    return;
                }
//
//                startActivityForResult(new Intent(context, ReminderSheduleActivity.class), ADD_REMINDERS);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
                showDialogReeminder();


                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context)) {
                    showLayouts();
                    callToGetAllReminders();
                } else
                    showLayouts();
                break;


            //mediciens
            case R.id.textMedicines_Add:


                if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                    shownoplan();
                    return;
                }

//
//                FragmentManager fm = getSupportFragmentManager();
//                dialogFragment = new AddMedicineDialogNewFragment(ReememberActivity.this);
//                dialogFragment.show(fm, "add_fragment");
//                showMedicineDailog();

                showDialog();
                break;


            case R.id.tvStartDate:

                isEndDate = false;
                startDatePicker.show();
                break;

            case R.id.tvEndDate:
                isEndDate = true;
                startDatePicker.show();
                break;

            case R.id.tvTime:
                isWhichTimeSlected = "1";

                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;


            case R.id.tvTime2:
                isWhichTimeSlected = "2";

                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime3:
                isWhichTimeSlected = "3";
                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime4:
                isWhichTimeSlected = "4";
                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime5:
                isWhichTimeSlected = "5";
                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime6:
                isWhichTimeSlected = "6";
                timepickerdialog.show();
                break;

            case R.id.tvTime7:
                isWhichTimeSlected = "7";
                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime8:
                isWhichTimeSlected = "8";
                timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime9:
                isWhichTimeSlected = "9";
                timepickerdialog.show();
                break;
            case R.id.tvTime10:
                isWhichTimeSlected = "10";
                timepickerdialog.show();
                break;


            case R.id.buttonAddReemider:

                ArrayList<String> reminderArrayList = new ArrayList<>();
                lsActivity = "";
                lsActivity = etActivityName.getText().toString().trim();
                if (lsActivity.isEmpty()){
                    Toast.makeText(context, "Please enter activity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lsStartDate.isEmpty()){
                    Toast.makeText(context, "Please select start date", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (lsEndDate.isEmpty()){
                    Toast.makeText(context, "Please select end date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (lsTime.isEmpty()){
                    Toast.makeText(context, "Please select time", Toast.LENGTH_SHORT).show();
                    return;
                }








                boolean isDataValid = validateAllData1(lsActivity, lsStartDate, lsEndDate, lsTime, frequency1);



                if (isDataValid) {
                    if (Utils.isNetworkAvailable(context)) {

                        if (frequency1 == 1) {
                            reminderArrayList.add(tvTime.getText().toString());
                        } else if (frequency1 == 2) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                        } else if (frequency1 == 3) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                        } else if (frequency1 == 4) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                        } else if (frequency1 == 5) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                        } else if (frequency1 == 6) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                        } else if (frequency1 == 7) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                        } else if (frequency1 == 8) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                        } else if (frequency1 == 9) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                            reminderArrayList.add(tvTime9.getText().toString());
                        } else if (frequency1 == 10) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                            reminderArrayList.add(tvTime9.getText().toString());
                            reminderArrayList.add(tvTime10.getText().toString());
                        }


                        /*JSONArray jsArray = new JSONArray(reminderArrayList);
                        String remindertest = jsArray.toString();*/
                        if(isSameDateSelected(reminderArrayList)==true){
                            Utils.shortToast(context,"time should not be same.");
                        }else {

                            callSheduleReminder(reminderArrayList);

                        }
                    } else {
                        Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
                    }
                } else {
                    Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }
                break;






            default:
        }
    }

    private void showMedicineDailog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);


        builder.setMessage("Medicine")
                .setCancelable(false)
                .setPositiveButton("Add Medicine", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        FragmentManager fm = getSupportFragmentManager();
                        dialogFragment = new AddMedicineDialogNewFragment(ReememberActivity.this);
                        dialogFragment.show(fm, "add_fragment");


                    }
                })
                .setNegativeButton("Upload Medicine", new DialogInterface.OnClickListener() {
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


    // below methods are from Fragment callback for Adding newly
    @Override
    public void onMedicineAdd(int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, boolean IsNotification) {
//        Toast.makeText(context, "AC = Mid:" + medicineId + " Fre:" + frequency + " Day:" + days, Toast.LENGTH_LONG).show();

        utils.showProgressbar(context);

        if (Utils.isNetworkAvailable(context)) {
            callAddMedicineApi(medicineId, frequency, days, eatingGSon, list, fileuploadimage, medicineName, IsNotification);
        } else {
            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
        }
    }

    // below methods are from Fragment callback for Edit(updating)
    @Override
    public void onMedicineEdit(int myMedID, int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, String imageName, String imagepath, boolean IsNotification) {
        utils.showProgressbar(context);

        if (Utils.isNetworkAvailable(context)) {

            callEditMedicineApi(myMedID, medicineId, frequency, days, userID, eatingGSon, list, fileuploadimage, medicineName, imageName, imagepath, IsNotification);
        } else {
            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
        }
//        callEditMedicineApi(myMedicine.getMyMedId(), myMedicine.getMedId(), myMedicine.getMedFreq(), myMedicine.getMedDays(), userID);

    }

    public static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;

        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    private void callAddMedicineApi(int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, boolean IsNotification) {

        RequestBody photoContent;
        MultipartBody.Part photo;

        if (fileuploadimage.getName().isEmpty()) {
            photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            photo = MultipartBody.Part.createFormData("ImgFile", "", photoContent);
        } else {
            photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileuploadimage);
            photo = MultipartBody.Part.createFormData("ImgFile", fileuploadimage.getName(), photoContent);
        }


        AddMedicineRequest request = new AddMedicineRequest();
        request.setUserID(userId);
        request.setChekdedNoti(IsNotification);
        request.setMedicineName(medicineName);
        request.setMedicineId(medicineId);
        request.setFrequency(frequency.trim());
        request.setDosage(days);

        ArrayList<FrequencyList> frequencyLists = new ArrayList<>();

        for (int i = 0; i < eatingGSon.length(); i++) {

            JSONObject json_obj = null;   //get the 3rd item
            try {
                json_obj = eatingGSon.getJSONObject(i);
                String EatingStatus = json_obj.getString("EatingStatus");
                String EatingTime = json_obj.getString("EatingTime");
                frequencyLists.add(new FrequencyList(EatingStatus, EatingTime, 0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        request.setFrequencyList(frequencyLists);


        String request2 = new Gson().toJson(request);

        //Here the json data is add to a hash map with key data
//        Map<String,String> params = new HashMap<String, String>();
//            params.put("Medicine", request1);
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("Medicine", request2)
//                .build();


        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), request2);


//        FoodService foodService = Client.getClientMultiPart().create(FoodService.class);
        medService = Client.getClientMultiPart().create(MedicineService.class);


        Call<AddMedicineResponse> call = medService.UpdateMedicine(photo, body);
        call.enqueue(new Callback<AddMedicineResponse>() {
            @Override
            public void onResponse(Call<AddMedicineResponse> call, Response<AddMedicineResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    AddMedicineResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        dialogFragment.dismiss();
                        // reload medicinesListView
                        myMedicinesList.clear();
                        callMedicineListAPI(userId);
                        dialog.dismiss();

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<AddMedicineResponse> call, Throwable t) {

                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void callEditMedicineApi(int myMedID, int medicineId, String frequency, String days, int userID, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, String imageName, String imagepath, boolean IsNotification) {
        EditMedicineApiRequest request = new EditMedicineApiRequest();
        request.setMyMedicineID(myMedID);
        request.setMedicineId(medicineId);
        request.setName(medicineName);
        request.setFrequency(String.valueOf(frequency));
        request.setDosage(days);
        request.setIsNotification(IsNotification);

        request.setUserID(userID);
        request.setImgPath(imagepath);
        request.setImgName(imageName);
//        request.setFrequencyList(toStringArray(eatingGSon));
//        request.setFrequencyList(list);

        ArrayList<FrequencyList> frequencyLists = new ArrayList<>();

        for (int i = 0; i < eatingGSon.length(); i++) {

            JSONObject json_obj = null;   //get the 3rd item
            try {
                json_obj = eatingGSon.getJSONObject(i);
                String EatingStatus = json_obj.getString("EatingStatus");
                String EatingTime = json_obj.getString("EatingTime");
                int id = Integer.parseInt(json_obj.getString("Id"));
                frequencyLists.add(new FrequencyList(EatingStatus, EatingTime, id));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        request.setFrequencyList(frequencyLists);
        String requestedit = new Gson().toJson(request);


        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), requestedit);
        MultipartBody.Part photo = null;
        if (!fileuploadimage.getPath().isEmpty()) {
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileuploadimage);
            photo = MultipartBody.Part.createFormData("ImgFile", fileuploadimage.getName(), photoContent);
        } else {
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            photo = MultipartBody.Part.createFormData("ImgFile", "", photoContent);

        }


        medService = Client.getClientMultiPart().create(MedicineService.class);

        Call<AddMedicineResponse> call = medService.UpdateMedicine(photo, body);
        call.enqueue(new Callback<AddMedicineResponse>() {
            @Override
            public void onResponse(Call<AddMedicineResponse> call, Response<AddMedicineResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    AddMedicineResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        dialogFragment.dismiss();

                        callMedicineListAPI(userId);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<AddMedicineResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callDeleteMedicineApi(int myMedID, final int userID, final int position) {
        DeleteMedicineRequest request = new DeleteMedicineRequest();
        request.setMyMedicineId(myMedID);
        request.setUserID(userID);

        Call<DeleteMedicineResponse> call = medService.deleteMedicines(request);
        call.enqueue(new Callback<DeleteMedicineResponse>() {
            @Override
            public void onResponse(Call<DeleteMedicineResponse> call, Response<DeleteMedicineResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    DeleteMedicineResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        // call List API to reload list

                        subscribeFeatureAdapter.removeItem(position);
                        subscribeFeatureAdapter.notifyDataSetChanged();
                        callMedicineListAPI(userID);

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteMedicineResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });

    }


    // below methods are from Adapter callback fro Editing Current medicine
    public void updateMedicine(int position, MyMedicine myMedicine) {
//        if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
//            shownoplan();
//            return;
//        }
//
//
//        try {
//            FragmentManager fm = getSupportFragmentManager();
//            dialogFragment = new AddMedicineDialogNewFragment(ReememberActivity.this);
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("isEdit", true);
//            bundle.putSerializable("myMedicine", myMedicine);
//            dialogFragment.setArguments(bundle);
//            dialogFragment.show(fm, "edit_fragment");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        showDialogedit(myMedicine);


    }


    private void showDialogedit(final MyMedicine myMedicine) {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        isAdd = false;

        edt_Medicine = dialog.findViewById(R.id.edtText_NameMedicine);
        txt_Days = dialog.findViewById(R.id.text_time_acrtivty12);
        spinnerFreq = dialog.findViewById(R.id.spinnerAddMedicine_Frequency);
        spinner_time = dialog.findViewById(R.id.spinner_time);
        spinner_time2 = dialog.findViewById(R.id.spinner_time2);
        spinner_time3 = dialog.findViewById(R.id.spinner_time3);

        txt_time1 = dialog.findViewById(R.id.txt_time1);
        txt_time2 = dialog.findViewById(R.id.txt_time2);
        txt_time3 = dialog.findViewById(R.id.txt_time3);

        spinnerDays = dialog.findViewById(R.id.spinnerAddMedicine_Days);

        imgAdd = dialog.findViewById(R.id.imgAdd);
        img_add_medicine = dialog.findViewById(R.id.img_add_medicine);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMedicineImageDailog();
            }
        });

        imgAdd.setText("Upload Again");

        ll_1 = dialog.findViewById(R.id.ll_1);
        ll_2 = dialog.findViewById(R.id.ll_2);
        ll_3 = dialog.findViewById(R.id.ll_3);

        lay1 = dialog.findViewById(R.id.lay1);
        lay2 = dialog.findViewById(R.id.lay2);
        lay3 = dialog.findViewById(R.id.lay3);

        switch_noti = dialog.findViewById(R.id.switch_noti);
        switch_noti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                IsNotification = isChecked;
            }
        });
        buttonAddMedicine = dialog.findViewById(R.id.buttonAddMedicine);

        TextView txt_close = dialog.findViewById(R.id.txt_close);
        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        String[] array = getResources().getStringArray(R.array.medicine_days);
        spinnerDays.setItems(array);
        spinnerDays.setSelection(new int[]{});
        spinnerDays.setListener(this);


        txt_Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDays.performClick();
            }
        });


        edt_Medicine.setText(myMedicine.getMedName());
        switch_noti.setChecked(myMedicine.IsNotification());

        edt_Medicine.setEnabled(false);

        LinearLayout lay_delete = dialog.findViewById(R.id.lay_delete);
        lay_delete.setVisibility(View.VISIBLE);

        lay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                img_add_medicine.setImageBitmap(null);


            }
        });



        String newDays = "";

        Glide.with(context)

                .load(myMedicine.getPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
//                .apply(RequestOptions.circleCropTransform())

//                                            .apply(options)
                .into(img_add_medicine);

        final String[] frequencyArray = getResources().getStringArray(R.array.medicine_frequency);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, frequencyArray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);
        frequency_whenArray_api = getResources().getStringArray(R.array.medicine_frequency_when);
        frequency_whenArray = getResources().getStringArray(R.array.medicine_frequency_when);
        adapter_whens = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, frequency_whenArray);

        spinner_time.setAdapter(adapter_whens);
        spinner_time2.setAdapter(adapter_whens);
        spinner_time3.setAdapter(adapter_whens);

        for (int i = 0; i < frequencyArray.length; i++) {

            if (myMedicine.getMedFreq().equalsIgnoreCase(frequencyArray[i].toString())) {
                spinnerFreq.setSelection(i);
                break;

            }
        }

        frequency = myMedicine.getMedFreq();
        if (myMedicine.getFrequencyList().size() == 1) {
            fromEatingStatus = "daily";
            fromFrequency = "daily";
            lay1.setVisibility(View.VISIBLE);
//            lay2.setVisibility(View.VISIBLE);
//            lay3.setVisibility(View.VISIBLE);
            ll_1.setVisibility(View.VISIBLE);
            txt_time1.setText(myMedicine.getFrequencyList().get(0).getEatingTime());


            for (int i = 0; i < frequency_whenArray_api.length; i++) {
                if (myMedicine.getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())) {
                    spinner_time.setSelection(i);
                    when1 = 1;

                    break;

                }
            }

        }


        if (myMedicine.getFrequencyList().size() == 2) {
            ll_1.setVisibility(View.VISIBLE);
            ll_2.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
//            lay3.setVisibility(View.VISIBLE);
            fromEatingStatus = "twice";
            fromFrequency = "twice";

            txt_time1.setText(myMedicine.getFrequencyList().get(0).getEatingTime());
            txt_time2.setText(myMedicine.getFrequencyList().get(1).getEatingTime());


            for (int i = 0; i < frequency_whenArray_api.length; i++) {
                if (myMedicine.getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())) {
                    spinner_time.setSelection(i);
                    break;

                }
            }

            for (int i = 0; i < frequency_whenArray_api.length; i++) {
                if (myMedicine.getFrequencyList().get(1).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())) {
                    spinner_time2.setSelection(i);
                    break;

                }
            }

        }

        if (myMedicine.getFrequencyList().size() == 3) {
            ll_1.setVisibility(View.VISIBLE);
            ll_2.setVisibility(View.VISIBLE);
            ll_3.setVisibility(View.VISIBLE);
            lay1.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.VISIBLE);
            lay3.setVisibility(View.VISIBLE);
            fromEatingStatus = "thrice";
            fromFrequency = "thrice";


            txt_time1.setText(myMedicine.getFrequencyList().get(0).getEatingTime());
            txt_time2.setText(myMedicine.getFrequencyList().get(1).getEatingTime());
            txt_time3.setText(myMedicine.getFrequencyList().get(2).getEatingTime());


            for (int i = 0; i < frequency_whenArray_api.length; i++) {
                if (myMedicine.getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())) {
                    spinner_time.setSelection(i);
                    break;

                }
            }


            for (int i = 0; i < frequency_whenArray_api.length; i++) {
                if (myMedicine.getFrequencyList().get(1).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())) {
                    spinner_time2.setSelection(i);
                    break;

                }
            }


            for (int i = 0; i < frequency_whenArray_api.length; i++) {
                if (myMedicine.getFrequencyList().get(2).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())) {
                    spinner_time3.setSelection(i);
                    break;

                }
            }


        }

        try {
            days = myMedicine.getMedDays();
            newDays = myMedicine.getMedDays();
//
            if (newDays.equalsIgnoreCase("2,3,4,5,6,7,8")) {

                String newDaysstr = "1," + newDays;
                spinnerDays.setSelection(DaysUtils.getIntArray(newDaysstr, ","));

            } else {
                spinnerDays.setSelection(DaysUtils.getIntArray(newDays, ","));

            }


            String str = newDays;
            ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(str.split(",")));

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < elephantList.size(); i++) {


                if (elephantList.get(i).toString().trim().equalsIgnoreCase("2")) {
                    stringBuilder.append("Sun,");
                }

                if (elephantList.get(i).toString().trim().equalsIgnoreCase("3")) {

                    stringBuilder.append("Mon,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("4")) {
                    stringBuilder.append("Tue,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("5")) {

                    stringBuilder.append("Wed,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("6")) {
                    stringBuilder.append("Thu,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("7")) {
                    stringBuilder.append("Fri,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("8")) {
                    stringBuilder.append("Sat,");

                }
            }

            txt_Days.setText(removeLastChar(stringBuilder.toString()));
//            days = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }




        spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequency = frequencyArray[i].toString();
                String strFreq = frequencyArray[i].toString();
                switch (i) {

                    case 1:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.GONE);
                        ll_3.setVisibility(View.GONE);

                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.GONE);
                        lay3.setVisibility(View.GONE);
                        fromEatingStatus = "daily";
                        break;
                    case 2:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.VISIBLE);
                        ll_3.setVisibility(View.GONE);

                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.VISIBLE);
                        lay3.setVisibility(View.GONE);
                        fromEatingStatus = "twice";

                        break;

                    case 3:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.VISIBLE);
                        ll_3.setVisibility(View.VISIBLE);

                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.VISIBLE);
                        lay3.setVisibility(View.VISIBLE);
                        fromEatingStatus = "thrice";

                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strEatingStatus1 = frequency_whenArray[i];
                when1 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_time2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strEatingStatus2 = frequency_whenArray[i];
                when2 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_time3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strEatingStatus3 = frequency_whenArray[i];
                when3 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);
        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


            }
        });

        txt_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromFrequency = "daily";
                timepickerdialog.show();
            }
        });


        txt_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromFrequency = "twice";
                timepickerdialog.show();
            }
        });

        txt_time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromFrequency = "thrice";
                timepickerdialog.show();
            }
        });


        TextView txt_header = dialog.findViewById(R.id.txt_header);


        txt_header.setText("Update Medicine");
        buttonAddMedicine.setText("Update Medicine");


        buttonAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_Medicine.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter Medicine name", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean dataIsValid = validateAllData(medicineId, frequency, days, when1, fromFrequency);
                if (dataIsValid) {

                    ArrayList<EatingRequest> medicineRequests = new ArrayList<>();
                    if (fromEatingStatus.equalsIgnoreCase("daily")) {
//                        if (isEditData){
                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(), txt_time1.getText().toString().trim(), myMedicine.getFrequencyList().get(0).getId()));

//                        }else {
//                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(),txt_time1.getText().toString().trim(),0));
//
//                        }

                    }


                    if (fromEatingStatus.equalsIgnoreCase("twice")) {
//                        if (isEditData){
                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(), txt_time1.getText().toString().trim(), myMedicine.getFrequencyList().get(0).getId()));
                        if (myMedicine.getFrequencyList().size() > 1) {
                            medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(), txt_time2.getText().toString().trim(), myMedicine.getFrequencyList().get(1).getId()));

                        } else {
                            medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(), txt_time2.getText().toString().trim(), 0));

                        }
//                        }else {
//                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(),txt_time1.getText().toString().trim(),0));
//                        medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(),txt_time2.getText().toString().trim(),0));
//                        }


                    }


                    if (fromEatingStatus.equalsIgnoreCase("thrice")) {

//                        if (isEditData){
                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(), txt_time1.getText().toString().trim(), myMedicine.getFrequencyList().get(0).getId()));
                        if (myMedicine.getFrequencyList().size() > 1) {
                            medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(), txt_time2.getText().toString().trim(), myMedicine.getFrequencyList().get(1).getId()));

                        } else {
                            medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(), txt_time2.getText().toString().trim(), 0));

                        }
                        if (myMedicine.getFrequencyList().size() > 2) {
                            medicineRequests.add(new EatingRequest(spinner_time3.getSelectedItem().toString().trim(), txt_time3.getText().toString().trim(), myMedicine.getFrequencyList().get(2).getId()));

                        } else {
                            medicineRequests.add(new EatingRequest(spinner_time3.getSelectedItem().toString().trim(), txt_time3.getText().toString().trim(), 0));

                        }
//                        }else {
//                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(),txt_time1.getText().toString().trim(),0));
//
//                        medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(),txt_time2.getText().toString().trim(),0));
//                        medicineRequests.add(new EatingRequest(spinner_time3.getSelectedItem().toString().trim(),txt_time3.getText().toString().trim(),0));
//                        }


                    }


                    JSONArray array = new JSONArray();
                    try {
                        for (int i = 0; i < medicineRequests.size(); i++) {
                            JSONObject json = new JSONObject(); // update here
                            json.put("EatingStatus", medicineRequests.get(i).getEatingStatus());
                            json.put("EatingTime", medicineRequests.get(i).getEatingTime());
                            json.put("Id", medicineRequests.get(i).getId());
                            array.put(json).toString().replaceAll("\"", "");

                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }

                    ArrayList<Object> list = convert(array);

                    if (fromEatingStatus.equalsIgnoreCase("daily")) {
                        strSubmitWhen = spinner_time.getSelectedItem().toString().trim();
                        strSubmitTime = txt_time1.getText().toString().trim();

                    }
                    if (fromEatingStatus.equalsIgnoreCase("twice")) {
                        strSubmitWhen = spinner_time.getSelectedItem().toString().trim() + "," + spinner_time2.getSelectedItem().toString().trim();
                        strSubmitTime = txt_time1.getText().toString().trim() + "," + txt_time2.getText().toString().trim();

                    }

                    if (fromEatingStatus.equalsIgnoreCase("thrice")) {
                        strSubmitWhen = spinner_time.getSelectedItem().toString().trim() + "," + spinner_time2.getSelectedItem().toString().trim() + "," + spinner_time3.getSelectedItem().toString().trim();
                        strSubmitTime = txt_time1.getText().toString().trim() + "," + txt_time2.getText().toString().trim() + "," + txt_time3.getText().toString().trim();

                    }


//                    sendData(medicineId, frequency, days,array,list,fileuploadimage,edt_Medicine.getText().toString(),edt_Medicine.getText().toString(),IsNotification);

                    callEditMedicineApi(myMedicine.getMyMedId(), medicineId, frequency, days, userID, array, list, fileuploadimage, edt_Medicine.getText().toString(), myMedicine.getImageName(), myMedicine.getPath(), IsNotification);


                }


            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }


    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }


    private void showDialog() {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        isAdd = true;

        edt_Medicine = dialog.findViewById(R.id.edtText_NameMedicine);
        txt_Days = dialog.findViewById(R.id.text_time_acrtivty12);
        spinnerFreq = dialog.findViewById(R.id.spinnerAddMedicine_Frequency);
        spinner_time = dialog.findViewById(R.id.spinner_time);
        spinner_time2 = dialog.findViewById(R.id.spinner_time2);
        spinner_time3 = dialog.findViewById(R.id.spinner_time3);

        txt_time1 = dialog.findViewById(R.id.txt_time1);
        txt_time2 = dialog.findViewById(R.id.txt_time2);
        txt_time3 = dialog.findViewById(R.id.txt_time3);

        spinnerDays = dialog.findViewById(R.id.spinnerAddMedicine_Days);


        switch_noti = dialog.findViewById(R.id.switch_noti);
        switch_noti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                IsNotification = isChecked;
            }
        });

        imgAdd = dialog.findViewById(R.id.imgAdd);
        img_add_medicine = dialog.findViewById(R.id.img_add_medicine);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMedicineImageDailog();
            }
        });

        ll_1 = dialog.findViewById(R.id.ll_1);
        ll_2 = dialog.findViewById(R.id.ll_2);
        ll_3 = dialog.findViewById(R.id.ll_3);

        lay1 = dialog.findViewById(R.id.lay1);
        lay2 = dialog.findViewById(R.id.lay2);
        lay3 = dialog.findViewById(R.id.lay3);

        LinearLayout lay_delete = dialog.findViewById(R.id.lay_delete);
        lay_delete.setVisibility(View.GONE);

        l1 = dialog.findViewById(R.id.l1);
        l1.setVisibility(View.GONE);
        l2 = dialog.findViewById(R.id.l2);
        l2.setVisibility(View.VISIBLE);

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMedicineImageDailog();
            }
        });



        buttonAddMedicine = dialog.findViewById(R.id.buttonAddMedicine);

        TextView txt_close = dialog.findViewById(R.id.txt_close);
        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        String[] array = getResources().getStringArray(R.array.medicine_days);
        spinnerDays.setItems(array);
        spinnerDays.setSelection(new int[]{});
        spinnerDays.setListener(this);


        txt_Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDays.performClick();
            }
        });


        final String[] frequencyArray = getResources().getStringArray(R.array.medicine_frequency);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, frequencyArray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);
        spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequency = frequencyArray[i].toString();
                String strFreq = frequencyArray[i].toString();
                switch (i) {

                    case 1:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.GONE);
                        ll_3.setVisibility(View.GONE);

                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.GONE);
                        lay3.setVisibility(View.GONE);
                        fromEatingStatus = "daily";
                        break;
                    case 2:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.VISIBLE);
                        ll_3.setVisibility(View.GONE);

                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.VISIBLE);
                        lay3.setVisibility(View.GONE);
                        fromEatingStatus = "twice";

                        break;

                    case 3:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.VISIBLE);
                        ll_3.setVisibility(View.VISIBLE);

                        lay1.setVisibility(View.VISIBLE);
                        lay2.setVisibility(View.VISIBLE);
                        lay3.setVisibility(View.VISIBLE);
                        fromEatingStatus = "thrice";

                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        frequency_whenArray = getResources().getStringArray(R.array.medicine_frequency_when);
        adapter_whens = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, frequency_whenArray);

        spinner_time.setAdapter(adapter_whens);
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strEatingStatus1 = frequency_whenArray[i];
                when1 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner_time2.setAdapter(adapter_whens);
        spinner_time2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strEatingStatus2 = frequency_whenArray[i];
                when2 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner_time3.setAdapter(adapter_whens);
        spinner_time3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strEatingStatus3 = frequency_whenArray[i];
                when3 = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);
        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


            }
        });

        txt_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromFrequency = "daily";
                timepickerdialog.show();
            }
        });


        txt_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromFrequency = "twice";
                timepickerdialog.show();
            }
        });

        txt_time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromFrequency = "thrice";
                timepickerdialog.show();
            }
        });


        buttonAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_Medicine.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter Medicine name", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean dataIsValid = validateAllData(medicineId, frequency, days, when1, fromFrequency);
                if (dataIsValid) {

                    ArrayList<EatingRequest> medicineRequests = new ArrayList<>();
                    if (fromEatingStatus.equalsIgnoreCase("daily")) {
//                        if (isEditData){
//                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),myMedicine.getFrequencyList().get(0).getId()));
//
//                        }else {
                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(), txt_time1.getText().toString().trim(), 0));

//                        }

                    }


                    if (fromEatingStatus.equalsIgnoreCase("twice")) {
//                        if (isEditData){
//                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),myMedicine.getFrequencyList().get(0).getId()));
//                            if (myMedicine.getFrequencyList().size()>1){
//                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),myMedicine.getFrequencyList().get(1).getId()));
//
//                            }else {
//                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),0));
//
//                            }
//                        }else {
                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(), txt_time1.getText().toString().trim(), 0));
                        medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(), txt_time2.getText().toString().trim(), 0));
//                        }


                    }


                    if (fromEatingStatus.equalsIgnoreCase("thrice")) {

//                        if (isEditData){
//                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),myMedicine.getFrequencyList().get(0).getId()));
//                            if (myMedicine.getFrequencyList().size()>1){
//                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),myMedicine.getFrequencyList().get(1).getId()));
//
//                            }else {
//                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),0));
//
//                            }
//                            if (myMedicine.getFrequencyList().size()>2){
//                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when3.getSelectedItem().toString().trim(),text_time_trice.getText().toString().trim(),myMedicine.getFrequencyList().get(2).getId()));
//
//                            }else {
//                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when3.getSelectedItem().toString().trim(),text_time_trice.getText().toString().trim(),0));
//
//                            }
//                        }else {
                        medicineRequests.add(new EatingRequest(spinner_time.getSelectedItem().toString().trim(), txt_time1.getText().toString().trim(), 0));

                        medicineRequests.add(new EatingRequest(spinner_time2.getSelectedItem().toString().trim(), txt_time2.getText().toString().trim(), 0));
                        medicineRequests.add(new EatingRequest(spinner_time3.getSelectedItem().toString().trim(), txt_time3.getText().toString().trim(), 0));
//                        }


                    }


                    JSONArray array = new JSONArray();
                    try {
                        for (int i = 0; i < medicineRequests.size(); i++) {
                            JSONObject json = new JSONObject(); // update here
                            json.put("EatingStatus", medicineRequests.get(i).getEatingStatus());
                            json.put("EatingTime", medicineRequests.get(i).getEatingTime());
                            json.put("Id", medicineRequests.get(i).getId());
                            array.put(json).toString().replaceAll("\"", "");

                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }

                    ArrayList<Object> list = convert(array);

                    if (fromEatingStatus.equalsIgnoreCase("daily")) {
                        strSubmitWhen = spinner_time.getSelectedItem().toString().trim();
                        strSubmitTime = txt_time1.getText().toString().trim();

                    }
                    if (fromEatingStatus.equalsIgnoreCase("twice")) {
                        strSubmitWhen = spinner_time.getSelectedItem().toString().trim() + "," + spinner_time2.getSelectedItem().toString().trim();
                        strSubmitTime = txt_time1.getText().toString().trim() + "," + txt_time2.getText().toString().trim();

                    }

                    if (fromEatingStatus.equalsIgnoreCase("thrice")) {
                        strSubmitWhen = spinner_time.getSelectedItem().toString().trim() + "," + spinner_time2.getSelectedItem().toString().trim() + "," + spinner_time3.getSelectedItem().toString().trim();
                        strSubmitTime = txt_time1.getText().toString().trim() + "," + txt_time2.getText().toString().trim() + "," + txt_time3.getText().toString().trim();

                    }


                    sendData(medicineId, frequency, days, array, list, fileuploadimage, edt_Medicine.getText().toString(), edt_Medicine.getText().toString(), IsNotification);


                }


            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void sendData(int medicineId, String frequency, String days, JSONArray EatingGSon, ArrayList<Object> list, File fileuploadimage, String name, String medicineName, boolean IsNotification) {
        callAddMedicineApi(medicineId, frequency, days, EatingGSon, list, fileuploadimage, medicineName, IsNotification);


//        ReememberActivity.AddMedicineDialogListener listener = (ReememberActivity.AddMedicineDialogListener) getApplication();
//        listener.onMedicineAdd(medicineId, frequency, days,EatingGSon,list,fileuploadimage,medicineName,IsNotification);
    }

    //    public interface AddMedicineDialogListener
//    {
//
//        void onMedicineAdd(int medicineId, String frequency, String inputText, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, boolean onMedicineAdd);
//
//        void onMedicineEdit(int myMedID, int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, String imageName, String imagepath, boolean onMedicineAdd);
//
//    }
    private boolean validateAllData(int medicineId, String frequency, String days, int when, String fromFrequency) {
        boolean valid = true;

//        if (medicineId == 0) {
//            valid = false;
//            errorMsg = "Select medicine";
//            return valid;
//
//        }
        if (days.isEmpty()) {
            valid = false;
            errorMsg = "Select days";
            return valid;

        }
        if (frequency.equalsIgnoreCase("Select frequency")) {
            valid = false;
            errorMsg = "Select frequency";
            return valid;
        }
        switch (fromEatingStatus) {

            case "daily":
                if (txt_time1.getText().toString().isEmpty()) {
                    errorMsg = "Please select time for first slot";
                    valid = false;
                    return valid;
                }

                if (when1 == 0) {

                    valid = false;
                    errorMsg = "Select when from first slot";
                    return valid;
                }
                break;

            case "twice":

                if (txt_time1.getText().toString().isEmpty()) {
                    errorMsg = "Please select time for first slot";

//                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    valid = false;
                    return valid;
                }


                if (when1 == 0) {

                    valid = false;
                    errorMsg = "Select when from first slot";
                    return valid;
                }


                if (strEatingStatus1.equalsIgnoreCase(strEatingStatus2)) {
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }


                if (txt_time2.getText().toString().isEmpty()) {
                    errorMsg = "Please select time for second slot";

                    valid = false;
                    return valid;
                }


//                if (isAdd == true) {

                if (when2 == 0) {

                    valid = false;
                    errorMsg = "Select when from second slot";
                    return valid;

                }
//                }else{
//
//
//                    if (when2 == 0) {
//
//                        valid = false;
//                        errorMsg = "Select when from second slot";
//                        return valid;
//
//                    }






                break;

            case "thrice":


                if (txt_time1.getText().toString().isEmpty()) {
                    errorMsg = "Please select time for first slot";
                    valid = false;
                    return valid;
                }
                if (when1 == 0) {

                    valid = false;
                    errorMsg = "Select when from first slot";
                    return valid;

                }

                if (strEatingStatus1.equalsIgnoreCase(strEatingStatus2)) {
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (txt_time2.getText().toString().isEmpty()) {
                    errorMsg = "Please select time for second slot";

                    valid = false;
                    return valid;
                }
                if (when2 == 0) {

                    valid = false;
                    errorMsg = "Select when from second slot";
                    return valid;

                }
                if (strEatingStatus2.equalsIgnoreCase(strEatingStatus1)) {
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (txt_time3.getText().toString().isEmpty()) {
                    errorMsg = "Please select time for third slot";
                    valid = false;
                    return valid;
                }

                if (when3 == 0) {

                    valid = false;
                    errorMsg = "Select when from third slot";
                    return valid;

                }


                if (strEatingStatus1.equalsIgnoreCase(strEatingStatus2)) {
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (strEatingStatus2.equalsIgnoreCase(strEatingStatus3)) {
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (strEatingStatus3.equalsIgnoreCase(strEatingStatus1)) {
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                break;
        }

        return valid;
    }

    public ArrayList<Object> convert(JSONArray jArr) {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            for (int i = 0, l = jArr.length(); i < l; i++) {
                list.add(jArr.get(i));
            }
        } catch (JSONException e) {
        }

        return list;
    }

    // below methods are from Adapter callback fro Deleting Current medicine
    @Override
    public void deleteMedicine(final int position, final MyMedicine myMedicine) {
        if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
            shownoplan();
            return;
        }


//        Toast.makeText(context, "Delete = " + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Medicine!")
                .setMessage("Do you really want to delete this medicine?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context)) {
                            callDeleteMedicineApi(myMedicine.getMyMedId(), userID, position);
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


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {


        Date tDate = new Date();
        //SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);

        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        time = formatter.format(cal.getTime());

        if(isReeminder==true){

            Date tDate1 = new Date();
            //SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            todayDate = dateFormatter.format(tDate1);

            if (lsStartDate != null && lsEndDate != null)
            {
                if (todayDate.equalsIgnoreCase(lsStartDate) || todayDate.equalsIgnoreCase(lsEndDate))
                {
                    Calendar current = Calendar.getInstance();
                    Calendar datetime = Calendar.getInstance();

                    datetime.set(Calendar.HOUR_OF_DAY, i);
                    datetime.set(Calendar.MINUTE, i1);

//                if (datetime.getTimeInMillis() > current.getTimeInMillis())
//                {
                    Calendar cal1 = Calendar.getInstance();

                    cal1.set(Calendar.HOUR_OF_DAY, i);
                    cal1.set(Calendar.MINUTE, i1);

//                    Format formatter;
                    formatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    lsTime = "";
                    String abc[]=formatter.format(cal.getTime()).split(" ");

                    if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                        lsTime = abc[0]+" AM";
                    }else{
//                            sb.append(abc[0]+" PM");
                        lsTime = abc[0]+" PM";
                    }


//                    lsTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsTime))
                        if(isWhichTimeSlected.equalsIgnoreCase("1")) {
                            tvTime.setText(lsTime);
                        }else if(isWhichTimeSlected.equalsIgnoreCase("2")){
                            tvTime2.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("3")){
                            tvTime3.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("4")){
                            tvTime4.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("5")){
                            tvTime5.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("6")){
                            tvTime6.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("7")){
                            tvTime7.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("8")){
                            tvTime8.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("9")){
                            tvTime9.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("10")){
                            tvTime10.setText(lsTime);
                        }

//                }
//                else
//                {
//                    //it's before current'
//                    Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
//                    //clearvariables();
//                }
                }
                else
                {
                    //clearvariables();

                    Calendar cal1 = Calendar.getInstance();
                    cal1.set(Calendar.HOUR_OF_DAY, i);
                    cal1.set(Calendar.MINUTE, i1);

//                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
//                    lsTime = "";

                    formatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    lsTime = "";
                    String abc[]=formatter.format(cal.getTime()).split(" ");

                    if(abc[1].equals("am")||abc[1].equals("Am")){
//                            sb.append(abc[0]+" AM");
                        lsTime = abc[0]+" AM";
                    }else{
//                            sb.append(abc[0]+" PM");
                        lsTime = abc[0]+" PM";
                    }

//                    lsTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsTime))
                        if(isWhichTimeSlected.equalsIgnoreCase("1")) {
                            tvTime.setText(lsTime);
                        }else if(isWhichTimeSlected.equalsIgnoreCase("2")){
                            tvTime2.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("3")){
                            tvTime3.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("4")){
                            tvTime4.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("5")){
                            tvTime5.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("6")){
                            tvTime6.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("7")){
                            tvTime7.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("8")){
                            tvTime8.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("9")){
                            tvTime9.setText(lsTime);
                        }
                        else if(isWhichTimeSlected.equalsIgnoreCase("10")){
                            tvTime10.setText(lsTime);
                        }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Date has not selected.", Toast.LENGTH_LONG).show();
            }



        }else {

            switch (fromFrequency) {


                case "daily":

                    txt_time1.setText(time);
                    break;

                case "twice":

                    txt_time2.setText(time);
                    break;


                case "thrice":

                    txt_time3.setText(time);
                    break;
            }

        }


    }

    @Override
    public void selectedIndices(List<Integer> indices) {
        this.indices = indices;
        if (indices.size() > 0) {
            StringBuilder strIds = new StringBuilder();
            for (int city : indices) {
                strIds.append((city + 1));
                strIds.append(SEPARATOR);
            }


            try {
                if (indices.size() == 8) {
                    String s = new String(strIds);
                    String strnew = s.replace("1,", "");
                    days = String.valueOf(strnew.substring(0, strnew.length() - 1));
                } else {
                    days = String.valueOf(strIds.substring(0, strIds.length() - 1));
                }


//                days = String.valueOf(strIds.substring(0, strIds.length() - 1));    //remove last comma and return commaSeparatedIds
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            days = "";
        }
    }

    @Override
    public void selectedStrings(List<String> strings) {
        try {
            String strdummyDays = new String(strings.toString());

            if (strdummyDays.contains("Select All,")) {
                String strdummyDayss = strdummyDays.toString().replace("Select All,", "");

                String first = strdummyDayss.replace("[", "");
                String second = first.replace("]", "");

                txt_Days.setText(second);
            } else {

                String firsts = strdummyDays.replace("[", "");
                String seconds = firsts.replace("]", "");
                txt_Days.setText(seconds.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if (isEndDate)
        {
            lsEndDate = "";

            String dateselect=year+"-"+dayOfMonth+"-"+(month+1);
            lsEndDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
//            lsEndDate = month+1+"-"+dayOfMonth+"-"+year;

//                lsEndDate=formatDates(dateselect,true);


            //lsEndDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
//            lsEndDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

            if (!TextUtils.isEmpty(lsEndDate))
                tvEndDate.setText(formatDates(dateselect,true));
        }
        else
        {
            lsStartDate = "";
            lsStartDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
//            lsStartDate = month+1+"-"+dayOfMonth+"-"+year;
            String dateselect=year+"-"+dayOfMonth+"-"+(month+1);

//            lsStartDate=formatDates(dateselect,true);

//e
            if (!TextUtils.isEmpty(lsStartDate))
                tvStartDate.setText(formatDates(dateselect,true));
        }

        tvTime.setText("Select Time");
    }

    public String formatDates(String dateFromServer, boolean b)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat=null;
        if (b){
            dateFormat = new SimpleDateFormat("yyyy-dd-MM");

        }else {
            dateFormat = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm");

        }
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


    private void callSheduleReminder( ArrayList<String> reminderArrayList)
    {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        ScheduleReminderRequest request = new ScheduleReminderRequest();
        request.setUserID(userID);
        request.setActivity(lsActivity);
        request.setFrequency(frequency1);
        request.setStartDate(lsStartDate);
        request.setEndDate(lsEndDate);
        request.setReminderTime(reminderArrayList);
        request.setActivityType(spinner_activity_type.getSelectedItem().toString().trim());

        Log.d("reminder",new Gson().toJson(request));

        Call<ReminderScheduleResponse> call = remindersService.setReminder(request);
        call.enqueue(new Callback<ReminderScheduleResponse>()
        {
            @Override
            public void onResponse(Call<ReminderScheduleResponse> call, Response<ReminderScheduleResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ReminderScheduleResponse reminderScheduleResponse = response.body();

                    if (reminderScheduleResponse != null && reminderScheduleResponse.getCode() == 1)
                    {
                        Toast.makeText(ReememberActivity.this,
                                "" + reminderScheduleResponse.getMessage(), Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                        callToGetAllReminders();
//                        remindersAdapter.notifyDataSetChanged();

//                        Intent intent=new Intent(context, ReminderSheduleActivity.class);
//                        startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//

                       /* Intent i = new Intent();
                        i.putExtra("RESULT","ok");
                        setResult(RESULT_OK,i);*/
//                        finish();

                    }
                    else
                    {
                        Toast.makeText(ReememberActivity.this,
                                "" + reminderScheduleResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReminderScheduleResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }


    private void callSheduleReminderEdit(int id, ArrayList<String> reminderArrayList)
    {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        ReminderEditRequest request = new ReminderEditRequest();
        request.setReminderID(id);
        request.setUserID(userID);
        request.setActivity(lsActivity);
        request.setFrequency(frequency1);
        request.setStartDate(lsStartDate);
        request.setEndDate(lsEndDate);
        request.setReminderTime(reminderArrayList);
        request.setActivityType(spinner_activity_type.getSelectedItem().toString().trim());

        Log.d("reminder",new Gson().toJson(request));

        Call<ReminderEditResponse> call = remindersService.editReminder(request);
        call.enqueue(new Callback<ReminderEditResponse>()
        {
            @Override
            public void onResponse(Call<ReminderEditResponse> call, Response<ReminderEditResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ReminderEditResponse reminderEditResponse = response.body();

                    if (reminderEditResponse != null && reminderEditResponse.getCode() == 1)
                    {
                        Toast.makeText(ReememberActivity.this,
                                "" + reminderEditResponse.getMessage(), Toast.LENGTH_LONG).show();

                        dialog.dismiss();
                        callToGetAllReminders();
//                        remindersAdapter.notifyDataSetChanged();

//                        Intent intent=new Intent(context, ReminderSheduleActivity.class);
//                        startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//

                       /* Intent i = new Intent();
                        i.putExtra("RESULT","ok");
                        setResult(RESULT_OK,i);*/
//                        finish();

                    }
                    else
                    {
                        Toast.makeText(ReememberActivity.this,
                                "" + reminderEditResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReminderEditResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }



}
