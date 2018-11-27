package me.mojodigi.lockscreen;
import android.app.Activity;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import me.mojodigi.lockscreen.utils.Helper;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        webView = (WebView) findViewById(R.id.webView1);

        if (Helper.NEWSURL != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new myWebClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(Helper.NEWSURL);
        }

        else
        {
            Toast.makeText(this, getResources().getString(R.string.cannotload), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        ((LockApplication) getApplication()).lockScreenShow = false;
        // does  not allow  the menu  button to  list and switch  the screen;
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LockApplication) getApplication()).lockScreenShow = true;
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {


            return super.onRenderProcessGone(view, detail);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
         //   CustomProgressDialog.dismiss();

        }

        @Override
        public void onPageFinished(WebView view, String url) {
           // CustomProgressDialog.dismiss();
            super.onPageFinished(view, url);
            Log.e("onpagefinish", url);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            //CustomProgressDialog.show(WebviewActivity.this,"Loading");
            view.loadUrl(url);
            return true;

        }

    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();



//        Intent i  =  new Intent(WebViewActivity.this,LockScreenActivity.class);
//        startActivity(i);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        Intent i  =  new Intent(WebViewActivity.this,LockScreenActivity.class);
        startActivity(i);
            finish();
        return super.onKeyDown(keyCode, event);
    }
}