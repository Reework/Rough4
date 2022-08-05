package com.shamrock.reework.activity.dietplan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.activity.FoodModule.model.AddMealRequest;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.dietplan.adapters.OnEditFoodPlan;
import com.shamrock.reework.activity.dietplan.adapters.RdpStatusAdapter;
import com.shamrock.reework.activity.dietplan.adapters.TestAdapter;
import com.shamrock.reework.activity.dietplan.pojo.ClsCustomRDpStatus;
import com.shamrock.reework.activity.dietplan.pojo.FoodPlan;
import com.shamrock.reework.activity.dietplan.pojo.RDPFoodPlanMain;
import com.shamrock.reework.activity.dietplan.pojo.RDPRequest;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.GetMealType;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRDPFoodPlanActivity extends AppCompatActivity implements OnEditFoodPlan {



   TextView txt_reecoach_rdp_name;

   TextView txt_to_date,txt_from_date;
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
    RDPFoodPlanMain  rdpFoodPlanMain_obj;
    ArrayList<ClsCustomRDpStatus> arylst_ClsCustomRDpStatuses;
    ImageView img_add_rdp_plan;
    ImageView img_rdp_plan_edit;
    ImageView img_rdp_pdf;
    private String pdflink="";



    public String formatDatesSleep(String dateFromServer)
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_d_p_food_plan);
        mContext= NewRDPFoodPlanActivity.this;

        txt_reecoach_rdp_name=findViewById(R.id.txt_reecoach_rdp_name);
        txt_reecoach_rdp_modfydate=findViewById(R.id.txt_reecoach_rdp_modfydate);
        txt_from_date=findViewById(R.id.txt_from_date);
        txt_to_date=findViewById(R.id.txt_to_date);
        img_add_rdp_plan=findViewById(R.id.img_add_rdp_plan);
        img_rdp_plan_edit=findViewById(R.id.img_rdp_plan_edit);
        ll_date_rdp=findViewById(R.id.ll_date_rdp);
        img_rdp_pdf=findViewById(R.id.img_rdp_pdf);
        img_rdp_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pdflink.isEmpty()){
                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    intent.putExtra("pdfLink", pdflink);
                    intent.putExtra("postdate", "");
                    intent.putExtra("name","");
                    startActivity(intent);
                }


            }
        });



        img_rdp_plan_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogrdp(false);

            }
        });

        img_add_rdp_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogrdp(true);
            }
        });

        ImageView imgLeft=findViewById(R.id.imgLeft);
        sessionManager = new SessionManager(mContext);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        foodService = Client.getClient().create(FoodService.class);
        utils=new Utils();
        if (sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID)!=0){
            getRDPFOODPlanReport();

        }
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab_upload_rdp=findViewById(R.id.fab_upload_rdp);
        fab_upload_rdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sessionManager.getStringValue("RDP_ID").equalsIgnoreCase("0")){
                    Toast.makeText(mContext, "Please Add RDP Plan", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivityForResult(new Intent(NewRDPFoodPlanActivity.this,AddRDPMealActivity.class),311);


            }
        });
        rd_button_veg=findViewById(R.id.rd_button_veg);
        rd_button_nonveg=findViewById(R.id.rd_button_nonveg);
        rd_button_nonveg.performClick();
        TextView tvTitle=findViewById(R.id.tvTitle);


        tvTitle.setText("My Diet plan");
        rd_button_veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewRDPFoodPlanActivity.this,DietPlanActivity.class));
                overridePendingTransition(0,0);
                finish();
            }
        });








    }

    private void showDialogrdp(final boolean b) {

        final Dialog dialog = new Dialog(NewRDPFoodPlanActivity.this, R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_edit_rdp);
        final EditText edt_ideal_energy=dialog.findViewById(R.id.edt_ideal_energy);
        final EditText edt_ideal_protin=dialog.findViewById(R.id.edt_ideal_protin);
        final EditText edt_ideal_carb=dialog.findViewById(R.id.edt_ideal_carb);
        final EditText edt_ideal_fat=dialog.findViewById(R.id.edt_ideal_fat);
        final EditText edt_ideal_fibre=dialog.findViewById(R.id.edt_ideal_fibre);
        Button btn_submit_rdp=dialog.findViewById(R.id.btn_submit_rdp);
        ImageView close_rdp=dialog.findViewById(R.id.close_rdp);
        close_rdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView labelMedicineTitle=dialog.findViewById(R.id.labelMedicineTitle);
        if (b){
            labelMedicineTitle.setText("Add RDP Plan");
        }else {
            labelMedicineTitle.setText("Edit RDP Plan");
        }
        btn_submit_rdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                RDPRequest  rdpRequest=new RDPRequest();

                if (b){
                    rdpRequest.setId(0);
                    rdpRequest.setFromDate("");
                    rdpRequest.setToDate("");
                }else {
                    rdpRequest.setId(rdpFoodPlanMain_obj.getData().getRdpId());
                    rdpRequest.setFromDate(rdpFoodPlanMain_obj.getData().getFromDate());
                    rdpRequest.setToDate(rdpFoodPlanMain_obj.getData().getToDate());
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

        if (b){

        }else {
            if (rdpFoodPlanMain_obj.getData()!=null){

                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCalories()).equalsIgnoreCase("null")){
                    edt_ideal_energy.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCalories()));

                }else {
                    edt_ideal_energy.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealProtein()).equalsIgnoreCase("null")){
                    edt_ideal_protin.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealProtein()));

                }else {
                    edt_ideal_protin.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCarb()).equalsIgnoreCase("null")){
                    edt_ideal_carb.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealCarb()));

                }else {
                    edt_ideal_carb.setText(String.valueOf("0.0"));

                }


                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFat()).equalsIgnoreCase("null")){
                    edt_ideal_fat.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFat()));

                }else {
                    edt_ideal_fat.setText(String.valueOf("0.0"));

                }



                if (!String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFibre()).equalsIgnoreCase("null")){
                    edt_ideal_fibre.setText(String.valueOf(rdpFoodPlanMain_obj.getData().getIdealFibre()));

                }else {
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
        call.enqueue(new Callback<RDPFoodPlanMain>()
        {
            @Override
            public void onResponse(Call<RDPFoodPlanMain> call, Response<RDPFoodPlanMain> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {

                    rdpFoodPlanMain_obj = response.body();
                    if (rdpFoodPlanMain_obj != null && rdpFoodPlanMain_obj.getCode().equals("1")){



                        sessionManager.setStringValue("RDP_ID", String.valueOf(rdpFoodPlanMain_obj.getData().getRdpId()));



                        txt_reecoach_rdp_name.setText("Reecoach name : "+rdpFoodPlanMain_obj.getData().getReecoachName());

                        if (rdpFoodPlanMain_obj.getData().getLastModified()!=null){
                            if (!rdpFoodPlanMain_obj.getData().getLastModified().isEmpty()){
                                txt_reecoach_rdp_modfydate.setText("Last Modified Date : "+formatDates(rdpFoodPlanMain_obj.getData().getLastModified()));
                            }
                        }

                        if (rdpFoodPlanMain_obj.getData().getFromDate()!=null&&!rdpFoodPlanMain_obj.getData().getFromDate().isEmpty()){
                            txt_from_date.setText(formatDates(rdpFoodPlanMain_obj.getData().getFromDate()));
                            txt_to_date.setText(formatDates(rdpFoodPlanMain_obj.getData().getToDate()));
                        }


                        arylst_ClsCustomRDpStatuses=new ArrayList<>();

                        arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                "Energy",String.valueOf(rdpFoodPlanMain_obj.getData().getExistingCalories()),
                               String.valueOf( rdpFoodPlanMain_obj.getData().getIdealCalories()),
                                String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningCalories())));

                        arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                "Protein",String.valueOf(rdpFoodPlanMain_obj.getData().getExistingProtein()),
                                String.valueOf( rdpFoodPlanMain_obj.getData().getIdealProtein()),
                                String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningProtein())));


                        arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                "Carb",String.valueOf(rdpFoodPlanMain_obj.getData().getExistingCarb()),
                                String.valueOf( rdpFoodPlanMain_obj.getData().getIdealCarb()),
                                String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningCarb())));


                        arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                "Fat",String.valueOf(rdpFoodPlanMain_obj.getData().getExistingFat()),
                                String.valueOf( rdpFoodPlanMain_obj.getData().getIdealFat()),
                                String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningFat())));


                        arylst_ClsCustomRDpStatuses.add(new ClsCustomRDpStatus(
                                "Fibre",String.valueOf(rdpFoodPlanMain_obj.getData().getExistingFibre()),
                                String.valueOf( rdpFoodPlanMain_obj.getData().getIdealFibre()),
                                String.valueOf(rdpFoodPlanMain_obj.getData().getPlanningFibre())));


                        RecyclerView recyler_rdp_status=findViewById(R.id.recyler_rdp_status);
                        recyler_rdp_status.setAdapter(new RdpStatusAdapter(NewRDPFoodPlanActivity.this,arylst_ClsCustomRDpStatuses));



                        RecyclerView recyler_food_plan=findViewById(R.id.recyler_food_plan);
                        TextView no_food_plan=findViewById(R.id.no_food_plan);
                        pdflink=rdpFoodPlanMain_obj.getData().getReportLink();

                        if (!rdpFoodPlanMain_obj.getData().getFoodPlan().isEmpty()){
                            recyler_food_plan.setVisibility(View.VISIBLE);
                            no_food_plan.setVisibility(View.GONE);
                            recyler_food_plan.setAdapter(new TestAdapter(NewRDPFoodPlanActivity.this,rdpFoodPlanMain_obj.getData().getFoodPlan()));

                        }else {
                            recyler_food_plan.setVisibility(View.GONE);
                            no_food_plan.setVisibility(View.VISIBLE);

                        }


                    }
                    else
                    {
                        Toast.makeText(mContext, rdpFoodPlanMain_obj.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPFoodPlanMain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }



    private void addUpdateRDP(RDPRequest rdpRequest) {
        utils.showProgressbar(this);
        Call<RDPSuccess> call = foodService.saveRDP(rdpRequest);
        call.enqueue(new Callback<RDPSuccess>()
        {
            @Override
            public void onResponse(Call<RDPSuccess> call, Response<RDPSuccess> response)
            {
                utils.hideProgressbar();
                RDPSuccess rdpSuccess;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {

                    if (response.body().getCode() == 1){


                        if (response.body().getData()!=null){
                            getRDPFOODPlanReport();
                            Toast.makeText(mContext, response.body().getData(), Toast.LENGTH_SHORT).show();

                        }

                    }
                    else
                    {

                        Toast.makeText(mContext, response.body().getData(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RDPSuccess> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }





    public String formatDates(String dateFromServers)
    {

        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00


        int index = dateFromServers.indexOf("T");


        String dateFromServer=dateFromServers.substring(0,index);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (requestCode==311){
                if (data!=null){
                    if (data.getStringExtra("result")!=null){
                        String result=data.getStringExtra("result");
                        if (result.equalsIgnoreCase("done")){
                            getRDPFOODPlanReport();

                        }
                    }
                }


            }
        }
    }

    @Override
    public void getEditFoodPlanData(FoodPlan foodPlan) {

        Intent intent=new Intent(NewRDPFoodPlanActivity.this,EditRDPMealActivity.class);
        intent.putExtra("Foodplan",foodPlan);

        startActivityForResult(intent,311);


    }

    @Override
    public void deleteFoodPlanData(int id) {

    }

    @Override
    public void addToDailyDairy(FoodPlan foodPlan) {


    }


}
