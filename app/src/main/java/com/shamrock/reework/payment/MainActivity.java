package com.shamrock.reework.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mobikwik.mobikwikpglib.PaymentCheckout;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionData;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionDataBuilder;
import com.mobikwik.mobikwikpglib.lib.transactional.TransactionResponse;
import com.mobikwik.mobikwikpglib.utils.Enums;
import com.mobikwik.mobikwikpglib.utils.Utils;
import com.shamrock.R;

import java.math.BigInteger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Mayank on 21/06/2019.
 */
public class MainActivity extends AppCompatActivity implements PaymentCheckout.ZaakPayPaymentListener {


    private static final String TAG = "SDK_TEST_APP";


    private Spinner spinnerEnvironment;

    private String checksumTransact;
    private String checksumPO;

    //production

//    private String amount="100";
//    private String MerchantId="dfa59a6f4db34aa0aaf7aa11a80e9732";
//    private String SecretKey="d8d0bd9c2055428f83af18575055a460";
//    private String OrderID="113455446466656";
//    private String email="sunitpranit@gmail.com";
//    private String mobile="";
//    private String MerchantName="";
//    private String secretKey="d8d0bd9c2055428f83af18575055a460";
//    private boolean IsPrdEnvironment=true;





    //teste
    private String amount="100";
//    private String MerchantId="b6415a6443604ec59644a70c8b25a0f6";
    private String MerchantId="d22b6680ce804b1a81cdccb69a1285f1";
//    private String MerchantId="b19e8f103bce406cbd3476431b6b7973";
    private String OrderID="113d455446466656";
    private String email="a";
    private String mobile="999999999";
    private String MerchantName="Test Merchant";
    private String secretKey="0678056d96914a8583fb518caf42828a";
        private boolean IsPrdEnvironment=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payment);

        spinnerEnvironment = findViewById(R.id.spinner_environment);
        spinnerEnvironment.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Enums.Environment.values()));

        //randomly adding current time as orderId




        Intent intent=getIntent();
        if (intent!=null){
            if (intent.getStringExtra("amount")!=null){
                if (!intent.getStringExtra("amount").isEmpty()){
                    amount= String.valueOf(Math.round(Float.parseFloat(intent.getStringExtra("amount"))));
                }
            }
            if (intent.getStringExtra("OrderID")!=null){
                if (!intent.getStringExtra("OrderID").isEmpty()){
                    OrderID= String.valueOf(intent.getStringExtra("OrderID"));
                }
            }

            if (intent.getStringExtra("SecretKey")!=null){
                if (!intent.getStringExtra("SecretKey").isEmpty()){
                    secretKey= String.valueOf(intent.getStringExtra("SecretKey"));
                }
            }
            if (intent.getStringExtra("MerchantId")!=null){
                if (!intent.getStringExtra("MerchantId").isEmpty()){
                    MerchantId= String.valueOf(intent.getStringExtra("MerchantId"));
                }
            }
            if (intent.getStringExtra("mobile")!=null){
                if (!intent.getStringExtra("mobile").isEmpty()){
                    mobile= String.valueOf(intent.getStringExtra("mobile"));
                }
            }

            if (intent.getStringExtra("email")!=null){
                if (!intent.getStringExtra("email").isEmpty()){
                    email= String.valueOf(intent.getStringExtra("email"));
                }
            }
            if (intent.getStringExtra("MerchantName")!=null){
                if (!intent.getStringExtra("MerchantName").isEmpty()){
                     MerchantName = String.valueOf(intent.getStringExtra("MerchantName"));
                }
            }
        }

        findViewById(R.id.mybutt).performClick();

    }

    public void onButtonClick(View view) {
        setCheckSums();

        BigInteger bigIntegerAmount = null;
        String amtTxt = amount;
        if (!amtTxt.isEmpty()) {
            bigIntegerAmount = new BigInteger(amtTxt);
        }


        TransactionData transactionData = setAllParams(bigIntegerAmount);
        PaymentCheckout paymentCheckout = new PaymentCheckout(this);
        paymentCheckout.startPayment(transactionData);

    }

    private TransactionData setAllParams(BigInteger bigIntegerAmount) {

      TextView txtReturnUrl=findViewById(R.id.edit_text_return_url);
        TransactionData transactionData = new TransactionData();

        transactionData.setAmount(bigIntegerAmount);
        transactionData.setChecksum(checksumTransact);
        transactionData.setChecksumPO(checksumPO);
        transactionData.setMerchantId(MerchantId);
        transactionData.setMerchantName(MerchantName);
//        transactionData.setMerchantIconUrl(txtIconUrl.getText().toString());
        transactionData.setOrderId(OrderID);
        transactionData.setReturnUrl(txtReturnUrl.getText().toString());
        transactionData.setUserEmail(email);
        transactionData.setUserPhoneNumber(mobile);


        transactionData.setCurrency("INR");

        if (IsPrdEnvironment){
            transactionData.setEnvironment(Enums.Environment.PRODUCTION);

        }else {
            transactionData.setEnvironment(Enums.Environment.TESTING);

        }
        return transactionData;
    }





    private void setCheckSums() {

        String ip = Utils.getLocalIpAddress();
        String date = Utils.getCurrentDate();

        BigInteger amtInt = null;
        String amtTxt = amount;
        if (!amtTxt.isEmpty()) {
            amtInt = new BigInteger(amtTxt);
        }

        String paramsForTransactChecksum = "'" + MerchantId + "''" +
                OrderID + "''1''INR''" + amtInt +
                "''" + ip + "''" + date + "'";

        Log.d(TAG, paramsForTransactChecksum);

        String paramsForPOChecksum = "merchantIdentifier=" + MerchantId + "&email=" + email;

        //staging key575
//        String secretKey = "0678056d96914a8583fb518caf42828a";
//         secretKey = "d8d0bd9c2055428f83af18575055a460";

        try {
            checksumTransact = createChecksum(secretKey, paramsForTransactChecksum);

            checksumPO = createChecksum(secretKey, paramsForPOChecksum);

            Log.d(TAG, "setCheckSums: " + checksumTransact);
            Log.d(TAG, "setCheckSums: " + checksumPO);

        } catch (Exception ex) {
            Log.d(TAG, "checksum exception");
        }
    }

    public static String createChecksum(String secretKey, String allParamValueExceptChecksum) throws Exception {
        Log.d(TAG, "In cryptoUtils createChecksum function");
        byte[] dataToEncryptByte = allParamValueExceptChecksum.trim().getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] checksumCalculatedByte = mac.doFinal(dataToEncryptByte);
        return toHex(checksumCalculatedByte);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder buffer = new StringBuilder(bytes.length * 2);
        String str;
        for (Byte b : bytes) {
            str = Integer.toHexString(b);
            int len = str.length();
            if (len == 8) {
                buffer.append(str.substring(6));
            } else if (str.length() == 2) {
                buffer.append(str);
            } else {
                buffer.append("0" + str);
            }
        }
        return buffer.toString();
    }
    @Override
    public void onPaymentFailure(String responseCode, String responseDescription) {
        String sb = ("\nStatuscode : " + responseCode) +
                "\nStatusmessage : " + responseDescription;
        Toast.makeText(this, ""+responseDescription, Toast.LENGTH_SHORT).show();

        Intent i = new Intent();
        i.putExtra("RESULT","ok");
        i.putExtra("Payment_Status",sb);
        setResult(RESULT_OK,i);
        finish();
//        showAlertDialog(sb);
    }

    @Override
    public void onPaymentSuccess(TransactionResponse response) {
        String sb = ("\nStatuscode : " + response.getResponseCode()) +
                "\nStatusmessage : " + response.getResponseDescription();
//        Toast.makeText(this, ""+response.getResponseDescription(), Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        String json = gson.toJson(response);
        Log.d("sunit",json.toString());


        ClsPaymetresponse clsPaymetresponse=new ClsPaymetresponse();
        clsPaymetresponse.setCode(Integer.parseInt(response.getResponseCode()));
        clsPaymetresponse.setDescription(response.getResponseDescription());
        clsPaymetresponse.setOrderId(response.getOrderId());
        clsPaymetresponse.setPaymentmode(response.getPaymentMethod());


        Intent i = new Intent();
        i.putExtra("RESULT","ok");
        i.putExtra("TransactionResponse",clsPaymetresponse);

        setResult(RESULT_OK,i);
        finish();
//        showAlertDialog(sb);

    }
}
