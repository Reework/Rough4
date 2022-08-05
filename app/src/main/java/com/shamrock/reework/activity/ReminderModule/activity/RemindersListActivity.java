package com.shamrock.reework.activity.ReminderModule.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.ReminderModule.adapter.RemindersListAdapter;
import com.shamrock.reework.activity.ReminderModule.service.ReminderItem;

import java.util.ArrayList;

public class RemindersListActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Toolbar toolbar;
    Typeface font;
    Button btnSubmit;
    ArrayList<ReminderItem> reminderItems, reminderItems_Temp;
    ListView listView;
    RemindersListAdapter remindersListAdapter;
    TypedArray arrayDrawable, arrayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders_list);
        context = RemindersListActivity.this;
        init();
        setToolBar();
        findViews();
    }

    private void init() {
        initItems();
    }

    private void initItems() {
        reminderItems = new ArrayList<ReminderItem>();
        reminderItems_Temp = new ArrayList<ReminderItem>();

        arrayDrawable = getResources().obtainTypedArray(R.array.reminder_icons);
        arrayText = getResources().obtainTypedArray(R.array.reminder_text);

        for (int i = 0; i < arrayDrawable.length(); i++) {
//            Drawable drawable = arrayDrawable.getDrawable(i);
            String name = arrayText.getString(i);
            String clock = "15:00";
            int times = 3;
            boolean checked = false;
            ReminderItem item = new ReminderItem(i, name, clock, times, checked);
            reminderItems.add(item);
        }

        arrayDrawable.recycle();
        arrayText.recycle();
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Select Reminders");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews() {
        listView = findViewById(R.id.listView_RemindersList);
        btnSubmit = findViewById(R.id.buttonSubmit_Reminders);

        remindersListAdapter = new RemindersListAdapter(this, reminderItems);
        listView.setAdapter(remindersListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, ((ReminderItem) (parent.getItemAtPosition(position))).getReminderName(), Toast.LENGTH_LONG).show();
            }
        });

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSubmit_Reminders:
                // send intent with data & finish
                String str = "Check items:\n";
                for (int i = 0; i < reminderItems.size(); i++) {
                    if (reminderItems.get(i).isChecked()) {
//                        Drawable drawable = arrayDrawable.getDrawable(i);
                        int position = i;
                        String name = reminderItems.get(i).getReminderName();
                        String clock = reminderItems.get(i).getReminderClock();
                        int times = reminderItems.get(i).getTimes();
                        boolean checked = reminderItems.get(i).isChecked();
//                        str += i + "-" + Name + ":" + times + "\n";
                        reminderItems_Temp.add(new ReminderItem(position, name, clock, times, checked));
                    }
                }

                /*
                int cnt = myItemsListAdapter.getCount();
                for (int i=0; i<cnt; i++){
                    if(myItemsListAdapter.isChecked(i)){
                        str += i + "\n";
                    }
                }
                */

//                Toast.makeText(context, str, Toast.LENGTH_LONG).show();

                if (!reminderItems_Temp.isEmpty()) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reminders", reminderItems_Temp);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }

                break;

            default:
        }
    }
}
