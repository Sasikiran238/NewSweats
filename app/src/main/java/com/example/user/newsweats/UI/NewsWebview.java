package com.example.user.newsweats.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.user.newsweats.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */
public class NewsWebview extends AppCompatActivity {

    WebView webView;
    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web);
        pDialog=new SweetAlertDialog(NewsWebview.this,SweetAlertDialog.PROGRESS_TYPE);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        webView=(WebView)findViewById(R.id.newsWebView);
        Intent webintent=getIntent();
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(webintent.getStringExtra("newsUrl"));

    }
    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            if(pDialog.isShowing()){

                pDialog.dismissWithAnimation();

            }

            return true;

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();

        }

        return super.onOptionsItemSelected(item);

    }

}
