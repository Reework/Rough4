package com.shamrock.reework.activity.HomeModule.activity;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.AnalyticsActivity;
import com.shamrock.reework.activity.AppoinmentModule.activity.ServiceAppointmentActivity;
import com.shamrock.reework.activity.BeforeAfterModule.activity.BeforeAfterActivity;
import com.shamrock.reework.activity.BloodTestModule.activity.AllReportActivity;
import com.shamrock.reework.activity.FaqActivity.ReeworkFAQActivity;
import com.shamrock.reework.activity.FoodModule.activity.AllFoodNewActivity;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.OnFragacess;
import com.shamrock.reework.activity.HomeModule.dialog.MindDialog;
import com.shamrock.reework.activity.HomeModule.fragment.HomeFragment;
import com.shamrock.reework.activity.HomeModule.lockumlock.ClsLockUnlockData;
import com.shamrock.reework.activity.HomeModule.lockumlock.ClsLockUnlockMain;
import com.shamrock.reework.activity.HomeModule.pojo.ClsLogoutMsg;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.HomeModule.service.MinMoodModel;
import com.shamrock.reework.activity.LoginModule.Data;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.LoginModule.UserStatus;
import com.shamrock.reework.activity.MedicineModule.activity.MyMedicinesActivity;
import com.shamrock.reework.activity.MiscellaneousModule.controller.MiscellaneousActivity;
import com.shamrock.reework.activity.MyPlansModule.activity.NewMyPlansActivity;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.REEspot.controller.ReespotActivity;
import com.shamrock.reework.activity.Reemember.controller.ReememberActivity;
import com.shamrock.reework.activity.ReminderModule.activity.RemindersActivity;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.community.CommunityActivityNew;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.activity.eshopping.EShoppingActivity;
import com.shamrock.reework.activity.eshopping.EShoppingActivityNew;
import com.shamrock.reework.activity.exoplayerview.YoutubeVideoListActivity;
import com.shamrock.reework.activity.homemenu.ExpandableListAdapter;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanActivity;
import com.shamrock.reework.activity.logout.LogoutActivity;
import com.shamrock.reework.activity.reeworkerhealth.NewDesignHealthActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.ReeevaluateHealthparamActivity;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.activity.spirituallibrary.SpiritualLibraryActivity;
import com.shamrock.reework.activity.uploadFile.FileUploadActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.FcmRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.FcmResponse;
import com.shamrock.reework.api.response.GetRecoachByUserResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class HomeActivity extends AppCompatActivity implements

        HomeFragment.OnHomeFragmentListener,
        MindDialog.GetMindMoodDialogListener {

    private static final String TAG_FRAGMENT_HOME = "home_fragment";
    Context context;
    DrawerLayout mDrawer;
    Toolbar toolbar;
    ListView lst_hamburger_menu;
    ImageView imgDrawerUserPhoto, imgToolbar_UserPhoto;
    //CircleImageViewHoffman imgToolbar_UserPhoto;
    TextView textDrawerUserName, textToolbar_FragmentName;
    SessionManager sessionManager;
    NotificationService notificationService;
    Utils utils;
    public static final int REQ_CODE = 500;
    UnfreezeService unfreezeService;
    LoginService loginService;
    Fragment fragments;

    private Fragment currentFragment;
    private String userPhoto;
    NavigationView navigationView;
    TextView tvNotificationCOunt;

    int planId, userId, mNotifcationCount = 0;
    String mFcmToken = "", mMobileNo, mSubscriptionList = "";
    String token;

    OnFragacess onFragacess;

    ArrayList<String> subscription_List;
    private BroadcastReceiver mBroadcastReceiver;
    public static ArrayList<ClsLockUnlockData> arylst_lock_unlockdata;


    HomeFragmentService service;
    private HomeFragmentResponse.Data mHomeModel = null;
    private SessionManager session;
TextView text_UserDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = HomeActivity.this;
        sessionManager = new SessionManager(context);
        service = Client.getClient().create(HomeFragmentService.class);
        session = new SessionManager(context);
//        Crashlytics.getInstance().crash();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_hamburger_menu);
        toolbar.setBackground(getResources().getDrawable(R.drawable.bkgr_splash));
        setSupportActionBar(toolbar);
        unfreezeService = Client.getClient().create(UnfreezeService.class);
//        token = sessionManager.g etStringValue(SessionManager.KEY_USER_TOKEN);

        notificationService = Client.getClient().create(NotificationService.class);

        utils = new Utils();
        loginService = Client.getClient().create(LoginService.class);
        text_UserDate = findViewById(R.id.text_UserDate);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        mFcmToken = sessionManager.getStringValue(SessionManager.KEY_FCM_DEVICE_ID);
        CallLogoutApi();
        CallToFetchRecoachId();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(HomeActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.i("FCM Token", token);
                mFcmToken = token;
                PushFcmToServer();

            }
        });

        Log.e("fcmtoken", mFcmToken.toString());

        mMobileNo = sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO);
        mSubscriptionList = sessionManager.getStringValue(SessionManager.KEY_USER_SUBSCRIPTION_LIST);
        subscription_List = new ArrayList<>();

        Log.e("subscription_List", mSubscriptionList.toString());
        try {
            if (mSubscriptionList != null) {
                JSONArray obj = new JSONArray(mSubscriptionList);
                subscription_List = new ArrayList<>();
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject object = obj.getJSONObject(i);
                    String name = object.getString("FeatureName");
                    subscription_List.add(name);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = findViewById(R.id.drawer_layout);
        imgToolbar_UserPhoto = findViewById(R.id.toolbarHome_UserImage);
        textToolbar_FragmentName = findViewById(R.id.toolbarHome_Title);

//        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        View headerView = navigationView.getHeaderView(0);
        imgDrawerUserPhoto = findViewById(R.id.imageView_UserProfile_Photo);
        textDrawerUserName = findViewById(R.id.text_UserProfile_Name);

        imgDrawerUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.isNetworkAvailable(HomeActivity.this)) {
                    startActivityForResult(new Intent(context, MyProfileActivity.class), 100);

                } else {
                    Toast.makeText(HomeActivity.this, "No internet !", Toast.LENGTH_LONG).show();

                }
            }
        });

        // SET PROFILE DATA
        String userName = sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME) + " " +
                sessionManager.getStringValue(SessionManager.KEY_USER_L_NAME);

        //USER SUBSCRIPTION STATUS
        planId = sessionManager.getIntValue(SessionManager.KEY_USER_PLAN_ID);


        textDrawerUserName.setText(userName);

        callLockUnlockApi();

        userPhoto = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);

        if (isValidContextForGlide(context)) {
            Glide.with(context)
                    .load(userPhoto)
                    .apply(
                            RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.ic_profile_pic_bg)
                                    .error(R.drawable.ic_profile_pic_bg)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    )
                    .into(imgDrawerUserPhoto);

            Glide.with(context)
                    .load(userPhoto)
                    .apply(
                            RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.ic_profile_pic_bg)
                                    .error(R.drawable.ic_profile_pic_bg)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    )
                    .into(imgToolbar_UserPhoto);
        }


        // load 1st fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragments = HomeFragment.newInstance("str11", "str22", HomeActivity.this);
        ft.add(R.id.fragmentContainer_Home, fragments, TAG_FRAGMENT_HOME).commit();

        toolbar.setTitle("REEwork");
