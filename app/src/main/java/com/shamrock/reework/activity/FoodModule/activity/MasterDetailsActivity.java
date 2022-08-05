package com.shamrock.reework.activity.FoodModule.activity;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragment;
import com.shamrock.reework.activity.FoodModule.fragment.MasterFoodFragmentAllMeals;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.fragment.MasterMindFragment;
import com.shamrock.reework.fragment.MasterMyActivityFragment;
import com.shamrock.reework.fragment.MasterSleepFragment;
import com.shamrock.reework.fragment.MasterWaterFragment;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterDetailsActivity extends AppCompatActivity implements MasterMyActivityFragment.OnFragmentInteractionListener,
        MasterFoodFragment.OnFragmentInteractionListener,
        MasterSleepFragment.OnFragmentInteractionListener,
        // MasterActivityFragment.OnFragmentInteractionListener,
        MasterMindFragment.OnFragmentInteractionListener,
        MasterWaterFragment.OnFragmentInteractionListener,
        WeightFragment.OnFragmentInteractionListener,
        MasterFoodFragmentAllMeals.OnFragmentInteractionListener,

        ViewPager.OnPageChangeListener {
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    String[] tabsTitle = {"Food", "Water", "Sleep",  "Activities","Feelings"};
    // Activity
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    static Context context;
    TextView tvTitle;
    private int FRAGMENT_POSITION = 0;
    private int MEAL_CAL_MAX;
    String ACTUAL_MIND_STATUS = "", COMING_FROM = "", BING_QUANTITY = "";
    boolean FromWater=false;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;
    RelativeLayout counterValuePanel;
    private int userID;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_master);
        context = MasterDetailsActivity.this;

        sessionManager = new SessionManager(context);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        init();

        findViews();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FRAGMENT_POSITION = position;
                tvTitle.setText(tabsTitle[position]);
//                mViewPager.getAdapter().notifyDataSetChanged();
                if(position == 1) { // 0 = the first fragment in the ViewPager, in this case, the fragment i want to refresh its UI
                    MasterWaterFragment fragment = (MasterWaterFragment) mViewPager.getAdapter().instantiateItem(mViewPager, position);
                    fragment.onResume(); // here i call the onResume of the fragment, where i have the method updateUI() to update its UI
                    mViewPager.getAdapter().notifyDataSetChanged();
                }
                if(position == 2) { // 0 = the first fragment in the ViewPager, in this case, the fragment i want to refresh its UI
                    MasterSleepFragment fragment = (MasterSleepFragment) mViewPager.getAdapter().instantiateItem(mViewPager, position);
                    fragment.onResume(); // here i call the onResume of the fragment, where i have the method updateUI() to update its UI
                    mViewPager.getAdapter().notifyDataSetChanged();
                }

                if(position == 0) { // 0 = the first fragment in the ViewPager, in this case, the fragment i want to refresh its UI
                    MasterFoodFragment fragment = (MasterFoodFragment) mViewPager.getAdapter().instantiateItem(mViewPager, position);
                    fragment.onResume(); // here i call the onResume of the fragment, where i have the method updateUI() to update its UI
                    mViewPager.getAdapter().notifyDataSetChanged();
                }

                if(position == 4) { // 0 = the first fragment in the ViewPager, in this case, the fragment i want to refresh its UI
                    MasterMindFragment fragment = (MasterMindFragment) mViewPager.getAdapter().instantiateItem(mViewPager, position);
                    fragment.onResume(); // here i call the onResume of the fragment, where i have the method updateUI() to update its UI
                    mViewPager.getAdapter().notifyDataSetChanged();
                }
                if(position == 3) { // 0 = the first fragment in the ViewPager, in this case, the fragment i want to refresh its UI
                    MasterMyActivityFragment fragment = (MasterMyActivityFragment) mViewPager.getAdapter().instantiateItem(mViewPager, position);
                    fragment.onResume();
                    // here i call the onResume of the fragment, where i have the method updateUI() to update its UI
                    mViewPager.getAdapter().notifyDataSetChanged();
                }
