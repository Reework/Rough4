package com.shamrock.reework.activity.FoodModule.history;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.shamrock.R;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.activity.FoodModule.activity.EditMealActivity;
import com.shamrock.reework.activity.FoodModule.fragment.FoodHistoryFragment;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.TodaysMeal;
import com.shamrock.reework.util.Utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealListHistorySubAdapter extends RecyclerView.Adapter<MealListHistorySubAdapter.MyHolder> {
    private ArrayList<Meals> list;
    Context context;
    getCall getCall_listner;
    String strUOM = "";

    public MealListHistorySubAdapter(Context context, ArrayList<Meals> list, FoodHistoryFragment foodHistoryFragmen) {
        this.list = list;
        this.context = context;
        getCall_listner = (getCall) foodHistoryFragmen;

    }

    public interface getCall {
        public void getCallData();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mCalories, mQuantity, textmealtime_all, txt_history_qnty, textmeal_edit, textmeal_delte;
        //        private ImageView mImage;
        ConstraintLayout linItem;
        ShapeableImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.textAllMealRowName);
//            mImage = itemView.findViewById(R.id.imgAllMeal_item_image);
            imageView = itemView.findViewById(R.id.image_view);
            imageView.setShapeAppearanceModel(imageView.getShapeAppearanceModel()
                    .toBuilder()
                    .setTopRightCorner(CornerFamily.ROUNDED, (float) 4.0)
                    .build());
            mQuantity = itemView.findViewById(R.id.textAllMealQty);
            mCalories = itemView.findViewById(R.id.textAllMealCalories);
            linItem = itemView.findViewById(R.id.linItem);
            textmealtime_all = itemView.findViewById(R.id.textmealtime_all);
            txt_history_qnty = itemView.findViewById(R.id.txt_history_qnty);
            textmeal_edit = itemView.findViewById(R.id.textmeal_edit);
            textmeal_delte = itemView.findViewById(R.id.textmeal_delte);
            textmeal_delte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeafaultDailog(getAdapterPosition());

                }
            });
            textmeal_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SessionManager sessionManager = new SessionManager(context);

                    if (sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")) {
                        shownoplan();
                    } else {
                        callEditHisty(getAdapterPosition());

                    }


                }
            });


        }

        private void shownoplan() {

            final Dialog dialog = new Dialog(context, R.style.CustomDialog);

            dialog.setContentView(R.layout.dialog_no_plan);
            dialog.setCancelable(false);
            TextView txt_lable_expired = dialog.findViewById(R.id.txt_lable_expired);

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500); //You can manage the blinking time with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            txt_lable_expired.startAnimation(anim);

            Button btn_subscribe = dialog.findViewById(R.id.btn_subscribe);
            Button btn_subscribe_no = dialog.findViewById(R.id.btn_subscribe_no);
            btn_subscribe_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn_subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                    Intent intent = new Intent(context, ViewPagerActivity.class);
                    intent.putExtra("param", "From_no_plan");
                    context.startActivity(intent);

                }
            });

            dialog.show();

        }


        private void callEditHisty(int adapterPosition) {
            SessionManager sessionManager = new SessionManager(context);
            int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
            int reecoachID = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
            Utils utils = new Utils();
            FoodService foodService = Client.getClient().create(FoodService.class);

            TodaysMeal todaysMeal = new TodaysMeal();
            todaysMeal.setMeal_type_name(list.get(adapterPosition).getMealType());

            todaysMeal.setMeal_name(list.get(adapterPosition).getMealName());
            todaysMeal.setUomId(list.get(adapterPosition).getUomId());
            todaysMeal.setUserMealId(Integer.valueOf(list.get(getAdapterPosition()).getUserMealId()));
            todaysMeal.setMeal_calory(list.get(adapterPosition).getCalories());
            todaysMeal.setIntakeTime(list.get(adapterPosition).getIntakeTime());
            todaysMeal.setMeal_cal_min(list.get(adapterPosition).getCalories());
            todaysMeal.setMeal_quantity(Double.parseDouble(list.get(adapterPosition).getMealQuantity()));
            todaysMeal.setFood_Id(Integer.parseInt(list.get(getAdapterPosition()).getRecipeId()));


            Intent i = new Intent(context, EditMealActivity.class);
            i.putExtra("commingFrom", "Edit");
            i.putExtra("mealItem", todaysMeal);
//            i.putExtra("CreatedonDate",sessionManager.getStringValue("statusdate"));
            i.putExtra("CreatedonDate", formatDates(list.get(adapterPosition).getCreatedOn()));
            context.startActivity(i);
        }
    }

    public void showDeafaultDailog(final int adapterPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage("Are you sure you want to delete ? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        callDeleteAppoinment(Integer.parseInt(list.get(adapterPosition).getUserMealId()), adapterPosition, formatDates(list.get(adapterPosition).getCreatedOn()));


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

    private void callDeleteAppoinment(int pos, final int del, String i) {

        final Utils utils = new Utils();
        SessionManager sessionManager = new SessionManager(context);
        AppoinmentService appoinmentService = Client.getClient().create(AppoinmentService.class);

        ClsHistrydeleteRequest clsHistrydeleteRequest = new ClsHistrydeleteRequest();
        clsHistrydeleteRequest.setUserMealId(pos);

        Call<ClsEditWaterResonse> call = appoinmentService.deleteMealhistory(clsHistrydeleteRequest);
        call.enqueue(new Callback<ClsEditWaterResonse>() {
            @Override
            public void onResponse(Call<ClsEditWaterResonse> call, Response<ClsEditWaterResonse> response) {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsEditWaterResonse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1")) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getCall_listner.getCallData();

                    } else {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsEditWaterResonse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    @NonNull
    @Override
    public MealListHistorySubAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_history_meal_list_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealListHistorySubAdapter.MyHolder holder, int i) {
        Meals model = list.get(i);
        if (model != null) {

            try {


                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_home_food)
                        .error(R.drawable.ic_home_food)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .priority(Priority.HIGH);


                if (model.getRecipeImagePath() != null) {
//                    Glide.with(context)
//                            .load(model.getRecipeImagePath())
//                            .apply(options.optionalCenterInside())
//                            .listener(new RequestListener<Drawable>() {
//                                @Override
//                                public boolean onLoadFailed(@Nullable GlideException e, Object model,
//                                                            Target<Drawable> target, boolean isFirstResource) {
//                                    return false;
//                                }
//
//                                @Override
//                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
//                                                               DataSource dataSource, boolean isFirstResource) {
//                                    return false;
//                                }
//                            })
//                            .into(holder.mImage);


                    Glide
                            .with(context)
                            .load(model.getRecipeImagePath())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(holder.imageView);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (list.get(i).getMealType() != null) {

                holder.mTitle.setSelected(true);
                holder.mTitle.setText(model.getMealName());
                holder.textmealtime_all.setText(model.getIntakeTime());


                if (model.getMeasurement() != null) {
                    strUOM = model.getMeasurement();

                    if (strUOM != null && !strUOM.isEmpty()) {
//                   holder.txt_history_qnty.setText("Qty ("+strUOM+")");
                    } else {
//                   holder.txt_history_qnty.setText("Qty");

                    }
                }


                String strqty = String.valueOf(model.getMealQuantity());
                int startindex = strqty.length() - 2;
                int endindex = strqty.length() - 1;
                String[] strNewQty = strqty.split("\\.");
                boolean isFound = false;


                if (strNewQty[1].equals("0")) {
                    isFound = true;

                }
                if (isFound) {
                    isFound = false;
//                    holder.mQuantity.setText(""+strNewQty[0].toString());
                    holder.txt_history_qnty.setSelected(true);
                    holder.txt_history_qnty.setText("Qty : " + strNewQty[0] + " " + strUOM);


                } else {
//                    holder.mQuantity.setText(""+model.getMealQuantity());
                    holder.txt_history_qnty.setSelected(true);
                    holder.txt_history_qnty.setText("Qty : " + model.getMealQuantity() + "" + strUOM);


                }


                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                String showschedulestr = decimalFormat.format(Double.parseDouble(model.getCalories()));
                if (showschedulestr.endsWith(".0")) {
                    holder.mCalories.setText(String.valueOf(Math.round(Double.parseDouble(model.getCalories()))));
                } else {
                    holder.mCalories.setText(showschedulestr);
                }




            }

        }


    }

    public String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours = "";
        int hoursstr = (totalMinutes / 60);
        if (hoursstr == 1) {
            strhours = " Hour ";
        } else {
            strhours = " Hours ";

        }
        return (totalMinutes / 60) + strhours + minutes + " Mins";
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public String formatDatesSunmit(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    public String formatDatesNew(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
