package com.shamrock.reework.activity.FoodModule.adapter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.shamrock.reework.activity.FoodModule.OnRepeatMealSelect;
import com.shamrock.reework.activity.FoodModule.RepeatSelectedMeal;
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.fragment.FoodRepeatMealFragmet;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.RepeatActivityListAdapter;
import com.shamrock.reework.activity.healthmonitoring.NewHealthMonitoringActivity;
import com.shamrock.reework.api.response.FoodListByMealType;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RepeatMealIntoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TimePickerDialog.OnTimeSetListener {
    private static final int CITY_TYPE = 1;
    private static final int ROW_TYPE = 2;
    private static final int FOOTER_TYPE = 3;
    public List<FoodListByMealType.Datum> mList;
    private List<FoodListByMealType.Datum> filtermList;
    OnMealRemoveListner listner;
    private Context context;
    OnRepeatMealSelect onRepeatMealSelect;
    ArrayList<String> arylst_meal_list;
    AddMealActivity foodRepeatMealFragmet;
    String time;
    OnAddRpeatListner onAddRpeatListner;
    RepeatSelectedMeal repeatSelectedMeal;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(context, ""+hourOfDay, Toast.LENGTH_SHORT).show();

    }


    public interface  OnMealRemoveListner{
        public void onRemove(int position, FoodListByMealType.Datum model);
    }

    public interface  OnAddRpeatListner{
        public void onAddRepeatMealSlected();

    }

    public RepeatMealIntoListAdapter(Context context, List<FoodListByMealType.Datum> list, AddMealActivity foodRepeatMealFragmet, ArrayList<String> arylst_meal_list, String time) {
        this.mList = list;
        this.context=context;
        this.arylst_meal_list=arylst_meal_list;
        this.foodRepeatMealFragmet=foodRepeatMealFragmet;
        this.time=time;
        onAddRpeatListner= (OnAddRpeatListner) foodRepeatMealFragmet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_mean_new_row_repeat, parent, false);
        return new RowViewHolder(view);
    }
    public void noti(){
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        if (position!=0){
            if (mList.get(position-1).getMealType().toString().equalsIgnoreCase(mList.get(position).getMealType().toString())){
                ((RowViewHolder) holder).txt_mealtype_main_header.setVisibility(View.GONE);
            }else {
                ((RowViewHolder) holder).txt_mealtype_main_header.setVisibility(View.GONE);

                ((RowViewHolder) holder).txt_mealtype_main_header.setText(mList.get(position).getMealType().toString());

            }
        }else {
            ((RowViewHolder) holder).txt_mealtype_main_header.setVisibility(View.GONE);

            ((RowViewHolder) holder).txt_mealtype_main_header.setText(mList.get(position).getMealType().toString());

        }


        ((RowViewHolder) holder).mTitle.setText(mList.get(position).getRecipeName());
        ((RowViewHolder) holder).txt_mealtype_new.setText(mList.get(position).getMealType().toString());


        ((RowViewHolder) holder).txt_mealtype_new.setText(mList.get(position).getMealType().toString());



        try {
            String strqty = String.valueOf(mList.get(position).getQuantity());
            String[] strNewQty = strqty.split("\\.");
            boolean isFound = false;


            if (strNewQty[1].equals("0")) {
                isFound = true;

            }
            if (isFound) {
                isFound = false;
//            ((DifferentRowAdapter.RowViewHolder) holder).mQuantity.setText(""+strNewQty[0].toString());
                ((RowViewHolder) holder).mQuantity.setText("" + strNewQty[0].toString());

            } else {
                ((RowViewHolder) holder).mQuantity.setText("" + mList.get(position).getQuantity());
            }

        }catch (Exception e){
            ((RowViewHolder) holder).mQuantity.setText("" + mList.get(position).getQuantity());

        }



        ((RowViewHolder) holder).img_edit_repeat_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog(position, mList.get(position));
            }
        });



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



        try {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");


            double valuegram = mList.get(position).getValueInGram();
            if (valuegram == 0) {
                valuegram = 1.0;
            } else {
                valuegram = mList.get(position).getValueInGram();
            }

            double qty = mList.get(position).getQuantity() * valuegram;
            double singlecal = 0;
            if (Double.parseDouble(mList.get(position).getCalories()) > 0) {
                singlecal = Double.parseDouble(mList.get(position).getCalories()) / qty;
            } else {

            }


            double calnew = singlecal * mList.get(position).getQuantity() * valuegram;

            ((RowViewHolder) holder).mCalories.setText(String.valueOf(decimalFormat.format(calnew)));
            ((RowViewHolder) holder).cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onRemove(position, mList.get(position));
                }
            });



            if (mList.get(position).isSelect()){
                ((RowViewHolder) holder). txt_select_repeat. setBackground(context.getResources().getDrawable(R.drawable.bg_green_button_new));
                ((RowViewHolder) holder). txt_select_repeat.setTextColor(context.getResources().getColor(R.color.white));
                 ((RowViewHolder) holder). txt_select_repeat. setText(" ADDED ");
                mList.get(position).setSelect(true);


            }else {
                ((RowViewHolder) holder). txt_select_repeat.setBackground(context.getResources().getDrawable(R.drawable.bkgr_button_green_bordered));
                ((RowViewHolder) holder). txt_select_repeat.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                ((RowViewHolder) holder). txt_select_repeat.setText("   ADD   ");
                mList.get(position).setSelect(false);

            }





        }catch (Exception e){

        }

    }


    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    public  class RowViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mCalories, mQuantity,cancle,textAllMealQty,txt_mealtype_new,txt_mealtype_main_header;
        private ImageView mImage;
        TextView img_edit_repeat_meal;
        TextView txt_select_repeat;
        TextView txt_add_repeat_food;
        int int_ada_pos=0;

        public RowViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textAllMealRowName);
            mImage = itemView.findViewById(R.id.imgAllMeal_item_image);
            mQuantity = itemView.findViewById(R.id.textAllMealQty);
            mCalories = itemView.findViewById(R.id.textAllMealCalories);
            cancle = itemView.findViewById(R.id.cancle);
            txt_mealtype_new = itemView.findViewById(R.id.txt_mealtype_new);
            txt_mealtype_main_header = itemView.findViewById(R.id.txt_mealtype_main_header);
            txt_select_repeat = itemView.findViewById(R.id.txt_select_repeat);
            txt_add_repeat_food = itemView.findViewById(R.id.txt_add_repeat_food);
            img_edit_repeat_meal = itemView.findViewById(R.id.img_edit_repeat_meal);


            int_ada_pos=getAdapterPosition();






            txt_select_repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mList.get(getAdapterPosition()).isSelect()){
                        txt_select_repeat.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        txt_select_repeat.setTextColor(context.getResources().getColor(R.color.white));
                        mList.get(getAdapterPosition()).setSelect(true);
                    }else {
                        txt_select_repeat.setBackgroundColor(context.getResources().getColor(R.color.line_color));
                        txt_select_repeat.setTextColor(context.getResources().getColor(R.color.black));
                        mList.get(getAdapterPosition()).setSelect(false);
                    }




                    notifyDataSetChanged();

                    onAddRpeatListner.onAddRepeatMealSlected();

                }
            });

