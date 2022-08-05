package com.shamrock.reework.activity.BloodTestModule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.database.SessionManager;

public class Download_BloodTestReportActivity extends AppCompatActivity implements View.OnClickListener
{

    Context context;
    Toolbar toolbar;
    Typeface font;
    ImageView imgNext;
    LinearLayout linearLayout_DownloadReport;
    SessionManager sessionManager;
    TextView textViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_report);

        context = Download_BloodTestReportActivity.this;
        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        sessionManager = new SessionManager(context);
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
        tvTitle.setText("Pathology Test Report");
        //tvTitle.setTypeface(font);
        imgLeft.setVisibility(View.VISIBLE);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        linearLayout_DownloadReport = findViewById(R.id.linDownloadReport);
        imgNext = findViewById(R.id.imgViewNext_BloodTest_ThankYou);
        textViewUser = findViewById(R.id.textReport_HiUser);

        imgNext.setOnClickListener(this);
        linearLayout_DownloadReport.setOnClickListener(this);

        String userNAme = "Hi " + sessionManager.getStringValue(SessionManager.KEY_USER_F_NAME) + "!";
        textViewUser.setText(userNAme);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.linDownloadReport:
                // download report and open it
                break;

            case R.id.imgViewNext_BloodTest_ThankYou:

                /*
                * show this Next button only in First time.(If reeworker has not scheduled(at least one )
                * any previous blood tests)
                * */
                Intent intent = new Intent(this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
               // startActivity(new Intent(context, HomeActivity.class));
                break;
            default:
        }
    }
}
