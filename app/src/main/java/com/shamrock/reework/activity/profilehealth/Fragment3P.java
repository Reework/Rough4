package com.shamrock.reework.activity.profilehealth;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
public class Fragment3P extends Fragment {


    public Fragment3P() {
        // Required empty public constructor
    }
    private ArrayList<HealthParamData> dataArrayList ;
    List<View> allViewInstance = new ArrayList<View>();
    List<View> cbViewInstance = new ArrayList<View>();



    List<View> allEdittextInstance=new ArrayList<>();
    List<View> allInputLAyouttInstance=new ArrayList<>();
    int editID=5;
    int inputID=10;
    ;int txtInput=6;

    String emotionalsuppering="";
    @SuppressLint("ValidFragment")
    public Fragment3P(ArrayList<HealthParamData> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment3, container, false);
        LinearLayout viewProductLayout = (LinearLayout) view.findViewById(R.id.customOptionLL);



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
                allViewInstance.add(spinner);
                spinner.setAdapter(spinnerArrayAdapter);
               spinner.setEnabled(false);

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
                    til.setEnabled(false);

                    til.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+inputID);
                    til.setPadding(0,50,0,0);
                    til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                    EditText et = new EditText(getActivity());
                    et.setEnabled(false);
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+editID);
                    allEdittextInstance.add(et);
                    allInputLAyouttInstance.add(til);
                    if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                        et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());
                    }
                    til.addView(et);

                    viewProductLayout.addView(til);
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


                if (dataArrayList.get(noOfCustomOpt).getQuestionId()==17){
                    emotionalsuppering=dataArrayList.get(noOfCustomOpt).getAnswer();
                }




                final RadioGroup rg = new RadioGroup(getActivity()); //create the RadioGroup
                // rg.setOrientation(LinearLayout.HORIZONTAL);
                rg.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                rg.setEnabled(false);
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

                            String variant_name="";
                            try {
                                View radioButton = group.findViewById(checkedId);
                                if (radioButton != null) {
                                    variant_name = radioButton.getTag().toString();

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            // Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();






                            if (rg.getId()==15){

                                if (variant_name.equalsIgnoreCase("Yes, please specify the name in remarks")||variant_name.equalsIgnoreCase("Any Other. please specify in remarks")){

                                    for (int i = 0; i <allEdittextInstance.size() ; i++) {

                                        if (allEdittextInstance.get(i).getId()==(15+editID)){
                                            allEdittextInstance.get(i).setVisibility(View.VISIBLE);                                     }
                                    }


                                    for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {

                                        if (allInputLAyouttInstance.get(i).getId()==(15+inputID)){
                                            allInputLAyouttInstance.get(i).setVisibility(View.VISIBLE);                                     }
                                    }



                                }else {
                                    for (int i = 0; i <allEdittextInstance.size() ; i++) {

                                        if (allEdittextInstance.get(i).getId()==(15+editID)){
                                            allEdittextInstance.get(i).setVisibility(View.GONE);
                                        }
                                    }
                                    for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {

                                        if (allInputLAyouttInstance.get(i).getId()==(15+inputID)){
                                            allInputLAyouttInstance.get(i).setVisibility(View.GONE);
                                        }
                                    }
                                }



                            }
//                            if (rg.getId()==17){
//
//
//                                if (variant_name.equalsIgnoreCase("Yes, please specify the name in remarks")||variant_name.equalsIgnoreCase("Any other, please specify in remarks")){
//
//                                    emotionalsuppering=variant_name;
//
//                                    for (int i = 0; i <allEdittextInstance.size() ; i++) {
//
//                                        if (allEdittextInstance.get(i).getId()==(17+editID)){
//                                            allEdittextInstance.get(i).setVisibility(View.VISIBLE);
//                                            break;
//                                        }
//                                    }
//
//                                    for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {
//
//                                        if (allInputLAyouttInstance.get(i).getId()==(17+inputID)){
//                                            allInputLAyouttInstance.get(i).setVisibility(View.VISIBLE);
//                                            break;
//                                        }
//                                    }
//
//                                }else {
//
//                                    emotionalsuppering=variant_name;
//                                    for (int i = 0; i <allEdittextInstance.size() ; i++) {
//
//                                        if (allEdittextInstance.get(i).getId()==(17+editID)){
//                                            allEdittextInstance.get(i).setVisibility(View.GONE);
//                                            break;
//                                        }
//                                    }
//
//                                    for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {
//
//                                        if (allInputLAyouttInstance.get(i).getId()==(17+inputID)){
//                                            allInputLAyouttInstance.get(i).setVisibility(View.GONE);
//                                            break;
//                                        }
//                                    }
//                                }
//
//
//
//                            }
//
//                            if (rg.getId()==16){
//                                if (variant_name.equalsIgnoreCase("Yes, please specify the name in remarks")||variant_name.equalsIgnoreCase("Any Other. please specify in remarks")){
//
//                                    for (int i = 0; i <allEdittextInstance.size() ; i++) {
//
//                                        if (allEdittextInstance.get(i).getId()==(16+editID)){
//                                            allEdittextInstance.get(i).setVisibility(View.VISIBLE);                                     }
//                                    }
//
//                                    for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {
//
//                                        if (allInputLAyouttInstance.get(i).getId()==(16+inputID)){
//                                            allInputLAyouttInstance.get(i).setVisibility(View.VISIBLE);                                     }
//                                    }
//
//                                }else {
//                                    for (int i = 0; i <allEdittextInstance.size() ; i++) {
//
//                                        if (allEdittextInstance.get(i).getId()==(16+editID)){
//                                            allEdittextInstance.get(i).setVisibility(View.GONE);
//                                        }
//                                    }
//                                    for (int i = 0; i <allInputLAyouttInstance.size() ; i++) {
//
//                                        if (allInputLAyouttInstance.get(i).getId()==(16+inputID)){
//                                            allInputLAyouttInstance.get(i).setVisibility(View.GONE);
//                                        }
//                                    }
//                                }
//
//
//
//                            }



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

                                        if (allInputLAyouttInstance.get(i).getId()==(17+inputID)){
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

                                        if (allInputLAyouttInstance.get(i).getId()==(17+inputID)){
                                            allInputLAyouttInstance.get(i).setVisibility(View.GONE);
                                            break;
                                        }
                                    }
                                }



                            }



                            if (rg.getId()==8){
                                if (variant_name.equalsIgnoreCase("Yes")){

                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==9){
                                            allViewInstance.get(i).setEnabled(true);
                                            break;
                                        }
                                    }

                                }else {
                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==9){
                                            allViewInstance.get(i).setEnabled(false);
                                            break;
                                        }
                                    }
                                }



                            }

                            if (rg.getId()==10){
                                if (variant_name.equalsIgnoreCase("Yes")){

                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==11){

                                            allViewInstance.get(i).setEnabled(true);
                                            break;
                                        }
                                    }

                                }else {
                                    for (int i = 0; i <allViewInstance.size() ; i++) {

                                        if (allViewInstance.get(i).getId()==11){
                                            allViewInstance.get(i).setEnabled(false);
                                            break;
                                        }
                                    }
                                }



                            }
                        }
                    });

                }


                viewProductLayout.addView(rg, params);




                if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                    TextInputLayout til = new TextInputLayout(getActivity());
                    til.setEnabled(false);

                    til.setPadding(0,50,0,0);
                    til.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+inputID);
                    allInputLAyouttInstance.add(til);

                    til.setHint("Remarks");
                    EditText et = new EditText(getActivity());
                    et.setEnabled(false);
                    et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+editID);
                    allEdittextInstance.add(et);
