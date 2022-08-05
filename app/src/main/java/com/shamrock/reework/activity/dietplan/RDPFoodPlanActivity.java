package com.shamrock.reework.activity.dietplan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.activity.FoodModule.activity.MasterDetailsActivity;
import com.shamrock.reework.activity.FoodModule.adapter.OnHealthCatoryClick;
import com.shamrock.reework.activity.FoodModule.model.AddMealRequest;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.dietplan.adapters.OnEditFoodPlan;
import com.shamrock.reework.activity.dietplan.adapters.PathoPlanAdapter;
import com.shamrock.reework.activity.dietplan.adapters.RdpStatusAdapter;
import com.shamrock.reework.activity.dietplan.adapters.TestAdapter;
import com.shamrock.reework.activity.dietplan.pojo.ClsCustomRDpStatus;
import com.shamrock.reework.activity.dietplan.pojo.ClsPathoReportMain;
import com.shamrock.reework.activity.dietplan.pojo.FoodPlan;
import com.shamrock.reework.activity.dietplan.pojo.RDPFoodPlanMain;
import com.shamrock.reework.activity.dietplan.pojo.RDPRequest;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.response.GetMealType;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

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

public class RDPFoodPlanActivity extends AppCompatActivity implements  View.OnClickListener,OnEditFoodPlan, DatePickerDialog.OnDateSetListener, SearchView.OnQueryTextListener,SearchView.OnCloseListener, OnHealthCatoryClick {


    TextView txt_reecoach_rdp_name;

    TextView txt_to_date, txt_from_date;
    TextView txt_reecoach_rdp_modfydate;
    LinearLayout ll_date_rdp;


    RadioButton rd_button_veg;
    RadioButton rd_button_nonveg;
    FloatingActionButton fab_upload_rdp;
    Utils utils;
    private SessionManager sessionManager;
    Context mContext;
    private int userId;
    private FoodService foodService;
    RDPFoodPlanMain rdpFoodPlanMain_obj;
    ArrayList<ClsCustomRDpStatus> arylst_ClsCustomRDpStatuses;
    ImageView img_add_rdp_plan;
    ImageView img_rdp_plan_edit;


    //old
    private TextView txt_show_sleep_to, txt_show_sleep_from;
    private ImageView img_add_sleep_date;
    private TextView txt_sleep_date_from_dialog;
    private TextView txt_sleep_date_to_dialog;

    private String submitFromDate;
    private String submitToDate;
    private String dummydate_from;

    TextView txt_sleep_date_from, txt_sleep_date_to;
    private String dummydate_to;
    private String StrDateOpen;
    private DatePickerDialog datepickerdialog;
    private CommonService commonService;
    SearchView search_rdp;

