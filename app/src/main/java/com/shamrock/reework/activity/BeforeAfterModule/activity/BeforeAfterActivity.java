package com.shamrock.reework.activity.BeforeAfterModule.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.BeforeAfterModule.adapter.BeforeAfterAdapter;
import com.shamrock.reework.activity.BeforeAfterModule.adapter.NewBeforeAfterAdapter;
import com.shamrock.reework.activity.BeforeAfterModule.service.BeforeAfterService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.NotificationModule.activity.NotificationsActivity;
import com.shamrock.reework.activity.NotificationModule.service.NotificationService;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.TempReqForBeforeAfter;
import com.shamrock.reework.api.response.BeforeAfterResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.FcmConstants;
import com.shamrock.reework.util.Utils;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeforeAfterActivity extends AppCompatActivity implements View.OnClickListener, BeforeAfterAdapter.BeforeAfterListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener

{
    private Context context;
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;

    private RecyclerView rvBefore, rvAfter;
    BeforeAfterAdapter beforeAfterAdapter;
    ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> beforeAfterDataList;

    private ImageView ivBefore, ivAfter;
    Toolbar toolbar;
    public boolean isAfter = false;
    public boolean isCamera = false;
    public boolean isImage = false;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
            FILE_SELECT_REQUEST_CODE = 300;
    private Uri mCapturedImageURI;
    private int userID;
    private SessionManager sessionManager;

    private Utils utils;
    private DatePickerDialog datepickerdialog;
    private TimePickerDialog timepickerdialog;
    StringBuilder stringBuilder_datetime;
    TextView txt_date_time;
    String curentDateTime="";
    StringBuilder strSubmitDateTime=new StringBuilder();
    private ArrayList<String> beforeID;
    private ArrayList<String> afterID;

    private ImageView img_comparebefore,img_compareafter;
    TextView txt_compareafter,txt_comparebefore;

    Button btn_upload;

    TextView tvNotificationCOunt;
    private BroadcastReceiver mBroadcastReceiver;
    int  mNotifcationCount = 0;
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_after);

        curentDateTime= new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault()).format(new Date());
        strSubmitDateTime.append(new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault()).format(new Date()).toString());

        img_comparebefore=findViewById(R.id.img_comparebefore);
        img_compareafter=findViewById(R.id.img_compareafter);
        txt_compareafter=findViewById(R.id.txt_compareafter);
        txt_comparebefore=findViewById(R.id.txt_comparebefore);
        btn_upload =findViewById(R.id.btn_upload);
        context = BeforeAfterActivity.this;
        init();

        findViews();
        showDatePickerDailog();
        showTimePickerDialog();

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

    private void showTimePickerDialog() {

        timepickerdialog = new TimePickerDialog(BeforeAfterActivity.this, this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE) ,
                false);
