package com.example.user.newsweats.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 20/2/17.
 */

public class Preferences {

    Context con;
    SharedPreferences logShare;
    SharedPreferences settingShare;
    SharedPreferences newsShare;
    SharedPreferences imageShare;

    SharedPreferences.Editor logEdit;
    SharedPreferences.Editor SettingEdit;
    SharedPreferences.Editor newsEdit;
    SharedPreferences.Editor imageEdit;

    public Preferences(Context context) {
        this.con=context;

        logShare=context.getSharedPreferences("Logstate",con.MODE_PRIVATE);
        settingShare=context.getSharedPreferences("settings",con.MODE_PRIVATE);
        newsShare=context.getSharedPreferences("newsApi",con.MODE_PRIVATE);
        imageShare=context.getSharedPreferences("image",con.MODE_PRIVATE);
    }

    public  void setloggedin(boolean login){
        logEdit=logShare.edit();
         logEdit.putBoolean("loginmode",login);
        logEdit.commit();

    }
    public void setSettings(String theme){
        SettingEdit=settingShare.edit();
    }

    public  boolean loggedin(){

        return  logShare.getBoolean("loginmode",false);
    }

    public void putNews(String api){
        newsEdit=newsShare.edit();
        newsEdit.putString("newsUrl",api);
        newsEdit.apply();
    }
    public String getNews(){
        return newsShare.getString("newsUrl",null);
    }


    public void putImage(String api){
        imageEdit=imageShare.edit();
        imageEdit.putString("imageUrl",api);
        imageEdit.apply();
    }
    public String getImage(){
        return imageShare.getString("imageUrl",null);
    }
}
