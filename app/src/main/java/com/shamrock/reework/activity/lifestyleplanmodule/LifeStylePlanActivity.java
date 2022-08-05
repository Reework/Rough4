package com.shamrock.reework.activity.lifestyleplanmodule;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.activity.MedicineModule.service.EatingRequest;
import com.shamrock.reework.activity.MyPlansModule.activity.MyPlansActivity;
import com.shamrock.reework.activity.MyPlansModule.adapter.MyPlanMasterAdapter;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.cheatplan.MyCheatPlanActivity;
import com.shamrock.reework.activity.dietplan.DietPlanActivity;
import com.shamrock.reework.activity.lifestyleplanmodule.adapters.WeeklyshotcutAdapter;
import com.shamrock.reework.activity.lifestyleplanmodule.pojo.ClsPostShotcutWeekly;
import com.shamrock.reework.activity.lifestyleplanmodule.pojo.ClsSuccess;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.MyPlanMastersResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.MasterActivty.ActivityResponse;
import com.shamrock.reework.model.MasterActivty.DeleteActivityRequest;
import com.shamrock.reework.util.ClsUpdateWeekDaysRequest;
import com.shamrock.reework.util.ClsWeeDaysResponse;
import com.shamrock.reework.util.DaysUtils;
import com.shamrock.reework.util.MultiSelectionSpinner;
import com.shamrock.reework.util.Utils;
import com.shamrock.reework.util.WeekMultiSelectionSpinner;
import com.yanzhenjie.wheel.OnWheelChangedListener;
import com.yanzhenjie.wheel.WheelView;
import com.yanzhenjie.wheel.adapters.AbstractWheelTextAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LifeStylePlanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnLifeStyleEdit,View.OnClickListener,WeekMultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private TextView tvTitle;
    private ImageView imgLeft;
    private ViewFlipper vp_lifestyleplan;
    private LinearLayout ll_holiday;
    private LinearLayout ll_week_off;
    private LinearLayout ll_normal_day;
    private RecyclerView lst_normal_day;
    private RadioButton rd_btn_holiday;
    private RadioButton rd_btn_weekly_off;
    private RadioButton rd_btn_normal_day;
    private ImageView imgRegistration;
    private TextView txt_lifestyleday;
    Context context;
    private TextView txt_add_shotcut_lifestyle;
    ArrayList<MyPlanMastersResponse.MasterData> spinnerList;;
    ImageView img_weeklyoff_edit;
    Utils utils;
    private static final String SEPARATOR = ",";
    NomalDayPlanAdapter madapter;
    LinearLayout ll_create_Activity;
    AlertDialog alertDialog;
    int durationInminu=0;
    int planID=0;
    int dayType=1;
    int hr_value = 0;
    Spinner spinnerCategory;

    int min_value = 0;
    RegistrationService regService;
    SessionManager sessionManager;
    Button rd_button_Improved;
    Button rd_button_Existing;
    boolean isExisting;
    boolean isWeeklyOff=false;

    private TextView txt_no_activity;
    WeekMultiSelectionSpinner spinnerAddWeek_Days;
    private List<Integer> indices;
    private String days;
    private String plan_type="Existing";
    boolean isImproved;
    boolean isFirstTime=true;
    private String ActivtyTypeMain="Existing";

    String isClicked="normal";
    Dialog dialog;

int strHr=0,strMin=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style_plan);
        context=LifeStylePlanActivity.this;
        txt_add_shotcut_lifestyle=findViewById(R.id.txt_add_shotcut_lifestyle);


        utils=new Utils();
        utils = new Utils();
        spinnerCategory = findViewById(R.id.spinner_MyPlan_Category);
        spinnerCategory.setOnItemSelectedListener(this);

        sessionManager = new SessionManager(context);
//        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);
        initView();
        addClickListener();
        getLifeStyleplanApi(1);
//        callMyPlanMasters();
        txt_add_shotcut_lifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayType=2;

                getAllShotcutLifeStyleplanApi(1);

            }
        });

    }

    private void addClickListener() {
        rd_btn_normal_day.setOnClickListener(this);
        rd_btn_weekly_off.setOnClickListener(this);
        rd_btn_holiday.setOnClickListener(this);
        imgRegistration.setOnClickListener(this);
    }

    private void initView() {
        rd_btn_normal_day=findViewById(R.id.rd_btn_normal_day);
        rd_btn_weekly_off=findViewById(R.id.rd_btn_weekly_off);
        rd_btn_holiday=findViewById(R.id.rd_btn_holiday);
        imgRegistration=findViewById(R.id.imgRegistration);
        txt_lifestyleday=findViewById(R.id.txt_lifestyleday);
        rd_button_Existing=findViewById(R.id.rd_button_Existing);
        rd_button_Improved=findViewById(R.id.rd_button_Improved);
        img_weeklyoff_edit=findViewById(R.id.img_weeklyoff_edit);
        spinnerAddWeek_Days=findViewById(R.id.spinnerAddweek_Days);
        String[] array = getResources().getStringArray(R.array.week_days);
        spinnerAddWeek_Days.setItems(array);
        spinnerAddWeek_Days.setSelection(new int[]{});
        spinnerAddWeek_Days.setListener(this);

        img_weeklyoff_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerAddWeek_Days.performClick();
            }
        });



        boolean isFromlifes=getIntent().getBooleanExtra("isFromlife",false);
        boolean isFrommyplan=getIntent().getBooleanExtra("isFrommyplan",false);



        if (isFromlifes){
            imgRegistration.setVisibility(View.GONE);
        }else {
            imgRegistration.setVisibility(View.VISIBLE);
        }

        if (isFrommyplan) {
            imgRegistration.setVisibility(View.GONE);
        }

        txt_no_activity=findViewById(R.id.txt_no_activity);
        ll_create_Activity=findViewById(R.id.ll_create_Activity);
        imgLeft=findViewById(R.id.imgLeft);
        tvTitle=findViewById(R.id.tvTitle);
        vp_lifestyleplan=findViewById(R.id.vp_lifestyleplan);
        ll_normal_day=findViewById(R.id.ll_normal_day);
        ll_week_off=findViewById(R.id.ll_week_off);
        ll_holiday=findViewById(R.id.ll_holiday);
        lst_normal_day=findViewById(R.id.lst_normal_day);
        tvTitle.setText("My Lifestyle ");
        vp_lifestyleplan.setDisplayedChild(0);
        rd_button_Improved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<Plan> plans=new ArrayList<>();
