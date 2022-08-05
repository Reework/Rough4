package com.shamrock.reework.activity.profilehealth;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;

import java.util.ArrayList;
import java.util.List;

import static com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity.healthParamData_static;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4P extends Fragment {


    private String emotionalsuppering;
    RadioButton rd_button_profile;

    public Fragment4P(ArrayList<HealthParamData> dataArrayList, RadioButton rd_button_profile) {
        this.dataArrayList = dataArrayList;
        this.rd_button_profile=rd_button_profile;

        // Required empty public constructor
    }
    private ArrayList<HealthParamData> dataArrayList ;
    List<View> allViewInstance = new ArrayList<View>();
    List<View> cbViewInstance = new ArrayList<View>();
    List<View> allEdittextInstance = new ArrayList<View>();
    List<View> allInputLAyouttInstance=new ArrayList<>();

    int editID=5;
    ;int txtInput=20;
    Button btn_go_to_basic;

    @SuppressLint("ValidFragment")
    public Fragment4P(ArrayList<HealthParamData> dataArrayList) {
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_4_s, container, false);
        btn_go_to_basic=view.findViewById(R.id.btn_go_to_basic);
        btn_go_to_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rd_button_profile.performClick();
            }
        });
        LinearLayout viewProductLayout = view.findViewById(R.id.customOptionLL);
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
                spinner.setEnabled(false);

                spinner.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                allViewInstance.add(spinner);
                spinner.setAdapter(spinnerArrayAdapter);
                spinner.setSelection(0, false);
                if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                    for (int i = 0; i < SpinnerOptions.size(); i++) {
                        if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(SpinnerOptions.get(i).trim().trim())){
                            spinner.setSelection(i);
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
                    til.setEnabled(false);

                    til.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+txtInput);

                    til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                    EditText et = new EditText(getActivity());
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+editID);
                    allEdittextInstance.add(et);
                    allInputLAyouttInstance.add(til);

                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                        et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                    }
                    til.addView(et);
