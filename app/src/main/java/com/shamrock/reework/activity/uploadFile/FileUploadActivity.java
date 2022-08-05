package com.shamrock.reework.activity.uploadFile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import com.shamrock.R;

public class FileUploadActivity extends AppCompatActivity {

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100, FILE_SELECT_REQUEST_CODE = 300, IMAGE_CROP = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fileChooser();

            }
        },2000);



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
}
