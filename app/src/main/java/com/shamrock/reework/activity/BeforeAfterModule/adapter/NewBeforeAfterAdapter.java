package com.shamrock.reework.activity.BeforeAfterModule.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.common.TouchImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewBeforeAfterAdapter extends RecyclerView.Adapter<NewBeforeAfterAdapter.MyHolder> {
    Context context;
    //ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> afterDataResponses;
    ArrayList<String> list;
    //    BeforeAfterListener listener;
    ArrayList<String> beforetim;
    ArrayList<String> beforeID;

    public interface BeforeAfterListener {
        public void GetPositionData(int pos, boolean isDelete);
    }

    public NewBeforeAfterAdapter(Context context, ArrayList<String> list,
                                 ArrayList<String> beforetime, ArrayList<String> beforeID) {
        this.context = context;
        this.list = list;
        this.beforetim = beforetime;
        this.beforeID=beforeID;
    }

   /* public BeforeAfterAdapter(Context context, ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> afterDataResponses,
                              BeforeAfterListener listener)
    {
        this.context = context;
        this.afterDataResponses = afterDataResponses;
        this.listener = listener;
    }*/

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView,imgclose,imgMedicineDelete,imgMedicine;
        ProgressBar progressBar;
        //        TextView txt_ulaod_date,dateshow;
        TextView txt_ulaod_date,txt_ulaod_time;

        RelativeLayout removeRel;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);

            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            imageView = itemView.findViewById(R.id.ivBeforeAfter);
            txt_ulaod_date = itemView.findViewById(R.id.txt_ulaod_date);
            txt_ulaod_time = itemView.findViewById(R.id.txt_ulaod_time);
            imgMedicineDelete = itemView.findViewById(R.id.imgMedicineDelete);
            imgMedicine = itemView.findViewById(R.id.imgMedicine);
            imgMedicineDelete.setVisibility(View.GONE);
            imgMedicine.setVisibility(View.GONE);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.GetPositionData(getAdapterPosition(),false);
//
//                }
//            });
//            removeRel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(context, "Delete InProgress...... ", Toast.LENGTH_LONG).show();
//
//                    listener.GetPositionData(getAdapterPosition(),true);
//
//                }
//            });
            //delete = itemView.findViewById(R.id.imgMedicineDelete);
        }



    }


    public String formatDatesNew(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_before_after, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        //String beforImage = null, afterImage = null, url = null;
        String url = null;
        String uplaodDate = "";

       /* beforImage = afterDataResponses.get(i).getBeforeFilePath();
        afterImage = afterDataResponses.get(i).getAfterFilePath();*/

        url = list.get(i);
        uplaodDate = beforetim.get(i);
        final String id = beforeID.get(i);



        if (!TextUtils.isEmpty(url)) {
           /* RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .priority(Priority.HIGH);*/


            String abc[] = formatDatesNew(uplaodDate).split(" ");


            myHolder.txt_ulaod_date.setText(abc[0]);

            if(abc[2].equals("pm")) {
                myHolder.txt_ulaod_time.setText(abc[1] + " " + "PM");
            }else{
                myHolder.txt_ulaod_time.setText(abc[1] + " " + "AM");
            }


//            myHolder.txt_ulaod_time.setText(abc[1]+" "+abc[2]);

//            myHolder.dateshow.setText("Me on "+formatDatesNew(uplaodDate));
            final String finalUrls = url;

            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            myHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource) {
                            myHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(RequestOptions.errorOf(R.drawable.ic_profile_pic_bg))
                    .into(myHolder.imageView);







            /*Glide.with(context)
                    .load(beforImage).transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions.circleCropTransform()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .error(R.drawable.ic_profile_pic_bg))
                    .into(myHolder.imageView);*/

        }

       /* if (!TextUtils.isEmpty(afterImage))
        {
           *//* RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .priority(Priority.HIGH);*//*

            Glide.with(context)
                    .load(afterImage)
                    .listener(new RequestListener<Drawable>()
                    {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource)
                        {
                            myHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource)
                        {
                            myHolder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(myHolder.imageView);
        }*/

        /*if (!TextUtils.isEmpty(afterDataResponses.get(i).getBeforeFilePath()))
        {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .priority(Priority.HIGH);

            Glide.with(context)
                    .load(afterDataResponses.get(i).getBeforeFilePath())
                    //.apply(RequestOptions.circleCropTransform())
                    .apply(options)
                    .into(myHolder.imageView);
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
