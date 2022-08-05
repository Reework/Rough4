package com.shamrock.reework.activity.todaysplan.model.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.todaysplan.model.PlanItems;
import com.shamrock.reework.activity.todaysplan.model.listners.onAddFoodPlan;
import com.shamrock.reework.common.TouchImageView;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.razerpaypsyment.PaymentWebviewActivity;

import java.util.ArrayList;

public class NewClsPlansDataAdapter extends RecyclerView.Adapter<NewClsPlansDataAdapter.MyHolder>
{
    private Context context;
    private ArrayList<PlanItems> dataList;


    private boolean isDone = false;
    int planId;
    String dailyPlanMasterId;
    String mealtype;

    onAddFoodPlan onAddFoodPlan_listner;
    private boolean isAdded;

    SessionManager   sessionManager;

    public NewClsPlansDataAdapter(Context context,
                                  ArrayList<PlanItems> dataList, String dailyPlanMasterId, String mealtype)
    {
        this.context = context;
        this.dataList = dataList;
        this.dailyPlanMasterId=dailyPlanMasterId;
        this.mealtype=mealtype;
        onAddFoodPlan_listner= (onAddFoodPlan) context;
           sessionManager=new SessionManager(context);

//        this.checkBoxListener = checkBoxListener;
//        this.planId = planId;
//        this.planEditListener = planEditListener;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvPlan_item_name, tvQty,txt_remark,txt_header_remark,txt_alternate_show,txt_add_food_plan,txt_edit_food_plan;
        ImageView ivChecked,img_recipe;
        LinearLayout ll_alternate,ll_remark;
        TextView txt_adddd,txt_view,txt_final_alternate_Show;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            img_recipe=itemView.findViewById(R.id.img_recipe);
            txt_adddd=itemView.findViewById(R.id.txt_adddd);
            txt_view=itemView.findViewById(R.id.txt_view);
            ll_remark=itemView.findViewById(R.id.ll_remark);
            ll_alternate=itemView.findViewById(R.id.ll_alternate);
            tvPlan_item_name=itemView.findViewById(R.id.tvPlan_item_name);
            tvQty=itemView.findViewById(R.id.tvQty);
            txt_remark=itemView.findViewById(R.id.txt_remark);
            txt_header_remark=itemView.findViewById(R.id.txt_header_remark);
            txt_alternate_show=itemView.findViewById(R.id.txt_alternate_show);
            ivChecked = itemView.findViewById(R.id.imgMyPlan_check);
            txt_add_food_plan = itemView.findViewById(R.id.txt_add_food_plan);
            txt_edit_food_plan = itemView.findViewById(R.id.txt_edit_food_plan);
            txt_final_alternate_Show = itemView.findViewById(R.id.txt_final_alternate_Show);


            txt_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddFoodPlan_listner.onViewRecipe(dataList.get(getAdapterPosition()).getLinkedRecipeId());

//                    Intent intent=new Intent(context, FoodRecipeActivity.class);
//                    intent.putExtra("RECEIPE_ID", dataList.get(getAdapterPosition()).getLinkedRecipeId());
//                    intent.putExtra("EDIT_ID",0);
//                    intent.putExtra("RECEIPE_Image","");
//                    intent.putExtra("FROMPLAN",true);
//                    context.startActivity(intent);
                }
            });

            txt_edit_food_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddFoodPlan_listner.onEditFoodPlanClick(dataList.get(getAdapterPosition()));

                }
            });

            txt_adddd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddFoodPlan_listner.onAddFoodPlanClick(dataList.get(getAdapterPosition()));
//                    Toast.makeText(context, ""+dataList.get(getAdapterPosition()).getDialyPlanMasterID(), Toast.LENGTH_SHORT).show();


                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    sessionManager.setStringValue("Mealtypescroll",dataList.get(getAdapterPosition()).getMealtype());


                    if (dataList.get(getAdapterPosition()).getPlanMasterID().equals("6")||(dataList.get(getAdapterPosition()).getPlanMasterID().equals("4"))){
                        if (!dataList.get(getAdapterPosition()).isAdded()){
                            ivChecked.setSelected(true);
                            isAdded=true;
                            dataList.get(getAdapterPosition()).setAdded(true);
                            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getPostPlanID()),dataList.get(getAdapterPosition()).isNotify(),isAdded,dataList.get(getAdapterPosition()).isAlternate(), Integer.parseInt(dataList.get(getAdapterPosition()).getId()), dataList.get(getAdapterPosition()).getSubmitID(),dataList.get(getAdapterPosition()).getId());

                        }else {
                            ivChecked.setSelected(false);
                            dataList.get(getAdapterPosition()).setAdded(false);
                            if (dataList.get(getAdapterPosition()).isAdded()){
                                isAdded=false;
                            }
                            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getPostPlanID()),dataList.get(getAdapterPosition()).isNotify(),isAdded,dataList.get(getAdapterPosition()).isAlternate(), Integer.parseInt(dataList.get(getAdapterPosition()).getId()), dataList.get(getAdapterPosition()).getSubmitID(), dataList.get(getAdapterPosition()).getId());
