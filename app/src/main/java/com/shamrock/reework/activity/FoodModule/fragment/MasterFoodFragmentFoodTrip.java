package com.shamrock.reework.activity.FoodModule.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.BeforeAfterModule.service.BeforeAfterService;
import com.shamrock.reework.activity.FoodModule.activity.FoodRecipeActivity;
import com.shamrock.reework.activity.FoodModule.adapter.FoodSnippingAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.FoodTripAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.UserFoodTripAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.UserFoodTripAdapterNew;
import com.shamrock.reework.activity.FoodModule.adapter.UserFoodTripMealAdapterNew;
import com.shamrock.reework.activity.FoodModule.adapter.WeeklyAnalysisAdapterNew;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistory;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistoryRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodData;
import com.shamrock.reework.activity.FoodModule.model.FoodHistoryData;
import com.shamrock.reework.activity.FoodModule.model.FoodMealData;
import com.shamrock.reework.activity.FoodModule.model.foodtripuser.ClsUSerFoodTripmain;
import com.shamrock.reework.activity.FoodModule.model.foodtripuser.UserFoodTripData;
import com.shamrock.reework.activity.FoodModule.service.OnFavoriteClick;
import com.shamrock.reework.adapter.DifferentRowAdapterNew;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.FoodTripRequest;
import com.shamrock.reework.api.response.BeforeAfterResponse;
import com.shamrock.reework.api.response.FoodSnippingResp;
import com.shamrock.reework.api.response.FoodTripFavoriteUpdateResponse;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetFilterSubFilterResponce;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.model.TodaysMealData;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class MasterFoodFragmentFoodTrip extends Fragment implements OnFavoriteClick, View.OnClickListener,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,

        FoodTripAdapter.FoodTripListener,SearchView.OnQueryTextListener,SearchView.OnCloseListener,DifferentRowAdapterNew.OnMealClickListner

{

    RadioGroup relfood;
    TextView food1,txt_rbFoodSnapping;
    //    private DatePickerDialog datepickerdialog;
//    private TimePickerDialog timepickerdialog;
    StringBuilder stringBuilder_datetime;
    TextView txt_date_time;
    String curentDateTime="";
    StringBuilder strSubmitDateTime=new StringBuilder();
    //For SubList Dialog
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterListDialog;
    boolean isVeg=true;
    TextView norecipe;


    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
            FILE_SELECT_REQUEST_CODE = 300;
    public static final int SECOND_ACTIVITY_REQUEST_CODE = 108;
    public boolean isCamera = false;
    public boolean isImage = false;
    private Uri mCapturedImageURI;
    private Context mContext;
    RadioButton rbFood, rbFoodSnapping,rbFoodreplace, rbHistory;

    RecyclerView rvAllRecipe, rvFoodSnipping;
    ArrayList<FoodTripResponse.FoodStripData> mFoodList;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterList;
    FoodTripAdapter mFoodTripAdapter;;
    UserFoodTripAdapter user_mFoodTripAdapter;;
    LinearLayout ll_food_recipe;
    ViewFlipper vp_reciepe;
    RecyclerView rvnonvegRecipies;
    RadioButton rd_button_nonveg;
    RadioButton rd_button_veg;

    FoodSnippingAdapter mFoodSnippingAdapter;
    List<FoodSnippingResp.Datum> mSnippingList;

    private Utils utils;
    FoodService foodService;
    SessionManager sessionManager;

    ImageView imgFilter;
    Button fabAdd;
    private static final String ARG_PARAM3 = "MEAL_CAL_MAX";
    int userId;
    private  int mParam3=0;
    public static int FOOD_TYPE;

    boolean isFoods = false;
    boolean isExpand = false;
    private DatePickerDialog datepickerdialog;
    private TimePickerDialog timepickerdialog;

    List<TodaysMeal> list = new ArrayList<>();
    SearchView searchRecipe;
    String   strrecipe="Vegetarian";
    private ArrayList<UserFoodTripData> arylst_userfood_trip;



    private String submitFromDate;
    private String submitToDate;

    ArrayList<FoodData> listFood =new ArrayList<FoodData>();
    ArrayList<FoodHistoryData> listFood1 =new ArrayList<FoodHistoryData>();
    ArrayList<ArrayList<FoodHistoryData>> listFood2 =new ArrayList<ArrayList<FoodHistoryData>>();
    ArrayList<FoodHistoryData> listFood3 =new ArrayList<FoodHistoryData>();
    ArrayList<FoodMealData> listFood4 =new ArrayList<FoodMealData>();
    RecyclerView recyclerView_All_Meal_Weekly;

    public MasterFoodFragmentFoodTrip(){}

    public static MasterFoodFragmentFoodTrip newInstance()
    {
        MasterFoodFragmentFoodTrip fragment = new MasterFoodFragmentFoodTrip();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        this.mContext = context;
    }

    private void showTimePickerDialog() {

        timepickerdialog = new TimePickerDialog(mContext, this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE) ,
                false);
//        datepickerdialog.show();
    }
    private void showDatePickerDailog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);





        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());


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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
//            FromWater=getArguments().getBoolean("FromWater");
            // mParam4 = getArguments().getString(ARG_PARAM4);
        }

        utils = new Utils();
        sessionManager = new SessionManager(mContext);
        foodService = Client.getClient().create(FoodService.class);
        mFoodList = new ArrayList<>();
        mSubFilterList = new ArrayList<>();
        mSnippingList = new ArrayList<>();

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }
    RecyclerView recyclerViewTodays;
    LinearLayout layout_daily,layout_foodrec;
    TextView txt_nodata,txt_nodata1;
    //    private Context context;
    UserFoodTripAdapterNew adapter;






    LinearLayout layot_foodhistory;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_master_food_fragment_food_trip, container, false);
        setHasOptionsMenu(true);

        //FoodHistory



        relfood=view.findViewById(R.id.relfood);
        layot_foodhistory=view.findViewById(R.id.layot_foodhistory);
        txt_rbFoodSnapping=view.findViewById(R.id.txt_rbFoodSnapping);
        food1=view.findViewById(R.id.food1);
        recyclerView_All_Meal_Weekly = view.findViewById(R.id.recyclerView_All_Meal_Weekly);
        txt_rbFoodSnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relfood.setVisibility(View.GONE);
