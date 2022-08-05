package com.shamrock.reework.activity.LoginModule;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.Welcome_ScheduleBloodTestActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.adapters.AdditionalQnAdapter;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.RegistrationModule.activity.RegisterActivity;
import com.shamrock.reework.activity.UnfreezeModule.activity.UnfreezeActivity;
import com.shamrock.reework.activity.WelcomeModule.WelcomeActivity;
import com.shamrock.reework.activity.changepassword.CreatePasswordActivity;
import com.shamrock.reework.activity.changepassword.ForgotPasswordActivity;
import com.shamrock.reework.activity.newlogin.ClsPasswordRequest;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewRegisterActivity;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.joinNowActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AuthenticationRequest;
import com.shamrock.reework.api.request.OtpRequest;
import com.shamrock.reework.api.request.ReecoachDetailsRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.OtpResponse;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.reecoachmodule.activities.ReecoachDashBoardActivity;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

//import com.facebook.AccessToken;
//import com.facebook.AccessTokenTracker;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;

public class  LoginActivity extends AppCompatActivity implements View.OnClickListener,
        OtpDialogFragment.EditNameDialogListener, DialogInterface.OnCancelListener, TextWatcher, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 110;
    Context context;
    EditText editTextEmail;
    TextView textSignUp;
    Button btn_login_password;
    Button btnLogin;
    CountDownTimer cTimer = null;
    LoginService loginService;
    String lsMobile, lsFcmToken;
    OtpDialogFragment dialogFragment;
    private SessionManager session;
    private Utils utils;
    EditText edt_password;
    ImageView txt_show_pass;
    String str="hide";
    TextView tvsendotp,tvsendotp1;
    EditText edt_password_mobile, edt_otp;
    LinearLayout ll_password;
    LinearLayout ll_verification_code;
    ViewFlipper vp_login;
    TextView txt_2, txt_1;
    TextView txt_forgot_password,txt_createpassword;
    TextView login_fb;
    //google
    private GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 001;
    RelativeLayout linearLayout;
    private static final String client_Id = "833644050628-ccq7hf6ks8a6gcj3u1omr4l8m4v3p8v9.apps.googleusercontent.com";


    private RelativeLayout relSignUp;
    private LinearLayout ll_fb_google;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    TextInputLayout firstnameTextInput, lastnameTextInput, PincodeTextInput, emailTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        setContentView(R.layout.content_new_login);
        ll_fb_google=findViewById(R.id.ll_fb_google);
        tvsendotp=findViewById(R.id.tvsendotp);
        tvsendotp1=findViewById(R.id.tvsendotp1);
        relSignUp=findViewById(R.id.relSignUp);

        if (getIntent().getBooleanExtra("ISReework",false)){
            ll_fb_google.setVisibility(View.VISIBLE);
//            relSignUp.setVisibility(View.VISIBLE);


        }else {
            ll_fb_google.setVisibility(View.VISIBLE);
//            relSignUp.setVisibility(View.VISIBLE);

        }

//        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

//        callbackManager = CallbackManager.Factory.create();



        context = LoginActivity.this;
        login_fb=findViewById(R.id.login_fb);
        login_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Coming soon.....", Toast.LENGTH_SHORT).show();
            }
        });
        init();
        findViews();
        TextView login_gmail=findViewById(R.id.login_gmail);
        login_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleInitiallise();




//                startActivity(new Intent(LoginActivity.this, FacebookLoginActivity.class));
            }
        });

