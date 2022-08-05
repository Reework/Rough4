package com.shamrock.reework.activity.FoodModule.adapter;

import android.app.TimePickerDialog;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.EditMealActivity;
import com.shamrock.reework.activity.FoodModule.fragment.UpdateMealitemFoodDialogFragmet;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.FoodModule.service.OnUpdateFoodQuantity;
import com.shamrock.reework.api.response.FoodListByMealType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EditMealIntoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnUpdateFoodQuantity, TimePickerDialog.OnTimeSetListener {
    private static final int CITY_TYPE = 1;
    private static final int ROW_TYPE = 2;
    private static final int FOOTER_TYPE = 3;
    private List<FoodListByMealType.Datum> mList;
    double quantityCount=0.0;
    String mAction;
    OnMealRemoveListner listner;
    private Context context;
    private TimePickerDialog timepickerdialog;
    RecyclerView.ViewHolder viewHolder;

    EditMealActivity editMealActivity;
    int positionMeal=0;
    ArrayList<FoodUnitMasterData> foodUnitMasterDataArrayList;
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void geteditMealData(String uomID, double valueingrma, double quantity, String adapterPos) {

        if (quantityCount < 1000) {
                    quantityCount = quantity;
                    mList.get(Integer.parseInt(adapterPos)).setQuantity(quantityCount);
                    if (mList.get(Integer.parseInt(adapterPos)).getSingleCal()!=null){
                        if (!mList.get(Integer.parseInt(adapterPos)).getSingleCal().isEmpty()){
                            double singlecal=Double.parseDouble(mList.get(Integer.parseInt(adapterPos)).getSingleCal());
                            double newCal=singlecal*quantityCount;
                            DecimalFormat decimalFormat=new DecimalFormat("0.00");
                            mList.get(Integer.parseInt(adapterPos)).setCalories(String.valueOf(decimalFormat.format(newCal)));
                            mList.get(Integer.parseInt(adapterPos)).setUomId(Integer.parseInt(uomID));
                            mList.get(Integer.parseInt(adapterPos)).setValueInGram(valueingrma);




                            notifyDataSetChanged();
                        }
                    }
                }
            }




    public interface  OnMealRemoveListner{
        public void onRemove(int position,FoodListByMealType.Datum model,boolean isRemove);
    }
    public EditMealIntoListAdapter(Context context, List<FoodListByMealType.Datum> list, String action, EditMealActivity editMealActivity, ArrayList<FoodUnitMasterData> foodUnitMasterDataArrayList) {
        this.mList = list;
        this.listner=(OnMealRemoveListner)context;
        this.mAction = action;
        this.context=context;
        this.editMealActivity=editMealActivity;
        this.foodUnitMasterDataArrayList=foodUnitMasterDataArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_meal_row, parent, false);
        return new RowViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (mList != null){
            this.viewHolder=holder;
            positionMeal=position;

            try {


                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_home_food)
                        .error(R.drawable.ic_home_food)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .priority(Priority.HIGH);
                if (mList.get(position).getRecipeImagePath()!=null){
                    Glide.with(context)
                            .load(mList.get(position).getRecipeImagePath())
                            .apply(options.circleCrop())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                            Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                               DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(((RowViewHolder) holder).mImage);
                }


            }catch (Exception e){
                e.printStackTrace();
            }



            if (mAction != null && mAction.equalsIgnoreCase("AddAction")) {
                ((RowViewHolder) holder).ivRemove.setVisibility(View.GONE);
                ((RowViewHolder) holder).ivAdd.setVisibility(View.GONE);
                ((RowViewHolder) holder).cancle.setVisibility(View.VISIBLE);
                ((RowViewHolder) holder).txtQty.setVisibility(View.VISIBLE);
            } else {
                ((RowViewHolder) holder).ivRemove.setVisibility(View.GONE);
                ((RowViewHolder) holder).ivAdd.setVisibility(View.GONE);
                ((RowViewHolder) holder).cancle.setVisibility(View.VISIBLE);
                ((RowViewHolder) holder).txtQty.setVisibility(View.GONE);
            }

        if (mList.get(position).getRecipeName() != null) {
            ((RowViewHolder) holder).mTitle.setText(mList.get(position).getRecipeName());
        }

        if (mList.get(position).getQuantity() != 0.0) {


            String strqty= String.valueOf(mList.get(position).getQuantity());
            int startindex=strqty.length()-2;
            int endindex=strqty.length()-1;
            String[] strNewQty=strqty.split("\\.");
            boolean isFound=false;


            if (strNewQty[1].equals("0")){
                isFound=true;

            }
            if (isFound){
                isFound=false;
//            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
//                ((AddMealIntoListAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
                ((RowViewHolder) holder).mQuantity.setText("" +strNewQty[0].toString());


            }else {
                ((RowViewHolder) holder).mQuantity.setText("" + mList.get(position).getQuantity());
            }




        }


        if (mList.get(position).getCalories() != null) {
            DecimalFormat decimalFormat=new DecimalFormat("0.00");
            double valuegram=mList.get(position).getValueInGram();
            if (valuegram==0){
                valuegram=1.0;
            }else {
                valuegram=mList.get(position).getValueInGram();
            }


            if (mAction != null && mAction.equalsIgnoreCase("AddAction")){

                            ((RowViewHolder) holder).mCalories.setText(String.valueOf(decimalFormat.format((Double.parseDouble(mList.get(position).getCalories())*mList.get(position).getQuantity())*valuegram)));

            }else {
                Log.d("sunitt",mList.get(position).getCalories());
                ((RowViewHolder) holder).mCalories.setText(String.valueOf(decimalFormat.format(Double.parseDouble(mList.get(position).getCalories()))));

            }
        }

            if (mList.get(position).getIntakeTime() != null) {
                ((RowViewHolder) holder).textmealtime.setText(mList.get(position).getIntakeTime());
            }

        ((RowViewHolder) holder).cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onRemove(position, mList.get(position), true);
            }
        });
        ((RowViewHolder) holder).ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityCount = mList.get(position).getQuantity();
                if (quantityCount > 0.25) {
                    quantityCount = quantityCount - 0.25;
                    ((RowViewHolder) holder).mQuantity.setText(String.valueOf(quantityCount));
                    mList.get(position).setQuantity(quantityCount);
                    if(mList.get(position).getSingleCal()!=null){
                        if (!mList.get(position).getSingleCal().isEmpty()){

                            double singlecal=Double.parseDouble(mList.get(position).getSingleCal());
                            double newCal=singlecal*quantityCount;

                            DecimalFormat decimalFormat=new DecimalFormat("0.00");

                            mList.get(position).setCalories(String.valueOf(decimalFormat.format(newCal)));
                            notifyDataSetChanged();
                        }



                    }




                }
            }
        });

            ((RowViewHolder) holder).textmealtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    editMealActivity.showtimeclock();


                }
            });


        ((RowViewHolder) holder).ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityCount = mList.get(position).getQuantity();
                if (quantityCount < 1000) {
                    quantityCount = quantityCount + 0.25;
                    ((RowViewHolder) holder).mQuantity.setText(String.valueOf(quantityCount));
                    mList.get(position).setQuantity(quantityCount);
                    if (mList.get(position).getSingleCal()!=null){
                        if (!mList.get(position).getSingleCal().isEmpty()){
                            double singlecal=Double.parseDouble(mList.get(position).getSingleCal());
                            double newCal=singlecal*quantityCount;
                            DecimalFormat decimalFormat=new DecimalFormat("0.00");
                            mList.get(position).setCalories(String.valueOf(decimalFormat.format(newCal)));
                            notifyDataSetChanged();
                        }
                    }



                }
            }
        });
    }
    }

    public void updateData(String lsTimeFrom){
        ((RowViewHolder) viewHolder).textmealtime.setText(String.valueOf(lsTimeFrom));
        mList.get(positionMeal).setIntakeTime(lsTimeFrom);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public  class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mCalories, mQuantity,cancle,txtQty,textmealtime;
        private ImageView mImage,ivRemove,ivAdd;


        public RowViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textAllMealRowName);
            mImage = itemView.findViewById(R.id.imgAllMeal_item_image);
            mQuantity = itemView.findViewById(R.id.textAllMealQty);
            mCalories = itemView.findViewById(R.id.textAllMealCalories);
            cancle = itemView.findViewById(R.id.cancle);
            ivRemove = itemView.findViewById(R.id.ivRemove);
            ivAdd = itemView.findViewById(R.id.ivAdd);
            txtQty = itemView.findViewById(R.id.txtQty);
            textmealtime = itemView.findViewById(R.id.textmealtime);
            mQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UpdateMealitemFoodDialogFragmet addFoodDialogFragmet=new UpdateMealitemFoodDialogFragmet(EditMealIntoListAdapter.this);
                    Bundle bundle=new Bundle();
                    bundle.putString("imagepath",mList.get(getAdapterPosition()).getRecipeImagePath());
                    bundle.putString("meal_name",mList.get(getAdapterPosition()).getRecipeName());
                    bundle.putString("meal_type", (String) mList.get(getAdapterPosition()).getMealType());
                    bundle.putSerializable("nutrionData",mList.get(getAdapterPosition()));
                    bundle.putSerializable("COUNTRY_LIST", foodUnitMasterDataArrayList);
                    bundle.putSerializable("quantity",String.valueOf(mList.get(getAdapterPosition()).getQuantity()));
                    bundle.putSerializable("uom",String.valueOf(mList.get(getAdapterPosition()).getUomId()));
                    Log.d("uomid", String.valueOf(mList.get(getAdapterPosition()).getUomId()));
                    bundle.putString("adapterposition",String.valueOf(getAdapterPosition()));

                    addFoodDialogFragmet.setArguments(bundle);


                    addFoodDialogFragmet.show(editMealActivity.getSupportFragmentManager(), addFoodDialogFragmet.getTag());

                    return;




