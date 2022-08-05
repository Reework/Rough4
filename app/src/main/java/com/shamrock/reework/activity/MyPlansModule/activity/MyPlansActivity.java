package com.shamrock.reework.activity.MyPlansModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.FoodRecipeActivity;
import com.shamrock.reework.activity.FoodModule.activity.MasterDetailsActivity;
import com.shamrock.reework.activity.FoodModule.model.AddMealRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodUnitMasterPojo;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyPlansModule.adapter.MyPlansAdapter;
import com.shamrock.reework.activity.MyPlansModule.addfood.ClsTodaysPlanAddFood;
import com.shamrock.reework.activity.MyPlansModule.addfood.ItemlistDialog;
import com.shamrock.reework.activity.MyPlansModule.addfood.Uom;
import com.shamrock.reework.activity.MyPlansModule.addfood.UpdatePlanItem;
import com.shamrock.reework.activity.MyPlansModule.addfood.uom.foodPlanUomlistDialog;
import com.shamrock.reework.activity.MyPlansModule.dialog.MyPlansEditDialog;
import com.shamrock.reework.activity.MyPlansModule.service.MyPlansService;
import com.shamrock.reework.activity.activtymaster.service.MyActivityService;
import com.shamrock.reework.activity.dietprofile.DietProfileActivity;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanPost;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanPostsucces;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanService;
import com.shamrock.reework.activity.newmyplan.ClsTodaysPlanNewMainPojo;
import com.shamrock.reework.activity.newmyplan.MyPlanNewDataqClass;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.todaysplan.model.ClsPlanName;
import com.shamrock.reework.activity.todaysplan.model.ClsTodaysPlanMain;
import com.shamrock.reework.activity.todaysplan.model.Groups;
import com.shamrock.reework.activity.todaysplan.model.PlanItems;
import com.shamrock.reework.activity.todaysplan.model.ReeworkerPlan;
import com.shamrock.reework.activity.todaysplan.model.TodaysPlanData;
import com.shamrock.reework.activity.todaysplan.model.adapters.ClsTodaysPlansAdapter;
import com.shamrock.reework.activity.todaysplan.model.adapters.MyPlanNameMasterAdapter;
import com.shamrock.reework.activity.todaysplan.model.listners.onAddFoodPlan;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetRecipeRequest;
import com.shamrock.reework.api.request.MyPlanRequest;
import com.shamrock.reework.api.request.MyPlansCheckRequest;
import com.shamrock.reework.api.response.GetMealType;
import com.shamrock.reework.api.response.MyPlanMastersResponse;
import com.shamrock.reework.api.response.MyPlanResponse;
import com.shamrock.reework.api.response.RecipeResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.fragment.AddActivityDialogListener;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;
import com.shamrock.reework.model.MasterActivty.ActivityResponse;
import com.shamrock.reework.model.MasterActivty.AddActivityRequest;
import com.shamrock.reework.model.MasterActivty.Data;
import com.shamrock.reework.model.MasterActivty.MyActivtyListMaster;
import com.shamrock.reework.util.Utils;

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

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class MyPlansActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener,
        MyPlansAdapter.CheckBoxListener,
        MyPlansAdapter.PlanEditListener,
        ItemlistDialog.GetAddItmeListener,foodPlanUomlistDialog.GetAddFoodPlanUoMListener,
        View.OnClickListener,
        MyPlansEditDialog.PlanEditInterface, onAddFoodPlan, AddActivityDialogListener,DatePickerDialog.OnDateSetListener
{
    Context context;
    private String passtitle="";
    TextView tvTitle;

    Toolbar toolbar;
    Typeface font;
    Button changedate;
    private Utils utils;
    MyPlansService myPlansService;
    SessionManager sessionManager;

    LinearLayout ll_plan_date;
    TextView textView_Category, textView_Date,txt_all_plan_data;
    Button btnSave;
    TextView txt_main_remark;
    TextView tvMeals_food_plan;
    TextView tvMealTime_food_plan;
    Spinner spinnerCategory;
    MyPlanNameMasterAdapter spinnerAdapter;
    ArrayList<MyPlanMastersResponse.MasterData> spinnerList;

    ArrayList<ClsPlanName> arylst_ClsPlanNames;
    TextView txt_uom_foodplan;
    RecyclerView recyclerView;
    MyPlansAdapter myPlansListAdapter;
    ArrayList<MyPlanResponse.MyPlanData> dataList;
    ArrayList<MyPlanResponse.MyPlanData> dataList_filter;
    MyPlansEditDialog editDialog;
    String postDate;
//    SwipeRefreshLayout swipeRefreshLayout;

    String currentDateandTime;
    String planType;
    int planId = -1;
    private int userID,
            reecoachId;
    private FoodService foodService;
    private ArrayList<FoodUnitMasterData> foodUnitMasterDataArrayList;
    private int planIDTemp=0;
    private DatePickerDialog entry_datepickerdialog_history;
    private String temp_title="Today's Plan";
    private ArrayList<com.shamrock.reework.activity.MyPlansModule.addfood.List> arylat_item_list;
    private foodPlanUomlistDialog uomlistDialog;
    private ItemlistDialog itemlistDialog;
    private ArrayList<Uom> arylat_uom_list;
    private TimePickerDialog timepickerdialog;
    PlanItems planItems_temp;
    private int uom_id;
    int itmeID;
    private boolean isFromEdit;
    private String imageUrl;
    ImageView img_user;
    private int sendId;
    private boolean isAlternateAdd;
    private String oldPlanID;
    private boolean isChangeItmeID=false;
    private TextView btn_add_activtity;
    private MyActivityService service;
    private Data data;
    private AddMyPlanActivtyDialogFragment dialogFragment;
    TextView txt_all_glosary;
    private boolean weeklyplan,detailsplan;

    private void setProfileImage()
    {
        imageUrl = "";
        imageUrl = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);



        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);

        if (isValidContextForGlide(context))
        {
            Glide.with(context)
                    .load(imageUrl)
                    .apply(options.circleCrop())
                    .listener(new RequestListener<Drawable>()
                    {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource)
                        {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource)
                        {
                            return false;
                        }
                    })
                    .into(img_user);
        }
    }
    private void callActivityAndSubActivityApi() {

        service = Client.getClient().create(MyActivityService.class);
        utils.showProgressbar(context);
        Call<MyActivtyListMaster> call = service.getAllActivityListMaster();
        call.enqueue(new Callback<MyActivtyListMaster>() {
            @Override
            public void onResponse(Call<MyActivtyListMaster> call, Response<MyActivtyListMaster> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MyActivtyListMaster tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode().equals("1")) {
                        if (tipsResponse.getData() != null) {

                            data = tipsResponse.getData();
                            FragmentManager fm = getSupportFragmentManager();
                            dialogFragment = new AddMyPlanActivtyDialogFragment(MyPlansActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("activtylist", data);
                            dialogFragment.setArguments(bundle);

                            dialogFragment.show(fm, "add_fragment");


                        }
                    } else
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyActivtyListMaster> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    private void callActivityAndSubActivityditEApi() {

        service = Client.getClient().create(MyActivityService.class);
        utils.showProgressbar(context);
        Call<MyActivtyListMaster> call = service.getAllActivityListMaster();
        call.enqueue(new Callback<MyActivtyListMaster>() {
            @Override
            public void onResponse(Call<MyActivtyListMaster> call, Response<MyActivtyListMaster> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    MyActivtyListMaster tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode().equals("1")) {
                        if (tipsResponse.getData() != null) {

                            data = tipsResponse.getData();
//                            FragmentManager fm = getSupportFragmentManager();
//                            dialogFragment = new AddMyPlanActivtyDialogFragment(MyPlansActivity.this);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("activtylist", data);

//                            dialogFragment.setArguments(bundle);

//                            dialogFragment.show(fm, "add_fragment");


//                            home repair, general, vigorous effort(HOME REPAIR)
                            //    String news=planItems_temp.getItemName().toString().split(".");

                            String[] strNewQty=planItems_temp.getItemName().split("\\(");
                            String sub=strNewQty[0].replace(")","");
                            String act=strNewQty[1].replace(")","");


                            AcivityHistory acivityHistory=new AcivityHistory();
                            acivityHistory.setActivityId(planItems_temp.getItemId());
                            acivityHistory.setSubActivityId(String.valueOf(planItems_temp.getSubActivityId()));
                            acivityHistory.setTotalMinutes(String.valueOf(Math.round(Float.parseFloat(planItems_temp.getQuantity()))));

//                            01/02/2022 11:31:22
//                            acivityHistory.setActivityTime(planItems_temp.getActivationTime());
                            acivityHistory.setActivityTime(planItems_temp.getActivationTime());
                            acivityHistory.setActivityName(act);
                            acivityHistory.setSubActivityName(sub);

                            FragmentManager fm = getSupportFragmentManager();
                            dialogFragment = new AddMyPlanActivtyDialogFragment(MyPlansActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isEdit", true);
                            bundle.putSerializable("activtylist", data);
                            bundle.putSerializable("myActivity", acivityHistory);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show(fm, "edit_fragment");


                        }
                    } else
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyActivtyListMaster> call, Throwable t) {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }

    public String formatRepeatSleepDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plans);
        context = MyPlansActivity.this;
         weeklyplan=getIntent().getBooleanExtra("Weekplan",false);
         detailsplan=getIntent().getBooleanExtra("detailsplan",false);

        txt_main_remark=findViewById(R.id.txt_main_remark);
        txt_all_glosary=findViewById(R.id.txt_all_glosary);
        boolean Glosary=getIntent().getBooleanExtra("Glosary",false);
        LinearLayout ll_myplan=findViewById(R.id.ll_myplan);
        if (Glosary){
            ll_myplan.setVisibility(View.INVISIBLE);
        }


        img_user=findViewById(R.id.img_user);
        btn_add_activtity=findViewById(R.id.btn_add_activtity);
        txt_all_plan_data=findViewById(R.id.txt_all_plan_data);
        txt_all_plan_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callAllPlanDataApi(planIDTemp);

            }
        });
        TextView txt_all_glosary=findViewById(R.id.txt_all_glosary);
        txt_all_glosary.setSelected(true);


        txt_all_glosary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyPlansActivity.this,GlosaryListActivity.class);
                intent.putExtra("DataID",sendId);
                startActivity(intent);
            }
        });


        init();
        setToolBar();
        findViews();
        setProfileImage();
        btn_add_activtity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callActivityAndSubActivityApi();








            }
        });
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyPlansActivity.this, DietProfileActivity.class);
                intent.putExtra("ID",sendId);
                startActivity(intent);

            }
        });


        boolean weeklyplan=getIntent().getBooleanExtra("Weekplan",false);
        boolean detailsplan=getIntent().getBooleanExtra("detailsplan",false);
        if (weeklyplan){
            txt_all_plan_data.setVisibility(View.GONE);
            txt_all_plan_data.performClick();
            img_user.setVisibility(View.GONE);
            txt_all_glosary.setVisibility(View.GONE);
            tvTitle.setText("Full Week Plan");
        }
        if (detailsplan){
            img_user.setVisibility(View.GONE);
            txt_all_glosary.setVisibility(View.GONE);
            txt_all_plan_data.setVisibility(View.GONE);
            tvTitle.setText("Details");

        }


    }
    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(MyPlansActivity.this, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();

        if (weeklyplan){
            finish();
            return;
        }
        if (detailsplan){
            finish();
            return;
        }

        Intent intent=new Intent(MyPlansActivity.this,NewMyPlansActivity.class);
        intent.putExtra("SubmitDatemyPlan",postDate);
        intent.putExtra("ShowMyPlanDate",textView_Date.getText().toString());
        intent.putExtra("planIDTemp",String.valueOf(planIDTemp));
        intent.putExtra("temp_title",passtitle);
        intent.putExtra("Backpage","yes");

        overridePendingTransition(0,0);
        finish();
        startActivity(intent);

    }

    private void init()
    {
        utils = new Utils();
        myPlansService = Client.getClient().create(MyPlansService.class);
        sessionManager = new SessionManager(context);

//        userIDuserID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        reecoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        foodService = Client.getClient().create(FoodService.class);

        spinnerList = new ArrayList<>();
        dataList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
        currentDateandTime = sdf.format(new Date());
        ll_plan_date=findViewById(R.id.ll_plan_date);
        showEntryDateAddDailog();
        ll_plan_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry_datepickerdialog_history.show();
            }
        });
    }

    private void showEntryDateAddDailog() {

        try {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            entry_datepickerdialog_history = new DatePickerDialog(MyPlansActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, MyPlansActivity.this, year, month, day);
            Calendar c = Calendar.getInstance();
//            c.set(year, month, day );//Year,Mounth -1,Day
//            entry_datepickerdialog_history.getDatePicker().setMaxDate(c.getTimeInMillis());



            entry_datepickerdialog_history.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button buttonNeg = entry_datepickerdialog_history.getButton(DialogInterface.BUTTON_NEGATIVE);
                    buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    Button buttonPos = entry_datepickerdialog_history.getButton(DialogInterface.BUTTON_POSITIVE);
                    buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
         tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEplan");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weeklyplan){
                    finish();
                    return;
                }
                if (detailsplan){
                    finish();
                    return;
                }
                Intent intent=new Intent(MyPlansActivity.this,NewMyPlansActivity.class);
                intent.putExtra("SubmitDatemyPlan",postDate);
                intent.putExtra("ShowMyPlanDate",textView_Date.getText().toString());
                intent.putExtra("planIDTemp",String.valueOf(planIDTemp));
                intent.putExtra("temp_title",passtitle);
                intent.putExtra("Backpage","yes");

                overridePendingTransition(0,0);
                finish();
                startActivity(intent);

//                finish();
//                Intent intent=new Intent(MyPlansActivity.this,NewMyPlansActivity.class);
//                overridePendingTransition(0,0);
//                startActivity(intent);
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        spinnerCategory = findViewById(R.id.spinner_MyPlan_Category);
        textView_Category = findViewById(R.id.text_MyPlan_Category);
        textView_Date = findViewById(R.id.text_MyPlan_Date);
        btnSave = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.recyclerview);