//                txt_rbFoodSnapping.setBackgroundColor(getResources().getColor(R.color.black));
                txt_rbFoodSnapping.setBackground(getResources().getDrawable(R.drawable.border_new_rightblack));
                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.white));
//                food1.setBackground(getResources().getDrawable(R.color.white));
//                food1.setTextColor(getResources().getColor(R.color.black));
                food1.setBackground(getResources().getDrawable(R.drawable.border_new_rightline));
                food1.setTextColor(getResources().getColor(R.color.shadegray));
                fabAdd.setVisibility(View.VISIBLE);
                rvFoodSnipping.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_daily.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));

                callToGetAllSnippingImages();
            }
        });



        curentDateTime= new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault()).format(new Date());
        strSubmitDateTime.append(new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault()).format(new Date()).toString());
        rbFood = view.findViewById(R.id.rbFood);
        fabAdd = view.findViewById(R.id.fab);
        rvAllRecipe = view.findViewById(R.id.rvRecipies);
        rvFoodSnipping = view.findViewById(R.id.rvFoodSnipping);
        recyclerViewTodays = view.findViewById(R.id.recyclerView_All_Meal_TodaysMeal);
        layout_daily = view.findViewById(R.id.layout_daily);
        layout_foodrec = view.findViewById(R.id.layout_foodrec);
        txt_nodata = view.findViewById(R.id.txt_nodata);
        txt_nodata1 = view.findViewById(R.id.txt_nodata1);
        imgFilter = view.findViewById(R.id.imgFilter);
        searchRecipe = view.findViewById(R.id.searchRecipe);
        searchRecipe.setOnClickListener(this);
        searchRecipe.setOnQueryTextListener(this);
        showDatePickerDailog();
        showTimePickerDialog();
//        this.context = context;

        searchRecipe.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchRecipe.setOnSearchClickListener(this);
        searchRecipe.setOnCloseListener((SearchView.OnCloseListener) this);
        searchRecipe.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand) {
                    visibleView();
                    isExpand = true;
                }
                else{
                    hideView();
                    isExpand = false;
                }
            }
        });
        rbFoodSnapping = view.findViewById(R.id.rbFoodSnapping);
        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                relfood.setVisibility(View.VISIBLE);
//                txt_rbFoodSnapping.setBackgroundColor(getResources().getColor(R.color.white));
                txt_rbFoodSnapping.setBackground(getResources().getDrawable(R.drawable.border_new_line));
                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.shadegray));
//                food1.setBackgroundColor(getResources().getColor(R.color.black));
                food1.setBackground(getResources().getDrawable(R.drawable.border_new_black));
                food1.setTextColor(getResources().getColor(R.color.white));
                fabAdd.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                rvFoodSnipping.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.VISIBLE);
                layout_daily.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);

                rbFood.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));


