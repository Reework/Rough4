package com.shamrock.reework.activity.reecoach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListByTypeData;

import java.util.ArrayList;

public class ReecoachpathoQnlistAdapter extends BaseAdapter {

    Context context;
    ArrayList<ClsReecoachQnDetails> countryList;
    LayoutInflater inflter;


    public ReecoachpathoQnlistAdapter(Context context, ArrayList<ClsReecoachQnDetails> countryList) {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public ClsReecoachQnDetails getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        view = inflter.inflate(R.layout.row_reecoach_qn, null);
        TextView txt_reecoach_qn = view.findViewById(R.id.txt_reecoach_qn);
        txt_reecoach_qn.setText(countryList.get(i).getQuestion());


        return view;
    }
}
