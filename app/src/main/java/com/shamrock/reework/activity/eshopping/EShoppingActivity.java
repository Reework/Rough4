package com.shamrock.reework.activity.eshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.shamrock.R;

public class EShoppingActivity extends AppCompatActivity {
    private TextView tvTitle;
    private ViewFlipper vp_shooping;
    RadioButton rd_button_suppliment,rd_button_medicaldevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_shopping);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle=findViewById(R.id.tvTitle);
        vp_shooping=findViewById(R.id.vp_shooping);
        rd_button_medicaldevices=findViewById(R.id.rd_button_medicaldevices);
        rd_button_suppliment=findViewById(R.id.rd_button_suppliment);

        rd_button_suppliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vp_shooping.setDisplayedChild(0);
            }
        });
        rd_button_medicaldevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_shooping.setDisplayedChild(1);
            }
        });
        vp_shooping.setDisplayedChild(0);
        tvTitle.setText("E-Shopping");

    }
}
