package com.shamrock.reework.activity.FoodModule.fragment;


import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.FoodSuggestionAdapter;
import com.shamrock.reework.activity.FoodModule.model.ClsSuggestion;
import com.shamrock.reework.activity.FoodModule.model.Data;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.activity.tips.ClsSleepTipsAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasterFoodSuggestionFrag extends Fragment {
    ListView listView_suggestion;
    RecyclerView recycler_suggestion_tips;
    FoodSuggestionAdapter foodSuggestionAdapter;

    Utils util;
    FoodService foodService;
    Spinner spinner_healthcodition;
    TextView txt_dos,txt_donts;
    Utils utils;
    HomeFragmentService service;


    ArrayList<ClsSuggestion> clsSuggestionArrayList;
    private ArrayList<com.shamrock.reework.activity.tips.Data> arylst_food_tips;

    public MasterFoodSuggestionFrag() {
    }

    public static MasterFoodSuggestionFrag newInstance(String param1, String param2,int param3)
    {
        MasterFoodSuggestionFrag fragment = new MasterFoodSuggestionFrag();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_master_food_suggestion, container, false);
        listView_suggestion=view.findViewById(R.id.recycler_suggestion);
        recycler_suggestion_tips=view.findViewById(R.id.recycler_suggestion_tips);
        spinner_healthcodition=view.findViewById(R.id.spinner_healthcodition);
        txt_donts=view.findViewById(R.id.txt_donts);
        txt_dos=view.findViewById(R.id.txt_dos);
        util = new Utils();
        service = Client.getClient().create(HomeFragmentService.class);

        foodService = Client.getClient().create(FoodService.class);

//        callFoodSuggestionApi();
        callToFetchFoodTipsMasterData();
        return view;
    }

    private void callToFetchFoodTipsMasterData() {

        util.showProgressbar(getActivity());

        Call<ClsSleepTips> call = service.getMasterFoodTipsData(3);
        call.enqueue(new Callback<ClsSleepTips>() {
            @Override
            public void onResponse(Call<ClsSleepTips> call, retrofit2.Response<ClsSleepTips> response) {

                util.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    ClsSleepTips tipsResponse = response.body();

                    if (tipsResponse != null && tipsResponse.getCode() == 1) {
                        if (tipsResponse.getData() != null) {

                            if (!tipsResponse.getData().isEmpty()){
                                arylst_food_tips=new ArrayList<>();
                                arylst_food_tips.clear();
                                arylst_food_tips.addAll(tipsResponse.getData());
//                            HomeFragment.mCommonDataListTips = mDataListTips;
//                                tipsAdapter.notifyDataSetChanged();



                               ClsSleepTipsAdapter adapter=  new ClsSleepTipsAdapter(getContext(), arylst_food_tips, "MasterSleepFragment");
                                RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                                recycler_suggestion_tips.setLayoutManager(layoutManager1);
                                recycler_suggestion_tips.setItemAnimator(new DefaultItemAnimator());
                                recycler_suggestion_tips.setAdapter(adapter);

                            }else {
                                Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ClsSleepTips> call, Throwable t) {
//                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }


    private void callFoodSuggestionApi() {

        if (!((Activity)getContext()).isFinishing())
        {
            util.showProgressbar(getContext());
        }
        Call<ClsSuggestion> call = foodService.getFoodSuggestion();
        call.enqueue(new Callback<ClsSuggestion>()
        {
            @Override
            public void onResponse(Call<ClsSuggestion> call, Response<ClsSuggestion> response)
            {
                util.hideProgressbar();

                final List<Data> tempList;
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsSuggestion listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        tempList = response.body().getData();
                        final ArrayList<String> arylst_healthcondtion=new ArrayList<>();

                        if (tempList!= null && tempList.size() > 0)
                        {
                            if (getContext()!=null){

                                for (int i = 0; i <tempList.size() ; i++) {
                                    arylst_healthcondtion.add(tempList.get(i).getHealthCondition());

                                }

                                ArrayAdapter<String> adapter_when = new ArrayAdapter<String>(getContext(),R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_healthcondtion);

                                spinner_healthcodition.setAdapter(adapter_when);

                                listView_suggestion.setAdapter(new FoodSuggestionAdapter(getContext(),tempList));

//                                spinner_healthcodition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                        String strHc=arylst_healthcondtion.get(i).toString().trim();
//
//                                        for (int j = 0; j <tempList.size() ; j++) {
//                                            if (strHc.equalsIgnoreCase(tempList.get(i).getHealthCondition().trim())){
//                                                txt_dos.setText(tempList.get(i).getDo());
//                                                txt_donts.setText(tempList.get(i).getDoNot());
//                                                break;
//                                            }
//
//                                        }
//
//
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                                    }
//                                });

                            }

                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsSuggestion> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MealType---->", t.toString());
                util.hideProgressbar();
            }
        });
    }


}
