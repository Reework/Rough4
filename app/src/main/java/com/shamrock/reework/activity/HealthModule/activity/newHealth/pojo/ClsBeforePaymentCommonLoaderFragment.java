package com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.adapter.HealthobjplanlistAdapter;
import com.shamrock.reework.activity.HealthModule.adapter.SubPlanListAdapter;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.payment.ClsCouponMainClass;
import com.shamrock.reework.payment.ClsMerchantRequest;
import com.shamrock.reework.payment.ClsPaymetresponse;
import com.shamrock.reework.payment.MainActivity;
import com.shamrock.reework.payment.MerhantDataResponse;
import com.shamrock.reework.razerpaypsyment.CreatePaymentResonse;
import com.shamrock.reework.razerpaypsyment.PaymentWebviewActivity;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ClsBeforePaymentCommonLoaderFragment extends Fragment {

    String strImahePath;
    private ListView lst_objetive_plan;
    private TextView txt_health_obj_description,txt_health_obj_name;
    private TextView txt_amount_health_obj;
    Button btn_submit_payment;
    PaidSubscriptionData clsHealthobjective;
    ArrayList<PaidSubscriptionData> paidSubscriptionData;
//    RegistrationRequest registrationRequest;
    private Utils utils;
    private SessionManager sessionManager;

    private Context context;
    private RegistrationService regService;
    String from="";
    boolean fromSubplan=false;
    int size;
    int pos;
    UnfreezeService unfreezeService;

    private HealthParametersService healthParametersService;
    private int userID;
    private String token;

    @SuppressLint("ValidFragment")
    public ClsBeforePaymentCommonLoaderFragment(PaidSubscriptionData paidSubscriptionData, ArrayList<PaidSubscriptionData> clsHealthobjectiveArrayList, String strImahePath) {
        this.strImahePath=strImahePath;
    }

    public ClsBeforePaymentCommonLoaderFragment(PaidSubscriptionData clsHealthobjective, ArrayList<PaidSubscriptionData> paidSubscriptionData, String from, int pos, int size) {
        this.clsHealthobjective=clsHealthobjective;
        this.paidSubscriptionData=paidSubscriptionData;
        this.from=from;
        this.size=size;
        this.pos=pos;

//        this.registrationRequest=registrationRequest;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.lay_common_frag,container,false);
        context=getActivity();
        healthParametersService = Client.getClient().create(HealthParametersService.class);

        sessionManager = new SessionManager(context);
        token = sessionManager.getStringValue(SessionManager.KEY_USER_TOKEN);
        unfreezeService = Client.getClient().create(UnfreezeService.class);
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
//        weightService = Client.getClient().create(WeightService.class);
//        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();

        lst_objetive_plan=view.findViewById(R.id.lst_plan_features);
        txt_health_obj_name=view.findViewById(R.id.txt_plan_name);
        txt_health_obj_description=view.findViewById(R.id.txt_plan_description);
        txt_amount_health_obj=view.findViewById(R.id.txt_plan_amount);
        btn_submit_payment=view.findViewById(R.id.btn_submit_payment);
        sessionManager = new SessionManager(context);
//        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);

        if (clsHealthobjective.getSubPlans()!=null&&!clsHealthobjective.getSubPlans().isEmpty()){
            btn_submit_payment.setText("Select SubPlans");
        }else {
            btn_submit_payment.setText("Payment");

        }


        txt_health_obj_name.setText(clsHealthobjective.getPlanName());
        txt_health_obj_description.setText(clsHealthobjective.getDesription());
        txt_amount_health_obj.setText(clsHealthobjective.getCurrency()+" "+Math.round(Double.valueOf(clsHealthobjective.getPrice())));

        lst_objetive_plan.setAdapter(new HealthobjplanlistAdapter(getContext(),clsHealthobjective.getServices()));
        btn_submit_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clsHealthobjective.getSubPlans()!=null&&!clsHealthobjective.getSubPlans().isEmpty()){

                    Dialog     dialog=new Dialog(context,R.style.CustomDialog);
                    dialog.setContentView(R.layout.dailog_lay_subplan);
                    ListView lst_sub_plan_list=dialog.findViewById(R.id.lst_sub_plan_list);

                    SubPlanListAdapter bcaPlanListAdapter=new SubPlanListAdapter(context,clsHealthobjective.getSubPlans(),3,clsHealthobjective.getDesription());

                    lst_sub_plan_list.setAdapter(bcaPlanListAdapter);
                    lst_sub_plan_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            showCloseDailog(position);
                        }
                    });





                    dialog.show();

                }else {
//                    createPayment("");

                    fromSubplan=false;

                    calledlotti(context, String.valueOf(clsHealthobjective.getId()), 0);

                }




            }
        });

        return view;
    }
    private void showCloseDailog(final int position) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);


        builder.setMessage("Are you sure want to select "+clsHealthobjective.getSubPlans().get(position).getSubPlanName()+" Plan")
                .setCancelable(false)
                .setPositiveButton("Make payment", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        callSubscriptionApi(String.valueOf(clsHealthobjective.getSubPlans().get(position).getId()));



                        fromSubplan=true;
                        calledlotti(context, String.valueOf(clsHealthobjective.getId()),position);


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        android.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();
    }

    private void callSubscriptionApi(String SubPlanId) {
        if (SubPlanId==null){
            SubPlanId="0";
        }
        if (SubPlanId.isEmpty()){
            SubPlanId="0";

        }
        final SessionManager sessionManager=new SessionManager(context);
      int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
      utils.showProgressbar(context);

        Call<ClsEditSleepResonse> call = healthParametersService.SaveMembershipPlan(userID,clsHealthobjective.getId(),SubPlanId);
        call.enqueue(new Callback<ClsEditSleepResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
            {
                utils.hideProgressbar();
                ClsEditSleepResonse moodResponse = response.body();

                    if (moodResponse!=null){
                        if (moodResponse.getCode().equals("1")){

                            sessionManager.setStringValue("notallowed","false");

                            showSubscriptionSuccessDailog();

                            Toast.makeText(context, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();


                        }else {

                            Toast.makeText(getContext(), ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }






            }


            @Override
            public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });


    }






    private void createPayment(String SubPlanId, String couponcode) {
        if (SubPlanId==null){
            SubPlanId="";
        }
        if (SubPlanId.isEmpty()){
            SubPlanId="";

        }

        if (couponcode==null){
            couponcode="";
        }
        if (couponcode.isEmpty()){
            couponcode="";
        }
        final SessionManager sessionManager=new SessionManager(context);
        int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils.showProgressbar(context);
        Call<CreatePaymentResonse> call = regService.createPayment(userID,"Online",clsHealthobjective.getId(), SubPlanId,couponcode);
        call.enqueue(new Callback<CreatePaymentResonse>()
        {
            @Override
            public void onResponse(Call<CreatePaymentResonse> call, Response<CreatePaymentResonse> response)
            {
                utils.hideProgressbar();
                CreatePaymentResonse moodResponse = response.body();


                if (moodResponse!=null){
                    if (moodResponse.getCode().equals("1")){

                        if (moodResponse.isPaymentDone()){

                            Toast.makeText(getContext(), ""+moodResponse.getMessage().toString(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();


                        }else {
                            if (moodResponse.getData()!=null){

                                Intent intent=new Intent(getContext(), PaymentWebviewActivity.class);
                                intent.putExtra("ShortUrl",moodResponse.getData().getShort_url());
                                intent.putExtra("from",from);
                                startActivity(intent);
                            }


                        }







                    }


                    }

                }









            @Override
            public void onFailure(Call<CreatePaymentResonse> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });


    }

    private void callForUsrFreezStatus()
    {

        utils.showProgressbar(context);


        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userID);

        Call<UserStatusResponse> call = unfreezeService.getUserStatus(token, request);
        call.enqueue(new Callback<UserStatusResponse>()
        {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    UserStatusResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        try {
                            int status = listResponse.getData().getStatusId();
                            int planid = listResponse.getData().getPlanID();




                            sessionManager.setIntValue(SessionManager.KEY_USER_PLAN_ID, planid);

                            if (sessionManager.getStringValue("Trial").equalsIgnoreCase("true")||sessionManager.getStringValue("Trial").equalsIgnoreCase("expire")){
                                sessionManager.setStringValue("Trial", listResponse.getData().getIsTrail());
                                Intent intent=new Intent(context, DynamicHealthparamActivity.class);
                                intent.putExtra("param","Trial");
                                startActivity(intent);
                                getActivity().finish();

                            }


                            Log.d("Trial",listResponse.getData().getIsTrail());

//
//                            Intent intent=new Intent(context, HomeActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            getActivity().finish();



                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
                else
                {
                    //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    Log.d("Error---->", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("RESULT"))
            {
                if (data.getStringExtra("RESULT").equals("ok"))
                {
                    if (data.getSerializableExtra("TransactionResponse")!=null){
                        ClsPaymetresponse clsPaymetresponse= (ClsPaymetresponse) data.getSerializableExtra("TransactionResponse");
                                if (clsPaymetresponse.getCode()==100){
                                    Toast.makeText(context, ""+clsPaymetresponse.getDescription()+" \n"+"OrderID: "+clsPaymetresponse.getOrderId(), Toast.LENGTH_LONG).show();


                                    callSubscriptionApi("");

                                }else if (clsPaymetresponse.getCode()==183){
                                    Toast.makeText(context, ""+clsPaymetresponse.getDescription(), Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(context, ""+clsPaymetresponse.getDescription(), Toast.LENGTH_LONG).show();

                                }


                    }


//                    calledlotti(context,"Sucesss");



                }
            }
        }


    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Transaction Response");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void calledlotti(Context mContext, final String plainid, final int position) {

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);


        View sheetView = getLayoutInflater().inflate(R.layout.lay_couponcode, null);
        dialog.setContentView(sheetView);




        final EditText edt_couponcode=dialog.findViewById(R.id.edt_couponcode);
        final Button btn_skip=dialog.findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (fromSubplan){
                    createPayment(String.valueOf(clsHealthobjective.getSubPlans().get(position).getId()),"");
                }else {
                    createPayment("","");
                }
            }
        });
        Button btn_submitcouponcode=dialog.findViewById(R.id.btn_submitcouponcode);
        ImageView img_closecoupon=dialog.findViewById(R.id.img_closecoupon);
        img_closecoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_submitcouponcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_couponcode.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter Coupon code", Toast.LENGTH_SHORT).show();
                    return;
                }
                callApplyCouponcodeApi(plainid,edt_couponcode.getText().toString(),position,dialog);
            }
        });

        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) sheetView.getParent());


        behavior.setPeekHeight(5000);
        behavior.setHideable(false);

