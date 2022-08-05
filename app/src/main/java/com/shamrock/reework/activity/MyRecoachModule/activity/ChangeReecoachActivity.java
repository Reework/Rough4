package com.shamrock.reework.activity.MyRecoachModule.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.SnipeetActivity;
import com.shamrock.reework.activity.HealthSupportModule.HealthAndSupportActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyRecoachModule.adapters.ReecoachListNewAdapter;
import com.shamrock.reework.activity.MyRecoachModule.adapters.ReecoachtypeAdapter;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.SingleChatModule.activity.SingleChatActivity;
import com.shamrock.reework.activity.services.MyServiceActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class ChangeReecoachActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,ReecoachtypeAdapter.ReecoaachtypeListener,ReecoachListNewAdapter.ReecoachSelecttener{
    Spinner spinner_recoach_type;
    ClsReecoachMasterType clsReecoachMasterType;

    ArrayList<String> arylst_reecoach_type=new ArrayList<>();
    ArrayList<String> arylst_reecoach_type_id=new ArrayList<>();
    private ReecoachService reecoachService;
    Utils utils=new Utils();
    RecyclerView lst_reecoah_list;
    private int userId;
    ArrayList<ReecoachListByTypeData> clsReecoachListByTypes=new ArrayList<>();
    ArrayList<ReecoachListByTypeData> arylst_reecoachListByTypeDat;
    private RecyclerView recycler_reecoachtype;
    TextView txt_select_reecoach_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_reecoach);
     try {
         TextView tvTitle=findViewById(R.id.tvTitle);
         tvTitle.setText("Change Reecoach");
         ImageView imageView=findViewById(R.id.imgLeft);
         lst_reecoah_list=findViewById(R.id.lst_reecoah_list);
         recycler_reecoachtype=findViewById(R.id.recycler_reecoachtype);
         txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         spinner_recoach_type=findViewById(R.id.spinner_recoach_type);

//        lst_reecoah_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


////                showReecoachDataDailog(arylst_reecoachListByTypeDat.get(position).getImageUrl());

//                showCloseDailog(arylst_reecoachListByTypeDat.get(position).getUserid(),arylst_reecoachListByTypeDat.get(position).getFirstName()+" "+arylst_reecoachListByTypeDat.get(position).getLastName(),arylst_reecoachListByTypeDat.get(position));


//            }
//        });


         clsReecoachMasterType= (ClsReecoachMasterType) getIntent().getSerializableExtra("KEY_ReecoachMasterType");

         for (int i = 0; i <clsReecoachMasterType.getData().size() ; i++) {
             arylst_reecoach_type.add(clsReecoachMasterType.getData().get(i).getReecoachType());
             arylst_reecoach_type_id.add(String.valueOf(clsReecoachMasterType.getData().get(i).getId()));
         }


         ReecoachtypeAdapter reecoachtypeAdapter=new ReecoachtypeAdapter(ChangeReecoachActivity.this,clsReecoachMasterType.getData());
         recycler_reecoachtype.setAdapter(reecoachtypeAdapter);


         ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_reecoach_type);
         spinner_recoach_type.setAdapter(stringArrayAdapter);


         getReecaochTypeData(arylst_reecoach_type_id.get(0).toString(),arylst_reecoach_type.get(0).toString());
         spinner_recoach_type.setOnItemSelectedListener(this);

     }catch (Exception e){
         Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
     }


    }

    public void showReecoachDataDailog(String imgUrl, String reecoachname){

        final Dialog dialog = new Dialog(ChangeReecoachActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.dialg_reecoah_description);
        ImageView img_reecoach_photo=dialog.findViewById(R.id.img_reecoach_photo);
        Button btn_schedule_now=dialog.findViewById(R.id.btn_schedule_now);

        TextView reecoach_name_dailog=dialog.findViewById(R.id.reecoach_name_dailog);
        reecoach_name_dailog.setText(reecoachname);
        btn_schedule_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                closeDialog();
                Intent intent = new Intent(ChangeReecoachActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                dialog.dismiss();

            }
        });

        Context context=ChangeReecoachActivity.this;

        TextView tvTitle=dialog.findViewById(R.id.tvTitle);
        tvTitle.setText("My Reecoach");
        ImageView imageView=dialog.findViewById(R.id.imgLeft);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                closeDialog();
            }
        });


        if (isValidContextForGlide(ChangeReecoachActivity.this))
        {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);

            // for background Image
            Glide.with(context)
                    .load(imgUrl)
                    .apply(options)
                    .into(img_reecoach_photo);

            // for profile Image
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
                    .into(img_reecoach_photo);
        }
        dialog.show();
    }


    public void closeDialog(){
        Intent intent=new Intent(ChangeReecoachActivity.this, MyReecoachProfileActivity.class);

        intent.putExtra("Reeacoach","yes");
        setResult(RESULT_OK, intent);
//                                    startActivity(intent);
        finish();
    }

    private void showCloseDailog(final String ReecoachID, String name, final ReecoachListByTypeData reecoachListByTypeData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Are you sure want to select "+name+" as a reecoach ?")
                .setCancelable(false)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();



                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        setReecoach(String.valueOf(ReecoachID),reecoachListByTypeData);

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();
    }




    private void setReecoach(String ReecoachID, final ReecoachListByTypeData reecoachListByTypeData){



        utils.showProgressbar(this);
        SessionManager sessionManager = new SessionManager(this);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        reecoachService = Client.getClient().create(ReecoachService.class);
        ClsSetReecoachRequest clsSetReecoachRequest=new ClsSetReecoachRequest();
        clsSetReecoachRequest.setReecoachId(Integer.parseInt(ReecoachID));
        clsSetReecoachRequest.setReeworkerId(userId);

        sessionManager.setIntValue(SessionManager.KEY_USER_REECOACH_ID, Integer.parseInt(ReecoachID));



        Call<ClsSetReecoach> call = reecoachService.SetReecoachDetailAPi(clsSetReecoachRequest);
        call.enqueue(new Callback<ClsSetReecoach>()
        {
            @Override
            public void onResponse(Call<ClsSetReecoach> call, retrofit2.Response<ClsSetReecoach> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsSetReecoach moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode()==1){

                                if (moodResponse.getData()!=null){

                                    showReecoachDataDailog(reecoachListByTypeData.getImageUrl(),reecoachListByTypeData.getFirstName()+" "+reecoachListByTypeData.getLastName());


                                    Toast.makeText(ChangeReecoachActivity.this, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();
//                                    Intent intent=new Intent(ChangeReecoachActivity.this, MyReecoachProfileActivity.class);
//
//                                    intent.putExtra("Reeacoach","yes");
//                                    setResult(RESULT_OK, intent);
////                                    startActivity(intent);
//                                    finish();

                                }


                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsSetReecoach> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void getReecaochTypeData(String ID, final String reecoachtype){



        utils.showProgressbar(this);
       SessionManager sessionManager = new SessionManager(this);
        reecoachService = Client.getClient().create(ReecoachService.class);

        Call<ClsReecoachListByType> call = reecoachService.GetReecoachListByType(Integer.parseInt(ID));
        call.enqueue(new Callback<ClsReecoachListByType>()
        {
            @Override
            public void onResponse(Call<ClsReecoachListByType> call, retrofit2.Response<ClsReecoachListByType> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsReecoachListByType moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode()==1){

                                if (moodResponse.getData()!=null){

//                                    arylst_reecoachListByTypeDat.clear();

                                    if (moodResponse.getData().isEmpty()){
                                        TextView txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);

                                        txt_select_reecoach_header.setVisibility(View.INVISIBLE);
                                        lst_reecoah_list.setVisibility(View.GONE);
                                        TextView txt_no_reecoach=findViewById(R.id.txt_no_reecoach);
                                        txt_no_reecoach.setText(reecoachtype+" are not available");
                                        txt_no_reecoach.setVisibility(View.VISIBLE);
                                    }else {
                                        TextView txt_no_reecoach=findViewById(R.id.txt_no_reecoach);
                                        TextView txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);

                                        txt_select_reecoach_header.setVisibility(View.VISIBLE);
                                        txt_no_reecoach.setVisibility(View.GONE);

                                        lst_reecoah_list.setVisibility(View.VISIBLE);

                                        arylst_reecoachListByTypeDat=moodResponse.getData();
                                        ReecoachListNewAdapter reecoachListAdapter=new ReecoachListNewAdapter(ChangeReecoachActivity.this,arylst_reecoachListByTypeDat);
                                        lst_reecoah_list.setAdapter(reecoachListAdapter);
                                    }


                                }else {

                                    txt_select_reecoach_header.setVisibility(View.INVISIBLE);
                                    lst_reecoah_list.setVisibility(View.GONE);
                                    TextView txt_no_reecoach=findViewById(R.id.txt_no_reecoach);
                                    txt_no_reecoach.setText(reecoachtype+" are not available");

                                    txt_no_reecoach.setVisibility(View.VISIBLE);
                                }


                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsReecoachListByType> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                getReecaochTypeData(arylst_reecoach_type_id.get(position).toString(),arylst_reecoach_type.get(position).toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void GetTypePosition(int pos, ReecoachMasterTypeData model) {
        txt_select_reecoach_header.setText("Select "+arylst_reecoach_type.get(pos).toString());


        getReecaochTypeData(arylst_reecoach_type_id.get(pos).toString(),arylst_reecoach_type.get(pos).toString());


    }

    @Override
    public void GetReecoachSelectPosition(final int pos, final ReecoachListByTypeData model) {


        try {

            final Dialog dialog=new Dialog(ChangeReecoachActivity.this,R.style.CustomDialog);
            dialog.setContentView(R.layout.lay_reecoach_details);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView img_reecoachtype=dialog.findViewById(R.id.img_reecoachtype);
            Glide.with(ChangeReecoachActivity.this)
                    .load(model.getImageUrl())
                    .error(R.drawable.ic_profile_pic_bg)

                    .apply(RequestOptions.circleCropTransform())

                    .into(img_reecoachtype);
            TextView lablname=dialog.findViewById(R.id.lablname);
            TextView text_ReecoachProfile_Mobile=dialog.findViewById(R.id.text_ReecoachProfile_Mobile);
            TextView text_ReecoachProfile_Email=dialog.findViewById(R.id.text_ReecoachProfile_Email);
            TextView text_ReecoachProfile_RegAddress=dialog.findViewById(R.id.text_ReecoachProfile_RegAddress);
        TextView text_ida_number=dialog.findViewById(R.id.text_ida_numbers);
            lablname.setText(model.getFirstName()+" "+model.getLastName());
            text_ReecoachProfile_Mobile.setText(model.getMobile_No());
            text_ReecoachProfile_Email.setText(model.getEmail());
            text_ReecoachProfile_RegAddress.setText(model.getAddress());
            TextView text_Reecoachdescription=dialog.findViewById(R.id.text_Reecoachdescription);
            try {
                if (model.getIdaNumber()!=null&&!model.getIdaNumber().isEmpty()){
               text_ida_number.setText("IDA No : "+model.getIdaNumber());
                }else {

               text_ida_number.setText("IDA No : ");

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            if (model.getDiscription()!=null){
                text_Reecoachdescription.setText("Expertise : "+model.getDiscription());

            }
            TextView txtselectreecoach=dialog.findViewById(R.id.txtselectreecoach);
            txtselectreecoach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCloseDailog(arylst_reecoachListByTypeDat.get(pos).getUserid(),arylst_reecoachListByTypeDat.get(pos).getFirstName()+" "+arylst_reecoachListByTypeDat.get(pos).getLastName(),arylst_reecoachListByTypeDat.get(pos));
                }
            });


            text_ReecoachProfile_Email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + model.getEmail()));
                    startActivity(emailIntent);
                }
            });




            text_ReecoachProfile_Mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dexter.withActivity(ChangeReecoachActivity.this)
                            .withPermission(Manifest.permission.CALL_PHONE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + model.getMobile_No()));
                                    startActivity(intent);
                                }
                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    showSettingsDialog();
                                }
                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                }
                            }).check();
                }
            });










            dialog.show();
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }




    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    private void showSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ChangeReecoachActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
