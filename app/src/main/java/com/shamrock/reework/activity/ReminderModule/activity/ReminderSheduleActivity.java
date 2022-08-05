package com.shamrock.reework.activity.ReminderModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.adapter.OtherReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsGetotherreportmain;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.ReminderModule.model.ClsReminderMaster;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.ReminderModule.service.RemindersService;
import com.shamrock.reework.api.request.ScheduleReminderRequest;
import com.shamrock.reework.api.response.ReminderScheduleResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderSheduleActivity extends AppCompatActivity implements View.OnClickListener,
                                                                            DatePickerDialog.OnDateSetListener,
                                                                            TimePickerDialog.OnTimeSetListener {
    Context context;
    Toolbar toolbar;
    TextView tvTime, tvStartDate, tvEndDate;
    EditText etActivity;
    Button btnSubmit;

    DatePickerDialog startDatePicker;
    TimePickerDialog timepickerdialog;
    Utils utils;
    RemindersService remindersService;
    Spinner spinnerFreq;
    SessionManager sessionManager;
    Spinner spinner_activity_type;

    LinearLayout linTime2, linTime3, linTime4, linTime5, linTime6, linTime7, linTime8, linTime9, linTime10;
    TextView tvTime2, tvTime3, tvTime4, tvTime5, tvTime6, tvTime7, tvTime8, tvTime9, tvTime10;


    private String lsStartDate = "", lsEndDate = "", lsTime = "", lsActivity = "", errorMsg = "";
    String lsTime2 = "", lsTime3 = "", lsTime4 = "", lsTime5 = "", lsTime6 = "", lsTime7 = "", lsTime8 = "", lsTime9 = "", lsTime10 = "";
    private boolean isEndDate = false;
    String isWhichTimeSlected = "";
    int frequency = 0, liUserId = 0;
    private ReeTestService reeTestService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_shedule);
        context = ReminderSheduleActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init() {
        utils = new Utils();
        remindersService = Client.getClient().create(RemindersService.class);
        sessionManager = new SessionManager(context);

        liUserId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        spinner_activity_type = findViewById(R.id.spinner_activity_type);
        linTime2 = findViewById(R.id.linTime2);
        linTime3 = findViewById(R.id.linTime3);
        linTime4 = findViewById(R.id.linTime4);
        linTime5 = findViewById(R.id.linTime5);
        linTime6 = findViewById(R.id.linTime6);
        linTime7 = findViewById(R.id.linTime7);
        linTime8 = findViewById(R.id.linTime8);
        linTime9 = findViewById(R.id.linTime9);
        linTime10 = findViewById(R.id.linTime10);

        tvTime2 = findViewById(R.id.tvTime2);
        tvTime3 = findViewById(R.id.tvTime3);
        tvTime4 = findViewById(R.id.tvTime4);
        tvTime5 = findViewById(R.id.tvTime5);
        tvTime6 = findViewById(R.id.tvTime6);
        tvTime7 = findViewById(R.id.tvTime7);
        tvTime8 = findViewById(R.id.tvTime8);
        tvTime9 = findViewById(R.id.tvTime9);
        tvTime10 = findViewById(R.id.tvTime10);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);


        timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);


        startDatePicker = new DatePickerDialog(ReminderSheduleActivity.this, this, year, month, day);

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

        callActvityTypeMasterAPI();


        String[] frequency_whenArray = getResources().getStringArray(R.array.activity_type);



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


    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Set your reminder");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        etActivity = findViewById(R.id.etActivityName);
        tvTime = findViewById(R.id.tvTime);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        btnSubmit = findViewById(R.id.btnSetReminder);
        spinnerFreq = findViewById(R.id.spinnerReminder);

        String[] frequencyArray = getResources().getStringArray(R.array.reminder_frequency);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequencyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);

        spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frequency = i;
                hideStartTimeView();
                hideNShow(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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
        btnSubmit.setOnClickListener(this);
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (v.getId()) {
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

                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime2:
                isWhichTimeSlected = "2";

                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime3:
                isWhichTimeSlected = "3";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime4:
                isWhichTimeSlected = "4";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime5:
                isWhichTimeSlected = "5";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime6:
                isWhichTimeSlected = "6";
                timepickerdialog.show();
                break;

            case R.id.tvTime7:
                isWhichTimeSlected = "7";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime8:
                isWhichTimeSlected = "8";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

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

            case R.id.btnSetReminder:

                ArrayList<String> reminderArrayList = new ArrayList<>();
                lsActivity = "";
                lsActivity = etActivity.getText().toString().trim();
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








                boolean isDataValid = validateAllData(lsActivity, lsStartDate, lsEndDate, lsTime, frequency);



                if (isDataValid) {
                    if (Utils.isNetworkAvailable(context)) {

                        if (frequency == 1) {
                            reminderArrayList.add(tvTime.getText().toString());
                        } else if (frequency == 2) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                        } else if (frequency == 3) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                        } else if (frequency == 4) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                        } else if (frequency == 5) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                        } else if (frequency == 6) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                        } else if (frequency == 7) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                        } else if (frequency == 8) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                        } else if (frequency == 9) {
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                            reminderArrayList.add(tvTime9.getText().toString());
                        } else if (frequency == 10) {
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
        }
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


    private void callSheduleReminder( ArrayList<String> reminderArrayList)
    {
        if (!((Activity) context).isFinishing()) {
               utils.showProgressbar(context);
        }

        ScheduleReminderRequest request = new ScheduleReminderRequest();
        request.setUserID(liUserId);
        request.setActivity(lsActivity);
        request.setFrequency(frequency);
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
                        Toast.makeText(ReminderSheduleActivity.this,
                                "" + reminderScheduleResponse.getMessage(), Toast.LENGTH_LONG).show();


                       /* Intent i = new Intent();
                        i.putExtra("RESULT","ok");
                        setResult(RESULT_OK,i);*/
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ReminderSheduleActivity.this,
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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        Date tDate = new Date();
        //SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);

        if (lsStartDate != null && lsEndDate != null)
        {
            if (todayDate.equalsIgnoreCase(lsStartDate) || todayDate.equalsIgnoreCase(lsEndDate))
            {
                Calendar current = Calendar.getInstance();
                Calendar datetime = Calendar.getInstance();

                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute);

//                if (datetime.getTimeInMillis() > current.getTimeInMillis())
//                {
                    Calendar cal = Calendar.getInstance();

                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);

                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    lsTime = "";
                    lsTime = formatter.format(cal.getTime());

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

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                Format formatter;
                formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                lsTime = "";
                lsTime = formatter.format(cal.getTime());

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
    }

    private boolean validateAllData(String activity, String startDate, String endDate, String time,int frequency)
    {
        String s =  tvTime4.getText().toString();
        String s1 =  tvTime2.getText().toString();
        boolean valid = true;

//        if (activity != null)
//        {
//            if (activity.isEmpty())
//            {
//                valid = false;
//                errorMsg = "Enter activity";
//            }
//        }
//        else
//        {
//            valid = false;
//        }

//        if (valid && startDate != null)
//        {
//            if (startDate.isEmpty())
//            {
//                valid = false;
//                errorMsg = "Select start date";
//            }
//        }
//        else
//        {
//            valid = false;
//        }

//        if (valid && endDate != null)
//        {
//            if (endDate.isEmpty())
//            {
//                valid = false;
//                errorMsg = "Select end date";
//            }
//        }
//        else
//        {
//            valid = false;
//        }

        if (valid && frequency != 0)
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
                }else if(tvTime2.getText().toString().equalsIgnoreCase("Select Time")){
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

   /* public void clearvariables()
    {

    }*/
     /* endDatePicker = new DatePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        endDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        endDatePicker.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button buttonNeg = endDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = endDatePicker.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });*/
}