//                    allViewInstance.add(et);
                    viewProductLayout.addView(til);
                }
            } else
                if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.RADIO_BUTTON)) {

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
                    rg.setEnabled(false);

                    rg.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                allViewInstance.add(rg);
                for (int j = 0; j < arrRadioButton.length; j++) {

                    RadioButton rb = new RadioButton(getActivity());
                    rb.setEnabled(false);

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


                            try {
                                View radioButton = group.findViewById(checkedId);
                                String variant_name = radioButton.getTag().toString();
                                if (rg.getId() == 8) {
                                    if (variant_name.equalsIgnoreCase("Yes")) {

                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 9) {
                                                allViewInstance.get(i).setEnabled(true);
                                            }
                                        }

                                    } else {
                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 9) {
                                                allViewInstance.get(i).setEnabled(false);
                                            }
                                        }
                                    }


                                }

                                if (rg.getId() == 10) {
                                    if (variant_name.equalsIgnoreCase("Yes")) {

                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 11) {
                                                allViewInstance.get(i).setEnabled(true);
                                            }
                                        }

                                    } else {
                                        for (int i = 0; i < allViewInstance.size(); i++) {

                                            if (allViewInstance.get(i).getId() == 11) {
                                                allViewInstance.get(i).setEnabled(false);
                                            }
                                        }
                                    }


                                }

                                if (rg.getId()==17){


                                    if (variant_name.equalsIgnoreCase("Yes, please specify the name in remarks")||variant_name.equalsIgnoreCase("Any other, please specify in remarks")){

                                        emotionalsuppering=variant_name;

                                        for (int i = 0; i <allEdittextInstance.size() ; i++) {

                                            if (allEdittextInstance.get(i).getId()==(17+editID)){
                                                allEdittextInstance.get(i).setVisibility(View.VISIBLE);
                                                break;
                                            }
                                        }

                                        for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {

                                            if (allInputLAyouttInstance.get(i).getId()==(17+txtInput)){
                                                allInputLAyouttInstance.get(i).setVisibility(View.VISIBLE);
                                                break;
                                            }
                                        }

                                    }else {

                                        emotionalsuppering=variant_name;
                                        for (int i = 0; i <allEdittextInstance.size() ; i++) {

                                            if (allEdittextInstance.get(i).getId()==(17+editID)){
                                                allEdittextInstance.get(i).setVisibility(View.GONE);
                                                break;
                                            }
                                        }

                                        for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {

                                            if (allInputLAyouttInstance.get(i).getId()==(17+txtInput)){
                                                allInputLAyouttInstance.get(i).setVisibility(View.GONE);
                                                break;
                                            }
                                        }
                                    }



                                }

                                if (rg.getId()==24){


                                    if (variant_name.equalsIgnoreCase("Yes, please specify in the remarks col.")||variant_name.equalsIgnoreCase("Any other, please specify in remarks")){

                                        emotionalsuppering=variant_name;

                                        for (int i = 0; i <allEdittextInstance.size() ; i++) {

                                            if (allEdittextInstance.get(i).getId()==(24+editID)){
                                                allEdittextInstance.get(i).setVisibility(View.VISIBLE);
                                                break;
                                            }
                                        }

                                        for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {

                                            if (allInputLAyouttInstance.get(i).getId()==(24+txtInput)){
                                                allInputLAyouttInstance.get(i).setVisibility(View.VISIBLE);
                                                break;
                                            }
                                        }

                                    }else {

                                        emotionalsuppering=variant_name;
                                        for (int i = 0; i <allEdittextInstance.size() ; i++) {

                                            if (allEdittextInstance.get(i).getId()==(24+editID)){
                                                allEdittextInstance.get(i).setVisibility(View.GONE);
                                                break;
                                            }
                                        }

                                        for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {

                                            if (allInputLAyouttInstance.get(i).getId()==(24+txtInput)){
                                                allInputLAyouttInstance.get(i).setVisibility(View.GONE);
                                                break;
                                            }
                                        }
                                    }



                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });


                }
                viewProductLayout.addView(rg, params);
                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                        TextInputLayout til = new TextInputLayout(getActivity());
                    til.setEnabled(false);

                    til.setPadding(0,50,0,0);
                        til.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+txtInput);
                        til.setHint("Remarks");
                        EditText et = new EditText(getActivity());
                    et.setEnabled(false);

                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+editID);
                        allEdittextInstance.add(et);
                        allInputLAyouttInstance.add(til);
                        if (dataArrayList.get(noOfCustomOpt).getRemark()!=null&&!dataArrayList.get(noOfCustomOpt).getRemark().isEmpty()){
                            et.setText(dataArrayList.get(noOfCustomOpt).getRemark());
                        }
                        til.addView(et);
                        til.setVisibility(View.GONE);
                        viewProductLayout.addView(til);
                    }

                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                        for (int i = 0; i < arrRadioButton.length; i++) {
                            if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(arrRadioButton[i].trim().toString())){
                                int qnID=dataArrayList.get(noOfCustomOpt).getQuestionId();
                                if ((qnID==17)){


                                    if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("Yes, please specify the name in remarks")||dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("Any other, please specify in remarks")){
                                        for (int j = 0; j <allEdittextInstance.size() ; j++) {
                                            if (allEdittextInstance.get(j).getId()==(qnID+editID)){
                                                allEdittextInstance.get(j).setVisibility(View.VISIBLE);
                                            }


                                        }



                                    }else {
                                        for (int j = 0; j <allEdittextInstance.size() ; j++) {
                                            if (allEdittextInstance.get(j).getId()==(qnID+editID)){
                                                allEdittextInstance.get(j).setVisibility(View.GONE);
                                            }


                                        }
                                    }


                                }

                                if ((qnID==24)){


                                    if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("Yes, please specify the name in remarks")||dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("Yes, please specify in the remarks col.")){
                                        for (int j = 0; j <allEdittextInstance.size() ; j++) {
                                            if (allEdittextInstance.get(j).getId()==(qnID+editID)){
                                                allEdittextInstance.get(j).setVisibility(View.VISIBLE);
                                            }


                                        }



                                    }else {
                                        for (int j = 0; j <allEdittextInstance.size() ; j++) {
                                            if (allEdittextInstance.get(j).getId()==(qnID+editID)){
                                                allEdittextInstance.get(j).setVisibility(View.GONE);
                                            }


                                        }
                                    }


                                }



                                rg.check(rg.getChildAt(i).getId());
                            }

                        }
                    }


                } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.CHECK_BOX)) {
                String arrCheckbox[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
                TextView customOptionsName = new TextView(getActivity());
                customOptionsName.setTextSize(14);
                customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                customOptionsName.setPadding(0, 50, 0, 5);
                customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                viewProductLayout.addView(customOptionsName);
                for (int j = 0; j < arrCheckbox.length; j++) {
                    CheckBox chk = new CheckBox(getActivity());
                    chk.setEnabled(false);

                    chk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if(j==0) {
                        allViewInstance.add(chk);
                    }
                    cbViewInstance.add(chk);
                    chk.setTag(arrCheckbox[j]);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 3;
                    params.bottomMargin = 3;
                    String optionString = arrCheckbox[j];
                    chk.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String variant_name = v.getTag().toString();
                            //Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                        }
                    });
                    chk.setText(optionString);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()&&dataArrayList.get(noOfCustomOpt).getAnswer().contains(",")){

                        String chkarr[]=dataArrayList.get(noOfCustomOpt).getAnswer().split("\\$");


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
                        til.setEnabled(false);

                        til.setPadding(0,50,0,0);
                        til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                        EditText et = new EditText(getActivity());
                        et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                        allEdittextInstance.add(et);
                        if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                            et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                        }
                        til.addView(et);
