package com.example.user.newsweats.frag;


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

import com.example.user.newsweats.Adapters.ImageRecycleAdapter;
import com.example.user.newsweats.Controllers.ConChecker;
import com.example.user.newsweats.Controllers.ImageApi;
import com.example.user.newsweats.Models.ImageUrl;
import com.example.user.newsweats.R;
import com.example.user.newsweats.UI.DetailImage;
import com.example.user.newsweats.UI.ImagePagerAct;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ImageFag extends Fragment {

    RecyclerView recycleListView;
    Context context;
    ConChecker conChecker;
    ArrayList<ImageUrl> image_final;

    ImageApi imageApi;
    boolean isListView=false;
    FloatingActionButton fabFull;
    ImageRecycleAdapter imageRecycleAdapter;

    int pos=1;
//   OnimageUrlPass onimageUrlPass;
    SweetAlertDialog pDialog;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_fag, container, false);
        recycleListView=(RecyclerView) rootView.findViewById(R.id.image_recycler);
        context=inflater.getContext();
        pDialog=new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        imageApi=new ImageApi(getContext());





//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        fabFull = (FloatingActionButton)rootView.findViewById(R.id.fabfullimage);


        recycleListView=(RecyclerView) rootView.findViewById(R.id.image_recycler);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleListView.setLayoutManager(mStaggeredLayoutManager);
        conChecker=new ConChecker(context);

//        if(conChecker.isNetworkAvailable()) {
            try {
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                image_final = imageApi.execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=911f0332a1f208e7352591356af39c9c&per_page=500&user_id=52540720@N02&format=json&nojsoncallback=1").get();
//                onimageUrlPass.getUrllist(image_final);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally {
                if(pDialog.isShowing()){
                    pDialog.dismissWithAnimation();}
            }

            ImageRecycleAdapter reCycleAdapter = new ImageRecycleAdapter(context,image_final);
            reCycleAdapter.setOnImageClicklistener(onImageClicklistener);
            Log.e(";;;;;;;;;;;;;;;;;;","haaaaaaaaaaaaaaaaaaaaaaaaii frooom ooooon after adapter");
            recycleListView.setAdapter(reCycleAdapter);
//        }


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
//                        intent.putExtra("position");

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



    ImageRecycleAdapter.OnImageClicklistener onImageClicklistener=new ImageRecycleAdapter.OnImageClicklistener() {
        @Override
        public void onItemClicks(View view, int position) {
        Log.e("onitemclic image",""+position);

            ImageUrl imageUrl=image_final.get(position);

            Intent intent=new Intent(getContext(),DetailImage.class);
            intent.putExtra("fullImage",imageUrl.getUrl());
            startActivity(intent);

        }
    };



}
