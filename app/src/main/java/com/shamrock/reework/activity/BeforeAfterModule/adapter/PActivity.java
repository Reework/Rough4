package com.shamrock.reework.activity.BeforeAfterModule.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.payment.MainActivity;

public class PActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p);
    }
    public void loadPage (View view) {
        WebView browser = new WebView(this);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("file:///android_asset/page.html");
        setContentView(browser);
        WebSettings ws = browser.getSettings();
        ws.setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new Object()
        {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl)
            {

                Toast.makeText (PActivity.this, strl, Toast.LENGTH_SHORT).show();

            }
        }, "ok");
    }
}