//        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        imgToolbar_UserPhoto.setVisibility(View.GONE);
        textToolbar_FragmentName.setVisibility(View.VISIBLE);
        imgToolbar_UserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(HomeActivity.this)) {
                    startActivityForResult(new Intent(context, MyProfileActivity.class), 100);

                } else {
                    Toast.makeText(HomeActivity.this, "No internet !", Toast.LENGTH_LONG).show();

                }
            }
        });

        if (planId == 1)  // This is PAID USER
        {
            showPaidItem();
        } else  // This is FREE USER
        {
            hidePaidItem();
        }


        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
                        PushFcmToServer();
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


        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {


        }


        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
    }

    public void addExpandalbe() {
        final List<String> listDataHeader = new ArrayList<>();
        final HashMap<String, List<String>> listDataChild = new HashMap<>();


        listDataHeader.add("Profile");
        listDataHeader.add("Daily Diary");

        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

        } else {
            listDataHeader.add("REEplan");

        }


        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            if (sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true")) {
                listDataHeader.add("REEcoach");

            }
        }
        listDataHeader.add("REEchat");
        listDataHeader.add("My Analysis");
        listDataHeader.add("Me-Before & After");
        listDataHeader.add("REEcipes");
        // listDataHeader.add("Create Recipe");
//        listDataHeader.add("Wellness Library");

        listDataHeader.add("My Lifestyle");
        listDataHeader.add("REEmember");

//        listDataHeader.add("Spiritual Library");
//        listDataHeader.add("Membership Plan");
        listDataHeader.add("More Services");
        listDataHeader.add("Help & Support");
        listDataHeader.add("Logout");

        List<String> heading2 = new ArrayList<>();
        final List<String> heading3 = new ArrayList<>();
        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

        } else {


        }
        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
            if (sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true")) {
//                heading3.add("REEcoach");

            }
//            if (sessionManager.getStringValue("KEY_ISSHOW_PATHO").equalsIgnoreCase("true")) {
//                heading3.add("Pathologist");
//
//            }

        }
        if (sessionManager.getStringValue("KEY_ISSHOW_REECOACH").equalsIgnoreCase("true") || sessionManager.getStringValue("KEY_ISSHOW_PATHO").equalsIgnoreCase("true")) {
//            heading3.add("Appointments");

        }
//        heading3.add("REEmember");

//        heading3.add("Reminders");

        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//            heading3.add("REEports");
//            heading3.add("Medicines");


        }
        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//            heading3.add("Health Profile");


        }

//        heading3.add("My Lifestyle");
//        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
//        } else {
//            heading3.add("Schedule Pathology Test");
//        }
//        heading3.add("Me-Before & After");
//        heading3.add("Testimonials");
//        heading3.add("E-Shopping");
//        heading3.add("External Devices");
        // heading3.add("FAQ");
//        heading3.add("REEchat");
//        heading3.add("Analytics");
        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {

        } else {
//            heading3.add("REEplace Items");
//            heading3.add("My Diet Plan");
//            heading3.add("Payment History");
//            heading3.add("REEcoach Questionare");
            heading3.add("REEspot");
//            heading3.add("Wellness Library");
            heading3.add("Miscellaneous");
//            heading3.add("REEvaluate1");

        }

//        heading3.add("Video");
//        heading3.add("Services");
//        heading3.add("Upload");


        for (int i = 0; i < listDataHeader.size(); i++) {
            if (listDataHeader.get(i).equalsIgnoreCase("More Services")) {
                listDataChild.put(listDataHeader.get(i), heading3);// Header, Child data

                break;
            }

        }


        final ExpandableListView expandable_list = findViewById(R.id.expandable_list);


        final ExpandableListAdapter mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandable_list, arylst_lock_unlockdata);
        // setting list adapter
        expandable_list.setAdapter(mMenuAdapter);

        expandable_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (!Utils.isNetworkAvailable(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, "No internet !", Toast.LENGTH_SHORT).show();
                    return false;

                }


                String tag = null;

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = null;
                Intent intent = null;


//                if (heading3.get(childPosition).equalsIgnoreCase("REEplace Items")) {
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//                        if (!callLockFunction("CHEATPLAN")) {
//                            startActivity(new Intent(HomeActivity.this, MyCheatPlanActivity.class));
//                        } else { callProcedureText("CHEATPLAN");
//                        }
//                    }
//
//                }

                if (heading3.get(childPosition).equalsIgnoreCase("My Diet Plan")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (!callLockFunction("DIETPLAN")) {

                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                            } else {
                                intent = new Intent(context, DietPlanActivity.class);
                                intent.putExtra("param", "");
                            }
                        } else {

                            callProcedureText("DIETPLAN");

                        }
                    }


                } else {
                }

//                if (heading3.get(childPosition).equalsIgnoreCase("Payment History")) {
//                    intent = new Intent(context, paymentHistoryActivity.class);
//
//                }
//                if (heading3.get(childPosition).equalsIgnoreCase("REEcoach Questionare")) {
//                    intent = new Intent(context, ReecoachQuestionActivity.class);
//
//                }


                if (heading3.get(childPosition).equalsIgnoreCase("Miscellaneous")) {
                    intent = new Intent(context, MiscellaneousActivity.class);

                }
                if (heading3.get(childPosition).equalsIgnoreCase("REEvaluate1")) {
                    intent = new Intent(context, ReeevaluateHealthparamActivity.class);

                }

//                if (heading3.get(childPosition).equalsIgnoreCase("REEmember")) {
//                    intent = new Intent(context, ReememberActivity.class);
//
//                }

                if (heading3.get(childPosition).equalsIgnoreCase("REEspot")) {
                    intent = new Intent(context, ReespotActivity.class);

                }

