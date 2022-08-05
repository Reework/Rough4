package com.shamrock.reework.activity.GFitActivity.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.shamrock.R;

public class GfitHomeActivity extends AppCompatActivity {
Button btn_next;
EditText etd_steps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gfit_home);
        TextView tvTitle=findViewById(R.id.tvTitle);

        tvTitle.setText("Google Fit");

        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next = (Button) findViewById(R.id.btn_next);
        etd_steps = (EditText) findViewById(R.id.etd_steps);

//        DecoView arcView = (DecoView)findViewById(R.id.dynamicArcView);
//        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
//                .setRange(0, 100, 100)
//                .setInitialVisibility(false)
//                .setLineWidth(32f)
//                .build());
//
////Create data series track
//        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
//                .setRange(0, 100, 0)
//                .setLineWidth(22f)
//                .build();
//
//        int series1Index = arcView.addSeries(seriesItem1);
//        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
//                .setDelay(1000)
//                .setDuration(2000)
//                .build());
//
//        arcView.addEvent(new DecoEvent.Builder(90).setIndex(series1Index).setDelay(4000).build());
//        arcView.addEvent(new DecoEvent.Builder(100).setIndex(series1Index).setDelay(8000).build());
//        arcView.addEvent(new DecoEvent.Builder(10).setIndex(series1Index).setDelay(12000).build());
//
//
//

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etd_steps.getText().toString().isEmpty()){
                    Toast.makeText(GfitHomeActivity.this, "please enter Steps", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent = new Intent(getApplicationContext(), GfitActivity.class);
                    intent.putExtra("step",etd_steps.getText().toString());
                    startActivity(intent);
                }
            }
        });



    }



}
