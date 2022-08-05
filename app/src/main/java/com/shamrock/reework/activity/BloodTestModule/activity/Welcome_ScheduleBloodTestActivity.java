package com.shamrock.reework.activity.BloodTestModule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.WelcomeModule.WelcomeActivity;
import com.shamrock.reework.database.SessionManager;

public class Welcome_ScheduleBloodTestActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    //    Toolbar toolbar;
    Typeface font;
    Button btnTestNow, btnTestLater;
    SessionManager sessionManager;
    TextView textView_Title;
    boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_blood_test);

        context = Welcome_ScheduleBloodTestActivity.this;
        init();
        //setToolBar();
        findViews();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isFirstTime = extras.getBoolean("isFirstTime");

        }

    }


    private void init() {
        sessionManager = new SessionManager(context);
    }


    private void findViews() {
        textView_Title = findViewById(R.id.textWelcomeBold_SBT);
        btnTestNow = findViewById(R.id.buttonWelcomeReetest_Now);
        btnTestLater = findViewById(R.id.buttonWelcomeReetest_Later);

        btnTestNow.setOnClickListener(this);
        btnTestLater.setOnClickListener(this);

        String userName = sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME);
        String morningMessage = "Welcome ";
        String wishMessage = morningMessage + userName + "!";
        SpannableStringBuilder str = new SpannableStringBuilder(wishMessage);
        str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), morningMessage.length(),
                wishMessage.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_Title.setText(str);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonWelcomeReetest_Later:

//                Intent intents = new Intent(this, WelcomeActivity.class);
//                intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intents.putExtra("isFirstTime", isFirstTime);
//                startActivity(intents);

                Intent intents = new Intent(this, HomeActivity.class);
                intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intents.putExtra("isFirstTime", isFirstTime);
                startActivity(intents);
                finish();

                break;

            case R.id.buttonWelcomeReetest_Now:
                Intent intent = new Intent(context, ScheduleBloodTestActivity.class);
                //intent.putExtra("param", "First_Time");
                startActivity(intent);
                break;
            default:
        }
        finish();
    }

}