//                if (heading3.get(childPosition).equalsIgnoreCase("Schedule Pathology Test")) {
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }
//                    else {
////                        if (getLockUnlockStatus("PATHOTEST")) {
////                            Toast.makeText(context, "Menu is lock", Toast.LENGTH_SHORT).show();
//
////                        }
//
//                        if (false) {
//                            Toast.makeText(context, "Menu is lock", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            if (containsName(subscription_List, "Schedule Blood Test")) {
//
//                                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                                    intent = new Intent(context, UnfreezeActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                } else if (sessionManager.getStringValue("KeyBloodTestStatus").equalsIgnoreCase("3")) {
//                                    Toast.makeText(context, "You are already schedule a request. You can't schedule new request meanwhile.", Toast.LENGTH_SHORT).show();
//
//                                } else {
//
//
//                                    intent = new Intent(context, ScdeduleBloodTestHistoryActivity.class);
//                                    intent.putExtra("param", "From_Home");
//                                }
//
//                            } else {
//                            }
//                        }
//                    }
//
//
//
//
//
//                }


//                if (listDataHeader.get(childPosition).toString().equalsIgnoreCase("Reecoach")) {
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//
//
//                    }else {
//                        if (!callLockFunction("REECOACH")) {
//                            if (containsName(subscription_List, "My Reecoach")) {
//
//
//                                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                                    intent = new Intent(context, UnfreezeActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                                    Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();
//
//                                }
////                        else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
////                            Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
////
////                        }
//                                else {
//                                    intent = new Intent(context, MyReecoachProfileActivity.class);
//                                    intent.putExtra("param", "");
//                                }
//
//
//                            } else {
//                            }
//
//                        } else {
//
//                            callProcedureText("REECOACH");
//
//                        }
//                    }
//
//
//
//
//
//
//
//                }


//                if (heading3.get(childPosition).equalsIgnoreCase("Pathologist")) {
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                            intent = new Intent(context, UnfreezeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        } else {
//                            intent = new Intent(context, PathologistMainActivity.class);
//                            intent.putExtra("param", "From_Home");
//                        }
//                    }
//
//                }


//                if (heading3.get(childPosition).equalsIgnoreCase("Analytics")) {
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                            intent = new Intent(context, UnfreezeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        } else {
//                            intent = new Intent(context, MyAnalysisActivity.class);
//                            intent.putExtra("param", "From_Home");
//                        }
//                    }
//
//
//
//
//
//
//
//
//                }


                if (heading3.get(childPosition).equalsIgnoreCase("Health Profile")) {

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        } else {
//                    intent = new Intent(context, NewHealthparamterActivity.class);
                            intent = new Intent(context, DynamicHealthparamActivity.class);
                            intent.putExtra("param", "From_Home");

                        }
                    }


                }
                if (heading3.get(childPosition).equalsIgnoreCase("Appointments")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                            intent = new Intent(context, ServiceAppointmentActivity.class);
                            intent.putExtra("param", "");

                        } else {
                            if (containsName(subscription_List, "My Appointments")) {


                                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                    intent = new Intent(context, UnfreezeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                    Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                                }

//                            else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                                Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                            }


                                else {
                                    intent = new Intent(context, ServiceAppointmentActivity.class);
                                    intent.putExtra("param", "");
                                }


                            } else {
//                            Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                }

                if (heading3.get(childPosition).equalsIgnoreCase("Reminders")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {

                        intent = new Intent(context, RemindersActivity.class);
                        intent.putExtra("param", "");
                        if (!callLockFunction("REMINDER")) {
                            if (sessionManager.getStringValue("Trial").equalsIgnoreCase("True")) {
                                intent = new Intent(context, RemindersActivity.class);
                                intent.putExtra("param", "");

                            } else {
                                if (containsName(subscription_List, "My Reminders")) {


                                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                        intent = new Intent(context, UnfreezeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                        Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                                    }
//                            else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                                Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                            }
//                            else if (sessionManager.getStringValue("KeyAssingDailyTassk").equalsIgnoreCase("false")) {
//
//                                String msg = sessionManager.getStringValue("KeynotAssingReacoachMsg");
//                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                            }
                                    else {
                                        intent = new Intent(context, RemindersActivity.class);
                                        intent.putExtra("param", "");
                                    }

                                } else {
//                            Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {

                            callProcedureText("REMINDER");

                        }
                    }


                }
                if (heading3.get(childPosition).equalsIgnoreCase("Reeports")) {


                    if (!callLockFunction("REPORTSTORAGE")) {
                        startActivity(new Intent(context, AllReportActivity.class));

                    } else {

                        callProcedureText("REPORTSTORAGE");

                    }


                }

                if (heading3.get(childPosition).equalsIgnoreCase("Medicines")) {

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (containsName(subscription_List, "My Medicines")) {

                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                            } else {
                                intent = new Intent(context, MyMedicinesActivity.class);
                                intent.putExtra("param", "From_Home");
                            }


                        } else {
                        }
                    }

                }

//                if (heading3.get(childPosition).equalsIgnoreCase("My Lifestyle")) {
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//                        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("True")) {
//                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                                intent = new Intent(context, UnfreezeActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            } else {
//                                Intent intent1 = new Intent(context, LifeStylePlanActivity.class);
//                                intent1.putExtra("isFromlife", true);
//                                startActivity(intent1);
//
//                            }
//                        } else {
//                            Intent intent1 = new Intent(context, LifeStylePlanActivity.class);
//                            intent1.putExtra("isFromlife", true);
//                            startActivity(intent1);
//
//                        }
//                    }
//
//
//
//
//
//                }

                if (heading3.get(childPosition).equalsIgnoreCase("E-Shopping")) {
                    if (true) {
//                        Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(context, EShoppingActivityNew.class);
//                        intent1.putExtra("isFromlife", true);
                        startActivity(intent1);
                        return false;
                    }

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (!callLockFunction("ESHOPPING")) {
                            Intent intent1 = new Intent(context, EShoppingActivity.class);
                            intent1.putExtra("isFromlife", true);
                            startActivity(intent1);
                        } else {

                            callProcedureText("ESHOPPING");

                        }

                    }

                }

                if (heading3.get(childPosition).equalsIgnoreCase("video")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        Intent intent1 = new Intent(context, YoutubeVideoListActivity.class);
                        intent1.putExtra("isFromlife", true);
                        startActivity(intent1);

                    }


                }

                if (heading3.get(childPosition).equalsIgnoreCase("upload")) {

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        Intent intent1 = new Intent(context, FileUploadActivity.class);
                        intent1.putExtra("isFromlife", true);
                        startActivity(intent1);

                    }

                }


                if (heading3.get(childPosition).equalsIgnoreCase("Services")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        Intent intent1 = new Intent(context, MyServiceActivity.class);
                        intent1.putExtra("isFromlife", true);
                        startActivity(intent1);
                    }
                }
