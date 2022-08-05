package com.shamrock.reework.activity.LoginModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shamrock.R;

public class ChooseLoginActivity extends AppCompatActivity {
    private Button btn_login_reework,btn_login_reecoach;
    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            //super.onBackPressed();
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                    finish();
                }
            }, 3 * 1000);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);

        btn_login_reecoach=findViewById(R.id.btn_login_reecoach);
        btn_login_reework=findViewById(R.id.btn_login_reework);
        btn_login_reework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseLoginActivity.this, LoginActivity.class);
                intent.putExtra("ISReework",true);
                startActivity(intent);

            }
        });

        btn_login_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseLoginActivity.this, LoginActivity.class);
                intent.putExtra("ISReework",false);
                startActivity(intent);
            }
        });
    }
}
