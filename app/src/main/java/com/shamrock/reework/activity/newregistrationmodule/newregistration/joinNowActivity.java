package com.shamrock.reework.activity.newregistrationmodule.newregistration;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 */
public class joinNowActivity extends AppCompatActivity {
//    RecyclerView joinNew_recycler;
    Context context;
    Utils utils;
    RegistrationService regService;
    JoinNewstepAdapter madapter;
    Toolbar toolbar;
    Button next_button;
    int [] layouts;
    private TextView[] dots;
    private Timer timer;
    ViewPager vp_joinnow;
    private MyViewPagerAdapter myViewPagerAdapter;
    int count=0;


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert..");
        builder.setMessage("Do you want to cancel this registration?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SessionManager sessionManager=new SessionManager(joinNowActivity.this);
                sessionManager.logoutUser();
                finish();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void automateViewPagerSwiping() {
        final long DELAY_MS = 4000;//delay in milliseconds before task is to be executed
        final long PERIOD_MS = 12000; // time in milliseconds between successive task executions.
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (vp_joinnow.getCurrentItem() == myViewPagerAdapter.getCount() - 1) { //adapter is your custom ViewPager's adapter
                    vp_joinnow.setCurrentItem(0);
                }
                else {
                    vp_joinnow.setCurrentItem(vp_joinnow.getCurrentItem() + 1, true);
                }
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
    private void addBottomDots(int currentPage) {
        LinearLayout layoutDots=findViewById(R.id.layoutDots);

        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(40);
            dots[i].setTextColor(colorsInactive[currentPage]);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_new_activity);
//        joinNew_recycler=findViewById(R.id.joinNew_recycler);
        vp_joinnow=findViewById(R.id.vp_joinnow);


        layouts=new int[]{
                R.layout.slide_1,
                R.layout.slide2,
                R.layout.slide3
        };

        next_button=findViewById(R.id.next_button);
        context = joinNowActivity.this;
        utils = new Utils();
        setToolBar();
        vp_joinnow.setCurrentItem(0);

//        getJoinNewStepsApi();
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                count++;
                vp_joinnow.setCurrentItem(vp_joinnow.getCurrentItem() + 1, true);

                if (count>2){
                    if (getIntent().getStringExtra("fromWeb")!=null&&getIntent().getStringExtra("fromWeb").equalsIgnoreCase("true")){
                        Intent intent=new Intent(joinNowActivity.this, DynamicHealthparamActivity.class);
                        intent.putExtra("FromWeb","true");
                        startActivity(intent);

                    }else {
                        Intent intent=new Intent(joinNowActivity.this, ViewPagerActivity.class);
                        startActivity(intent);

                    }
                }





            }
        });
        myViewPagerAdapter = new MyViewPagerAdapter();
        vp_joinnow.setAdapter(myViewPagerAdapter);
        vp_joinnow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        automateViewPagerSwiping();
        addBottomDots(0);
        changeStatusBarColor();
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#00D5BC"));
        }
    }
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.img_close_joinnow);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Join Now");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert..");
                builder.setMessage("Do you want to cancel this registration?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SessionManager sessionManager=new SessionManager(joinNowActivity.this);
                        sessionManager.logoutUser();
                        finish();


                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void getJoinNewStepsApi()
    {
        regService = Client.getClient().create(RegistrationService.class);
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        Call<JoinNew> call = regService.getJoinNewSteps();
        call.enqueue(new Callback<JoinNew>()
        {
            @Override
            public void onResponse(Call<JoinNew> call, Response<JoinNew> response)
            {
                utils.hideProgressbar();
//                joinNew_recycler.setHasFixedSize(true);
//                joinNew_recycler.setLayoutManager(new LinearLayoutManager(joinNowActivity.this));
//                madapter = new JoinNewstepAdapter(response.body().getData());
//                joinNew_recycler.setAdapter(madapter);

            }

            @Override
            public void onFailure(Call<JoinNew> call, Throwable t)
            {
            }
        });

    }
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
