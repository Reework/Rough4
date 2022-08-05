package com.shamrock.reework.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.State;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyHolder> {

    Context context;
    List<State> stateList;
    StateListener listener;

    public interface StateListener
    {
        public void GetStatePosition(int pos, State model);
    }

    public StateAdapter(Context context, ArrayList<State> stateList, StateListener listener)
    {
        this.context = context;
        this.stateList = stateList;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvName;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvName = itemView.findViewById(R.id.spinner_textView);
        }

        @Override
        public void onClick(View v)
        {
            listener.GetStatePosition(getAdapterPosition(), stateList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public StateAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.dialog_adapter, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.MyHolder myHolder, int i)
    {
        if (!TextUtils.isEmpty(stateList.get(i).getStateName()))
            myHolder.tvName.setText(stateList.get(i).getStateName());
    }

    @Override
    public int getItemCount()
    {
        return stateList.size();
    }


    /*LayoutInflater inflter;

    public StateAdapter(Context context, List<State> stateList) {
        this.context = context;
        this.stateList = stateList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return stateList.size();
    }

    @Override
    public State getItem(int i) {
        return stateList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.simple_spinner_item, null);
        TextView names = view.findViewById(R.id.spinner_textView);
        names.setText(stateList.get(i).getStateName());
        return view;
    }*/
}
