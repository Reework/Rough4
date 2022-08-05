package com.shamrock.reework.razerpaypsyment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.BeforeAfterModule.adapter.PActivity;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.payment.ClsPaymetresponse;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentWebviewActivity extends AppCompatActivity {
    WebView webview_payment;
    String from;

    @Override
    public void onBackPressed() {
        if (from!=null&&from.equalsIgnoreCase("Reecoach")){
            Intent i = new Intent();
            i.putExtra("RESULT","ok");
            setResult(RESULT_OK,i);
            finish();
            SessionManager   sessionManager=new SessionManager(PaymentWebviewActivity.this);
            sessionManager.setStringValue("Back","true");

            return;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_webview);
        TextView tvTitle=findViewById(R.id.tvTitle);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        from=getIntent().getStringExtra("from");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from!=null&&from.equalsIgnoreCase("Reecoach")){
                    Intent i = new Intent();
                    i.putExtra("RESULT","ok");
                    setResult(RESULT_OK,i);
                    finish();
                 SessionManager   sessionManager=new SessionManager(PaymentWebviewActivity.this);
                 sessionManager.setStringValue("Back","true");

                    return;

                }

                finish();
            }
        });
        tvTitle.setText("Reework ");

        webview_payment=findViewById(R.id.webview_payment);
        webview_payment.getSettings().setJavaScriptEnabled(true);
        webview_payment.loadUrl(getIntent().getStringExtra("ShortUrl"));
        webview_payment.setWebViewClient(new myWebViewClient());
        webview_payment.addJavascriptInterface(new Object()
        {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl)
            {
                if (from!=null&&from.equalsIgnoreCase("Reecoach")){
                    Intent i = new Intent();
                    i.putExtra("RESULT","ok");
                    setResult(RESULT_OK,i);
                    finish();
                    return;

                }

                    final SessionManager sessionManager=new SessionManager(PaymentWebviewActivity.this);
                sessionManager.setStringValue("notallowed","false");
                showSubscriptionSuccessDailog();



            }
        }, "ok");


    }
    public void showSubscriptionSuccessDailog(){

        final Dialog dialog = new Dialog(PaymentWebviewActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.dailog_subscription);
        Button btn_subscription_done=dialog.findViewById(R.id.btn_subscription_done);



        btn_subscription_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                From_no_plan

                if (from!=null&&from.equalsIgnoreCase("Reecoach")){
                    Intent i = new Intent();
                        i.putExtra("RESULT","ok");
                        setResult(RESULT_OK,i);
                        finish();

                }

                if (from!=null&&from.equalsIgnoreCase("From_no_plan")){

                    finish();
                    return;

                }

                if (from!=null&&from.equalsIgnoreCase("From_Home")){

                    callForUsrFreezStatus();
                }

                else if (from!=null&&from.equalsIgnoreCase("change")){


                    Intent intent=new Intent(PaymentWebviewActivity.this, MyServiceActivity.class);
                    startActivity(intent);
                    finish();

//                                callForUsrFrefezStatus();
                }
                else {
                    Intent intent=new Intent(PaymentWebviewActivity.this, DynamicHealthparamActivity.class);
                    intent.putExtra("frompayment",true);
                    startActivity(intent);

                }
                dialog.dismiss();


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
    private void callForUsrFreezStatus()
    {
        final Utils  utils = new Utils();

      final SessionManager  sessionManager = new SessionManager(PaymentWebviewActivity.this);
      int  userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils.showProgressbar(PaymentWebviewActivity.this);
        UnfreezeService unfreezeService = Client.getClient().create(UnfreezeService.class);

       String token = sessionManager.getStringValue(SessionManager.KEY_USER_TOKEN);

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
                                Intent intent=new Intent(PaymentWebviewActivity.this, DynamicHealthparamActivity.class);
                                intent.putExtra("param","Trial");
                                startActivity(intent);
                                finish();

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

    public class myWebViewClient extends WebViewClient {
        final Utils  utils = new Utils();

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);


                    utils.showProgressbar(PaymentWebviewActivity.this);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            utils.hideProgressbar();
        }
    }
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 3000 && resultCode == RESULT_OK)
    {
        if (data != null && data.hasExtra("RESULT"))
        {
            if (data.getStringExtra("RESULT").equals("ok"))
            {


                Intent i = new Intent();
                i.putExtra("RESULT","ok");
                setResult(RESULT_OK,i);
                finish();




            }
        }
    }

}

}
