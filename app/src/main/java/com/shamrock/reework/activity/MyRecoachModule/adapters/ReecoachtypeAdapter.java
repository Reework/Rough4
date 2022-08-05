package com.shamrock.reework.activity.MyRecoachModule.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.activity.ChangeReecoachActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachMasterTypeData;
import com.shamrock.reework.api.response.City;

import java.util.ArrayList;

public class ReecoachtypeAdapter extends RecyclerView.Adapter<ReecoachtypeAdapter.MyHolder>
{

    Context context;
    ArrayList<ReecoachMasterTypeData> cityList;
    ReecoaachtypeListener listener;

    public ReecoachtypeAdapter(Context context, ArrayList<ReecoachMasterTypeData> cityList) {
        this.context = context;
        this.cityList = cityList;
        listener= (ReecoaachtypeListener) context;
    }

    public interface ReecoaachtypeListener
    {
        public void GetTypePosition(int pos, ReecoachMasterTypeData model);
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView reecocahctypetext;
        ImageView img_reecoachtype;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            reecocahctypetext = itemView.findViewById(R.id.reecocahctypetext);
            img_reecoachtype = itemView.findViewById(R.id.img_reecoachtype);


        }

        @Override
        public void onClick(View v)
        {

            listener.GetTypePosition(getAdapterPosition(), cityList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public ReecoachtypeAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_reecoachtype_list, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReecoachtypeAdapter.MyHolder myHolder, int i)
    {
        myHolder.reecocahctypetext.setText((cityList.get(i).getReecoachType()).replace("Reecoaches" +
                "",""));
        if (i==0){

            myHolder.img_reecoachtype.setImageResource(R.drawable.celebrity);

        }
        if (i==3){

            myHolder.img_reecoachtype.setImageResource(R.drawable.inspiration);

        }

        if (i==1){
            myHolder.img_reecoachtype.setImageResource(R.drawable.trends);

        }
        if (i==2){
        myHolder.img_reecoachtype.setImageResource(R.drawable.popular);

    }
    }

    @Override
    public int getItemCount()
    {
        return cityList.size();
    }
   /* LayoutInflater inflter;

    public CityAdapter(Context context, List<City> countryList) {
        this.context = context;
        this.countryList = countryList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public City getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.simple_spinner_item, null);
        TextView names = view.findViewById(R.id.spinner_textView);
        names.setText(countryList.get(i).getCityName());
        return view;
    }*/
}
