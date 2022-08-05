package com.shamrock.reework.activity.MedicineModule.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.MedicineModule.adapter.MyMedicinesAdapter;
import com.shamrock.reework.activity.MedicineModule.service.FrequencyList;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.api.request.AddMedicineRequest;
import com.shamrock.reework.api.request.DeleteMedicineRequest;
import com.shamrock.reework.api.request.EditMedicineApiRequest;
import com.shamrock.reework.api.request.MedicineListRequest;
import com.shamrock.reework.api.response.AddMedicineResponse;
import com.shamrock.reework.api.response.DeleteMedicineResponse;
import com.shamrock.reework.api.response.MedicineListResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.activity.MedicineModule.dialog.AddMedicineDialogFragment;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyMedicinesActivity extends AppCompatActivity
                                         implements View.OnClickListener,
                                         AddMedicineDialogFragment.AddMedicineDialogListener,
                                         MyMedicinesAdapter.MedicineListListener
{

    private static final String TAG = "MyMedicinesActivity";
    Context context;
    Toolbar toolbar;
    TextView txt_no_medicine;
    Typeface font;
    LinearLayout textAddMedicine;
    ListView listView;
    ArrayList<MyMedicine> myMedicinesList;
    AddMedicineDialogFragment dialogFragment;
    SessionManager sessionManager;
    private MedicineService medService;
    Utils utils;
    MyMedicinesAdapter subscribeFeatureAdapter;
    int userID;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medicines);
        context = MyMedicinesActivity.this;
        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        sessionManager = new SessionManager(context);
        medService = Client.getClient().create(MedicineService.class);
        utils = new Utils();

        myMedicinesList = new ArrayList<>();
        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Medicines");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        textAddMedicine = findViewById(R.id.textMedicines_Add);
        listView = findViewById(R.id.listViewMyMedicines);
        txt_no_medicine = findViewById(R.id.txt_no_medicine);

        textAddMedicine.setOnClickListener(this);

