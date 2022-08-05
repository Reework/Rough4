package com.shamrock.reework.activity.Reemember.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
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
import com.shamrock.reework.activity.MedicineModule.activity.MyMedicinesActivity;
import com.shamrock.reework.activity.MedicineModule.service.EatingRequest;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.adapter.AutoSuggestAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.MedicineRequest;
import com.shamrock.reework.api.response.MedicineListResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.DaysUtils;
import com.shamrock.reework.util.MultiSelectionSpinner;
import com.shamrock.reework.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;

public class AddMedicineDialogNewFragment extends DialogFragment implements View.OnClickListener,
        MultiSelectionSpinner.OnMultipleItemsSelectedListener,TimePickerDialog.OnTimeSetListener
{
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
            FILE_SELECT_REQUEST_CODE = 300;
    private Uri mCapturedImageURI;
    private MedicineService medService;
    private Utils utils;
    private static final String SEPARATOR = ",";
    private String errorMsg;
    private TextView text_time_daily;
    private TextView text_time_trice;
    private TextView text_time_twice;
    private String fromFrequency="daily";
    private String fromEatingStatus="daily";
    private String time;
    private LinearLayout ll_1;
    private LinearLayout ll_2;
    private LinearLayout ll_3;
    public boolean isCamera = false;
    List<MedicineListResponse.DataResponse> medList;

    String[] frequency_whenArray ;
    String[] frequency_whenArray_api ;
    ArrayAdapter<String> adapter_whens;
    ImageView img_add_medicine;
    Switch switch_noti;

    private String strEatingStatus1;
    private String strEatingStatus2;
    private String strEatingStatus3;
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;


    private String strSubmitWhen="";
    private String strSubmitTime="";
    ReememberActivity myMedicinesActivity;
    private boolean IsNotification;

    public AddMedicineDialogNewFragment(ReememberActivity myMedicinesActivity) {
        this.myMedicinesActivity=myMedicinesActivity;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        Date tDate = new Date();
        //SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormatter.format(tDate);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);

        Format formatter;
        formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        time = formatter.format(cal.getTime());

        switch (fromFrequency){




            case "daily":

                text_time_daily.setText(time);
                break;

            case "twice":

                text_time_twice.setText(time);
                break;


            case "thrice":

                text_time_trice.setText(time);
                break;
        }





    }

    public interface AddMedicineDialogListener
    {

        void onMedicineAdd(int medicineId, String frequency, String inputText, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, boolean onMedicineAdd);

        void onMedicineEdit(int myMedID, int medicineId, String frequency, String days, JSONArray eatingGSon, ArrayList<Object> list, File fileuploadimage, String medicineName, String imageName, String imagepath, boolean onMedicineAdd);

    }

    Context context;
    AutoCompleteTextView actv;
    TextView textViewTitle;
    Button btnSubmit;
    Spinner spinnerFreq;
    TextView spinnerAddMedicine_Days_dummy;
    Spinner spinnerAddMedicine_when;
    Spinner spinnerAddMedicine_when2;
    Spinner spinnerAddMedicine_when3;
    MultiSelectionSpinner spinnerDays;
    List<Integer> indices;
    ImageView imgClose;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;

    String medicineName;
    int userId;
    int medicineId = 0;
    String frequency = "Select frequency";
    int when1 = 0;
    int when2 = 0;
    int when3 = 0;
    String days = "";

    File fileuploadimage=new File("");
    private boolean isEditData;
    private MyMedicine myMedicine;
    private int myMedicineID;

    TimePickerDialog timepickerdialog;

    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        indices = new ArrayList<>();
        medService = Client.getClient().create(MedicineService.class);
        utils = new Utils();

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            try {
                isEditData = bundle.getBoolean("isEdit", false);
                myMedicine = (MyMedicine) bundle.getSerializable("myMedicine");
                myMedicineID = myMedicine.getMyMedId();
                medicineId = myMedicine.getMedId();
                days = myMedicine.getMedDays();
            }catch (Exception e){

            }
        }
    }

    private void AddMedicineImageDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage("Upload Medicine Image ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(myMedicinesActivity)
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

                        Dexter.withActivity(myMedicinesActivity)
                                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        fileChooser();                                    }

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


                        if (!checkIfAlreadyHavePermission())
                        {
                            isCamera = false;
                            requestForSpecificPermission();

                        }
                        else
                        {

                        }
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
        Uri uri = Uri.fromParts("package", myMedicinesActivity.getPackageName(), null);
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
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void fileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }

    private boolean checkIfAlreadyHavePermission()
    {
        int res = ContextCompat.checkSelfPermission(myMedicinesActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission()
    {
        ActivityCompat.requestPermissions(myMedicinesActivity,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_READ_PERMISSION_GRANT);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_add_medicine, container, false);

        textViewTitle = view.findViewById(R.id.labelMedicineTitle);
        img_add_medicine = view.findViewById(R.id.img_add_medicine);
        switch_noti = view.findViewById(R.id.switch_noti);
        switch_noti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                IsNotification=isChecked;
            }
        });
        img_add_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddMedicineImageDailog();
            }
        });
        text_time_daily = view.findViewById(R.id.text_time_daily);
        text_time_twice = view.findViewById(R.id.text_time_twice);
        text_time_trice = view.findViewById(R.id.text_time_trice);
        spinnerAddMedicine_Days_dummy = view.findViewById(R.id.spinnerAddMedicine_Days_dummy);
        ll_1 = view.findViewById(R.id.ll_1);
        ll_2 = view.findViewById(R.id.ll_2);
        ll_3 = view.findViewById(R.id.ll_3);
        frequency_whenArray= getResources().getStringArray(R.array.medicine_frequency_when);
        adapter_whens  = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, frequency_whenArray);

        actv = view.findViewById(R.id.edtText_NameMedicine);
        btnSubmit = view.findViewById(R.id.buttonAddMedicine);
        imgClose = view.findViewById(R.id.imageView_AddMedicine_CloseDialog);
        spinnerDays = view.findViewById(R.id.spinnerAddMedicine_Days);
        spinnerFreq = view.findViewById(R.id.spinnerAddMedicine_Frequency);

        spinnerAddMedicine_when = view.findViewById(R.id.spinnerAddMedicine_when1);
        spinnerAddMedicine_when2 = view.findViewById(R.id.spinnerAddMedicine_when2);
        spinnerAddMedicine_when3 = view.findViewById(R.id.spinnerAddMedicine_when3);

        text_time_daily.setOnClickListener(this);
        text_time_twice.setOnClickListener(this);
        text_time_trice.setOnClickListener(this);
        getDialog().setCancelable(true);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timepickerdialog = new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, hour, minute, false);
        timepickerdialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                               @Override
                                               public void onShow(DialogInterface dialog) {
                                                   Button buttonNeg = timepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                                                   buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                                   Button buttonPos = timepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                                   buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


                                               }
                                           });

