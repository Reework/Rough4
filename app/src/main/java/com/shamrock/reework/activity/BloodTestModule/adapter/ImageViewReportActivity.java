package com.shamrock.reework.activity.BloodTestModule.adapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.util.Utils;
import com.squareup.okhttp.internal.Util;

public class ImageViewReportActivity extends AppCompatActivity {
    ozaydin.serkan.com.image_zoom_view.ImageViewZoom img_report;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_report);
        img_report=findViewById(R.id.img_report);
        utils = new Utils();

        utils.showProgressbar(this);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);

        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Image");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });

        Glide.with(this)

                .load(getIntent().getStringExtra("ImageLink"))

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        utils.hideProgressbar();

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                        utils.hideProgressbar();
                        return false;
                    }
                })
//                                            .apply(options)
                .into(img_report);
    }
}
