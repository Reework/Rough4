package com.shamrock.reework.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.activity.activtyhistory.Activities;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.RepeatActivityListAdapter;
import com.shamrock.reework.adapter.AutoSuggestAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.common.DurationTimePickDialog;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;
import com.shamrock.reework.model.MasterActivty.Data;
import com.shamrock.reework.util.MultiSelectionSpinner;
import com.shamrock.reework.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivtyDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener
        , MultiSelectionSpinner.OnMultipleItemsSelectedListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener
{

    private MedicineService medService;
    private Utils utils;
    private static final String SEPARATOR = ",";
    private String errorMsg;

    AddActivtyDialogFragment dialogFragment;
    String[] frequency_whenArray ;
    String[] frequency_whenArray_api ;
    ArrayAdapter<String> adapter_whens;
    String StrActivityType="";
    TextView text_time_acrtivty;
    int strMinutes=0;
    Data activitlist;
    TextView txt_add_activity_date;
    Button but_add_activity;
    private String activtyID="";
    private String subActivtyID="";
    private  String toatalMin="0";
    private String id;
    MasterMyActivityFragment masterMyActivityFragment;

    AddActivityDialogListener addActivityDialogListener;
    boolean idCall;
    private DatePickerDialog datepicker_add__history;
    private String dummyaddhistoryActshowdate="";
    private String submitAddActivityHistoryDate="";
    private TimePickerDialog timepickerdialog_activity_time;
    private String str_activty_clock_time="";
    private boolean isEditAgainData;
    private BottomSheetDialog bDialog;

    ArrayList<AcivityHistory> acivityHistories_repeat;

    @SuppressLint("ValidFragment")
    public AddActivtyDialogFragment(MasterMyActivityFragment masterMyActivityFragment) {
        this.masterMyActivityFragment=masterMyActivityFragment;
        addActivityDialogListener= (AddActivityDialogListener) masterMyActivityFragment;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,0);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        if (strFromActivityTime.equalsIgnoreCase("yes")){

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            Format formatter;
            formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
            String lsTimeFrom = formatter.format(cal.getTime());
            text_time_clock_activity.setText(lsTimeFrom);
            strFromActivityTime="";
            return;


        }



        strMinutes=(hourOfDay*60)+minute;
        toatalMin= String.valueOf((hourOfDay*60)+minute);
        if (strMinutes>0){
            if (hourOfDay>0){
                String strhours="";
                if (hourOfDay==1){
                    strhours=" Hour ";
                }else {
                    strhours=" Hours ";

                }



                if (minute==0){
                    String time=hourOfDay+strhours;
                    text_time_acrtivty.setText(time);
                }else {
                    String time=hourOfDay+strhours+minute+" Mins";
                    text_time_acrtivty.setText(time);
                }


            }

            else {
                String time=minute+" min";
                text_time_acrtivty.setText(time);

            }

        }else {
            Toast.makeText(context, "Invalid time", Toast.LENGTH_SHORT).show();
        }








    }

//    public interface AddActivityDialogListener
//    {
//
//        void onActivityAdd(String ActivityID,String SubActivtyID,String hours,String ID);
//
//        void onActivityEdit(String ActivityID,String SubActivtyID,String hours,String ID);
//
//    }

    Context context;
    AutoCompleteTextView actv;
    TextView textViewTitle;
    Button btnSubmit;
    Spinner spinner_activity_type;
    Spinner spinner_sub_Activity;
    MultiSelectionSpinner spinnerDays;
    List<Integer> indices;
    ImageView imgClose;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;

    String medicineName;
    int userId;
    int medicineId = 0;
    String frequency = "Select frequency";



    private boolean isEditData;
    private MyMedicine myMedicine;
    private int myMedicineID;
    ArrayList<String> stringArrayListActivty;
    ArrayList<String> stringArrayListSubActivty;
    ArrayList<String> stringArrayListSubActivtYid;
    TimePickerDialog timepickerdialog;
    AcivityHistory AcivityHistory;
    String ids="0";
    ArrayAdapter<String> adapters_activty;
    ArrayAdapter<String> adapters_subactivty;
    TextView text_time_clock_activity;
    String strFromActivityTime="";
    RecyclerView recylr_repeat_activity_list;
    TextView txt_activity_name_new,txt_activity_time_new,txt_activity_duration_new;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        indices = new ArrayList<>();
        medService = Client.getClient().create(MedicineService.class);
        utils = new Utils();


    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        bDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

