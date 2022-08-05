package com.shamrock.reework.activity.SubscriptionModule.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.SubscriptionModule.adapter.PlanAdapter;
import com.shamrock.reework.api.response.SubscriptionFeaturesResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class PlanDialog extends DialogFragment implements View.OnClickListener, PlanAdapter.PlanListener
{
    Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    //private SearchView searchView;
    private TextView tvTitle;
    SessionManager sessionManager;

    ArrayList<PlanModel>  planList;
    PlanAdapter planAdapter;
    GetPlanListener listener;
    int mPlanId = 0;

    private PlanModel planModel = null;
    ArrayList<HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList>> hashPlanPriceList;

    @Override
    public void GetPlanPosition(int pos, PlanModel model)
    {
        if (model != null)
        {
            planModel = model;
            listener.onSubmitCityeData(planModel);
        }
    }

    public interface GetPlanListener
    {
        void onSubmitCityeData(PlanModel model);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            listener = (GetPlanListener) context;
            sessionManager = new SessionManager(context);
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
            try
            {
                hashPlanPriceList = (ArrayList<HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList>>)
                                    getArguments().getSerializable("HASH_PRICE_LIST");

                mPlanId = sessionManager.getIntValue(SessionManager.KEY_USER_PLAN_ID);
            }
            catch (Exception e){e.printStackTrace();}
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_spinner, container, false);

        //searchView = (SearchView) view.findViewById(R.id.searchCountry);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        tvTitle = (TextView) view.findViewById(R.id.spinner_textView);
        tvTitle.setText("---------- Select your plan ----------");

        planList = new ArrayList<>();
       // planListSearch = new ArrayList<>();

        try
        {
            if (hashPlanPriceList != null && hashPlanPriceList.size() > 0)
            {
                for (int i=0; i < hashPlanPriceList.size(); i++)
                {
                    HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList> hashMap = hashPlanPriceList.get(i);

                    SubscriptionFeaturesResponse.PlanHeadingList header = hashMap.get(i+1);

                    planList.add(new PlanModel(header.getPlanID(), header.getPlanName(), header.getPlanFees()));

                    if (mPlanId == 2)
                        break;
                }
            }
        }
        catch (Exception e){e.printStackTrace();}

        planAdapter = new PlanAdapter(context, planList, this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(planAdapter);

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                planList.clear();
                if (TextUtils.isEmpty(newText))
                {
                    planList.addAll(planListSearch);
                }
                else
                {
                    for (PlanModel model:planListSearch)
                    {
                        if (model.getPlanName().toLowerCase().contains(newText.toLowerCase()))
                        {
                            planList.add(model);
                        }
                    }
                }
                //cityAdapter.notifyDataSetChanged();
                return false;
            }
        });*/
        return view;
    }

    @Override
    public void onClick(View v)
    {

    }
}
