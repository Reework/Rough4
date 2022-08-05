package com.shamrock.reework.activity.HealthModule.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.adapter.CityAdapter;
import com.shamrock.reework.api.response.City;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class NewCityDialog extends DialogFragment implements View.OnClickListener, CityAdapter.CityListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvTitle;

    ArrayList<City> bundleList, cityList, cityListSearch;
    CityAdapter cityAdapter;
    GetCityListener listener;

    private City selectedCity = null;

    public interface GetCityListener
    {
        void onSubmitCityeData(City model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetCityListener) context;
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
            cityList = new ArrayList<>();
            cityListSearch = new ArrayList<>();

            try
            {
                bundleList = (ArrayList<City>) getArguments().getSerializable("CITY_LIST");
                if (bundleList != null)
                {
                    cityList.addAll(bundleList);
                    cityListSearch.addAll(bundleList);
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
        tvTitle.setText("Select your city");

        cityAdapter = new CityAdapter(context, cityList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cityAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                cityList.clear();
                if (TextUtils.isEmpty(newText))
                {
                    cityList.addAll(cityListSearch);
                }
                else
                {
                    for (City model:cityListSearch)
                    {
                        if (model.getCityName().toLowerCase().contains(newText.toLowerCase()))
                        {
                            cityList.add(model);
                        }
                    }
                }
                cityAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v)
    {

    }

    /* Get city after selection from list */
    @Override
    public void GetCityPosition(int pos, City model)
    {
        if (model != null)
        {
            selectedCity = model;
            listener.onSubmitCityeData(selectedCity);
            this.dismiss();
        }
    }
}
