package com.shamrock.reework.activity.FoodModule.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.history.ClsFoodHistoryPojo;
import com.shamrock.reework.activity.FoodModule.history.Data;
import com.shamrock.reework.activity.FoodModule.history.FoodHistoryMainAdapter;
import com.shamrock.reework.activity.FoodModule.history.MealListHistorySubAdapter;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodHistoryFragment extends Fragment implements   DatePickerDialog.OnDateSetListener, MealListHistorySubAdapter.getCall
{
    private RecyclerView recler_food_history;
    private TextView txt_show_sleep_to,txt_show_sleep_from;
    private ImageView img_add_sleep_date;
    private TextView txt_sleep_date_from_dialog;
    private TextView txt_sleep_date_to_dialog;

    private Context context;
    private String submitFromDate;
    private String submitToDate;
    private String dummydate_from;
    LinearLayout ll_sleep_date;
    TextView historydatte;
    TextView txt_avg_food;

    private TextView txt_no_food;
    TextView txt_sleep_date_from, txt_sleep_date_to;
    private String dummydate_to;
    private String StrDateOpen;
    private DatePickerDialog datepickerdialog;
    private CommonService commonService;
    private Utils utils;
    private RelativeLayout ll_avg_food;
    TextView myText;

    LinearLayout ll_avg_nutrition;
    TextView txt_avg_fibre,txt_avg_fat,txt_avg_protine,txt_avg_carb;
    boolean isRsume=false;

    public FoodHistoryFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String str11, String str22, int mParam3, boolean fromWater) {
        FoodHistoryFragment fragment = new FoodHistoryFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food_history2, container, false);
        context=getActivity();
        commonService = Client.getClient().create(CommonService.class);
        utils=new Utils();
         myText = view.findViewById(R.id.txt_no_food );


        historydatte=view.findViewById(R.id.historydatte);
        img_add_sleep_date=view.findViewById(R.id.img_add_sleep_date);
        ll_avg_nutrition=view.findViewById(R.id.ll_avg_nutrition);
        recler_food_history=view.findViewById(R.id.recler_food_history);
        txt_show_sleep_to=view.findViewById(R.id.txt_show_sleep_to);
        txt_show_sleep_from=view.findViewById(R.id.txt_show_sleep_from);
        txt_no_food=view.findViewById(R.id.txt_no_food);
        ll_sleep_date=view.findViewById(R.id.ll_sleep_date);
        txt_avg_food=view.findViewById(R.id.txt_avg_food);
        ll_avg_food=view.findViewById(R.id.ll_avg_food);
        txt_avg_carb=view.findViewById(R.id.txt_avg_carb);
        txt_avg_protine=view.findViewById(R.id.txt_avg_protine);
        txt_avg_fat=view.findViewById(R.id.txt_avg_fat);
        txt_avg_fibre=view.findViewById(R.id.txt_avg_fibre);


        try {
            submitFromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            submitToDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


            dummydate_from = formatDatesSleep(submitFromDate);
//            txt_sleep_date_from.setText(dummydate_from);
            txt_show_sleep_from.setText(dummydate_from);
            dummydate_to = formatDatesSleep(submitToDate);
//            txt_sleep_date_to.setText(dummydate_to);
            txt_show_sleep_to.setText(dummydate_to);
            callFoodHistoryApi(submitFromDate,submitToDate);

        }catch (Exception e){
            e.printStackTrace();
        }


        img_add_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetFoodhistroy();
            }
        });
        ll_sleep_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewgetFoodhistroy();

            }
        });


        return view;
    }
    public String formatDatesSleep(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";
//        2020-07-02T00:00:00

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
    private void showNewgetFoodhistroy() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.dialog_add_sleep_date);
        txt_sleep_date_from_dialog=dialog.findViewById(R.id.txt_sleep_date_from_dialog);
        txt_sleep_date_to_dialog=dialog.findViewById(R.id.txt_sleep_date_to_dialog);


        Button btn_submit_sleep_hours = dialog.findViewById(R.id.btn_submit_sleep_hours);

        btn_submit_sleep_hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Utils.isNetworkAvailable(context)) {

                    dialog.dismiss();

                    callFoodHistoryApi(submitFromDate,submitToDate);
                } else {
                    Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

                }

            }
        });


