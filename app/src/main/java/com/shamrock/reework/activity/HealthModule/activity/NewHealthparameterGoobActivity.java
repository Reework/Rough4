package com.shamrock.reework.activity.HealthModule.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.lifestyleplanmodule.LifeStylePlanActivity;

public class NewHealthparameterGoobActivity extends AppCompatActivity {
    private ImageView imgViewNext_HPThankYou;

    private ImageView imgLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_healthparameter_goob);
        imgViewNext_HPThankYou=findViewById(R.id.imgViewNext_HPThankYou);
        imgLeft=findViewById(R.id.imgLeft);

        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Health Parameters");

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgViewNext_HPThankYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewHealthparameterGoobActivity.this, LifeStylePlanActivity.class);
//                intent.putExtra("UserDataReg",getIntent().getSerializableExtra("finalregdata"));

                if (getIntent().getStringExtra("FromWeb")!=null&&getIntent().getStringExtra("FromWeb").equalsIgnoreCase("true")){
                    intent.putExtra("FromWeb","true");
                }
                startActivity(intent);
            }
        });

    }
}
