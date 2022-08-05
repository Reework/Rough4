package com.shamrock.reework.activity.HealthModule.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.FoodCulturesAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.FoodTypesAdapter;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.FoodCulture;
import com.shamrock.reework.api.response.FoodCulturesResponse;
import com.shamrock.reework.api.response.FoodType;
import com.shamrock.reework.api.response.FoodTypesResponse;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHealth2InteractionListener} interface
 * to handle interaction events.
 * Use the {@link HealthParameters2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthParameters2Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_MY_HEALTH_DATA2 = "ARG_MY_HEALTH_DATA2";

    private String mParam1;
    private String mParam2;

    private OnHealth2InteractionListener mListener;
    Context mContext;
    private HealthParametersService healthParametersService;
    private FoodTypesAdapter foodTypesAdapter;
    private FoodCulturesAdapter foodCultureAdapter;
    private List<FoodType> foodTypeList;
    private List<FoodType> foodTypeList_all;
    private List<FoodCulture> foodCultureList;
    private Utils utils;
    private TextView txt_body_type_tooltip;
    private LinearLayout ll_bodytype;
    ImageView act_1,act_2,act_3,act_4;
    private ArrayList<UserHealthResponse.Data> arylst_my_health_data;

    LinearLayout ll_actvity_status_hint;

    public HealthParameters2Fragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
//     * @param data
     * @return A new instance of fragment HealthParameters2Fragment.
     */
    public static HealthParameters2Fragment newInstance(String param1, String param2, ArrayList<UserHealthResponse.Data> arylst_my_health_data)
    {
        HealthParameters2Fragment fragment = new HealthParameters2Fragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_MY_HEALTH_DATA2,arylst_my_health_data);

        fragment.setArguments(args);
        return fragment;
    }


    public void showtooltip(LinearLayout imageView,String text){
        new SimpleTooltip.Builder(getContext())
                .anchorView(imageView)
                .textColor(mContext.getResources().getColor(R.color.black_overlay_dark))
                .gravity(Gravity.TOP)
                .backgroundColor(getActivity().getResources().getColor(R.color.white))
                .animated(true)
                .contentView(R.layout.lay_text,R.id.txt)
                .text(Html.fromHtml(getResources().getString(R.string.activty_status)))

                .arrowColor(getActivity().getResources().getColor(R.color.white))
                .transparentOverlay(false)
                .build()
                .show();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if (context instanceof OnHealth2InteractionListener)
        {
            mListener = (OnHealth2InteractionListener) context;
        }
        else
            {
            throw new RuntimeException(context.toString()
                    + " must implement OnHealth2InteractionListener");
        }
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            arylst_my_health_data= (ArrayList<UserHealthResponse.Data>) getArguments().getSerializable(ARG_MY_HEALTH_DATA2);

        }

        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();
        foodTypeList = new ArrayList<>();
        foodTypeList_all = new ArrayList<>();
        foodCultureList = new ArrayList<>();
    }

    Spinner spinnerFoodTypes, spinnerFoodCulture;
    RadioButton radioButtonSecondary, radioButtonMildlyActive, radioButtonModeratelyActive, radioButtonHighlyActive;
    RadioButton radioButton_Ectomorph, radioButton_Mesomorph, radioButton_Endomorph;
    // Data Variable
    int FoodtypeID, FoodcultureID, ActivityStatus, BodyType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_parameters2, container, false);

        spinnerFoodTypes = view.findViewById(R.id.spinnerFoodPreferences);
        spinnerFoodCulture = view.findViewById(R.id.spinnerFoodCulture);
        radioButtonSecondary = view.findViewById(R.id.radioButtonSecondary);
        radioButtonMildlyActive = view.findViewById(R.id.radioButtonMildlyActive);
        radioButtonModeratelyActive = view.findViewById(R.id.radioButtonModeratelyActive);
        radioButtonHighlyActive = view.findViewById(R.id.radioButtonHighlyActive);
        radioButton_Ectomorph = view.findViewById(R.id.radioButtonEctomorph);
        radioButton_Endomorph = view.findViewById(R.id.radioButtonEndomorph);
        radioButton_Mesomorph = view.findViewById(R.id.radioButtonMesomorph);
        txt_body_type_tooltip = view.findViewById(R.id.txt_body_type_tooltip);
        ll_bodytype = view.findViewById(R.id.ll_bodytype);
        ll_actvity_status_hint = view.findViewById(R.id.ll_actvity_status_hint);

        act_1=view.findViewById(R.id.act_1);
        act_2=view.findViewById(R.id.act_2);
        act_3=view.findViewById(R.id.act_3);
        act_4=view.findViewById(R.id.act_4);



        ll_actvity_status_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtooltip(ll_actvity_status_hint,"");

            }
        });



        ll_bodytype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder builder=new StringBuilder();


                String txt1="Ectomorph Body:";
                SpannableString txtSpannable= new SpannableString(txt1);
                StyleSpan boldSpan = new StyleSpan(Typeface.ITALIC);
                txtSpannable.setSpan(boldSpan, 0, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.append(txtSpannable);

                new SimpleTooltip.Builder(getContext())
                        .anchorView(txt_body_type_tooltip)
                               .textColor(mContext.getResources().getColor(R.color.black_overlay_dark))


                        .gravity(Gravity.TOP)
                        .backgroundColor(getActivity().getResources().getColor(R.color.white))
                        .animated(true)
                        .contentView(R.layout.lay_text,R.id.txt)
                        .text(Html.fromHtml(getResources().getString(R.string.bodytype)))

                        .arrowColor(getActivity().getResources().getColor(R.color.white))
                        .transparentOverlay(false)
                        .build()
                        .show();
            }
        });

        // call webAPI for FoodType, FoodCulture
        if (Utils.isNetworkAvailable(mContext))
        {
            callFoodTypesApi();
            callFoodCulturesApi();
        }
        else
        {
            ShowRetryBar("Check internet connection!");
        }

        spinnerFoodTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (foodTypesAdapter != null)
                {
                    FoodType country = foodTypesAdapter.getItem(i);
                    FoodtypeID = country.getFoodtypeID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){}
        });

        spinnerFoodCulture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (foodCultureAdapter != null)
                {
                    FoodCulture country = foodCultureAdapter.getItem(i);
                    FoodcultureID = country.getFoodcultureID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){}
        });

        radioButtonSecondary.setOnCheckedChangeListener(this);
        radioButtonMildlyActive.setOnCheckedChangeListener(this);
        radioButtonModeratelyActive.setOnCheckedChangeListener(this);
        radioButtonHighlyActive.setOnCheckedChangeListener(this);
        radioButton_Ectomorph.setOnCheckedChangeListener(this);
        radioButton_Endomorph.setOnCheckedChangeListener(this);
        radioButton_Mesomorph.setOnCheckedChangeListener(this);


        binddata();

        return view;
    }

    private void binddata() {

     if (arylst_my_health_data!=null){
         if (arylst_my_health_data.size()!=0){
             if (arylst_my_health_data.get(0).getActivityStatus()!=null){

                 String activitySatatus=arylst_my_health_data.get(0).getActivityStatus();
                 switch (activitySatatus){

                     case "1":
                         radioButtonSecondary.setChecked(true);
                         break;
                     case "2":
                         radioButtonMildlyActive.setChecked(true);

                         break;
                     case "3":
                         radioButtonModeratelyActive.setChecked(true);

                         break;
                     case "4":
                         radioButtonHighlyActive.setChecked(true);

                         break;
                 }


             }


             if (arylst_my_health_data.get(0).getBody_Type()!=null){

                 String bodytype=arylst_my_health_data.get(0).getBody_Type();

                 switch (bodytype){
                     case "1":
                         radioButton_Mesomorph.setChecked(true);
                         break;
                     case "2":
                         radioButton_Ectomorph.setChecked(true);

                         break;
                     case "3":
                         radioButton_Endomorph.setChecked(true);

                         break;


                 }


             }

         }
     }



    }

    private void ShowRetryBar(String msg)
    {
        String strMessage;
        if (!msg.isEmpty())
        {
            strMessage = msg;
        }
        else
        {
            strMessage = "Unable to load data.";
        }

        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (Utils.isNetworkAvailable(mContext))
                        {
                            callFoodTypesApi();
                            callFoodCulturesApi();
                        }
                        else
                            {
                            ShowRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }

    private void callFoodTypesApi()
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        Call<FoodTypesResponse> call = healthParametersService.getFoodTypesList();
        call.enqueue(new Callback<FoodTypesResponse>()
        {
            @Override
            public void onResponse(Call<FoodTypesResponse> call, Response<FoodTypesResponse> response)
            {
                if (response.body() != null)
                {

                    foodTypeList = response.body().getData();
                    foodTypeList.add(0,new FoodType(0,"Select Food Preference",false,new Object()));

                    foodTypesAdapter = new FoodTypesAdapter(mContext, foodTypeList);
                    spinnerFoodTypes.setAdapter(foodTypesAdapter);
                    FoodtypeID=0;

                    if (arylst_my_health_data!=null){
                       if (!arylst_my_health_data.isEmpty()){
                           try {
                               if (arylst_my_health_data.get(0).getFoodtypeID()!=null){

                                   if (!arylst_my_health_data.get(0).getFoodtypeID().isEmpty()){
                                       FoodtypeID= Integer.parseInt(arylst_my_health_data.get(0).getFoodtypeID());

                                       for (int i = 0; i <foodTypeList.size() ; i++) {

                                           if (arylst_my_health_data.get(0).getFoodtypeID().equalsIgnoreCase(String.valueOf(foodTypeList.get(i).getFoodtypeID()))){

                                               spinnerFoodTypes.setSelection(i);
                                               break;
                                           }
                                       }
                                   }


                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
                    }

                }
                else
                {
                    Toast.makeText(mContext, "" + response, Toast.LENGTH_SHORT).show();
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<FoodTypesResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });
    }

    private void callFoodCulturesApi()
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        Call<FoodCulturesResponse> call = healthParametersService.getFoodCulturesList();
        call.enqueue(new Callback<FoodCulturesResponse>()
        {
            @Override
            public void onResponse(Call<FoodCulturesResponse> call, Response<FoodCulturesResponse> response)
            {
                int statusCode = response.code();
                if (response.body() != null)
                {
                    foodCultureList = response.body().getData();
                    foodCultureList.add(0,new FoodCulture(0,"Select Food Culture",false,new Object()));

                    foodCultureAdapter = new FoodCulturesAdapter(mContext, foodCultureList);
                    spinnerFoodCulture.setAdapter(foodCultureAdapter);
                    FoodcultureID=0;


                    if (arylst_my_health_data!=null){

                        try {
                            if (!arylst_my_health_data.isEmpty()){
                                if (arylst_my_health_data.get(0).getFoodcultureID()!=null){
                                    if (!arylst_my_health_data.get(0).getFoodcultureID().isEmpty()){
                                        FoodcultureID= Integer.parseInt(arylst_my_health_data.get(0).getFoodcultureID());


                                        for (int i = 0; i <foodTypeList.size() ; i++) {

                                            if (arylst_my_health_data.get(0).getFoodcultureID().equalsIgnoreCase(String.valueOf(foodCultureList.get(i).getFoodcultureID()))){

                                                spinnerFoodCulture.setSelection(i);
                                                break;
                                            }
                                        }
                                    }


                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                else
                    {
                    Toast.makeText(mContext, "" + response, Toast.LENGTH_SHORT).show();
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<FoodCulturesResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });
    }

    public void onButtonPressed(boolean isError, String errorMessage)
    {
        if (mListener != null)
        {
            // send validation status
            mListener.onFragment2Interaction(isError, errorMessage);
            // if no error then notify data to activity
            if (!isError)
            {
                mListener.onFragment_2InteractionData(FoodtypeID, FoodcultureID, ActivityStatus, BodyType);
            }

        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public void validateData()
    {
        boolean isError = false;        String errorMessage = "";

        if (!radioButtonSecondary.isChecked() && !radioButtonMildlyActive.isChecked() &&
                !radioButtonModeratelyActive.isChecked() && !radioButtonHighlyActive.isChecked())
        {
//        if (ActivityStatus == 0) {
            isError = true;
            errorMessage = "Select Your Activity Status.";
        }
//        if (BodyType == 0) {
//            isError = true;
//            errorMessage = "Select Your Body Type.";
//        }
        if (!foodCultureList.isEmpty())
        {
            if (!(spinnerFoodCulture.getSelectedItem().toString().length() > 0))
            {
                isError = true;
                errorMessage = "Select Your Food Culture";
            }
            if (spinnerFoodCulture.getSelectedItem().toString().equalsIgnoreCase("Select Food Culture")){
                isError = true;
                errorMessage = "Select Your Food Culture";
            }

            if (FoodcultureID==0){
                isError = true;
                errorMessage = "Select Your Food Culture";
            }



        }
        else
        {
            isError = true;
            ShowRetryBar("Unable to load food cultures");
        }

        if (!foodTypeList.isEmpty())
        {
            if (!(spinnerFoodTypes.getSelectedItem().toString().length() > 0))
            {
                isError = true;
                errorMessage = "Select Your Food Preference";
            }


            if (spinnerFoodTypes.getSelectedItem().toString().equalsIgnoreCase("Select Food Preference")){
                isError = true;
                errorMessage = "Select Your Food Preference";
            }


            if (FoodtypeID==0){
                isError = true;
                errorMessage = "Select Your Food Preference";
            }
        }
        else
        {
            isError = true;
            ShowRetryBar("Unable to load food preferences");
        }

        buttonCheckChanged();

        onButtonPressed(isError, errorMessage);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
       // buttonCheckChanged();

        buttonCheckChangednew(compoundButton);

    }

    private void buttonCheckChangednew(CompoundButton compoundButton) {

        if (compoundButton==radioButtonSecondary){

            if (radioButtonSecondary.isChecked()){

                ActivityStatus = 1;

                radioButtonMildlyActive.setChecked(false);
                radioButtonHighlyActive.setChecked(false);
                radioButtonModeratelyActive.setChecked(false);
                radioButtonModeratelyActive.setChecked(false);
            }
        }
        if (compoundButton==radioButtonMildlyActive){

            if (radioButtonMildlyActive.isChecked()){
                ActivityStatus = 2;

                radioButtonSecondary.setChecked(false);
                radioButtonHighlyActive.setChecked(false);
                radioButtonModeratelyActive.setChecked(false);
            }
        }

        if (compoundButton==radioButtonModeratelyActive){

            if (radioButtonModeratelyActive.isChecked()){
                ActivityStatus = 3;

                radioButtonSecondary.setChecked(false);
                radioButtonHighlyActive.setChecked(false);
                radioButtonMildlyActive.setChecked(false);
            }
        }



        if (compoundButton==radioButtonHighlyActive){

            if (radioButtonHighlyActive.isChecked()){

                ActivityStatus = 4;

                radioButtonSecondary.setChecked(false);
                radioButtonModeratelyActive.setChecked(false);
                radioButtonMildlyActive.setChecked(false);
            }
        }


    }

    private void buttonCheckChanged()
    {
        if (radioButtonSecondary.isChecked())
        {
            ActivityStatus = 1;
        }
        else if (radioButtonMildlyActive.isChecked())
        {
            ActivityStatus = 2;
        }
        else if (radioButtonModeratelyActive.isChecked())
        {
            ActivityStatus = 3;
        }
        else if (radioButtonHighlyActive.isChecked())
        {
            ActivityStatus = 4;
        }
        if (radioButton_Mesomorph.isChecked())
        {
            BodyType = 1;
        }
        else if (radioButton_Ectomorph.isChecked())
        {
            BodyType = 2;
        }
        else if (radioButton_Endomorph.isChecked())
        {
            BodyType = 3;
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
    public interface OnHealth2InteractionListener
    {
        void onFragment2Interaction(boolean isError, String errorMessage);

        void onFragment_2InteractionData(int FoodtypeID, int FoodcultureID, int ActivityStatus, int BodyType);
    }
}
