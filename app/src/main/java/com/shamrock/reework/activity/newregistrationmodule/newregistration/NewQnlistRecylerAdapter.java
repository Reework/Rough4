package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.AnalysisModule.activity.userQuestiondata.QuestionData;

import java.util.ArrayList;

public class NewQnlistRecylerAdapter extends RecyclerView.Adapter<NewQnlistRecylerAdapter.ViewHolder> {

    Context mContext;
    ArrayList<QuestionData> list;
    private static LayoutInflater inflater = null;
    int colorBlue;
    TypedArray arrayDrawable, arrayText;
    OnRadioBtnYesClick onRadioBtnYesClick;

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView textName;
        TextView textClock;
        Spinner spinner;
        RadioButton radio_btn_yes,radio_btn_no;
        TextView txt_qn_text;

        public ViewHolder(View view) {
            super(view);
            radio_btn_yes=view.findViewById(R.id.radio_btn_yes);
             radio_btn_no=view.findViewById(R.id.radio_btn_no);
             txt_qn_text=view.findViewById(R.id.txt_qn_text);
        }
    }

    public NewQnlistRecylerAdapter(Context context, ArrayList<QuestionData> rArrayList) {
        this.list = rArrayList;
        mContext = context;
        onRadioBtnYesClick= (OnRadioBtnYesClick) context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }












    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.activity_row_qn, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.txt_qn_text.setText(""+(i+1)+". "+list.get(i).getQuestion());
        viewHolder.radio_btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onRadioBtnYesClick.getSelectedID(Integer.parseInt(list.get(position).getIds()));
            }
        });
        viewHolder.radio_btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onRadioBtnYesClick.notselected(Integer.parseInt(list.get(position).getIds()));
            }
        });

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
