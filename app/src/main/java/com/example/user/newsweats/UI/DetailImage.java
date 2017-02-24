package com.example.user.newsweats.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.R;

public class DetailImage extends AppCompatActivity {

    ImageView full_imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        Intent intent=getIntent();

        full_imageView=(ImageView)findViewById(R.id.ImagesView);

        Glide.with(DetailImage.this).load(intent.getStringExtra("fullImage")).diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(full_imageView);
    }
}