//                        allViewInstance.add(et);
                        viewProductLayout.addView(til);
                    }

            }else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.INPUT_BOX)){



                TextInputLayout til = new TextInputLayout(getActivity());
                    til.setEnabled(false);

                    til.setPadding(0,50,0,0);

                    if (dataArrayList.get(noOfCustomOpt).getQuestion().equalsIgnoreCase("Height(CM)")){
//                        til.setHint("dataArrayList.get(noOfCustomOpt).getQuestion()");
                        til.setHint("Height(cm)");

                    }else if (dataArrayList.get(noOfCustomOpt).getQuestion().equalsIgnoreCase("Weight(KG)")){
                        til.setHint("Weight(kg)");

                    }else {
                        til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());

                    }

                EditText et = new EditText(getActivity());
                    et.setEnabled(false);


                    if (dataArrayList.get(noOfCustomOpt).getQuestionId()==27){
                        et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
                        int maxlngth=6;
                        InputFilter[] inputFilters=new InputFilter[1];
                        inputFilters[0]=new InputFilter.LengthFilter(maxlngth);
                        et.setFilters(inputFilters);
                    }
                    if (dataArrayList.get(noOfCustomOpt).getQuestionId()==28){
                        et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
                        int maxlngth=6;
                        InputFilter[] inputFilters=new InputFilter[1];
                        inputFilters[0]=new InputFilter.LengthFilter(maxlngth);
                        et.setFilters(inputFilters);
                    }

                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){

                    et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());

                }
                til.addView(et);
                allViewInstance.add(et);
                viewProductLayout.addView(til);
            }


        }


        return view;    }
    public boolean getFourthPageData(){

        boolean isValidated=true;




        ArrayList<HealthParamData> healthParamData = new ArrayList<>();
        for (int noOfViews = 0; noOfViews < dataArrayList.size(); noOfViews++) {








            HealthParamData eachData = dataArrayList.get(noOfViews);

            if (eachData.getInputType().equals(DynamicHealthparamActivity.DROPDOWN)) {
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
                if(variant_name.equalsIgnoreCase("Select")){

                }else{
                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setAnswer(variant_name);
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));

                }
            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.RADIO_BUTTON)) {
                try {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);

                    RadioButton selectedRadioBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());





                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                    for (int i = 0; i <allEdittextInstance.size() ; i++) {
                        if (allEdittextInstance.get(i).getId()==(eachData.getQuestionId()+editID)){
                            EditText editText= (EditText) allEdittextInstance.get(i);
                            dataArrayList.get(noOfViews).setRemark(editText.getText().toString());
                        }
                    }
                    if (selectedRadioBtn.getTag().toString().equalsIgnoreCase("No")){
                        dataArrayList.get(noOfViews).setRemark("");

                    }
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));
                }catch (Exception e){
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
                            stringBuilder.append(tempChkBox1.getText().toString()+"$");
                        }

                        if(!stringBuilder.toString().isEmpty()){
                            String ds ="";
                            if(stringBuilder.toString().endsWith("$")){
                                ds=   stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
                            }else{
                                ds =stringBuilder.toString();
                            }

                        }else {

                        }
                    }else {
                        if (stringBuilder.toString().isEmpty()){
//
                        }

                    }
                }

                String   dsdd=   stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);

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

            }
            if (eachData.getInputType().equals(DynamicHealthparamActivity.INPUT_BOX)) {
                TextView textView = (TextView) allViewInstance.get(noOfViews);

                if (!textView.getText().toString().equalsIgnoreCase("")) {
                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setAnswer(textView.getText().toString());
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));

                } else{

                    if (eachData.getQuestionId()==27){
                        Toast.makeText(getActivity(), "Please enter the height", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    if (eachData.getQuestionId()==28){
                        Toast.makeText(getActivity(), "Please enter the weight", Toast.LENGTH_SHORT).show();
                        return false;
                    }


                }


                Log.d("variant_name", textView.getText().toString() + "");
            }
        }

        return isValidated;
    }


}
