package com.shamrock.reework.activity.MyPlansModule.addfood;

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
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.adapter.UOMAdapter;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class ItemlistDialog extends DialogFragment implements View.OnClickListener, AddFoodPlanItemAdapter.SelectItmeListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvTitle;

    ArrayList<List> bundleList, languageList, languageListSearch;
    AddFoodPlanItemAdapter languageAdapter;
    GetAddItmeListener listener;

    private List selectedLangaue = null;
    private  boolean isFromEdit;
    int ItemToBeEdit;

    public ItemlistDialog() {
    }



    @Override
    public void GetSelectedFoodItmePosition(int pos, List model) {
        if (model != null)
        {
            selectedLangaue = model;
            listener.onSubmitFoodPlanItem(selectedLangaue);
            this.dismiss();

        }

    }

    public interface GetAddItmeListener
    {
        void onSubmitFoodPlanItem(List model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetAddItmeListener) context;
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
                bundleList = (ArrayList<List>) getArguments().getSerializable("COUNTRY_LIST");
                isFromEdit=getArguments().getBoolean("isFromEdit",false);
                ItemToBeEdit=getArguments().getInt("ItemToBeEdit");
                if (bundleList != null)
                {
                    languageList.addAll(bundleList);
                    languageListSearch.addAll(bundleList);
                }
            }
            catch (Exception e){e.printStackTrace();}
        }

        if (isFromEdit){

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
        tvTitle.setText("Select Item");
        ImageView img_close_mealtype=view.findViewById(R.id.img_close_mealtype);
        img_close_mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        languageAdapter = new AddFoodPlanItemAdapter(context, languageList, this);

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
                    for (List model:languageListSearch)
                    {
                        if (model.getText().toLowerCase().contains(newText.toLowerCase()))
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
