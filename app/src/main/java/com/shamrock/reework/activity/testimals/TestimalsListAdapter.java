package com.shamrock.reework.activity.testimals;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.activity.BloodTestModule.adapter.ImageViewReportActivity;
import com.shamrock.reework.activity.FoodModule.adapter.AddMealIntoListAdapter;
import com.shamrock.reework.activity.recipeanalytics.RecipeAnalyticResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class
TestimalsListAdapter extends RecyclerView.Adapter<TestimalsListAdapter.MyHolder>
{

    Context context;

    ArrayList<TestimalDataClass> arylst_testimal_data;
    public TestimalsListAdapter(Context context, ArrayList<TestimalDataClass> arylst_testimal_data)
    {
        this.context = context;
        this.arylst_testimal_data=arylst_testimal_data;

    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_user,img_testominal;
        TextView txt_testomal_text,txt_name,txt_age,txt_test_pdf,txt_testomal_time;


        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            img_user=itemView.findViewById(R.id.img_user);
            txt_testomal_text=itemView.findViewById(R.id.txt_testomal_text);
            txt_testomal_time=itemView.findViewById(R.id.txt_testomal_time);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_age=itemView.findViewById(R.id.txt_age);
            img_testominal=itemView.findViewById(R.id.img_testominal);
            img_testominal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((arylst_testimal_data.get(getAdapterPosition()).getReportFilePath()!=null&&!arylst_testimal_data.get(getAdapterPosition()).getReportFilePath().isEmpty())){
                        Intent intent = new Intent(context, ImageViewReportActivity.class);
                        intent.putExtra("ImageLink", arylst_testimal_data.get(getAdapterPosition()).getReportFilePath());
                        context.startActivity(intent);
                    }


                }
            });
            txt_test_pdf=itemView.findViewById(R.id.txt_test_pdf);
            txt_test_pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arylst_testimal_data.get(getAdapterPosition()).getFileType().equalsIgnoreCase("2")){
                        if (arylst_testimal_data.get(getAdapterPosition()).getReportFilePath()!=null&&!arylst_testimal_data.get(getAdapterPosition()).getReportFilePath().isEmpty()){
                            Intent intent = new Intent(context, PdfViewerActivity.class);
                            intent.putExtra("pdfLink", arylst_testimal_data.get(getAdapterPosition()).getReportFilePath());
                            context.startActivity(intent);
                        }


                    }
                }
            });


        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public TestimalsListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_testimal_list, viewGroup, false);
        return new MyHolder(view);
    }
    public String time(String utcTime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("BST"));
        Date date = null;
        try {
            date = dateFormat.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        return simpleDateFormat.format(date);
    }

    @Override
    public void onBindViewHolder(@NonNull TestimalsListAdapter.MyHolder myHolder, int i)
    {







        try {
            myHolder.txt_name.setText(arylst_testimal_data.get(i).getReewokerName());
            if (arylst_testimal_data.get(i).getAge()!=null){
                myHolder.txt_age.setText("( Age : "+arylst_testimal_data.get(i).getAge()+")");

            }
            myHolder.txt_testomal_text.setText(arylst_testimal_data.get(i).getTestimonial());


            if (arylst_testimal_data.get(i).getTestimonialCreated()!=null&&!arylst_testimal_data.get(i).getTestimonialCreated().isEmpty()){
                myHolder.txt_testomal_time.setVisibility(View.VISIBLE);
                myHolder.txt_testomal_time.setText(time(arylst_testimal_data.get(i).getTestimonialCreated()));

            }else {
                myHolder.txt_testomal_time.setVisibility(View.GONE);

            }

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.defaultmedicine)
                    .error(R.drawable.defaultmedicine)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .priority(Priority.HIGH);
            if (arylst_testimal_data.get(i).getProfileImg()!=null){
                Glide.with(context)
                        .load(arylst_testimal_data.get(i).getProfileImg())
                        .apply(options.circleCrop())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(myHolder.img_user);
            }


            if (arylst_testimal_data.get(i).getFileType().equalsIgnoreCase("1")){
                myHolder.img_testominal.setVisibility(View.VISIBLE);

                RequestOptions optionss = new RequestOptions()
                        .placeholder(R.drawable.defaultmedicine)
                        .error(R.drawable.defaultmedicine)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .priority(Priority.HIGH);
                if (arylst_testimal_data.get(i).getReportFilePath()!=null){
                    Glide.with(context)
                            .load(arylst_testimal_data.get(i).getReportFilePath())

                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                            Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                               DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(myHolder.img_testominal);
                }

            }else {
                myHolder.img_testominal.setVisibility(View.GONE );

            }

            if (arylst_testimal_data.get(i).getFileType().equalsIgnoreCase("2")){
                myHolder.txt_test_pdf.setVisibility(View.VISIBLE);
                myHolder.txt_test_pdf.setText(arylst_testimal_data.get(i).getReportName());




            }else {
                myHolder.txt_test_pdf.setVisibility(View.GONE);

            }


        }catch (Exception e){
            e.printStackTrace();
        }

//        myHolder.txt_name.setText(arylst_testimal_data.get(i).getReewokerName());



    }

    @Override
    public int getItemCount()
    {
        return arylst_testimal_data.size();
    }


}