//                7058705931

//                if (heading3.get(childPosition).equalsIgnoreCase("Testimonials")) {
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//                        Intent intent1 = new Intent(context, TestimalActivity.class);
//                        intent1.putExtra("isFromlife", true);
//                        startActivity(intent1);
//                    }
//                }
//                if (listDataHeader.get(childPosition).equalsIgnoreCase("Me-Before & After")) {
//
//                    if (containsName(subscription_List, "Before and After")) {
//
//                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                            intent = new Intent(context, UnfreezeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                        } else {
//                            intent = new Intent(context, BeforeAfterActivity.class);
//                            intent.putExtra("param", "");
//                        }
//
//
//                    } else {
////                        Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                if (heading3.get(childPosition).equalsIgnoreCase("External Devices")) {
//                }

//                if (heading3.get(childPosition).equalsIgnoreCase("REEchat")) {
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//
//
//                        if (!callLockFunction("REEchat")) {
//                            startActivity(new Intent(HomeActivity.this, CommunityActivityNew.class));
//
//
//                        } else {
//
//                            callProcedureText("REEchat");
//
//                        }
//                    }
//
//                }


                if (heading3.get(childPosition).equalsIgnoreCase("Wellness Library")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {


                        if (!callLockFunction("WELLNESSLIBRARY")) {
                            startActivity(new Intent(context, SpiritualLibraryActivity.class));

                        } else {

                            callProcedureText("WELLNESSLIBRARY");

                        }
                    }


                }




              /*  if (heading3.get(childPosition).equalsIgnoreCase("FAQ")) {

                    startActivity(new Intent(HomeActivity.this, ReeworkFAQActivity.class));
                }*/

                if (fragment != null) {
                    fragmentTransaction.add(R.id.fragmentContainer_Home, fragment, tag).commit();
                    currentFragment = fragment;
                    // fragment loaded in Current Activity
                    mDrawer.closeDrawer(GravityCompat.START);

                    // below only for Home fragment
                    if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_HOME)) {
                        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueGreen5));
                        imgToolbar_UserPhoto.setVisibility(View.VISIBLE);
                        textToolbar_FragmentName.setVisibility(View.GONE);
                    } else {
                        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPremiumWhite));
                        imgToolbar_UserPhoto.setVisibility(View.GONE);
                        textToolbar_FragmentName.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (intent != null) {
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
                return false;
            }
        });

        expandable_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {


                if (!Utils.isNetworkAvailable(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, "No internet !", Toast.LENGTH_SHORT).show();
                    return false;
                }


                if (groupPosition == 10) {
                    if (parent.isGroupExpanded(groupPosition)) {
                        mMenuAdapter.updateIcon(true);
                        // Do your Staff
                    } else {
                        mMenuAdapter.updateIcon(false);
                        // Expanded ,Do your Staff
                    }
                }


//


//


                String tag = null;

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = null;
                Intent intent = null;


//                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Wellness Library")) {
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//
//
//                        if (!callLockFunction("WELLNESSLIBRARY")) {
//                            startActivity(new Intent(context, SpiritualLibraryActivity.class));
//
//                        } else {
//
//                            callProcedureText("WELLNESSLIBRARY");
//
//                        }
//                    }
//
//
//                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Me-Before & After")) {

                    if (containsName(subscription_List, "Before and After")) {

                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        } else {
                            intent = new Intent(context, BeforeAfterActivity.class);
                            intent.putExtra("param", "");
                        }


                    } else {
//                        Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                    }

                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Profile")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {


                        startActivityForResult(new Intent(context, MyProfileActivity.class), 100);
                    }


                }


                if (listDataHeader.get(groupPosition).toString().equalsIgnoreCase("Reecoach")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();


                    } else {
                        if (!callLockFunction("REECOACH")) {
                            if (containsName(subscription_List, "My Reecoach")) {


                                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                    intent = new Intent(context, UnfreezeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                    Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                                }
//                        else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                            Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                        }
                                else {
                                    intent = new Intent(context, MyReecoachProfileActivity.class);
                                    intent.putExtra("param", "");
                                }


                            } else {
                            }

                        } else {

                            callProcedureText("REECOACH");

                        }
                    }


                }


//                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Schedule Pathology Test")) {
//
//
////            intent = new Intent(context, ScheduleBloodTestActivity.class);
////            intent.putExtra("param", "From_Home");
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//
//                        if (containsName(subscription_List, "Schedule Blood Test")) {
//
//                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                                intent = new Intent(context, UnfreezeActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                            } else if (sessionManager.getStringValue("KeyBloodTestStatus").equalsIgnoreCase("3")) {
//                                Toast.makeText(context, "You are already schedule a request. You can't schedule new request meanwhile.", Toast.LENGTH_SHORT).show();
//
//                            } else {
//                                intent = new Intent(context, ScdeduleBloodTestHistoryActivity.class);
//                                intent.putExtra("param", "From_Home");
//                            }
//
//                        } else {
//                        }
//                    }
//
//                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Daily Diary")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {

                        fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME);

                        if (fragment != null) {
                            fragment = null;
                            Log.v(TAG_FRAGMENT_HOME, "Already Added");
                        } else {
                            fragment = HomeFragment.newInstance("Camera", "100", HomeActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putString("arg1", "argValue");
                            bundle.putString("arg2", "argValue22");
                            fragment.setArguments(bundle);
                        }
                        tag = TAG_FRAGMENT_HOME;
                        mDrawer.closeDrawer(GravityCompat.START);
                    }
                }
                if (listDataHeader.get(groupPosition).equalsIgnoreCase("My Analysis")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (containsName(subscription_List, "My Analysis")) {


                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            } else {
                                intent = new Intent(context, AnalyticsActivity.class);

                            }

                        } else {
//                        Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (listDataHeader.get(groupPosition).equalsIgnoreCase("My Diet Plan")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {


                        if (!callLockFunction("DIETPLAN")) {

                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            } else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {

                                Toast.makeText(context, "Please schedule your Pathology Test", Toast.LENGTH_SHORT).show();

                            } else {
                                intent = new Intent(context, DietPlanActivity.class);
                                intent.putExtra("param", "");
                            }
                        } else {

                            callProcedureText("DIETPLAN");

                        }
                    }


                } else {
                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("REEplan")) {

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();

                    } else {
                        if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                            intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } else {
                            intent = new Intent(context, NewMyPlansActivity.class);
                            intent.putExtra("param", "");
                        }
                    }


                }

