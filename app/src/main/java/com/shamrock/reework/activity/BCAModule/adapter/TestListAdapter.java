package com.shamrock.reework.activity.BCAModule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.TestDetails;
import com.shamrock.reework.api.response.TestList;

import java.util.List;


public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {
    Context context;
//    List<BCAResponce.TestDetails> mStudentDataList;
    LayoutInflater inflter;
    List<BCAResponce.TestDetails>listOfStudentDataItems;
    LinearLayoutManager mLayoutManager;
    List<BCAResponce.TestDetails> testList;
    public interface ChildDataClick {
        void onChildDataClick(int id, String value);
    }

    public TestListAdapter(Context context, List<BCAResponce.TestDetails> testList) {
        this.context = context;
//        this.mStudentDataList = Student/DataList;
        this.testList=testList;
        //  this.mStudentDataClick = (StudentDataClick) Fragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_profile_test_lsit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {



        viewHolder.txtName.setText(testList.get(position).getTestName());
        viewHolder.txtValue.setText(testList.get(position).getTestValue());
        viewHolder.txtRange.setText(testList.get(position).getNormalRange());
//        if (mStudentDataList.get(position).getScore()!=null){
//            viewHolder.txtScore.setText(mStudentDataList.get(position).getScore());
////
//        }
//
        if (testList.get(position).getRemark()!=null){
            viewHolder.txtRemark.setText(testList.get(position).getRemark());
        }

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
//            txt_groupname=(TextView)itemView.findViewById(R.id.txt_groupname);



        }
    }
}
