package com.shamrock.reework.activity.FoodModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shamrock.R;
import com.shamrock.reework.api.response.RecipeResponse;

import java.util.ArrayList;
import java.util.List;

public class FoodRecipeIngridentAdapter extends RecyclerView.Adapter<FoodRecipeIngridentAdapter.MyHolder>
{
    Context context;
    ArrayList<RecipeResponse.Recipee> list;
    IngridentAdapter adapter;

    public FoodRecipeIngridentAdapter(Context context, ArrayList<RecipeResponse.Recipee> list)
    {
        this.context = context;
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        RecyclerView recyclerView;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            recyclerView = (RecyclerView) itemView.findViewById(R.id.tvList);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_food_recipe_ingrident, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        RecipeResponse.Recipee model = list.get(i);

        List<RecipeResponse.RecipeIngrdient> ingList = model.getRecipeIngrdients();

        adapter = new IngridentAdapter(context, ingList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myHolder.recyclerView.setLayoutManager(layoutManager);
        myHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        myHolder.recyclerView.setAdapter(adapter);

        myHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
