package com.shamrock.reework.activity.SingleChatModule.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.api.response.SingleChatResponse;
import com.shamrock.reework.database.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleChatAdapter extends RecyclerView.Adapter<SingleChatAdapter.MyHolder>
{

    private Context context;
    private ArrayList<SingleChatResponse.ChatList> list;
    private boolean isAdmin;

    public SingleChatAdapter(Context context, ArrayList<SingleChatResponse.ChatList> list, boolean isAdmin)
    {
        this.context = context;
        this.list = list;
        this.isAdmin=isAdmin;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvMsgCoach, tvTimeCoach, tvMsgUser, tvTimeUser, tvDate,tv_namecoach,tv_name;
        LinearLayout linearCoach, linearUser;
        ImageView img_user_image,img_user_image_client;

        public MyHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setClickable(true);

            tvMsgCoach = itemView.findViewById(R.id.tvMsgCoach);
            tvTimeCoach = itemView.findViewById(R.id.tvTimeCoach);
            tv_namecoach = itemView.findViewById(R.id.tv_namecoach);
            tvMsgUser = itemView.findViewById(R.id.tvMsgUser);
            tvTimeUser = itemView.findViewById(R.id.tvTimeUser);
            linearCoach = itemView.findViewById(R.id.linearCoach);
            linearUser = itemView.findViewById(R.id.linearUser);
            tvDate = itemView.findViewById(R.id.tvDate);
            img_user_image_client = itemView.findViewById(R.id.img_coach_image);
            img_user_image = itemView.findViewById(R.id.img_user_image);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.reechat_row_new, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i)
    {
        SingleChatResponse.ChatList model = list.get(i);
        SessionManager sessionManager=new SessionManager(context);


        if (isAdmin){
            Glide.with(context)
                    .load(sessionManager.getStringValue("admin"))
                    .apply(RequestOptions.circleCropTransform()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .error(R.drawable.ic_profile_pic_bg))
                    .into(holder.img_user_image_client);

        }else {
            Glide.with(context)
                    .load(sessionManager.getStringValue("reecoach"))
                    .apply(RequestOptions.circleCropTransform()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .error(R.drawable.ic_profile_pic_bg))
                    .into(holder.img_user_image_client);

        }

        String   userPhoto = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);
        Glide.with(context)
                .load(userPhoto)
                .apply(RequestOptions.circleCropTransform()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.ic_profile_pic_bg))
                .into(holder.img_user_image);


        String dateFromApi = formatDates(model.getDateTime());

        if (i != 0)
        {
            String dateFromApiCompare = formatDates(list.get(i-1).getDateTime());
            processDate(holder.tvDate, dateFromApi, dateFromApiCompare,false);
        }
        else
        {
            processDate(holder.tvDate, dateFromApi,null,true);
        }

        boolean isCoach = model.getIsReecoach();
        if (isCoach)
        {
            holder.linearCoach.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(model.getMessage()))
                holder.tvMsgCoach.setText(model.getMessage());
            if (!TextUtils.isEmpty(model.getDateTime()))
            {
                String time = formatTime(model.getDateTime());

                String abc[] = time.split(" ");

                if(abc[1].equals("pm")) {
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeCoach.setText(abc[0]+" PM");
                }else{
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeCoach.setText(abc[0]+" AM");
                }


            }
//            if (!TextUtils.isEmpty(model.get())) {
//                holder.tv_namecoach.setText(model.getFullname());
//            }

            holder.linearUser.setVisibility(LinearLayout.GONE);
        }
        else
        {
            holder.linearUser.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(model.getMessage()))
                holder.tvMsgUser.setText(model.getMessage());
            if (!TextUtils.isEmpty(model.getDateTime()))
            {
                String time = formatTime(model.getDateTime());

                String abc[] = time.split(" ");

                if(abc[1].equals("pm")) {
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeUser.setText(abc[0]+" PM");
                }else{
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeUser.setText(abc[0]+" AM");
                }

                
            }
            holder.linearCoach.setVisibility(LinearLayout.GONE);
        }
    }

    private void processDate(@NonNull TextView tv, String dateAPIStr, String dateAPICompareStr
                            , boolean isFirstItem)
    {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");

        if (isFirstItem)
        {
            //first item always got date/today to shows
            //and overkill to compare with next item flow
            Date dateFromAPI = null;

            try
            {
                dateFromAPI = f.parse(dateAPIStr);

                if (DateUtils.isToday(dateFromAPI.getTime()))
                    tv.setText("TODAY");
                else if (DateUtils.isToday(dateFromAPI.getTime() + DateUtils.DAY_IN_MILLIS))
                    tv.setText("YESTERDAY");
                else
                    tv.setText(dateAPIStr);

                tv.setIncludeFontPadding(false);
                tv.setVisibility(View.VISIBLE);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                tv.setVisibility(View.GONE);
            }
        }
        else
        {
            if (!dateAPIStr.equalsIgnoreCase(dateAPICompareStr))
            {
                try
                {
                    Date dateFromAPI = f.parse(dateAPIStr);
                    if (DateUtils.isToday(dateFromAPI.getTime())) tv.setText("today");
                    else if (DateUtils.isToday(dateFromAPI.getTime() + DateUtils.DAY_IN_MILLIS)) tv.setText("yesterday");
                    else tv.setText(dateAPIStr);
                    tv.setIncludeFontPadding(false);
                    tv.setVisibility(View.VISIBLE);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                    tv.setVisibility(View.GONE);
                }
            }
            else
            {
                tv.setVisibility(View.GONE);
            }
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try
        {
            date = dateFormat.parse(dateFromServer);
            return newDateFormatter.format(date);
        } catch (ParseException e) { e.printStackTrace(); }
        return "N/A";
    }
}
