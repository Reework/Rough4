package com.shamrock.reework.activity.HealthModule.activity.newHealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionResponse;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.NewHealthparameterGoobActivity;
import com.shamrock.reework.activity.HealthModule.adapter.HealthobjplanlistAdapter;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.Reecoachpathoselection.SelectReecoachPathActivity;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewRegisterActivity;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.RegistrationRespo;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.joinNowActivity;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.RegistrationRequest;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.payment.ClsMerchantRequest;
import com.shamrock.reework.payment.ClsPaymetresponse;
import com.shamrock.reework.payment.MainActivity;
import com.shamrock.reework.payment.MerhantDataResponse;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.shamrock.reework.util.Utils.isValidContextForGlide;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ClsCommonLoaderFragment extends Fragment {

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
    int size;
    int pos;
    UnfreezeService unfreezeService;

    private HealthParametersService healthParametersService;
    private int userID;
    private String token;

    @SuppressLint("ValidFragment")
    public ClsCommonLoaderFragment(PaidSubscriptionData paidSubscriptionData, ArrayList<PaidSubscriptionData> clsHealthobjectiveArrayList, String strImahePath) {
        this.strImahePath=strImahePath;
    }

    public ClsCommonLoaderFragment(PaidSubscriptionData clsHealthobjective, ArrayList<PaidSubscriptionData> paidSubscriptionData,String from, int pos, int size) {
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

        txt_health_obj_name.setText(clsHealthobjective.getPlanName());
        txt_health_obj_description.setText(clsHealthobjective.getDesription());
        txt_amount_health_obj.setText(clsHealthobjective.getCurrency()+" "+Math.round(Double.valueOf(clsHealthobjective.getPrice())));

        lst_objetive_plan.setAdapter(new HealthobjplanlistAdapter(getContext(),clsHealthobjective.getServices()));
        btn_submit_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                calledlotti(context,"");
//                if (true)
//                    return;

             int   userId = new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID);

                ClsMerchantRequest clsMerchantRequest=new ClsMerchantRequest();
                clsMerchantRequest.setReeworkerId(userId);
                clsMerchantRequest.setPlanId(clsHealthobjective.getId());
                getMerchantData(clsMerchantRequest);



            }
        });

        return view;
    }

    private void callSubscriptionApi(String SubPlanId) {
        if (SubPlanId==null){
            SubPlanId="0";
        }
        if (SubPlanId.isEmpty()){
            SubPlanId="0";
        }
        SessionManager sessionManager=new SessionManager(context);
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

                            showSubscriptionSuccessDailog();

                            Toast.makeText(context, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();

//                            if (from!=null&&from.equalsIgnoreCase("From_Home")){
//
//                                callForUsrFreezStatus();
//                            }
//
//                           else if (from!=null&&from.equalsIgnoreCase("change")){
//
//
//                               Intent intent=new Intent(context, MyServiceActivity.class);
//                               startActivity(intent);
//                                getActivity().finish();
//
////                                callForUsrFrefezStatus();
//                            }
//                            else {
//                                Intent intent=new Intent(context, DynamicHealthparamActivity.class);
//                                intent.putExtra("frompayment",true);
//                                startActivity(intent);
//
//                            }
//





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
    private void calledlotti(Context mContext, String str_message) {

        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);


        View sheetView = getLayoutInflater().inflate(R.layout.lay_success_mobikwik, null);
        dialog.setContentView(sheetView);


        BottomSheetBehavior behavior = BottomSheetBehavior.from((View) sheetView.getParent());


//        behavior.setPeekHeight(5000);
        behavior.setHideable(false);

        setupFullHeight(dialog);

        TextView dialogMsg = dialog.findViewById(R.id.txt_errorMSG_new);
        TextView txt_refrence_id = dialog.findViewById(R.id.txt_refrence_id);
        TextView btn_ok = dialog.findViewById(R.id.btn_ok_dialog_new);


        LottieAnimationView av_loader = dialog.findViewById(R.id.av_loader);
        av_loader.setSpeed(Float.parseFloat("0.50"));
        av_loader.useExperimentalHardwareAcceleration();
        av_loader.enableMergePathsForKitKatAndAbove(true);
        dialog.setCancelable(false);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });


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

}

