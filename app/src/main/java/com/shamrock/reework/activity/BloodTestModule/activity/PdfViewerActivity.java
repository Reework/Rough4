package com.shamrock.reework.activity.BloodTestModule.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shamrock.R;

public class PdfViewerActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "PdfViewerActivity";
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
    WebView webView;
    private String PreUrl = "https://docs.google.com/viewer?embedded=true&url=";
    String pdfUrl = "", strExtension = "", strName = "", strPostdate = "";
    ImageView imgView_Back_arrow, imgView_download;
    ProgressBar progressBars;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        initViews();
    }


    public void initViews(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            pdfUrl = bundle.getString("pdfLink");
            Log.d("urlsunit",pdfUrl);
            strName = bundle.getString("name");
            strPostdate = bundle.getString("postdate");
        }
        imgView_Back_arrow = findViewById(R.id.imgView_Back_arrow);
        imgView_Back_arrow.setOnClickListener(this);

        imgView_download = findViewById(R.id.imgView_download);
        imgView_download.setOnClickListener(this);
        webView = findViewById(R.id.webView_PdfViewer);
        progressBars = findViewById(R.id.progressBars);
        progressBars.setMax(100);
        load();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void load() {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


//        Toast.makeText(this, "Extenstion "+strExtension, Toast.LENGTH_SHORT).show();


        if (strExtension.equalsIgnoreCase(".pdf")) {
            webView.loadUrl(PreUrl + pdfUrl);
        } else if (strExtension.equalsIgnoreCase(".jpeg")) {
            webView.loadUrl(pdfUrl);
        } else if (strExtension.equalsIgnoreCase(".rpt")) {
            webView.loadUrl(PreUrl + pdfUrl);
        } else {
            webView.loadUrl(PreUrl + pdfUrl);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBars.setVisibility(View.VISIBLE);
//                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view.getTitle().equals("")){
                    view.reload();

                }

                progressBars.setVisibility(View.GONE);
                progressBars.setProgress(100);
                webView.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");

            }

          /*  @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }*/
        });
    }


        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.imgView_Back_arrow:
                finish();
                break;

                case R.id.imgView_download:
                    if (!checkIfAlreadyHavePermission())
                    {
                        requestForSpecificPermission();

                    }
                    else
                    {
                        downloadPDF(pdfUrl, strPostdate, strExtension, strName);
                    }
                    //checkAndRequestPermissions();
                //downloadStudyMAterial(strPath, strPostdate, strExtension, strName);
                break;
        }
    }





    private boolean checkIfAlreadyHavePermission()
    {

        int result = ContextCompat.checkSelfPermission(PdfViewerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission()
    {
        ActivityCompat.requestPermissions(PdfViewerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
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

                    downloadPDF(pdfUrl, strPostdate, strExtension, strName);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

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


    private void downloadPDF(String url, String date,
                                       String extension, String filesItemName) {
        try {
            Log.d(TAG, url + " " + date + " " + extension + " " + " " + filesItemName);
            if (url != null) {
                if (url.contains("http") || url.contains("https")) {
/*                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription("Your downloads will be available soon");
                request.setTitle(filesItemName + " " + "File  Downloads");
                // in order for this if to run, you must use the android 3.2 to compile your app
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ShamRock Files" + "/" + filesItemName + "_" + date + extension);
                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                long refid = manager.enqueue(request);
                Log.d(TAG, refid + " Downloaded ");*/


                    DownloadManager downloadmanager;
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .mkdirs();

                    downloadmanager = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
                    String url1 = url;
                    Uri uri = Uri.parse(url1);
                    DownloadManager.Request request1 = new DownloadManager.Request(uri)
                            .setTitle(filesItemName + " " + "File  Downloads")
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                    "ShamRock Files" + "/" + filesItemName + "_" + date + extension)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Log.i("Download1", String.valueOf(request1));
                    downloadmanager.enqueue(request1);
                } else {
                    Toast.makeText(getApplicationContext(), "not able to download,not a valid pdf path", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
