package com.shamrock.reework.activity.MyPlansModule.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyPlansListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

   /* Context mContext;
    ArrayList<PlanItem> list;
    private static LayoutInflater inflater = null;
    int colorBlue;

    static class ViewHolder {
        TextView textName;
        TextView textClock;
        LinearLayout linearLayoutChild;
    }

    static class ChildViewHolder {
        TextView todoName;
        TextView todoCount;
        ImageView todoImage;
    }

    public MyPlansListAdapter(Context context, ArrayList<PlanItem> rArrayList) {
        this.list = rArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        colorBlue = ContextCompat.getColor(mContext, R.color.colorRobinEggBlue);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public PlanItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

//    public boolean isChecked(int position) {
//        return list.get(position).isChecked();
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        View rowView = convertView;

        // reuse views
        ViewHolder viewHolder = new ViewHolder();
        ChildViewHolder childViewHolder = new ChildViewHolder();
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_row_my_plan, null);

            viewHolder.textName = rowView.findViewById(R.id.textView_MyPlan_Name);
            viewHolder.textClock = rowView.findViewById(R.id.textView_MyPlan_clock);
            viewHolder.linearLayoutChild = rowView.findViewById(R.id.linLay_MyPlan_Todo);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        // set Data to views
        final String name = list.get(position).getPlan_name();
        final String clock = list.get(position).getPlan_time();
        viewHolder.textName.setText(name);
        viewHolder.textClock.setText(clock);

        //creating child todoList
        List<PlanItem.PlanTodoItem> planTodoItems = list.get(position).getGroupItemCollection();
        for (int i = 0; i < planTodoItems.size(); i++) {
            View todoList_view = inflater.inflate(R.layout.list_row_child_my_plan_todo, null);
            childViewHolder.todoName = todoList_view.findViewById(R.id.textView_Todo_Name);
            childViewHolder.todoCount = todoList_view.findViewById(R.id.textView_Todo_Count);
            childViewHolder.todoImage = todoList_view.findViewById(R.id.imageView_Todo_icon);
            todoList_view.setTag(childViewHolder);

            PlanItem.PlanTodoItem todoItem = planTodoItems.get(i);
            String todoName = todoItem.getName();
            String todoCount = todoItem.getCount();
            childViewHolder.todoName.setText(todoName);
            childViewHolder.todoCount.setText(todoCount);

            viewHolder.linearLayoutChild.addView(todoList_view, i);
        }

        return rowView;
    }
*/
}