//
                        }
                        if (isAdded){
                            onAddFoodPlan_listner.getFoodPlanData(dataList.get(getAdapterPosition()),dataList.get(getAdapterPosition()).getMealtype(),dataList.get(getAdapterPosition()).getPlantime(),dataList.get(getAdapterPosition()).getItemId());

                        }



                    }else if (dataList.get(getAdapterPosition()).getPlanMasterID().equals("5")) {
                        shoAddActivityDailog(getAdapterPosition(),dataList.get(getAdapterPosition()).isAlternate(),ivChecked);


                    }else if (dataList.get(getAdapterPosition()).getPlanMasterID().equals("1")){

                        showDeafaultDailog(getAdapterPosition(),ivChecked,dataList.get(getAdapterPosition()).isAlternate(),"Nutrition Plan");


                    }else if (dataList.get(getAdapterPosition()).getPlanMasterID().equals("2")){

                        showDeafaultDailog(getAdapterPosition(),ivChecked,dataList.get(getAdapterPosition()).isAlternate(),"Suppliment Plan");


                    }



                }
            });
        }


    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_plan_items_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        String lsTime, lsPlan, lsQty;

        boolean isAlternateFound=false;
        final PlanItems model = dataList.get(i);

        setCylcerImage(myHolder.img_recipe,model.getImagePath());
        myHolder.img_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openss(model.getImagePath(),model.getItemName());
                openss(model.getImagePath(),model.getItemName());
            }
        });




        if (model.getFinalAlternate()!=null&&!model.getFinalAlternate().isEmpty()){
            myHolder.txt_final_alternate_Show.setVisibility(View.VISIBLE);
            myHolder.txt_final_alternate_Show.setText(model.getFinalAlternate());
        }else {
            myHolder.txt_final_alternate_Show.setVisibility(View.GONE);

        }

        if (model.getLinkedRecipeId()==0){
            myHolder.txt_view.setAlpha((float) 0.5);

        }else {
            myHolder.txt_view.setAlpha((float) 1.0);

        }



        if (model.getDialyPlanMasterID()!=5){
            myHolder.txt_view.setVisibility(View.VISIBLE);
        }else {
            myHolder.txt_view.setVisibility(View.GONE);

        }





        if (i==0){
            myHolder.txt_adddd.setVisibility(View.GONE);
        }else {
            myHolder.txt_adddd.setVisibility(View.GONE);

        }

        if (model.isAdded()){
            myHolder.ivChecked.setSelected(true);
        }else {
            myHolder.ivChecked.setSelected(false);

        }

