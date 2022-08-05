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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.shamrock.R;
import com.shamrock.reework.adapter.MealTypeAdapter;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class MealTypeDialog extends DialogFragment implements MealTypeAdapter.MealTypeListener{

    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private TextView tvTitle;
    private SearchView searchView;
    ArrayList<String> bundleList, countryList, countryListSearch;
    MealTypeAdapter countryAdapter;
    GetMealTypeListener listener;
    private ArrayList<String> arylst_mealtype;

    private String selectedCountry = null;

    public interface GetMealTypeListener
    {
        void onSubmitMealTypeData(String model);
    }




    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context.getApplicationContext();

        try
        {
            listener = (GetMealTypeListener) context;
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
            countryList = new ArrayList<>();
            countryListSearch = new ArrayList<>();

            try
            {
                bundleList = (ArrayList<String>) getArguments().getSerializable("COUNTRY_LIST");
                if (bundleList != null)
                {


                    countryList.addAll(bundleList);
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

        searchView = view.findViewById(R.id.searchCountry);
        recyclerView = view.findViewById(R.id.recyclerview);
        tvTitle = view.findViewById(R.id.spinner_textView);
        tvTitle.setText("Select Your Meal Type");
        ImageView img_close_mealtype=view.findViewById(R.id.img_close_mealtype);
        img_close_mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        countryAdapter = new MealTypeAdapter(context, countryList, this);

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
                countryList.clear();
                try
                {
                    if (TextUtils.isEmpty(newText))
                    {
                        //countryList = countryListSearch;
                        countryList.addAll(countryListSearch);
                    }
                    else
                    {
                        for (String model:countryListSearch)
                        {
                            if (model.toLowerCase().contains(newText.toLowerCase()))
                            {
                                countryList.add(model);
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
    public void GetCountryPosition(int pos, String model)
    {
        if (model != null)
        {
            selectedCountry = model;

            listener.onSubmitMealTypeData(selectedCountry);
        }
    }
}
