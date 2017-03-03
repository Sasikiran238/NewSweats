package com.example.user.newsweats.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.user.newsweats.Database.NewsDbHandler;
import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.Models.NewsItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by sasikiran on 21/2/17.
 * version 1.0
 *
 */

//  get news data from google news api (.json file)
public class NewsApi extends AsyncTask<String,Void,ArrayList> {

//    tile string
    String newsTitle="";
//    news data list
    ArrayList<NewsItems> newsList;
//    news model class
    NewsItems newsItems;
//    context
    Context context;
//    network checker class
    NetworkConnectionChecker networkConnectionChecker;
//    preferences initiation
    Preferences share;
//    DB initiation
    NewsDbHandler newsDbHandler;

//    get context from activity
    public NewsApi(Context con) {

        this.context=con;

    }

//    preexicute
    protected void onpreexecute(){

        super.onPreExecute();

    }

//    pharsing data from news json and save to Db
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ArrayList doInBackground(String... params) {

//      object of preference
        share=new Preferences(context);
//        local json string reference
        String jsonstring = null;
//      connection check object
        networkConnectionChecker =new NetworkConnectionChecker(context);
        newsDbHandler=new NewsDbHandler(context);
//      local url list
        newsList=new ArrayList<>();
//        Db urls
        String reqUrl=params[0];
//      http connection object
        NewsHttpHandler reqCon=new NewsHttpHandler();
//      json from DB

        try {

//            checking network available?
//            (true) check db && request url to string && insert data to db
//            ::
//            (false) get data from db
            if (networkConnectionChecker.isNetworkAvailable()) {

                jsonstring = reqCon.httpreq(reqUrl);
                if (jsonstring != null) {

                    JSONObject jsonObject = new JSONObject(jsonstring);
                    newsTitle = jsonObject.getString("source");
                    JSONArray jsonArticleArray = jsonObject.getJSONArray("articles");

                    for (int a = 0; a < jsonArticleArray.length(); a++) {

                        JSONObject articleObject = jsonArticleArray.getJSONObject(a);
                        String titleFinal = articleObject.getString("title");
                        String disFinal = articleObject.getString("description");
                        String image = articleObject.getString("urlToImage");
                        String newsDetailurl = articleObject.getString("url");
                        String allData = titleFinal + disFinal;

                        newsDbHandler.insertNews(allData, titleFinal, disFinal, image, newsDetailurl);
                        newsItems = new NewsItems(titleFinal, disFinal, image, newsDetailurl);
                        newsList.add(newsItems);

                    }

                }

            } else {

                newsList = newsDbHandler.getNews();

            }



        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return newsList;

    }

    }