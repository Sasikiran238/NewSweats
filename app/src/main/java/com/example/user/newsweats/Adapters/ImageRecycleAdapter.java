package com.example.user.newsweats.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.Models.ImageItems;
import com.example.user.newsweats.Models.ListItems;
import com.example.user.newsweats.R;

import java.util.ArrayList;

/**
 * Created by user on 21/2/17.
 */

public class ImageRecycleAdapter  extends RecyclerView.Adapter<ImageRecycleAdapter.Myhold>{



    Context con;
    ArrayList<ImageItems> imageitems;

    ReCycleAdapter.OnItemClickListener mItemClickListener;
    //    inner class for holder......it holds the view id of items xml

    class Myhold extends RecyclerView.ViewHolder implements View.OnClickListener{


        ImageView image;


        public Myhold(View itemView) {
            super(itemView);
            Log.e("My holder","Inside constructor");

            image=(ImageView)itemView.findViewById(R.id.img_place);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final ReCycleAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

//    ,ArrayList<ImageItems> arrayList
    public ImageRecycleAdapter(Context context) {
        Log.e("IN Adapter","Inside constructor");

        this.con=context;
//        this.imageitems=arrayList;
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

//        ImageItems image=imageitems.get(position);
        Glide.with(con).load("http://feelgrafix.com/group/images.html").diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into( holder.image);

    }

    @Override
    public int getItemCount() {
//        con.getResources().getStringArray(R.array.imageUrl).length
        return 1000;
    }


}
