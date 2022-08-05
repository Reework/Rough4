package com.shamrock.reework.activity.community;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.api.response.SingleChatResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommunityChatAdapter extends RecyclerView.Adapter<CommunityChatAdapter.MyHolder>
{

    private Context context;
    private ArrayList<GetCommunityData> list;
    private int userId;

    public CommunityChatAdapter(Context context, ArrayList<GetCommunityData> list, int userId)
    {
        this.context = context;
        this.list = list;
        this.userId=userId;
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tvMsgCoach, tvTimeCoach,tv_namecoach, tvMsgUser, tvTimeUser,tv_name,tvDate;
        LinearLayout linearCoach, linearUser;
        ImageView img_coach_image,img_user_image;

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
            tv_name = itemView.findViewById(R.id.tv_name);
            img_user_image = itemView.findViewById(R.id.img_user_image);
            img_coach_image = itemView.findViewById(R.id.img_coach_image);
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
        GetCommunityData model = list.get(i);

        String dateFromApi = formatDates(model.getCreatedOn());

        if (i != 0)
        {
            String dateFromApiCompare = formatDates(list.get(i-1).getCreatedOn());
            processDate(holder.tvDate, dateFromApi, dateFromApiCompare,false);
        }
        else
        {
            processDate(holder.tvDate, dateFromApi,null,true);
        }

        String isCoach = model.getUserId();
        if (isCoach.equalsIgnoreCase(String.valueOf(userId)))
        {
            holder.linearCoach.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(model.getMessage()))
                holder.tvMsgCoach.setText(model.getMessage());
            if (!TextUtils.isEmpty(model.getCreatedOn()))
            {
                String time = formatTime(model.getCreatedOn());

                String abc[] = time.split(" ");
                if(abc[1].equals("pm")) {
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeCoach.setText(abc[0]+" PM");
                }else{
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeCoach.setText(abc[0]+" AM");
                }

            }
            if (!TextUtils.isEmpty(model.getFullname())) {
                holder.tv_namecoach.setText(model.getFullname());
            }
            holder.linearUser.setVisibility(LinearLayout.GONE);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);
            Glide.with(context)
                    .load(model.getImg())
                    .apply(options)
                    .apply(RequestOptions.circleCropTransform())
                    .listener(new RequestListener<Drawable>()
                    {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource)
                        {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                       DataSource dataSource, boolean isFirstResource)
                        {
                            return false;
                        }
                    })
                    .into(holder.img_coach_image);




        }
        else
        {
            holder.linearUser.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(model.getMessage()))
                holder.tvMsgUser.setText(model.getMessage());
            if (!TextUtils.isEmpty(model.getCreatedOn()))
            {
                String time = formatTime(model.getCreatedOn());

                String abc[] = time.split(" ");
                if(abc[1].equals("pm")) {
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeUser.setText(abc[0]+" PM");
                }else{
                    if (!TextUtils.isEmpty(time))
                        holder.tvTimeUser.setText(abc[0]+" AM");
                }


            }
            holder.tv_name.setText(model.getFullname()+"    ");

            holder.linearCoach.setVisibility(LinearLayout.GONE);
            setImage(holder,model.getImg());
        }
    }

    private void setImage(MyHolder holder, String imgUrl) {

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_pic_bg)
                .error(R.drawable.ic_profile_pic_bg)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .apply(RequestOptions.circleCropTransform())
                .listener(new RequestListener<Drawable>()
                {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource)
                    {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource)
                    {
                        return false;
                    }
                })
                .into(holder.img_user_image);
    }

    private void processDate(@NonNull TextView tv, String dateAPIStr, String dateAPICompareStr
            , boolean isFirstItem)
    {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yy");

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("hh:mm a");
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
