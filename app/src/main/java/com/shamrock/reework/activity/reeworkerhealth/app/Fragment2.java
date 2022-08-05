package com.shamrock.reework.activity.reeworkerhealth.app;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;


import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;

import java.util.ArrayList;
import java.util.List;

import static com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity.healthParamData_static;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {


    private String strradioSmoke = "";

    public Fragment2() {
        // Required empty public constructor
    }

    private ArrayList<HealthParamData> dataArrayList;
    List<View> allViewInstance = new ArrayList<View>();
    List<View> cbViewInstance = new ArrayList<View>();
    List<View> allEdittextInstance = new ArrayList<>();
    List<View> allTextnstance = new ArrayList<>();
    List<View> allInputLAyouttInstance = new ArrayList<>();

    List<View> lst_spinner = new ArrayList<>();
    int spinnerid = 101;

    List<View> lst_radio = new ArrayList<>();
    int radioid = 102;
    private Spinner spinner_alcohol;
    private RadioButton rd_alcohol_yes;
    private RadioButton rd_alcohol_no;
    private RadioGroup rd_group_alcohol;

    private Spinner spinner_smoke;
    private RadioButton rd_smoke_yes;
    private RadioButton rd_smoke_no;
    private RadioGroup rd_group_smoke;
    private String strradioAlchohol = "";
    int editID = 5;
    int TextID = 3;

    int txtInput = 6;

    @SuppressLint("ValidFragment")
    public Fragment2(ArrayList<HealthParamData> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2, container, false);
        LinearLayout viewProductLayout = (LinearLayout) view.findViewById(R.id.customOptionLL);
        spinner_alcohol = view.findViewById(R.id.spinner_alcohol);
        rd_group_alcohol = view.findViewById(R.id.rd_group_alcohol);
        rd_alcohol_yes = view.findViewById(R.id.rd_alcohol_yes);
        rd_alcohol_no = view.findViewById(R.id.rd_alcohol_no);


        spinner_smoke = view.findViewById(R.id.spinner_smoke);
        rd_group_smoke = view.findViewById(R.id.rd_group_smoke);
        rd_smoke_yes = view.findViewById(R.id.rd_smoke_yes);
        rd_smoke_no = view.findViewById(R.id.rd_smoke_no);
        setSpinnderxml();
//
//        HealthParamData healthParamData=new HealthParamData();
//        healthParamData.setInputType("");
//        healthParamData.setQuestionId(100);
//        healthParamData.setQuestion("medial condition remark");
//        healthParamData.setInputType("INPUTBOX");


//        dataArrayList.add(healthParamData);


        rd_smoke_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strradioAlchohol = "Yes";
                spinner_smoke.setEnabled(true);

            }
        });
        rd_smoke_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_smoke.setEnabled(false);
                spinner_smoke.setSelection(0);
                strradioAlchohol = "No";

            }
        });


        RadioButton selectedRadioBtn1 = (RadioButton) rd_group_alcohol.findViewById(rd_group_alcohol.getCheckedRadioButtonId());
        rd_alcohol_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strradioSmoke = "Yes";

                spinner_alcohol.setEnabled(true);


            }
        });
        rd_alcohol_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_alcohol.setEnabled(false);
                spinner_alcohol.setSelection(0);
                strradioSmoke = "No";

            }
        });


        for (int noOfCustomOpt = 0; noOfCustomOpt < dataArrayList.size(); noOfCustomOpt++) {


            if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.DROPDOWN)) {


                TextView customOptionsName = new TextView(getActivity());
                customOptionsName.setTextSize(14);
                customOptionsName.setId(dataArrayList.get(noOfCustomOpt).getQuestionId() + TextID);
                allTextnstance.add(customOptionsName);
                customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                customOptionsName.setPadding(0, 50, 0, 5);
                customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 10, 0, 0);
                customOptionsName.setLayoutParams(layoutParams);
                viewProductLayout.addView(customOptionsName);
                String arrDropdown[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
                ArrayList<String> SpinnerOptions = new ArrayList<String>();
                for (int j = 0; j < arrDropdown.length; j++) {
                    SpinnerOptions.add(arrDropdown[j]);
                }
                SpinnerOptions.add(0, "Select");
                ArrayAdapter<String> spinnerArrayAdapter = null;
                spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_row, SpinnerOptions);
                Spinner spinner = new Spinner(getActivity());
                spinner.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                allViewInstance.add(spinner);
                spinner.setAdapter(spinnerArrayAdapter);
                spinner.setSelection(0, false);

                try {

                    if (dataArrayList.get(noOfCustomOpt).getQuestionId() == 9) {
                        for (int i = 0; i < dataArrayList.size(); i++) {
                            if (dataArrayList.get(i).getQuestionId() == 8) {
                                try {
                                    if (dataArrayList.get(i).getAnswer() == null) {
                                        dataArrayList.get(i).setAnswer("No");
                                    }
                                    if (dataArrayList.get(i).getAnswer().equalsIgnoreCase("No")) {

                                        for (int j = 0; j < allViewInstance.size(); j++) {

                                            if (allViewInstance.get(j).getId() == 9) {
                                                Spinner editText = (Spinner) allViewInstance.get(j);

                                                editText.setVisibility(View.GONE);
                                                editText.setSelection(0);

                                                TextView editText1 = (TextView) allTextnstance.get(i);


                                                for (int k = 0; k < allTextnstance.size(); k++) {
                                                    if (allTextnstance.get(k).getId() == (dataArrayList.get(j).getQuestionId() + TextID)) {
                                                        allTextnstance.get(k).setVisibility(View.GONE);
                                                        break;
                                                    }
                                                }


                                                break;
                                            }
                                        }


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }


                    if (dataArrayList.get(noOfCustomOpt).getQuestionId() == 11) {
                        try {
                            for (int i = 0; i < dataArrayList.size(); i++) {
                                if (dataArrayList.get(i).getQuestionId() == 10) {
                                    if (dataArrayList.get(i).getAnswer() == null) {
                                        dataArrayList.get(i).setAnswer("No");
                                    }
                                    if (dataArrayList.get(i).getAnswer().equalsIgnoreCase("No")) {


                                        for (int j = 0; j < allViewInstance.size(); j++) {

                                            if (allViewInstance.get(j).getId() == 11) {
                                                Spinner editText = (Spinner) allViewInstance.get(j);

                                                editText.setVisibility(View.GONE);
                                                editText.setSelection(0);

                                                for (int k = 0; k < allTextnstance.size(); k++) {
                                                    if (allTextnstance.get(k).getId() == (dataArrayList.get(j).getQuestionId() + TextID)) {
                                                        allTextnstance.get(k).setVisibility(View.GONE);
                                                        break;
                                                    }
                                                }


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

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {


                    if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {
                        for (int i = 0; i < SpinnerOptions.size(); i++) {
                            if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(SpinnerOptions.get(i).trim().trim())) {
                                spinner.setSelection(i);
                                break;
                            }

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }

                });
                viewProductLayout.addView(spinner);
                View v1 = new View(getActivity());
                v1.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1));
                v1.setBackgroundColor(Color.parseColor("#c0c0c0"));

                viewProductLayout.addView(v1);

                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")) {
                    TextInputLayout til = new TextInputLayout(getActivity());
                    til.setPadding(0, 50, 0, 0);
                    til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                    EditText et = new EditText(getActivity());
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId() + editID);
                    allEdittextInstance.add(et);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {
                        et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                    }
                    til.addView(et);
//
                    viewProductLayout.addView(til);
                }


            } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.RADIO_BUTTON)) {

                try {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 3;
                    params.bottomMargin = 3;
                    TextView customOptionsName = new TextView(getActivity());
                    customOptionsName.setTextSize(14);
                    customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                    customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                    customOptionsName.setPadding(0, 50, 0, 5);
                    customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                    viewProductLayout.addView(customOptionsName);
                    String arrRadioButton[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");


                    final RadioGroup rg = new RadioGroup(getActivity()); //create the RadioGroup
                    // rg.setOrientation(LinearLayout.HORIZONTAL);

                    rg.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                    allViewInstance.add(rg);
                    for (int j = 0; j < arrRadioButton.length; j++) {

                        RadioButton rb = new RadioButton(getActivity());
                        rg.addView(rb, params);
                        if (j == 0)
                            rb.setChecked(true);
                        rb.setLayoutParams(params);
                        rb.setTag(arrRadioButton[j]);
                        rb.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        String optionString = arrRadioButton[j];
                        rb.setText(optionString);
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @SuppressLint("ResourceType")
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                View radioButton = group.findViewById(checkedId);
                                String variant_name = radioButton.getTag().toString();
                                if (rg.getId() == 8) {
                                    if (variant_name.equalsIgnoreCase("Yes")) {

                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 9) {
                                                allViewInstance.get(i).setVisibility(View.VISIBLE);


                                                for (int k = 0; k < allTextnstance.size(); k++) {
                                                    if (allTextnstance.get(k).getId() == (dataArrayList.get(i).getQuestionId() + TextID)) {
                                                        allTextnstance.get(k).setVisibility(View.VISIBLE);
                                                        break;
                                                    }
                                                }

                                                break;

                                            }
                                        }

                                    } else {


                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 9) {
                                                allViewInstance.get(i).setVisibility(View.GONE);
                                                Spinner editText = (Spinner) allViewInstance.get(i);

                                                editText.setSelection(0);


                                                for (int k = 0; k < allTextnstance.size(); k++) {
                                                    if (allTextnstance.get(k).getId() == (dataArrayList.get(i).getQuestionId() + TextID)) {
                                                        allTextnstance.get(k).setVisibility(View.GONE);
                                                        break;
                                                    }
                                                }


                                                break;
                                            }
                                        }
                                    }


                                }

                                if (rg.getId() == 10) {
                                    if (variant_name.equalsIgnoreCase("Yes")) {

                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 11) {
                                                allViewInstance.get(i).setVisibility(View.VISIBLE);

                                                for (int k = 0; k < allTextnstance.size(); k++) {
                                                    if (allTextnstance.get(k).getId() == (dataArrayList.get(i).getQuestionId() + TextID)) {
                                                        allTextnstance.get(k).setVisibility(View.VISIBLE);
                                                        break;
                                                    }
                                                }


                                                break;
                                            }
                                        }


                                    } else {
                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 11) {
                                                allViewInstance.get(i).setVisibility(View.GONE);

                                                Spinner editText = (Spinner) allViewInstance.get(i);

                                                editText.setSelection(0);


                                                for (int k = 0; k < allTextnstance.size(); k++) {
                                                    if (allTextnstance.get(k).getId() == (dataArrayList.get(i).getQuestionId() + TextID)) {
                                                        allTextnstance.get(k).setVisibility(View.GONE);
                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }


                                }


                            }
                        });

                    }

                    if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {
                        for (int i = 0; i < arrRadioButton.length; i++) {
                            if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(arrRadioButton[i].trim().toString())) {
                                rg.check(rg.getChildAt(i).getId());


                            }

                        }
                    }
                    viewProductLayout.addView(rg, params);


                    if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")) {
                        TextInputLayout til = new TextInputLayout(getActivity());
                        til.setPadding(0, 50, 0, 0);
                        til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                        EditText et = new EditText(getActivity());
                        et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId() + editID);
                        allEdittextInstance.add(et);
                        if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {
                            et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                        }
                        til.addView(et);
                        viewProductLayout.addView(til);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.CHECK_BOX)) {
                final String arrCheckbox[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
                TextView customOptionsName = new TextView(getActivity());
                customOptionsName.setTextSize(14);
                customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                customOptionsName.setPadding(0, 10, 0, 15);
                customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                viewProductLayout.addView(customOptionsName);

                TextInputLayout til = null;
                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")) {
                    til = new TextInputLayout(getActivity());
                    til.setId(dataArrayList.get(noOfCustomOpt).getQuestionId() + txtInput);
                    allInputLAyouttInstance.add(til);
                    til.setPadding(0, 50, 0, 0);
                    til.setHint("Remarks");
                    EditText et = new EditText(getActivity());
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId() + editID);
                    allEdittextInstance.add(et);
                    if (dataArrayList.get(noOfCustomOpt).getRemark() != null && !dataArrayList.get(noOfCustomOpt).getRemark().isEmpty()) {
                        et.setText(dataArrayList.get(noOfCustomOpt).getRemark());
                    }
                    til.addView(et);

                }


                for (int j = 0; j < arrCheckbox.length; j++) {


                    final CheckBox chk = new CheckBox(getActivity());
                    chk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if (j == 0) {
                        allViewInstance.add(chk);
                        chk.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());

                    }
                    cbViewInstance.add(chk);
                    chk.setTag(arrCheckbox[j]);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    params.topMargin = 3;
                    params.bottomMargin = 3;
                    String optionString = arrCheckbox[j];
                    final int finalNoOfCustomOpt = noOfCustomOpt;
                    chk.setOnClickListener(new View.OnClickListener() {

                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(View v) {
                            if (chk.isChecked()) {

                                String variant_name = v.getTag().toString();


                                if (variant_name.toString().equalsIgnoreCase("Any Other. please specify in remarks")) {

                                    for (int i = 0; i < allEdittextInstance.size(); i++) {
                                        if (allEdittextInstance.get(i).getId() == (dataArrayList.get(finalNoOfCustomOpt).getQuestionId() + editID)) {
                                            allEdittextInstance.get(i).setVisibility(View.VISIBLE);
                                            break;
                                        }
                                    }

                                    for (int f = 0; f < allInputLAyouttInstance.size(); f++) {
                                        if (allInputLAyouttInstance.get(f).getId() == (dataArrayList.get(finalNoOfCustomOpt).getQuestionId() + txtInput)) {
                                            allInputLAyouttInstance.get(f).setVisibility(View.VISIBLE);
                                            break;
                                        }
                                    }


                                } else {

                                }


                            } else {

                                String variant_name = v.getTag().toString();
                                if (variant_name.toString().equalsIgnoreCase("Any Other. please specify in remarks")) {
                                    for (int i = 0; i < allEdittextInstance.size(); i++) {
                                        if (allEdittextInstance.get(i).getId() == (dataArrayList.get(finalNoOfCustomOpt).getQuestionId() + editID)) {
                                            allEdittextInstance.get(i).setVisibility(View.GONE);
                                            break;
                                        }
                                    }

                                    for (int f = 0; f < allInputLAyouttInstance.size(); f++) {
                                        if (allInputLAyouttInstance.get(f).getId() == (dataArrayList.get(finalNoOfCustomOpt).getQuestionId() + txtInput)) {
                                            allInputLAyouttInstance.get(f).setVisibility(View.GONE);
                                            break;
                                        }
                                    }

                                } else {

                                }

                            }

                        }
                    });
                    chk.setText(optionString);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty() && dataArrayList.get(noOfCustomOpt).getAnswer().contains("" +
                            "$")) {

                        String chkarr[] = dataArrayList.get(noOfCustomOpt).getAnswer().split("\\$");

                        for (int i = 0; i < chkarr.length; i++) {
                            if (arrCheckbox[j].toString().equalsIgnoreCase(chkarr[i].toString())) {
                                chk.setChecked(true);
                                if (chkarr[i].toString().equalsIgnoreCase("Any Other. please specify in remarks")) {

                                    for (int k = 0; k < allEdittextInstance.size(); k++) {
                                        if ((dataArrayList.get(noOfCustomOpt).getQuestionId() + editID) == (allEdittextInstance.get(k).getId())) {
                                            allEdittextInstance.get(k).setVisibility(View.VISIBLE);
                                            break;
                                        }


                                    }
                                    for (int f = 0; f < allInputLAyouttInstance.size(); f++) {
                                        if (allInputLAyouttInstance.get(f).getId() == (dataArrayList.get(noOfCustomOpt).getQuestionId() + txtInput)) {
                                            allInputLAyouttInstance.get(f).setVisibility(View.VISIBLE);
                                            break;
                                        }
                                    }

                                } else {
                                    for (int k = 0; k < allEdittextInstance.size(); k++) {
                                        if ((dataArrayList.get(noOfCustomOpt).getQuestionId() + editID) == (allEdittextInstance.get(k).getId())) {
                                            allEdittextInstance.get(k).setVisibility(View.GONE);
                                            break;
                                        }

                                    }

                                    for (int f = 0; f < allInputLAyouttInstance.size(); f++) {
                                        if (allInputLAyouttInstance.get(f).getId() == (dataArrayList.get(noOfCustomOpt).getQuestionId() + txtInput)) {
                                            allInputLAyouttInstance.get(f).setVisibility(View.GONE);
                                            break;
                                        }
                                    }
                                }
                            }


                        }


                    } else if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty() && !dataArrayList.get(noOfCustomOpt).getAnswer().contains(",")) {

                        if (dataArrayList.get(noOfCustomOpt).getAnswer().toString().equalsIgnoreCase(arrCheckbox[j].toString())) {
                            chk.setChecked(true);

                        }
                    }
                    viewProductLayout.addView(chk, params);
                }


                if (til != null) {
                    viewProductLayout.addView(til);

                }


            } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.INPUT_BOX)) {


                TextInputLayout til = new TextInputLayout(getActivity());
                til.setPadding(0, 50, 0, 0);
                til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                EditText et = new EditText(getActivity());
                et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());

                if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {

                    et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());

                }
                til.addView(et);
                allViewInstance.add(et);
                viewProductLayout.addView(til);
            }


        }


        return view;
    }

    private void setSpinnderxml() {

        ArrayAdapter<String> spinnerArrayAdapter = null;
        ArrayList<String> SpinnerOptions = new ArrayList<String>();
        SpinnerOptions.add("Select");
        SpinnerOptions.add("Once a week");
        SpinnerOptions.add("1-2 times a week");
        SpinnerOptions.add("3-4 times a week");
        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_row, SpinnerOptions);
        spinner_alcohol.setAdapter(spinnerArrayAdapter);


        ArrayAdapter<String> spinnerArrayAdapter1 = null;
        ArrayList<String> SpinnerOptions1 = new ArrayList<String>();
        SpinnerOptions1.add("Select");
        SpinnerOptions1.add("Once a week");
        SpinnerOptions1.add("1-2 times a week");
        SpinnerOptions1.add("3-4 times a week");
        spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spiner_row, SpinnerOptions);
        spinner_smoke.setAdapter(spinnerArrayAdapter1);


    }

    @SuppressLint("ResourceType")
    public boolean getSecondPageData() {


        boolean isTrue = true;


        RadioButton radioButton2;

        int selectedId2 = rd_group_smoke.getCheckedRadioButtonId();

        radioButton2 = (RadioButton) getActivity().findViewById(selectedId2);


        ArrayList<HealthParamData> healthParamData = new ArrayList<>();

        try {

            for (int i = 0; i < allViewInstance.size(); i++) {
                if (allViewInstance.get(i).getId() == 8) {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(i);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) getActivity().findViewById(selectedId);
                    if (radioButton.getText().toString().trim().equalsIgnoreCase("Yes")) {
                        for (int j = 0; j < allViewInstance.size(); j++) {
                            if (allViewInstance.get(j).getId() == 9) {
                                Spinner spinner9 = (Spinner) allViewInstance.get(j);

                                if (spinner9.getSelectedItem().toString().equalsIgnoreCase("Select")) {

                                    Toast.makeText(getActivity(), "Please select frequency of alcohol consumption", Toast.LENGTH_SHORT).show();

                                    return false;

                                }
                            }
                        }
                    }
                }


                if (allViewInstance.get(i).getId() == 10) {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(i);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) getActivity().findViewById(selectedId);
                    if (radioButton.getText().toString().trim().equalsIgnoreCase("Yes")) {
                        for (int j = 0; j < allViewInstance.size(); j++) {
                            if (allViewInstance.get(j).getId() == 11) {
                                Spinner spinner9 = (Spinner) allViewInstance.get(j);

                                if (spinner9.getSelectedItem().toString().equalsIgnoreCase("Select")) {

                                    Toast.makeText(getActivity(), "Please select smoking frequency", Toast.LENGTH_SHORT).show();

                                    return false;

                                }
                            }
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int noOfViews = 0; noOfViews < dataArrayList.size(); noOfViews++) {
            HealthParamData eachData = dataArrayList.get(noOfViews);


            if (eachData.getInputType().equals(DynamicHealthparamActivity.DROPDOWN)) {

                try {

                    if (eachData.getQuestionId() == 9) {
                        Spinner spinner = (Spinner) allViewInstance.get(noOfViews);

                        String dropDownJSONOpt[] = eachData.getItemList().split("\\$");
                        ArrayList<String> SpinnerOptions = new ArrayList<String>();
                        for (int j = 0; j < dropDownJSONOpt.length; j++) {
                            SpinnerOptions.add(dropDownJSONOpt[j]);
                        }
                        SpinnerOptions.add(0, "Select");

                        String variant_name = SpinnerOptions.get(spinner.getSelectedItemPosition());
                        Log.d("variant_name", variant_name + "");
                        if (spinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                            dataArrayList.get(noOfViews).setAnswer("Select");
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        } else {
                            dataArrayList.get(noOfViews).setAnswer(spinner.getSelectedItem().toString());
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));

                        }


                    } else if (eachData.getQuestionId() == 11) {


                        Spinner spinner = (Spinner) allViewInstance.get(noOfViews);

                        String dropDownJSONOpt[] = eachData.getItemList().split("\\$");
                        ArrayList<String> SpinnerOptions = new ArrayList<String>();
                        for (int j = 0; j < dropDownJSONOpt.length; j++) {
                            SpinnerOptions.add(dropDownJSONOpt[j]);
                        }
                        SpinnerOptions.add(0, "Select");

                        String variant_name = SpinnerOptions.get(spinner.getSelectedItemPosition());
                        Log.d("variant_name", variant_name + "");
                        if (spinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            dataArrayList.get(noOfViews).setAnswer("Select");
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        } else {
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            dataArrayList.get(noOfViews).setAnswer(spinner.getSelectedItem().toString());
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));

                        }


                    } else {

                        Spinner spinner = (Spinner) allViewInstance.get(noOfViews);
                        String dropDownJSONOpt[] = eachData.getItemList().split("\\$");
                        ArrayList<String> SpinnerOptions = new ArrayList<String>();
                        for (int j = 0; j < dropDownJSONOpt.length; j++) {
                            SpinnerOptions.add(dropDownJSONOpt[j]);
                        }
                        SpinnerOptions.add(0, "Select");

                        String variant_name = SpinnerOptions.get(spinner.getSelectedItemPosition());
                        Log.d("variant_name", variant_name + "");
                        if (variant_name.equalsIgnoreCase("Select")) {

                        } else {
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            dataArrayList.get(noOfViews).setAnswer(variant_name);
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.RADIO_BUTTON)) {
                try {


                    if (eachData.getQuestionId() == 8) {

                        RadioButton radioButton;
                        RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);

                        int selectedId = radioGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioButton = (RadioButton) getActivity().findViewById(selectedId);


                        if (radioButton != null) {
                            if (radioButton.getText().toString().length() > 0) {
                                dataArrayList.get(noOfViews).setItemList("");
                                dataArrayList.get(noOfViews).setQuestion("");
                                dataArrayList.get(noOfViews).setAnswer(radioButton.getText().toString());
                                healthParamData.add(dataArrayList.get(noOfViews));
                                healthParamData_static.add(dataArrayList.get(noOfViews));
                            }

                        }


                    } else if (eachData.getQuestionId() == 10) {

                        RadioButton radioButton;
                        RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);

                        int selectedId = radioGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioButton = (RadioButton) getActivity().findViewById(selectedId);


                        if (radioButton != null && radioButton.getText().toString().length() > 0) {
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            dataArrayList.get(noOfViews).setAnswer(radioButton.getText().toString());
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        }

                    } else {
                        RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);
                        RadioButton selectedRadioBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                        dataArrayList.get(noOfViews).setItemList("");
                        dataArrayList.get(noOfViews).setQuestion("");
                        dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.CHECK_BOX)) {
                try {


                    CheckBox tempChkBox = (CheckBox) allViewInstance.get(noOfViews);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < cbViewInstance.size(); i++) {
                        CheckBox tempChkBox1 = (CheckBox) cbViewInstance.get(i);
                        if (tempChkBox1.isChecked()) {
                            if (i == cbViewInstance.size()) {
                                stringBuilder.append(tempChkBox1.getText().toString());
                            } else {
                                stringBuilder.append(tempChkBox1.getText().toString() + "$");
                            }

                            if (!stringBuilder.toString().isEmpty()) {
                                String ds = "";
                                if (stringBuilder.toString().endsWith("$")) {
                                    ds = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
                                } else {
                                    ds = stringBuilder.toString();
                                }
                            } else {
                            }
                        } else {
                            if (stringBuilder.toString().isEmpty()) {
                            }

                        }
                    }
                    String dsdd = "";
                    if (stringBuilder.length() > 0) {
                        dsdd = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);

                    }


                    for (int i = 0; i < allEdittextInstance.size(); i++) {
                        if (allEdittextInstance.get(i).getId() == (eachData.getQuestionId() + editID)) {
                            EditText editText = (EditText) allEdittextInstance.get(i);
                            dataArrayList.get(noOfViews).setRemark(editText.getText().toString());
                        }
                    }
                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setAnswer(dsdd);
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));

                    if (tempChkBox.isChecked()) {
                        String arrCheckbox[] = dataArrayList.get(noOfViews).getItemList().split("\\$");
                        for (int j = 0; j < arrCheckbox.length; j++) {

                        }


                        Log.d("fsf", "sff");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.INPUT_BOX)) {
                try {
                    TextView textView = (TextView) allViewInstance.get(noOfViews);


                    if (!textView.getText().toString().equalsIgnoreCase("")) {
                        dataArrayList.get(noOfViews).setItemList("");
                        dataArrayList.get(noOfViews).setQuestion("");
                        dataArrayList.get(noOfViews).setAnswer(textView.getText().toString());
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));
                    } else {
                    }


                    Log.d("variant_name", textView.getText().toString() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return isTrue;
    }
}
//{"Code":1,"Message":"Success","Data":[{"QuestionId":4,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"O-,O+,B-,B+,A-,A+,AB-,AB+","Question":"Blood Group:"},{"QuestionId":5,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Vegetarian,Non-Vegetarian,Eggeterian,Jain","Question":"Food Habbits:"},{"QuestionId":6,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Sedentary,Mildly Active,Moderately Active,Highly Active","Question":"Activity status:"},{"QuestionId":7,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you consume Alcohol?\r\n"},{"QuestionId":8,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Once a week,1-2 times a week,3-4 times a week\r\n","Question":"Frequency of alcohol consumption:\r\n"},{"QuestionId":9,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you Smoke?"},{"QuestionId":10,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Once a week,1-2 times a week,3-4 times a week\r\n","Question":"Smoking frequency"},{"QuestionId":11,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you chew Tobacco or any other similar item?"},{"QuestionId":12,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you frequently experience stress?"},{"QuestionId":13,"InputType":"MULTISELECT","Answer":null,"AnswerId":0,"ItemList":"Diabetes Type II,High Blood Pressure,Low Blood Pressure,Acidity,PCOD,High Cholesterol,Water Retention,High Uric Acid,Heart Disease,Thyroid Hypo,Others","Question":"Any medical condition?"},{"QuestionId":14,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you have any food allergy?"},{"QuestionId":15,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you regularly take any health supplement?"},{"QuestionId":16,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Any Emotional Suffering?"},{"QuestionId":17,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Are you a Self Motivator?"},{"QuestionId":18,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you eat more, when stressed?"},{"QuestionId":19,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you have the habbit of \"Distracted Eating\" i.e eating while watching movie, listening to music etc.?"},{"QuestionId":20,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you have self control?"},{"QuestionId":21,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Any other Special Mention"},{"QuestionId":22,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"None","Question":"Future Question 1"},{"QuestionId":23,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"None","Question":"Future Question 2"},{"QuestionId":24,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"None","Question":"Future Question 3"},{"QuestionId":25,"InputType":"INPUTBOX","Answer":null,"AnswerId":0,"ItemList":"","Question":"Remarks"}]}