//                if (listDataHeader.get(groupPosition).equalsIgnoreCase("REEplace Items")) {
//
//                    if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                        shownoplan();
//                    }else {
//
//                        if (!callLockFunction("CHEATPLAN")) {
//                            startActivity(new Intent(HomeActivity.this, MyCheatPlanActivity.class));
//
//                        } else {
//
//                            callProcedureText("CHEATPLAN");
//
//                        }
//                    }
//
//
//                }

                if (listDataHeader.get(groupPosition).equalsIgnoreCase("REEcipes")) {

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {


                        if (!callLockFunction("RECIPELIBNORM")) {
                            startActivity(new Intent(context, AllFoodNewActivity.class));

                        } else {

                            callProcedureText("RECIPELIBNORM");

                        }
                    }

                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("REEchat")) {
                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {


                        if (!callLockFunction("REEchat")) {
                            startActivity(new Intent(HomeActivity.this, CommunityActivityNew.class));


                        } else {

                            callProcedureText("REEchat");

                        }
                    }

                }

                if (listDataHeader.get(groupPosition).equalsIgnoreCase("REEmember")) {
                    intent = new Intent(context, ReememberActivity.class);

                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("My Lifestyle")) {

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("True")) {
                            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                                intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Intent intent1 = new Intent(context, LifeStylePlanActivity.class);
                                intent1.putExtra("isFromlife", true);
                                startActivity(intent1);

                            }
                        } else {
                            Intent intent1 = new Intent(context, LifeStylePlanActivity.class);
                            intent1.putExtra("isFromlife", true);
                            startActivity(intent1);

                        }
                    }


                }








              /*  if (listDataHeader.get(groupPosition).equalsIgnoreCase("Create Recipe")) {


                    startActivity(new Intent(context, CreateRecipeActivity.class));


                }
*/

                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Membership Plan")) {

                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
                        intent = new Intent(context, UnfreezeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } else {

                        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")) {
                            intent = new Intent(context, ViewPagerActivity.class);
                            intent.putExtra("param", "From_Home");
                        } else {
                            intent = new Intent(context, MyServiceActivity.class);
                            intent.putExtra("param", "From_Home");
                        }


                    }
                }

                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Logout")) {


                    final Dialog dialog = new Dialog(HomeActivity.this, R.style.CustomDialog);
                    dialog.setContentView(R.layout.dailg_logout_alert);
                    Button btn_cacel = dialog.findViewById(R.id.btn_cacel);
                    btn_cacel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Button btn_logout = dialog.findViewById(R.id.btn_logout);
                    btn_logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sessionManager.clearSession();
                            startActivity(new Intent(HomeActivity.this, LogoutActivity.class));
                            showFullScrenenStayHeladyDailog();
                            finish();
                        }
                    });
                    dialog.show();


                }


                if (listDataHeader.get(groupPosition).equalsIgnoreCase("Help & Support")) {
                    // startActivity(new Intent(HomeActivity.this, HealthAndSupportActivity.class));
                    startActivity(new Intent(HomeActivity.this, ReeworkFAQActivity.class));
                }

                if (fragment != null) {
                    fragmentTransaction.add(R.id.fragmentContainer_Home, fragment, tag).commit();
                    currentFragment = fragment;
                    // fragment loaded in Current Activity
                    mDrawer.closeDrawer(GravityCompat.START);

                    // below only for Home fragment
                    if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_HOME)) {
                        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueGreen5));
                        imgToolbar_UserPhoto.setVisibility(View.VISIBLE);
                        textToolbar_FragmentName.setVisibility(View.VISIBLE);
                    } else {
                        toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPremiumWhite));
                        imgToolbar_UserPhoto.setVisibility(View.GONE);
                        textToolbar_FragmentName.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (intent != null) {
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }

                return false;
            }
        });
    }

    private void callProcedureText(String wellnesslibrary) {
        if (arylst_lock_unlockdata != null) {
            Dialog dialog = new Dialog(context, R.style.CustomDialog);
            dialog.setContentView(R.layout.lay_procudure_text);
            TextView txt_procedure_text = dialog.findViewById(R.id.txt_procedure_text);
            TextView txt_pro_header = dialog.findViewById(R.id.txt_pro_header);

            for (int i = 0; i < arylst_lock_unlockdata.size(); i++) {
                if (wellnesslibrary.equalsIgnoreCase(arylst_lock_unlockdata.get(i).getStaticName())) {
                    if (arylst_lock_unlockdata.get(i).getProcedureText() != null) {
                        txt_procedure_text.setText(arylst_lock_unlockdata.get(0).getProcedureText());
                        txt_pro_header.setText(arylst_lock_unlockdata.get(0).getServiceName() + " unlock procedure");
                        dialog.show();

                    }
                    break;
                }


            }
        }


    }


    private static boolean containsName(final List<String> transaction, final String search) {


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RESUME--->", "RESUME CALLED");

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            // To close Drawer immediately set animation = false
            mDrawer.closeDrawer(GravityCompat.START, false);
        }

        if (TextUtils.isEmpty(mFcmToken))
            PushFcmToServer();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(FcmConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(FcmConstants.PUSH_NOTIFICATION));
    }

    private void PushFcmToServer() {
        FcmRequest request = new FcmRequest();
        request.setMobileNo(mMobileNo);
        request.setFcmDeviceToken(mFcmToken);
        request.setDeviceTypeID(0);

        Call<FcmResponse> call = notificationService.PushFcmToServer(request);

        call.enqueue(new Callback<FcmResponse>() {
            @Override
            public void onResponse(Call<FcmResponse> call, Response<FcmResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    FcmResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Log.d("FCM REGISTER--->", "FCM REGISTER SUCCESSFULLY");

                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<FcmResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }


    private void CallLogoutApi() {


        Call<ClsLogoutMsg> call = notificationService.getLogoutMessage(userId);

        call.enqueue(new Callback<ClsLogoutMsg>() {
            @Override
            public void onResponse(Call<ClsLogoutMsg> call, Response<ClsLogoutMsg> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsLogoutMsg listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        Log.d("FCM REGISTER--->", "FCM REGISTER SUCCESSFULLY");
                        try {
                            if (listResponse.getData().getMessage() != null) {
                                sessionManager.setStringValue("Logoutmsg", listResponse.getData().getMessage());

                            }
                        } catch (Exception e) {
                            sessionManager.setStringValue("Logoutmsg", "");

                        }


                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<ClsLogoutMsg> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);

        Log.d("MENU--->", "MENU CALLED");

        MenuItem menuItem = menu.findItem(R.id.bellIcon);
        menuItem.setIcon(buildCounterDrawable());
        return true;
    }

    private Drawable buildCounterDrawable() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.menu_notif_count, null);

        tvNotificationCOunt = (TextView) view.findViewById(R.id.count);

        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //handle the home_menu button onClick event here.
                mDrawer.openDrawer(GravityCompat.START);
                return true;

            //BELL ICON CLICK
            case R.id.bellIcon:

                startActivityForResult(new Intent(this, NotificationsActivity.class), REQ_CODE);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();

//        String tag = null;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment fragment = null;
    Intent intent = null;

//        if (id == R.id.nav_home) {
//            fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME);
//
//            if (fragment != null) {
//                fragment = null;
//                Log.v(TAG_FRAGMENT_HOME, "Already Added");
//            } else {
//                fragment = HomeFragment.newInstance("Camera", "100");
//                Bundle bundle = new Bundle();
//                bundle.putString("arg1", "argValue");
//                bundle.putString("arg2", "argValue22");
//                fragment.setArguments(bundle);
//            }
//            tag = TAG_FRAGMENT_HOME;
//            mDrawer.closeDrawer(GravityCompat.START);
//
//        }
//        else if (id==R.id.nav_profile){
//
//            startActivityForResult(new Intent(context, MyProfileActivity.class), 100);
//
//
//        }

//        else if (id==R.id.nav_services){
//            Menu nav_menu=navigationView.getMenu();
//
//            if (nav_menu.findItem(R.id.nav_medication).isVisible()){
//                nav_menu.findItem(R.id.nav_medication).setVisible(false)  ;
//            }
//            if (!nav_menu.findItem(R.id.nav_medication).isVisible()){
//                nav_menu.findItem(R.id.nav_medication).setVisible(true)  ;
//            }
//
////            startActivityForResult(new Intent(context, MyProfileActivity.class), 100);
//
//
//        }

//        else if (id==R.id.nav_my_life){
//
//            Intent intent1=new Intent(context, LifeStylePlanActivity.class);
//            intent1.putExtra("isFromlife",true);
//            startActivity(intent1);
//
//
//
//        }


//        else if (id==R.id.nav_food){
//
//
//            startActivity(new Intent(context, AllFoodActivity.class));
////            startActivity(new Intent(context, ViewPagerActivity.class));
//
//
//
//        }
//        else if (id==R.id.nav_my_report){


//            startActivity(new Intent(context, BloodReportActivity.class));


//        }


//        else if (id == R.id.nav_weight) {
//            if (containsName(subscription_List, "Weight Tracker")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }else {
//                    intent = new Intent(context, WeightActivity.class);
//                    intent.putExtra("param", "From_Home");
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_my_health) {
//            if (containsName(subscription_List, "My Health")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else {
////                    intent = new Intent(context, NewHealthparamterActivity.class);
//                    intent = new Intent(context, DynamicHealthparamActivity.class);
//                    intent.putExtra("param", "From_Home");
//
//                }
//
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_medication) {
//            if (containsName(subscription_List, "My Medicines")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                    Toast.makeText(context, "Please schedule your blood test", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                    Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingDailyTassk").equalsIgnoreCase("false")) {
//
//                    String msg = sessionManager.getStringValue("KeynotAssingReacoachMsg");
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                } else {
//                    intent = new Intent(context, MyMedicinesActivity.class);
//                    intent.putExtra("param", "From_Home");
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_my_reecoach) {
//
//            if (containsName(subscription_List, "My Reecoach")) {
//
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                    Toast.makeText(context, "Please schedule your blood test", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                    Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    intent = new Intent(context, MyReecoachProfileActivity.class);
//                    intent.putExtra("param", "");
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_schedule_blood_test) {
//
//
////            intent = new Intent(context, ScheduleBloodTestActivity.class);
////            intent.putExtra("param", "From_Home");
//
//
//            if (containsName(subscription_List, "Schedule Blood Test")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else
//
//                if (sessionManager.getStringValue("KeyBloodTestStatus").equalsIgnoreCase("3")) {
//                    Toast.makeText(context, "You are already schedule a request. You can't schedule new request meanwhile.", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    intent = new Intent(context, ScdeduleBloodTestHistoryActivity.class);
//                    intent.putExtra("param", "From_Home");
//                }
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_blood_report) {
//            if (containsName(subscription_List, "Blood Report")) {
//
//
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                    Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingDailyTassk").equalsIgnoreCase("false")) {
//
//                    String msg = sessionManager.getStringValue("KeynotAssingReacoachMsg");
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                } else {
//
//                    intent = new Intent(context, BloodReportActivity.class);
//                    intent.putExtra("param", "");
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_my_appointment) {
//        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
//            intent = new Intent(context, AppointmentsActivity.class);
//            intent.putExtra("param", "");
//
//        }else {
//            if (containsName(subscription_List, "My Appointments")) {
//
//
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                    Toast.makeText(context, "Please schedule your blood test", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                    Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//                else {
//                    intent = new Intent(context, AppointmentsActivity.class);
//                    intent.putExtra("param", "");
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        } else if (id == R.id.nav_you_before_after) {
//            if (containsName(subscription_List, "Before and After")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")){
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                }else{
//                    intent = new Intent(context, BeforeAfterActivity.class);
//                    intent.putExtra("param", "");
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id==R.id.nav_my_cheat_plans){
//
//
//            startActivity(new Intent(context, MyCheatPlanActivity.class));
//
//        }
//
//
//
//
//        else if (id == R.id.nav_subscription) {
//            if (containsName(subscription_List, "Before and After")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }else {
//                    intent = new Intent(context, ViewPagerActivity.class);
//                    intent.putExtra("param", "From_Home");
//                }
//
//
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_my_plans) {
//            if (containsName(subscription_List, "My Plans")) {
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }else
//                    if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                    Toast.makeText(context, "Please schedule your blood test", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                    Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingDailyTassk").equalsIgnoreCase("false")) {
//
//                    String msg = sessionManager.getStringValue("KeynotAssingReacoachMsg");
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                } else {
//                    intent = new Intent(context, MyPlansActivity.class);
//                    intent.putExtra("param", "");
//                }
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To SubsCribed Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_upgrade) {
//            // if(containsName(subscription_List,"My")) {
//
//
//            if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                intent = new Intent(context, UnfreezeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }else{
//                intent = new Intent(context, UpgradeActivity.class);
//                intent.putExtra("param", "From_Home");
//            }



           /* }else {
                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
            }*/
//        } else if (id == R.id.nav_my_reminders) {
//
//            if (sessionManager.getStringValue("Trial").equalsIgnoreCase("True")){
//                intent = new Intent(context, RemindersActivity.class);
//                intent.putExtra("param", "");
//
//            }else {
//                if (containsName(subscription_List, "My Reminders")) {
//
//
//
//                    if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                        intent = new Intent(context, UnfreezeActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    }else
//
//                    if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                        Toast.makeText(context, "Please schedule your blood test", Toast.LENGTH_SHORT).show();
//
//                    } else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                        Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                    } else if (sessionManager.getStringValue("KeyAssingDailyTassk").equalsIgnoreCase("false")) {
//
//                        String msg = sessionManager.getStringValue("KeynotAssingReacoachMsg");
//                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                    } else {
//                        intent = new Intent(context, RemindersActivity.class);
//                        intent.putExtra("param", "");
//                    }
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//
//        } else if (id == R.id.nav_chat_with_reecoach) {
//            if (containsName(subscription_List, "Chat with Reecoach ")) {
//
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }else
//                if (!sessionManager.getStringValue("IsAllowUser").equalsIgnoreCase("true")) {
//
//                    Toast.makeText(context, "Please schedule your blood test", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingReecoach").equalsIgnoreCase("false")) {
//                    Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                } else if (sessionManager.getStringValue("KeyAssingDailyTassk").equalsIgnoreCase("false")) {
//
//                    String msg = sessionManager.getStringValue("KeynotAssingReacoachMsg");
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                } else {
//                    intent = new Intent(context, SingleChatActivity.class);
//
//                }
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To SubsCribed Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_my_analysis) {
//            if (containsName(subscription_List, "My Analysis")) {
//
//
//                if (sessionManager.getStringValue("KeyIsFreezed").equalsIgnoreCase("true")) {
//                    intent = new Intent(context, UnfreezeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }else{
//                    intent = new Intent(context, MyAnalysisActivity.class);
//
//                }
//
//            } else {
//                Toast.makeText(getApplicationContext(), "You Have To Subscribe Paid Plan", Toast.LENGTH_SHORT).show();
//            }
//        } else if (id == R.id.nav_logout) {
////            finish();
//
//
//            showFullScrenenStayHeladyDailog();
//
//
//        }
//
//        if (fragment != null) {
//            fragmentTransaction.add(R.id.fragmentContainer_Home, fragment, tag).commit();
//            currentFragment = fragment;
//            // fragment loaded in Current Activity
//            mDrawer.closeDrawer(GravityCompat.START);
//
//            // below only for Home fragment
//            if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_HOME)) {
//                toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueGreen5));
//                imgToolbar_UserPhoto.setVisibility(View.VISIBLE);
//                textToolbar_FragmentName.setVisibility(View.GONE);
//            } else {
//                toolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPremiumWhite));
//                imgToolbar_UserPhoto.setVisibility(View.GONE);
//                textToolbar_FragmentName.setVisibility(View.VISIBLE);
//            }
//        } else {
//            if (intent != null) {
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
//        }
//        return true;


    private void showFullScrenenStayHeladyDailog() {


        final Dialog dialog = new Dialog(HomeActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.dialg_stay_healthy);
        AppCompatTextView txt_headthy = dialog.findViewById(R.id.txt_headthy);

//        if (sessionManager.getStringValue(SessionManager.KEY_USER_DOB))


        Log.d("dob", sessionManager.getStringValue(SessionManager.KEY_USER_DOB));
        if (sessionManager.getStringValue(SessionManager.KEY_USER_DOB) != null) {
            if (!sessionManager.getStringValue(SessionManager.KEY_USER_DOB).isEmpty()) {

                int age = 0;
                try {
                    age = getAge(sessionManager.getStringValue(SessionManager.KEY_USER_DOB));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String msg = sessionManager.getStringValue("Logoutmsg");
                if (!msg.isEmpty()) {
                    txt_headthy.setText(msg);

                } else {
                    txt_headthy.setText("Always a Rockstar");

                }

//                if (age < 18) {
//                    txt_headthy.setText("You are a Star");
//                } else if (age > 18 && age < 30) {
//                    txt_headthy.setText("Miss You");
//
//                } else if (age > 31 && age < 45) {
//                    txt_headthy.setText("Stay Young & Fit");
//
//                } else if (age > 46 && age < 60) {
//                    txt_headthy.setText("Always a Rockstar");
//
//                } else {
//                    txt_headthy.setText("Stay Healthy");
//
//                }

            }
        }

        TextView btn_close = dialog.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = context.getSharedPreferences("AndroidHivePref", Context.MODE_PRIVATE);
                settings.edit().clear().apply();


                sessionManager.logoutUser();
                dialog.dismiss();
                finish();


            }
        });
//        setTvZoomInOutAnimation(txt_headthy);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(550); //You can manage the blinking time with this parameter
        anim.setStartOffset(80);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_headthy.startAnimation(anim);
        dialog.show();


    }


    private void setTvZoomInOutAnimation(final TextView textView) {
        // TODO Auto-generated method stub

        final float startSize = 25;
        final float endSize = 20;
        final int animationDuration = 1100; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (Float) valueAnimator.getAnimatedValue();
                textView.setTextSize(animatedValue);
            }
        });

        //animator.setRepeatCount(ValueAnimator.INFINITE);  // Use this line for infinite animations
        animator.setRepeatCount(8);
        animator.start();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();

        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();

                if (bundle != null) {
                    userPhoto = bundle.getString("image");

                    if (isValidContextForGlide(context)) {
                        Glide.with(context)
                                .load(userPhoto)
                                .apply(RequestOptions.circleCropTransform().error(R.drawable.ic_profile_pic_bg).
                                        skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                                .into(imgDrawerUserPhoto);

                        Glide.with(context)
                                .load(userPhoto)
                                .apply(RequestOptions.circleCropTransform().error(R.drawable.ic_profile_pic_bg).
                                        skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE))
                                .into(imgToolbar_UserPhoto);
                    }
                }
            }
        } else if (requestCode == FcmConstants.REQUEST_CODE && resultCode == RESULT_OK) {

        }