//                callToGetFoodTripAllRecipies();
                getAllTodaysMeal();

                if(list.size()==0){
                    recyclerViewTodays.setVisibility(View.GONE);
                    txt_nodata.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewTodays.setVisibility(View.VISIBLE);
                    txt_nodata.setVisibility(View.GONE);
                }

            }
        });
        rbFood.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {


                relfood.setVisibility(View.VISIBLE);
//                txt_rbFoodSnapping.setBackgroundColor(getResources().getColor(R.color.white));
//                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.black));
//                food1.setBackgroundColor(getResources().getColor(R.color.black));
//                food1.setTextColor(getResources().getColor(R.color.white));

                txt_rbFoodSnapping.setBackground(getResources().getDrawable(R.drawable.border_new_line));
                txt_rbFoodSnapping.setTextColor(getResources().getColor(R.color.shadegray));
//                food1.setBackgroundColor(getResources().getColor(R.color.black));
                food1.setBackground(getResources().getDrawable(R.drawable.border_new_black));
                food1.setTextColor(getResources().getColor(R.color.white));
                fabAdd.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                rvFoodSnipping.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.VISIBLE);
                layout_daily.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);

                rbFood.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));


//                callToGetFoodTripAllRecipies();
                getAllTodaysMeal();

                if(list.size()==0){
                    recyclerViewTodays.setVisibility(View.GONE);
                    txt_nodata.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewTodays.setVisibility(View.VISIBLE);
                    txt_nodata.setVisibility(View.GONE);
                }


            }
        });


        rbFoodSnapping.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.VISIBLE);
                rvFoodSnipping.setVisibility(View.VISIBLE);
                layout_foodrec.setVisibility(View.GONE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_daily.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));

                callToGetAllSnippingImages();
            }
        });

        rbFoodreplace = view.findViewById(R.id.rbFoodreplace);
        rbHistory = view.findViewById(R.id.rbHistory);


        rbFoodreplace.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.GONE);
                rbFoodreplace.setVisibility(View.VISIBLE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);
                rvFoodSnipping.setVisibility(View.GONE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_foodrec.setVisibility(View.VISIBLE);
                layout_daily.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.GONE);
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.black));

//                callToGetAllSnippingImages();
            }
        });

        rbHistory.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fabAdd.setVisibility(View.GONE);
                rbHistory.setVisibility(View.VISIBLE);
                rvAllRecipe.setVisibility(View.GONE);
                searchRecipe.setVisibility(View.VISIBLE);
                recyclerViewTodays.setVisibility(View.GONE);
                layout_foodrec.setVisibility(View.GONE);
                layout_daily.setVisibility(View.GONE);
                rvFoodSnipping.setVisibility(View.GONE);
                layot_foodhistory.setVisibility(View.VISIBLE);
                rbHistory.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                rbFood.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodSnapping.setTextColor(mContext.getResources().getColor(R.color.black));
                rbFoodreplace.setTextColor(mContext.getResources().getColor(R.color.black));


                callFoodHistoryData();
//                callToGetAllSnippingImages();
            }
        });





        rd_button_veg=view.findViewById(R.id.rd_button_veg);
        rd_button_nonveg=view.findViewById(R.id.rd_button_nonveg);
        norecipe=view.findViewById(R.id.norecipe);

//        callToGetFoodTripAllRecipies();

        getAllTodaysMeal();



        imgFilter.setOnClickListener(this);
        fabAdd.setOnClickListener(this);



        mFoodSnippingAdapter = new FoodSnippingAdapter(mContext, mSnippingList);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvFoodSnipping.setLayoutManager(gridLayoutManager);
        rvFoodSnipping.setItemAnimator(new DefaultItemAnimator());
        rvFoodSnipping.setAdapter(mFoodSnippingAdapter);


