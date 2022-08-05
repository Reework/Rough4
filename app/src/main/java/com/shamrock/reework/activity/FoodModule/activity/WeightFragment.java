package com.shamrock.reework.activity.FoodModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.fragment.HomeFragment;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.WeightModule.MyWeight;
import com.shamrock.reework.activity.WeightModule.OnWeightVideoListClick;
import com.shamrock.reework.activity.WeightModule.UserWeightRequest;
import com.shamrock.reework.activity.WeightModule.activity.OnUpdateWeight;
import com.shamrock.reework.activity.WeightModule.adapters.ClsMyNewWeightAdapter;
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightPojo;
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightRequest;
import com.shamrock.reework.activity.WeightModule.adapters.WeightHistory;
import com.shamrock.reework.activity.WeightModule.adapters.WeightVideoListAdapter;
import com.shamrock.reework.activity.WeightModule.service.WeightService;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.activity.water.WaterVideoListAdapter;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.GraphRequest;
import com.shamrock.reework.api.request.WeightMonthsRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.GraphResponse;
import com.shamrock.reework.api.response.WeightMonthsResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.fragment.MasterWaterFragment;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment implements OnWeightVideoListClick,OnUpdateWeight, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    MasterDetailsActivity context;
    Toolbar toolbar;
    Typeface font;
    LineChart mChart;
    TextInputLayout txt_input_initialwt,txt_input_idealwt;
    SessionManager sessionManager;
    EditText editTextCurrent, editTextIdeal;
    Spinner spinner;
    int userID;
    WeightService weightService;
    HealthParametersService healthParametersService;
    private List<String> WeightMonthsList;
    private Utils utils;
    int check = 0;
    RadioButton rd_button_weight_tips;
    private Map<String, Float> dataMain;
    private RadioButton rd_button_weight_history,rd_button_weight;
    private TextView btn_show_weight_history,txt_weight_date_to,txt_weight_date_from;
    private RecyclerView list_weight_history;
    private ViewFlipper vp_weight;
    RecyclerView recycler_weight_tips;

    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    private OnFragmentInteractionListener mListener;


    Button buttonSubmitWeight;
    EditText editText_current_user_weight;
    private DatePickerDialog datepickerdialog;
    private String StrDateOpen="";
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView  txt_no_weight;
    private Utils util;
    private HomeFragmentService services;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_food_tips;
    private TextView txt_weight_date_select;
    private DatePickerDialog datepickerdialog_weight_entry;

    private String strFromEntry="";
    private String todaysubmitHistoryDate="";


    private RadioButton rd_weight_video;
    RadioButton rd_button_sleep_video;
    private RecyclerView recylcer_spiritual_list;
    private SessionManager session;
    TextView txt_no_data_spiritual;
    private CommonService commonService;
    TextView txt_sleep_date_to_dialog;
    TextView txt_sleep_date_from_dialog;
    TextView txt_show_sleep_from;
    TextView txt_show_sleep_to;
    TextView txt_sleep_date_from;
    TextView txt_sleep_date_to;

    LinearLayout ll_choosedate;


    public WeightFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = (MasterDetailsActivity) context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }


    public static Fragment newInstance(String camera, String s) {

        WeightFragment fragment = new WeightFragment();
        Bundle args = new Bundle();


        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weight, container, false);
        vp_weight=view.findViewById(R.id.vp_weight);

        rd_button_weight_tips=view.findViewById(R.id.rd_button_weight_tips);
        ll_choosedate=view.findViewById(R.id.ll_choosedate);
        ll_choosedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetSleephistroy();
            }
        });
        recycler_weight_tips=view.findViewById(R.id.recycler_weight_tips);
        txt_weight_date_select=view.findViewById(R.id.txt_weight_date_select);
        todaysubmitHistoryDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        txt_show_sleep_from = view.findViewById(R.id.txt_show_sleep_from);
        txt_show_sleep_to = view.findViewById(R.id.txt_show_sleep_to);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        dummydate_from = formatDatesSleep(submitFromDate);
        txt_show_sleep_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
        txt_show_sleep_to.setText(dummydate_to);


        showDatePickerDailogWeight();
        txt_weight_date_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="weightEntry";
                datepickerdialog_weight_entry.show();
            }
        });
        util=new Utils();

        init();
        findViews(view);
        editText_current_user_weight.clearFocus();
        view.requestFocus();
