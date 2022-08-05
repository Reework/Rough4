package com.shamrock.reework.activity.spirituallibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.OnHealthCatoryClick;
import com.shamrock.reework.activity.spirituallibrary.listenres.OnVideoCLick;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;
import com.shamrock.reework.activity.spirituallibrary.pojo.SpiritualTypeData;

import java.util.ArrayList;

public class SpiritualVideoListAdapter extends RecyclerView.Adapter<SpiritualVideoListAdapter.MyHolder>
{
    Context context;
    ArrayList<ClsSpiritualData> list;
    OnVideoCLick onHealthCatoryClick;
    int selectedPosition=-1;
    boolean isFirstTime=true;
    public SpiritualVideoListAdapter(Context context, ArrayList<ClsSpiritualData> list)
    {
        this.context = context;
        this.list = list;
        onHealthCatoryClick= (OnVideoCLick) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        ImageView img_thumbnil;
        TextView txt_video_title;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            img_thumbnil = itemView.findViewById(R.id.img_thumbnil);
            txt_video_title = itemView.findViewById(R.id.txt_video_title);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_spiritaul_video, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i)
    {
        ClsSpiritualData model = list.get(i);
        myHolder.txt_video_title.setText(model.getTitle());




        if (model.getThumbnaiLink()!=null&&!model.getThumbnaiLink().isEmpty()){
            Glide.with(context)

                    .load(model.getThumbnaiLink())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            myHolder.img_thumbnil.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
//                                            .apply(options)
                    .into(myHolder.img_thumbnil);
        }else {
            myHolder.img_thumbnil.setBackgroundColor(context.getResources().getColor(R.color.line_grey_20));
        }





        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstTime=false;
                onHealthCatoryClick.getVideoLink(list.get(i).getVideoLink(),list.get(i).getTitle(),list.get(i).getDescription());
                selectedPosition=i;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
