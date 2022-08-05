package com.shamrock.reework.activity.HealthModule.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.response.MedicalCondition;
import com.shamrock.reework.api.response.MedicalConditionsResponse;
import com.shamrock.reework.util.Utils;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HealthParameters4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
HealthParameters4Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener,CompoundButton.OnCheckedChangeListener, ToolTipsManager.TipListener
{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_MY_HEALTH_DATA = "ARG_MY_HEALTH_DATA_4";
    private String mParam1;
    private String mParam2;
    RktArrayAdapter rktArrayAdapter;
    ListView lst_health_condition;
    boolean isFirstCall=false;

    private OnHealth4InteractionListener mListener;
    Context mContext;
    private String m_currentWeight;
    private boolean status;
    String medicalConditionIDS="";
    ArrayList<String> kaminey_dost_array_list;
    ArrayList<Boolean> arylst_isChdcked;
    // Data variables
    ArrayList<UserHealthResponse.Data> arylst_my_health_data;

    private HealthParametersService healthParametersService;
    private List<MedicalCondition> medicalConditionList;
    private Utils utils;



    public HealthParameters4Fragment()
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
     * @param arylst_my_health_data
     * @return A new instance of fragment HealthParameters1Fragment.
     */

    public static HealthParameters4Fragment newInstance(String param1, String param2, ArrayList<UserHealthResponse.Data> arylst_my_health_data)
    {
        HealthParameters4Fragment fragment = new HealthParameters4Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putSerializable(ARG_MY_HEALTH_DATA,arylst_my_health_data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnHealth4InteractionListener)
        {
            mListener = (OnHealth4InteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnHealth1InteractionListener");
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
            arylst_my_health_data= (ArrayList<UserHealthResponse.Data>) getArguments().getSerializable(ARG_MY_HEALTH_DATA);
            healthParametersService = Client.getClient().create(HealthParametersService.class);
            utils = new Utils();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_parameters4, container, false);
        lst_health_condition=view.findViewById(R.id.lst_health_condition);

        callHealthConditionsApi();






        return view;
    }



    public void onButtonPressed(boolean hasError, String msg)
    {
        if (mListener != null)
        {
            // send validation status
            mListener.onFragment4Interaction(hasError, msg);
            // if no error then notify data to activity
            if (!hasError)
            {



                ArrayList<String> arylst_medicalids=new ArrayList<>();




                String result = "";

                List<String> resultList = rktArrayAdapter.getCheckedItems();
                for (int i = 0; i < resultList.size(); i++) {
                    arylst_medicalids.add(resultList.get(i).toString());

                }

                rktArrayAdapter.getCheckedItemPositions().toString();

                if (arylst_medicalids.isEmpty()) {
                    Toast.makeText(
                            mContext,
                            "Please select any Medical Condition ",
                            Toast.LENGTH_LONG).show();
                } else {

                    StringBuilder medicalConditionIDSbuilder=new StringBuilder();
                    for (int i = 0; i <medicalConditionList.size(); i++) {


                        for (int j = 0; j <arylst_medicalids.size() ; j++) {

                            if (arylst_medicalids.get(j).equalsIgnoreCase(medicalConditionList.get(i).getMedicalCondition())){
                                medicalConditionIDSbuilder.append(medicalConditionList.get(i).getMedicalConditionID().toString()+",");

                            }
                        }

                    }

                    medicalConditionIDS=medicalConditionIDSbuilder.replace(medicalConditionIDSbuilder.length()-1, medicalConditionIDSbuilder.length(), "").toString();
                    mListener.onFragment_4InteractionData(medicalConditionIDS);


                }












            }
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View view)
    {

            }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



       // buttonCheckChanged();
    }





    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {

    }
    boolean isNoneChecked =false;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

       CheckedTextView checkedTextView = (CheckedTextView) view.findViewWithTag(i);

//                     if(i==0){
//                         if(checkedTextView.isChecked()) {
//                             isNoneChecked = true;
//                         }else{
//                             isNoneChecked=false;
//                         }
//                       }



                       rktArrayAdapter.rkt_toggleChecked(i,checkedTextView.isChecked());






    }
    public void validateData()
    {
        boolean hasError = false;

        String errorMessage = "";
//        if (medicalConditionIDS.isEmpty())
//        {
//            hasError = true;
//            errorMessage = "Select Medicale Condition ";
//        }





        onButtonPressed(hasError, errorMessage);
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
                  //  medicalConditionsAdapter = new MedicalConditionsAdapter(mContext, medicalConditionList);
                   // spinnerMedicalCondition.setAdapter(medicalConditionsAdapter);



                    kaminey_dost_array_list  = new ArrayList<String>();
                    arylst_isChdcked=new ArrayList<>();

                    for (int i = 0; i <medicalConditionList.size() ; i++) {
                        kaminey_dost_array_list.add(medicalConditionList.get(i).getMedicalCondition());

                    }



                    lst_health_condition.setOnItemClickListener(HealthParameters4Fragment.this);
                    if (arylst_my_health_data!=null&&!arylst_my_health_data.isEmpty() &&arylst_my_health_data.get(0).getMedicalConditionID()!=null&&!arylst_my_health_data.get(0).getMedicalConditionID().isEmpty()){

                        if (!arylst_my_health_data.isEmpty()){
                            if (arylst_my_health_data.get(0).getMedicalConditionID()!=null){
                                medicalConditionIDS=arylst_my_health_data.get(0).getMedicalConditionID().toString();
                                  List<String> list = new ArrayList<String>(Arrays.asList(arylst_my_health_data.get(0).getMedicalConditionID().split(",")));

                                for (int i = 0; i <kaminey_dost_array_list.size() ; i++) {

                                    boolean found=false;
                                    for (int j = 0; j <list.size() ; j++) {
                                        if (list.get(j).toString().trim().equalsIgnoreCase(String.valueOf(medicalConditionList.get(i).getMedicalConditionID().toString().trim()))){
                                            found=true;
                                            break;
                                        }






                                    }

                                    arylst_isChdcked.add(found);


                                }



                                   rktArrayAdapter = new RktArrayAdapter(mContext,R.layout.list_row,android.R.id.text1,
                                           kaminey_dost_array_list,arylst_isChdcked
                                   );
                                lst_health_condition.setAdapter(rktArrayAdapter);

                                lst_health_condition.setOnItemClickListener(HealthParameters4Fragment.this);


                            }
                        }
                    }else {


                        for (int i = 0; i <kaminey_dost_array_list.size() ; i++) {
                                 arylst_isChdcked.add(false);
                        }


                                    rktArrayAdapter = new RktArrayAdapter(mContext,R.layout.list_row,android.R.id.text1,
                                            kaminey_dost_array_list,arylst_isChdcked   );




                        lst_health_condition.setAdapter(rktArrayAdapter);

                        lst_health_condition.setOnItemClickListener(HealthParameters4Fragment.this);
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
    public interface OnHealth4InteractionListener
    {
        void onFragment4Interaction(boolean uri, String msg);

        void onFragment_4InteractionData(String medicalcondtionIDS);
    }

    public class RktArrayAdapter extends ArrayAdapter<String> {

        private HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();
//                                     ArrayList<Boolean> arylst_isChdcked ;
        public RktArrayAdapter(Context context, int resource,
                               int textViewResourceId, List<String> objects, ArrayList<Boolean> arylst_isChdcked) {
            super(context, resource, textViewResourceId, objects);

            for (int i = 0; i < arylst_isChdcked.size(); i++) {
                myChecked.put(i, arylst_isChdcked.get(i));
            }
        }

        public void rkt_toggleChecked(int position, boolean checked) {

            if (myChecked.get(position)) {

                myChecked.put(position, false);
            } else {
                myChecked.put(position, true);
            }
//            if(position==0&&!checked) {
//                for (int j = 0; j < arylst_isChdcked.size(); j++) {
//                    if (j != 0) {
//                        myChecked.put(j,false);
//                    }
//                }
//            }
                if(myChecked.get(0)){
                    for (int j = 0; j < arylst_isChdcked.size(); j++) {
                        if (j != 0) {
                            myChecked.put(j,false);
                        }
                    }
                }



            notifyDataSetChanged();
        }

        public List<Integer> getCheckedItemPositions() {
            List<Integer> checkedItemPositions = new ArrayList<Integer>();

            for (int i = 0; i < myChecked.size(); i++) {
                if (myChecked.get(i)) {
                    (checkedItemPositions).add(i);
                }
            }

            return checkedItemPositions;
        }

        public List<String> getCheckedItems() {
            List<String> checkedItems = new ArrayList<String>();

            for (int i = 0; i < myChecked.size(); i++) {
                if (myChecked.get(i)) {
                    (checkedItems).add(kaminey_dost_array_list.get(i));
                }
            }

            return checkedItems;
        }

        @Override
        public boolean isEnabled(int position) {
            return super.isEnabled(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.list_row, parent, false);
            }

            CheckedTextView checked_TextView = (CheckedTextView) row.findViewById(R.id.checked_textview);

            checked_TextView.setText(kaminey_dost_array_list.get(position));

            Boolean checked = myChecked.get(position);
            if (checked != null) {
                checked_TextView.setChecked(checked);
            }


            checked_TextView.setTag(position);
                return row;
            }

        }

}


