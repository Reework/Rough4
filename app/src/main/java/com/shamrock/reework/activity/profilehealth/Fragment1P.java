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
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
public class Fragment1P extends Fragment {


    private int editID=5;
    public boolean isEnterWeight=false;
    LinearLayout ll_main_fi;

    public Fragment1P() {
        // Required empty public constructor
    }

    private ArrayList<HealthParamData> dataArrayList;
    List<View> allViewInstance = new ArrayList<View>();
    List<View> cbViewInstance = new ArrayList<View>();
    List<View> allEdittextInstance=new ArrayList<>();
    List<View>  autocomplete=new ArrayList<>();

    int radiobtnId=5;

    public EditText edt_weight;
    RadioGroup rg;
    int weight_type=1;
    RadioButton rb_2,rb_1;


    public EditText edt_hight;
    RadioGroup rb_grp_height;
    RadioButton rb_ft,rb_cm;
    int heighttype=1;
    EditText edt_feet,edt_inch;


    @SuppressLint("ValidFragment")
    public Fragment1P(ArrayList<HealthParamData> dataArrayList
    ) {
        this.dataArrayList = dataArrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment1, container, false);
        edt_weight = view. findViewById(R.id.edt_weight);
        edt_inch = view. findViewById(R.id.edt_inch);
        edt_feet = view. findViewById(R.id.edt_feet);
        ll_main_fi = view. findViewById(R.id.ll_main_fi);
        rg = (RadioGroup)view. findViewById(R.id.rb_grp_weight);
        rg.setEnabled(false);
        rb_1=view.findViewById(R.id.rb_1);
        rb_2=view.findViewById(R.id.rb_2);

        edt_hight=view.findViewById(R.id.edt_hight);
        rb_ft=view.findViewById(R.id.rb_ft);
        rb_cm=view.findViewById(R.id.rb_cm);
        rb_grp_height=view.findViewById(R.id.rb_grp_height);
        rb_grp_height.setEnabled(false);
        LinearLayout viewProductLayout = view.findViewById(R.id.customOptionLL);
        viewProductLayout.setEnabled(false);
        try{
            if (dataArrayList!=null){
                for (int noOfCustomOpt = 0; noOfCustomOpt < dataArrayList.size(); noOfCustomOpt++) {

                    if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.DROPDOWN)) {
                        if (dataArrayList.get(noOfCustomOpt).getQuestionId()==3){

                            TextView customOptionsName = new TextView(getActivity());
                            customOptionsName.setTextSize(14);
//                        customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                            customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                            customOptionsName.setPadding(0, 50, 0, 5);

                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0,10,0,0);
                            customOptionsName.setLayoutParams(layoutParams);
                            customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                            customOptionsName.setTextSize(14);
                            viewProductLayout.addView(customOptionsName);

                            AutoCompleteTextView autoCompleteTextView=new AutoCompleteTextView(getActivity());
                            autoCompleteTextView.setHint("search city");
                            autoCompleteTextView.setTextSize(14);
                            viewProductLayout.addView(autoCompleteTextView);
                            autocomplete.add(autoCompleteTextView);


                            String arrDropdown[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");
                            ArrayList<String> SpinnerOptions = new ArrayList<String>();
                            for (int j = 0; j < arrDropdown.length; j++) {
                                SpinnerOptions.add(arrDropdown[j]);
                            }
                            SpinnerOptions.add(0, "Select");
                            ArrayAdapter<String> spinnerArrayAdapter = null;
                            spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spiner_row, SpinnerOptions);
//                        Spinner spinner = new Spinner(getActivity());

                            autoCompleteTextView.setAdapter(spinnerArrayAdapter);
                            autoCompleteTextView.setEnabled(false);

                            if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                                for (int i = 0; i < SpinnerOptions.size(); i++) {
                                    if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(SpinnerOptions.get(i).trim().trim())){
                                        autoCompleteTextView.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                                        break;
                                    }

                                }
                            }


