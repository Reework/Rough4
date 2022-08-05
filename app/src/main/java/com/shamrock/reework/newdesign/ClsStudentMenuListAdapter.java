package com.shamrock.reework.newdesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;

import java.util.ArrayList;

public class ClsStudentMenuListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<StudentMenu> arylst_menu;

    public ClsStudentMenuListAdapter(Context mContext, ArrayList<StudentMenu> arylst_menu) {
        this.mContext = mContext;
        this.arylst_menu = arylst_menu;
    }

    @Override
    public int getCount() {
        return arylst_menu.size();
    }

    @Override
    public Object getItem(int i) {
        return arylst_menu.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView=LayoutInflater.from(mContext).inflate(R.layout.row_menu,viewGroup,false);
        try {


            ViewHiolder viewHolder=new ViewHiolder(convertView);
            viewHolder.studentMenu=arylst_menu.get(i);
            viewHolder.txt_menu.setText(viewHolder.studentMenu.getMenuName());



            viewHolder.menu_img.setImageResource(R.drawable.sweet);
















//            convertView.setTag(holder.studentMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public class ViewHiolder{
        TextView txt_menu;
        StudentMenu studentMenu;
        ImageView menu_img;

        public ViewHiolder(View view) {
            txt_menu=view.findViewById(R.id.txt_menu);
            menu_img=view.findViewById(R.id.menu_img);
        }
    }


}
