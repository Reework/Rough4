package com.shamrock.reework.activity.BloodTestModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsScheduleData;
import com.shamrock.reework.activity.BloodTestModule.pojo.Data;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.ProfileDataRequest;
import com.shamrock.reework.api.request.ReetestRequest;
import com.shamrock.reework.api.response.ProfileDataResponse;
import com.shamrock.reework.api.response.ReetestResponse;
import com.shamrock.reework.common.DurationTimePickDialog;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.activity.BloodTestModule.dialog.SBT_ConfirmationFragment;
import com.shamrock.reework.util.Utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleBloodTestActivity extends AppCompatActivity implements
                                        View.OnClickListener, DatePickerDialog.OnDateSetListener,
                                        TimePickerDialog.OnTimeSetListener, SBT_ConfirmationFragment.SBT_DialogListener,
                                        CompoundButton.OnCheckedChangeListener
{
    Context context;
    Toolbar toolbar;
    Typeface font;
    TextView textViewDate, textViewTime;
    RadioButton radioButtonReg, radioButtonNew;
    EditText editTextNewAddress;
    Button btnTestSchedule,buttonCancel_ScheduleTest;
    DatePickerDialog datepickerdialog;
    TimePickerDialog timepickerdialog;
    String lsDate = "", lsTime = "", lsAddress = "";
    int user_id;
    String errorMsg;
    ReeTestService reeTestService;
    SessionManager sessionManager;
    int addressType;
    LinearLayout ll_pin;
    Date selectedDate;
   // boolean isFirstTime;
    Utils utils;
    CommonService commonService;
    RegistrationService regService;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_blood_test);
        ll_pin=findViewById(R.id.ll_pin);
        context = ScheduleBloodTestActivity.this;
        init();
        setToolBar();
        findViews();
        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);
        sessionManager = new SessionManager(context);
        utils = new Utils();

