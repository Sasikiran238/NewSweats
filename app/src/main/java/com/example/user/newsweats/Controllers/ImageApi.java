package com.example.user.newsweats.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.newsweats.Database.ImageDbHandler;
import com.example.user.newsweats.Database.NewsDbHandler;
import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.Models.ImageUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by sasikiran on 24/2/17.
 * version 1.0
 */

//  Get image urls from api.flickr.com
public class ImageApi extends AsyncTask<String,Void,ArrayList> {

//  url list
    ArrayList<ImageUrl> imageUrls;
//  url string
    ImageUrl imageUrl;
//  context
    Context context;
//  networkconnectionchecker class object
    NetworkConnectionChecker networkConnectionChecker;
//  Prefence object
    Preferences share;
//  Database link
    ImageDbHandler imageDbHandler;
//  constructor
    public ImageApi(Context context) {

        this.context=context;

    }

//  async pretask
    protected void onpreexecute(){

        super.onPreExecute();

    }

//  parsing flickr api json
    @Override
    protected ArrayList doInBackground(String... params) {

//      object of preference
        share = new Preferences(context);
//      connection check object
        networkConnectionChecker = new NetworkConnectionChecker(context);
//      jsonString
        String jsonstring;
//      json from DB
        ArrayList<String> dbJson;
//      local url list
        imageUrls = new ArrayList<>();
//      db url lists
        String reqUrl = params[0];
//      image db call
        imageDbHandler=new ImageDbHandler(context);
//      http connection object
        ImageHttpHandler reqCon = new ImageHttpHandler();

        try {

//            checking network available?
//            (true) check db && request url to string && insert data to db
//            ::
//            (false) get data from db
            if (networkConnectionChecker.isNetworkAvailable()) {

                jsonstring = reqCon.httpreq(reqUrl);

                if (!imageDbHandler.checkImage()) {
                    imageDbHandler.insertImage(jsonstring);
                }


            } else {

                dbJson = imageDbHandler.getImage();
                if (dbJson.get(0) != "noData") {
                    jsonstring = dbJson.get(0);
                } else {
                    jsonstring = null;
                }


            }
            if (jsonstring != null) {

                JSONObject jsonObject = new JSONObject(jsonstring).getJSONObject("photos");

                JSONArray jsonArray = jsonObject.getJSONArray("photo");

                for (int i = 0; i <= jsonArray.length(); i++) {

                    JSONObject temp = jsonArray.getJSONObject(i);
                    String imgUrl = GenerateImageURL(temp.getString("farm")
                            , temp.getString("server")
                            , temp.getString("id")
                            , temp.getString("secret"));

                    imageUrl = new ImageUrl(imgUrl);
                    imageUrls.add(imageUrl);

                }
            }

            }catch(MalformedURLException e1){

                e1.printStackTrace();

            }catch(JSONException e1){

                e1.printStackTrace();
            }

            return imageUrls;

    }

//  Generating image url
    private String GenerateImageURL(String farm_id,String server_id,String id,String secret) {

//      Combine url link by farmid,serverid,id,secret
        StringBuilder sb = new StringBuilder();
        sb.append("https://farm");
        sb.append(farm_id);
        sb.append(".staticflickr.com/");
        sb.append(server_id);
        sb.append("/");
        sb.append(id);
        sb.append("_");
        sb.append(secret);
        sb.append(".jpg");

        return sb.toString();

    }
}
