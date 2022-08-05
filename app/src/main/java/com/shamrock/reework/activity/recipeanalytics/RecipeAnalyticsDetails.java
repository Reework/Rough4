package com.shamrock.reework.activity.recipeanalytics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.DecimalFormat;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class RecipeAnalyticsDetails extends AppCompatActivity {
    TextView txt_carb_value;
    TextView txt_fat_value;
    TextView txt_fibre_value;
    TextView txt_protin_value;
    RecipeAnalyticResult recipeAnalyticResult;
    TextView txt_description;
    ImageView img_add_recipe;
    TextView txt_caloroes;
    TextView txt_recipe;
    ImageView img_close_food;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_analytics_details);
        txt_description=findViewById(R.id.txt_description);
        findViewById(R.id.img_close_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0,0);

                finish();
            }
        });
        txt_recipe=findViewById(R.id.txt_recipe);
        img_add_recipe=findViewById(R.id.img_add_recipe);
        txt_protin_value=findViewById(R.id.txt_protin_value);
        txt_fibre_value=findViewById(R.id.txt_fibre_value);
        txt_fat_value=findViewById(R.id.txt_fat_value);
        txt_carb_value=findViewById(R.id.txt_carb_value);
        txt_caloroes=findViewById(R.id.txt_caloroes);
        if (getIntent().getSerializableExtra("Data")!=null){
            recipeAnalyticResult= (RecipeAnalyticResult) getIntent().getSerializableExtra("Data");
            txt_caloroes.setText("Calories : "+recipeAnalyticResult.getCalories()+ " kcal");
            txt_recipe.setText(recipeAnalyticResult.getItemName());
            if (isValidContextForGlide(RecipeAnalyticsDetails.this)) {
                Glide.with(RecipeAnalyticsDetails.this)
                        .load(recipeAnalyticResult.getImage())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(img_add_recipe);
            }
            txt_description.setText(recipeAnalyticResult.getDescription());
            if (recipeAnalyticResult.getProtein()!=null){


                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                double showschedulestr = Double.parseDouble(recipeAnalyticResult.getProtein());
                String protin = decimalFormat.format(showschedulestr);

                txt_protin_value.setText(protin);

            }
            if (recipeAnalyticResult.getCarbs()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                double showschedulestr = Double.parseDouble(recipeAnalyticResult.getCarbs());
                String protin = decimalFormat.format(showschedulestr);

                txt_carb_value.setText(protin);

//                txt_carb_value.setText(recipeAnalyticResult.getCarbs());

            }
            if (recipeAnalyticResult.getFibre()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                double showschedulestr = Double.parseDouble(recipeAnalyticResult.getFibre());
                String protin = decimalFormat.format(showschedulestr);

                txt_fibre_value.setText(protin);
//                txt_fibre_value.setText(recipeAnalyticResult.getFibre());

            }
            if (recipeAnalyticResult.getFat()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                double showschedulestr = Double.parseDouble(recipeAnalyticResult.getFat());
                String protin = decimalFormat.format(showschedulestr);

//                txt_fibre_value.setText(protin);
                txt_fat_value.setText(protin);

            }
        }


     
    }
}