//        if (model.isCheckedFlag()){
//            myHolder.ivChecked.setSelected(true);
//        }else {
//            myHolder.ivChecked.setSelected(false);
//
//        }


        if (model.isAlternate()){
            myHolder.txt_alternate_show.setVisibility(View.GONE);
            myHolder.txt_alternate_show.setText(model.getMsgcount());
            myHolder.ivChecked.setVisibility(View.VISIBLE);
            myHolder.ll_alternate.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
            myHolder.ll_remark.setBackgroundColor(context.getResources().getColor((android.R.color.darker_gray)));

        }else {
            myHolder.txt_alternate_show.setVisibility(View.GONE);
            myHolder.ivChecked.setVisibility(View.VISIBLE);
            myHolder.ll_alternate.setBackgroundColor(context.getResources().getColor(R.color.white));
            myHolder.ll_remark.setBackgroundColor(context.getResources().getColor(R.color.white));


        }

        if(model.getId().equalsIgnoreCase("5")){

        }

        if (!myHolder.txt_alternate_show.getText().toString().trim().equalsIgnoreCase("Alternate Items 1")){
            myHolder.txt_alternate_show.setVisibility(View.GONE);
            myHolder.ivChecked.setVisibility(View.VISIBLE);
            myHolder.txt_alternate_show.setText("OR");
            myHolder.ivChecked.setVisibility(View.INVISIBLE);


        }else {
            myHolder.txt_alternate_show.setVisibility(View.VISIBLE);
            myHolder.txt_alternate_show.setText(model.getMsgcount());
            myHolder.ivChecked.setVisibility(View.VISIBLE);
            myHolder.txt_alternate_show.setText("OR");


        }
        if (!model.isAlternate()){
            myHolder.ivChecked.setVisibility(View.VISIBLE);

        }else {
            myHolder.ivChecked.setVisibility(View.VISIBLE);

        }



        if (model.getRemark()!=null){
            myHolder.txt_header_remark.setVisibility(View.VISIBLE);
            myHolder.txt_remark.setVisibility(View.VISIBLE);
            myHolder.txt_remark.setText(""+model.getRemark());
        }else {
            myHolder.txt_header_remark.setVisibility(View.GONE);
            myHolder.txt_remark.setVisibility(View.GONE);

        }


        if (!dailyPlanMasterId.equalsIgnoreCase("5")){
            myHolder.txt_view.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty((String) model.getItemName())){

                myHolder.tvPlan_item_name.setText(model.getMeasurement()+"- "+model.getItemName());
            }
        }else {
            if (!TextUtils.isEmpty((String) model.getItemName())){
                myHolder.tvPlan_item_name.setText("Mins -"+model.getItemName());
            }

            myHolder.txt_view.setVisibility(View.GONE);
        }

        if (!model.isAlternate()) {
            myHolder.tvPlan_item_name.setTypeface(myHolder.tvPlan_item_name.getTypeface(), Typeface.BOLD);
        }
        else {
            myHolder.tvPlan_item_name.setTypeface(myHolder.tvPlan_item_name.getTypeface(), Typeface.NORMAL);
        }


        try {
            String strqty = String.valueOf(model.getQuantity());
            String[] strNewQty = strqty.split("\\.");
            boolean isFound = false;


            if (strNewQty[1].equals("0")) {
                isFound = true;

            }
            if (isFound) {
                isFound = false;
//            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
                myHolder.tvQty.setText( strNewQty[0].toString());

            } else {
                myHolder.tvQty.setText( model.getQuantity());
            }

        }catch (Exception e){
            myHolder.tvQty.setText("" + model.getQuantity());

        }



    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    public void showmealDailog(final int adapterPosition, final boolean alternate, final ImageView ivChecked){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//
//        builder.setMessage("Are you sure want to add item to daily diary ?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//
//                        if (!dataList.get(adapterPosition).isAdded()){
//                            ivChecked.setSelected(true);
//                            isAdded=true;
//                            dataList.get(adapterPosition).setAdded(true);
//                            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate, Integer.parseInt(dataList.get(adapterPosition).getId()), dataList.get(adapterPosition).getSubmitID(),dataList.get(adapterPosition).getId());
//
//                        }else {
//                            isAdded=true;
//                            ivChecked.setSelected(false);
//                            dataList.get(adapterPosition).setAdded(true);
//                            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate, Integer.parseInt(dataList.get(adapterPosition).getId()), dataList.get(adapterPosition).getSubmitID(), dataList.get(adapterPosition).getId());
//                        }
//                        onAddFoodPlan_listner.getFoodPlanData(dataList.get(adapterPosition),dataList.get(adapterPosition).getMealtype(),dataList.get(adapterPosition).getPlantime(),dataList.get(adapterPosition).getItemId());
//
//
//
//
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//                        dialog.cancel();
//                    }
//                });
//        //Creating dialog box
//        AlertDialog alert = builder.create();
//        //Setting the title manually
//        alert.show();
        if (!dataList.get(adapterPosition).isAdded()){
            ivChecked.setSelected(true);
            isAdded=true;
            dataList.get(adapterPosition).setAdded(true);
            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate, Integer.parseInt(dataList.get(adapterPosition).getId()), dataList.get(adapterPosition).getSubmitID(),dataList.get(adapterPosition).getId());

        }else {
            isAdded=true;
            ivChecked.setSelected(false);
            dataList.get(adapterPosition).setAdded(true);
            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate, Integer.parseInt(dataList.get(adapterPosition).getId()), dataList.get(adapterPosition).getSubmitID(), dataList.get(adapterPosition).getId());
        }
        onAddFoodPlan_listner.getFoodPlanData(dataList.get(adapterPosition),dataList.get(adapterPosition).getMealtype(),dataList.get(adapterPosition).getPlantime(),dataList.get(adapterPosition).getItemId());



    }
    public void shoAddActivityDailog(final int adapterPosition, final boolean alternate, final ImageView ivChecked){



        if (!dataList.get(adapterPosition).isAdded()){
            ivChecked.setSelected(true);
            isAdded=true;
            dataList.get(adapterPosition).setAdded(true);
            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate,Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).getSubmitID(), dataList.get(adapterPosition).getId());

        }else {
            isAdded=true;
            ivChecked.setSelected(false);
            dataList.get(adapterPosition).setAdded(true);
            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate,Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).getSubmitID(), dataList.get(adapterPosition).getId());
