package com.example.user.newsweats.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.Models.ImageUrl;
import com.example.user.newsweats.R;

import java.util.ArrayList;

/**
 * Created by sasikiran on 21/2/17.
 * version 1.0
 */

// recycler adapter for image tab
public class ImageRecyclerviewAdapter extends RecyclerView.Adapter<ImageRecyclerviewAdapter.ImageRecycleHolder> {

//  Interface Object
    OnImageClicklistener onImageClicklistener;
//  Context
    Context con;
//  image url list
    ArrayList<ImageUrl> imageUrls;

//  constructer  to get the context and imageurl list
    public ImageRecyclerviewAdapter(Context context, ArrayList<ImageUrl> arrayList) {

        this.con = context;
        this.imageUrls = arrayList;

    }

//  View holder inflation
    @Override
    public ImageRecyclerviewAdapter.ImageRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycle_items, parent, false);
        return new ImageRecycleHolder(view);

    }

//  Binding of holder to view
    @Override
    public void onBindViewHolder(ImageRecycleHolder holder, int position) {

        ImageUrl imageUrl = imageUrls.get(position);
//  Glide for load image on view and store asap
        Glide.with(con).load(imageUrl.getUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.image);

    }

//  get list size
    @Override
    public int getItemCount() {

        return imageUrls.size();
    }

//  Interface to pass cliked position
    public interface OnImageClicklistener {

        void onItemClicks(View view, int position);

    }

//  setter to set position in object
    public void setOnImageClicklistener(OnImageClicklistener onImageClicklistener) {

        this.onImageClicklistener = onImageClicklistener;

    }

//  inner class for holder......it holds the view id of items xml
    class ImageRecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//    imageView
        ImageView image;
//   Layout background of image
        LinearLayout placeHolder;

//   Constructor for Holder
        public ImageRecycleHolder(View itemView) {

            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.imagemainHolder);
            image = (ImageView) itemView.findViewById(R.id.img_place);
            placeHolder.setOnClickListener(this);

        }

//  Onclick listener
        @Override
        public void onClick(View v) {

            if (onImageClicklistener != null) {

                onImageClicklistener.onItemClicks(itemView, getPosition());

            }

        }
    }


}
