package com.shamrock.reework.activity.MyProfileModule.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.BCAModule.adapter.ChildDirectoryAdapter;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.TestList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeficienceAdapter extends RecyclerView.Adapter<DeficienceAdapter.MyHolder> {
    Context mContext;
    ArrayList<BCAResponce.TestDetails> list;
    int colorBlue;
    List<BCAResponce.TestDetails> listOfStudentDataItems;
    private int expandedPosition = -1;
    private List<TestList> TestList;

    public DeficienceAdapter(Context context, ArrayList<BCAResponce.TestDetails> list) {
        this.list = list;
        mContext = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        RecyclerView recyclerView;
        RelativeLayout linParent;
        LinearLayout linChildView;
        ImageView imgView_PDF;
        TextView txtName,txtValue,txtRange,txtScore,txtRemark,txt_groupname;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setClickable(true);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            txtValue=(TextView)itemView.findViewById(R.id.txtValue);
            txtRange=(TextView)itemView.findViewById(R.id.txtRange);
            txtScore=(TextView)itemView.findViewById(R.id.txtScore);
            txtRemark=(TextView)itemView.findViewById(R.id.txtRemark);
//            tvTitle = itemView.findViewById(R.id.txt_name);
//            recyclerView = itemView.findViewById(R.id.recyclerView);
//            linParent = itemView.findViewById(R.id.linParent);
//            linChildView = itemView.findViewById(R.id.linChildView);
//            imgView_PDF = itemView.findViewById(R.id.imgView_PDF);
            //  tvDate = itemView.findViewById(R.id.textBCA_Percent);



        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.content_deficience_row, viewGroup, false);
        return new MyHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {


            if (list.get(i).getRemark() != null) {
            if (list.get(i).getRemark().equals("Above average.") || list.get(i).getRemark().equals("Required attention.")
                    || list.get(i).getRemark().equals("Needs attention.") || list.get(i).getRemark().equals("Needs intervention.")
                    || list.get(i).getRemark().equals("Below Normal.") || list.get(i).getRemark().equals("Need to contact your REEcoach.")
                    || list.get(i).getRemark().equals("Talk to your REEcoach") || list.get(i).getRemark().equals("Improvement required.")
                    || list.get(i).getRemark().equals("Intervention required.") || list.get(i).getRemark().equals("Slightly above normal.")
                    || list.get(i).getRemark().equals("Talk to REEcoach.") || list.get(i).getRemark().equals("Urgent attention required.")
                    || list.get(i).getRemark().equals("Pre-obese, need to take precautions.") || list.get(i).getRemark().equals("Overweight.")
                    || list.get(i).getRemark().equals("Need to talk to REEcoach.") || list.get(i).getRemark().equals("Hypertension stage 1, need to discuss with REEcoach.")
                    || list.get(i).getRemark().equals("Just below normal.") || list.get(i).getRemark().equals("Attention required.")
                    || list.get(i).getRemark().equals("Reactive, Needs Attention.") || list.get(i).getRemark().equals("Need to contact REEcoach.")
                    || list.get(i).getRemark().equals("Need to be careful.") || list.get(i).getRemark().equals("Obese Type I, talk to your REEcoach.")
                    || list.get(i).getRemark().equals("Moderate risk.") || list.get(i).getRemark().equals("Hypertension.")
                    || list.get(i).getRemark().equals("Pre-hypertension, need to take precautions.")) {


                if (list.get(i).getTestName() != null) {
                    myHolder.txtName.setText(list.get(i).getTestName());
                }
                if (list.get(i).getTestValue() != null) {
                    myHolder.txtValue.setText(list.get(i).getTestValue());
                }
                if (list.get(i).getNormalRange() != null) {
                    myHolder.txtRange.setText(list.get(i).getNormalRange());
                }
//        if (mStudentDataList.get(position).getScore()!=null){
//            viewHolder.txtScore.setText(mStudentDataList.get(position).getScore());
////
//        }
//
                if (list.get(i).getRemark() != null) {
                    myHolder.txtRemark.setText(list.get(i).getRemark());
                }


            }


        }






    }

    @Override
    public int getItemCount() {
        return list.size();
    }
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
    /*
    private static LayoutInflater inflater = null;
    int colorBlue;



    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_my_bca_report_item, null);

        TextView title = vi.findViewById(R.id.textBCA_Name);
        TextView date = vi.findViewById(R.id.textBCA_Percent);

        BCAReportItem song = list.get(i);
        String featureName = song.getName();
        String featurePercent = song.getPercent();

        Spannable wordtoSpan = new SpannableString(featurePercent);
        wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, featurePercent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(featureName);
        date.setText(wordtoSpan);
        return vi;
    }*/


//    public String formatDates(String dateFromServer)
//    {
//        //String strDate = "2013-05-15T10:00:00-0700";
////        2020-07-02T00:00:00
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = null;
//        try
//        {
//            date = dateFormat.parse(dateFromServer);
//            return newDateFormatter.format(date);
//        } catch (ParseException e) { e.printStackTrace(); }
//        return "N/A";
//    }
}
