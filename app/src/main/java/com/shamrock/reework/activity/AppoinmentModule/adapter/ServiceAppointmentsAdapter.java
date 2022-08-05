package com.shamrock.reework.activity.AppoinmentModule.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shamrock.R;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.database.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServiceAppointmentsAdapter extends RecyclerView.Adapter<ServiceAppointmentsAdapter.MyHolder>
{
    Context mContext;
    ArrayList<GetAllAppointmentResponse.AppointmentData> list;
    MyAppointmentListener listener;

    public interface MyAppointmentListener
    {
        public void GetClickedAppointment(String type, int position, GetAllAppointmentResponse.AppointmentData model);
    }

    public ServiceAppointmentsAdapter(Context context, ArrayList<GetAllAppointmentResponse.AppointmentData> list,
                                      MyAppointmentListener listener)
    {
        this.mContext = context;
        this.list = list;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvTitle, tvDate, tvTime, tvEndTime,text_Appointmentstatus,txt_revisite;
        ImageView imgEdit, imgDelete;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvTitle = itemView.findViewById(R.id.text_AppointmentName);
            tvDate = itemView.findViewById(R.id.textAppointmentDate);
            tvTime = itemView.findViewById(R.id.textAppointmentTime);
            imgEdit = itemView.findViewById(R.id.imgAppointmentEdit);
            imgDelete = itemView.findViewById(R.id.imgAppointmentDelete);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            txt_revisite = itemView.findViewById(R.id.txt_revisite);
            text_Appointmentstatus = itemView.findViewById(R.id.text_Appointmentstatus);

            imgEdit.setOnClickListener(this);
            imgDelete.setOnClickListener(this);
            txt_revisite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.imgAppointmentDelete:

                    listener.GetClickedAppointment("delete", getAdapterPosition(), list.get(getAdapterPosition()));
                    break;

                case R.id.imgAppointmentEdit:

                    listener.GetClickedAppointment("edit", getAdapterPosition(), list.get(getAdapterPosition()));
                    break;

                case R.id.txt_revisite:






                    listener.GetClickedAppointment("edit", getAdapterPosition(), list.get(getAdapterPosition()));
                    break;
            }
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_my_appointment_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        GetAllAppointmentResponse.AppointmentData model = list.get(i);

        int id=model.getStatusID();
        String statusstr="";
        if (id==1){
            statusstr="Active";
            myHolder.text_Appointmentstatus.setTextColor(mContext.getResources().getColor(R.color.holo_green_darks));
        }else if (id==2){
            statusstr="Canceled";
            myHolder.text_Appointmentstatus.setTextColor(mContext.getResources().getColor(R.color.colorReescore_BlueYellow));


        }else {
            statusstr="Closed";
            myHolder.text_Appointmentstatus.setTextColor(mContext.getResources().getColor(R.color.red_btn_bg_color));

        }

//        String featureName = "Appointment-"+(i+1);
        String featureName = "Appointment-"+(i+1)+" with "+model.getFullname()+"";

        myHolder.tvTitle.setText(featureName);
        myHolder.text_Appointmentstatus.setText(statusstr);


        String strDate = model.getDate();
        if (!TextUtils.isEmpty(strDate))
        {
            String date = formatDates(strDate);
            myHolder.tvDate.setText(date);
        }

       /* String strTime = model.getTime();
        if (!TextUtils.isEmpty(strTime))
        {
            myHolder.tvTime.setText(strTime);
        }

        String strEndTime = model.getEndTime();
        if (!TextUtils.isEmpty(strEndTime))
        {
            myHolder.tvEndTime.setText(strEndTime);
        }*/

        String strTime = model.getTime();
        if (!TextUtils.isEmpty(strTime))
        {
            String time = formatTime(strTime);

            if (!TextUtils.isEmpty(time))
                myHolder.tvTime.setText(time);
        }

        String strEndTime = model.getEndTime();
        if (!TextUtils.isEmpty(strEndTime))
        {
            String endTime = formatTime(strEndTime);
            if (!TextUtils.isEmpty(endTime))
                myHolder.tvEndTime.setText(endTime);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public String formatTime(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }


    public String formatDates(String dateFromServer)
    {
        //String strDate = "2013-05-15T10:00:00-0700";

//        2020-04-02T00:00:00

        SessionManager sessionManager=new SessionManager(mContext);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }


      /*SimpleDateFormat oldDateFormatter, newDateFormatter;
        oldDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
        try
        {
            Date date = oldDateFormatter.parse(dateFromServer.substring(0, 26) + dateFromServer.substring(27, 29));
            newDateFormatter = new SimpleDateFormat("h:mm a");

            return newDateFormatter.format(date);
        } catch (ParseException e) {e.printStackTrace();}*/

    /*Context mContext;
    ArrayList<ScheduledAppointment> list;
    private static LayoutInflater inflater = null;


    public MyAppointmentsAdapter(Context context, ArrayList<ScheduledAppointment> subscriptionFeatureArrayList)
    {
        this.list = subscriptionFeatureArrayList;
        mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ScheduledAppointment getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_my_appointment, null);

        TextView title = vi.findViewById(R.id.text_AppointmentName);
        TextView date = vi.findViewById(R.id.textAppointmentDate);
        TextView time = vi.findViewById(R.id.textAppointmentTime);
        ImageView imgEdit = vi.findViewById(R.id.imgAppointmentEdit);
        ImageView imgDelete = vi.findViewById(R.id.imgAppointmentDelete);

        ScheduledAppointment song = list.get(i);
        String featureName = song.getName();
        String strDate = song.getDate();
        String strTime = song.getTime();
        boolean free = song.isActive();


        return vi;
    }*/
}
