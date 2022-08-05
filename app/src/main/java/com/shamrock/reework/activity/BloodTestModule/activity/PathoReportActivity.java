package com.shamrock.reework.activity.BloodTestModule.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.BCAModule.adapter.MyBCAReportAdapter;
import com.shamrock.reework.activity.BCAModule.adapter.MyBloodReportAdapter;
import com.shamrock.reework.activity.BCAModule.service.BCAReportItem;
import com.shamrock.reework.activity.BeforeAfterModule.service.BeforeAfterService;
import com.shamrock.reework.activity.BloodTestModule.adapter.BloodReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.adapter.OtherReportAdapter;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsGetotherreportmain;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsUplaodReportSucess;
import com.shamrock.reework.activity.BloodTestModule.pojo.OtherReportData;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.BloodTestReportRequest;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.BcaResponse;
import com.shamrock.reework.api.response.BloodReportItem;
import com.shamrock.reework.api.response.BloodTestReportResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PathoReportActivity extends AppCompatActivity
{
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
    private final int GALLERY = 1;
    private ArrayList<HashMap<String, String>> arraylist;
    boolean isFile=true;



    Context context;
    Toolbar toolbar;
    Typeface font;
    ListView rv_other_report;
    ListView listView;
    List<BloodReportItem> BloodReportItemArrayList;
    private ReeTestService reeTestService;
    private SessionManager sessionManager;
    private Utils utils;
    private int userID;
    BloodReportAdapter bloodReportAdapter;
    TextView txt_noreportbca,txt_noreport;

    ViewFlipper vp_reports;


    RecyclerView recyclerView;
    RecyclerView rvMyBloodReport;
    MyBCAReportAdapter bcaReportAdapter;
    MyBloodReportAdapter newbloodReportAdapter;
    ArrayList<BCAReportItem> bcaList;
    ArrayList<BcaResponse.BcaReport> mList;
    List<BCAResponce.Datum> mBCAList;
    List<BCAResponce.Datum> mBCAList_filter;
    List<BCAResponce.Datum> mbloodList_filter;
    RadioButton rd_button_blood;
    RadioButton rd_button_bca;
    RadioButton rd_button_anyother;
    FloatingActionButton fab_upload_data;
TextView txt_pdf_upload;
boolean isShowmenu=false;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;
    private String docFilePath="";

ArrayList<OtherReportData> arylst_other_Data;
    private boolean isCamera;
    private Uri mCapturedImageURI;
    private File fileuploadimage;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patho_report);
        fab_upload_data=findViewById(R.id.fab_upload_data);
        txt_pdf_upload=findViewById(R.id.txt_pdf_upload);
        rv_other_report=findViewById(R.id.rv_other_report);
        fab_upload_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(PathoReportActivity.this, R.style.CustomDialog);
                dialog.setContentView(R.layout.dialog_add_report);
                ImageView close_add_report=dialog.findViewById(R.id.close_add_report);
                close_add_report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btn_add_png=dialog.findViewById(R.id.btn_add_png);
                Button btn_add_image=dialog.findViewById(R.id.btn_add_image);
                btn_add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AddMedicineImageDailog();
                    }
                });
                btn_add_png.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        fileChooser();

                    }
                });
                dialog.show();

            }
        });