//                    dataArrayList.get(noOfCustomOpt).setRemark("Testing");//sunit
                    if (dataArrayList.get(noOfCustomOpt).getRemark()!=null&&!dataArrayList.get(noOfCustomOpt).getRemark().isEmpty()){
                        et.setText(dataArrayList.get(noOfCustomOpt).getRemark());
                    }
                    til.addView(et);
                    viewProductLayout.addView(til);
                }

//               dataArrayList.get(noOfCustomOpt).setAnswer("Yes");


               if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                   for (int i = 0; i < arrRadioButton.length; i++) {
                       if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase(arrRadioButton[i].trim().toString())){

                           int qnID=dataArrayList.get(noOfCustomOpt).getQuestionId();

                           if ((qnID==15)||(qnID==16)||(qnID==17)){
                               if (dataArrayList.get(noOfCustomOpt).getAnswer()==null){
                                   dataArrayList.get(noOfCustomOpt).setAnswer("No");
                               }

                               if (dataArrayList.get(noOfCustomOpt).getAnswer().equalsIgnoreCase("No")){
                                   for (int j = 0; j <allEdittextInstance.size() ; j++) {
                                       if (allEdittextInstance.get(j).getId()==(qnID+editID)){
                                           allEdittextInstance.get(j).setVisibility(View.GONE);
                                       }


                                   }
                               }else {
                                   for (int j = 0; j <allEdittextInstance.size() ; j++) {
                                       if (allEdittextInstance.get(j).getId()==(qnID+editID)){
                                           allEdittextInstance.get(j).setVisibility(View.VISIBLE);
                                       }


                                   }
                               }


                           }

                           rg.check(rg.getChildAt(i).getId());




                       }

                   }
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
               if (dataArrayList.get(noOfCustomOpt).getIsRemarkShow().equalsIgnoreCase("true")){
                   til = new TextInputLayout(getActivity());
                   til.setEnabled(false);

                   til.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+txtInput);
                   allInputLAyouttInstance.add(til);
                   til.setPadding(0,50,0,0);
                   til.setHint("Remarks");
                   EditText et = new EditText(getActivity());

                   et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId()+editID);
                   allEdittextInstance.add(et);
                   if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                       et.setText(dataArrayList.get(noOfCustomOpt).getRemark());
                   }
                   til.addView(et);

                   til.setVisibility(View.GONE);

               }


               for (int j = 0; j < arrCheckbox.length; j++) {


                   final CheckBox chk = new CheckBox(getActivity());
                   chk.setEnabled(false);

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
                   final int finalNoOfCustomOpt = noOfCustomOpt;
                   chk.setOnClickListener(new View.OnClickListener() {

                       @SuppressLint("ResourceType")
                       @Override
                       public void onClick(View v) {
                           if(chk.isChecked()){

                               String variant_name = v.getTag().toString();




                               if (variant_name.toString().equalsIgnoreCase("Any Other. please specify in remarks")){
                                   for (int f = 0; f <allInputLAyouttInstance.size() ; f++) {
                                       if (allInputLAyouttInstance.get(f).getId()==(dataArrayList.get(finalNoOfCustomOpt).getQuestionId()+txtInput)){
                                           allInputLAyouttInstance.get(f).setVisibility(View.VISIBLE);
                                           break;
                                       }
                                   }

                                   for (int i = 0; i <allEdittextInstance.size() ; i++) {
                                       if (allEdittextInstance.get(i).getId()==(dataArrayList.get(finalNoOfCustomOpt).getQuestionId()+editID)){
                                           allEdittextInstance.get(i).setVisibility(View.VISIBLE);
                                           break;
                                       }
                                   }




                               }else {

                               }





                           }else{

                               String variant_name = v.getTag().toString();
                               if (variant_name.toString().equalsIgnoreCase("Any Other. please specify in remarks")){
                                   for (int i = 0; i <allEdittextInstance.size() ; i++) {
                                       if (allEdittextInstance.get(i).getId()==(dataArrayList.get(finalNoOfCustomOpt).getQuestionId()+editID)){
                                           allEdittextInstance.get(i).setVisibility(View.GONE);
                                           break;
                                       }
                                   }

                                   for (int f = 0; f <allInputLAyouttInstance.size() ; f++) {
                                       if (allInputLAyouttInstance.get(f).getId()==(dataArrayList.get(finalNoOfCustomOpt).getQuestionId()+txtInput)){
                                           allInputLAyouttInstance.get(f).setVisibility(View.GONE);
                                           break;
                                       }
                                   }

                               }else {

                               }

                           }

                       }
                   });
                   chk.setText(optionString);
                   if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()&&dataArrayList.get(noOfCustomOpt).getAnswer().contains("$")){

                       String chkarr[]=dataArrayList.get(noOfCustomOpt).getAnswer().split("\\$");

                       for (int i = 0; i <chkarr.length ; i++) {
                           if (arrCheckbox[j].toString().equalsIgnoreCase(chkarr[i].toString())){
                               chk.setChecked(true);
                               if (chkarr[i].toString().equalsIgnoreCase("Any Other. please specify in remarks")){

                                   for (int k = 0; k <allEdittextInstance.size() ; k++) {
                                       if ((dataArrayList.get(noOfCustomOpt).getQuestionId()+editID)==(allEdittextInstance.get(k).getId())){
                                           allEdittextInstance.get(k).setVisibility(View.VISIBLE);
                                           break;
                                       }




                                   }
                                   for (int f = 0; f <allInputLAyouttInstance.size() ; f++) {
                                       if (allInputLAyouttInstance.get(f).getId()==(dataArrayList.get(noOfCustomOpt).getQuestionId()+txtInput)){
                                           allInputLAyouttInstance.get(f).setVisibility(View.VISIBLE);
                                           break;
                                       }
                                   }

                               }else {
                                   for (int k = 0; k <allEdittextInstance.size() ; k++) {
                                       if ((dataArrayList.get(noOfCustomOpt).getQuestionId()+editID)==(allEdittextInstance.get(k).getId())){
                                           allEdittextInstance.get(k).setVisibility(View.GONE);
                                           break;
                                       }

                                   }

                                   for (int f = 0; f <allInputLAyouttInstance.size() ; f++) {
                                       if (allInputLAyouttInstance.get(f).getId()==(dataArrayList.get(noOfCustomOpt).getQuestionId()+txtInput)){
                                           allInputLAyouttInstance.get(f).setVisibility(View.GONE);
                                           break;
                                       }
                                   }
                               }
                           }


                       }







                   }else if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()&&!dataArrayList.get(noOfCustomOpt).getAnswer().contains("$"))
                   {

                       if (dataArrayList.get(noOfCustomOpt).getAnswer().toString().equalsIgnoreCase(arrCheckbox[j].toString())){
                           chk.setChecked(true);

                       }else {
                           chk.setChecked(false);
                       }
                   }

                   if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){
                       for (int k = 0; k <allEdittextInstance.size() ; k++) {
                           if ((dataArrayList.get(noOfCustomOpt).getQuestionId()+editID)==(allEdittextInstance.get(k).getId())){
                               allEdittextInstance.get(k).setVisibility(View.GONE);
                               break;
                           }

                       }

                       for (int f = 0; f <allInputLAyouttInstance.size() ; f++) {
                           if (allInputLAyouttInstance.get(f).getId()==(dataArrayList.get(noOfCustomOpt).getQuestionId()+txtInput)){
                               allInputLAyouttInstance.get(f).setVisibility(View.GONE);
                               break;
                           }
                       }


                   }


                   viewProductLayout.addView(chk, params);
               }








               if (til!=null){
                   viewProductLayout.addView(til);

               }






           }else if (dataArrayList.get(noOfCustomOpt).getInputType().equalsIgnoreCase(DynamicHealthparamActivity.INPUT_BOX)){


               TextInputLayout til = new TextInputLayout(getActivity());
                til.setPadding(0,50,0,0);
               til.setEnabled(false);


               til.setHint(dataArrayList.get(noOfCustomOpt).getQuestion());
                EditText et = new EditText(getActivity());
                et.setEnabled(false);
                et.setId(dataArrayList.get(noOfCustomOpt).getQuestionId());
                if (dataArrayList.get(noOfCustomOpt).getAnswer()!=null&&!dataArrayList.get(noOfCustomOpt).getAnswer().isEmpty()){

                    et.setText(dataArrayList.get(noOfCustomOpt).getAnswer());

                }
                til.addView(et);
                allViewInstance.add(et);
                viewProductLayout.addView(til);
            }


        }


        return view;    }
    @SuppressLint("ResourceType")
    public boolean getThirdPageData(){


        boolean isValidThirdPAge=true;





        try{

            for (int i = 0; i < allViewInstance.size(); i++) {
                if (allViewInstance.get(i).getId() == 15) {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(i);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) getActivity().findViewById(selectedId);

                    if (radioButton.getText().toString().trim().equalsIgnoreCase("Yes, please specify the name in remarks")) {
                        for (int j = 0; j < allEdittextInstance.size(); j++) {
                            if (allEdittextInstance.get(j).getId() ==allViewInstance.get(i).getId()+editID ) {
                                EditText editText = (EditText) allEdittextInstance.get(j);
                                if (editText.getText().toString().trim().isEmpty()) {

                                    Toast.makeText(getActivity(), "please specify food allergy in remarks", Toast.LENGTH_SHORT).show();

                                    return false;

                                }}}}}

                if (allViewInstance.get(i).getId() == 16) {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(i);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) getActivity().findViewById(selectedId);

                    if (radioButton.getText().toString().trim().equalsIgnoreCase("Yes, please specify the name in remarks")) {
                        for (int j = 0; j < allEdittextInstance.size(); j++) {
                            if (allEdittextInstance.get(j).getId() ==allViewInstance.get(i).getId()+editID ) {
                                EditText editText = (EditText) allEdittextInstance.get(j);
                                if (editText.getText().toString().trim().isEmpty()) {

                                    Toast.makeText(getActivity(), "please specify health supplement in remarks", Toast.LENGTH_SHORT).show();

                                    return false;

                                }}}}}

                if (allViewInstance.get(i).getId() == 17) {


                    if (emotionalsuppering.equalsIgnoreCase("Yes, please specify the name in remarks")) {
                        for (int j = 0; j < allEdittextInstance.size(); j++) {
                            if (allEdittextInstance.get(j).getId() ==allViewInstance.get(i).getId()+editID ) {
                                EditText editText = (EditText) allEdittextInstance.get(j);
                                if (editText.getText().toString().trim().isEmpty()) {

                                    Toast.makeText(getActivity(), "please specify emotional suffering in remarks", Toast.LENGTH_SHORT).show();

                                    return false;

                                }}}}}




            }
        }catch (Exception e){
            e.printStackTrace();
        }







        ArrayList<HealthParamData> healthParamData = new ArrayList<>();
        for (int noOfViews = 0; noOfViews < dataArrayList.size(); noOfViews++) {
            HealthParamData eachData = dataArrayList.get(noOfViews);





            if (eachData.getInputType().equals(DynamicHealthparamActivity.DROPDOWN)) {
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

                        if (dataArrayList.get(noOfViews).getQuestionId() == 10) {
                        }

                        dataArrayList.get(noOfViews).setItemList("");
                        dataArrayList.get(noOfViews).setQuestion("");
                        dataArrayList.get(noOfViews).setAnswer(variant_name);
                        healthParamData.add(dataArrayList.get(noOfViews));
                        healthParamData_static.add(dataArrayList.get(noOfViews));

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (eachData.getInputType().equals(DynamicHealthparamActivity.RADIO_BUTTON)) {

                try {
                    RadioGroup radioGroup = (RadioGroup) allViewInstance.get(noOfViews);

                    RadioButton selectedRadioBtn = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());




                    for (int i = 0; i <allEdittextInstance.size() ; i++) {
                        if (allEdittextInstance.get(i).getId()==(eachData.getQuestionId()+editID)){
                            EditText editText= (EditText) allEdittextInstance.get(i);
                            dataArrayList.get(noOfViews).setItemList("");
                            dataArrayList.get(noOfViews).setQuestion("");
                            dataArrayList.get(noOfViews).setRemark(editText.getText().toString());
                        }
                    }


                    dataArrayList.get(noOfViews).setItemList("");
                    dataArrayList.get(noOfViews).setQuestion("");
                    dataArrayList.get(noOfViews).setAnswer(selectedRadioBtn.getTag().toString() + "");
                    healthParamData.add(dataArrayList.get(noOfViews));
                    healthParamData_static.add(dataArrayList.get(noOfViews));
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


                            if (tempChkBox1.getText().toString().equalsIgnoreCase("Any Other. please specify in remarks")){

                                for (int f = 0; f < allEdittextInstance.size(); f++) {
                                    if (allEdittextInstance.get(f).getId() == (eachData.getQuestionId() + editID)) {
                                        EditText editText = (EditText) allEdittextInstance.get(f);
                                        if (editText.getText().toString().trim().isEmpty()){
                                            Toast.makeText(getActivity(), "Please specify any other medical condition in remark", Toast.LENGTH_SHORT).show();
                                            return false;

                                        }
                                    }
                                }

                            }




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

                }catch (Exception e){
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
                        // optionsObj.put(eachData.getString("option_name"), textView.getText().toString());
                    }


                    Log.d("variant_name", textView.getText().toString() + "");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return isValidThirdPAge;
    }
}
