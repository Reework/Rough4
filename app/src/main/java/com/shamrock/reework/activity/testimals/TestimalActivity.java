package com.shamrock.reework.activity.testimals;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.BloodTestModule.activity.AllReportActivity;
import com.shamrock.reework.activity.BloodTestModule.pojo.OtherReportData;
import com.shamrock.reework.activity.BloodTestModule.service.ReeTestService;
import com.shamrock.reework.activity.HealthModule.activity.ViewPagerActivity;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestimalActivity extends AppCompatActivity {
    private FloatingActionButton fab_add_testimals;
    private RecyclerView recler_testimals;
    private Utils util;
    private SessionManager sessionManager;
    private int userID;
    Context context;
    private static final int BUFFER_SIZE = 1024 * 2;
    boolean isFile=true;
     Dialog dialog;
    private ReeTestService reeTestService;
    File fileuploadimage=new File("");
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;
    private String docFilePath="";

    ArrayList<OtherReportData> arylst_other_Data;
    private boolean isCamera;
    private Uri mCapturedImageURI;

    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";
     EditText editText;
     int testo_ID;
    private String filetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimal);
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Testimonials");
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        util = new Utils();
        context=TestimalActivity.this;
        reeTestService = Client.getClient().create(ReeTestService.class);


        sessionManager = new SessionManager(TestimalActivity.this);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        addTestimals();

        fab_add_testimals=findViewById(R.id.fab_add_testimals);
        fab_add_testimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getMytestominals();

                if(sessionManager.getStringValue("notallowed").equalsIgnoreCase("true")){
                    shownoplan();
                    return;
                }

                addTestominal();

            }
        });

        recler_testimals=findViewById(R.id.recler_testimals);



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


    private void addTestominal() {
        dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.dailog_lay_add_testimal);
        TextView testimonal_header=dialog.findViewById(R.id.testimonal_header);
        editText=dialog.findViewById(R.id.edt_testominals);
        testimonal_header.setText("Add Testimonials");

        Button btn_submit_testomials=dialog.findViewById(R.id.btn_submit_testomials);

        btn_submit_testomials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(TestimalActivity.this, "Please enter the Testominals", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();

                postTestimals(editText.getText().toString().trim(), fileuploadimage);
            }
        });
        ImageView img_upload_image=dialog.findViewById(R.id.img_upload_image);
        ImageView img_upload_file=dialog.findViewById(R.id.img_upload_file);
        img_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicineImageDailog();
            }
        });
        img_upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChooser();

            }
        });


        dialog.show();
    }

    private void getMytestominals() {
        util.showProgressbar(context);
        {
            if (!((Activity) context).isFinishing())
            {
                util.showProgressbar(context);
            }
            Call<ClsMyTestimonialsMain> call = reeTestService.getMyTestominals(userID);
            call.enqueue(new Callback<ClsMyTestimonialsMain>()
            {
                @Override
                public void onResponse(Call<ClsMyTestimonialsMain> call, Response<ClsMyTestimonialsMain> response)
                {
                    util.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsMyTestimonialsMain listResponse = response.body();
                        if (listResponse != null && listResponse.getCode()==1)
                        {
                            try {
                                final MyTestominalsData arylst_testimal_data = listResponse.getData();

                                testo_ID= Integer.parseInt(arylst_testimal_data.getTestimonialId());





                                if (arylst_testimal_data != null) {

//                                   dialog=new Dialog(context,R.style.CustomDialog);
//                                    dialog.setContentView(R.layout.dailog_lay_add_testimal);
//                                    TextView testimonal_header=dialog.findViewById(R.id.testimonal_header);
//                                    editText=dialog.findViewById(R.id.edt_testominals);
//                                    if (arylst_testimal_data.getTestimonial()!=null&&!arylst_testimal_data.getTestimonial().isEmpty()){
//                                        editText.setText(arylst_testimal_data.getTestimonial());
//                                        testimonal_header.setText("Edit Testimonials");
//
//                                    }else {
//                                        testimonal_header.setText("Add Testimonials");
//
//                                    }
//                                    Button btn_submit_testomials=dialog.findViewById(R.id.btn_submit_testomials);
//
//                                    btn_submit_testomials.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//                                            if (editText.getText().toString().trim().isEmpty()){
//                                                Toast.makeText(TestimalActivity.this, "Please enter the Testominals", Toast.LENGTH_SHORT).show();
//                                           return;
//                                            }
//                                            dialog.dismiss();
//
//                                            postTestimals(editText.getText().toString().trim(), fileuploadimage);
//                                        }
//                                    });
//                                    Button btn_add_png=dialog.findViewById(R.id.btn_add_png);
//                                    Button btn_add_image=dialog.findViewById(R.id.btn_add_image);
//                                    btn_add_image.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            AddMedicineImageDailog();
//                                        }
//                                    });
//                                    btn_add_png.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            fileChooser();
//
//                                        }
//                                    });
//
//
//                                    dialog.show();
                                }


                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsMyTestimonialsMain> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    util.hideProgressbar();
                }
            });
        }

    }



    private void addTestimals()
    {
        util.showProgressbar(context);
        {
            if (!((Activity) context).isFinishing())
            {
                util.showProgressbar(context);
            }
            Call<ClsTestimalMain> call = reeTestService.getTestimalList();
            call.enqueue(new Callback<ClsTestimalMain>()
            {
                @Override
                public void onResponse(Call<ClsTestimalMain> call, Response<ClsTestimalMain> response)
                {
                    util.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsTestimalMain listResponse = response.body();
                        if (listResponse != null && listResponse.getCode()==1)
                        {
                            ArrayList<TestimalDataClass> arylst_testimal_data=listResponse.getData();
//                            arylst_testimal_data.addAll(arylst_testimal_data);
//                            arylst_testimal_data.addAll(arylst_testimal_data);
                            if (arylst_testimal_data!=null){
                                TestimalsListAdapter testimalsListAdapter=new TestimalsListAdapter(TestimalActivity.this,arylst_testimal_data);
                                recler_testimals.setAdapter(testimalsListAdapter);
                            }




                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsTestimalMain> call, Throwable t)
                {
                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    util.hideProgressbar();
                }
            });
        }
    }

    private void postTestimals(String str_data, File file)
    {
        util.showProgressbar(context);
        {
            if (!((Activity) context).isFinishing())
            {
                util.showProgressbar(context);
            }

             filetype="0";


            RequestBody userIdbody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(testo_ID));
            RequestBody TestominalIdbody = RequestBody.create(MediaType.parse("text/plain"), ""+str_data);
            RequestBody ReeworkIDbody = RequestBody.create(MediaType.parse("text/plain"), ""+userID);



            MultipartBody.Part photo = null;
            if (!file.getPath().isEmpty()){



                if (!isFile){

                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    String imagename = file.getName();
                    photo = MultipartBody.Part.createFormData("ReportName", imagename, requestFile);
                    filetype="1";

                }else {

                    RequestBody photoContent;

                    photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    photo = MultipartBody.Part.createFormData("ReportName", file.getName(), photoContent);
                    filetype="2";


                }




//                RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), this.fileuploadimage);
//                photo = MultipartBody.Part.createFormData("ReportName", this.fileuploadimage.getName(), photoContent);
            }else {
                RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                photo = MultipartBody.Part.createFormData("ReportName","", photoContent);
                filetype="0";

            }
            MedicineService medService = Client.getClientMultiPart().create(MedicineService.class);

            RequestBody fileTypebody = RequestBody.create(MediaType.parse("text/plain"), ""+filetype);

//            Call<ClsSuccessTest> call = reeTestService.posttestominals(clsPostTestominals);
            Call<ClsSuccessTest> call = medService.newPosttestominalss(userIdbody,TestominalIdbody,ReeworkIDbody,fileTypebody,photo);
            call.enqueue(new Callback<ClsSuccessTest>()
            {
                @Override
                public void onResponse(Call<ClsSuccessTest> call, Response<ClsSuccessTest> response)
                {
                    util.hideProgressbar();

                    if (response.code() == Client.RESPONSE_CODE_OK)
                    {
                        ClsSuccessTest listResponse = response.body();
                        if (listResponse != null && listResponse.getCode()==1)
                        {
                            Toast.makeText(TestimalActivity.this, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            fileuploadimage=new File("");

                            dialog.dismiss();
                            addTestimals();


                        }
                        else
                        {
                            Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsSuccessTest> call, Throwable t)
                {
                    dialog.dismiss();

                    // Log error here since request failed
                    Log.e("MealType---->", t.toString());
                    util.hideProgressbar();
                }
            });
        }
    }
    private void AddMedicineImageDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage("Upload Image ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(TestimalActivity.this)
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

                        Dexter.withActivity(TestimalActivity.this)
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
    public void fileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(intent, FILE_SELECT_REQUEST_CODE);

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                isFile=false;

                fileuploadimage = new File(getRealPathFromURI(mCapturedImageURI));
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(fileuploadimage.getPath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
//



//                    Bitmap bitmapImage = BitmapFactory.decodeFile(fileuploadimage.getPath());
//                    int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                    Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);

                    if (fileuploadimage.exists()) {
                        postTestimals(editText.getText().toString().trim(),fileuploadimage);

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
                    postTestimals(editText.getText().toString().trim(),file);

                }else {
                    Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
                }

            }
        }





        if (resultCode == RESULT_OK) {
            if (requestCode == FILE_SELECT_REQUEST_CODE) {
                isFile = true;
                Uri uri = data.getData();

                String path = getFilePathFromURI(TestimalActivity.this, uri);
                File file = new File(path);

                if (file.exists()) {
                    postTestimals(editText.getText().toString().trim(),file);


                } else {
                    Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
                }

            }



        }
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
}
