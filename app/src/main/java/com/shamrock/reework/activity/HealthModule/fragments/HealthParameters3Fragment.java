package com.shamrock.reework.activity.HealthModule.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.adapter.MedicalConditionsAdapter;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.api.response.MedicalCondition;
import com.shamrock.reework.api.response.MedicalConditionsResponse;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHealth3InteractionListener} interface
 * to handle interaction events.
 * Use the {@link HealthParameters3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthParameters3Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_MY_HEALTH_DATA3 = "ARG_MY_HEALTH_DATA3";

    private String mParam1;
    private String mParam2;

    private OnHealth3InteractionListener mListener;
    private HealthParametersService healthParametersService;
    private Context mContext;
    private List<MedicalCondition> medicalConditionList;
    private MedicalConditionsAdapter medicalConditionsAdapter;
    private Utils utils;

    Spinner spinnerAverageSleepPerDay, spinnerDailyWaterIntake, spinnerMedicalCondition;
    RadioButton radioButtonNoDrink, radioButton12, radioButton34;
    RadioButton radioButtonSmokeNo, radioButtonSmokeYes;
    // Data Variable
    int Avgsleep, Waterintake, Drink, Smoke, MedicalConditionID;
    private ArrayList<UserHealthResponse.Data> arylst_my_health_data;

    public HealthParameters3Fragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
//     * @param data
     * @return A new instance of fragment HealthParameters3Fragment.
     */
    public static HealthParameters3Fragment newInstance(String param1, String param2, ArrayList<UserHealthResponse.Data> arylst_my_health_data)
    {
        HealthParameters3Fragment fragment = new HealthParameters3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_MY_HEALTH_DATA3, arylst_my_health_data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnHealth3InteractionListener)
        {
            mListener = (OnHealth3InteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnHealth3InteractionListener");
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
            arylst_my_health_data = (ArrayList<UserHealthResponse.Data>) getArguments().getSerializable(ARG_MY_HEALTH_DATA3);
        }

        healthParametersService = Client.getClient().create(HealthParametersService.class);
        utils = new Utils();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_parameters3, container, false);

        spinnerAverageSleepPerDay = view.findViewById(R.id.spinnerAverageSleepPerDay);
        spinnerDailyWaterIntake = view.findViewById(R.id.spinnerDailyWaterIntake);
        spinnerMedicalCondition = view.findViewById(R.id.spinnerMedicalCondition);
        radioButtonNoDrink = view.findViewById(R.id.radioButtonNoDrink);
        radioButton12 = view.findViewById(R.id.radioButton12);
        radioButton34 = view.findViewById(R.id.radioButton34);
        radioButtonSmokeNo = view.findViewById(R.id.radioButtonSmokeNo);
        radioButtonSmokeYes = view.findViewById(R.id.radioButtonSmokeYes);

        spinnerAverageSleepPerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Avgsleep = (i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerDailyWaterIntake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                Waterintake = (i + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerMedicalCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (medicalConditionsAdapter != null)
                {
                    MedicalCondition country = medicalConditionsAdapter.getItem(i);
                    MedicalConditionID = country.getMedicalConditionID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        radioButtonNoDrink.setOnCheckedChangeListener(this);
        radioButton12.setOnCheckedChangeListener(this);
        radioButton34.setOnCheckedChangeListener(this);
        radioButtonSmokeNo.setOnCheckedChangeListener(this);
        radioButtonSmokeYes.setOnCheckedChangeListener(this);

        radioButtonNoDrink.setChecked(true);
        medicalConditionList = new ArrayList<>();

        //call api
        if (Utils.isNetworkAvailable(mContext))
        {
            callHealthConditionsApi();
        }
        else
        {
            ShowRetryBar("Check internet connection!");
        }



        bindata();
        return view;
    }

    private void bindata() {


        if (arylst_my_health_data!=null){

            try {
                if (!arylst_my_health_data.isEmpty()){

                    if (arylst_my_health_data.get(0).getAvgsleep()!=null){
                        Avgsleep= Integer.parseInt(arylst_my_health_data.get(0).getAvgsleep());

                        int pos= Integer.parseInt(arylst_my_health_data.get(0).getAvgsleep()) -1;
                        spinnerAverageSleepPerDay.setSelection(pos);
                    }


                    if (arylst_my_health_data.get(0).getWaterintake()!=null){

                        Waterintake= Integer.parseInt(arylst_my_health_data.get(0).getWaterintake());
                        int pos= Integer.parseInt(arylst_my_health_data.get(0).getWaterintake()) -1;
                        spinnerDailyWaterIntake.setSelection(pos);
                    }



                    if (arylst_my_health_data.get(0).getDrink()!=null){


                        Drink= Integer.parseInt(arylst_my_health_data.get(0).getDrink());

                        switch (arylst_my_health_data.get(0).getDrink()){


                            case "1":
                                radioButtonNoDrink.setChecked(true);
                                break;


                            case "2":
                                radioButton12.setChecked(true);

                                break;

                            case "3":
                                radioButton34.setChecked(true);

                                break;
                        }
                    }



                    if (arylst_my_health_data.get(0).getSmoke()!=null){
                        Smoke= Integer.parseInt(arylst_my_health_data.get(0).getSmoke());
                        switch (arylst_my_health_data.get(0).getSmoke()){



                            case "2":
                                radioButtonSmokeNo.setChecked(true);

                                break;



                            case "1":
                                radioButtonSmokeYes.setChecked(true);
                                break;

                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
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
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Utils.isNetworkAvailable(mContext)) {
                          //  callHealthConditionsApi();
                        } else {
                            ShowRetryBar("Check internet connection!");
                        }
                    }
                });
        snackbar.show();
    }

    private void callHealthConditionsApi()
    {
        if (!((Activity) mContext).isFinishing())
        {
            utils.showProgressbar(mContext);
        }

        Call<MedicalConditionsResponse> call = healthParametersService.getMedicalConditionList();
        call.enqueue(new Callback<MedicalConditionsResponse>()
        {
            @Override
            public void onResponse(Call<MedicalConditionsResponse> call, Response<MedicalConditionsResponse> response)
            {
                int statusCode = response.code();
                if (response.body() != null)
                {
                    medicalConditionList = response.body().getData();
                    medicalConditionsAdapter = new MedicalConditionsAdapter(mContext, medicalConditionList);
                    spinnerMedicalCondition.setAdapter(medicalConditionsAdapter);



                    if (arylst_my_health_data!=null){

                        if (!arylst_my_health_data.isEmpty()){
                            if (arylst_my_health_data.get(0).getMedicalConditionID()!=null){

                                for (int i = 0; i <medicalConditionList.size() ; i++) {
                                    if (arylst_my_health_data.get(0).getMedicalConditionID().equalsIgnoreCase(String.valueOf(medicalConditionList.get(i).getMedicalConditionID()))){
                                        spinnerMedicalCondition.setSelection(i);
                                        break;
                                    }



                                }

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
            public void onFailure(Call<MedicalConditionsResponse> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });
    }

    public void onButtonPressed(boolean hasError, String errorMessage)
    {
        if (mListener != null)
        {
            // send validation status
            mListener.onFragment3tInteraction(hasError, Avgsleep, Waterintake, Drink, Smoke, MedicalConditionID, errorMessage);
            // if no error then notify data to activity
//            if (!hasError) {
//                mListener.onFragment_3InteractionData(Avgsleep, Waterintake, Drink, Smoke, MedicalConditionID);
//            }
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
        boolean hasError = false;

        String errorMessage = "";
        if (!radioButtonNoDrink.isChecked() && !radioButton12.isChecked() && !radioButton34.isChecked())
        {
            hasError = true;
            errorMessage = "Select Your ";
        }

        if (!hasError)
        {
            if (!radioButtonSmokeNo.isChecked() && !radioButtonSmokeYes.isChecked())
            {
                hasError = true;
                errorMessage = "Select Your ";
            }
        }

        if (!hasError)
        {
            if (!(spinnerAverageSleepPerDay.getSelectedItem().toString().length() > 0))
            {
                hasError = true;
                errorMessage = "Select Your ";
            }
        }

        if (!hasError)
        {
            if (!(spinnerDailyWaterIntake.getSelectedItem().toString().length() > 0))
            {
                hasError = true;
                errorMessage = "Select Your ";
            }
        }

        if (!hasError)
        {
            if (medicalConditionList.isEmpty())
            {
                hasError = true;
                errorMessage = "Unable to load medical conditions";
                ShowRetryBar("Unable to load medical conditions");
            }
        }

        if (!hasError)
        {
            if (!(spinnerMedicalCondition.getSelectedItem().toString().length() > 0))
            {
                hasError = true;
                errorMessage = "Select Your ";
            }
        }

        buttonCheckChanged();

        onButtonPressed(hasError, errorMessage);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        buttonCheckChanged();
    }

    private void buttonCheckChanged()
    {
        if (radioButtonNoDrink.isChecked())
        {
            Drink = 1;
        }
        else if (radioButton12.isChecked())
        {
            Drink = 2;
        }
        else if (radioButton34.isChecked())
        {
            Drink = 3;
        }

        if (radioButtonSmokeNo.isChecked())
        {
            Smoke = 2;
        }
        else if (radioButtonSmokeYes.isChecked())
        {
            Smoke = 1;
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
    public interface OnHealth3InteractionListener
    {
        void onFragment3tInteraction(boolean hasError, int Avgsleep, int Waterintake, int Drink,
                                     int Smoke, int MedicalConditionID, String errorMessage);

//        void onFragment_3InteractionData(int Avgsleep, int Waterintake, int Drink, int Smoke, int MedicalConditionID);
    }
}