//                Fragment fragment = null;
//
//                switch (position) {
//
//                    case 1:
//
//                        //Trigger the onCreate method in the activity
//                        Bundle bundle1 = new Bundle();
//                        fragment = MasterWaterFragment.newInstance("Camera", "100");
//                        bundle1.putString("arg1", "argValue");
//                        bundle1.putString("arg2", "argValue22");
//                        fragment.setArguments(bundle1);
//
//                        //tvTitle.setText("Water");
//                        break;
//                    case 0:
//
//                        Bundle bundle = new Bundle();
//                        fragment =
//                                MasterFoodFragment.newInstance("Camera", "100", MEAL_CAL_MAX, COMING_FROM);
//                        bundle.putString("arg1", "argValue");
//                        bundle.putString("arg2", "argValue22");
//                        bundle.putInt("MEAL_CAL_MAX", MEAL_CAL_MAX);
//                        bundle.putString("COMING_FROM", COMING_FROM);
//                        bundle.putBoolean("FromWater", FromWater);
//                        fragment.setArguments(bundle);
//                        //tvTitle.setText("Food");
//
//                        break;
//
//                    case 2:
//
//                        Bundle bundle2 = new Bundle();
//                        fragment = MasterSleepFragment.newInstance("Camera", "100");
//                        bundle2.putString("arg1", "argValue");
//                        bundle2.putString("arg2", "argValue22");
//                        fragment.setArguments(bundle2);
//                        //tvTitle.setText("Sleep");
//                        break;
//                    case 4:
//                        Bundle bundle3 = new Bundle();
//                        fragment = MasterMindFragment.newInstance("Camera", "100", ACTUAL_MIND_STATUS, BING_QUANTITY);
//                        bundle3.putString("arg1", "argValue");
//                        bundle3.putString("arg2", "argValue22");
//                        bundle3.putString("ACTUAL_MIND_STATUS", ACTUAL_MIND_STATUS);
//                        bundle3.putString("BING_QUANTITY", BING_QUANTITY);
//                        fragment.setArguments(bundle3);
//                        //tvTitle.setText("Mind");
//                        break;
//                    case 3:
//                        Bundle bundle4 = new Bundle();
//                        fragment = MasterMyActivityFragment.newInstance("Camera", "100");
//                        bundle4.putString("arg1", "argValue");
//                        bundle4.putString("arg2", "argValue22");
////                    bundle4.putString("ACTUAL_MIND_STATUS",ACTUAL_MIND_STATUS);
////                    bundle4.putString("BING_QUANTITY",BING_QUANTITY);
//                        fragment.setArguments(bundle4);
//                        //tvTitle.setText("Mind");
//                        break;
//
//



//                }








            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        mViewPager.addOnPageChangeListener(this);
//        mViewPager.setOffscreenPageLimit(1);

        mViewPager.setCurrentItem(FRAGMENT_POSITION, true);


        notificationService = Client.getClient().create(NotificationService.class);
        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
//                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
//                        PushFcmToServer();
                    } else if (intent.getAction().equals(FcmConstants.PUSH_NOTIFICATION)) // new push notification is received
                    {
                        String title = intent.getStringExtra(FcmConstants.FCM_TITLE);
                        String message = intent.getStringExtra(FcmConstants.FCM_MESSEGE);
                        mNotifcationCount = intent.getIntExtra(FcmConstants.FCM_COUNT, 0);

                        if (mNotifcationCount > 0) {
                            tvNotificationCOunt.setText(String.valueOf(mNotifcationCount));
                            invalidateOptionsMenu();
                        } else {
                            if (Utils.isNetworkAvailable(context))
                                GetAllNotificationCount();
                            else
                                Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

//        setToolBar();

        counterValuePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(), NotificationsActivity.class), 500);

            }
        });

        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }



    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        tvTitle.setText(tabsTitle[i]);
        FRAGMENT_POSITION = i;
