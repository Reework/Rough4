package com.shamrock.reework.activity.HealthModule.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shamrock.R;

public class HealthParameterDialogFragment extends DialogFragment implements View.OnClickListener
{

    public interface Health_DialogListener
    {
        void onHealth_Dialog();
    }

    Context context;
    TextView textMsg;
    Button btnOk;

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonHealth_Ok:
//                Health_DialogListener  activity = (Health_DialogListener ) getActivity();
//                activity.onHealth_Dialog();
                this.dismiss();
                break;
            default:
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_fragment_health, container, false);
        textMsg = view.findViewById(R.id.labelMessageHealth);
        btnOk = view.findViewById(R.id.buttonHealth_Ok);
        getDialog().setCancelable(true);
//        int[] args = getArguments().getIntArray("args");
        btnOk.setOnClickListener(this);
        return view;
    }


    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


}
