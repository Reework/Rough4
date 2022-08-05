package com.shamrock.reework.activity.FoodModule.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterFoodFragment extends Fragment implements View.OnClickListener
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM4 = "param4";

    private static final String ARG_PARAM3 = "MEAL_CAL_MAX";

    private static final String TAG_FRAGMENT_ALL_MEAL = "ALL_MEAL";
    private static final String TAG_FRAGMENT_FOOD_TRIP = "FOOD_TRIP";

   static RadioButton rbAllMeal, rbSuggetion, rbFoodTrip, rbOthers,btn_food_history, button_video;
    FrameLayout frameLayout;
    RadioGroup radioGroup;
    String abc;
    private String mParam1;
    private String mParam2;
    private String mParam4;
    private int mParam3;

    private boolean FromWater=false;
    private OnFragmentInteractionListener mListener;
    private Context context;
    private SessionManager session;

    LinearLayout layout_profile,layout_home,layout_setting;


    public MasterFoodFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MasterFoodFragment.
     */
    public static MasterFoodFragment newInstance(String param1, String param2,int MEAL_CAL_MAX,String commingForm)
    {
        MasterFoodFragment fragment = new MasterFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3,MEAL_CAL_MAX);
        args.putString(ARG_PARAM4,commingForm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        session = new SessionManager(context);
        abc = session.getStringValue("Allpart");
        if(abc.equals("video")){

            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = new FoodVideoFragment();
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();

            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));
        }else  if(abc.equals("tip")){

            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = MasterFoodSuggestionFrag.newInstance("str114", "str242",mParam3);
            ft.replace(R.id.fragmentContainer_Food,fragment, TAG_FRAGMENT_ALL_MEAL).commit();


            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));

        }else  if(abc.equals("history")){

            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = FoodHistoryFragment.newInstance("str11", "str22",mParam3, FromWater);
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();



            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));
        }else  if(abc.equals("daily")){
            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = MasterFoodFragmentAllMeals.newInstance("str11", "str22",mParam3, FromWater);
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();


            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
        }


