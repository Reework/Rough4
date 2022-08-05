package com.shamrock.reework.activity.NotificationModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shamrock.R;
import com.shamrock.reework.api.response.GetAllNotificationResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyHolder>
{

    Context context;
    ArrayList<GetAllNotificationResponse.Notifications> list;
    NotificationListener listener;

    public interface NotificationListener
    {
        public void GetNotificationListener(int pos, GetAllNotificationResponse.Notifications notifModel);
    }

    public NotificationAdapter(Context context, ArrayList<GetAllNotificationResponse.Notifications> list,
                               NotificationListener listener)
    {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvMessege, tvTime;
        ImageView imageView;
        public CardView cardView;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            tvMessege = itemView.findViewById(R.id.tvMessege);
            tvTime = itemView.findViewById(R.id.tvTime);
            imageView = itemView.findViewById(R.id.imageView_MyReminder_icon);
            cardView = itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View v)
        {
            listener.GetNotificationListener(getAdapterPosition(), list.get(getAdapterPosition()));
            cardView.setBackgroundColor(context.getResources().getColor(R.color.colorPremiumWhite));
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_notifications, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i)
    {
        GetAllNotificationResponse.Notifications model = list.get(i);

        if (!model.getIsRead())
            myHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorGreyWhite));
        else
            myHolder.cardView.setBackgroundColor(context.getResources().getColor(R.color.colorPremiumWhite));

        myHolder.imageView.setImageResource(R.drawable.ic_launcher);

        if (!TextUtils.isEmpty(model.getNotificationMessage()))
            myHolder.tvMessege.setText(model.getNotificationMessage());

        if (!TextUtils.isEmpty(model.getNotificationDate()))
        {
            String time = formatTime(model.getNotificationDate());
            if (!TextUtils.isEmpty(time))
                myHolder.tvTime.setText(time);
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
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd-MM-yyyy h:mm a");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "";
    }
}