//        tabLayout.setScrollPosition(i, 0f, true);


        Fragment fragment = null;

        switch (i) {

            case 1:

                 //Trigger the onCreate method in the activity
                Bundle bundle1 = new Bundle();
                fragment = MasterWaterFragment.newInstance("Camera", "100");
                bundle1.putString("arg1", "argValue");
                bundle1.putString("arg2", "argValue22");
                fragment.setArguments(bundle1);

                //tvTitle.setText("Water");
                break;
            case 0:

                Bundle bundle = new Bundle();
                fragment =
                        MasterFoodFragment.newInstance("Camera", "100", MEAL_CAL_MAX, COMING_FROM);
                bundle.putString("arg1", "argValue");
                bundle.putString("arg2", "argValue22");
                bundle.putInt("MEAL_CAL_MAX", MEAL_CAL_MAX);
                bundle.putString("COMING_FROM", COMING_FROM);
                bundle.putBoolean("FromWater", FromWater);
                fragment.setArguments(bundle);
                //tvTitle.setText("Food");

                break;

            case 2:

                Bundle bundle2 = new Bundle();
                fragment = MasterSleepFragment.newInstance("Camera", "100");
                bundle2.putString("arg1", "argValue");
                bundle2.putString("arg2", "argValue22");
                fragment.setArguments(bundle2);
                //tvTitle.setText("Sleep");
                break;
            case 4:
                Bundle bundle3 = new Bundle();
                fragment = MasterMindFragment.newInstance("Camera", "100", ACTUAL_MIND_STATUS, BING_QUANTITY);
                bundle3.putString("arg1", "argValue");
                bundle3.putString("arg2", "argValue22");
                bundle3.putString("ACTUAL_MIND_STATUS", ACTUAL_MIND_STATUS);
                bundle3.putString("BING_QUANTITY", BING_QUANTITY);
                fragment.setArguments(bundle3);
                //tvTitle.setText("Mind");
                break;
            case 3:
                Bundle bundle4 = new Bundle();
                fragment = MasterMyActivityFragment.newInstance("Camera", "100");
                bundle4.putString("arg1", "argValue");
                bundle4.putString("arg2", "argValue22");
//                    bundle4.putString("ACTUAL_MIND_STATUS",ACTUAL_MIND_STATUS);
//                    bundle4.putString("BING_QUANTITY",BING_QUANTITY);
                fragment.setArguments(bundle4);
                //tvTitle.setText("Mind");
                break;


            case 5:
//
//                    fragment = WeightFragment.newInstance("Camera", "100");
//                    Bundle bundle5 = new Bundle();
//
//                    bundle5.putString("arg1", "argValue");
//                    bundle5.putString("arg2", "argValue22");
//                    fragment.setArguments(bundle5);


//                    bundle4.putString("ACTUAL_MIND_STATUS",ACTUAL_MIND_STATUS);
//                    bundle4.putString("BING_QUANTITY",BING_QUANTITY);


//                    startActivity(new Intent(context, WeightActivity.class));

                //tvTitle.setText("Mind");
                break;



                /*case 4:
                    Bundle bundle4 = new Bundle();

                    fragment = MasterActivityFragment.newInstance("Camera", "100");
                    bundle4.putString("arg1", "argValue");
                    bundle4.putString("arg2", "argValue22");
                    fragment.setArguments(bundle4);
                    //tvTitle.setText("Activity");
                    break;*/
        }











    }

    @Override
    public void onPageScrollStateChanged(int i) {


    }

    private void init() {
        try {
            FRAGMENT_POSITION = getIntent().getExtras().getInt("FRAGMENT_POSITION");
            ACTUAL_MIND_STATUS = getIntent().getExtras().getString("ACTUAL_MIND_STATUS");
            COMING_FROM = getIntent().getExtras().getString("COMING_FROM");
            BING_QUANTITY = getIntent().getExtras().getString("BING_QUANTITY");
            MEAL_CAL_MAX = getIntent().getExtras().getInt("MEAL_CAL_MAX");
            FromWater = getIntent().getExtras().getBoolean("FromWater");

            if (COMING_FROM == null && BING_QUANTITY == null) {
                COMING_FROM = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setToolBar() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (context!=null){
            new SessionManager(context).setStringValue("backdatesubmit","");
            new SessionManager(context).setStringValue("backdateShow","");
            new SessionManager(context).setStringValue("statusdate","");

        }

        if (COMING_FROM != null && COMING_FROM.equalsIgnoreCase("MyAnalysisActivity")) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            backPressed();
        }
    }


    private void backPressed() {


        Intent homeActivity = new Intent(MasterDetailsActivity.this, HomeActivity.class);
//        homeActivity.putExtra("ISFromMaster",true);

        homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeActivity);
//        setResult(RESULT_OK, homeActivity);

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);



