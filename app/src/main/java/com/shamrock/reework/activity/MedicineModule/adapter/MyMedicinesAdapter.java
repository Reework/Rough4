package com.shamrock.reework.activity.MedicineModule.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.common.TouchImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class MyMedicinesAdapter extends BaseAdapter
{

    public interface MedicineListListener {
        void updateMedicine(int position, MyMedicine myMedicine);
        void deleteMedicine(int position, MyMedicine myMedicine);
    }

    private static final String SEPARATOR = " | ";
    Context mContext;
    ArrayList<MyMedicine> list;
    private static LayoutInflater inflater = null;

    public MyMedicinesAdapter(Context context, ArrayList<MyMedicine> subscriptionFeatureArrayList)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MyMedicine getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_my_medicines_new, null);

        TextView title = vi.findViewById(R.id.text_MedicineName);
        TextView days = vi.findViewById(R.id.textMedicineDays);
        TextView time = vi.findViewById(R.id.textMedicineTime);
        TextView edit = vi.findViewById(R.id.imgMedicineEdit);
        TextView delete = vi.findViewById(R.id.imgMedicineDelete);
        LinearLayout ll_meda = vi.findViewById(R.id.ll_meda);
//        if (i==0){
//            ll_meda.setBackgroundColor(mContext.getResources().getColor(R.color.material_deep_teal_20));
//
//        }
//        if (i==1){
//            ll_meda.setBackgroundColor(mContext.getResources().getColor(R.color.composite_edit_text_error_color));
//
//        }
//        if (i==2){
//            ll_meda.setBackgroundColor(mContext.getResources().getColor(R.color.success_stroke_color));
//
//        }
//        if (i==3){
//            ll_meda.setBackgroundColor(mContext.getResources().getColor(R.color.material_deep_teal_20));
//
//        }
//        if (i==4){
//            ll_meda.setBackgroundColor(mContext.getResources().getColor(R.color.composite_edit_text_error_color));
//
//        }
//        if (i==5){
//            ll_meda.setBackgroundColor(mContext.getResources().getColor(R.color.success_stroke_color));
//
//        }
        final ImageView img_medicine_logo = vi.findViewById(R.id.img_medicine_logo);

        MyMedicine song = list.get(i);
        final String featureName = song.getMedName();
        String strDays = song.getMedDays();
        String strFreq = song.getMedFreq();
        final String path = song.getPath();
        img_medicine_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!path.isEmpty()){
                    final Dialog dialog=new Dialog(mContext,R.style.CustomDialog);

                    dialog.setContentView(R.layout.dailg_fill);
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
                    txt_medicine_name.setText(featureName);
                    Glide.with(mContext)

                            .load(path)
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

//
            }
        });




        Glide.with(mContext)

                .load(path)
                .placeholder(R.drawable.thumb)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
//                                            .apply(options)
//                .apply(RequestOptions.circleCropTransform())
                .into(img_medicine_logo);



        String[] daysList = strDays.split(",");
        StringBuilder sb = new StringBuilder();
        for (String ss : daysList)
        {

            try {
                int ssnew= Integer.parseInt(ss);

                int newData=ssnew-1;

                String newmainData=String.valueOf(newData);

                sb.append(newmainData);
                sb.append(SEPARATOR);
            } catch (NumberFormatException e) {
                e.printStackTrace();



                sb.append(ss);
                sb.append(SEPARATOR);
            }
           /* sb.append(""+DaysUtils.fromInteger(Integer.parseInt(ss)));
            sb.append(SEPARATOR);*/
        }
        String medDays = sb.substring(0, sb.length() - 3);    //remove last comma and return commaSeparatedIds

        title.setText(featureName);

        showdays(strDays,days);

//        days.setText(medDays);
        time.setText("Frequency : "+strFreq);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Edit " + i, Toast.LENGTH_SHORT).show();
                if (mContext instanceof MedicineListListener) {

                    ((MedicineListListener) mContext).updateMedicine(i, list.get(i));
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "Delete = " + i, Toast.LENGTH_SHORT).show();
                if (mContext instanceof MedicineListListener) {
                    ((MedicineListListener) mContext).deleteMedicine(i, list.get(i));
                }
            }
        });
        vi.setTag(i);

        return vi;
    }
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    private void showdays(String medDays, TextView days) {
        try {
            String str = medDays;
            ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(str.split(",")));

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < elephantList.size(); i++) {


                if (elephantList.get(i).toString().trim().equalsIgnoreCase("2")) {
                    stringBuilder.append("Sun,");
                }

                if (elephantList.get(i).toString().trim().equalsIgnoreCase("3")) {

                    stringBuilder.append("Mon,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("4")) {
                    stringBuilder.append("Tue,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("5")) {

                    stringBuilder.append("Wed,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("6")) {
                    stringBuilder.append("Thu,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("7")) {
                    stringBuilder.append("Fri,");

                }
                if (elephantList.get(i).toString().trim().equalsIgnoreCase("8")) {
                    stringBuilder.append("Sat,");

                }
            }
            days.setSelected(true);
            days.setText("Days : "+removeLastChar(stringBuilder.toString()));
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void removeItem(int position)
    {
        try {
            list.remove(position);
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