//        subscribeFeatureAdapter = new MyMedicinesAdapter(context, myMedicinesList);
//        listView.setAdapter(subscribeFeatureAdapter);

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        if (Utils.isNetworkAvailable(context))
        {
            callMedicineListAPI(userId);
        }
        else
        {
            ShowRetryBar();
        }
    }

    private void ShowRetryBar() {
        String strMessage = "Unable to load data";
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callMedicineListAPI(userId);
                    }
                });
        snackbar.show();
    }

    private void callMedicineListAPI(int userId)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        MedicineListRequest request = new MedicineListRequest();
        request.setUserid(userId);

        Call<MedicineListResponse> call = medService.getMedicinesList(request);
        call.enqueue(new Callback<MedicineListResponse>()
        {
            @Override
            public void onResponse(Call<MedicineListResponse> call, Response<MedicineListResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MedicineListResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        List<MedicineListResponse.DataResponse> dataResponseList = listResponse.getData();

                        if (dataResponseList != null)
                        {
                            myMedicinesList.clear();
                            for (MedicineListResponse.DataResponse data : dataResponseList)
                            {
                                ArrayList<FrequencyList> FrequencyList=new ArrayList<>();

                                int medID = 0;
                                int myMedID = data.getMymedid();
                                String name = data.getMedicineName();
                                String fre = data.getFrequency();
                                String daysInComma = data.getDosage();
                                FrequencyList.addAll(data.getFrequencyList());
                                String path=data.getImgPath();
                                String imageName=data.getImgName();

                                MyMedicine myMedicine = new MyMedicine(medID, myMedID, name, fre, daysInComma,FrequencyList,path,imageName,data.IsNotification());
                                myMedicinesList.add(myMedicine);
                            }

                            if (!myMedicinesList.isEmpty())
                            {
                                listView.setVisibility(View.VISIBLE);
                                txt_no_medicine.setVisibility(View.GONE);
                                subscribeFeatureAdapter = new MyMedicinesAdapter(context, myMedicinesList);
                                listView.setAdapter(subscribeFeatureAdapter);
                            }else {
                                listView.setVisibility(View.GONE);
                                txt_no_medicine.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    else
                    {
                        listView.setVisibility(View.GONE);
                        txt_no_medicine.setVisibility(View.VISIBLE);
                        txt_no_medicine.setText(listResponse.getMessage());

//                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
//                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                    ShowRetryBar();
                }
            }

            @Override
            public void onFailure(Call<MedicineListResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                ShowRetryBar();
            }
        });
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void shownoplan() {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);

        dialog.setContentView(R.layout.dialog_no_plan);
        dialog.setCancelable(false);
        TextView txt_lable_expired=dialog.findViewById(R.id.txt_lable_expired);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt_lable_expired.startAnimation(anim);

        Button btn_subscribe=dialog.findViewById(R.id.btn_subscribe);
        Button btn_subscribe_no=dialog.findViewById(R.id.btn_subscribe_no);
        btn_subscribe_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.textMedicines_Add:


                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }


                FragmentManager fm = getSupportFragmentManager();
                dialogFragment = new AddMedicineDialogFragment(MyMedicinesActivity.this);
                dialogFragment.show(fm, "add_fragment");
//                showMedicineDailog();




                break;
            default:
        }
    }

    private void showMedicineDailog() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);


        builder.setMessage("Medicine")
                .setCancelable(false)
                .setPositiveButton("Add Medicine", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        FragmentManager fm = getSupportFragmentManager();
                        dialogFragment = new AddMedicineDialogFragment(MyMedicinesActivity.this);
                        dialogFragment.show(fm, "add_fragment");



                    }
                })
                .setNegativeButton("Upload Medicine", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button




                        dialog.cancel();
                    }
                });
        //Creating dialog box
        android.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();
    }



    // below methods are from Fragment callback for Adding newly
    @Override
    public void onMedicineAdd(int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName,boolean IsNotification)
    {
//        Toast.makeText(context, "AC = Mid:" + medicineId + " Fre:" + frequency + " Day:" + days, Toast.LENGTH_LONG).show();

        utils.showProgressbar(context);

        if (Utils.isNetworkAvailable(context))
        {
            callAddMedicineApi(medicineId, frequency, days,eatingGSon,list,fileuploadimage,medicineName,IsNotification);
        }
        else
        {
            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
        }
    }

    // below methods are from Fragment callback for Edit(updating)
    @Override
    public void onMedicineEdit(int myMedID, int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName,String imageName,String imagepath,boolean IsNotification)
    {
        utils.showProgressbar(context);

        if (Utils.isNetworkAvailable(context))
        {

            callEditMedicineApi(myMedID, medicineId, frequency, days, userID,eatingGSon,list,fileuploadimage,medicineName,imageName,imagepath,IsNotification);
        }
        else
        {
            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
        }
//        callEditMedicineApi(myMedicine.getMyMedId(), myMedicine.getMedId(), myMedicine.getMedFreq(), myMedicine.getMedDays(), userID);

    }

    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }
    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
    private void callAddMedicineApi(int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, boolean IsNotification)
    {

        RequestBody photoContent;
        MultipartBody.Part photo;

        if (fileuploadimage.getName().isEmpty()){
             photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            photo= MultipartBody.Part.createFormData("ImgFile","", photoContent);
        }else {
             photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileuploadimage);
            photo = MultipartBody.Part.createFormData("ImgFile", fileuploadimage.getName(), photoContent);
        }



        AddMedicineRequest request = new AddMedicineRequest();
        request.setUserID(userId);
        request.setChekdedNoti(IsNotification);
        request.setMedicineName(medicineName);
        request.setMedicineId(medicineId);
        request.setFrequency(frequency.trim());
        request.setDosage(days);

        ArrayList<FrequencyList> frequencyLists=new ArrayList<>();

        for (int i = 0; i <eatingGSon.length() ; i++) {

            JSONObject json_obj = null;   //get the 3rd item
            try {
                json_obj = eatingGSon.getJSONObject(i);
                String EatingStatus = json_obj.getString("EatingStatus");
                String EatingTime = json_obj.getString("EatingTime");
                frequencyLists.add(new FrequencyList(EatingStatus,EatingTime,0));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        request.setFrequencyList(frequencyLists);


        String request2=new Gson().toJson(request);

        //Here the json data is add to a hash map with key data
//        Map<String,String> params = new HashMap<String, String>();
//            params.put("Medicine", request1);
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("Medicine", request2)
//                .build();


        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), request2);



