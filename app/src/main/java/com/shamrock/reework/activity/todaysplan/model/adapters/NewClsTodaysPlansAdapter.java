package com.shamrock.reework.activity.todaysplan.model.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.todaysplan.model.PlanItems;
import com.shamrock.reework.activity.todaysplan.model.ReeworkerPlan;
import com.shamrock.reework.activity.todaysplan.model.listners.onAddFoodPlan;
import com.shamrock.reework.api.response.MyPlanResponse;

import java.util.ArrayList;

public class NewClsTodaysPlansAdapter extends RecyclerView.Adapter<NewClsTodaysPlansAdapter.MyHolder> {
    private Context context;
    private ArrayList<ReeworkerPlan> dataList;
    private CheckBoxListener checkBoxListener;
    private PlanEditListener planEditListener;

    private boolean isDone = false;
    private boolean isAdded = false;
    private boolean isDoneCheck = false;
    int planId;
    boolean isFirstTime;

    onAddFoodPlan onAddFoodPlan_listner;
    private StringBuilder  str_final_text ;

    public interface CheckBoxListener {
        void isCheckBoxSelected(int pos, boolean isDone);
    }

    public interface PlanEditListener {
        void editedPlan(int pos, ArrayList<MyPlanResponse.MyPlanData> list);
    }

    public NewClsTodaysPlansAdapter(Context context,
                                    ArrayList<ReeworkerPlan> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.planId = planId;
        onAddFoodPlan_listner = (onAddFoodPlan) context;

    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvShedule, tvTime, tvPlan, tvQty;
        ImageView ivEdit, ivChecked, ivActivityDone;
        RecyclerView recler_todays_plan_item;
        LinearLayout ll_mian;
        CardView cardsss;
        TextView txt_add_plan;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            txt_add_plan = itemView.findViewById(R.id.txt_add_plan);
            txt_add_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//onAddFoodPlan_listner.onAddnewFoodPlanClick(dataList.get());
                }
            });
            recler_todays_plan_item = itemView.findViewById(R.id.recler_todays_plan_item);
            tvShedule = itemView.findViewById(R.id.tvSheduleName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPlan = itemView.findViewById(R.id.tvPlan);
            tvQty = itemView.findViewById(R.id.tvQty);
            ivActivityDone = itemView.findViewById(R.id.ivSelected);
            ivChecked = itemView.findViewById(R.id.imgMyPlan_check);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ll_mian = itemView.findViewById(R.id.ll_mian);


            ivActivityDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!dataList.get(getAdapterPosition()).isNotify()) {
                        ivActivityDone.setSelected(true);
                        isDone = true;
                        dataList.get(getAdapterPosition()).setNotify(true);
                        onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getDailyPlanMasterId()), dataList.get(getAdapterPosition()).isNotify(), isAdded, false, Integer.parseInt(dataList.get(getAdapterPosition()).getId()), Integer.parseInt(dataList.get(getAdapterPosition()).getId()), dataList.get(getAdapterPosition()).getId());

//                        onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getId()),isDone,dataList.get(getAdapterPosition()).isAdded(), alternate);

                    } else {
                        isDone = false;
                        ivActivityDone.setSelected(false);
                        dataList.get(getAdapterPosition()).setNotify(false);
                        onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getDailyPlanMasterId()), dataList.get(getAdapterPosition()).isNotify(), isAdded, false, Integer.parseInt(dataList.get(getAdapterPosition()).getId()), Integer.parseInt(dataList.get(getAdapterPosition()).getId()), dataList.get(getAdapterPosition()).getId());