                            allViewInstance.add(autoCompleteTextView);

                        }else {
                            TextView customOptionsName = new TextView(getActivity());
                            customOptionsName.setTextSize(14);
                            customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                            customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                            customOptionsName.setPadding(0, 50, 0, 5);

                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0,10,0,0);
                            customOptionsName.setLayoutParams(layoutParams);
                            customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
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
                            allViewInstance.add(spinner);
                            spinner.setAdapter(spinnerArrayAdapter);
                            spinner.setSelection(0, false);
                            spinner.setEnabled(false);
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
                            v1.setPadding(0, 0, 0, 25);

                            viewProductLayout.addView(v1);




                            if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                                TextInputLayout til = new TextInputLayout(getActivity());
                                til.setPadding(0,50,0,0);
                                til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                                EditText et = new EditText(getActivity());
                                et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+editID);
                                et.setEnabled(false);
                                allEdittextInstance.add(et);
                                if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){

                                    et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());

                                }
                                til.addView(et);




                                viewProductLayout.addView(til);
                            }



                        }





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
                        rg.setEnabled(false);
                        // rg.setOrientation(LinearLayout.HORIZONTAL);
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
                                        // Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
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

                                    }catch (Exception ex){
                                        ex.printStackTrace();
                                    }

                                }
                            });

                        }

                        if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                            for (int i = 0; i < arrRadioButton.length; i++) {
                                if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(arrRadioButton[i].trim().toString())){
                                    rg.check(rg.getChildAt(i).getId());
                                }

                            }
                        }
                        viewProductLayout.addView(rg, params);



                        if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                            TextInputLayout til = new TextInputLayout(getActivity());
                            til.setPadding(0,50,0,0);
                            til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                            EditText et = new EditText(getActivity());
                            et.setEnabled(false);
                            et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                            allEdittextInstance.add(et);
                            if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                                et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                            }
                            til.addView(et);
//                    allViewInstance.add(et);
                            viewProductLayout.addView(til);
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
                            til.setPadding(0,50,0,0);
                            til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                            EditText et = new EditText(getActivity());
                            et.setEnabled(false);
                            et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                            allEdittextInstance.add(et);
                            if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                                et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                            }
                            til.addView(et);
//                    allViewInstance.add(et);
                            viewProductLayout.addView(til);
                        }


                    }else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.INPUT_BOX)){

                        TextInputLayout til = new TextInputLayout(getActivity());
                        til.setPadding(0,50,0,0);
                        til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                        EditText et = new EditText(getActivity());
                        et.setEnabled(false);

                        et.setTextSize(14);
                        if (dataArrayList.get(noOfCustomOpt).getQuestionId()==4){
                            et.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                            til.setVisibility(View.GONE);
                            try {
                                if (dataArrayList.get(noOfCustomOpt).getAnswer() != null) {
                                    if (!dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("null")){
                                        edt_weight.setText(String.valueOf(dataArrayList.get(noOfCustomOpt).getAnswer()));

                                    }else {
                                        edt_weight.setText("");

                                    }

                                } else {
                                    edt_weight.setText("");
                                }
                            }catch (Exception e){
                                edt_weight.setText("");

                            }

                            edt_weight.setEnabled(false);
                            rb_1.setEnabled(false);
                            rb_2.setEnabled(false);

                            if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter() != null ) {
                                if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter().equals("1")){
                                    rb_1.setChecked(true);
                                    rb_2.setChecked(false);

                                }else {
                                    rb_2.setChecked(true);
                                    rb_1.setChecked(false);

                                }
                            } else {
                                rb_2.setChecked(false);

                            }

                        }
                        if (dataArrayList.get(noOfCustomOpt).getQuestionId()==27){
                            et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
                            int maxlngth=6;
                            InputFilter[] inputFilters=new InputFilter[1];
                            inputFilters[0]=new InputFilter.LengthFilter(maxlngth);
                            et.setFilters(inputFilters);
                            til.setVisibility(View.GONE);

                            if (dataArrayList.get(noOfCustomOpt).getAnswer() != null) {
                                try {
                                    if (!dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("null")){
                                        edt_hight.setText(String.valueOf(dataArrayList.get(noOfCustomOpt).getAnswer()));
                                        if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter().equalsIgnoreCase("1")){
                                            edt_hight.setText(String.valueOf(Integer.parseInt(String.valueOf(Math.round(Float.parseFloat(dataArrayList.get(noOfCustomOpt).getAnswer()))))));

                                        }

                                    }else {
                                        edt_hight.setText("");

                                    }
                                }catch (Exception e){
                                    edt_hight.setText("");

                                }




                            } else {
                                edt_hight.setText("");
                            }
