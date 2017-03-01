package com.example.user.newsweats.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.Models.NewsItems;
import com.example.user.newsweats.R;

import java.util.ArrayList;

/**
 * Created by Sasikiran on 21/2/17.
 * version 1.0
 */

// Adapter for news Recyclerview
public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.Myholder> {

//  image url list
    ArrayList<NewsItems> list;
//  Interface Object
    OnItemClickListener mItemClickListener;
//  Context
    Context con;

//  inner class for holder......it holds the view id of items xml
    class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener{

//  title text
        TextView title;
//  imageview
        ImageView image;
//  layout background of news
        LinearLayout placeHolder;

//   Constructor for Holder
        public Myholder(View itemView) {

            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            title= (TextView) itemView.findViewById(R.id.textView_title);
            image = (ImageView) itemView.findViewById(R.id.imageView_news);
            placeHolder.setOnClickListener(this);
        }

//  Onclick listener
        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) {

                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }

    }

//  Interface to pass cliked position
    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

//  setter to set position in object
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;

    }

//  constructer  to get the context and imageurl list
    public NewsRecyclerviewAdapter(ArrayList<NewsItems> list_fin, Context context) {

        this.list =list_fin;
        this.con=context;

    }

//  View holder inflation
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items,parent,false);
        return new Myholder(view);

    }

//  Binding of holder to view
    @Override
    public void onBindViewHolder(Myholder holder, int position) {

        NewsItems items=list.get(position);
        holder.title.setText(items.getTitle());
        Glide.with(con).load(items.getImage()).diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into( holder.image);

    }

//  get list size
    @Override
    public int getItemCount() {
        return list.size();
    }


}
