package com.shamrock.reework.activity.AnalysisModule.activity.newregistration;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.shamrock.R;

import java.util.ArrayList;

public class NewQnlistAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ClsRegisterQutn> list;
    private static LayoutInflater inflater = null;
    int colorBlue;
    TypedArray arrayDrawable, arrayText;
    OnRadioBtnYesClick onRadioBtnYesClick;

    class ViewHolder {
        CheckBox checkBox;
        ImageView icon;
        TextView textName;
        TextView textClock;
        Spinner spinner;
    }

    public NewQnlistAdapter(Context context, ArrayList<ClsRegisterQutn> rArrayList) {
        this.list = rArrayList;
        mContext = context;
        onRadioBtnYesClick= (OnRadioBtnYesClick) context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.activity_row_qn, null);

            RadioButton radio_btn_yes=rowView.findViewById(R.id.radio_btn_yes);
            RadioButton radio_btn_no=rowView.findViewById(R.id.radio_btn_no);
            TextView txt_qn_text=rowView.findViewById(R.id.txt_qn_text);
            txt_qn_text.setText(list.get(position).getQuestion());

            radio_btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioBtnYesClick.getSelectedID(Integer.parseInt(list.get(position).getIds()));

                }
            });
            radio_btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioBtnYesClick.notselected(Integer.parseInt(list.get(position).getIds()));


                }
            });






        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }



        return rowView;
    }
}