//                madapter = new NomalDayPlanAdapter(plans,LifeStylePlanActivity.this);
//                lst_normal_day.setAdapter(madapter);
                plan_type="Improved";
                img_weeklyoff_edit.setVisibility(View.GONE);
                ActivtyTypeMain="Improved";

                isImproved=true;
                vp_lifestyleplan.setVisibility(View.GONE);
                isExisting=false;
//                rd_btn_normal_day.performClick();

                if(isClicked.equals("normal")){
                    rd_btn_normal_day.performClick();

                }else if(isClicked.equals("weekly")){
                    rd_btn_weekly_off.performClick();

                }else if(isClicked.equals("holiday")){
                    rd_btn_holiday.performClick();

                }

                rd_button_Existing.setBackgroundColor(getResources().getColor(R.color.white_recipe));
                rd_button_Improved.setBackgroundColor(getResources().getColor(R.color.green_recipe));

                rd_button_Existing.setTextColor(getResources().getColor(R.color.black_recipe));
                rd_button_Improved.setTextColor(getResources().getColor(R.color.white_recipe));

            }
        });
        rd_button_Existing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImproved=false;
                ActivtyTypeMain="Existing";
                plan_type="Existing";
                vp_lifestyleplan.setVisibility(View.VISIBLE);

                isExisting=true;
//                rd_btn_normal_day.performClick();

                if(isClicked.equals("normal")){
                    rd_btn_normal_day.performClick();

                }else if(isClicked.equals("weekly")){
                    rd_btn_weekly_off.performClick();

                }else if(isClicked.equals("holiday")){
                    rd_btn_holiday.performClick();

                }

                rd_button_Existing.setBackgroundColor(getResources().getColor(R.color.green_recipe));
                rd_button_Improved.setBackgroundColor(getResources().getColor(R.color.white_recipe));

                rd_button_Existing.setTextColor(getResources().getColor(R.color.white_recipe));
                rd_button_Improved.setTextColor(getResources().getColor(R.color.black_recipe));



            }
        });

        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                startActivity(new Intent(context, LoginActivity.class));
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
        ll_create_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }
                Plan plan = new Plan();
showDialog(plan);


