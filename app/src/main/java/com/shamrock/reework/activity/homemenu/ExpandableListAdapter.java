package com.shamrock.reework.activity.homemenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.lockumlock.ClsLockUnlockData;
import com.shamrock.reework.database.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private boolean isFirstTime;
    private List<String> mListDataHeader; // header titles
    String headerTitle;
    // child data in format of header title, child title
    private HashMap<String, List<String>> mListDataChild;
    ExpandableListView expandList;
    private ArrayList<ClsLockUnlockData> arylst_lock_unlockdata;

    public ExpandableListAdapter(Context context,
                                 List<String> listDataHeader,
                                 HashMap<String,
                                         List<String>> listChildData,
                                 ExpandableListView expandableList, ArrayList<ClsLockUnlockData> arylst_lock_unlockdata) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = expandableList;
        this.arylst_lock_unlockdata = arylst_lock_unlockdata;

    }

    boolean isUpdateMoreServiceICon;

    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
        //Log.d("GROUPCOUNT", String.valueOf(i));
        return i;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        try {


            if (this.mListDataChild != null) {
                if (mListDataChild.size() > 0) {

                    size = this.mListDataChild.get(
                            this.mListDataHeader.get(groupPosition))
                            .size();


                }
            } else {
                size = 0;

            }
        } catch (Exception e) {

        }

        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
        //        .get(childPosition).toString());
        return this.mListDataChild.get(
                this.mListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    ImageView arrow,arrowDown;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);

        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);


        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        GifImageView iconimagerecipe = (GifImageView) convertView.findViewById(R.id.iconimagerecipe);

        LinearLayout linearLayout = convertView.findViewById(R.id.layout_menus);


        if (headerTitle.equalsIgnoreCase("Profile")) {

            headerIcon.setImageResource(R.drawable.person);

//            SessionManager sessionManager=new SessionManager(mContext);
//            String  userPhoto = sessionManager.getStringValue(SessionManager.KEY_USER_PROFILE_IMAGE);
//
//            if (isValidContextForGlide(mContext)) {
//                Glide.with(mContext)
//                        .load(userPhoto)
//                        .apply(
//                                RequestOptions.circleCropTransform()
//                                        .placeholder(R.drawable.ic_profile_pic_bg)
//                                        .error(R.drawable.ic_profile_pic_bg)
//                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        )
//                        .into(headerIcon);
//
//                Glide.with(mContext)
//                        .load(userPhoto)
//                        .apply(
//                                RequestOptions.circleCropTransform()
//                                        .placeholder(R.drawable.ic_profile_pic_bg)
//                                        .error(R.drawable.ic_profile_pic_bg)
//                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        )
//                        .into(headerIcon);
//            }
        }
        else if (headerTitle.equalsIgnoreCase("Daily Diary")) {
            headerIcon.setImageResource(R.drawable.dailynew);
        } else if (headerTitle.equalsIgnoreCase("My Analysis")) {
            headerIcon.setImageResource(R.drawable.myanalysis);
        } else if (headerTitle.equalsIgnoreCase("Reeplan")) {
            headerIcon.setImageResource(R.drawable.reeplannew);
        }
        else if (headerTitle.equalsIgnoreCase("REEplace Items")) {
            headerIcon.setImageResource(R.drawable.cheat);
        }
        else if (headerTitle.equalsIgnoreCase("REEcoach")) {


            headerIcon.setImageResource(R.drawable.reecoachnew);

//            if (isValidContextForGlide(mContext)) {
//
//                SessionManager sessionManager=new SessionManager(mContext);
//                String  userPhoto = sessionManager.getStringValue("ReecoachImage");
//
//                Glide.with(mContext)
//                        .load(userPhoto)
//                        .apply(
//                                RequestOptions.circleCropTransform()
//                                        .placeholder(R.drawable.ic_profile_pic_bg)
//                                        .error(R.drawable.ic_profile_pic_bg)
//                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        )
//                        .into(headerIcon);
//
//
//            }

        }
        else if (headerTitle.equalsIgnoreCase("REEcipes")) {
            iconimagerecipe.setVisibility(View.VISIBLE);
            headerIcon.setVisibility(View.GONE);
            headerIcon.setImageResource(R.drawable.foodanimation);
        } else if (headerTitle.equalsIgnoreCase("Membership Plan")) {
            headerIcon.setImageResource(R.drawable.ic_subscription);
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
        } else if (headerTitle.equalsIgnoreCase("Analytics")) {
            headerIcon.setImageResource(R.drawable.ic_menu_my_analysis);
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
        } else if (headerTitle.equalsIgnoreCase("Logout")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.logoutnew);
        }else if (headerTitle.equalsIgnoreCase("My Lifestyle")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.mylifestylenew);
        } else if (headerTitle.equalsIgnoreCase("Me-Before & After")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.beforeafternew);
        }else if (headerTitle.equalsIgnoreCase("REEchat")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.reechatnew);
        }else if (headerTitle.equalsIgnoreCase("REEmember")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.reemembernew);
        }else if (headerTitle.equalsIgnoreCase("Help & Support")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.helpnew);
        } else if (headerTitle.equalsIgnoreCase("More Services") && isUpdateMoreServiceICon) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.moreservnew);


            headerIcon.setRotation(0f);


        } else if (headerTitle.equalsIgnoreCase("More Services") && !isUpdateMoreServiceICon && !isFirstTime) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.moreservnew);

            headerIcon.setRotation(0f);

        } else if (headerTitle.equalsIgnoreCase("More Services") && !isUpdateMoreServiceICon && isFirstTime) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.moreservnew);

            headerIcon.setRotation(180f);

        } else if (headerTitle.equalsIgnoreCase("Schedule Pathology Test")) {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.ic_menu_my_appointment);
        } else {
            iconimagerecipe.setVisibility(View.GONE);
            headerIcon.setVisibility(View.VISIBLE);
            headerIcon.setImageResource(R.drawable.ic_menu_my_appointment);

        }

        arrow = convertView.findViewById(R.id.iconimage1);
        arrowDown = convertView.findViewById(R.id.arrowDown);
        arrow.setVisibility(View.INVISIBLE);
        if (headerTitle.equals("More Services")) {
            arrow.setVisibility(View.VISIBLE);


        } else {
            arrow.setVisibility(View.INVISIBLE);
        }
        lblListHeader.setText(headerTitle);





        ImageView img_lock = convertView.findViewById(R.id.img_lock);


        if (headerTitle.equalsIgnoreCase("My Diet Plan")){

            callLockFunction("DIETPLAN",img_lock);



        }

        if (headerTitle.equalsIgnoreCase("CHEATPLAN")){


            callLockFunction("CHEATPLAN",img_lock);

        }

        if (headerTitle.equalsIgnoreCase("REEplace Items")){


            callLockFunction("CHEATPLAN",img_lock);

        }


        if (headerTitle.equalsIgnoreCase("REEplace Items")){


            callLockFunction("CHEATPLAN",img_lock);

        }

        if (headerTitle.equalsIgnoreCase("Recipe Library")){


            callLockFunction("RECIPELIBNORM",img_lock);

        }

        if (headerTitle.equalsIgnoreCase("Wellness Library")){


            callLockFunction("WELLNESSLIBRARY",img_lock);

        }

