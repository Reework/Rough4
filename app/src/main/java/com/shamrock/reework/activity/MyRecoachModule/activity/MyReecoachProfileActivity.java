package com.shamrock.reework.activity.MyRecoachModule.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.AppoinmentModule.activity.AppoinmentScheduleActivity;
import com.shamrock.reework.activity.AppoinmentModule.adapter.MyAppointmentsAdapter;
import com.shamrock.reework.activity.AppoinmentModule.dialog.MyAppoinmentEditDialog;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.activity.BloodTestModule.activity.SnipeetActivity;
import com.shamrock.reework.activity.MiscellaneousModule.controller.MiscellaneousActivity;
import com.shamrock.reework.activity.MyRecoachModule.adapters.AdditionalQnAdapter;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.SingleChatModule.adapter.SingleChatAdapter;
import com.shamrock.reework.activity.SingleChatModule.service.ChatService;
import com.shamrock.reework.activity.SingleChatModule.service.SingleChatModel;
import com.shamrock.reework.activity.health.HealthNewActivity;
import com.shamrock.reework.activity.mybcaplan.ClsMyBCAPlanActivity;
import com.shamrock.reework.activity.reecoach.ReecoachRatingActivity;
import com.shamrock.reework.activity.reecoachquestion.ReecoachQuestionActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.ReecoachDetailsRequest;
import com.shamrock.reework.api.request.SaveSingleChatRequest;
import com.shamrock.reework.api.request.SingleChatRequest;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;
import com.shamrock.reework.api.response.SingleChatResponse;
import com.shamrock.reework.common.TouchImageView;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class MyReecoachProfileActivity extends AppCompatActivity implements View.OnClickListener, MyAppointmentsAdapter.MyAppointmentListener,
        MyAppoinmentEditDialog.ApponmentEditInterface
{

    private static final String TAG = "MyReecoachProfile";
    Context context;
    Toolbar toolbar;
    Typeface font;
    RadioButton rd_button_reecoach_appointment,rd_button_bca,rd_button_quest,rd_button_health;
    ImageView  imgReecoach;
    //    imgReecoachBg
    TextView textView_Name, textView_MobNo, textView_Email, textView_RegAddress, textView_NewAddress;
    SessionManager sessionManager;
    ReecoachService reecoachService;
    Utils utils;
    String email;
    int userId;ImageView imgViewNext_roacoach;
    private ProgressBar progressBar;
    //    SwipeRefreshLayout swipeRefreshLayout;
    TextView btn_change_reecoach;
    RecyclerView recyler_reecoach_add_info;
    int size;
    TextView txt_rate_reecoach;
    RadioButton rd_button__reecoach_profile;
    TextView text_ida_number;

    //appointemnt
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
    TextView txt_unreadcount;
    ImageView img_noti;
    TextView txtNoData;
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView  txt_no_weight;

    //chat
    private ImageView btnSend;
    private EditText inputMsg;
    String lsMessege;
    ChatService chatService;
    SwipeRefreshLayout swipeRefreshLayout1;

    int  recoachId;
    ViewFlipper vp_reecoach;
    boolean isFalse=false;
    RadioButton rd_button_reecoach,rd_button_patho;


    // Chat messages list adapter
    private ArrayList<SingleChatResponse.ChatList> chatList;
    private SingleChatAdapter chatAdapter;
    private RecyclerView recyclerView_chat;
    SingleChatModel model;
    CountDownTimer countDownTimer;
    int object_size;
    TextView txt_bca_plam_my;
    RadioButton rd_button_chat;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;


    private void callProfileDetailsAPI() {

        ReecoachDetailsRequest request = new ReecoachDetailsRequest();
//        request.setEmail(email);
        request.setUserid(userId);

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

                            String reecoachName = dataResponse.getFirstName() + " " + dataResponse.getLastName();
                            sessionManager.setStringValue("pathologistname",reecoachName);











                        } else {
                        }
                    } else {
                    }
                } else {

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reecoach_profile);
        context = MyReecoachProfileActivity.this;
        rd_button_patho=findViewById(R.id.rd_button_patho);
        rd_button_reecoach=findViewById(R.id.rd_button_reecoach);
        rd_button_reecoach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    callToGetAllAppoinmentsDynamic(3);
                }
            }
        });
        rd_button_patho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    callToGetAllAppoinmentsDynamic(4);
                }
            }
        });
        recyler_reecoach_add_info=findViewById(R.id.recyler_reecoach_add_info);
        img_noti=findViewById(R.id.img_noti);
        txt_unreadcount=findViewById(R.id.txt_unreadcount);
        rd_button_health=findViewById(R.id.rd_button_health);
        text_ida_number=findViewById(R.id.text_ida_number);
        txt_rate_reecoach=findViewById(R.id.txt_rate_reecoach);
        txt_rate_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyReecoachProfileActivity.this, ReecoachRatingActivity.class));
            }
        });
        init();
        rd_button_quest=findViewById(R.id.rd_button_quest);
        rd_button_bca=findViewById(R.id.rd_button_bca);
        findViews();
        inits();
        findViewss();
        initchat();
        findViewschat();



        rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
        rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));

        rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.white));
        rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
        rd_button_health.setTextColor(getResources().getColor(R.color.black));
        rd_button_bca.setTextColor(getResources().getColor(R.color.black));
        rd_button_chat.setTextColor(getResources().getColor(R.color.black));
        rd_button_quest.setTextColor(getResources().getColor(R.color.black));

        txt_bca_plam_my=findViewById(R.id.txt_bca_plam_my);
        txt_bca_plam_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyReecoachProfileActivity.this, ClsMyBCAPlanActivity.class) );

            }
        });


        rd_button_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd_button__reecoach_profile.performClick();



