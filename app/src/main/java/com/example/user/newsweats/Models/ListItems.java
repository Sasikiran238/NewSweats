package com.example.user.newsweats.Models;

import android.util.Log;

/**
 * Created by user on 21/2/17.
 */

public class ListItems {



    private String title;
    //    private String date;
    private String desc;
    private String image;
    private String url;


    public ListItems(String title, String desc,String image,String url) {
        Log.e("IN Pojo","Inside constructor");
        this.title = title;
        this.desc = desc;
        this.image=image;
        this.url=url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        Log.e("IN Pojo","Inside get title");
        return title;
    }

    public void setTitle( String title) {
        Log.e("IN Pojo","Inside set title");
        this.title = title;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public String getDesc() {
        Log.e("IN Pojo","Inside get disp");

        return desc;
    }

    public void setDesc(String desc) {
        Log.e("IN Pojo","Inside set disp");

        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