//        boolean test=data.getBooleanExtra("ISFromMaster",false);
//        if (test){
//            ((HomeFragment)fragments).CallToGetInitialData(false);
////            fragment.CallToGetInitialData();


//        }

    }

    @Override
    protected void onPause() {
        // LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            showExitDialog();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    private void showExitDialog() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


    }

    private void showPaidItem()  // This is PAID USER
    {
//        Menu nav_Menu = navigationView.getMenu();
//
//        nav_Menu.findItem(R.id.nav_subscription).setVisible(false);
//        nav_Menu.findItem(R.id.nav_upgrade).setVisible(false);
//        nav_Menu.findItem(R.id.nav_schedule_blood_test).setVisible(true);
//        nav_Menu.findItem(R.id.nav_blood_report).setVisible(true);
    }

    private void hidePaidItem() // This is FREE USER
    {
//        Menu nav_Menu = navigationView.getMenu();
//
//        if (sessionManager.getBooleanValue(SessionManager.KEY_USER_IS_SUBSCRIBE_FOR_FREE)) {
//            nav_Menu.findItem(R.id.nav_subscription).setVisible(false);
//            nav_Menu.findItem(R.id.nav_upgrade).setVisible(true);
//        } else {
//            nav_Menu.findItem(R.id.nav_subscription).setVisible(true);
//            nav_Menu.findItem(R.id.nav_upgrade).setVisible(false);
//        }
//
//        nav_Menu.findItem(R.id.nav_schedule_blood_test).setVisible(true);
//        nav_Menu.findItem(R.id.nav_blood_report).setVisible(false);
    }

    @Override
    public void OnHomeFragment(Uri uri) {
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


    /* GET MIND MODEL HERE */
    @Override
    public void onSubmitMindMood(MinMoodModel model) {
        if (model != null) {
            /*HealthParameters1Fragment fragX = (HealthParameters1Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE);
            fragX.validateData();*/
            FragmentManager fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_HOME);
            homeFragment.setMindDataFromActivity(model);
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
                        addExpandalbe();


                    }
                } else {
                    //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    Log.d("Error---->", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t) {
                addExpandalbe();

                utils.hideProgressbar();
            }
        });
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

                                if (data.getBloodTestStatus() != null) {
                                    if (data.getBloodTestStatus().equalsIgnoreCase("2")) {
//                                        nav_camara.setTitle("Reschedule Blood Test");

                                    } else {
//                                        nav_camara.setTitle("Schedule Blood Test");

                                    }
                                } else {
//                                    nav_camara.setTitle("Schedule Blood Test");

                                }

                                callPathoReecoachStatus();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