//        setupFullHeight(dialog);

        dialog.setCancelable(true);



        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog1) {
            }
        });
        dialog.show();
    }
    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    public void showSubscriptionSuccessDailog(){

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.dailog_subscription);
        Button btn_subscription_done=dialog.findViewById(R.id.btn_subscription_done);



        btn_subscription_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
//                From_no_plan

                if (from!=null&&from.equalsIgnoreCase("From_no_plan")){

                    getActivity().finish();
                    return;

                }

                if (from!=null&&from.equalsIgnoreCase("From_Home")){

                    callForUsrFreezStatus();
                }

                else if (from!=null&&from.equalsIgnoreCase("change")){


                    Intent intent=new Intent(context, MyServiceActivity.class);
                    startActivity(intent);
                    getActivity().finish();

//                                callForUsrFrefezStatus();
                }
                else {
                    Intent intent=new Intent(context, DynamicHealthparamActivity.class);
                    intent.putExtra("frompayment",true);
                    startActivity(intent);

                }



//                closeDialog();
            }
        });


        TextView tvTitle=dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("Membership Plan");
        ImageView imageView=dialog.findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }
    private void getMerchantData(final ClsMerchantRequest request) {

        Intent intent=new Intent(context, MainActivity.class);

        startActivityForResult(intent,1001);
        if (true){
            return;
        }

        utils.showProgressbar(context);
        Call<MerhantDataResponse> call = regService.getMerchantData(request);
        call.enqueue(new Callback<MerhantDataResponse>()
        {
            @Override
            public void onResponse(Call<MerhantDataResponse> call, Response<MerhantDataResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MerhantDataResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {


                        if (listResponse.getData()!=null){

                            if (from!=null&&from.equalsIgnoreCase("From_Home")){

                                Intent intent=new Intent(context, MainActivity.class);
                                intent.putExtra("amount",listResponse.getData().getAmount());
                                intent.putExtra("OrderID",listResponse.getData().getOrderId());
                                intent.putExtra("SecretKey",listResponse.getData().getOrderId());
                                intent.putExtra("MerchantId",listResponse.getData().getMerchantId());
                                intent.putExtra("Email",listResponse.getData().getEmail());
                                intent.putExtra("mobile",listResponse.getData().getMobileNo());
                                intent.putExtra("MerchantName",listResponse.getData().getMerchantName());
                                startActivityForResult(intent,1001);


//                    callSubscriptionApi();

                            }else {

                                Intent intent=new Intent(context, MainActivity.class);
                                intent.putExtra("amount",listResponse.getData().getAmount());
                                intent.putExtra("OrderID",listResponse.getData().getOrderId());
                                intent.putExtra("SecretKey",listResponse.getData().getOrderId());
                                intent.putExtra("MerchantId",listResponse.getData().getMerchantId());
                                intent.putExtra("Email",listResponse.getData().getEmail());
                                intent.putExtra("mobile",listResponse.getData().getMobileNo());
                                intent.putExtra("MerchantName",listResponse.getData().getMerchantName());

                                startActivityForResult(intent,1001);

//                    callSubscriptionApi();


                            }
                        }



                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                }
            }

            @Override
            public void onFailure(Call<MerhantDataResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }


        });
    }
    private void callApplyCouponcodeApi(String plainid, final String couponcode, final int position, final BottomSheetDialog dialogss) {

        final SessionManager sessionManager=new SessionManager(context);
        int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils.showProgressbar(context);

        plainid="2";

        Call<ClsCouponMainClass> call = healthParametersService.getCouponDiscountApi(plainid,couponcode);
        call.enqueue(new Callback<ClsCouponMainClass>()
        {
            @Override
            public void onResponse(Call<ClsCouponMainClass> call, Response<ClsCouponMainClass> response)
            {
                utils.hideProgressbar();
                ClsCouponMainClass moodResponse = response.body();

                if (moodResponse!=null){
                    if (moodResponse.getCode().equals("1")){
                        dialogss.dismiss();
                        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        dialog.setContentView(R.layout.dialog_succescoupon);
                        TextView txt_coupon_successmsg=dialog.findViewById(R.id.txt_coupon_successmsg);
                        txt_coupon_successmsg.setText("Congratulations!! You have successfully applied the coupon code.You have discount Rs."+moodResponse.getData().getDiscount());
                        Button btn_proceedpayment=dialog.findViewById(R.id.btn_proceedpayment);
                        btn_proceedpayment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                if (fromSubplan){
                                    createPayment(String.valueOf(clsHealthobjective.getSubPlans().get(position).getId()),couponcode);

                                }else {
                                    createPayment("",couponcode);

                                }
                            }
                        });


                        dialog.show();



//                        Toast.makeText(context, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();


                    }else {

                        Toast.makeText(getContext(), ""+moodResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }






            }


            @Override
            public void onFailure(Call<ClsCouponMainClass> call, Throwable t)
            {
                utils.hideProgressbar();

            }
        });


    }

}