//                    addData(getAdapterPosition());



                }
            });
        }
    }

//    private void addData(int getAdapterPosition()) {
//        final Dialog dialog=new Dialog(editMealActivity,R.style.CustomDialog);
//        dialog.setContentView(R.layout.lay_quantitiy);
//        ImageView img_quantity_close=dialog.findViewById(R.id.img_quantity_close);
//        img_quantity_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        final EditText edt_quantity=dialog.findViewById(R.id.edt_quantity);
//        try {
//            String strqty = String.valueOf(mList.get(getAdapterPosition()).getQuantity());
//            String[] strNewQty = strqty.split("\\.");
//            boolean isFound = false;
//
//
//            if (strNewQty[1].equals("0")) {
//                isFound = true;
//
//            }
//            if (isFound) {
//                isFound = false;
////            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
//                edt_quantity.setText("" + strNewQty[0].toString());
//
//            } else {
//                edt_quantity.setText(String.valueOf(mList.get(getAdapterPosition()).getQuantity()));
//            }
//
//        }catch (Exception e){
//
//            edt_quantity.setText(String.valueOf(mList.get(getAdapterPosition()).getQuantity()));
//
//        }
//        Button btn_submit_quantity=dialog.findViewById(R.id.btn_submit_quantity);
//        btn_submit_quantity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (edt_quantity.getText().toString().isEmpty()){
//                    Toast.makeText(editMealActivity, "Please enter valid quantity", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (Double.parseDouble(edt_quantity.getText().toString())<0){
//                    Toast.makeText(editMealActivity, "Please enter valid quantity", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                dialog.dismiss();
//
//                quantityCount = mList.get(getAdapterPosition()).getQuantity();
//                if (quantityCount < 1000) {
//                    quantityCount = Double.parseDouble(edt_quantity.getText().toString());
//                    mQuantity.setText(String.valueOf(quantityCount));
//                    mList.get(getAdapterPosition()).setQuantity(quantityCount);
//                    if (mList.get(getAdapterPosition()).getSingleCal()!=null){
//                        if (!mList.get(getAdapterPosition()).getSingleCal().isEmpty()){
//                            double singlecal=Double.parseDouble(mList.get(getAdapterPosition()).getSingleCal());
//                            double newCal=singlecal*quantityCount;
//                            DecimalFormat decimalFormat=new DecimalFormat("0.00");
//                            mList.get(getAdapterPosition()).setCalories(String.valueOf(decimalFormat.format(newCal)));
//
//
//                            notifyDataSetChanged();
//                        }
//                    }
//                }
//            }
//        });
//
//        dialog.show();
//    }
}