//        datepickerdialog.show();
    }

    private void init()
    {
        utils = new Utils();
        sessionManager = new SessionManager(context);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        tvNotificationCOunt = (TextView) findViewById(R.id.count);
        RelativeLayout counterValuePanel = findViewById(R.id.counterValuePanel);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Before/After");

        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
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
        ivBefore = findViewById(R.id.ivUploadBefore);
        ivAfter = findViewById(R.id.ivUploadAfter);
        rvBefore = findViewById(R.id.rvBefore);
        rvAfter = findViewById(R.id.rvAfter);

        beforeAfterDataList = new ArrayList<>();

        ivBefore.setOnClickListener(this);
        ivAfter.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        registerForContextMenu(ivBefore);

        if (Utils.isNetworkAvailable(context))
        {
            fetchData();
        }
        else
            Toast.makeText(context, "No Internet", Toast.LENGTH_LONG);
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

                Intent  intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("param", "From_no_plan");
                startActivity(intent);

            }
        });

        dialog.show();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {


            case R.id.btn_upload:

                if (Utils.isNetworkAvailable(context))
                {
                    isAfter = false;

                    final Dialog dialog=new Dialog(context,R.style.CustomDialog);

                    dialog.setContentView(R.layout.dialog_beforeafter_image);

                    Button btn_before=dialog.findViewById(R.id.btn_before);
                    Button btn_after=dialog.findViewById(R.id.btn_after);

                    ImageView img_close_upload=dialog.findViewById(R.id.img_close_upload);
                    img_close_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btn_after.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                            try {
                                isAfter = true;
                                final Dialog dialog = new Dialog(context, R.style.CustomDialog);

                                dialog.setContentView(R.layout.dialog_datetime_image);

                                Button btn_camera = dialog.findViewById(R.id.btn_camera);
                                Button btn_gallery = dialog.findViewById(R.id.btn_gallery);
                                ImageView img_close_upload = dialog.findViewById(R.id.img_close_upload);
                                txt_date_time = dialog.findViewById(R.id.txt_date_time);

                                txt_date_time.setText(curentDateTime);

                                img_close_upload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                LinearLayout ll_upload_photo_date=dialog.findViewById(R.id.ll_upload_photo_date);
                                btn_gallery.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!checkIfAlreadyHavePermission())
                                        {
                                            isCamera = false;
                                            requestForSpecificPermission();
                                        }
                                        else
                                        {
                                            dialog.dismiss();
                                            fileChooser();
                                        }
                                    }
                                });
                                btn_camera.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog.dismiss();

                                        Dexter.withActivity(BeforeAfterActivity.this)
                                                .withPermission(Manifest.permission.CAMERA)
                                                .withListener(new PermissionListener() {
                                                    @Override
                                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                                        // permission is granted, open the camera
                                                        isCamera = true;
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
                                });


                                ll_upload_photo_date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        datepickerdialog.show();
                                    }
                                });


                                dialog.show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }










                        }
                    });


                    btn_before.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            if (Utils.isNetworkAvailable(context))
                            {
                                isAfter = false;

                                final Dialog dialog=new Dialog(context,R.style.CustomDialog);

                                dialog.setContentView(R.layout.dialog_datetime_image);

                                Button btn_camera=dialog.findViewById(R.id.btn_camera);
                                Button btn_gallery=dialog.findViewById(R.id.btn_gallery);
                                txt_date_time=dialog.findViewById(R.id.txt_date_time);
                                txt_date_time.setText(curentDateTime);
                                ImageView img_close_upload=dialog.findViewById(R.id.img_close_upload);
                                img_close_upload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                LinearLayout ll_upload_photo_date=dialog.findViewById(R.id.ll_upload_photo_date);
                                btn_gallery.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!checkIfAlreadyHavePermission())
                                        {
                                            isCamera = false;
                                            requestForSpecificPermission();

                                        }
                                        else
                                        {
                                            dialog.dismiss();
                                            fileChooser();
                                        }
                                    }
                                });
                                btn_camera.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        Dexter.withActivity(BeforeAfterActivity.this)
                                                .withPermission(Manifest.permission.CAMERA)
                                                .withListener(new PermissionListener() {
                                                    @Override
                                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                                        // permission is granted, open the camera
                                                        dialog.dismiss();
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



//                            if (!checkIfAlreadyHavePermission())
//                            {
//                                isCamera = true;
//                                requestForSpecificPermission();
//                            }
//                            else
//                            {
//                                dialog.dismiss();
//                                CallCameraIntent();
//                            }
                                    }
                                });


                                ll_upload_photo_date.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        datepickerdialog.show();
                                    }
                                });


                                dialog.show();



//                    openContextMenu(ivBefore);
                            }
                            else
                            {
                                showRetryBar(getString(R.string.internet_connection_unavailable));
                            }



                        }
                    });


                    dialog.show();



//                    openContextMenu(ivBefore);
                }
                else
                {
                    showRetryBar(getString(R.string.internet_connection_unavailable));
                }
                break;





            case R.id.ivUploadBefore:

