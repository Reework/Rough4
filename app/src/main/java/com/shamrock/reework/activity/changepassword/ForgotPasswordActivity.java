package com.shamrock.reework.activity.changepassword;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shamrock.R;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AuthenticationRequest;
import com.shamrock.reework.api.request.OtpRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.OtpResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edt_new_pass_confirm,edt_new_pass,edt_otp,edt_mobile;

    Button btn_update_password;
    Utils utils;
    SessionManager sessionManager;
    private int userId;
    private LoginService loginService;
    String str="hide";
    ImageView txt_show_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);

        txt_show_pass = findViewById(R.id.txt_show_pass);
        txt_show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.equalsIgnoreCase("hide")){
                    str="show";
                }else {
                    str="hide";

                }

                if(!str.equalsIgnoreCase("hide")){
                    txt_show_pass.setImageResource(R.drawable.showpass);
                    edt_new_pass.setTransformationMethod(null);
                }else {
                    txt_show_pass.setImageResource(R.drawable.hidepass);
                    edt_new_pass.setTransformationMethod(new PasswordTransformationMethod());


                }

            }
        });



        tvTitle.setText("Reset Password");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });

        loginService = Client.getClient().create(LoginService.class);

        edt_new_pass_confirm=findViewById(R.id.edt_new_pass_confirm);
        edt_otp=findViewById(R.id.edt_otp);
        edt_new_pass=findViewById(R.id.edt_new_pass);
        edt_mobile=findViewById(R.id.edt_password_mobile);
        btn_update_password=findViewById(R.id.btn_update_password);
        sessionManager = new SessionManager(ForgotPasswordActivity.this);
        if (!sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO).isEmpty()){
            edt_mobile.setText(sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO));
        }
        TextView txt_get_otp=findViewById(R.id.txt_get_otp);
        txt_get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_mobile.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter  mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edt_mobile.getText().toString().length()<10){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
               return;
                }
                callAuthenticationApi(edt_mobile.getText().toString());
            }
        });

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils = new Utils();


        btn_update_password.setText("Reset Password");
        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_mobile.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter  mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edt_mobile.getText().toString().length()<10){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (edt_otp.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (edt_new_pass.getText().toString().trim().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidPassword(edt_new_pass.getText().toString())){
                    Toast.makeText(ForgotPasswordActivity.this, "Password is not valid,should contain atleast 8 characters,one digit,one lower case letter,one special character", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_new_pass_confirm.getText().toString().trim().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!edt_new_pass.getText().toString().trim().equalsIgnoreCase(edt_new_pass_confirm.getText().toString().trim())){
                    Toast.makeText(ForgotPasswordActivity.this, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
                    return;

                }


//                getuserID(edt_otp.getText().toString().trim());


                ChangepaawordbyMobrequest clsChangePasswordRequest=new ChangepaawordbyMobrequest();
                clsChangePasswordRequest.setMobileNo(edt_mobile.getText().toString().trim());
                clsChangePasswordRequest.setOldPassword(edt_otp.getText().toString());
                clsChangePasswordRequest.setPassword(edt_new_pass.getText().toString().trim());

                loginthroughpasswordAPI(clsChangePasswordRequest);

            }
        });


    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";
//        final String PASSWORD_PATTERN = "^" +
//                               "(?=.*[0-9])" +
//                              "(?=.*[a-z])" +
//                               "(?=.*[A-Z])" +
//                               "(?=.*[a-zA-Z])" +
//        "(?=.*[@#$%^&+=])" +
//                               "(?=S+$)" +
//                               ".{8,}" +
//        "$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{8,15}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void callAuthenticationApi(String mobile)
    {
        utils.showProgressbar(ForgotPasswordActivity.this);

        AuthenticationRequest request = new AuthenticationRequest();
        request.setMobileNo(mobile.replace("+91",""));
        if (sessionManager.getStringValue(SessionManager.KEY_FCM_DEVICE_ID)!=null){
            if (sessionManager.getStringValue(SessionManager.KEY_FCM_DEVICE_ID).isEmpty()){
                request.setFcmDeviceToken("NA.");


            }else {
                request.setFcmDeviceToken(sessionManager.getStringValue(SessionManager.KEY_FCM_DEVICE_ID));

            }
        }else {
            request.setFcmDeviceToken("NA.");

        }
        request.setDeviceTypeID(0);

        Call<AuthenticationResponse> call = loginService.authenticateUser(request);
        call.enqueue(new Callback<AuthenticationResponse>()
        {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AuthenticationResponse listResponse = response.body();
                    Log.e("sunit",listResponse.toString());


                    if (listResponse != null && listResponse.getCode().equalsIgnoreCase("1"))
                    {




                        Toast.makeText(ForgotPasswordActivity.this,listResponse.getMessage(), Toast.LENGTH_LONG).show();









                    }
                    else
                    {
                        Toast.makeText(ForgotPasswordActivity.this,listResponse.getMessage(), Toast.LENGTH_LONG).show();

                        if (listResponse.getMessage().trim().equalsIgnoreCase("Your account has been freezed")){
                            return;
                        }





//                        showToast(listResponse.getMessage());


//                        Toast.makeText(LoginActivity.this, "" + listResponse.getMessage(),5000).show();
                    }
                }
                else
                {
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }

    private void getuserID(String otp) {
        OtpRequest request = new OtpRequest();
        request.setEmail(edt_mobile.getText().toString().trim());
        request.setOtp(otp);

        Call<OtpResponse> call = loginService.verifyOTP(request);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    OtpResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

                        // save user data to preferences
                        OtpResponse.DataResponse data = listResponse.getData();

                        int USER_ID = data.getUserid();

//                        ChangepaawordbyMobrequest clsChangePasswordRequest=new ChangepaawordbyMobrequest();
//                        clsChangePasswordRequest.setMobileNo(USER_ID);
//                        clsChangePasswordRequest.setOldPassword(edt_otp.getText().toString());
//                        clsChangePasswordRequest.setPassword(edt_new_pass.getText().toString().trim());
//                        loginthroughpasswordAPI(clsChangePasswordRequest);

                        Log.d("LOGIN", "LOGGED IN");


                    }
                }

            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();


            }
        });

    }


    //    http://shamrockuat.dweb.in/api/Registration/ChangePassword
    public void loginthroughpasswordAPI(ChangepaawordbyMobrequest clsPasswordRequest){
        utils.showProgressbar(ForgotPasswordActivity.this);

        Call<ChangeSucess> call = loginService.ChangePasswordbymob(clsPasswordRequest);
        call.enqueue(new Callback<ChangeSucess>()
        {
            @Override
            public void onResponse(Call<ChangeSucess> call, Response<ChangeSucess> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ChangeSucess listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(ForgotPasswordActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                finish();
                            }
                        },1000);

//
//                        finish();
                    }
                    else
                    {



                        Toast.makeText(ForgotPasswordActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();


                    }
                }
                else
                {
                }
            }

            @Override
            public void onFailure(Call<ChangeSucess> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }

}
