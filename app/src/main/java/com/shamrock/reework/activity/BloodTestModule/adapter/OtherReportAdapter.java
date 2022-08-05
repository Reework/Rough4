package com.shamrock.reework.activity.BloodTestModule.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.activity.BloodTestModule.pojo.OtherReportData;
import com.shamrock.reework.api.response.BloodReportItem;

import java.util.List;

public class OtherReportAdapter extends BaseAdapter {

    Context mContext;
    List<OtherReportData> list;
    private static LayoutInflater inflater = null;
    int colorBlue;

    public OtherReportAdapter(Context context, List<OtherReportData> subscriptionFeatureArrayList) {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        colorBlue = ContextCompat.getColor(mContext, R.color.colorRobinEggBlue);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public OtherReportData getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_other_report_item, null);

        TextView title = vi.findViewById(R.id.textBlood_Name);

        final OtherReportData song = list.get(i);
        String featureName = song.getReportName();
        final String reportLink = song.getReportFilePath();

//        Spannable wordtoSpan = new SpannableString(featurePercent);
//        wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, featurePercent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        title.setText(featureName);

        ImageView imgotherreport=vi.findViewById(R.id.imgotherreport);
        if (featureName.endsWith("jpeg")||featureName.endsWith("png")){
            imgotherreport.setImageResource(R.drawable.picture);
        }

        if (featureName.endsWith("xlsx")||featureName.endsWith("txt")){
            imgotherreport.setImageResource(R.drawable.files);
        }

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (list.get(i).getFileType().equalsIgnoreCase("pdf")||list.get(i).getFileType().equalsIgnoreCase("docx")||list.get(i).getFileType().equalsIgnoreCase("xlsx")){
                    if (list!=null&&!list.isEmpty()){
                        Intent intent = new Intent(mContext, PdfViewerActivity.class);
                        intent.putExtra("pdfLink", reportLink);
                        mContext.startActivity(intent);
                    }


                }
                else {
                    if (list!=null&&!list.isEmpty()){
                        Intent intent = new Intent(mContext, ImageViewReportActivity.class);
                        intent.putExtra("ImageLink", reportLink);
                        mContext.startActivity(intent);
                    }




                }

            }
        });
        return vi;
    }
}