//                rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
//                rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));
//
//                rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.black));
//                rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
//                rd_button_health.setTextColor(getResources().getColor(R.color.black));
//                rd_button_bca.setTextColor(getResources().getColor(R.color.white));
//                rd_button_chat.setTextColor(getResources().getColor(R.color.black));
//                rd_button_quest.setTextColor(getResources().getColor(R.color.black));

                startActivity(new Intent(MyReecoachProfileActivity.this, ClsMyBCAPlanActivity.class));
                overridePendingTransition(0,0);
            }
        });

        rd_button_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd_button__reecoach_profile.performClick();


//                rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
//                rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
//                rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));
//
//                rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.black));
//                rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
//                rd_button_health.setTextColor(getResources().getColor(R.color.white));
//                rd_button_bca.setTextColor(getResources().getColor(R.color.black));
//                rd_button_chat.setTextColor(getResources().getColor(R.color.black));
//                rd_button_quest.setTextColor(getResources().getColor(R.color.black));




                startActivity(new Intent(MyReecoachProfileActivity.this, HealthNewActivity.class));
                overridePendingTransition(0,0);
            }
        });

        rd_button_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rd_button_quest.performClick();


                rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));

                rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.black));
                rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
                rd_button_health.setTextColor(getResources().getColor(R.color.black));
                rd_button_bca.setTextColor(getResources().getColor(R.color.black));
                rd_button_chat.setTextColor(getResources().getColor(R.color.black));
                rd_button_quest.setTextColor(getResources().getColor(R.color.white));


                startActivity(new Intent(MyReecoachProfileActivity.this, ReecoachQuestionActivity.class));
                overridePendingTransition(0,0);
            }
        });


        sessionManager.setStringValue("Back","");

        if (getIntent().getStringExtra("FromNotification")!=null&&getIntent().getStringExtra("FromNotification").equalsIgnoreCase("true")){
            txt_unreadcount.setVisibility(View.GONE);
            txt_unreadcount.setText("");
            img_noti.setVisibility(View.GONE);
            rd_button_chat.performClick();
        }

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


        callProfileDetailsAPI();
        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();



