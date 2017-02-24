package com.example.user.newsweats.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.user.newsweats.Models.ImageItems;
import com.example.user.newsweats.Models.ListItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by user on 24/2/17.
 */

public class ImageApi extends AsyncTask<String,Void,ArrayList> {

    ArrayList<ImageItems> list_finish;
    ImageItems imageItems;

    String image;
    protected void onpreexecute(){
        super.onPreExecute();

    }
    @Override
    protected ArrayList doInBackground(String... params) {


        list_finish=new ArrayList<>();
        String reqUrl=params[0];
        ImageHttp reqCon=new ImageHttp();
        Log.e("Test_inBack",reqUrl);

        Log.e("InBack","before jsonget");
        try {
            String jsonstring=reqCon.httpreq(reqUrl);
            Log.e("InBack  afterurl",jsonstring);


            JSONArray jsonArticleArray=new JSONArray(jsonstring);
//

//
            Log.e("InJsonf  Loop","inside artical");

            for(int a=0;a<100;a++){

                Log.e("InJsonf  Loop",""+a+jsonArticleArray.length());
                // article Objects



                JSONObject articleObject=jsonArticleArray.getJSONObject(a);

                String image=articleObject.getString("post_url");

                Log.d("......................","---------------------------");
                Log.e("InJsonf  Loop","inside artical"+image);

                Log.e("IN back","before setdisp");


                imageItems=new ImageItems(image);



                list_finish.add(imageItems);



            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
