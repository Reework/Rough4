package com.shamrock.reework.activity.health;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.PdfViewerActivity;
import com.shamrock.reework.activity.BloodTestModule.adapter.ImageViewReportActivity;
import com.shamrock.reework.activity.spirituallibrary.listenres.OnVideoCLick;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualData;

import java.util.ArrayList;

public class HealthListAdapter extends RecyclerView.Adapter<HealthListAdapter.MyHolder>
{
    Context context;
    ArrayList<Data> list;
    OnVideoCLick onHealthCatoryClick;
    int selectedPosition=-1;
    boolean isFirstTime=true;
    public HealthListAdapter(Context context, ArrayList<Data> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
//        TextView KnowledgeBank_name,description,filename;
        TextView tvTitle, tvDate;
//        RecyclerView recyclerView;
        RelativeLayout linParent;
//        LinearLayout linChildView;
        ImageView imgView_PDF;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

//            KnowledgeBank_name = itemView.findViewById(R.id.KnowledgeBank_name);
//            description = itemView.findViewById(R.id.description);
//            filename = itemView.findViewById(R.id.filename);

            tvTitle = itemView.findViewById(R.id.textDate);
//            recyclerView = itemView.findViewById(R.id.recyclerView);
            linParent = itemView.findViewById(R.id.linParent);
//            linChildView = itemView.findViewById(R.id.linChildView);
            imgView_PDF = itemView.findViewById(R.id.imgView_PDF);


        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_my_bca_report_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i)
    {
        Data model = list.get(i);
//        myHolder.KnowledgeBank_name.setText(model.getKnowledgeBankName());
//        myHolder.description.setText(model.getDescription());
        myHolder.tvTitle.setText(model.getFileName());

        myHolder.imgView_PDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).getFilePath().endsWith("pdf")||list.get(i).getFilePath().endsWith("docx")||list.get(i).getFilePath().endsWith("xlsx")){
                    if (list!=null&&!list.isEmpty()){
                        Intent intent = new Intent(context, PdfViewerActivity.class);
                        intent.putExtra("pdfLink", list.get(i).getFilePath());
                        context.startActivity(intent);
                    }


                }else if (list.get(i).getFilePath().endsWith(".jpg")||list.get(i).getFilePath().endsWith(".png")){
                    if (list!=null&&!list.isEmpty()){
                        Intent intent = new Intent(context, ImageViewReportActivity.class);
                        intent.putExtra("ImageLink", list.get(i).getFilePath());
                        context.startActivity(intent);
                    }
                }
                else {
                    if (list!=null&&!list.isEmpty()){
                        Intent intent = new Intent(context, ImageViewReportActivity.class);
                        intent.putExtra("ImageLink", list.get(i).getFilePath());
                        context.startActivity(intent);
                    }




                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



}
