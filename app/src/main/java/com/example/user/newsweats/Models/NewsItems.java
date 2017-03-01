package com.example.user.newsweats.Models;

import android.util.Log;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

public class NewsItems {

    private String title;
    private String desc;
    private String image;
    private String url;


    public NewsItems(String title, String desc, String image, String url) {

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

        return title;

    }

    public void setTitle( String title) {

        this.title = title;

    }

    public String getDesc() {

        return desc;

    }

    public void setDesc(String desc) {

        this.desc = desc;

    }

    public String getImage() {

        return image;

    }

    public void setImage(String image) {

        this.image = image;

    }
}
