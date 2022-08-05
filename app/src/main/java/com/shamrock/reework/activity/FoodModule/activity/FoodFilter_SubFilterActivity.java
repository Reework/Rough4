package com.shamrock.reework.activity.FoodModule.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.CustomExpandableListAdapter;
import com.shamrock.reework.api.response.GetFilterSubFilterResponce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FoodFilter_SubFilterActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<GetFilterSubFilterResponce.Datum> mSubFilterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_filter__sub_filter);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            final LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

            mSubFilterList = (ArrayList<GetFilterSubFilterResponce.Datum>) getIntent().getSerializableExtra("data");
            if(mSubFilterList!=null){
                for(int i=0;i< mSubFilterList.size();i++){
                    if(mSubFilterList.get(i).getData()!=null) {
                        ArrayList<String> childData = new ArrayList<>();
                        for (int j = 0; j < mSubFilterList.get(i).getData().size(); j++) {

                            childData.add(mSubFilterList.get(i).getData().get(j));
                        }
                        expandableListDetail.put(mSubFilterList.get(i).getText(),childData);
                    }

                }
            }
      //  expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                int groupId = mSubFilterList.get(groupPosition).getValue();
                String itemValue = mSubFilterList.get(groupPosition).getData().get(childPosition);
                return false;
            }
        });
    }

}