//        int[] args = getArguments().getIntArray("args");
        final String[] frequencyArray = getResources().getStringArray(R.array.medicine_frequency);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, frequencyArray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFreq.setAdapter(adapter);
        spinnerFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                frequency = frequencyArray[i].toString();
                String strFreq=frequencyArray[i].toString();
                switch (i){

                    case 1:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.GONE);
                        ll_3.setVisibility(View.GONE);
                        fromEatingStatus="daily";
                         break;
                    case 2:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.VISIBLE);
                        ll_3.setVisibility(View.GONE);
                        fromEatingStatus="twice";

                        break;

                    case 3:
                        ll_1.setVisibility(View.VISIBLE);
                        ll_2.setVisibility(View.VISIBLE);
                        ll_3.setVisibility(View.VISIBLE);
                        fromEatingStatus="thrice";

                        break;

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        spinnerAddMedicine_Days_dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerDays.performClick();
            }
        });



        spinnerAddMedicine_when.setAdapter(adapter_whens);
        spinnerAddMedicine_when.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {

                strEatingStatus1=frequency_whenArray[i];
                when1=i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinnerAddMedicine_when2.setAdapter(adapter_whens);
        spinnerAddMedicine_when2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                strEatingStatus2=frequency_whenArray[i];
                when2=i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        spinnerAddMedicine_when3.setAdapter(adapter_whens);
        spinnerAddMedicine_when3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                strEatingStatus3=frequency_whenArray[i];
                when3=i;



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });












        String[] array = getResources().getStringArray(R.array.medicine_days);
        spinnerDays.setItems(array);
        spinnerDays.setSelection(new int[]{});
        spinnerDays.setListener(this);