//        Toast.makeText(getContext(),"abc",Toast.LENGTH_LONG).show();

    }



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getInt(ARG_PARAM3);
            mParam4 =  getArguments().getString(ARG_PARAM4);
            FromWater=getArguments().getBoolean("FromWater");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_master_food, container, false);

        frameLayout = view.findViewById(R.id.fragmentContainer_Food);
        rbAllMeal = view.findViewById(R.id.buttonFoodMaster_AllMeals);
        rbSuggetion = view.findViewById(R.id.buttonFoodMaster_Suggestions);
        rbFoodTrip = view.findViewById(R.id.buttonFoodMaster_FoodTrip);
        rbOthers = view.findViewById(R.id.buttonFoodMaster_Others);
        btn_food_history = view.findViewById(R.id.btn_food_history);
        button_video = view.findViewById(R.id.button_video);


        layout_profile = view.findViewById(R.id.layout_profile);
        layout_home = view.findViewById(R.id.layout_home);
        layout_setting = view.findViewById(R.id.layout_setting);


        layout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyProfileActivity.class);
                startActivity(intent);
            }
        });

        layout_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        layout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Coming Soon",Toast.LENGTH_LONG).show();
            }
        });


        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        session = new SessionManager(context);
        abc = session.getStringValue("Allpart");
        if(abc.equals("video")){

            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = new FoodVideoFragment();
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();

            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));
        }else  if(abc.equals("tip")){

            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = MasterFoodSuggestionFrag.newInstance("str114", "str242",mParam3);
            ft.replace(R.id.fragmentContainer_Food,fragment, TAG_FRAGMENT_ALL_MEAL).commit();


            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));

        }else  if(abc.equals("history")){

            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = FoodHistoryFragment.newInstance("str11", "str22",mParam3, FromWater);
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();



            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));
        }else  if(abc.equals("daily")){
            FragmentManager fm;
            FragmentTransaction ft;
            Fragment fragment;
            fm = getChildFragmentManager();
            ft = fm.beginTransaction();
            fragment = MasterFoodFragmentAllMeals.newInstance("str11", "str22",mParam3, FromWater);
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();


            btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
            button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
            rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
        }

        rbAllMeal.setOnClickListener(this);
        rbSuggetion.setOnClickListener(this);
        rbFoodTrip.setOnClickListener(this);
        rbOthers.setOnClickListener(this);
        button_video.setOnClickListener(this);
        btn_food_history.setOnClickListener(this);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager fm  = getChildFragmentManager();
        FragmentTransaction  ft = fm.beginTransaction();

        if(mParam4!=null && mParam4.equalsIgnoreCase("FoodRecipeActivity")){
            Fragment fragment = MasterFoodFragmentFoodTrip.newInstance();
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();
            rbFoodTrip.setChecked(true);
        }else {
            Fragment fragment = MasterFoodFragmentAllMeals.newInstance("str11", "str22",mParam3,FromWater);
            ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();
            rbAllMeal.setChecked(true);
        }

       // buttonCheckChanged();
    }

    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        FragmentManager fm;
        FragmentTransaction ft;
        Fragment fragment;
        switch (v.getId()){

            case R.id.buttonFoodMaster_AllMeals:
                session = new SessionManager(context);
                session.setStringValue("Allpart","daily");
                btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rbAllMeal.setTextColor(getResources().getColor(R.color.white));
                btn_food_history.setTextColor(getResources().getColor(R.color.black));
                button_video.setTextColor(getResources().getColor(R.color.black));
                rbSuggetion.setTextColor(getResources().getColor(R.color.black));
                rbFoodTrip.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbFoodTrip.setTextColor(getResources().getColor(R.color.black));
                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                fragment = MasterFoodFragmentAllMeals.newInstance("str11", "str22",mParam3, FromWater);
                ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();
                break;

            case R.id.buttonFoodMaster_FoodTrip:

                btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbFoodTrip.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rbFoodTrip.setTextColor(getResources().getColor(R.color.white));
                rbAllMeal.setTextColor(getResources().getColor(R.color.black));
                btn_food_history.setTextColor(getResources().getColor(R.color.black));
                button_video.setTextColor(getResources().getColor(R.color.black));
                rbSuggetion.setTextColor(getResources().getColor(R.color.black));


                session = new SessionManager(context);
                session.setStringValue("Allpart","foodtrip");

                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                fragment = MasterFoodFragmentFoodTrip.newInstance();
                ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();
                break;


            case R.id.btn_food_history:

                session = new SessionManager(context);
                session.setStringValue("Allpart","history");

                btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbAllMeal.setTextColor(getResources().getColor(R.color.black));
                btn_food_history.setTextColor(getResources().getColor(R.color.white));
                button_video.setTextColor(getResources().getColor(R.color.black));
                rbSuggetion.setTextColor(getResources().getColor(R.color.black));
                rbFoodTrip.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbFoodTrip.setTextColor(getResources().getColor(R.color.black));
                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                fragment = FoodHistoryFragment.newInstance("str11", "str22",mParam3, FromWater);
                ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();
                break;


            case R.id.buttonFoodMaster_Suggestions:
                session = new SessionManager(context);
                session.setStringValue("Allpart","tip");
                btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                button_video.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rbAllMeal.setTextColor(getResources().getColor(R.color.black));
                btn_food_history.setTextColor(getResources().getColor(R.color.black));
                button_video.setTextColor(getResources().getColor(R.color.black));
                rbSuggetion.setTextColor(getResources().getColor(R.color.white));
                rbFoodTrip.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbFoodTrip.setTextColor(getResources().getColor(R.color.black));
                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                fragment = MasterFoodSuggestionFrag.newInstance("str114", "str242",mParam3);
                ft.replace(R.id.fragmentContainer_Food,fragment, TAG_FRAGMENT_ALL_MEAL).commit();

            break;

            case R.id.button_video:
                session = new SessionManager(context);
                session.setStringValue("Allpart","video");
                btn_food_history.setBackgroundResource((R.drawable.custom_white_radio_new1));
                button_video.setBackgroundResource((R.drawable.custom_white_radio_new_food_selected));
                rbSuggetion.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbAllMeal.setBackgroundResource((R.drawable.custom_white_radio_new1));

                rbAllMeal.setTextColor(getResources().getColor(R.color.black));
                btn_food_history.setTextColor(getResources().getColor(R.color.black));
                button_video.setTextColor(getResources().getColor(R.color.white));
                rbSuggetion.setTextColor(getResources().getColor(R.color.black));
                rbFoodTrip.setBackgroundResource((R.drawable.custom_white_radio_new1));
                rbFoodTrip.setTextColor(getResources().getColor(R.color.black));
                fm = getChildFragmentManager();
                ft = fm.beginTransaction();
                fragment = new FoodVideoFragment();
                ft.replace(R.id.fragmentContainer_Food, fragment, TAG_FRAGMENT_ALL_MEAL).commit();
                 break;

            case R.id.buttonFoodMaster_Others:
                break;

        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
