package com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.fragment.ActivtyListListener;
import com.shamrock.reework.model.ClsGadgetData;

import java.util.ArrayList;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class ShareCalAdapter extends BaseAdapter
{
//    com.shamrock.reework.fragment.ActivtyListListener ActivtyListListener;


    Context mContext;
    ArrayList<ShareCalPojo> list;
    private static LayoutInflater inflater = null;


    public ShareCalAdapter(Context context, ArrayList<ShareCalPojo> subscriptionFeatureArrayList)
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
    public ShareCalPojo getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_row_device_list, null);

        TextView txt_device_name = vi.findViewById(R.id.txt_device_name);
        ImageView img_device_image = vi.findViewById(R.id.img_device_image);
        txt_device_name.setText(list.get(i).getName());



        if (isValidContextForGlide(mContext)) {
            if(list.get(i).getId()==1) {
                Glide.with(mContext)
                        .load(R.drawable.pdficon)
                        .into(img_device_image);
            }else{
                Glide.with(mContext)
                        .load(R.drawable.gallery)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(RequestOptions.noTransformation()
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .error(R.drawable.gallery))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(img_device_image);
            }

        }


        return vi;
    }

    public void removeItem(int position)
    {
        list.remove(position);
        notifyDataSetChanged();
    }

}

