package com.shamrock.reework.activity.RescoreModule.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.shamrock.R;
import com.shamrock.reework.activity.InterpretationModule.activity.InterpretationActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.RescoreModule.model.ClsAddingImageDetails;
import com.shamrock.reework.activity.RescoreModule.model.ClsReescoreMianClass;
import com.shamrock.reework.activity.aNewInterpretation.model.ClsReescoreMain;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.RescoreModule.service.RescoreService;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.RescoreRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.RescoreResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReescoreActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    Context context;
    Toolbar toolbar;
    Typeface font;
    //    ImageView imgNext;
    TextView tvCurrentScore;
    ArcSeekBar arcSeekBar;
    ArcSeekBar seekArc2;
    ArcSeekBar seekArc3;

    ProgressBar pbar_first_reescore,pbar_recent_reescore,pbar_future_reescore;

    Utils utils;
    RescoreService service;
    SessionManager sessionManager;

    int userId;
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
    LinearLayout l1,l2,l3,l4,l5,l6,l7,l8,l9;
    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9;
    Button buttonSubmit_MyProfiless;

    //    ImageView img_cycle;
    ClsReescoreMianClass reescoreMianClassl;
    ArrayList<ClsAddingImageDetails> arylst_adding_image = new ArrayList<>();
    ImageView img_add_1, img_add_2, img_add_3, img_add_4;
    TextView txt_add_1, txt_add_2, txt_add_3, txt_add_4;

    TextView txt_Todays_Score, txt_First_score, tvFutureScore;
    private float mTodaysREEscore, mFutureScore;

    TextView txt_reescore;
    ClsReescoreMianClass reescoreMianClassl1;
    SpannableStringBuilder spannableStringBuilder,spannableStringBuilder1;
    String strText="",strText1;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reescore);
        context = ReescoreActivity.this;



        reescoreMianClassl = (ClsReescoreMianClass) getIntent().getSerializableExtra("REESCORE_DATA");

        txt_cycle_text = findViewById(R.id.txt_cycle_text);

//        img_cycle = findViewById(R.id.img_cycle);
        txt_First_score = findViewById(R.id.tvFirstScore);
        txt_Todays_Score = findViewById(R.id.txt_todays_score);
        tvFutureScore = findViewById(R.id.tv_future_score);

        buttonSubmit_MyProfiless = findViewById(R.id.buttonSubmit_MyProfiless);
        buttonSubmit_MyProfiless.setOnClickListener(this);
        //adding Image
        img_add_1 = findViewById(R.id.img_add_1);
        img_add_2 = findViewById(R.id.img_add_2);
        img_add_3 = findViewById(R.id.img_add_3);
        img_add_4 = findViewById(R.id.img_add_4);

        txt_add_1 = findViewById(R.id.txt_add_1);
        txt_add_2 = findViewById(R.id.txt_add_2);
        txt_add_3 = findViewById(R.id.txt_add_3);
        txt_add_4 = findViewById(R.id.txt_add_4);

        for (int i = 0; i < reescoreMianClassl.getData().size(); i++) {

            if (reescoreMianClassl.getData().get(i).getMessageType().equalsIgnoreCase("Adding Image")) {
                arylst_adding_image.add(new ClsAddingImageDetails(reescoreMianClassl.getData().get(i).getImagePath(),
                        reescoreMianClassl.getData().get(i).getMessage()));

            }
        }


        if (arylst_adding_image.size() == 3) {
            img_add_1.setVisibility(View.VISIBLE);
            img_add_2.setVisibility(View.VISIBLE);
            img_add_3.setVisibility(View.VISIBLE);
            setAddingImage(arylst_adding_image.get(0).getAddingImageName(), img_add_1, txt_add_1, arylst_adding_image.get(0).getAddingImageMessage());
            setAddingImage(arylst_adding_image.get(1).getAddingImageName(), img_add_2, txt_add_2, arylst_adding_image.get(1).getAddingImageMessage());
            setAddingImage(arylst_adding_image.get(2).getAddingImageName(), img_add_3, txt_add_3, arylst_adding_image.get(2).getAddingImageMessage());
        }

        if (arylst_adding_image.size() == 2) {
            img_add_1.setVisibility(View.VISIBLE);
            img_add_2.setVisibility(View.VISIBLE);
            LinearLayout ll_33 = findViewById(R.id.ll_33);
            ll_33.setVisibility(View.GONE);
            setAddingImage(arylst_adding_image.get(0).getAddingImageName(), img_add_1, txt_add_1, arylst_adding_image.get(0).getAddingImageMessage());
            setAddingImage(arylst_adding_image.get(1).getAddingImageName(), img_add_2, txt_add_2, arylst_adding_image.get(1).getAddingImageMessage());
//            setAddingImage(arylst_adding_image.get(2).getAddingImageName(),img_add_3,txt_add_3, arylst_adding_image.get(2).getAddingImageMessage());
        }


        for (int i = 0; i < reescoreMianClassl.getData().size(); i++) {

            if (reescoreMianClassl.getData().get(i).getMessageType().equalsIgnoreCase("Default Image")) {
                setAddingImage(reescoreMianClassl.getData().get(i).getImagePath(), img_add_4, txt_add_4,"Health");

                break;

            }
        }

