package com.shamrock.reework.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.adapter.NewCountryAdapter;
import com.shamrock.reework.api.response.Country;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class NewCountryDialog extends DialogFragment implements NewCountryAdapter.CountryListener {
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private SearchView searchView;
    ImageView img_close_mealtype;
    ArrayList<String> bundleList, countryList, countryListSearch;
    NewCountryAdapter countryAdapter;
    GetCountryListener listener;
    private String from = "";

    private Country selectedCountry = null;

    @Override
    public void GetCountryPosition(int pos, String coutry, String from) {
        listener.onSubmitCountryData(coutry, from);
        this.dismiss();

    }

    public interface GetCountryListener {
        void onSubmitCountryData(String model, String from);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        try {
            listener = (GetCountryListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

        utils = new Utils();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        setCancelable(true);

        if (getArguments() != null) {
            countryList = new ArrayList<>();
            countryListSearch = new ArrayList<>();

            try {
                bundleList = (ArrayList<String>) getArguments().getSerializable("COUNTRY_LIST");
                from = getArguments().getString("from");
                if (bundleList != null) {
                    countryList.addAll(bundleList);
                    countryListSearch.addAll(bundleList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_spinner, container, false);

        searchView = (SearchView) view.findViewById(R.id.searchCountry);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        tvTitle = (TextView) view.findViewById(R.id.spinner_textView);
        img_close_mealtype = (ImageView) view.findViewById(R.id.img_close_mealtype);
        if (from.equalsIgnoreCase("country")) {
            tvTitle.setText("Select your country");

        }
        if (from.equalsIgnoreCase("state")) {

            tvTitle.setText("Select your state");

        }

        if (from.equalsIgnoreCase("city")) {

            tvTitle.setText("Select your city");

        }

        if (from.equalsIgnoreCase("foodculture")) {

            tvTitle.setText("Select your Food-Culture");

        }
        if (from.equalsIgnoreCase("blood")) {
            tvTitle.setText("Select your blood group");
        }

        img_close_mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
dismiss();
            }
        });

        countryAdapter = new NewCountryAdapter(context, countryList, this, from);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(countryAdapter);

//        if (countryList!=null){
//            if (!countryList.isEmpty()){
//                for (int i = 0; i < countryList.size(); i++) {
//
//                    if (countryList.get(i).getCountryName().equalsIgnoreCase("india")){
//                        listener.onSubmitCountryData(countryList.get(i));
//
//                        break;
//
//                    }
//
//                }
//
//            }
//        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryList.clear();
                try {
                    if (TextUtils.isEmpty(newText)) {
                        //countryList = countryListSearch;
                        countryList.addAll(countryListSearch);
                    } else {
                        for (String model : countryListSearch) {
                            if (model.toString().toLowerCase().contains(newText.toLowerCase())) {
                                countryList.add(model);
                            }
                        }
                    }
                } catch (Exception e) {
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

}