//        callToGetAllSnippingImages();


        fabAdd.setVisibility(View.VISIBLE);
        callToGetAllSnippingImages();



        return view;
    }


    //FoodHistory



    public void callFoodHistoryData() {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }
        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(myDate);
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date newDate = calendar.getTime();
        Date date1=null;
        String sDate1=sessionManager.getStringValue("statusdate");
        try {
            date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date weekAgoDate = getDateWithOffset(-7, date1);
//        getCalculatedDate("01-01-2015", "dd-MM-yyyy", -10);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(weekAgoDate);
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        ClsFoodHistoryRequest clsFoodHistoryRequest=new ClsFoodHistoryRequest();

        clsFoodHistoryRequest.setFromDate(submitFromDate);
        clsFoodHistoryRequest.setToDate(sessionManager.getStringValue("statusdate"));
        clsFoodHistoryRequest.setReeworkerId(String.valueOf(userId));
        FoodService   commonService = Client.getClient().create(FoodService.class);


        Call<ClsFoodHistory> call = commonService.FoodHistory(clsFoodHistoryRequest);
        call.enqueue(new Callback<ClsFoodHistory>()
        {
            @Override
            public void onResponse(Call<ClsFoodHistory> call, retrofit2.Response<ClsFoodHistory> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){
                    try {
                        ClsFoodHistory moodResponse = response.body();
                        if (moodResponse != null ) {
                            moodResponse.getData();
                            listFood = new ArrayList<FoodData>();
                            ArrayList<FoodData> data = response.body().getData();
//                         ArrayList<FoodHistoryData> data1=new ArrayList<FoodHistoryData>();
                            for (int i = 0; i < data.size(); i++)
                            {
                                FoodData datum = new FoodData();
                                datum.setCreatedOn(data.get(i).getCreatedOn());
                                datum.setTotalCaloriesIntake(data.get(i).getTotalCaloriesIntake());
                                datum.setTotalCarbsIntake(data.get(i).getTotalCarbsIntake());
                                datum.setTotalFatIntake(data.get(i).getTotalFatIntake());
                                datum.setTotalFibreIntake(data.get(i).getTotalFibreIntake());
                                datum.setTotalProteinIntake(data.get(i).getTotalProteinIntake());
                                datum.setMealIntakeByMealType(data.get(i).getMealIntakeByMealType());
//                                FoodHistoryData datum1 = new FoodHistoryData();
//                                datum.setData(data.get(i).getData());
//                                datum.set(data.get(i).getTotalCaloriesIntake());
//                                data1 = data.get(i).getData();
//                                listFood1.add(data.get(i).getData());
//                                listFood2.add(data.get(i).getData()));
                                listFood.add(datum);

                            }
                            listFood1.clear();
                            listFood2.clear();
                            listFood3.clear();
                            listFood4.clear();
                            for (int i = 0; i < listFood.size(); i++)
                            {
//                                FoodData datum = new FoodData();
//                                datum.setData(listFood.get(i).getData());
//                                datum.set(data.get(i).getTotalCaloriesIntake());

                                listFood1.addAll(listFood.get(i).getMealIntakeByMealType());

                            }

                            for (int i = 0; i < listFood1.size(); i++)
                            {

                                FoodHistoryData datum = new FoodHistoryData();
                                datum.setMeals(listFood1.get(i).getMeals());

                                listFood3.add(datum);



                            }


                            for (int i = 0; i < listFood3.size(); i++)
                            {
//                                FoodData datum = new FoodData();
//                                datum.setData(listFood.get(i).getData());
//                                datum.set(data.get(i).getTotalCaloriesIntake());

                                listFood4.addAll(listFood3.get(i).getMeals());

                            }



                            UserFoodTripMealAdapterNew adapter = new UserFoodTripMealAdapterNew(mContext,listFood4);
                            recyclerView_All_Meal_Weekly.setLayoutManager(new LinearLayoutManager(mContext));
                            recyclerView_All_Meal_Weekly.setAdapter(adapter);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsFoodHistory> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });






    }


    public static Date getDateWithOffset(int offset, Date date){
        Calendar calendar = calendar = Calendar.getInstance();;
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar.getTime();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.fab:

                if (Utils.isNetworkAvailable(mContext))
                {
                    /* registerForContextMenu(imgFilter);*/
//                    selectImage();

                    newDailog();
                }
                break;

            case R.id.imgFilter:
                /*Intent i = new Intent(mContext, FoodFilter_SubFilterActivity.class);
                i.putExtra("data",mSubFilterList);
                startActivity(i);*/
//                showFilterDialog(v);
                break;

            case R.id.searchRecipe:
                hideView();
                break;
        }

    }


    private void getAllTodaysMeal()

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }
        BcaRequest request = new BcaRequest();
        request.setUserid(userId);
        request.setMeal_cal_max(mParam3);
        request.setCreatedOn(sessionManager.getStringValue("statusdate"));
        //4186

        Call<TodaysMealData> call = foodService.getTodyasMeal(request);
        call.enqueue(new Callback<TodaysMealData>()
        {
            @Override
            public void onResponse(Call<TodaysMealData> call, Response<TodaysMealData> response)
            {
                utils.hideProgressbar();
                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    list = new ArrayList<>();
                    TodaysMealData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<TodaysMealData.Datum> data = response.body().getData();

                        if (data!= null && data.size() > 0)
                        {

                            try{

                                double totalAllFoodCal=0.0;


                                for (int i = 0; i < data.size(); i++)
                                {
                                    //  JSONObject mainMeal = dataArray.getJSONObject(i);

                                    // HEADER Data

                                    totalAllFoodCal=totalAllFoodCal+data.get(i).getMealCalMin();
                                    TodaysMeal todaysMeal = new TodaysMeal();
                                    todaysMeal.setMeal_type_name(data.get(i).getMealType());
                                    todaysMeal.setMeal_cal_min(String.valueOf(data.get(i).getMealCalMin()));
                                    todaysMeal.setMeal_cal_max(Double.parseDouble(String.valueOf(data.get(i).getMealCalMax())));
                                    todaysMeal.setMeal_type(1);
                                    todaysMeal.setIntakeTime(data.get(i).getIntakeTime());
                                    todaysMeal.setCreatedOn(data.get(i).getCreatedOn());
                                    list.add(todaysMeal);

                                    // ROW Data

                                    ;
                                    //JSONArray mealItems = mainMeal.getJSONArray("meal_data");





                                    Collections.sort( data.get(i).getLstSubMealData(), new Comparator<TodaysMealData.LstSubMealDatum>() {

                                        @Override
                                        public int compare(TodaysMealData.LstSubMealDatum o1, TodaysMealData.LstSubMealDatum o2) {
                                            try {
                                                if (o1.getIntakeTime()!=null&&o2.getIntakeTime()!=null){
                                                    return new SimpleDateFormat("hh:mm aa").parse(o1.getIntakeTime()).compareTo(new SimpleDateFormat("hh:mm aa").parse(o2.getIntakeTime()));

                                                }
                                                return 0;

                                            } catch (Exception e) {
                                                return 0;
                                            }
                                        }
                                    });

                                    for (int j = 0; j < data.get(i).getLstSubMealData().size(); j++) {
                                        //JSONObject rowMeal = mealItems.getJSONObject(j);
                                        TodaysMeal meal = new TodaysMeal();
                                        if (j == data.get(i).getLstSubMealData().size()) {
                                            meal.setMeal_type(3);   // last row
                                        } else {
                                            meal.setMeal_type(2);   // middle rows
                                        }
                                        meal.setMeal_type_name(data.get(i).getMealType());
                                        if(data.get(i).getLstSubMealData().get(j).getIntakeTime()!=null){
                                            data.get(i).getLstSubMealData().get(j).getIntakeTime();
                                        }
                                        meal.setIntakeTime(data.get(i).getLstSubMealData().get(j).getIntakeTime());
                                        meal.setMeal_img(data.get(i).getLstSubMealData().get(j).getMealImg());
                                        meal.setMeal_name(data.get(i).getLstSubMealData().get(j).getMealName());
                                        meal.setMeal_quantity(data.get(i).getLstSubMealData().get(j).getMealQty());
                                        meal.setFood_Id(data.get(i).getLstSubMealData().get(j).getRecipeId());
                                        meal.setUserMealId(data.get(i).getLstSubMealData().get(j).getUserMealId());
                                        meal.setUomId(data.get(i).getLstSubMealData().get(j).getUomId());
                                        meal.setUnitText(data.get(i).getLstSubMealData().get(j).getUnitText());
                                        meal.setProtin(data.get(i).getLstSubMealData().get(j).getProtin());
                                        meal.setCarbs(data.get(i).getLstSubMealData().get(j).getCarb());
                                        meal.setFibre(data.get(i).getLstSubMealData().get(j).getFibre());
                                        meal.setFat(data.get(i).getLstSubMealData().get(j).getFat());
                                        meal.setItemTypeId(data.get(i).getLstSubMealData().get(j).getItemTypeId());

                                        double singlecal=data.get(i).getLstSubMealData().get(j).getMealCalory();


                                        DecimalFormat decimalFormat=new DecimalFormat("0.00");
                                        meal.setRecipeImagePath(data.get(i).getLstSubMealData().get(j).getRecipeImagePath());

                                        meal.setMeal_calory(String.valueOf(decimalFormat.format(singlecal)));
                                        list.add(meal);
                                    }
                                }



//                                Collections.sort(list, new Comparator<TodaysMeal>() {
//
//                                    @Override
//                                    public int compare(TodaysMeal o1, TodaysMeal o2) {
//                                        try {
//                                            return new SimpleDateFormat("hh:mm aa").parse(o1.getIntakeTime()).compareTo(new SimpleDateFormat("hh:mm aa").parse(o2.getIntakeTime()));
//                                        } catch (ParseException e) {
//                                            return 0;
//                                        }
//                                    }
//                                });


                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                String showschedulestr = decimalFormat.format(totalAllFoodCal);


//                                txt_acual_total_cal_consumed.setText(" Total consumed for the day : "+showschedulestr+" kcal");

                                adapter = new UserFoodTripAdapterNew(mContext,list);
                                recyclerViewTodays.setLayoutManager(new LinearLayoutManager(mContext));
                                recyclerViewTodays.setAdapter(adapter);

                                if(list.size()==0){
                                    recyclerViewTodays.setVisibility(View.GONE);
                                    txt_nodata.setVisibility(View.VISIBLE);
                                }else{
                                    recyclerViewTodays.setVisibility(View.VISIBLE);
                                    txt_nodata.setVisibility(View.GONE);
                                }



                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        }
                    }
                    else
                    {


                        list.clear();
                        List<TodaysMealData.Datum> datas=new ArrayList<>();
                        adapter = new UserFoodTripAdapterNew(mContext,list);
                        recyclerViewTodays.setLayoutManager(new LinearLayoutManager(mContext));
                        recyclerViewTodays.setAdapter(adapter);



                        if (getUserVisibleHint()){

                            if (!sessionManager.getStringValue("IsShowMSg").equalsIgnoreCase("false")){
//                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }
                    }
                }

                sessionManager.setStringValue("IsShowMSg","true");

            }

            @Override
            public void onFailure(Call<TodaysMealData> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }




    private void newDailog() {

        if (Utils.isNetworkAvailable(mContext))
        {

            final Dialog dialog=new Dialog(mContext,R.style.CustomDialog);

            dialog.setContentView(R.layout.dialog_datetime_image);

            Button btn_camera=dialog.findViewById(R.id.btn_camera);
            Button btn_gallery=dialog.findViewById(R.id.btn_gallery);
            txt_date_time=dialog.findViewById(R.id.txt_date_time);
            txt_date_time.setText(curentDateTime);
            ImageView img_close_upload=dialog.findViewById(R.id.img_close_upload);
            img_close_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            LinearLayout ll_upload_photo_date=dialog.findViewById(R.id.ll_upload_photo_date);
            btn_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dexter.withActivity(getActivity())
                            .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera

                                    dialog.dismiss();
                                    fileChooser();                                    }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings
                                        showSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();

                }
            });
            btn_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    Dexter.withActivity(getActivity())
                            .withPermission(Manifest.permission.CAMERA)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera
                                    dialog.dismiss();
                                    CallCameraIntent();
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings
                                        showSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();


                }
            });


            ll_upload_photo_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datepickerdialog.show();
                }
            });


            dialog.show();