//        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark,
//                android.R.color.holo_green_light,
//                android.R.color.holo_purple,
//                android.R.color.holo_red_light);

        btnSave.setOnClickListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
        textView_Date.setText(currentDateandTime);

        addAllPlan();

//        callMyPlanMasters();
        postDate=getCurrentDates();

        int planIDTemp= Integer.parseInt(getIntent().getStringExtra("planIDTemp"));
        postDate=getIntent().getStringExtra("SubmitDatemyPlan");
        textView_Date.setText(getIntent().getStringExtra("ShowMyPlanDate"));

        boolean weeklyplan=getIntent().getBooleanExtra("Weekplan",false);




//        getUOMData(reeworkerPlan.getPlanMasterID());
    }

    private void addAllPlan() {
        arylst_ClsPlanNames=new ArrayList<>();
        MyPlanMastersResponse.MasterData data = new MyPlanMastersResponse.MasterData();

        arylst_ClsPlanNames.add(new ClsPlanName("Today's Plan","0"));
        arylst_ClsPlanNames.add(new ClsPlanName("Nutrition Plan","1"));
        arylst_ClsPlanNames.add(new ClsPlanName("Supplements Plan ","2"));
        arylst_ClsPlanNames.add(new ClsPlanName("REEplace Items","4"));
        arylst_ClsPlanNames.add(new ClsPlanName("Lifestyle Plan","5"));
        arylst_ClsPlanNames.add(new ClsPlanName("Food Plan","6"));
//        spinnerList.add(0, data);

        spinnerAdapter = new MyPlanNameMasterAdapter(context, arylst_ClsPlanNames);
        spinnerCategory.setAdapter(spinnerAdapter);

        for (int i = 0; i <arylst_ClsPlanNames.size() ; i++) {
            if (getIntent().getStringExtra("temp_title").equalsIgnoreCase(arylst_ClsPlanNames.get(i).getName())){

                spinnerCategory.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /* Call to fill spinners data */
    private void callDietProfile()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<MyPlanMastersResponse> call = myPlansService.getMasterPlans(request);
        call.enqueue(new Callback<MyPlanMastersResponse>()
        {
            @Override
            public void onResponse(Call<MyPlanMastersResponse> call, Response<MyPlanMastersResponse> response)
            {
                utils.hideProgressbar();

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

//                        spinnerAdapter = new MyPlanMasterAdapter(context, spinnerList);
//                        spinnerCategory.setAdapter(spinnerAdapter);
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
                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        if (spinnerList!= null) {
            planIDTemp=Integer.parseInt(arylst_ClsPlanNames.get(i).getId());

            callTodaysPlanApi(Integer.parseInt(arylst_ClsPlanNames.get(i).getId()));
//            textView_Category.setText(arylst_ClsPlanNames.get(i).getName());

            temp_title=arylst_ClsPlanNames.get(i).getName();
            passtitle=temp_title;


        }
    }

    private void callAddDataApi(String firstfiled) {

        arylst_ClsPlanNames=new ArrayList<>();
        MyPlanMastersResponse.MasterData data = new MyPlanMastersResponse.MasterData();

        arylst_ClsPlanNames.add(new ClsPlanName(firstfiled+"'s Plan","0"));
        arylst_ClsPlanNames.add(new ClsPlanName("Nutrition Plan","1"));
        arylst_ClsPlanNames.add(new ClsPlanName("Supplements Plan ","2"));
        arylst_ClsPlanNames.add(new ClsPlanName("REEplace Items","4"));
        arylst_ClsPlanNames.add(new ClsPlanName("Lifestyle Plan","5"));
        arylst_ClsPlanNames.add(new ClsPlanName("Food Plan","6"));
//        spinnerList.add(0, data);

        spinnerAdapter = new MyPlanNameMasterAdapter(context, arylst_ClsPlanNames);
        spinnerCategory.setAdapter(spinnerAdapter);

    }

    public void loadData(final boolean isSwipeToRefresh)
    {
        try
        {
            /* FOR SAVE BUTTON */
            if (planId == 0)
                btnSave.setVisibility(View.GONE);
            else
                btnSave.setVisibility(View.GONE);

            dataList.clear();

            switch (planId)
            {
                case 0:
//                   textView_Category.setText(planType);

                    if (Utils.isNetworkAvailable(context))
                        getAllPlans(planId, isSwipeToRefresh);
                    else
                        Toast.makeText(context, "Check internet connection", Toast.LENGTH_LONG);
                    break;
                case 1:
//                   textView_Category.setText(planType);

                    if (Utils.isNetworkAvailable(context))
                        getAllPlans(planId, isSwipeToRefresh);
                    else
                        Toast.makeText(context, "Check internet connection", Toast.LENGTH_LONG);
                    break;
                case 2:
//                   textView_Category.setText(planType);

                    if (Utils.isNetworkAvailable(context))
                        getAllPlans(planId, isSwipeToRefresh);
                    else
                        Toast.makeText(context, "Check internet connection", Toast.LENGTH_LONG);
                    break;
                case 3:
//                   textView_Category.setText(planType);

                    if (Utils.isNetworkAvailable(context))
                        getAllPlans(planId, isSwipeToRefresh);
                    else
                        Toast.makeText(context, "Check internet connection", Toast.LENGTH_LONG);
                    break;
                case 4:
//                   textView_Category.setText(planType);
                    if (Utils.isNetworkAvailable(context))
                        getAllPlans(planId, isSwipeToRefresh);
                    else
                        Toast.makeText(context, "Check internet connection", Toast.LENGTH_LONG);
                    break;
                case 5:
//                   textView_Category.setText(planType);
                    if (Utils.isNetworkAvailable(context))
                        getAllPlans(planId,isSwipeToRefresh);
                    else
                        Toast.makeText(context, "Check internet connection", Toast.LENGTH_LONG);
                    break;
            }
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView){}


    private void getAllPlans(final int planId, final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        if (planId == 0) //IF IT IS MYPLANS
        {
            MyPlanRequest request = new MyPlanRequest();
            request.setUserid(userID);
            request.setReecoachId(reecoachId);
            request.setPlanID(planId);

            /*Clearing plan data*/
            dataList.clear();

            Call<MyPlanResponse> call = myPlansService.getTodaysPlan(request);
            call.enqueue(new Callback<MyPlanResponse>()
            {
                @Override
                public void onResponse(Call<MyPlanResponse> call, Response<MyPlanResponse> response)
                {
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        MyPlanResponse listResponse = response.body();

                        if (listResponse != null && listResponse.getCode() == 1)
                        {
                            btnSave.setVisibility(View.GONE);

                            dataList = response.body().getData();


                            if (dataList != null && dataList.size() > 0)
                            {
                                setRecyclerView(dataList, planId);
                            }
                            else
                            {
                                setRecyclerView(dataList, planId);
                                Toast.makeText(context, "No data to show !", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            dataList.clear();
                            setRecyclerView(dataList, planId);
                            Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                            btnSave.setVisibility(View.GONE);
                        }
                    }
                    else
                    {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<MyPlanResponse> call, Throwable t)
                {
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    Log.d("ERROR--->", t.toString());
                }
            });
        }
        else //FOR REST OF THE PLANS
        {
            MyPlanRequest request = new MyPlanRequest();
            request.setUserid(userID);
            request.setReecoachId(reecoachId);
            request.setPlanID(planId);
            request.setIsActive(1);

            /*Clearing plan data*/
            dataList.clear();

            Call<MyPlanResponse> call = myPlansService.getAllPlans(request);
            call.enqueue(new Callback<MyPlanResponse>()
            {
                @Override
                public void onResponse(Call<MyPlanResponse> call, Response<MyPlanResponse> response)
                {
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        MyPlanResponse listResponse = response.body();

                        if (listResponse != null && listResponse.getCode() == 1)
                        {
                            dataList = response.body().getData();

                            if (dataList != null && dataList.size() > 0)
                            {
                                setRecyclerView(dataList, planId);
                            }
                            else
                            {
                                setRecyclerView(dataList, planId);
                                Toast.makeText(context, "No data to show !", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            dataList.clear();
                            setRecyclerView(dataList, planId);
                            Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MyPlanResponse> call, Throwable t)
                {
                    if (!isSwipeToRefresh)
                        utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    Log.d("ERROR--->", t.toString());
                }
            });
        }
    }

    public void setRecyclerView(ArrayList<MyPlanResponse.MyPlanData> tempDataList, int planId)
    {
        myPlansListAdapter = new MyPlansAdapter(context, tempDataList,
                MyPlansActivity.this, planId, MyPlansActivity.this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(MyPlansActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myPlansListAdapter);

        myPlansListAdapter.notifyDataSetChanged();

//        btnSave.performClick();
    }

    /* ON EDIT CLICK FROM ADAPTER */
    @Override
    public void editedPlan(int pos, ArrayList<MyPlanResponse.MyPlanData> list)
    {
        if (list != null)
        {
            FragmentManager fm = getSupportFragmentManager();
            editDialog = new MyPlansEditDialog();

            Bundle bundle = new Bundle();
            bundle.putSerializable("LIST", list);
            bundle.putInt("POSITION", pos);
            editDialog.setArguments(bundle);
            editDialog.show(fm, "editPlan");
        }
    }

    @Override
    public void isCheckBoxSelected(int pos, boolean isDone)
    {
        if (pos > -1)
        {
            dataList.get(pos).setAction(isDone);
            //dataList.get(pos).setDateOfAction(getCurrentDate());
        }
    }

    /*EDIT FROM DIALOG*/
    @Override
    public void onEdit(ArrayList<MyPlanResponse.MyPlanData> list)
    {
        setRecyclerView(list, planId);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnSave)
        {
            if (Utils.isNetworkAvailable(context))
            {
                callForDoneActivites();
            }
            else
                Snackbar.make(findViewById(android.R.id.content), "Please check internet connection !", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void callForDoneActivites()
    {
        utils.showProgressbar(context);
        dataList_filter=new ArrayList<>();
        dataList_filter.clear();
        for (int i = 0; i <dataList.size() ; i++) {
            dataList_filter.add(dataList.get(i));
            if (dataList.get(i).getScheduledValue()!=null){
                dataList_filter.get(i).setScheduledValue(dataList.get(i).getScheduledValue());

            }else {
                dataList_filter.get(i).setScheduledValue("0");

            }

        }


        MyPlansCheckRequest request = new MyPlansCheckRequest();
        request.setTodaysSchedules(dataList_filter);

        /*Gson gson = new Gson();
        String json = gson.toJson(request);*/

        Call<MyPlanResponse> call = myPlansService.setDonePlans(request);
        call.enqueue(new Callback<MyPlanResponse>()
        {
            @Override
            public void onResponse(Call<MyPlanResponse> call, Response<MyPlanResponse> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MyPlanResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("ERROR---->>>", response.toString());
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<MyPlanResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }

    public String getCurrentDate()
    {
        String date = "";
        try
        {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        }catch (Exception e){e.printStackTrace();}
        return date;
    }

    private void callTodaysPlanApi(int planID)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<ClsTodaysPlanMain> call = myPlansService.getTodaysPlan(userID,planID,postDate);
        call.enqueue(new Callback<ClsTodaysPlanMain>()
        {
            @Override
            public void onResponse(Call<ClsTodaysPlanMain> call, Response<ClsTodaysPlanMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsTodaysPlanMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        sendId= Integer.parseInt(listResponse.getData().getId());
                        boolean Glosary=getIntent().getBooleanExtra("Glosary",false);

                        if (Glosary){
                            txt_all_glosary.performClick();
                            finish();
                            return;
                        }



                        TodaysPlanData aryltTodaysPlanData = response.body().getData();


                        if (aryltTodaysPlanData.getTitle()!=null&&!aryltTodaysPlanData.getTitle().isEmpty()){
                            textView_Category.setText(aryltTodaysPlanData.getTitle());
                        }else {
                            if (aryltTodaysPlanData.getTitle()!=null&&!aryltTodaysPlanData.getTitle().isEmpty()){
                                textView_Category.setText(temp_title);
                            }
                        }



                        ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=null;
                        artylst_ReeworkerPlans=aryltTodaysPlanData.getReeworkerPlan();
                        if (aryltTodaysPlanData.getRemark()!=null){
                            txt_main_remark.setVisibility(View.VISIBLE);
                            txt_main_remark.setText("Remark : "+aryltTodaysPlanData.getRemark());

                        }





                        ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);
                        recyclerView.setAdapter(clsTodaysPlansAdapter);


                        try {

                            for (int i = 0; i <artylst_ReeworkerPlans.size() ; i++) {
                                if (sessionManager.getStringValue("Mealtypescroll").equalsIgnoreCase(artylst_ReeworkerPlans.get(i).getMealType())){
                                    recyclerView.scrollToPosition(i);
                                    sessionManager.setStringValue("Mealtypescroll","");
                                    break;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else
                    {
                        textView_Category.setText(temp_title);

                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=new ArrayList<>();
                        ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);

                        recyclerView.setAdapter(clsTodaysPlansAdapter);
                    }
                }
                else
                {
                    textView_Category.setText(temp_title);

                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClsTodaysPlanMain> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
                Toast.makeText(context,"No data found ", Toast.LENGTH_LONG).show();
                textView_Category.setText(temp_title);

                ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=new ArrayList<>();
                ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);

                recyclerView.setAdapter(clsTodaysPlansAdapter);
            }
        });
    }

    private void callAllPlanDataApi(int planIDTemp)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<ClsTodaysPlanMain> call = myPlansService.getAllTodaysPlan(userID,planIDTemp,postDate, String.valueOf(planIDTemp));
        call.enqueue(new Callback<ClsTodaysPlanMain>()
        {
            @Override
            public void onResponse(Call<ClsTodaysPlanMain> call, Response<ClsTodaysPlanMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsTodaysPlanMain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        sendId= Integer.parseInt(listResponse.getData().getId());



                        TodaysPlanData aryltTodaysPlanData = response.body().getData();


                        if (aryltTodaysPlanData.getTitle()!=null&&!aryltTodaysPlanData.getTitle().isEmpty()){
                            textView_Category.setText(aryltTodaysPlanData.getTitle());
                        }else {
                            if (aryltTodaysPlanData.getTitle()!=null&&!aryltTodaysPlanData.getTitle().isEmpty()){
                                textView_Category.setText(temp_title);
                            }
                        }



                        ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=null;
                        artylst_ReeworkerPlans=aryltTodaysPlanData.getReeworkerPlan();
                        if (aryltTodaysPlanData.getRemark()!=null){
                            txt_main_remark.setVisibility(View.VISIBLE);
                            txt_main_remark.setText("Remark : "+aryltTodaysPlanData.getRemark());

                        }





                        ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);
                        recyclerView.setAdapter(clsTodaysPlansAdapter);




                    }
                    else
                    {
                        textView_Category.setText(temp_title);

                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=new ArrayList<>();
                        ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);

                        recyclerView.setAdapter(clsTodaysPlansAdapter);
                    }
                }
                else
                {
                    textView_Category.setText(temp_title);

                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClsTodaysPlanMain> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
                Toast.makeText(context,"No data found ", Toast.LENGTH_LONG).show();
                textView_Category.setText(temp_title);

                ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=new ArrayList<>();
                ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);

                recyclerView.setAdapter(clsTodaysPlansAdapter);
            }
        });
    }
    private void getUOMData(String masterID, final PlanItems reeworkerPlan, final String mealType, final String time, String itemId) {


        {
            if (masterID.equals("2")||masterID.equals("6")||masterID.equals("1")){
                masterID=reeworkerPlan.getItemId();

                Call<ClsFoodUnitMasterPojo> call = foodService.getSupplimentItmeWiseUOMw((Integer.parseInt(masterID)));
                call.enqueue(new Callback<ClsFoodUnitMasterPojo>()
                {
                    @Override
                    public void onResponse(Call<ClsFoodUnitMasterPojo> call, Response<ClsFoodUnitMasterPojo> response)
                    {

                        List<String> tempList;
                        if (response.code() == Client.RESPONSE_CODE_OK)
                        {
                            ClsFoodUnitMasterPojo listResponse = response.body();
                            if (listResponse != null && listResponse.getCode().equals("1"))
                            {


                                foodUnitMasterDataArrayList = response.body().getData();
                                if (foodUnitMasterDataArrayList!=null){
                                    if (!foodUnitMasterDataArrayList.isEmpty()){

                                        saveTodysMeal(reeworkerPlan,mealType,time);


                                    }
                                }


                            }
                            else
                            {
                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ClsFoodUnitMasterPojo> call, Throwable t)
                    {
                        // Log error here since request failed
                        Log.e("MealType---->", t.toString());
                    }
                });
            }else {

                Call<ClsFoodUnitMasterPojo> call = foodService.getItmeWiseUOMw(Integer.parseInt(masterID));
                call.enqueue(new Callback<ClsFoodUnitMasterPojo>()
                {
                    @Override
                    public void onResponse(Call<ClsFoodUnitMasterPojo> call, Response<ClsFoodUnitMasterPojo> response)
                    {

                        List<String> tempList;
                        if (response.code() == Client.RESPONSE_CODE_OK)
                        {
                            ClsFoodUnitMasterPojo listResponse = response.body();
                            if (listResponse != null && listResponse.getCode().equals("1"))
                            {


                                foodUnitMasterDataArrayList = response.body().getData();
                                if (foodUnitMasterDataArrayList!=null){
                                    if (!foodUnitMasterDataArrayList.isEmpty()){

                                        saveTodysMeal(reeworkerPlan,mealType,time);


                                    }
                                }


                            }
                            else
                            {
                                Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ClsFoodUnitMasterPojo> call, Throwable t)
                    {
                        // Log error here since request failed
                        Log.e("MealType---->", t.toString());
                    }
                });
            }

        }
    }




    private void getItemWiseUOMData(String itmeid) {


        {

            Call<ClsFoodUnitMasterPojo> call = foodService.getSupplimentItmeWiseUOMw(Integer.parseInt(itmeid));
            call.enqueue(new Callback<ClsFoodUnitMasterPojo>()
            {
                @Override
                public void onResponse(Call<ClsFoodUnitMasterPojo> call, Response<ClsFoodUnitMasterPojo> response)
                {

                    List<String> tempList;
                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsFoodUnitMasterPojo listResponse = response.body();
                        if (listResponse != null && listResponse.getCode().equals("1"))
                        {


                            foodUnitMasterDataArrayList = response.body().getData();
                            if (foodUnitMasterDataArrayList!=null){
                                if (!foodUnitMasterDataArrayList.isEmpty()){
                                    arylat_uom_list.clear();
                                    for (int i = 0; i <foodUnitMasterDataArrayList.size() ; i++) {

                                        arylat_uom_list.add(new Uom(new ArrayList<Groups>()
                                                ,""+foodUnitMasterDataArrayList.get(i).getId()
                                                ,""+foodUnitMasterDataArrayList.get(i).getMeasurement(),
                                                ""+foodUnitMasterDataArrayList.get(i).getIsActive()
                                                ,"true"));

                                    }



                                }
                            }


                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsFoodUnitMasterPojo> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                }
            });
        }
    }

    private void getEditItemWiseUOMData(String itmeid) {


        {

//            Call<ClsFoodUnitMasterPojo> call = foodService.getItmeWiseUOMw(Integer.parseInt(itmeid));
            Call<ClsFoodUnitMasterPojo> call = foodService.getSupplimentItmeWiseUOMw(Integer.parseInt(itmeid));
            call.enqueue(new Callback<ClsFoodUnitMasterPojo>()
            {
                @Override
                public void onResponse(Call<ClsFoodUnitMasterPojo> call, Response<ClsFoodUnitMasterPojo> response)
                {

                    List<String> tempList;
                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsFoodUnitMasterPojo listResponse = response.body();
                        if (listResponse != null && listResponse.getCode().equals("1"))
                        {


                            foodUnitMasterDataArrayList = response.body().getData();
                            if (foodUnitMasterDataArrayList!=null){
                                if (!foodUnitMasterDataArrayList.isEmpty()){

                                    try{
                                        arylat_uom_list=new ArrayList<>();
                                        arylat_uom_list.clear();
                                        for (int i = 0; i <foodUnitMasterDataArrayList.size() ; i++) {

                                            arylat_uom_list.add(new Uom(new ArrayList<Groups>()
                                                    ,""+foodUnitMasterDataArrayList.get(i).getId()
                                                    ,""+foodUnitMasterDataArrayList.get(i).getMeasurement(),
                                                    ""+foodUnitMasterDataArrayList.get(i).getIsActive()
                                                    ,"true"));

                                        }
                                        getAllFooodPlans(planItems_temp.getDialyPlanMasterID());



                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }



                                }
                            }


                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsFoodUnitMasterPojo> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                }
            });
        }
    }



    private void saveTodysMeal(PlanItems planItemsArrayList, String mealType, String planTime) {
        final Utils utils=new Utils();
        utils.showProgressbar(MyPlansActivity.this);


        ArrayList< AddMealRequest.LstSubMealDatum> itemDataList = new ArrayList<>();
        AddMealRequest request = new AddMealRequest();
        request.setUserId(userID);
        request.setMealType(mealType);
        request.setIntakeTime(planTime);
        request.setCreatedOn(postDate);
        request.setItemTypeId(Integer.parseInt(planItemsArrayList.getPlanMasterID()));



        AddMealRequest.LstSubMealDatum  ItemData  = new AddMealRequest.LstSubMealDatum();
        ItemData.setRecipeId(Integer.valueOf(planItemsArrayList.getItemId()));
        ItemData.setMealQty(String.valueOf(planItemsArrayList.getQuantity()));
        ItemData.setIntakeTime(planTime);
        ItemData.setUomId(Integer.parseInt(planItemsArrayList.getUomId()));

        if (foodUnitMasterDataArrayList!=null){
            for (int j = 0; j <foodUnitMasterDataArrayList.size() ; j++) {
                if (foodUnitMasterDataArrayList.get(j).getId().equalsIgnoreCase(planItemsArrayList.getUomId())){
                    ItemData.setValueInGram(foodUnitMasterDataArrayList.get(j).getValueInGram());
                    break;
                }


            }

        }else {
            ItemData.setValueInGram(1.0);

        }

        ItemData.setMeal_type(mealType);
        itemDataList.add(ItemData);
        request.setLstSubMealData(itemDataList);


        String S   = new Gson().toJson(request);
        String Test = S;

        Call<GetMealType> call = foodService.saveToadysMeal(request);
        call.enqueue(new Callback<GetMealType>()
        {
            @Override
            public void onResponse(Call<GetMealType> call, Response<GetMealType> response)
            {

                utils.hideProgressbar();

                List<String> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetMealType listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(MyPlansActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {


                    }
                }
            }

            @Override
            public void onFailure(Call<GetMealType> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void onAddFoodPlanClick(PlanItems planItems) {
        isFromEdit=false;
        planItems_temp=null;

        planItems_temp=planItems;



        if (planItems_temp.getPostPlanID().equalsIgnoreCase("5")){
            callActivityAndSubActivityApi();
        }else {


            getAllFooodPlans(planItems.getDialyPlanMasterID() );

        }


    }

    @Override
    public void onAddnewFoodPlanClick(PlanItems id) {

    }

    @Override
    public void onEditFoodPlanClick(PlanItems planItems) {
        isFromEdit=true;
        planItems_temp=null;
        planItems_temp=planItems;

        oldPlanID=planItems.getItemId();

        if (planItems.isAlternate()){
            isAlternateAdd=true;

        }else {
            isAlternateAdd=false;
        }



        getEditItemWiseUOMData(String.valueOf(planItems.getItemId()));








    }

    @Override
    public void getFoodPlanData(PlanItems reeworkerPlan, String mealType, String planTime, String itemId) {


        getUOMData(reeworkerPlan.getPlanMasterID(),reeworkerPlan,mealType,planTime,itemId);



    }

    @Override
    public void getLifeStyleplan(PlanItems reeworkerPlan, String mealType, String planTime) {



//        addLifeStylePlan("","",5,3,"");
        double time=Double.parseDouble(reeworkerPlan.getQuantity());
        int value=(int)time;
        addLifeStylePlan(reeworkerPlan.getItemName(),planTime,value,1,"Existing");

    }

    @Override
    public void addActivityLifeStyleplantoDailyDaily(PlanItems reeworkerPlan) {

        callAddActivtyAPI(reeworkerPlan);

    }
    private void callAddActivtyAPI(PlanItems reeworkerPlan) {





        AddActivityRequest addActivityRequest = new AddActivityRequest();
        addActivityRequest.setActivityId(reeworkerPlan.getItemId());
        addActivityRequest.setSubActivityId(String.valueOf(reeworkerPlan.getSubActivityId()));
        addActivityRequest.setTotalMinutes(String.valueOf(Math.round(Float.parseFloat(reeworkerPlan.getQuantity()))));
        addActivityRequest.setId("0");
        addActivityRequest.setActivityTime(formatTimeDates(reeworkerPlan.getActivationTime()));
        addActivityRequest.setCreatedOn(postDate);
        addActivityRequest.setUserId(String.valueOf(userID));
        utils.showProgressbar(context);
        service = Client.getClient().create(MyActivityService.class);

        Call<ActivityResponse> call = service.setActivityData(addActivityRequest);
        call.enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ActivityResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {

                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });


    }
    public String formatTimeDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }



    @Override
    public void onNotificationClick(int id, boolean status, boolean isAdded, boolean alternate, int alternatePostItemID, int submitID, String mainID) {

        updateNotificationFlag(id,status,isAdded,alternate,alternatePostItemID,submitID,mainID);

    }

    @Override
    public void onViewRecipe(int linkedRecipeId) {



        callToGetRecipe(linkedRecipeId);

//


    }

    private void callToGetRecipe(int linkedRecipeId)
    {



        GetRecipeRequest request = new GetRecipeRequest();
        request.setEditId(0);
        request.setRecipeId(linkedRecipeId);
        utils.showProgressbar(context);

        Call<RecipeResponse> call = foodService.GetRecipe(request);
        call.enqueue(new Callback<RecipeResponse>()
        {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    RecipeResponse recipeResponse = response.body();

                    if (recipeResponse != null && recipeResponse.getCode() == 1)
                    {
                        ArrayList<RecipeResponse.Recipee> tempList = recipeResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
//                            setData(tempList);

                            Intent intent=new Intent(context, FoodRecipeActivity.class);
                            intent.putExtra("RECEIPE_ID", 0);
                            intent.putExtra("PLAN_RECEIPE", tempList);
                            intent.putExtra("EDIT_ID",0);
                            intent.putExtra("RECEIPE_Image","");
                            intent.putExtra("FROMPLAN",true);
                            context.startActivity(intent);

                        }
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), recipeResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t)
            {
                Log.d("error",t.getMessage());
                utils.hideProgressbar();
            }
        });
    }

    private void callAPIViewRecipeAPI() {


    }

    public String getCurrentDates()
    {
        String date = "";
        try
        {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        }catch (Exception e){e.printStackTrace();}
        return date;
    }

    public void addLifeStylePlan(String activityname,String activitytime,int durationInminu,int daytype,String planType){
        Integer planID=0;
        int dayType=1;

        LifeStylePlanPost lifeStylePlanPost=new LifeStylePlanPost(planID,Integer.valueOf(new SessionManager(MyPlansActivity.this).getIntValue(SessionManager.KEY_USER_ID)),activityname,activitytime,durationInminu,"",dayType, planType);
        insertLifeStyleplanApi(lifeStylePlanPost);
    }
    private void insertLifeStyleplanApi(LifeStylePlanPost lifeStylePlanPost)
    {
        LifeStylePlanService regService = Client.getClient().create(LifeStylePlanService.class);
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


                utils.hideProgressbar();


                if (response.code() == Client.RESPONSE_CODE_OK) {
                    LifeStylePlanPostsucces listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {

                        if (listResponse.getMessage()!=null){
                            Toast.makeText(MyPlansActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }



                    }
                } else {
                    utils.hideProgressbar();
                }

            }

            @Override
            public void onFailure(Call<LifeStylePlanPostsucces> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }
    private void updateNotificationFlag(int id, boolean notifystatus, boolean isAddedFlag, boolean alternate, int alternatePostItemID, int submitID, String mainID)
    {
        utils.showProgressbar(context);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);
        int idAletnate;
        if (alternate){
            idAletnate=alternatePostItemID;
        }else {
            idAletnate=0;
            idAletnate=alternatePostItemID;
        }

        Call<ClsEditSleepResonse> call = myPlansService.SetTodaysPlan(submitID,isAddedFlag,notifystatus,postDate, Integer.parseInt(mainID));
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsEditSleepResonse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
//                       sunit
                        callTodaysPlanApi(planIDTemp);

                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();


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
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1) + "-" + year;
//        yyyy-MM-dd

        postDate=year+"-"+getFromattedStringDate(month+1)+"-"+getFromattedStringDate(dayOfMonth);
        textView_Date.setText(formatDatesSleep(dummydate_from));

        if (postDate.equalsIgnoreCase(getCurrentDates())){
            temp_title="Today";

        }else {
            temp_title=formatSpinnerDatesSleep(dummydate_from);

        }


        callTodaysPlanApi(planIDTemp);

        if (postDate.equalsIgnoreCase(getCurrentDates())){
            callAddDataApi("Today");

        }else {
            callAddDataApi(formatSpinnerDatesSleep(dummydate_from));

        }






    }
    public String getFromattedStringDate(int s){


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }



    }
    public String formatDatesSleep(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    public String formatSpinnerDatesSleep(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd MMM");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    private void getAllFooodPlans(final int planId)
    {

        MyPlanRequest request = new MyPlanRequest();
        request.setUserid(userID);
        request.setReecoachId(reecoachId);
        request.setPlanID(planId);

        /*Clearing plan data*/
        dataList.clear();
        utils.showProgressbar(context);
        String sendType;
        if (isAlternateAdd){
            sendType="A";
        }else {
            sendType="I";
        }

        Call<ClsTodaysPlanAddFood> call = myPlansService.getItemList(planId);
        call.enqueue(new Callback<ClsTodaysPlanAddFood>()
        {
            @Override
            public void onResponse(Call<ClsTodaysPlanAddFood> call, Response<ClsTodaysPlanAddFood> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsTodaysPlanAddFood listResponse = response.body();

                    if (listResponse != null)
                    {
                        btnSave.setVisibility(View.GONE);

                        arylat_item_list = response.body().getList();
//                        arylat_uom_list = response.body().getUom();


                        if (arylat_item_list != null && arylat_item_list.size() > 0)
                        {
                            if (planItems_temp.getPostPlanID().equalsIgnoreCase("5")){
//                                showDataInItemListDialogAcirvity(arylat_item_list,arylat_uom_list,planId);
                                callActivityAndSubActivityditEApi();

                            }else {
                                showDataInItemListDialog(arylat_item_list,arylat_uom_list,planId);

                            }

                        }
                        else
                        {

                        }
                    }
                    else
                    {

                    }
                }
                else
                {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClsTodaysPlanAddFood> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });
        //FOR REST OF THE PLANS

    }





    private void showDataInItemListDialog(final ArrayList<com.shamrock.reework.activity.MyPlansModule.addfood.List> arylat_item_list, final ArrayList<Uom> arylat_uom_list, int planId) {
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.lay_add_foodplan);
        tvMealTime_food_plan=dialog.findViewById(R.id.tvMealTime_food_plan);

        final EditText tvQty_food_plan=dialog.findViewById(R.id.tvQty_food_plan);
        ImageView img_add_more = dialog.findViewById(R.id.img_add_more);
        img_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(context, MasterDetailsActivity.class);
                intent.putExtra("FRAGMENT_POSITION", 0);
                sessionManager.setStringValue("AddFood","true");
                sessionManager.setStringValue("AddFoodFromMyPlan","true");

                intent.putExtra("MEAL_CAL_MAX", 0);
                context.startActivity(intent);
            }
        });

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        ImageView imgLeft = dialog.findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvMeals_food_plan=dialog.findViewById(R.id.tvMeals_food_plan);
        final EditText edt_remark_food_plan=dialog.findViewById(R.id.edt_remark_food_plan);

        if (isFromEdit){

            String name=arylst_ClsPlanNames.get(spinnerCategory.getSelectedItemPosition()).getName();


            tvTitle.setText("Edit "+name+"");

            tvQty_food_plan.setText(planItems_temp.getQuantity());
            edt_remark_food_plan.setText(planItems_temp.getRemark());
            if (planItems_temp.getPostPlanID().equalsIgnoreCase("5")){
                tvMeals_food_plan.setEnabled(true);

            }else {
                tvMeals_food_plan.setEnabled(true);

            }

        }else {
            tvMeals_food_plan.setEnabled(true);

            String name=arylst_ClsPlanNames.get(spinnerCategory.getSelectedItemPosition()).getName();

            tvTitle.setText("Add "+name+"");
            if (planId==5){
                tvQty_food_plan.setHint("Mins");
                tvMeals_food_plan.setText("Select Activity");
            }else {
                tvQty_food_plan.setHint("0.00");
                tvMeals_food_plan.setText("Select Meal");


            }
            if (planId==5){
                tvQty_food_plan.setHint("Mins");
            }else {
                tvQty_food_plan.setHint("0.00");

            }
            tvQty_food_plan.setFilters(new InputFilter[]{new DigitsInputFilter(4, 2, 3000)});


        }
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                dialog.cancel();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });





        Button btnSave_foodplan=dialog.findViewById(R.id.btnSave_foodplan);
        btnSave_foodplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvMeals_food_plan.getText().toString().equalsIgnoreCase("Select Activity")||tvMeals_food_plan.getText().toString().equalsIgnoreCase("Select Meal")){
                    if (planItems_temp.getDialyPlanMasterID()==5){
                        Toast.makeText(context, "Please select activity", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Toast.makeText(context, "Please select Meal", Toast.LENGTH_SHORT).show();
                        return;

                    }
                }

                if (txt_uom_foodplan.getText().toString().equalsIgnoreCase("Select Measurement")){
                    Toast.makeText(context, "Please select Measurement", Toast.LENGTH_SHORT).show();
                    return;
                }


                postFoodPlans(planItems_temp.getReeworkerPlanID(),Integer.parseInt(planItems_temp.getId()),Integer.parseInt(planItems_temp.getItemId()),planItems_temp.getItemId(), Double.parseDouble(tvQty_food_plan.getText().toString()),edt_remark_food_plan.getText().toString().trim(), String.valueOf(uom_id),planItems_temp.getReeworkerplanId());
                dialog.dismiss();
            }
        });
        tvMealTime_food_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepickerdialog = new TimePickerDialog(context, MyPlansActivity.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);
                timepickerdialog.show();
            }
        });
        txt_uom_foodplan=dialog.findViewById(R.id.txt_uom_foodplan);
        txt_uom_foodplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm;

                fm = getSupportFragmentManager();
                uomlistDialog = new foodPlanUomlistDialog();
                Bundle  bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arylat_uom_list);
                bundle.putSerializable("isFromEdit",isFromEdit);
                uomlistDialog.setArguments(bundle);
                uomlistDialog.show(fm, "COUNTRY_FRAGMENT");
            }
        });

        tvMeals_food_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arylst_string_item_name=new ArrayList<>();

                for (int i = 0; i <arylat_item_list.size() ; i++) {
                    arylst_string_item_name.add(arylat_item_list.get(i).getText());
                }
                FragmentManager fm;

                fm = getSupportFragmentManager();
                itemlistDialog = new ItemlistDialog();
                Bundle  bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arylat_item_list);
//                bundle.putSerializable("isFromEdit",isFromEdit);
                if (isFromEdit){
                    bundle.putSerializable("ItemToBeEdit",planItems_temp.getItemId());

                }

                itemlistDialog.setArguments(bundle);
                itemlistDialog.show(fm, "COUNTRY_FRAGMENT");
            }
        });

        if (isFromEdit){

//            for (int i = 0; i <arylat_item_list.size() ; i++) {
//                if (planItems_temp.getItemId().equals(arylat_item_list.get(i).getValue())){
//                    itmeID= Integer.parseInt(arylat_item_list.get(i).getValue());
//                    tvMeals_food_plan.setText(arylat_item_list.get(i).getText());
//
//                    break;
//                }
//
//            }
            itmeID= Integer.parseInt(planItems_temp.getItemId());
            tvMeals_food_plan.setText(planItems_temp.getItemName());


            try{
                for (int i = 0; i <arylat_uom_list.size() ; i++) {
                    if (planItems_temp.getMeasurement().equalsIgnoreCase(arylat_uom_list.get(i).getText())){
                        uom_id= Integer.parseInt(arylat_uom_list.get(i).getValue());
                        txt_uom_foodplan.setText(arylat_uom_list.get(i).getText());

                        break;
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }



        }

        dialog.show();
    }
    private void showDataInItemListDialogAcirvity(final ArrayList<com.shamrock.reework.activity.MyPlansModule.addfood.List> arylat_item_list, final ArrayList<Uom> arylat_uom_list, int planId) {
        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.lay_add_foodplan);
        tvMealTime_food_plan=dialog.findViewById(R.id.tvMealTime_food_plan);

        final EditText tvQty_food_plan=dialog.findViewById(R.id.tvQty_food_plan);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        ImageView imgLeft = dialog.findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvMeals_food_plan=dialog.findViewById(R.id.tvMeals_food_plan);
        final EditText edt_remark_food_plan=dialog.findViewById(R.id.edt_remark_food_plan);

        if (isFromEdit){

            String name=arylst_ClsPlanNames.get(spinnerCategory.getSelectedItemPosition()).getName();


            tvTitle.setText("Edit "+name+"");

            tvQty_food_plan.setText(planItems_temp.getQuantity());
            edt_remark_food_plan.setText(planItems_temp.getRemark());
            if (planItems_temp.getPostPlanID().equalsIgnoreCase("5")){
                tvMeals_food_plan.setEnabled(true);

            }else {
                tvMeals_food_plan.setEnabled(true);

            }


        }else {
            tvMeals_food_plan.setEnabled(true);

            String name=arylst_ClsPlanNames.get(spinnerCategory.getSelectedItemPosition()).getName();

            tvTitle.setText("Add "+name+"");
            if (planId==5){
                tvQty_food_plan.setHint("Mins");
                tvMeals_food_plan.setText("Select Activity");
            }else {
                tvQty_food_plan.setHint("0.00");
                tvMeals_food_plan.setText("Select Meal");


            }
            if (planId==5){
                tvQty_food_plan.setHint("Mins");
            }else {
                tvQty_food_plan.setHint("0.00");

            }
            tvQty_food_plan.setFilters(new InputFilter[]{new DigitsInputFilter(4, 2, 3000)});


        }
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                dialog.cancel();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });





        Button btnSave_foodplan=dialog.findViewById(R.id.btnSave_foodplan);
        btnSave_foodplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvMeals_food_plan.getText().toString().equalsIgnoreCase("Select Activity")||tvMeals_food_plan.getText().toString().equalsIgnoreCase("Select Meal")){
                    if (planItems_temp.getDialyPlanMasterID()==5){
                        Toast.makeText(context, "Please select activity", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Toast.makeText(context, "Please select Meal", Toast.LENGTH_SHORT).show();
                        return;

                    }
                }

                if (txt_uom_foodplan.getText().toString().equalsIgnoreCase("Select Measurement")){
                    Toast.makeText(context, "Please select Measurement", Toast.LENGTH_SHORT).show();
                    return;
                }


                postFoodPlans(planItems_temp.getReeworkerPlanID(),Integer.parseInt(planItems_temp.getId()),Integer.parseInt(planItems_temp.getItemId()),planItems_temp.getItemId(), Double.parseDouble(tvQty_food_plan.getText().toString()),edt_remark_food_plan.getText().toString().trim(), String.valueOf(uom_id),planItems_temp.getReeworkerplanId());
                dialog.dismiss();
            }
        });
        tvMealTime_food_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepickerdialog = new TimePickerDialog(context, MyPlansActivity.this,
                        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                        Calendar.getInstance().get(Calendar.MINUTE),
                        false);
                timepickerdialog.show();
            }
        });
        txt_uom_foodplan=dialog.findViewById(R.id.txt_uom_foodplan);
        txt_uom_foodplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arylat_uom_list!=null&&!arylat_uom_list.isEmpty()){
                    FragmentManager fm;

                    fm = getSupportFragmentManager();
                    uomlistDialog = new foodPlanUomlistDialog();
                    Bundle  bundle = new Bundle();
                    bundle.putSerializable("COUNTRY_LIST", arylat_uom_list);
                    bundle.putSerializable("isFromEdit",isFromEdit);
                    uomlistDialog.setArguments(bundle);
                    uomlistDialog.show(fm, "COUNTRY_FRAGMENT");
                }

            }
        });

        tvMeals_food_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arylst_string_item_name=new ArrayList<>();

                for (int i = 0; i <arylat_item_list.size() ; i++) {
                    arylst_string_item_name.add(arylat_item_list.get(i).getText());
                }
                FragmentManager fm;

                fm = getSupportFragmentManager();
                itemlistDialog = new ItemlistDialog();
                Bundle  bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", arylat_item_list);
