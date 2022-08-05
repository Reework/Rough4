package com.shamrock.reework.activity.FaqActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.activity.AdditionalDetails;

import java.util.ArrayList;

public class FAQListAdapter extends RecyclerView.Adapter<FAQListAdapter.MyHolder>
{
    Context context;
    ArrayList<ClsFAQData> list;
    int selectedPosition=-1;
    public FAQListAdapter(Context context, ArrayList<ClsFAQData> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_qn_faq,txt_answer_faq;
        ImageView img_arraow_faq;
        RecyclerView recyler_answerer;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_answer_faq = itemView.findViewById(R.id.txt_answer_faq);
            txt_qn_faq = itemView.findViewById(R.id.txt_qn_faq);
            img_arraow_faq = itemView.findViewById(R.id.img_arraow_faq);
            recyler_answerer = itemView.findViewById(R.id.recyler_answerer);

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_faq_data, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i)
    {
        ClsFAQData model = list.get(i);
        myHolder.txt_qn_faq.setText(model.getQuestion());
        myHolder.txt_answer_faq.setText(model.getAnswer());
        myHolder.img_arraow_faq.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down_grey));

        myHolder.txt_qn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myHolder.txt_answer_faq.getVisibility()==View.VISIBLE){
                    myHolder.img_arraow_faq.setRotation(0f);

                    myHolder.txt_answer_faq.setVisibility(View.GONE);
                }else {
                    myHolder.img_arraow_faq.setRotation(180f);

                    myHolder.txt_answer_faq.setVisibility(View.VISIBLE);

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
