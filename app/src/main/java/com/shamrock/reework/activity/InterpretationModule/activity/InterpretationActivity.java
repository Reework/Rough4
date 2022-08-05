package com.shamrock.reework.activity.InterpretationModule.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.shamrock.R;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.aNewInterpretation.model.ReescoreData;
import com.shamrock.reework.activity.aNewInterpretation.model.WellnessListAdapter;
import com.shamrock.reework.activity.aNewInterpretation.model.WellnessListAdapterNew;
import com.shamrock.reework.activity.mybcaplan.BcaPlanListAdapter;
import com.shamrock.reework.activity.mybcaplan.ClsSingleBCAPlanActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.RescoreResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterpretationActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    Context context;
    Toolbar toolbar;
    Typeface font;
    TextView imgNext;
    //    ProgressBar progressBar_BodyWeight, progressBar_BMI, progressBar_CalorieIntake, progressBar_Sleep;
//    TextView tvName, tvBodyWeightTotal, tvBodyWeightProgress, tvBmiTotal, tvBmiProgress,
//            tvCalorieIntakeTotal, tvCalorieIntakeProgress, tvSleepTotal, tvSleepProgress;
    RadioButton rbActivitySecondry, rbActivityActive, rbSmokeNo, rbSmokeYes, rbAcidic, rbAlkaine;
    Button btn_Back, btn_Home_Page;
    ArrayList<RescoreResponse.RescoreData> mDataList;
    TextView txt_user_newScore;
    int mActivitySecondry = 0, mActivityActive = 0, mSmokeNo = 0, mSmokeYes = 0, mAcidic = 0, mAlkaine = 0;
    //    TextView txt_activty_level;
//    TextView txt_smoke;
//    TextView txt_medical_state;
    boolean isFirstTime;
    TextView txt_rescore_name;
    Button buttonSubmit_MyProfiless;
    TextView txt_lable_2;
    RecyclerView recyler_wellness;
    ReescoreData NEwInterpretation_data;
    TextView txt_health_summery;
    ScrollView scroller_HS;

    DecoView arcView;
    int series1Index;
    SeriesItem seriesItem1;
    CircularProgressBar progress_circular_consumed;
    RelativeLayout lay_view;

    private int userID;
    private SessionManager sessionManager;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpretation);
        context = InterpretationActivity.this;
//        txt_lable_2 = findViewById(R.id.txt_lable_2);

        sessionManager = new SessionManager(context);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        txt_health_summery = findViewById(R.id.txt_health_summery);
        recyler_wellness = findViewById(R.id.recyler_wellness);
        NEwInterpretation_data = (ReescoreData) getIntent().getSerializableExtra("NEwInterpretation");

        lay_view = findViewById(R.id.lay_view);
        arcView = (DecoView)findViewById(R.id.dynamicArcView);
//        progress_circular_consumed =findViewById(R.id.progress_circular_consumed);
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(22f)
                .build());

        seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(22f)
                .build();

        series1Index = arcView.addSeries(seriesItem1);
        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(1000)
                .setDuration(2000)
                .build());

        init();

        findViews();

        setData(NEwInterpretation_data);
        lay_view.setVisibility(View.VISIBLE);
//        LinearLayout btnBar = (LinearLayout) findViewById(R.id.btnBar);
//        RelativeLayout.LayoutParams btnBarParams = new RelativeLayout.LayoutParams(500,500 / 3);
//        btnBarParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        btnBarParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        btnBar.setLayoutParams(btnBarParams);

