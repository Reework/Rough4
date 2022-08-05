package com.shamrock.reework.activity.HomeModule.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.adapter.MindMoodAdapter;
import com.shamrock.reework.activity.HomeModule.service.MinMoodModel;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class MindDialog extends DialogFragment implements MindMoodAdapter.MindMoodAdapterListener
{
    Context context;
    private Utils utils;

    RecyclerView recyclerView;
    ArrayList<MinMoodModel> list;
    MindMoodAdapter adapter;

    //GetMindMoodDialogListener listener;

    public interface GetMindMoodDialogListener
    {
        void onSubmitMindMood(MinMoodModel model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        /*try
        {
            listener = (GetMindMoodDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }*/

        utils = new Utils();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        setCancelable(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_mind, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvMinMoodDialog);
        list = new ArrayList<>();
        adapter = new MindMoodAdapter(context, list, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadData();

        return view;
    }

    /* GET MIND MOOD HERE */
    @Override
    public void GetMoodModel(MinMoodModel model)
    {
        if (model != null)
        {
            GetMindMoodDialogListener listener = (GetMindMoodDialogListener) getActivity();
            listener.onSubmitMindMood(model);
            dismiss();

        }
    }

    public void loadData()
    {
        MinMoodModel set;

        set = new MinMoodModel(1, R.drawable.ic_happy, "happy");
        list.add(set);

        set = new MinMoodModel(2, R.drawable.icc_stressed, "Stressed");
        list.add(set);

        set = new MinMoodModel(3, R.drawable.ic_neutral, "neutral");
        list.add(set);

        adapter.notifyDataSetChanged();
    }
}
