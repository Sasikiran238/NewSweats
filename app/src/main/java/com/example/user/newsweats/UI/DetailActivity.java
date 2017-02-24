package com.example.user.newsweats.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.R;

public class DetailActivity extends AppCompatActivity {

    ImageView news_img;
    TextView discp_news;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();


        news_img=(ImageView)findViewById(R.id.newsImages);
        title=(TextView)findViewById(R.id.news_title);
        discp_news=(TextView)findViewById(R.id.newsDiscp);
        discp_news.setText(intent.getStringExtra("discp"));
        title.setText(intent.getStringExtra("title"));

        Glide.with(DetailActivity.this).load(intent.getStringExtra("Image")).diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(news_img);

    }
}
