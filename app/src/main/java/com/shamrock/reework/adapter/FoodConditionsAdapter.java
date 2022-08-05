package com.shamrock.reework.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.common.MedicalConditionData;

import java.util.ArrayList;

public class FoodConditionsAdapter extends RecyclerView.Adapter<FoodConditionsAdapter.MyHolder> {

    Context context;
    ArrayList<MedicalConditionData> cityList;
    FoodConditionListener listener;
    private boolean isFirstTime = true;
    int selectedPosition = -1;

    public interface FoodConditionListener {
        public void GetfoodCondition(ArrayList<MedicalConditionData> medicalcondition);
    }

    public FoodConditionsAdapter(Context context, ArrayList<MedicalConditionData> cityList, FoodConditionListener listener) {
        this.context = context;
        this.cityList = cityList;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        RelativeLayout rel_medicalcondition;
        ImageView img_select;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvName = itemView.findViewById(R.id.txt_medical);
            rel_medicalcondition = itemView.findViewById(R.id.rel_medicalcondition);
            img_select = itemView.findViewById(R.id.img_select);
        }

        @Override
        public void onClick(View v) {
            listener.GetfoodCondition(cityList);
        }
    }

    @NonNull
    @Override
    public FoodConditionsAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_medical, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodConditionsAdapter.MyHolder myHolder, final int i) {

        final MedicalConditionData model = cityList.get(i);
//        if (isFirstTime){
//            if (i==0){
//                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_done));
//                myHolder.rel_medicalcondition.setPadding(15,15,15,15);
//                myHolder.img_select.setVisibility(View.VISIBLE);
//
//            }else {
//                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_undone));
//                myHolder.rel_medicalcondition.setPadding(15,15,15,15);
//
//                myHolder.img_select.setVisibility(View.INVISIBLE);
//            }
//
//
//        }else {
//            if(model.isSelect()) {
//                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_done));
//                myHolder.rel_medicalcondition.setPadding(15,15,15,15);
//
//                myHolder.img_select.setVisibility(View.VISIBLE);
//            }
//            else{
//                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_undone));
//                myHolder.rel_medicalcondition.setPadding(15,15,15,15);
//
//                myHolder.img_select.setVisibility(View.INVISIBLE);
//            }


        if (isFirstTime){
            if (i==10){
                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_done));
                myHolder.rel_medicalcondition.setPadding(15,15,15,15);
                myHolder.img_select.setVisibility(View.VISIBLE);

            }else {
                model.setSelect(false);
                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_undone));
                myHolder.rel_medicalcondition.setPadding(15,15,15,15);

                myHolder.img_select.setVisibility(View.INVISIBLE);
            }

        }else {
            if (model.isSelect()) {
                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_done));
                myHolder.rel_medicalcondition.setPadding(15, 15, 15, 15);

                myHolder.img_select.setVisibility(View.VISIBLE);
            } else {
                myHolder.rel_medicalcondition.setBackground(context.getResources().getDrawable(R.drawable.bg_undone));
                myHolder.rel_medicalcondition.setPadding(15, 15, 15, 15);

                myHolder.img_select.setVisibility(View.INVISIBLE);
            }

        }







        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstTime=false;
                if (cityList.get(i).isSelect()){
                    cityList.get(i).setSelect(false);

                }else {
                    cityList.get(i).setSelect(true);

                }

                if(myHolder.getAdapterPosition()==10){
                    isFirstTime=true;

                    for(int i=1; i<cityList.size();i++){
                        cityList.get(i).setSelect(false);
                    }


                }else{
                    isFirstTime=false;
                    cityList.get(0).setSelect(false);
                }

                listener.GetfoodCondition(cityList);
                selectedPosition=i;
                notifyDataSetChanged();
            }
        });







        myHolder.tvName.setText(model.getMedicalconditionName());
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
