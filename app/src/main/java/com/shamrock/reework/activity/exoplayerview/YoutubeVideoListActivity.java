package com.shamrock.reework.activity.exoplayerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamrock.R;

public class YoutubeVideoListActivity extends AppCompatActivity {
    CardView card2,card1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_analysis);
        card2=findViewById(R.id.card2);
        card1=findViewById(R.id.card1);



        RelativeLayout img_play_video=findViewById(R.id.img_play_video);
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Recipe Video");
        ImageView imageView=findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(YoutubeVideoListActivity.this,ExoActivity.class);
                intent.putExtra("VideoID","sH-juTnjm-Y");
                startActivity(intent);
            }
        });

        RelativeLayout img_play_video1=findViewById(R.id.img_play_video1);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(YoutubeVideoListActivity.this,ExoActivity.class);
                intent.putExtra("VideoID","Q4kXLqUiSWI");
                startActivity(intent);

            }
        });

    }
}