//        View view = inflater.inflate(R.layout.dialog_fragment_add_activity, container, false);
        Bundle bundle = getArguments();
        isEditData = bundle.getBoolean("isEdit", false);
        final View view;
        if (isEditData){
             view = View.inflate(getContext(), R.layout.dialog_fragment_update_activity, null);


            txt_activity_duration_new=view.findViewById(R.id.txt_activity_duration_new);
            txt_activity_time_new=view.findViewById(R.id.txt_activity_time_new);
            txt_activity_name_new=view.findViewById(R.id.txt_activity_name_new);


        }else {
             view = View.inflate(getContext(), R.layout.dialog_fragment_add_activity, null);

        }

        bDialog.setContentView(view);
        recylr_repeat_activity_list=view.findViewById(R.id.recylr_repeat_activity_list);
        if (bundle != null)
        {
            AcivityHistory = (AcivityHistory) bundle.getSerializable("myActivity");
            activitlist= (Data) bundle.getSerializable("activtylist");
            if ( (ArrayList<AcivityHistory>) bundle.getSerializable("Repeatactivtylist")!=null){
                acivityHistories_repeat= (ArrayList<AcivityHistory>) bundle.getSerializable("Repeatactivtylist");

                recylr_repeat_activity_list.setHasFixedSize(true);
                recylr_repeat_activity_list.setAdapter(new RepeatActivityListAdapter(AddActivtyDialogFragment.this, acivityHistories_repeat));

            }

        }
        try {
            Field behaviorField = bDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bDialog);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING){
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



        textViewTitle = view.findViewById(R.id.labelMedicineTitle);
        text_time_acrtivty = view.findViewById(R.id.text_time_acrtivty);
        text_time_clock_activity = view.findViewById(R.id.text_time_clock_activity);
        but_add_activity = view.findViewById(R.id.but_add_activity);
        frequency_whenArray= getResources().getStringArray(R.array.medicine_frequency_when);
        adapter_whens  = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, frequency_whenArray);
        actv = view.findViewById(R.id.edtText_NameMedicine);
        imgClose = view.findViewById(R.id.imageView_AddMedicine_CloseDialog);
        spinnerDays = view.findViewById(R.id.spinnerAddMedicine_Days);
        spinner_activity_type = view.findViewById(R.id.spinner_activity_type);
        spinner_sub_Activity = view.findViewById(R.id.spinner_sub_Activity);
        txt_add_activity_date = view.findViewById(R.id.txt_add_activity_date);


        text_time_clock_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFromActivityTime="yes";
                timepickerdialog_activity_time = new TimePickerDialog(context, AddActivtyDialogFragment.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);
                timepickerdialog_activity_time.show();


            }
        });
        text_time_acrtivty.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        but_add_activity.setOnClickListener(this);
        txt_add_activity_date.setOnClickListener(this);

        stringArrayListActivty = new ArrayList<>();
        stringArrayListSubActivty=new ArrayList<>();
        stringArrayListSubActivtYid=new ArrayList<>();

//        getDialog().setCancelable(true);
        Calendar calendar = Calendar.getInstance();

        if(isEditData){

            int totalSecs= Integer.parseInt(AcivityHistory.getTotalMinutes())*60;

            int hours = totalSecs / 3600;
            int minutes = (totalSecs % 3600) / 60;
            timepickerdialog = new TimePickerDialog
                    (context, AddActivtyDialogFragment.this,
                            hours,
                            minutes,
                            true);
        }else {
            if (context!=null)
//            timepickerdialog = new DurationTimePickDialog(context, AddActivtyDialogFragment.this,
//                    0,
//                    1,
//                    true);

                timepickerdialog = new TimePickerDialog(context, AddActivtyDialogFragment.this,
                        0,
                        1,
                        true);


        }

        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


            }
        });

        timepickerdialog.setTitle("Select Time Spend in Activity");
