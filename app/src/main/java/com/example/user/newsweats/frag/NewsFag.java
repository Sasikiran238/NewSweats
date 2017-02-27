package com.example.user.newsweats.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.newsweats.Adapters.ReCycleAdapter;
import com.example.user.newsweats.Controllers.ApiAsyn;
import com.example.user.newsweats.Controllers.ConChecker;
import com.example.user.newsweats.Models.ListItems;
import com.example.user.newsweats.R;
import com.example.user.newsweats.UI.DetailActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class NewsFag extends Fragment {
    ApiAsyn apiAsyn;
    ArrayList<ListItems> listfinal;
    RecyclerView recycleListView;
    Context context;

    ConChecker conChecker;
    ReCycleAdapter reCycleAdapter;
    FloatingActionButton fab;

    boolean isListView=false;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_fag, container, false);
        context=inflater.getContext();


         fab = (FloatingActionButton)rootView.findViewById(R.id.fab);



        apiAsyn=new ApiAsyn(getContext());

        recycleListView=(RecyclerView) rootView.findViewById(R.id.newsRecyclers);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleListView.setLayoutManager(mStaggeredLayoutManager);
        conChecker=new ConChecker(context);

        try {
//            if(conChecker.isNetworkAvailable()) {
                listfinal = apiAsyn.execute("https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=4e878f5b065e4592be3503001cb494b9").get();
                Log.e(";;;;;;;;;;;;;;;;;;", "haaaaaaaaaaaaaaaaaaaaaaaaii frooom ooooon cllllllllllllllllll");
                reCycleAdapter = new ReCycleAdapter(listfinal, context);
                Log.e(";;;;;;;;;;;;;;;;;;", "haaaaaaaaaaaaaaaaaaaaaaaaii frooom ooooon after adapter");
                recycleListView.setAdapter(reCycleAdapter);
                reCycleAdapter.setOnItemClickListener(onItemClickListener);
//            }else {
//
//
//            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isListView) {
                    mStaggeredLayoutManager.setSpanCount(1);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_grid));
                    isListView = false;
                } else {
                    mStaggeredLayoutManager.setSpanCount(2);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_list));
                    isListView = true;
                }
            }
        });


        return rootView;
    }
    ReCycleAdapter.OnItemClickListener onItemClickListener = new ReCycleAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            ListItems items=listfinal.get(position);
//            Toast.makeText(getContext(),"Clicked"+position,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getContext(),DetailActivity.class);
            intent.putExtra("discp",items.getDesc());
            intent.putExtra("Image",items.getImage());
            intent.putExtra("title",items.getTitle());
            intent.putExtra("url",items.getUrl());
            Log.e("=============",items.getDesc());
           startActivity(intent);
        }
    };

}
