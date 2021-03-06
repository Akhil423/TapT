package com.project.greenhat.tap_t;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebViewClient;


public class Web extends AppCompatActivity {

    private WebView webView;



    public static String EXTRA_URL = "api.twitter.com/oauth/authorize?force_login=true";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        android.webkit.CookieManager.getInstance().removeAllCookie();
        setTitle("Login");

        final String url = this.getIntent().getStringExtra(EXTRA_URL);
        if (null == url) {
            Log.e("Twitter", "URL cannot be null");
            finish();
        }

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {



            if (url.contains(getResources().getString(R.string.twitter_callback))) {
                Uri uri = Uri.parse(url);

				/* Sending results back */
                String verifier = uri.getQueryParameter(getString(R.string.twitter_oauth_verifier));
                Intent resultIntent = new Intent();
                resultIntent.putExtra(getString(R.string.twitter_oauth_verifier), verifier);
                setResult(RESULT_OK, resultIntent);

				/* closing webview */
                finish();
                return true;
            }



            return false;
        }


    }
}