//                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                    shownoplan();
//                    return;
//                }




                if (Utils.isNetworkAvailable(context))
                {
                    isAfter = false;

                    final Dialog dialog=new Dialog(context,R.style.CustomDialog);

                    dialog.setContentView(R.layout.dialog_datetime_image);

                    Button btn_camera=dialog.findViewById(R.id.btn_camera);
                    Button btn_gallery=dialog.findViewById(R.id.btn_gallery);
                    txt_date_time=dialog.findViewById(R.id.txt_date_time);
                    txt_date_time.setText(curentDateTime);
                    ImageView img_close_upload=dialog.findViewById(R.id.img_close_upload);
                    img_close_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    LinearLayout ll_upload_photo_date=dialog.findViewById(R.id.ll_upload_photo_date);
                    btn_gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!checkIfAlreadyHavePermission())
                            {
                                isCamera = false;
                                requestForSpecificPermission();

                            }
                            else
                            {
                                dialog.dismiss();
                                fileChooser();
                            }
                        }
                    });
                    btn_camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            Dexter.withActivity(BeforeAfterActivity.this)
                                    .withPermission(Manifest.permission.CAMERA)
                                    .withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse response) {
                                            // permission is granted, open the camera
                                            dialog.dismiss();
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



//                            if (!checkIfAlreadyHavePermission())
//                            {
//                                isCamera = true;
//                                requestForSpecificPermission();
//                            }
//                            else
//                            {
//                                dialog.dismiss();
//                                CallCameraIntent();
//                            }
                        }
                    });


                    ll_upload_photo_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            datepickerdialog.show();
                        }
                    });


                    dialog.show();



//                    openContextMenu(ivBefore);
                }
                else
                {
                    showRetryBar(getString(R.string.internet_connection_unavailable));
                }
                break;

            case R.id.ivUploadAfter:

//                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
//                    shownoplan();
//                    return;
//                }

                try {
                    isAfter = true;
                    final Dialog dialog = new Dialog(context, R.style.CustomDialog);

                    dialog.setContentView(R.layout.dialog_datetime_image);

                    Button btn_camera = dialog.findViewById(R.id.btn_camera);
                    Button btn_gallery = dialog.findViewById(R.id.btn_gallery);
                    ImageView img_close_upload = dialog.findViewById(R.id.img_close_upload);
                    txt_date_time = dialog.findViewById(R.id.txt_date_time);

                    txt_date_time.setText(curentDateTime);

                    img_close_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    LinearLayout ll_upload_photo_date=dialog.findViewById(R.id.ll_upload_photo_date);
                    btn_gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!checkIfAlreadyHavePermission())
                            {
                                isCamera = false;
                                requestForSpecificPermission();
                            }
                            else
                            {
                                dialog.dismiss();
                                fileChooser();
                            }
                        }
                    });
                    btn_camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();

                            Dexter.withActivity(BeforeAfterActivity.this)
                                    .withPermission(Manifest.permission.CAMERA)
                                    .withListener(new PermissionListener() {
                                        @Override
                                        public void onPermissionGranted(PermissionGrantedResponse response) {
                                            // permission is granted, open the camera
                                            isCamera = true;
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
                    });


                    ll_upload_photo_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            datepickerdialog.show();
                        }
                    });


                    dialog.show();

                }catch (Exception e){
                    e.printStackTrace();
                }
