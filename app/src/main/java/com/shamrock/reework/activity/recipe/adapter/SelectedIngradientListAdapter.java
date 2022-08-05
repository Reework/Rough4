package com.shamrock.reework.activity.recipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.recipe.model.ClsSelectedIngradientDetails;

import java.util.ArrayList;

public class
SelectedIngradientListAdapter extends RecyclerView.Adapter<SelectedIngradientListAdapter.MyHolder>
{

    Context context;
    ArrayList<ClsSelectedIngradientDetails> languages;
    OnEditIngradeitn listener;

    public interface OnEditIngradeitn {
        public void geteditingradData(ClsSelectedIngradientDetails cusineList, int adapterPosition);
    }

    public SelectedIngradientListAdapter(Context context, ArrayList<ClsSelectedIngradientDetails> languages)
    {
        this.context = context;
        this.languages = languages;
//        this.listener = listener;
        listener= (OnEditIngradeitn) context;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView txt_remarks,txt_qty,txt_unit,txt_ingradeint_name;
        ImageView img_edit_ingrad;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_remarks = itemView.findViewById(R.id.txt_remarks);
            txt_qty = itemView.findViewById(R.id.txt_qty);
            txt_unit = itemView.findViewById(R.id.txt_unit);
            txt_ingradeint_name = itemView.findViewById(R.id.txt_ingradeint_name);
            img_edit_ingrad = itemView.findViewById(R.id.img_edit_ingrad);
            img_edit_ingrad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.geteditingradData(languages.get(getAdapterPosition()),getAdapterPosition());
                }

            });
        }


    }

    @NonNull
    @Override
    public SelectedIngradientListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_ingradeint, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedIngradientListAdapter.MyHolder myHolder, int i)
    {

            myHolder.txt_ingradeint_name.setText(languages.get(i).getIngradientName());
            myHolder.txt_unit.setText(languages.get(i).getUomName());
            myHolder.txt_remarks.setText(languages.get(i).getRemarks());
            myHolder.txt_qty.setText(String.valueOf(languages.get(i).getQuantity()));
    }

    @Override
    public int getItemCount()
    {
        return languages.size();
    }
}
