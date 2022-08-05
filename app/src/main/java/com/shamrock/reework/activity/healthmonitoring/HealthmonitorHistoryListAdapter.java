package com.shamrock.reework.activity.healthmonitoring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.recipe.model.ClsSelectedIngradientDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class
HealthmonitorHistoryListAdapter extends RecyclerView.Adapter<HealthmonitorHistoryListAdapter.MyHolder>
{

    Context context;
    ArrayList<ClsCustomTestList> languages;
    OnEditTestLinstenr listener;

    public interface OnEditTestLinstenr {
        public void geteditinTest(ClsCustomTestList adapterPosition);
        public void getDeleteTest(ClsCustomTestList adapterPosition);
    }

    public HealthmonitorHistoryListAdapter(Context context, ArrayList<ClsCustomTestList> languages)
    {
        this.context = context;
        this.languages = languages;
//        this.listener = listener;
        listener= (OnEditTestLinstenr) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_remark,txt_testvalue,txt_date,txt_name,txt_edit,txt_delete;
        //        ImageView img_edit_test,txt_edit_reemonitor,txt_delete_reemonitor;
        TextView edt_updated_unit;


        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_date = itemView.findViewById(R.id.txt_date);
//            txt_edit_reemonitor = itemView.findViewById(R.id.txt_edit_reemonitor);
//            txt_delete_reemonitor = itemView.findViewById(R.id.txt_delete_reemonitor);
//            txt_remark = itemView.findViewById(R.id.txt_remark);
            txt_testvalue = itemView.findViewById(R.id.txt_testvalue);
            txt_name = itemView.findViewById(R.id.txt_name);
//            txt_testname = itemView.findViewById(R.id.txt_testname);
//            img_edit_test = itemView.findViewById(R.id.img_edit_test);
            txt_edit = itemView.findViewById(R.id.txt_edit);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            txt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.geteditinTest(languages.get(getAdapterPosition()));
                }

            });
            txt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.getDeleteTest(languages.get(getAdapterPosition()));
                }

            });
        }


    }

    @NonNull
    @Override
    public HealthmonitorHistoryListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_reemonitor_history_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthmonitorHistoryListAdapter.MyHolder myHolder, int i)
    {

        try {
//            myHolder.txt_testname.setText("Test Name  :  " + languages.get(i).getTestName());
            myHolder.txt_name.setText(languages.get(i).getTestName());
            myHolder.txt_testvalue.setText("Value : " + Math.round(Float.parseFloat(languages.get(i).getTestValue())) + " " + languages.get(i).getUnit());
//            myHolder.txt_remark.setText(" Remark       :  " + languages.get(i).getRemark());
            myHolder.txt_date.setText( formatDates(languages.get(i).getDate())+" "+ languages.get(i).getLabTime());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //2020-11-11T09:54:37
    @Override
    public int getItemCount()
    {
        return languages.size();
    }

    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy ");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return " ";
    }

}