//        latest//
//        Intent homeActivity = new Intent(MasterDetailsActivity.this, HomeActivity.class);
//        homeActivity.putExtra("ISFromMaster",true);
//        setResult(RESULT_OK, homeActivity);
//
//        finish();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    private void findViews() {
        tvTitle = findViewById(R.id.tvTitle);

        // set initial page title
        tvTitle.setText(tabsTitle[0]);

        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        counterValuePanel = findViewById(R.id.counterValuePanel);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    Bundle bundle = new Bundle();
                    fragment =
                            MasterFoodFragment.newInstance("Camera", "100", MEAL_CAL_MAX, COMING_FROM);
                    bundle.putString("arg1", "argValue");
                    bundle.putString("arg2", "argValue22");
                    bundle.putInt("MEAL_CAL_MAX", MEAL_CAL_MAX);
                    bundle.putString("COMING_FROM", COMING_FROM);
                    bundle.putBoolean("FromWater", FromWater);
                    fragment.setArguments(bundle);
                    //tvTitle.setText("Food");

                    break;
                case 1:
                    Bundle bundle1 = new Bundle();
                    fragment = MasterWaterFragment.newInstance("Camera", "100");
                    bundle1.putString("arg1", "argValue");
                    bundle1.putString("arg2", "argValue22");
                    fragment.setArguments(bundle1);
                    //tvTitle.setText("Water");
                    break;
                case 2:
                    Bundle bundle2 = new Bundle();
                    fragment = MasterSleepFragment.newInstance("Camera", "100");
                    bundle2.putString("arg1", "argValue");
                    bundle2.putString("arg2", "argValue22");
                    fragment.setArguments(bundle2);
                    //tvTitle.setText("Sleep");
                    break;
                case 4:
                    Bundle bundle3 = new Bundle();
                    fragment = MasterMindFragment.newInstance("Camera", "100", ACTUAL_MIND_STATUS, BING_QUANTITY);
                    bundle3.putString("arg1", "argValue");
                    bundle3.putString("arg2", "argValue22");
                    bundle3.putString("ACTUAL_MIND_STATUS", ACTUAL_MIND_STATUS);
                    bundle3.putString("BING_QUANTITY", BING_QUANTITY);
                    fragment.setArguments(bundle3);
                    //tvTitle.setText("Mind");
                    break;
                case 3:
                    Bundle bundle4 = new Bundle();
                    fragment = MasterMyActivityFragment.newInstance("Camera", "100");
                    bundle4.putString("arg1", "argValue");
                    bundle4.putString("arg2", "argValue22");
//                    bundle4.putString("ACTUAL_MIND_STATUS",ACTUAL_MIND_STATUS);
//                    bundle4.putString("BING_QUANTITY",BING_QUANTITY);
                    fragment.setArguments(bundle4);
                    //tvTitle.setText("Mind");
                    break;


                case 5:
//
//                    fragment = WeightFragment.newInstance("Camera", "100");
//                    Bundle bundle5 = new Bundle();
//
//                    bundle5.putString("arg1", "argValue");
//                    bundle5.putString("arg2", "argValue22");
//                    fragment.setArguments(bundle5);


//                    bundle4.putString("ACTUAL_MIND_STATUS",ACTUAL_MIND_STATUS);
//                    bundle4.putString("BING_QUANTITY",BING_QUANTITY);


//                    startActivity(new Intent(context, WeightActivity.class));

                    //tvTitle.setText("Mind");
                    break;



                /*case 4:
                    Bundle bundle4 = new Bundle();
                    fragment = MasterActivityFragment.newInstance("Camera", "100");
                    bundle4.putString("arg1", "argValue");
                    bundle4.putString("arg2", "argValue22");
                    fragment.setArguments(bundle4);
                    //tvTitle.setText("Activity");
                    break;*/
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }
    }

    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

        Call<NotifCountResponse> call = notificationService.getAllNotificationCount(request);

        call.enqueue(new Callback<NotifCountResponse>() {
            @Override
            public void onResponse(Call<NotifCountResponse> call, Response<NotifCountResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    NotifCountResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mNotifcationCount = listResponse.getData();

                        invalidateOptionsMenu();
                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifCountResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }

}
