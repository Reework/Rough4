package com.shamrock.reework.activity.Reecoachpathoselection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachListByType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachMasterType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsSetReecoach;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsSetReecoachRequest;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListAdapter;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListByTypeData;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.UnfreezeModule.service.UnfreezeService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UserStatusResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class SelectReecoachPathActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //reecaoch
    Spinner spinner_recoach_type;
    ClsReecoachMasterType clsReecoachMasterType;

    ArrayList<String> arylst_reecoach_type=new ArrayList<>();
    ArrayList<String> arylst_reecoach_type_id=new ArrayList<>();
    private ReecoachService reecoachService;
    Utils utils=new Utils();
    ListView lst_reecoah_list;
    ListView lst_patho;
    private int userId;


    ArrayList<ReecoachListByTypeData> clsReecoachListByTypes=new ArrayList<>();
    ArrayList<ReecoachListByTypeData> arylst_reecoachListByTypeDat;


//    /pathologyst

    ViewFlipper vp_recoachpatho;
    RadioButton rd_reecoach,rd_patho;
    private ArrayList<ReecoachListByTypeData> arylst_patholist;
    private UnfreezeService unfreezeService;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_reecoach_path);
        reecoachService = Client.getClient().create(ReecoachService.class);
        lst_patho=findViewById(R.id.lst_patho);

        getReecaochTypeData();
        pathlogist();
    }
    private void getReecaochTypeData(){


        utils.showProgressbar(this);

        Call<ClsReecoachMasterType> call = reecoachService.GetReecoachTypeMaster();
        call.enqueue(new Callback<ClsReecoachMasterType>()
        {
            @Override
            public void onResponse(Call<ClsReecoachMasterType> call, retrofit2.Response<ClsReecoachMasterType> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsReecoachMasterType moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode()==1){

                                if (moodResponse.getData()!=null){
                                    reecoach(moodResponse);


                                }

//                                Toast.makeText(mContext, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<ClsReecoachMasterType> call, Throwable t)
            {

                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }


    private void pathlogist() {
        rd_patho=findViewById(R.id.rd_patho);
        rd_reecoach=findViewById(R.id.rd_reecoach);

        vp_recoachpatho=findViewById(R.id.vp_recoachpatho);
        vp_recoachpatho.setDisplayedChild(0);
        rd_patho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_recoachpatho.setDisplayedChild(1);
                getpatholistData();

            }
        });
        rd_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_recoachpatho.setDisplayedChild(0);
            }
        });
        getpatholistData();
    }

    private void reecoach(ClsReecoachMasterType moodResponse) {
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Select Reecoach");
        ImageView imageView=findViewById(R.id.imgLeft);
        lst_reecoah_list=findViewById(R.id.lst_reecoah_list);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_recoach_type=findViewById(R.id.spinner_recoach_type);

        lst_reecoah_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                showCloseDailog(arylst_reecoachListByTypeDat.get(position).getUserid(),arylst_reecoachListByTypeDat.get(position).getFirstName()+" "+arylst_reecoachListByTypeDat.get(position).getLastName(),arylst_reecoachListByTypeDat.get(position));


            }
        });


        clsReecoachMasterType= moodResponse;

        for (int i = 0; i <clsReecoachMasterType.getData().size() ; i++) {
            arylst_reecoach_type.add(clsReecoachMasterType.getData().get(i).getReecoachType());
            arylst_reecoach_type_id.add(String.valueOf(clsReecoachMasterType.getData().get(i).getId()));
        }



        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_reecoach_type);
        spinner_recoach_type.setAdapter(stringArrayAdapter);


        getReecaochTypeData(arylst_reecoach_type_id.get(0).toString(),arylst_reecoach_type.get(0).toString());
        spinner_recoach_type.setOnItemSelectedListener(this);



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

                                    callPathoReecoachStatus(moodResponse.getData(),reecoachListByTypeData);

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
                                        ReecoachListAdapter reecoachListAdapter=new ReecoachListAdapter(SelectReecoachPathActivity.this,arylst_reecoachListByTypeDat, true);
                                        lst_reecoah_list.setAdapter(reecoachListAdapter);
                                    }


                                }else {
                                    TextView txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);

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
        getReecaochTypeData(arylst_reecoach_type_id.get(position).toString(),arylst_reecoach_type.get(position).toString());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void getpatholistData(){



        utils.showProgressbar(this);
        SessionManager sessionManager = new SessionManager(this);
        reecoachService = Client.getClient().create(ReecoachService.class);

        Call<ClsReecoachListByType> call = reecoachService.GetPathoListByType(4);
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

                                    }else {



                                        arylst_patholist=moodResponse.getData();
                                        ReecoachListAdapter reecoachListAdapter=new ReecoachListAdapter(SelectReecoachPathActivity.this,arylst_patholist, true);
                                        lst_patho.setAdapter(reecoachListAdapter);
                                    }


                                }else {

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
    private void callPathoReecoachStatus(final String data, final ReecoachListByTypeData reecoachListByTypeData) {


        utils.showProgressbar(this);


        UnfreezeRequest request =  new UnfreezeRequest();
        request.setUserid(userId);

        SessionManager sessionManager=new SessionManager(this);

        unfreezeService = Client.getClient().create(UnfreezeService.class);

        token = sessionManager.getStringValue(SessionManager.KEY_USER_TOKEN);

        Call<UserStatusResponse> call = unfreezeService.getUserStatus(token, request);
        call.enqueue(new Callback<UserStatusResponse>()
        {
            @Override
            public void onResponse(Call<UserStatusResponse> call, Response<UserStatusResponse> response)
            {
                utils.hideProgressbar();
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    UserStatusResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        SessionManager sessionManager=new SessionManager(SelectReecoachPathActivity.this);
//                        sessionManager.setIntValue(SessionManager.KEY_USER_REECOACH_ID, listResponse.getData().getReecoachId());
//                        sessionManager.setIntValue(SessionManager.KEY_USER_PATHO_ID, listResponse.getData().getReecoachId());

                        if (listResponse.getData().isReecoachRequire()){

                            sessionManager.setStringValue("KEY_ISSHOW_REECOACH","true");

                        }else {
                            sessionManager.setStringValue("KEY_ISSHOW_REECOACH","false");

                        }

                        if (listResponse.getData().isPathoRequire()){

                            sessionManager.setStringValue("KEY_ISSHOW_PATHO","true");



                        }else {
                            sessionManager.setStringValue("KEY_ISSHOW_PATHO","false");

                        }
                        Toast.makeText(SelectReecoachPathActivity.this, "" + data, Toast.LENGTH_SHORT).show();

                        showReecoachDataDailog(reecoachListByTypeData.getImageUrl(),reecoachListByTypeData.getFirstName()+" "+reecoachListByTypeData.getLastName());




                        if ((listResponse.getData().isPathoRequire())&&listResponse.getData().getPathologistId()==0||(listResponse.getData().isReecoachRequire())&&listResponse.getData().getReecoachId()==0){
//                            selectReecoachPathDialog(listResponse.getData());


                        }




                    }
                }
                else
                {
                    SessionManager sessionManager=new SessionManager(SelectReecoachPathActivity.this);

                    Toast.makeText(SelectReecoachPathActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SelectReecoachPathActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                    //Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    Log.d("Error---->", response.message());
                }
            }

            @Override
            public void onFailure(Call<UserStatusResponse> call, Throwable t)
            {

                SessionManager sessionManager=new SessionManager(SelectReecoachPathActivity.this);
                utils.hideProgressbar();
                Toast.makeText(SelectReecoachPathActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectReecoachPathActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void showReecoachDataDailog(String imgUrl, String reecoachname){

        final Dialog dialog = new Dialog(SelectReecoachPathActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        dialog.setContentView(R.layout.dialg_reecoah_description);
        ImageView img_reecoach_photo=dialog.findViewById(R.id.img_reecoach_photo);
        Button btn_schedule_now=dialog.findViewById(R.id.btn_schedule_now);

        TextView reecoach_name_dailog=dialog.findViewById(R.id.reecoach_name_dailog);
        reecoach_name_dailog.setText(reecoachname);
        btn_schedule_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectReecoachPathActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                dialog.dismiss();

//                closeDialog();
            }
        });

        Context context=SelectReecoachPathActivity.this;

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


        if (isValidContextForGlide(SelectReecoachPathActivity.this))
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

    private void closeDialog() {
        Intent intent = new Intent(SelectReecoachPathActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }


}