//        spinnerDays.setListener(new MultiSelectionSpinner.OnMultipleItemsSelectedListener() {
//            @Override
//            public void selectedIndices(List<Integer> indices) {
//
//            }
//
//            @Override
//            public void selectedStrings(List<String> strings) {
//
//            }
//        });


        imgClose.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        if (!isEditData) {
            textViewTitle.setText(getString(R.string.add_medicine));
        } else {
            textViewTitle.setText(getString(R.string.edit_medicine));
        }
        if (!isEditData) {

            //Setting up the adapter for AutoSuggest
            autoSuggestAdapter = new AutoSuggestAdapter(context, android.R.layout.simple_dropdown_item_1line);
            actv.setThreshold(3);
            actv.setAdapter(autoSuggestAdapter);
            actv.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
//                        selectedText.setText(autoSuggestAdapter.getObject(position));

                            days=medList.get(position).getDosage();
                            frequency_whenArray_api= getResources().getStringArray(R.array.medicine_frequency_when);

                            if (medList.get(position).getFrequencyList().size()==1){
                                fromEatingStatus="daily";

                                ll_1.setVisibility(View.VISIBLE);
                                text_time_daily.setText(medList.get(position).getFrequencyList().get(0).getEatingTime());


                                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                                    if (medList.get(position).getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                                        spinnerAddMedicine_when.setSelection(i);
                                        break;

                                    }
                                }




                            }

                            if (medList.get(position).getFrequencyList().size()==2){
                                ll_1.setVisibility(View.VISIBLE);
                                ll_2.setVisibility(View.VISIBLE);

                                fromEatingStatus="twice";


                                text_time_daily.setText(medList.get(position).getFrequencyList().get(0).getEatingTime());
                                text_time_twice.setText(medList.get(position).getFrequencyList().get(1).getEatingTime());


                                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                                    if (medList.get(position).getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                                        spinnerAddMedicine_when.setSelection(i);
                                        break;

                                    }
                                }


                                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                                    if (myMedicine.getFrequencyList().get(1).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                                        spinnerAddMedicine_when2.setSelection(i);
                                        break;

                                    }
                                }




                            }

                            if (medList.get(position).getFrequencyList().size()==3){
                                ll_1.setVisibility(View.VISIBLE);
                                ll_2.setVisibility(View.VISIBLE);
                                ll_3.setVisibility(View.VISIBLE);
                                fromEatingStatus="thrice";



                                text_time_daily.setText(medList.get(position).getFrequencyList().get(0).getEatingTime());
                                text_time_twice.setText(medList.get(position).getFrequencyList().get(1).getEatingTime());
                                text_time_trice.setText(medList.get(position).getFrequencyList().get(2).getEatingTime());


                                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                                    if (medList.get(position).getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                                        spinnerAddMedicine_when.setSelection(i);
                                        break;

                                    }
                                }


                                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                                    if (medList.get(position).getFrequencyList().get(1).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                                        spinnerAddMedicine_when2.setSelection(i);
                                        break;

                                    }
                                }


                                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                                    if (medList.get(position).getFrequencyList().get(2).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                                        spinnerAddMedicine_when3.setSelection(i);
                                        break;

                                    }
                                }


                            }



                            frequency_whenArray_api= getResources().getStringArray(R.array.medicine_frequency_when);


                            for (int i = 0; i <frequencyArray.length ; i++) {

                                if (medList.get(position).getFrequency().equalsIgnoreCase(frequencyArray[i].toString())){
                                    spinnerFreq.setSelection(i);
                                    break;

                                }
                            }



                            try {
                           String     newDays=medList.get(position).getDosage();

                                if (newDays.equalsIgnoreCase("2,3,4,5,6,7,8")){

                                    String newDaysstr="1,"+newDays;
                                    spinnerDays.setSelection(DaysUtils.getIntArray(newDaysstr, ","));

                                }else {
                                    spinnerDays.setSelection(DaysUtils.getIntArray(newDays, ","));

                                }



                                String str = newDays;
                                ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(str.split(",")));

                                StringBuilder stringBuilder=new StringBuilder();
                                for (int i = 0; i <elephantList.size() ; i++) {


                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("2")){
                                        stringBuilder.append("Sun,");
                                    }

                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("3")){

                                        stringBuilder.append("Mon,");

                                    }
                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("4")){
                                        stringBuilder.append("Tue,");

                                    }
                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("5")){

                                        stringBuilder.append("Wed,");

                                    }
                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("6")){
                                        stringBuilder.append("Thu,");

                                    }
                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("7")){
                                        stringBuilder.append("Fri,");

                                    }
                                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("8")){
                                        stringBuilder.append("Sat,");

                                    }
                                }

                                spinnerAddMedicine_Days_dummy.setText(removeLastChar(stringBuilder.toString()));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            medicineName = autoSuggestAdapter.getObject(position).getMedicineName();
                            medicineId = autoSuggestAdapter.getObject(position).getMymedid();
                            Glide.with(context)

                                    .load(medList.get(position).getImgPath())
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                            return false;
                                        }
                                    })
