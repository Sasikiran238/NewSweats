package com.example.user.newsweats.Controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.Models.ImageUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by user on 24/2/17.
 */

public class ImageApi extends AsyncTask<String,Void,ArrayList> {

    ArrayList<ImageUrl> list_finish;
    ImageUrl imageUrl;
    Context context;
    ConChecker conChecker;
    Preferences share;
    

    public ImageApi(Context context) {
        this.context=context;
    }
    

    protected void onpreexecute(){
        super.onPreExecute();

    }
    @Override
    protected ArrayList doInBackground(String... params) {
        share = new Preferences(context);
        conChecker = new ConChecker(context);
        String jsonstring = null;

        list_finish = new ArrayList<>();
        String reqUrl = params[0];
        ImageHttp reqCon = new ImageHttp();
        Log.e("Test_inBack", reqUrl);

        Log.e("InBack", "before jsonget");
        
            try {
                if (conChecker.isNetworkAvailable()) {
                    jsonstring = reqCon.httpreq(reqUrl);
                    share.putImage(jsonstring);
//                Log.e("getShare",);
                } else {
                    if (share.getNews() != null) {
                        jsonstring = share.getImage();
                    }

                }

                Log.e("InBack  afterurl", jsonstring);

                JSONObject jsonObject = new JSONObject(jsonstring).getJSONObject("photos");
                JSONArray jsonArray = jsonObject.getJSONArray("photo");
                for (int i = 0; i <= jsonArray.length(); i++) {

                    JSONObject temp = jsonArray.getJSONObject(i);
                    String imgUrl = GenerateImageURL(temp.getString("farm"), temp.getString("server"), temp.getString("id"), temp.getString("secret"));

                    imageUrl = new ImageUrl(imgUrl);
                    list_finish.add(imageUrl);

                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();


            }
            return list_finish;


        
    }

    private String GenerateImageURL(String farm_id,String server_id,String id,String secret) {
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