//        getReecaochTypeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer=null;
        }

    }

    void openWhatsappContact(final String numbers) {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.activity_sms_text);
        final EditText edt_message=dialog.findViewById(R.id.edt_message);
        TextView txt_send=dialog.findViewById(R.id.txt_send);
        ImageView img_closee=dialog.findViewById(R.id.img_closee);
        img_closee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number="+91"+numbers;
                if (edt_message.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter text",Toast.LENGTH_LONG).show();

                }
                String msg=edt_message.getText().toString();
                try {
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(number,null,msg,null,null);
                    Toast.makeText(getApplicationContext(),"Message Sent",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Enter Message ",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
        if (true){
            return;
        }





    }


    private void whatsappMsg(String number){
        String phoneNumberWithCountryCode = "+91"+number;
        String message = "Hello,";

        startActivity(
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                        )
                )
        );
    }




    private void getReecaochTypeData(){


        utils.showProgressbar(this);

        Call<ClsReecoachMasterType> call = reecoachService.GetReecoachTypeMaster();
        call.enqueue(new Callback<ClsReecoachMasterType>()
        {
            @Override
            public void onResponse(Call<ClsReecoachMasterType> call, retrofit2.Response<ClsReecoachMasterType> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsReecoachMasterType moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode()==1){

                                if (moodResponse.getData()!=null){
                                    Intent intent=new Intent(MyReecoachProfileActivity.this, ChangeReecoachActivity.class);
                                    intent.putExtra("KEY_ReecoachMasterType",moodResponse);
                                    startActivityForResult(intent,205);
                                }

//                                Toast.makeText(mContext, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();
                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                new UserHealthResponse().Data=moodResponse.getData();

                }


            }

            @Override
            public void onFailure(Call<ClsReecoachMasterType> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void init()
    {
        sessionManager = new SessionManager(context);
        reecoachService = Client.getClient().create(ReecoachService.class);
        utils = new Utils();
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText(R.string.reecoach);
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vp_reecoach.getDisplayedChild()==0){
                    finish();

                }else {
                    rd_button__reecoach_profile.performClick();

                    if (vp_reecoach.getDisplayedChild()==0){
                        btn_change_reecoach.setVisibility(View.VISIBLE);
                        txt_rate_reecoach.setVisibility(View.VISIBLE);
                    }else {
                        btn_change_reecoach.setVisibility(View.GONE);
                        txt_rate_reecoach.setVisibility(View.GONE);
                    }

                }
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

    private void findViews()
    {
        rd_button_chat=findViewById(R.id.rd_button_chat);

//        imgReecoachBg = findViewById(R.id.imageView_ReecoachProfile_Photo_bg);
        btn_change_reecoach = findViewById(R.id.btn_change_reecoach);
        imgViewNext_roacoach = findViewById(R.id.imgViewNext_roacoach);
        imgReecoach = findViewById(R.id.imageView_ReecoachProfile_Photo);
        textView_Name = findViewById(R.id.text_ReecoachProfile_Name);
        textView_MobNo = findViewById(R.id.text_ReecoachProfile_Mobile);
        textView_Email = findViewById(R.id.text_ReecoachProfile_Email);
        textView_RegAddress = findViewById(R.id.text_ReecoachProfile_RegAddress);
        textView_NewAddress = findViewById(R.id.text_ReecoachProfile_NewAddress);
        progressBar = findViewById(R.id.progress);
        rd_button_reecoach_appointment = findViewById(R.id.rd_button_reecoach_appointment);
        vp_reecoach=findViewById(R.id.vp_reecoach);
        vp_reecoach.setDisplayedChild(0);
        rd_button__reecoach_profile=findViewById(R.id.rd_button__reecoach_profile);
        rd_button__reecoach_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.white));
                rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
                rd_button_health.setTextColor(getResources().getColor(R.color.black));
                rd_button_bca.setTextColor(getResources().getColor(R.color.black));
                rd_button_chat.setTextColor(getResources().getColor(R.color.black));
                rd_button_quest.setTextColor(getResources().getColor(R.color.black));










                btn_change_reecoach.setVisibility(View.VISIBLE);
                txt_rate_reecoach.setVisibility(View.VISIBLE);
                vp_reecoach.setDisplayedChild(0);
                txt_bca_plam_my.setVisibility(View.GONE);

                if (countDownTimer!=null){
                    countDownTimer.cancel();
                    countDownTimer=null;
                }

                if (Utils.isNetworkAvailable(context))
                {
                    callProfileDetailsAPI(email, userId);
                }
                else
                {
                    ShowRetryBar();
                }

            }
        });

        rd_button_chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.black));
                rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
                rd_button_health.setTextColor(getResources().getColor(R.color.black));
                rd_button_bca.setTextColor(getResources().getColor(R.color.black));
                rd_button_chat.setTextColor(getResources().getColor(R.color.white));
                rd_button_quest.setTextColor(getResources().getColor(R.color.black));



                btn_change_reecoach.setVisibility(View.GONE);
                txt_rate_reecoach.setVisibility(View.GONE);

                vp_reecoach.setDisplayedChild(2);
                txt_bca_plam_my.setVisibility(View.GONE);
                isFalse=true;
                if (vp_reecoach.getDisplayedChild()==2){
                    countDownTimer=  new CountDownTimer(150000, 4000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                            GetAllChatRefresh(false);
                        }

                        @Override
                        public void onFinish() {


                        }
                    }.start();
                }

                recyclerView_chat.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
