package com.example.user.newsweats.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.user.newsweats.Adapters.ImagePagerAdapter;
import com.example.user.newsweats.Controllers.ImageApi;
import com.example.user.newsweats.Controllers.NetworkConnectionChecker;
import com.example.user.newsweats.Models.ImageUrl;
import com.example.user.newsweats.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

public class ImagePagerAct extends AppCompatActivity {

    ViewPager imgPager;
    ArrayList<ImageUrl> imageUrl;
    ImageApi imageApi;
    NetworkConnectionChecker networkConnectionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        imageApi = new ImageApi(getApplicationContext());
        Intent intent = getIntent();
        networkConnectionChecker = new NetworkConnectionChecker(getApplicationContext());

        try {

            imageUrl = imageApi.execute("https://api.flickr.com/services/rest/" +
                    "?method=flickr.photos.search&api_key=911f0332a1f208e7352591356af39c9c" +
                    "&per_page=500&user_id=52540720@N02&format=json&nojsoncallback=1").get();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

        imgPager = (ViewPager) findViewById(R.id.imagePagerAd);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, intent.getIntExtra("position", 0), imageUrl);
        imgPager.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
}