//                                            .apply(options)

                                    .apply(RequestOptions.circleCropTransform())
                                    .into(img_add_medicine);
                        }
                    });

            actv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int
                        count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                    handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == TRIGGER_AUTO_COMPLETE) {
                        if (!TextUtils.isEmpty(actv.getText())) {
                            makeApiCall(actv.getText().toString());
                        }
                    }
                    return false;
                }
            });
        } else {
            actv.setText(myMedicine.getMedName());
            switch_noti.setChecked(myMedicine.IsNotification());

            actv.setEnabled(false);
            String newDays="";

            Glide.with(context) 

                    .load(myMedicine.getPath())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .apply(RequestOptions.circleCropTransform())

//                                            .apply(options)
                    .into(img_add_medicine);



            frequency_whenArray_api= getResources().getStringArray(R.array.medicine_frequency_when);


            for (int i = 0; i <frequencyArray.length ; i++) {

                if (myMedicine.getMedFreq().equalsIgnoreCase(frequencyArray[i].toString())){
                    spinnerFreq.setSelection(i);
                    break;

                }
            }


            if (myMedicine.getFrequencyList().size()==1){
                fromEatingStatus="daily";

                ll_1.setVisibility(View.VISIBLE);
                text_time_daily.setText(myMedicine.getFrequencyList().get(0).getEatingTime());


                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                    if (myMedicine.getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                        spinnerAddMedicine_when.setSelection(i);
                        break;

                    }
                }




            }

            if (myMedicine.getFrequencyList().size()==2){
                ll_1.setVisibility(View.VISIBLE);
                ll_2.setVisibility(View.VISIBLE);

                fromEatingStatus="twice";


                text_time_daily.setText(myMedicine.getFrequencyList().get(0).getEatingTime());
                text_time_twice.setText(myMedicine.getFrequencyList().get(1).getEatingTime());


                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                    if (myMedicine.getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                        spinnerAddMedicine_when.setSelection(i);
                        break;

                    }
                }


                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                    if (myMedicine.getFrequencyList().get(1).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                        spinnerAddMedicine_when2.setSelection(i);
                        break;

                    }
                }




            }

            if (myMedicine.getFrequencyList().size()==3){
                ll_1.setVisibility(View.VISIBLE);
                ll_2.setVisibility(View.VISIBLE);
                ll_3.setVisibility(View.VISIBLE);
                fromEatingStatus="thrice";



                text_time_daily.setText(myMedicine.getFrequencyList().get(0).getEatingTime());
                text_time_twice.setText(myMedicine.getFrequencyList().get(1).getEatingTime());
                text_time_trice.setText(myMedicine.getFrequencyList().get(2).getEatingTime());


                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                    if (myMedicine.getFrequencyList().get(0).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                        spinnerAddMedicine_when.setSelection(i);
                        break;

                    }
                }


                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                    if (myMedicine.getFrequencyList().get(1).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                        spinnerAddMedicine_when2.setSelection(i);
                        break;

                    }
                }


                for (int i = 0; i <frequency_whenArray_api.length ; i++) {
                    if (myMedicine.getFrequencyList().get(2).getEatingStatus().equalsIgnoreCase(frequency_whenArray_api[i].toString())){
                        spinnerAddMedicine_when3.setSelection(i);
                        break;

                    }
                }


            }




            try {
                newDays=myMedicine.getMedDays();

                if (newDays.equalsIgnoreCase("2,3,4,5,6,7,8")){

                    String newDaysstr="1,"+newDays;
                    spinnerDays.setSelection(DaysUtils.getIntArray(newDaysstr, ","));

                }else {
                    spinnerDays.setSelection(DaysUtils.getIntArray(newDays, ","));

                }



                String str = newDays;
                ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(str.split(",")));

                StringBuilder stringBuilder=new StringBuilder();
                for (int i = 0; i <elephantList.size() ; i++) {


                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("2")){
                        stringBuilder.append("Sun,");
                    }

                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("3")){

                        stringBuilder.append("Mon,");

                    }
                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("4")){
                        stringBuilder.append("Tue,");

                    }
                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("5")){

                        stringBuilder.append("Wed,");

                    }
                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("6")){
                        stringBuilder.append("Thu,");

                    }
                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("7")){
                        stringBuilder.append("Fri,");

                    }
                    if (elephantList.get(i).toString().trim().equalsIgnoreCase("8")){
                        stringBuilder.append("Sat,");

                    }
                }

                spinnerAddMedicine_Days_dummy.setText(removeLastChar(stringBuilder.toString()));

            } catch (Exception e) {
                e.printStackTrace();
            }





        }


        return view;
    }
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
    private void makeApiCall(String text)
    {
        MedicineRequest request = new MedicineRequest();
        request.setMedicineName(text);
        userId = new SessionManager(context).getIntValue(SessionManager.KEY_USER_ID);

        request.setUserId(userId);

        Call<MedicineListResponse> call = medService.downloadMedicines(request);
        call.enqueue(new Callback<MedicineListResponse>()
        {
            @Override
            public void onResponse(Call<MedicineListResponse> call, retrofit2.Response<MedicineListResponse> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    MedicineListResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        //parsing logic, please change it as per your requirement
                        List<String> stringList = new ArrayList<>();
                        medList = listResponse.getData();
                        try
                        {
//                            JSONObject responseObject = new JSONObject(response);
//                            JSONArray array = responseObject.getJSONArray("results");
                            for (int i = 0; i < medList.size(); i++)
                            {
//                                JSONObject row = array.getJSONObject(i);
                                stringList.add(medList.get(i).getMedicineName());
                            }
                        } catch (Exception e) { e.printStackTrace();}

                        //IMPORTANT: set data here and notify
                        autoSuggestAdapter.setData(stringList, medList);
                        autoSuggestAdapter.notifyDataSetChanged();
                    }
                    else
                    {
//                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
//                utils.hideProgressbar();
            }

            @Override
            public void onFailure(Call<MedicineListResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
//                utils.hideProgressbar();
            }
        });
    }

    @Override
    public void selectedIndices(List<Integer> indices) {
        this.indices = indices;
        if (indices.size() > 0) {
            StringBuilder strIds = new StringBuilder();
            for (int city : indices) {
                strIds.append((city + 1));
                strIds.append(SEPARATOR);
            }


            try {
                if (indices.size()==8){
                    String s=new String(strIds);
                    String strnew=s.replace("1,","");
                    days = String.valueOf(strnew.substring(0, strnew.length() - 1));
                }else {
                    days = String.valueOf(strIds.substring(0, strIds.length() - 1));
                }


//                days = String.valueOf(strIds.substring(0, strIds.length() - 1));    //remove last comma and return commaSeparatedIds
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            days = "";
        }
//        Toast.makeText(context, "" + days, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectedStrings(List<String> strings) {

//        Toast.makeText(context, "" + strings.toString(), Toast.LENGTH_SHORT).show();

        try {
            String strdummyDays=new String(strings.toString());

            if (strdummyDays.contains("Select All,")){
                String strdummyDayss=strdummyDays.toString().replace("Select All,","");

                String first=strdummyDayss.replace("[","");
                String second=first.replace("]","");

                spinnerAddMedicine_Days_dummy.setText(second);
            }else {

                String firsts=strdummyDays.replace("[","");
                String seconds=firsts.replace("]","");
                spinnerAddMedicine_Days_dummy.setText(seconds.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.text_time_daily:
                fromFrequency="daily";
                timepickerdialog.show();


                break;


            case R.id.text_time_twice:
                fromFrequency="twice";
                timepickerdialog.show();
                break;

            case R.id.text_time_trice:
                fromFrequency="thrice";
                timepickerdialog.show();
                break;
            case R.id.buttonAddMedicine:

                if (actv.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter Medicine name", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean dataIsValid = validateAllData(medicineId, frequency, days,when1,fromFrequency);
                if (dataIsValid)
                {



                    ArrayList<EatingRequest> medicineRequests=new ArrayList<>();
                    if (fromEatingStatus.equalsIgnoreCase("daily")){
                        if (isEditData){
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),myMedicine.getFrequencyList().get(0).getId()));

                        }else {
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),0));

                        }

                    }

                    if (fromEatingStatus.equalsIgnoreCase("twice")){
                        if (isEditData){
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),myMedicine.getFrequencyList().get(0).getId()));
                            if (myMedicine.getFrequencyList().size()>1){
                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),myMedicine.getFrequencyList().get(1).getId()));

                            }else {
                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),0));

                            }
                        }else {
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),0));
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),0));
                        }


                    }

                    if (fromEatingStatus.equalsIgnoreCase("thrice")){

                        if (isEditData){
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),myMedicine.getFrequencyList().get(0).getId()));
                            if (myMedicine.getFrequencyList().size()>1){
                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),myMedicine.getFrequencyList().get(1).getId()));

                            }else {
                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),0));

                            }
                            if (myMedicine.getFrequencyList().size()>2){
                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when3.getSelectedItem().toString().trim(),text_time_trice.getText().toString().trim(),myMedicine.getFrequencyList().get(2).getId()));

                            }else {
                                medicineRequests.add(new EatingRequest(spinnerAddMedicine_when3.getSelectedItem().toString().trim(),text_time_trice.getText().toString().trim(),0));

                            }
                        }else {
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when.getSelectedItem().toString().trim(),text_time_daily.getText().toString().trim(),0));

                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when2.getSelectedItem().toString().trim(),text_time_twice.getText().toString().trim(),0));
                            medicineRequests.add(new EatingRequest(spinnerAddMedicine_when3.getSelectedItem().toString().trim(),text_time_trice.getText().toString().trim(),0));
                        }


                    }
                    JSONArray array = new JSONArray();
                    try {
                        for (int i = 0; i < medicineRequests.size(); i++) {
                            JSONObject json = new JSONObject(); // update here
                            json.put("EatingStatus", medicineRequests.get(i).getEatingStatus());
                            json.put("EatingTime", medicineRequests.get(i).getEatingTime());
                            json.put("Id", medicineRequests.get(i).getId());
                            array.put(json).toString().replaceAll("\"", "");

                        }
                    }

                  catch (JSONException je) {
                      je.printStackTrace();
                        }

                    ArrayList<Object> list = convert(array);

                  if (fromEatingStatus.equalsIgnoreCase("daily")){
                      strSubmitWhen=spinnerAddMedicine_when.getSelectedItem().toString().trim();
                      strSubmitTime=text_time_daily.getText().toString().trim();

                  }
                    if (fromEatingStatus.equalsIgnoreCase("twice")){
                        strSubmitWhen=spinnerAddMedicine_when.getSelectedItem().toString().trim()+","+spinnerAddMedicine_when2.getSelectedItem().toString().trim();
                        strSubmitTime=text_time_daily.getText().toString().trim()+","+text_time_twice.getText().toString().trim();

                    }

                    if (fromEatingStatus.equalsIgnoreCase("thrice")){
                        strSubmitWhen=spinnerAddMedicine_when.getSelectedItem().toString().trim()+","+spinnerAddMedicine_when2.getSelectedItem().toString().trim()+","+spinnerAddMedicine_when3.getSelectedItem().toString().trim();
                        strSubmitTime=text_time_daily.getText().toString().trim()+","+text_time_twice.getText().toString().trim()+","+text_time_trice.getText().toString().trim();

                    }

