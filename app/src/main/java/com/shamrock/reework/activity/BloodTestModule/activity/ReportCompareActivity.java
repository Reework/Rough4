package com.shamrock.reework.activity.BloodTestModule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.adapter.PlannerAdapter;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.Pathologists.SelectPathoActivity;
import com.shamrock.reework.activity.healthmonitoring.NewHealthMonitoringActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportCompareActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    private final int GALLERY = 1;
    private ArrayList<HashMap<String, String>> arraylist;
    boolean isFile=true;
    RecyclerView recycler_report_compare;



    Context context;

    private ReeTestService reeTestService;
    private SessionManager sessionManager;
    private Utils utils;
    private int userID;

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;

    TextView txt_sleep_date_to_dialog;
    TextView txt_sleep_date_from_dialog;
    TextView txt_show_sleep_from;
    TextView txt_show_sleep_to;
    TextView txt_no_water_history;
    private String StrDateOpen;
    private String dummydate_from;
    private String dummydate_to;
    private String submitToDate;
    private String submitFromDate;
    LinearLayout ll_choosedate;
    private DatePickerDialog datepickerdialog;
    private String currentDateandTime;
    private Utils util;
    private DatePickerDialog datepickerdialog_entry;
    int testType=2;
    RadioButton txt_patho,txt_bca,txt_reescore;
    boolean isRescore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reemonitor_compare);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEports");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportCompareActivity.this, AllReportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0,0);

                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
        txt_patho=findViewById(R.id.txt_patho);
        txt_reescore=findViewById(R.id.txt_reescore);
        txt_patho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testType=1;
                isRescore=false;
                CallOtherTestReports(submitFromDate,submitToDate);

            }
        });
        txt_bca=findViewById(R.id.txt_bca);
        txt_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testType=2;
                isRescore=false;
                CallOtherTestReports(submitFromDate,submitToDate);

            }
        });

        txt_reescore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testType=2;
                isRescore=true;
                CallReescoreReports(submitFromDate,submitToDate);

            }
        });


        recycler_report_compare=findViewById(R.id.recycler_report_compare);
        context=ReportCompareActivity.this;
        sessionManager = new SessionManager(context);
        reeTestService = Client.getClient().create(ReeTestService.class);
        utils = new Utils();
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        CallOtherTestReports(submitFromDate,submitToDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());

        SimpleDateFormat sdfss = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDateandTimess = sdfss.format(new Date());
        ll_choosedate = findViewById(R.id.ll_choosedate);
        util = new Utils();
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        sessionManager = new SessionManager(ReportCompareActivity.this);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        txt_show_sleep_from = findViewById(R.id.txt_show_sleep_from);
        txt_show_sleep_to = findViewById(R.id.txt_show_sleep_to);