//                        onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getId()),isDone,dataList.get(getAdapterPosition()).isAdded(), alternate);

                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataList.get(getAdapterPosition()).getDailyPlanMasterId().equals("6") || (dataList.get(getAdapterPosition()).getDailyPlanMasterId().equals("4"))) {
                        showmealDailog(getAdapterPosition());
                    } else if (dataList.get(getAdapterPosition()).getDailyPlanMasterId().equals("5")) {
                        shoAddActivityDailog(getAdapterPosition());


                    } else {

                        showDeafaultDailog(getAdapterPosition());


                    }


                }
            });


        }


        public void showmealDailog(final int adapterPosition) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setMessage("Are you sure want to add item to daily diary ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
//                            onAddFoodPlan_listner.getFoodPlanData(dataList.get(adapterPosition).getPlanItems(),dataList.get(adapterPosition).getMealType(),dataList.get(adapterPosition).getPlanTime());


                            if (!dataList.get(adapterPosition).isAdded()) {
                                ivChecked.setSelected(true);
                                isAdded = true;
                                dataList.get(adapterPosition).setAdded(true);
//                                onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).isNotify(),isAdded, alternate);

                            } else {
                                isAdded = true;
                                ivChecked.setSelected(true);
                                dataList.get(adapterPosition).setAdded(true);
//                                onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).isNotify(),isAdded, alternate);

                            }


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.show();

        }

        public void shoAddActivityDailog(final int adapterPosition) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setMessage("Are you sure want to add activity  to lifestyle activity ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

//                            onAddFoodPlan_listner.getLifeStyleplan(dataList.get(adapterPosition).getPlanItems(), dataList.get(adapterPosition).getMealType(), dataList.get(adapterPosition).getPlanTime());
                            if (!dataList.get(adapterPosition).isAdded()) {
                                ivChecked.setSelected(true);
                                isAdded = true;
                                dataList.get(adapterPosition).setAdded(true);
//                                onAddFoodPlan_listner.getFoodPlanData();
//                                onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).isNotify(),isAdded, alternate);

                            } else {
                                isAdded = true;
                                ivChecked.setSelected(false);
                                dataList.get(getAdapterPosition()).setAdded(true);
//                                onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).isNotify(),isAdded, alternate);

                            }


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.show();

        }

        public void showDeafaultDailog(final int adapterPosition) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setMessage("Are you sure you want to add ? ")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            if (!dataList.get(adapterPosition).isAdded()) {
                                ivChecked.setSelected(true);
                                isAdded = true;
                                dataList.get(adapterPosition).setAdded(true);
//                                onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).isNotify(),isAdded, alternate);

                            } else {
                                isAdded = true;
                                ivChecked.setSelected(false);
                                dataList.get(getAdapterPosition()).setAdded(true);
//                                onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(adapterPosition).getId()),dataList.get(adapterPosition).isNotify(),isAdded, alternate);

                            }


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.show();

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ivSelected) {
                boolean status = false;


                if (!dataList.get(getAdapterPosition()).isNotify()) {
                    ivActivityDone.setSelected(true);
                    isDone = true;
                    dataList.get(getAdapterPosition()).setNotify(true);
                    notifyDataSetChanged();

                    onAddFoodPlan_listner.onNotificationClick(Integer.parseInt(dataList.get(getAdapterPosition()).getDailyPlanMasterId()), dataList.get(getAdapterPosition()).isNotify(), isAdded, false, Integer.parseInt(dataList.get(getAdapterPosition()).getId()), Integer.parseInt(dataList.get(getAdapterPosition()).getId()), dataList.get(getAdapterPosition()).getId());


                } else {
                    isDone = false;
                    ivActivityDone.setSelected(false);
                    dataList.get(getAdapterPosition()).setNotify(false);
                    notifyDataSetChanged();

                }


            }
            if (v.getId() == R.id.imgMyPlan_check) {
                boolean status = false;

                if (!dataList.get(getAdapterPosition()).isNotify()) {
                    ivChecked.setSelected(true);
                    isDoneCheck = true;

                } else {
                    isDoneCheck = false;
                    ivChecked.setSelected(false);
                }


            }
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_todays_plan_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        String lsTime, lsPlan, lsQty;

        ReeworkerPlan model = dataList.get(i);


        if (!TextUtils.isEmpty((String) model.getPlanTime())) {
            myHolder.tvTime.setText(model.getPlanTime());
        }


        if (!TextUtils.isEmpty((String) model.getMealType())) {
            myHolder.tvShedule.setText(model.getMealType());
        }


        if (model.isAdded()) {
            myHolder.ivChecked.setSelected(true);
        } else {
            myHolder.ivChecked.setSelected(false);

        }

        if (model.isNotify()) {
            myHolder.ivActivityDone.setSelected(true);
        } else {
            myHolder.ivActivityDone.setSelected(false);

        }


        if (model.getGroups() != null && !model.getGroups().isEmpty()) {

            ArrayList<PlanItems> arylst_cuPlanItems = new ArrayList<>();
            int count = 0;
            int finalCount = 0;





            for (int j = 0; j < model.getGroups().size(); j++) {






                for (int k = 0; k <model.getGroups().get(j).getPlanItems().size(); k++) {
                    str_final_text=new StringBuilder();


                    if (k==0){


                        if (model.getGroups().size()==1) {

                            if (model.getGroups().size() == 1) {
                                if (j == 0) {
                                    str_final_text.append(model.getGroups().get(j).getGroupName());
                                    str_final_text.setLength(0);
                                }

                            }
                        }

                        if (model.getGroups().size()==2){
                            if (j==0){
                                str_final_text=new StringBuilder();
                                str_final_text.append(model.getGroups().get(j).getGroupName());
                                str_final_text.setLength(0);
                            }

                            if (j==1){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 1");
                            }



                        }

                        if (model.getGroups().size()==3){
                            if (j==0){
                                str_final_text=new StringBuilder();
                                str_final_text.append(model.getGroups().get(j).getGroupName());
                                str_final_text.setLength(0);
                            }

                            if (j==1){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 1");
                            }

                            if (j==2){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 2");
                            }

                        }

                        if (model.getGroups().size()==4){
                            if (j==0){
                                str_final_text=new StringBuilder();
                                str_final_text.append(model.getGroups().get(j).getGroupName());
                                str_final_text.setLength(0);
                            }

                            if (j==1){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 1");
                            }

                            if (j==2){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 2");
                            }
                            if (j==3){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 3");
                            }

                        }

                        if (model.getGroups().size()==5){
                            if (j==0){
                                str_final_text=new StringBuilder();
                                str_final_text.append(model.getGroups().get(j).getGroupName());
                                str_final_text.setLength(0);
                            }

                            if (j==1){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 1");
                            }

                            if (j==2){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 2");
                            }
                            if (j==3){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 3");
                            }
                            if (j==4){
                                str_final_text=new StringBuilder();
                                str_final_text.append("Alternate 4");
                            }

                        }




//


                    }else {
                        str_final_text.setLength(0);
                    }

                    boolean isFound=false;


                    if (!isFound){
                        count = 0;

                        if (model.getGroups().get(j).getPlanItems()!=null){
                            try{
                                arylst_cuPlanItems.add(new PlanItems(model.getGroups().get(j).getPlanItems().get(k).getSubActivityId(),model.getGroups().get(j).getPlanItems().get(k).getActivationTime(),model.getGroups().get(j).getPlanItems().get(k).getMeasurement()
                                        , model.getGroups().get(j).getPlanItems().get(k).getItemName()
                                        , model.getGroups().get(j).getPlanItems().get(k).getQuantity()
                                        , model.getGroups().get(j).getPlanItems().get(k).getId()
                                        , model.getGroups().get(j).getPlanItems().get(k).getItemId()
                                        , model.getGroups().get(j).getPlanItems().get(k).getUomId()
                                        , model.getGroups().get(j).getPlanItems().get(k).getRemark()
                                        , false, "Alternate Item " + count, model.getDailyPlanMasterId()
                                        , model.getMealType(), model.getPlanTime()
                                        , model.getGroups().get(j).getPlanItems().get(k).isAdded(), model.isNotify(), model.getDailyPlanMasterId()

                                        , 0, model.isAdded(), Integer.parseInt(model.getId()), model.getGroups().get(j).getPlanItems().get(k).getLinkedRecipeId(), str_final_text.toString(),model.getGroups().get(j).getId(),Integer.parseInt(model.getDailyPlanMasterId()),model.getGroups().get(j).getPlanItems().get(k).isTodayPlan(),model.getGroups().get(j).getPlanItems().get(k).getReeworkerplanId(),model.getGroups().get(j).getPlanItems().get(k).getImagePath()));

                            }catch (Exception e){
                                e.printStackTrace();

                            }

                        }



                    }







                }

                if (model.getGroups().get(j).getAlternateItems() != null) {






                    try {
                        if (!(model.getGroups().get(j).getAlternateItems().isEmpty())) {


                            boolean isFounAlternate=false;





//                            for (int m = model.getGroups().get(j).getAlternateItems().size()-1; m >= 0; m--) {
                            for (int m =0; m < model.getGroups().get(j).getAlternateItems().size(); m++) {
                                count++;
                                isFirstTime = false;


                                arylst_cuPlanItems.add(new PlanItems
                                        (model.getGroups().get(j).getAlternateItems().get(m).getSubActivityId(),model.getGroups().get(j).getAlternateItems().get(m).getActivationTime(),model.getGroups().get(j).getAlternateItems().get(m).getMeasurement()
                                                , model.getGroups().get(j).getAlternateItems().get(m).getItemName()
                                                , model.getGroups().get(j).getAlternateItems().get(m).getQuantity()
                                                , model.getGroups().get(j).getAlternateItems().get(m).getId()
                                                , model.getGroups().get(j).getAlternateItems().get(m).getItemId()
                                                , model.getGroups().get(j).getAlternateItems().get(m).getUomId()
                                                , model.getGroups().get(j).getAlternateItems().get(m).getRemark(),
                                                true, "Alternate Items " + count, model.getDailyPlanMasterId()
                                                , model.getMealType(), model.getPlanTime()
                                                , model.getGroups().get(j).getAlternateItems().get(m).isAdded(), model.isNotify(), model.getDailyPlanMasterId()
                                                , Integer.parseInt(model.getGroups().get(j).getAlternateItems().get(m).getId())
                                                , model.getGroups().get(j).getAlternateItems().get(m).isAdded()
                                                , Integer.parseInt(model.getId()), model.getGroups().get(j).getAlternateItems().get(m).getLinkedRecipeId(), "",model.getGroups().get(j).getId(),Integer.parseInt(model.getDailyPlanMasterId()),model.getGroups().get(j).getAlternateItems().get(m).isTodayPlan(), model.getGroups().get(j).getAlternateItems().get(m).getReeworkerplanId(), model.getGroups().get(j).getAlternateItems().get(m).getImagePath()));


                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isFirstTime = false;
                } else {

                }


            }


            myHolder.ll_mian.setVisibility(View.VISIBLE);
            myHolder.recler_todays_plan_item.setAdapter(new NewClsPlansDataAdapter(context, arylst_cuPlanItems, model.getDailyPlanMasterId(), model.getMealType()));

        } else {
            myHolder.ll_mian.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public void showSupllimentDailog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.testyyy);
        dialog.show();
    }


}