//                            if (data.getIsReecoachAssigned().equalsIgnoreCase("true")){
//                                SBT_DialogListener  activity = (SBT_DialogListener ) getActivity();
//                                activity.onSBT_Dialog();
//                                dismiss();
//                            }else {
//
//                                Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                            }


//                            callForUsrFreezStatus();


                        }

                    }
                }
                //                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t) {
                callPathoReecoachStatus();
                // Log error here since request failed
                utils.hideProgressbar();
//                callForUsrFreezStatus();

            }
        });

    }

    private void callLockUnlockApi() {

        utils.showProgressbar(context);

        Call<ClsLockUnlockMain> call = loginService.getLockUnlockDataAPI(userId);
        call.enqueue(new Callback<ClsLockUnlockMain>() {
            @Override
            public void onResponse(Call<ClsLockUnlockMain> call, retrofit2.Response<ClsLockUnlockMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {


                    try {
                        ClsLockUnlockMain moodResponse = response.body();

                        if (moodResponse != null) {
                            if (moodResponse.getData() != null && !moodResponse.getData().isEmpty()) {

                                arylst_lock_unlockdata = moodResponse.getData();

                                callUserStatusApi();


                            } else {
//                                Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show();
                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ClsLockUnlockMain> call, Throwable t) {

                utils.hideProgressbar();


                Log.e("sunit------>", t.toString());
            }
        });
    }

    public boolean getLockUnlockStatus(String name) {
        boolean isLock = false;
        for (int i = 0; i < arylst_lock_unlockdata.size(); i++) {
            if (name.equalsIgnoreCase(arylst_lock_unlockdata.get(i).getStaticName())) isLock = true;

        }
        return isLock;


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



    private void CallToFetchRecoachId() {

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
                          int  recoachId = listResponse.getData().getReecoachId();
                            sessionManager.setIntValue(SessionManager.KEY_USER_REECOACH_ID, recoachId);

                            /*CALL TO GET ALL BASIC DAILY INFO */
                            CallToGetInitialData(recoachId);
                        } else {

                            mHomeModel = null;

                            Toast.makeText(context, "Recoach is not assigned", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(mContext, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<GetRecoachByUserResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());

            }
        });
    }






    public void CallToGetInitialData(int id) {
        HomeFragmentRequest request = new HomeFragmentRequest();
        request.setReeworkerID(userId);
        request.setReecoachID(id);

        if (!sessionManager.getStringValue("Entrystatusdate").isEmpty()) {
            request.setCreatedOn(sessionManager.getStringValue("Entrystatusdate"));

        } else {
            request.setCreatedOn(sessionManager.getStringValue("statusdate"));

        }

        session.setStringValue("SleepDate", request.getCreatedOn());

//        Toast.makeText(mContext, "Date "+request.getCreatedOn(), Toast.LENGTH_SHORT).show();


//        request.setCreatedOn(sessionManager.getStringValue("statusdate"));

//        isupdateData = false;
        utils.showProgressbar(context);
        Call<HomeFragmentResponse> call = service.getInitialData(request);
        Log.d("req", call.request().toString());
        call.enqueue(new Callback<HomeFragmentResponse>() {
            @Override
            public void onResponse(Call<HomeFragmentResponse> call, retrofit2.Response<HomeFragmentResponse> response) {
//                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    HomeFragmentResponse listResponse = response.body();


                    if (listResponse != null && listResponse.getCode() == 1) {
                        mHomeModel = listResponse.getData();
//                        Toast.makeText(context, "Server down: " + listResponse.getData(), Toast.LENGTH_LONG).show();
                        text_UserDate.setText("Plan valid till "  + " " +formatDates(mHomeModel.getScheduledTo()) + "");
//                        setHomeData(mHomeModel);
                        callUserStatusApi();
                    } else {
//                        if (!isSwipeToRefresh)
                            utils.hideProgressbar();
//                        intaildataMsg = response.body().getMessage();

//                        sessionManager.setStringValue("KeynotAssingReacoachMsg", intaildataMsg);


                        callUserStatusApi();


                    }
                } else {
//                    Toast.makeText(mContext, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
//                    if (!isSwipeToRefresh)
//                        utils.hideProgressbar();
                }
            }

            @Override
            public void onFailure(Call<HomeFragmentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ERROR------>", t.toString());
//                if (!isSwipeToRefresh)
//                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
