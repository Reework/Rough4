package com.shamrock.reework.activity.BloodTestModule.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.api.response.BloodReportItem;

import java.util.List;

public class BloodReportAdapter extends BaseAdapter {

    Context mContext;
    List<BloodReportItem> list;
    private static LayoutInflater inflater = null;
    int colorBlue;

    public BloodReportAdapter(Context context, List<BloodReportItem> subscriptionFeatureArrayList) {
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
    public BloodReportItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_blood_report_item, null);

        TextView title = vi.findViewById(R.id.textBlood_Name);
        TextView percent = vi.findViewById(R.id.textBlood_Percent);
        ImageView imgPDF = vi.findViewById(R.id.imgView_PDF);

        final BloodReportItem song = list.get(i);
        String featureName = song.getUploadDate();
        final String reportLink = song.getReportLink();

//        Spannable wordtoSpan = new SpannableString(featurePercent);
//        wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, featurePercent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        title.setText(featureName);
        percent.setText(reportLink);

        imgPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                Toast.makeText(mContext, "" + song.getReportLink(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, PdfViewerActivity.class);
                intent.putExtra("pdfLink", reportLink);
                mContext.startActivity(intent);
            }
        });
        return vi;
    }
}
