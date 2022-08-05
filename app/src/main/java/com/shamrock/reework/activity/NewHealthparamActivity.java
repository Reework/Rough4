package com.shamrock.reework.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.shamrock.R;

public class NewHealthparamActivity extends AppCompatActivity {
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_healthparam);
        tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Health Parameters");
    }
}