    TestAdapter testAdapter;
    boolean isAddDailyDairy=false;
    private String pdflink="";
    ImageView img_rdp_pdf;

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
        return " ";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_d_p_food_plan);
        mContext = RDPFoodPlanActivity.this;

        txt_reecoach_rdp_name = findViewById(R.id.txt_reecoach_rdp_name);
        txt_reecoach_rdp_modfydate = findViewById(R.id.txt_reecoach_rdp_modfydate);
        txt_from_date = findViewById(R.id.txt_from_date);
        txt_to_date = findViewById(R.id.txt_to_date);
        img_add_rdp_plan = findViewById(R.id.img_add_rdp_plan);
        img_rdp_plan_edit = findViewById(R.id.img_rdp_plan_edit);
        ll_date_rdp = findViewById(R.id.ll_date_rdp);
        search_rdp = findViewById(R.id.search_rdp);
        search_rdp.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        search_rdp.setOnSearchClickListener(this);
        search_rdp.setOnCloseListener((SearchView.OnCloseListener) this);
        img_rdp_pdf=findViewById(R.id.img_rdp_pdf);
        img_rdp_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }


                if (pdflink!=null&&!pdflink.isEmpty()){
                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    intent.putExtra("pdfLink", pdflink);
                    intent.putExtra("postdate", "");
                    intent.putExtra("name","");
                    startActivity(intent);
                }


            }
        });

        showdate();


        img_rdp_plan_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }


                showDialogrdp(false);

            }
        });

        img_add_rdp_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }
                showDialogrdp(true);
            }
        });

        ImageView imgLeft = findViewById(R.id.imgLeft);
        sessionManager = new SessionManager(mContext);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
        utils = new Utils();
        if (sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID) != 0) {
            getRDPFOODPlanReport();

        }
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAddDailyDairy){
                    finish();


                }else {
                    Intent intent = new Intent(RDPFoodPlanActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
            }
        });
        fab_upload_rdp = findViewById(R.id.fab_upload_rdp);
        fab_upload_rdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }


                if (sessionManager.getStringValue("RDP_ID").equalsIgnoreCase("0")) {
                    Toast.makeText(mContext, "Please Add RDP Plan", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivityForResult(new Intent(RDPFoodPlanActivity.this, AddRDPMealActivity.class), 311);


            }
        });
        rd_button_veg = findViewById(R.id.rd_button_veg);
        rd_button_nonveg = findViewById(R.id.rd_button_nonveg);
        rd_button_nonveg.performClick();
        TextView tvTitle = findViewById(R.id.tvTitle);


        final boolean ISReeplan=getIntent().getBooleanExtra("ISReeplan",false);


        if (ISReeplan){
            tvTitle.setText("REEplan");

        }else {
            tvTitle.setText("My Diet Plan");

        }

        rd_button_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(RDPFoodPlanActivity.this, DietPlanActivity.class);
                intent.putExtra("ISReeplan",ISReeplan);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


    }

    private void shownoplan() {

        final Dialog dialog=new Dialog(RDPFoodPlanActivity.this,R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe=dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no=dialog.findViewById(R.id.btn_subscribe_no);
        btn_subscribe_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent  intent = new Intent(RDPFoodPlanActivity.this, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }


    private void showdate() {

        txt_show_sleep_to = findViewById(R.id.txt_to_date);
        txt_show_sleep_from = findViewById(R.id.txt_from_date);
        submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dummydate_from = formatDatesSleep(submitFromDate);
        txt_show_sleep_from.setText(dummydate_from);
        dummydate_to = formatDatesSleep(submitToDate);
        txt_show_sleep_to.setText(dummydate_to);

        LinearLayout ll_sleep_date = findViewById(R.id.ll_date_rdp);
        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelectionDialog();
            }
        });
    }

    private void showDialogrdp(final boolean b) {

        final Dialog dialog = new Dialog(RDPFoodPlanActivity.this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_edit_rdp);
        final EditText edt_ideal_energy = dialog.findViewById(R.id.edt_ideal_energy);
        final EditText edt_ideal_protin = dialog.findViewById(R.id.edt_ideal_protin);
        final EditText edt_ideal_carb = dialog.findViewById(R.id.edt_ideal_carb);
        final EditText edt_ideal_fat = dialog.findViewById(R.id.edt_ideal_fat);
        final EditText edt_ideal_fibre = dialog.findViewById(R.id.edt_ideal_fibre);
        Button btn_submit_rdp = dialog.findViewById(R.id.btn_submit_rdp);
        ImageView close_rdp = dialog.findViewById(R.id.close_rdp);
        close_rdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView labelMedicineTitle = dialog.findViewById(R.id.labelMedicineTitle);
        if (b) {
            labelMedicineTitle.setText("Add RDP Plan");
        } else {
            labelMedicineTitle.setText("Edit RDP Plan");
        }
        btn_submit_rdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edt_ideal_energy.getText().toString().trim().isEmpty()){
                    Toast.makeText(mContext, "Enter Ideal Energy", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(edt_ideal_protin.getText().toString().trim().isEmpty()){
                    Toast.makeText(mContext, "Enter Ideal Protin", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(edt_ideal_carb.getText().toString().trim().isEmpty()){
                    Toast.makeText(mContext, "Enter Ideal Carb", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(edt_ideal_fat.getText().toString().trim().isEmpty()){
                    Toast.makeText(mContext, "Enter Ideal Fat", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(edt_ideal_fibre.getText().toString().trim().isEmpty()){
                    Toast.makeText(mContext, "Enter Ideal Fibre", Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog.dismiss();
                RDPRequest rdpRequest = new RDPRequest();

                if (b) {
                    rdpRequest.setId(0);
                    rdpRequest.setFromDate(submitFromDate);
                    rdpRequest.setToDate(submitToDate);
                } else {
                    rdpRequest.setId(rdpFoodPlanMain_obj.getData().getRdpId());
                    rdpRequest.setFromDate(submitFromDate);
                    rdpRequest.setToDate(submitToDate);
                }





                rdpRequest.setIdealCalories(Double.parseDouble(edt_ideal_energy.getText().toString()));
                rdpRequest.setIdealCarb(Double.parseDouble(edt_ideal_carb.getText().toString()));
                rdpRequest.setIdealFat(Double.parseDouble(edt_ideal_fat.getText().toString()));
                rdpRequest.setIdealFibre(Double.parseDouble(edt_ideal_fibre.getText().toString()));
                rdpRequest.setIdealProtein(Double.parseDouble(edt_ideal_protin.getText().toString()));
                rdpRequest.setReeworkerId(userId);
                addUpdateRDP(rdpRequest);

            }
        });

        if (b) {


        } else {
            if (rdpFoodPlanMain_obj.getData() != null) {

                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCalories()).equalsIgnoreCase("null")) {
                    edt_ideal_energy.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCalories()));

                } else {
                    edt_ideal_energy.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealProtein()).equalsIgnoreCase("null")) {
                    edt_ideal_protin.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealProtein()));

                } else {
                    edt_ideal_protin.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCarb()).equalsIgnoreCase("null")) {
                    edt_ideal_carb.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCarb()));

                } else {
                    edt_ideal_carb.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFat()).equalsIgnoreCase("null")) {
                    edt_ideal_fat.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFat()));

                } else {
                    edt_ideal_fat.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFibre()).equalsIgnoreCase("null")) {
                    edt_ideal_fibre.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFibre()));

                } else {
                    edt_ideal_fibre.setText(String.valueOf("0.0"));

                }


            }

        }
        dialog.show();
    }

    private void showAddRdpDialog() {

        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_add_rdp);
        dialog.show();
    }

    private void getRDPFOODPlanReport() {
        utils.showProgressbar(this);
        Call<RDPFoodPlanMain> call = foodService.getGetRDPReport(userId);
        call.enqueue(new Callback<RDPFoodPlanMain>() {
            @Override
            public void onResponse(Call<RDPFoodPlanMain> call, Response<RDPFoodPlanMain> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    rdpFoodPlanMain_obj = response.body();
                    if (rdpFoodPlanMain_obj != null && rdpFoodPlanMain_obj.getCode().equals("1")) {
                        try {


                            sessionManager.setStringValue("RDP_ID", String.valueOf(rdpFoodPlanMain_obj.getData().getRdpId()));


                            txt_reecoach_rdp_name.setText("Reecoach name : " + rdpFoodPlanMain_obj.getData().getReecoachName());

                            if (rdpFoodPlanMain_obj.getData().getLastModified() != null) {
                                if (!rdpFoodPlanMain_obj.getData().getLastModified().isEmpty()) {
                                    txt_reecoach_rdp_modfydate.setText("Last Modified Date : " + formatDates(rdpFoodPlanMain_obj.getData().getLastModified()));
                                }
                            }

                            if (rdpFoodPlanMain_obj.getData().getFromDate() != null && !rdpFoodPlanMain_obj.getData().getFromDate().isEmpty()) {

                                try {
                                    submitFromDate = serverformatDates(rdpFoodPlanMain_obj.getData().getFromDate());
                                    submitToDate = serverformatDates(rdpFoodPlanMain_obj.getData().getToDate());
                                    txt_from_date.setText(formatDates(rdpFoodPlanMain_obj.getData().getFromDate()));
                                    txt_to_date.setText(formatDates(rdpFoodPlanMain_obj.getData().getToDate()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }


                            arylst_ClsCustomRDpStatuses = new ArrayList<>();

                            arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                    "Energy(kcal)", String.valueOf(rdpFoodPlanMain_obj.getData().getExistingCalories()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCalories()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningCalories())));

                            arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                    "Protein(g/day)", String.valueOf(rdpFoodPlanMain_obj.getData().getExistingProtein()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getIdealProtein()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningProtein())));


                            arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                    "Carb(g/day)", String.valueOf(rdpFoodPlanMain_obj.getData().getExistingCarb()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCarb()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningCarb())));


                            arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                    "Fat(g/day)", String.valueOf(rdpFoodPlanMain_obj.getData().getExistingFat()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFat()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningFat())));


                            arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                    "Fibre(g/day)", String.valueOf(rdpFoodPlanMain_obj.getData().getExistingFibre()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFibre()),
                                    String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningFibre())));


                            RecyclerView recyler_rdp_status = findViewById(R.id.recyler_rdp_status);
                            recyler_rdp_status.setAdapter(new RdpStatusAdapter(RDPFoodPlanActivity.this, arylst_ClsCustomRDpStatuses));
                            pdflink=rdpFoodPlanMain_obj.getData().getReportLink();


                            RecyclerView recyler_food_plan = findViewById(R.id.recyler_food_plan);
                            TextView no_food_plan = findViewById(R.id.no_food_plan);

                            if (!rdpFoodPlanMain_obj.getData().getFoodPlan().isEmpty()) {
                                no_food_plan.setVisibility(View.GONE);
                                search_rdp.setVisibility(View.VISIBLE);

                                recyler_food_plan.setVisibility(View.VISIBLE);
                                testAdapter=new TestAdapter(RDPFoodPlanActivity.this, rdpFoodPlanMain_obj.getData().getFoodPlan());
                                recyler_food_plan.setAdapter(testAdapter);

                            } else {
                                recyler_food_plan.setVisibility(View.GONE);
                                no_food_plan.setVisibility(View.VISIBLE);
                                search_rdp.setVisibility(View.GONE);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        Toast.makeText(mContext, rdpFoodPlanMain_obj.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPFoodPlanMain> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void addUpdateRDP(RDPRequest rdpRequest) {
        utils.showProgressbar(this);
        Call<RDPSuccess> call = foodService.saveRDP(rdpRequest);
        call.enqueue(new Callback<RDPSuccess>() {
            @Override
            public void onResponse(Call<RDPSuccess> call, Response<RDPSuccess> response) {
                utils.hideProgressbar();
                RDPSuccess rdpSuccess;
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    if (response.body().getCode() == 1) {


                        if (response.body().getData() != null) {
                            getRDPFOODPlanReport();
                            Toast.makeText(mContext, response.body().getData(), Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(mContext, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPSuccess> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void deleteFoodPlan(int id) {
        utils.showProgressbar(this);
        Call<RDPSuccess> call = foodService.getDeleteFoodPlan(id);
        call.enqueue(new Callback<RDPSuccess>() {
            @Override
            public void onResponse(Call<RDPSuccess> call, Response<RDPSuccess> response) {
                utils.hideProgressbar();
                RDPSuccess rdpSuccess;
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    if (response.body().getCode() == 1) {


                        if (response.body().getData() != null) {
                            getRDPFOODPlanReport();
                            Toast.makeText(mContext, response.body().getData(), Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(mContext, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPSuccess> call, Throwable t) {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }


    public String formatDates(String dateFromServers) {

        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        String dateFromServer="";

        try{
        int index = dateFromServers.indexOf("T");



         dateFromServer = dateFromServers.substring(0, index);
        }catch (Exception e){
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return " ";
    }


    public String serverformatDates(String dateFromServers) {
        //String strDate = "2013-05-15T10:00:00-0700";
        String dateFromServer = "";
        try {


            int index = dateFromServers.indexOf("T");
            dateFromServer = dateFromServers.substring(0, index);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return dateFromServer;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 311) {
                if (data != null) {
                    if (data.getStringExtra("result") != null) {
                        String result = data.getStringExtra("result");
                        if (result.equalsIgnoreCase("done")) {
                            getRDPFOODPlanReport();

                        }
                    }
                }


            }
        }
    }

    @Override
    public void getEditFoodPlanData(FoodPlan foodPlan) {

        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }

        Intent intent = new Intent(RDPFoodPlanActivity.this, EditRDPMealActivity.class);
        intent.putExtra("Foodplan", foodPlan);

        startActivityForResult(intent, 311);


    }

    @Override
    public void deleteFoodPlanData(int id) {
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }
        deleteFoodPlan(id);

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


    @Override
    public void addToDailyDairy(FoodPlan foodPlan) {
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }

        saveTodysMeal(foodPlan.getMealType(),foodPlan.getMealTime(),getCurrentDate(),foodPlan.getFinishedProductId(),foodPlan.getQuantity(),foodPlan.getUomId(),foodPlan.getValueInGram(),foodPlan.getMealType());
    }

    private void showDateSelectionDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog = dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog = dialog.findViewById(R.id.txt_sleep_date_to_dialog);
        TextView txt_dialog_header=dialog.findViewById(R.id.txt_dialog_header);
        txt_dialog_header.setText("Add Date");


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);
        btn_submit_sleep_hours.setText("Add");

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Date added successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });


//        txt_show_sleep_from.setText(dummydate_from);
        txt_sleep_date_from_dialog.setText(dummydate_from);
//        txt_show_sleep_to.setText(dummydate_to);
        txt_sleep_date_to_dialog.setText(dummydate_to);

        showDatePickerDailog();

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
        String strMindate[] = new SessionManager(this).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datepickerdialog = new DatePickerDialog(RDPFoodPlanActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3);


//        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        if (strMindate.length > 1) {
            c1.set(Integer.parseInt(strMindate[2]), Integer.parseInt(strMindate[1]) - 1, Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day

        }
        datepickerdialog.getDatePicker().setMinDate(c1.getTimeInMillis());


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

    public String getFromattedStringDate(int s){


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }



    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (StrDateOpen.equalsIgnoreCase("from")) {

            if (month < 10) {
                dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1) + "-" + year;

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
                Toast.makeText(this, "From date should be greater than End date", Toast.LENGTH_SHORT).show();
                return;
            }
            txt_show_sleep_from.setText(dummydate_from);
            txt_sleep_date_from_dialog.setText(dummydate_from);
            submitFromDate = year + "-" + (month + 1) + "-" + dayOfMonth;


        }


        if (StrDateOpen.equalsIgnoreCase("to")) {


            if (!dummydate_from.trim().isEmpty()) {


                if (month < 10) {
                    dummydate_to = dayOfMonth + "-" + "0" + (month + 1) + "-" + year;

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
                    Toast.makeText(this, "End date should be greater than Start date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    txt_show_sleep_to.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                }
            } else {
                Toast.makeText(this, "Please select Start date", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (testAdapter!=null){
            try {
                testAdapter.getFilter().filter(query);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (testAdapter!=null){
            try {
                testAdapter.getFilter().filter(newText);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return false;
    }

    @Override
    public void getHathCatogoryPosName(String name, boolean b) {

    }

    @Override
    public void onClick(View v) {

    }

    private void saveTodysMeal(String strMealType,String time,String date,int recipeID,double qty,int uomID,double valueinGram,String mealType) {
        final Utils utils=new Utils();
        utils.showProgressbar(mContext);

        ArrayList<AddMealRequest.LstSubMealDatum> itemDataList = new ArrayList<>();
        AddMealRequest request = new AddMealRequest();
        request.setUserId(userId);
        request.setMealType(strMealType);
        request.setIntakeTime(time);
        request.setCreatedOn(date);
        AddMealRequest.LstSubMealDatum  ItemData  = new AddMealRequest.LstSubMealDatum();
        ItemData.setRecipeId(recipeID);

        String getdata="";
        if (sessionManager.getStringValue("matchrecords")!=null){
             getdata=sessionManager.getStringValue("matchrecords");

        }else {
             getdata="";

        }
        String ddd=date+","+getdata+","+recipeID;
        sessionManager.setStringValue("matchrecords",ddd.replace(",,",","));
        ItemData.setMealQty(String.valueOf(qty));
        ItemData.setIntakeTime(time);
        ItemData.setUomId(uomID);
        ItemData.setValueInGram(valueinGram);
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
                        isAddDailyDairy=true;
                        Toast.makeText(mContext, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getRDPFOODPlanReport();
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



}
