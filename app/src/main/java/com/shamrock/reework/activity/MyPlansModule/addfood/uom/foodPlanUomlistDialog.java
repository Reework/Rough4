package com.shamrock.reework.activity.MyPlansModule.addfood.uom;

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
import com.shamrock.reework.activity.MyPlansModule.addfood.AddFoodPlanItemAdapter;
import com.shamrock.reework.activity.MyPlansModule.addfood.List;
import com.shamrock.reework.activity.MyPlansModule.addfood.Uom;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class foodPlanUomlistDialog extends DialogFragment implements View.OnClickListener, AddFoodPlanUOMAdapter.SelectUOMListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvTitle;

    ArrayList<Uom> bundleList, languageList, languageListSearch;
    AddFoodPlanUOMAdapter languageAdapter;
    GetAddFoodPlanUoMListener listener;

    private Uom selectedLangaue = null;

    public foodPlanUomlistDialog() {
    }

    @Override
    public void GetSelectedFoodUOMPosition(int pos, Uom model) {
        if (model != null)
        {
            selectedLangaue = model;
            listener.onSubmitFoodPlanUOM(selectedLangaue);
            this.dismiss();
        }
    }


    public interface GetAddFoodPlanUoMListener
    {
        void onSubmitFoodPlanUOM(Uom model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetAddFoodPlanUoMListener) context;
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
                bundleList = (ArrayList<Uom>) getArguments().getSerializable("COUNTRY_LIST");
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
        tvTitle.setText("Select Item");
        ImageView img_close_mealtype=view.findViewById(R.id.img_close_mealtype);
        img_close_mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        languageAdapter = new AddFoodPlanUOMAdapter(context, languageList, this);

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
                    for (Uom model:languageListSearch)
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