//                            if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter() != null) {
//                                if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter().equals("1")){
//                                    rb_cm.setChecked(true);
//                                    rb_ft.setChecked(false);
//
//
//                                }else {
//                                    rb_ft.setChecked(true);
//                                    rb_cm.setChecked(false);
//
//
//                                }
//                            } else {
//                                rb_ft.setChecked(true);
//
//                            }
                            if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter() != null) {
                                if (dataArrayList.get(noOfCustomOpt).getAdditonalParameter().equals("1")){
                                    rb_ft.setChecked(false);
                                    rb_cm.setChecked(true);
                                    ll_main_fi.setVisibility(View.GONE);
                                    edt_hight.setVisibility(View.VISIBLE);
                                    rb_ft.setEnabled(false);
                                    rb_cm.setEnabled(false);
                                    rb_ft.setEnabled(false);
                                    rb_cm.setEnabled(false);
                                    edt_feet.setEnabled(false);
                                    edt_inch.setEnabled(false);

                                }else {
                                    ll_main_fi.setVisibility(View.VISIBLE);
                                    edt_hight.setVisibility(View.GONE);
                                    rb_ft.setChecked(true);
                                    rb_cm.setChecked(false);

                                    String ary[]=dataArrayList.get(noOfCustomOpt).getAnswer().split("\\.");
                                    edt_feet.setText(ary[0]);
                                    edt_inch.setText(ary[1]);
                                    rb_ft.setEnabled(false);
                                    rb_cm.setEnabled(false);
                                    rb_ft.setEnabled(false);
                                    rb_cm.setEnabled(false);
                                    edt_feet.setEnabled(false);
                                    edt_inch.setEnabled(false);
                                }
                            } else {
                                rb_ft.setChecked(true);
                                rb_ft.setEnabled(false);
                                rb_cm.setEnabled(false);
                                edt_feet.setEnabled(false);
                                edt_inch.setEnabled(false);

                            }
                        }



                        if (dataArrayList.get(noOfCustomOpt).getQuestion().equalsIgnoreCase("Country")){
                            et.setText("India");
                        }





                        if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){

                            et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());

                        }
                        til.addView(et);


                        allViewInstance.add(et);


                        viewProductLayout.addView(til);
                    }


                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }

    public void add(){

    }

    @SuppressLint("ResourceType")
    public void getFirstPageData() {
        ArrayList<HealthParamData> healthParamData = new ArrayList<>();
        for (int noOfViews = 0; noOfViews < dataArrayList.size(); noOfViews++) {
            HealthParamData eachData = dataArrayList.get(noOfViews);

            if (eachData.getInputType().equals(DynamicHealthparamActivity.DROPDOWN)) {

                if (eachData.getQuestionId()==3){
                    AutoCompleteTextView autoCompleteTextView= (AutoCompleteTextView) autocomplete.get(0);

                    dataArrayList.get(noOfViews).setAnswer(autoCompleteTextView.getText().toString().trim());
                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));

                }else {
                    try {
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

                            dataArrayList.get(noOfViews).setAnswer(variant_name);
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            healthParamData.add(dataArrayList.get(noOfViews));
                            healthParamData_static.add(dataArrayList.get(noOfViews));

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }



                //getRadiobuttonData



            }
            if (eachData.getInputType().equals(DynamicHealthparamActivity.RADIO_BUTTON)) {


                try {

                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);
                    RadioButton selectedRadioBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

                    if (eachData.getQuestionId() == 1) {

                        for (int i = 0; i < allEdittextInstance.size(); i++) {

                            if (allEdittextInstance.get(i).getId() == 1) {
                                EditText editText = (EditText) allEdittextInstance.get(i);

                                if (editText != null && editText.getText().toString() != null) {
                                    dataArrayList.get(noOfViews).setRemark(editText.getText().toString());
                                    dataArrayList.get(noOfViews).setItemList("");
                                    dataArrayList.get(noOfViews).setQuestion("");
                                    dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                                    healthParamData.add(dataArrayList.get(noOfViews));
                                    healthParamData_static.add(dataArrayList.get(noOfViews));
                                }

                            }
                        }


                    } else if (eachData.getQuestionId() == 2) {

                        for (int i = 0; i < allEdittextInstance.size(); i++) {

                            if (allEdittextInstance.get(i).getId() == 1) {
                                EditText editText = (EditText) allEdittextInstance.get(i);

                                if (editText != null && editText.getText().toString() != null) {
                                    dataArrayList.get(noOfViews).setRemark(editText.getText().toString());
                                    dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                                    dataArrayList.get(noOfViews).setItemList("");
                                    dataArrayList.get(noOfViews).setQuestion("");
                                    healthParamData.add(dataArrayList.get(noOfViews));
                                    healthParamData_static.add(dataArrayList.get(noOfViews));
                                }

                            }
                        }

                    } else {
                        dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                        dataArrayList.get(noOfViews).setItemList("");
                        dataArrayList.get(noOfViews).setQuestion("");
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));
                    }

                }catch (Exception e){
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
                                dataArrayList.get(noOfViews).setAnswer(ds);
                                dataArrayList.get(noOfViews).setItemList("");
                                dataArrayList.get(noOfViews).setQuestion("");
                                healthParamData.add(dataArrayList.get(noOfViews));
                                healthParamData_static.add(dataArrayList.get(noOfViews));
                            } else {
                                dataArrayList.get(noOfViews).setAnswer("");
                                dataArrayList.get(noOfViews).setItemList("");
                                dataArrayList.get(noOfViews).setQuestion("");
                                healthParamData.add(dataArrayList.get(noOfViews));
                                healthParamData_static.add(dataArrayList.get(noOfViews));
                            }
                        } else {
                            if (stringBuilder.toString().isEmpty()) {
                                dataArrayList.get(noOfViews).setAnswer("");
                                dataArrayList.get(noOfViews).setItemList("");
                                dataArrayList.get(noOfViews).setQuestion("");
                                healthParamData.add(dataArrayList.get(noOfViews));
                                healthParamData_static.add(dataArrayList.get(noOfViews));
                            }

                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.INPUT_BOX)) {





                try {




                    TextView textView = (TextView) allViewInstance.get(noOfViews);




                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");

                    if (eachData.getQuestionId()==28){


                        dataArrayList.get(noOfViews).setAnswer(edt_weight.getText().toString());


                        dataArrayList.get(noOfViews).setAdditonalParameter(String.valueOf(weight_type));



                    }else if (eachData.getQuestionId()==27){

                        double heightsubmit=Double.parseDouble(edt_hight.getText().toString());
                        dataArrayList.get(noOfViews).setAnswer(String.valueOf(heightsubmit));


                        dataArrayList.get(noOfViews).setAdditonalParameter(String.valueOf(heighttype));
                    }

                    else {
                        dataArrayList.get(noOfViews).setAnswer(textView.getText().toString());

                    }
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));




                    Log.d("variant_name", textView.getText().toString() + "");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
