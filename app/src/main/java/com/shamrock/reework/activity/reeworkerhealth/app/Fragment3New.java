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

import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;

import java.util.ArrayList;
import java.util.List;

import static com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity.healthParamData_static;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3New extends Fragment {


    private String strradioSmoke="";

    public Fragment3New() {
        // Required empty public constructor
    }

    private ArrayList<HealthParamData> dataArrayList;
    List<View> allViewInstance = new ArrayList<View>();
    List<View> cbViewInstance = new ArrayList<View>();
    List<View> allEdittextInstance=new ArrayList<>();
    private Spinner spinner_alcohol;
    private RadioButton rd_alcohol_yes;
    private RadioButton rd_alcohol_no;
    private RadioGroup rd_group_alcohol;

    private Spinner spinner_smoke;
    private RadioButton rd_smoke_yes;
    private RadioButton rd_smoke_no;
    private RadioGroup rd_group_smoke;
    private String strradioAlchohol="";

    @SuppressLint("ValidFragment")
    public Fragment3New(ArrayList<HealthParamData> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2, container, false);
        LinearLayout viewProductLayout = (LinearLayout) view.findViewById(R.id.customOptionLL);
        spinner_alcohol=view.findViewById(R.id.spinner_alcohol);
        rd_group_alcohol=view.findViewById(R.id.rd_group_alcohol);
        rd_alcohol_yes=view.findViewById(R.id.rd_alcohol_yes);
        rd_alcohol_no=view.findViewById(R.id.rd_alcohol_no);


        spinner_smoke=view.findViewById(R.id.spinner_smoke);
        rd_group_smoke=view.findViewById(R.id.rd_group_smoke);
        rd_smoke_yes=view.findViewById(R.id.rd_smoke_yes);
        rd_smoke_no=view.findViewById(R.id.rd_smoke_no);
        setSpinnderxml();
//
//        HealthParamData healthParamData=new HealthParamData();6
//        healthParamData.setInputType("");
//        healthParamData.setQuestionId(100);
//        healthParamData.setQuestion("medial condition remark");
//        healthParamData.setInputType("INPUTBOX");6


//        dataArrayList.add(healthParamData);


        rd_smoke_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strradioAlchohol="Yes";
                spinner_smoke.setEnabled(true);

            }
        });
        rd_smoke_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_smoke.setEnabled(false);
                spinner_smoke.setSelection(0);
                strradioAlchohol="No";

            }
        });


        RadioButton selectedRadioBtn1 = (RadioButton)rd_group_alcohol.findViewById(rd_group_alcohol.getCheckedRadioButtonId());
        rd_alcohol_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strradioSmoke="Yes";

                spinner_alcohol.setEnabled(true);


            }
        });
        rd_alcohol_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_alcohol.setEnabled(false);
                spinner_alcohol.setSelection(0);
                strradioSmoke="No";

            }
        });





        for (int noOfCustomOpt = 0; noOfCustomOpt < dataArrayList.size(); noOfCustomOpt++) {


            if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.DROPDOWN)) {
                TextView customOptionsName = new TextView(getActivity());
                customOptionsName.setTextSize(14);
                customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                customOptionsName.setPadding(0, 50, 0, 5);
                customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,10,0,0);
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

                if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                    for (int i = 0; i < SpinnerOptions.size(); i++) {
                        if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(SpinnerOptions.get(i).trim().trim())){
                            spinner.setSelection(i);
                            break;
                        }

                    }
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

                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                    TextInputLayout til = new TextInputLayout(getActivity());
                    til.setPadding(0,50,0,0);
                    til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                    EditText et = new EditText(getActivity());
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                    allEdittextInstance.add(et);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                        et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                    }
                    til.addView(et);
//
                    viewProductLayout.addView(til);
                }