//        callToFetchWeightTipsMasterData();


        rd_weight_video=view.findViewById(R.id.rd_weight_video);

        recylcer_spiritual_list=view.findViewById(R.id.recylcer_spiritual_list);
        txt_no_data_spiritual=view.findViewById(R.id.txt_no_data_spiritual);

//        getSpitualListAPiByID(6,"Weight videos ");
        rd_weight_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpitualListAPiByID(6,"Weight videos ");

                vp_weight.setDisplayedChild(3);
            }
        });
        return view;
    }

    private void showDatePickerDailogWeight() {
        sessionManager=new SessionManager(getActivity());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog_weight_entry = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, WeightFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog.getDatePicker().setMaxDate(maxDate);

        String strMindate[]= new SessionManager(getActivity()).getStringValue("mindate").split("-");



        datepickerdialog_weight_entry.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (strMindate!=null){
            if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
                try {
                    if (strMindate.length > 1) {
                        c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                try {
                    if (strMindate.length > 1) {
                        c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        datepickerdialog_weight_entry.getDatePicker().setMinDate(c1.getTimeInMillis());

        datepickerdialog_weight_entry.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog_weight_entry.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog_weight_entry.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }

    private void callToFetchWeightTipsMasterData() {

        util.showProgressbar(getActivity());
        services = Client.getClient().create(HomeFragmentService.class);


        Call<ClsSleepTips> call = services.getMasterFoodTipsData(6);
        call.enqueue(new Callback<ClsSleepTips>() {
            @Override
            public void onResponse(Call<ClsSleepTips> call, retrofit2.Response<ClsSleepTips> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsSleepTips tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {

                            if (!tipsResponse.getData().isEmpty()){
                                arylst_food_tips=new ArrayList<>();
                                arylst_food_tips.clear();
                                arylst_food_tips.addAll(tipsResponse.getData());



                                ClsSleepTipsAdapter adapter=  new ClsSleepTipsAdapter(getContext(), arylst_food_tips, "MasterSleepFragment");
                                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                                recycler_weight_tips.setLayoutManager(layoutManager1);
                                recycler_weight_tips.setItemAnimator(new DefaultItemAnimator());
                                recycler_weight_tips.setAdapter(adapter);

                            }else {
//                                Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else{

                    }
//                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else{

                }
//                    Toast.makeText(getActivity(), "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ClsSleepTips> call, Throwable t) {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }
    private void init() {
        sessionManager = new SessionManager(context);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        weightService = Client.getClient().create(WeightService.class);
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();
        submitFromDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    }
    public void hideSoftKeyboard() { if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager)context. getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void showDatePickerDailog() {
        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,WeightFragment.this, year, month, day);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();


        if (strMindate!=null){
            if (!sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
                if (strMindate.length>1){
                    c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

                }

            }

        }
//        datepickerdialog.getDatePicker().setMinDate(c1.getTimeInMillis());

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


    private void findViews(View view) {
        editTextCurrent = view.findViewById(R.id.editText_WeightCurrent);
        editTextIdeal = view.findViewById(R.id.editText_WeightIdeal);
        editText_current_user_weight = view.findViewById(R.id.editText_current_user_weight);
        rd_button_weight=view.findViewById(R.id.rd_button_weight);
        rd_button_weight_history=view.findViewById(R.id.rd_button_weight_history);
        list_weight_history=view.findViewById(R.id.list_weight_history);
        ll_weight_header=view.findViewById(R.id.ll_weight_header);
        txt_no_weight=view.findViewById(R.id.txt_no_weight);
        txt_input_idealwt=view.findViewById(R.id.txt_input_idealwt);
        txt_input_initialwt=view.findViewById(R.id.txt_input_initialwt);

        if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")){
            txt_input_initialwt.setVisibility(View.GONE);
            txt_input_idealwt.setVisibility(View.GONE);
        }


        txt_weight_date_from=view.findViewById(R.id.txt_weight_date_from);
        txt_weight_date_to=view.findViewById(R.id.txt_weight_date_to);
        btn_show_weight_history=view.findViewById(R.id.btn_show_weight_history);
        btn_show_weight_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callWeightHistoryApi(submitFromDate,submitToDate);

            }
        });
        rd_button_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_weight.setDisplayedChild(0);

                callMonthsApi(userID, false);

            }
        });

        rd_button_weight_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_weight.setDisplayedChild(1);
                callWeightHistoryApi(submitFromDate,submitToDate);

            }
        });

        rd_button_weight_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToFetchWeightTipsMasterData();
                vp_weight.setDisplayedChild(2);

            }
        });
        showDatePickerDailog();
        vp_weight.setDisplayedChild(0);

        txt_weight_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="from";
                datepickerdialog.show();
            }
        });

        txt_weight_date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";
                datepickerdialog.show();

            }
        });



        dummydate_from=formatDatesSleep(submitFromDate);
        txt_weight_date_from.setText(dummydate_from);
        dummydate_to=formatDatesSleep(submitToDate);
        txt_weight_date_to.setText(dummydate_to);
        showDatePickerDailog();
