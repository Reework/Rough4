package com.shamrock.reework.activity.LoginModule;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class OtpDialogFragment extends DialogFragment implements View.OnClickListener
{

    Context context;
    EditText editTextOTP;
    TextView textView_Timer;
    Button btnSubmit, btnResend;
    ImageView imageViewCancel;
    CountDownTimer cTimer = null;

    public void setOtpText(String extractedOTP)
    {
        editTextOTP.setText(extractedOTP);
    }

    public void visibleResend() {
//        textView_Timer.setVisibility(View.GONE);
//        btnResend.setVisibility(View.VISIBLE);
//        btnSubmit.setEnabled(false);
//        editTextOTP.setText("");
//        editTextOTP.setEnabled(false);
//        cancelTimer();
    }

    public interface EditNameDialogListener
    {
        void onSubmitOTP(String inputText);

        void onResendOtp();
    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        setCancelable(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.dialog_fragment_otp, container, false);

        editTextOTP = view.findViewById(R.id.edtText_OTP);
        textView_Timer = view.findViewById(R.id.textView_Otp_clock);
        btnSubmit = view.findViewById(R.id.buttonOtpSubmit);
        btnResend = view.findViewById(R.id.buttonOtpResend);
        imageViewCancel = view.findViewById(R.id.buttonOtpCancel);
        getDialog().setCancelable(false);

        //int[] args = getArguments().getIntArray("args");

        btnSubmit.setOnClickListener(this);
        btnResend.setOnClickListener(this);
        imageViewCancel.setOnClickListener(this);
        startTimer();
        return view;
    }

    void startTimer()
    {
        cTimer = new CountDownTimer(120000, 1000)
        {
            public void onTick(long millis)
            {
                textView_Timer.setVisibility(View.VISIBLE);
                textView_Timer.setText("" + millis / 1000+" Second Left");
            }

            public void onFinish()
            {
                textView_Timer.setVisibility(View.GONE);
                btnResend.setVisibility(View.VISIBLE);
                btnSubmit.setEnabled(false);
                editTextOTP.setText("");
                editTextOTP.setEnabled(false);
            }
        };
        cTimer.start();
    }

    //cancel timer
    public void cancelTimer()
    {
        if (cTimer != null)
            cTimer.cancel();
    }

    @Override
    public void onClick(View view)
    {
        EditNameDialogListener activity = (EditNameDialogListener) getActivity();

        switch (view.getId())
        {
            case R.id.buttonOtpSubmit:
                //cancelTimer();

                try {
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(btnSubmit.getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                String otp = editTextOTP.getText().toString().trim();

                if (!otp.isEmpty() && otp.length() == 6)
                {

                    try {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    activity.onSubmitOTP(editTextOTP.getText().toString());
                }
                else
                {
                    Toast.makeText(context, "Enter valid OTP", Toast.LENGTH_LONG).show();
                }
                // this.dismiss();
                break;

            case R.id.buttonOtpResend:

                try {
                    activity.onResendOtp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.dismiss();
                break;

            case R.id.buttonOtpCancel:

                this.dismiss();
                break;

            default:
        }
    }


//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
//            // Return input text to activity
//            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
//            activity.onFinishEditDialog(editTextOTP.getText().toString());
//            this.dismiss();
//            return true;
//        }
//        return false;
//    }


}
