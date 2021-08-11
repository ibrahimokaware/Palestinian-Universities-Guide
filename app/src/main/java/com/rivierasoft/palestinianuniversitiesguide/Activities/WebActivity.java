package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.Locale;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private Intent intent;
    private boolean loadingFinished = true, redirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
        setContentView(R.layout.activity_web);

        Connectivity.checkConnection(this);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.pb_web);

        intent = getIntent();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(intent.getStringExtra("link"));
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (!loadingFinished)
                    redirect = true;

                loadingFinished = false;
                //webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFinished = false;
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!redirect) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "قم بتدوير الشاشة للعرض بشكل أفضل", Toast.LENGTH_SHORT).show();
                } else redirect = false;
            }
        });
    }
}