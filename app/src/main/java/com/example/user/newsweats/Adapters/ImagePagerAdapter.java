package com.example.user.newsweats.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.Models.ImageUrl;
import com.example.user.newsweats.UI.ImagePagerAct;

import java.util.ArrayList;

/**
 * Created by sasikiran on 25-Feb-17.
 * version 1.0
 */

// pager adapter for slide view inside image tab
public class ImagePagerAdapter extends PagerAdapter {

    //  Urllist
    ArrayList<ImageUrl> imageUrllist;
    //  positon
    int viewPosition;
    //  context
    Context context;

    //  Constructor for context and urllist
    public ImagePagerAdapter(ImagePagerAct imagePagerAct, int position, ArrayList<ImageUrl> imageUrllist) {

        this.context = imagePagerAct;
        this.viewPosition = position;
        this.imageUrllist = imageUrllist;

    }

    //  get length of the list on view
    @Override
    public int getCount() {
        return imageUrllist.size();
    }

    //  view holder
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //  instantiate view
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        ImageUrl imageUrl = imageUrllist.get(position);
        imageView.setPadding(2, 2, 2, 2);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(imageUrl.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        container.addView(imageView, 0);
        return imageView;
    }

    //  destroy item after view displayed
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ImageView) object);
    }
}
