package com.example.user.newsweats.Adapters;

import android.content.Context;
import android.content.Intent;
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
 * Created by user on 21/2/17.
 */

public class ImageRecycleAdapter  extends RecyclerView.Adapter<ImageRecycleAdapter.Myhold>{


    OnImageClicklistener onImageClicklistener;

    Context con;
    ArrayList<ImageUrl> imageUrls;


    //    inner class for holder......it holds the view id of items xml

    class Myhold extends RecyclerView.ViewHolder implements View.OnClickListener{


        ImageView image;

        LinearLayout placeHolder;
        public Myhold(View itemView) {
            super(itemView);
            Log.e("My holder","Inside constructor");
            placeHolder = (LinearLayout) itemView.findViewById(R.id.imagemainHolder);
            image=(ImageView)itemView.findViewById(R.id.img_place);
//            placeHolder.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(onImageClicklistener !=null){
                onImageClicklistener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnImageClicklistener{
        void onItemClick(View view, int position);
    }


    public ImageRecycleAdapter(Context context,ArrayList<ImageUrl> arrayList) {
        Log.e("IN Adapter","Inside constructor");

        this.con=context;
        this.imageUrls=arrayList;
        Log.e("IN Adapter","Inside constructor after list_fin");
    }

    @Override
    public ImageRecycleAdapter.Myhold onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("IN Adapter","Inside oncreateViewholder");
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycle_items,parent,false);
        return new Myhold(view);

    }

    @Override
    public void onBindViewHolder(Myhold holder, int position) {
        Log.e("IN Adapter","Inside onbindViewholder");

         ImageUrl imageUrl=imageUrls.get(position);
        Glide.with(con).load(imageUrl.getUrl()).diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into( holder.image);

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }


}
