package com.shamrock.reework.activity.MyPlansModule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.dietprofile.DietProfileActivity;
import com.shamrock.reework.activity.foodguide.FoodGuideAdapter;

public class MyPlanDetailsActivity extends AppCompatActivity {
    RelativeLayout rel_myplan_details,rel_full_week_plan,rel_grocery_list,rel_diet_profile,
            rel_action_score,rel_food_guide;
    private String postDate;
    private String ShowMyPlanDate="";
    private int planIDTemp;
    private String temp_title="";
    private String DataID="";
    private String ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan_details);
        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("REEplan Details");
        rel_food_guide=findViewById(R.id.rel_food_guide);
        rel_action_score=findViewById(R.id.rel_action_score);
        rel_diet_profile=findViewById(R.id.rel_diet_profile);
        rel_grocery_list=findViewById(R.id.rel_grocery_list);
        rel_full_week_plan=findViewById(R.id.rel_full_week_plan);
        rel_myplan_details=findViewById(R.id.rel_myplan_details);
         planIDTemp= Integer.parseInt(getIntent().getStringExtra("planIDTemp"));
        postDate=getIntent().getStringExtra("SubmitDatemyPlan");
        ShowMyPlanDate=getIntent().getStringExtra("ShowMyPlanDate");
        temp_title=getIntent().getStringExtra("temp_title");
        DataID=getIntent().getStringExtra("DataID");
        ID=getIntent().getStringExtra("ID");
//        textView_Date.setText(getIntent().getStringExtra("ShowMyPlanDate"));

        rel_food_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPlanDetailsActivity.this, FoodGuideActivity.class));
            }
        });

        rel_action_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyPlanDetailsActivity.this,ActionScoreActivity.class);
//                intent.putExtra("DataID",sendId);
                intent.putExtra("DataID",DataID);
                startActivity(intent);
            }
        });
        rel_diet_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyPlanDetailsActivity.this, DietProfileActivity.class);
//                intent.putExtra("ID",sendId);
                intent.putExtra("ID",2);
                startActivity(intent);

            }
        });
        rel_grocery_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyPlanDetailsActivity.this,MyPlansActivity.class);
//                intent.putExtra("DataID",sendId);
                intent.putExtra("Glosary",true);

                intent.putExtra("SubmitDatemyPlan",postDate);
                intent.putExtra("ShowMyPlanDate",ShowMyPlanDate);
                intent.putExtra("planIDTemp",String.valueOf(planIDTemp));
                intent.putExtra("temp_title",temp_title);
                intent.putExtra("DataID",DataID);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });
        rel_full_week_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyPlanDetailsActivity.this,MyPlansActivity.class);
//                intent.putExtra("DataID",sendId);
                intent.putExtra("Weekplan",true);

                intent.putExtra("SubmitDatemyPlan",postDate);
                intent.putExtra("ShowMyPlanDate",ShowMyPlanDate);
                intent.putExtra("planIDTemp",String.valueOf(planIDTemp));
                intent.putExtra("temp_title",temp_title);
                intent.putExtra("DataID",DataID);
                intent.putExtra("ID",ID);
                startActivity(intent);

            }
        });

        rel_myplan_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyPlanDetailsActivity.this,MyPlansActivity.class);
//                intent.putExtra("DataID",sendId);
                intent.putExtra("detailsplan",true);
                intent.putExtra("SubmitDatemyPlan",postDate);
                intent.putExtra("ShowMyPlanDate",ShowMyPlanDate);
                intent.putExtra("planIDTemp",String.valueOf(planIDTemp));
                intent.putExtra("temp_title",temp_title);
                intent.putExtra("DataID",DataID);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });
    }
}