//                AddLisestyleDialog(plan);
            }
        });
    }
    private void shownoplan() {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe=dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no=dialog.findViewById(R.id.btn_subscribe_no);
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

                Intent  intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }

    private void AddLisestyleDialog(Plan plan) {
        try
        {
            int mYear;
            int mMonth;
            int mDay;
            int plan_id = 0;
            final int[] mHour = new int[1];
            final int[] mMinute = new int[1];

            final AlertDialog.Builder dilaog =
                    new AlertDialog.Builder(LifeStylePlanActivity.this, R.style.CustomDialogs);
            LayoutInflater layoutInflater = LayoutInflater.from(LifeStylePlanActivity.this);
            final View view = layoutInflater.inflate(R.layout.add_newlifestyleplain,null);
            dilaog.setView(view);
            alertDialog = dilaog.create();
            final  TextView text_time_acrtivty=view.findViewById(R.id.text_time_acrtivty);
            final TextView text_activity=view.findViewById(R.id.text_activity);
            final  ImageView hrs_dec=view.findViewById(R.id.hrs_dec);
            final  ImageView hrs_inc=view.findViewById(R.id.hrs_inc);
            final  ImageView mins_dec=view.findViewById(R.id.mins_dec);
            final  ImageView mins_imc=view.findViewById(R.id.mins_imc);
            final EditText hr_tv=view.findViewById(R.id.hr_tv);
            final  EditText min_tv=view.findViewById(R.id.min_tv);
            final Button but_add_activity=view.findViewById(R.id.but_add_activity);

            if (plan.getActivityTime()!=null){
                text_time_acrtivty.setText(plan.getActivityTime());
            }

            if (plan.getActivityName()!=null){
                text_activity.setText(plan.getActivityName());
            }
            if (plan.getId()!=null){
                planID= Integer.parseInt(plan.getId());

            }
            if (plan.getDurationInMinute()!=null){
                durationInminu= Integer.parseInt(plan.getDurationInMinute());

            }


            if (plan.getPlanType()!=null){
                plan_type= plan.getPlanType();

            }
            if (plan!=null){

                if (durationInminu>0){
                    int hoursstr=(durationInminu / 60);
                    hr_value=hoursstr;
                    String minutes = Integer.toString(durationInminu % 60);
                    minutes = minutes.length() == 1 ? "0" + minutes : minutes;
                    min_value= Integer.parseInt(minutes);
                    min_tv.setText(minutes);
                    hr_tv.setText(String.valueOf(hoursstr));
                }



            }

            final ImageView imageView_AddMedicine_CloseDialog=view.findViewById(R.id.imageView_AddMedicine_CloseDialog);

            imageView_AddMedicine_CloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            hrs_dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hr_tv.getText().toString().contentEquals("0")){
                        hr_tv.setText("0");
                    }else {
                        hr_value = hr_value-1;
                        hr_tv.setText(String.valueOf(hr_value));
                    }

                }
            });
            hrs_inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hr_value = hr_value +1;
                    hr_tv.setText(String.valueOf(hr_value));
                }
            });
            mins_dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(min_tv.getText().toString().contentEquals("0")){
                        min_tv.setText("0");
                    }else {
                        min_value = min_value-1;
                        min_tv.setText(String.valueOf(min_value));
                    }

                }
            });
            mins_imc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(min_tv.getText().toString().contentEquals("60")){
                        min_tv.setText("60");
                    }else {
                        min_value = min_value+1;
                        min_tv.setText(String.valueOf(min_value));
                    }
                }
            });
            text_time_acrtivty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar c = Calendar.getInstance();
                    mHour[0] = c.get(Calendar.HOUR_OF_DAY);
                    mMinute[0] = c.get(Calendar.MINUTE);
                    final String[] timeFormat = new String[1];
                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(LifeStylePlanActivity.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    if (hourOfDay == 0) {
                                        hourOfDay += 12;
                                        timeFormat[0] = "AM";
                                    } else if (hourOfDay == 12) {
                                        timeFormat[0] = "PM";
                                    } else if (hourOfDay > 12) {
                                        hourOfDay -= 12;
                                        timeFormat[0] = "PM";
                                    } else {
                                        timeFormat[0] = "AM";
                                    }

                                    if (minute<10){
                                        String selectedTime = hourOfDay + ":0" + minute + " " + timeFormat[0];
                                        text_time_acrtivty.setText(selectedTime);
                                    }else {
                                        String selectedTime = hourOfDay + ":" + minute + " " + timeFormat[0];
                                        text_time_acrtivty.setText(selectedTime);
                                    }




                                }
                            }, mHour[0], mMinute[0], false);
                    timePickerDialog.show();
                }
            });

            but_add_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    if (hr_tv.getText().toString().isEmpty()){
                        hr_tv.setText("0");
                    }
                    if (min_tv.getText().toString().isEmpty()){
                        min_tv.setText("0");
                    }

                    durationInminu=(60*Integer.parseInt(hr_tv.getText().toString()))+Integer.parseInt(min_tv.getText().toString());


                    if (text_time_acrtivty.getText().toString().isEmpty()){
                        Toast.makeText(LifeStylePlanActivity.this, "Select activity time.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(text_time_acrtivty.getText().toString())||TextUtils.isEmpty(text_activity.getText().toString()))
                    {
                        Toast.makeText(LifeStylePlanActivity.this, "Please enter activity name.", Toast.LENGTH_SHORT).show();

                        return;

                    }
                    if (durationInminu<1){
                        Toast.makeText(LifeStylePlanActivity.this, "Enter activity duration.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    else {
                        hr_tv.setText("");
                        min_tv.setText("");



                        LifeStylePlanPost lifeStylePlanPost=new LifeStylePlanPost(planID,Integer.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)),text_activity.getText().toString(),text_time_acrtivty.getText().toString(),durationInminu,"",dayType, plan_type);
                        insertLifeStyleplanApi(lifeStylePlanPost);
                        durationInminu=0;
                    }


                }
            });
            dilaog.setCancelable(true);
            alertDialog.setCancelable(true);
            alertDialog.show();
        }catch (Exception e)
        {
//            alertDialog.dismiss();
            e.getMessage();
        }

    }

    private void showSuccessDailog(String  message) {


        final Dialog dialog=new Dialog(LifeStylePlanActivity.this);
        dialog.setContentView(R.layout.dialog_sucess);
        dialog.setCancelable(false);
        Button btn_ok=dialog.findViewById(R.id.btn_ok);
        TextView txt_msg=dialog.findViewById(R.id.txt_msg);
        txt_msg.setText(message);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();



//                sessionManager.setIntValue(SessionManager.KEY_USER_ID, message.getData().getUserid());
////                        startActivity(new Intent(NewRegisterActivity.this,joinNowActivity.class));
//                Intent intent=new Intent(LifeStylePlanActivity.this, HomeActivity.class);
////                intent.putExtra("RegistrationaData",request);
//                startActivity(intent);
                clearTask();

            }
        });
        dialog.show();
    }


    public void clearTask()
    {


        if (getIntent().getStringExtra("FromWeb")!=null&&getIntent().getStringExtra("FromWeb").equalsIgnoreCase("true")){
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();

        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.rd_btn_normal_day:
                dayType=1;
                txt_add_shotcut_lifestyle.setVisibility(View.GONE);
                img_weeklyoff_edit.setVisibility(View.INVISIBLE);
                isClicked="normal";
                txt_lifestyleday.setText("Normal Day");
                getLifeStyleplanApi(1);




                rd_btn_normal_day.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rd_btn_holiday.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_btn_weekly_off.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_btn_normal_day.setTextColor(getResources().getColor(R.color.white));
                rd_btn_holiday.setTextColor(getResources().getColor(R.color.black));
                rd_btn_weekly_off.setTextColor(getResources().getColor(R.color.black));




                break;


            case R.id.imgRegistration:

                utils.showProgressbar(LifeStylePlanActivity.this);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        utils.hideProgressbar();
                        showSuccessDailog(sessionManager.getStringValue("regmsg"));
                    }
                },2000);

