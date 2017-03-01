package com.example.user.newsweats.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

//  preferences fro Login  optionals Api ,settings
public class Preferences {

//    local Context
    Context con;
//    login preference
    SharedPreferences logShare;
//    Setting Preference
    SharedPreferences settingShare;
//    News Api preference
    SharedPreferences newsShare;
//    Image Api preference
    SharedPreferences imageShare;

//    Editors fro preferences
    SharedPreferences.Editor logEdit;
    SharedPreferences.Editor SettingEdit;
    SharedPreferences.Editor newsEdit;
    SharedPreferences.Editor imageEdit;

//    Preferences initialisation
    public Preferences(Context context) {

        this.con=context;
        logShare=context.getSharedPreferences("Logstate",con.MODE_PRIVATE);
        settingShare=context.getSharedPreferences("settings",con.MODE_PRIVATE);
        newsShare=context.getSharedPreferences("newsApi",con.MODE_PRIVATE);
        imageShare=context.getSharedPreferences("image",con.MODE_PRIVATE);

    }

//    set Login Status
    public  void setloggedin(boolean login){

        logEdit=logShare.edit();
        logEdit.putBoolean("loginmode",login);
        logEdit.commit();

    }

//    Optional settings
    public void setSettings(String theme){
        SettingEdit=settingShare.edit();
    }

    public  boolean loggedin(){

        return  logShare.getBoolean("loginmode",false);
    }

//    Optional settings
    public void putNews(String api){

        newsEdit=newsShare.edit();
        newsEdit.putString("newsUrl",api);
        newsEdit.apply();

    }

//    Optional settings
    public String getNews(){

        return newsShare.getString("newsUrl",null);

    }

//    Optional settings
    public void putImage(String api){

        imageEdit=imageShare.edit();
        imageEdit.putString("imageUrl",api);
        imageEdit.apply();

    }

//    Optional settings
    public String getImage(){

        return imageShare.getString("imageUrl",null);

    }
}