//                        if (newState==0){
//                            countDownTimer.cancel();
//                        }
//                        if (newState==1){
//                            countDownTimer.start();
//                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                    }
                });

            }
        });


        rd_button_reecoach_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.black));
                rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.white));
                rd_button_health.setTextColor(getResources().getColor(R.color.black));
                rd_button_bca.setTextColor(getResources().getColor(R.color.black));
                rd_button_chat.setTextColor(getResources().getColor(R.color.black));
                rd_button_quest.setTextColor(getResources().getColor(R.color.black));

                vp_reecoach.setDisplayedChild(1);

                btn_change_reecoach.setVisibility(View.GONE);
                txt_rate_reecoach.setVisibility(View.GONE);

                txt_bca_plam_my.setVisibility(View.GONE);

                if (countDownTimer!=null){
                    countDownTimer.cancel();
                    countDownTimer=null;
                }

//                startActivity(new Intent(MyReecoachProfileActivity.this, AppointmentsActivity.class));
            }
        });

        btn_change_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getReecaochTypeData();

            }
        });


        imgViewNext_roacoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyReecoachProfileActivity.this, SnipeetActivity.class));
            }
        });

//        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
//                android.R.color.holo_green_light,
//                android.R.color.holo_purple,
//                android.R.color.holo_red_light);

        email = sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        if (Utils.isNetworkAvailable(context))
        {
            callProfileDetailsAPI(email, userId);
        }
        else
        {
            ShowRetryBar();
        }

        /* SWIPE TO REFRESH */
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//                if (Utils.isNetworkAvailable(context))
//                {
//                    callProfileDetailsAPI(email, userId);
//                }
//                else
//                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();
//            }
//        });



    }

    private void ShowRetryBar()
    {
        String strMessage = "Unable to load data";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callProfileDetailsAPI(email, userId);
                    }
                });
        snackbar.show();
    }

    private void callProfileDetailsAPI(String email, int userid) {

        ReecoachDetailsRequest request = new ReecoachDetailsRequest();
        request.setEmail(email);
        request.setUserid(userid);

        Call<ReecoachDetailsResponse> call = reecoachService.getProfileDetails(request);
        call.enqueue(new Callback<ReecoachDetailsResponse>()
        {
            @Override
            public void onResponse(Call<ReecoachDetailsResponse> call, Response<ReecoachDetailsResponse> response)
            {
//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ReecoachDetailsResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        final ReecoachDetailsResponse.DataResponse dataResponse = listResponse.getData();

                        if (dataResponse != null)
                        {



                            if (dataResponse.getUnreadMessageCount()!=0){
                                txt_unreadcount.setVisibility(View.VISIBLE);
                                txt_unreadcount.setText(""+dataResponse.getUnreadMessageCount());
                                img_noti.setVisibility(View.VISIBLE);
                            }else {
                                txt_unreadcount.setVisibility(View.GONE);
                                txt_unreadcount.setText(""+dataResponse.getUnreadMessageCount());
                                img_noti.setVisibility(View.GONE);
                            }


                            btn_change_reecoach.setText("Change Reecoach");

                            String reecoachName = dataResponse.getFirstName() + " " + dataResponse.getLastName();
                            sessionManager.setStringValue("Reecoachname",reecoachName);
                            final String reecoachMobile = dataResponse.getMobileNo();
                            final String reecoachEmail = dataResponse.getEmail();
                            String reecoachAddress = dataResponse.getAddress();
                            final String imgUrl = dataResponse.getImageUrl();

                            if (dataResponse.getIdaNumber()!=null&&!dataResponse.getIdaNumber().isEmpty()){
                                text_ida_number.setText("IDA No : "+dataResponse.getIdaNumber());
                            }else {
                                text_ida_number.setText("IDA No : ");

                            }

                            LinearLayout ll_whatsappmessage=findViewById(R.id.ll_whatsappmessage);
                            LinearLayout ll_text_msg=findViewById(R.id.ll_text_msg);
                            ll_text_msg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    openNewpermission(dataResponse);

                                }
                            });

                            ll_whatsappmessage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    whatsappMsg(dataResponse.getMobileNo());
                                }
                            });
                            TextView txt_description_reecoach=findViewById(R.id.txt_description_reecoach);
                            if (dataResponse.getDiscription()!=null&&!dataResponse.getDiscription().isEmpty()){
                                txt_description_reecoach.setVisibility(View.VISIBLE);

                                txt_description_reecoach.setText(dataResponse.getDiscription());
                            }else {
                                txt_description_reecoach.setVisibility(View.GONE);
                                txt_description_reecoach.setText("Expertise : ");

                            }

                            if (!TextUtils.isEmpty(reecoachName))
                                if (dataResponse.getReecoachType()!=null&&!dataResponse.getReecoachType().trim().isEmpty()){




                                    String type=dataResponse.getReecoachType().substring(0,dataResponse.getReecoachType().length()-2);

                                    String strReecoachType="("+type+")";

                                    textView_Name.setText(reecoachName);
                                }else {
                                    textView_Name.setText(reecoachName);
                                }


                            if (!TextUtils.isEmpty(reecoachMobile)) {
                                textView_MobNo.setText("+91 "+reecoachMobile);

                                textView_MobNo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Dexter.withActivity(MyReecoachProfileActivity.this)
                                                .withPermission(Manifest.permission.CALL_PHONE)
                                                .withListener(new PermissionListener() {
                                                    @Override
                                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + reecoachMobile));
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onPermissionDenied(PermissionDeniedResponse response) {

                                                        showSettingsDialog();

                                                    }

                                                    @Override
                                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                                                    }


                                                }).check();
                                    }
                                });

                            }





                            if (!TextUtils.isEmpty(reecoachEmail)) {
                                textView_Email.setText(reecoachEmail);
                                textView_Email.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + reecoachEmail));
                                        startActivity(emailIntent);
                                    }
                                });

                            }


                            if (dataResponse.isAllowChange()){
                                btn_change_reecoach.setVisibility(View.VISIBLE);
                            }else {
                                btn_change_reecoach.setVisibility(View.GONE);





































































































                            }

                            if (!TextUtils.isEmpty(reecoachAddress))
                                textView_RegAddress.setText(reecoachAddress);

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_profile_pic_bg)
                                    .error(R.drawable.ic_profile_pic_bg)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .priority(Priority.HIGH);


                            if (listResponse.getData().getAdditionalDetails()!=null&&!listResponse.getData().getAdditionalDetails().isEmpty()){
//                                ArrayList<AdditionalDetails> list=listResponse.getData().getAdditionalDetails();
//                                list.addAll(list);
                                recyler_reecoach_add_info.setAdapter(new AdditionalQnAdapter(MyReecoachProfileActivity.this,listResponse.getData().getAdditionalDetails()));

                            }
                            imgReecoach.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    opens(imgUrl);
                                }
                            });



                            if (isValidContextForGlide(context))
                            {
                                // for background Image
//                                Glide.with(context)
//                                        .load(imgUrl)
//                                        .apply(options)
//                                        .into(imgReecoachBg);

                                // for profile Image
                                Glide.with(context)
                                        .load(imgUrl)
                                        .apply(options)
                                        .apply(RequestOptions.circleCropTransform())
                                        .listener(new RequestListener<Drawable>()
                                        {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                                        Target<Drawable> target, boolean isFirstResource)
                                            {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                                           DataSource dataSource, boolean isFirstResource)
                                            {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }
                                        })
                                        .into(imgReecoach);
                            }
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    btn_change_reecoach.setText("Select Reecoach");
                    btn_change_reecoach.performClick();

                    Toast.makeText(context, "You have not selected any Reecoach ,Please select Reecoach first", Toast.LENGTH_SHORT).show();

