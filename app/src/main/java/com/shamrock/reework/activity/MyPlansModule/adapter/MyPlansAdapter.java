package com.shamrock.reework.activity.MyPlansModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.MyPlanResponse;

import java.util.ArrayList;

public class MyPlansAdapter extends RecyclerView.Adapter<MyPlansAdapter.MyHolder>
{
    private Context context;
    private ArrayList<MyPlanResponse.MyPlanData> dataList;
    private CheckBoxListener checkBoxListener;
    private PlanEditListener planEditListener;

    private boolean isDone = false;
    int planId;

    public interface CheckBoxListener
    {
        void isCheckBoxSelected(int pos, boolean isDone);
    }

    public interface PlanEditListener
    {
        void editedPlan(int pos, ArrayList<MyPlanResponse.MyPlanData> list);
    }

    public MyPlansAdapter(Context context,
                          ArrayList<MyPlanResponse.MyPlanData> dataList, CheckBoxListener checkBoxListener,
                          int planId, PlanEditListener planEditListener)
    {
        this.context = context;
        this.dataList = dataList;
        this.checkBoxListener = checkBoxListener;
        this.planId = planId;
        this.planEditListener = planEditListener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvShedule,tvTime, tvPlan, tvQty;
        ImageView ivEdit, ivChecked, ivActivityDone;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvShedule = itemView.findViewById(R.id.tvSheduleName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPlan = itemView.findViewById(R.id.tvPlan);
            tvQty = itemView.findViewById(R.id.tvQty);
            ivActivityDone = itemView.findViewById(R.id.ivSelected);
            ivChecked = itemView.findViewById(R.id.imgMyPlan_check);
            ivEdit = itemView.findViewById(R.id.ivEdit);

            ivActivityDone.setOnClickListener(this);
            ivEdit.setOnClickListener(this);

            if (planId > 0)
            {
                ivActivityDone.setVisibility(View.INVISIBLE);
                ivEdit.setVisibility(View.INVISIBLE);
                ivChecked.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.ivSelected:

                    boolean status = dataList.get(getAdapterPosition()).getAction();

                    if (status)
                    {
                        isDone = false;
                        dataList.get(getAdapterPosition()).setAction(false);
                        ivChecked.setSelected(false);
                        checkBoxListener.isCheckBoxSelected(getAdapterPosition(), isDone);
                    }
                    else
                    {
                        isDone = true;
                        dataList.get(getAdapterPosition()).setAction(true);
                        ivChecked.setSelected(true);
                        checkBoxListener.isCheckBoxSelected(getAdapterPosition(), isDone);
                    }
                    ivActivityDone.setSelected(dataList.get(getAdapterPosition()).getAction());
                    break;

                case R.id.ivEdit:

                    planEditListener.editedPlan(getAdapterPosition(), dataList);
                    break;
            }
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_myplans, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        String lsTime, lsPlan, lsQty;

        MyPlanResponse.MyPlanData model = dataList.get(i);

        /* SHEDULE NAME */
        String shedule = model.getScheduleName();
        if (!TextUtils.isEmpty(shedule))
            myHolder.tvShedule.setText(shedule);

        /* TIME */
        if (!TextUtils.isEmpty((String) model.getActualTime()))
        {
            lsTime = (String) model.getActualTime();

            if (!TextUtils.isEmpty(lsTime))
                myHolder.tvTime.setText(lsTime);
        }
        else
        {
            if (!TextUtils.isEmpty(model.getScheduledTime()))
            {
                lsTime = model.getScheduledTime();

                if (!TextUtils.isEmpty(lsTime))
                    myHolder.tvTime.setText(lsTime);
            }
        }

        /* PLAN NAME */
        if (!TextUtils.isEmpty((String) model.getActualPlan()))
        {
            lsPlan = (String) model.getActualPlan();

            if (!TextUtils.isEmpty(lsPlan))
                myHolder.tvPlan.setText(lsPlan);
        }
        else
        {
            if (!TextUtils.isEmpty(model.getScheduledPlan()))
            {
                lsPlan = model.getScheduledPlan();

                if (!TextUtils.isEmpty(lsPlan))
                    myHolder.tvPlan.setText(lsPlan);
            }
        }

        if (!TextUtils.isEmpty((String) model.getActualValue()))
        {
            lsQty = (String) model.getActualValue();

            if (!TextUtils.isEmpty(lsQty))
                myHolder.tvQty.setText(lsQty);
        }
        else
        {
            if (!TextUtils.isEmpty(model.getScheduledValue()))
            {
                lsQty = model.getScheduledValue();

                if (!TextUtils.isEmpty(lsQty)){
                    myHolder.tvQty.setText(lsQty);

                }else {
                    myHolder.tvQty.setText("0");

                }
            }else {
                myHolder.tvQty.setText("0");

            }
        }



        boolean isDone = model.getAction();

        if (isDone)
        {
            myHolder.ivChecked.setSelected(true);
            myHolder.ivActivityDone.setSelected(true);
        }
        else
        {
            myHolder.ivChecked.setSelected(false);
            myHolder.ivActivityDone.setSelected(false);
        }
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }

    /*---------------------------------------------------------------------------------*/
   /* private Context context;
    private ArrayList<MyPlanResponse.MyPlanData> dataList;
    private CheckBoxListener checkBoxListener;
    private PlanEditListener planEditListener;

    private boolean isDone = false;
    int planId;

    public interface CheckBoxListener
    {
        public void isCheckBoxSelected(int pos, boolean isDone);
    }

    public interface PlanEditListener
    {
        public void editedPlan(int pos, ArrayList<MyPlanResponse.MyPlanData> list);
    }

    public MyPlansAdapter(Context context,
                          ArrayList<MyPlanResponse.MyPlanData> dataList, CheckBoxListener checkBoxListener,
                          int planId, PlanEditListener planEditListener)
    {
        this.context = context;
        this.dataList = dataList;
        this.checkBoxListener = checkBoxListener;
        this.planId = planId;
        this.planEditListener = planEditListener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvShedule,tvTime, tvPlan, tvQty;
        CheckBox checkBox;
        ImageView ivEdit;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvShedule = (TextView) itemView.findViewById(R.id.tvSheduleName);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvPlan = (TextView) itemView.findViewById(R.id.tvPlan);
            tvQty = (TextView) itemView.findViewById(R.id.tvQty);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            ivEdit = (ImageView) itemView.findViewById(R.id.ivEdit);

            checkBox.setOnClickListener(this);
            ivEdit.setOnClickListener(this);

            if (planId > 0)
            {
                checkBox.setVisibility(View.GONE);
                ivEdit.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.checkBox:

                    if (checkBox.isChecked())
                    {
                        isDone = true;
                        checkBox.setChecked(true);
                        checkBoxListener.isCheckBoxSelected(getAdapterPosition(), isDone);
                    }
                    else
                    {
                        isDone = false;
                        checkBox.setChecked(false);
                        checkBoxListener.isCheckBoxSelected(getAdapterPosition(), isDone);
                    }
                    break;

                case R.id.ivEdit:

                    planEditListener.editedPlan(getAdapterPosition(), dataList);
                    break;
            }
        }
    }

    @NonNull
    @Override
    public MyPlansAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adap_myplans, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlansAdapter.MyHolder myHolder, int i)
    {
        String lsTime, lsPlan, lsQty;

        MyPlanResponse.MyPlanData model = dataList.get(i);

        String shedule = model.getScheduleName();

        if (!TextUtils.isEmpty((String) model.getActualTime()))
        {
            lsTime = (String) model.getActualTime();

            if (!TextUtils.isEmpty(lsTime))
                myHolder.tvTime.setText(lsTime);
        }
        else
        {
            if (!TextUtils.isEmpty(model.getScheduledTime()))
            {
                lsTime = model.getScheduledTime();

                if (!TextUtils.isEmpty(lsTime))
                    myHolder.tvTime.setText(lsTime);
            }
        }

        if (!TextUtils.isEmpty((String) model.getActualPlan()))
        {
            lsPlan = (String) model.getActualPlan();

            if (!TextUtils.isEmpty(lsPlan))
                myHolder.tvPlan.setText(lsPlan);
        }
        else
        {
            if (!TextUtils.isEmpty(model.getScheduledPlan()))
            {
                lsPlan = model.getScheduledPlan();

                if (!TextUtils.isEmpty(lsPlan))
                    myHolder.tvPlan.setText(lsPlan);
            }
        }

        if (!TextUtils.isEmpty((String) model.getActualValue()))
        {
            lsQty = (String) model.getActualValue();

            if (!TextUtils.isEmpty(lsQty))
                myHolder.tvQty.setText(lsQty);
        }
        else
        {
            if (!TextUtils.isEmpty(model.getScheduledValue()))
            {
                lsQty = model.getScheduledValue();

                if (!TextUtils.isEmpty(lsQty))
                    myHolder.tvQty.setText(lsQty);
            }
        }

        boolean isDone = model.getAction();

        if (!TextUtils.isEmpty(shedule))
            myHolder.tvShedule.setText(shedule);

        if (isDone)
            myHolder.checkBox.setChecked(true);
        else
            myHolder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount()
    {
        return dataList.size();
    }*/
}
