package com.shamrock.reework.activity.recipeanalytics;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.recipe.model.ClsSelectedIngradientDetails;
import com.shamrock.reework.api.response.FoodTripResponse;

import java.util.ArrayList;

import retrofit2.Callback;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class
ClsRecipeAnalyticsListAdapter extends RecyclerView.Adapter<ClsRecipeAnalyticsListAdapter.MyHolder> implements Filterable
{

    Context context;
    ArrayList<RecipeAnalyticResult> languages;
    OnGetClickData listener;
    ArrayList<RecipeAnalyticResult> mFilterlist;
    ArrayList<RecipeAnalyticResult> mArrayList;

    public interface OnGetClickData {
        public void getData(RecipeAnalyticResult adapterPosition);
    }

    public ClsRecipeAnalyticsListAdapter(Context context, ArrayList<RecipeAnalyticResult> languages)
    {
        this.context = context;
        this.languages = languages;
        mFilterlist = languages;
        mArrayList = languages;
        listener= (OnGetClickData) context;
//        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        private ImageView img_recipe_logo,ivIsVegOrNonveg;
        private TextView txt_recipe_name,txt_recipe_kcal;
        TextView txt_category,txt_recipe_type;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
//            ivIsVegOrNonveg=itemView.findViewById(R.id.ivIsVegOrNonveg);
//            img_recipe_logo=itemView.findViewById(R.id.img_recipe_logo);
            txt_recipe_type=itemView.findViewById(R.id.txt_recipe_type);
            txt_category=itemView.findViewById(R.id.txt_category);
            txt_recipe_name=itemView.findViewById(R.id.txt_recipe_name);
            txt_recipe_kcal=itemView.findViewById(R.id.txt_recipe_kcal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.getData(mFilterlist.get(getAdapterPosition()));
                }
            });

        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ClsRecipeAnalyticsListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.row_recipe_library, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClsRecipeAnalyticsListAdapter.MyHolder myHolder, int i)
    {


if (mFilterlist.get(i).getIsVeg()!=null){
    if (mFilterlist.get(i).getIsVeg().equalsIgnoreCase("true")){
        myHolder.txt_recipe_type.setText("Veg");
    }else {
        myHolder.txt_recipe_type.setText("Non-Veg");

    }
}else {
    myHolder.txt_recipe_type.setText("");

}

myHolder.txt_category.setSelected(true);
        if (mFilterlist.get(i).getCategory()!=null){
            myHolder.txt_category.setText(mFilterlist.get(i).getCategory());
        }
        myHolder.txt_recipe_name.setText(mFilterlist.get(i).getItemName());

        if (mFilterlist.get(i).getIsVeg()!=null&&!mFilterlist.get(i).getIsVeg().isEmpty()){
//            myHolder.ivIsVegOrNonveg.setVisibility(View.VISIBLE);

            if (mFilterlist.get(i).getIsVeg().equalsIgnoreCase("true")){
//                myHolder.ivIsVegOrNonveg.setBackgroundResource(R.drawable.veg_icon);

            }else {
//                myHolder.ivIsVegOrNonveg.setBackgroundResource(R.drawable.fibre_circle);

            }
        }else {
//            myHolder.ivIsVegOrNonveg.setVisibility(View.INVISIBLE);
        }

        myHolder.txt_recipe_kcal.setText(mFilterlist.get(i).getCalories()+"");
myHolder.txt_recipe_name.setSelected(true);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.defaultmedicine)
                .error(R.drawable.defaultmedicine)
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .priority(Priority.HIGH);

//        if (isValidContextForGlide(context)) {
//            Glide.with(context)
//                    .load(mFilterlist.get(i).getImage())
//                    .apply(options.circleCrop())
//                    .listener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
//                                                    Target<Drawable> target, boolean isFirstResource) {
////                            progressBar.setVisibility(View.GONE);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
//                                                       DataSource dataSource, boolean isFirstResource) {
////                            progressBar.setVisibility(View.GONE);
//                            return false;
//                        }
//                    })
//                    .into(myHolder.img_recipe_logo);
//        }

    }

    @Override
    public int getItemCount()
    {
        return mFilterlist.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilterlist = mArrayList;
                } else {

                    ArrayList<RecipeAnalyticResult> filterData = new ArrayList<>();
                    for (RecipeAnalyticResult row : mArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getItemName()!=null && row.getItemName().toLowerCase().contains(charString.toLowerCase())) {

                            filterData.add(row);
                        }
                    }

                    mFilterlist = filterData;
                    if(filterData.size()==0){
                        Toast.makeText(context,"Recipe name not found",Toast.LENGTH_SHORT).show();
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                /*  list = (ArrayList<FoodTripResponse.FoodStripData>) results.values;*/
                mFilterlist = (ArrayList<RecipeAnalyticResult>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