//        callAlreadyScheduleApi();

        if (sessionManager.getStringValue("KeyBloodTestStatus").equals("2")){
            callGetProfileDataApi(sessionManager.getIntValue(SessionManager.KEY_USER_ID));


        }else {
            callGetProfileDataApi(sessionManager.getIntValue(SessionManager.KEY_USER_ID));

        }
    }

    private void callAlreadyScheduleApi() {


        Call<ClsScheduleData> call = reeTestService.getAlreadyScheduledBloodTest(String.valueOf(sessionManager.getIntValue(SessionManager.KEY_USER_ID)));
        call.enqueue(new Callback<ClsScheduleData>() {
            @Override
            public void onResponse(Call<ClsScheduleData> call, Response<ClsScheduleData> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsScheduleData listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("0")) {
//                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();


                        Data data=listResponse.getData();

                        if (data!=null){


                            try {
//                                lsDate=data.getScheduledate();
//                                textViewDate.setText(formatDates(data.getScheduledate()));


                                if (data.getScheduledate()!=null){
//                                    textViewTime.setText(getLocalFromUTC(data.getScheduledate()));

                                }

                                addressType= Integer.parseInt(data.getSelect_Address());

                                if (addressType==2){
                                    radioButtonNew.setChecked(true);
                                    ll_pin.setVisibility(View.VISIBLE);



                                }else {
                                    radioButtonReg.setChecked(true);
                                    ll_pin.setVisibility(View.GONE);

                                }

                                if (data.getNew_Address()!=null){
                                    editTextNewAddress.setText(data.getNew_Address());
                                }

                                btnTestSchedule.setText("Reschedule");
                                buttonCancel_ScheduleTest.setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }





                    } else {
//                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsScheduleData> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });

    }


    private void callGetProfileDataApi(int userId)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        ProfileDataRequest request = new ProfileDataRequest();
        request.setUserid(userId);

        Call<ProfileDataResponse> call = regService.getProfileData(request);
        call.enqueue(new Callback<ProfileDataResponse>()
        {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ProfileDataResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        try {
                            ProfileDataResponse.DataResponse data = listResponse.getData();

                            String USER_F_NAME = data.getFirstName();
                            String USER_L_NAME = data.getLastName();
                            String USER_EMAIL = data.getEmail();
                            String USER_MOBILE_NO = data.getMobileNo();
                            String USER_DOB = data.getDOB();
                            int USER_GENDER = data.getGender();
                            String USER_ADDRESS = data.getAddress();
                            int USER_COUNTRY_ID = data.getCountryId();
                            int USER_STATE_ID = data.getStateId();
                            int USER_CITY_ID = data.getCityId();
                            String USER_LANGUAGE_CODE = data.getLangCode();
                            String USER_TOKEN = data.getToken();
                            String USER_Image = data.getImageUrl();
                            String COUNTRY_NAME = data.getCountryName();
                            String STATE_NAME = data.getStateName();
                            String CITY_NAME = data.getCityName();


                            sessionManager.saveProfileData(USER_F_NAME, USER_L_NAME, USER_EMAIL, USER_MOBILE_NO, USER_DOB,
                                    USER_GENDER, USER_ADDRESS, USER_COUNTRY_ID, USER_STATE_ID, USER_CITY_ID, USER_LANGUAGE_CODE,
                                    USER_TOKEN, USER_Image, COUNTRY_NAME, STATE_NAME, CITY_NAME);

                            sessionManager.setStringValue(SessionManager.KEY_USER_ADDRESS, USER_ADDRESS);
                            lsAddress = sessionManager.getStringValue(SessionManager.KEY_USER_ADDRESS);

                            if (!TextUtils.isEmpty(lsAddress)){
                                editTextNewAddress.setText(lsAddress);
                                editTextNewAddress.setEnabled(true);
                            }

                            if (sessionManager.getStringValue("KeyBloodTestStatus").equals("2")){
                                callAlreadyScheduleApi();

                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    else
                    {
//                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
//                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileDataResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }
    private void init()
    {
       /* Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            isFirstTime = bundle.getString("param", "NA").equalsIgnoreCase("First_Time");
        }*/

        reeTestService = Client.getClient().create(ReeTestService.class);
        sessionManager = new SessionManager(context);
        addressType = 1;
        lsAddress = sessionManager.getStringValue(SessionManager.KEY_USER_ADDRESS);
        user_id = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

//        timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

        timepickerdialog = new DurationTimePickDialog(context,this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)+15,
                false);

        datepickerdialog = new DatePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialogInterface)
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
            public void onShow(DialogInterface dialogInterface)
            {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
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
        tvTitle.setText(R.string.title_schedule_blood_test);
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        textViewDate = findViewById(R.id.text_SBT_Date);
        textViewTime = findViewById(R.id.text_SBT_Time);
        radioButtonReg = findViewById(R.id.radioButtonRegisteredAddress);
        radioButtonNew = findViewById(R.id.radioButtonNewAddress);
        editTextNewAddress = findViewById(R.id.edtTextBloodTest_NewAddress);
        btnTestSchedule = findViewById(R.id.buttonSubmit_ScheduleTest);
        buttonCancel_ScheduleTest = findViewById(R.id.buttonCancel_ScheduleTest);

        textViewDate.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
        btnTestSchedule.setOnClickListener(this);
        buttonCancel_ScheduleTest.setOnClickListener(this);
        radioButtonReg.setOnCheckedChangeListener(this);
        radioButtonNew.setOnCheckedChangeListener(this);

//        String curentdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        lsDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        textViewDate.setText(formatDates(lsDate));



        if (!TextUtils.isEmpty(lsAddress))
            editTextNewAddress.setText(lsAddress);
        editTextNewAddress.setEnabled(true);

        utils = new Utils();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonSubmit_ScheduleTest:


                boolean dataIsValid = validateAllData(lsDate, lsTime, lsAddress);

                if (addressType == 2)
                {
                    lsAddress = editTextNewAddress.getText().toString().trim();
                }

                if (dataIsValid)
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        /*utils.showProgressbar(context);
                        callReetestApi(user_id, date, time, addressType, address);*/

                        if (!((Activity) context).isFinishing())
                        {
                            utils.showProgressbar(context);
                        }
                        callReetestApi(user_id, lsDate, lsTime, addressType, lsAddress);

                        /*sessionManager.setBooleanValue(SessionManager.KEY_USER_REETEST_IS_DONE, true);
                        startActivity(new Intent(context, Download_BloodTestReportActivity.class));
                        finish();*/
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

            case R.id.buttonCancel_ScheduleTest:
                // show date dialog

                //Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                break;


            case R.id.text_SBT_Time:
                // show time dialog
                timepickerdialog.show();
                break;

            default:
        }
    }

    private void callReetestApi(int userId, String date, String time, int addressType, String address)
    {
        EditText edt_pincode=findViewById(R.id.edt_pincode);
        String strpincode=edt_pincode.getText().toString();
        ReetestRequest request = new ReetestRequest();

        if (editTextNewAddress.getText().toString().isEmpty()){
            utils.hideProgressbar();

            Toast.makeText(context, "Enter the address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (strpincode.isEmpty()&&addressType==2){
            utils.hideProgressbar();

            Toast.makeText(context, "Enter Pincode", Toast.LENGTH_SHORT).show();
            return;
        }


        if (strpincode.length()<6&&addressType==2){
            utils.hideProgressbar();

            Toast.makeText(context, "Enter valid Pincode", Toast.LENGTH_SHORT).show();
            return;
        }


        request.setScheduledByUserID(userId);
        request.setScheduledate(date+" "+time);
//        request.setScheduletime(time);
        request.setSelectAddress(addressType);
        request.setNewAddress(address);
        if (strpincode.isEmpty()){
            request.setPincode(0);

        }else {
            request.setPincode(Integer.parseInt(strpincode));

        }

        Call<ReetestResponse> call = reeTestService.scheduleBloodTest(request);
        call.enqueue(new Callback<ReetestResponse>()
        {
            @Override
            public void onResponse(Call<ReetestResponse> call, Response<ReetestResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ReetestResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        // show Dialog
                        FragmentManager fm = getSupportFragmentManager();
                        SBT_ConfirmationFragment dialogFragment = new SBT_ConfirmationFragment();
                        dialogFragment.show(fm, "SBT_fragment");

                        sessionManager.setBooleanValue(SessionManager.KEY_USER_IS_BLOOD_TEST_SCHEDULE, true);
                    }
                    else
                    {
                        Toast.makeText(ScheduleBloodTestActivity.this, "" + listResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReetestResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });

    }

    private boolean validateAllData(String date, String time, String address)
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



        String curentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Date currentTime = Calendar.getInstance().getTime();











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
            errorMsg = "Select time";

        }


        return valid;
    }

    @Override
    public void onSBT_Dialog()
    {

        Intent intents = new Intent(this, SnipeetActivity.class);
        startActivity(intents);






       /* startActivity(new Intent(context, Download_BloodTestReportActivity.class));*/

        /*if (isFirstTime)
        {
            startActivity(new Intent(context, Download_BloodTestReportActivity.class));
            finish();
        }*/
        /*else {
            finish();
        }*/
        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {
       // lsDate = String.format(Locale.getDefault(), "%02d | %02d | %04d", day, month + 1, year);
        // change date format for webAPI
        //lsDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", day, (month + 1), year);
        clearvariables();

        lsDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, (month + 1),day);

//        lsDate=;
        textViewDate.setText(formatDates(lsDate));

        textViewTime.setText("select time");
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1)
    {
        Date tDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);

        if (lsDate != null)
        {
            // check selected date is Today's date or not
            if (todayDate.equalsIgnoreCase(lsDate))
            {
                Calendar current = Calendar.getInstance();
                Calendar datetime = Calendar.getInstance();

                datetime.set(Calendar.HOUR_OF_DAY, i);
                datetime.set(Calendar.MINUTE, i1);

                if (datetime.getTimeInMillis() > current.getTimeInMillis())
                {
                    //it's after current
                    int hour = i % 12;
            //btnPickStartTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour, i1, i < 12 ? "am" : "pm"));

                    Calendar cal = Calendar.getInstance();

                    cal.set(Calendar.HOUR_OF_DAY, i);
                    cal.set(Calendar.MINUTE, i1);

                    Format formatter;
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    lsTime = formatter.format(cal.getTime());
                    textViewTime.setText(lsTime);

                    // change date format for webAPI
                    //time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
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
                cal.set(Calendar.HOUR_OF_DAY, i);
                cal.set(Calendar.MINUTE, i1);
                Format formatter;
                formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                lsTime = formatter.format(cal.getTime());
                textViewTime.setText(lsTime);
                // change date format for webAPI
                //time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Date has not selected.", Toast.LENGTH_LONG).show();
        }

/*
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        time = formatter.format(cal.getTime());
        textViewTime.setText(time);

        // change date format for webAPI
        time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
*/
    }

    public void clearvariables()
    {
        lsTime = "";
        textViewTime.setText("");
    }



    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }



    public static String getLocalFromUTC(String utcTime) {
        /**  2017-10-22T18:22:37.000+06:00  **/

        try {
//            String newUtcTime = utcTime.substring(0, utcTime.indexOf("+"));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = dateFormat.parse(utcTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            return simpleDateFormat.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                /** "created_at": "2017-08-15T10:29:20.000Z"  &&  **/
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                dateFormat.setTimeZone(TimeZone.getTimeZone("BST"));
                Date date = dateFormat.parse(utcTime);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                return simpleDateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        if (radioButtonNew.isChecked())
        {
            ll_pin.setVisibility(View.VISIBLE);
            //editTextNewAddress.setVisibility(View.VISIBLE);
            editTextNewAddress.setEnabled(true);
            editTextNewAddress.setText("");
            editTextNewAddress.setHint("Enter New Address");
            addressType = 2;
        }
        else
        {
            ll_pin.setVisibility(View.GONE);

            addressType = 1;
            //editTextNewAddress.setVisibility(View.GONE);
            editTextNewAddress.setEnabled(true);
            String strAddress = sessionManager.getStringValue(SessionManager.KEY_USER_ADDRESS);
            editTextNewAddress.setText(strAddress);
            lsAddress = strAddress;
        }
    }

}