//                    strSubmitWhen=spinnerAddMedicine_when.getSelectedItem().toString().trim()+","+spinnerAddMedicine_when2.getSelectedItem().toString().trim()+","+spinnerAddMedicine_when3.getSelectedItem().toString().trim();
//                    strSubmitTime=text_time_daily.getText().toString().trim()+","+text_time_twice.getText().toString().trim()+","+text_time_trice.getText().toString().trim();
                    if (isEditData)
                    {
                        updateData(myMedicineID, medicineId, frequency, days,array,list,fileuploadimage,actv.getText().toString(),myMedicine.getImageName(),myMedicine.getPath(),IsNotification);
                    }
                    else
                    {
                        sendData(medicineId, frequency, days,array,list,fileuploadimage,actv.getText().toString(),actv.getText().toString(),IsNotification);
                    }
                }
                else
                {
                    Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.imageView_AddMedicine_CloseDialog:
                this.dismiss();
                break;

            default:
        }
    }


    public  ArrayList<Object> convert(JSONArray jArr)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                list.add(jArr.get(i));
            }
        } catch (JSONException e) {}

        return list;
    }

    public  JSONArray convert(Collection<Object> list)
    {
        return new JSONArray(list);
    }
    private void sendData(int medicineId, String frequency, String days, JSONArray EatingGSon, ArrayList<Object> list, File fileuploadimage, String name, String medicineName,boolean IsNotification)
    {
        AddMedicineDialogListener listener = (AddMedicineDialogListener) getActivity();
        listener.onMedicineAdd(medicineId, frequency, days,EatingGSon,list,fileuploadimage,medicineName,IsNotification);
    }

    private void updateData(int myMedID, int medicineId, String frequency, String days, JSONArray EatingGSon, ArrayList<Object> list, File fileuploadimage,String medicineName,String imageName,String imagepath,boolean IsNotification)
    {
        AddMedicineDialogListener listener = (AddMedicineDialogListener) getActivity();
        listener.onMedicineEdit(myMedID, myMedID, frequency, days,EatingGSon,list,fileuploadimage,medicineName,imageName,imagepath,IsNotification);
    }

    private boolean validateAllData(int medicineId, String frequency, String days, int when, String fromFrequency) {
        boolean valid = true;

//        if (medicineId == 0) {
//            valid = false;
//            errorMsg = "Select medicine";
//            return valid;
//
//        }
        if (days.isEmpty()) {
            valid = false;
            errorMsg = "Select days";
            return valid;

        }
        if (frequency.equalsIgnoreCase("Select frequency")) {
            valid = false;
            errorMsg = "Select frequency";
            return valid;
        }
        switch (fromEatingStatus){

            case  "daily" :
                if (text_time_daily.getText().toString().isEmpty()){
                    errorMsg="Please select time for first slot";
                    valid = false;
                    return valid;
                }

                if (when1 == 0) {

                    valid = false;
                    errorMsg = "Select when from first slot";
                    return valid;
                }
                break;

            case  "twice" :

                if (text_time_daily.getText().toString().isEmpty()){
                    errorMsg="Please select time for first slot";

//                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    valid = false;
                    return valid;
                }


                if (when1 == 0) {

                    valid = false;
                    errorMsg = "Select when from first slot";
                    return valid;
                }


                if (strEatingStatus1.equalsIgnoreCase(strEatingStatus2)){
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }


                if (text_time_twice.getText().toString().isEmpty()){
                    errorMsg="Please select time for second slot";

                    valid = false;
                    return valid;
                }




                if (when2 == 0) {

                    valid = false;
                    errorMsg = "Select when from second slot";
                    return valid;

                }





                break;

            case  "thrice" :


                if (text_time_daily.getText().toString().isEmpty()){
                    errorMsg="Please select time for first slot";
                    valid = false;
                    return valid;
                }
                if (when1 == 0) {

                    valid = false;
                    errorMsg = "Select when from first slot";
                    return valid;

                }

                if (strEatingStatus1.equalsIgnoreCase(strEatingStatus2)){
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (text_time_twice.getText().toString().isEmpty()){
                    errorMsg="Please select time for second slot";

                    valid = false;
                    return valid;
                }
                if (when2 == 0) {

                    valid = false;
                    errorMsg = "Select when from second slot";
                    return valid;

                }
                if (strEatingStatus2.equalsIgnoreCase(strEatingStatus1)){
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (text_time_trice.getText().toString().isEmpty()){
                    errorMsg="Please select time for third slot";
                    valid = false;
                    return valid;
                }

                if (when3 == 0) {

                    valid = false;
                    errorMsg = "Select when from third slot";
                    return valid;

                }


                if (strEatingStatus1.equalsIgnoreCase(strEatingStatus2)){
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (strEatingStatus2.equalsIgnoreCase(strEatingStatus3)){
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                if (strEatingStatus3.equalsIgnoreCase(strEatingStatus1)){
                    valid = false;
                    errorMsg = "Already selected";
                    return valid;
                }
                break;
        }

        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = myMedicinesActivity.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                img_add_medicine.setImageBitmap(null);
                img_add_medicine.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                fileuploadimage=new File(picturePath);
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile (fileuploadimage.getPath ());
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                }
                catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString ());
                    t.printStackTrace ();
                }
//                uploadFile(new File(picturePath), userID);

//                isImage = true;
            }
        }
        else
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                fileuploadimage=new File(getRealPathFromURI(mCapturedImageURI));
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile (fileuploadimage.getPath ());
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                }
                catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString ());
                    t.printStackTrace ();
                }

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(myMedicinesActivity.getContentResolver(), mCapturedImageURI);
                    img_add_medicine.setImageBitmap(null);

                    img_add_medicine.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
//                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
//                isImage = true;
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = myMedicinesActivity.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e) {return contentUri.getPath();}
    }
}