//        Window window = getWindow();
//        WindowManager.LayoutParams winParams = window.getAttributes();
//        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        window.setAttributes(winParams);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


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

    private void setData(ReescoreData reescoreData) {
        if (reescoreData.getReeScore() != null) {
//            txt_user_newScore.setText(reescoreData.getReeScore());
            txt_user_newScore.setText(String.valueOf(Math.round(Float.parseFloat(reescoreData.getReeScore()))));
            arcView.addEvent(new DecoEvent.Builder((float) Float.parseFloat(reescoreData.getReeScore())).setIndex(series1Index).setDelay(10).build());

//            progress_circular_consumed.setProgress((float) Float.parseFloat(reescoreData.getReeScore()));
//            progress_circular_consumed.setProgressMax(100);
        }
        if (reescoreData.getHealthSummary() != null) {
            String lines[] = reescoreData.getHealthSummary().split("\\r?\\n");
            txt_health_summery.setText(lines[1] + lines[2]);
            txt_health_summery.setMovementMethod(new ScrollingMovementMethod());
        } else {
            txt_health_summery.setText("Health Summary : ");
            //txt_health_summery.setMovementMethod(new ScrollingMovementMethod());

        }


        if (reescoreData.getLabel1() != null) {

            String abc[] =reescoreData.getLabel1().split(" ");


            txt_rescore_name.setText(abc[3].replace("!",""));
        }
        if (reescoreData.getLabel2() != null) {
//            txt_lable_2.setText(reescoreData.getLabel2());
        }

        if (reescoreData.getWellnessParams() != null) {
            RecyclerView recyler_wellness = findViewById(R.id.recyler_wellness);
//            recyler_wellness.setAdapter(new WellnessListAdapter(context, reescoreData.getWellnessParams()));

//            recyler_wellness.setAdapter(new WellnessListAdapterNew(context, reescoreData.getWellnessParams()));

            recyler_wellness.setLayoutManager(
                    new LinearLayoutManager(context));
            WellnessListAdapterNew clsTodaysPlansAdapter=new WellnessListAdapterNew(context, reescoreData.getWellnessParams());
            recyler_wellness.setAdapter(clsTodaysPlansAdapter);


        }

    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                mDataList = (ArrayList<RescoreResponse.RescoreData>)
                        extras.getSerializable("RESCORE_LIST");
                isFirstTime = extras.getBoolean("isFirstTime");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


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
        tvTitle.setText(R.string.interpretation);





//        tvTitle.setTypeface(font);
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
        buttonSubmit_MyProfiless = findViewById(R.id.buttonSubmit_MyProfiless);
//        progressBar_BodyWeight = findViewById(R.id.progressBarBodyWeight);
//        progressBar_BMI = findViewById(R.id.progressBarBMI);
//        progressBar_CalorieIntake = findViewById(R.id.progressBarCalorieIntake);
//        progressBar_Sleep = findViewById(R.id.progressBarSleep);
//        txt_activty_level = findViewById(R.id.txt_activty_level);
//        txt_smoke = findViewById(R.id.txt_smoke);
//        txt_medical_state = findViewById(R.id.txt_medical_state);
//
//        tvName = findViewById(R.id.tvUser);

        SessionManager sessionManager = new SessionManager(InterpretationActivity.this);
        txt_rescore_name = findViewById(R.id.txt_rescore_name);
        txt_rescore_name.setText("Hi, " + sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME) + " !!!");
        txt_user_newScore = findViewById(R.id.txt_user_newScore);
//        tvBodyWeightTotal = findViewById(R.id.text_Interpretation_BodyWeight_Total);
//        tvBodyWeightProgress = findViewById(R.id.text_Interpretation_BodyWeight_Progress);
//        tvBmiTotal = findViewById(R.id.text_Interpretation_BMI_Total);
//        tvBmiProgress = findViewById(R.id.text_Interpretation_BMI_Progress);
//        tvCalorieIntakeTotal = findViewById(R.id.text_Interpretation_CalorieIntake_Total);
//        tvCalorieIntakeProgress = findViewById(R.id.text_Interpretation_CalorieIntake_Progress);
//        tvSleepTotal = findViewById(R.id.text_Interpretation_Sleep_Total);
//        tvSleepProgress = findViewById(R.id.text_Interpretation_Sleep_Progress);

