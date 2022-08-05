package com.shamrock.reework.activity.FoodModule.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.api.response.FoodSnippingResp;
import com.shamrock.reework.common.TouchImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FoodSnippingAdapter extends RecyclerView.Adapter<FoodSnippingAdapter.MyHolder>
{
    Context context;
    List<FoodSnippingResp.Datum> list;
    public FoodSnippingAdapter(Context context, List<FoodSnippingResp.Datum> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        ImageView ivRecipe;
        TextView txt_food_snap_time;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            ivRecipe = itemView.findViewById(R.id.ivFood);
            txt_food_snap_time = itemView.findViewById(R.id.txt_food_snap_time);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_food_snipping, viewGroup, false);
        

        return new MyHolder(view);
    }

    public String formatDatesNew(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        FoodSnippingResp.Datum model = list.get(i);

        final String foodImageUrl = (String) model.getFoodSnappingPath();

//        myHolder.txt_food_snap_time.setText(model.getUploadedOn());
        myHolder.txt_food_snap_time.setText(formatDatesNew(model.getUploadedOn()));


        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);*/
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.food_default)
                .error(R.drawable.food_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(foodImageUrl)
                .apply(options)
                .into(myHolder.ivRecipe);


        myHolder.ivRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context,R.style.CustomDialog);

                dialog.setContentView(R.layout.dailg_fill_before_after);
                dialog.setCancelable(true);
                TouchImageView img_full_screen=dialog.findViewById(R.id.img_full_screen);
                TextView txt_medicine_name=dialog.findViewById(R.id.txt_medicine_name);
                txt_medicine_name.setSelected(true);
                ImageView img_close_dailog=dialog.findViewById(R.id.img_close_dailog);
                final RelativeLayout rel_med_header=dialog.findViewById(R.id.rel_med_header);
                img_close_dailog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Glide.with(context)

                        .load(foodImageUrl)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                rel_med_header.setVisibility(View.VISIBLE);
                                return false;
                            }
                        })
//                                            .apply(options)
                        .into(img_full_screen);





                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static int getScreenWidth()
    {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

}
