package com.shamrock.reework.newdesign;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.shamrock.R;

import java.util.ArrayList;

public class StudentMeuLandingActivity extends AppCompatActivity {
    private ArrayList<StudentMenu> arylst_meun = new ArrayList<>();
    private GridView grid_menu;
    ClsStudentMenuListAdapter clsMenuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid_menu = findViewById(R.id.grid_menu);


        addMenu();

        setMenuToGridview();



    }



    private void setMenuToGridview() {

        //calling adapter and pass arraylist to adapter
        clsMenuListAdapter = new ClsStudentMenuListAdapter(this, arylst_meun);
        grid_menu.setAdapter(clsMenuListAdapter);
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.grid_anim);
//        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
//        grid_menu.setLayoutAnimation(controller);

    }


    private void addMenu() {
        //add menu to student grid menu into the arraylist and pass arraylist to adapter

        arylst_meun.add(new StudentMenu("My Profile"));
        arylst_meun.add(new StudentMenu("My Attendance"));
        arylst_meun.add(new StudentMenu("Notes"));
        arylst_meun.add(new StudentMenu("Friend list"));
        arylst_meun.add(new StudentMenu("My Mark"));
        arylst_meun.add(new StudentMenu("Feedback"));
        arylst_meun.add(new StudentMenu("Log Out"));
        arylst_meun.add(new StudentMenu("Online Chat"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement




        return super.onOptionsItemSelected(item);
    }
}
