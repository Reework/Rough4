package com.shamrock.reework.activity.RescoreModule.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shamrock.R;

public class DemoActivity extends AppCompatActivity {

    LinearLayout parent_varialtion, rInvoiceMain,parent_varialti54on;

boolean flage=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        parent_varialtion = findViewById(R.id.parent_varialtion);
        if(flage==false) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View rowView = inflater.inflate(R.layout.row_additional_qn, null);

            int count = parent_varialtion.getChildCount();
            for (int i = 0; i < count; i++) {

                LinearLayout linearLayout = (LinearLayout) parent_varialtion;
                TextView addonItemName = linearLayout.findViewById(R.id.txt_add_qn);
                addonItemName.setText("item.getNAME()");

                flage=true;
            }
        }
        for (int i = 0; i < 4; i++) {

            if (flage == true) {


                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.row_additional_qn, null);
//
                TextView addonItemName = rowView.findViewById(R.id.txt_add_qn);
                addonItemName.setText("item.getNAME()");//
//
                parent_varialtion.addView(rowView, parent_varialtion.getChildCount());
////                LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//


            }
        }

//            foodType = (Spinner) linearLayout.findViewById(R.id.foodType);
//

//
//        }
//            parent_varialtion.addView(rowView, parent_varialtion.getChildCount());




//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View rowView = inflater.inflate(R.layout.row_additional_qn, null);
//
//            considartionasvariation = (CheckBox) rowView.findViewById(R.id.considartionasvariation);
//
//
//            parent_varialtion.addView(rowView, parent_varialtion.getChildCount());
////                LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//
//        }





//        AddNewRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count1 = parent_varialtion.getChildCount();
//                for (int i = 0; i < count1; i++) {
//                    final LinearLayout linearLayout = (LinearLayout) parent_varialtion.getChildAt(i);
//                    addonItemName = (EditText) linearLayout.findViewById(R.id.addonItemName);
//                    foodType = (Spinner) linearLayout.findViewById(R.id.foodType);
//
//                    foodType1 = new ArrayList<String>();
//                    foodType1.add("Select Food Type");
//                    foodType1.add("Veg");
//                    foodType1.add("Non-Veg");
////                    Log.d("result5252", foodType1 + "");
//                    foodTypeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row_spinner_text, foodType1);
//                    foodTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item2);
//                    if (isEdit == false) {
//                        foodType.setAdapter(foodTypeAdapter);}
////                    foodType.setSelection(0);
//                    itemPrice = (EditText) linearLayout.findViewById(R.id.itemPrice);
//                    considartionasvariation = (CheckBox) linearLayout.findViewById(R.id.considartionasvariation);
//                    ImageView delete = (ImageView) linearLayout.findViewById(R.id.delete);
//                    String sa = itemPrice.getText().toString();
////                    Log.d("result525252", sa + "");
//                    considartionasvariation = (CheckBox) linearLayout.findViewById(R.id.considartionasvariation);
//
//
//                }
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View rowView = inflater.inflate(R.layout.addon_row, null);
//                foodType = (Spinner) rowView.findViewById(R.id.foodType);
//
//                foodType1 = new ArrayList<String>();
//                foodType1.add("Select Food Type");
//                foodType1.add("Veg");
//                foodType1.add("Non-Veg");
//                foodTypeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row_spinner_text, foodType1);
//                foodTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item2);
////                if (isEdit == false) {
//                foodType.setAdapter(foodTypeAdapter);
////                }
//
////                foodType.setSelection(0);
//                considartionasvariation = (CheckBox) rowView.findViewById(R.id.considartionasvariation);
//
//                parent_varialtion.addView(rowView, parent_varialtion.getChildCount());
////                LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                ImageView delete = (ImageView) rowView.findViewById(R.id.delete);
//
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        parent_varialtion.removeView(rowView);
//                    }
//                });
//
//            }
//        });


