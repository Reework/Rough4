package com.shamrock.reework.activity.AppoinmentModule.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.AppoinmentModule.activity.AppoinmentScheduleActivity;
import com.shamrock.reework.activity.AppoinmentModule.pojo.ClsChargableMain;
import com.shamrock.reework.activity.AppoinmentModule.pojo.User;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.AppoinmentRequest;
import com.shamrock.reework.api.response.AppoinmentEditResponse;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.common.DurationTimePickDialog;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.razerpaypsyment.PaymentWebviewActivity;
import com.shamrock.reework.util.Utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAppoinmentEditDialog extends DialogFragment implements View.OnClickListener,
                                                                        DatePickerDialog.OnDateSetListener,
                                                                        TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener
{
    private AppoinmentService appoinmentService;
    private Utils utils;
    private String errorMsg;
    Context context;
    boolean isFreeFrom;

    Button btnSave;
    TextView textViewDate, textViewTime, tvEndTime;
    ImageView ivClose;
    TextView labelMedicineTitle;
    LinearLayout linearEndTime;
    EditText etComments;
    TextView edt_reecoach_heder;
    DatePickerDialog datepickerdialog;
    TimePickerDialog timepickerdialog;
    String lsDate = "", lsTime = "", lsEndTime = "", lsComments = "";
    int liCoachId = 0, liUserId = 0;
    SessionManager sessionManager;

    RadioButton radioButton_oncall;
    RadioButton radioButton_at_office;
    private GetAllAppointmentResponse.AppointmentData dataModel;
    int venue;
    RadioButton rd_button_free;
    RadioButton rd_button_charge;
    private ArrayList<User> arylst_free_group_data;
    private ArrayList<User> arylst_chanrge_group_data;
    private ArrayAdapter adapter_charge;
    Spinner spinner_reecoach_list;
    private int int_ReecaochID;
    private boolean isFree;
    private String str_existing_text;
    boolean PATHObolean;
    private boolean boolean_FROM_SERVICE;
    private String strrollID;
    private int int_appointment;
    private int fees;
    private void buttonCheckChanged()
    {
        if (radioButton_at_office.isChecked())
        {
            venue = 1;
        }
        else if (radioButton_oncall.isChecked())
        {
            venue = 2;
        }

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        buttonCheckChanged();

//        if (rd_button_free.isChecked()){
//            isFree=true;
//
//            int_ReecaochID= Integer.parseInt(arylst_free_group_data.get(0).getId());
//            ArrayList<String> arylst_free=new ArrayList<>();
//            for (int i = 0; i <arylst_free_group_data.size() ; i++) {
//                String text=arylst_free_group_data.get(i).getFullname()+"("+"Fees-"+"Rs."+arylst_free_group_data.get(i).getFees()+")";
//                arylst_free.add(text);
//            }
//
//
//            adapter_charge = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_free);
//            spinner_reecoach_list.setAdapter(adapter_charge);
//
//            callGroupApi();

//
//        }else if (rd_button_charge.isChecked()){
//            int_ReecaochID= Integer.parseInt(arylst_chanrge_group_data.get(0).getId());
//
//            isFree=false;
//            ArrayList<String> arylst_free=new ArrayList<>();
//            for (int i = 0; i <arylst_chanrge_group_data.size() ; i++) {
//                String text=arylst_chanrge_group_data.get(i).getFullname()+" ("+"Fees-"+"Rs."+arylst_chanrge_group_data.get(i).getFees()+")";
//                arylst_free.add(text);
//            }
//            adapter_charge = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_free);
//            spinner_reecoach_list.setAdapter(adapter_charge);
//
//        }
    }

    public interface ApponmentEditInterface
    {
        public void onEdit(GetAllAppointmentResponse.AppointmentData model);
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

        appoinmentService = Client.getClient().create(AppoinmentService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);

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

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            dataModel = (GetAllAppointmentResponse.AppointmentData) bundle.getSerializable("MODEL");
            PATHObolean=bundle.getBoolean("PATHO",false);
            boolean_FROM_SERVICE=bundle.getBoolean("FROM_SERVICE",false);
            strrollID=bundle.getString("rollID");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_appoinment, container, false);

        textViewDate = view.findViewById(R.id.text_SBT_Date);
        edt_reecoach_heder = view.findViewById(R.id.edt_reecoach_heder);

        if (PATHObolean){
            edt_reecoach_heder.setText("Select Pathologist");
        }else {
            edt_reecoach_heder.setText("Select Reecoach");

        }

        textViewTime = view.findViewById(R.id.text_SBT_Time);
        tvEndTime = view.findViewById(R.id.tvEndTime);
        etComments = view.findViewById(R.id.etComments);
        btnSave = view.findViewById(R.id.btnSave);
        ivClose = view.findViewById(R.id.ivClose);
        radioButton_oncall=view.findViewById(R.id.radioButton_oncall);
        radioButton_at_office=view.findViewById(R.id.radioButton_at_office);

        rd_button_free=view.findViewById(R.id.rd_button_free);
        rd_button_charge=view.findViewById(R.id.rd_button_charge);
        spinner_reecoach_list=view.findViewById(R.id.spinner_reecoach_list);
        labelMedicineTitle=view.findViewById(R.id.labelMedicineTitle);

        if (boolean_FROM_SERVICE){
            labelMedicineTitle.setText("Revisit appointment");
            btnSave.setText("Revisit");
        }

        rd_button_free.setOnCheckedChangeListener(this);
        rd_button_charge.setOnCheckedChangeListener(this);


        textViewDate.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        radioButton_oncall.setOnCheckedChangeListener(this);
        radioButton_at_office.setOnCheckedChangeListener(this);

        liCoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        liUserId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        lsDate="";
        lsTime="";
        lsEndTime="";

        if (dataModel != null)
        {
            try {

                if (dataModel.getReeCoachId() != null) {
                    int_ReecaochID = dataModel.getReeCoachId();
                }


                if (dataModel.getVenuType() != null) {
                    if (dataModel.getVenuType().equals(1)) {
                        venue = 1;
                        radioButton_at_office.setChecked(true);
                    } else {
                        venue = 2;
                        radioButton_oncall.setChecked(true);
                    }
                }

                if (!TextUtils.isEmpty(dataModel.getDate())) {
                    String tempDate = dataModel.getDate();

                    if (!TextUtils.isEmpty(tempDate)) {
                        lsDate = formatDatesServer(tempDate);
                        textViewDate.setText(formatDatesSelect(tempDate, false));
                    }
                }
                if (!TextUtils.isEmpty(dataModel.getTime())) {
                    String tempTime = dataModel.getTime();

                    if (!TextUtils.isEmpty(tempTime)) {
                        lsTime = formatTime(tempTime);
                        textViewTime.setText(lsTime);
                    }
                }
                if (!TextUtils.isEmpty(dataModel.getEndTime())) {
                    String endTime = dataModel.getEndTime();

                    if (!TextUtils.isEmpty(endTime)) {
                        lsEndTime = formatTime(endTime);
                        tvEndTime.setText(lsEndTime);
                    }
                }
                if (!TextUtils.isEmpty((String) dataModel.getComment())) {
                    lsComments = (String) dataModel.getComment();

                    if (!TextUtils.isEmpty(lsComments))
                        etComments.setText(lsComments);
                }


              if (dataModel.getFees()==0){

                  isFreeFrom=true;
                  rd_button_free.setChecked(true);
                  rd_button_charge.setEnabled(false);
                  rd_button_free.setEnabled(false);

              }else {

                  isFree=false;
                  rd_button_charge.setChecked(true);
                  rd_button_charge.setEnabled(false);
                  rd_button_free.setEnabled(false);
              }



                if (dataModel.getFullname()!=null){
                    ArrayList<String> str=new ArrayList<>();
                    str.add(dataModel.getFullname());

                    adapter_charge = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, str);
                    spinner_reecoach_list.setAdapter(adapter_charge);
                    spinner_reecoach_list.setEnabled(false);

                }


                if (boolean_FROM_SERVICE){
                    callGroupApi();

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSave:

                lsComments = "";
                lsComments = etComments.getText().toString().trim();

                boolean isDataValid = validateAllData(lsDate, lsTime, lsComments);
                if (isDataValid)
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        dataModel.setComment(lsComments);
                        dataModel.setDate(lsDate);
                        dataModel.setTime(lsTime);
                        if(lsTime.contains(".")) {
                            lsTime = lsTime.replace(".", "");
                        }
                        if(lsEndTime.contains(".")){
                            lsEndTime = lsEndTime.replace(".", "");
                        }
                        dataModel.setEndTime(lsEndTime);

                        if (!((Activity) context).isFinishing())
                        {
                            utils.showProgressbar(context);
                        }
                        callApoinmentEditApi(liCoachId, lsDate, lsTime, lsComments, lsEndTime, liUserId);
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
                datepickerdialog.show();
                break;

            case R.id.text_SBT_Time:
                timepickerdialog.show();
                break;

            case R.id.ivClose:
                this.dismiss();
                break;
        }
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



    private void callApoinmentEditApi(int coachId, String date, String time, String comment, String endTime, int userId)
    {
        AppoinmentEditRequest request = new AppoinmentEditRequest();
        request.setDate(date);
        request.setComment(comment);
        request.setTime(time);
        request.setReeCoachId(coachId);
        request.setEndTime(endTime);
        request.setVenuType(venue);
        request.setUserID(userId);
        if (boolean_FROM_SERVICE){
            request.setApptID(0);

        }else {
            request.setApptID(dataModel.getApptID());

        }




//        if (isFree){
//            int pos=spinner_reecoach_list.getSelectedItemPosition();
//            int_ReecaochID= Integer.parseInt(arylst_free_group_data.get(pos).getId());
//            request.setReeCoachId(int_ReecaochID);
//        }else {
//            int pos=spinner_reecoach_list.getSelectedItemPosition();
//            int_ReecaochID= Integer.parseInt(arylst_chanrge_group_data.get(pos).getId());
//            request.setReeCoachId(int_ReecaochID);
//        }

        if (boolean_FROM_SERVICE){
            AppoinmentRequest request1 = new AppoinmentRequest();
            request1.setDate(date);
            request1.setComment(comment);
            request1.setTime(time);
            request1.setReCoachID(coachId);
            request1.setEndTime(endTime);
            request1.setVenuTypeType(venue);
            request1.setUserId(userId);
            if (boolean_FROM_SERVICE){
                request.setApptID(0);

            }else {

            }


            Call<AppoinmentResponse> call = appoinmentService.sentAppoinmentRequest(request1);
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


                            ApponmentEditInterface listener = (ApponmentEditInterface) getActivity();

                            if (appoinmentResponse.getData().isPay()){
                                Intent intent=new Intent(context, PaymentWebviewActivity.class);
                                intent.putExtra("ShortUrl",appoinmentResponse.getData().getPayLink());
                                intent.putExtra("from","Reecoach");
                                startActivity(intent);
                                startActivityForResult(intent, 3000);
                                listener.onEdit(dataModel);
                                dismiss();
                            }else {
                                Toast.makeText(context,
                                        "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                                listener.onEdit(dataModel);
                                dismiss();
                            }










                        }
                        else
                        {
                            Toast.makeText(context,
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

        }else {
            Call<AppoinmentEditResponse> call = appoinmentService.editAppoinmentResponse(request);
            call.enqueue(new Callback<AppoinmentEditResponse>()
            {
                @Override
                public void onResponse(Call<AppoinmentEditResponse> call, Response<AppoinmentEditResponse> response)
                {
                    utils.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        AppoinmentEditResponse appoinmentResponse = response.body();

                        if (appoinmentResponse != null && appoinmentResponse.getCode() == 1)
                        {
                            Toast.makeText(context,
                                    "" + appoinmentResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            ApponmentEditInterface listener = (ApponmentEditInterface) getActivity();
                            listener.onEdit(dataModel);
                            dismiss();
                        }
                        else
                        {
                            Toast.makeText(context,
                                    "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AppoinmentEditResponse> call, Throwable t)
                {
                    utils.hideProgressbar();
                }
            });
        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        clearvariables();

        //lsDate = String.format(Locale.getDefault(), "%02d | %02d | %04d", dayOfMonth, month + 1, year);
        //lsDate = String.format(Locale.getDefault(), "%04d| %02d | %02d |", year, month + 1, dayOfMonth);

        lsDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
//        lsDate=formatDatesServer(lsDate);

        String selecteddate=year+"-"+dayOfMonth+"-"+(month+1);

//        lsDate=formatDates(selecteddate,true);
        if (!TextUtils.isEmpty(lsDate))
            textViewDate.setText(formatDates(selecteddate,true));

        // change date format for webAPI
        //lsDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
        /*lsDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);*/
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
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    lsTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsTime))
                        textViewTime.setText(lsTime);

                    /* increment the time by 30 mins */
                    if (!TextUtils.isEmpty(lsTime))
                    {
                        cal.add(Calendar.MINUTE, 30);
                        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                        lsEndTime = formatter.format(cal.getTime());

                        if (!TextUtils.isEmpty(lsEndTime))
                        {
                            tvEndTime.setText(lsEndTime);
                        }
                    }
                }
                else
                {
                    //it's before current'
                    Toast.makeText(getContext(), "Invalid Time", Toast.LENGTH_LONG).show();
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
                formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                lsTime = formatter.format(cal.getTime());

                if (!TextUtils.isEmpty(lsTime))
                    textViewTime.setText(lsTime);

                /* increment the time by 30 mins */
                if (!TextUtils.isEmpty(lsTime))
                {
                    cal.add(Calendar.MINUTE, 30);
                    formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                    lsEndTime = formatter.format(cal.getTime());

                    if (!TextUtils.isEmpty(lsEndTime))
                    {
                        tvEndTime.setText(lsEndTime);
                    }
                }
                // change date format for webAPI
                //lsTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            }
        }
        else
        {
            Toast.makeText(context, "Date has not selected.", Toast.LENGTH_LONG).show();
        }
    }
    public void clearvariables()
    {
        lsTime = "";
        lsEndTime = "";
        textViewTime.setText("");
        tvEndTime.setText("");
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


    public String formatDatesSelect(String dateFromServer, boolean b)
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
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    private void callGroupApi()
    {
        Call<ClsChargableMain> call = appoinmentService.getNutritionist(Integer.parseInt(strrollID),liUserId);
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

                        if (strrollID.equalsIgnoreCase("3")){
                            int_appointment=appoinmentResponse.getReecoachVisitLeft();
                            if (appoinmentResponse.getReecoachVisitLeft()<1){

                                if (isFreeFrom){
                                    btnSave.setText("Submit");

                                }else {
                                    btnSave.setText("Make Payment");

                                }



                            }else {
                                btnSave.setText("Submit");

                            }
                        }


                        if (strrollID.equalsIgnoreCase("4")){
                            int_appointment=appoinmentResponse.getPathoVisitLeft();
                            if (appoinmentResponse.getPathoVisitLeft()<1){

                                if (isFreeFrom){
                                    btnSave.setText("Submit");
                                }else {
                                    btnSave.setText("Make Payment");
                                }



                            }else {
                                btnSave.setText("Submit");

                            }
                        }















                    }
                    else
                    {

//                        Toast.makeText(context, "" + appoinmentResponse.getMessage(), Toast.LENGTH_LONG).show();
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

}
