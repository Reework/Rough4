package com.shamrock.reework.activity.changepassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
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

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.Welcome_ScheduleBloodTestActivity;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.LoginModule.OtpDialogFragment;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.activity.WelcomeModule.WelcomeActivity;
import com.shamrock.reework.activity.newlogin.ClsPasswordRequest;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewRegisterActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AuthenticationRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.OtpResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText edt_new_pass_confirm,edt_new_pass,edt_otp,edt_mobile;

    Button btn_update_password;
    Utils utils;
    SessionManager sessionManager;
    private int userId;
    private LoginService loginService;

    private ImageView txt_show_pass;
    String str="hide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Change Password");
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
        sessionManager = new SessionManager(ChangePasswordActivity.this);

        if (!sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO).isEmpty()){
            edt_mobile.setText(sessionManager.getStringValue(SessionManager.KEY_USER_MOBILE_NO));
        }

        TextView txt_get_otp=findViewById(R.id.txt_get_otp);
        txt_get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_mobile.getText().toString().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter  mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edt_mobile.getText().toString().length()<10){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
               return;
                }
                callAuthenticationApi(edt_mobile.getText().toString());
            }
        });

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        utils = new Utils();



        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edt_mobile.getText().toString().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter  mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edt_mobile.getText().toString().length()<10){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (edt_otp.getText().toString().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter otp", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (edt_new_pass.getText().toString().trim().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidPassword(edt_new_pass.getText().toString())){
                    Toast.makeText(ChangePasswordActivity.this, "Password is not valid,should contain atleast 8 characters,one digit,one lower case letter,one special character", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edt_new_pass_confirm.getText().toString().trim().isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!edt_new_pass.getText().toString().trim().equalsIgnoreCase(edt_new_pass_confirm.getText().toString().trim())){
                    Toast.makeText(ChangePasswordActivity.this, "Password and Confirm password should be same", Toast.LENGTH_SHORT).show();
                    return;

                }


                ClsChangePasswordRequest clsChangePasswordRequest=new ClsChangePasswordRequest();
                clsChangePasswordRequest.setReeworkerId(userId);
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
        utils.showProgressbar(ChangePasswordActivity.this);

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




                        Toast.makeText(ChangePasswordActivity.this,listResponse.getMessage(), Toast.LENGTH_LONG).show();









                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this,listResponse.getMessage(), Toast.LENGTH_LONG).show();

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
//    http://shamrockuat.dweb.in/api/Registration/ChangePassword
    public void loginthroughpasswordAPI(ClsChangePasswordRequest clsPasswordRequest){
        utils.showProgressbar(ChangePasswordActivity.this);

        Call<ChangeSucess> call = loginService.ChangePassword(clsPasswordRequest);
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
                        Toast.makeText(ChangePasswordActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();

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



                        Toast.makeText(ChangePasswordActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();


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
