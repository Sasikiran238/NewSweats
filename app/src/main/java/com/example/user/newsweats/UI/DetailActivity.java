package com.example.user.newsweats.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.Controllers.ConChecker;
import com.example.user.newsweats.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


//Detail Main View For User  with taped news,images

public class DetailActivity extends AppCompatActivity {

    ImageView news_img;
    TextView discp_news;
    TextView title;
    LinearLayout newstitlePlace;
    ConChecker conChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        conChecker = new ConChecker(DetailActivity.this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        news_img = (ImageView) findViewById(R.id.newsImages);
        title = (TextView) findViewById(R.id.news_title);
        discp_news = (TextView) findViewById(R.id.newsDiscp);
        discp_news.setText(intent.getStringExtra("discp"));
        title.setText(intent.getStringExtra("title"));
        newstitlePlace = (LinearLayout) findViewById(R.id.newsTitleplace);

        newstitlePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onclick", intent.getStringExtra("url"));
                if (conChecker.isNetworkAvailable()) {

                    Intent intent1 = new Intent(DetailActivity.this, NewsWeb.class);
                    intent1.putExtra("newsUrl", intent.getStringExtra("url"));
                    startActivity(intent1);
                } else {
                    new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Check Network Connection")
                            .setContentText("Sorry !!")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }
        });
        Glide.with(DetailActivity.this).load(intent.getStringExtra("Image")).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(news_img);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
