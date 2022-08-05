package com.shamrock.reework.activity.PaymentModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobikwik.mobikwikpglib.PaymentCheckout;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionData;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionDataBuilder;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionResponse;
import com.mobikwik.mobikwikpglib.utils.Enums;
import com.shamrock.R;
import com.shamrock.reework.activity.PaymentModule.model.request.CheckSumRequest;
import com.shamrock.reework.activity.PaymentModule.model.request.CheckSumRequestForPO;
import com.shamrock.reework.activity.PaymentModule.model.request.PaymentVerify_SaveRequest;
import com.shamrock.reework.activity.PaymentModule.model.responce.GetCheckSumForPOResponce;
import com.shamrock.reework.activity.PaymentModule.model.responce.GetCheckSumResponce;
import com.shamrock.reework.activity.PaymentModule.service.PaymentService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity implements PaymentCheckout.ZaakPayPaymentListener {

    Button btnSubmit;
    EditText txtAmount;
    SessionManager sessionManager;
    Context mContext;
    String userEmail,userMobNo;
    int userId;

    String strCheckSumForPO,strCheckSum,strOrderID;

    PaymentService paymentService;
    private Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intiViews();
    }


//initialise
public void intiViews() {

    mContext = PaymentActivity.this;
    btnSubmit = (Button)findViewById(R.id.btnSubmit);
    txtAmount = (EditText)findViewById(R.id.txtAmount);
    utils = new Utils();
    sessionManager = new SessionManager(mContext);
    paymentService = Client.getClient().create(PaymentService.class);
    userEmail = sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL);
    userMobNo = sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO);
    userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            callToGetCheckSum();
            // setCheckSums();


        }
    });
}

    //set Request Para
    private TransactionData buildViaDynamicParams() {
        TransactionDataBuilder transactionDataBuilder = TransactionDataBuilder.TransactionDataBuilder()
                .withAmount(new BigInteger(txtAmount.getText().toString()))
                .withChecksum(strCheckSum)
                .withChecksumPO(strCheckSumForPO)
                .withCurrency("INR")
                .withOrderId(strOrderID)
                .withUserEmail(userEmail)
                .withReturnUrl("https://zaakstaging.zaakpay.com/testmerchant/sdkresponse")
                .withMerchantIconUrl("")
                .withMerchantName("ShamRock")
                .withMerchantId("b19e8f103bce406cbd3476431b6b7973")
                .withUserPhoneNumber(userMobNo)
                .withEnvironment(Enums.Environment.STAGING);


        return transactionDataBuilder.build();
    }


    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    @Override
    public void onPaymentFailure(@NotNull String responseCode, @NotNull String responseMsg) {
        String sb = ("\nStatus Code : " + responseCode) +
                "\nStatus Message : " + responseMsg;
        showAlertDialog(sb);
    }

    @Override
    public void onPaymentSuccess(@NotNull TransactionResponse transactionResponse) {
        String sb = ("\nStatus Code : " + transactionResponse.getResponseCode()) +
                "\nStatus Message : " + transactionResponse.getResponseDescription();
        showAlertDialog("Payment Success " + sb);
    }

    //Calling APIS

    //for getting CheckSum
    private void callToGetCheckSum()
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        CheckSumRequest request = new CheckSumRequest();
        request.setUserId(userId);
        request.setAmount(txtAmount.getText().toString());
        request.setBankid("");
        request.setCurrency("INR");
        request.setDebitorcredit("");
        request.setMode("");
        request.setPurpose("");
        request.setShowMobile("");
        request.setTxnType("");
        request.setPaymentOptionTypes("");
        request.setZpPayOption("");
        request.setIsAutoRedirect("");
       String requestBody  = new Gson().toJson(request);
       String Test = requestBody;

        Call<GetCheckSumResponce> call = paymentService.getCheckSum(request);
        call.enqueue(new Callback<GetCheckSumResponce>()
        {
            @Override
            public void onResponse(Call<GetCheckSumResponce> call, Response<GetCheckSumResponce> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetCheckSumResponce Response = response.body();
                    if (Response != null && Response.getCode() == 1)
                    {
                         strCheckSum = response.body().getMessage();
                        GetCheckSumResponce.Data getOrderIdModel = response.body().getData();

                        if (getOrderIdModel!= null)
                        {
                             strOrderID = getOrderIdModel.getOrderId();
                            callToGetCheckSumForPO(strOrderID);
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext, Response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCheckSumResponce> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    //for getting checksum for PO

    private void callToGetCheckSumForPO(String orderID)
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        CheckSumRequestForPO request = new CheckSumRequestForPO();
        request.setOrderId(orderID);
        request.setMode("");
        String requestBody  = new Gson().toJson(request);
        String Test = requestBody;



        Call<GetCheckSumForPOResponce> call = paymentService.getCheckSumForPO(request);
        call.enqueue(new Callback<GetCheckSumForPOResponce>()
        {
            @Override
            public void onResponse(Call<GetCheckSumForPOResponce> call, Response<GetCheckSumForPOResponce> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetCheckSumForPOResponce Response = response.body();
                    if (Response != null && Response.getCode() == 1)
                    {
                         strCheckSumForPO = response.body().getMessage();
                        TransactionData transactionData = buildViaDynamicParams();
                        PaymentCheckout paymentCheckout = new PaymentCheckout(PaymentActivity.this);
                        paymentCheckout.startPayment(transactionData);

                    }
                    else
                    {
                        Toast.makeText(mContext, Response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCheckSumForPOResponce> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void callToSaveAndVerifyPayment(String responce,String recivedCheckSum)
    {

        PaymentVerify_SaveRequest request = new PaymentVerify_SaveRequest();
       // request.setReceivedChecksum();
        request.setResponseStr(responce);


    Call<ReecoachDetailsResponse> call = paymentService.verify_savePayment(request);
        call.enqueue(new Callback<ReecoachDetailsResponse>()
    {
        @Override
        public void onResponse(Call<ReecoachDetailsResponse> call, Response<ReecoachDetailsResponse> response)
        {
            utils.hideProgressbar();

            if (response.code() == Client.RESPONSE_CODE_OK)
            {
                ReecoachDetailsResponse Response = response.body();
                if (Response != null && Response.getCode() == 1)
                {
                  /*  strCheckSum = response.body().getMessage();
                    //GetCheckSumResponce.Data getOrderIdModel = response.body().getData();

                    if (getOrderIdModel!= null)
                    {
                        strOrderID = getOrderIdModel.getOrderId();
                        callToGetCheckSumForPO(strOrderID);
                    }*/
                }
                else
                {
                    Toast.makeText(mContext, Response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<ReecoachDetailsResponse> call, Throwable t)
        {
            // Log error here since request failed
            Log.e("MasterFood---->", t.toString());
            utils.hideProgressbar();
        }
    });
}

}
