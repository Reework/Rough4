package com.shamrock.reework.activity.CheatPlanModule.dialog;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.CheatPlanModule.adapter.OccassionAdapter;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.OccasionResponce;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class OccasionDialog extends DialogFragment implements OccassionAdapter.OccassionListener{
  Context context;
private Utils utils;
private RecyclerView recyclerView;
private TextView tvTitle;
private SearchView searchView;
        ArrayList<OccasionResponce.Datum> bundleList, occasionList, countryListSearch;
    OccassionAdapter countryAdapter;
    GetOcaasionListener listener;

private OccasionResponce.Datum selectedCountry = null;


    public interface GetOcaasionListener
{
    void onSubmitOccasionData(OccasionResponce.Datum model);
}

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context.getApplicationContext();

        try
        {
            listener = (GetOcaasionListener) context;
        }catch (Exception e){
            e.printStackTrace();
        }

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
            occasionList = new ArrayList<>();
            countryListSearch = new ArrayList<>();

            try
            {
                bundleList = (ArrayList<OccasionResponce.Datum>) getArguments().getSerializable("COUNTRY_LIST");
                if (bundleList != null)
                {
                    occasionList.addAll(bundleList);
                    countryListSearch.addAll(bundleList);
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
        tvTitle.setText("----------Select your Occassion----------");

        countryAdapter = new OccassionAdapter(context, occasionList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(countryAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                occasionList.clear();
                try
                {
                    if (TextUtils.isEmpty(newText))
                    {
                        //countryList = countryListSearch;
                        occasionList.addAll(countryListSearch);
                    }
                    else
                    {
                        for ( OccasionResponce.Datum model:countryListSearch)
                        {
                            if (model.getOccasion().toLowerCase().contains(newText.toLowerCase()))
                            {
                                occasionList.add(model);
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.d("CountryDialog--->", e.toString());
                    e.printStackTrace();
                }

                countryAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    /* Get country after selection of countryList */
    @Override
    public void GetOccassionPosition(int pos, OccasionResponce.Datum model) {

        if (model != null)
        {
            selectedCountry = model;

            listener.onSubmitOccasionData(selectedCountry);
        }
    }
}