//        txt_weight_date_from = findViewById(R.id.txt_weight_date_from);
//        txt_weight_date_to = findViewById(R.id.txt_weight_date_to);txt_weight_date_to




        dummydate_from = formatDatesSleep(submitFromDate);
        txt_show_sleep_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
        txt_show_sleep_to.setText(dummydate_to);
        showDatePickerDailog();
        showDatePickerDailogentry();
        ll_choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetSleephistroy();
            }
        });

    }
    private void showNewgetSleephistroy() {

        final Dialog dialog = new Dialog(ReportCompareActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date_new);
        txt_sleep_date_from_dialog = dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog = dialog.findViewById(R.id.txt_sleep_date_to_dialog);


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(ReportCompareActivity.this)) {

                    dialog.dismiss();

                    if (isRescore){
                        CallReescoreReports(submitFromDate,submitToDate);
                    }else {
                        CallOtherTestReports(submitFromDate,submitToDate);

                    }

                    txt_show_sleep_from.setText(dummydate_from);
                    txt_show_sleep_to.setText(dummydate_to);


//                    getHistoryParameterData();
                } else {

                }

            }
        });


        txt_show_sleep_from.setText(dummydate_from);
        txt_sleep_date_from_dialog.setText(dummydate_from);
        txt_show_sleep_to.setText(dummydate_to);
        txt_sleep_date_to_dialog.setText(dummydate_to);


        txt_sleep_date_from_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "from";
                datepickerdialog.show();

            }
        });

        txt_sleep_date_to_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen = "to";
                datepickerdialog.show();
            }
        });

        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }


    private void showDatePickerDailog() {
        String strMindate[] = new SessionManager(ReportCompareActivity.this).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(ReportCompareActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, ReportCompareActivity.this, year, month, day);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();



        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }

    @SuppressLint("NewApi")
    private void showDatePickerDailogentry() {
        String strMindate[] = new SessionManager(ReportCompareActivity.this).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_entry = new DatePickerDialog(ReportCompareActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, ReportCompareActivity.this, year, month, day);

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

                dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;



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
    public String formatDatesSleep(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    private void CallOtherTestReports(String fromdate,String todate)
    {
        utils.showProgressbar(context);
//        http://shamrockuat.dweb.in/api/HealthMonitor/ReemonitorSummary?UserId=3040&ReportTypeId=1&From=2010-01-01&To=2021-01-01

//        Call<ResponseBody> call = reeTestService.getCompareReportList(userID,2,"2019-01-01","2021-01-01");

        Call<ResponseBody> call = reeTestService.getCompareReportList(userID,testType,fromdate,todate);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    utils.hideProgressbar();
                    int count=0;
                    ResponseBody listResponse = response.body();
                    ArrayList<String> arylst_key=null;
                    ArrayList<String> arylst_result=new ArrayList<>();
                    try {
                        String res = response.body().string();
                        JSONObject obj = new JSONObject(res);


                        JSONArray jsonArray = obj.getJSONArray("Data");


                        JSONObject object = jsonArray.getJSONObject(0);
                        StringBuilder stringBuilder=new StringBuilder();
                        arylst_key=new ArrayList<>();
                        for (String key : iterate(object.keys()))
                        {
                            stringBuilder.append(key).append(",");
                            arylst_key.add(key);
                            // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.

                        }
                        arylst_result.add(0,stringBuilder.substring(0,stringBuilder.length()-1));

                        for (int i = 0; i <jsonArray.length() ; i++) {
                            count=0;

                            JSONObject object1 = jsonArray.getJSONObject(i);
                            StringBuilder stringBuilder1=new StringBuilder();

                            for (int j = 0; j <arylst_key.size() ; j++) {
                                String data=object1.getString(arylst_key.get(j).toString());
                                if (!data.equalsIgnoreCase("null")){
                                    stringBuilder1.append(data).append(",");
                                }else {
                                    stringBuilder1.append("-").append(",");
                                }

                                count++;

                            }

                            arylst_result.add(stringBuilder1.toString().substring(0,stringBuilder1.length()-1));





                        }


                        PlannerAdapter clsReportCompareAdapter=new PlannerAdapter(ReportCompareActivity.this,arylst_result,count);
                        recycler_report_compare.setAdapter(clsReportCompareAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }


    private void CallReescoreReports(String fromdate,String todate)
    {
        utils.showProgressbar(context);
//        http://shamrockuat.dweb.in/api/HealthMonitor/ReemonitorSummary?UserId=3040&ReportTypeId=1&From=2010-01-01&To=2021-01-01

//        Call<ResponseBody> call = reeTestService.getCompareReportList(userID,2,"2019-01-01","2021-01-01");

        Call<ResponseBody> call = reeTestService.getReeScoreReprtCompare(userID,testType,fromdate,todate);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    utils.hideProgressbar();
                    int count=0;
                    ResponseBody listResponse = response.body();
                    ArrayList<String> arylst_key=null;
                    ArrayList<String> arylst_result=new ArrayList<>();
                    try {
                        String res = response.body().string();
                        JSONObject obj = new JSONObject(res);


                        JSONArray jsonArray = obj.getJSONArray("Data");


                        JSONObject object = jsonArray.getJSONObject(0);
                        StringBuilder stringBuilder=new StringBuilder();
                        arylst_key=new ArrayList<>();
                        for (String key : iterate(object.keys()))
                        {
                            stringBuilder.append(key).append(",");
                            arylst_key.add(key);
                            // here key will be containing your OBJECT NAME YOU CAN SET IT IN TEXTVIEW.

                        }
                        arylst_result.add(0,stringBuilder.substring(0,stringBuilder.length()-1));

                        for (int i = 0; i <jsonArray.length() ; i++) {
                            count=0;

                            JSONObject object1 = jsonArray.getJSONObject(i);
                            StringBuilder stringBuilder1=new StringBuilder();

                            for (int j = 0; j <arylst_key.size() ; j++) {
                                String data=object1.getString(arylst_key.get(j).toString());
                                if (!data.equalsIgnoreCase("null")){
                                    stringBuilder1.append(data).append(",");
                                }else {
                                    stringBuilder1.append("-").append(",");
                                }

                                count++;

                            }

                            arylst_result.add(stringBuilder1.toString().substring(0,stringBuilder1.length()-1));





                        }


                        PlannerAdapter clsReportCompareAdapter=new PlannerAdapter(ReportCompareActivity.this,arylst_result,count);
                        recycler_report_compare.setAdapter(clsReportCompareAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ReportCompareActivity.this, "No data available", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
                Toast.makeText(ReportCompareActivity.this, "No data available", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private <T> Iterable<T> iterate(final Iterator<T> i){
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        if (StrDateOpen.equalsIgnoreCase("weightEntry")) {
            dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;
//            todaysubmitHistoryDate=year+"-"+(month+1)+"-"+dayOfMonth;

//            yyyy-MM-dd

//            txt_weight_date_select.setText(dummydate_from);
        }


        if (StrDateOpen.equalsIgnoreCase("from")) {

            if (dayOfMonth < 10) {
                dummydate_from = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;

            } else {
                dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;

            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = simpleDateFormat.parse(dummydate_from);
                date2 = simpleDateFormat.parse(dummydate_to);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date1.after(date2)) {
                Toast.makeText(ReportCompareActivity.this, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_sleep_date_from_dialog.setText(dummydate_from);
            submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        }
        if (StrDateOpen.equalsIgnoreCase("to")) {
            if (!dummydate_from.trim().isEmpty()) {
                if (dayOfMonth < 10) {
                    dummydate_to = "0" + dayOfMonth + "-" + (month + 1) + "-" + year;

                } else {
                    dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;

                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = simpleDateFormat.parse(dummydate_from.trim());
                    date2 = simpleDateFormat.parse(dummydate_to.toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date1.after(date2)) {
                    Toast.makeText(ReportCompareActivity.this, "To date should be greater than From date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate = year + "-" + (month + 1) + "-" + dayOfMonth;

//                    getHistoryParameterData();

                }


            } else {
                Toast.makeText(ReportCompareActivity.this, "Please select from date", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public String getFromattedStringDate(int s) {


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }


    }
}
