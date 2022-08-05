package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;

import java.util.List;

/**
 * Created by Rahul on 05,June,2020
 */
public class JoinNewstepAdapter extends RecyclerView.Adapter<JoinNewstepAdapter.ViewHolder> {
    private List<JoinNew.Datum> data;
    Context mcontext;
    public JoinNewstepAdapter(List<JoinNew.Datum> dataList) {
        data = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.joinnew_step_adapter,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.index.setText(data.get(position).getIndex().toString());
            holder.text.setText(data.get(position).getSteps().toString().trim());
        }catch (Exception ex)
        {

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,index;
        public ViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.text);
            index=(TextView)itemView.findViewById(R.id.index);
        }
    }
}