//                    openContextMenu(ivBefore);
        }
        else
        {
            showRetryBar(getString(R.string.internet_connection_unavailable));
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Camera", "Gallery"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Image Picker");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {

                    // Do some stuff

                    Dexter.withActivity(getActivity())
                            .withPermission(Manifest.permission.CAMERA)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera

                                    CallCameraIntent();
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings
                                        showSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();

                } else if (items[item].equals("Gallery")) {
                    Dexter.withActivity(getActivity())
                            .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    // permission is granted, open the camera

                                    dialog.dismiss();
                                    fileChooser();                                    }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings
                                        showSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();

                }
            }
        });
        builder.show();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }





    public void CallCameraIntent()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = mContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void fileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }





    //Declared in FoodtripAdapter
    @Override
    public void GetFootTripPosition(int pos, FoodTripResponse.FoodStripData model)
    {
        if (model != null)
        {
            int recipeId = model.getRecipeId();
            int editId = model.getEditId();

            Intent intent = new Intent(mContext, FoodRecipeActivity.class);
            intent.putExtra("RECEIPE_ID", recipeId);
            intent.putExtra("EDIT_ID",editId);
            intent.putExtra("RECEIPE_Image",model.getRecipeImagePath());
            startActivity(intent);
        }
    }

    //Declared in FoodtripAdapter
    @Override
    public void GetFavoriteItem(int pos, boolean isDone, FoodTripResponse.FoodStripData model)
    {
//        if (Utils.isNetworkAvailable(mContext))
//            callToUpdateFavoriteStatus(model);
//        else
//            showRetryBar("Check internet connection!");
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        int dataSize=0;

        if (requestCode == FILE_SELECT_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {


                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = mContext.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                uploadFile(new File(picturePath), userId);

                isImage = true;
            }
        }
        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userId);
                isImage = true;
            }
        }
        else if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                boolean res = data.getBooleanExtra("result",false);
                if (res) {
                    if (Utils.isNetworkAvailable(mContext)) {
//                        callToGetAllRecipies("intial", 0, "");

                    } else
                        Toast.makeText(mContext.getApplicationContext(), "R.string.internet_connection_unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e) {return contentUri.getPath();}
    }

    private void uploadFile(File file, int userId)
    {

        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        File fileTemp = null;
        try {
            fileTemp = new Compressor(mContext).compressToFile(file);
          /*  fileTemp  = new Compressor(getActivity())
                    .setMaxWidth(2000)
                    .setMaxHeight(1400)
                    .setQuality(90)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file, file.getName());*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileTemp);

        MultipartBody.Part photo = MultipartBody.Part.createFormData("image", fileTemp.getName(), photoContent);

        BeforeAfterService uploadService = Client.getClientMultiPart().create(BeforeAfterService.class);
        RequestBody user_Id = RequestBody.create(MediaType.parse("text/plain"), ""+userId);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), fileTemp.getName());

        FoodService foodService = Client.getClientMultiPart().create(FoodService.class);
        RequestBody uplaodtime = RequestBody.create(MediaType.parse("multipart/form-MainModel"), strSubmitDateTime.toString());

        Call<BeforeAfterResponse> call = foodService.Upload(photo, user_Id, filename,uplaodtime);
        call.enqueue(new Callback<BeforeAfterResponse>()
        {

            @Override
            public void onResponse(Call<BeforeAfterResponse> call, Response<BeforeAfterResponse> response)
            {
                //ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> data = null;

                utils.hideProgressbar();

                if (response.isSuccessful())
                {
                    if (response.body() != null)
                    {
                        BeforeAfterResponse dataResponse = response.body();

                        if (dataResponse.getCode() == 1)
                        {
                            callToGetAllSnippingImages();
                        }
                        Toast.makeText(mContext.getApplicationContext(), "Uploaded successfully !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(mContext.getApplicationContext(), "Server : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext.getApplicationContext(), "Server : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BeforeAfterResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Toast.makeText(mContext.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void callToGetAllSnippingImages()

    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        FoodTripRequest request = new FoodTripRequest();
        request.setUserId(userId);//userId
        Call<FoodSnippingResp> call = foodService.getAllImages(request);
        call.enqueue(new Callback<FoodSnippingResp>()
        {
            @Override
            public void onResponse(Call<FoodSnippingResp> call, Response<FoodSnippingResp> response)
            {
                utils.hideProgressbar();

                List<FoodSnippingResp.Datum> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodSnippingResp listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        tempList = response.body().getData();

                        if (tempList!= null && tempList.size() > 0)
                        {
                            rvAllRecipe.setVisibility(View.GONE);
                            rvFoodSnipping.setVisibility(View.VISIBLE);
                            mSnippingList.clear();
                            mSnippingList.addAll(tempList);
                            mFoodSnippingAdapter.notifyDataSetChanged();
                        }else{
                            rvAllRecipe.setVisibility(View.GONE);
                            rvFoodSnipping.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, "Data Not Found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        rvAllRecipe.setVisibility(View.GONE);
                        rvFoodSnipping.setVisibility(View.VISIBLE);
                        if (!listResponse.getMessage().isEmpty()){
                            Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(mContext, "Data Not Found.", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodSnippingResp> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }





    private void callToGetFoodTripAllRecipies() {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }


        Call<ClsUSerFoodTripmain> call = foodService.getUserFoodTrip(userId);
        call.enqueue(new Callback<ClsUSerFoodTripmain>()
        {
            @Override

            public void onResponse(Call<ClsUSerFoodTripmain> call, Response<ClsUSerFoodTripmain> response)
            {
                utils.hideProgressbar();

                ArrayList<FoodTripResponse.FoodStripData> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsUSerFoodTripmain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        arylst_userfood_trip = response.body().getData();

                        if (arylst_userfood_trip!= null && arylst_userfood_trip.size() > 0)
                        {
                            rvAllRecipe.setVisibility(View.VISIBLE);

                            user_mFoodTripAdapter = new UserFoodTripAdapter(mContext, arylst_userfood_trip,MasterFoodFragmentFoodTrip.this);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                            rvAllRecipe.setLayoutManager(layoutManager);
                            rvAllRecipe.setItemAnimator(new DefaultItemAnimator());
                            rvAllRecipe.setAdapter(user_mFoodTripAdapter);
//                            mFoodTripAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        mFoodList.clear();
//                        mFoodList.addAll(tempList);
                        mFoodTripAdapter.notifyDataSetChanged();
                        rvAllRecipe.setVisibility(View.GONE);
//                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsUSerFoodTripmain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }



    private void callToUpdateFavoriteStatus(int id,boolean b)
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

//        FoodTripFavoriteUpdateRequest request = new FoodTripFavoriteUpdateRequest();
//        request.setUserId(userId);
//
//        if(model.getIsfavourite()!=null){
//            if(model.getIsfavourite()==true){
//                request.setIsfavourite(1);
//            }else{
//                request.setIsfavourite(0);
//            }
//        }
//        request.setEditId(model.getEditId());
//        request.setRecipeId(model.getRecipeId());
//        Gson gson = new Gson();
//        String json = gson.toJson(request);
//        String test = json;

        Call<FoodTripFavoriteUpdateResponse> call = foodService.setuserTripFavoriete(id,b);
        call.enqueue(new Callback<FoodTripFavoriteUpdateResponse>()
        {
            @Override
            public void onResponse(Call<FoodTripFavoriteUpdateResponse> call, Response<FoodTripFavoriteUpdateResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodTripFavoriteUpdateResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();


//                        mFoodTripAdapter.notifyDataSetChanged();

                        if (Utils.isNetworkAvailable(mContext)) {



                            callToGetFoodTripAllRecipies();
                        } else{
                            Toast.makeText(mContext.getApplicationContext(), R.string.internet_connection_unavailable, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodTripFavoriteUpdateResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void showRetryBar(String msg)
    {
        String strMessage;
        if (!msg.isEmpty())
        {
            strMessage = msg;
        }
        else
        {
            strMessage = "Unable to load data.";
        }

        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(mContext))
                        {
//                            callToGetAllRecipies("Vegetarian");
                        }
                        else
                        {
                            showRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }

    public void hideView(){
        rbFoodSnapping.setVisibility(View.GONE);
        rbFood.setVisibility(View.GONE);
        rbFoodreplace.setVisibility(View.GONE);
        rbHistory.setVisibility(View.GONE);
    }

    public void visibleView(){
        rbFoodSnapping.setVisibility(View.GONE);
        rbFood.setVisibility(View.VISIBLE);
        rbFoodreplace.setVisibility(View.VISIBLE);
        rbHistory.setVisibility(View.VISIBLE);
    }




    @Override
    public boolean onQueryTextSubmit(String query) {
        hideView();
        String text = query;
        if (user_mFoodTripAdapter!=null){
            user_mFoodTripAdapter.getFilter().filter(query);

        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        hideView();
        String text = newText;
        try {
            user_mFoodTripAdapter.getFilter().filter(newText);

        }catch (Exception e){
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean onClose() {
        visibleView();
        return false;
    }

    @Override
    public void getFavorite(int mealid, boolean status) {

//        http://shamrockuat.dweb.in/api/Recipe/SaveFavoriteMeal?MealId=413&IsFav=true
        if (Utils.isNetworkAvailable(mContext))
            callToUpdateFavoriteStatus(mealid,status);
        else
            showRetryBar("Check internet connection!");

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(layot_foodhistory.getVisibility()==View.GONE) {
            strSubmitDateTime = new StringBuilder();
            stringBuilder_datetime = new StringBuilder();
            String dummydateentry = dayOfMonth + "-" + (month + 1) + "-" + year;

            strSubmitDateTime.append(year + "-" + (month + 1) + "-" + dayOfMonth);
            stringBuilder_datetime.append(dummydateentry);
            timepickerdialog.show();

        }










    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String lsTimeFrom = formatter.format(cal.getTime());
        stringBuilder_datetime.append(" ").append(lsTimeFrom);
        strSubmitDateTime.append(" ").append(lsTimeFrom);
        txt_date_time.setText(stringBuilder_datetime.toString());

    }

    @Override
    public void onClickMeal(int position, TodaysMeal model) {

    }




   /* @Override
    public void onEditResponceRecipe(boolean res) {
        if (res)
        {
            if (Utils.isNetworkAvailable(mContext)) {
                callToGetAllRecipies("intial",0,"");

            } else
               Toast.makeText(mContext.getApplicationContext(),"R.string.internet_connection_unavailable",Toast.LENGTH_SHORT).show();
        }
    }*/
}

