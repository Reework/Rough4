package com.shamrock.reework.activity.UnfreezeModule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UnfreezeResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnfreezeActivity extends AppCompatActivity implements View.OnClickListener
{

    Context context;
    Toolbar toolbar;
    Typeface font;
    TextView tvUserName, tvClickhere;
    private SessionManager session;
    private Utils utils;
    UnfreezeService unfreezeService;
    int userId;
    boolean ISFromLogin;
    TextView txtnailno;


    @Override
    public void onBackPressed() {
        if (ISFromLogin){
            super.onBackPressed();
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfreeze);
        context = UnfreezeActivity.this;
        txtnailno=findViewById(R.id.txtnailno);
        init();
        setToolBar();
        findViews();
        ISFromLogin=getIntent().getBooleanExtra("ISFromLogin",false);
    }



    private void init()
    {
        session = new SessionManager(context);
        utils = new Utils();
        unfreezeService = Client.getClient().create(UnfreezeService.class);

        userId = session.getIntValue(SessionManager.KEY_USER_ID);
        String meesaghe=session.getStringValue("KEY_EMAIL")+"\n"+session.getStringValue("KEY_MOBILE");
        txtnailno.setText(meesaghe);
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Unfreeze");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        tvUserName = findViewById(R.id.textUnfreeze_Title);
        tvClickhere = findViewById(R.id.textUnfreeze_ClickHere);

        String user = session.getStringValue(SessionManager.KEY_USER_F_NAME);
        if (!TextUtils.isEmpty(user))
            tvUserName.setText("Hi, "+user);

        Spannable wordtoSpan = new SpannableString("Please contact Administrator for the same or click here to send the request");
        //wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, featurePercent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), 45, 55, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new UnderlineSpan(), 45, 55, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvClickhere.setText(wordtoSpan);

        tvClickhere.setOnClickListener(this);
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.textUnfreeze_ClickHere)
        {
            if (Utils.isNetworkAvailable(context))
            {
                callRequestForUnfreez();
            }
            else
                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
        }
    }

    private void callRequestForUnfreez()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userId);

        Call<UnfreezeResponse> call = unfreezeService.unfreezRequest(request);
        call.enqueue(new Callback<UnfreezeResponse>()
        {
            @Override
            public void onResponse(Call<UnfreezeResponse> call, Response<UnfreezeResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    UnfreezeResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "Request to unfreeze account has been sent successfully !", Toast.LENGTH_LONG).show();
                        clearTask();
                    }
                    else
                    {
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UnfreezeResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }

    public void clearTask()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }
}
