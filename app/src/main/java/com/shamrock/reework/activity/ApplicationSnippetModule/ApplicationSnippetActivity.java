package com.shamrock.reework.activity.ApplicationSnippetModule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.SubscriptionModule.activity.SubscriptionActivity;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.activity.WelcomeModule.WelcomeActivity;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationSnippetActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Toolbar toolbar;
    Typeface font;
    ImageView imgNext;
    TextView textReengage, textReeases, textReeScore, textReevaluate,
            textReTest, textGetRe, textCounce, textAssesment, textAssign,
            textGetCuReplan, textReeports, textReengageStartLog,
            textReVital, textReFocus, textReestore;

    UnfreezeService unfreezeService;
    Utils utils;
    private SessionManager sessionManager;
    private int userId;

    RecyclerView recler_snippet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_snippet);
        recler_snippet=findViewById(R.id.recler_snippet);
        context = ApplicationSnippetActivity.this;
        unfreezeService = Client.getClient().create(UnfreezeService.class);
        sessionManager=new SessionManager(this);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        utils = new Utils();
        sessionManager = new SessionManager(context);
        init();
        setToolBar();
        findViews();
        callForUsrFreezStatus();

    }

    private void callForUsrFreezStatus()
    {
        utils.showProgressbar(context);


        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userId);

        Call<ClsSnippetPojo> call = unfreezeService.getSnippetMessage();
        call.enqueue(new Callback<ClsSnippetPojo>()
        {
            @Override
            public void onResponse(Call<ClsSnippetPojo> call, Response<ClsSnippetPojo> response) {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK) {

                    ArrayList<SnippetData> arylst_snippet=response.body().getData();


                    ClsSnippetListAdapter adapter=  new ClsSnippetListAdapter(ApplicationSnippetActivity.this, arylst_snippet);
                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(ApplicationSnippetActivity.this);
                    recler_snippet.setLayoutManager(layoutManager1);
                    recler_snippet.setItemAnimator(new DefaultItemAnimator());
                    recler_snippet.setAdapter(adapter);


                }

            }
            @Override
            public void onFailure(Call<ClsSnippetPojo> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }


    private void init() {

    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Application Snippet");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews() {
        imgNext = findViewById(R.id.imgViewNext_ApplicationSnippet);
        imgNext.setOnClickListener(this);

        textReengage = findViewById(R.id.textAppSnip_Reengage);
        textReeases = findViewById(R.id.textAppSnip_Reeases);
        textReeScore = findViewById(R.id.textAppSnip_ReeScore);
        textReevaluate = findViewById(R.id.textAppSnip_Reevaluate);
        textReTest = findViewById(R.id.textAppSnip_ReTest);
        textGetRe = findViewById(R.id.textAppSnip_GetRe);
        textCounce = findViewById(R.id.textAppSnip_Counce);
        textAssesment = findViewById(R.id.textAppSnip_Assesment);
        textAssign = findViewById(R.id.textAppSnip_Assign);
        textGetCuReplan = findViewById(R.id.textAppSnip_GetCuReplan);
        textReeports = findViewById(R.id.textAppSnip_Reeports);
        textReengageStartLog = findViewById(R.id.textAppSnip_ReengageStartLog);
        textReVital = findViewById(R.id.textAppSnip_ReVital);
        textReFocus = findViewById(R.id.textAppSnip_ReFocus);
        textReestore = findViewById(R.id.textAppSnip_Reestore);

        String strMessage = getResources().getString(R.string.app_snippet_reengage);
        int INT_START = 0;
        int INT_END = 10;
        SpannableStringBuilder str = new SpannableStringBuilder(strMessage);
        str.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), INT_START,
                    INT_END, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReengage.setText(str);

        String strMessage2 = getResources().getString(R.string.app_snippet_reeasses);
        SpannableStringBuilder str2 = new SpannableStringBuilder(strMessage2);
        str2.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReeases.setText(str2);

        String strMessage3 = getResources().getString(R.string.app_snippet_reescore);
        SpannableStringBuilder str3 = new SpannableStringBuilder(strMessage3);
        str3.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReeScore.setText(str3);

        String strMessage4 = getResources().getString(R.string.app_snippet_reeevaluate);
        SpannableStringBuilder str4 = new SpannableStringBuilder(strMessage4);
        str4.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReevaluate.setText(str4);

        String strMessage5 = getResources().getString(R.string.app_snippet_reetest);
        SpannableStringBuilder str5 = new SpannableStringBuilder(strMessage5);
        str5.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReTest.setText(str5);

        String strMessage6 = getResources().getString(R.string.app_snippet_reeports);
        SpannableStringBuilder str6 = new SpannableStringBuilder(strMessage6);
        str6.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReeports.setText(str6);

        String strMessage7 = getResources().getString(R.string.app_snippet_reengage_start);
        SpannableStringBuilder str7 = new SpannableStringBuilder(strMessage7);
        str7.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReengageStartLog.setText(str7);

        String strMessage8 = getResources().getString(R.string.app_snippet_reevitalise);
        SpannableStringBuilder str8 = new SpannableStringBuilder(strMessage8);
        str8.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReVital.setText(str8);

        String strMessage9 = getResources().getString(R.string.app_snippet_reefocus);
        SpannableStringBuilder str9 = new SpannableStringBuilder(strMessage9);
        str9.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReFocus.setText(str9);

        String strMessage10 = getResources().getString(R.string.app_snippet_reestore);
        SpannableStringBuilder str10 = new SpannableStringBuilder(strMessage10);
        str10.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textReestore.setText(str10);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgViewNext_ApplicationSnippet:
                startActivity(new Intent(context, SubscriptionActivity.class));
                break;
            default:
        }

    }
}
