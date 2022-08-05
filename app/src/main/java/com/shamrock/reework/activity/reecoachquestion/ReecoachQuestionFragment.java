package com.shamrock.reework.activity.reecoachquestion;


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
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.HealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.ReecoachHealthParamData;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsPostHealthData;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsgetPostData;
import com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.activity.reecoachquestion.ReecoachQuestionActivity._reecoachhealthParamData_static;
import static com.shamrock.reework.activity.reeworkerhealth.app.DynamicHealthparamActivity.healthParamData_static;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReecoachQuestionFragment extends Fragment {


    private int editID=5;
    public boolean isEnterWeight=false;

    public ReecoachQuestionFragment() {
        // Required empty public constructor
    }

    private ArrayList<ReecoachHealthParamData> dataArrayList;
    List<View> allViewInstance = new ArrayList<View>();
    List<View> cbViewInstance = new ArrayList<View>();
    List<View> allEdittextInstance=new ArrayList<>();
    List<View>  autocomplete=new ArrayList<>();

    int radiobtnId=5;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    public EditText edt_weight;
    RadioGroup rg;
    int weight_type=1;
    RadioButton rb_2,rb_1;


    public EditText edt_hight;
    RadioGroup rb_grp_height;
    RadioButton rb_ft,rb_cm;
    int heighttype=1;
    EditText edt_inch,edt_feet;
    LinearLayout ll_main_fi;

    private ViewFlipper vp_health_profile;
    int count=1;
    int count_viewflipper=0;
    View view9,view10,view11,view12,view13,view14,view15,view16;
    View view1,view2,view3,view4,view5,view6,view7,view8;

    private Utils utils;
    private HealthParametersService healthParametersService;


    @SuppressLint("ValidFragment")
    public ReecoachQuestionFragment(ArrayList<ReecoachHealthParamData> dataArrayList
    ) {
        this.dataArrayList = dataArrayList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment1s, container, false);


        utils=new Utils();
        healthParametersService = Client.getClient().create(HealthParametersService.class);


        edt_weight = view. findViewById(R.id.edt_weight);
        rg = (RadioGroup)view. findViewById(R.id.rb_grp_weight);
        rb_1=view.findViewById(R.id.rb_1);
        rb_2=view.findViewById(R.id.rb_2);
        ll_main_fi=view.findViewById(R.id.ll_main_fi);

        edt_hight=view.findViewById(R.id.edt_hight);
        edt_inch=view.findViewById(R.id.edt_inch);
        edt_feet=view.findViewById(R.id.edt_feet);
        rb_ft=view.findViewById(R.id.rb_ft);
        rb_cm=view.findViewById(R.id.rb_cm);
        rb_grp_height=view.findViewById(R.id.rb_grp_height);
        rb_grp_height.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_cm:
                        heighttype=1;
                        edt_hight.setVisibility(View.VISIBLE);
                        ll_main_fi.setVisibility(View.GONE);
                        break;
                    case R.id.rb_ft:
                        heighttype=2;
                        ll_main_fi.setVisibility(View.VISIBLE);
                        edt_hight.setVisibility(View.GONE);
                        break;

                }
            }
        });





        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_1:
                        weight_type=1;
                        break;
                    case R.id.rb_2:
                        weight_type=2;


                        // do operations specific to this selection
                        break;

                }
            }
        });
        view1=view.findViewById(R.id.view1);
        view2=view.findViewById(R.id.view2);
        view3=view.findViewById(R.id.view3);
        view4=view.findViewById(R.id.view4);
        view5=view.findViewById(R.id.view5);
        view6=view.findViewById(R.id.view6);
        view7=view.findViewById(R.id.view7);
        view8=view.findViewById(R.id.view8);
        view9=view.findViewById(R.id.view9);
        view10=view.findViewById(R.id.view10);
        vp_health_profile=view.findViewById(R.id.vp_health_profile);
        final TextView btn_next_slide=view.findViewById(R.id.btn_next_slide);
        final TextView txt_back_slide=view.findViewById(R.id.txt_back_slide);


        btn_next_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;
                count_viewflipper++;

                if (count_viewflipper < 10) {
                    vp_health_profile.setDisplayedChild(count_viewflipper);

                    setindicator(count);
                    if (vp_health_profile.getDisplayedChild() == 9) {
                        btn_next_slide.setText("Submit");
                    } else {
                        btn_next_slide.setText("Next");

                    }
                }
                if (count_viewflipper == 10) {
                    btn_next_slide.setEnabled(false);
//                    callHealthParameterPostApi();
                    getFirstPageData();
                    callHealthParameterPostApi();

                }else{
                    btn_next_slide.setEnabled(true);
                }
                if (count_viewflipper == 1) {
                    txt_back_slide.setBackgroundResource(R.drawable.bg_black_box);
                    txt_back_slide.setTextColor(getResources().getColor(R.color.black));
                }





            }
        });
        txt_back_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vp_health_profile.getDisplayedChild() == 0) {
                    txt_back_slide.setBackgroundResource(R.drawable.bg_gray_box);
                    txt_back_slide.setTextColor(getResources().getColor(R.color.gray));
                    return;
                }
                if (count_viewflipper == 1) {
                    txt_back_slide.setBackgroundResource(R.drawable.bg_gray_box);
                    txt_back_slide.setTextColor(getResources().getColor(R.color.gray));
                }
                if (count != 0) {
                    count--;
                    count_viewflipper--;
                    vp_health_profile.setDisplayedChild(count_viewflipper);
                    setindicator(count);
                    if (vp_health_profile.getDisplayedChild() == 15) {
                        btn_next_slide.setText("Submit");
                    } else {
                        btn_next_slide.setText("Next");
                        btn_next_slide.setEnabled(true);

                    }
//                    if (count_viewflipper != 1) {
//                        txt_back_slide.setBackgroundResource(R.drawable.bg_black_box);
//                        txt_back_slide.setTextColor(getResources().getColor(R.color.black));
//                    }

                }


            }
        });


        LinearLayout viewProductLayout1 = view.findViewById(R.id.customOptionLL1);
        LinearLayout viewProductLayout2 = view.findViewById(R.id.customOptionLL2);
        LinearLayout viewProductLayout3 = view.findViewById(R.id.customOptionLL3);
        LinearLayout viewProductLayout4 = view.findViewById(R.id.customOptionLL4);

        try {
            if (dataArrayList != null) {
                for (int noOfCustomOpt = 0; noOfCustomOpt < dataArrayList.size(); noOfCustomOpt++) {

                    if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase("Dropdown")) {
                            TextView customOptionsName = new TextView(getActivity());
                            customOptionsName.setTextSize(14);
                            customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                            customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                            customOptionsName.setPadding(0, 50, 0, 5);

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0, 10, 0, 0);
                            customOptionsName.setLayoutParams(layoutParams);
                            customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                            viewProductLayout1.addView(customOptionsName);
                            String arrDropdown[] = dataArrayList.get(noOfCustomOpt).getItemList().split(",");
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
                            if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {
                                for (int i = 0; i < SpinnerOptions.size(); i++) {
                                    if (dataArrayList.get(noOfCustomOpt).getAnswer().trim().equalsIgnoreCase(SpinnerOptions.get(i).trim().trim())) {
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
                            viewProductLayout1.addView(spinner);
                            View v1 = new View(getActivity());
                            v1.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    1));
                            v1.setBackgroundColor(Color.parseColor("#c0c0c0"));
                            v1.setPadding(0, 0, 0, 25);

                            viewProductLayout1.addView(v1);



//                        View v2 = new View(getActivity());
//                        v2.setLayoutParams(new LinearLayout.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                5));
//                        v2.setBackgroundColor(Color.parseColor("#c0c0c0"));
//                        v2.setPadding(0, 30, 0, 25);
//                        v2.setLayoutParams();
//
//                        viewProductLayout.addView(v2);



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
                        viewProductLayout2.addView(customOptionsName);
                        String arrRadioButton[] = dataArrayList.get(noOfCustomOpt).getItemList().split("\\$");


                        final RadioGroup rg = new RadioGroup(getActivity()); //create the RadioGroup
                        // rg.setOrientation(LinearLayout.HORIZONTAL);
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

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
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
                        viewProductLayout2.addView(rg, params);




                    } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase("Multi-Select")) {
                        String arrCheckbox[] = dataArrayList.get(noOfCustomOpt).getItemList().split(",");
                        TextView customOptionsName = new TextView(getActivity());
                        customOptionsName.setTextSize(14);
                        customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                        customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                        customOptionsName.setPadding(0, 50, 0, 5);
                        customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                        viewProductLayout3.addView(customOptionsName);


                        for (int j = 0; j < arrCheckbox.length; j++) {


                            CheckBox chk = new CheckBox(getActivity());
                            chk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            if (j == 0) {
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
                            if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty() && dataArrayList.get(noOfCustomOpt).getAnswer().contains(",")) {

                                String chkarr[] = dataArrayList.get(noOfCustomOpt).getAnswer().split(",");

                                for (int i = 0; i < chkarr.length; i++) {
                                    if (arrCheckbox[j].toString().trim().equalsIgnoreCase(chkarr[i].toString().trim())) {
                                        chk.setChecked(true);
                                    }


                                }


                            } else if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty() && !dataArrayList.get(noOfCustomOpt).getAnswer().contains(",")) {

                                if (dataArrayList.get(noOfCustomOpt).getAnswer().toString().trim().equalsIgnoreCase(arrCheckbox[j].toString().trim())) {
                                    chk.setChecked(true);
                                }
                            }
                            viewProductLayout3.addView(chk, params);
                        }




                    } else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase("Input")) {


                        TextView customOptionsName = new TextView(getActivity());
                        customOptionsName.setTextSize(14);
                        customOptionsName.setTypeface(Typeface.DEFAULT_BOLD);
                        customOptionsName.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                        customOptionsName.setPadding(0, 50, 0, 5);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 10, 0, 0);
                        customOptionsName.setLayoutParams(layoutParams);
                        customOptionsName.setText(dataArrayList.get(noOfCustomOpt).getQuestion());
                        viewProductLayout2.addView(customOptionsName);

