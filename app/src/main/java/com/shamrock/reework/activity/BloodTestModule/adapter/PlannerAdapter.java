package com.shamrock.reework.activity.BloodTestModule.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;

import java.util.ArrayList;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.RecyclerViewHolder> {
    private Context context;
    LinearLayout mainlayout;
    ArrayList<String> strings;
    ArrayList<String> tvIds;
    int mTextviewCount;
    boolean isFoundData=false;

    public PlannerAdapter(Context context, ArrayList<String> s, int mTexviewQuantity) {
        this.context = context;
        this.strings = s;
        this.mTextviewCount = mTexviewQuantity;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_compare_report, viewGroup, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {

        setData(recyclerViewHolder,strings.get(position),position);

    }

    private void setData(RecyclerViewHolder recyclerViewHolder, String strings, int position) {
        String[] str_new=strings.split(",");
        visibiityOnOff(mTextviewCount,recyclerViewHolder,str_new);
        showProperty(recyclerViewHolder,position);

        if (recyclerViewHolder.txt_c2.getText().toString().equalsIgnoreCase("range")){
            recyclerViewHolder.txt_c2.setText("Range");
        }else {
            recyclerViewHolder.txt_c2.setText(recyclerViewHolder.txt_c2.getText().toString());
        }
    }



    private void visibiityOnOff(int mTextviewCount, RecyclerViewHolder recyclerViewHolder, String[] str_new) {
        if (mTextviewCount==1){
            isFoundData=true;
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
//            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }



        }
        if (mTextviewCount==2){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }



            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            isFoundData=true;

        }
        if (mTextviewCount==3){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            isFoundData=true;

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
        }
        if (mTextviewCount==4){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            isFoundData=true;

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
        }
        if (mTextviewCount==5){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            isFoundData=true;

        }
        if (mTextviewCount==6){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            isFoundData=true;

        }
        if (mTextviewCount==7){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            isFoundData=true;

        }if (mTextviewCount==8){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            isFoundData=true;


            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[7].toString());
            isFoundData=true;

        }if (mTextviewCount==9){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c9.setVisibility(View.VISIBLE);

            isFoundData=true;

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[7].toString());
            recyclerViewHolder.txt_c9.setText(str_new[8].toString());
        }if (mTextviewCount==10){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c9.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c10.setVisibility(View.VISIBLE);
            isFoundData=true;

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[7].toString());
            recyclerViewHolder.txt_c9.setText(str_new[8].toString());
            recyclerViewHolder.txt_c10.setText(str_new[9].toString());
        }if (mTextviewCount==11){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c9.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c10.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c11.setVisibility(View.VISIBLE);
            isFoundData=true;

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[7].toString());
            recyclerViewHolder.txt_c9.setText(str_new[8].toString());
            recyclerViewHolder.txt_c10.setText(str_new[9].toString());
            recyclerViewHolder.txt_c11.setText(str_new[10].toString());
        }if (mTextviewCount==12){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c9.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c10.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c11.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c12.setVisibility(View.VISIBLE);
            isFoundData=true;

            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[7].toString());
            recyclerViewHolder.txt_c9.setText(str_new[8].toString());
            recyclerViewHolder.txt_c10.setText(str_new[9].toString());
            recyclerViewHolder.txt_c11.setText(str_new[10].toString());
            recyclerViewHolder.txt_c12.setText(str_new[11].toString());
        }if (mTextviewCount==13){
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c9.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c10.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c11.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c12.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c13.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c1.setText(str_new[0].toString());
            isFoundData=true;

            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[7].toString());
            recyclerViewHolder.txt_c9.setText(str_new[8].toString());
            recyclerViewHolder.txt_c10.setText(str_new[9].toString());
            recyclerViewHolder.txt_c12.setText(str_new[10].toString());
            recyclerViewHolder.txt_c13.setText(str_new[11].toString());

        }



        if (mTextviewCount==14){

            setData(recyclerViewHolder,str_new,7,8,9,11,12);
            isFoundData=true;

        }
        if (mTextviewCount==15){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,8,9,12,13);

        }
        if (mTextviewCount==16){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,9,11,13,14);

        }
        if (mTextviewCount==17){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,9,11,14,15);

        }

        if (mTextviewCount==18){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,10,13,15,16);

        }

        if (mTextviewCount==19){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,10,13,16,17);

        }
        if (mTextviewCount==20){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,11,14,17,18);

        }
        if (mTextviewCount==21){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,11,15,18,19);

        }
        if (mTextviewCount==22){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,11,16,19,20);

        }
        if (mTextviewCount==23){
            isFoundData=true;

            setData(recyclerViewHolder,str_new,7,15,17,20,21);

        }
        if (!isFoundData){

            setData(recyclerViewHolder,str_new,7,15,17,20,22);
            isFoundData=false;

        }

        if (str_new[0].toString().equalsIgnoreCase("testname")){
            recyclerViewHolder.txt_c1.setText("Name");

        }else {
            recyclerViewHolder.txt_c1.setText(str_new[0].toString());

        }



        isFoundData=false;






    }

    private void setData(RecyclerViewHolder recyclerViewHolder, String[] str_new,int pos1,int pos2,int pos3,int pos4,int pos5) {
        try {
            recyclerViewHolder.txt_c1.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c2.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c3.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c4.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c5.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c6.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c7.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c8.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c9.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c10.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c11.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c12.setVisibility(View.VISIBLE);
            recyclerViewHolder.txt_c13.setVisibility(View.VISIBLE);
            if (str_new[0].toString().equalsIgnoreCase("testname")){
                recyclerViewHolder.txt_c1.setText("Name");

            }else {
                recyclerViewHolder.txt_c1.setText(str_new[0].toString());

            }

            recyclerViewHolder.txt_c2.setText(str_new[1].toString());
            recyclerViewHolder.txt_c3.setText(str_new[2].toString());
            recyclerViewHolder.txt_c4.setText(str_new[3].toString());
            recyclerViewHolder.txt_c5.setText(str_new[4].toString());
            recyclerViewHolder.txt_c6.setText(str_new[5].toString());
            recyclerViewHolder.txt_c7.setText(str_new[6].toString());
            recyclerViewHolder.txt_c8.setText(str_new[pos1].toString());
            recyclerViewHolder.txt_c9.setText(str_new[pos2].toString());
            recyclerViewHolder.txt_c10.setText(str_new[pos3].toString());
            recyclerViewHolder.txt_c12.setText(str_new[pos4].toString());
            recyclerViewHolder.txt_c13.setText(str_new[pos5].toString());
            isFoundData=false;
        }catch (Exception e){
//           Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {

        return strings.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txt_c1,txt_c2,txt_c3,txt_c4,txt_c5,txt_c6,txt_c7,txt_c8,txt_c9,txt_c10,txt_c11,txt_c12,txt_c13,txt_c14,txt_c15,txt_c16,txt_c17,txt_c18,txt_c19,txt_c20;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txt_c1=itemView.findViewById(R.id.txt_c1);
            txt_c1.setSelected(true);
            txt_c2=itemView.findViewById(R.id.txt_c2);
            txt_c3=itemView.findViewById(R.id.txt_c3);
            txt_c4=itemView.findViewById(R.id.txt_c4);
            txt_c5=itemView.findViewById(R.id.txt_c5);
            txt_c6=itemView.findViewById(R.id.txt_c6);
            txt_c7=itemView.findViewById(R.id.txt_c7);
            txt_c8=itemView.findViewById(R.id.txt_c8);
            txt_c9=itemView.findViewById(R.id.txt_c9);
            txt_c10=itemView.findViewById(R.id.txt_c10);
            txt_c11=itemView.findViewById(R.id.txt_c11);
            txt_c12=itemView.findViewById(R.id.txt_c12);
            txt_c13=itemView.findViewById(R.id.txt_c13);
            txt_c14=itemView.findViewById(R.id.txt_c14);
            txt_c15=itemView.findViewById(R.id.txt_c15);
            txt_c16=itemView.findViewById(R.id.txt_c16);
            txt_c17=itemView.findViewById(R.id.txt_c17);
            txt_c18=itemView.findViewById(R.id.txt_c18);
            txt_c19=itemView.findViewById(R.id.txt_c19);
            txt_c20=itemView.findViewById(R.id.txt_c20);

        }

        @Override
        public void onClick(View view) {

        }
    }
    private void showProperty(RecyclerViewHolder recyclerViewHolder, int position) {
        if (position==0){
            recyclerViewHolder.txt_c1.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c2.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c3.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c4.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c5.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c6.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c7.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c8.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c9.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c10.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c11.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c12.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c13.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c14.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c15.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c16.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c17.setTypeface(Typeface.DEFAULT_BOLD);
            recyclerViewHolder.txt_c18.setTypeface(Typeface.DEFAULT_BOLD);




            recyclerViewHolder.txt_c1.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c2.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c3.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c4.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c5.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c6.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c7.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c8.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c9.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c10.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c11.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c12.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c13.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c14.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c15.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c16.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c17.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c18.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c19.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));
            recyclerViewHolder.txt_c20.setTextColor(context.getResources().getColor(R.color.dark_grey_blue));


        }else {
            recyclerViewHolder.txt_c1.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c2.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c3.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c4.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c5.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c6.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c7.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c8.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c9.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c10.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c11.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c12.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c13.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c14.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c15.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c16.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c17.setTypeface(Typeface.DEFAULT);
            recyclerViewHolder.txt_c18.setTypeface(Typeface.DEFAULT);



            recyclerViewHolder.txt_c1.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c2.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c3.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c4.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c5.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c6.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c7.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c8.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c9.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c10.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c11.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c12.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c13.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c14.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c15.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c16.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c17.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c18.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c19.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
            recyclerViewHolder.txt_c20.setTextColor(context.getResources().getColor(R.color.colorBlueGreen1));
        }
    }
}