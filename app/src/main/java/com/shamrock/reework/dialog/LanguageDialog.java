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
import com.shamrock.reework.adapter.LanguageAdapter;
import com.shamrock.reework.api.response.Language;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

public class LanguageDialog extends DialogFragment implements View.OnClickListener, LanguageAdapter.LanguageListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView tvTitle;

    ArrayList<Language> bundleList, languageList, languageListSearch;
    LanguageAdapter languageAdapter;
    GetLanguageListener listener;

    private Language selectedLangaue = null;

    public interface GetLanguageListener
    {
        void onSubmitLanguageeData(Language model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetLanguageListener) context;
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
                bundleList = (ArrayList<Language>) getArguments().getSerializable("LANGUAGE_LIST");
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
        tvTitle.setText("Select your language");

        languageAdapter = new LanguageAdapter(context, languageList, this);

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
                    for (Language model:languageListSearch)
                    {
                        if (model.getLangName().toLowerCase().contains(newText.toLowerCase()))
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
    @Override
    public void GetLanguagePosition(int pos, Language model)
    {
        if (model != null)
        {
            selectedLangaue = model;
            listener.onSubmitLanguageeData(selectedLangaue);
        }
    }
}