//                openContextMenu(ivBefore);
        }
    }

    private void showRetryBar(String msg)
    {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.title_image_picker);
        menu.add(0, v.getId(), 0, getString(R.string.camera));
        menu.add(0, v.getId(), 0, getString(R.string.gallery));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if (item.getTitle().equals(getString(R.string.camera)))
        {
//            if (!checkIfAlreadyHavePermission())
//            {
//                isCamera = true;
//                requestForSpecificPermission();
//            }
//            else
//            {
            Dexter.withActivity(BeforeAfterActivity.this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            // permission is granted, open the camera
                            isCamera = true;
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

//            }
        }
        else if (item.getTitle().equals(getString(R.string.gallery)))
        {
            if (!checkIfAlreadyHavePermission())
            {
                isCamera = false;
                requestForSpecificPermission();

            }
            else
            {
                fileChooser();
            }
        }
        else
        {
            return false;
        }
        return true;
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
        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);







    }

    public void fileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }

    private boolean checkIfAlreadyHavePermission()
    {
        int res = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission()
    {
        ActivityCompat.requestPermissions(BeforeAfterActivity.this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_READ_PERMISSION_GRANT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case EXTERNAL_READ_PERMISSION_GRANT:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    if (isCamera)
                        CallCameraIntent();
                    else
                        fileChooser();
                }
                else
                {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied !", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                if (data!=null){
                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    uploadFile(new File(picturePath), userID);

                    isImage = true;
                }

            }
        }
        else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                if (mCapturedImageURI!=null){
//                    mCapturedImageURI = (Uri) data.getExtras().get("data");
//                    uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);


                    Uri selectedImage = (Uri) mCapturedImageURI;

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};


                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    uploadFile(new File(picturePath), userID);


                    isImage = true;
                }

            }
        }
    }


    private void showDatePickerDailog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        datepickerdialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);





        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });


    }


    private void uploadFile(File file, int userId)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        File fileTemp = null;
        try {
            fileTemp = new Compressor(this).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), fileTemp);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", fileTemp.getName(), photoContent);

        BeforeAfterService uploadService = Client.getClientMultiPart().create(BeforeAfterService.class);

        RequestBody user_Id = RequestBody.create(MediaType.parse("multipart/form-MainModel"), ""+userId);
        RequestBody is_After = RequestBody.create(MediaType.parse("multipart/form-MainModel"), ""+isAfter);
        RequestBody filename = RequestBody.create(MediaType.parse("multipart/form-MainModel"), fileTemp.getName());
        RequestBody uplaodtime = RequestBody.create(MediaType.parse("multipart/form-MainModel"), strSubmitDateTime.toString());

        Call<BeforeAfterResponse> call = uploadService.Upload(photo, user_Id, is_After, filename,uplaodtime);
        call.enqueue(new Callback<BeforeAfterResponse>()
        {

            @Override
            public void onResponse(Call<BeforeAfterResponse> call, Response<BeforeAfterResponse> response)
            {
                //ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> data = null;

                utils.hideProgressbar();

                if (response.isSuccessful())
                {

                    if (response.body() != null)
                    {
                        BeforeAfterResponse dataResponse = response.body();

                        if (dataResponse.getCode() == 1)
                        {
                            fetchData();
                        }
                        Toast.makeText(getApplicationContext(), "Uploaded successfully !", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<BeforeAfterResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                fetchData();
//                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchData()
    {
        try
        {
            if (!((Activity) context).isFinishing())
            {
                utils.showProgressbar(context);
            }

            TempReqForBeforeAfter request = new TempReqForBeforeAfter();
            request.setUserId(userID);

            BeforeAfterService beforeAfterService  = Client.getClient().create(BeforeAfterService.class);
            Call<BeforeAfterResponse> call = beforeAfterService.getAllImages(request);
            call.enqueue(new Callback<BeforeAfterResponse>()
            {
                @Override
                public void onResponse(Call<BeforeAfterResponse> call, Response<BeforeAfterResponse> response)
                {
                    utils.hideProgressbar();

                    /*ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> beforeList;
                    ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> afterList;*/

                    final ArrayList<String> beforeUrls;
                    final ArrayList<String> afterUrls;

                    final ArrayList<String> beforetime;
                    final ArrayList<String> aftertime;

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        BeforeAfterResponse listResponse = response.body();

                        if (listResponse != null && listResponse.getCode() == 1)
                        {
                            beforeAfterDataList = listResponse.getData();
                        }

                        if (beforeAfterDataList != null && beforeAfterDataList.size() >0)
                        {
                            beforeUrls = new ArrayList<>();
                            afterUrls = new ArrayList<>();
                            beforetime=new ArrayList<>();
                            aftertime=new ArrayList<>();
                            beforeID=new ArrayList<>();
                            afterID=new ArrayList<>();


                            for (int i=0; i<beforeAfterDataList.size(); i++)
                            {
                                if (!TextUtils.isEmpty(beforeAfterDataList.get(i).getBeforeFilePath()))
                                {
                                    if (beforeUrls.size() < 300)
                                    {
                                        beforeUrls.add(beforeAfterDataList.get(i).getBeforeFilePath());
                                        beforetime.add(beforeAfterDataList.get(i).getUploadedOn().toString());
                                        beforeID.add(String.valueOf(beforeAfterDataList.get(i).getID()));

                                    }
                                }

                                if (!TextUtils.isEmpty((CharSequence) beforeAfterDataList.get(i).getAfterFilePath()))
                                {
                                    if (afterUrls.size() < 300)
                                    {
                                        afterUrls.add(beforeAfterDataList.get(i).getAfterFilePath());
                                        aftertime.add(beforeAfterDataList.get(i).getUploadedOn().toString());
                                        afterID.add(String.valueOf(beforeAfterDataList.get(i).getID()));


                                    }
                                }
                                  /*  if (!TextUtils.isEmpty(beforeAfterDataList.get(i).getBeforeFilePath()))
                                    {
                                        if (beforeList.size() < 3)
                                        {
                                            beforeList.add(beforeAfterDataList.get(i));
                                        }
                                    }
                                    if (!TextUtils.isEmpty((CharSequence) beforeAfterDataList.get(i).getAfterFilePath()))
                                    {
                                        if (afterList.size() < 3)
                                        {
                                            afterList.add(beforeAfterDataList.get(i));
                                        }
                                    }*/
                            }

                            if (beforeUrls != null && beforeUrls.size() > 0)
                            {
                                beforeAfterAdapter = new BeforeAfterAdapter(context, beforeUrls,
                                        BeforeAfterActivity.this,beforetime,beforeID);
                                rvBefore.setLayoutManager(new LinearLayoutManager(BeforeAfterActivity.this, LinearLayoutManager.HORIZONTAL, false));

//                                RecyclerView.LayoutManager manager = new GridLayoutManager(BeforeAfterActivity.this, 3);
//                                rvBefore.setLayoutManager(manager);
                                rvBefore.setItemAnimator(new DefaultItemAnimator());
                                rvBefore.setAdapter(beforeAfterAdapter);
                            }

                            if (afterUrls != null && afterUrls.size() > 0)
                            {
                                beforeAfterAdapter = new BeforeAfterAdapter(context, afterUrls,
                                        BeforeAfterActivity.this,aftertime,afterID);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BeforeAfterActivity.this,LinearLayoutManager.HORIZONTAL);

//                                RecyclerView.LayoutManager manager = new GridLayoutManager(BeforeAfterActivity.this, 3);
//                                rvAfter.setLayoutManager(layoutManager);
                                rvAfter.setLayoutManager(new LinearLayoutManager(BeforeAfterActivity.this, LinearLayoutManager.HORIZONTAL, false));

                                rvAfter.setItemAnimator(new DefaultItemAnimator());
                                rvAfter.setAdapter(beforeAfterAdapter);
                            }
                            beforeAfterAdapter.notifyDataSetChanged();

                            txt_comparebefore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Dialog     dialog1=new Dialog(context,R.style.CustomDialog);
                                    dialog1.setContentView(R.layout.dailog_lay_comapre);
                                    AppCompatImageView img_fash=dialog1.findViewById(R.id.img_fash);
                                    img_fash.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog1.dismiss();
                                        }
                                    });
//                                    ImageView imgclose=findViewById(R.id.imgclose);
//                                    imgclose.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            dialog.dismiss();
//                                        }
//                                    });
                                    RecyclerView img_before=dialog1.findViewById(R.id.img_before);
                                    RecyclerView img_after=dialog1.findViewById(R.id.img_after);
                                    NewBeforeAfterAdapter     beforeAfterAdapter1 = new NewBeforeAfterAdapter(context, beforeUrls,beforetime,beforeID);

//                                    RecyclerView.LayoutManager manager = new GridLayoutManager(BeforeAfterActivity.this, 1);
//                                    img_before.setLayoutManager(manager);
                                    img_before.setItemAnimator(new DefaultItemAnimator());
                                    img_before.setAdapter(beforeAfterAdapter1);


                                    NewBeforeAfterAdapter beforeAfterAdapter2 = new NewBeforeAfterAdapter(context, afterUrls,
                                            aftertime,afterID);

//                                    RecyclerView.LayoutManager manager2 = new GridLayoutManager(BeforeAfterActivity.this, 1);
//                                    img_after.setLayoutManager(manager2);
                                    img_after.setItemAnimator(new DefaultItemAnimator());
                                    img_after.setAdapter(beforeAfterAdapter2);
                                    dialog1.show();
                                }
                            });


                            txt_compareafter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Dialog     dialogs=new Dialog(context,R.style.CustomDialog);
                                    dialogs.setContentView(R.layout.dailog_lay_comapre);
                                    AppCompatImageView img_fash=dialogs.findViewById(R.id.img_fash);
                                    img_fash.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogs.dismiss();
                                        }
                                    });
//                                    ImageView imgclose=findViewById(R.id.imgclose);
//                                    imgclose.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            dialog.dismiss();
//                                        }
//                                    });
                                    RecyclerView img_before=dialogs.findViewById(R.id.img_before);
                                    RecyclerView img_after=dialogs.findViewById(R.id.img_after);
                                    NewBeforeAfterAdapter     beforeAfterAdapter1 = new NewBeforeAfterAdapter(context, beforeUrls,beforetime,beforeID);

//                                    RecyclerView.LayoutManager manager = new GridLayoutManager(BeforeAfterActivity.this, 1);
//                                    img_before.setLayoutManager(manager);
                                    img_after.setItemAnimator(new DefaultItemAnimator());
                                    img_after.setAdapter(beforeAfterAdapter1);


                                    NewBeforeAfterAdapter beforeAfterAdapter2 = new NewBeforeAfterAdapter(context, afterUrls,
                                            aftertime,afterID);

//                                    RecyclerView.LayoutManager manager2 = new GridLayoutManager(BeforeAfterActivity.this, 1);
//                                    img_after.setLayoutManager(manager2);
                                    img_before.setItemAnimator(new DefaultItemAnimator());
                                    img_before.setAdapter(beforeAfterAdapter2);
                                    dialogs.show();
                                }
                            });


                        }
                    }
                }

                @Override
                public void onFailure(Call<BeforeAfterResponse> call, Throwable t)
                {
                    utils.hideProgressbar();
                }
            });

        }
        catch (Exception e){e.printStackTrace();}
    }

    private void DeleteData(int ids)
    {
        try
        {
            if (!((Activity) context).isFinishing())
            {
                utils.showProgressbar(context);
            }





            BeforeAfterService beforeAfterService  = Client.getClient().create(BeforeAfterService.class);
            Call<ClsEditSleepResonse> call = beforeAfterService.getDeleteUserBeforeAfters(ids);
            call.enqueue(new Callback<ClsEditSleepResonse>()
            {
                @Override
                public void onResponse(Call<ClsEditSleepResonse> call, Response<ClsEditSleepResonse> response)
                {
                    utils.hideProgressbar();

                    /*ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> beforeList;
                    ArrayList<BeforeAfterResponse.BeforeAfterDataResponse> afterList;*/


                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsEditSleepResonse listResponse = response.body();

                        if (listResponse != null && listResponse.getCode().equals("1"))
                        {
                            Toast.makeText(BeforeAfterActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            fetchData();


                        }


                    }
                }

                @Override
                public void onFailure(Call<ClsEditSleepResonse> call, Throwable t)
                {
                    utils.hideProgressbar();
                }
            });

        }
        catch (Exception e){e.printStackTrace();}
    }

    /**
     * This method is used to get real path of file from from uri
     *
     * @param contentUri
     * @return String
     */
    //----------------------------------------
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
        catch (Exception e) {return contentUri.getPath();}
    }

    @Override
    public void GetPositionData(final int iddelete, boolean isDelete)
    {
        if (isDelete){


            AlertDialog.Builder builder = new AlertDialog.Builder(context);


            builder.setMessage("Are you sure want to delete ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            DeleteData(iddelete);
//                            http://localhost:62096//api/BeforeAfter/DeleteUserBeforeAfters?Id=2183


                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        }
                    });
            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.show();
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        strSubmitDateTime=new StringBuilder();
        stringBuilder_datetime=new StringBuilder();
        String dummydateentry = dayOfMonth + "-" + (month + 1) + "-" + year;

        strSubmitDateTime.append(year+ "-"+ (month + 1)+"-"+dayOfMonth);
        stringBuilder_datetime.append(dummydateentry);
        timepickerdialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String lsTimeFrom = formatter.format(cal.getTime());
        stringBuilder_datetime.append(" ").append(lsTimeFrom);
        strSubmitDateTime.append(" ").append(lsTimeFrom);
        txt_date_time.setText(stringBuilder_datetime.toString());

    }






   /* @Override
    public void GetPositionData(int pos, BeforeAfterResponse.BeforeAfterDataResponse model)
    {

    }*/

     /*imageUrl = response.body().getImageUrl();
                            imageUrlNew = response.body().getImageUrl();
                            sessionManager.setStringValue(SessionManager.KEY_USER_PROFILE_IMAGE, imageUrl);

                            if (isValidContextForGlide(context))
                            {
                                Glide.with(context)
                                        .load(imageUrl).transition(DrawableTransitionOptions.withCrossFade())
                                        .apply(RequestOptions.circleCropTransform()
                                                .skipMemoryCache(true)
                                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                                .error(R.drawable.ic_profile_pic_bg))
                                        .into(imageViewProfile);
                            }*/



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