//        googleInitiallise();








    }

    private void googleInitiallise() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(client_Id)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();





        signIn();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cTimer!=null){
            cTimer.cancel();
        }
    }

    void startTimer()
    {
        final TextView textView_Timer=findViewById(R.id.textView_Timer);
//        final TextView tvsendotp=findViewById(R.id.tvsendotp);
        final TextView tvsendotp1=findViewById(R.id.tvsendotp1);
        cTimer = new CountDownTimer(120000, 1000)
        {
            public void onTick(long millis)
            {
                textView_Timer.setVisibility(View.VISIBLE);

                tvsendotp1.setVisibility(View.INVISIBLE);
                textView_Timer.setText("" + millis / 1000+" Seconds Left");
            }

            public void onFinish()
            {
                textView_Timer.setVisibility(View.INVISIBLE);
                tvsendotp1.setVisibility(View.VISIBLE);
                tvsendotp1.setText("Resend OTP ");
                if (cTimer!=null){
                    cTimer.cancel();
                }
//                btnSubmit.setEnabled(false);
//                editTextOTP.setText("");
//                editTextOTP.setEnabled(false);
            }
        };
        cTimer.start();
    }

    //cancel timer
    public void cancelTimer()
    {
        if (cTimer != null)
            cTimer.cancel();
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void init() {
        txt_2 = findViewById(R.id.txt_2);
        txt_1 = findViewById(R.id.txt_1);
        vp_login = findViewById(R.id.vp_login);
        vp_login.setDisplayedChild(0);
        ll_password = findViewById(R.id.ll_password);
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
                    txt_show_pass.setImageResource(R.drawable.visibility_gray);
                    edt_password.setTransformationMethod(null);
                }else {
                    txt_show_pass.setImageResource(R.drawable.offvisibility_gray);
                    edt_password.setTransformationMethod(new PasswordTransformationMethod());


                }

            }
        });
        txt_createpassword = findViewById(R.id.txt_createpassword);
        txt_forgot_password = findViewById(R.id.txt_forgot_password);

        txt_createpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, NewRegisterActivity.class));

            }
        });
        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

            }
        });
        ll_verification_code = findViewById(R.id.ll_verification_code);
        final View view_txt_1=findViewById(R.id.view_txt_1);
        final View view_txt_2=findViewById(R.id.view_txt_2);


        final RelativeLayout rel_pass=findViewById(R.id.rel_pass);
        final RelativeLayout relSignUp=findViewById(R.id.relSignUp);
        ll_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_1.setTextColor(getResources().getColor(R.color.gray));
                txt_2.setTextColor(getResources().getColor(R.color.black));
                view_txt_2.setVisibility(View.VISIBLE);
                view_txt_1.setVisibility(View.INVISIBLE);
                vp_login.setDisplayedChild(0);
                rel_pass.setVisibility(View.VISIBLE);
                relSignUp.setVisibility(View.GONE);

            }
        });


        ll_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_2.setTextColor(getResources().getColor(R.color.gray));
                txt_1.setTextColor(getResources().getColor(R.color.black));
                vp_login.setDisplayedChild(1);
                view_txt_2.setVisibility(View.INVISIBLE);
                view_txt_1.setVisibility(View.VISIBLE);
                rel_pass.setVisibility(View.GONE);
                relSignUp.setVisibility(View.VISIBLE);


            }
        });

        loginService = Client.getClient().create(LoginService.class);
        session = new SessionManager(context);
        utils = new Utils();

        lsFcmToken = session.getStringValue(SessionManager.KEY_FCM_DEVICE_ID);
        Log.d("FCM TOKEN------>", lsFcmToken);
    }

    private void findViews() {
        editTextEmail = findViewById(R.id.edtText_Login_Email);
        btn_login_password = findViewById(R.id.btn_login_password);
        edt_password_mobile = findViewById(R.id.edt_password_mobile);
         edt_otp=findViewById(R.id.edt_otp);
        editTextEmail.addTextChangedListener(this);
        edt_password_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text_value = edt_password_mobile.getText().toString().trim();
                if (text_value.equalsIgnoreCase("+91")) {
                    edt_password_mobile.setText("");
                } else {
                    if (!text_value.startsWith("+91") && text_value.length() > 0) {
                        edt_password_mobile.setText("+91" + s.toString());
                        Selection.setSelection(edt_password_mobile.getText(), edt_password_mobile.getText().length());
                    }
                }


                if (edt_password_mobile.getText().toString().trim().length() == 13) {
                    try {

                        editTextEmail.setText(edt_password_mobile.getText().toString().trim());
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
//            btnLogin.performClick();
                }

                if (edt_password_mobile.getText().toString().trim().length() == 0) {
                    try {

                        editTextEmail.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
//            btnLogin.performClick();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        textSignUp = findViewById(R.id.textSignUp);
        btnLogin = findViewById(R.id.buttonSubmitLogin);
        edt_password = findViewById(R.id.edt_password);


        btn_login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_password_mobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }


                else if (edt_password_mobile.getText().toString().length() < 13)
                {
                    Toast.makeText(context, "Please enter 10 digit Mobile Number", Toast.LENGTH_SHORT).show();
                    return;

                }

                else if (!Utils.isValidMobileNo(edt_password_mobile.getText().toString()))
                {
                    Toast.makeText(context, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (edt_password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                ClsPasswordRequest clsPasswordRequest = new ClsPasswordRequest();


                String mob=edt_password_mobile.getText().toString();
                clsPasswordRequest.setMobileNo(mob.replace("+91",""));
                clsPasswordRequest.setPassword(edt_password.getText().toString().trim());
                loginthroughpasswordAPI(clsPasswordRequest);

            }
        });

        btnLogin.setOnClickListener(this);
        tvsendotp.setOnClickListener(this);
        tvsendotp1.setOnClickListener(this);
        textSignUp.setOnClickListener(this);
        editTextEmail.addTextChangedListener(this);

        final TextView text_dummy_hint_fullname = (TextView) findViewById(R.id.text_dummy_hint_fullname);

        edt_password_mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_fullname.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (edt_password_mobile.getText().length() > 0)
                        text_dummy_hint_fullname.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_fullname.setVisibility(View.INVISIBLE);
                }
            }
        });


        final TextView text_dummy_hint_fullname1 = (TextView) findViewById(R.id.text_dummy_hint_fullname1);

        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_fullname1.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (edt_password.getText().length() > 0)
                        text_dummy_hint_fullname1.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_fullname1.setVisibility(View.INVISIBLE);
                }
            }
        });


        final TextView text_dummy_hint_fullname2 = (TextView) findViewById(R.id.text_dummy_hint_full);

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_fullname2.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editTextEmail.getText().length() > 0)
                        text_dummy_hint_fullname2.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_fullname2.setVisibility(View.INVISIBLE);
                }
            }
        });


        final TextView text_dummy_hint_fullname3 = (TextView) findViewById(R.id.text_dummy_hint_f);

        edt_otp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            text_dummy_hint_fullname3.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if ( edt_otp.getText().length() > 0)
                        text_dummy_hint_fullname3.setVisibility(View.VISIBLE);
                    else
                        text_dummy_hint_fullname3.setVisibility(View.INVISIBLE);
                }
            }
        });