//                bundle.putSerializable("isFromEdit",isFromEdit);
                if (isFromEdit){
                    bundle.putSerializable("ItemToBeEdit",planItems_temp.getItemId());

                }

                itemlistDialog.setArguments(bundle);
                itemlistDialog.show(fm, "COUNTRY_FRAGMENT");
            }
        });

        if (isFromEdit){

            for (int i = 0; i <arylat_item_list.size() ; i++) {
                if (planItems_temp.getItemId().equals(arylat_item_list.get(i).getValue())){
                    itmeID= Integer.parseInt(arylat_item_list.get(i).getValue());
                    tvMeals_food_plan.setText(arylat_item_list.get(i).getText());

                    break;
                }

            }

            for (int i = 0; i <arylat_item_list.size() ; i++) {
                if (planItems_temp.getMeasurement().equalsIgnoreCase(arylat_uom_list.get(i).getText())){
                    uom_id= Integer.parseInt(arylat_uom_list.get(i).getValue());
                    txt_uom_foodplan.setText(arylat_uom_list.get(i).getText());

                    break;
                }

            }

        }

        dialog.show();
    }

    @Override
    public void onSubmitFoodPlanItem(com.shamrock.reework.activity.MyPlansModule.addfood.List model) {
        itmeID= Integer.parseInt(model.getValue());
        tvMeals_food_plan.setText(model.getText());
        if (!planItems_temp.getItemId().equalsIgnoreCase(String.valueOf(itmeID))){
            isChangeItmeID=true;
        }

        getItemWiseUOMData(String.valueOf(itmeID));
    }

    @Override
    public void onSubmitFoodPlanUOM(Uom model) {
        uom_id= Integer.parseInt(model.getValue());
        txt_uom_foodplan.setText(model.getText());

    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String lsTimeFrom = formatter.format(cal.getTime());
        tvMealTime_food_plan.setText(lsTimeFrom);
    }



    private void postFoodPlans(int GroupId, int id, int ItemId, String ItemType, double Quantity, String remark, String uomid, int reeworkerPlanID)
    {
        utils.showProgressbar(context);


        UpdatePlanItem request = new UpdatePlanItem();
        request.setGroupId(GroupId);
//            request.setGroupId(12);
        if (isFromEdit){
            if (isChangeItmeID){

                request.setReeworkerplanId(0);
                request.setId(Integer.parseInt(planItems_temp.getId()));
                request.setTodayPlan(planItems_temp.isTodayPlan());
                request.setItemId(itmeID);

            }else {
                request.setId(Integer.parseInt(planItems_temp.getId()));
                request.setReeworkerplanId(reeworkerPlanID);
                request.setTodayPlan(planItems_temp.isTodayPlan());
                request.setItemId(ItemId);


            }


        }else {
            request.setId(0);
            request.setReeworkerplanId(0);

            request.setTodayPlan(planItems_temp.isTodayPlan());
            request.setItemId(itmeID);

        }
        if (planItems_temp.isAlternate()){
            request.setItemType("A");

        }else {
            request.setItemType("I");

        }

        request.setQuantity(Quantity);
        request.setRemark(remark);
        request.setUomId(Integer.parseInt(uomid));

        sessionManager.setStringValue("EditItmeID", String.valueOf(request.getItemId()));

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
//        currentDateandTime = sdf.format(new Date());
        request.setCreatedOn(postDate);

        /*Clearing plan data*/
        dataList.clear();



        Call<ClsEditSleepResonse> call = myPlansService.setFooodPlan(request);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsEditSleepResonse listResponse = response.body();

                    callTodaysPlanApi(planIDTemp);
                    planItems_temp=null;


                    if (listResponse != null && listResponse.getCode().equalsIgnoreCase("1"))
                    {

                        Toast.makeText(MyPlansActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                    else
                    {

                    }
                }
                else
                {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
                utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                Log.d("ERROR--->", t.toString());
            }
        });

        //FOR REST OF THE PLANS

    }
    private void callTodaysPlanNewApi(int planID)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setUserID(userID);

        Call<ClsTodaysPlanNewMainPojo> call = myPlansService.getTodaysPlanNew(userID,planID,postDate);
        call.enqueue(new Callback<ClsTodaysPlanNewMainPojo>()
        {
            @Override
            public void onResponse(Call<ClsTodaysPlanNewMainPojo> call, Response<ClsTodaysPlanNewMainPojo> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsTodaysPlanNewMainPojo listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        sendId= Integer.parseInt(listResponse.getData().getResult().getId());



                        MyPlanNewDataqClass aryltTodaysPlanData = response.body().getData();


                        if (aryltTodaysPlanData.getResult().getTitle()!=null&&!aryltTodaysPlanData.getResult().getTitle().isEmpty()){
                            textView_Category.setText(aryltTodaysPlanData.getResult().getTitle());
                        }else {
                            if (aryltTodaysPlanData.getResult().getTitle()!=null&&!aryltTodaysPlanData.getResult().getTitle().isEmpty()){
                                textView_Category.setText(temp_title);
                            }
                        }



                        ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=null;
                        artylst_ReeworkerPlans=aryltTodaysPlanData.getResult().getReeworkerPlan();
                        if (aryltTodaysPlanData.getResult().getRemark()!=null){
                            txt_main_remark.setVisibility(View.VISIBLE);
                            txt_main_remark.setText("Remark : "+aryltTodaysPlanData.getResult().getRemark());

                        }



                        ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);
                        recyclerView.setAdapter(clsTodaysPlansAdapter);




                    }
                    else
                    {
                        textView_Category.setText(temp_title);

                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=new ArrayList<>();
                        ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);

                        recyclerView.setAdapter(clsTodaysPlansAdapter);
                    }
                }
                else
                {
                    textView_Category.setText(temp_title);

                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClsTodaysPlanNewMainPojo> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.d("ERROR--->", t.toString());
                Toast.makeText(context,"No data found ", Toast.LENGTH_LONG).show();
                textView_Category.setText(temp_title);

                ArrayList<ReeworkerPlan> artylst_ReeworkerPlans=new ArrayList<>();
                ClsTodaysPlansAdapter clsTodaysPlansAdapter=new ClsTodaysPlansAdapter(MyPlansActivity.this,artylst_ReeworkerPlans);

                recyclerView.setAdapter(clsTodaysPlansAdapter);
            }
        });
    }

    @Override
    public void onActivityAdd(String ActivityID, String SubActivtyID, String hours, String ID, String activityclocktime) {

        {
//            {"CreatedOn":"2021-05-08","GroupId":47,"Id":0,"ItemId":2,"ItemType":"A","Quantity":1,"Remark":"Excercise1","UomId":2,
//                    "IsTodayPlan":false,"SubActivityId":18,"ActivationTime":"10:38:31.0000000"}

            UpdatePlanItem request = new UpdatePlanItem();
            request.setCreatedOn(postDate);
            request.setGroupId(planItems_temp.getReeworkerPlanID());
            request.setId(0);
            request.setItemId(Integer.parseInt(ActivityID));
            if (planItems_temp.isAlternate()){
                request.setItemType("A");

            }else {
                request.setItemType("I");

            }
            if (!sessionManager.getStringValue("ActivtyRemark").isEmpty()){
                request.setRemark(sessionManager.getStringValue("ActivtyRemark"));

            }else {
                request.setRemark("");

            }
            request.setUomId(2);
            request.setQuantity(Double.parseDouble(hours));
            request.setTodayPlan(false);
            request.setSubActivityId(Integer.parseInt(SubActivtyID));
            request.setActivationTime(activityclocktime);



            Call<ClsEditSleepResonse> call = myPlansService.setFooodPlan(request);
            call.enqueue(new Callback<ClsEditSleepResonse>()
            {
                @Override
                public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
                {
                    utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsEditSleepResonse listResponse = response.body();

                        callTodaysPlanApi(planIDTemp);
                        planItems_temp=null;


                        if (listResponse != null && listResponse.getCode().equalsIgnoreCase("1"))
                        {

                            Toast.makeText(MyPlansActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            callTodaysPlanApi(planIDTemp);

                        }
                        else
                        {

                        }
                    }
                    else
                    {
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
                {
                    utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                    Log.d("ERROR--->", t.toString());
                }
            });



        }

    }

    @Override
    public void onActivityEdit(String ActivityID, String SubActivtyID, String hours, String ID, String activityclocktime) {


        UpdatePlanItem request = new UpdatePlanItem();
        if (!planItems_temp.getItemId().equalsIgnoreCase(String.valueOf(ActivityID))){
            request.setTodayPlan(true);

        }else {
            request.setTodayPlan(false);

        }

        request.setCreatedOn(postDate);
        request.setGroupId(planItems_temp.getReeworkerPlanID());
        request.setId(Integer.parseInt(planItems_temp.getId()));
        request.setItemId(Integer.parseInt(ActivityID));
        if (planItems_temp.isAlternate()){
            request.setItemType("A");

        }else {
            request.setItemType("I");

        }
        if (!sessionManager.getStringValue("ActivtyRemark").isEmpty()){
            request.setRemark(sessionManager.getStringValue("ActivtyRemark"));

        }else {
            request.setRemark("");

        }
        request.setUomId(2);
        request.setQuantity(Double.parseDouble(hours));
        request.setSubActivityId(Integer.parseInt(SubActivtyID));
        request.setActivationTime(activityclocktime);



        Call<ClsEditSleepResonse> call = myPlansService.setFooodPlan(request);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsEditSleepResonse listResponse = response.body();

                    callTodaysPlanApi(planIDTemp);
                    planItems_temp=null;


                    if (listResponse != null && listResponse.getCode().equalsIgnoreCase("1"))
                    {

                        Toast.makeText(MyPlansActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        callTodaysPlanApi(planIDTemp);

                    }
                    else
                    {

                    }
                }
                else
                {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
                utils.hideProgressbar();

//                    if (swipeRefreshLayout.isRefreshing())
//                        swipeRefreshLayout.setRefreshing(false);
                Log.d("ERROR--->", t.toString());
            }
        });
    }
}