//                RegistrationRequest registrationRequest= (RegistrationRequest) getIntent().getSerializableExtra("UserDataReg");
//                callregistrationapi(registrationRequest);

                break;

            case R.id.rd_btn_weekly_off:
                dayType=2;
                txt_add_shotcut_lifestyle.setVisibility(View.VISIBLE);


                img_weeklyoff_edit.setVisibility(View.VISIBLE);

                isClicked="weekly";
                if (!sessionManager.getStringValue("KEY_WEEk_DAYS").isEmpty()){

                    txt_lifestyleday.setText(sessionManager.getStringValue("KEY_WEEk_DAYS"));

                }else {
                    txt_lifestyleday.setText("Weekly-off");

                }

//                if (plan_type.equalsIgnoreCase("Improved")){
//                    txt_lifestyleday.setText(sessionManager.getStringValue("KEY_WEEk_DAYS"));

//                }


                getLifeStyleplanApi(2);

                rd_btn_normal_day.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_btn_holiday.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_btn_weekly_off.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));

                rd_btn_normal_day.setTextColor(getResources().getColor(R.color.black));
                rd_btn_holiday.setTextColor(getResources().getColor(R.color.black));
                rd_btn_weekly_off.setTextColor(getResources().getColor(R.color.white));



                break;

            case R.id.rd_btn_holiday:
                dayType=3;
                txt_add_shotcut_lifestyle.setVisibility(View.GONE);
                isClicked="holiday";
                img_weeklyoff_edit.setVisibility(View.INVISIBLE);
                txt_lifestyleday.setText("Holiday");

                getLifeStyleplanApi(3);

                rd_btn_normal_day.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rd_btn_holiday.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rd_btn_weekly_off.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rd_btn_normal_day.setTextColor(getResources().getColor(R.color.black));
                rd_btn_holiday.setTextColor(getResources().getColor(R.color.white));
                rd_btn_weekly_off.setTextColor(getResources().getColor(R.color.black));



                break;

        }

    }

    private void getLifeStyleplanApi(final int daytype)
    {
//        if (isImproved){
//            madapter = new NomalDayPlanAdapter(new ArrayList<Plan>(),LifeStylePlanActivity.this);
//            lst_normal_day.setAdapter(madapter);
//            return;
//
//        }

        LifeStylePlanService  regService = Client.getClient().create(LifeStylePlanService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }


        Call<ClsMainLifeStylePlanData> call = regService.getLifeStylePlan(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)), String.valueOf(dayType),ActivtyTypeMain);
        call.enqueue(new Callback<ClsMainLifeStylePlanData>()
        {
            @Override
            public void onResponse(Call<ClsMainLifeStylePlanData> call, Response<ClsMainLifeStylePlanData> response)
            {
                utils.hideProgressbar();
//                lst_normal_day.setHasFixedSize(true);
//                lst_normal_day.setLayoutManager(new LinearLayoutManager(LifeStylePlanActivity.this));


                if (response.body()!=null){
                    try {
                        if (response.body().getData() != null) {

                            if (response.body().getData().getWeek().getDays() != null) {
                                if (!response.body().getData().getWeek().getDays().isEmpty()) {
                                    sessionManager.setStringValue("WEEk_ID", response.body().getData().getWeek().getId());
                                    sessionManager.setStringValue("KEY_WEEk_DAYS", "Weekly-off " + "(" + response.body().getData().getWeek().getDays() + ")");

                                    if (daytype==2){
                                        txt_lifestyleday.setText("Weekly-off " + "(" + response.body().getData().getWeek().getDays() + ")");
                                    }
                                }
                            }


                            if (!sessionManager.getStringValue("WeekSeletion").isEmpty()){
                                spinnerAddWeek_Days.setSelection(DaysUtils.getIntArray(sessionManager.getStringValue("WeekSeletion"), ","));

                            }





                            if (!response.body().getData().getPlan().isEmpty()) {
                                txt_no_activity.setVisibility(View.GONE);
                                vp_lifestyleplan.setVisibility(View.VISIBLE);
                            } else {
                                txt_no_activity.setVisibility(View.VISIBLE);
                                vp_lifestyleplan.setVisibility(View.GONE);
                            }
                            madapter = new NomalDayPlanAdapter(response.body().getData().getPlan(), LifeStylePlanActivity.this);

                            RecyclerView.LayoutManager manager = new LinearLayoutManager(LifeStylePlanActivity.this);
                            lst_normal_day.setLayoutManager(manager);
                            lst_normal_day.setItemAnimator(new DefaultItemAnimator());

                            lst_normal_day.setAdapter(madapter);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ClsMainLifeStylePlanData> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });

    }


    private void getAllShotcutLifeStyleplanApi(final int daytype)
    {
//        if (isImproved){
//            madapter = new NomalDayPlanAdapter(new ArrayList<Plan>(),LifeStylePlanActivity.this);
//            lst_normal_day.setAdapter(madapter);
//            return;
//
//        }

        LifeStylePlanService  regService = Client.getClient().create(LifeStylePlanService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        Call<ClsMainLifeStylePlanData> call = regService.getLifeStylePlan(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)), String.valueOf(daytype),ActivtyTypeMain);
        call.enqueue(new Callback<ClsMainLifeStylePlanData>()
        {
            @Override
            public void onResponse(Call<ClsMainLifeStylePlanData> call, Response<ClsMainLifeStylePlanData> response)
            {
                utils.hideProgressbar();
//                lst_normal_day.setHasFixedSize(true);
//                lst_normal_day.setLayoutManager(new LinearLayoutManager(LifeStylePlanActivity.this));


                if (response.body()!=null){
                    try {
                        if (response.body().getData() != null) {

                            if (response.body().getData().getWeek().getDays() != null) {
                                if (!response.body().getData().getWeek().getDays().isEmpty()) {
                                }
                            }




                            showAllListInDailog(response.body().getData().getPlan());


                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ClsMainLifeStylePlanData> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });

    }

    private void showAllListInDailog(final ArrayList<Plan> plan) {
        if (plan!=null){
            final Dialog dialog=new Dialog(LifeStylePlanActivity.this,R.style.CustomDialog);
            dialog.setContentView(R.layout.lay_dailog_select_lifestyleplan);
            ListView reclyer_select_lifesyle_shotcut=dialog.findViewById(R.id.reclyer_select_lifesyle_shotcut);
            WeeklyshotcutAdapter shotcutWeeklyAdapter=new WeeklyshotcutAdapter(context,plan);
            ImageView img_close_wekly=dialog.findViewById(R.id.img_close_wekly);
            img_close_wekly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            reclyer_select_lifesyle_shotcut.setAdapter(shotcutWeeklyAdapter);
            Button btn_add_shotcut_weekly=dialog.findViewById(R.id.btn_add_shotcut_weekly);
            btn_add_shotcut_weekly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<ClsPostShotcutWeekly> plan_new=new ArrayList<>();
                    for (int i = 0; i < plan.size(); i++) {
                        if (plan.get(i).isAdded()){
                            plan_new.add(new ClsPostShotcutWeekly(plan.get(i).getPlanType(),plan.get(i).getActivityName(),plan.get(i).getActivityTime(),"",2,Integer.parseInt(plan.get(i).getDurationInMinute()),0, new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)));
                        }
                    }

                    dialog.dismiss();

                    if (plan_new.isEmpty()){
                        Toast.makeText(context, "Please select activity", Toast.LENGTH_SHORT).show();

                    }else {
                        insertLifeStyleplanApiWeeklynew(plan_new);

                    }

                }
            });

            dialog.show();
        }


    }

    private void insertLifeStyleplanApi(LifeStylePlanPost lifeStylePlanPost)
    {
        LifeStylePlanService  regService = Client.getClient().create(LifeStylePlanService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        Call<LifeStylePlanPostsucces> call = regService.insertLifeStylePlan(lifeStylePlanPost);
        call.enqueue(new Callback<LifeStylePlanPostsucces>()
        {
            @Override
            public void onResponse(Call<LifeStylePlanPostsucces> call, Response<LifeStylePlanPostsucces> response)
            {

                hr_value = 0;
                min_value = 0;
                utils.hideProgressbar();
                dialog.dismiss();


                if (response.code() == Client.RESPONSE_CODE_OK) {
                    LifeStylePlanPostsucces listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {

                        if (listResponse.getMessage()!=null){
                            Toast.makeText(LifeStylePlanActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }



                    }
                } else {
                    utils.hideProgressbar();
                }

                getLifeStyleplanApi(dayType);
            }

            @Override
            public void onFailure(Call<LifeStylePlanPostsucces> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }



    private void insertLifeStyleplanApiWeeklynew(ArrayList<ClsPostShotcutWeekly> lifeStylePlanPost)
    {


        LifeStylePlanService  regService = Client.getClient().create(LifeStylePlanService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        Call<ClsSuccess> call = regService.insertLifeStylePlanShoutcut(lifeStylePlanPost);
        call.enqueue(new Callback<ClsSuccess>()
        {
            @Override
            public void onResponse(Call<ClsSuccess> call, Response<ClsSuccess> response)
            {


                utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsSuccess listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        if (listResponse.getMessage()!=null){
                            Toast.makeText(LifeStylePlanActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }



                    }
                } else {
                    utils.hideProgressbar();
                }

                getLifeStyleplanApi(dayType);
            }

            @Override
            public void onFailure(Call<ClsSuccess> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }


    private void updateWeekDays(ClsUpdateWeekDaysRequest lifeStylePlanPost)
    {
        utils=new Utils();
        LifeStylePlanService  regService = Client.getClient().create(LifeStylePlanService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        Call<ClsWeeDaysResponse> call = regService.UpdateWeekDays(lifeStylePlanPost);
        call.enqueue(new Callback<ClsWeeDaysResponse>()
        {
            @Override
            public void onResponse(Call<ClsWeeDaysResponse> call, Response<ClsWeeDaysResponse> response)
            {
                utils.hideProgressbar();


                ClsWeeDaysResponse clsWeeDaysResponse=response.body();
                Toast.makeText(LifeStylePlanActivity.this, ""+clsWeeDaysResponse.getMessage(), Toast.LENGTH_SHORT).show();
                sessionManager.setStringValue("WEEk_ID",clsWeeDaysResponse.getData().getId());
                sessionManager.setStringValue("KEY_WEEk_DAYS","Weekly-off "+"("+clsWeeDaysResponse.getData().getDays()+")");
                txt_lifestyleday.setText(sessionManager.getStringValue("KEY_WEEk_DAYS"));
            }

            @Override
            public void onFailure(Call<ClsWeeDaysResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }


    @Override
    public void selectedIndices(List<Integer> indices) {
        this.indices = indices;

        int weekID;
        if (indices.size() > 0) {
            StringBuilder strIds = new StringBuilder();
            StringBuilder edt_selection = new StringBuilder();
            for (int y : indices) {
                if (y==0){
                    strIds.append("Sun");
                    strIds.append(SEPARATOR);
                    edt_selection.append("1,");
                }
                if (y==1){
                    strIds.append("Mon");
                    strIds.append(SEPARATOR);
                    edt_selection.append("2,");

                }

                if (y==2){
                    strIds.append("Tue");
                    strIds.append(SEPARATOR);
                    edt_selection.append("3,");

                }


                if (y==3){
                    strIds.append("Wed");
                    strIds.append(SEPARATOR);
                    edt_selection.append("4,");

                }
                if (y==4){
                    strIds.append("Thu");
                    strIds.append(SEPARATOR);
                    edt_selection.append("5,");

                }


                if (y==5){
                    strIds.append("Fri");
                    strIds.append(SEPARATOR);
                    edt_selection.append("6,");

                }
                if (y==6){
                    strIds.append("Sat");
                    strIds.append(SEPARATOR);
                    edt_selection.append("7,");

                }
                String  sss = String.valueOf(edt_selection.substring(0, edt_selection.length() - 1));
                sessionManager.setStringValue("WeekSeletion",sss);




            }


            try {
//                if (indices.size()>2){
//
//                }else {
//                    days = String.valueOf(strIds.substring(0, strIds.length() - 1));
//
//                }
                days = String.valueOf(strIds.substring(0, strIds.length() - 1));





//                days = String.valueOf(strIds.substring(0, strIds.length() - 1));    //remove last comma and return commaSeparatedIds
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            days = "";
            return;

        }



        ClsUpdateWeekDaysRequest clsUpdateWeekDaysRequest=new ClsUpdateWeekDaysRequest();
        clsUpdateWeekDaysRequest.setDays(days);
        clsUpdateWeekDaysRequest.setWeekType(String.valueOf(dayType));
        clsUpdateWeekDaysRequest.setReeworkerId(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID));
        if (!sessionManager.getStringValue("WEEk_ID").isEmpty()){
            clsUpdateWeekDaysRequest.setId(Integer.parseInt(sessionManager.getStringValue("WEEk_ID")));

        }else {
            clsUpdateWeekDaysRequest.setId(0);

        }
        updateWeekDays(clsUpdateWeekDaysRequest);

    }

    @Override
    public void selectedStrings(List<String> strings) {

    }

    @Override
    public void getEditData(Plan plan) {

        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }

//        AddLisestyleDialog(plan);
        showDialog(plan);


    }
    private void callDeletActivityApi( int id) {


        MyPlansService myPlansService = Client.getClient().create(MyPlansService.class);

        Call<ActivityResponse> call = myPlansService.getDeleteLifestyelPlan(id);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        getLifeStyleplanApi(dayType);



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
    public void getDeleteData(int id) {
        callDeletActivityApi(id);
    }

    private void callMyPlanMasters()
    {
//        utils=new Utils();
//        if (!((Activity) mContext).isFinishing())
//        {
//            utils.showProgressbar(mContext);
//        }
        sessionManager=new SessionManager(context);
        int   userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        MyPlansService myPlansService = Client.getClient().create(MyPlansService.class);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<MyPlanMastersResponse> call = myPlansService.getMasterPlans(request);
        call.enqueue(new Callback<MyPlanMastersResponse>()
        {
            @Override
            public void onResponse(Call<MyPlanMastersResponse> call, Response<MyPlanMastersResponse> response)
            {
//                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyPlanMastersResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {





                        spinnerList = response.body().getData();

                        MyPlanMastersResponse.MasterData data = new MyPlanMastersResponse.MasterData();
                        data.setPlanName("Today's Plan");
                        data.setID(0);
                        spinnerList.add(0, data);
                        MyPlanMasterAdapter spinnerAdapter;


                        spinnerAdapter = new MyPlanMasterAdapter(context, spinnerList);
                        spinnerCategory.setAdapter(spinnerAdapter);

                        for (int i = 0; i <spinnerList.size() ; i++) {
                            if (spinnerList.get(i).getPlanName().toString().trim().equalsIgnoreCase("Lifestyle Plan")){
                                spinnerCategory.setSelection(i);
                                break;
                            }

                        }


                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyPlanMastersResponse> call, Throwable t)
            {
//                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        if (spinnerList!= null) {

            String planType = spinnerList.get(i).getPlanName();
            int planId = spinnerList.get(i).getID();


            if (planType.trim().equalsIgnoreCase("Food Plan")) {
                startActivity(new Intent(LifeStylePlanActivity.this, DietPlanActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return;
            }



            if (planType.trim().equalsIgnoreCase("REEplace Items")) {
                Intent intent = new Intent(LifeStylePlanActivity.this, MyCheatPlanActivity.class);
                intent.putExtra("isFrommyplan", true);

                startActivity(intent);


                overridePendingTransition(0, 0);
                finish();
                return;
            }
            if (planType.trim().equalsIgnoreCase("Today's Plan")) {
                Intent intent = new Intent(LifeStylePlanActivity.this, MyPlansActivity.class);
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



    private void showDialog(Plan plan) {

        int mYear;
        int mMonth;
        int mDay;
        int plan_id = 0;
        final int[] mHour = new int[1];
        final int[] mMinute = new int[1];

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog_lifestyle);

//        isAdd = true;

//        edt_Medicine = dialog.findViewById(R.id.edtText_NameMedicine);

        final  TextView text_time_acrtivty=dialog.findViewById(R.id.text_time_acrtivty);
        final AutoCompleteTextView text_activity=dialog.findViewById(R.id.text_activity);
//        final  ImageView hrs_dec=dialog.findViewById(R.id.hrs_dec);
//        final  ImageView hrs_inc=dialog.findViewById(R.id.hrs_inc);
//        final  ImageView mins_dec=dialog.findViewById(R.id.mins_dec);
//        final  ImageView mins_imc=dialog.findViewById(R.id.mins_imc);
        final  TextView txt_header=dialog.findViewById(R.id.txt_header);

        WheelView wheal_list_uom=dialog.findViewById(R.id.wheal_list_uom);
        WheelView wheal_list_min=dialog.findViewById(R.id.wheal_list_min);

        final EditText hr_tv=dialog.findViewById(R.id.hr_tv);
        final  EditText min_tv=dialog.findViewById(R.id.min_tv);
        final Button but_add_activity=dialog.findViewById(R.id.but_add_activity);


        final String[] str_qnty;

        str_qnty = context.getResources().getStringArray(R.array.activity_hr);
        final ArrayList<String> str_qnty1=new ArrayList<>(Arrays.asList(str_qnty));
//        str_qnty1.add(Arrays.asList(str_qnty));
        AbstractWheelTextAdapter abstractWheelTextAdapter=new AbstractWheelTextAdapter(context) {
            @Override
            protected CharSequence getItemText(int index) {
                for (int i = 0; i <str_qnty1.size() ; i++) {
                    return str_qnty1.get(index);

                }
                return "";            }

            @Override
            public int getItemsCount() {
                return (str_qnty1.size());
            }
        };



        abstractWheelTextAdapter.setItemResource(R.layout.layout_weel_black);
        abstractWheelTextAdapter.setItemTextResource(R.id.txt_wheel_quantity);
        wheal_list_uom.setAdapter(abstractWheelTextAdapter);

        int[] colors = {Color.WHITE, Color.TRANSPARENT};

        Drawable centerFilter = context.getResources().getDrawable(R.drawable.bg_border_grey);
        wheal_list_uom.setCenterFilter(centerFilter);

        GradientDrawable topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        wheal_list_uom.setTopShadow(topShadow);

        GradientDrawable bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors);
        wheal_list_uom.setBottomShadow(bottomShadow);



        wheal_list_uom.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {


//                Toast.makeText(context,oldValue+ " "+newValue,Toast.LENGTH_LONG).show();

                strHr = newValue;


            }
        });

        final String[] str_qntymin;

        str_qntymin = context.getResources().getStringArray(R.array.activity_min);
        final ArrayList<String> str_qntymin1=new ArrayList<>(Arrays.asList(str_qntymin));
//        str_qnty1.add(Arrays.asList(str_qnty));
        AbstractWheelTextAdapter abstractWheelTextAdapter1=new AbstractWheelTextAdapter(context) {
            @Override
            protected CharSequence getItemText(int index) {
                for (int i = 0; i <str_qntymin1.size() ; i++) {
                    return str_qntymin1.get(index);

                }
                return "";            }

            @Override
            public int getItemsCount() {
                return (str_qntymin1.size());
            }
        };

        abstractWheelTextAdapter1.setItemResource(R.layout.layout_weel_black);
        abstractWheelTextAdapter1.setItemTextResource(R.id.txt_wheel_quantity);
//        abstractWheelTextAdapter1.setEmptyItemResource(R.layout.layout_weel_gray);
        wheal_list_min.setAdapter(abstractWheelTextAdapter1);
        int[] colors1 = {Color.WHITE, Color.TRANSPARENT};

        Drawable centerFilter1 = context.getResources().getDrawable(R.drawable.bg_border_grey);
        wheal_list_min.setCenterFilter(centerFilter1);

        GradientDrawable topShadow1 = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        wheal_list_min.setTopShadow(topShadow1);

        GradientDrawable bottomShadow1 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors);
        wheal_list_min.setBottomShadow(bottomShadow1);

        wheal_list_min.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {


//                Toast.makeText(context,oldValue+ " "+newValue,Toast.LENGTH_LONG).show();

                strMin = newValue;


            }
        });








        if (plan.getActivityTime()!=null){
            text_time_acrtivty.setText(plan.getActivityTime());
        }

        if (plan.getActivityName()!=null){
            text_activity.setText(plan.getActivityName());
            but_add_activity.setText("Update");
            txt_header.setText("Update Activity");
        }
        if (plan.getId()!=null){
            planID= Integer.parseInt(plan.getId());

        }
        if (plan.getDurationInMinute()!=null){
            durationInminu= Integer.parseInt(plan.getDurationInMinute());

        }


        if (plan.getPlanType()!=null){
            plan_type= plan.getPlanType();

        }
        if (plan!=null){

            if (durationInminu>0){
                int hoursstr=(durationInminu / 60);
                hr_value=hoursstr;
                String minutes = Integer.toString(durationInminu % 60);
                minutes = minutes.length() == 1 ? "0" + minutes : minutes;
                min_value= Integer.parseInt(minutes);
//                min_tv.setText(minutes);
//                hr_tv.setText(String.valueOf(hoursstr));

                wheal_list_uom.setCurrentItem(hoursstr);
                wheal_list_min.setCurrentItem(min_value);
                strHr = hoursstr;
                strMin = min_value;

            }



        }

        final TextView txt_close=dialog.findViewById(R.id.txt_close);

        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        hrs_dec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(hr_tv.getText().toString().contentEquals("0")){
//                    hr_tv.setText("0");
//                }else {
//                    hr_value = hr_value-1;
//                    hr_tv.setText(String.valueOf(hr_value));
//                }
//
//            }
//        });
//        hrs_inc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                hr_value = hr_value +1;
//                hr_tv.setText(String.valueOf(hr_value));
//            }
//        });
//        mins_dec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(min_tv.getText().toString().contentEquals("0")){
//                    min_tv.setText("0");
//                }else {
//                    min_value = min_value-1;
//                    min_tv.setText(String.valueOf(min_value));
//                }
//
//            }
//        });
//        mins_imc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(min_tv.getText().toString().contentEquals("60")){
//                    min_tv.setText("60");
//                }else {
//                    min_value = min_value+1;
//                    min_tv.setText(String.valueOf(min_value));
//                }
//            }
//        });
        text_time_acrtivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour[0] = c.get(Calendar.HOUR_OF_DAY);
                mMinute[0] = c.get(Calendar.MINUTE);
                final String[] timeFormat = new String[1];
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(LifeStylePlanActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    timeFormat[0] = "AM";
                                } else if (hourOfDay == 12) {
                                    timeFormat[0] = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    timeFormat[0] = "PM";
                                } else {
                                    timeFormat[0] = "AM";
                                }

                                if (minute<10){
                                    String selectedTime = hourOfDay + ":0" + minute + " " + timeFormat[0];
                                    text_time_acrtivty.setText(selectedTime);
                                }else {
                                    String selectedTime = hourOfDay + ":" + minute + " " + timeFormat[0];
                                    text_time_acrtivty.setText(selectedTime);
                                }




                            }
                        }, mHour[0], mMinute[0], false);
                timePickerDialog.show();
            }
        });

        but_add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




//                if (hr_tv.getText().toString().isEmpty()){
//                    hr_tv.setText("0");
//                }
//                if (min_tv.getText().toString().isEmpty()){
//                    min_tv.setText("0");
//                }

                durationInminu=(60*(strHr)+(strMin));


                if (text_time_acrtivty.getText().toString().isEmpty()){
                    Toast.makeText(LifeStylePlanActivity.this, "Select activity time.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(text_time_acrtivty.getText().toString())||TextUtils.isEmpty(text_activity.getText().toString()))
                {
                    Toast.makeText(LifeStylePlanActivity.this, "Please enter activity name.", Toast.LENGTH_SHORT).show();

                    return;

                }
                if (durationInminu<1){
                    Toast.makeText(LifeStylePlanActivity.this, "Enter activity duration.", Toast.LENGTH_SHORT).show();
                    return;
                }


                else {
//                    hr_tv.setText("");
//                    min_tv.setText("");



                    LifeStylePlanPost lifeStylePlanPost=new LifeStylePlanPost(planID,Integer.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)),text_activity.getText().toString(),text_time_acrtivty.getText().toString(),durationInminu,"",dayType, plan_type);
                    insertLifeStyleplanApi(lifeStylePlanPost);
                    durationInminu=0;
                }


            }
        });






        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }








}
