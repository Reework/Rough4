package com.shamrock.reework.activity.ReminderModule.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.ReminderModule.service.RemindersService;
import com.shamrock.reework.api.request.ReminderEditRequest;
import com.shamrock.reework.api.response.ReminderEditResponse;
import com.shamrock.reework.api.response.ReminderResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemindersEditDialoge extends DialogFragment implements View.OnClickListener,
                                                                    DatePickerDialog.OnDateSetListener,
                                                                    TimePickerDialog.OnTimeSetListener
{
    RemindersService remindersService;
    private Utils utils;
    private String errorMsg;
    Context context;

    TextView tvTime, tvStartDate, tvEndDate;
    EditText etActivity;
    Button btnSave;
    ImageView ivClose;

    DatePickerDialog startDatePicker;
    TimePickerDialog timepickerdialog;
    Spinner spinnerFreq;
    SessionManager sessionManager;
    Spinner spinner_activity_type;
    LinearLayout linTime2,linTime3,linTime4,linTime5,linTime6,linTime7,linTime8,linTime9,linTime10;
    TextView tvTime2,tvTime3,tvTime4,tvTime5,tvTime6,tvTime7,tvTime8,tvTime9,tvTime10;


    private String lsStartDate = "", lsEndDate = "", lsTime = "", lsActivity = "";
    private boolean isEndDate = false;
    int frequency = 0, liUserId = 0;
    String isWhichTimeSlected="";


    ReminderResponse.ReminderData mModel;
    ArrayList<String> stryf;
    public interface ReminderEditDialogeInterface
    {
        public void onEdit(ReminderResponse.ReminderData model);
    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        remindersService = Client.getClient().create(RemindersService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);

        liUserId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

        startDatePicker = new DatePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);

        startDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        startDatePicker.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button buttonNeg = startDatePicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = startDatePicker.getButton(DialogInterface.BUTTON_POSITIVE);
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

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mModel = (ReminderResponse.ReminderData) bundle.getSerializable("MODEL");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialoge_reminder_edit, container, false);

        ivClose = view.findViewById(R.id.ivClose);
        etActivity = view.findViewById(R.id.etActivityName);
        tvTime = view.findViewById(R.id.tvTime);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        btnSave = view.findViewById(R.id.btnSave);
        spinnerFreq = view.findViewById(R.id.spinnerReminder);
        spinner_activity_type = view.findViewById(R.id.spinner_activity_type);


        linTime2=view.findViewById(R.id.linTime2);
        linTime3=view.findViewById(R.id.linTime3);
        linTime4=view.findViewById(R.id.linTime4);
        linTime5=view.findViewById(R.id.linTime5);
        linTime6=view.findViewById(R.id.linTime6);
        linTime7=view.findViewById(R.id.linTime7);
        linTime8=view.findViewById(R.id.linTime8);
        linTime9=view.findViewById(R.id.linTime9);
        linTime10=view.findViewById(R.id.linTime10);

        tvTime2=view.findViewById(R.id.tvTime2);
        tvTime3=view.findViewById(R.id.tvTime3);
        tvTime4=view.findViewById(R.id.tvTime4);
        tvTime5=view.findViewById(R.id.tvTime5);
        tvTime6=view.findViewById(R.id.tvTime6);
        tvTime7=view.findViewById(R.id.tvTime7);
        tvTime8=view.findViewById(R.id.tvTime8);
        tvTime9=view.findViewById(R.id.tvTime9);
        tvTime10=view.findViewById(R.id.tvTime10);




        String[] frequency_whenArray = getResources().getStringArray(R.array.activity_type);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
             stryf= (ArrayList<String>) bundle.getSerializable("Actvitiylist");
            ArrayAdapter<String> adapter_when = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, stryf);
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
        }




        String[] frequencyArray = getResources().getStringArray(R.array.reminder_frequency);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequencyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);

        lsActivity= "";
        lsStartDate="";
        lsEndDate="";
        lsTime="";
        frequency = 0;

        if (mModel != null)
        {
            if(mModel.getActivityType()!=null){

               if (stryf!=null){
                   if (!mModel.getActivityType().isEmpty()){

                       for (int i = 0; i <stryf.size() ; i++) {

                           if (mModel.getActivityType().trim().equalsIgnoreCase(stryf.get(i).toString().trim())){
                               spinner_activity_type.setSelection(i);
                               break;
                           }

                       }
                   }
               }
            }




            if (!TextUtils.isEmpty(mModel.getActivity()))
            {
                lsActivity = mModel.getActivity();

                if (!TextUtils.isEmpty(lsActivity))
                    etActivity.setText(lsActivity);
            }
            if (!TextUtils.isEmpty(mModel.getStartDate()))
            {
                String tempStartDate = mModel.getStartDate();

                if (!TextUtils.isEmpty(tempStartDate))
                {
                    lsStartDate = formatDatesServer(tempStartDate);
                    tvStartDate.setText(formatDatesNew(tempStartDate,false));
                }
            }
            if (!TextUtils.isEmpty(mModel.getEndDate()))
            {
                String tempEndDate = mModel.getEndDate();

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
            if (mModel.getFrequency() > 0)
            {
                frequency = mModel.getFrequency();
                spinnerFreq.setSelection(mModel.getFrequency());
            }

           List<String> reminderList  = mModel.getReminderTime();
            if(reminderList!=null) {
                for (int i = 0; i < reminderList.size(); i++) {
                    if (i == 0) {
                        tvTime.setText(reminderList.get(i));
                    } else if (i == 1) {
                        linTime2.setVisibility(View.VISIBLE);
                        tvTime2.setText(reminderList.get(i));
                    } else if (i == 2) {
                        linTime3.setVisibility(View.VISIBLE);
                        tvTime3.setText(reminderList.get(i));
                    } else if (i == 3) {
                        linTime4.setVisibility(View.VISIBLE);
                        tvTime4.setText(reminderList.get(i));
                    } else if (i == 4) {
                        linTime5.setVisibility(View.VISIBLE);
                        tvTime5.setText(reminderList.get(i));
                    } else if (i == 5) {
                        linTime6.setVisibility(View.VISIBLE);
                        tvTime6.setText(reminderList.get(i));
                    } else if (i == 6) {
                        linTime7.setVisibility(View.VISIBLE);
                        tvTime7.setText(reminderList.get(i));
                    } else if (i == 7) {
                        linTime8.setVisibility(View.VISIBLE);
                        tvTime8.setText(reminderList.get(i));
                    } else if (i == 8) {
                        linTime9.setVisibility(View.VISIBLE);
                        tvTime9.setText(reminderList.get(i));
                    } else if (i == 9) {
                        linTime10.setVisibility(View.VISIBLE);
                        tvTime10.setText(reminderList.get(i));
                    }

                }
            }
        }

        spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                frequency = i;
                hideStartTimeView();
                hideNShow(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){}
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
        btnSave.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        return view;
    }


    private void hideStartTimeView(){
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

    private void hideNShow(int i){
        switch (i){

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

    @Override
    public void onClick(View v)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (v.getId())
        {
            case R.id.btnSave:

                lsActivity = "";
                lsActivity = etActivity.getText().toString().trim();

                boolean isDataValid = validateAllData(lsActivity, lsStartDate, lsEndDate, lsTime, frequency);

                if (isDataValid)
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        ArrayList<String> reminderArrayList  = new ArrayList<>();

                        if(frequency==1){
                            reminderArrayList.add(tvTime.getText().toString());
                        }else if(frequency==2){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                        }
                        else if(frequency==3){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                        }
                        else if(frequency==4){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                        }
                        else if(frequency==5){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                        }
                        else if(frequency==6){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                        }
                        else if(frequency==7){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                        }
                        else if(frequency==8){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                        }
                        else if(frequency==9){
                            reminderArrayList.add(tvTime.getText().toString());
                            reminderArrayList.add(tvTime2.getText().toString());
                            reminderArrayList.add(tvTime3.getText().toString());
                            reminderArrayList.add(tvTime4.getText().toString());
                            reminderArrayList.add(tvTime5.getText().toString());
                            reminderArrayList.add(tvTime6.getText().toString());
                            reminderArrayList.add(tvTime7.getText().toString());
                            reminderArrayList.add(tvTime8.getText().toString());
                            reminderArrayList.add(tvTime9.getText().toString());
                        }
                        else if(frequency==10){
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


                        mModel.setActivity(lsActivity);
                        mModel.setStartDate(lsStartDate);
                        mModel.setEndDate(lsEndDate);
                        mModel.setReminderTime(reminderArrayList);
                        mModel.setFrequency(frequency);
                        mModel.setActivityType(spinner_activity_type.getSelectedItem().toString());


                        if(isSameDateSelected(reminderArrayList)==true){
                            Utils.shortToast(context,"time should not be same.");
                        }else {
                            callReminderEditApi(reminderArrayList);
                        }


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

            case R.id.tvStartDate:

                isEndDate = false;
                startDatePicker.show();
                break;

            case R.id.tvEndDate:
                isEndDate = true;
                startDatePicker.show();
                break;

            case R.id.tvTime:
                isWhichTimeSlected="1";
                timepickerdialog.show();
                break;

            case R.id.tvTime2:
                isWhichTimeSlected="2";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime3:
                isWhichTimeSlected="3";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime4:
                isWhichTimeSlected="4";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime5:
                isWhichTimeSlected="5";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime6:
                isWhichTimeSlected="6";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;

            case R.id.tvTime7:
                isWhichTimeSlected="7";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime8:
                isWhichTimeSlected="8";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime9:
                isWhichTimeSlected="9";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;
            case R.id.tvTime10:
                isWhichTimeSlected="10";
                timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);

                timepickerdialog.show();
                break;


            case R.id.ivClose:
                this.dismiss();
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

    private boolean validateAllData(String activity, String startDate, String endDate, String time, int localFreq)
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

        if (valid && localFreq <= 0)
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

    private void callReminderEditApi(ArrayList<String> reminderArrayList)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }
        ReminderEditRequest request = new ReminderEditRequest();
        request.setReminderID(mModel.getReminderID());
        request.setUserID(liUserId);
        request.setActivity(lsActivity);
        request.setFrequency(frequency);
        request.setStartDate(lsStartDate);
        request.setEndDate(lsEndDate);
        request.setReminderTime(reminderArrayList);

        request.setActivityType(spinner_activity_type.getSelectedItem().toString());

        String JsonRequest = new Gson().toJson(request);
        String test = JsonRequest;

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
                        Toast.makeText(context,
                                "" + reminderEditResponse.getMessage(), Toast.LENGTH_LONG).show();

                        ReminderEditDialogeInterface listener = (ReminderEditDialogeInterface) getActivity();
                        listener.onEdit(mModel);
                        dismiss();
                    }
                    else
                    {
                        Toast.makeText(context,
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

    public String formatTime(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-yyyy-MM");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        if (isEndDate)
        {
            lsEndDate = "";
            //lsEndDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
            lsEndDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

            String dateselect=year+"-"+dayOfMonth+"-"+(month+1);

//            lsEndDate=formatDatesNew(dateselect,true);


            if (!TextUtils.isEmpty(lsEndDate))
                tvEndDate.setText(formatDatesNew(dateselect,true));
        }
        else
        {
            lsStartDate = "";
            lsStartDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

            String dateselect=year+"-"+dayOfMonth+"-"+(month+1);

//            lsStartDate=formatDatesNew(dateselect,true);


            if (!TextUtils.isEmpty(lsStartDate))
                tvStartDate.setText(formatDatesNew(dateselect,true));
        }
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
//            if (todayDate.equalsIgnoreCase(lsStartDate) || todayDate.equalsIgnoreCase(lsEndDate))
//            {
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
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    lsTime = "";
                    lsTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsTime)){
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
                    //it's before current'
                    Toast.makeText(context, "Invalid Time", Toast.LENGTH_LONG).show();
                    //clearvariables();
                }
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
                    tvTime.setText(lsTime);
            }



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
}
