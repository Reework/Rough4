package com.shamrock.reework.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.adapter.LanguageAdapter;
import com.shamrock.reework.adapter.UOMAdapter;
import com.shamrock.reework.api.response.Language;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class UOMDialog extends DialogFragment implements View.OnClickListener, UOMAdapter.UOMListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvTitle;

    ArrayList<FoodUnitMasterData> bundleList, languageList, languageListSearch;
    UOMAdapter languageAdapter;
    GetUOMListener listener;

    private FoodUnitMasterData selectedLangaue = null;

    public UOMDialog() {
    }

    @Override
    public void GetUOMPosition(int pos, FoodUnitMasterData model) {
        if (model != null)
        {
            selectedLangaue = model;
            listener.onSubmitUOMData(selectedLangaue);
        }

    }

    public interface GetUOMListener
    {
        void onSubmitUOMData(FoodUnitMasterData model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetUOMListener) context;
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
            languageList = new ArrayList<>();
            languageListSearch = new ArrayList<>();

            try
            {
                bundleList = (ArrayList<FoodUnitMasterData>) getArguments().getSerializable("COUNTRY_LIST");
                if (bundleList != null)
                {
                    languageList.addAll(bundleList);
                    languageListSearch.addAll(bundleList);
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
        tvTitle.setText("Select Measurement");
        ImageView img_close_mealtype=view.findViewById(R.id.img_close_mealtype);
        img_close_mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        languageAdapter = new UOMAdapter(context, languageList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(languageAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                languageList.clear();
                if (TextUtils.isEmpty(newText))
                {
                    languageList.addAll(languageListSearch);
                }
                else
                {
                    for (FoodUnitMasterData model:languageListSearch)
                    {
                        if (model.getMeasurement().toLowerCase().contains(newText.toLowerCase()))
                        {
                            languageList.add(model);
                        }
                    }
                }
                languageAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v)
    {

    }

    /* Get langauge after selection from list */
//    @Override
//    public void GetUOMPosition(int pos, Language model)
//    {
//
//    }
}