//        if(arrow.getVisibility()==View.VISIBLE){
//            arrowDown.setVisibility(View.VISIBLE);
//            arrow.setVisibility(View.GONE);
//        }else if(arrowDown.getVisibility()==View.VISIBLE){
//            arrowDown.setVisibility(View.GONE);
//            arrow.setVisibility(View.VISIBLE);
//        }

        return convertView;
    }

    private void callLockFunction(String menuname, ImageView img_lock) {
        if (arylst_lock_unlockdata!=null){
            for (int i = 0; i < arylst_lock_unlockdata.size(); i++) {

                if (arylst_lock_unlockdata.get(i).getStaticName().equalsIgnoreCase(menuname)){
                    String isLocked=arylst_lock_unlockdata.get(i).getIsLocked();
                    if (isLocked.equalsIgnoreCase("true")){
                        img_lock.setVisibility(View.VISIBLE);
                    }else {
                        img_lock.setVisibility(View.GONE);

                    }

                    break;

                }
            }

        }
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.submenu);
        ImageView iconimage_submenu = convertView.findViewById(R.id.iconimage_submenu);
        ImageView img_lock1 = convertView.findViewById(R.id.img_lock1);

        if (childText.equalsIgnoreCase("REEplace Items")) {
            iconimage_submenu.setImageResource(R.drawable.cheat);
        }else

        if (childText.equalsIgnoreCase("Reecoach")) {
//            iconimage_submenu.setImageResource(R.drawable.ic_menu_my_reecoach);

        } else if (childText.equalsIgnoreCase("Pathologist")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_my_reecoach);
        } else if (childText.equalsIgnoreCase("Appointments")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_my_appointment);
        } else if (childText.equalsIgnoreCase("Reminders")) {
            iconimage_submenu.setImageResource(R.drawable.reemembernew);
        } else if (childText.equalsIgnoreCase("Reeports")) {
            iconimage_submenu.setImageResource(R.drawable.ic_home_reeport);
        } else if (childText.equalsIgnoreCase("Medicines")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_medication);
        } else if (childText.equalsIgnoreCase("Analytics")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_my_analysis);

        } else if (childText.equalsIgnoreCase("My Lifestyle")) {
            iconimage_submenu.setImageResource(R.drawable.mylifestylenew);
        } else if (childText.equalsIgnoreCase("Me-Before & After")) {
            iconimage_submenu.setImageResource(R.drawable.beforeafternew);
        } else if (childText.equalsIgnoreCase("Health Profile")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_my_health);
        } else if (childText.equalsIgnoreCase("Schedule Pathology Test")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_my_appointment);
        } else if (childText.equalsIgnoreCase("Community")) {
            iconimage_submenu.setImageResource(R.drawable.ic_menu_chat_community);
        } else if (childText.equalsIgnoreCase("E-Shopping")) {
            iconimage_submenu.setImageResource(R.drawable.shooping);
        } else if (childText.equalsIgnoreCase("FAQ")) {
            iconimage_submenu.setImageResource(R.drawable.fa);
        } else if (childText.equalsIgnoreCase("External Devices")) {
            iconimage_submenu.setImageResource(R.drawable.external_device);
        }
        else if (childText.equalsIgnoreCase("REEspot")) {
            iconimage_submenu.setImageResource(R.drawable.reespotnew);
        }
        else if (childText.equalsIgnoreCase("Miscellaneous")) {
            iconimage_submenu.setImageResource(R.drawable.miss);
        }


        txtListChild.setText(childText);


        if (childText.equalsIgnoreCase("Reecoach")){


            callLockFunction("REECOACH",img_lock1);

        }

        if (childText.equalsIgnoreCase("Reminders")){


            callLockFunction("REMINDER",img_lock1);

        }
        if (childText.equalsIgnoreCase("Reeports")){


            callLockFunction("REPORTSTORAGE",img_lock1);

        }

        if (childText.equalsIgnoreCase("Community")){


            callLockFunction("COMMUNITY",img_lock1);

        }
        if (childText.equalsIgnoreCase("REEplace Items")){


            callLockFunction("CHEATPLAN",img_lock1);

        }


        if (childText.equalsIgnoreCase("REEplace Items")){


            callLockFunction("CHEATPLAN",img_lock1);

        }

//        if(arrow.getVisibility()==View.VISIBLE){
//            arrowDown.setVisibility(View.VISIBLE);
//            arrow.setVisibility(View.GONE);
//        }else
//        if(arrowDown.getVisibility()==View.VISIBLE){
//            arrowDown.setVisibility(View.GONE);
//            arrow.setVisibility(View.VISIBLE);
//        }




        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public void updateIcon(boolean isUpdateMoreServiceICon) {
        this.isUpdateMoreServiceICon = isUpdateMoreServiceICon;
        notifyDataSetChanged();
        isFirstTime = true;

    }

}