//
        }

//                        onAddFoodPlan_listner.getLifeStyleplan(dataList.get(adapterPosition), dataList.get(adapterPosition).getMealtype(), dataList.get(adapterPosition).getPlantime());
        onAddFoodPlan_listner.addActivityLifeStyleplantoDailyDaily(dataList.get(adapterPosition));
    }

    public void showDeafaultDailog(final int adapterPosition, final ImageView ivChecked, final boolean alternate, String s){


        if (!dataList.get(adapterPosition).isAdded()){
            ivChecked.setSelected(true);
            isAdded=true;
            if (dataList.get(adapterPosition).isAdded()){
                isAdded=false;
            }else {
                isAdded=true;

            }
            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate, Integer.parseInt(dataList.get(adapterPosition).getItemId()), dataList.get(adapterPosition).getSubmitID(), dataList.get(adapterPosition).getId());

        }else {
            isAdded=true;
            ivChecked.setSelected(false);
            if (dataList.get(adapterPosition).isAdded()){
                isAdded=false;
            }else {
                isAdded=true;

            }
            onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getPostPlanID()),dataList.get(adapterPosition).isNotify(),isAdded,alternate, Integer.parseInt(dataList.get(adapterPosition).getItemId()), dataList.get(adapterPosition).getSubmitID(), dataList.get(adapterPosition).getId());

        }
        if (!dataList.get(adapterPosition).isAdded()){
            onAddFoodPlan_listner.getFoodPlanData(dataList.get(adapterPosition),dataList.get(adapterPosition).getMealtype(),dataList.get(adapterPosition).getPlantime(), dataList.get(adapterPosition).getItemId());

        }




    }

    private void setCylcerImage(ImageView imagePath,String path) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.defaultmedicine)
                .error(R.drawable.defaultmedicine)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);
        if (path != null) {
            Glide.with(context)
                    .load(path)
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
                    .into(imagePath);
        }
    }
    private void openss(String finalUrl,String name) {
        if (!finalUrl.isEmpty()) {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);

            dialog.setContentView(R.layout.dailg_fill_before_aftersss);
            dialog.setCancelable(true);
            TouchImageView img_full_screen = dialog.findViewById(R.id.img_full_screen);
            TextView txt_medicine_name = dialog.findViewById(R.id.txt_medicine_name);
            txt_medicine_name.setText(name);

            txt_medicine_name.setSelected(true);
            ImageView img_close_dailog = dialog.findViewById(R.id.img_close_dailog);
            final RelativeLayout rel_med_header = dialog.findViewById(R.id.rel_med_header);
            img_close_dailog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Glide.with(context)

                    .load(finalUrl)
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
    }

    private void opens(String finalUrl,String name) {
        if (!finalUrl.isEmpty()) {
            final Dialog dialog = new Dialog(context, R.style.CustomDialog);

            dialog.setContentView(R.layout.dailg_fill_before_afters);
            dialog.setCancelable(true);
            ImageView img_full_screen = dialog.findViewById(R.id.img_full_screen);
            TextView txt_name_imge = dialog.findViewById(R.id.txt_name_imge);
            txt_name_imge.setText(name);
            ImageView img_close_dailog = dialog.findViewById(R.id.img_close_dailog);
            img_close_dailog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            Glide.with(context)

                    .load(finalUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
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
                    .into(img_full_screen);

            dialog.show();
        }
    }


}
