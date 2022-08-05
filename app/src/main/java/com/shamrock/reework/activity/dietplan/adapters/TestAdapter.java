package com.shamrock.reework.activity.dietplan.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.FoodRecipeActivity;
import com.shamrock.reework.activity.FoodModule.adapter.RepeatMealIntoListAdapter;
import com.shamrock.reework.activity.dietplan.pojo.FoodPlan;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.database.SessionManager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyViewHolder> implements Filterable {
    Context context;
//    ArrayList<FoodPlan> arrayList;
    ArrayList<FoodPlan> mFilterlist;
    ArrayList<FoodPlan> mArrayList;
    OnEditFoodPlan listner;
    SessionManager sessionManager;

    public TestAdapter(Context context, ArrayList<FoodPlan> list) {
        this.context = context;
//        this.arrayList = arrayList;
        mFilterlist = list;
        mArrayList = list;
        listner= (OnEditFoodPlan) context;
        sessionManager=new SessionManager(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.food,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {

//          String  submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


//          String sddd=sessionManager.getStringValue("matchrecords");
//            String[]  ary=sddd.split(",");
//            if (ary[0].equalsIgnoreCase(submitFromDate)){
//                for (int i = 0; i <ary.length ; i++) {
//                    if (String.valueOf(mFilterlist.get(position).getRecipeId()).equalsIgnoreCase(ary[i].toString())){
//                    }
//
//                }
//            }



            holder.txtMealName.setText(mFilterlist.get(position).getMealName());
            holder.txtMealTime.setText("Time : " + mFilterlist.get(position).getMealTime());
            holder.txtMealType.setText(mFilterlist.get(position).getMealType());
            holder.txtUOM.setText("Unit : " + mFilterlist.get(position).getUOM());



            try {
                String strqty = String.valueOf(mFilterlist.get(position).getQuantity());
                String[] strNewQty = strqty.split("\\.");
                boolean isFound = false;


                if (strNewQty[1].equals("0")) {
                    isFound = true;

                }
                if (isFound) {
                    isFound = false;
//            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
                    holder.txtQuantity.setText("Quantity : " + strNewQty[0].toString());

                } else {
                    holder.txtQuantity.setText("Quantity : " + mFilterlist.get(position).getQuantity());
                }

            }catch (Exception e){
                holder.txtQuantity.setText("Quantity : " + mFilterlist.get(position).getQuantity());

            }


//            holder.txtQuantity.setText("Quantity: " + String.valueOf(mFilterlist.get(position).getQuantity()));
            holder.txtFat.setText("Fat : " + String.valueOf(mFilterlist.get(position).getFat()));
            holder.txtFiber.setText("Fibre : " + String.valueOf(mFilterlist.get(position).getFibre()));
            holder.txtRemark.setText("Remark : " + String.valueOf(mFilterlist.get(position).getRemark()));
            holder.txtCarb.setText("Carb : " + String.valueOf(mFilterlist.get(position).getCarb()));
            holder.txtCalories.setText("Calories :" + String.valueOf(mFilterlist.get(position).getCalories()));
            holder.txtProtein.setText("Protein : " + String.valueOf(mFilterlist.get(position).getProtein()));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mFilterlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMealType,txtMealName,txtMealTime,txtUOM,txtFat,txtQuantity,txtProtein,txtCarb,txtFiber,txtRemark,txtCalories,txt_view_recipe;
       ImageView img_edit_food_plan,delete_food_plan,img_add_to_daily_dairy;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            txtMealName=itemView.findViewById(R.id.txt_meal_name);
            img_add_to_daily_dairy=itemView.findViewById(R.id.img_add_to_daily_dairy);
            img_edit_food_plan=itemView.findViewById(R.id.img_edit_food_plan);
            txtMealType=itemView.findViewById(R.id.txt_meal_type);
            txtMealTime=itemView.findViewById(R.id.txt_meal_time);
            txtUOM=itemView.findViewById(R.id.txt_uom);
            txtFat=itemView.findViewById(R.id.txt_fat);
            txtQuantity=itemView.findViewById(R.id.txt_quantity);
            txtProtein=itemView.findViewById(R.id.txt_protein);
            txtCarb=itemView.findViewById(R.id.txt_carb);
            txtFiber=itemView.findViewById(R.id.txt_fibre);
            txtRemark=itemView.findViewById(R.id.txt_remark);
            txtCalories=itemView.findViewById(R.id.txt_calories);
            delete_food_plan=itemView.findViewById(R.id.delete_food_plan);
            txt_view_recipe=itemView.findViewById(R.id.txt_view_recipe);





            delete_food_plan=itemView.findViewById(R.id.delete_food_plan);


            txt_view_recipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, FoodRecipeActivity.class);
                    intent.putExtra("RECEIPE_ID", mFilterlist.get(getAdapterPosition()).getRecipeId());
                    intent.putExtra("EDIT_ID",0);
                    intent.putExtra("RECEIPE_Image","");
                    context.startActivity(intent);
                }
            });

            delete_food_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listner.deleteFoodPlanData(mFilterlist.get(getAdapterPosition()).getFoodPlanId());

                }

            });

            img_add_to_daily_dairy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);


                    builder.setMessage("Are you sure want to add meal item to daily diary")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                    listner.addToDailyDairy(mFilterlist.get(getAdapterPosition()));

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
            });

            img_edit_food_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.getEditFoodPlanData(mFilterlist.get(getAdapterPosition()));
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterlist = mArrayList;
                } else {

                    ArrayList<FoodPlan> filterData = new ArrayList<>();
                    for (FoodPlan row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMealName()!=null && row.getMealName().toLowerCase().contains(charString.toLowerCase())) {

                            filterData.add(row);
                        }
                    }

                    mFilterlist = filterData;
                    if(filterData.size()==0){
                        Toast.makeText(context,"Meal not found",Toast.LENGTH_SHORT).show();
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                /*  list = (ArrayList<FoodTripResponse.FoodStripData>) results.values;*/
                mFilterlist = (ArrayList<FoodPlan>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
