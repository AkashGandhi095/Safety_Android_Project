package com.akash.womensafetyapp.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.akash.womensafetyapp.Fragments.PostFragment;
import com.akash.womensafetyapp.R;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    @SuppressLint("setJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String URL = getIntent().getStringExtra(PostFragment.POST_STRING);

        if(TextUtils.isEmpty(URL))
        {

            Toast.makeText(this , "An Error Occured" , Toast.LENGTH_SHORT).show();
            return;
        }
        webView = findViewById(R.id.MyWebView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URL);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if(webView != null && webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();

    }
}