//            mQuantity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                 showDailog();
//
//                }
//            });
        }



    }

    private void showDailog(final int newgetPOs,  FoodListByMealType.Datum datum) {


         try {
             final Dialog dialog = new Dialog(context,R.style.DialogTheme1);

             dialog.setContentView(R.layout.dailg_repeat_meal);
             WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
             lp.copyFrom(dialog.getWindow().getAttributes());
             lp.width = WindowManager.LayoutParams.MATCH_PARENT;
             lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
             lp.gravity = Gravity.BOTTOM;
             dialog.getWindow().setAttributes(lp);


             TextView repeatname=dialog.findViewById(R.id.repeatname);
             TextView repeatcal=dialog.findViewById(R.id.repeatcal);
             TextView repeattype=dialog.findViewById(R.id.repeattype);
             TextView txtquntity=dialog.findViewById(R.id.txtquntity);
             txtquntity.setText("Quantity in "+datum.getUnit());

             repeatname.setText(datum.getRecipeName());
             repeatcal.setText(datum.getCalories()+" kcal");
             repeattype.setText(datum.getMealType()+"");
             Button btn_update_repeat_quantity=dialog.findViewById(R.id.btn_update_repeat_quantity);
             final EditText edt_repeat_quantity=dialog.findViewById(R.id.edt_repeat_quantity);
             final TextView tvMealType_repeat=dialog.findViewById(R.id.tvMealType_repeat);
             tvMealType_repeat.setText(datum.getMealType().toString());
             mList.get(newgetPOs).setMealType(datum.getMealType().toString());
             tvMealType_repeat.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     final Dialog dialog=new Dialog(context,R.style.CustomDialog);


                     dialog.setContentView(R.layout.dailg_select_meal);
                     ListView lst_mealtype=dialog.findViewById(R.id.lst_mealtype);
//                            lst_mealtype

                     ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item_white, arylst_meal_list);
                     final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.simple_spinner_item, arylst_meal_list);
                     adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     lst_mealtype.setAdapter(adapter1);
