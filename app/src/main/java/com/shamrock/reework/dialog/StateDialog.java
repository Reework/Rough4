package com.shamrock.reework.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.adapter.StateAdapter;
import com.shamrock.reework.api.response.State;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class StateDialog extends DialogFragment implements StateAdapter.StateListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvTitle;
    ArrayList<State> bundleList, stateList, stateListSearch;
    StateAdapter stateAdapter;
    GetStateListener listener;

    private State selectedState = null;

    public interface GetStateListener
    {
        void onSubmitStateData(State model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetStateListener) context;
        }catch (Exception e){e.printStackTrace();}

        utils = new Utils();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        setCancelable(true);

        if (getArguments() != null)
        {
            stateList = new ArrayList<>();
            stateListSearch = new ArrayList<>();

            try
            {
                bundleList = (ArrayList<State>) getArguments().getSerializable("STATE_LIST");
                if (bundleList != null)
                {
                    stateList.addAll(bundleList);
                    stateListSearch.addAll(bundleList);
                }
            }
            catch (Exception e){e.printStackTrace();}
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_spinner, container, false);

        searchView = (SearchView) view.findViewById(R.id.searchCountry);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        tvTitle = (TextView) view.findViewById(R.id.spinner_textView);
        tvTitle.setText("Select your state");

        stateAdapter = new StateAdapter(context, stateList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(stateAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                stateList.clear();
                if (TextUtils.isEmpty(newText))
                {
                    stateList.addAll(stateListSearch);
                }
                else
                {
                    for (State model:stateListSearch)
                    {
                        if (model.getStateName().toLowerCase().contains(newText.toLowerCase()))
                        {
                            stateList.add(model);
                        }
                    }
                }
                stateAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    /* Get state after selection from list */
    @Override
    public void GetStatePosition(int pos, State model)
    {
        if (model != null)
        {
            selectedState = model;
            listener.onSubmitStateData(selectedState);
        }
    }
}
