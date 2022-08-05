package com.shamrock.reework.activity.MyRecoachModule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListByTypeData;
import com.shamrock.reework.api.response.City;

import java.util.ArrayList;

public class ReecoachListNewAdapter extends RecyclerView.Adapter<ReecoachListNewAdapter.MyHolder>
{

    Context context;
    ArrayList<ReecoachListByTypeData> cityList;
    ReecoachSelecttener listener;

    public ReecoachListNewAdapter(Context context, ArrayList<ReecoachListByTypeData> cityList) {
        this.context = context;
        this.cityList = cityList;
        listener= (ReecoachSelecttener) context;
    }

    public interface ReecoachSelecttener
    {
        public void GetReecoachSelectPosition(int pos, ReecoachListByTypeData model);
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView reecocahctypetext,reecocahaddress;
        ImageView img_reecoachtype;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            reecocahctypetext = itemView.findViewById(R.id.reecocahctypetext);
            img_reecoachtype = itemView.findViewById(R.id.img_reecoachtype);
            reecocahaddress = itemView.findViewById(R.id.reecocahaddress);
        }

        @Override
        public void onClick(View v)
        {
            listener.GetReecoachSelectPosition(getAdapterPosition(), cityList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public ReecoachListNewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_reecoachtype_listname, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReecoachListNewAdapter.MyHolder myHolder, int i)
    {
        myHolder.reecocahctypetext.setText((cityList.get(i).getFirstName()+" "+cityList.get(i).getLastName()).replace("Reecoaches" +
                "",""));

        myHolder.reecocahaddress.setText(cityList.get(i).getAddress());



        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        if (!cityList.get(i).getImageUrl().isEmpty()){
            Glide.with(context)
                    .load(cityList.get(i).getImageUrl())
                    .apply(RequestOptions.circleCropTransform())

                    .error(R.drawable.ic_profile_pic_bg)
                    .into(myHolder.img_reecoachtype);
        }else {
            myHolder.img_reecoachtype.setImageResource(R.drawable.ic_profile_pic_bg);
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
