package com.example.user.newsweats.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.EditText;


import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.Models.ListItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by user on 21/2/17.
 */

public class ApiAsyn extends AsyncTask<String,Void,ArrayList> {

    EditText editText;
    String newsTitle="haaaaaaai";
    ArrayList<ListItems> list_fin;
    ListItems listItems;
    Context context;
    ConChecker conChecker;
    Preferences share;
    public ApiAsyn(Context con) {
        this.context=con;
    }

    protected void onpreexecute(){
        super.onPreExecute();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ArrayList doInBackground(String... params) {

        share=new Preferences(context);
        String jsonstring=null;
        conChecker=new ConChecker(context);
        list_fin=new ArrayList<>();
        String reqUrl=params[0];
        HttpHandler reqCon=new HttpHandler();
        Log.e("Test_inBack",reqUrl);

        Log.e("InBack","before jsonget");
        try {
            if(conChecker.isNetworkAvailable()) {
                jsonstring = reqCon.httpreq(reqUrl);
                share.putNews(jsonstring);
//                Log.e("getShare",);
            }else {
                if(share.getNews()!=null){
                    jsonstring=share.getNews();
                }

            }
            Log.e("InBack  afterurl",jsonstring);

            //open object  {
            JSONObject jsonObject=new JSONObject(jsonstring);
//                //get title     " "
            newsTitle=jsonObject.getString("source");

            Log.e("InBacksssssssss",newsTitle);

            JSONArray jsonArticleArray=jsonObject.getJSONArray("articles");
//
//            // Article array entry loop :
//
            Log.e("InJsonf  Loop","inside artical");

            for(int a=0;a<jsonArticleArray.length();a++){

                Log.e("InJsonf  Loop",""+a+jsonArticleArray.length());
                // article Objects



                JSONObject articleObject=jsonArticleArray.getJSONObject(a);
                String title_fin=articleObject.getString("title");
                String dis_fin=articleObject.getString("description");
                String image=articleObject.getString("urlToImage");
                String newsdetailurl=articleObject.getString("url");
                Log.e("InJsonf  Loop","inside artical"+title_fin);
                Log.e("InJsonf  Loop","inside artical"+articleObject.getString("title"));
                Log.e("InJsonf  Loop","inside artical"+dis_fin);
                Log.e("InJsonf  Loop","inside artical"+articleObject.getString("publishedAt"));
                Log.e("InJsonf  Loop","inside artical"+image);
                Log.d("......................","---------------------------");
                Log.e("IN back","before setdisp");

//                listItems.setDate(articleObject.getString("publishedAt"));
                listItems=new ListItems(title_fin,dis_fin,image,newsdetailurl);
                //   listItems.setTitle(title_fin);
                //   listItems.setDesc(dis_fin);
                Log.e("IN back","after setdisp");

                list_fin.add(listItems);


//                   newsMap.put("author",articleObject.getString("author"));
//                    newsMap.put("title",articleObject.getString("title"));
//                   newsMap.put("description",articleObject.getString("description"));
//                    newsMap.put("publishedAt",articleObject.getString("publishedAt"));
//
//                newslist.add(newsMap);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list_fin;
    }

}