//        checkAndRequestPermissions();
    }

    private boolean checkAndRequestPermissions() {
        int permissionREADEXTR = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionWRITEEXTR = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permissionREADPHONSTATE = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionREADEXTR != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWRITEEXTR != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionREADPHONSTATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
//                Toast.makeText(context, "OTP = " + message, Toast.LENGTH_SHORT).show();
//                editTextEmail.setText(message);
                if (dialogFragment != null) {
                    dialogFragment.setOtpText(message);
                }
                //Do whatever you want with the code here
            }
        }
    };

    @Override
    public void onResume() {
        if (mGoogleSignInClient!=null){
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
// ...
                        }
                    });
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonSubmitLogin:


                callOtpVerificationApi(edt_otp.getText().toString().trim());

                break;



            case R.id.tvsendotp:
                if (editTextEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter mobile number.", Toast.LENGTH_SHORT).show();
                    return;
                }


                lsMobile = editTextEmail.getText().toString().trim();

                if ((lsMobile.length() == 13) && Utils.isValidMobileNo(lsMobile)) {
                    Utils.hideSoftInput(this, context);

                    if (Utils.isNetworkAvailable(context)) {


                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        callAuthenticationApi(lsMobile);
//                        tvsendotp.setText("Resend");

                        Log.d("CALLING_AUTH_API", "CALLING_AUTHENTICATION_API");
                    } else {
                        showRetryBar(getString(R.string.internet_connection_unavailable));
                    }
                } else {
                    if (lsMobile.length() == 0) {
                        Toast.makeText(context, "Mobile Number can not be blank", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Enter valid mobile number", Toast.LENGTH_SHORT).show();

                    }
                }
                break;

            case R.id.tvsendotp1:
                if (editTextEmail.getText().toString().trim().isEmpty()){
                    Toast.makeText(context, "Please enter mobile number.", Toast.LENGTH_SHORT).show();
                    return;
                }


                lsMobile = editTextEmail.getText().toString().trim();

                if ((lsMobile.length() == 13) && Utils.isValidMobileNo(lsMobile)) {
                    Utils.hideSoftInput(this, context);

                    if (Utils.isNetworkAvailable(context)) {


                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        callAuthenticationApi(lsMobile);
//                        tvsendotp.setText("Resend");

                        Log.d("CALLING_AUTH_API", "CALLING_AUTHENTICATION_API");
                    } else {
                        showRetryBar(getString(R.string.internet_connection_unavailable));
                    }
                } else {
                    if (lsMobile.length() == 0) {
                        Toast.makeText(context, "Mobile Number can not be blank", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Enter valid mobile number", Toast.LENGTH_SHORT).show();

                    }
                }
                break;

            case R.id.textSignUp:
//                startActivity(new Intent(context, RegisterActivity.class));
//                startActivity(new Intent(LoginActivity.this, NewRegisterActivity.class));

                Intent intent = new Intent(LoginActivity.this, NewRegisterActivity.class);
                intent.putExtra("KEY_MOB", lsMobile);
                startActivity(intent);
                /*if (Utils.isNetworkAvailable(context))
                {
                    startActivity(new Intent(context, RegisterActivity.class));
                    finish();
                }
                else
                    showRetryBar(getString(R.string.internet_connection_unavailable));*/
                break;
            default:
        }
    }







    private void callOtpVerificationApi(String otp) {
        lsMobile=editTextEmail.getText().toString().trim();
        OtpRequest request = new OtpRequest();
        request.setEmail(lsMobile.replace("+91", ""));
        request.setOtp(otp);

        Call<OtpResponse> call = loginService.verifyOTP(request);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    OtpResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
//                        dialogFragment.cancelTimer();

                        // save user data to preferences
                        OtpResponse.DataResponse data = listResponse.getData();

                        int USER_ID = data.getUserid();
                        int USER_REECOACH_ID = data.getReecoachId();
                        String USER_F_NAME = data.getFirstName();
                        String USER_L_NAME = data.getLastName();
                        String USER_EMAIL = data.getEmail();

                        String USER_MOBILE_NO = data.getMobileNo();
                        String USER_DOB = data.getDOB();
                        int USER_GENDER = data.getGender();
                        String USER_ADDRESS = data.getAddress();
                        int USER_ROLE_ID = data.getRoleID();
                        int USER_COUNTRY_ID = data.getCountryId();
                        int USER_STATE_ID = data.getStateId();
                        int USER_CITY_ID = data.getCityId();
                        String USER_LANGUAGE_CODE = data.getLangCode();
                        int USER_PLAN_ID = data.getPlanID();
                        boolean USER_IS_VERIFIED = data.getIsVerified();
                        boolean USER_IS_FREEZE = data.getIsFreezed();
                        boolean USER_IS_DELETED = data.getIsDeleted();
                        //String USER_DELETED_ON = data.getDeletedOn();
                        String USER_OTP_STATUS = (String) data.getOtpStatus();
                        String USER_TOKEN = data.getToken();
                        boolean USER_HealthFound = data.getHealthParam();
                        boolean user_blood_test_schedule = data.getIsBloodTestSchedule();
                        boolean user_blood_report_done = data.getBloodTestReport();
                        String USER_Image = data.getImageUrl();
                        String COUNTRY_NAME = data.getCountryName();
                        String STATE_NAME = data.getStateName();
                        String CITY_NAME = data.getCityName();

                        callProfileDetailsAPI(data.getEmail(),data.getUserid());
//                        String SUBSCRITPION_PLANS =   new Gson().toJson(data.getSubscribedFeatureList());
//                        Log.d("createLoginSession2",SUBSCRITPION_PLANS);
                        session.createLoginSession(USER_ID, USER_REECOACH_ID, USER_F_NAME, USER_L_NAME, USER_EMAIL, USER_MOBILE_NO,
                                USER_DOB, USER_GENDER, USER_ADDRESS, USER_ROLE_ID, USER_COUNTRY_ID, USER_STATE_ID, USER_CITY_ID,
                                USER_LANGUAGE_CODE, USER_PLAN_ID, USER_IS_VERIFIED, USER_IS_FREEZE, USER_IS_DELETED, USER_OTP_STATUS,
                                USER_TOKEN, USER_HealthFound, user_blood_test_schedule, user_blood_report_done,
                                USER_Image, COUNTRY_NAME, STATE_NAME, CITY_NAME, "");

                        Log.d("LOGIN", "LOGGED IN");

                        session.setStringValue("ROLEID", String.valueOf(data.getRoleID()));

                        if (data.getRoleID()==2){
                            if (USER_IS_FREEZE) {
                                Intent intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                if (USER_PLAN_ID == 1) {
                                    if (session.getBooleanValue(SessionManager.KEY_USER_IS_BLOOD_TEST_SCHEDULE)) {
                                        Intent intent = new Intent(context, WelcomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {


                                        if (session.getStringValue("Trial").equalsIgnoreCase("true")) {

                                            Intent intents = new Intent(context, WelcomeActivity.class);
                                            intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intents.putExtra("isFirstTime", true);
                                            startActivity(intents);
                                        } else {
                                            Intent intent = new Intent(context, Welcome_ScheduleBloodTestActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }


                                    }
                                } else {

                                    Intent intent = new Intent(context, WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                }

                            }
                        }else {
                            Intent intent = new Intent(context, ReecoachDashBoardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            finish();
                        }


                        finish();
                    } else {


                        Toast.makeText(LoginActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();

//                        dialogFragment.visibleResend();

                    }
                } else {
                    showRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.e(TAG, t.toString());
                showRetryBar("");
            }
        });

    }
    private void callProfileDetailsAPI(String email, int userid) {

        ReecoachDetailsRequest request = new ReecoachDetailsRequest();
        request.setEmail(email);
        request.setUserid(userid);

      final SessionManager  sessionManager = new SessionManager(context);
        ReecoachService   reecoachService = Client.getClient().create(ReecoachService.class);
        Call<ReecoachDetailsResponse> call = reecoachService.getProfileDetails(request);
        call.enqueue(new Callback<ReecoachDetailsResponse>()
        {
            @Override
            public void onResponse(Call<ReecoachDetailsResponse> call, Response<ReecoachDetailsResponse> response) {
//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ReecoachDetailsResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        final ReecoachDetailsResponse.DataResponse dataResponse = listResponse.getData();

                        if (dataResponse != null) {


                            final String reecoach = dataResponse.getImageUrl();
                            sessionManager.setStringValue("ReecoachImage",reecoach);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReecoachDetailsResponse> call, Throwable t)
            {
                // Log error here since request failed
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void callAuthenticationApi(String mobile) {
        if (!((Activity) context).isFinishing()) {
            utils.showProgressbar(context);
        }

        AuthenticationRequest request = new AuthenticationRequest();
        request.setMobileNo(mobile.replace("+91", ""));
        if (session.getStringValue(SessionManager.KEY_FCM_DEVICE_ID) != null) {
            if (session.getStringValue(SessionManager.KEY_FCM_DEVICE_ID).isEmpty()) {
                request.setFcmDeviceToken("NA.");


            } else {
                request.setFcmDeviceToken(session.getStringValue(SessionManager.KEY_FCM_DEVICE_ID));

            }
        } else {
            request.setFcmDeviceToken("NA.");

        }
        request.setDeviceTypeID(0);

        Call<AuthenticationResponse> call = loginService.authenticateUser(request);
        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    AuthenticationResponse listResponse = response.body();
                    Log.e("sunit", listResponse.toString());




                    if (listResponse != null && listResponse.getCode().equalsIgnoreCase("1")) {
                        try {

                            int duration = 5000;
                            snackWithCustomTiming(findViewById(android.R.id.content)
                                    , listResponse.getMessage(), duration);

                            startTimer();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (listResponse.getMessage().trim().equalsIgnoreCase("Your account has been freezed")) {

                            Intent  intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("ISFromLogin",true);
                            startActivity(intent);
                            return;
                        }



                        if (listResponse.getCode().equals("2")){
                            Intent intent = new Intent(LoginActivity.this, NewRegisterActivity.class);
                            intent.putExtra("KEY_MOB", lsMobile);
                            startActivity(intent);

                        }

                        if (listResponse.getMessage().toString().equalsIgnoreCase("Please confirm your 'Activation Email' to proceed further.")){
//                            Toast.makeText(LoginActivity.this, ""+listResponse.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            expiredDialog();
                        }



//                        showToast(listResponse.getMessage());


//                        Toast.makeText(LoginActivity.this, "" + listResponse.getMessage(),5000).show();
                    }
                } else {
                    showRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    public void expiredDialog() {
        try {
            final Dialog dialog = new Dialog(LoginActivity.this, R.style.CustomDialog);
            dialog.setContentView(R.layout.dialog_email_verified);
            dialog.setCancelable(false);

//            Please confirm your 'Activation Email' to proceed further.
            TextView txtmessage = dialog.findViewById(R.id.txtmessage);

            String normaltext1 = "Please confirm your";
            String boldText = "Activation Email";
            String normalText = "to proceed further";
            SpannableString str = new SpannableString(normaltext1+boldText + normalText);
            str.setSpan(new StyleSpan(Typeface.BOLD), 20, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            txtmessage.setText(str);
            Button btn_ok_close = dialog.findViewById(R.id.btn_ok_close);
            btn_ok_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.exit(0);
                }
            });


            if (dialog != null) {

                dialog.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void loginthroughpasswordAPI(ClsPasswordRequest clsPasswordRequest) {
        utils.showProgressbar(context);
        Call<OtpResponse> call = loginService.getVerifyPassword(clsPasswordRequest);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    OtpResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

//                     dialogFragment.cancelTimer();

                        // save user data to preferences
                        OtpResponse.DataResponse data = listResponse.getData();

                        int USER_ID = data.getUserid();
                        int USER_REECOACH_ID = data.getReecoachId();
                        String USER_F_NAME = data.getFirstName();
                        String USER_L_NAME = data.getLastName();
                        String USER_EMAIL = data.getEmail();

                        String USER_MOBILE_NO = data.getMobileNo();
                        String USER_DOB = data.getDOB();
                        int USER_GENDER = data.getGender();
                        String USER_ADDRESS = data.getAddress();
                        int USER_ROLE_ID = data.getRoleID();
                        int USER_COUNTRY_ID = data.getCountryId();
                        int USER_STATE_ID = data.getStateId();
                        int USER_CITY_ID = data.getCityId();
                        String USER_LANGUAGE_CODE = data.getLangCode();
                        int USER_PLAN_ID = data.getPlanID();
                        boolean USER_IS_VERIFIED = data.getIsVerified();
                        boolean USER_IS_FREEZE = data.getIsFreezed();
                        boolean USER_IS_DELETED = data.getIsDeleted();
                        //String USER_DELETED_ON = data.getDeletedOn();
                        String USER_OTP_STATUS = (String) data.getOtpStatus();
                        String USER_TOKEN = data.getToken();
                        boolean USER_HealthFound = data.getHealthParam();
                        boolean user_blood_test_schedule = data.getIsBloodTestSchedule();
                        boolean user_blood_report_done = data.getBloodTestReport();
                        String USER_Image = data.getImageUrl();
                        String COUNTRY_NAME = data.getCountryName();
                        String STATE_NAME = data.getStateName();
                        String CITY_NAME = data.getCityName();
//                        String SUBSCRITPION_PLANS =   new Gson().toJson(data.getSubscribedFeatureList());
//                        Log.d("createLoginSession2",SUBSCRITPION_PLANS);
                        session.createLoginSession(USER_ID, USER_REECOACH_ID, USER_F_NAME, USER_L_NAME, USER_EMAIL, USER_MOBILE_NO,
                                USER_DOB, USER_GENDER, USER_ADDRESS, USER_ROLE_ID, USER_COUNTRY_ID, USER_STATE_ID, USER_CITY_ID,
                                USER_LANGUAGE_CODE, USER_PLAN_ID, USER_IS_VERIFIED, USER_IS_FREEZE, USER_IS_DELETED, USER_OTP_STATUS,
                                USER_TOKEN, USER_HealthFound, user_blood_test_schedule, user_blood_report_done,
                                USER_Image, COUNTRY_NAME, STATE_NAME, CITY_NAME, "");

                        Log.d("LOGIN", "LOGGED IN");
                        session.setStringValue("ROLEID", String.valueOf(data.getRoleID()));

                        session.setStringValue("Createdby", String.valueOf(data.getCreatedBy()));
                        callProfileDetailsAPI(data.getEmail(),data.getUserid());




                        if (data.getRoleID()==2){

                            if (USER_IS_FREEZE) {
                                Intent intent = new Intent(context, UnfreezeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                if (USER_PLAN_ID == 1) {
                                    if (session.getBooleanValue(SessionManager.KEY_USER_IS_BLOOD_TEST_SCHEDULE)) {
                                        Intent intent = new Intent(context, WelcomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {


                                        if (session.getStringValue("Trial").equalsIgnoreCase("true")) {

                                            Intent intents = new Intent(context, WelcomeActivity.class);
                                            intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intents.putExtra("isFirstTime", true);
                                            startActivity(intents);
                                        } else {
                                            Intent intent = new Intent(context, Welcome_ScheduleBloodTestActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }


                                    }
                                } else {
                                    if (!data.getHealthParam()&&data.getCreatedBy()==1){
                                        session.setStringValue("FromWeb","true");
                                        Intent intent=new Intent(LoginActivity.this, joinNowActivity.class);
                                        intent.putExtra("fromWeb","true");
                                        startActivity(intent);


                                    }else {
                                        Intent intent = new Intent(context, WelcomeActivity.class);
                                        intent.putExtra("fromWeb","true");
                                        session.setStringValue("FromWeb","true");
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }




                                }

                            }
                        }else {
                            Intent intent = new Intent(context, ReecoachDashBoardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        finish();
                    } else {



//                        Toast.makeText(LoginActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();

                        if (listResponse.getMessage().toString().equalsIgnoreCase("Please confirm your 'Activation Email' to proceed further.")){
//                            Toast.makeText(LoginActivity.this, ""+listResponse.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            expiredDialog();
                            return;
                        }


                        if (listResponse.getMessage().equalsIgnoreCase("Mobile Number not Found")) {
                            Intent intent = new Intent(LoginActivity.this, NewRegisterActivity.class);
                            intent.putExtra("KEY_MOB", edt_password_mobile.getText().toString());
                            startActivity(intent);
                        }

//                        dialogFragment.visibleResend();

                    }
                } else {
                    showRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });
    }


    public void loginthrougGoogleAPI(ClsPasswordRequest clsPasswordRequest, final String id, final String personName, final String email) {
        utils.showProgressbar(context);
        Call<OtpResponse> call = loginService.getVerifyPassword(clsPasswordRequest);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    OtpResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {

//                        dialogFragment.cancelTimer();

                        // save user data to preferences
                        OtpResponse.DataResponse data = listResponse.getData();

                        int USER_ID = data.getUserid();
                        int USER_REECOACH_ID = data.getReecoachId();
                        String USER_F_NAME = data.getFirstName();
                        String USER_L_NAME = data.getLastName();
                        String USER_EMAIL = data.getEmail();

                        String USER_MOBILE_NO = data.getMobileNo();
                        String USER_DOB = data.getDOB();
                        int USER_GENDER = data.getGender();
                        String USER_ADDRESS = data.getAddress();
                        int USER_ROLE_ID = data.getRoleID();
                        int USER_COUNTRY_ID = data.getCountryId();
                        int USER_STATE_ID = data.getStateId();
                        int USER_CITY_ID = data.getCityId();
                        String USER_LANGUAGE_CODE = data.getLangCode();
                        int USER_PLAN_ID = data.getPlanID();
                        boolean USER_IS_VERIFIED = data.getIsVerified();
                        boolean USER_IS_FREEZE = data.getIsFreezed();
                        boolean USER_IS_DELETED = data.getIsDeleted();
                        //String USER_DELETED_ON = data.getDeletedOn();
                        String USER_OTP_STATUS = (String) data.getOtpStatus();
                        String USER_TOKEN = data.getToken();
                        boolean USER_HealthFound = data.getHealthParam();
                        boolean user_blood_test_schedule = data.getIsBloodTestSchedule();
                        boolean user_blood_report_done = data.getBloodTestReport();
                        String USER_Image = data.getImageUrl();
                        session.setStringValue("ReecoachPhoto",USER_Image);
                        String COUNTRY_NAME = data.getCountryName();
                        String STATE_NAME = data.getStateName();
                        String CITY_NAME = data.getCityName();
//                        String SUBSCRITPION_PLANS =   new Gson().toJson(data.getSubscribedFeatureList());
//                        Log.d("createLoginSession2",SUBSCRITPION_PLANS);
                        session.createLoginSession(USER_ID, USER_REECOACH_ID, USER_F_NAME, USER_L_NAME, USER_EMAIL, USER_MOBILE_NO,
                                USER_DOB, USER_GENDER, USER_ADDRESS, USER_ROLE_ID, USER_COUNTRY_ID, USER_STATE_ID, USER_CITY_ID,
                                USER_LANGUAGE_CODE, USER_PLAN_ID, USER_IS_VERIFIED, USER_IS_FREEZE, USER_IS_DELETED, USER_OTP_STATUS,
                                USER_TOKEN, USER_HealthFound, user_blood_test_schedule, user_blood_report_done,
                                USER_Image, COUNTRY_NAME, STATE_NAME, CITY_NAME, "");

                        Log.d("LOGIN", "LOGGED IN");

                        if (USER_IS_FREEZE) {
                            Intent intent = new Intent(context, UnfreezeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            if (USER_PLAN_ID == 1) {
                                if (session.getBooleanValue(SessionManager.KEY_USER_IS_BLOOD_TEST_SCHEDULE)) {
                                    Intent intent = new Intent(context, WelcomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {


                                    if (session.getStringValue("Trial").equalsIgnoreCase("true")) {

                                        Intent intents = new Intent(context, WelcomeActivity.class);
                                        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intents.putExtra("isFirstTime", true);
                                        startActivity(intents);
                                    } else {
                                        Intent intent = new Intent(context, Welcome_ScheduleBloodTestActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }


                                }
                            } else {
                                Intent intent = new Intent(context, WelcomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            /*Intent intent = new Intent(context, WelcomeActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);*/
                        }
                        finish();
                    } else {

                        Intent intent = new Intent(LoginActivity.this, NewRegisterActivity.class);
                        intent.putExtra("KEY_MOB", "");
                        intent.putExtra("KEY_FROM_GOOGLE",true);
                        intent.putExtra("personId", ""+id);
                        intent.putExtra("personEmail", ""+email);
                        intent.putExtra("personName", ""+personName);
                        startActivity(intent);


//                        Toast.makeText(LoginActivity.this, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();

                    }
                } else {
                    showRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });
    }


    public void snackWithCustomTiming(View content, String message, int duration) {
        final Snackbar snackbar = Snackbar.make(content, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                snackbar.dismiss();
            }
        }, duration);
    }

    public void
    showToast(String message) {
        // Set the toast and duration
        Toast mToastToShow;
        int toastDurationInMilliSeconds = 5000;
        mToastToShow = Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        final Toast finalMToastToShow = mToastToShow;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                finalMToastToShow.show();
            }

            public void onFinish() {
                finalMToastToShow.show();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }

    private void showRetryBar(String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();


    }
//
//    @Override
//    public void onBackPressed() {
//        if (exit) {
//            //super.onBackPressed();
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    exit = false;
//                    finish();
//                }
//            }, 3 * 1000);
//
//        }
//    }

    @Override
    public void onSubmitOTP(String otp) {


        utils.showProgressbar(context);
        callOtpVerificationApi(otp);
    }

    @Override
    public void onResendOtp() {
        callAuthenticationApi(lsMobile);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        Toast.makeText(context, "Dialog canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        String text_value = editTextEmail.getText().toString().trim();
        if (text_value.equalsIgnoreCase("+91")) {
            editTextEmail.setText("");
        } else {
            if (!text_value.startsWith("+91") && text_value.length() > 0) {
                editTextEmail.setText("+91" + charSequence.toString());
                Selection.setSelection(editTextEmail.getText(), editTextEmail.getText().length());
            }
        }


        if (editTextEmail.getText().toString().trim().length() == 13) {
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                // TODO: handle exception
            }
//            btnLogin.performClick();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
// a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);


            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();



                callGoogleLoginAPI(personId,personName,personEmail);





            }

//
        } catch (ApiException e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void callGoogleLoginAPI(String id, String personName, String email) {

        ClsPasswordRequest clsPasswordRequest=new ClsPasswordRequest();
        clsPasswordRequest.setAuthType(1);
        clsPasswordRequest.setAuthToken(id);

        loginthrougGoogleAPI(clsPasswordRequest,id,personName,email);
    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
// updateUI(account);
    }
//    protected void facebookInit()
//    {
//        try {
//            boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
//            if (!loggedOut) {
//                // getUserProfile(AccessToken.getCurrentAccessToken());
//            }
//            AccessTokenTracker fbTracker = new AccessTokenTracker() {
//                @Override
//                protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
//                    if (accessToken2 == null) {
//                        //  Toast.makeText(getApplicationContext(), "User Logged Out.", Toast.LENGTH_LONG).show();
//                    }
//                }
//            };
//            fbTracker.startTracking();
//            loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
//            callbackManager = CallbackManager.Factory.create();
//            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//                @Override
//                public void onSuccess(LoginResult loginResult) {
//                    boolean loggedOut = AccessToken.getCurrentAccessToken() == null;
//                    // MyUtils.showToast(LoginActivity.this, "OnClick call");
//
//                    if (!loggedOut) {
//                        getUserProfile(AccessToken.getCurrentAccessToken());
//                    } else {
//                        //
//                    }
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//
//                @Override
//                public void onError(FacebookException exception) {
//                    Toast.makeText(context, ""+exception.toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("error",e.toString());
//
//        }
//    }
//    private void getUserProfile(AccessToken currentAccessToken)
//    {
//
//        GraphRequest request = GraphRequest.newMeRequest(
//                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        Log.d("fbdata", object.toString());
//                        String image_url="";
//                        try {
//                            String first_name = object.getString("first_name");
//                            String last_name = object.getString("last_name");
//                            if (response.getJSONObject().has("id")){
//                                String id=object.getString("id");
//                                image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
//
//
//                            }
//
//                            String email;
//                            if (response.getJSONObject().has("email")) {
//                                email = object.getString("email");
//                            } else {
//                                email = "";
//                            }
//
//
//                            getFbData(first_name + " " + last_name, object.getString("id"),email,image_url );
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "first_name,last_name,email,id");
//        request.setParameters(parameters);
//        request.executeAsync();
//
//    }

}