//        timepickerdialog.updateTime(5,30);



//showDatePickerHistoryAddDailog();


        bindActivtylistToSpinner();

        bindSubActivtyListToSpinner();


        imgClose.setOnClickListener(this);

        if (!isEditData) {


            textViewTitle.setText("Add Activity");
            spinner_sub_Activity.setEnabled(true);
            spinner_activity_type.setEnabled(true);
        } else {
            textViewTitle.setText("Update Activity");
//            spinner_activity_type.setEnabled(false);
//            spinner_sub_Activity.setEnabled(false);
        }
        if (!isEditData) {



        } else {


            if (AcivityHistory!=null){

//              new Handler().postDelayed(new Runnable() {
//                  @Override
//                  public void run() {
                ids=AcivityHistory.getId();
                activtyID=AcivityHistory.getActivityId();
                subActivtyID=AcivityHistory.getSubActivityId();
                toatalMin=AcivityHistory.getTotalMinutes();
                text_time_clock_activity.setText(AcivityHistory.getActivityTime());

                txt_activity_name_new.setText(AcivityHistory.getActivityName());
                txt_activity_time_new.setText(AcivityHistory.getActivityTime());



                if (AcivityHistory.getTotalMinutes()!=null){
                    if (Integer.parseInt(AcivityHistory.getTotalMinutes())<60){
                        text_time_acrtivty.setText(AcivityHistory.getTotalMinutes()+ " Mins");
                        txt_activity_duration_new.setText(AcivityHistory.getTotalMinutes()+ " Mins");


                    }else {
                        text_time_acrtivty.setText(formatHoursAndMinutesnew(Integer.parseInt(AcivityHistory.getTotalMinutes())));
                        txt_activity_duration_new.setText(formatHoursAndMinutesnew(Integer.parseInt(AcivityHistory.getTotalMinutes())));


                    }
                }



                idCall=true;


                for (int i = 0; i <stringArrayListActivty.size(); i++) {

                    if (AcivityHistory.getActivityName().equalsIgnoreCase(stringArrayListActivty.get(i).toString())){
                        idCall=true;
                        spinner_activity_type.setSelection(i);
                        break;

                    }

                }





                stringArrayListSubActivty.clear();

                if(activitlist!=null){
                    if (activitlist.getSubActivities()!=null){
                        for (int j = 0; j <activitlist.getSubActivities().size() ; j++) {
                            if (AcivityHistory.getActivityId().equals(activitlist.getSubActivities().get(j).getActivityId())){
                                stringArrayListSubActivty.add(activitlist.getSubActivities().get(j).getSubActivityName());
                            }

                        }
                        adapters_subactivty = new ArrayAdapter<>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, stringArrayListSubActivty);
                        spinner_sub_Activity.setAdapter(adapters_subactivty);

                        for (int k = 0; k <stringArrayListSubActivty.size(); k++) {

                            if (AcivityHistory.getSubActivityName().equalsIgnoreCase(stringArrayListSubActivty.get(k).toString())){
                                spinner_sub_Activity.setSelection(k);

                                break;
                            }


                        }
                    }

                }






//                  }
//              },100);

            }






        }



        return bDialog;
    }
    public void addRepeatActivityData(ArrayList<AcivityHistory> dataResponseList_repeat){

        ArrayList<AcivityHistory> dataResponseList_repeat_new;

        dataResponseList_repeat_new = new ArrayList<>();
        for (int i = 0; i < dataResponseList_repeat.size(); i++) {
            if (dataResponseList_repeat.get(i).isSelect()) {
                dataResponseList_repeat_new.add(dataResponseList_repeat.get(i));
            }
        }

        if (dataResponseList_repeat_new.isEmpty()){
            Toast.makeText(context, "Please select activity", Toast.LENGTH_SHORT).show();
            return;
        }
//                                            dialog.dismiss();


        masterMyActivityFragment.addRepeatActivityAPi(dataResponseList_repeat_new);

        SessionManager sessionManager=new SessionManager(context);
        if (sessionManager.getStringValue("DialogClose").equalsIgnoreCase("true")){
            sessionManager.setStringValue("DialogClose","");
            Intent intent=new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }

//        addRepeatActivityAPi(dataResponseList_repeat_new);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return super.onCreateView(inflater, container, savedInstanceState);
    }



    private void bindSubActivtyListToSpinner() {


        try {
            stringArrayListSubActivty=new ArrayList<>();
            stringArrayListSubActivtYid=new ArrayList<>();
            stringArrayListSubActivtYid.clear();

            if (activitlist.getSubActivities()!=null){
                for (int j = 0; j <activitlist.getSubActivities().size() ; j++) {
                    if (activtyID.equals(activitlist.getSubActivities().get(j).getActivityId())){
                        stringArrayListSubActivty.add(activitlist.getSubActivities().get(j).getSubActivityName());
                        stringArrayListSubActivtYid.add(activitlist.getSubActivities().get(j).getSubActivityId());
                    }

                }


                adapters_subactivty = new ArrayAdapter<>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, stringArrayListSubActivty);
                spinner_sub_Activity.setAdapter(adapters_subactivty);


            }










            spinner_sub_Activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {



                    subActivtyID=stringArrayListSubActivtYid.get(i).toString();



                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindActivtylistToSpinner() {

        try {

            if (activitlist.getActivities()!=null){
                for (int i = 0; i <activitlist.getActivities().size() ; i++) {
                    stringArrayListActivty.add(activitlist.getActivities().get(i).getActivityName().toString());

                }
                adapters_activty = new ArrayAdapter<>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, stringArrayListActivty);
                spinner_activity_type.setAdapter(adapters_activty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




        try {
            spinner_activity_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {


                    String StrActivityID=activitlist.getActivities().get(i).getActivityId().toString();
                    activtyID=activitlist.getActivities().get(i).getActivityId().toString();
                    bindSubActivtyListToSpinner();


//                    if (isEditData){
//                        String activID=AcivityHistory.getActivityId();
//                        if (activID.equalsIgnoreCase(activtyID)){
//                            isEditData=false;
//                            isEditAgainData=true;
//                        }
//                    }



                    if (isEditData){

                        stringArrayListSubActivty.clear();

                        for (int j = 0; j <activitlist.getSubActivities().size() ; j++) {
                            if (activtyID.equals(activitlist.getSubActivities().get(j).getActivityId())){
                                stringArrayListSubActivty.add(activitlist.getSubActivities().get(j).getSubActivityName());
                            }

                        }




                        adapters_subactivty = new ArrayAdapter<>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, stringArrayListSubActivty);
                        spinner_sub_Activity.setAdapter(adapters_subactivty);
                        for (int k = 0; k <stringArrayListSubActivty.size(); k++) {

                            if (AcivityHistory.getSubActivityName().equalsIgnoreCase(stringArrayListSubActivty.get(k).toString())){
                                spinner_sub_Activity.setSelection(k);

                                break;
                            }


                        }

                    }







                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void selectedIndices(List<Integer> indices) {
        this.indices = indices;
        if (indices.size() > 0) {
            StringBuilder strIds = new StringBuilder();
            for (int city : indices) {
                strIds.append((city + 1));
                strIds.append(SEPARATOR);
            }
        } else {
        }
    }

    @Override
    public void selectedStrings(List<String> strings) {
    }

    private void showDatePickerHistoryAddDailog() {
        String strMindate[]= new SessionManager(context).getStringValue("mindate").split("-");



        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepicker_add__history = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, AddActivtyDialogFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1]),Integer.parseInt(strMindate[0]));
        c.setTime(today);
        c.add(Calendar.MONTH, 3);
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());
        datepicker_add__history.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
        datepicker_add__history.getDatePicker().setMinDate(c1.getTimeInMillis());
        datepicker_add__history.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepicker_add__history.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepicker_add__history.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {


            case R.id.txt_add_activity_date:

                datepicker_add__history.show();


                break;



            case R.id.but_add_activity:




                boolean isFoundRepeatActivity=false;
                if (!isEditData){
                    for (int i = 0; i < acivityHistories_repeat.size(); i++) {
                        if (acivityHistories_repeat.get(i).isSelect()) {
                            isFoundRepeatActivity=true;
                        }
                    }
                }else {
                    isFoundRepeatActivity=false;
                }


                if (isFoundRepeatActivity){
                    addRepeatActivityData(acivityHistories_repeat);
                    dismiss();

                }else {

                    boolean dataIsValid = validateAllData(medicineId, frequency, "",4,StrActivityType);
                    if (dataIsValid)
                    {
                        this.dismiss();
                        if (isEditData)
                        {
                            updateData(activtyID, subActivtyID,toatalMin,ids,text_time_clock_activity.getText().toString().trim());
                        }
                        else
                        {

                            if (activtyID.isEmpty()){
                                Toast.makeText(context, "Please select activity type", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (subActivtyID.isEmpty()){
                                Toast.makeText(context, "Please select sub activity type", Toast.LENGTH_SHORT).show();
                                return;

                            }
                            if (text_time_clock_activity.getText().toString().isEmpty()){
                                Toast.makeText(context, "Please select  activity time", Toast.LENGTH_SHORT).show();
                                return;

                            }

                            sendData(activtyID, subActivtyID,toatalMin,ids,text_time_clock_activity.getText().toString().trim());
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }








                break;

            case R.id.imageView_AddMedicine_CloseDialog:
                this.dismiss();
                break;



            case R.id.text_time_acrtivty:
                timepickerdialog.show();
                break;




            default:
        }
    }



    private void sendData(String ActivtyID, String SubActivtyID, String Min, String ID, String activityclocktime)
    {
//        AddActivityDialogListener listener = masterMyActivityFragment;
        addActivityDialogListener.onActivityAdd(ActivtyID,SubActivtyID, Min,ID,activityclocktime);

        SessionManager sessionManager=new SessionManager(context);
        if (sessionManager.getStringValue("DialogClose").equalsIgnoreCase("true")){
            sessionManager.setStringValue("DialogClose","");
            Intent intent=new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }

    }

    private void updateData(String ActivtyID, String SubActivtyID, String Min, String ID, String activityclocktime)
    {
        addActivityDialogListener.onActivityEdit(ActivtyID,SubActivtyID, Min,ID,activityclocktime);
    }




    private boolean validateAllData(int medicineId, String frequency, String days, int when, String fromFrequency) {
        boolean valid = true;


        if (toatalMin.equals("0")){
            valid = false;
            errorMsg = "Select Total Time Spend In Activity";
            return valid;
        }




        if (toatalMin.isEmpty()){
            valid = false;
            errorMsg = "Select Total Time Spend In Activity";
            return valid;
        }
        if (!toatalMin.isEmpty())
        {
            if (Integer.parseInt(toatalMin)<1){

                valid = false;
                errorMsg = "Invalid Time";
                return valid;

            }
        }

        if (text_time_clock_activity.getText().toString().isEmpty()){
            valid = false;
            errorMsg = "Select Activity Time";
            return valid;
        }



//        if (medicineId == 0) {
//            valid = false;
//            errorMsg = "Select Activty";
//            return valid;
//
//        }
//        if (days.isEmpty()) {
//            valid = false;
//            errorMsg = "Select Sub Activity";
//            return valid;
//
//        }
//        if (frequency.equalsIgnoreCase("Select Activity")) {
//            valid = false;
//            errorMsg = "Select Activity";
//            return valid;
//        }










        return valid;
    }
    private void hideAmPmLayout(TimePickerDialog picker) {
        final int id = Resources.getSystem().getIdentifier("ampm_layout", "id", "android");
        final View amPmLayout = picker.findViewById(id);
        if(amPmLayout != null) {
            amPmLayout.setVisibility(View.GONE);
        }
    }
    public  String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + ":" + minutes;
    }
    public  String formatHoursAndMinutesnew(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" Hour ";
        }else {
            strhours=" Hours ";

        }
        return (totalMinutes / 60) + strhours + minutes+" Mins";
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dummyaddhistoryActshowdate = dayOfMonth + "-" + (month + 1) + "-" + year;

        txt_add_activity_date.setText(dummyaddhistoryActshowdate);
        submitAddActivityHistoryDate=year + " " + (month + 1) + " " + dayOfMonth;
//                submitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());





    }
}
