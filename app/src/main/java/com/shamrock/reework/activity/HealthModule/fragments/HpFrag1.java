package com.shamrock.reework.activity.HealthModule.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.shamrock.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HpFrag1 extends Fragment {
    List<View> allViewInstance = new ArrayList<View>();
    String opttype = "spinner";

    public HpFrag1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hp_frag1, container, false);
        LinearLayout viewProductLayout = (LinearLayout) view.findViewById(R.id.customOptionLL);

        TextView customOptionsName = new TextView(getContext());
        customOptionsName.setTextSize(18);
        customOptionsName.setPadding(0, 15, 0, 15);
        customOptionsName.setText("fisrt ");
        viewProductLayout.addView(customOptionsName);

        if (opttype.equals("spinner")) {

//            final JSONArray dropDownJSONOpt = eachData.getJSONArray("variants");
            ArrayList<String> SpinnerOptions = new ArrayList<String>();
            for (int j = 0; j < 2; j++) {
                String optionString = "test";
                SpinnerOptions.add(optionString);
            }

            ArrayAdapter<String> spinnerArrayAdapter = null;
            spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_row, SpinnerOptions);
            Spinner spinner = new Spinner(getContext());
            allViewInstance.add(spinner);
            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setSelection(0, false);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
//                        String variant_name = dropDownJSONOpt.getJSONObject(position).getString("variant_name");
//                        Toast.makeText(getApplicationContext(), variant_name + "", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });
            viewProductLayout.addView(spinner);


        }

        return view;

    }

    public static HpFrag1 newInstance(String text) {

        HpFrag1 f = new HpFrag1();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
