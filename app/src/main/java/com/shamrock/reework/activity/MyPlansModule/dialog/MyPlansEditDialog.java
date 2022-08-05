package com.shamrock.reework.activity.MyPlansModule.dialog;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.api.response.MyPlanResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyPlansEditDialog extends DialogFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener
{
    private Utils utils;
    private String errorMsg;
    private SessionManager sessionManager;
    Context context;

    TimePickerDialog timepickerdialog;
    String lsTime = "", lsPlan = "", lsQty = "";
    int liCoachId = 0, liUserId = 0;
    ArrayList<MyPlanResponse.MyPlanData> list;
    int position = -1;

    Button btnSave;
    TextView tvTime;
    EditText etPlan, etQty;
    ImageView ivClose;

    public interface PlanEditInterface
    {
        public void onEdit(ArrayList<MyPlanResponse.MyPlanData> list);
    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        utils = new Utils();
        sessionManager = new SessionManager(context);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, true);

        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            list = (ArrayList<MyPlanResponse.MyPlanData>) bundle.getSerializable("LIST");
            position = bundle.getInt("POSITION");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_plan, container, false);

        tvTime = view.findViewById(R.id.tvTime);
        etPlan = view.findViewById(R.id.etPlan);
        etQty = view.findViewById(R.id.etQty);
        btnSave = view.findViewById(R.id.btnSave);
        ivClose = view.findViewById(R.id.ivClose);

        tvTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        liCoachId = sessionManager.getIntValue(SessionManager.KEY_USER_REECOACH_ID);
        liUserId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        if (list != null)
        {
            MyPlanResponse.MyPlanData model = list.get(position);

            if (!TextUtils.isEmpty((String) model.getActualTime()))
            {
                lsTime = (String) model.getActualTime();

                if (!TextUtils.isEmpty(lsTime))
                    tvTime.setText(lsTime);
            }
            else
            {
                if (!TextUtils.isEmpty(model.getScheduledTime()))
                {
                    lsTime = model.getScheduledTime();

                    if (!TextUtils.isEmpty(lsTime))
                        tvTime.setText(lsTime);
                }
            }

            if (!TextUtils.isEmpty((String) model.getActualPlan()))
            {
                lsPlan = (String) model.getActualPlan();

                if (!TextUtils.isEmpty(lsPlan))
                    etPlan.setText(lsPlan);
            }
            else
            {
                if (!TextUtils.isEmpty(model.getScheduledPlan()))
                {
                    lsPlan = model.getScheduledPlan();

                    if (!TextUtils.isEmpty(lsPlan))
                        etPlan.setText(lsPlan);
                }
            }

            if (!TextUtils.isEmpty((String) model.getActualValue()))
            {
                lsQty = (String) model.getActualValue();

                if (!TextUtils.isEmpty(lsQty))
                    etQty.setText(lsQty);
            }
            else
            {
                if (!TextUtils.isEmpty(model.getScheduledValue()))
                {
                    lsQty = model.getScheduledValue();

                    if (!TextUtils.isEmpty(lsQty))
                        etQty.setText(lsQty);
                }
            }
        }
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSave:

                lsPlan = "";
                lsQty = "";
                lsPlan = etPlan.getText().toString();
                lsQty = etQty.getText().toString();

                boolean isDataValid = validateAllData(lsTime, lsPlan, lsQty);

                if (isDataValid)
                {
                    list.get(position).setActualTime(lsTime);
                    list.get(position).setActualPlan(lsPlan);
                    list.get(position).setActualValue(lsQty);
                    //list.get(position).setDateOfAction(getCurrentDate());

                    PlanEditInterface listener = (PlanEditInterface) getActivity();
                    listener.onEdit(list);
                    dismiss();
                }
                else
                {
                    Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvTime:
                timepickerdialog.show();
                break;

            case R.id.ivClose:
                this.dismiss();
                break;
        }

    }

    private boolean validateAllData(String time, String plan, String qty)
    {
        boolean valid = true;

        if (time != null)
        {
            if (time.isEmpty())
            {
                valid = false;
                errorMsg = "Select time";
            }
        }
        else
        {
            valid = false;
        }

        if (valid && plan != null)
        {
            if (plan.isEmpty())
            {
                valid = false;
                errorMsg = "Enter plan";
            }
        }
        else
        {
            valid = false;
        }

        if (valid && qty != null)
        {
            if (qty.isEmpty())
            {
                valid = false;
                errorMsg = "Enter value";
            }
        }
        else
        {
            valid = false;
        }
        return valid;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        Calendar current = Calendar.getInstance();
        Calendar datetime = Calendar.getInstance();

        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);

        Format formatter;
        //formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        lsTime = formatter.format(cal.getTime());

        if (!TextUtils.isEmpty(lsTime))
            tvTime.setText(lsTime);

        /*if (datetime.getTimeInMillis() > current.getTimeInMillis())
        {
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cal.set(Calendar.MINUTE, minute);

            Format formatter;
            formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
            lsTime = formatter.format(cal.getTime());

            if (!TextUtils.isEmpty(lsTime))
                tvTime.setText(lsTime);
        }
        else
        {
            //it's before current'
            Toast.makeText(getContext(), "Invalid Time", Toast.LENGTH_LONG).show();
            clearvariables();
        }*/
    }

    public void clearvariables()
    {
        lsTime = "";
        tvTime.setText("");
    }

    public String getCurrentDate()
    {
        String date = "";
        try
        {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        }catch (Exception e){e.printStackTrace();}
        return date;
    }

}