//        fab_upload_data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                sh
////                if (isShowmenu){
////                    closeSubMenusFab();
////                } else {
////                    openSubMenusFab();
////                }
//
//            }
//        });


        context = PathoReportActivity.this;
        init();

        findViews();

        CallOtherTestReports();


        notificationService = Client.getClient().create(NotificationService.class);
        //BRIADCAST RECEIVER
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equals(FcmConstants.REGISTRATION_COMPLETE))// gcm successfully registered
                    {
//                        mFcmToken = intent.getStringExtra(FcmConstants.FCM_TOKEN);
//                        PushFcmToServer();
                    } else if (intent.getAction().equals(FcmConstants.PUSH_NOTIFICATION)) // new push notification is received
                    {
                        String title = intent.getStringExtra(FcmConstants.FCM_TITLE);
                        String message = intent.getStringExtra(FcmConstants.FCM_MESSEGE);
                        mNotifcationCount = intent.getIntExtra(FcmConstants.FCM_COUNT, 0);

                        if (mNotifcationCount > 0) {
                            tvNotificationCOunt.setText(String.valueOf(mNotifcationCount));
                            invalidateOptionsMenu();
                        } else {
                            if (Utils.isNetworkAvailable(context))
                                GetAllNotificationCount();
                            else
                                Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        if (Utils.isNetworkAvailable(context))
            GetAllNotificationCount();
        else
            Snackbar.make(findViewById(android.R.id.content), "No internet !", Snackbar.LENGTH_LONG).show();

        setToolBar();



    }
    public void fileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(intent, FILE_SELECT_REQUEST_CODE);
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Files.getContentUri());
//        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }

    private void closeSubMenusFab(){
        txt_pdf_upload.setVisibility(View.INVISIBLE);
        isShowmenu = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        txt_pdf_upload.setVisibility(View.VISIBLE);
        isShowmenu = true;
    }

    private void init()
    {
        BloodReportItemArrayList = new ArrayList<>();

        sessionManager = new SessionManager(context);
        reeTestService = Client.getClient().create(ReeTestService.class);
        utils = new Utils();

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Pathology Reeport");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });

        counterValuePanel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(), NotificationsActivity.class), 500);

            }
        });



        if (tvNotificationCOunt != null) {
            if (mNotifcationCount == 0)
                tvNotificationCOunt.setVisibility(View.GONE);
            else {
                tvNotificationCOunt.setVisibility(View.VISIBLE);
                tvNotificationCOunt.setText("" + mNotifcationCount);
            }
        }

    }

    private void findViews()
    {
     //   listView = findViewById(R.id.listViewBloodReport);

        recyclerView = findViewById(R.id.rvMyBCAReport);
        rvMyBloodReport = findViewById(R.id.rvMyBloodReport);
        txt_noreport=findViewById(R.id.txt_noreport);
        txt_noreportbca=findViewById(R.id.txt_noreportbca);
        vp_reports = findViewById(R.id.vp_reports);
        rd_button_bca=findViewById(R.id.rd_button_bca);
        rd_button_blood=findViewById(R.id.rd_button_blood);
        rd_button_anyother=findViewById(R.id.rd_button_anyother);
        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        vp_reports.setDisplayedChild(1);

        rd_button_bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_reports.setDisplayedChild(0);
            }
        });

        rd_button_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_reports.setDisplayedChild(1);
            }
        });

        rd_button_anyother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_reports.setDisplayedChild(2);
                CallOtherTestReports();

            }
        });
        if (Utils.isNetworkAvailable(context))
            // callToGetBcaReport();
            callToGetBCAReport();
        else
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
    }



    private void callToGetBCAReport()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        BcaRequest request = new BcaRequest();
        request.setUserid(userId);////4186


        Call<BCAResponce> call = reeTestService.getAllBloodReportNewHistory(String.valueOf(userId));
        call.enqueue(new Callback<BCAResponce>()
        {
            @Override
            public void onResponse(Call<BCAResponce> call, Response<BCAResponce> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    BCAResponce bcaResponse = response.body();

                    if (bcaResponse != null && bcaResponse.getCode() == 1)
                    {
                        mBCAList = bcaResponse.getData();

                        if (mBCAList != null && mBCAList.size() > 0)
                        {
                            mBCAList_filter=new ArrayList<>();
                            mbloodList_filter=new ArrayList<>();

                            for (int i = 0; i <mBCAList.size() ; i++) {
                                if (mBCAList.get(i).getReportType().equalsIgnoreCase("BCA Test")){
                                    mBCAList_filter.add(mBCAList.get(i));
                                }else {
                                    mbloodList_filter.add(mBCAList.get(i));

                                }


                            }

                            if (!mBCAList_filter.isEmpty()){
                                recyclerView.setVisibility(View.VISIBLE);
                                txt_noreportbca.setVisibility(View.GONE);
                                bcaReportAdapter = new MyBCAReportAdapter(context, mBCAList_filter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PathoReportActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(bcaReportAdapter);
                            }else {
                                recyclerView.setVisibility(View.GONE);
                                txt_noreportbca.setVisibility(View.VISIBLE);
                                txt_noreportbca.setText("No Reports available");
                            }



                            if (!mbloodList_filter.isEmpty()){
                                rvMyBloodReport.setVisibility(View.VISIBLE);
                                txt_noreport.setVisibility(View.GONE);
                                newbloodReportAdapter = new MyBloodReportAdapter(context, mbloodList_filter);
                                RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(PathoReportActivity.this);
                                rvMyBloodReport.setLayoutManager(layoutManager2);
                                rvMyBloodReport.setItemAnimator(new DefaultItemAnimator());
                                rvMyBloodReport.setAdapter(newbloodReportAdapter);
                            }else {
                                rvMyBloodReport.setVisibility(View.GONE);
                                txt_noreport.setVisibility(View.VISIBLE);
                                txt_noreport.setText("No Reports available");

                            }


                        }else {
                            recyclerView.setVisibility(View.GONE);
                            rvMyBloodReport.setVisibility(View.GONE);
                            txt_noreport.setVisibility(View.VISIBLE);
                            txt_noreportbca.setVisibility(View.VISIBLE);

                            txt_noreport.setText("No Reports available");
                            txt_noreportbca.setText("No Reports available");


//                            Toast.makeText(BloodReportActivity.this, "No Reports available", Toast.LENGTH_SHORT).show();
//                            finish();

                        }
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), bcaResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BCAResponce> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }



    private void CallToGetAllBloodTestReports(int userId)
    {
        utils.showProgressbar(context);

        BloodTestReportRequest request = new BloodTestReportRequest();
        request.setUserID(String.valueOf(userId));

        Call<BloodTestReportResponse> call = reeTestService.getBloodTestReports(request);
        call.enqueue(new Callback<BloodTestReportResponse>()
        {
            @Override
            public void onResponse(Call<BloodTestReportResponse> call, Response<BloodTestReportResponse> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    BloodTestReportResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        BloodReportItemArrayList = listResponse.getData();

                        if (BloodReportItemArrayList != null && !BloodReportItemArrayList.isEmpty())
                        {
                            bloodReportAdapter = new BloodReportAdapter(context, BloodReportItemArrayList);
                           // listView.setAdapter(bloodReportAdapter);
                        }
                        else
                            Snackbar.make(findViewById(android.R.id.content), "No reports available !", Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<BloodTestReportResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }




    private void CallOtherTestReports()
    {
        utils.showProgressbar(context);



        Call<ClsGetotherreportmain> call = reeTestService.getOtherReportList(userID);
        call.enqueue(new Callback<ClsGetotherreportmain>()
        {
            @Override
            public void onResponse(Call<ClsGetotherreportmain> call, Response<ClsGetotherreportmain> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsGetotherreportmain listResponse = response.body();

                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        arylst_other_Data = listResponse.getData();

                        if (arylst_other_Data != null && !arylst_other_Data.isEmpty())
                        {

                          OtherReportAdapter bloodReportAdapter = new OtherReportAdapter(context, arylst_other_Data);
                            rv_other_report.setAdapter(bloodReportAdapter);
                        }
                        else
                        {
                            if (vp_reports.getDisplayedChild()==2){
                                Snackbar.make(findViewById(android.R.id.content), "No reports available !", Snackbar.LENGTH_LONG).show();

                            }

                        }
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<ClsGetotherreportmain> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.d("ERROR---->>>", t.toString());
            }
        });
    }
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void ShowRetryBar(String msg)
    {

        String strMessage;

        if (TextUtils.isEmpty(msg))
        {
            strMessage = "Unable to load data";
        }
        else
        {
            strMessage = msg;
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        CallToGetAllBloodTestReports(userID);
                    }
                });

        snackbar.show();
    }
    private String getFileNameByUri(Context context, Uri uri)
    {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file = null;
        if (uri.getScheme().toString().compareTo("content") == 0)
        {
            Cursor cursor = context.getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION }, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        }
        else
        if (uri.getScheme().compareTo("file") == 0)
        {
            try
            {
                try {
                    file = new File(new URI(uri.toString()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (file.exists())
                    filepath = file.getAbsolutePath();

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            filepath = uri.getPath();
        }
        return filepath;
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {

            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e) {

            return contentUri.getPath();}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
//



//                    Bitmap bitmapImage = BitmapFactory.decodeFile(fileuploadimage.getPath());
//                    int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

                    if (fileuploadimage.exists()) {
                        callUploadApi(fileuploadimage);

                    } else {
                        Toast.makeText(context, "file not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString());
                    t.printStackTrace();
                }

            }
        }


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                isFile=false;

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


//                Uri uri =/ data/.getData();

//                String path = getFilePathFr//omURI(BloodReportActivity.this,selectedImage);
                File file=new File(picturePath);
                Bitmap bitmap = BitmapFactory.decodeFile (file.getPath ());
                try {
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                Bitmap bitmapImage = BitmapFactory.decodeFile(file.getPath());
//                int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

                if (file.exists()){
                    callUploadApi(file);

                }else {
                    Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
                }

            }
        }





        if (resultCode == RESULT_OK) {
            if (requestCode == FILE_SELECT_REQUEST_CODE) {
                isFile = true;
                Uri uri = data.getData();

                String path = getFilePathFromURI(PathoReportActivity.this, uri);
                File file = new File(path);

                if (file.exists()) {
                    callUploadApi(file);

                } else {
                    Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
                }

            }



        }
    }

    private void callUploadApi(File file) {


        MultipartBody.Part photo;


        if (isFile){

            RequestBody requestFile = RequestBody.create(
                    MediaType.parse("image/*"), file);
            String imagename = file.getName();
        photo = MultipartBody.Part.createFormData("ReportFile", imagename, requestFile);


        }else {

            RequestBody photoContent;

            photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            photo = MultipartBody.Part.createFormData("ReportFile", file.getName(), photoContent);


        }




        BeforeAfterService uploadService = Client.getClientMultiPart().create(BeforeAfterService.class);


        utils.showProgressbar(this);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setId("0");
        reportRequest.setReeworkerId(String.valueOf(userID));
        reportRequest.setReportFilePath(file.getPath());
        reportRequest.setReportName(file.getName());


        String request2=new Gson().toJson(reportRequest);

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), request2);



//        RequestBody user_Id = RequestBody.create(MediaType.parse("multipart/form-MainModel"), ""+reportRequest);



        Call<ClsUplaodReportSucess> call = uploadService.UploadReportFile(photo,body);
        call.enqueue(new Callback<ClsUplaodReportSucess>()
        {

            @Override
            public void onResponse(Call<ClsUplaodReportSucess> call, Response<ClsUplaodReportSucess> response)
            {
                //ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> data = null;

                utils.hideProgressbar();

                if (response.isSuccessful())
                {

                    if (response.body() != null)
                    {
                        ClsUplaodReportSucess dataResponse = response.body();

                        if (dataResponse.getCode().equals("1"))
                        {

                            Toast.makeText(getApplicationContext(), "Uploaded successfully !", Toast.LENGTH_SHORT).show();
                            CallOtherTestReports();


//                            fetchData();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Server : " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Server : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ClsUplaodReportSucess> call, Throwable t)
            {

                utils.hideProgressbar();
                Toast.makeText(PathoReportActivity.this, ""+t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                fetchData();
//                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void Imagechooser()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName+".pdf");
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }
    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }
    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private void browseDocuments() {

        String[] mimeTypes =
                { // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "image/*"
                        };

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 1);

    }
    private void AddMedicineImageDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage("Upload Report ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(PathoReportActivity.this)
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        CallCameraIntent();
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();





                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        dialog.cancel();

                        Dexter.withActivity(PathoReportActivity.this)
                                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        Imagechooser() ;
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();



                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCancelable(true);
        //Setting the title manually
        alert.show();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void CallCameraIntent()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, 500);
    }
    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }



    private void GetAllNotificationCount() {
        GetAllNotificationRequest request = new GetAllNotificationRequest();
        request.setUserid(userID);

        Call<NotifCountResponse> call = notificationService.getAllNotificationCount(request);

        call.enqueue(new Callback<NotifCountResponse>() {
            @Override
            public void onResponse(Call<NotifCountResponse> call, Response<NotifCountResponse> response) {

                if (response.code() == Client.RESPONSE_CODE_OK) {
                    NotifCountResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1) {
                        mNotifcationCount = listResponse.getData();

                        invalidateOptionsMenu();
                    } else {
                        Log.d("RESUME--->", "" + listResponse.getMessage());
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RESUME--->", "" + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NotifCountResponse> call, Throwable t) {
                Log.e("HOME_ACTIVITY", t.toString());
            }
        });
    }

}
