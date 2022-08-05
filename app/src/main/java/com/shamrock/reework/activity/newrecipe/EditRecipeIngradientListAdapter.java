package com.shamrock.reework.activity.newrecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.activity.cheatplan.pojo.Plans;
import com.shamrock.reework.activity.recipe.model.IngradientData;

import java.util.ArrayList;

public class EditRecipeIngradientListAdapter extends RecyclerView.Adapter<EditRecipeIngradientListAdapter.MyHolder>
{
    Context context;
    ArrayList<Ingredients> arlst_ingrdient;
    String occastion;
    String category;
    OnRecipeEditListener   onRecipeEditListener;
    public EditRecipeIngradientListAdapter(NewRecipeEditDialouge context, ArrayList<Ingredients> arlst_ingrdient)
    {
        this.arlst_ingrdient=arlst_ingrdient;
        onRecipeEditListener= context;

    }

    public class MyHolder extends RecyclerView.ViewHolder
    {

        TextView txt_ingrad_name,txt_quantiy_ingradient,txt_uom_ingradient,txt_edit_ingradient;
        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            txt_ingrad_name = itemView.findViewById(R.id.txt_ingrad_name);
            txt_quantiy_ingradient = itemView.findViewById(R.id.txt_quantiy_ingradient);
            txt_uom_ingradient = itemView.findViewById(R.id.txt_uom_ingradient);
            txt_edit_ingradient = itemView.findViewById(R.id.txt_edit_ingradient);
            txt_edit_ingradient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    onRecipeEditListener.onEditIngradient(arlst_ingrdient.get(getAdapterPosition()),getAdapterPosition());
                }
            });

        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_new_ingradeints, viewGroup, false);
        

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        Ingredients  model = arlst_ingrdient.get(i);
        myHolder.txt_ingrad_name.setText(arlst_ingrdient.get(i).getIngredientName());
        myHolder.txt_quantiy_ingradient.setText(arlst_ingrdient.get(i).getQuantity());
        myHolder.txt_uom_ingradient.setText(arlst_ingrdient.get(i).getUomName());





    }

    @Override
    public int getItemCount()
    {
        return arlst_ingrdient.size();
    }


}
