package com.example.user.newsweats.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.newsweats.Models.ListItems;
import com.example.user.newsweats.R;

import java.util.ArrayList;

/**
 * Created by user on 21/2/17.
 */

public class ReCycleAdapter  extends RecyclerView.Adapter<ReCycleAdapter.Myholder> {

    ArrayList<ListItems> list;
    OnItemClickListener mItemClickListener;


    Context con;




    class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView txt_disp;
        ImageView image;
         LinearLayout placeHolder;

        public Myholder(View itemView) {
            super(itemView);
            Log.e("My holder", "Inside constructor");
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            title= (TextView) itemView.findViewById(R.id.textView_title);
//            txt_disp = (TextView) itemView.findViewById(R.id.textView_disp);
            image = (ImageView) itemView.findViewById(R.id.imageView_news);
            placeHolder.setOnClickListener(this);


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

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public ReCycleAdapter(ArrayList<ListItems> list_fin,Context context) {
        Log.e("IN Adapter","Inside constructor"+list_fin.get(0).getDesc());
        this.list =list_fin;
        this.con=context;
        Log.e("IN Adapter","Inside constructor after list_fin");
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.e("IN Adapter","Inside oncreateViewholder");
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items,parent,false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        Log.e("IN Adapter","Inside onbindViewholder");
        ListItems items=list.get(position);
        holder.title.setText(items.getTitle());
//        holder.txt_disp.setText(items.getDesc());
//        items.getImage()
        Glide.with(con).load(items.getImage()).diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into( holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
