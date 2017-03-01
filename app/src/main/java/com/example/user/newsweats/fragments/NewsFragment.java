package com.example.user.newsweats.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.newsweats.Adapters.NewsRecyclerviewAdapter;
import com.example.user.newsweats.Controllers.NetworkConnectionChecker;
import com.example.user.newsweats.Controllers.NewsApi;
import com.example.user.newsweats.Models.NewsItems;
import com.example.user.newsweats.R;
import com.example.user.newsweats.UI.NewsDetailview;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */
public class NewsFragment extends Fragment {

    NewsApi newsApi;
    ArrayList<NewsItems> listfinal;
    RecyclerView recycleListView;
    Context context;
    NetworkConnectionChecker networkConnectionChecker;
    NewsRecyclerviewAdapter newsRecyclerviewAdapter;
    FloatingActionButton fab;
    boolean isListView=false;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_fag, container, false);
        context=inflater.getContext();
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        newsApi =new NewsApi(getContext());
        recycleListView=(RecyclerView) rootView.findViewById(R.id.newsRecyclers);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycleListView.setLayoutManager(mStaggeredLayoutManager);
        networkConnectionChecker =new NetworkConnectionChecker(context);

        try {
                listfinal = newsApi.execute("https://newsapi.org/v1/" +
                        "articles?source=bbc-news&sortBy=top&" +
                        "apiKey=4e878f5b065e4592be3503001cb494b9").get();
                newsRecyclerviewAdapter = new NewsRecyclerviewAdapter(listfinal, context);
                recycleListView.setAdapter(newsRecyclerviewAdapter);
                newsRecyclerviewAdapter.setOnItemClickListener(onItemClickListener);

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

    NewsRecyclerviewAdapter.OnItemClickListener onItemClickListener = new NewsRecyclerviewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {

            NewsItems items=listfinal.get(position);
            Intent intent=new Intent(getContext(),NewsDetailview.class);
            intent.putExtra("discp",items.getDesc());
            intent.putExtra("Image",items.getImage());
            intent.putExtra("title",items.getTitle());
            intent.putExtra("url",items.getUrl());
            startActivity(intent);

        }
    };

}