//        textReescore_BigMessage = findViewById(R.id.txt2);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        l5 = findViewById(R.id.l5);
        l6 = findViewById(R.id.l6);
        l7 = findViewById(R.id.l7);
        l8 = findViewById(R.id.l8);
        l9 = findViewById(R.id.l9);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        txt8 = findViewById(R.id.txt8);
        txt9 = findViewById(R.id.txt9);
        for (int i = 0; i < reescoreMianClassl.getData().size(); i++) {

            if (reescoreMianClassl.getData().get(i).getMessageType().equalsIgnoreCase("ReeScore Description")) {
//                textReescore_BigMessage.setText(reescoreMianClassl.getData().get(i).getMessage());

                String lines[] = reescoreMianClassl.getData().get(i).getMessage().split("\\r?\\n");

                for (int k = 0; k < lines.length; k++) {
                    if(k==0){
                        String replacetext = "(" + String.valueOf(k + 1) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt1.setText(text);
                        l1.setVisibility(View.VISIBLE);
                    }
                    if(k==1){
                        String replacetext = "(" + String.valueOf(k) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt2.setText(text);
                        l2.setVisibility(View.VISIBLE);
                    }
                    if(k==2){
                        String replacetext = "(" + String.valueOf(k ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt3.setText(text);
                        l3.setVisibility(View.VISIBLE);
                    }
                    if(k==3){
                        String replacetext = "(" + String.valueOf(k ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt4.setText(text);
                        l4.setVisibility(View.VISIBLE);
                    }
                    if(k==4){
                        String replacetext = "(" + String.valueOf(k ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt5.setText(text);
                        l5.setVisibility(View.VISIBLE);
                    }
                    if(k==5){
                        String replacetext = "(" + String.valueOf(k ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt6.setText(text);
                        l6.setVisibility(View.VISIBLE);
                    }
                    if(k==6){
                        String replacetext = "(" + String.valueOf(k ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt7.setText(text);
                        l7.setVisibility(View.VISIBLE);
                    }
                    if(k==7){
                        String replacetext = "(" + String.valueOf(k ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt8.setText(text);
                        l8.setVisibility(View.VISIBLE);
                    }
                    if(k==8){
                        String replacetext = "(" + String.valueOf(k
                        ) + ")";
                        String text = lines[k].replace(replacetext, "");

                        txt9.setText(text);
                        l9.setVisibility(View.VISIBLE);
                    }

                }

                break;

            }
        }


        for (int i = 0; i < reescoreMianClassl.getData().size(); i++) {

            if (reescoreMianClassl.getData().get(i).getMessageType().equalsIgnoreCase("Main Image")) {
                txt_cycle_text.setText(reescoreMianClassl.getData().get(i).getMessage());
                setCylcerImage(reescoreMianClassl.getData().get(i).getImagePath());
                break;

            }
        }
        textReescore_BigMessage = findViewById(R.id.txt2);


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

    private void setAddingImage(String addingImageName, ImageView img_add_1, TextView textView, String message) {

        textView.setText(message);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.defaultmedicine)
                .error(R.drawable.defaultmedicine)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);
        if (addingImageName != null) {
            Glide.with(context)
                    .load(addingImageName)
                    .apply(options.circleCrop())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(img_add_1);
        }
    }

    private void setCylcerImage(String imagePath) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.defaultmedicine)
                .error(R.drawable.defaultmedicine)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);
//        if (imagePath != null) {
//            Glide.with(context)
//                    .load(imagePath)
//                    .apply(options.circleCrop())
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
//                                                    Target<Drawable> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
//                                                       DataSource dataSource, boolean isFirstResource) {
//                            return false;
//                        }
//                    })
//                    .into(img_cycle);
//        }
    }

    @SuppressLint("NewApi")
    private void showDatePickerDailogentry() {
        String strMindate[] = new SessionManager(ReescoreActivity.this).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_entry = new DatePickerDialog(ReescoreActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, ReescoreActivity.this, year, month, day);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog_entry.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();


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


    private void init() {
        service = Client.getClient().create(RescoreService.class);
        sessionManager = new SessionManager(context);
        utils = new Utils();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isFirstTime = bundle.getBoolean("isFirstTime");
        }
    }

    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        strText1 ="My Reescore?";
        spannableStringBuilder1 = new SpannableStringBuilder(strText1);
        SuperscriptSpan superscriptSpan1 = new SuperscriptSpan();

//        if(strText.equals("?")) {
        spannableStringBuilder1.setSpan(superscriptSpan1, strText1.indexOf("?"),
                strText1.indexOf("?") + ("?").length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        showSmallSizeText1("?");
//        }
        tvTitle.setText(spannableStringBuilder1);


        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context,R.style.CustomDialog);
                dialog.setContentView(R.layout.content_layout_reescore);
                txt_reescore=dialog.findViewById(R.id.txt_reescore);


                reescoreMianClassl.getData();
                for (int i = 0; i < reescoreMianClassl.getData().size(); i++) {

                    if (reescoreMianClassl.getData().get(i).getMessageType().equalsIgnoreCase("What is Reescore")){
                        txt_reescore.setText(reescoreMianClassl.getData().get(i).getMessage());
                        break;

                    }
                }

                ImageView close_rdp=dialog.findViewById(R.id.close_rdp);
                close_rdp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });




                dialog.show();
            }
        });


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
    private void showSmallSizeText1(String s) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(.5f);
        spannableStringBuilder1.setSpan(relativeSizeSpan, strText1.indexOf(s), strText1.indexOf(s) + (s).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    private void findViews() {
//        imgNext = findViewById(R.id.imgViewNext_Reescore);
//        imgNext.setOnClickListener(this);
        //  tvCurrentScore = findViewById(R.id.tvCurrentScore);
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

//        pbar_recent_reescore.getProgressDrawable().setColorFilter(
//                R.color.p2, android.graphics.PorterDuff.Mode.SRC_IN);
//        pbar_future_reescore.getProgressDrawable().setColorFilter(
//                R.color.p3, android.graphics.PorterDuff.Mode.SRC_IN);
//        pbar_first_reescore.setProgress(100);
//        pbar_recent_reescore.setMaxProgress(100);
//        pbar_future_reescore.setMaxProgress(100);


        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);

        if (Utils.isNetworkAvailable(context))
            CallToGetRescoreData();
        else
            showLayouts();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSubmit_MyProfiless:

                callInterpretationApi();

//                Intent intent = new Intent(context, InterpretationActivity.class);
//                intent.putExtra("RESCORE_LIST", mDataList);
//                intent .putExtra("isFirstTime", isFirstTime);
//                startActivity(intent);
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context)) {
                    showLayouts();
                    CallToGetRescoreData();
                } else
                    showLayouts();
                break;

            default:
        }
    }



    private void callInterpretationApi() {

        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }


        Call<ClsReescoreMain> call = service.GetInterpretationByReeWorkerIDAPI(userId,submitFromDate);
        call.enqueue(new Callback<ClsReescoreMain>() {
            @Override
            public void onResponse(Call<ClsReescoreMain> call, Response<ClsReescoreMain> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsReescoreMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        Intent intent = new Intent(context, InterpretationActivity.class);
                        intent.putExtra("RESCORE_LIST", mDataList);
                        intent.putExtra("isFirstTime", isFirstTime);
                        intent.putExtra("NEwInterpretation", listResponse.getData());
                        startActivity(intent);

                    } else
                        Toast.makeText(ReescoreActivity.this, "" + listResponse.getMessage(),
                                Toast.LENGTH_LONG).show();
                } else
                    ShowRetryBar("");
            }

            @Override
            public void onFailure(Call<ClsReescoreMain> call, Throwable t) {
                Log.e("ReescoreActivity", t.toString());
                Toast.makeText(ReescoreActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                utils.hideProgressbar();
                ShowRetryBar("");
            }
        });
    }

    private void CallToGetRescoreData() {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        RescoreRequest request = new RescoreRequest();
        request.setUserid(userId);
        request.setHealthdate(submitFromDate);

        Call<RescoreResponse> call = service.GetRescoreData(request);
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
//                               arcSeekBar.setProgress(Math.round(mCurrentScore));
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
                        Toast.makeText(ReescoreActivity.this, "" + listResponse.getMessage(),
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

//        imgNext.setVisibility(View.INVISIBLE);
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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//         dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1) + "-" + year;
//
//        submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;
//        txt_select_paramters_date.setText(dummydate_from);
//        CallToGetRescoreData();

    }

    public String getFromattedStringDate(int s) {


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

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
