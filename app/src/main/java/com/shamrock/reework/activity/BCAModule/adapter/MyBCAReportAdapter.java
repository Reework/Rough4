package com.shamrock.reework.activity.BCAModule.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.TestList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyBCAReportAdapter extends RecyclerView.Adapter<MyBCAReportAdapter.MyHolder>
{
    Context mContext;
    List<BCAResponce.Datum> list;
    int colorBlue;
    List<BCAResponce.TestDetails>listOfStudentDataItems;
    private int expandedPosition = -1;
    private List<TestList> TestList;

    public MyBCAReportAdapter(Context context, List<BCAResponce.Datum> list)
    {
        this.list = list;
        mContext = context;
        colorBlue = ContextCompat.getColor(context, R.color.colorRobinEggBlue);
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvTitle, tvDate;
        RecyclerView recyclerView;
        RelativeLayout linParent;
        LinearLayout linChildView;
        ImageView imgView_PDF;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            tvTitle = itemView.findViewById(R.id.textDate);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            linParent = itemView.findViewById(R.id.linParent);
            linChildView = itemView.findViewById(R.id.linChildView);
            imgView_PDF = itemView.findViewById(R.id.imgView_PDF);
          //  tvDate = itemView.findViewById(R.id.textBCA_Percent);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.list_row_my_bca_report_item, viewGroup, false);
        return new MyHolder(view);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i)
    {
       try
       {
           /** expand collapse view*/
           if (i  == expandedPosition) {
               myHolder.linChildView.setVisibility(View.VISIBLE);

           } else {
               myHolder.linChildView.setVisibility(View.GONE);

           }
           BCAResponce.Datum  model = list.get(i);
           String createdOn = model.getCreatedOn();

           //set Created date with removing T-seconds
           if(model.getCreatedOn()!=null){
               if(model.getCreatedOn().contains("T")){
                   try {
                       int index = model.getCreatedOn().indexOf("T");

                       myHolder.tvTitle.setText(formatDates(model.getCreatedOn().substring(0,index)));
                   } catch (Exception e) {
                       int index = model.getCreatedOn().indexOf("T");

                       myHolder.tvTitle.setText((model.getCreatedOn().substring(0,index)));

                       e.printStackTrace();
                   }
               }

           }
           //String featurePercent = String.valueOf(model.getPercent());

          /* Spannable wordtoSpan = new SpannableString(featurePercent);
           wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, featurePercent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        /*   myHolder.tvTitle.setText(createdOn);*/

           //myHolder.tvDate.setText(wordtoSpan);
           TestList = list.get(i).getTestList();

           listOfStudentDataItems = list.get(i).getTestList().get(0).getTestDetails();

           for(int j  = 0;j<listOfStudentDataItems.size();j++){
//               list.get(i).setFilePath(listOfStudentDataItems.get(j).getFilePath());
           }

           if(listOfStudentDataItems!=null) {
               if(listOfStudentDataItems.size()!=0){
                   ChildDirectoryAdapter ChildDirectoryAdapter = new ChildDirectoryAdapter(mContext, listOfStudentDataItems, TestList);
                   myHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                   myHolder.recyclerView.setAdapter(ChildDirectoryAdapter);
                   myHolder.recyclerView.setHasFixedSize(true);
                   myHolder.recyclerView.setNestedScrollingEnabled(false);
               }
           }
           myHolder.linParent.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   // Check for an expanded view, collapse if you find one
                   if (expandedPosition >= 0) {
                       int prev = expandedPosition;
                       notifyItemChanged(prev);
                   }
                   if (myHolder.linChildView.getVisibility() == View.VISIBLE) {
                       expandedPosition = -1;
                   } else {
                       // Set the current position to "expanded"
                       expandedPosition = myHolder.getPosition();
                       notifyItemChanged(expandedPosition);
                   }
               }
           });

           myHolder.imgView_PDF.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(list.get(i).getFilePath()!=null) {
                       Intent intent = new Intent(mContext, PdfViewerActivity.class);
//                       intent.putExtra("pdfLink", list.get(i).getFilePath());
                       intent.putExtra("pdfLink", list.get(i).getFilePath());
                       intent.putExtra("postdate", list.get(i).getCreatedOn());
                       intent.putExtra("name",list.get(i).getReportType());
                       mContext.startActivity(intent);
                   }else{

                   }
               }
           });
       }

       catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

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



    public String formatDates(String dateFromServer)
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
}