//                if (!(dataArrayList.get(noOfCustomOpt).getQuestionId()==9||dataArrayList.get(noOfCustomOpt).getQuestionId()==11)){
//
//                }else {
//                    if (dataArrayList.get(noOfCustomOpt).getQuestionId()==9){
//
//
//                        String arrDropdown[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
//                        ArrayList<String> SpinnerOptions = new ArrayList<String>();
//                        for (int j = 0; j < arrDropdown.length; j++) {
//                            SpinnerOptions.add(arrDropdown[j]);
//                        }
//                        SpinnerOptions.add(0, "Select");
//
//                        if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
//                            for (int i = 0; i < SpinnerOptions.size(); i++) {
//                                if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(SpinnerOptions.get(i).toString())){
//                                    spinner_alcohol.setSelection(i);
//                                    break;
//                                }
//
//                            }
//                        }
//
//
//                    }else  if (dataArrayList.get(noOfCustomOpt).getQuestionId()==11) {
//
//
//                        String arrDropdown[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
//                        ArrayList<String> SpinnerOptions = new ArrayList<String>();
//                        for (int j = 0; j < arrDropdown.length; j++) {
//                            SpinnerOptions.add(arrDropdown[j]);
//                        }
//                        SpinnerOptions.add(0, "Select");
//                        if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {
//                            for (int i = 0; i < SpinnerOptions.size(); i++) {
//                                if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(SpinnerOptions.get(i).toString())) {
//                                    spinner_smoke.setSelection(i);
//                                    break;
//                                }
//
//                            }
//                        }
//                    }
//
//
//
//
//
//                }





            } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.RADIO_BUTTON)) {


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
                            if (rg.getId()==8){
                                if (variant_name.equalsIgnoreCase("Yes")){

                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==9){
                                            allViewInstance.get(i).setEnabled(true);                                        }
                                    }

                                }else {
                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==9){
                                            allViewInstance.get(i).setEnabled(false);
                                        }
                                    }
                                }



                            }

                            if (rg.getId()==10){
                                if (variant_name.equalsIgnoreCase("Yes")){

                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==11){
                                            allViewInstance.get(i).setEnabled(true);                                        }
                                    }

                                }else {
                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==11){
                                            allViewInstance.get(i).setEnabled(false);
                                        }
                                    }
                                }



                            }





                            // Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                        }
                    });

                }

                if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                    for (int i = 0; i < arrRadioButton.length; i++) {
                        if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(arrRadioButton[i].trim().toString())){
                            rg.check(rg.getChildAt(i).getId());
                            if (allViewInstance.get(i).getId()==8){
                                if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("Yes")){
                                    for (int j = 0; j <allViewInstance.size() ; j++) {
                                        if (allViewInstance.get(j).getId()==9){

                                            allViewInstance.get(j).setEnabled(true);
                                        }else {
                                            allViewInstance.get(j).setEnabled(false);


                                        }

                                    }
                                }
                            }
                            if (allViewInstance.get(i).getId()==10){
                                if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("Yes")){
                                    for (int j = 0; j <allViewInstance.size() ; j++) {
                                        if (allViewInstance.get(j).getId()==11){

                                            allViewInstance.get(j).setEnabled(true);
                                        }else {
                                            allViewInstance.get(j).setEnabled(false);


                                        }

                                    }
                                }
                            }

                        }

                    }
                }
                viewProductLayout.addView(rg, params);




                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                    TextInputLayout til = new TextInputLayout(getActivity());
                    til.setPadding(0,50,0,0);
                    til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                    EditText et = new EditText(getActivity());
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                    allEdittextInstance.add(et);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                        et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                    }
                    til.addView(et);
                    viewProductLayout.addView(til);
                }


            } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.CHECK_BOX)) {
                String arrCheckbox[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
                TextView customOptionsName = new TextView(getActivity());
                customOptionsName.setTextSize(14);
                customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                customOptionsName.setPadding(0, 10, 0, 15);
                customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                viewProductLayout.addView(customOptionsName);


                for (int j = 0; j < arrCheckbox.length; j++) {


                    final CheckBox chk = new CheckBox(getActivity());
                    chk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if(j==0) {
                        allViewInstance.add(chk);
                    }
                    cbViewInstance.add(chk);
                    chk.setTag(arrCheckbox[j]);
                    chk.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    params.topMargin = 3;
                    params.bottomMargin = 3;
                    String optionString = arrCheckbox[j];
                    chk.setOnClickListener(new View.OnClickListener() {

                        @SuppressLint("ResourceType")
                        @Override
                        public void onClick(View v) {
                            if(chk.isChecked()){

                                String variant_name = v.getTag().toString();
                                if (variant_name.toString().equalsIgnoreCase("Others")){

                                    for (int i = 0; i <allViewInstance.size() ; i++) {
                                        if (allViewInstance.get(i).getId()==100){
                                            allViewInstance.get(i).setVisibility(View.VISIBLE);
                                            break;
                                        }
                                    }
                                }else {

                                }





                            }else{

                                String variant_name = v.getTag().toString();
                                if (variant_name.toString().equalsIgnoreCase("Others")){
                                    for (int i = 0; i <allViewInstance.size() ; i++) {
                                        if (allViewInstance.get(i).getId()==100){
                                            allViewInstance.get(i).setVisibility(View.GONE);
                                            break;
                                        }
                                    }
                                }else {

                                }

                            }

                        }
                    });
                    chk.setText(optionString);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()&&dataArrayList.get(noOfCustomOpt).getAnswer().contains(",")){

                        String chkarr[]=dataArrayList.get(noOfCustomOpt).getAnswer().split(",");

                        for (int i = 0; i <chkarr.length ; i++) {
                            if (arrCheckbox[j].toString().equalsIgnoreCase(chkarr[i].toString())){
                                chk.setChecked(true);
                            }


                        }


                    }else if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()&&!dataArrayList.get(noOfCustomOpt).getAnswer().contains(","))
                    {

                        if (dataArrayList.get(noOfCustomOpt).getAnswer().toString().equalsIgnoreCase(arrCheckbox[j].toString())){
                            chk.setChecked(true);
                        }
                    }
                    viewProductLayout.addView(chk, params);
                }






                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                    TextInputLayout til = new TextInputLayout(getActivity());
                    til.setPadding(0,50,0,0);
                    til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                    EditText et = new EditText(getActivity());
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                    allEdittextInstance.add(et);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                        et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                    }
                    til.addView(et);
                    viewProductLayout.addView(til);
                }









            } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.INPUT_BOX)) {



                TextInputLayout til = new TextInputLayout(getActivity());
                til.setPadding(0,50,0,0);
                til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                EditText et = new EditText(getActivity());
                et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());

                if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){

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

    public boolean getThirdPageData() {


        boolean isTrue=true;

        RadioButton radioButton2;

        int selectedId2 = rd_group_smoke.getCheckedRadioButtonId();

        radioButton2 = (RadioButton) getActivity().findViewById(selectedId2);




        ArrayList<HealthParamData> healthParamData = new ArrayList<>();
        for (int noOfViews = 0; noOfViews < dataArrayList.size(); noOfViews++) {
            HealthParamData eachData = dataArrayList.get(noOfViews);

            if (eachData.getInputType().equals(DynamicHealthparamActivity.DROPDOWN)) {

                if (eachData.getQuestionId()==9){

                    String dropDownJSONOpt[] = eachData.getItemList().split("\\$");
                    ArrayList<String> SpinnerOptions = new ArrayList<String>();
                    for (int j = 0; j < dropDownJSONOpt.length; j++) {
                        SpinnerOptions.add(dropDownJSONOpt[j]);
                    }
                    SpinnerOptions.add(0, "Select");

                    String variant_name = SpinnerOptions.get(spinner_alcohol.getSelectedItemPosition());
                    Log.d("variant_name", variant_name + "");
                    if (spinner_smoke.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                        dataArrayList.get(noOfViews).setAnswer("Select");
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));
                    } else {
                        dataArrayList.get(noOfViews).setAnswer(spinner_alcohol.getSelectedItem().toString());
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));

                    }


                }else if (eachData.getQuestionId()==11){

//                    Spinner spinner = (Spinner) allViewInstance.get(noOfViews);
                    String dropDownJSONOpt[] = eachData.getItemList().split("\\$");
                    ArrayList<String> SpinnerOptions = new ArrayList<String>();
                    for (int j = 0; j < dropDownJSONOpt.length; j++) {
                        SpinnerOptions.add(dropDownJSONOpt[j]);
                    }
                    SpinnerOptions.add(0, "Select");

                    String variant_name = SpinnerOptions.get(spinner_smoke.getSelectedItemPosition());
                    Log.d("variant_name", variant_name + "");
                    if (spinner_smoke.getSelectedItem().toString().equalsIgnoreCase("Select")) {

                        dataArrayList.get(noOfViews).setAnswer("Select");
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));
                    } else {
                        dataArrayList.get(noOfViews).setAnswer(spinner_smoke.getSelectedItem().toString());
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));

                    }


                }



                else {

                    Spinner spinner = (Spinner) allViewInstance.get(noOfViews);
                    String dropDownJSONOpt[] = eachData.getItemList().split("\\$");
                    ArrayList<String> SpinnerOptions = new ArrayList<String>();
                    for (int j = 0; j < dropDownJSONOpt.length; j++) {
                        SpinnerOptions.add(dropDownJSONOpt[j]);
                    }
                    SpinnerOptions.add(0, "Select");

                    String variant_name = SpinnerOptions.get(spinner.getSelectedItemPosition());
                    Log.d("variant_name", variant_name + "");
//                    optionsObj.put(eachData.getString("option_name"),
//                            "" + variant_name);
                    if (variant_name.equalsIgnoreCase("Select")) {

                    } else {
                        dataArrayList.get(noOfViews).setAnswer(variant_name);
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));

                    }

                }


            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.RADIO_BUTTON)) {
                try {


                    if (eachData.getQuestionId()==8){

                         RadioButton radioButton;

                        int selectedId = rd_group_alcohol.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioButton = (RadioButton) getActivity().findViewById(selectedId);


                        if (radioButton!=null){
                            if (radioButton.getText().toString().length()>0){
                                dataArrayList.get(noOfViews).setAnswer(radioButton.getText().toString());
                                healthParamData.add(dataArrayList.get(noOfViews));
                                healthParamData_static.add(dataArrayList.get(noOfViews));
                            }

                        }






                    }else if (eachData.getQuestionId()==10){

                        RadioButton radioButton;

                        int selectedId = rd_group_smoke.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        radioButton = (RadioButton) getActivity().findViewById(selectedId);


                        if (radioButton!=null&&radioButton.getText().toString().length()>0){
                            dataArrayList.get(noOfViews).setAnswer(radioButton.getText().toString());
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        }

                    }


                    else {
                        RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);
                        RadioButton selectedRadioBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                        dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.CHECK_BOX)) {
                CheckBox tempChkBox = (CheckBox) allViewInstance.get(noOfViews);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < cbViewInstance.size(); i++) {
                    CheckBox tempChkBox1 = (CheckBox) cbViewInstance.get(i);
                    if(tempChkBox1.isChecked()){
                        if(i==cbViewInstance.size()){
                            stringBuilder.append(tempChkBox1.getText().toString());
                        }else{
                            stringBuilder.append(tempChkBox1.getText().toString()+",");
                        }

                        if(!stringBuilder.toString().isEmpty()){
                            String ds ="";
                            if(stringBuilder.toString().endsWith(",")){
                                ds=  stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
                            }else{
                                ds =stringBuilder.toString();
                            }
//                            dataArrayList.get(noOfViews).setAnswer(ds);
//                            healthParamData.add(dataArrayList.get(noOfViews));
//                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        }else {
//                            dataArrayList.get(noOfViews).setAnswer("");
//                            healthParamData.add(dataArrayList.get(noOfViews));
//                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        }
                    }else {
                        if (stringBuilder.toString().isEmpty()){
//                            dataArrayList.get(noOfViews).setAnswer("");
//                            healthParamData.add(dataArrayList.get(noOfViews));
//                            healthParamData_static.add(dataArrayList.get(noOfViews));
                        }

                    }
                }
                String   dsdd="";
                if (stringBuilder.length()>0){
                       dsdd=   stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);

                }


                dataArrayList.get(noOfViews).setAnswer(dsdd);
                healthParamData.add(dataArrayList.get(noOfViews));
                healthParamData_static.add(dataArrayList.get(noOfViews));

                if (tempChkBox.isChecked()) {
                    String arrCheckbox[] = dataArrayList.get(noOfViews).getItemList().split("\\$");
                    for (int j = 0; j < arrCheckbox.length; j++) {

                    }


                    Log.d("fsf", "sff");
                }

            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.INPUT_BOX)) {
                TextView textView = (TextView) allViewInstance.get(noOfViews);


                if (!textView.getText().toString().equalsIgnoreCase("")) {
                    dataArrayList.get(noOfViews).setAnswer(textView.getText().toString());
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));
                    // optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                } else {
                    // optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                }


                Log.d("variant_name", textView.getText().toString() + "");
            }
        }
        return isTrue;
    }
}
//{"Code":1,"Message":"Success","Data":[{"QuestionId":4,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"O-,O+,B-,B+,A-,A+,AB-,AB+","Question":"Blood Group:"},{"QuestionId":5,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Vegetarian,Non-Vegetarian,Eggeterian,Jain","Question":"Food Habbits:"},{"QuestionId":6,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Sedentary,Mildly Active,Moderately Active,Highly Active","Question":"Activity status:"},{"QuestionId":7,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you consume Alcohol?\r\n"},{"QuestionId":8,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Once a week,1-2 times a week,3-4 times a week\r\n","Question":"Frequency of alcohol consumption:\r\n"},{"QuestionId":9,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you Smoke?"},{"QuestionId":10,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"Once a week,1-2 times a week,3-4 times a week\r\n","Question":"Smoking frequency"},{"QuestionId":11,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you chew Tobacco or any other similar item?"},{"QuestionId":12,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you frequently experience stress?"},{"QuestionId":13,"InputType":"MULTISELECT","Answer":null,"AnswerId":0,"ItemList":"Diabetes Type II,High Blood Pressure,Low Blood Pressure,Acidity,PCOD,High Cholesterol,Water Retention,High Uric Acid,Heart Disease,Thyroid Hypo,Others","Question":"Any medical condition?"},{"QuestionId":14,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you have any food allergy?"},{"QuestionId":15,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you regularly take any health supplement?"},{"QuestionId":16,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Any Emotional Suffering?"},{"QuestionId":17,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Are you a Self Motivator?"},{"QuestionId":18,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you eat more, when stressed?"},{"QuestionId":19,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you have the habbit of \"Distracted Eating\" i.e eating while watching movie, listening to music etc.?"},{"QuestionId":20,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Do you have self control?"},{"QuestionId":21,"InputType":"RADIO","Answer":null,"AnswerId":0,"ItemList":"Yes,No","Question":"Any other Special Mention"},{"QuestionId":22,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"None","Question":"Future Question 1"},{"QuestionId":23,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"None","Question":"Future Question 2"},{"QuestionId":24,"InputType":"DROPDOWN","Answer":null,"AnswerId":0,"ItemList":"None","Question":"Future Question 3"},{"QuestionId":25,"InputType":"INPUTBOX","Answer":null,"AnswerId":0,"ItemList":"","Question":"Remarks"}]}
