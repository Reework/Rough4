package com.shamrock.reework.activity.HealthModule.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.dialog.HealthParameterDialogFragment;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.util.Utils;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnHealth1InteractionListener} interface
 * to handle interaction events.
 * Use the {@link HealthParameters1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HealthParameters1Fragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ToolTipsManager.TipListener
{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_MY_HEALTH_DATA = "ARG_MY_HEALTH_DATA";
    private String mParam1;
    private String mParam2;
    private RecyclerView recyler_1;

    private OnHealth1InteractionListener mListener;
    Context mContext;
    private String m_currentWeight;
    private boolean status;

    TextView textHP_Why;
    Switch switch1, switch2, switch3, switch4;
    RadioButton radioButton_Pear, radioButton_Apple;
    EditText editText_Weight, editText_Height;
    // Data variables
    int Health_And_Wellness, Weight_Control, weight_Gain, Easy_Monitoring_of_Body_Parameters, body_shape, Height;
    double Weight;
    ArrayList<UserHealthResponse.Data> arylst_my_health_data;
    private LinearLayout ll_body_shape_tooltip;
    RadioButton rd_1;
    RadioButton rd_2;
    RadioButton rd_3;
    RadioButton rd_4;


    ImageView iv_easy_monitoring;
    ImageView iv_weight_gain;
    ImageView iv_weight_control;
    ImageView iv_health_fitness;
    public HealthParameters1Fragment()
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

    public static HealthParameters1Fragment newInstance(String param1, String param2, ArrayList<UserHealthResponse.Data> arylst_my_health_data)
    {
        HealthParameters1Fragment fragment = new HealthParameters1Fragment();
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
        if (context instanceof OnHealth1InteractionListener)
        {
            mListener = (OnHealth1InteractionListener) context;
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_parameters1, container, false);
        textHP_Why = view.findViewById(R.id.textHP_WhyHealthAndWealth);
        switch1 = view.findViewById(R.id.switchHP_1);
        switch2 = view.findViewById(R.id.switchHP_2);
        switch3 = view.findViewById(R.id.switchHP_3);
        switch4 = view.findViewById(R.id.switchHP_4);
        rd_1 = view.findViewById(R.id.rd_1);
        rd_2 = view.findViewById(R.id.rd_2);
        rd_3 = view.findViewById(R.id.rd_3);
        rd_4 = view.findViewById(R.id.rd_4);
        recyler_1=view.findViewById(R.id.recyler_1);

        iv_health_fitness = view.findViewById(R.id.iv_health_fitness);
        iv_weight_control = view.findViewById(R.id.iv_weight_control);
        iv_easy_monitoring = view.findViewById(R.id.iv_easy_monitoring);
        iv_weight_gain = view.findViewById(R.id.iv_weight_gain);





        iv_easy_monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SimpleTooltip.Builder(getContext())
                        .anchorView(iv_easy_monitoring)
                        .textColor(mContext.getResources().getColor(R.color.black_overlay_dark))
//                        .text("Apple Shape:If you have an apple-shaped body, then your body is \"top heavy,\" meaning you have a wide torso, broad shoulders, and a full bust, waist, and upper back.\n" +
//                                "\n" +
//                                "Pear Shape: A pear body shape is one where most of your weight is situated around your thighs, hips and buttocks and you have slim arms and shoulders and a bust that is smaller than your hips")
                        .gravity(Gravity.TOP)
                        .contentView(R.layout.lay_text,R.id.txt)
                        .text(Html.fromHtml(getResources().getString(R.string.bodyshape)))

                        .backgroundColor(getActivity().getResources().getColor(R.color.white))
                        .animated(true)
                        .arrowColor(getActivity().getResources().getColor(R.color.white))
                        .transparentOverlay(false)
                        .build()
                        .show();

            }
        });

        rd_1.setOnCheckedChangeListener(this);
        rd_2.setOnCheckedChangeListener(this);
        rd_3.setOnCheckedChangeListener(this);
        rd_4.setOnCheckedChangeListener(this);

        ll_body_shape_tooltip = view.findViewById(R.id.ll_body_shape_tooltip);
        radioButton_Pear = view.findViewById(R.id.radioButtonPear);
        radioButton_Apple = view.findViewById(R.id.radioButtonApple);
        editText_Weight = view.findViewById(R.id.edtTextHP_Weight);
        editText_Height = view.findViewById(R.id.edtTextHP_Height);

        SpannableString content = new SpannableString(getString(R.string.why_health_amp_wellness));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textHP_Why.setText(content);

        textHP_Why.setOnClickListener(this);
        ll_body_shape_tooltip.setOnClickListener(this);
        switch1.setOnCheckedChangeListener(this);
        switch2.setOnCheckedChangeListener(this);
        switch3.setOnCheckedChangeListener(this);
        switch4.setOnCheckedChangeListener(this);
        radioButton_Pear.setOnCheckedChangeListener(this);
        radioButton_Apple.setOnCheckedChangeListener(this);


        bindHealthData();

        return view;
    }

    private void bindHealthData() {


        try {
            if (arylst_my_health_data.get(0).getHealth_And_Wellness()!=null){
                if (arylst_my_health_data.get(0).getHealth_And_Wellness().equalsIgnoreCase("1")){
                    rd_1.setChecked(true);
                }else {
                    rd_1.setChecked(false);

                }
            }


            if (arylst_my_health_data.get(0).getWeight_Control()!=null){
                if (arylst_my_health_data.get(0).getWeight_Control().equalsIgnoreCase("1")){
                    rd_2.setChecked(true);
                }else {
                    rd_2.setChecked(false);

                }
            }



            if (arylst_my_health_data.get(0).getWeight_Gain()!=null){
                if (arylst_my_health_data.get(0).getWeight_Gain().equalsIgnoreCase("1")){
                    rd_3.setChecked(true);
                }else {
                    rd_3.setChecked(false);

                }

            }


            if (arylst_my_health_data.get(0).getEasy_Monitoring_of_Body_Parameters() != null) {
                if (arylst_my_health_data.get(0).getEasy_Monitoring_of_Body_Parameters().equalsIgnoreCase("1")){
                    rd_4.setChecked(true);
                }else {
                    rd_4.setChecked(false);

                }

            }




            if (arylst_my_health_data.get(0).getBody_shape()!=null){
                if (arylst_my_health_data.get(0).getBody_shape().equalsIgnoreCase("1")){
                    radioButton_Pear.setChecked(true);
                    radioButton_Apple.setChecked(false);

                }else {
                    radioButton_Pear.setChecked(true);
                    radioButton_Apple.setChecked(true);

                }

            }



            try {
                if (arylst_my_health_data.get(0).getWeight()!=null){
                    double wt=Double.parseDouble(arylst_my_health_data.get(0).getWeight());
                    String newwt=String.valueOf(Math.round(wt));

                    editText_Weight.setText(newwt);

                }
            } catch (NumberFormatException e) {
                editText_Weight.setText(arylst_my_health_data.get(0).getWeight());
                e.printStackTrace();

            }


            if (arylst_my_health_data.get(0).getHeight()!=null){
                editText_Height.setText(arylst_my_health_data.get(0).getHeight());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void onButtonPressed(boolean hasError, String msg)
    {
        if (mListener != null)
        {
            // send validation status
            mListener.onFragment1Interaction(hasError, msg);
            // if no error then notify data to activity
            if (!hasError)
            {
                mListener.onFragment_1InteractionData(Health_And_Wellness, Weight_Control, weight_Gain,
                        Easy_Monitoring_of_Body_Parameters, body_shape, Height, Weight);
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
        boolean hasError = false;
        String msg = "";

        if (!rd_1.isChecked() && !rd_2.isChecked() && !rd_3.isChecked() && !rd_4.isChecked())
        {
            hasError = true;
            msg = "Please select your Health Goal";
        }

        if (!hasError)
        {
            if (editText_Weight.getText().toString().trim().length() == 0)
            {
                hasError = true;
                msg = "Please enter weight";
            }
            else if (!Utils.isValidWeight(editText_Weight.getText().toString().trim()))
            {
                hasError = true;
                msg = "Enter valid weight";
            }
            else
            {
                Weight = Double.parseDouble(editText_Weight.getText().toString().trim());
                if (Double.compare(Weight, 0) == 0)
                {
                    hasError = true;
                    msg = "Please enter valid weight";
                }
            }
        }

        if (!hasError)
        {
            if (editText_Height.getText().toString().trim().length() == 0)
            {
                hasError = true;
                msg = "Please enter height";
            }
            else
            {
                Weight = Double.parseDouble(editText_Weight.getText().toString().trim());
                Height = Integer.parseInt(editText_Height.getText().toString().trim());
//                if (Height == 0) {
//                    hasError = true;
//                    msg = "Please enter valid height";
//                }
                if (Height < Weight)
                {
                    hasError = true;
                    msg = "Enter valid height";
                }
            }
        }
       // buttonCheckChanged();

        onButtonPressed(hasError, msg);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.textHP_WhyHealthAndWealth:
                FragmentManager fm = getFragmentManager();
                HealthParameterDialogFragment dialogFragment = new HealthParameterDialogFragment();
                dialogFragment.show(fm, "SBT_fragment");
                break;


            case R.id.ll_body_shape_tooltip:

//                ToolTipsManager mToolTipsManager;
                new SimpleTooltip.Builder(getContext())
                        .anchorView(ll_body_shape_tooltip)
                        .textColor(mContext.getResources().getColor(R.color.black_overlay_dark))
//                        .text("Apple Shape:If you have an apple-shaped body, then your body is \"top heavy,\" meaning you have a wide torso, broad shoulders, and a full bust, waist, and upper back.\n" +
//                                "\n" +
//                                "Pear Shape: A pear body shape is one where most of your weight is situated around your thighs, hips and buttocks and you have slim arms and shoulders and a bust that is smaller than your hips")
                        .gravity(Gravity.TOP)
                        .contentView(R.layout.lay_text,R.id.txt)
                        .text(Html.fromHtml(getResources().getString(R.string.bodyshape)))

                        .backgroundColor(getActivity().getResources().getColor(R.color.white))
                        .animated(true)
                        .arrowColor(getActivity().getResources().getColor(R.color.white))
                        .transparentOverlay(false)
                        .build()
                        .show();


                break;

            default:
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



       // buttonCheckChanged();
        buttonCheckChangedsecond(compoundButton);
    }

    private void buttonCheckChanged()
    {
        if (rd_1.isChecked())
        {

            rd_2.setChecked(false);
            rd_3.setChecked(false);
            rd_4.setChecked(false);

//            switch2.setChecked(false);
//            switch3.setChecked(false);
//            switch4.setChecked(false);

            Health_And_Wellness = 1;
        }

        else {
            Health_And_Wellness = 0;
        }

        if (rd_2.isChecked())
        {

            rd_1.setChecked(false);
            rd_3.setChecked(false);
            rd_4.setChecked(false);

//            switch1.setChecked(false);
//            switch3.setChecked(false);
//            switch4.setChecked(false);

            Weight_Control = 1;
        }
        else {
            Weight_Control = 0;
        }

        if (rd_3.isChecked())
        {
            rd_1.setChecked(false);
            rd_2.setChecked(false);
            rd_4.setChecked(false);
            weight_Gain = 1;
        }

        else {
            weight_Gain = 0;
        }

        if (rd_4.isChecked())
        {

            rd_1.setChecked(false);
            rd_2.setChecked(false);
            rd_3.setChecked(false);
            Easy_Monitoring_of_Body_Parameters = 1;
        }

        else {
            Easy_Monitoring_of_Body_Parameters = 0;
        }

        if (radioButton_Pear.isChecked())
        {
            body_shape = 1;
        }

        if (radioButton_Apple.isChecked())
        {
            body_shape = 2;
        }
    }

    private void buttonCheckChangedsecond(CompoundButton compoundButton)
    {
        if (compoundButton==rd_1)
        {

            if (rd_1.isChecked()){
                rd_2.setChecked(false);
                rd_3.setChecked(false);
                rd_4.setChecked(false);
                Health_And_Wellness = 1;

            }else {
                Health_And_Wellness = 0;

            }

        }



        if (compoundButton==rd_2)
        {

            if (rd_2.isChecked()){
                rd_1.setChecked(false);
                rd_3.setChecked(false);
                rd_4.setChecked(false);
                Weight_Control = 1;

            }else {
                Weight_Control = 0;

            }



        }

        if (compoundButton==rd_3)
        {

            if (rd_3.isChecked()){
                rd_1.setChecked(false);
                rd_2.setChecked(false);
                rd_4.setChecked(false);
                weight_Gain = 1;

            }else {
                weight_Gain = 0;

            }


        }



        if (compoundButton==rd_4)
        {
            if (rd_4.isChecked()){
                rd_1.setChecked(false);
                rd_2.setChecked(false);
                rd_3.setChecked(false);
                Easy_Monitoring_of_Body_Parameters = 1;

            }else {
                Easy_Monitoring_of_Body_Parameters = 0;

            }


        }


        if (radioButton_Pear.isChecked())
        {
            body_shape = 1;
        }

        if (radioButton_Apple.isChecked())
        {
            body_shape = 2;
        }
    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {

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
    public interface OnHealth1InteractionListener
    {
        void onFragment1Interaction(boolean uri, String msg);

        void onFragment_1InteractionData(int Health_And_Wellness, int Weight_Control,
                                         int weight_Gain, int Easy_Monitoring_of_Body_Parameters,
                                         int body_shape, int Height, double Weight);
    }
}
