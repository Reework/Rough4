package com.shamrock.reework.activity.HealthModule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.fragments.HealthParameters4Fragment;
import com.shamrock.reework.activity.HealthModule.service.HealthRequest;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.api.request.HealthParameterRequest;
import com.shamrock.reework.api.response.HealthParameterResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.activity.HealthModule.fragments.HealthParameters1Fragment;
import com.shamrock.reework.activity.HealthModule.fragments.HealthParameters2Fragment;
import com.shamrock.reework.activity.HealthModule.fragments.HealthParameters3Fragment;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthParametersActivity extends AppCompatActivity
                                                implements View.OnClickListener,
                                                HealthParameters1Fragment.OnHealth1InteractionListener,
                                                HealthParameters2Fragment.OnHealth2InteractionListener,
                                                HealthParameters3Fragment.OnHealth3InteractionListener,
        HealthParameters4Fragment.OnHealth4InteractionListener
{
    Context context;
    Toolbar toolbar;
    Typeface font;

    private static final String TAG_FRAGMENT_ONE = "fragment_one";
    private static final String TAG_FRAGMENT_TWO = "fragment_two";
    private static final String TAG_FRAGMENT_THREE = "fragment_three";
    private static final String TAG_FRAGMENT_FOUR = "fragment_four";
    FragmentManager fragmentManager;
    Fragment currentFragment;

    Button btnNext;
    TextView textTitle;
    ImageView imgProgress1, imgProgress2, imgProgress3,imgProgress4;
    boolean fragment_1_isError = true, fragment_2_isError = true, fragment_3_isError = true,fragment_4_isError = true;

    // Below variables are Health Parameter Data
    int Health_And_Wellness, Weight_Control, weight_Gain, Easy_Monitoring_of_Body_Parameters, body_shape, Height;
    double Weight;
    int FoodtypeID, FoodcultureID, ActivityStatus, BodyType;
    int Avgsleep, Waterintake, Drink, Smoke;
String MedicalConditionID;
    private HealthParametersService healthParametersService;
    SessionManager sessionManager;
    private boolean isFirstTime;
    private Utils utils;
//    Data data;
    private ArrayList<UserHealthResponse.Data> arylst_healthdata;
    boolean isFromFirstHealth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_parameters);

        context = HealthParametersActivity.this;
        init();
        setToolBar();
        findViews();

        isFromFirstHealth=getIntent().getBooleanExtra("isFromFirstHealth",false);
        Intent intent=new Intent(HealthParametersActivity.this,HealthParameterGoodJobActivity.class);
        intent .putExtra("isFirstTime", isFirstTime);
        startActivity(intent);
        finish();

        callHealthParametergetApi();
    }

    private void callHealthParametergetApi() {

        HealthRequest healthRequest=new HealthRequest();
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Log.e("idd", String.valueOf(userID));

        healthRequest.setUserId(userID);


        utils.showProgressbar(HealthParametersActivity.this);

        Call<UserHealthResponse> call = healthParametersService.getHealthparametdata(healthRequest);
        call.enqueue(new Callback<UserHealthResponse>()
        {
            @Override
            public void onResponse(Call<UserHealthResponse> call, retrofit2.Response<UserHealthResponse> response)
            {

                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        UserHealthResponse moodResponse = response.body();
                        if (moodResponse.getCode()!=null){

                            if (moodResponse.getCode().equals("1")){
                                arylst_healthdata=new ArrayList<>();
                                arylst_healthdata.add(moodResponse.getData());
                                loadFragments();
                            }else {
                                arylst_healthdata=new ArrayList<>();
                                loadFragments();
                            }



                        }else {
                            loadFragments();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadFragments();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<UserHealthResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
                loadFragments();
            }
        });

    }

    private void loadFragments()
    {


        Fragment fragmentMain = fragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE);
        if (fragmentMain == null)
        {
//            fragmentMain = HealthParameters1Fragment.newInstance("Camera", "100");
            fragmentMain = HealthParameters1Fragment.newInstance("Camera", "100",arylst_healthdata);
            addFragment(fragmentMain, TAG_FRAGMENT_ONE);
        }
        else
        {
            Log.e("TAG", "onCreate, Fragment ONE already added");
        }

    }

    private void init()

    {
        fragmentManager = getSupportFragmentManager();
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        sessionManager = new SessionManager(context);
        utils = new Utils();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            isFirstTime = bundle.getString("param", "NA").equalsIgnoreCase("First_Time");
        }
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText(R.string.title_health_parameters);
        //tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                //finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        imgProgress1 = findViewById(R.id.imgHealthProgress_1);
        imgProgress2 = findViewById(R.id.imgHealthProgress_2);
        imgProgress3 = findViewById(R.id.imgHealthProgress_3);
        imgProgress4 = findViewById(R.id.imgHealthProgress_4);
        btnNext = findViewById(R.id.buttonNext_Health);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onFragment1Interaction(boolean isError, String errorMessage)
    {
        fragment_1_isError = isError;

        Fragment fragment = null;
        if (!fragment_1_isError)
        {
            //show frag_2
            fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
            if (fragment == null)
            {
                fragment = HealthParameters2Fragment.newInstance("Camera", " increment by TWO",arylst_healthdata);
                addFragment(fragment, TAG_FRAGMENT_TWO);
            }
            else
            {
                Log.d("LOG", TAG_FRAGMENT_TWO + "Already added");
            }

            showFragment(fragment, TAG_FRAGMENT_TWO);
            setTitleByTag(TAG_FRAGMENT_TWO);
            imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
            fragment_2_isError = true;
        }
        else
        {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFragment_1InteractionData(int Health_And_Wellness, int Weight_Control, int weight_Gain,
                                            int Easy_Monitoring_of_Body_Parameters, int body_shape, int Height, double Weight)
    {
        this.Health_And_Wellness = Health_And_Wellness;
        this.Weight_Control = Weight_Control;
        this.weight_Gain = weight_Gain;
        this.Easy_Monitoring_of_Body_Parameters = Easy_Monitoring_of_Body_Parameters;
        this.body_shape = body_shape;
        this.Height = Height;
        this.Weight = Weight;

      Log.d("FRAGMENT 1 = ", Health_And_Wellness + "\n" + Weight_Control + "\n" + weight_Gain +
              "\n" + Easy_Monitoring_of_Body_Parameters + "\n" + body_shape + "\n" + Height + "\n" + Weight);
    }

    @Override
    public void onFragment2Interaction(boolean isError, String errorMessage)
    {
        fragment_2_isError = isError;

        Fragment fragment = null;
        if (!fragment_2_isError)
        {
            //show frag_3
            fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE);
            if (fragment == null)
            {
                fragment = HealthParameters3Fragment.newInstance("Camera", " increment by TWO",arylst_healthdata);
                addFragment(fragment, TAG_FRAGMENT_THREE);
            }
            else
            {
                Log.d("LOG", TAG_FRAGMENT_THREE + "Already added");
            }

            showFragment(fragment, TAG_FRAGMENT_THREE);
            setTitleByTag(TAG_FRAGMENT_THREE);
            imgProgress3.setImageResource(R.drawable.ic_health_progress_done);
            fragment_3_isError = true;

        }
        else
        {
            if (!errorMessage.isEmpty())
            {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragment_2InteractionData(int FoodtypeID, int FoodcultureID, int ActivityStatus, int BodyType)
    {
        this.FoodtypeID = FoodtypeID;
        this.FoodcultureID = FoodcultureID;
        this.ActivityStatus = ActivityStatus;
        this.BodyType = BodyType;

        Log.d("DATA 2 = ", FoodtypeID + "\n" + FoodcultureID + "\n" + ActivityStatus +"\n" +BodyType);
    }

    @Override
    public void onFragment3tInteraction(boolean isError, int Avgsleep, int Waterintake, int Drink, int Smoke,
                                        int MedicalConditionID, String errorMessage)
    {
        fragment_3_isError = isError;

        if (!fragment_3_isError)
        {
            this.Avgsleep = Avgsleep;
            this.Waterintake = Waterintake;
            this.Drink = Drink;
            this.Smoke = Smoke;


        Log.d("DATA = ", Avgsleep + "\n" + Waterintake + "\n" + Drink + "\n" + Smoke + "\n" + MedicalConditionID);

            // send data to server and call GoodJob page


            fragment_3_isError = isError;

            Fragment fragment = null;
            if (!fragment_3_isError)
            {
                //show frag_3
                fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                if (fragment == null)
                {
                    fragment = HealthParameters4Fragment.newInstance("Camera", " increment by TWO",arylst_healthdata);
                    addFragment(fragment, TAG_FRAGMENT_FOUR);
                }
                else
                {
                    Log.d("LOG", TAG_FRAGMENT_FOUR + "Already added");
                }

                showFragment(fragment, TAG_FRAGMENT_FOUR);
                setTitleByTag(TAG_FRAGMENT_FOUR);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_done);
                fragment_3_isError = true;

            }
            else
            {
                if (!errorMessage.isEmpty())
                {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }



























        }
        else
        {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    //    @Override
//    public void onFragment_3InteractionData(int Avgsleep, int Waterintake, int Drink, int Smoke, int MedicalConditionID) {
//        this.Avgsleep = Avgsleep;
//        this.Waterintake = Waterintake;
//        this.Drink = Drink;
//        this.Smoke = Smoke;
//        this.MedicalConditionID = MedicalConditionID;
////        Log.w("DATA = ", Avgsleep + "\n" + Waterintake + "\n" + Drink + "\n" + Smoke + "\n" + MedicalConditionID);
//    }

    private void callHealthParameterApi()
    {
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        HealthParameterRequest request = new HealthParameterRequest();
        request.setUserID(userID);
        request.setHealth_And_Wellness(Health_And_Wellness);
        request.setWeight_Control(Weight_Control);
        request.setWeight_Gain(weight_Gain);
        request.setEasy_Monitoring_of_Body_Parameters(Easy_Monitoring_of_Body_Parameters);
        request.setBody_shape(body_shape);
        request.setHeight(Height);
        request.setWeight(Weight);
        request.setFoodtypeID(FoodtypeID);
        request.setFoodcultureID(FoodcultureID);
        request.setActivityStatus(ActivityStatus);
        request.setBodyType(BodyType);
        request.setAvgsleep(Avgsleep);
        request.setWaterintake(Waterintake);
        request.setDrink(Drink);
        request.setSmoke(Smoke);
        request.setMedicalConditionID(MedicalConditionID);
     //   {"ActivityStatus":2,"Avgsleep":8,"Drink":1,"Easy_Monitoring_of_Body_Parameters":0,"FoodcultureID":1,"FoodtypeID":3,"Health_And_Wellness":0,"Height":195,"MedicalConditionID":"1,2,3,4,5,6,7,8,9,10,11,12","Smoke":1,"UserID":3040,"Waterintake":5,"Weight":64.0,"Weight_Control":0,"body_Type":1,"body_shape":2,"weight_Gain":1}
        Call<HealthParameterResponse> call = healthParametersService.sendHealthParameter(request);
        call.enqueue(new Callback<HealthParameterResponse>()
        {
            @Override
            public void onResponse(Call<HealthParameterResponse> call, Response<HealthParameterResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    HealthParameterResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        double weight = listResponse.getData().getWeight();
                        double idealWeight = listResponse.getData().getWeight();
                        boolean healthFound = true;

                        sessionManager.setHealthParameterData(weight, idealWeight, healthFound);
                        Intent intent=new Intent(HealthParametersActivity.this,HealthParameterGoodJobActivity.class);

                        intent .putExtra("isFirstTime", isFirstTime);
                        startActivity(intent);
                        finish();


//                        }
                       /* finish();*/
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                        fragment_3_isError = true;
                    }
                }
                else
                {
                    Toast.makeText(context, "" + response.code(), Toast.LENGTH_SHORT).show();
                    fragment_3_isError = true;
                }
            }

            @Override
            public void onFailure(Call<HealthParameterResponse> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonNext_Health:

                Fragment fragment = null;

                if (!fragment_3_isError && !fragment_2_isError && !fragment_1_isError)
                {
                    // check current fragment and show next page
                    if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_ONE))
                    {
                        fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                        showFragment(fragment, TAG_FRAGMENT_TWO);
                        setTitleByTag(TAG_FRAGMENT_TWO);
                    }
                    else

                        if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_TWO))
                    {
                        fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE);
                        showFragment(fragment, TAG_FRAGMENT_THREE);
                        setTitleByTag(TAG_FRAGMENT_THREE);
                    }

                    else if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_THREE))
                    {

                        HealthParameters4Fragment fragX = (HealthParameters4Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                        fragX.validateData();

                        //   fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                        //  showFragment(fragment, TAG_FRAGMENT_FOUR);
                        // setTitleByTag(TAG_FRAGMENT_FOUR);
                    }

                    else if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_FOUR))
                    {
                        HealthParameters4Fragment fragX = (HealthParameters4Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                        fragX.validateData();

                    }
                }
                else if (!fragment_2_isError && !fragment_1_isError)
                {
                    // Validate fragment3 else
                    if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_THREE))
                    {
                        HealthParameters3Fragment fragX = (HealthParameters3Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE);

                        setTitleByTag(TAG_FRAGMENT_THREE);
                        fragX.validateData();

                    }
                    else if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_TWO))
                    {
                        fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_THREE);
                        showFragment(fragment, TAG_FRAGMENT_THREE);
                        setTitleByTag(TAG_FRAGMENT_THREE);
                    }

                    else if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_FOUR))
                    {

                        HealthParameters4Fragment fragX = (HealthParameters4Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                        fragX.validateData();

                     //   fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_FOUR);
                      //  showFragment(fragment, TAG_FRAGMENT_FOUR);
                       // setTitleByTag(TAG_FRAGMENT_FOUR);
                    }
                    else if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_ONE))
                    {
                        fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                        showFragment(fragment, TAG_FRAGMENT_TWO);
                        setTitleByTag(TAG_FRAGMENT_TWO);

                    }
                }
                else if (!fragment_1_isError)
                {
                    // Validate fragment2
                    if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_TWO))
                    {
                        HealthParameters2Fragment fragX = (HealthParameters2Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                        fragX.validateData();
                    }
                    else if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_ONE))
                    {
                        fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                        showFragment(fragment, TAG_FRAGMENT_TWO);
                        setTitleByTag(TAG_FRAGMENT_TWO);
                    }
                }
                else
                {
                    // Validate fragment1
                    if (currentFragment.getTag().equalsIgnoreCase(TAG_FRAGMENT_ONE))
                    {
                        HealthParameters1Fragment fragX = (HealthParameters1Fragment) fragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE);
                        fragX.validateData();
                    }
                }