//                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<ReecoachDetailsResponse> call, Throwable t)
            {
                // Log error here since request failed
                progressBar.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
                ShowRetryBar();
            }
        });
    }

    private void openNewpermission(final ReecoachDetailsResponse.DataResponse dataResponse) {
        Dexter.withActivity(MyReecoachProfileActivity.this)
                .withPermission(Manifest.permission.SEND_SMS)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted, open the camera

                        openWhatsappContact(dataResponse.getMobileNo());
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

    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyReecoachProfileActivity.this);
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

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }




    private void inits()
    {
        appoinmentService = Client.getClient().create(AppoinmentService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);
    }


    private void findViewss()
    {
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
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);
//        swipeRefreshLayout = findViewById(R.id.swipeContainer);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
//                android.R.color.holo_green_light,
//                android.R.color.holo_purple,
//                android.R.color.holo_red_light);

        /* SWIPE TO REFRESH */
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
//        {
//            @Override
//            public void onRefresh()
//            {
//                if (Utils.isNetworkAvailable(context))
//                    callToGetAllAppoinments(true);
//                else
//                    showLayouts();
//            }
//        });

        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);
        else
            showLayouts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.getStringValue("Back").equalsIgnoreCase("true")){
            callToGetAllAppoinments(false);
            sessionManager.setStringValue("Back","");

        }

        vp_reecoach.setDisplayedChild(0);
        if (Utils.isNetworkAvailable(context))
        {
            callProfileDetailsAPI(email, userId);
        }
        else
        {
            ShowRetryBar();
        }

        rd_button__reecoach_profile.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
        rd_button_reecoach_appointment.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_health.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_bca.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_chat.setBackgroundResource((R.drawable.custom_white_radio_new1));
        rd_button_quest.setBackgroundResource((R.drawable.custom_white_radio_new1));

        rd_button__reecoach_profile.setTextColor(getResources().getColor(R.color.white));
        rd_button_reecoach_appointment.setTextColor(getResources().getColor(R.color.black));
        rd_button_health.setTextColor(getResources().getColor(R.color.black));
        rd_button_bca.setTextColor(getResources().getColor(R.color.black));
        rd_button_chat.setTextColor(getResources().getColor(R.color.black));
        rd_button_quest.setTextColor(getResources().getColor(R.color.black));


    }

    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
