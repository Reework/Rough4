package com.shamrock.reework.activity.AppoinmentModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shamrock.R;
import com.shamrock.reework.activity.AppoinmentModule.pojo.ClsChargableMain;
import com.shamrock.reework.activity.AppoinmentModule.pojo.GroupData;
import com.shamrock.reework.activity.AppoinmentModule.pojo.User;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AppoinmentRequest;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.common.DurationTimePickDialog;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.payment.ClsPaymetresponse;
import com.shamrock.reework.payment.MainActivity;
import com.shamrock.reework.razerpaypsyment.PaymentWebviewActivity;
import com.shamrock.reework.util.Utils;

import java.lang.reflect.Field;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//Edit 2020-02-07T00:00:00
public class AppoinmentScheduleActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener
{
    Context context;
    Toolbar toolbar;
    Button btnSubmit;
    TextView textViewDate, textViewTime, tvEndTime;
    LinearLayout linearEndTime;
    EditText etComments;
    DatePickerDialog datepickerdialog;
    TimePickerDialog timepickerdialog;
    String lsDate = "", lsTime = "", lsEndTime = "", lsComments = "";
    int liCoachId = 0, liUserId = 0;
    boolean isFree=true;
    RadioButton rd_button_patho,rd_button_reecoach;
    TextView freevisit;
    TextView txtselectedreecoah;

    String errorMsg;
    Utils utils;
    private int int_ReecaochID;
    AppoinmentService appoinmentService;
    SessionManager sessionManager;
    RadioButton radioButton_oncall;
    RadioButton radioButton_at_office;
    int venue=1;
    String commanDateFormat;
    RadioButton rd_button_free;
    RadioButton rd_button_charge;
    private ArrayList<User> arylst_free_group_data;
    private ArrayList<User> arylst_chanrge_group_data;
    private ArrayAdapter adapter_charge;
    Spinner spinner_reecoach_list;
    boolean ISFromPAth;
    private int int_appointment;
    TextView txt1,txt2,txt3;
    int submitCode=1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_reecoach_appointment);
        rd_button_reecoach=findViewById(R.id.rd_button_reecoach);
        rd_button_patho=findViewById(R.id.rd_button_patho);
        txtselectedreecoah=findViewById(R.id.txtselectedreecoah);
        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCode=1;
                txt1.setBackgroundColor(context.getResources().getColor(R.color.colorGreen1));
                txt2.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));
                txt3.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));

            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCode=2;
                txt2.setBackgroundColor(context.getResources().getColor(R.color.colorGreen1));
                txt1.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));
                txt3.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));
            }
        });

        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCode=3;
                txt3.setBackgroundColor(context.getResources().getColor(R.color.colorGreen1));
                txt2.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));
                txt1.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));
            }
        });

        context = AppoinmentScheduleActivity.this;
        spinner_reecoach_list=findViewById(R.id.spinner_reecoach_list);
        freevisit=findViewById(R.id.freevisit);
        TextView txt_select_reecoach_header_name=findViewById(R.id.txt_select_reecoach_header_name);
        ISFromPAth=getIntent().getBooleanExtra("IsFromPatho",false);
        LinearLayout ll_appointemntlist=findViewById(R.id.ll_appointemntlist);
        if (ISFromPAth){
            rd_button_patho.setChecked(true);
            txt_select_reecoach_header_name.setText("Select Pathologist");
            ll_appointemntlist.setVisibility(View.GONE);
        }else {
            rd_button_reecoach.setChecked(true);

            txt_select_reecoach_header_name.setText("Select Reecoach");
            ll_appointemntlist.setVisibility(View.VISIBLE);


        }

        init();
        setToolBar();
        findViews();
        callGroupApi();
    }

    private void init()
    {
        utils = new Utils();
        appoinmentService = Client.getClient().create(AppoinmentService.class);
        sessionManager = new SessionManager(context);
        if (ISFromPAth){
            txtselectedreecoah.setText(sessionManager.getStringValue("pathologistname"));

        }else {
            txtselectedreecoah.setText(sessionManager.getStringValue("Reecoachname"));

        }
        commanDateFormat=sessionManager.getStringValue("commanDateFormat");
        liCoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        liUserId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        rd_button_free=findViewById(R.id.rd_button_free);
        rd_button_charge=findViewById(R.id.rd_button_charge);

        rd_button_free.setOnCheckedChangeListener(this);
        rd_button_charge.setOnCheckedChangeListener(this);
        rd_button_patho.setOnCheckedChangeListener(this);
        rd_button_reecoach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Intent intent=new Intent(AppoinmentScheduleActivity.this,AppoinmentScheduleActivity.class);
                    intent.putExtra("IsFromPatho", false);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }

            }
        });
        rd_button_patho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Intent intent=new Intent(AppoinmentScheduleActivity.this,AppoinmentScheduleActivity.class);
                    intent.putExtra("IsFromPatho", true);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();
                }

            }
        });