//        rbActivitySecondry = findViewById(R.id.radioButtonSecondary);
//        rbActivityActive = findViewById(R.id.radioButtonActive);
//        rbSmokeNo = findViewById(R.id.radioButtonSmokeNo);
//        rbSmokeYes = findViewById(R.id.radioButtonSmokeYes);
//        rbAcidic = findViewById(R.id.radioButtonAcidic);
//        rbAlkaine = findViewById(R.id.radioButtonIncreaseAlkalineFood);
//
//        rbActivitySecondry.setOnCheckedChangeListener(this);
//        rbActivityActive.setOnCheckedChangeListener(this);
//        rbSmokeNo.setOnCheckedChangeListener(this);
//        rbSmokeYes.setOnCheckedChangeListener(this);
//        rbAcidic.setOnCheckedChangeListener(this);
//        rbAlkaine.setOnCheckedChangeListener(this);
        buttonSubmit_MyProfiless.setOnClickListener(this);

        if (mDataList != null) {

            try {
                //txt_user_newScore.setText(String.valueOf(mDataList.get(0).getReScore()));
                txt_user_newScore.setText(String.valueOf(Math.round(mDataList.get(0).getReScore())));
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (mDataList.get(0).getActivityLevel() != null) {

                switch (mDataList.get(0).getActivityLevel()) {

                    case 1:
//                        txt_activty_level.setText("Activity Level : " + "Sedentary");

                        break;
                    case 2:
//                        txt_activty_level.setText("Activity Level : " + "Mildly Active");

                        break;


                    case 3:
//                        txt_activty_level.setText("Activity Level : " + "Moderately Active");

                        break;


                    case 4:
//                        txt_activty_level.setText("Activity Level : " + "Highly Active");

                        break;

                }


            }

            if (mDataList.get(0).getMedicalState() != null) {
                if (!mDataList.get(0).getMedicalState().isEmpty()) {
//                    txt_medical_state.setText("Medical State : " + mDataList.get(0).getMedicalState());
                }
            }


            if (mDataList.get(0).getIsSmoke() != null) {

                if (mDataList.get(0).getIsSmoke()) {

//                    txt_smoke.setText("Smoke : " + "Yes");
                } else {
//                    txt_smoke.setText("Smoke : " + "No");

                }
            }


            //BMI
            if (!TextUtils.isEmpty(mDataList.get(0).getIdealBMI())) {
                double dIdealBmi = convertValueToInteger(mDataList.get(0).getIdealBMI());
                if (dIdealBmi > 0) {
//                    progressBar_BMI.setMax((int) dIdealBmi);
//                    tvBmiTotal.setText(dIdealBmi + "");
                }
            }
            if (!TextUtils.isEmpty(mDataList.get(0).getActualBMI())) {
                double iActualBmi = convertValueToInteger(mDataList.get(0).getActualBMI());
                if (iActualBmi > 0) {
//                    progressBar_BMI.setProgress((int) iActualBmi);
//                    tvBmiProgress.setText(iActualBmi + "");
                }
            }

            //Body Weight
            if (!TextUtils.isEmpty(mDataList.get(0).getIdealBodyWeight())) {
                double idealBodyWeight = convertValueToInteger(mDataList.get(0).getIdealBodyWeight());
                if (idealBodyWeight > 0) {
//                    progressBar_BodyWeight.setMax((int) idealBodyWeight);
//                    tvBodyWeightTotal.setText(idealBodyWeight + "");
                }
            }
            if (!TextUtils.isEmpty(mDataList.get(0).getActualBodyWeight())) {
                double actualBodyWeight = convertValueToInteger(mDataList.get(0).getActualBodyWeight());
                if (actualBodyWeight > 0) {
//                    progressBar_BodyWeight.setProgress((int) actualBodyWeight);
//                    tvBodyWeightProgress.setText(actualBodyWeight + "");
                }
            }

            //Calorie
            if (!TextUtils.isEmpty(mDataList.get(0).getIdealCalorieIntake())) {
                double calorieIntake = convertValueToInteger(mDataList.get(0).getIdealCalorieIntake());
                if (calorieIntake > 0) {
//                    progressBar_CalorieIntake.setMax((int) calorieIntake);
//                    tvCalorieIntakeTotal.setText(calorieIntake + "");
                }
            }
            if (!TextUtils.isEmpty(mDataList.get(0).getActuialCalorieIntake())) {
                double actuialCalorieIntake = convertValueToInteger(mDataList.get(0).getActuialCalorieIntake());
                if (actuialCalorieIntake > 0) {
//                    progressBar_CalorieIntake.setProgress((int) actuialCalorieIntake);
//                    tvCalorieIntakeProgress.setText(actuialCalorieIntake + "");
                }
            }

            //Sleep
            if (!TextUtils.isEmpty(mDataList.get(0).getIdealSleep())) {
                double idealSleep = convertValueToInteger(mDataList.get(0).getIdealSleep());
                if (idealSleep > 0) {
//                    progressBar_Sleep.setMax((int) idealSleep);
//                    tvSleepTotal.setText(idealSleep + "");
                }
            }
            if (!TextUtils.isEmpty(mDataList.get(0).getActualSleep())) {
                double actualSleep = convertValueToInteger(mDataList.get(0).getActualSleep());
                if (actualSleep > 0) {
//                    progressBar_Sleep.setProgress((int) actualSleep);
//                    tvSleepProgress.setText(actualSleep + "");
                }
            }
        }
    }

    public double convertValueToInteger(String value) {
        double iDouble = 0.0;

        if (!TextUtils.isEmpty(value)) {
            try {
                iDouble = Double.parseDouble(value);
                return iDouble;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iDouble;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSubmit_MyProfiless:

                Intent intent = new Intent(context, InterpretationViewMoreActivity.class);
                intent.putExtra("RESCORE_LIST", mDataList);
                intent.putExtra("isFirstTime", isFirstTime);
                intent.putExtra("NEWINTERPRETATIONDATA", NEwInterpretation_data);
                startActivity(intent);
                break;
            default:
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

//    private void buttonCheckChanged()
//    {
//        if (rbActivitySecondry.isChecked())
//        {
//            mActivitySecondry = 1;
//        }
//        else if (rbActivityActive.isChecked())
//        {
//            gender = 2;
//        }
//        else if (radioButton_Transgender.isChecked())
//        {
//            gender = 3;
//        }
//    }

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
