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
import com.shamrock.reework.activity.CheatPlanModule.adapter.WishToEatAdapter;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.WishToEatResponse;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class WishToEatDialog extends DialogFragment implements WishToEatAdapter.WishToEatListener{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private SearchView searchView;
    ArrayList<WishToEatResponse.Datum> bundleList, wishToEatList, countryListSearch;
    WishToEatAdapter WishToAdapterAdapter;
    GetWishToEatListener listener;

    private WishToEatResponse.Datum selectedCountry = null;



    public interface GetWishToEatListener
    {
        void onSubmitWishToEatData(WishToEatResponse.Datum model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context.getApplicationContext();

        try
        {
            listener = (GetWishToEatListener) context;
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
            wishToEatList = new ArrayList<>();
            countryListSearch = new ArrayList<>();

            try
            {
                bundleList = (ArrayList<WishToEatResponse.Datum>) getArguments().getSerializable("COUNTRY_LIST");
                if (bundleList != null)
                {
                    wishToEatList.addAll(bundleList);
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
        tvTitle.setText("----------Select your Wish To Eat----------");

        WishToAdapterAdapter = new WishToEatAdapter(context, wishToEatList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(WishToAdapterAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                wishToEatList.clear();
                try
                {
                    if (TextUtils.isEmpty(newText))
                    {
                        //countryList = countryListSearch;
                        wishToEatList.addAll(countryListSearch);
                    }
                    else
                    {
                        for ( WishToEatResponse .Datum model:countryListSearch)
                        {
                            if (model.getWishToEat().toLowerCase().contains(newText.toLowerCase()))
                            {
                                wishToEatList.add(model);
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.d("CountryDialog--->", e.toString());
                    e.printStackTrace();
                }

                WishToAdapterAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    /* Get country after selection of countryList */


    @Override
    public void GetWishToEatPosition(int pos, WishToEatResponse.Datum model) {

        if (model != null)
        {
            selectedCountry = model;

            listener.onSubmitWishToEatData(selectedCountry);
        }
    }

}