//        txt_show_sleep_from.setText(dummydate_from);
        txt_sleep_date_from_dialog.setText(dummydate_from);
//        txt_show_sleep_to.setText(dummydate_to);
        txt_sleep_date_to_dialog.setText(dummydate_to);

        showDatePickerDailog();

        txt_sleep_date_from_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="from";
                datepickerdialog.show();

            }
        });

        txt_sleep_date_to_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrDateOpen="to";
                datepickerdialog.show();
            }
        });

        ImageView img_close=dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();


    }
    private void showDatePickerDailog() {
        String strMindate[]=new SessionManager(context).getStringValue("mindate").split("-");


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, FoodHistoryFragment.this, year, month, day);
//        datepickerdialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.MONTH, 3); // Subtract 6 months
        long maxDate = c.getTime().getTime(); // T
//        datepickerdialog.getDatePicker().setMaxDate(maxDate);



        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        Calendar c1 = Calendar.getInstance();
        c1.set(Integer.parseInt(strMindate[2]),Integer.parseInt(strMindate[1])-1,Integer.parseInt(strMindate[0]));//Year,Mounth -1,Day
//        datepickerdialog.getDatePicker().setMinDate(c1.getTimeInMillis());


        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }
    public String getFromattedStringDate(int s){


        String v = String.valueOf(s);

        if (v.length() == 1) {
            return "0" + v;
        } else {
            return v;

        }



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        if (StrDateOpen.equalsIgnoreCase("from")) {

            if (month<10){
                dummydate_from = dayOfMonth + "-" + getFromattedStringDate(month+1) + "-" + year;

            }else {
                dummydate_from = dayOfMonth + "-"+ getFromattedStringDate(month+1) + "-" + year;

            }








            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");

            Date date1 = null;
            Date date2 = null;
            try {
                date1 = simpleDateFormat.parse(dummydate_from);
                date2 = simpleDateFormat.parse(dummydate_to);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (date1.after(date2)) {
                Toast.makeText(context, "From date should be greater than End date", Toast.LENGTH_SHORT).show();
                return;
            }
















//            txt_sleep_date_from.setText(dummydate_from);
            txt_show_sleep_from.setText(dummydate_from);
            txt_sleep_date_from_dialog.setText(dummydate_from);



            submitFromDate=year+"-"+(month+1)+"-"+dayOfMonth;


        }


        if (StrDateOpen.equalsIgnoreCase("to")) {


            if (!dummydate_from.trim().isEmpty()){



                if (month<10){
                    dummydate_to = dayOfMonth + "-" + "0"+(month + 1) + "-" + year;

                }else  {
                    dummydate_to = dayOfMonth + "-" +(month + 1) + "-" + year;

                }




//                    dummydate_to = dayOfMonth + "-" + (month + 1) + "-" + year;




                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = simpleDateFormat.parse(dummydate_from.trim());
                    date2 = simpleDateFormat.parse(dummydate_to.toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if (date1.after(date2)) {
                    Toast.makeText(context, "End date should be greater than Start date", Toast.LENGTH_SHORT).show();
                    return;
                }else {
//                    txt_sleep_date_to.setText(dummydate_to);
                    txt_show_sleep_to.setText(dummydate_to);
                    txt_sleep_date_to_dialog.setText(dummydate_to);

                    submitToDate=year+"-"+(month+1)+"-"+dayOfMonth;

                    callFoodHistoryApi(submitFromDate,submitToDate);

                }






            }else {
                Toast.makeText(context, "Please select Start date", Toast.LENGTH_SHORT).show();
            }



        }


    }

    private void callFoodHistordffyApi(String submitFromDate, String submitToDate) {


    }
    public String formatDates(String dateFromServer) {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return dateFromServer;



    }


    public void callFoodHistoryApi(String submitFromDate, String submitToDate) {
        ClsHistoryRequest clsHistoryRequest=new ClsHistoryRequest();
        clsHistoryRequest.setFromDate(submitFromDate);
        clsHistoryRequest.setToDate(submitToDate);
        clsHistoryRequest.setReeworkerId(String.valueOf(new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID)));


        historydatte.setText(formatDates(txt_show_sleep_from.getText().toString())+" to "+formatDates(txt_show_sleep_to.getText().toString()));

        utils.showProgressbar(context);
        Call<ClsFoodHistoryPojo> call = commonService.getFoodHistoryData(clsHistoryRequest);
        call.enqueue(new Callback<ClsFoodHistoryPojo>()
        {
            @Override
            public void onResponse(Call<ClsFoodHistoryPojo> call, retrofit2.Response<ClsFoodHistoryPojo> response)
            {


                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsFoodHistoryPojo moodResponse = response.body();
                        if (moodResponse.getCode()!=null){

                            if (moodResponse.getCode().equals("1")){

                                ArrayList<Data> dataArrayList=moodResponse.getData();


                                if (dataArrayList!=null){
                                    if (!dataArrayList.isEmpty()){
                                        ll_avg_food.setVisibility(View.VISIBLE);
                                        ll_avg_nutrition.setVisibility(View.VISIBLE);
                                        DecimalFormat decimalFormat = new DecimalFormat("0.0");
                                        double scheduleintake = moodResponse.getAvgFoodCalories();
                                        String showschedulestr = decimalFormat.format(scheduleintake);
                                        txt_avg_food.setText(String.valueOf(showschedulestr)+" kcal");


                                        txt_avg_fibre.setText(String.valueOf(Math.round(moodResponse.getAvgFoodFibre())));
                                        txt_avg_fat.setText(String.valueOf(Math.round(moodResponse.getAvgFoodFat())));
                                        txt_avg_protine.setText(String.valueOf(Math.round(moodResponse.getAvgFoodProtein())));
                                        txt_avg_carb.setText(String.valueOf(Math.round(moodResponse.getAvgFoodCarbs())));


//                                        new DecimalFormat("##.#").format((Double.parseDouble()

                                        txt_no_food.setVisibility(View.GONE);
                                        recler_food_history.setVisibility(View.VISIBLE);
                                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                        recler_food_history.setLayoutManager(layoutManager);

                                        recler_food_history.setAdapter(new FoodHistoryMainAdapter(context,dataArrayList,FoodHistoryFragment.this));

//                                        recler_food_history.setAdapter(new FoodHistoyDifferentRowAdapter(context,dataArrayList,FoodHistoryFragment.this));


                                    }else {
                                        ll_avg_food.setVisibility(View.GONE);
                                        ll_avg_nutrition.setVisibility(View.GONE);

                                        recler_food_history.setVisibility(View.GONE);
                                        txt_no_food.setVisibility(View.VISIBLE);

//                                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
//                                        anim.setDuration(50); //You can manage the blinking time with this parameter
//                                        anim.setStartOffset(20);
//                                        anim.setRepeatMode(Animation.REVERSE);
//                                        anim.setRepeatCount(Animation.INFINITE);
//                                        myText.startAnimation(anim);

                                    }

                                }else {
                                    ll_avg_food.setVisibility(View.GONE);

                                }



                            }else {



                            }



                        }else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsFoodHistoryPojo> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });






    }



    @Override
    public void onResume() {
        super.onResume();
        if (isRsume){
            if (context!=null){
                if (submitFromDate!=null){
                    callFoodHistoryApi(submitFromDate,submitToDate);

                }
            }
            isRsume=false;
        }

        isRsume=true;

    }



    @Override
    public void getCallData() {
        if (context!=null){
            if (submitFromDate!=null){
                callFoodHistoryApi(submitFromDate,submitToDate);

            }
        }
    }
}