//        AddOn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                JSONArray addonitems = new JSONArray();
//                int count = parent_varialtion.getChildCount();
//                for (int i = 0; i < count; i++) {
//                    LinearLayout linearLayout = (LinearLayout) parent_varialtion.getChildAt(i);
//                    addonItemName = (EditText) linearLayout.findViewById(R.id.addonItemName);
//
//                    foodType = (Spinner) linearLayout.findViewById(R.id.foodType);
//
//                    itemPrice = (EditText) linearLayout.findViewById(R.id.itemPrice);
//
//                    considartionasvariation = (CheckBox) linearLayout.findViewById(R.id.considartionasvariation);
//
//                    String result = addonItemName.getText().toString();
////                        Log.d("result5225452", result + "");
//                    validation();
//
//
//                    JSONObject PriceList1 = new JSONObject();
//
//                    try {
//                        PriceList1.put("name", addonItemName.getText().toString());
//                        PriceList1.put("rate", itemPrice.getText().toString());
//                        PriceList1.put("considerAsVariation", considartionasvariation.isChecked() ? "1" : "0");
//                        PriceList1.put("foodType", foodType.getSelectedItem().toString());
//                        addonitems.put(PriceList1);
//                    } catch (JSONException e) {
//
//                    }
////                        Log.d("result52254325452", PriceList1 + "");
//
//                }
//
//
//                JSONObject lJson = new JSONObject();
//                try {
//                    if (isEdit == true) {
//                        lJson.put("agId", pid);
//                    }
//                    lJson.put("name", add0nName.getText().toString());
//                    lJson.put("onlineDisplayName", displayAddonName.getText().toString());
//                    lJson.put("mid", mid);
//                    lJson.put("smid", smid);
//                    lJson.put("makerid", String.valueOf(buid));
//                    lJson.put("source", "mob");
//                    lJson.put("addonitems", addonitems);
//                    if (isValidate == true) {
//
//                        isValidate = false;
//
//                    } else {
//                        if (isEdit == true) {
//                            updateitem(lJson);
//                        } else {
//                            addonitem(lJson);
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });





//    private void setAddOnData(AddOnActivity item) {
//
//
//        if(flage==false) {
//            int count1 = parent_varialtion.getChildCount();
//            for (int i = 0; i < count1; i++) {
//                final LinearLayout linearLayout = (LinearLayout) parent_varialtion;
//                addonItemName = (EditText) linearLayout.findViewById(R.id.addonItemName);
//
//                flage=true;
//            }
//        }
//        else if(flage==true) {
////
////            considartionasvariation.setActivated(item.getCONSIDER_AS_VARIATION());
//
//            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View rowView = inflater.inflate(R.layout.addon_row, null);
//            addonItemName = (EditText) rowView.findViewById(R.id.addonItemName);
//            addonItemName.setText(item.getNAME());
//            foodType = (Spinner) rowView.findViewById(R.id.foodType);
//            foodType1 = new ArrayList<String>();
//            foodType1.add("Veg");
//            foodType1.add("Non-Veg");
//            foodTypeAdapter = new ArrayAdapter(getApplicationContext(), R.layout.row_spinner_text, foodType1);
//            foodTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item2);
//            foodType.setAdapter(foodTypeAdapter);
////            foodType.setSelection(0);
//            if (item.getFOOD_TYPE().equals("Veg")) {
//                foodType.setSelection(0);
//            } else if (item.getFOOD_TYPE().equals("Non-Veg")) {
//                foodType.setSelection(1);
//            }
//            considartionasvariation = (CheckBox) rowView.findViewById(R.id.considartionasvariation);
//            if (item.getCONSIDER_AS_VARIATION() == 0) {
//                considartionasvariation.setChecked(false);
//            } else if (item.getCONSIDER_AS_VARIATION() == 1) {
//                considartionasvariation.setChecked(true);
//            }
//            itemPrice = (EditText) rowView.findViewById(R.id.itemPrice);
//            itemPrice.setText("" + item.getRATE());
//            parent_varialtion.addView(rowView, parent_varialtion.getChildCount());
////                LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            ImageView delete = (ImageView) rowView.findViewById(R.id.delete);
//
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    parent_varialtion.removeView(rowView);
//                }
//            });
//        }
//
////
//
////        }
//
//
//
////        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        final View rowView = inflater.inflate(R.layout.addon_row, null);
//
////        int count = parent_varialtion.getChildCount();
////        for (int i = 0; i < count; i++) {
////
////            LinearLayout linearLayout = (LinearLayout) parent_varialtion.getChildAt(i);
////            addonItemName = (EditText) linearLayout.findViewById(R.id.addonItemName);
////            addonItemName.setText(item.getNAME());
////            foodType = (Spinner) linearLayout.findViewById(R.id.foodType);
////            if(item.getFOOD_TYPE().equals("Veg")){
////            foodType.setSelection(0);
////            }else if(item.getFOOD_TYPE().equals("Non-Veg")){
////                foodType.setSelection(1);
////            }
////            itemPrice = (EditText) linearLayout.findViewById(R.id.itemPrice);
////            itemPrice.setText("" +item.getRATE());
//////            considartionasvariation = (CheckBox) linearLayout.findViewById(R.id.considartionasvariation);
//////
////            ImageView delete = (ImageView) rowView.findViewById(R.id.delete);
////            delete.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    parent_varialtion.removeView(rowView);
////                }
////            });
//////
//////        }
////        parent_varialtion.addView(rowView, parent_varialtion.getChildCount());
//
//    }


    }

}
