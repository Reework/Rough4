package com.shamrock.reework.reecoachmodule.adapters;

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
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListByTypeData;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsReeeworklistData;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsReeworkListMain;

import java.util.ArrayList;

public class ReeworkerlistListAdapter extends BaseAdapter {

    Context context;
    ArrayList<ClsReeeworklistData> countryList;
    LayoutInflater inflter;


    public ReeworkerlistListAdapter(Context context, ArrayList<ClsReeeworklistData> countryList) {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public ClsReeeworklistData getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        view = inflter.inflate(R.layout.row_reeworker_list_new, null);
        TextView txt_reeworker_name = view.findViewById(R.id.txt_reecoach_name);
        TextView txt_reework_address = view.findViewById(R.id.txt_reecoach_address);
        ImageView img_reework_image = view.findViewById(R.id.img_reecoach_image);


        txt_reeworker_name.setText(countryList.get(i).getFullName());

        if (countryList.get(i).getAddress()!=null&&!countryList.get(i).getAddress().isEmpty()){
            txt_reework_address.setVisibility(View.VISIBLE);
            txt_reework_address.setText(countryList.get(i).getAddress());

        }else {
            txt_reework_address.setVisibility(View.GONE);

        }
//        txt_reecoach_age.setText("Age : "+countryList.get(i).getAge()+" years");

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(countryList.get(i).getProfileImg())
                .apply(options)
                .into(img_reework_image);

        return view;
    }
}
