package com.example.user.newsweats.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.R;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */
public class DetailImageview extends AppCompatActivity {

    ImageView full_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent=getIntent();
        full_imageView=(ImageView)findViewById(R.id.ImagesView);
        Glide.with(DetailImageview.this).load(intent.getStringExtra("fullImage"))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(full_imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();

        }

        return super.onOptionsItemSelected(item);

    }
}