//        FoodService foodService = Client.getClientMultiPart().create(FoodService.class);
        medService = Client.getClientMultiPart().create(MedicineService.class);


        Call<AddMedicineResponse> call = medService.UpdateMedicine(photo,body);
        call.enqueue(new Callback<AddMedicineResponse>()
        {
            @Override
            public void onResponse(Call<AddMedicineResponse> call, Response<AddMedicineResponse> response)
            {

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AddMedicineResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        dialogFragment.dismiss();
                        // reload medicinesListView
                        myMedicinesList.clear();
                        callMedicineListAPI(userId);
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<AddMedicineResponse> call, Throwable t)
            {

                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }

    private void callEditMedicineApi(int myMedID, int medicineId, String frequency, String days, int userID, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, String imageName, String imagepath, boolean IsNotification)
    {
        EditMedicineApiRequest request = new EditMedicineApiRequest();
        request.setMyMedicineID(myMedID);
        request.setMedicineId(medicineId);
        request.setName(medicineName);
        request.setFrequency(String.valueOf(frequency));
        request.setDosage(days);
        request.setIsNotification(IsNotification);

        request.setUserID(userID);
        request.setImgPath(imagepath);
        request.setImgName(imageName);
//        request.setFrequencyList(toStringArray(eatingGSon));
//        request.setFrequencyList(list);

        ArrayList<FrequencyList> frequencyLists=new ArrayList<>();

        for (int i = 0; i <eatingGSon.length() ; i++) {

            JSONObject json_obj = null;   //get the 3rd item
            try {
                json_obj = eatingGSon.getJSONObject(i);
                String EatingStatus = json_obj.getString("EatingStatus");
                String EatingTime = json_obj.getString("EatingTime");
                int id = Integer.parseInt(json_obj.getString("Id"));
                frequencyLists.add(new FrequencyList(EatingStatus,EatingTime,id));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        request.setFrequencyList(frequencyLists);
        String requestedit=new Gson().toJson(request);


        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), requestedit);
        MultipartBody.Part photo = null;
        if (!fileuploadimage.getPath().isEmpty()){
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileuploadimage);
            photo = MultipartBody.Part.createFormData("ImgFile", fileuploadimage.getName(), photoContent);
        }else {
            RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            photo = MultipartBody.Part.createFormData("ImgFile","", photoContent);

        }



        medService = Client.getClientMultiPart().create(MedicineService.class);

        Call<AddMedicineResponse> call = medService.UpdateMedicine(photo,body);
        call.enqueue(new Callback<AddMedicineResponse>()
        {
            @Override
            public void onResponse(Call<AddMedicineResponse> call, Response<AddMedicineResponse> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AddMedicineResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                        dialogFragment.dismiss();

                        callMedicineListAPI(userId);
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<AddMedicineResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callDeleteMedicineApi(int myMedID, final int userID, final int position)
    {
        DeleteMedicineRequest request = new DeleteMedicineRequest();
        request.setMyMedicineId(myMedID);
        request.setUserID(userID);

        Call<DeleteMedicineResponse> call = medService.deleteMedicines(request);
        call.enqueue(new Callback<DeleteMedicineResponse>()
        {
            @Override
            public void onResponse(Call<DeleteMedicineResponse> call, Response<DeleteMedicineResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    DeleteMedicineResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();

                        // call List API to reload list

                        subscribeFeatureAdapter.removeItem(position);
                        subscribeFeatureAdapter.notifyDataSetChanged();
                        callMedicineListAPI(userID);

                    }
                    else
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteMedicineResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });

    }


    // below methods are from Adapter callback fro Editing Current medicine
    public void updateMedicine(int position, MyMedicine myMedicine)
    {
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }



        try {
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment = new AddMedicineDialogFragment(MyMedicinesActivity.this);
            Bundle bundle = new Bundle();
            bundle.putBoolean("isEdit", true);
            bundle.putSerializable("myMedicine", myMedicine);
            dialogFragment.setArguments(bundle);
            dialogFragment.show(fm, "edit_fragment");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // below methods are from Adapter callback fro Deleting Current medicine
    @Override
    public void deleteMedicine(final int position, final MyMedicine myMedicine)
    {
        if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
            shownoplan();
            return;
        }



//        Toast.makeText(context, "Delete = " + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Medicine!")
                .setMessage("Do you really want to delete this medicine?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context))
                        {
                            callDeleteMedicineApi(myMedicine.getMyMedId(), userID, position);
                        }
                        else
                        {
                            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
