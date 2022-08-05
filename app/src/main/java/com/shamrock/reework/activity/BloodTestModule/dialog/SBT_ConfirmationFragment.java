package com.shamrock.reework.activity.BloodTestModule.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.activity.LoginModule.Data;
import com.shamrock.reework.activity.LoginModule.LoginService;
import com.shamrock.reework.activity.LoginModule.UserStatus;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SBT_ConfirmationFragment extends DialogFragment implements View.OnClickListener
{

    public interface SBT_DialogListener
    {
        void onSBT_Dialog();
    }

    Context context;
    TextView textMsg;
    Button btnOk;
    Utils utils;
    SessionManager sessionManager;


    LoginService loginService;
int userId;
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
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.dialog_fragment_sbt, container, false);
        textMsg = view.findViewById(R.id.labelMessageSBT);
        btnOk = view.findViewById(R.id.buttonSBT_Ok);
        utils = new Utils();
        sessionManager = new SessionManager(context);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        loginService = Client.getClient().create(LoginService.class);

        getDialog().setCancelable(false);

        btnOk.setOnClickListener(this);
        return view;
    }

//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
//            // Return input text to activity
//            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
//            activity.onFinishEditDialog(textOTP.getText().toString());
//            this.dismiss();
//            return true;
//        }
//        return false;
//    }

     @Override
    public void onClick(View view)
     {
        switch (view.getId())
        {
            case R.id.buttonSBT_Ok:
                SBT_DialogListener  activity = (SBT_DialogListener ) getActivity();
                activity.onSBT_Dialog();
                this.dismiss();

//                callUserStatusApi();








                break;
            default:
        }
    }



    private void callUserStatusApi() {
        Call<UserStatus> call = loginService.getUserStatusHistroy(userId);
        Log.d("req",call.request().toString());
        call.enqueue(new Callback<UserStatus>()
        {
            @Override
            public void onResponse(Call<UserStatus> call, Response<UserStatus> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    UserStatus listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("0"))
                    {


                        Data data=listResponse.getData();

                        if (data!=null){

                            sessionManager.setStringValue("IsAllowUser",data.getIsAppliedBloodTest());
                            sessionManager.setStringValue("KeyAssingDailyTassk",data.getIsScheduledTask());
                            sessionManager.setStringValue("KeyAssingReecoach",data.getIsReecoachAssigned());





//                            if (data.getIsReecoachAssigned().equalsIgnoreCase("true")){
//                                SBT_DialogListener  activity = (SBT_DialogListener ) getActivity();
//                                activity.onSBT_Dialog();
//                                dismiss();
//                            }else {
//
//                                Toast.makeText(context, "Recoach is not assigned.", Toast.LENGTH_SHORT).show();
//
//                            }














                        }

                    }
                }
                //                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UserStatus> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });

    }



}