//                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     lst_mealtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                         @Override
                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             tvMealType_repeat.setText(arylst_meal_list.get(position).toString());
                             dialog.dismiss();
                             mList.get(newgetPOs).setMealType(arylst_meal_list.get(position).toString());
                             noti();

                         }
                     });

                     dialog.setCancelable(true);
                     dialog.show();


                 }
             });

             final TextView tvMealTime_repeat=dialog.findViewById(R.id.tvMealTime_repeat);
             if (time!=null){
                 tvMealTime_repeat.setText(time);
                 mList.get(newgetPOs).setIntakeTime(time);


             }



             tvMealTime_repeat.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     Calendar mcurrentTime = Calendar.getInstance();
                     int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                     int minute = mcurrentTime.get(Calendar.MINUTE);
                     TimePickerDialog mTimePicker;
                     mTimePicker = new TimePickerDialog(foodRepeatMealFragmet, new TimePickerDialog.OnTimeSetListener() {
                         @Override
                         public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                             Calendar cal = Calendar.getInstance();
                             cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                             cal.set(Calendar.MINUTE, selectedMinute);
                             Format formatter;
                             formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
                             String lsTimeFrom = formatter.format(cal.getTime());

                             tvMealTime_repeat.setText(lsTimeFrom);
                             mList.get(newgetPOs).setIntakeTime(lsTimeFrom);
                             noti();

                         }
                     }, hour, minute, false);
                     mTimePicker.setTitle("Select Time");
                     mTimePicker.show();
                 }
             });
             edt_repeat_quantity.setText(String.valueOf(mList.get(newgetPOs).getQuantity()));
             ImageView close_update_qn_repeat=dialog.findViewById(R.id.close_update_qn_repeat);
             close_update_qn_repeat.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                 }
             });

             btn_update_repeat_quantity.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {


                     if (tvMealTime_repeat.getText().toString().equalsIgnoreCase("Select Meal Time")){
                         Toast.makeText(context, "Please select meal time", Toast.LENGTH_SHORT).show();
                         return;
                     }


                     if (edt_repeat_quantity.getText().toString().trim().isEmpty()){
                         Toast.makeText(context, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                         return;
                     }

                     if (Double.parseDouble(edt_repeat_quantity.getText().toString())>1000000){
                         Toast.makeText(context, "Enter valid quantity", Toast.LENGTH_SHORT).show();
                         return;
                     }

                     double valuegram=mList.get(newgetPOs).getValueInGram();
                     if (valuegram==0){
                         valuegram=1.0;
                     }else {
                         valuegram=mList.get(newgetPOs).getValueInGram();
                     }
                     double qty=mList.get(newgetPOs).getQuantity();
                     mList.get(newgetPOs).setQuantity(Double.parseDouble(edt_repeat_quantity.getText().toString()));

                     double singlecal=Double.parseDouble(mList.get(newgetPOs).getCalories())/qty;



                     mList.get(newgetPOs).setCalories(String.valueOf(singlecal*mList.get(newgetPOs).getQuantity()));
                     mList.get(newgetPOs).setSelect(true);
                     notifyDataSetChanged();

                     dialog.dismiss();
                 }
             });
             dialog.show();
         }catch (Exception e){
             e.printStackTrace();
         }


    }

    public void customArrrylsit(ArrayList<FoodListByMealType.Datum> commonAddmealList){



    }
}
