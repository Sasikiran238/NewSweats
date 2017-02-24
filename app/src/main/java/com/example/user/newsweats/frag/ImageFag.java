package com.example.user.newsweats.frag;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.newsweats.Adapters.ImageRecycleAdapter;
import com.example.user.newsweats.Adapters.ReCycleAdapter;
import com.example.user.newsweats.Controllers.ConChecker;
import com.example.user.newsweats.Controllers.ImageApi;
import com.example.user.newsweats.Models.ImageItems;
import com.example.user.newsweats.Models.ListItems;
import com.example.user.newsweats.R;
import com.example.user.newsweats.UI.DetailActivity;
import com.example.user.newsweats.UI.DetailImage;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ImageFag extends Fragment {

    RecyclerView recycleListView;
    Context context;
    ConChecker conChecker;
    ArrayList<ImageItems> image_final;

    ImageApi imageApi;
    boolean isListView=true;
    FloatingActionButton fabFull;
//    String mainImageUrl;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_fag, container, false);
        recycleListView=(RecyclerView) rootView.findViewById(R.id.image_recycler);
        context=inflater.getContext();
        imageApi=new ImageApi();


        fabFull = (FloatingActionButton)rootView.findViewById(R.id.fabfullimage);


        recycleListView=(RecyclerView) rootView.findViewById(R.id.image_recycler);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleListView.setLayoutManager(mStaggeredLayoutManager);
        conChecker=new ConChecker(context);
        if(conChecker.isNetworkAvailable()) {
            try {
                image_final = imageApi.execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=911f0332a1f208e7352591356af39c9c&per_page=500&user_id=52540720@N02&format=json&nojsoncallback=1").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
//            ,image_final
            ImageRecycleAdapter reCycleAdapter = new ImageRecycleAdapter(context);

            Log.e(";;;;;;;;;;;;;;;;;;","haaaaaaaaaaaaaaaaaaaaaaaaii frooom ooooon after adapter");
            recycleListView.setAdapter(reCycleAdapter);
        }


        fabFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if (isListView) {
                    mStaggeredLayoutManager.setSpanCount(1);
                    fabFull.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_grid));
                    isListView = false;
                } else {
                    mStaggeredLayoutManager.setSpanCount(2);
                    fabFull.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_list));
                    isListView = true;
                }
            }
        });



        return rootView;



    }

    ReCycleAdapter.OnItemClickListener onItemClickListener = new ReCycleAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            ImageItems imageItems=image_final.get(position);
            Toast.makeText(getContext(),"Clicked"+position,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(),DetailImage.class);

//            intent.putExtra("fullImage",imageItems.getImages());


            startActivity(intent);
        }
    };



    @Override
    public void onResume() {
        super.onResume();

    }
}