//        rd_button_free.setChecked(true);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(context, AppoinmentScheduleActivity.this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)+15,
                false);

        datepickerdialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add( Calendar.MONTH, 3 ); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(maxDate);



        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
    }


    private void setTimePickerInterval(TimePicker timePicker) {
        try {
            int TIME_PICKER_INTERVAL = 15;
            NumberPicker minutePicker;
            List<String> displayedValues;
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            // Field timePickerField = classForid.getField("timePicker");

            Field field = classForid.getField("minute");
            minutePicker = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));

            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(3);
            displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            //  for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
            //      displayedValues.add(String.format("%02d", i));
            //  }
            minutePicker.setDisplayedValues(displayedValues
                    .toArray(new String[0]));
            minutePicker.setWrapSelectorWheel(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("My Appointment");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void findViews()
    {
        linearEndTime = (LinearLayout) findViewById(R.id.linearEndTime);
        textViewDate = findViewById(R.id.text_SBT_Date);
        textViewTime = findViewById(R.id.text_SBT_Time);
        tvEndTime = findViewById(R.id.tvEndTime);
        etComments = findViewById(R.id.etComments);
        btnSubmit = findViewById(R.id.buttonSubmit_ScheduleAppointment);
        radioButton_oncall=findViewById(R.id.radioButton_oncall);
        radioButton_at_office=findViewById(R.id.radioButton_at_office);

        textViewDate.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setText("Schedule Appointment");

        radioButton_oncall.setOnCheckedChangeListener(this);
        radioButton_at_office.setOnCheckedChangeListener(this);
    }

//    @Override
//    public void finish()
//    {
//        super.finish();
////        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonSubmit_ScheduleAppointment:



                lsComments = "";
                lsComments = etComments.getText().toString().trim();

                boolean isDataValid = validateAllData(lsDate, lsTime, lsComments);

                if (isDataValid)
                {

                    try {
                        if(getCurrentFocus()!=null){
                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    if (Utils.isNetworkAvailable(context))
                    {
                        if (!((Activity) context).isFinishing())
                        {
                            utils.showProgressbar(context);
                        }

                        if(lsTime.contains(".")){
                            lsTime = lsTime.replace(".","");
                        }
                        if(lsEndTime.contains(".")){
                            lsEndTime = lsEndTime.replace(".","");
                        }

//                        if (rd_button_charge.isChecked()){
//                            Intent intent=new Intent(context, MainActivity.class);
//
//                            int  pos=spinner_reecoach_list.getSelectedItemPosition();
//                            String amount=arylst_chanrge_group_data.get(pos).getFees();
//                            intent.putExtra("amount",String.valueOf(Math.round(Float.parseFloat(amount))));
//                            startActivityForResult(intent,1002);
//
//                        }
                        callApoinmentApi(liCoachId, lsDate, lsTime, lsComments, lsEndTime, liUserId);

                    }
                    else
                    {
                        Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
                    }
                }
                else
                {
                    Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.text_SBT_Date:
                // show date dialog
                datepickerdialog.show();
                break;

            case R.id.text_SBT_Time:
                // show time dialog
                timepickerdialog.show();
                break;

            default:
        }
    }

    private void callApoinmentApi(int coachId, String date, String time, String comment, String endTime, int userId)
    {
        AppoinmentRequest request = new AppoinmentRequest();
        request.setDate(date);
        request.setComment(comment);
        request.setTime(time);


        if (ISFromPAth){
            int_ReecaochID= sessionManager.getIntValue(SessionManager.KEY_USER_PATHO_ID);

        }else {
            int_ReecaochID=sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);

        }
        request.setReCoachID(int_ReecaochID);

//        request.setReCoachID(coachId);
        request.setEndTime(endTime);
        request.setUserId(userId);
        request.setVenuTypeType(venue);
        if (ISFromPAth){
            request.setAppointmentTypeId(4);

        }else {
            request.setAppointmentTypeId(submitCode);

        }

//        if (isFree){
//            int pos=spinner_reecoach_list.getSelectedItemPosition();
//            int_ReecaochID= Integer.parseInt(arylst_free_group_data.get(pos).getId());
////            request.setReCoachID(int_ReecaochID);
//
//        }else {
//            int pos=spinner_reecoach_list.getSelectedItemPosition();
//            int_ReecaochID= Integer.parseInt(arylst_chanrge_group_data.get(pos).getId());
////            request.setReCoachID(int_ReecaochID);
//        }

        sessionManager=new SessionManager(AppoinmentScheduleActivity.this);

        try {

            if (ISFromPAth){
                int_ReecaochID= Integer.parseInt(sessionManager.getStringValue(SessionManager.KEY_USER_PATHO_ID));

            }else {
                int_ReecaochID= Integer.parseInt(sessionManager.getStringValue(SessionManager.KEY_USER_REECOACH_ID));

            }
            request.setReCoachID(int_ReecaochID);

        }catch (Exception w){
            w.printStackTrace();
        }



        Gson gson = new GsonBuilder().create();
        String Test  = gson.toJson(request);
        String ABC  = Test;

        Call<AppoinmentResponse> call = appoinmentService.sentAppoinmentRequest(request);
        call.enqueue(new Callback<AppoinmentResponse>()
        {
            @Override
            public void onResponse(Call<AppoinmentResponse> call, Response<AppoinmentResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AppoinmentResponse appoinmentResponse = response.body();

                    if (appoinmentResponse != null && appoinmentResponse.getCode() == 1)
                    {
//                        Toast.makeText(AppoinmentScheduleActivity.this, "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();

//                        Intent i = new Intent();
//                        i.putExtra("RESULT","ok");
//                        setResult(RESULT_OK,i);
//                        finish();


                        if (!appoinmentResponse.getData().isPay()){
                            Toast.makeText(AppoinmentScheduleActivity.this, "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent();
                            i.putExtra("RESULT","ok");
                            setResult(RESULT_OK,i);
                            finish();


                        }else {
                            Intent intent=new Intent(AppoinmentScheduleActivity.this, PaymentWebviewActivity.class);
                            intent.putExtra("ShortUrl",appoinmentResponse.getData().getPayLink());
                            intent.putExtra("from","Reecoach");
                            startActivity(intent);
                            startActivityForResult(intent, 3000);
                            finish();
                        }




//



                    }
                    else
                    {
                        Toast.makeText(AppoinmentScheduleActivity.this,
                                "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppoinmentResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }



    private void callGroupApi()
    {
        final int rollID;

        if (ISFromPAth){
            rollID=4;
        }else {
            rollID=3;
        }


        Call<ClsChargableMain> call = appoinmentService.getNutritionist(rollID,liUserId);
        call.enqueue(new Callback<ClsChargableMain>()
        {
            @Override
            public void onResponse(Call<ClsChargableMain> call, Response<ClsChargableMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsChargableMain appoinmentResponse = response.body();

                    if (appoinmentResponse != null && appoinmentResponse.getCode().equals("1"))
                    {

                        if (rollID==3){
                            int_appointment=appoinmentResponse.getReecoachVisitLeft();
                            freevisit.setText("Free Visit Left : "+int_appointment);
                            if (appoinmentResponse.getReecoachVisitLeft()<1){
                                if (rd_button_charge.isChecked()){
                                    btnSubmit.setText("Make Payment");
                                }else {
                                    btnSubmit.setText("Schedule Appointment");
                                }
                            }
                        }

                        if (rollID==4){
                            int_appointment=appoinmentResponse.getPathoVisitLeft();
                            if (appoinmentResponse.getPathoVisitLeft()<1){
                                if (rd_button_charge.isChecked()){
                                    btnSubmit.setText("Make Payment");
                                }else {
                                    btnSubmit.setText("Schedule Appointment");
                                }
                            }
                        }
                        if (int_appointment==0){
                            rd_button_free.setEnabled(false);
                            rd_button_charge.setChecked(true);
                        }else {
                            rd_button_free.setEnabled(true);

                        }

//                        rd_button_free.performClick();

                        arylst_free_group_data=appoinmentResponse.getData().get(0).getUsers();
                        int_ReecaochID= Integer.parseInt(appoinmentResponse.getData().get(0).getUsers().get(0).getId());
                        arylst_chanrge_group_data=appoinmentResponse.getData().get(1).getUsers();
                        ArrayList<String> arylst_free=new ArrayList<>();
                        for (int i = 0; i <arylst_free_group_data.size() ; i++) {
                            String text=arylst_free_group_data.get(i).getFullname()+" ("+"Fees-"+"Rs."+arylst_free_group_data.get(i).getFees()+")";
                            arylst_free.add(text);
                        }
                        adapter_charge = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_free);
                        spinner_reecoach_list.setAdapter(adapter_charge);




                    }
                    else
                    {
                        Toast.makeText(AppoinmentScheduleActivity.this,
                                "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsChargableMain> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        clearvariables();
//        lsDate = String.format(Locale.getDefault(), "%04d| %02d | %02d ", dayOfMonth, month + 1, year);
//        lsDate = String.format(Locale.getDefault(), "%02d |%04d| %02d |",  dayOfMonth,year, month + 1);
//
        lsDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0); ////disable dates
        cal.set(year, month, dayOfMonth, 0, 0, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormat.format(cal.getTime());




        if (!TextUtils.isEmpty(dateString))
            textViewDate.setText(dateString);

        // change date format for webAPI
        //lsDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, (month + 1), year);
//        lsDate = String.format(Locale.getDefault(), "%04d| %02d | %02d",  dayOfMonth,year, month + 1);
        lsDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);


        String dateselect=year+"-"+dayOfMonth+"-"+(month+1);
//        lsDate=formatDates(dateselect);
        // lsTime = "Select Time";
//        lsDate="";
//        lsDate=dateString;
        textViewTime.setText("Select Time");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        Date tDate = new Date();
        //SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);

        if (lsDate != null)
        {
            if (todayDate.equalsIgnoreCase(lsDate))
            {
                Calendar current = Calendar.getInstance();
                Calendar datetime = Calendar.getInstance();

                datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                datetime.set(Calendar.MINUTE, minute);

                if (datetime.getTimeInMillis() > current.getTimeInMillis())
                {
                    Calendar cal = Calendar.getInstance();

                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);

                    Format formatter;
                    formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                    lsTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsTime))
                        textViewTime.setText(lsTime);

                    /* increment the time by 30 mins */
                    if (!TextUtils.isEmpty(lsTime))
                    {
                        cal.add(Calendar.MINUTE, 30);
                        formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                        lsEndTime = formatter.format(cal.getTime());

                        if (!TextUtils.isEmpty(lsEndTime))
                        {
                            linearEndTime.setVisibility(View.VISIBLE);
                            tvEndTime.setText(lsEndTime);
                        }
                        /*Calendar selectedTime = Calendar.getInstance();
                        selectedTime.add(Calendar.MINUTE, 30);

                        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                        lsEndTime = formatter.format(selectedTime.getTime());*/
                    }

                    // change date format for webAPI
                    //lsTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    //lsEndTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                }
                else
                {
                    //it's before current'
                    Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                    textViewTime.setText("select time");
                    clearvariables();
                }
            }
            else
            {
                clearvariables();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                Format formatter;
                formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                lsTime = formatter.format(cal.getTime());

                if (!TextUtils.isEmpty(lsTime))
                    textViewTime.setText(lsTime);

                /* increment the time by 30 mins */
                if (!TextUtils.isEmpty(lsTime))
                {
                    cal.add(Calendar.MINUTE, 30);
                    formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                    lsEndTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsEndTime))
                    {
                        linearEndTime.setVisibility(View.VISIBLE);
                        tvEndTime.setText(lsEndTime);
                    }
                        /*Calendar selectedTime = Calendar.getInstance();
                        selectedTime.add(Calendar.MINUTE, 30);

                        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                        lsEndTime = formatter.format(selectedTime.getTime());*/
                }

                // change date format for webAPI
                //lsTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Date has not selected.", Toast.LENGTH_LONG).show();
        }
    }

    public void clearvariables()
    {
        lsTime = "";
        lsEndTime = "";
        textViewTime.setText("");
        tvEndTime.setText("");
        linearEndTime.setVisibility(View.GONE);
    }

    private boolean validateAllData(String date, String time, String comment)
    {
        boolean valid = true;

        if (date != null)
        {
            if (date.isEmpty())
            {
                valid = false;
                errorMsg = "Select date";
            }
        }
        else
        {
            valid = false;
        }

        if (valid && time != null)
        {
            if (time.isEmpty())
            {
                valid = false;
                errorMsg = "Select time";
            }
        }
        else
        {
            valid = false;
        }

//        if (valid && comment != null)
//        {
//            if (comment.isEmpty() || comment.length() < 2)
//            {
//                valid = false;
//                errorMsg = "Enter valid comment";
//            }
//        }
//        else
//        {
//            valid = false;
//        }
        return valid;
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        buttonCheckChanged();
    }

    private void buttonCheckChanged()
    {
        try {

//         if (rd_button_reecoach.isChecked()){
//             ISFromPAth=false;
//         }else {
//             ISFromPAth=true;
//
//         }


            if (radioButton_at_office.isChecked())
            {
                venue = 1;
            }
            else if (radioButton_oncall.isChecked())
            {
                venue = 2;

            }




            if (rd_button_free.isChecked()){
                isFree=true;
                btnSubmit.setText("Schedule Appointment");


                ArrayList<String> arylst_free=new ArrayList<>();
                if (arylst_free_group_data!=null&&!arylst_free_group_data.isEmpty()){

                    try {
                        for (int i = 0; i < arylst_free_group_data.size(); i++) {

                            String text = arylst_free_group_data.get(i).getFullname() + " (" + "Fees-" + "Rs." + arylst_free_group_data.get(i).getFees() + ")";
                            arylst_free.add(text);
                        }
                        adapter_charge = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, arylst_free);
                        spinner_reecoach_list.setAdapter(adapter_charge);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }









//            callGroupApi();


            }else if (rd_button_charge.isChecked()){




                if (int_appointment<1){
                    btnSubmit.setText("Make Payment");
                }else {
                    btnSubmit.setText("Schedule Appointment");
                }












                isFree=false;
                ArrayList<String> arylst_free=new ArrayList<>();

                if (arylst_chanrge_group_data!=null&&!arylst_chanrge_group_data.isEmpty()){
                    try {
                        for (int i = 0; i < arylst_chanrge_group_data.size(); i++) {
                            String text = arylst_chanrge_group_data.get(i).getFullname() + " (" + "Fees-" + "Rs." + arylst_chanrge_group_data.get(i).getFees() + ")";
                            arylst_free.add(text);
                        }
                        adapter_charge = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, R.id.txt_when, arylst_free);
                        spinner_reecoach_list.setAdapter(adapter_charge);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }





            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000 && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("RESULT"))
            {
                if (data.getStringExtra("RESULT").equals("ok"))
                {
                    Intent i = new Intent();
                    i.putExtra("RESULT","ok");
                    setResult(RESULT_OK,i);
                    finish();

                }
            }
        }

    }
}
