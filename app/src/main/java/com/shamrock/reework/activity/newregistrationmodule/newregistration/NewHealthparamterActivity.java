package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.NewHealthparameterGoobActivity;

public class NewHealthparamterActivity extends AppCompatActivity {
    private Button buttonNext_Health_new;
    private String param="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_healthparamter);
        buttonNext_Health_new=findViewById(R.id.buttonNext_Health_new);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);


        param=getIntent().getStringExtra("param");
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText(R.string.title_health_parameters);
        //tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                //finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });



        buttonNext_Health_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (param.equalsIgnoreCase("From_Home")){
                    finish();
//                    startActivity(new Intent(NewHealthparamterActivity.this, NewHealthparameterGoobActivity.class));

                }else {
                    startActivity(new Intent(NewHealthparamterActivity.this, NewHealthparameterGoobActivity.class));

                }

            }
        });

    }
}