//                        TextInputLayout til = new TextInputLayout(getActivity());
//                        til.setPadding(0, 50, 0, 0);
//                        til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
//                        til.setTypeface(Typeface.DEFAULT_BOLD);
//                        til.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                        EditText et = new EditText(getActivity());
                        et.setTextSize(14);
                        et.setTypeface(Typeface.DEFAULT_BOLD);
                        et.setTextColor(getActivity().getResources().getColor(android.R.color.black));
//                        et.setPadding(0, 50, 0, 5);

                        if (dataArrayList.get(noOfCustomOpt).getAnswer() != null && !dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()) {

                            et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());

                        }
                        viewProductLayout2.addView(et);


                        allViewInstance.add(et);
//                        View v1 = new View(getActivity());
//                        v1.setLayoutParams(new LinearLayout.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                1));
//                        v1.setBackgroundColor(Color.parseColor("#c0c0c0"));
//                        v1.setPadding(0, 0, 0, 25);
//
//                        viewProductLayout2.addView(v1);

//                        viewProductLayout2.addView(til);
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


        ArrayList<ReecoachHealthParamData> healthParamData = new ArrayList<>();
        for (int noOfViews = 0; noOfViews < dataArrayList.size(); noOfViews++) {
            ReecoachHealthParamData eachData = dataArrayList.get(noOfViews);

            if (eachData.getInputType().equals("Dropdown")) {



                    try {
                        Spinner spinner = (Spinner) allViewInstance.get(noOfViews);
                        String dropDownJSONOpt[] = eachData.getItemList().split(",");
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
                            dataArrayList.get(noOfViews).setId(dataArrayList.get(noOfViews).getId());
                            healthParamData.add(dataArrayList.get(noOfViews));
                            _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));

                        }
                    }catch (Exception e){
                        e.printStackTrace();
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
                                    dataArrayList.get(noOfViews).setId(dataArrayList.get(noOfViews).getQuestionId());

                                    healthParamData.add(dataArrayList.get(noOfViews));
                                    _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
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
                                    _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
                                }

                            }
                        }

                    } else {
                        dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                        dataArrayList.get(noOfViews).setItemList("");
                        dataArrayList.get(noOfViews).setQuestion("");
                        healthParamData.add(dataArrayList.get(noOfViews));
                        _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (eachData.getInputType().equals("Multi-Select")) {
                try {
                    CheckBox tempChkBox = (CheckBox) allViewInstance.get(noOfViews);
                    StringBuilder stringBuilder = new StringBuilder();
                    String ds = "";
                    for (int i = 0; i < cbViewInstance.size(); i++) {
                        CheckBox tempChkBox1 = (CheckBox) cbViewInstance.get(i);
                        if (tempChkBox1.isChecked()) {
                            if (i == cbViewInstance.size()) {
                                stringBuilder.append(tempChkBox1.getText().toString());
                            } else {
                                stringBuilder.append(tempChkBox1.getText().toString() + ",");
                            }

                            if (!stringBuilder.toString().isEmpty()) {

                                if (stringBuilder.toString().endsWith(",")) {
                                    ds = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
                                } else {
                                    ds = stringBuilder.toString();
                                }
//                                dataArrayList.get(noOfViews).setAnswer(ds);
//                                dataArrayList.get(noOfViews).setItemList("");
//                                dataArrayList.get(noOfViews).setQuestion("");
//                                healthParamData.add(dataArrayList.get(noOfViews));
//                                _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
                            } else {
//                                dataArrayList.get(noOfViews).setAnswer("");
//                                dataArrayList.get(noOfViews).setItemList("");
//                                dataArrayList.get(noOfViews).setQuestion("");
//                                healthParamData.add(dataArrayList.get(noOfViews));
//                                _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
                            }
                        } else {
//                            if (stringBuilder.toString().isEmpty()) {
//                                dataArrayList.get(noOfViews).setAnswer("");
//                                dataArrayList.get(noOfViews).setItemList("");
//                                dataArrayList.get(noOfViews).setQuestion("");
//                                healthParamData.add(dataArrayList.get(noOfViews));
//                                _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
//                            }

                        }
                    }
                    dataArrayList.get(noOfViews).setAnswer(ds);
                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setId(dataArrayList.get(noOfViews).getId());

                    healthParamData.add(dataArrayList.get(noOfViews));
                    _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (eachData.getInputType().equals("Input")) {
                try {

                    TextView textView = (TextView) allViewInstance.get(noOfViews);
                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setAnswer(textView.getText().toString());

                    dataArrayList.get(noOfViews).setId(dataArrayList.get(noOfViews).getId());



                    healthParamData.add(dataArrayList.get(noOfViews));
                    _reecoachhealthParamData_static.add(dataArrayList.get(noOfViews));

                    Log.d("variant_name", textView.getText().toString() + "");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void setindicator(int count) {


        view1.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view2.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view3.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view4.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view5.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view6.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view7.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view8.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view9.setBackgroundColor(Color.parseColor("#EAEAEC"));
        view10.setBackgroundColor(Color.parseColor("#EAEAEC"));
//        view11.setBackgroundColor(Color.parseColor("#EAEAEC"));
//        view12.setBackgroundColor(Color.parseColor("#EAEAEC"));
//        view13.setBackgroundColor(Color.parseColor("#EAEAEC"));
//        view14.setBackgroundColor(Color.parseColor("#EAEAEC"));
//        view15.setBackgroundColor(Color.parseColor("#EAEAEC"));
//        view16.setBackgroundColor(Color.parseColor("#EAEAEC"));


        if (count==1){ view1.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==2){ view2.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==3){ view3.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==4){ view4.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==5){ view5.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==6){ view6.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==7){ view7.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==8){ view8.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==9){ view9.setBackgroundColor(Color.parseColor("#00D5BC")); }
        if (count==10){ view10.setBackgroundColor(Color.parseColor("#00D5BC")); }
//        if (count==11){ view11.setBackgroundColor(Color.parseColor("#00D5BC")); }
//        if (count==12){ view12.setBackgroundColor(Color.parseColor("#00D5BC")); }
//        if (count==13){ view13.setBackgroundColor(Color.parseColor("#00D5BC")); }
//        if (count==14){ view14.setBackgroundColor(Color.parseColor("#00D5BC")); }
//        if (count==15){ view15.setBackgroundColor(Color.parseColor("#00D5BC")); }
//        if (count==16){ view16.setBackgroundColor(Color.parseColor("#00D5BC")); }






    }

    private void initView() {

//        view11=findViewById(R.id.view11);
//        view12=findViewById(R.id.view12);
//        view13=findViewById(R.id.view13);
//        view14=findViewById(R.id.view14);
//        view15=findViewById(R.id.view15);
//        view16=findViewById(R.id.view16);
    }


    private void callHealthParameterPostApi()
    {

        utils.showProgressbar(getContext());


        ClsPostHealthData clsPostHealthData_new=new ClsPostHealthData();
        ArrayList<HealthParamData> arylst_Data=new ArrayList<>();





        Call<ClsgetPostData> call = healthParametersService.saveReecoachQuestionParameter(_reecoachhealthParamData_static);

        call.enqueue(new Callback<ClsgetPostData>()
        {
            @Override
            public void onResponse(Call<ClsgetPostData> call, Response<ClsgetPostData> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsgetPostData listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        _reecoachhealthParamData_static.clear();

                        if (listResponse.getData()!=null){
                            Toast.makeText(getContext(), "" + listResponse.getData(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();

                        }
                        if (getActivity().getIntent().getStringExtra("param")!=null){
                            if (getActivity().getIntent().getStringExtra("param").equalsIgnoreCase("From_Home")){
                                getActivity().finish();
                            }else {
//                                startActivity(new Intent(ProfileDynamicHealthparamActivity.this, NewHealthparameterGoobActivity.class));

//                                callHealthParametergetApi();
                                getActivity().finish();
                            }

                        }else {
//                            startActivity(new Intent(ProfileDynamicHealthparamActivity.this, NewHealthparameterGoobActivity.class));


                            getActivity().finish();

                        }



//                        }
                        /* finish();*/
                    }
                    else
                    {
//                        Toast.makeText(ProfileDynamicHealthparamActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "You have successfully Reeevaluated" , Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                    }
                }
                else
                {
                    Toast.makeText(getContext(), "You have successfully Reeevaluated", Toast.LENGTH_SHORT).show();

                    getActivity().finish();

//                    Toast.makeText(ProfileDynamicHealthparamActivity.this, "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }





            @Override
            public void onFailure(Call<ClsgetPostData> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
                Toast.makeText(getContext(), "You have successfully Reeevaluated", Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        });

    }







}