//        callWeightHistoryApi(submitFromDate,submitToDate);




        spinner = view.findViewById(R.id.spinner_Weight_Months);
        buttonSubmitWeight = view.findViewById(R.id.buttonSubmitWeight);
        buttonSubmitWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_current_user_weight.getText().toString().trim().isEmpty()){
                    hideSoftKeyboard();
                    CallWeightSubmitApi(editText_current_user_weight.getText().toString().trim());

                }else {
                    Toast.makeText(context, "Please enter the current weight.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        spinner.setOnItemSelectedListener(this);
        mChart = view.findViewById(R.id.lineChart);

        mainLayout = view.findViewById(R.id.mainLayout);
        noInternetLayout = view.findViewById(R.id.no_internet);
        btnRefresh= view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);


        if (Utils.isNetworkAvailable(context))
        {
            callMonthsApi(userID, false);
        }
        else
            showLayouts();
    }

    public String formatDatesSleep(String dateFromServer) {
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

    private void CallWeightSubmitApi(String trim) {
        UserWeightRequest request = new UserWeightRequest();
        request.setUserid(userID);

        request.setCreatedOn(sessionManager.getStringValue("statusdate"));
        utils.showProgressbar(context);
        request.setCurrentWeight(String.valueOf(editText_current_user_weight.getText().toString().trim()));
        Call<MyWeight> call = healthParametersService.sendUserHealthDetail(request);
        call.enqueue(new Callback<MyWeight>() {
            @Override
            public void onResponse(Call<MyWeight> call, Response<MyWeight> response) {

                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyWeight graphResponse = response.body();

                    if (graphResponse != null && graphResponse.getCode().equals("1"))
                    {
                        Toast.makeText(context, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context))
                        {
                            editText_current_user_weight.setText("");
                            showLayouts();
                            callMonthsApi(userID, false);
                        }else {
                            showLayouts();
                        }


                    }
                    else
                    {
                        Toast.makeText(context, "" + graphResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyWeight> call, Throwable t) {

                utils.hideProgressbar();
            }
        });


    }
    private void callMonthsApi(int user_ID, final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        WeightMonthsRequest request = new WeightMonthsRequest();
        request.setUserid(user_ID);

        Call<WeightMonthsResponse> call = weightService.getWeightMonthsList(request);
        call.enqueue(new Callback<WeightMonthsResponse>()
        {
            @Override
            public void onResponse(Call<WeightMonthsResponse> call, Response<WeightMonthsResponse> response)
            {
                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    WeightMonthsResponse weightMonthsResponse = response.body();

                    Log.d("wt_data",weightMonthsResponse.toString());

                    if (weightMonthsResponse != null && weightMonthsResponse.getCode() == 1)
                    {
                        double currentWeight = weightMonthsResponse.getCurrentWeight();
                        double idealWeight = weightMonthsResponse.getIdealWeight();

                        editTextCurrent.setText(String.format(Locale.getDefault(), "%.2f kg", currentWeight));
                        editTextIdeal.setText(String.format(Locale.getDefault(), "%.2f kg", idealWeight));

                        // set month spinner Data
                        List<WeightMonthsResponse.MonthDataResponse> MonthsList = response.body().getMonthData();
                        WeightMonthsList = new ArrayList<>();

                        for (WeightMonthsResponse.MonthDataResponse monthDataResponse : MonthsList)
                        {
                            WeightMonthsList.add(monthDataResponse.getHealthdate());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item_white, WeightMonthsList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        utils.hideProgressbar();
                        spinner.setVisibility(View.VISIBLE);

                        List<GraphResponse.GraphEntityData> graphEntityDataArrayList = weightMonthsResponse.getGraphData();

                        ArrayList<Entry> yAxis = new ArrayList<>();
                        ArrayList<String> xAxis = new ArrayList<>();

                        if (graphEntityDataArrayList != null)
                        {

                            if (!graphEntityDataArrayList.isEmpty()){

                                for (int i = 0; i < graphEntityDataArrayList.size(); i++)
                                {

                                    yAxis.add(new Entry(i, (float) graphEntityDataArrayList.get(i).getWeight()));
                                    xAxis.add(graphEntityDataArrayList.get(i).getMonth().trim());
                                }


                                if (yAxis.size() > 0)
                                {


                                    callGraphApi(userID,WeightMonthsList.get(0).toString());
                                }
                            }
                        }
                    }
                    else
                    {
                        if (weightMonthsResponse.getMessage().equalsIgnoreCase("Month Date list not available")){

                        }else {

                        }
                    }
                }
                else
                {
//                    ShowRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeightMonthsResponse> call, Throwable t)
            {
                utils.hideProgressbar();






            }
        });
    }
    @Override public void onClick(View v)
    { if (v.getId() == R.id.btnRefresh)
        {
            if (Utils.isNetworkAvailable(context))
            {
                showLayouts();
                callMonthsApi(userID, false);
            }
            else
                showLayouts();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    { if (++check > 1)
        { if (adapterView.getItemAtPosition(i) != null)
            {
                String monthName = adapterView.getItemAtPosition(i).toString();

                if (Utils.isNetworkAvailable(context))
                {
                    callGraphApi(userID, monthName);
                }
                else
                    showLayouts();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void callGraphApi(int userID, final String monthName)
    {
        GraphRequest request = new GraphRequest();
        request.setUserid(userID);
        request.setMonthName(monthName);
        if (!context.isFinishing())
        {

            utils.showProgressbar(context);
        }

        Call<GraphResponse> call = weightService.getGraphList(request);
        call.enqueue(new Callback<GraphResponse>()
        {
            @Override
            public void onResponse(Call<GraphResponse> call, Response<GraphResponse> response)
            {

                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GraphResponse graphResponse = response.body();

                    if (graphResponse != null && graphResponse.getCode() == 1)
                    {
                        // Get graph data which has yAxis list & xAxis list
                        List<GraphResponse.GraphEntityData> graphEntityList = graphResponse.getData();
                        Log.d("sunit",graphEntityList.toString());
//                        Collections.reverse(graphEntityList);

                        final ArrayList<Entry> yAxisValues = new ArrayList<>();
                        final ArrayList<String> xAxisValues = new ArrayList<>();

                        if (graphEntityList != null)
                        {
                            for (int i = 0; i < graphEntityList.size(); i++)
                            {
                                yAxisValues.add(new Entry(i, (float) graphEntityList.get(i).getWeight()));
                                xAxisValues.add(graphEntityList.get(i).getMonth());
                            }


                            if (yAxisValues.size() > 0)
                            {

                                if (!((Activity) context).isFinishing())
                                {
                                    if (context != null){
                                        try {
                                            initChartData(yAxisValues, xAxisValues);
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                }



                            }
                        }
                    }
                    else
                    {
                        mChart.clear();
                        mChart.setData(null);
                        mChart.highlightValue(null);
                        mChart.invalidate();
                    }
                }
                else
                {
//                    ShowRetryBar(response.message());
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GraphResponse> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });
    }

    public void showLayouts() {
        if (!Utils.isNetworkAvailable(context))
        {
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    private void initChartData(final ArrayList<Entry> yVals, final ArrayList<String> xVals) {
        mChart.clear();
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.getLegend().setTextColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        Log.d("sunit",xVals.toString());


        Spannable wordtoSpan = new SpannableString("Monthly weights(kg)");

        wordtoSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//

        LineDataSet set1 = new LineDataSet(yVals, "Monthly weights(kg)");

        set1.setFillAlpha(110);

        set1.setColor(ContextCompat.getColor(context, R.color.colorMediumSpringGreen));
        set1.setLineWidth(2f);
        set1.setValueTextColor(ContextCompat.getColor(context, R.color.white));
        set1.setCircleRadius(5);
        set1.setCircleColor(getResources().getColor(R.color.colorMediumSpringGreen));
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        mChart.setData(data);
        mChart.setNoDataText("Monthly weights(kg)");

//        mChart.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPremiumBlack));

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setDrawLabels(false); // no axis labels
        xAxis.setTextColor(Color.WHITE); // axis line colour
        xAxis.setDrawAxisLine(true); // no axis line
        xAxis.setAxisLineColor(Color.WHITE); // axis line colour
        xAxis.setDrawGridLines(false); // no grid lines
        xAxis.setTextSize(2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis at bottom
        xAxis.setValueFormatter(new IAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, AxisBase axis)
            {

                for (int i = 0; i < yVals.size(); ++i)
                {
                    if (yVals.get(i).getX() == value)
                    {
                        return xVals.get(i);

                    }
                }
                return "";
            }
        });

        xAxis.setGranularity(1);
        xAxis.setAxisLineWidth(2); // axis line colour
        if (xVals.size()==1){
            xAxis.setLabelCount(xVals.size());

        }else {
            xAxis.setLabelCount(xVals.size(), true);

        }

        YAxis left = mChart.getAxisLeft();
        left.setTextColor(Color.WHITE); // axis line colour
        left.setDrawAxisLine(true); // no axis line
        left.setAxisLineColor(Color.WHITE); // axis line colour
        left.setDrawGridLines(true); // no grid lines
        left.setGridColor(ContextCompat.getColor(context, R.color.colorGreyLight2)); // no grid lines
        left.setGridColor(Color.WHITE); // grid line colour

        if (yVals.size() > 4)
        {
            left.setAxisLineWidth(2); // axis line colour
        }

        YAxis right = mChart.getAxisRight();
        right.setEnabled(false); // no right axis

        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
    }

    private void ShowRetryBar(String msg)
    {

        String strMessage;
        if (msg.isEmpty())
        {
            strMessage = "No data available";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(context))
                        {
                            showLayouts();
                            callMonthsApi(userID, false);
                        }
                        else
                            showLayouts();
                    }
                });

        snackbar.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        if (StrDateOpen.equalsIgnoreCase("weightEntry")){
            dummydate_from = dayOfMonth + "-" + (month + 1) + "-" + year;
            todaysubmitHistoryDate=year+"-"+(month+1)+"-"+dayOfMonth;

//            yyyy-MM-dd

            txt_weight_date_select.setText(dummydate_from);
        }




        if (StrDateOpen.equalsIgnoreCase("from")) {

            if (dayOfMonth<10){
                dummydate_from = "0"+dayOfMonth + "-" + (month + 1) + "-" + year;

            }else {
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
                Toast.makeText(context, "From date should be greater than To date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_weight_date_from.setText(dummydate_from);
            txt_sleep_date_from_dialog.setText(dummydate_from);
            submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;
        }
        if (StrDateOpen.equalsIgnoreCase("to")) {
            if (!dummydate_from.trim().isEmpty()){
                if (dayOfMonth<10){
                    dummydate_to = "0"+dayOfMonth + "-" + (month + 1) + "-" + year;

                }else {
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
                    Toast.makeText(context, "To date should be greater than From date", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    txt_weight_date_to.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;

                    callWeightHistoryApi(submitFromDate,submitToDate);

                }


            }else {
                Toast.makeText(context, "Please select from date", Toast.LENGTH_SHORT).show();
            }



        }
    }


    private void callWeightHistoryApi(String submitFromDate, String submitToDate)    {

        ClsWeightRequest clsHistoryRequest=new ClsWeightRequest();
        clsHistoryRequest.setReeworkerId(userID);
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        utils.showProgressbar(context);
        Call<ClsWeightPojo> call = healthParametersService.GetReeworkerWeight(clsHistoryRequest);
        call.enqueue(new Callback<ClsWeightPojo>()
        {
            @Override
            public void onResponse(Call<ClsWeightPojo> call, Response<ClsWeightPojo> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsWeightPojo listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {

                        if (!listResponse.getData().isEmpty()){
                            txt_no_weight.setVisibility(View.GONE);
                            list_weight_history.setVisibility(View.VISIBLE);
                            ll_weight_header.setVisibility(View.GONE);
                            ArrayList<WeightHistory> weightHistories=listResponse.getData();


                            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                            list_weight_history.setLayoutManager(layoutManager);
                            list_weight_history.setAdapter(new ClsMyNewWeightAdapter(context,weightHistories,WeightFragment.this));


                        }else {
                            ll_weight_header.setVisibility(View.INVISIBLE);
                            txt_no_weight.setVisibility(View.VISIBLE);
                            list_weight_history.setVisibility(View.GONE);
                            txt_no_weight.setText("No records available");

                            if(vp_weight.getDisplayedChild()==1){
//                                Toast.makeText(context, "No records available", Toast.LENGTH_SHORT).show();

                            }

                        }








                    }
                    else
                    {
                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<ClsWeightPojo> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callWeightHistorupdateyApi(WeightHistory weightHistory)    {


        utils.showProgressbar(context);
        Call<AuthenticationResponse> call = healthParametersService.updateReeworkerWeight(weightHistory);
        call.enqueue(new Callback<AuthenticationResponse>()
        {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AuthenticationResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {


                        callWeightHistoryApi(submitFromDate,submitToDate);


                        Toast.makeText(context, "" + listResponse.getData(), Toast.LENGTH_SHORT).show();







                    }
                    else
                    {
                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }
    public String formatDates(String dateFromServer)
    {

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

    @Override
    public void updateweight(final WeightHistory weightHistory) {


        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_update_sleep);
        final EditText edt_update_weight=dialog.findViewById(R.id.edt_update_weight);
        edt_update_weight.setFilters(new InputFilter[]{new DigitsInputFilter(3, 2, 300)});

        ImageView img_close=dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edt_update_weight.setText(weightHistory.getWeight());

        Button btn_submit_weight=dialog.findViewById(R.id.btn_submit_weight);
        btn_submit_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                int index = weightHistory.getHealthDate().indexOf("T");

                weightHistory.setHealthDate(weightHistory.getHealthDate().substring(0,index));



                weightHistory.setWeight(edt_update_weight.getText().toString());
                callWeightHistorupdateyApi(weightHistory);
            }
        });
        dialog.show();




    }
    private void getSpitualListAPiByID(int id, final String libraryName){ utils = new Utils();
        session = new SessionManager(getActivity());
        try {
            utils.showProgressbar(getActivity());
        }catch (Exception e){
            e.printStackTrace();
        }
        commonService = Client.getClient().create(CommonService.class);

        Call<ClsSpiritualListMain> call = commonService.getDailyDiaryVideoByCategoryId(id);
        call.enqueue(new Callback<ClsSpiritualListMain>()
        {
            @Override
            public void onResponse(Call<ClsSpiritualListMain> call, retrofit2.Response<ClsSpiritualListMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsSpiritualListMain moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null&&!moodResponse.getData().isEmpty()){
                                    txt_no_data_spiritual.setVisibility(View.GONE);
                                    recylcer_spiritual_list.setVisibility(View.VISIBLE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=moodResponse.getData();

                                    recylcer_spiritual_list.setAdapter(new WeightVideoListAdapter(WeightFragment.this,arylst_SpiritualTypeData));


                                }else {
                                    txt_no_data_spiritual.setVisibility(View.VISIBLE);
                                    txt_no_data_spiritual.setText(libraryName+" not available");
                                    recylcer_spiritual_list.setVisibility(View.GONE);
                                    ArrayList<ClsSpiritualData> arylst_SpiritualTypeData=new ArrayList<>();

                                    recylcer_spiritual_list.setAdapter(new WeightVideoListAdapter(WeightFragment.this,arylst_SpiritualTypeData));

                                }






                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

            @Override
            public void onFailure(Call<ClsSpiritualListMain> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    public void getFoodVideoData(String videoLink, String title, String description) { Intent intent=new Intent(getActivity(), ExoActivity.class);
        intent.putExtra("VideoID",extractYTId(videoLink));
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        startActivity(intent);




    }
    public String extractYTId(String url) { final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        return null;

    }
    public String youTubeLinkWithoutProtocolAndDomain(String url) { final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
    }
    private void showNewgetSleephistroy() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog=dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog=dialog.findViewById(R.id.txt_sleep_date_to_dialog);


        Button  btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    dialog.dismiss();

                    callWeightHistoryApi(submitFromDate,submitToDate);
                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

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
                StrDateOpen="from";
                datepickerdialog.show();

            }
        });

        txt_sleep_date_to_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";
                datepickerdialog.show();
            }
        });

        ImageView img_close=dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }

}
