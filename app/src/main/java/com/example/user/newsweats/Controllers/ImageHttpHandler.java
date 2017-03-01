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
 * Created by sasikiran on 24/2/17.
 * version 1.0
 */

//  download url from request handler for image url
public class ImageHttpHandler {
    public String httpreq(String url) throws MalformedURLException {

//        response
        String respond=null;
//        urlreq
        URL url_req=new URL(url);

//        intialize connection and get image json
        try {

            HttpURLConnection httpURLConnection=(HttpURLConnection) url_req.openConnection();
            InputStream bufferedInputStream=new BufferedInputStream(httpURLConnection.getInputStream());
            respond=stringconverter(bufferedInputStream);

        }
        catch (IOException e1) {

            e1.printStackTrace();

        }

        return respond;

    }

//    stream data to string canvertion
    public String stringconverter(InputStream bufferedInputStream){

//        one line string
        String line;
//        bufferreader initialization
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
