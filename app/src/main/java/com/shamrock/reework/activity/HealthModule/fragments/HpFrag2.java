package com.shamrock.reework.activity.HealthModule.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shamrock.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HpFrag2 extends Fragment {

    public HpFrag2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hp_frag2, container, false);
        return view;
    }
    public static HpFrag2 newInstance(String text) {

        HpFrag2 f = new HpFrag2();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


}
