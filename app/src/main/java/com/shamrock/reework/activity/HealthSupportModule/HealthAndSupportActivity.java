package com.shamrock.reework.activity.HealthSupportModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.service.MyAnalysisService;
import com.shamrock.reework.activity.BCAModule.activity.MyBCAReportActivity;
import com.shamrock.reework.activity.BCAModule.adapter.MyBCAReportAdapter;
import com.shamrock.reework.activity.FaqActivity.ReeworkFAQActivity;
import com.shamrock.reework.activity.HealthSupportModule.modal.HealthSupportClassMain;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthAndSupportActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Utils utils;
    private MyAnalysisService service;
    Toolbar toolbar;
    CardView tvMobileNumber, tvEmailAdd;
    String mobNumberSave, emailSave;
    RadioButton rd_btn_Faq,rd_btn_NeedMoreHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_and_support);

        tvMobileNumber = findViewById(R.id.tvMobileNum);
        tvEmailAdd = findViewById(R.id.tvEmail);

        rd_btn_Faq=findViewById(R.id.rd_btn_faq);
        rd_btn_NeedMoreHelp=findViewById(R.id.rd_btn_needMoreHelp);

        utils = new Utils();
        sessionManager = new SessionManager(HealthAndSupportActivity.this);
        service = Client.getClient().create(MyAnalysisService.class);

        setToolBar();
        callToGetHealthSupportData();

        tvMobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dexter.withActivity(HealthAndSupportActivity.this)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobNumberSave));
                                startActivity(intent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                                showSettingsDialog();

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                            }


                        }).check();
            }
        });

        tvEmailAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailSave));
                startActivity(emailIntent);
            }
        });


        rd_btn_Faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthAndSupportActivity.this, ReeworkFAQActivity.class));
                finish();
            }
        });

    }

    private void callToGetHealthSupportData() {
        utils.showProgressbar(this);

        Call<HealthSupportClassMain> call = service.getHealthSupportData();
        call.enqueue(new Callback<HealthSupportClassMain>() {
            @Override
            public void onResponse(Call<HealthSupportClassMain> call, Response<HealthSupportClassMain> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    HealthSupportClassMain bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode().equals("1")) {
//                        tvMobileNumber.setText("Mobile no. : " + bcaResponse.getData().getMobile_No());
//                        tvEmailAdd.setText("Email : " + bcaResponse.getData().getEmail());
                        mobNumberSave = bcaResponse.getData().getMobile_No();
                        emailSave = bcaResponse.getData().getEmail();
                     //   Toast.makeText(HealthAndSupportActivity.this, bcaResponse.getData().getMobile_No(), Toast.LENGTH_SHORT).show();

                    } else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HealthSupportClassMain> call, Throwable t) {
                utils.hideProgressbar();

            }
        });
    }


    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Help & Support");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HealthAndSupportActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}