package com.shamrock.reework.activity.MyRecoachModule.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;

import java.util.ArrayList;

public class ReecoachListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ReecoachListByTypeData> countryList;
    LayoutInflater inflter;

    boolean b;
    public ReecoachListAdapter(Context context, ArrayList<ReecoachListByTypeData> countryList, boolean b) {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
        this.b=b;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public ReecoachListByTypeData getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        view = inflter.inflate(R.layout.row_reecoach_list, null);
        TextView txt_reecoach_name = view.findViewById(R.id.txt_reecoach_name);
        TextView txt_reecoach_address = view.findViewById(R.id.txt_reecoach_address);
        ImageView img_reecoach_image = view.findViewById(R.id.img_reecoach_image);
        txt_reecoach_name.setText(countryList.get(i).getFirstName()+" "+countryList.get(i).getLastName());
        RatingBar rate_recoach=view.findViewById(R.id.rate_recoach);
        rate_recoach.setRating(countryList.get(i).getRating());
        txt_reecoach_address.setText(countryList.get(i).getAddress());

        if (b){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.pathologist)
                    .error(R.drawable.pathologist)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);
            Glide.with(context)
                    .load(countryList.get(i).getImageUrl())
                    .apply(options)
                    .apply(RequestOptions.circleCropTransform())

                    .into(img_reecoach_image);

        }else {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);
            Glide.with(context)
                    .load(countryList.get(i).getImageUrl())
                    .apply(options)
                    .into(img_reecoach_image);
        }


        return view;
    }
}
