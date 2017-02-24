package com.example.user.newsweats.Controllers;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 21/2/17.
 */

public class HttpHandler {

    public String httpreq(String url) throws MalformedURLException {

        String respond=null;
        URL url_req=new URL(url);
        Log.e("InAPIHandler","Beforeconnection");
        try {
            HttpURLConnection httpURLConnection=(HttpURLConnection) url_req.openConnection();


            InputStream bufferedInputStream=new BufferedInputStream(httpURLConnection.getInputStream());

            Log.e("InAPIHandler","after bufferStream");
            respond=stringconverter(bufferedInputStream);

            Log.e("InAPIHandler","afterrespond");

        } catch (IOException e1) {
            e1.printStackTrace();
        }



        return respond;
    }

    public String stringconverter(InputStream bufferedInputStream){

        Log.e("InAPIHandler","In convertstring");
        String line;
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(bufferedInputStream));
        StringBuilder stringBuilder=new StringBuilder();

        try {
            while((line=bufferedReader.readLine())!=null){

                stringBuilder.append(line).append('\n');

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }
}
