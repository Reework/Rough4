package com.shamrock.reework.activity.BCAModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.TestList;

import java.util.List;



public class ChildDirectoryAdapter extends RecyclerView.Adapter<ChildDirectoryAdapter.ViewHolder> {
    Context context;
    List<BCAResponce.TestDetails> mStudentDataList;
    LayoutInflater inflter;
    List<BCAResponce.TestDetails>listOfStudentDataItems;
    LinearLayoutManager mLayoutManager;
    List<TestList> testList;
    public interface ChildDataClick {
        void onChildDataClick(int id, String value);
    }

    public ChildDirectoryAdapter(Context context, List<BCAResponce.TestDetails> StudentDataList, List<TestList> testList) {
        this.context = context;
        this.mStudentDataList = StudentDataList;
        this.testList=testList;
        //  this.mStudentDataClick = (StudentDataClick) Fragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        viewHolder.txt_groupname.setText(testList.get(position).getGroupName());



        TestListAdapter ChildDirectoryAdapter = new TestListAdapter(context, testList.get(position).getTestDetails());
        viewHolder.recyler_testlist.setAdapter(ChildDirectoryAdapter);
        viewHolder.recyler_testlist.setNestedScrollingEnabled(false);



//        viewHolder.txtName.setText(mStudentDataList.get(position).getTestName());
//        viewHolder.txtValue.setText(mStudentDataList.get(position).getTestValue());
//        viewHolder.txtRange.setText(mStudentDataList.get(position).getNormalRange());
////        if (mStudentDataList.get(position).getScore()!=null){
////            viewHolder.txtScore.setText(mStudentDataList.get(position).getScore());
////
////        }
//
//        if (mStudentDataList.get(position).getRemark()!=null){
//            viewHolder.txtRemark.setText(mStudentDataList.get(position).getRemark());
//        }

    }

    @Override
    public int getItemCount() {
        return testList.size();
        //return 200;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName,txtValue,txtRange,txtScore,txtRemark,txt_groupname;
        RecyclerView recyler_testlist;
      //  RecyclerView studentRecyclerView;
        ImageView ivDownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            txtValue=(TextView)itemView.findViewById(R.id.txtValue);
            txtRange=(TextView)itemView.findViewById(R.id.txtRange);
            txtScore=(TextView)itemView.findViewById(R.id.txtScore);
            txtRemark=(TextView)itemView.findViewById(R.id.txtRemark);
            txt_groupname=(TextView)itemView.findViewById(R.id.txt_groupname);
            recyler_testlist=itemView.findViewById(R.id.recyler_testlist);



        }
    }
}
