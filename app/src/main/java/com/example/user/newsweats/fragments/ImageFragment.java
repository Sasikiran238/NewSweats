package com.example.user.newsweats.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.newsweats.Adapters.ImageRecyclerviewAdapter;
import com.example.user.newsweats.Controllers.NetworkConnectionChecker;
import com.example.user.newsweats.Controllers.ImageApi;
import com.example.user.newsweats.Models.ImageUrl;
import com.example.user.newsweats.R;
import com.example.user.newsweats.UI.DetailImageview;
import com.example.user.newsweats.UI.ImagePagerAct;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

//  Tabbed activity image fragment
public class ImageFragment extends Fragment {

//    Recyclerview
    RecyclerView recycleListView;
//    context
    Context context;
//     networkConnection
    NetworkConnectionChecker networkConnectionChecker;
//    image local list
    ArrayList<ImageUrl> imageUrls;
//    call image api
    ImageApi imageApi;
//    for fab initial list false to show grid
    boolean isListView=false;
//    fab initiation
    FloatingActionButton fabFull;
//    image recycle adapter
    ImageRecyclerviewAdapter imageRecyclerviewAdapter;
    int pos=1;
    SweetAlertDialog pDialog;
//  staggered for grid and list
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return  root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image_fag, container, false);
        recycleListView=(RecyclerView) rootView.findViewById(R.id.image_recycler);
        context=inflater.getContext();
        pDialog=new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        imageApi=new ImageApi(getContext());
        fabFull = (FloatingActionButton)rootView.findViewById(R.id.fabfullimage);
        recycleListView=(RecyclerView) rootView.findViewById(R.id.image_recycler);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleListView.setLayoutManager(mStaggeredLayoutManager);
        networkConnectionChecker =new NetworkConnectionChecker(context);

            try {
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                imageUrls = imageApi.execute("https://api.flickr.com/services/rest/" +
                        "?method=flickr.photos.search&api_key=911f0332a1f208e7352591356af39c9c" +
                        "&per_page=500&user_id=52540720@N02&format=json&nojsoncallback=1").get();

            } catch (InterruptedException e) {

                e.printStackTrace();

            } catch (ExecutionException e) {

                e.printStackTrace();

            }finally {

                if(pDialog.isShowing()){
                    pDialog.dismissWithAnimation();}

            }

            ImageRecyclerviewAdapter reCycleAdapter = new ImageRecyclerviewAdapter(context,imageUrls);
            reCycleAdapter.setOnImageClicklistener(onImageClicklistener);
            recycleListView.setAdapter(reCycleAdapter);

        fabFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (pos){

                    case 1:

                        mStaggeredLayoutManager.setSpanCount(2);
                        fabFull.setImageDrawable(getResources().getDrawable(R.drawable.slide));
                        pos+=1;
                        break;

                    case 2:

                        Intent intent=new Intent(getContext(),ImagePagerAct.class);
                        startActivity(intent);
                        fabFull.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_list));
                        pos+=1;
                        break;

                    case 3:

                        mStaggeredLayoutManager.setSpanCount(1);
                        fabFull.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_grid));
                        pos=1;
                        break;

                    default:

                        break;

                }

            }
        });

        return rootView;

    }

    ImageRecyclerviewAdapter.OnImageClicklistener onImageClicklistener=new ImageRecyclerviewAdapter.OnImageClicklistener() {
        @Override
        public void onItemClicks(View view, int position) {

            ImageUrl imageUrl=imageUrls.get(position);
            Intent intent=new Intent(getContext(),DetailImageview.class);
            intent.putExtra("fullImage",imageUrl.getUrl());
            startActivity(intent);

        }
    };



}
