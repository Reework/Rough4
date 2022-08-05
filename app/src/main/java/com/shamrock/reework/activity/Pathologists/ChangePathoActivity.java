package com.shamrock.reework.activity.Pathologists;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.shamrock.R;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachListByType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachMasterType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsSetReecoach;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsSetReecoachRequest;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListAdapter;
import com.shamrock.reework.activity.MyRecoachModule.activity.ReecoachListByTypeData;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ChangePathoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner_recoach_type;
    ClsReecoachMasterType clsReecoachMasterType;

    ArrayList<String> arylst_reecoach_type=new ArrayList<>();
    ArrayList<String> arylst_reecoach_type_id=new ArrayList<>();
    private ReecoachService reecoachService;
    Utils utils=new Utils();
    ListView lst_reecoah_list;
    private int userId;
    ArrayList<ReecoachListByTypeData> clsReecoachListByTypes=new ArrayList<>();
    ArrayList<ReecoachListByTypeData> arylst_reecoachListByTypeDat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_path);
        TextView tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setText("Select Pathologist");
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



                showCloseDailog(arylst_reecoachListByTypeDat.get(position).getUserid(),arylst_reecoachListByTypeDat.get(position).getFirstName()+" "+arylst_reecoachListByTypeDat.get(position).getLastName());


            }
        });


//        clsReecoachMasterType= (ClsReecoachMasterType) getIntent().getSerializableExtra("KEY_ReecoachMasterType");

//        for (int i = 0; i <clsReecoachMasterType.getData().size() ; i++) {
//            arylst_reecoach_type.add(clsReecoachMasterType.getData().get(i).getReecoachType());
//            arylst_reecoach_type_id.add(String.valueOf(clsReecoachMasterType.getData().get(i).getId()));
//        }



//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_reecoach_type);
//        spinner_recoach_type.setAdapter(stringArrayAdapter);
//

//        getReecaochTypeData(arylst_reecoach_type_id.get(0).toString(),arylst_reecoach_type.get(0).toString());
        getReecaochTypeData();
//        spinner_recoach_type.setOnItemSelectedListener(this);



    }

    private void showCloseDailog(final String ReecoachID, String name) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("Are you sure want to select "+name+" as a Pathologist ?")
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
                        setReecoach(String.valueOf(ReecoachID));

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();
    }




    private void setReecoach(String ReecoachID){



        utils.showProgressbar(this);
        SessionManager sessionManager = new SessionManager(this);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        reecoachService = Client.getClient().create(ReecoachService.class);
        ClsSetPAthoRequest clsSetReecoachRequest=new ClsSetPAthoRequest();
        clsSetReecoachRequest.setPathologistId(Integer.parseInt(ReecoachID));
        clsSetReecoachRequest.setReeworkerId(userId);


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

                                    Toast.makeText(ChangePathoActivity.this, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ChangePathoActivity.this, MyReecoachProfileActivity.class);

                                    intent.putExtra("Reeacoach","yes");
                                    setResult(RESULT_OK, intent);
//                                    startActivity(intent);
                                    finish();

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


    private void getReecaochTypeData(){



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
                                        TextView txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);

                                        txt_select_reecoach_header.setVisibility(View.GONE);
                                        lst_reecoah_list.setVisibility(View.GONE);
                                        TextView txt_no_reecoach=findViewById(R.id.txt_no_reecoach);
//                                        txt_no_reecoach.setText(reecoachtype+" are not available");
                                        txt_no_reecoach.setVisibility(View.VISIBLE);
                                    }else {
                                        TextView txt_no_reecoach=findViewById(R.id.txt_no_reecoach);
                                        TextView txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);

                                        txt_select_reecoach_header.setVisibility(View.GONE);
                                        txt_no_reecoach.setVisibility(View.GONE);

                                        lst_reecoah_list.setVisibility(View.VISIBLE);

                                        arylst_reecoachListByTypeDat=moodResponse.getData();
                                        ReecoachListAdapter reecoachListAdapter=new ReecoachListAdapter(ChangePathoActivity.this,arylst_reecoachListByTypeDat,true);
                                        lst_reecoah_list.setAdapter(reecoachListAdapter);
                                    }

                                }else {
                                    TextView txt_select_reecoach_header=findViewById(R.id.txt_select_reecoach_header);

                                    txt_select_reecoach_header.setVisibility(View.INVISIBLE);
                                    lst_reecoah_list.setVisibility(View.GONE);
                                    TextView txt_no_reecoach=findViewById(R.id.txt_no_reecoach);
//                                    txt_no_reecoach.setText(reecoachtype+" are not available");

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
}