//            swipeRefreshLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    private void callToGetAllAppoinments(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setRoleId(3);
        request.setUserID(userId);


        Call<GetAllAppointmentResponse> call = appoinmentService.getAllAppoinments(request);
        call.enqueue(new Callback<GetAllAppointmentResponse>()
        {
            @Override
            public void onResponse(Call<GetAllAppointmentResponse> call, Response<GetAllAppointmentResponse> response)
            {

                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetAllAppointmentResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<GetAllAppointmentResponse.AppointmentData>  tempList = appointmentResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
                            appointmentDataArrayList.clear();
                            appointmentDataArrayList.addAll(tempList);
                            adapter.notifyDataSetChanged();
                            txtNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            txtNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                    else

                    if (vp_reecoach.getDisplayedChild()==1)
                    {
                        txtNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
//                        Snackbar.make(findViewById(android.R.id.content), appointmentResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<GetAllAppointmentResponse> call, Throwable t)
            {
                Log.e("ERROR---->", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    private void callToGetAllAppoinmentsDynamic( int Rollid)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setRoleId(Rollid);
        request.setUserID(userId);


        Call<GetAllAppointmentResponse> call = appoinmentService.getAllAppoinments(request);
        call.enqueue(new Callback<GetAllAppointmentResponse>()
        {
            @Override
            public void onResponse(Call<GetAllAppointmentResponse> call, Response<GetAllAppointmentResponse> response)
            {

                utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetAllAppointmentResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<GetAllAppointmentResponse.AppointmentData>  tempList = appointmentResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
                            appointmentDataArrayList.clear();
                            appointmentDataArrayList.addAll(tempList);
                            adapter.notifyDataSetChanged();
                            txtNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            txtNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                    else

                    if (vp_reecoach.getDisplayedChild()==1)
                    {
                        txtNoData.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
//                        Snackbar.make(findViewById(android.R.id.content), appointmentResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<GetAllAppointmentResponse> call, Throwable t)
            {
                Log.e("ERROR---->", t.toString());
                utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    private void ShowRetryBar(String msg)
    {
        String strMessage;
        if (msg.isEmpty())
        {
            strMessage = "Unable to load data";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(context))
                        {
                            showLayouts();
                            callToGetAllAppoinments(false);
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {



            case R.id.buttonScheduleAppointment:

                startActivityForResult(new Intent(context, AppoinmentScheduleActivity.class), REQ_FOR_APPOINMENT);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.btnSend:

                if (Utils.isNetworkAvailable(context))
                {
                    if (recoachId > 0)
                    {
                        lsMessege = inputMsg.getText().toString();

                        if (!TextUtils.isEmpty(lsMessege))
                        {

                            try {
                                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                            if (Utils.isNetworkAvailable(context))
                            {
                                showLayouts();
                                CallToSendMessege();
                                inputMsg.setText("");
                            }
                            else
                                showLayouts();
                        }
                        else
                            Toast.makeText(context, "Type something to send", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    showLayouts();
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    callToGetAllAppoinments(false);
                }
                else
                    showLayouts();
                break;


            case R.id.buttonSleep_ViewMore:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_FOR_APPOINMENT && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("RESULT"))
            {
                if (data.getStringExtra("RESULT").equals("ok"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        callToGetAllAppoinments(false);
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == 205 && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("Reeacoach"))
            {
                if (data.getStringExtra("Reeacoach").equals("yes"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        callProfileDetailsAPI(email, userId);

                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }


    }

    /* Get clicked position here */
    @Override
    public void GetClickedAppointment(String type, int position, GetAllAppointmentResponse.AppointmentData model)
    {
        if (!TextUtils.isEmpty(type))
        {
            if (type.equals("edit"))
            {
                FragmentManager fm = getSupportFragmentManager();
                editDialog = new MyAppoinmentEditDialog();

                Bundle bundle = new Bundle();
                bundle.putSerializable("MODEL", model);
                editDialog.setArguments(bundle);
                editDialog.show(fm, "edit_fragment");
            }
            else
            {
                deleteAppoinment(position, model);
            }
        }
    }

    @Override
    public void onEdit(GetAllAppointmentResponse.AppointmentData model)
    {
        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);
        else
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
    }

    public void deleteAppoinment(final int pos , final GetAllAppointmentResponse.AppointmentData model)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete appointment!")
                .setMessage("Do you really want to delete this appointment?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context))
                        {
                            utils.showProgressbar(context);

                            callDeleteAppoinment(pos, model);
                        }
                        else
                        {
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

    private void callDeleteAppoinment(final int pos , GetAllAppointmentResponse.AppointmentData model)
    {
        AppoinmentEditRequest request = new AppoinmentEditRequest();
        request.setUserID(model.getUserID());
        request.setApptID(model.getApptID());

        Call<AppoinmentResponse> call = appoinmentService.deleteAppoinment(request);
        call.enqueue(new Callback<AppoinmentResponse>()
        {
            @Override
            public void onResponse(Call<AppoinmentResponse> call, Response<AppoinmentResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AppoinmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        // call List API to reload list
                        appointmentDataArrayList.remove(pos);
                        adapter.notifyDataSetChanged();
                        callToGetAllAppoinments(false);
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppoinmentResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }



    //Reecoach Chat
    private void initchat()
    {
        chatService = Client.getClient().create(ChatService.class);
//        utils = new Utils();
        sessionManager = new SessionManager(context);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        recoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
    }



    private void findViewschat()
    {
        btnSend =  findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.inputMsg);
        recyclerView_chat = findViewById(R.id.recyclerView_chat);
        noInternetLayout =  findViewById(R.id.no_internet);
        btnRefresh = findViewById(R.id.btnRefresh);

        swipeRefreshLayout1 = findViewById(R.id.swipeContainer1);
        // Configure the refreshing colors
        swipeRefreshLayout1.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_purple,
                android.R.color.holo_red_light);

        btnSend.setOnClickListener(this);
//        btnRefresh.setOnClickListener(this);

        chatList = new ArrayList<>();
        chatAdapter = new SingleChatAdapter(this, chatList, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_chat.setLayoutManager(layoutManager);
        recyclerView_chat.setItemAnimator(new DefaultItemAnimator());
        recyclerView_chat.setAdapter(chatAdapter);

        if (Utils.isNetworkAvailable(context))
            GetAllChat(false);

        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();

        /* SWIPE TO REFRESH */
        swipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if (Utils.isNetworkAvailable(context))
                {
                    GetAllChat(true);
                }
                else
                    Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void showLayoutschat()
    {
        if (!Utils.isNetworkAvailable(context))
        {
//            swipeRefreshLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }



    private void GetAllChat(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);

        Call<SingleChatResponse> call = chatService.getAllChat(1,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {

                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

                if (swipeRefreshLayout1.isRefreshing())
                    swipeRefreshLayout1.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();
                            recyclerView_chat.smoothScrollToPosition(chatList.size()-1);


                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }


    private void GetAllChatRefresh(final boolean isSwipeToRefresh)
    {


        SingleChatRequest request = new SingleChatRequest();
        request.setUserID(userId);
        Call<SingleChatResponse> call = chatService.getAllChat(1,userId);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {


                if (swipeRefreshLayout1.isRefreshing())
                    swipeRefreshLayout1.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = appointmentResponse.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            if (object_size<tempChatList.size()){
                                recyclerView_chat.smoothScrollToPosition(tempChatList.size()-1);

                            }

                            object_size=tempChatList.size();
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();


                        }
                    }
                    else
                        Toast.makeText(context, "" + appointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    ShowRetryBar(response.message());
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

//    private void ShowRetryBar(String msg)
//    {
//        String strMessage;
//        if (msg.isEmpty())
//        {
//            strMessage = "Unable to load data";
//        }
//        else
//        {
//            strMessage = msg;
//        }
//
//        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
//                .setAction("Retry", new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View view)
//                    {
//                        if (Utils.isNetworkAvailable(context))
//                        {
//                            showLayouts();
//                            GetAllChat(false);
//                        }
//                        else
//                            showLayouts();
//                    }
//                });
//
//        snackbar.show();
//    }

    private void CallToSendMessege()
    {
        if (!((Activity) context).isFinishing())
        {
//            utils.showProgressbar(context);
        }
        SaveSingleChatRequest request = new SaveSingleChatRequest();
        request.setFromUserID(userId);
        request.setToUserID(recoachId);
        request.setMessage(lsMessege);

        Call<SingleChatResponse> call = chatService.saveChat(request);
        call.enqueue(new Callback<SingleChatResponse>()
        {
            @Override
            public void onResponse(Call<SingleChatResponse> call, Response<SingleChatResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SingleChatResponse body = response.body();

                    if (body != null && body.getCode() == 1)
                    {
                        ArrayList<SingleChatResponse.ChatList>  tempChatList = body.getData();

                        if (tempChatList != null && tempChatList.size() > 0)
                        {
                            chatList.clear();
                            chatList.addAll(tempChatList);
                            chatAdapter.notifyDataSetChanged();
                            recyclerView_chat.smoothScrollToPosition(chatList.size()-1);
                        }
                        else
                            Snackbar.make(findViewById(android.R.id.content), "No chat history found !",
                                    Snackbar.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), body.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleChatResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
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




}