//                Toast.makeText(context, "Current Fragment: " + currentFragment.getTag(), Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    // set Title of selected fragment by its Tag
    private void setTitleByTag(String tag)
    {
        switch (tag)
        {
            case TAG_FRAGMENT_ONE:
//                setTitle(R.string.m_camera);
                imgProgress1.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_2);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);

                break;
            case TAG_FRAGMENT_TWO:
//                setTitle(R.string.m_gallery);
                imgProgress2.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress3.setImageResource(R.drawable.ic_health_progress_3);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);

                break;
            case TAG_FRAGMENT_THREE:
                imgProgress3.setImageResource(R.drawable.ic_health_progress_done);
                imgProgress4.setImageResource(R.drawable.ic_health_progress_4);

                break;
            case TAG_FRAGMENT_FOUR:
                imgProgress4.setImageResource(R.drawable.ic_health_progress_done);
//                setTitle(R.string.m_slideshow);
                break;
            default:
        }
    }

    // add fragment instance
    private void addFragment(@NonNull Fragment fragment, @NonNull String tag)
    {
        fragmentManager
                .beginTransaction()
                .add(R.id.frameContainer_HealthParameter, fragment, tag)
                .commit();
        currentFragment = fragment;
    }

    // to show fragment by Tag and hide other fragments
    private void showFragment(@NonNull Fragment fragment, @NonNull String tag)
    {
//        if (!fragment.equals(currentFragment)) {
        // Insert the fragment by replacing any existing fragment
//            fragmentManager.beginTransaction().show(fragment).commit();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        List<Fragment> allFragments = fragmentManager.getFragments();

        if (allFragments == null || allFragments.isEmpty())
        {
            return;
        }

        for (Fragment fragmentX : allFragments)
        {
            if (fragmentX.getTag().equalsIgnoreCase(tag))
            {
                ft.show(fragmentX).commit();
                currentFragment = fragmentX;
            }
            else
            {
                FragmentTransaction ftHide = fragmentManager.beginTransaction();
                ftHide.hide(fragmentX).commit();
            }
//                if (fragmentX.isVisible()) {
//                }
        }
//        }
    }

    @Override
    public void onBackPressed()
    {
        String tag = currentFragment.getTag();
        Fragment fragment = null;
        switch (tag)
        {
            case TAG_FRAGMENT_ONE:
                showExitDialog();
                break;
            case TAG_FRAGMENT_TWO:
                fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_ONE);
                showFragment(fragment, TAG_FRAGMENT_ONE);
                setTitleByTag(TAG_FRAGMENT_ONE);
                break;
            case TAG_FRAGMENT_THREE:
                fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                showFragment(fragment, TAG_FRAGMENT_TWO);
                setTitleByTag(TAG_FRAGMENT_TWO);
                break;
            case TAG_FRAGMENT_FOUR:
                fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_TWO);
                showFragment(fragment, TAG_FRAGMENT_THREE);
                setTitleByTag(TAG_FRAGMENT_THREE);
                break;
            default:

        }
    }

    private void showExitDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alert..");
        builder.setMessage("Do you want to cancel Health parameter setting?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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

    @Override
    public void onFragment4Interaction(boolean uri, String msg) {

        fragment_3_isError = uri;


    }

    @Override
    public void onFragment_4InteractionData(String ids) {

        this.MedicalConditionID = ids;
        imgProgress4.setImageResource(R.drawable.ic_health_progress_done);


            if (!((Activity) context).isFinishing())
           {
              utils.showProgressbar(context);
            }

        callHealthParameterApi();
    }
}
